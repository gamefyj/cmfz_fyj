<%@ page pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%--jqgrid的核心css--%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jqgrid/css/ui.jqgrid.css">
<%--jqgrid的主题css--%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jqgrid/css/css/cupertino/jquery-ui-1.8.16.custom.css">
<strong><h3>轮播图管理</h3></strong>
<hr>
<script type="text/javascript">
    $(function () {
        //初始化jqgrid
        $("#table1").jqGrid({
            styleUI:"Bootstrap",
            url:"${pageContext.request.contextPath}/p1/queryByPage",
            datatype:"json",
            colNames:["编号","轮播图名称","轮播图图片","状态","创建时间"],
            colModel:[{
                name:"id",
            },{
                name:"title",editable:true
            },{
                name:"imgPath",
                edittype:"file",
                editable:true,
                formatter:function(cellvalue, options, rowObject){
                    return "<img style='width:50px;height:50px' src='${pageContext.request.contextPath}/upload/"+cellvalue+"'/>";
                }
            },{
                name:"status",editable:true
            },{
                name:"createTime",editable:true,edittype:"date"
            }],
            // 分页相关
            pager:"#pager",
            viewrecords:true,
            autowidth:true,
            rowNum:5,// 每页显示的行数
            rowList:[2, 5, 10, 15], // 行数的数组
            page:1,  // 默认显示的页码
            // 增删改提交到的的资源地址，使用oper参数进行区分（添加add 修改edit 删除del）
            editurl:"${pageContext.request.contextPath}/p1/edit",
            height:"100%",
            rownumbers: true,
            multiselect: true,
        }).jqGrid("navGrid","#pager",{add:true,edit:true,del:true,refresh:true},{
            closeAfterEdit:true,
            afterSubmit:function(response){
                $.ajaxFileUpload({
                    url:"${pageContext.request.contextPath}/p1/upload",
                    fileElementId:"imgPath",
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
                        url:"${pageContext.request.contextPath}/p1/upload",
                        fileElementId:"imgPath",
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