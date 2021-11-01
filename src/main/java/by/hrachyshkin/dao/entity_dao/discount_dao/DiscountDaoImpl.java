package by.hrachyshkin.dao.entity_dao.discount_dao;

import by.hrachyshkin.dao.BaseDao;
import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.entity.Criteria;
import by.hrachyshkin.entity.Discount;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DiscountDaoImpl extends BaseDao implements DiscountDao {

    private static final String IS_EXIST_DISCOUNT_QUERY =
            "SELECT EXISTS(SELECT 1 FROM discounts WHERE name = ?) ";

    private static final String CREATE_DISCOUNT_QUERY =
            "INSERT " +
                    "INTO discounts (name, type, value) " +
                    "VALUES (?, ?, ?) " +
                    "ON CONFLICT DO NOTHING";

    private static final String FIND_ALL_DISCOUNTS_QUERY =
            "SELECT id, name, type, value " +
                    "FROM discounts";

    private static final String FIND_ALL_DISCOUNTS_QUERY_WITH_SORT =
            "SELECT id, name, type, value " +
                    "FROM discounts " +
                    "ORDER BY ? ?";

    private static final String FIND_ALL_DISCOUNTS_QUERY_BY_FILTER =
            "SELECT id, name, type, value " +
                    "FROM discounts " +
                    "WHERE ? LIKE ?%";

    private static final String FIND_ONE_DISCOUNT_QUERY_BY_ID =
            "SELECT id, name, type, value " +
                    "FROM discounts " +
                    "WHERE id = ?";

    private static final String UPDATE_DISCOUNT_QUERY =
            "INSERT INTO discounts (name, type, value) " +
                    "VALUES ?, ?, ?" +
                    "WHERE id = ? " +
                    "ON CONFLICT DO UPDATE";

    private static final String DELETE_DISCOUNT_BY_ID_QUERY =
            "DELETE id, name, type, value " +
                    "FROM discounts " +
                    "WHERE id = ?";


    public DiscountDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public boolean isExist(final String name) throws DaoException {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(IS_EXIST_DISCOUNT_QUERY);
             final ResultSet resultSet = statement.executeQuery()) {

            statement.setString(1, name);
            resultSet.next();
            return resultSet.getBoolean(1);

        } catch (SQLException e) {
            throw new DaoException("Can't find account by id", e);
        }
    }

    @Override
    public void create(final Discount discount) throws DaoException {

        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(CREATE_DISCOUNT_QUERY)) {

            statement.setString(1, discount.getName());
            statement.setInt(2, discount.getType().ordinal());
            statement.setInt(3, discount.getValue());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Can't create discount", e);
        }
    }

    @Override
    public List<Discount> find(final Criteria criteria) throws DaoException {

        try (final Connection connection = dataSource.getConnection()) {

            String query;
            if (criteria.getSorting() != null) {
                query = FIND_ALL_DISCOUNTS_QUERY_WITH_SORT;
            } else if (criteria.getFilter() != null) {
                query = FIND_ALL_DISCOUNTS_QUERY_BY_FILTER;
            } else query = FIND_ALL_DISCOUNTS_QUERY;

            try (final PreparedStatement statement = connection.prepareStatement(query);
                 final ResultSet resultSet = statement.executeQuery()) {

                if (criteria.getSorting() != null) {
                    statement.setString(1, criteria.getSorting().getColumn());
                    statement.setString(2, criteria.getSorting().getDirection().name());
                } else if (criteria.getFilter() != null) {
                    statement.setString(1, criteria.getFilter().getColumn());
                    statement.setString(2, criteria.getFilter().getPattern());
                }


                final List<Discount> discounts = new ArrayList<>();
                while (resultSet.next()) {
                    final Discount discount = new Discount(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            Discount.Type.values()[resultSet.getInt(3)],
                            resultSet.getInt(4));
                    discounts.add(discount);
                }

                return discounts;
            }
        } catch (Exception e) {
            throw new DaoException("Can't find required discounts");
        }
    }

    @Override
    public Discount findOneById(int id) throws DaoException {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(FIND_ONE_DISCOUNT_QUERY_BY_ID);
             final ResultSet resultSet = statement.executeQuery()) {

            statement.setInt(1, id);
            resultSet.next();

            return new Discount(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    Discount.Type.values()[resultSet.getInt(3)],
                    resultSet.getInt(4));

        } catch (SQLException e) {
            throw new DaoException("Can't find discount by id", e);
        }
    }

    @Override
    public void update(final Discount discount) throws DaoException {

        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(UPDATE_DISCOUNT_QUERY)) {

            statement.setString(1, discount.getName());
            statement.setString(2, discount.getType().name());
            statement.setInt(3, discount.getValue());

            statement.setInt(4, discount.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Can't add discount", e);
        }
    }

    @Override
    public void delete(final int id) throws DaoException {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(DELETE_DISCOUNT_BY_ID_QUERY)) {

            statement.setInt(1, id);
            statement.executeQuery();

        } catch (SQLException e) {
            throw new DaoException("Can't delete discount", e);
        }
    }
}
