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
                            <input type="hidden" value="BAN_USER" name="command">

                            <input type="text" name="id" placeholder="${locale.getValue("commonId")}" size="5" class="form-control-sm" required
                                   pattern="[\d]{1,25}"/><br>
                            <input class="btn btn-danger" type="submit" value="${locale.getValue("adminDropUserButton")}">
                        </form>
                    </th>

                    <th>
                        <form action="/pufar" accept-charset="utf-8" method="post" >
                            <input type="hidden" value="CHANGE_USER_LOGIN" name="command">

                            <input type="text" name="id" placeholder="${locale.getValue("commonId")}" size="5" class="form-control-sm" required
                                   pattern="[\d]{1,25}"/>
                            <input type="text" name="login" placeholder="${locale.getValue("adminChangeNewLoginField")}" size="8" class="form-control-sm" required
                                   pattern="[\w.@а-яА-яёЁ]{5,20}" /><br>
                            <input class="btn btn-warning" type="submit" value="${locale.getValue("adminChangeUserLoginButton")}">
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
                        <form action="/pufar" method="post" >
                            <input type="hidden" value="UNBAN_USER" name="command">

                            <input type="text" name="id" placeholder="${locale.getValue("commonId")}" size="5" class="form-control-sm" required
                                   pattern="[\d]{1,25}" title="${locale.getValue("commonLongPattern")}"/><br>
                            <input class="btn btn-danger" type="submit" value="${locale.getValue("adminUnDropUserButton")}">
                        </form>
                    </th>

                    <th>
                        <form action="/pufar" accept-charset="utf-8" method="post" >
                            <input type="hidden" value="CHANGE_NOTIFICATION_MESSAGE" name="command">

                            <input type="text" name="id" placeholder="${locale.getValue("commonId")}" size="5" class="form-control-sm" required
                                   pattern="[\d]{1,25}" title="${locale.getValue("commonLongPattern")}"/>
                            <input type="text" name="message" placeholder="${locale.getValue("adminChangeNewMessageField")}" size="8" class="form-control-sm" required
                                   pattern=".{2,1000}" title="${locale.getValue("commonNotificationPattern")}"/><br>
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
                            <input type="hidden" value="DROP_COMMENT" name="command">

                            <input type="text" name="id" placeholder="${locale.getValue("commonId")}" size="5" class="form-control-sm" required
                                   pattern="[\d]{1,25}" title="${locale.getValue("commonLongPattern")}"/><br>
                            <input class="btn btn-danger" type="submit" value="${locale.getValue("adminDropCommentButton")}">
                        </form>
                    </th>

                    <th>
                        <form action="/pufar" accept-charset="utf-8" method="post" >
                            <input type="hidden" value="CHANGE_USER_STATUS" name="command">

                            <input type="text" name="id" placeholder="${locale.getValue("commonId")}" size="5" class="form-control-sm" required
                                   pattern="[\d]{1,25}" title="${locale.getValue("commonLongPattern")}"/>
                            <input type="text" name="status" placeholder="${locale.getValue("adminChangeNewStatusField")}" size="8" class="form-control-sm" minlength="1"
                                   pattern="(ADMIN|admin|SUPER_ADMIN|super_admin|SIMPLE_USER|simple_user)" title="${locale.getValue("commonChangeUserStatusPattern")} "/><br>
                            <input class="btn btn-warning" type="submit" value="${locale.getValue("adminChangeUserStatusButton")}">
                        </form>

                    </th>

                </tr>

                <tr>
                    <th>
                        <form action="/pufar" accept-charset="utf-8" method="post" >
                            <input type="hidden" value="DROP_NOTIFICATION" name="command">

                            <input type="text" name="id" placeholder="${locale.getValue("commonId")}" size="5" class="form-control-sm" required
                                   pattern="[\d]{1,25}" title="${locale.getValue("commonLongPattern")}"/><br>
                            <input class="btn btn-danger" type="submit" value="${locale.getValue("adminDropNotificationButton")}">
                        </form>
                    </th>

                </tr>

            </tbody>
        </table>

        <br><br>

        <h2>${adminInputMessage}</h2>

        <c:if test="${adminInputResult != null}">
            <h2>......</h2>
        </c:if>
        <c:forEach var="listElement" items="${adminInputResult}" varStatus="status">
            <h3 class="font-weight-bold">
                ${status.count}) ${listElement}
            </h3>
        </c:forEach>
        <c:if test="${adminInputResult != null}">
            <h2>......</h2>
        </c:if>


    </div>


</body>

<div class="footer">
    <jsp:include page="${context}/jsp/footer/footer.jsp"/>
</div>

</html>
