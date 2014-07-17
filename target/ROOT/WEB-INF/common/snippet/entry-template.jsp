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
                </div>
                <div class="prod-details-txt">
                    <h3><a href="{{showUrl DocId SubDocsCount}}" class="prod-details-title" target="_blank" title="{{Title}}">{{Title}}</a></h3>
                    <p> 
                        {{#if Score}}
                            <span class="star-wrap"><span class="star-out"><span class="star-in" style="width:{{Score}}%"></span></span></span>
                        {{else}}
                            <span class="star-wrap cl-5555">该商品暂无评分 </span>
                        {{/if}}

                        <span>销量（月）<span class="cl-eb7e">{{SalesAmount}}</span> 件</span>

                        {{#unless CommentSize}}
                            <span class="ml20">暂无评分</span>
                        {{else}}
                            <span class="ml20">评论（<a href="" class="cl-eb7e">{{CommentSize}}</a>）</span>
                        {{/unless}}
                    </p>
                    <p class="fw mt20">商品推荐：</p>

                    <ul class="h-list rec-prod-list cf"></ul>

                    <ul class="prod-detail-price h-list mt20 cf">
                        <li class="item-odd">
                            <p class="goods-mall cf">
                                商城：<span class="cl-eb7e">{{Source}}</span>

                                {{!按钮为‘帮我买’是才显示‘直接去购买’}}
                                {{#if isDaigou}}
                                    <a href="{{Url}}" class="cl-eb7e r" target="_blank">直接去购买</a>
                                {{/if}}
                            </p>
                            {{#if isDaigou}}
                                <a href="javascript:void(0);" class="btn-bevel btn-bevel-4">&yen;{{Price}}</a>
                                <a href="{{searchPath}}{{daigouSource.Url}}" class="btn-bevel btn-bevel-3" lp="1" data-attr="100803" docid="{{daigouSource.DOCID}}" target="_blank"><span>帮我买</span></a>
                            {{else}}
                                <a href="javascript:void(0);" class="btn-bevel btn-bevel-1">&yen;{{Price}}</a>
                                <a href="{{Url}}" class="btn-bevel btn-bevel-2" data-attr="100803" lp="0">直接去购买</a>
                            {{/if}}
                        </li>
                        <li class="item-even">
                            <div class="pro-malls l">
                                {{#if SubDocs}}
                                    {{#listSubDocs SubDocs}}{{/listSubDocs}}
                                {{/if}}
                                {{！运费非比价商品}}
                                {{#if carriage}}
                                    {{#listProdServer carriage}}{{/listProdServer}}
                                {{/if}}
                                {{#if SubDocs}}
                                    <p class="more-mall cf">
                                        <a href="{{showUrl DocId SubDocsCount}}" class="l cl-eb7e fw">查看更多商家</a>
                                    </p>
                                {{/if}}
                            </div>

                            {{#if tagList}}
                                {{#listTags tagList}}{{/listTags}}
                            {{/if}}

                        </li>
                    </ul>
                    <div class="prod-price-trend">
                    	{{!价格趋势图}}
                        <div class="icon-price-trend J_price_trend {{showPriceTrend isLowPrice trend}}"></div>
                        {{#if DetailDocId}}
                            <div class="prod-price-chart" id="chart_{{DetailDocId}}">
                                图标
                            </div>
                        {{else}}
                            <div class="prod-price-chart" id="chart_{{DocId}}">
                                图标
                            </div>
                        {{/if}}
                        {{!价格预测}}
                        <div class="prod-price-pred  J_forecastPrice"></div>
                    </div>
                </div>
            </div>
            <span class="pop-icon pop-arrow"></span><span class="pop-icon pop-close" data-closeid="{{DocId}}"></span>
        </div>
    </li>
</script>
<!-- handlebars template s -->