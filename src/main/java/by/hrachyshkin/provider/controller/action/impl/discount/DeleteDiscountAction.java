package by.hrachyshkin.provider.controller.action.impl.discount;

import by.hrachyshkin.provider.controller.action.impl.BaseAction;
import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.service.DiscountService;
import by.hrachyshkin.provider.service.ServiceException;
import by.hrachyshkin.provider.service.ServiceFactory;
import by.hrachyshkin.provider.service.ServiceKeys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteDiscountAction extends BaseAction {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        try {
            final DiscountService discountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.DISCOUNT_SERVICE);

            final Integer id = Integer.valueOf(request.getParameter("discountId"));
            discountService.delete(id);

        } catch (ServiceException | NumberFormatException | TransactionException e) {
            request.setAttribute("error", e.getMessage());
        }
        return "/discounts";
    }
}