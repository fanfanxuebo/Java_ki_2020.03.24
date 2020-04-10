<%--
  Created by IntelliJ IDEA.
  User: fanfa
  Date: 2020/4/8
  Time: 16:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>登陆页面</title>
</head>
<body>
<div align="center">
    <br/><br/><br/>
    <form action="/resume/login" method="post">
        用户名：<input name="username" value="admin"/><br/><br/>
         密码： <input name="password" value="admin"/><br/><br/>
        <div <c:if test="${errorMsg == ''}">hidden</c:if>>${errorMsg}</div>
        <input type="submit" value="登陆"/><br/>
    </form>
</div>
</body>
</html>
