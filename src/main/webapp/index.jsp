<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>${locale.getValue("commonTitle")}</title>

    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Open+Sans:300,400">  <!-- Google web font "Open Sans" -->
    <link rel="stylesheet" href="css/index/common/font-awesome.min.css">
    <link rel="stylesheet" href="css/index/common/bootstrap.min.css">

    <link rel="stylesheet" href="css/index/common/demo.css" />
    <link rel="stylesheet" href="css/index/common/templatemo-style.css">

    <script type="text/javascript" src="js/index/modernizr.custom.86080.js"></script>

</head>

<body>

<div id="particles-js"></div>

<ul class="cb-slideshow">
    <li></li>
    <li></li>
    <li></li>
    <li></li>
    <li></li>
    <li></li>
</ul>

<div class="container-fluid">
    <div class="row cb-slideshow-text-container ">
        <div class= "tm-content col-xl-6 col-sm-8 col-xs-8 ml-auto section">
            <header class="mb-5"><h1>${locale.getValue("indexGreeting")}</h1></header>
            <P class="mb-5"> ${accessRight} ${locale.getValue("indexMessage")} ${currentUser.login}!</P>

            <form action="/jsp/login/login.jsp" method="get" class="subscribe-form">
                <div class="row form-section">

                    <%--nice input field--%>
                    <%--<div class="col-md-7 col-sm-7 col-xs-7">--%>
                        <%--<input name="email" type="text" class="form-control" id="contact_email" placeholder="Your Email..." required/>--%>
                    <%--</div>--%>

                    <div class="col-md-5 col-sm-5 col-xs-5">
                        <button type="submit" value="Login" class="tm-btn-subscribe">${locale.getValue("indexLoginButton")}</button>
                    </div>

                </div>

            </form>

            <P class="mb-5"> ${locale.getValue("indexRegisterLabel")}</P>
            <form action="/pufar" method="post">

                <input type="hidden" value="REGISTRATION" name="command">

                <table align="center">
                    <tr>
                        <th align="right"><div class="indexLabel">${locale.getValue("indexRegisterLogin")}</div></th>
                        <td><input type="text" name="login" placeholder="${locale.getValue("indexRegisterLogin")}" class="form-control"/></td>
                    </tr>
                    <tr>
                        <th align="right"><div class="indexLabel">${locale.getValue("indexRegisterPassword")}</div></th>
                        <td><input type="password" name="password" placeholder="${locale.getValue("indexRegisterPassword")}" class="form-control"/></td>
                    </tr>
                    <tr>
                        <td colspan="2" align="right"><input type="submit" value="${locale.getValue("indexRegisterButton")}" class="btn btn-outline-info"/></td>
                    </tr>
                </table>

            </form>


            <div class="tm-social-icons-container text-xs-center">
                <a href="" class="tm-social-link"><i class="fa fa-facebook"></i></a>
                <a href="" class="tm-social-link"><i class="fa fa-google-plus"></i></a>
                <a href="" class="tm-social-link"><i class="fa fa-twitter"></i></a>
                <a href="" class="tm-social-link"><i class="fa fa-linkedin"></i></a>
            </div>

        </div>
    </div>
    <div class="footer-link">
        <p>${locale.getValue("indexCompanyName")} <a rel="nofollow" href="https://vk.com/akozitsky" target="_parent">${locale.getValue("indexCreatorName")}</a></p>
    </div>
</div>
</body>

<script type="text/javascript" src="js/index/particles.js"></script>
<script type="text/javascript" src="js/index/app.js"></script>
</html>