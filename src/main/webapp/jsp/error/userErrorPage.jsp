<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html class="ie ie6" lang="ru">
<html class="ie ie7" lang="ru">
<html class="ie ie8" lang="ru">
<html lang="ru">

<head>
    <meta charset="utf-8">
    <title>${locale.getValue("commonTitle")}</title>
    <meta name="description" content="">
    <meta name="author" content="">
    <meta name="viewport" content="width=device-width">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

    <c:set var="context" value="${pageContext.request.contextPath}" />
    <link rel="stylesheet" href="${context}/css/error/style.css">

    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <script src="http://css3-mediaqueries-js.googlecode.com/svn/trunk/css3-mediaqueries.js"></script>

</head>
<body>

<div class="myEdit">
    <div class="comingcontainer">
        <div class="checkbacksoon">
            <p>
                <span class="go3d">4</span>
                <span class="go3d">0</span>
                <span class="go3d">4</span>
                <span class="go3d">!</span>

            </p>

            <p class="error">
                ${locale.getValue("userErrorMessage")}</p>
            <form action="" method="post" class="search">
                <input type="search" name="" placeholder="поиск" class="input" />
                <input type="submit" name="" value="" class="submit" />
            </form>
            <nav>
                <ul>
                    <li><a href="#">${locale.getValue("userErrorMainButton")}</a></li>
                    <li><a href="#">${locale.getValue("userErrorInfoButton")}</a></li>
                    <li><a href="#">${locale.getValue("userErrorContactButton")}</a></li>
                </ul>
            </nav>

        </div>
    </div>
</div>

</body>
</html>