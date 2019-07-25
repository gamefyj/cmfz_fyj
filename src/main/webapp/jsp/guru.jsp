<%@ page pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%--jqgrid的核心css--%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jqgrid/css/ui.jqgrid.css">
<%--jqgrid的主题css--%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jqgrid/css/css/cupertino/jquery-ui-1.8.16.custom.css">
<script charset="utf-8" src="../kindeditor/kindeditor-all.js"></script>
<script charset="utf-8" src="../kindeditor/lang/zh-CN.js"></script>
<strong><h3>上师和文章管理</h3></strong>
<hr>
<script type="text/javascript">
    $(function () {
        KindEditor.create('#editor_id',{
            width : '300px',
            uploadJson:'${pageContext.request.contextPath}/article/upload',
            fileManagerJson:'${pageContext.request.contextPath}/article/showAll',
            allowFileManager:true,
            filePostName:'file',
            afterBlur:function(){
                this.sync();
            }
        });


        $("#table1").jqGrid({
            styleUI:"Bootstrap",
            url:"${pageContext.request.contextPath}/p4/queryByPage",
            datatype:"json",
            colNames:["上师编号","上师名称","头像","状态","性别","操作"],
            colModel:[{
                name:"id",
            },{
                name:"name",editable:true
            },{
                name:"profile",
                edittype:"file",
                editable:true,
                formatter:function(cellvalue, options, rowObject){
                    return "<img style='width:50px;height:50px' src='${pageContext.request.contextPath}/GuruUpload/"+cellvalue+"'/>";
                }
            },{
                name:"status",editable:true,edittype:'select', editoptions: {value:{正常:"正常",冻结:"冻结"}}
            },{
                name:"sex",editable:true,edittype:'select', editoptions: {value:{男:"男",女:"女"}}
            },{
                formatter:function (cellvalue, row, jsonRow) {
                    if(jsonRow.status=="冻结"){
                        return "<button class='btn btn-primary btn-sm' onclick='cc(\""+jsonRow.id+"\")'>上传文章</button>  <button class='btn btn-primary btn-sm' onclick='update(\""+jsonRow.id+"\",\""+jsonRow.status+"\")'>解除冻结</button>"
                    }else if(jsonRow.status=="正常"){
                        return "<button class='btn btn-primary btn-sm'  onclick='cc(\""+jsonRow.id+"\")'>上传文章</button>  <button class='btn btn-primary btn-sm' onclick='update(\""+jsonRow.id+"\",\""+jsonRow.status+"\")'>点击冻结</button>"
                    }
                }
            }],
            // 分页相关
            pager:"#pager",
            viewrecords:true,
            autowidth:true,
            rowNum:5,// 每页显示的行数
            rowList:[2, 5, 10, 15], // 行数的数组
            page:1,  // 默认显示的页码
            // 增删改提交到的的资源地址，使用oper参数进行区分（添加add 修改edit 删除del）
            editurl:"${pageContext.request.contextPath}/p4/edit",
            height:"100%",
            rownumbers: true,
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
                        url : "${pageContext.request.contextPath}/article/queryByPage?id="+ row_id,
                        styleUI:"Bootstrap",
                        multiselect: true,
                        datatype : "json",
                        colNames : [ '编号', '上师编号', '文章标题', '内容','发布时间'],
                        colModel : [
                            {name : "id",  index : "id",width : 80,key : true},
                            {name : "guruId",index : "guruId",width : 70,align : "right"},
                            {name : "title",index : "title",  width : 130,editable:true},
                            {name : "content",formatter:function (cellvalue, row, jsonRow) {
                                    return "<button class='btn btn-primary btn-sm' onclick='content(\""+jsonRow.id+"\")'>文章预览</button>"
                                }},
                            {name : "publishTime",index : "publishTime",width : 70,align : "right",editable:true,edittype:"date"
                            }],
                        rowNum : 20,
                        autowidth:true,
                        pager : pager_id,
                        viewrecords:true,
                        sortname : 'num',
                        sortorder : "asc",
                        height : '100%',
                        editurl:"${pageContext.request.contextPath}/article/edit?guruId=" + row_id
                    });
                jQuery("#" + subgrid_table_id).jqGrid('navGrid',
                    "#" + pager_id, {
                        edit : true,
                        add : false,
                        del : true

                    });
            },

        }).jqGrid("navGrid","#pager",{add:true,edit:false,del:false,refresh:false},{
                closeAfterEdit:true,
                afterSubmit:function(response){
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/p4/upload",
                        fileElementId:"profile",
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
                        url:"${pageContext.request.contextPath}/p4/upload",
                        fileElementId:"profile",
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
    //预览模态框
    function content(id) {
        $("#myModal1").modal("show");
        $.ajax({
            url:"${pageContext.request.contextPath}/article/queryOne",
            type:"post",
            datatype:"json",
            data:{id:id},
            success:function(data){
                $("#bb1").html(data.content)
            }
        })
    }








    //显示模态框 带上师ID
    function cc(id) {
        // 显示 模态框
        $("#myModal").modal("show");
        $("#bb").val(id)
    }
    // 模态框表单ajax提交
    function addArticle() {
        $("#myModal").modal("hide");
        $.ajax({
            url:"${pageContext.request.contextPath}/article/add",
            type:"post",
            datatype:"json",
            data:$("#articleForm").serialize(),
            success:function(data){
                $("#table1").trigger("reloadGrid");
                //清空表单数据
                document.getElementById("articleForm").reset();
            }
        })
    }
    function update(id,status){
        $.ajax({
            url:"${pageContext.request.contextPath}/p4/status",
            type:"post",
            datatype:"json",
            data:{status:status,id:id},
            success:function(data){
                $("#table1").trigger("reloadGrid");
            }
        })
    }
</script>
<body>
<table id="table1"></table>
<div id="pager" onclick="delRows()"></div>


<%--上传文章模态框--%>
<div class="modal fade" id="myModal" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content" style="width:800px">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Modal title</h4>
            </div>
            <form action="javascript:void(0);" id="articleForm">
                <div class="modal-body" id="aa">
                    <input type="hidden" name="guruId" id="bb">
                    <input type="text" name="title">
                    <textarea id="editor_id" name="content" style="width:700px;height:300px;">
                &lt;strong&gt;HTML内容&lt;/strong&gt;
                </textarea>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary" onclick="addArticle()">上传</button>
                </div>
            </form>
        </div>
    </div>
</div>
<%--预览模态框--%>
<div class="modal fade" id="myModal1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content" style="width:800px">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel1">文章预览</h4>
            </div>
            <form action="javascript:void(0);" id="articleForm1">
                <div class="modal-body" id="aa1" style="width: 500px">
                    <span id="bb1"></span>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                </div>
            </form>
        </div>
    </div>
</div>

</body>