<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@page session="false"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="top-hdbanner"></div>
<div class="tpbar"></div>
<div class="head">
	<div class="wp cf">
		<div class="logo l">
			<a href="http://www.b5m.com"><img src="http://y.b5mcdn.com/static/images/www/logo.png" alt="#"></a>
		</div>
		<div class="search-input">
            <form action="${serverPath}/s/Search" onsubmit="if(document.getElementById('scbar_txt').value=='')return false;form_submit()" autocomplete="off" method="get" name="scbar_form" id="scbar_form">
                <span class="header-search-content">
                    <input data-attr="1005" name="key" type="text" x-webkit-grammar="builtin:search" x-webkit-speech="" autocomplete="off" id="scbar_txt" placeholder="搜索从这里开始" value="${keyword}" class="header-search-key J_autofill af-input act">
                    <input type="submit" class="header-rearch-submit" onclick="if(document.getElementById('scbar_txt').value!='')setTimeout(form_submit(this),0);" value="帮5搜">
                </span>
            </form>
		</div>
		<div class="top-hot-key">
			<div id="hot-key-div">
			</div>
		</div>
	</div>
	<div class="nav-bar">
		<div class="wp cf">
			<ul class="nav-list">
				<li class="cur"><a href="http://www.b5m.com" target="_blank" data-attr='1004'>首页</a></li>
				<li><a href="http://zdm.b5m.com" target="_blank" data-attr='1004'>值得买</a></li>
				<li><a href="http://tejia.b5m.com" target="_blank" data-attr='1004'>淘特价</a></li>
				<li><a href="http://haiwai.b5m.com/" target="_blank" data-attr='1004'>海外馆</a> <i class="hot">热门</i></li>
				<li><a href="http://tuan.b5m.com" target="_blank" data-attr='1004'>帮团购</a></li>
				<li><a href="http://you.b5m.com" target="_blank" data-attr='1004'>帮5游</a></li>
				<li><a href="http://gzx.b5m.com/" target="_blank" data-attr='1004'>购真相</a></li>
				<li><a href="http://t.b5m.com/" target="_blank" data-attr='1004'>帮5淘</a></li>
				<li><a href="http://tao.b5m.com/" target="_blank" data-attr='1004'>淘沙</a><i class="new">new</i></li>
				<li><a href="http://bjr.b5m.com/" target="_blank" data-attr='1004'>帮金融</a></li>
			</ul>
			<div class="nav-more r" data-hover="nav-more-hover">
				<span class="nav-more-txt nav-icon icon-more">更多</span>
				<ul class="nav-more-list cf">
					<li><a href="http://korea.b5m.com/" target="_blank" class="nav-icon icon-hgg">韩国馆</a></li>
					<li><a href="http://www.b5m.com/forum.php" target="_blank" class="nav-icon icon-dis">讨论区</a></li>
					<li><a href="http://piao.b5m.com" target="_blank" class="nav-icon icon-piao">帮票务</a></li>
					<li><a href="http://daikuan.b5m.com" target="_blank" class="nav-icon icon-search">帮贷款</a></li>
				</ul>
			</div>
			<div class="r-nav">
				<a href="http://ucenter.b5m.com/dh" target="_blank" data-attr='1004'>兑换中心</a>
			</div>
		</div>
	</div>
</div>