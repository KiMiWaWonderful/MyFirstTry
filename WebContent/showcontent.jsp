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
 <!-- 
 <div class="col-sm-1">
<button type="button" class="btn btn-warning btn-sm" id="exportFile.do"><i class="glyphicon glyphicon-file"/>导出文本文件</button>
</div>

<script type="text/javascript">
$("#exportFile.do").on('click',function(){
    window.open(contextPath+'/exportFile.json', '_blank');
});

</script>
- -->

<form action="queryContent.do" accept-charset="UTF-8"><%--servlet里面的方法 --%>
<table>

<tr>
<th colspan="6">${requestScope.diary.title }</th>
</tr>
<br><br>
<tr>
<td colspan="11" >${requestScope.diary.author }</td>
</tr>
<br><br>

<br><br>
<tr>
<td>${requestScope.diary.content }</td>
</tr>
<br><br>
</table>
<%--- 
<c:set var="id" value="${diary !=null ? diary.id:param.id}"></c:set>
<input type="hidden" name="id" value="${diary.id }"/>
--%>
<table>
<tr>
<th>留言人</th>
<th>留言</th>
<th>留言时间</th>
</tr>
<c:forEach items="${requestScope.comments }" var="comment" >
<tr>
<td>${comment.name }</td>
<td>${comment.comment }</td>
<td>${comment.time }</td>
</tr>
</c:forEach>
<br><br>

</table>

<table>
<br><br>
<tr>
<th>点赞数</th>
</tr>

<tr>
<td>${requestScope.likeCount }</td>
</tr>

<%-- 
<c:forEach items="${requestScope.likes }" var="like" >
<tr>
<td>${like.count }</td>
</tr>
</c:forEach>
--%>
</table>
<%--- 
<br>
<a href="addLike.do">点赞</a>
<br><br>
<a href="exportFile.do">导出文件</a>
--%>
</form>



<%--- 
<form action="showComment.do"> 
<c:set var="id" value="${diary !=null ? diary.id:param.id}"></c:set>
<input type="hidden" name="id" value="${diary.id }"/>
<table>
<tr>
<th>留言人</th>
<th>留言</th>
<th>留言时间</th>
</tr>
<c:forEach items="${requestScope.comments }" var="comment" >
<tr>
<td>${comment.name }</td>
<td>${comment.comment }</td>
<td>${comment.time }</td>
</tr>
</c:forEach>
<br><br>
</table>
</form>
--%>
</body>
</html>