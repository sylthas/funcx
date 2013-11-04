<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="Fx.min.js"></script>
<script type="text/javascript">
	function del(id) {
		$.get($.bp() + '/del/' + id, function(data) {
			alert(data.msg);
		    if (data && data.success) {
		    	$('#blog-id-' + id).fadeOut();
		    }
		}, 'json');
	}
</script>
</head>
<body>
	<a href="post.jsp">发布</a>
	<hr />
	<c:if test="${not empty list}">
		<c:forEach items="${list }" var="blog">
			<div id="blog-id-${blog.id}">
				编号:${blog.id } | <a href="javascript:del('${blog.id }')">删除</a> <br />
				内容:${blog.content }<br /> 日期:${blog.createdate }
				<hr />
			</div>
		</c:forEach>
	</c:if>
	<c:if test="${empty list}">
    对不起,没有内容....
</c:if>
</body>
</html>