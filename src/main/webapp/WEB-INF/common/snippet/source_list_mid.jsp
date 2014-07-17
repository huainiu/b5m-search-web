<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page session="false"%>
<!--商家筛选 s-->
<c:if test="${fn:length(sourceLinks)>0}">
	<div class="goods-filter">
		<h3 class="s-icon goods-f">商家筛选</h3>
		<ul class="g-fil">
			<c:forEach items="${sourceLinks}" var="source" varStatus="status" begin="0" end="7">
				<c:set value="" var="sourceClass"/>
				<c:if test="${source.clicked}">
					<c:set value="cur" var="sourceClass"/>
				</c:if>
           		<li><a rel="nofollow" data-attr='1006' href="${source.url}" class="${sourceClass}" title="${source.text}">${source.text}</a></li>
            </c:forEach>
		</ul>
		<a class="show-more" href="javascript:void(0);"><span>展开</span></a>
	</div>
</c:if>
<!--商家筛选 e-->
