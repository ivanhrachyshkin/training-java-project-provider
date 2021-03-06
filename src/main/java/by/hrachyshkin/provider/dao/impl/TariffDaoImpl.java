package by.hrachyshkin.provider.dao.impl;

import by.hrachyshkin.provider.dao.DaoException;
import by.hrachyshkin.provider.dao.TariffDao;
import by.hrachyshkin.provider.model.Tariff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TariffDaoImpl implements TariffDao {

    private static final String EXISTS_BY_ID_QUERY =
            "SELECT EXISTS ("
                    + "SELECT * "
                    + "FROM tariffs "
                    + "WHERE id = ?"
                    + ")";

    private static final String EXISTS_BY_NAME_QUERY =
            "SELECT EXISTS ("
                    + "SELECT * "
                    + "FROM tariffs "
                    + "WHERE name = ?"
                    + ")";

    private static final String EXISTS_BY_NOT_ID_AND_NAME_QUERY =
            "SELECT EXISTS ("
                    + "SELECT * "
                    + "FROM tariffs "
                    + "WHERE id != ? AND name = ?"
                    + ")";

    private static final String FIND_QUERY =
            "SELECT id, name, type, speed, price "
                    + "FROM tariffs ";

    private static final String FIND_AND_SORT_BY_SPEED_AND_PRICE_QUERY =
            "SELECT id, name, type, speed, price "
                    + "FROM tariffs "
                    + "ORDER BY speed DESC, price ASC "
                    + "LIMIT 5 OFFSET ?";

    private static final String FIND_AND_FILTER_BY_TYPE_QUERY =
            "SELECT id, name, type, speed, price "
                    + "FROM tariffs "
                    + "WHERE type = ? "
                    + "LIMIT 5 OFFSET ?";

    private static final String FIND_AND_FILTER_AND_SORT_QUERY =
            "SELECT id, name, type, speed, price "
                    + "FROM tariffs "
                    + "WHERE type = ? "
                    + "ORDER by speed DESC, price ASC ";

    private static final String FIND_ONE_BY_ID_QUERY =
            "SELECT id, name, type, speed, price "
                    + "FROM tariffs "
                    + "WHERE id = ?";

    private static final String ADD_QUERY =
            "INSERT "
                    + "INTO tariffs (name, type, speed, price) "
                    + "VALUES (?, ?, ?, ?)";

    private static final String UPDATE_QUERY =
            "UPDATE tariffs "
                    + "SET name = ?, type  = ?, speed  = ?, price  = ? "
                    + "WHERE id = ?";

    private static final String DELETE_QUERY =
            "DELETE "
                    + "FROM tariffs "
                    + "WHERE id = ?";

    private final Connection connection;
    private final ResourceBundle rb;

    public TariffDaoImpl(final Connection connection, final ResourceBundle rb) {
        this.connection = connection;
        this.rb = rb;
    }

    @Override
    public boolean isExistById(final Integer id) throws DaoException {

        try (final PreparedStatement statement =
                     connection.prepareStatement(EXISTS_BY_ID_QUERY)) {
            statement.setInt(1, id);

            try (final ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getBoolean(1);
            }

        } catch (SQLException e) {
            throw new DaoException(
                    rb.getString("tariff.exist.by.id.exception"), e);
        }
    }

    @Override
    public boolean isExistByName(final String name) throws DaoException {

        try (final PreparedStatement statement =
                     connection.prepareStatement(EXISTS_BY_NAME_QUERY)) {
            statement.setString(1, name);

            try (final ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getBoolean(1);
            }

        } catch (SQLException e) {
            throw new DaoException(
                    rb.getString("tariff.exist.by.name.exception"), e);
        }
    }

    @Override
    public boolean isExistByNotIdAndName(final Integer id, final String name)
            throws DaoException {

        try (final PreparedStatement statement = connection
                .prepareStatement(EXISTS_BY_NOT_ID_AND_NAME_QUERY)) {
            statement.setInt(1, id);
            statement.setString(2, name);

            try (final ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getBoolean(1);
            }

        } catch (SQLException e) {
            throw new DaoException(
                    rb.getString("tariff.exist.by.not.name.and.id"
                            + ".exception"), e);
        }
    }

    @Override
    public List<Tariff> find() throws DaoException {

        try (final PreparedStatement statement =
                     connection.prepareStatement(FIND_QUERY);
             final ResultSet resultSet = statement.executeQuery()) {

            final List<Tariff> tariffs = new ArrayList<>();
            while (resultSet.next()) {
                final Tariff tariff = buildTariff(resultSet);
                tariffs.add(tariff);
            }
            return tariffs;

        } catch (Exception e) {
            throw new DaoException(rb.getString("tariff.find.exception"));
        }
    }

    @Override
    public List<Tariff> findAndSortBySpeedAndPriceOffset(final int offset)
            throws DaoException {

        try (final PreparedStatement statement = connection
                .prepareStatement(FIND_AND_SORT_BY_SPEED_AND_PRICE_QUERY)) {
            statement.setInt(1, offset);

            try (final ResultSet resultSet = statement.executeQuery()) {

                final List<Tariff> tariffs = new ArrayList<>();
                while (resultSet.next()) {
                    final Tariff tariff = buildTariff(resultSet);
                    tariffs.add(tariff);
                }
                return tariffs;
            }
        } catch (Exception e) {
            throw new DaoException(
                    rb.getString("tariff.find.or.sort.by.speed.and"
                            + ".price.exception"));
        }
    }

    @Override
    public List<Tariff> findAndFilterByTypeOffset(final Tariff.Type type,
                                                  final int offset)
            throws DaoException {

        try (final PreparedStatement statement = connection
                .prepareStatement(FIND_AND_FILTER_BY_TYPE_QUERY)) {
            statement.setInt(1, type.ordinal());
            statement.setInt(2, offset);

            try (final ResultSet resultSet = statement.executeQuery()) {
                final List<Tariff> tariffs = new ArrayList<>();
                while (resultSet.next()) {
                    final Tariff tariff = buildTariff(resultSet);
                    tariffs.add(tariff);
                }
                return tariffs;
            }

        } catch (Exception e) {
            throw new DaoException(
                    rb.getString("tariff.find.or.filter.by.type"
                            + ".exception"));
        }
    }

    @Override
    public List<Tariff> findAndFilterAndSort(final Tariff.Type type)
            throws DaoException {

        try (final PreparedStatement statement = connection
                .prepareStatement(FIND_AND_FILTER_AND_SORT_QUERY)) {
            statement.setInt(1, type.ordinal());

            try (final ResultSet resultSet = statement.executeQuery()) {
                final List<Tariff> tariffs = new ArrayList<>();
                while (resultSet.next()) {
                    final Tariff tariff = buildTariff(resultSet);
                    tariffs.add(tariff);
                }
                return tariffs;
            }

        } catch (Exception e) {
            throw new DaoException(
                    rb.getString("tariff.find.or.filter.by.type.or.sort"
                            + ".by.speed.exception"));
        }
    }

    @Override
    public Tariff findOneById(final Integer id) throws DaoException {

        try (final PreparedStatement statement =
                     connection.prepareStatement(FIND_ONE_BY_ID_QUERY)) {
            statement.setInt(1, id);

            try (final ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return buildTariff(resultSet);
            }

        } catch (SQLException e) {
            rb.getString("tariff.find.one.by.id.exception");
            throw new DaoException("Can't find tariff by id", e);
        }
    }

    @Override
    public void add(final Tariff tariff) throws DaoException {

        try (final PreparedStatement statement =
                     connection.prepareStatement(ADD_QUERY)) {
            statement.setString(1, tariff.getName());
            statement.setInt(2, tariff.getType().ordinal());
            statement.setInt(3, tariff.getSpeed());
            statement.setDouble(4, tariff.getPrice());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException(rb.getString("tariff.add.exception"), e);
        }
    }

    @Override
    public void update(final Tariff tariff) throws DaoException {

        try (final PreparedStatement statement =
                     connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, tariff.getName());
            statement.setInt(2, tariff.getType().ordinal());
            statement.setInt(3, tariff.getSpeed());
            statement.setDouble(4, tariff.getPrice());

            statement.setInt(5, tariff.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException(
                    rb.getString("tariff.update.exception"), e);
        }
    }

    @Override
    public void delete(final Integer id) throws DaoException {

        try (final PreparedStatement statement =
                     connection.prepareStatement(DELETE_QUERY)) {
            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException(
                    rb.getString("tariff.delete.exception"), e);
        }
    }

    private Tariff buildTariff(final ResultSet resultSet) throws SQLException {

        return new Tariff(
                resultSet.getInt(1),
                resultSet.getString(2),
                Tariff.Type.values()[resultSet.getInt(3)],
                resultSet.getInt(4),
                resultSet.getFloat(5));
    }
}
