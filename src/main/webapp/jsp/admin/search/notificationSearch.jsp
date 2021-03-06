<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>

    <c:set var="context" value="${pageContext.request.contextPath}" />
    <link href="${context}/css/admin/adminPanel.css" rel="stylesheet" />
</head>
<body>

    <br><br><br>

    <div class="controlPanel">
        <form action="/pufar" method="post" >
            <input type="hidden" value="CHOSE_NOTIFICATION_EXECUTE" name="command">

            ${locale.getValue("adminNotificationId")}:
            <input type="text" class="form-control" placeholder="${locale.getValue("adminNotificationId")}" name="id"
                   pattern="[\d]{1,25}" title="${locale.getValue("commonLongPattern")}"/>
            ${locale.getValue("adminLowerPrice")}:
            <input type="text" class="form-control" placeholder="${locale.getValue("adminLowerPrice")}" name="adminLowerPrice"
                   pattern="[\d.]{1,25}" title="${locale.getValue("commonDoublePattern")}"/>
            ${locale.getValue("adminHigherPrice")}:
            <input type="text" class="form-control" placeholder="${locale.getValue("adminHigherPrice")}" name="adminHigherPrice"
                   pattern="[\d.]{1,25}" title="${locale.getValue("commonDoublePattern")}"/>

            ${locale.getValue("adminPassedTime")}:
            <input type="text" class="form-control" placeholder="${locale.getValue("adminPassedTime")}" name="adminPassedTime"
                    pattern="[\d]{1,25}" title="${locale.getValue("commonLongPattern")}"/>
            ${locale.getValue("adminSenderId")}:
            <input type="text" class="form-control" placeholder="${locale.getValue("adminSenderId")}" name="adminSenderId"
                   pattern="[\d]{1,25}" title="${locale.getValue("commonLongPattern")}"/>
            ${locale.getValue("adminNotificationUnit")}:
            <input type="text" class="form-control" placeholder="${locale.getValue("adminNotificationUnit")}" name="adminNotificationUnit"
                    pattern="(OTHER|other|AUTO|auto|HOME|home|CHILD|child|FOOD|food|WORK|work|BEAUTY|beauty|ELECTRONICS|electronics|)"
                   title="${locale.getValue("commonUnitPattern")}"/>

            ${locale.getValue("adminLowerRate")}:
            <input type="text" class="form-control" placeholder="${locale.getValue("adminLowerRate")}" name="adminLowerRate"
                   pattern="[\d.]{1,25}" title="${locale.getValue("commonDoublePattern")}"/>
            ${locale.getValue("adminBiggerRate")}:
            <input type="text" class="form-control" placeholder="${locale.getValue("adminBiggerRate")}" name="adminBiggerRate"
                   pattern="[\d.]{1,25}" title="${locale.getValue("commonDoublePattern")}"/>

            <br>

            <input class="btn btn-dark" type="submit" value="${locale.getValue("adminChooseNotificationButton")}">
        </form>
    </div>

</body>

<div class="footer">
    <jsp:include page="${context}/jsp/footer/footer.jsp"/>
</div>


</html>

