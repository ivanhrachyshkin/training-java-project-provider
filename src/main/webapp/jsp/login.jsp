<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ex" uri="/WEB-INF/custom.tld" %>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="langs"/>

<c:set var="url">${pageContext.request.contextPath}</c:set>

<html>
<head>
    <ex:Head/>
</head>
<body>
<nav class="navbar navbar-inverse">
    <div class="container">
        <div class="navbar-header">
            <a href="${url}/main" class="navbar-brand">Provider</a>
        </div>
        <ul class="nav navbar-nav">
            <li><a href="${url}/tariffs"><b><fmt:message key="tariffsLabel"/></b></a></li>
            <li><a href="${url}/discounts"><b><fmt:message key="discountsLabel"/></b></a></li>
            <li><a href="${url}/cabinet"><b><fmt:message key="cabinetLabel"/></b></a></li>
        </ul>
    </div>
</nav>

<div class="container">
    <div class="row">
        <div class="col-md-4"></div>
        <div class="col-md-4">
            <form role="form" action="${url}/login" method="POST">
                <div class="form-group">
                    <label><fmt:message key="emailLabel"/></label>
                    <input type="email" class="form-control" name="email" placeholder="<fmt:message key="emailLabel"/>">
                </div>
                <div class="form-group">
                    <label><fmt:message key="passwordLabel"/></label>
                    <input type="password" class="form-control" name="password"
                           placeholder="<fmt:message key="passwordLabel"/>">
                </div>
                <input type="submit" class="btn btn-info btn-sm" value=<fmt:message key="loginLabel"/>>
            </form>
        </div>
        <div class="col-md-4"></div>
    </div>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>