<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" pageEncoding="UTF-8"%><%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ page session="false"%>
<div class="row" id="mid-category">
    <div class="container">
      <div class="grid_10">
        <div class="total-list" id="J_totalList">
          <h4><a href="#nogo" class="btn-total-list">关闭</a>所有分类</h4>
          <div class="total-list-con">
          	  <c:set var="showCategory" value="false"/>
	          <c:set var="layer" value="${categoryLayer}"></c:set>
	          <c:choose>
	          	<c:when test="${layer == 0 || layer == 1}">
	          		<c:forEach items="${categoryList.linkTree}" var="category"><c:forEach items="${category.linkTree}" var="categoryLayer1"><a rel="nofollow" data-attr='1003' href="${categoryLayer1.link.url}">${categoryLayer1.link.text}</a></c:forEach></c:forEach>
	          		<c:set var="showCategory" value="true"/>
	          	</c:when>
	          	<c:when test="${layer == 2}">
					<c:forEach items="${categoryList.linkTree}" var="category" >
						<c:forEach items="${category.linkTree}" var="categoryLayer1">
							<c:if test="${categoryLayer1.link.clicked}">
								<c:forEach items="${categoryLayer1.linkTree}" var="categoryLayer2">
									<a data-attr='1003' rel="nofollow" href="${categoryLayer2.link.url}">${categoryLayer2.link.text}</a>
									<c:set var="showCategory" value="true"/>
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
									<a data-attr='1003' rel="nofollow" href="${categoryLayer2.link.url}">${categoryLayer2.link.text}</a>
									<c:set var="showCategory" value="true"/>
								</c:forEach>
							</c:if>
						</c:forEach>
					</c:forEach>
				</c:when>
			 </c:choose>
          </div>
        </div>
      </div>
    </div>
</div>
<script type="text/javascript">if('${showCategory}' == 'false'){document.getElementById("mid-category").style.display = "none"; }</script>
