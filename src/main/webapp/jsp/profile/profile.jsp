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
                    <input type="hidden" name="command" value="XXXXXXxxXXXX">

                    <input type="submit" value="${locale.getValue("profileMobilePhoneChange")}" class="btn btn-success">

                    ${locale.getValue("profileMobilePhonePrefix")}
                    <input type="text" name="operator" placeholder="${locale.getValue("profileMobilPhoneLabel1")}" size="2" class="form-control-sm" maxlength="2" minlength="2"/>
                    <input type="text" name="mobileNumber" placeholder="${locale.getValue("profileMobilPhoneLabel2")}" size="15" class="form-control-sm" maxlength="7" minlength="7"/><br>
                </form>

                <h3><strong> ${locale.getValue("profileChangePasswordLabel")}: </h3>
                <form action="/pufar" method="post">
                    <input type="hidden" name="command" value="XXXXXXxxXXXX">

                    ${locale.getValue("profileOldPassword")}
                    <input type="text" name="operator" placeholder="${locale.getValue("profileOldPassword")}" size="25" class="form-control-sm"/><br>

                    ${locale.getValue("profileNewPassword")}
                    <input type="text" name="operator" placeholder="${locale.getValue("profileNewPassword")}" size="25" class="form-control-sm"/><br>

                    ${locale.getValue("profileNewPasswordConfirm")}
                    <input type="text" name="operator" placeholder="${locale.getValue("profileNewPasswordConfirm")}" size="25" class="form-control-sm"/><br>

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