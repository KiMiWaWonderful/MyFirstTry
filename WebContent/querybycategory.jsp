<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.List" import="domain.Diary" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<form action="queryByCategory.do" method="post">

<table border="0" cellpadding="10" cellspacing="0">

<br><br>

<tr>
<td>Category_name</td>
<td>Title</td>
</tr>

<c:if test="${! empty requestScope.category }">

<c:forEach items="${requestScope.category }" var="category" >

<tr>
<c:choose>
<c:when test="${category.category_name ==null}">
<td>null</td>
</c:when>
<c:otherwise>
<td>${category.category_name }</td>
</c:otherwise>
</c:choose>


<td>${category.title }</td>
</tr>
</c:forEach>

</c:if>
</table>
</form>
</body>
</html>