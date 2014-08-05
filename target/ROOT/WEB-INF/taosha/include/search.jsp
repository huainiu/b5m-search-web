<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page session="false"%>
<div class="tpbar"></div>
<!-- top bar end   -->
<!-- search start -->
<div class="row search">
	<div class="container">
		<a href="http://tao.b5m.com" class="logo"> 
			<img src="http://img.b5m.com/image/T1MMdgBmYs1RCvBVdK" alt="">
		</a>
		<a href="http://www.b5m.com/qwdh.html" data-mps="21045" class="logo-banner">
        	<img width="75" height="68" alt="" src="http://img.b5m.com/image/T1FXhQBXxX1RCvBVdK/85x77">
      	</a>
		<div class="search-box gray-border">
			<i class="icon-search">搜索</i> 
			<input type="text" id="search-text" value="${orignKeyword}" placeholder="快速搜索  全网比价 请输入您想买的商品">
			<button onclick="_search()">搜索</button>
		</div>
		<div class="search-hd">
			<a href="http://tao.b5m.com/index.php?dispatch=companies.view&company_id=100024">
				<img src="http://om04.b5mcdn.com/image/T1KMJjB4hs1RCvBVdK" alt="">
			</a>
		</div>
	</div>
</div>
<!-- search end -->
<!-- nav start -->
<div class="row nav">
	<div class="container">
		<div class="grid_9">
			<ul class="nav-list">
				<li><a href="http://tao.b5m.com" class="cur">首页</a></li>
				<li><a href="http://tao.b5m.com/index.php?dispatch=pages.view&page_id=26" target="_blank">淘沙团</a></li>
				<li><a href="http://tao.b5m.com/index.php?dispatch=pages.view&page_id=28" target="_blank">品牌街</a></li>
			</ul>
		</div>
		<div class="grid_1">
	        <div class="my-haitao">
	          <div class="haitao-box"><h4>我的淘沙</h4></div>
	          <div class="haitao-menu">
	          <ul style="margin-top: -105px;">
	            <li class="no"><a href="http://tao.b5m.com/index.php?dispatch=orders.search">我的订单</a></li>
	            <li><a href="http://tao.b5m.com/index.php?dispatch=wishlist.view">我的收藏</a></li>
	            <li><a href="http://tao.b5m.com/index.php?dispatch=profiles.delivery_address">收货地址</a></li>
	          </ul>
	          </div>
	        </div>
      </div>
	</div>
</div>