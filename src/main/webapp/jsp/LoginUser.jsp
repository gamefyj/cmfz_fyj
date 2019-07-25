<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; UTF-8" %>
<form action="${pageContext.request.contextPath}/user/login" method="post">
  <input type="text" name="phone"><br>
 <input type="password" name="password"><br>
 <input type="submit" value="登录">
</form>