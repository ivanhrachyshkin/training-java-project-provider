package by.hrachyshkin.provider.controller.action.impl.subscription;

import by.hrachyshkin.provider.controller.action.impl.ActionException;
import by.hrachyshkin.provider.controller.action.impl.BaseAction;
import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.model.Subscription;
import by.hrachyshkin.provider.service.ServiceException;
import by.hrachyshkin.provider.service.ServiceFactory;
import by.hrachyshkin.provider.service.ServiceKeys;
import by.hrachyshkin.provider.service.SubscriptionService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteSubscriptionAction extends BaseAction {

    public static final String DELETE_SUBSCRIPTION =
            "/cabinet/subscriptions/delete";

    @Override
    public String execute(final HttpServletRequest request,
                          final HttpServletResponse response)
            throws ServletException, IOException, ServiceException {

        try {
            checkGetHTTPMethod(request);
            final SubscriptionService subscriptionService = ServiceFactory
                    .getINSTANCE().getService(ServiceKeys.SUBSCRIPTION);

            final Integer accountId = getAccountId(request);
            final Integer tariffId = getIntParameter(request, "tariffId");
            final Subscription subscription = subscriptionService.
                    findOneByAccountIdAndTariffId(accountId, tariffId);

            subscriptionService.delete(subscription);

        } catch (ServiceException | TransactionException | ActionException e) {
            setErrorAttributeToSession(request, e.getMessage());
        }
        return ShowSubscriptionsForAccountAction.SUBSCRIPTIONS;
    }

    @Override
    public void postExecute(final HttpServletRequest request,
                            final HttpServletResponse response,
                            final String path)
            throws ServletException, IOException, ServiceException {
        response.sendRedirect(request.getContextPath() + path);
    }
}
