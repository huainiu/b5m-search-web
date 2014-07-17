<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page session="false"%>
<h3 class="s-icon s-all">
	<c:choose>
	    <c:when test="${orignKeyword != '*' && categoryLayer > 0 && categoryList.link.url != ''}">
	    	<a href="${serverPath}/s/Search?key=${keyword}" data-attr='1003' style='color:#000'>所有分类</a>
	    </c:when>
	    <c:otherwise>所有分类</c:otherwise>
    </c:choose>
</h3>

<c:set var="index" value="0"></c:set>
<c:forEach items="${categoryList.linkTree}" var="category" varStatus="stat1">
	<c:forEach items="${category.linkTree}" var="groupTree" varStatus="stat2">
		<c:set var="index" value="${index + 1}"></c:set>
		<c:set var="clicked" value="${groupTree.link.clicked}"></c:set>
		<c:choose>
			<c:when test="${index == 1}">
				<dl class="m-dl cur">
					<dt class=${fn:length(groupTree.linkTree)==0 ? "no-bg" : "" }><a class=${(categoryLayer == 2) ? "\"title cur\"" : "title"} href="${groupTree.link.url}">${groupTree.link.text}</a></dt>
					<dd>
						<c:forEach items="${groupTree.linkTree}" var="tree2">
							<ul class="m-ul">
								<li><a ${tree2.link.clicked ? "class=\"cur\" " : ""} href="${tree2.link.url}" data-attr='1003'>${tree2.link.text}</a></li>
							</ul>
						</c:forEach>
					</dd>
				</dl>
			</c:when>
			<c:when test="${index <= 5 && index >1}">
				<dl class=${clicked ? "\"m-dl cur\""  : "m-dl"}>
					<dt class=${fn:length(groupTree.linkTree)==0 ? "no-bg" : "" }><a class="title" href="${groupTree.link.url}">${groupTree.link.text}</a></dt>
					<dd>
						<c:forEach items="${groupTree.linkTree}" var="tree2">
							<ul class="m-ul">
								<li><a ${tree2.link.clicked ? "class=\"cur\" " : ""} href="${tree2.link.url}" data-attr='1003'>${tree2.link.text}</a></li>
							</ul>
						</c:forEach>
					</dd>
				</dl>
			</c:when>
			<c:otherwise>
				<dl class="m-dl" style="display: none;">
					<dt class=${fn:length(groupTree.linkTree)==0 ? "no-bg" : "" }><a class="title" href="${groupTree.link.url}">${groupTree.link.text}</a></dt>
					<dd>
						<c:forEach items="${groupTree.linkTree}" var="tree2">
							<ul class="m-ul">
								<li><a ${tree2.link.clicked ? "class=\"cur\" " : ""} href="${tree2.link.url}" data-attr='1003'>${tree2.link.text}</a></li>
							</ul>
						</c:forEach>
					</dd>
				</dl>
			</c:otherwise>
		</c:choose>
	</c:forEach>
</c:forEach>
<div class="more-category">
	<strong class="open-cate">查看更多分类</strong>
	<strong class="close-cate">收起更多分类</strong>
</div>