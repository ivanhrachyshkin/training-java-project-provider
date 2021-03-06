package by.hrachyshkin.provider.service;

import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.model.Subscription;

import java.time.LocalDate;
import java.util.List;

public interface SubscriptionService extends Service<Subscription> {

    List<Subscription> findAndFilterByAccountId(Integer accountId)
            throws ServiceException, TransactionException;

    Subscription findOneByAccountIdAndTariffId(Integer accountId,
                                               Integer tariffId)
            throws ServiceException, TransactionException;

    void delete(final Subscription subscription)
            throws ServiceException, TransactionException;

    void payBillForSubscription(Integer accountId,
                                Integer subscriptionIdForBill,
                                Float value,
                                LocalDate date)
            throws ServiceException, TransactionException;

}
