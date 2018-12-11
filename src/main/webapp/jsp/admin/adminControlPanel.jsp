<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Pufar admin</title>

    <c:set var="context" value="${pageContext.request.contextPath}" />
    <link href="${context}/css/admin/adminPanel.css" rel="stylesheet" />

    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">

    <c:set var="context" value="${pageContext.request.contextPath}" />

</head>
<body>

    <br><br><br>

    <div class="controlPanel">
        <table align="center">
            <tbody>

                <tr>
                    <th>
                        <form action="/pufar" method="post" >
                            <input type="hidden" value="DROP_USER" name="command">

                            <input class="btn btn-danger" type="submit" value="${locale.getValue("adminDropUserButton")}">
                        </form>
                    </th>

                    <th>
                        <form action="/pufar" accept-charset="utf-8" method="post" >
                            <input type="hidden" value="DROP_NOTIFICATION" name="command">

                            <input class="btn btn-warning" type="submit" value="${locale.getValue("adminDropNotificationButton")}">
                        </form>
                    </th>

                    <th>
                        <form action="/pufar" accept-charset="utf-8" method="post" >
                            <input type="hidden" value="CHOOSE_USER" name="command">

                            <input class="btn btn-dark" type="submit" value="${locale.getValue("adminChooseUserButton")}">
                        </form>
                    </th>

                </tr>

                <tr>

                    <th>
                        <form action="/pufar" accept-charset="utf-8" method="post" >
                            <input type="hidden" value="CHANGE_USER_LOGIN" name="command">

                            <input class="btn btn-danger" type="submit" value="${locale.getValue("adminChangeUserLoginButton")}">
                        </form>
                    </th>

                    <th>
                        <form action="/pufar" accept-charset="utf-8" method="post" >
                            <input type="hidden" value="CHANGE_NOTIFICATION_MESSAGE" name="command">

                            <input class="btn btn-warning" type="submit" value="${locale.getValue("adminChangeNotificationButton")}">
                        </form>
                    </th>

                    <th>
                        <form action="/pufar" accept-charset="utf-8" method="post" >
                            <input type="hidden" value="CHOOSE_NOTIFICATION" name="command">

                            <input class="btn btn-dark" type="submit" value="${locale.getValue("adminChooseNotificationButton")}">
                        </form>
                    </th>

                </tr>

                <tr>
                    <th>
                        <form action="/pufar" accept-charset="utf-8" method="post" >
                            <input type="hidden" value="CHANGE_USER_STATUS" name="command">

                            <input class="btn btn-danger" type="submit" value="${locale.getValue("adminChangeUserStatusButton")}">
                        </form>

                    </th>

                    <th>
                        <form action="/pufar" accept-charset="utf-8" method="post" >
                            <input type="hidden" value="DROP_COMMENT" name="command">

                            <input class="btn btn-warning" type="submit" value="${locale.getValue("adminDropCommentButton")}">
                        </form>
                    </th>

                </tr>

            <tbody>
        </table>

    </div>

    <div class="footer">
        <jsp:include page="${context}/jsp/footer/footer.jsp"/>
    </div>


</body>
</html>
