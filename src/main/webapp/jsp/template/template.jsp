<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="pyf" uri="pufarTag" %>

<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<!------ Include the above in your HEAD tag ---------->

<head>

    <title>${locale.getValue("commonTitle")}</title>

    <c:set var="context" value="${pageContext.request.contextPath}" />

    <link href="${context}/css/template/templateStyle.css" rel="stylesheet" />
    <script src="${context}/js/template/jsTemplate.js" type="text/javascript"></script>


    <%--importnat for footer--%>
    <script src="${context}/js/footer/jquery-3.2.1.min.js"></script>

</head>


<%--SOURCE WITH EXAMPLE--%>
<%--https://bootsnipp.com/siframe/index.php/snippets/featured/bootstrap-v4-side-toggle-and-fixed-top-menu-responsive--%>

<body>
    <div class="myEdit">
        <div id="wrapper" class="animate">
            <nav class="navbar header-top fixed-top navbar-expand-lg  navbar-dark bg-dark">
                <span class="navbar-toggler-icon leftmenutrigger"></span>

                <c:if test="${currentUser.status != null}">
                    <a class="navbar-brand" href="#"> ${currentUser.login} </a>
                </c:if>

                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarText" aria-controls="navbarText"
                        aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <div class="collapse navbar-collapse" id="navbarText">

                    <ul class="navbar-nav animate side-nav">
                        <%--<li class="nav-item">--%>
                            <%--<a class="nav-link" href="#">${locale.getValue("templateDialogButton")}--%>
                                <%--<span class="sr-only">(current)</span>--%>
                            <%--</a>--%>
                        <%--</li>--%>

                        <li class="nav-item">
                            <a class="sideLink"  href="${context}/jsp/login/login.jsp">${locale.getValue("templateLoginLink")}</a>
                        </li>
                        <li class="nav-item">
                            <a class="sideLink" href="${context}/index.jsp">${locale.getValue("templateIndexLink")}</a>
                        </li>

                        <li class="nav-item">
                            <div class="sideNote">${locale.getValue("commonUnits")}</div>
                            <form action="/pufar" method="post">
                                <input type="hidden" name="command" value="SHOW_NOTIFICATION_BY_UNIT_TYPE">
                                <input type="hidden" name="unitType" value="OTHER">

                                <input type="submit" name="button1" value="${locale.getValue("createNotificationUnit1")}" class="btn btn-outline-warning">
                            </form>
                            <form action="/pufar" method="post">
                                <input type="hidden" name="command" value="SHOW_NOTIFICATION_BY_UNIT_TYPE">
                                <input type="hidden" name="unitType" value="AUTO">

                                <input type="submit" name="button1" value="${locale.getValue("createNotificationUnit2")}" class="btn btn-outline-warning">
                            </form>
                            <form action="/pufar" method="post">
                                <input type="hidden" name="command" value="SHOW_NOTIFICATION_BY_UNIT_TYPE">
                                <input type="hidden" name="unitType" value="HOME">

                                <input type="submit" name="button1" value="${locale.getValue("createNotificationUnit3")}" class="btn btn-outline-warning">
                            </form>
                            <form action="/pufar" method="post">
                                <input type="hidden" name="command" value="SHOW_NOTIFICATION_BY_UNIT_TYPE">
                                <input type="hidden" name="unitType" value="CHILD">

                                <input type="submit" name="button1" value="${locale.getValue("createNotificationUnit4")}" class="btn btn-outline-warning">
                            </form>
                            <form action="/pufar" method="post">
                                <input type="hidden" name="command" value="SHOW_NOTIFICATION_BY_UNIT_TYPE">
                                <input type="hidden" name="unitType" value="FOOD">

                                <input type="submit" name="button1" value="${locale.getValue("createNotificationUnit5")}" class="btn btn-outline-warning">
                            </form>
                            <form action="/pufar" method="post">
                                <input type="hidden" name="command" value="SHOW_NOTIFICATION_BY_UNIT_TYPE">
                                <input type="hidden" name="unitType" value="WORK">

                                <input type="submit" name="button1" value="${locale.getValue("createNotificationUnit6")}" class="btn btn-outline-warning">
                            </form>
                            <form action="/pufar" method="post">
                                <input type="hidden" name="command" value="SHOW_NOTIFICATION_BY_UNIT_TYPE">
                                <input type="hidden" name="unitType" value="BEAUTY">

                                <input type="submit" name="button1" value="${locale.getValue("createNotificationUnit7")}" class="btn btn-outline-warning">
                            </form>
                            <form action="/pufar" method="post">
                                <input type="hidden" name="command" value="SHOW_NOTIFICATION_BY_UNIT_TYPE">
                                <input type="hidden" name="unitType" value="ELECTRONICS">

                                <input type="submit" name="button1" value="${locale.getValue("createNotificationUnit8")}" class="btn btn-outline-warning">
                            </form>
                        </li>



                            <%--<jsp:forward page="${context}"></jsp:forward>--%>

                    </ul>

                    <ul class="navbar-nav ml-md-auto d-md-flex">
                        <c:if test="${notificationsCursor != null}">
                            <div>
                                <div style="float: left;">
                                    <form action="/pufar" method="post">
                                        <input type="hidden" value="CHANGE_NOTIFICATION" name="command">
                                        <input type="hidden" value="false" name="nextCharacter">

                                        <input type="submit" name="button1" value="<<" class="btn btn-outline-success">
                                    </form>
                                </div>

                                <div style="float: right;">
                                    <form action="/pufar" method="post">
                                        <input type="hidden" value="CHANGE_NOTIFICATION" name="command">
                                        <input type="hidden" value="true" name="nextCharacter">

                                        <input type="submit" name="button1" value=">>" class="btn btn-outline-success">
                                    </form>
                                </div>
                            </div>
                        </c:if>

                        <li class="nav-item">
                            <pyf:adminButton value="admin"/>
                        </li>

                        <c:if test="${currentUser.status!=null}">
                            <li class="nav-item">
                                <form action="/pufar" method="post">
                                    <input type="hidden" name="command" value="GET_CONTACT">

                                    <input type="submit" name="button1" value="${locale.getValue("templateDialogButton")}" class="btn btn-outline-info">
                                </form>
                            </li>
                        </c:if>

                        <c:if test="${currentUser.status!=null}">
                            <li class="nav-item">

                                <form action="/pufar" method="post">
                                    <input type="hidden" name="command" value="CHOOSE_CREATE_NOTIFICATION">

                                    <input type="submit" value="${locale.getValue("templateCreateNotification")}" class="btn btn-outline-info">

                                </form>

                            </li>
                        </c:if>

                        <li class="nav-item">

                            <form action="/invalidate" method="post">
                                <input type="hidden" name="command" value="LOGOUT">

                                <input type="submit" value="${locale.getValue("templateLogout")}" class="btn btn-outline-info">

                            </form>

                        </li>

                    </ul>
                </div>
            </nav>

            <div class="container-fluid">
                <div class="row">

                 <c:if test="${currentNotifications.size() == 0}">
                     <br><br><br><br><br><br><br>
                     <br><br><br><br><br><br><br>
                     <br><br><br><br><br><br><br>
                 </c:if>

                <c:forEach var="notification" items="${currentNotifications}">

                    <div class="col">
                        <div class="card">
                            <div class="card-body">

                                <h5 class="card-title">${notification.unit}</h5>
                                <h6 class="card-subtitle mb-2 text-muted">${notification.price}</h6>

                                <img src="/imageShow/${notification.notificationId}" >

                                <p class="card-text">${notification.message}</p>
                                <p class="card-text">${notification.date}</p>
                                <p class="card-text">${notification.rate}</p>

                                <form action="/pufar" method="post">
                                    <input type="hidden" name="command" value="SHOW_ADDITIONAL_NOTIFICATION">
                                    <input type="hidden" name="notification" value="${notification.notificationId}">

                                    <input type="submit" value="${locale.getValue("templateNotificationButton")}" class="btn btn-outline-info">
                                </form>

                            </div>
                        </div>
                    </div>

                </c:forEach>


            </div>
        </div>
    </div>

    <div>
        <jsp:include page="${context}/jsp/footer/footer.jsp"/>
    </div>

</body>