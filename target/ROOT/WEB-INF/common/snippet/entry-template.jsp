<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false"%>
<!-- handlebars template s -->
<script id="entry-template" type="text/x-handlebars-template">
    <li class="pop-ls cf" id="{{DocId}}_prod">
        <div class="prod-pop cf">
            <div class="prod-details cf">
                <div class="prod-details-pic l">
                    <div class="main-slider">
                        <div class="main-slider-pic">
                            <img src="{{Picture}}" alt="">
                        </div>
                        <div class="slider-bar cf mini-slider">
                            <ul class="cf">
                                {{#if OriginalPicture}}
                                    {{#listMiniPicFun OriginalPicture}}{{/listMiniPicFun}}
                                {{/if}}
                            </ul>
                            {{#if OriginalPicture}}
                                {{#isShowArrow OriginalPicture}}
                                    <a href="" class="slider-trigger slider-left arrow-left-disable"></a>
                                    <a href="" class="slider-trigger slider-right"></a>
                                {{/isShowArrow}}                                
                            {{/if}}
                        </div>
                    </div>
					<div class="share-opt l cf">
						<span class="l">分享给朋友:</span>
						<div class="bshare-custom icon-medium l b5m-share"></div>
					</div>
					<!--
					<div class="prod-remind">
						<span class="remind-me" id="J_count_down"><i></i>降价提醒我</span>
					</div>
					-->
					<!--
					<div class="sel-his">
						累计销量&nbsp;<span>{{SalesAmount}}</span>&nbsp;件&nbsp;&nbsp;|&nbsp;&nbsp;
						{{#unless CommentSize}}
                            暂无评分
                        {{else}}
                            评论(<span>{{CommentSize}}</span>)
                        {{/unless}}
					</div> 
					-->
                </div>
                <div class="prod-details-txt">
                    <h3><a href="{{showUrl DocId SubDocsCount}}" class="prod-details-title" target="_blank" title="{{Title}}">{{Title}}</a></h3>
					<div class="loading-tip" style="display:none">
						<img alt="" src="http://y.b5mcdn.com/images/search/5-121204194026.gif">
						<br>
						<span>商品信息正在玩命加载中…</span>
					</div>
                    <div class="prod-average-price">
						<p>全网均价：<span class="definite-price"><span>￥</span><font class="average-price" data="{{daigouSource.HighPrice}}">{{daigouSource.HighPrice}}</font></span></p>
						<a href="{{daigouSource.originUrl}}" target="_blank">直接购买&gt;</a>
					</div>
					<div class="prod-b5m-price">
						<div class="b5m-price l">
							<div class="b5m-price-low">帮我买价格：<span><i>¥</i><font class="total-product-price" data="{{daigouSource.Price}}">{{daigouSource.Price}}</font></span></div>
							<div>
								{{#genuineLogic daigouSource.Source daigouSource.isLowCompPrice}}{{/genuineLogic}}
							</div>
						</div>
						<div class="cut-price l">
							<div class="cut-price-num">帮您节省了：<span><i>¥</i><font class="remain-price">{{remainPrice}}</font></span></div>
							<!--
							<div>
								{{#priceTrendTypeShowFun trend}}{{/priceTrendTypeShowFun}}
							</div>
							-->
						</div>
					</div>
					<div class="prod-sku-info centrt-sku centrt-sku-error">
						<p class="info-tips" style="display:none"><em class="icon-warning-red"></em>请选择你要的商品信息</p>
						<dl class="">
							<dt>数量：</dt>
							<dd class="centre-details-quantity"> 
								<span class="btn-subtraction" data-cid="C_002" data-id="005">-</span><span class="input-box"><input type="text" class="p-text" value="1" name="quantity005" id="quantity005"></span><span class="btn-add" data-cid="C_002" data-id="005">+</span><font> 件</font>
							</dd>
						</dl>
					</div>
					<div class="prod-btns">
						<a id="J_count_down" class="btn btn-b5m-buy btn-red-two" href="http://cart.b5m.com/" target="_blank"><i></i>帮我买</a>
						<a class="btn btn-shop-cart btn-orange" href="javascript:void(0)"><i></i>加入购物车</a>
						<span class="add-cart-success" style="display:none"><a class="go-to-cart" href="http://cart.b5m.com/" target="_blank">去购物车结算</a><a class="go-shoping" href="">继续购物</a><i class="cart-close"></i></span>
						<a class="btn btn-shop-diamond" href="http://s.b5m.com/exchange/item.htm?docId={{daigouSource.DOCID}}" target="_blank">帮钻兑换
							<span class="what-is-bangzuan">
								兑换该商品需要<em class="duihuanbangzhuan">{{duihuanbangzhuan}}</em>帮钻<br>
								购买该商品可获得<em class="bangzhuan">{{bangzhuan}}</em>帮钻
							</span>
						</a>
						<p><a class="what-is-b5m" href="http://www.b5m.com/bwm" target="_blank">什么是帮5买？</a></p>
					</div>
                </div>
            </div>
            <span class="pop-icon pop-arrow"></span><span data-attr="100805" class="pop-icon pop-close" data-closeid="{{DocId}}"></span>
        </div>
		<div class="pl-ctn cf">
			<div class="tit">全网比价</div>
		    <div class="p-slider-bar cf">
				<a href="javascript:;" class="slider-trigger left disabled"><span></span></a>
				<div class="sc-ctn cf">
					<ul class="cf" id="recommand-source">
						
					</ul>
				</div>
				<a href="javascript:;" class="slider-trigger right"><span></span></a>
			</div>
		</div>
    </li>
</script>
<!-- handlebars template s -->