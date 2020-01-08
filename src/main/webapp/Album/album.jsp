<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
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
<script type="text/javascript">
    $(function () {
        $("#abTable1").jqGrid({
            url: "${path}/album/selectByPage",
            editurl: "${path}/album/edit",
            datatype: "json",
            rowNum: 3,
            rowList: [3, 6, 9],
            pager: '#abPage1',
            viewrecords: true,
            styleUI: "Bootstrap",
            height: "auto",
            autowidth: true,
            multiselect: false,
            colNames: ['Id', '标题', '评分', '作者', '播音', '集数', '描述', '状态','发布日期', '图片'],
            colModel: [
                {name: 'id', index: 'id', width: 55},
                {name: 'title',editable: true, index: 'invdate', width: 90},
                {name: 'score',editable: true, index: 'name', width: 100},
                {name: 'author',editable: true, index: 'name', width: 100},
                {name: 'broadcast',editable: true, index: 'name', width: 100},
                {name: 'count',editable: true, index: 'amount', width: 80, align: "right"},
                {name: 'description', editable: true, index: 'tax', width: 80, align: "right"},
                {name : 'status',align:"center",formatter:function (data) {
                        if (data=="1"){
                            return "展示";
                        } else return "冻结";
                    },editable:true,editrules:{required:true},edittype:"select",editoptions: {value:"1:展示;2:冻结"}
                },
                {name: 'createDate',index : 'note',width : 150,sortable : false,align:"center",editable:true,edittype:"date"},
                {
                    name: "src", editable: true, index: "item", width: 130, edittype: "file",editoptions: {enctype:"multipart/form-data"},
                    formatter: function (cellvalue, options, rowObject) {
                        return "<img src='${path}/upload/photo/" + cellvalue + "' style='width:180px;height:80px' />";
                    }
                },
            ],
            subGrid: true,  //是否开启子表格
            //1.subgrid_id 点击一行会在父表各种创建一个div来容纳子表格  subgrid_id就是这个div的id
            //2.row_id  点击行的id
            subGridRowExpanded: function (subgrid_id, row_id) {
                addSubGrid(subgrid_id, row_id);
            }
        });

        //父表格 工具栏
        $("#abTable1").jqGrid('navGrid', '#abPage1', {edit: true, add: true, del: true},
         {
             closeAfterEdit: true,
                 //提交之后操作
                 afterSubmit: function (data) {

             //文件上传
             $.ajaxFileUpload({
                 url: "${path}/album/upload",
                 type: "post",
                 dataType: "json",
                 data: {id: data.responseText},
                 //需要上传的文件域的id
                 fileElementId: "src",
                 success: function () {
                     //刷新表单
                     $("#abTable1").trigger("reloadGrid");
                 }
             });
             return "cjh";
         }
         },
        //添加之后的方法
        {
            closeAfterAdd: true, //关闭添加框
                //提交之后操作
                afterSubmit: function (data) {

            //文件上传
            $.ajaxFileUpload({
                url: "${path}/album/upload",
                type: "post",
                dataType: "json",
                data: {id: data.responseText},
                //需要上传的文件域的id
                fileElementId: "src",
                success: function () {
                    //刷新表单
                    $("#abTable1").trigger("reloadGrid");
                }
            });
            return "cjh";
        }
        }
    );
    });

    //子表格
    function addSubGrid(subgridId, rowId) {

        var subgridTableId = subgridId + "Table";
        var pagerId = subgridId + "Page";

        //创建子表格的 table 和分页工具栏
        $("#" + subgridId).html("" +
            "<table id='" + subgridTableId + "' />" +
            "<div id='" + pagerId + "'/>"
        );
        //子表格
        $("#" + subgridTableId).jqGrid({

            //url : "/chapter/queryByPage?AlbumId=" + rowId,
            url: "${path}/chapter/selectByPage?id=" + rowId,
            editurl: "${path}/chapter/edit?albumId=" + rowId,
            datatype: "json",
            rowNum: 20,
            pager: "#" + pagerId,
            styleUI: "Bootstrap",
            height: "auto",
            viewrecords: true,
            autowidth: true,
            colNames: ['Id', '标题', '操作', '大小', '时长', '日期', '专辑id'],
            colModel: [
                {name: "id", index: "num", width: 80, key: true},
                {name: "title", editable: true, index: "item", width: 130},
                {name: "url", editable: true, edittype: "file", index: "qty", width: 70, align: "right",
                    formatter: function (cellvalue) {
                        return "<a href='#'  onclick=\"onPlay('"+cellvalue+"')\"><span class='glyphicon glyphicon-download' /></a>&nbsp;&emsp;&emsp;" +
                            "<a href='#' onclick=\"audioPlayer('" + cellvalue + "')\"><span class='glyphicon glyphicon-play-circle' /></a>";
                    }
                        },
                {name: "csize", index: "qty", width: 70, align: "right"},
                {name: "ctime", index: "unit", width: 70, align: "right"},
                {name: "createTime", index: "total", width: 70, align: "right", sortable: false},
                {name: "albumId", index: "total", width: 70, align: "right"}

            ]
        });

        //子表格的正删改查操作
        $("#" + subgridTableId).jqGrid('navGrid', "#" + pagerId, {edit: true, add: true, del: true},
            //修改之后的方法
            {
                closeAfterEdit: true,
                //提交之后操作
                afterSubmit: function (data) {

                    //文件上传
                    $.ajaxFileUpload({
                        url: "${path}/chapter/uploadChapter",
                        type: "post",
                        dataType: "json",
                        data: {id: data.responseText},
                        //需要上传的文件域的id
                        fileElementId: "url",
                        success: function () {
                            //刷新表单
                            $("#" + subgridTableId).trigger("reloadGrid");
                        }
                    });
                    return "cjh";
                }
            },
            //添加之后的方法
            {
                closeAfterAdd: true, //关闭添加框
                //提交之后操作
                afterSubmit: function (data) {

                    //文件上传
                    $.ajaxFileUpload({
                        url: "${path}/chapter/uploadChapter",
                        type: "post",
                        dataType: "json",
                        data: {id: data.responseText},
                        //需要上传的文件域的id
                        fileElementId: "url",
                        success: function () {
                            //刷新表单
                            $("#" + subgridTableId).trigger("reloadGrid");
                        }
                    });
                    return "cjh";
                }
            },
            {
                closeAfterDel: true,
            }
        );
    }

    //下载
    function onPlay(cellvalue) {
        location.href = "${path}/chapter/audioDownload?audioName=" + cellvalue;
    }

    //在线播放
    function audioPlayer(cellvalue) {
        alert(cellvalue)
        //给音频标签设置值
        $("#myAudio").attr("src", cellvalue);
        //展示模态框
        $("#AudioModal").modal("show");


    }
</script>


<%--初始化面板--%>
<div class="panel panel-warning">

    <%--面板标题--%>
    <div class="panel panel-heading">
        <h3>专辑管理</h3>
    </div>

    <%--标签页--%>
    <ul class="nav nav-tabs">
        <li class="active"><a>专辑管理</a></li>
    </ul>

    <%--初始化表单--%>
    <table id="abTable1"/>

    <%--分页工具栏--%>
    <div id="abPage1"/>
    <!--播放的模态框-->
    <div id="AudioModal" class="modal fade " tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <audio id="myAudio" src="" controls/>
        </div>
    </div>
</div>