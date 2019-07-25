<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>

<script charset="utf-8" src="../kindeditor/kindeditor-all.js"></script>
<script charset="utf-8" src="../kindeditor/lang/zh-CN.js"></script>
<script>
    KindEditor.ready(function(K) {
        window.editor = K.create('#editor_id',{
            width : '1000px',
            uploadJson:'${pageContext.request.contextPath}/article/upload',
            fileManagerJson:'${pageContext.request.contextPath}/article/showAll',
            allowFileManager:true,
            filePostName:'file',
            afterBlur:function() {
                this.sync();
            }
        });
    });
</script>


<textarea id="editor_id" name="content" style="width:700px;height:300px;">
&lt;strong&gt;HTML内容&lt;/strong&gt;
</textarea>