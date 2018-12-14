<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Pufar admin</title>
    <c:set var="context" value="${pageContext.request.contextPath}" />
</head>
<body>

<div class="adminContainer">

    <form action="/pufar" method="post" >
        <input type="hidden" value="DROP_USER" name="command">

        <input class="btn btn-danger" type="submit" value="${locale.getValue("adminDropUserButton")}">
    </form>


</div>

</body>
</html>