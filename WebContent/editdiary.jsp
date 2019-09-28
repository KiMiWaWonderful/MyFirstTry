<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"   %>
       <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>



<c:if test="${requestScope.message != null }">
	<br>
	<font color="red">${requestScope.message }</font>
	<br>
	<br>
</c:if>

<c:set var="id" value="${diary !=null ? diary.id:param.id}"></c:set>
<c:set var="title" value="${diary !=null ? diary.title:param.title}"></c:set>
<c:set var="author" value="${diary !=null ? diary.author:param.author}"></c:set>
<c:set var="content" value="${diary !=null ? diary.content:param.content}"></c:set>

<form action="edit.do" method="post" accept-charset="UTF-8" >
<input type="hidden" name="id" value="${id }"/>
<table>
<tr>
<td>编辑文本</td>
</tr>
<tr>

<% request.setCharacterEncoding("UTF-8"); %>

</tr>
<tr>
<td>Title:</td><%---回显功能 --%>
<td><input name="title"  type="text" value="${ title}"></td><br><br>
</tr>

<tr>
<td>Author:</td>
<td><input name="author"  type="text" value="${author }"></td><br><br>
</tr>

<tr>
<td>Content:</td>
<td><textarea name="content"  rows="10" cols="60" >${ content  }</textarea></td><br><br>
</tr>

<tr>
<td><input type="submit" value="Submit"></td><br>
</tr>

<%---
String date=new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()); 
request.getParameter("date");
--%>

</table>
</form>
</body>
</html>