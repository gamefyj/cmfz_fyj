<%@ page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--jqgrid的核心css--%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jqgrid/css/ui.jqgrid.css">
<%--jqgrid的主题css--%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jqgrid/css/css/cupertino/jquery-ui-1.8.16.custom.css">
<strong><h3>专辑管理</h3></strong>
<hr>
<script type="text/javascript">
    $(function () {
        //初始化jqgrid
        $("#table3").jqGrid({
            styleUI:"Bootstrap",
            url:"${pageContext.request.contextPath}/user/queryByPage",
            datatype:"json",
            colNames:["编号","手机号","密码","法名","省","市","性别","个性签名","头像","状态","创建时间","操作"],
            colModel:[{
                name:"id",
            },{
                name:"phone",
            },{
                name:"password",
            },{
                name:"dharmaName"
            },{
                name:"province"
            },{
                name:"city"
            },{
                name:"gender"
            },{
                name:"personalSign"
            },{
                name:"profile"
            },{
                name:"status"
            },{
                name:"registTime",edittype:"date"
            },{
                formatter:function (cellvalue, row, jsonRow) {
                    if(jsonRow.status=="正常"){
                        return "<button class='btn btn-primary btn-sm' onclick='update(\""+jsonRow.id+"\",\""+jsonRow.status+"\")'>点击冻结</button>"
                    }else  if(jsonRow.status=="冻结"){
                        return "<button class='btn btn-primary btn-sm' onclick='update(\""+jsonRow.id+"\",\""+jsonRow.status+"\")'>解除冻结</button>"
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
            height:"100%",
            rownumbers: true,
        })
    })

    function update(id,status) {
        $.ajax({
            url:"${pageContext.request.contextPath}/user/update",
            type:"post",
            datatype:"json",
            data:{status:status,id:id},
            success:function(data){
                $("#table3").trigger("reloadGrid");
            }
        })
    }
    //一键导入
  function onSubmit() {
        var formData = new FormData(document.getElementById('form1'));
        $.ajax({
            url:"${pageContext.request.contextPath}/user/daoru",
            type:"post",
            data:formData,
            datatype:"json",
            processData:false,
            contentType:false,
            success:function () {
                $("#table3").trigger("reloadGrid");
                alert(成功)
            },
            error:function () {
                alert(失败)
            }
        })
  }

</script>
<body>
<a class='btn btn-primary btn-sm' href="${pageContext.request.contextPath}/user/importFile">一键导出</a><br><br>


<form  enctype="multipart/form-data" id="form1" >
<input type="file" name="file">
<input type="button" onclick="onSubmit()" value="上传">
</form>
<table id="table3"></table>
<div id="pager"></div>
</body>