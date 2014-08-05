<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@page session="false"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="top-hdbanner"></div>
<div class="tpbar"></div>
<div class="header">
    <div class="wp cf">
        <h1 class="header-logo fl">
        	<a title="海外馆" href="http://haiwai.b5m.com">
        		<img src="http://staticcdn.b5m.com/static/images/logos/haiwai.png" alt="海外馆">
        	</a>
        </h1>
        <div class="logo-banner">
            <a data-mps="21045" href="http://www.b5m.com/qwdh.html?mps=____.1001.0.21045.0"><img src="http://cdn.bang5mai.com/upload/web/cmsphp//link/201408/d80e6b5d0e0117202633.jpg" alt="" width="80" height="80"></a>
        </div>
        <div class="header-tools-search" style="margin:25px 0px 0px 40px">
            <form action="${serverPath}/s/Search" onsubmit="if(document.getElementById('scbar_txt').value=='')return false;form_submit()" autocomplete="off" method="get" name="scbar_form" id="scbar_form">
                <span class="header-search-content">
                    <input data-attr="1005" name="key" type="text" x-webkit-grammar="builtin:search" x-webkit-speech="" autocomplete="off" id="scbar_txt" placeholder="快速搜索全网最新商品与资讯" value="${keyword}" class="header-search-key J_autofill af-input act">
                    <input type="submit" class="header-rearch-submit" onclick="if(document.getElementById('scbar_txt').value!='')setTimeout(form_submit(this),0);" value="搜全网">
                </span>
            </form>
        </div>
        <!-- country html -->
        <div class="top-hot-key on">
            <div class="key-cont cf">
                <strong class="iconfont">뀈</strong>
                <div>
                    <span style="display: block;">
                        <a target="_blank" href="#">比基尼</a><em>|</em> 
                        <a target="_blank" href="#">豆浆机</a>
                    </span>
                </div>
            </div>
            <div class="cf">
                <b class="btn-next"><i class="iconfont">뀐</i></b>
                <b class="btn-prev"><i class="iconfont">뀉</i></b>
            </div>
        </div>
        <!-- country html -->
    </div>
    <div class="header-nav-box">
        <div class="wp">
            <div class="header-nav-back"><a data-attr="1004" href="http://haiwai.b5m.com/" title="海外馆首页" class="icon"><u>海外馆首页</u></a><s class="header-nav-back-s2"></s><s></s></div>
            <ul id="menu">
                <li class="index cur"><a data-attr="1004" href="http://haiwai.b5m.com/" target="_self">首页</a></li>
                <li><a href="http://korea.b5m.com/" target="_blank" data-attr="1004">韩国馆</a></li>
                <li><a href="http://usa.b5m.com" target="_blank" data-attr="1005">美国馆</a></li>
                <li><a href="http://jp.b5m.com" target="_blank" data-attr="1006">日本馆</a></li>
                <li><a data-attr="1004" target="_blank" href="http://haiwai.b5m.com/zhuanti-all-1.html">专题</a></li>
            </ul>
        </div>

    </div>
</div>
