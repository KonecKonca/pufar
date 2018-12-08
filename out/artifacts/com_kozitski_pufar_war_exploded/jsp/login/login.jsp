<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>

    <title>${locale.getValue("commonTitle")}</title>

    <c:set var="context" value="${pageContext.request.contextPath}"/>

    <script src="${context}/js/login/validation.js" type="text/javascript"></script>

    <%--import bootstrap--%>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <script src="${context}/js/login/jquery-1.5.2.min.js" type="text/javascript"></script>

    <%--importnat for footer--%>
    <script src="${context}/js/login/jquery-3.2.1.min.js"></script>

    <script src="${context}/js/login/jquery.validation.min.js" type="text/javascript"></script>
    <link href="${context}/css/login/styles.css" rel="stylesheet" />


</head>
<body>

<div class="form">

    <br><br><br><br><br>
    <center><h1>${locale.getValue("loginMessage")}</h1></center>
    <br>

    <form action="/pufar" method="post" id="loginForm">

        <input type="hidden" value="LOGIN" name="command">

        <table align="center">
            <tr>
                <th align="right">${locale.getValue("loginLabel")}</th>
                <td><input type="text" name="login" placeholder="${locale.getValue("loginLabel")}" class="form-control"/></td>
            </tr>
            <tr>
                <th align="right">${locale.getValue("loginPasswordLabel")}</th>
                <td><input type="password" name="password" placeholder="${locale.getValue("loginPasswordLabel")}" class="form-control"/></td>
            </tr>
            <tr>
                <td colspan="2" align="right"><input type="submit" value="${locale.getValue("loginButton")}" class="btn btn-primary"/></td>
            </tr>
        </table>

    </form>


</div>

<div>
    <jsp:include page="../footer/footer.jsp"/>
</div>

</body>
</html>
