<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>


<script>

    $(function(){
        pageInit();
    });

    function pageInit(){
        $("#cateTable").jqGrid({
            url : "${path}/video/queryByPageVideo",
            datatype : "json",
            rowNum : 3,
            rowList : [ 3, 10, 20, 30 ],
            pager : '#catePage',
            sortname : 'id',
            viewrecords : true,
            styleUI:"Bootstrap",
            autowidth:true,
            height:"auto",
            colNames : [ 'ID', '标题','视频','封面','上传时间','描述','所属类别', '类别id','用户id'],
            colModel : [
                {name : 'id',index : 'id',  width : 55},
                {name : 'title',editable:true,index : 'invdate',width : 90},
                {name : 'videoPath',editable:true,index : 'amount',width : 80,align : "right",edittype:"file",
                    formatter: function (cellvalue, options, rowObject) {
                        return "<video src='" + cellvalue + "' controls width='150px' height='200px'/>";
                    }
                },
                {
                    name : 'coverPath',
                    formatter: function (cellvalue, options, rowObject) {
                        return "<img src='" + cellvalue + "' controls width='150px' height='200px'/>";
                    }
                },
                {name : 'uploadTime'},
                {name : 'brief',editable:true},
                {name : 'groupId'},
                {name : 'categoryId'},
                {name : 'userId'}
            ],
            editurl:"${path}/video/edit",
        });
        $("#cateTable").jqGrid('navGrid', '#catePage', {add : true,edit : true,del : true},
            {
                closeAfterEdit: true,//关闭面板
                reloadAfterSubmit: true,
                afterSubmit: function (data) {  //添加成功之后执行的内容
                    alert(data.responseText);
                    //1.信息入库   返回id
                    //2.文件上传
                    $.ajaxFileUpload({
                        url: "${path}/user/upload",
                        type: "post",
                        data: {"id": data.responseText},
                        fileElementId: "videoPath", //文件选择框的id属性，即<input type="file" id="picImg" name="picImg">的id
                        success: function () {
                            //上传成功 所作的事情
                            //刷新页面
                            $("#cateTable").trigger("reloadGrid");
                        }
                    });
                    return "hello";
                }
            },  //修改
            {
                closeAfterAdd: true,
                reloadAfterSubmit: true,
                afterSubmit: function (data) {  //添加成功之后执行的内容
                    alert(data.responseText);
                    //1.信息入库   返回id
                    //2.文件上传
                    $.ajaxFileUpload({
                        url: "${path}/video/headUpload",
                        type: "post",
                        data: {"id": data.responseText},
                        fileElementId: "videoPath", //文件选择框的id属性，即<input type="file" id="picImg" name="picImg">的id
                        success: function () {
                            //上传成功 所作的事情
                            //刷新页面
                            $("#cateTable").trigger("reloadGrid");
                        }
                    });
                    return "hello";
                }
            }, //添加
            {
                closeAfterDelete: true,
                reloadAfterSubmit: true,
            }, //删除
            {

            }
        );

    }

</script>


<%--设置面板--%>
<div class="panel panel-success">

    <%--面板头--%>
    <div class="panel panel-heading">
        <h2>视频信息</h2>
    </div>
    <%--警告框--%>
    <div id="showMsg" style="width: 300px;display:none" class="alert alert-danger alert-dismissible" role="alert">
        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <span id="message"></span>
    </div>
    <%--标签页--%>
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">类别管理</a></li>
    </ul>

    <%--表单--%>
    <table id="cateTable" />

    <%--分页工具栏--%>
    <div id="catePage" />

</div>

<%--
    删除要有提示信息
--%>
