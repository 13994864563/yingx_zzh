<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>应学视频App后台管理系统</title>
    <link rel="icon" href="${path}/bootstrap/img/arrow-up.png" type="image/x-icon">
    <link rel="stylesheet" href="${path}/bootstrap/css/bootstrap.css">

    <%--引入jqgrid中主题css--%>
    <link rel="stylesheet" href="${path}/bootstrap/jqgrid/css/css/hot-sneaks/jquery-ui-1.8.16.custom.css">
    <link rel="stylesheet" href="${path}/bootstrap/jqgrid/boot/css/trirand/ui.jqgrid-bootstrap.css">
    <%--引入js文件--%>
    <script src="${path}/bootstrap/js/jquery.min.js"></script>
    <script src="${path}/bootstrap/js/bootstrap.js"></script>
    <script src="${path}/bootstrap/jqgrid/js/i18n/grid.locale-cn.js"></script>
    <script src="${path}/bootstrap/jqgrid/boot/js/trirand/jquery.jqGrid.min.js"></script>
    <script src="${path}/bootstrap/js/ajaxfileupload.js"></script>


</head>
<body>

    <!--顶部导航-->
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#">应学视频App后台管理系统 <small>v1.0</small></a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="#">欢迎：${sessionScope.admin1.username}</a></li>
                    <li><a href="${path}/admin/logout">退出系统 <span class="glyphicon glyphicon-log-out"></span></a></li>
                </ul>
            </div>
        </div>
    </nav>
    <!--栅格系统-->
    <div class="container-fluid">
        <div class="row">
        <!--左边手风琴部分-->
            <div class="col-sm-2">
                <!--系统菜单-->
                <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
                    <div class="panel panel-default" >
                        <div style="background-color: #ffc7bd" class="panel-heading" role="tab" id="headingOne">
                            <h4 class="panel-title">
                                <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                                     用户管理
                                </a>
                            </h4>
                        </div>
                        <div id="collapseOne" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
                            <div class="panel-body">
                                <ul class="list-group text-center">
                                    <li class="btn btn-success" style="background-color: #ffb938">
                                        <a href="javascript:$('#mainId').load('${path}/user/showUser.jsp')">用户展示</a>
                                    </li><br><br/>
                                    <li class="btn btn-success" style="background-color: #ffb938">
                                        <a href="javascript:$('#mainId').load('${path}/test/testEchartsJson.jsp')">用户展示</a>
                                    </li><br><br/>
                                    <li class="btn btn-success" style="background-color: #ffb938">
                                        <a href="javascript:$('#mainId').load('${path}/test/testChina.jsp')">用户展示</a>
                                    </li><br/>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <hr/>
                    <div class="panel panel-default">
                        <div style="background-color: #b5ff9e" class="panel-heading" role="tab" id="headingTwo">
                            <h4 class="panel-title">
                                <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                                    <span class="glyphicon glyphicon-bell"></span> 分类管理
                                </a>
                            </h4>
                        </div>
                        <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
                            <div class="panel-body">
                                <ul class="list-group text-center">
                                    <li class="btn btn-danger" style="background-color: #ffd3df">
                                        <a href="javascript:$('#mainId').load('${path}/category/showCategory.jsp')">显示分类</a>
                                    </li><br><br/>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <hr/>
                    <div class="panel panel-default">
                        <div style="background-color: #fbffb8" class="panel-heading" role="tab" id="headingThree">
                            <h4 class="panel-title">
                                <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                                    <span class="glyphicon glyphicon-scale"></span> 视频管理
                                </a>
                            </h4>
                        </div>
                        <div id="collapseThree" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
                            <div class="panel-body">
                                <ul class="list-group text-center">
                                    <li class="btn btn-danger" style="background-color: #ffd3df">
                                        <a href="javascript:$('#mainId').load('${path}/video/showVideo.jsp')" >显示视频</a>
                                    </li><br><br/>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <hr/>
                    <div class="panel panel-default">
                        <div style="background-color: #a2e5ff" class="panel-heading" role="tab" id="headingfirst">
                            <h4 class="panel-title">
                                <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapsefirst" aria-expanded="false" aria-controls="collapsefirst">
                                    <span class="glyphicon glyphicon-scale"></span> 日志管理
                                </a>
                            </h4>
                        </div>
                        <div id="collapsefirst" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingfirst">
                            <div class="panel-body">
                                <ul class="list-group text-center">
                                    <li class="btn btn-danger" style="background-color: #ffd3df">
                                        <a href="javascript:$('#mainId').load('${path}/log/showLog.jsp')" >显示视频</a>
                                    </li><br><br/>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <hr/>
                    <div class="panel panel-default">
                        <div style="background-color: #a2e5ff" class="panel-heading" role="tab" id="headingFour">
                            <h4 class="panel-title">
                                <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseFour" aria-expanded="false" aria-controls="collapseFour">
                                    <span class="glyphicon glyphicon-scale"></span> 反馈管理
                                </a>
                            </h4>
                        </div>
                        <div id="collapseFour" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingFour">
                            <div class="panel-body">
                                <ul class="list-group text-center">
                                    <li class="btn btn-danger" style="background-color: #ffd3df">
                                        <a href="javascript:$('#mainId').load('${path}/feedback/showFeedback.jsp')" >显示视频</a>
                                    </li><br><br/>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        <!--巨幕开始-->
            <div class="col-sm-10" id="mainId">
                <div class="jumbotron">
                    <h1>欢迎来到应学视频App后台管理系统!</h1>
                </div>
        <!--右边轮播图部分-->
                <!--创建轮播图-->
                <div id="carousel-example-generic" style="width: 1350px; height:450px; margin-top: 50px;" class="carousel slide" data-ride="carousel" align="center">
                    <!-- Indicators -->
                    <ol class="carousel-indicators">
                        <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
                        <li data-target="#carousel-example-generic" data-slide-to="1"></li>
                        <li data-target="#carousel-example-generic" data-slide-to="2"></li>
                        <li data-target="#carousel-example-generic" data-slide-to="3"></li>
                    </ol>

                    <!-- Wrapper for slides -->
                    <div class="carousel-inner" role="listbox">
                        <div class="item active">
                            <img src="../bootstrap/img/pic1.jpg"  style="height: 450px; width:1350px" alt="...">
                        </div>
                        <div class="item">
                            <img src="../bootstrap/img/pic2.jpg"  style="height: 450px; width:1350px" alt="...">
                        </div>
                        <div class="item">
                            <img src="../bootstrap/img/pic3.jpg"  style="height: 450px; width:1350px" alt="...">
                        </div>
                        <div class="item">
                            <img src="../bootstrap/img/pic4.jpg"  style="height:450px; width:1350px" alt="...">
                        </div>

                    </div>

                    <!-- Controls -->
                    <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
                        <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                    </a>

                    <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
                        <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                    </a>
                </div>
            </div>
        <!--页脚-->
            <nav class="navbar navbar-default navbar-fixed-bottom" style="padding-top: 15px">
                <div class="container" style="text-align: center">
                    <p>@百知教育  z2604033294@163.com</p >
                </div>
            </nav>
    <!--栅格系统-->
        </div>
    </div>
</body>
</html>
