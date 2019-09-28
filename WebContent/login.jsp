<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
      <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%-- 
<script type="text/javascript">

window.onload=function(){
	document.getElementsByTagName("a")[0].onclick=function(){
		var request=new XMLHttpRequest();
		var url="success.jsp";
		var method="post";
		request.open(method,url);
		request.send(null);
		request.onreadystatechange=function(){
			if(request.readyState==4){
				if(request.status==200 || request.status==304){
					alert(request.responseText);
				}
			}
		}
		return false;
	}
}
</script>
--%>
</head>
<body>

<form action="login.do">


<h4>欢迎登陆</h4>


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
<td>密码：&nbsp &nbsp <input type="password" name="password" value="${param.password}"></td>
</tr>
<br>
<tr>
<td>验证码：<input type="text" name="inputCode"></td>
<td><img src="checkCode.do" id="checkImg"/><a href="#" onClick="window.location.reload()">看不清</a></td><br>    
</tr>
<br>
<tr>
<td><input type="submit" value="Submit"></td>
</tr>
</table>

</form>
</body>
</html>