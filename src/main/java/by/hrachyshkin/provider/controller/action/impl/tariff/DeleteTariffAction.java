package by.hrachyshkin.provider.controller.action.impl.tariff;

import by.hrachyshkin.provider.controller.action.impl.BaseAction;
import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.service.ServiceException;
import by.hrachyshkin.provider.service.ServiceFactory;
import by.hrachyshkin.provider.service.ServiceKeys;
import by.hrachyshkin.provider.service.TariffService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteTariffAction extends BaseAction  {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ServiceException, TransactionException {

        try {
            final TariffService tariffService = ServiceFactory.getINSTANCE().getService(ServiceKeys.TARIFF_SERVICE);

            final Integer tariffId = Integer.valueOf(request.getParameter("tariffId"));
            tariffService.delete(tariffId);

        } catch (ServiceException | NumberFormatException e) {
            request.setAttribute("error", e.getMessage());
        }
        return "/tariffs";
    }
}
