<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<!doctype html>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="../boot/css/bootstrap.min.css">
    <link rel="stylesheet" href="../jqgrid/extend/css/trirand/ui.jqgrid-bootstrap.css">
    <script src="../boot/js/jquery-3.3.1.min.js"></script>
    <script src="../boot/js/bootstrap.3.3.7.min.js"></script>
    <script src="../jqgrid/js/i18n/grid.locale-cn.js"></script>
    <script src="../jqgrid/extend/js/trirand/jquery.jqGrid.min.js"></script>
    <script src="../boot/js/ajaxfileupload.js"></script>
    <title>持明法洲登录页面</title>
</head>
<body>
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <%--标题/头部分--%>
        <div class="navbar-header">
            <button class="navbar-toggle collapsed" data-toggle="collapse" data-target="#div1">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a href="" class="navbar-brand">
                <img src="${pageContext.request.contextPath}/images/timg.jpg" align="left" width="40"/>持名法州后台登录系统 v1.0
            </a>
        </div>
        <%--内容--%>
        <div class="collapse navbar-collapse" id="div1">
            <p class="navbar-text navbar-right">
              <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                &nbsp;&nbsp;&nbsp;
        </div>
    </div>
</nav>
<%-- 栅格系统 --%>
<div class="container">
<div class="row">
    <div class="col-md-7">
        <img src="${pageContext.request.contextPath}/img/2.png" class="img-responsive" alt="Responsive image">

    </div>
<%--  右边 --%>
    <div class="col-md-5">
        <div class="panel panel-default">
            <div class="panel-heading"><strong><h2>管理登录</h2></strong></div>
            <div class="panel-body">
                <span style="color:red;font-size: 21px;margin-left: 45px;">${message}</span>
                <form action="/cmfz/p1/login" method="post">
                <div class="input-group">
                    <span class="input-group-addon" id="basic-addon1"><strong>账号</strong></span>
                    <input type="text" name="username" class="form-control" placeholder="Username" aria-describedby="basic-addon1">
                </div><br><br>
                <div class="input-group">
                    <span class="input-group-addon" id="basic-addon2"><strong>密码</strong></span>
                    <input type="password" class="form-control" name="password" placeholder="Password" aria-describedby="basic-addon1">
                </div><br><br>
                <button type="submit" class="btn btn-primary text-center" >管理员登录</button>
                </form>
            </div>
        </div>

    </div>
</div>
</div>
</body>
</html>