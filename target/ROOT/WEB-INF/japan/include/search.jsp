<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@page session="false"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="top-hdbanner"></div>
<div class="tpbar"></div>
<div class="header">
    <div class="wp cfx">
        <h1 class="header-logo fl">
        	<a title="日本馆" href="/">
        		<img src="http://y.b5mcdn.com/images/logos/japan.png" alt="日本馆">
        	</a>
        </h1>
        <div class="logo-banner">
            <a data-mps="21045" href="http://www.b5m.com/qwdh.html?mps=____.1001.0.21045.0"><img src="http://cdn.bang5mai.com/upload/web/cmsphp//link/201408/d80e6b5d0e0117202633.jpg" alt="" width="80" height="80"></a>
        </div>
        <div class="header-tools-search" style="margin:25px 0px 0px 40px">
            <form action="${serverPath}/s/Search" onsubmit="if(document.getElementById('scbar_txt').value=='')return false;form_submit()" autocomplete="off" method="get" name="scbar_form" id="scbar_form">
                <input type="hidden" id="keytext" name="key">
                <span class="header-search-content">
                    <input data-attr="1005" type="text" name="key" x-webkit-grammar="builtin:search" x-webkit-speech="" autocomplete="off" id="scbar_txt" placeholder="快速搜索全网最新商品与资讯" value="${keyword}" class="header-search-key J_autofill af-input act">
                    <input type="submit" class="header-rearch-submit" onclick="if(document.getElementById('scbar_txt').value!='')setTimeout(form_submit(this),0);" value="搜全网">
                </span>
            </form>
            <script type="text/javascript">
                function form_submit() {
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
                        document.getElementById("keytext").value = value;
                        if (/msie/i.test(navigator.userAgent)){ //ie brower
                            document.scbar_form.submit();
                        }else{
                            document.scbar_form.submit();
                        }
                    }
                }
            </script>
        </div>
        <!-- country html -->
        <div class="top-hot-key"></div>
        <!-- country html -->
    </div>
    <div class="header-nav-box">
        <div class="wp">
            <div class="header-nav-back"><a data-attr="1004" href="http://haiwai.b5m.com/" title="海外馆首页" class="icon"><u>海外馆首页</u></a><s class="header-nav-back-s2"></s><s></s></div>
            <ul id="menu">
                <li class="index cur"><a data-attr="1004" href="http://jp.b5m.com" target="_self">首页</a></li>

            </ul>
            <div class="nav-more r">
                <span class="nav-more-txt nav-icon icon-more">更多</span>
                <ul class="nav-more-list cf">
                    <li><a href="http://korea.b5m.com/" target="_blank" data-attr="1004" class="nav-icon icon-hgg">韩国馆</a></li>
                    <li><a href="http://usa.b5m.com" target="_blank" data-attr="1005" class="nav-icon  icon-mgg">美国馆</a></li>
                    <li><a href="http://jp.b5m.com" target="_blank" data-attr="1006" class="nav-icon  icon-rbg">日本馆</a></li>
                </ul>
            </div>
        </div>

    </div>
</div>