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

    <c:choose>
        <c:when test="${lastMessages != null && lastMessages.size() != 0}">
            <div class="container">
                <div class="row " style="padding-top:40px;">
                    <br /><br />
                    <div class="col-md-8">
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                    ${locale.getValue("chatTable")} <strong>${currentOpponent.login}</strong>

                            </div>
                            <div class="panel-body">
                                <ul class="media-list">

                                    <c:forEach var="message" items="${lastMessages}">

                                        <li class="media">
                                            <div class="media-body">
                                                <div class="media">
                                                    <div class="pull-left">
                                                        <img class="media-object img-circle " src="${context}/image/chat/user.png" />
                                                    </div>
                                                    <div class="media-body" >
                                                            ${message.message}
                                                        <br />
                                                        <small class="text-muted">${message.senderLogin} | ${message.getStringDate()}</small>
                                                        <hr />
                                                    </div>
                                                </div>
                                            </div>
                                        </li>
                                    </c:forEach>

                                    <div class="panel-footer">


                                        <form action="/pufar" accept-charset="utf-8" method="post" >
                                            <input type="hidden" value="SEND_MESSAGE" name="command">

                                            <input type="text" class="form-control" placeholder="Enter Message"  name="sentValue" required
                                                   pattern=".{1,555}" title="${locale.getValue("commonMessagePattern")}"/>
                                            <input class="btn btn-info" type="submit" value="${locale.getValue("chatSentButton")}">
                                        </form>

                                        <div>
                                            <div style="float: left;">
                                                <form action="/pufar" method="post">
                                                    <input type="hidden" value="MESSAGE_PREVIOUS" name="command">
                                                    <input type="submit" name="button1" value="<<" class="btn btn-outline-info">
                                                </form>
                                            </div>
                                            <div style="float: right;">
                                                <form action="/pufar" method="post">
                                                    <input type="hidden" value="MESSAGE_NEXT" name="command">
                                                    <input type="submit" name="button1" value=">>" class="btn btn-outline-info">
                                                </form>
                                            </div>
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

                                                    <div class="pull-left">
                                                        <img class="media-object img-circle" style="max-height:40px;" src="${context}/image/chat/user.png" />
                                                    </div>

                                                    <div class="media-body" >
                                                        <form action="pufar" method="post">
                                                            <input type="hidden" value="CHANGE_OPPONENT" name="command">
                                                            <input type="hidden" value="${user.userId}" name="chosenUser">

                                                            <input type="submit" value="${user.login} [${user.status}]" class="btn btn-outline-info">
                                                        </form>

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
        </c:when>

        <c:otherwise>
            <img src="${context}/image/chat/noMessages.png">
        </c:otherwise>
    </c:choose>

</div>

<div>
    <jsp:include page="../footer/footer.jsp"/>
</div>

</body>
</html>