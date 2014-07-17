<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page session="false"%>
<!--商家筛选 s-->
<ul class="g-fil">
	<c:forEach items="${sourceLinks}" var="source" varStatus="status" begin="0" end="7">
		<c:set value="" var="sourceClass"/>
          	<c:if test="${source.clicked}">
          		<c:set value="cur" var="sourceClass"/>
          	</c:if>
          	<li><a data-attr='1006' href="${source.url}" class="${sourceClass}" title="${source.text}">${source.text}</a></li>
	</c:forEach>
</ul>
<c:if test="${fn:length(sourceLinks) > 8}">
	<div class="more-c more-category">
		<span class="more-c-title"><a href="javascript:void(0);" class="s-icon more-btn">更多商家 &gt;</a></span>
           <ul class="m-items cf merchant_ls mall-list">
        	<c:forEach items="${sourceLinks}" var="source" varStatus="status" begin="8" end="${fn:length(sourceLinks)}">
        		<c:set value="" var="sourceClass"/>
        		<c:if test="${source.clicked}">
            		<c:set value="cur" var="sourceClass"/>
            	</c:if>
	            <li><a data-attr='1006' href="${source.url}" class="${sourceClass}" title="${source.text}">${source.text}</a></li>
        	</c:forEach>
        </ul>			
	</div>
</c:if>
<!--商家筛选 e-->
