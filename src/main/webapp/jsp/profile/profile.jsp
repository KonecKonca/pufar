<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>

    <c:set var="context" value="${pageContext.request.contextPath}" />
    <link href="${context}/css/profile/plofileStyle.css" rel="stylesheet"/>
</head>
<body>

<div class="controlPanel">

    <div class="col">
        <div class="card">
            <div class="card-body">
                <form action="${context}/jsp/template/template.jsp" method="post">
                    <input type="submit" value="${locale.getValue("additionalNotificationBackButton")}" class="btn btn-danger">
                </form>

                <h4>${locale.getValue("profileLoginLabel")}: <strong>${currentUser.login}</strong></h4>

                <h3><strong> ${locale.getValue("profileMobilePhone")}: </strong>${currentUser.number}</h3>
                <form action="/pufar" method="post">
                    <input type="hidden" name="command" value="CHANGE_MOBILE_PHONE">
                    <input type="hidden" name="isNumberExist" value="${currentUser.number}">

                    <input type="submit" value="${locale.getValue("profileMobilePhoneChange")}" class="btn btn-success">

                    ${locale.getValue("profileMobilePhonePrefix")}
                    <input type="text" name="operator" placeholder="${locale.getValue("profileMobilPhoneLabel1")}" size="2" class="form-control-sm" required
                           pattern="[\d]{2}" title="${locale.getValue("commonOperatorPattern")}"/>
                    <input type="text" name="mobileNumber" placeholder="${locale.getValue("profileMobilPhoneLabel2")}" size="15" class="form-control-sm" required
                           pattern="[\d]{7}" title="${locale.getValue("commonNumberPattern")}"/><br>
                </form>

                <h3><strong> ${locale.getValue("profileChangePasswordLabel")}: </h3>
                <form action="/pufar" method="post">
                    <input type="hidden" name="command" value="CHANGE_PASSWORD">

                    ${locale.getValue("profileOldPassword")}
                    <input type="password" name="oldPassword" placeholder="${locale.getValue("profileOldPassword")}" size="25" class="form-control-sm" required
                           pattern="[\w.@а-яА-яёЁ]{5,60}" title="${locale.getValue("commonChangePasswordPattern")}"/><br>

                    ${locale.getValue("profileNewPassword")}
                    <input type="password" name="newPassword" placeholder="${locale.getValue("commonChangePasswordPattern")}" size="25" class="form-control-sm" required
                           pattern="[\w.@а-яА-яёЁ]{5,60}"/><br>

                    ${locale.getValue("profileNewPasswordConfirm")}
                    <input type="password" name="newPasswordConfirm" placeholder="${locale.getValue("commonChangePasswordPattern")}" size="25" class="form-control-sm" required
                           pattern="[\w.@а-яА-яёЁ]{5,60}"/><br>

                    <c:choose>
                        <c:when test="${changePasswordMessage == true}">
                            ${locale.getValue("profilePasswordChanged")}<br>
                        </c:when>

                        <c:otherwise>
                            ${locale.getValue("profilePasswordNotChanged")}<br>
                        </c:otherwise>
                    </c:choose>
                    <input type="submit" value="${locale.getValue("profileChangePasswordLabel")}" class="btn btn-success">
                </form>

                <c:if test="${currentUserNotification != null}">
                    <h3>${locale.getValue("profileNotifications")}: </h3>
                </c:if>
                <c:forEach var="notification" items="${currentUserNotification}" varStatus="status">

                    <h3>
                        <i>${status.count}) ${notification.unit} | ${notification.price} | ${notification.message}</i>
                        <form action="/pufar" method="post">
                            <input type="hidden" name="command" value="DROP_MYSELF_NOTIFICATION">

                            <input type="hidden" name="notificationId" value="${notification.notificationId}">
                            <input type="submit" value="${locale.getValue("profileDeleteNotification")}" class="btn btn-outline-success">
                        </form>
                    </h3>

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