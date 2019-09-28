<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">

window.onload=function(){
	document.getElementsByTagName("a")[0].onclick=function(){
		var request=new XMLHttpRequest();
		var url=this.href;
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
</head>
<body>
<a href="hello.txt">hello</a>
</body>
</html>