<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<script>

    $(function(){
        $("#userId").jqGrid({
            url:"${path}/user/queryAll",
            datatype: "json",
            rowNum:2,   //当前显示的记录数  后台可用rows接收
            rowList:[10,20,30],   //当前页  后台可用page接收
            pager: '#userPage',
            sortname: 'id',
            styleUI:"Bootstrap",
            autowidth:true,
            height:"auto",
            viewrecords: true,  //是否展示总条数书
            colNames:['id', '头像', '昵称','手机号','简介','状态','创建日期'],
            colModel:[
                {name:'id'},
                {
                    name:'picImg',editable:true,edittype:"file",
                    formatter:function(value,options,row){
                        return "<img src='${path}/bootstrap/img/"+value+"' style='height:50px;'/>";
                    }
                },
                {name:'nickName',editable:true},
                {name:'phone',editable:true},
                {name:'brief',editable:true},
                {
                    name:'status',
                    formatter:function(value,options,row){
                        if(value==1){
                            return "<button class='btn btn-success' onclick='aa(\""+row.id+"\",\""+row.status+"\")'>冻结</button>";
                        }else{
                            return "<button class='btn btn-danger' onclick='aa(\""+row.id+"\",\""+row.status+"\")'>解除冻结</button>";
                        }
                    }
                },
                {name:'createDate'}
            ],
            editurl:"${path}/user/edit",
        });
        $("#userId").jqGrid('navGrid','#userPage',{edit:false,add:true,del:false},
            {

            },
            {
                closeAfterAdd: true,
                reloadAfterSubmit: true,
                afterSubmit: function (data) {  //添加成功之后执行的内容
                    alert(data.responseText);
                    //1.信息入库   返回id
                    //2.文件上传
                    $.ajaxFileUpload({
                        url: "${path}/user/uploadAliyun",
                        type: "post",
                        data: {"id": data.responseText},
                        fileElementId: "picImg", //文件选择框的id属性，即<input type="file" id="picImg" name="picImg">的id
                        success: function () {
                            //上传成功 所作的事情
                            //刷新页面
                            $("#cateTable").trigger("reloadGrid");
                        }
                    });
                    return "hello";
                }
            },
            {

            },
            {

            }


        );
    });

    function aa(id,status) {

        if (status == "1"){
            $.post("${path}/user/edit",{"id":id,"status":0,"oper":"edit"},function (data) {
                //刷新页面
                $("#userId").trigger("reloadGrid");
            },"text");
        }
        if(status == "0") {
            $.post("${path}/user/edit",{"id":id,"status":1,"oper":"edit"},function (data) {
                //刷新页面
                $("#userId").trigger("reloadGrid");
            },"text");
        }
    }
</script>
<script>
    $(function () {
        $("#aliyun").click(function () {
            //获取手机号
            var phones = $("#phone").val();
            //发送ajax请求
            $.post("${path}/user/phoneCode",{phone:phones},function (data) {
                if(data.status=="200"){
                    alert(data.message);
                }else{
                    alert(data.message);
                }
            },"JSON")
        });
    });
</script>
<script>
    $(function () {
        $("#poiex").click(function () {
            alert("1111");
            $.get("${path}/user/poiexport",{},function () {
                alert("导出成功");
            })
        });
        $("#importExcels").click(function () {
            $.get("${path}/user/importExcel",function () {
                alert("导入成功");
            })
        });
    });
</script>
<body>
    <%--设置面板--%>
    <div class="panel panel-info">

        <%--面板头--%>
        <div class="panel panel-heading">
            <h2>用户信息</h2>
        </div>

            <div>
                <div class="pull-left"><%--"${path}/user/poiexport"--%>
                    <button class="btn btn-info" id="poiex">导出用户信息</button>
                    <button class="btn btn-success" id="importExcels">导入用户</button>
                    <button class="btn btn-danger">测试按钮</button>
                </div>

                <div class="pull-right col-sm-5">
                    <form>
                        <div class="col-md-4 col-md-offset-6" style="padding: 0px;">
                            <input type="text" class="form-control" id="phone" name="phone" placeholder="请输入手机号..." required
                                   minlength="11">
                        </div>
                        <div class="col-md-2 pull-right" style="padding: 0px;">
                            <button type="button" id="aliyun" class="btn btn-info btn-block">发送验证码</button>
                        </div>
                    </form>
                </div>
            </div>
            <br>
            <div style="margin-top: 20px;">
                <%--表单--%>
                <table id="userId"/>

                <%--分页工具栏--%>
                <div id="userPage"/>
            </div>

    </div>
</body>
</html>