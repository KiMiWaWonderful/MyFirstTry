<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<c:set var="id" value="${diary !=null ? diary.id:param.id}"></c:set>
<c:set var="title" value="${diary !=null ? diary.title:param.title}"></c:set>
<c:set var="author" value="${diary !=null ? diary.author:param.author}"></c:set>
<c:set var="content" value="${diary !=null ? diary.content:param.content}"></c:set>

<table>
<tr>
<th colspan="6">${diary.title }</th>
</tr>
<br><br>
<tr>
<td colspan="11" >${diary.author }</td>
</tr>
<br><br>

<br><br>
<tr>
<td>${diary.content }</td>
</tr>
<br><br>

<br><br>
<tr>
<td>${comment.comment }</td>
</tr>
<br><br>

</table>

<form action="addComment.do" method="post" accept-charset="UTF-8">

<c:set var="title" value="${diary !=null ? diary.title:param.title}"></c:set>
<input type="hidden" name="title" value="${diary.title }"/>

<!--  <input type="hidden" name="id" value="${id }"/>
<input type="hidden" name="author" value="${author }"/>
<input type="hidden" name="content" value="${content }"/>
- -->

<table>
<%--- 
<tr>
<td>留言人：</td>
<td><input type="text" name="name"></td>
<tr>
--%>

<td>留言内容:</td>
<td><textarea name="commentt"  rows="10" cols="60" ></textarea></td>
</tr>

<tr>
<td colspan="2"><input type="submit" value="Submit"></td>
</tr>

</table>
</form>
</body>
</html>