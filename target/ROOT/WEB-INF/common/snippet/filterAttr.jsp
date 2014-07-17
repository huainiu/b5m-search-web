<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page session="false"%>
<div class="filter-area mb20">
	<c:if test="${fn:length(filterList)>0}">                            
		<div class="filter-on">
			<dl>
				<dt class="filter-on-tit">您已经选择：</dt>
				<dd class="cf">
					<c:forEach items="${filterList}" var="flist"><a href="${flist.link.url}" class="filter-on-mod">${fn:split(flist.filterType, "_")[0]}：<em>${flist.link.text}</em><b>x</b></a></c:forEach>
				</dd>
				<a id="cancel-filter" class="cancel-all" href="javascript:void(0);">全部取消</a>
			</dl>
		</div>
	</c:if>
	<div class="filter-panel" id="J_filter"><c:set value="0" var="hiddenAttrNum"/>
		<c:forEach items="${attrsLinks}" var="attrLinks" varStatus="stat">
			<c:set value="${fn:split(attrLinks.name, '_')[0]}" var="itemName"/>
			<c:set var="hide" value="false"></c:set>
			<c:if test="${stat.index > 3}">
				<c:set var="hide" value="true"></c:set>
			</c:if>
			<dl class="filter-item" style="${hide ? 'display:none':''}"><c:set value="${hiddenAttrNum + 1}" var="hiddenAttrNum"/>
				<dt title="${attrLinks.name}">${fn:substring(itemName, 0, 4)}：</dt>
				<dd class="filter-lists cf">
					<c:forEach items="${attrLinks.attrList}" var="attr">
						<a href="${attr.url}"  data-attr="1002" title="${attr.text}">${attr.text}</a>
					</c:forEach>
				</dd>
				<div class="filter-act">
					<c:if test="${fn:length(attrLinks.attrList)>2}">
						<a href="javascript:void(0);" class="btn-multiple">多选</a>
					</c:if>
					<a href="javascript:void(0);" class="btn-sure" >确定</a>
					<a href="javascript:void(0);" class="btn-cancle">取消</a>
				</div>
				<a href="javascript:void(0);" class="show-more"><span>展开</span></a>
				<c:if test="${stat.index > 3 && stat.index < 6}"><c:choose><c:when test="${hiddenAttr == null}"><c:set var="hiddenAttr" value="${attrLinks.name}"/></c:when>
					<c:otherwise><c:set var="hiddenAttr" value="${hiddenAttr}、${attrLinks.name}"/></c:otherwise></c:choose></c:if>
			</dl>
	     </c:forEach>
	    <c:if test="${fn:length(priceList) >= 1}">
			<dl class="filter-item">
				<dt>价格：</dt>
				<dd class="filter-lists cf">
					<c:forEach items="${priceList}" var="price" varStatus="stat"><c:choose><c:when test="${price.text=='5000'}"><li><a href="${price.url}">${price.text}元以上</a></li></c:when><c:otherwise><a href="${price.url}">${price.text}元</a></c:otherwise></c:choose></c:forEach>
				</dd>
				<a href="javascript:void(0);" class="show-more"><span>展开</span></a>
			</dl>
		</c:if>
	</div>
	<div class="filter-more">
		<c:if test="${hiddenAttr != null && hiddenAttr != ''}">
			<a href="?ls=true" id="J_more" class="open">
				<span class="other-attr">其他属性<em>（${hiddenAttr} ${hiddenAttrNum > 9 ? '等' : ''}）</em></span>
				<span class="pack-up">收起</span>
			</a>
		</c:if>
	</div>
</div>
