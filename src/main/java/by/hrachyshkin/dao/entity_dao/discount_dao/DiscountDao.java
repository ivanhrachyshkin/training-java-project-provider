package by.hrachyshkin.dao.entity_dao.discount_dao;

import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.dao.entity_dao.EntityDao;
import by.hrachyshkin.entity.Discount;

public interface DiscountDao extends EntityDao<Discount> {

   boolean isExist(String name) throws DaoException;
}
