<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<%-- <c:set var="id" value="${user.id}"></c:set>--%>
<form action="query.do" method="post" accept-charset="UTF-8">
 <!--<form action="QueryServlet" method="post" >- -->

<h4>日记列表</h4>
欢迎您：${user.userName}  
<hr>
<table border="0" cellpadding="10" cellspacing="0">
<br><br>

<tr>
<th>Title</th>
<th>Author</th>
<th>Date</th>
<th>Edit</th>
<th>Delete</th>
<th>Comment</th>
<th>导出文件</th>
<th>点赞</th>
</tr>

<c:if test="${! empty requestScope.diarys }">

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

<td>
<c:url value="/showEditContent.do" var="showeditcontenturl">
<c:param name="id" value="${diary.id }"></c:param>
</c:url>
<a href="${showeditcontenturl }">编辑</a>
</td>

<td>
<c:url value="/delete.do" var="deleteurl">
<c:param name="id" value="${diary.id }"></c:param>
</c:url>
<a href="${deleteurl }">删除</a>
</td>

<td>
<c:url value="/showAddCommentContent.do" var="showaddcommenturl">
<c:param name="id" value="${diary.id }"></c:param>
</c:url>
<a href="${ showaddcommenturl}">添加评论</a>
</td>

<td>
<c:url value="/exportFile.do" var="exportfileurl">
<c:param name="id" value="${diary.id }"></c:param>
</c:url>
<a href="${ exportfileurl}">导出文件</a>
</td>

<td>
<c:url value="/addLike.do" var="addlikeurl">
<c:param name="id" value="${diary.id }"></c:param>
<c:param name="id" value="${sessionScope.user_id }"></c:param>
</c:url>
<a href="${ addlikeurl}">点赞</a>
</td>
</tr>
</c:forEach>
</c:if>

</table>
<br><br>
<br><br>
<a href="showconsequence.jsp">搜索</a>&nbsp &nbsp<a href="adddiary.jsp">添加</a>&nbsp &nbsp<a href="queryByCategory.do">分类查看：</a>
<br><br>
<a href="register.jsp">注册</a> &nbsp &nbsp <a href="login.jsp">登陆</a>
</form>

<a href="https://www.baidu.com" target="_self" onclick="window.location.href='https://zhidao.baidu.com/'">链接</a>
</body>
</html>