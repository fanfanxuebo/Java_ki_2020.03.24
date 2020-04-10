<%--
  Created by IntelliJ IDEA.
  User: fanfa
  Date: 2020/4/8
  Time: 17:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title><c:if test="${resume.id != null}">编辑</c:if><c:if test="${resume.id == null}">新增</c:if>简历</title>
</head>
<body>
<div align="center">
<form action="/resume/saveOrEdit" method="post">
    <input name="id" value="${resume.id}" hidden/><br/>
    姓名：<input name="name" value="${resume.name}"/><br/>
    地址：<input name="address" value="${resume.address}"/><br/>
    手机：<input name="phone" value="${resume.phone}"/><br/><br/>
    <input type="submit" value="保存"/><br/>
</form>
<a href="/resume/queryAll">查询所有简历</a>
</div>
</body>
</html>
