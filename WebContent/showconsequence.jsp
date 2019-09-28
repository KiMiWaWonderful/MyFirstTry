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

<form action="queryPartly.do">
<table>
<tr>
<td>Title:</td>
<td><input name="title"  type="text"></td><br>
</tr>

<tr>
<td>Author:</td>
<td><input name="author"  type="text"></td><br>
</tr>

<tr>
<td>Content:</td>
<td><input name="content"  type="text"></td><br>
</tr>

<tr>
<td><input type="submit" value="Query"></td><%--Q要大写 --%>
</tr>
</table>
</form>


<c:if test="${! empty requestScope.diarys }">

<hr>
<table border="0" cellpadding="10" cellspacing="0">
<br><br>


<tr>
<td>Title</td>
<td>Author</td>
<td>Date</td>
</tr>

<c:forEach items="${requestScope.diarys }" var="diary" >
<tr>
<td>
<c:url value="/queryContent.do" var="showcontenturl">
<c:param name="id" value="${diary.id }"></c:param>
</c:url>
<a href="${showcontenturl }">${diary.title }</a>
</td>
<td>${diary.author }</td>
<td>${diary.date }</td>
</tr>

</c:forEach>
</table>
</c:if>

</body>
</html>