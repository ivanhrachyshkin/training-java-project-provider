package by.hrachyshkin.provider.dao;

import by.hrachyshkin.provider.model.Bill;

import java.util.List;

public interface BillDao extends Dao<Bill> {

    boolean isExists(final Bill bill) throws DaoException;

    List<Bill> findAndFilterBySubscriptionId(Integer subscriptionId) throws DaoException;

    List<Bill> findAndSortByDate() throws DaoException;

    List<Bill> findAndFilterAndSort(Integer subscriptionId) throws DaoException;
}