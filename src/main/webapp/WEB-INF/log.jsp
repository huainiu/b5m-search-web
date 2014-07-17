<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>日志查询页面</title>
</head>
<body>
	<c:if test="${error != '' && error != null}">
		${error}
	</c:if>
	<c:if test="${error == '' || error == null}">
	    <table border="1">
	      <caption>日志列表</caption>
	      <thead>
	      	<tr style="border: 1px solid;">
	      		<th>服务器:</th>
	      		<c:forEach var="ip" items="${ips}">
	      			<td>${ip}</td>
	      		</c:forEach>
	      	</tr>
	      </thead>
	      <c:forEach var="logName" items="${logNames}">
		      <tr>
		      	<th></th>
		         <c:forEach var="ip" items="${ips}">
	      			<td><a target="_blank" href="http://${ip}/sys/log.html?name=${logName}&token=${token}">${logName}</a></td>
	      		 </c:forEach>
		      </tr>
	      </c:forEach>
	   </table>
	</c:if>
</body>
</html>