<%@ page import="by.hrachyshkin.provider.model.Tariff" %>
<%@ page import="by.hrachyshkin.provider.model.Account" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a href="${url}/index" class="navbar-brand">Provider</a>
        </div>
        <div>
            <ul class="nav navbar-nav">
                <li class="nav-item"><a href="${url}/tariffs"><b>Tariff plans</b></a></li>
                <li><a href="${url}/discounts"><b>Discounts</b></a></li>
                <li><a href="#"><b>About us</b></a></li>
                <li><a href="${url}/cabinet"><b>Personal Cabinet</b></a></li>
            </ul>
        </div>
    </div>
</nav>


<h1 class="text-center">Accounts</h1>


<div class="container">
<table class="table table-hover table-stripped">
    <tr>
        <th>Email</th>
        <th>Role</th>
        <th>Name</th>
        <th>Phone</th>
        <th>Address</th>
        <th>Balance</th>
        <th>Action</th>
    </tr>
    <c:forEach var="account" items="${accounts}">
        <tr>
            <form action="${url}/cabinet/update" method="POST">
                <td><input name="email" type="text" value="${account.email}"
                           placeholder="${account.email}"/></td>
                <td><select name="role">
                    <c:set var="roles" value="<%=Account.Role.values()%>"/>
                    <c:forEach var="role" items="${roles}">
                        <option value="${role.name()}" ${account.role.name()==role ?'selected':''}>${role.name()}</option>
                    </c:forEach>
                </select></td>
                <td><input name="name" type="text" value="${account.name}" placeholder="${account.name}"/>
                </td>
                <td><input name="phone" type="text" value="${account.phone}"
                           placeholder="${account.phone}"/></td>
                <td><input name="address" type="text" value="${account.address}"
                           placeholder="${account.address}"/></td>
                <td><input name="balance" type="number" value="${account.balance}"
                           placeholder="${account.balance}"/></td>
                <td>
                    <button type="submit" class="btn btn-info">update</button>
                </td>
                <input name="accountId" type="hidden" value="${account.id}">
            </form>
        </tr>
    </c:forEach>
</table>
</div>

<br>
<div class="row">
    <div class="col-md-2"></div>
    <div class="col-md-8">
        <form action="${url}/cabinet/accounts/create" method="POST">
            <input name="email" type="text" placeholder="email"/>
            <input name="password" type="text" placeholder="password"/>
            <select name="role">
                <c:set var="roles" value="<%=Account.Role.values()%>"/>
                <c:forEach var="role" items="${roles}">
                    <option value="${role.name()}">${role.name()}</option>
                </c:forEach>
            </select>
            <input name="name" type="text" placeholder="name"/>
            <input name="phone" type="text" placeholder="phone"/>
            <br> <input name="address" type="text" placeholder="address"/>
            <input name="balance" type="number" placeholder="balance"/>
            <button type="submit" class="btn btn-info">create</button>
        </form>
    </div>
    <div class="col-md-2"></div>
</div>


<div>
    ${error}
</div>
</body>
</html>