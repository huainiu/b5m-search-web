<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ page session="false"%>
<!-- 只有淘宝和天猫的 设置一级跳或者二级跳。 去购买 一级跳，浮层中显示 二级跳，标题,更多商家和去比较 都是去比较页面 -->
<div class="row">
    <div class="container">
      	<div class="grid_10">
      		<div class="item-list-box clearfix">
				<c:forEach items="${docResourceDtos}" varStatus="stat" var="docResourceDto">
					<c:set value="${docResourceDto.res}" var="res" />
					<c:url value="${res.Url}" var="detailUrl"></c:url>
					<div class="item-list-cell">
						<div class="box">
							<c:set value="${res.Picture}" var="picturePath" />
							<c:if test="${fn:indexOf(res.Picture, 'b5mcdn') > 0}">
								<c:set value="${res.Picture}/212x212" var="picturePath" />
							</c:if>
							<a href="${detailUrl}" class="fiximg" target="_blank">
								<img src="${picturePath}" alt="${res.Title}" width="212" height="212" onerror="this.src='http://tao.b5mcdn.com/static/img/common/noimg.png'"></a>
							<p><a href="${detailUrl}" target="_blank">${res.Title}</a></p>
							<div class="con">
								<span><c:if test="${res.isFreeDelivery == 1}">免运费</c:if></span><i><b>￥${res.Price}</b></i>
							</div>
							<div class="con">
								<i><a href="http://tao.b5m.com/index.php?dispatch=companies.view&company_id=${res.CompanyId}" target="_blank">${res.ShopName}</a></i>
								<c:if test="${res.ProductSource != null && res.ProductSource != ''}"><span>${res.ProductSource}发货</span></c:if>
							</div>
							<div class="total">已售出${res.SalesAmount}件</div>
						</div>
					</div>
				</c:forEach>
             </div>
		</div>
	</div>
</div>
