<%@ page pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%--jqgrid的核心css--%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jqgrid/css/ui.jqgrid.css">
<%--jqgrid的主题css--%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jqgrid/css/css/cupertino/jquery-ui-1.8.16.custom.css">
<strong><h3>专辑管理</h3></strong>
<hr>
<script type="text/javascript">
    $(function () {
        //初始化jqgrid
        $("#table1").jqGrid({
            styleUI:"Bootstrap",
            url:"${pageContext.request.contextPath}/p2/queryByPage",
            datatype:"json",
            colNames:["编号","专辑名称","专辑封面","章节数量","专辑得分","专辑作者","播音员","专辑简介","出版时间"],
            colModel:[{
                name:"id",
            },{
                name:"title",editable:true
            },{
                name:"cover",
                edittype:"file",
                editable:true,
                formatter:function(cellvalue, options, rowObject){
                    return "<img style='width:50px;height:50px' src='${pageContext.request.contextPath}/upload/"+cellvalue+"'/>";
                }
            },{
                name:"count"
            },{
                name:"score",editable:true
            },{
                name:"author",editable:true
            },{
                name:"broadcast",editable:true
            },{
                name:"brief",editable:true
            },{
                name:"publishTime",editable:true,edittype:"date"
            }],
            // 分页相关
            pager:"#pager",
            viewrecords:true,
            autowidth:true,
            rowNum:5,// 每页显示的行数
            rowList:[2, 5, 10, 15], // 行数的数组
            page:1,  // 默认显示的页码
            // 增删改提交到的的资源地址，使用oper参数进行区分（添加add 修改edit 删除del）
            editurl:"${pageContext.request.contextPath}/p2/edit",
            height:"100%",
            rownumbers: true,
            multiselect: true,
            subGrid : true,
            caption : "Grid as Subgrid",
            subGridRowExpanded : function(subgrid_id, row_id) {
                // we pass two parameters
                // subgrid_id is a id of the div tag created whitin a table data
                // the id of this elemenet is a combination of the "sg_" + id of the row
                // the row_id is the id of the row
                // If we wan to pass additinal parameters to the url we can use
                // a method getRowData(row_id) - which returns associative array in type name-value
                // here we can easy construct the flowing
                var subgrid_table_id, pager_id;
                subgrid_table_id = subgrid_id + "_t";
                pager_id = "p_" + subgrid_table_id;
                $("#" + subgrid_id).html(
                    "<table id='" + subgrid_table_id
                    + "' class='scroll'></table><div id='"
                    + pager_id + "' class='scroll'></div>");
                jQuery("#" + subgrid_table_id).jqGrid(
                    {

                        url : "${pageContext.request.contextPath}/p3/queryByPage?id=" + row_id,
                        styleUI:"Bootstrap",
                        multiselect: true,
                        datatype : "json",
                        colNames : [ '编号', '专辑编号', '音频名称', '音频大小','音频路径','上传时间','操作' ],
                        colModel : [
                            {name : "id",  index : "id",width : 80,key : true},
                            {name : "albumid",index : "albumid",width : 70,align : "right"},
                            {name : "title",index : "title",  width : 130,editable:true},
                            {name : "size",index : "size",width : 70,align : "right",editable:true},
                            {name : "downPath",index : "downPath",width : 70,align : "right",editable:true,edittype:"file"},
                            {name : "uploadTime",index : "uploadTime",edittype:"date",width : 70,align : "right",sortable : false,editable:true},
                            {
                                formatter:function (celVal, row, jsonRow) {
                                    return "<a class='btn btn-primary btn-sm' href='${pageContext.request.contextPath}/p3/down?fname="+jsonRow.downPath+"' >下载</a>"
                                }
                            }],
                        rowNum : 20,
                        autowidth:true,
                        pager : pager_id,
                        viewrecords:true,
                        sortname : 'num',
                        sortorder : "asc",
                        height : '100%',
                        rowNum:5,// 每页显示的行数
                        rowList:[2, 5, 10, 15], // 行数的数组
                        page:1,
                        editurl:"${pageContext.request.contextPath}/p3/edit?albumid=" + row_id
                    });
                jQuery("#" + subgrid_table_id).jqGrid('navGrid',
                    "#" + pager_id, {
                        edit : false,
                        add : true,
                        del : false
                    }, {closeAfterEdit:true},{
                        closeAfterAdd:true,
                        afterSubmit:function(response){
                            $.ajaxFileUpload({
                                url:"${pageContext.request.contextPath}/p3/upload",
                                fileElementId:"downPath",
                                data:{"id":response.responseText},
                                type:"post",
                                success:function(){
                                    $("#table1").trigger("reloadGrid");
                                }
                            })
                            return "[true]";
                        }
                    });
            },

        }).jqGrid("navGrid","#pager",{add:true,edit:true,del:true,refresh:true},{
            closeAfterEdit:true,
            afterSubmit:function(response){
                $.ajaxFileUpload({
                    url:"${pageContext.request.contextPath}/p2/upload",
                    fileElementId:"cover",
                    data:{"id":response.responseText},
                    type:"post",
                    success:function(){
                        $("#table1").trigger("reloadGrid");
                    }
                })
                return "[true]";
            }},
            {
                closeAfterAdd:true,
                afterSubmit:function(response){
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/p2/upload",
                        fileElementId:"cover",
                        data:{"id":response.responseText},
                        type:"post",
                        success:function(){
                            $("#table1").trigger("reloadGrid");
                        }
                    })
                    return "[true]";
                }
            })
    })
</script>
<body>
<table id="table1"></table>
<div id="pager" onclick="delRows()"></div>
</body>