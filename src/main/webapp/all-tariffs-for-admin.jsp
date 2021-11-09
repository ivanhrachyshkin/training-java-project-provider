<%@ page import="by.hrachyshkin.provider.model.Tariff" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="langs"/>

<!doctype html>
<html lang="ru">
<head>
    <c:set var="url">${pageContext.request.contextPath}</c:set>
    <title>Provider</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
            crossorigin="anonymous"></script>
</head>
<body>
<jsp:include page="navbar.jsp" />

<div class="container">
    <h1 class="text-center"><fmt:message key="tariffsLabel"/></h1>
    <br>
    <a href="${url}/tariffs?filter=trafficked" class="btn btn-info"><fmt:message key="traffickedLabel"/></a>
    <a href="${url}/tariffs?filter=unlimited" class="btn btn-info"><fmt:message key="unlimitedLabel"/></a>
    <a href="${url}/tariffs?filter=all" class="btn btn-info">all</a>
</div>

<div class="container">
    <table class="table table-hover table-stripped">
        <tr>
            <th><fmt:message key="nameLabel"/></th>
            <th><fmt:message key="typeLabel"/></th>
            <th><fmt:message key="speedLabel"/></th>
            <th><fmt:message key="priceLabel"/></th>
        </tr>
        <c:forEach var="tariff" items="${tariffs}">
            <tr>
                <form action="${url}/tariffs/update" method="POST">
                    <td><input name="name" type="text" value="${tariff.name}" placeholder="${tariff.name}"/></td>
                    <td><select name="type">
                        <c:set var="types" value="<%=Tariff.Type.values()%>"/>
                        <c:forEach var="type" items="${types}">
                            <option value="${type.name()}" ${tariff.type.name()==type ?'selected':''}>${type.name()}</option>
                        </c:forEach>
                    </select></td>
                    <td><input name="speed" type="number" value="${tariff.speed}" placeholder="${tariff.speed}"/></td>
                    <td><input name="price" type="number" value="${tariff.price}" placeholder="${tariff.price}"/></td>
                    <td><button type="submit" class="btn btn-info"><fmt:message key="updateLabel"/></button></td>
                    <input name="tariffId" type="hidden" value="${tariff.id}">
                </form>
                <form action="${url}/tariffs/delete" method="POST">
                    <td><button type="submit" class="btn btn-info"><fmt:message key="deleteLabel"/></button></td>
                    <input name="tariffId" type="hidden" value="${tariff.id}">
                </form>
                <form action="${url}/tariffs/discounts" method="POST">
                    <td><button type="submit" class="btn btn-info"><fmt:message key="discountsLabel"/></button></td>
                    <input name="tariffId" type="hidden" value="${tariff.id}">
                </form>
            </tr>
        </c:forEach>
        <tr>
            <form action="${url}/tariffs/create" method="POST">
                <td><input name="name" type="text"/></td>
                <td><select name="type">
                    <c:set var="types" value="<%=Tariff.Type.values()%>"/>
                    <c:forEach var="type" items="${types}">
                        <option value="${type.name()}">${type.name()}</option>
                    </c:forEach>
                </select></td>
                <td><input name="speed" type="number"/></td>
                <td><input name="price" type="number"/></td>
                <td><button type="submit" class="btn btn-info"><fmt:message key="createLabel"/></button></td>
            </form>
        </tr>
    </table>
</div>
<jsp:include page="footer.jsp" />
</body>
</html>