<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@page session="false"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="top-hdbanner"></div>
<div class="tpbar"></div>
<div class="header">
    <div class="wp cfx">
        <h1 class="header-logo fl">
        	<a title="韩国馆" href="http://korea.b5m.com">
        		<img src="http://staticcdn.b5m.com/static/images/logos/korea.png" alt="韩国馆">
        	</a>
        </h1>
        <div class="logo-banner">
            <a data-mps="21045" href="http://www.b5m.com/qwdh.html?mps=____.1001.0.21045.0"><img src="http://cdn.bang5mai.com/upload/web/cmsphp//link/201408/d80e6b5d0e0117202633.jpg" alt="" width="80" height="80"></a>
        </div>
        <div class="header-tools-search" style="margin:25px 0px 0px 40px">
            <form action="${serverPath}/s/Search" onsubmit="if(document.getElementById('scbar_txt').value=='')return false;form_submit()" autocomplete="off" method="post" name="scbar_form" id="scbar_form" class="search-form" >
                <span class="header-search-content"><input data-attr="1005" type="text" x-webkit-grammar="builtin:search" x-webkit-speech="" name="key"  autocomplete="off" id="scbar_txt" placeholder="快速搜索全网最新商品与资讯" value="${keyword}" class="header-search-key J_autofill af-input act"><input type="submit" class="header-rearch-submit"  onclick="if(document.getElementById('scbar_txt').value!='')setTimeout(form_submit(this),0);" value="搜全网"></span>
            </form>
            <input type="hidden" name="search_type" id="search_type" value="item" />
            <script type="text/javascript">
                function form_submit(e) {
                    var value = document.getElementById('scbar_txt').value;
                    value = value.replace(/(^\s*)|(\s*$)/g, "");

                    if (value != '') {
                        var keywords = value.split("/");
                        if (keywords.length > 1) {
                            value = '';
                            for (var i = 0; i < keywords.length; i++) {
                                if (keywords[i] != "" && keywords[i] != null) {
                                    value += keywords[i];
                                    if (i < (keywords.length-1)) {
                                        value += " ";
                                    }
                                } else {
                                    if (i < (keywords.length-1)) {
                                        value += " ";
                                    }
                                }
                            }
                        }
                        if (/msie/i.test(navigator.userAgent)){ //ie brower
                            setTimeout(function(){document.scbar_form.submit();},0);
                        }else{
                            var e = document.createEvent('MouseEvent');
                            e.initEvent('click', false, false);
                            setTimeout(function(){document.scbar_form.submit();},0);
                        }
                    }
                }
            </script>
        </div>
        <!-- country html -->
        <div class="top-hot-key" ></div>
        <!-- country html -->
    </div>
    <div class="korea-nav-box">
        <div class="wp cf">
            <div class="header-nav-back">
                <a class="zdm-icon" title="海外馆首页" href="http://haiwai.b5m.com" data-attr="1004"><u>海外馆首页</u></a>
            </div>
            <dl class="nav-item shouye">
                <dt class="cur"><a href="http://korea.b5m.com" target="_blank">首页</a></dt>
            </dl>
            <dl class="nav-item yule">
                <dt   ><a href="http://korea.b5m.com/clzx/21-1.html" target="_blank">娱乐</a></dt>
                <dd>
                    <p class="link"><a   href="http://korea.b5m.com/clzx/hyzx/22-1.html" target="_blank">韩娱新闻</a><a   href="http://korea.b5m.com/clzx/news/120-1.html"  target="_blank">韩剧News</a><a   href="http://korea.b5m.com/clzx/xlhj/24-1.html" target="_blank">闲聊韩剧</a><a   href="http://korea.b5m.com/clzx/hxsp/112-1.html" target="_blank">独家视频<i class="hot"></i></a></p>
                    <p class="link"><a   href="http://korea.b5m.com/clzx/hxzf/121-1.html" target="_blank">韩星专访</a><a   href="http://korea.b5m.com/clzx/mv/122-1.html"  target="_blank">韩国MV</a><a   href="http://korea.b5m.com/krstar.html" target="_blank">韩星资料</a><a   href="http://korea.b5m.com/clzx/ost/123-1.html" target="_blank">韩剧OST</a></p>
                </dd>
            </dl>
            <dl class="nav-item lvyou">
                <dt   ><a href="http://korea.b5m.com/lyxd/95-1.html" target="_blank">旅游</a></dt>
                <dd>
                    <p class="link"><a  href="http://korea.b5m.com/mstt/25-1.html" target="_blank">美食</a><a  href="http://korea.b5m.com/lygl/tjny/37-1.html"  target="_blank">景点</a><a  href="http://korea.b5m.com/gwwl/dngw/30-1.html" target="_blank">购物玩乐</a></p>
                    <p class="link"><a  href="http://korea.b5m.com/lygl/jdtj/35-1.html" target="_blank">酒店</a><a  href="http://korea.b5m.com/gwwl/jcwl/32-1.html"  target="_blank">酒吧</a><a  href="http://korea.b5m.com/lygl/jdtj/34-1.html" target="_blank">随韩剧游</a></p>
                </dd>
            </dl>
            <dl class="nav-item aimei">
                <dt ><a href="http://korea.b5m.com/am/126-1.html" target="_blank">爱美</a></dt>
                <dd>
                    <p class="link"><a  href="http://korea.b5m.com/am/mrcd/127-1.html" target="_blank">每日穿搭</a></p>
                    <p class="link"><a  href="http://korea.b5m.com/am/hgjp/130-1.html" target="_blank">韩国街拍</a></p>
                </dd>
            </dl>
            <dl class="nav-item shouye">
                <dt ><a href="http://korea.b5m.com/zxmr/38-1.html" target="_blank">医疗美容</a></dt>
            </dl>
            <dl class="nav-item shouye">
                <dt ><a href="http://korea.b5m.com/about.html" target="_blank">회사소개</a></dt>
            </dl>
            <div class="nav-more r">
                <span class="nav-more-txt nav-icon icon-more">更多</span>
                <ul class="nav-more-list cf">
                    <li><a class="nav-icon icon-hgg" data-attr="1004" target="_blank" href="http://korea.b5m.com">韩国馆</a></li>
                    <li><a class="nav-icon  icon-mgg" data-attr="1005" target="_blank" href="http://usa.b5m.com">美国馆</a></li>
                    <li><a href="http://jp.b5m.com" target="_blank" data-attr="1006" class="nav-icon  icon-rbg">日本馆</a></li>
                </ul>
            </div>
        </div>
    </div>
</div>