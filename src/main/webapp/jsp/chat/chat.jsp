<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
    <meta name="description" content="" />
    <meta name="author" content="" />

    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>${locale.getValue("commonTitle")}</title>

    <c:set var="context" value="${pageContext.request.contextPath}" />

    <link href="${context}/css/chat/bootstrap.css" rel="stylesheet" />
    <script src="${context}/js/chat/jquery-3.2.1.min.js"></script>

</head>

<style>
</style>

<body style="font-family:Verdana">
<div class="myEdit">
    <div class="container">
        <div class="row " style="padding-top:40px;">
            <br /><br />
            <div class="col-md-8">
                <div class="panel panel-info">
                    <div class="panel-heading">
                        ${locale.getValue("chatTable")} <strong>${topUsers.get(0).login}</strong>

                    </div>
                    <div class="panel-body">
                        <ul class="media-list">

                            <c:forEach var="message" items="${lastMessages}">

                                <li class="media">
                                    <div class="media-body">
                                        <div class="media">
                                            <a class="pull-left" href="#">
                                                <img class="media-object img-circle " src="${context}/image/chat/user.png" />
                                            </a>
                                            <div class="media-body" >
                                                    ${message.message}
                                                <br />
                                                <small class="text-muted">${message.senderLogin} | ${message.time}</small>
                                                <hr />
                                            </div>
                                        </div>
                                    </div>
                                </li>
                            </c:forEach>

                            <div class="panel-footer">
                                <div class="input-group">
                                    <input type="text" class="form-control" placeholder="Enter Message" />
                                    <span class="input-group-btn">
                                                <button class="btn btn-info" type="button">${locale.getValue("chatSentButton")}</button>
                                            </span>
                                </div>
                            </div>

                        </ul>
                    </div>
                </div>
            </div>

            <div class="col-md-4">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        ${locale.getValue("chatUserTable")}
                    </div>
                    <div class="panel-body">
                        <ul class="media-list">

                            <c:forEach var="user" items="${topUsers}">

                                <li class="media">
                                    <div class="media-body">
                                        <div class="media">
                                            <a class="pull-left" href="#">
                                                <img class="media-object img-circle" style="max-height:40px;" src="${context}/image/chat/user.png" />
                                            </a>
                                            <div class="media-body" >
                                                <h5>${user.login} [${user.status}] </h5>
                                                <small class="text-muted">Active From 3 hours</small>
                                            </div>
                                        </div>

                                    </div>
                                </li>

                            </c:forEach>

                        </ul>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>

<div>
    <jsp:include page="../footer/footer.jsp"/>
</div>

</body>
</html>