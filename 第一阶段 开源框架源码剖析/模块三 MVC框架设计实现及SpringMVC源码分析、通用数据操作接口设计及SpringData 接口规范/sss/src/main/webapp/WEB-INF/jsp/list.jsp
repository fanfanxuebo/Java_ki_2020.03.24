<%--
  Created by IntelliJ IDEA.
  User: fanfa
  Date: 2020/4/8
  Time: 16:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>查询页面</title>
</head>
<body>
<a href="/resume/queryAll">刷新数据</a>
<a href="/resume/toSaveOrEdit">新增简历</a>
<table width="70%" align="center" border="1">
    <c:forEach items="${resumeList}" var="resume">
        <tr>
            <td hidden>${resume.id}</td>
            <td>${resume.name}</td>
            <td>${resume.address}</td>
            <td>${resume.phone}</td>
            <td><a href="/resume/toSaveOrEdit?id=${resume.id}">编辑</a> / <a href="/resume/delete?id=${resume.id}">删除</a> </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
