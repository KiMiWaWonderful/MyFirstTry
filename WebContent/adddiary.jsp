<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"   %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<form action="add.do" method="post" accept-charset="UTF-8">

<h4>添加日记</h4>
<table>

<tr>
<td>Title:</td>
<td><input name="title"  type="text" value="${param.title}"><br></td><%---回显功能 --%>

</tr>

<%-- 
<tr>
<td>Author:</td>
 <td><input name="author"  type="text" value="${param.author}"></td><br>
<td>${userName }</td><br>
</tr>
--%>

<tr>
<td>Content:</td>
<td><textarea name="content"  rows="10" cols="60" value="${param.content }"></textarea></td><br>
</tr>

<tr>
<td>Category:
<select name="category_name">
<option value="汉语">汉语</option>
<option value="英语">英语</option>
<option value="null">不分类</option>
</select><br>
</td>
</tr>

<tr>
<td><input type="submit" value="Submit"></td>
</tr>

<%---
String date=new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()); 
request.getParameter("date");
--%>

</table>
</form>
</body>
</html>