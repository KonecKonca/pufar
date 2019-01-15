<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>

    <c:set var="context" value="${pageContext.request.contextPath}" />
    <link href="${context}/css/notification/notificationadditionalStyle.css" rel="stylesheet" />
</head>
<body>

<div class="controlPanel">

    <div class="col">
        <div class="card">
            <div class="card-body">
                <h4>${locale.getValue("additionalNotificationPrice")}: <strong>${lookingNotification.price}</strong></h4>

                <img src="/pufar/${lookingNotification.notificationId}" >

                <h6><strong>${locale.getValue("additionalNotificationDescription")}: </strong>${lookingNotification.message}</h6>
                <h6><strong>${locale.getValue("additionalNotificationDate")}: </strong>${lookingNotification.getStringDate()}</h6>

                <h6><strong>${locale.getValue("additionalNotificationUserName")}: </strong>${ownerUser.login}</h6>
                <h6><strong>${locale.getValue("additionalNotificationUserNumber")}: </strong>${ownerUser.number}</h6>
                <h6><strong>${locale.getValue("additionalNotificationRate")}: </strong>${lookingNotification.rate}</h6>

                <c:if test="${currentUser.status != null}">
                    <form action="/pufar" method="post">
                        <input type="hidden" name="command" value="SENT_MESSAGE_TO_NOTIFICATION_AUTHOR">

                        <input type="text" class="form-control" placeholder="${locale.getValue("additionalNotificationSentMessage")} ${ownerUser.login}" name="messageValue" required
                               pattern=".{1,1000}" title="${locale.getValue("commonNotificationPattern")}"/>

                        <input type="hidden" value="${lookingNotification.userId}" name="chosenUser"/>
                        <input type="submit" value="${locale.getValue("additionalNotificationSentMessage")} ${ownerUser.login}" class="btn btn-info">
                    </form>
                </c:if>

                <c:if test="${currentUser.status != null}">
                    <form action="/pufar" method="post">
                        <input type="hidden" name="command" value="SENT_COMMENT">
                        <input type="hidden" name="notificationId" value="${lookingNotification.notificationId}">

                        <input type="text" class="form-control" placeholder="${locale.getValue("additionalNotificationSentCommentButton")}" name="commentValue" required
                               pattern=".{1,1000}" title="${locale.getValue("commonNotificationPattern")}"/>
                        <input type="submit" value="${locale.getValue("additionalNotificationSentCommentButton")}" class="btn btn-info">
                    </form>
                </c:if>


                <c:if test="${currentUser.status != null}">
                    <form action="/pufar" method="post" >
                        <input type="hidden" name="command" value="PUT_MARK">
                        <input type="hidden" name="notificationId" value="${lookingNotification.notificationId}">

                        <div class="form-check-inline">
                            <input class="form-check-input" type="radio" value="1" name="radios" id="option1">
                            <label class="form-check-label" for="option1">
                                1
                            </label>
                        </div>
                        <div class="form-check-inline">
                            <input class="form-check-input" type="radio" value="2" name="radios" id="option2">
                            <label class="form-check-label" for="option2">
                                2
                            </label>
                        </div>
                        <div class="form-check-inline">
                            <input class="form-check-input" type="radio" value="3" name="radios" id="option3">
                            <label class="form-check-label" for="option2">
                                3
                            </label>
                        </div>
                        <div class="form-check-inline">
                            <input class="form-check-input" type="radio" value="4" name="radios" id="option4">
                            <label class="form-check-label" for="option2">
                                4
                            </label>
                        </div>
                        <div class="form-check-inline">
                            <input class="form-check-input" type="radio" value="5" name="radios" id="option5">
                            <label class="form-check-label" for="option2">
                                5
                            </label>
                        </div>

                        <input class="btn btn-info" type="submit" value="${locale.getValue("additionalNotificationGiveMarkButton")}" >
                    </form>
                </c:if>

                <c:if test="${lookingNotification.comments.size() > 0}">
                    <h6>${locale.getValue("additionalNotificationComment")}: </h6>
                </c:if>
                <c:forEach var="comment" items="${lookingNotification.comments}" >
                    <h5><i>${comment.getStringDate()} -${comment.senderLogin}: ${comment.comment}</i><br></h5>
                </c:forEach>

                <form action="${context}/jsp/template/template.jsp" method="post">
                    <input type="submit" value="${locale.getValue("additionalNotificationBackButton")}" class="btn btn-danger">
                </form>

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