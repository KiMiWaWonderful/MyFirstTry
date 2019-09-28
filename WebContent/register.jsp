<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>



<form action="register.do">

<%---这一步要写在form表单里面 --%>

<h4>欢迎注册</h4>
<br><br>
<c:if test="${requestScope.message != null }">
	<br>
	<font color="red">${requestScope.message }</font>
	<br>
	<br>
</c:if>

<table>
<tr>
<td>用户名：<input type="text" name="userName" value="${param.userName}"></td>
</tr>
<br>
<tr>
<td>密码：&nbsp &nbsp<input type="password" name="password"  value="${param.password}"></td>
</tr>
<br>
<tr>
<td><input type="submit" value="Submit"></td>
</tr>
</table>
</form>
</body>
</html>