package by.hrachyshkin.servlet;

import by.hrachyshkin.entity.Account;
import by.hrachyshkin.entity.Tariff;
import by.hrachyshkin.service.AccountServiceImpl;
import by.hrachyshkin.service.ServiceFactoryImpl;
import by.hrachyshkin.service.ServiceKeys;
import by.hrachyshkin.service.TariffServiceImpl;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

@WebServlet("/tariffs")
public class TariffsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @SneakyThrows
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        TariffServiceImpl tariffService = ServiceFactoryImpl.getINSTANCE().getService(ServiceKeys.TARIFF_SERVICE);

        final List<Tariff> tariffs;
        final String rawType = request.getParameter("filter");


        if(rawType != null) {
            final Tariff.Type type = Tariff.Type.valueOf(rawType.toUpperCase());
            tariffs = tariffService.findAndFilterByType(type);
        } else tariffs = tariffService.find();

        request.setAttribute("tariffs", tariffs);
        request.getRequestDispatcher("tariffs.jsp").forward(request, response);
    }
}