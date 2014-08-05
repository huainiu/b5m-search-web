<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" pageEncoding="UTF-8"%><%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ page session="false"%>
<div class="s-icon s-all">
	<c:choose>
		<c:when test="${orignKeyword != '*' && categoryLayer > 0 && categoryList.link.url != ''}"><a href="${basePath}${keyword}.html" data-attr='1003' style='color:#000'>所有分类</a></c:when>
	    <c:otherwise>所有分类</c:otherwise>
    </c:choose>
</div>
<dl class="m-dl">
	<c:set var="layer" value="${categoryLayer}"></c:set>
	<c:choose>
		<c:when test="${layer == 0 || layer == 1}">
			<c:forEach items="${categoryList.linkTree}" var="category"><c:forEach items="${category.linkTree}" var="categoryLayer1"><dd><a rel="nofollow" class="s-icon" data-attr='1003' href="${categoryLayer1.link.url}">${categoryLayer1.link.text}</a></dd></c:forEach></c:forEach>
		</c:when>
		<c:when test="${layer == 2}">
			<c:forEach items="${categoryList.linkTree}" var="category" >
				<%-- ${fn:length(subResources) >= 1} --%>
				<c:forEach items="${category.linkTree}" var="categoryLayer1">
					<c:if test="${categoryLayer1.link.clicked && fn:length(categoryLayer1.linkTree) < 1}">
					   <c:forEach items="${category.linkTree}" var="categoryLayer1">
					       <dd><a class="s-icon ${categoryLayer1.link.clicked ? 'cur':''}" data-attr='1003' rel="nofollow" href="${categoryLayer1.link.url}">${categoryLayer1.link.text}</a></dd>
					   </c:forEach>
					</c:if>
					<c:if test="${categoryLayer1.link.clicked && fn:length(categoryLayer1.linkTree) >= 1}">
						<c:forEach items="${categoryLayer1.linkTree}" var="categoryLayer2">
							<dd><a class="s-icon" data-attr='1003' rel="nofollow" href="${categoryLayer2.link.url}">${categoryLayer2.link.text}</a></dd>
						</c:forEach>
					</c:if>
				</c:forEach>
			</c:forEach>
		</c:when>
		<c:when test="${layer == 3}">
			<c:forEach items="${categoryList.linkTree}" var="category" >
				<c:forEach items="${category.linkTree}" var="categoryLayer1">
					<c:if test="${categoryLayer1.link.clicked}">
						<c:forEach items="${categoryLayer1.linkTree}" var="categoryLayer2">
							<dd><a ${categoryLayer2.link.clicked ? "class=\"cur\" " : ""} data-attr='1003' rel="nofollow" href="${categoryLayer2.link.url}">${categoryLayer2.link.text}</a></dd>
						</c:forEach>
					</c:if>
				</c:forEach>
			</c:forEach>
		</c:when>
	</c:choose>
</dl>
<a class="show-more" href="javascript:void(0);"><span>展开</span></a>



