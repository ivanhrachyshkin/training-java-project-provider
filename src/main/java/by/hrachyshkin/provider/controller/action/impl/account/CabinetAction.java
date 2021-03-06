package by.hrachyshkin.provider.controller.action.impl.account;

import by.hrachyshkin.provider.ResourceBundleFactory;
import by.hrachyshkin.provider.controller.action.impl.BaseAction;
import by.hrachyshkin.provider.controller.action.impl.WelcomeAction;
import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.model.Account;
import by.hrachyshkin.provider.service.AccountService;
import by.hrachyshkin.provider.service.ServiceException;
import by.hrachyshkin.provider.service.ServiceFactory;
import by.hrachyshkin.provider.service.ServiceKeys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ResourceBundle;

public class CabinetAction extends BaseAction {

    public static final String CABINET = "/cabinet";
    private final ResourceBundle rb = ResourceBundleFactory.getINSTANCE().getRb();

    @Override
    public String execute(final HttpServletRequest request,
                          final HttpServletResponse response) {

        String page = null;

        try {
            final AccountService accountService = ServiceFactory.getINSTANCE()
                    .getService(ServiceKeys.ACCOUNT);

            final Account account =
                    accountService.findOneById(getAccountId(request));

            if (getRole(request).equals(Account.Role.BLOCKED)) {
                request.setAttribute("error", rb.getString("account.blocked.exception"));
                page = WelcomeAction.WELCOME;
            }

            if (getRole(request).equals(Account.Role.ADMINISTRATOR)) {
                request.setAttribute("account", account);
                page = "/jsp/cabinet-admin.jsp";
            }

            if (getRole(request).equals(Account.Role.USER)) {
                request.setAttribute("account", account);
                page = "/jsp/cabinet-user.jsp";
            }

        } catch (ServiceException | NumberFormatException
                | TransactionException e) {
            setErrorAttributeToSession(request, e.getMessage());
            page = WelcomeAction.WELCOME;
        }
        return page;
    }
}
