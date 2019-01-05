<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>

    <c:set var="context" value="${pageContext.request.contextPath}" />
    <link href="${context}/css/admin/adminPanel.css" rel="stylesheet" />
</head>
<body>

<br><br>

<div class="controlPanel">

    <h2>${locale.getValue("createNotificationCommonMessage")}</h2>

    <form action="/upload" enctype="multipart/form-data" method="POST">

        <h3>${locale.getValue("createNotificationImage")}</h3>
        <input type="file" name="${locale.getValue("createNotificationImage")}">
        <input class="btn btn-dark" type="submit" value="${locale.getValue("createNotificationImage")}" title="${locale.getValue("createNotificationButtonAltChose")}">
    </form>

    <c:choose>
        <c:when test="${notificationImagePath != null}">
            <form action="/pufar" method="post" >
                <input type="hidden" name="command" value="CREATE_NOTIFICATION">

                    ${locale.getValue("createNotificationUnit")}:
                <div class="form-check">
                    <input class="form-check-input" type="radio" value="OTHER" name="radios" id="option1" checked>
                    <label class="form-check-label" for="option1">
                            ${locale.getValue("createNotificationUnit1")}
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="radio" value="AUTO" name="radios" id="option2">
                    <label class="form-check-label" for="option2">
                            ${locale.getValue("createNotificationUnit2")}
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="radio" value="HOME" name="radios" id="option3">
                    <label class="form-check-label" for="option3">
                            ${locale.getValue("createNotificationUnit3")}
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="radio" value="CHILD" name="radios" id="option4">
                    <label class="form-check-label" for="option4">
                            ${locale.getValue("createNotificationUnit4")}
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="radio" value="FOOD" name="radios" id="option5">
                    <label class="form-check-label" for="option5">
                            ${locale.getValue("createNotificationUnit5")}
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="radio" value="WORK" name="radios" id="option6">
                    <label class="form-check-label" for="option6">
                            ${locale.getValue("createNotificationUnit6")}
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="radio" value="BEAUTY" name="radios" id="option7">
                    <label class="form-check-label" for="option7">
                            ${locale.getValue("createNotificationUnit7")}
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="radio" value="ELECTRONICS" name="radios" id="option8">
                    <label class="form-check-label" for="option8">
                            ${locale.getValue("createNotificationUnit8")}
                    </label>
                </div>

                    ${locale.getValue("createNotificationMessage")}:
                <input type="text" class="form-control" placeholder="${locale.getValue("createNotificationMessage")}" name="createNotificationMessage" required
                       pattern="[\w.@а-яА-яёЁ]{1,1000}" title="${locale.getValue("commonNotificationPattern")}" />
                    ${locale.getValue("createNotificationPrice")}:
                <input type="text" class="form-control" placeholder="${locale.getValue("createNotificationPrice")}" name="createNotificationPrice" required
                       pattern="[\d.]{1,25}" title="${locale.getValue("commonDoublePattern")}"/>

                <input class="btn btn-dark" type="submit" value="${locale.getValue("createNotificationButton")}" title="${locale.getValue("createNotificationButtonAltCreate")}"/>
            </form>
        </c:when>
        <c:otherwise>
            <br><br><br><br><br><br>
        </c:otherwise>
    </c:choose>

    <br>

</div>

</body>

<div class="footer">
    <jsp:include page="${context}/jsp/footer/footer.jsp"/>
</div>


</html>