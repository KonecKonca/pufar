<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
                <a class="navbar-brand" href="#"> ${currentUser.login} </a>
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
                            <a class="nav-link" href="${context}/jsp/login/login.jsp">${locale.getValue("templateLoginLink")}</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${context}/index.jsp">${locale.getValue("templateIndexLink")}</a>
                        </li>
                    </ul>

                    <ul class="navbar-nav ml-md-auto d-md-flex">
                        <li class="nav-item">

                            <form action="/pufar" method="post">
                                <input type="hidden" name="command" value="GET_CONTACT">

                                <input type="submit" name="button1" value="${locale.getValue("templateDialogButton")}" class="btn btn-outline-info">

                                <%--<a class="nav-link" href="/speaker">Dialogues--%>
                                    <%--<span class="sr-only">(current)</span>--%>
                                <%--</a>--%>

                            </form>

                        </li>
                        <li class="nav-item">

                            <form action="/pufar" method="post">
                                <input type="hidden" name="command" value="notDefine">

                                <input type="submit" value="${locale.getValue("templateInfoButton")}" class="btn btn-outline-info">

                            </form>

                        </li>
                    </ul>
                </div>
            </nav>
            <div class="container-fluid">
                <div class="row">
                    <div class="col">
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title">Try Other</h5>
                                <h6 class="card-subtitle mb-2 text-muted">Bootstrap 4.0.0 Snippet by pradeep330</h6>
                                <p class="card-text">You can also try different version of Bootstrap V4 side menu. Click below link to view all Bootstrap Menu versions.</p>
                                <a href="https://bootsnipp.com/pradeep330" class="card-link">link</a>
                                <a href="http://websitedesigntamilnadu.com" class="card-link">Another link</a>
                            </div>
                        </div>
                    </div>
                    <div class="col">
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title">Try Other</h5>
                                <h6 class="card-subtitle mb-2 text-muted">Bootstrap 4.0.0 Snippet by pradeep330</h6>
                                <p class="card-text">You can also try different version of Bootstrap V4 side menu. Click below link to view all Bootstrap Menu versions.</p>
                                <a href="https://bootsnipp.com/pradeep330" class="card-link">link</a>
                                <a href="http://websitedesigntamilnadu.com" class="card-link">Another link</a>
                            </div>
                        </div>
                    </div>
                    <div class="col">
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title">Try Other</h5>
                                <h6 class="card-subtitle mb-2 text-muted">Bootstrap 4.0.0 Snippet by pradeep330</h6>
                                <p class="card-text">You can also try different version of Bootstrap V4 side menu. Click below link to view all Bootstrap Menu versions.</p>
                                <a href="https://bootsnipp.com/pradeep330" class="card-link">link</a>
                                <a href="http://websitedesigntamilnadu.com" class="card-link">Another link</a>
                            </div>
                        </div>
                    </div>
                    <div class="col">
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title">Try Other</h5>
                                <h6 class="card-subtitle mb-2 text-muted">Bootstrap 4.0.0 Snippet by pradeep330</h6>
                                <p class="card-text">You can also try different version of Bootstrap V4 side menu. Click below link to view all Bootstrap Menu versions.</p>
                                <a href="https://bootsnipp.com/pradeep330" class="card-link">link</a>
                                <a href="http://websitedesigntamilnadu.com" class="card-link">Another link</a>
                            </div>
                        </div>
                    </div>
                    <div class="col">
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title">Try Other</h5>
                                <h6 class="card-subtitle mb-2 text-muted">Bootstrap 4.0.0 Snippet by pradeep330</h6>
                                <p class="card-text">You can also try different version of Bootstrap V4 side menu. Click below link to view all Bootstrap Menu versions.</p>
                                <a href="https://bootsnipp.com/pradeep330" class="card-link">link</a>
                                <a href="http://websitedesigntamilnadu.com" class="card-link">Another link</a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col">
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title">Try Other</h5>
                                <h6 class="card-subtitle mb-2 text-muted">Bootstrap 4.0.0 Snippet by pradeep330</h6>
                                <p class="card-text">You can also try different version of Bootstrap V4 side menu. Click below link to view all Bootstrap Menu versions.</p>
                                <a href="https://bootsnipp.com/pradeep330" class="card-link">link</a>
                                <a href="http://websitedesigntamilnadu.com" class="card-link">Another link</a>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col">
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title">Card title</h5>
                                <table class="table">
                                    <thead>
                                    <tr>
                                        <th scope="col">#</th>
                                        <th scope="col">First</th>
                                        <th scope="col">Last</th>
                                        <th scope="col">Handle</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <th scope="row">1</th>
                                        <td>Mark</td>
                                        <td>Otto</td>
                                        <td>@mdo</td>
                                    </tr>
                                    <tr>
                                        <th scope="row">2</th>
                                        <td>Jacob</td>
                                        <td>Thornton</td>
                                        <td>@fat</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>

    <div>
        <jsp:include page="${context}/jsp/footer/footer.jsp"/>
    </div>

</body>