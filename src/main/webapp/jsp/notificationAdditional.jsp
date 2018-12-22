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

    <div class="col">
        <div class="card">
            <div class="card-body">
                <h6 class="card-subtitle mb-2 text-muted">${locale.getValue("additionalNotificationPrice")}: ${lookingNotification.price}</h6>

                <img src="/imageShow/${lookingNotification.notificationId}" >

                <p class="card-text">${locale.getValue("additionalNotificationDescription")}:<br> ${lookingNotification.message}</p>
                <p class="card-text">${locale.getValue("additionalNotificationDate")}: ${lookingNotification.date}</p>
                <p class="card-text">${locale.getValue("additionalNotificationRate")}: ${lookingNotification.rate}</p>

                <p class="card-text">${locale.getValue("additionalNotificationUserName")}: ${ownerUser.login}</p>
                <p class="card-text">${locale.getValue("additionalNotificationUserNumber")}: ${ownerUser.number}</p>


                <form action="/pufar" method="post">
                    <input type="hidden" name="command" value="xxXXXXXxx">

                    <input type="text" class="form-control" placeholder="${locale.getValue("additionalNotificationSentMessage")}" name="XXXXX"/>
                    <input type="submit" value="${locale.getValue("additionalNotificationSentMessage")} ${ownerUser}" class="btn btn-outline-info">
                </form>

                <form action="/pufar" method="post">
                    <input type="hidden" name="command" value="xxXXXXXxx">

                    <input type="text" class="form-control" placeholder="${locale.getValue("additionalNotificationSentCommentButton")}" name="XXXXX"/>
                    <input type="submit" value="${locale.getValue("additionalNotificationSentCommentButton")} ${ownerUser}" class="btn btn-outline-info">
                </form>

                <form action="/pufar" method="post" >
                    <input type="hidden" name="command" value="CREATE_NOTIFICATION">

                    <div class="form-check">
                        <input class="form-check-input" type="radio" value="1" name="radios" id="option1" checked>
                        <label class="form-check-label" for="option1">
                            1
                        </label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" value="2" name="radios" id="option2">
                        <label class="form-check-label" for="option2">
                            2
                        </label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" value="3" name="radios" id="option3">
                        <label class="form-check-label" for="option2">
                            3
                        </label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" value="4" name="radios" id="option4">
                        <label class="form-check-label" for="option2">
                            4
                        </label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" value="5" name="radios" id="option5">
                        <label class="form-check-label" for="option2">
                            5
                        </label>
                    </div>

                    <input class="btn btn-outline-info" type="submit" value="${locale.getValue("additionalNotificationGiveMarkButton")}" >
                </form>


                <p class="card-text">${locale.getValue("additionalNotificationComment")}: <br></p>
                <c:forEach var="comment" items="${lookingNotification.comments}" >
                    <h5>-${comment.senderLogin}: ${comment.comment} </h5>
                </c:forEach>

            </div>
        </div>
    </div>

    <br>

</div>

</body>

<div class="footer">
    <jsp:include page="${context}/jsp/footer/footer.jsp"/>
</div>


</html>