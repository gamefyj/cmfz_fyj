<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; UTF-8" %>
<form method="post" action="${pageContext.request.contextPath}/user/regist">
   手机号:<input type="text"name="phone"><br>
    密码: <input type="password" name="password"><br>
    上师名称: <input type="text" name="dharmaName"><br>
    省份: <input type="text" name="province"><br>
    城市: <input type="text" name="city"><br>
    性别: <input type="text" name="gender"><br>
    个性签名: <input type="text" name="personalSign"><br>
    头像路径: <input type="text" name="profile"><br>
    注册日期:<input type="date" name="registTime"><br>
 <input type="submit"value="提交">
</form>