<%@ page contentType="text/html;charset=UTF-8"%>
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
        <input type="hidden" value="CREATE_NOTIFICATION" name="command">

        ${locale.getValue("adminSearchUserId")}:
        <input type="text" class="form-control" placeholder="${locale.getValue("adminSearchUserId")}" name="adminSearchUserId"/>
        ${locale.getValue("adminSearchUserLoginStart")}:
        <input type="text" class="form-control" placeholder="${locale.getValue("adminSearchUserLoginStart")}" name="adminSearchUserLoginStart"/>
        ${locale.getValue("adminSearchUserStatus")}:
        <input type="text" class="form-control" placeholder="${locale.getValue("adminSearchUserStatus")}" name="adminSearchUserStatus"/>

        <br>

        <input class="btn btn-dark" type="submit" value="${locale.getValue("adminSearchUserButton")}">
    </form>

</div>

</body>

<div class="footer">
    <jsp:include page="${context}/jsp/footer/footer.jsp"/>
</div>


</html>