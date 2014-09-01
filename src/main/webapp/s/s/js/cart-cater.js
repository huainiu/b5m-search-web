function CartCenter(opt){
	this.docId = opt.docId || '';
	this.url = opt.url || '';
	this.priceAvg = opt.priceAvg || '';
	this.ref = opt.ref || '';
	this.paramurl = opt.paramurl || '';//来自b5t那边传递过来的url
	this.subPrice = Number(opt.subPrice) || 0.5;//降价多少
	this.searchPath = opt.searchPath || "http://s.b5m.com";
	this.source = opt.source;
}
CartCenter.prototype.init = function(){
	$(".btn-red-two").attr("href", "javascript:void(0)");
	var _this = this;
	//显示规格
	this.showGuige();
	//点击减号
	$(".btn-subtraction").click(function(){
		if(!$(this).hasClass("p-reduceno")){
			var pval = $(".p-text").val();
			pval = Number(Number(pval)-Number(1));
			if(pval <= 1){
				pval = 1;
			}
			$(".p-text").val(pval);
			_this.modifyPrice(pval);
		}
	});
	//点击加号
	$(".btn-add").click(function(){
		var pval=$(".p-text").val();
		pval = Number(Number(pval) + Number(1));
		$(".p-text").val(pval);
		_this.modifyPrice(pval);
	});
	$(".p-text").keyup(function(){
		if(isNaN($(".p-text").val()) || $(".p-text").val()<=1){
			$(".p-text").val(1);
		}
		var pval=$(".p-text").val();
		pval = Number(pval);
		_this.modifyPrice(pval);
	});
	$(".centre-error-close").click(function(){
		_this.closeggts();
	});
	$(".cart-close").click(function(){
		$(".add-cart-success").hide();
	});
};
CartCenter.prototype.closeggts = function(){
	$(".centre-details-r").removeClass("error");
	$(".centre-error-msg").hide();
};
CartCenter.prototype.modifyPrice = function(pval){
	if(!pval){
		pval = $(".p-text").val();
		pval = Number(pval);
	}
	var orignPrice = $(".total-product-price").attr("data");
	var orignPriceAvg = $(".average-price").attr("data");
	var remainPrice = (parseFloat(orignPriceAvg) - parseFloat(orignPrice)) * pval;
	if(remainPrice <= 0) {
		remainPrice = this.subPrice * pval;
		if(orignPrice < 5){remainPrice = 0;}
		$(".average-price").text(new Number(new Number(orignPrice) + this.subPrice).toFixed(2));
	}else{
		$(".average-price").text(orignPriceAvg);
	}
	$(".remain-price").text(new Number(remainPrice).toFixed(2));
	var price = parseFloat(orignPrice) * pval;
	$(".chongzhika").text(new Number(parseFloat(price)/1000).toFixed(1));
	$(".total-product-price").text(new Number(price).toFixed(2));
	if(price < 0){
		$(".total-product-price").text('暂无报价');
	}
	price = price + 0.99;
	var num = parseInt(price);
	$(".bangzhuan").text(num);
	$(".duihuanbangzhuan").text(num * 100);
};
CartCenter.prototype.showGuige = function(){
	var _this = this;
	var data={"docId": _this.docId, "url": _this.url};
	if(window.location.host.indexOf("tao") == 0 || "淘沙商城" == _this.source){
		data.col = "taosha";
	}
	$.ajax({
		url:_this.searchPath + "/ontimesku/single.htm",
		data: data,
		dataType: 'jsonp',
		jsonp: 'jsonCallback',
		success:function(result){
			_this.displayGuige(result.val);
		},error:function(result){
			_this.displayGuige(result.val);
		}
	});
};
CartCenter.prototype.displayGuige = function(val){
	$(".btn-red-two").attr("href", "http://cart.b5m.com/").attr("target", "_blank");
	$(".centrt-sku").show();
	$(".prod-b5m-price").show();
	$(".prod-btns").show();
	$(".loading-tip").hide();
	this.clickBtn();
	if(!val.skuProps || val.skuProps.length < 1) {
		return;
	}
	var $count = $(".centre-details-quantity").prev();
	var html = '';
	for(var index = 0; index < val.skuProps.length; index++){
		var skuProp = val.skuProps[index];
		html = html + '<dt>' + skuProp.name + ':</dt><dd class="gg_prop">';
		for(var i = 0; i < skuProp.props.length; i++){
			html = html + '<a href=\"javascript:void(0)\" class="guige">' + skuProp.props[i] + '</a>';
		}
		html = html + "</dd>";
	}
	$count.before(html);
	if(val.lowestPrice){
		$(".total-product-price").attr("data", Number(val.lowestPrice - this.subPrice).toFixed(2));
		$(".product-price").text(Number(val.lowestPrice - this.subPrice).toFixed(2));
		this.modifyPrice();
	}
	this.guigeAddEvent(val);
};
CartCenter.prototype.guigeAddEvent = function(val){
	var _this = this;
	var $props = $(".gg_prop");
	$props.find("a").click(function(){
		var $this = $(this);
		if($this.attr("disable")) return;
		
		$this.siblings("a").each(function(){$(this).removeClass("checked");});
		
		if($this.hasClass("checked")){
			$this.removeClass("checked");
		}else{
			$this.addClass("checked");
		}
		var num = 0;
		for(var index = 0; index < $props.length; index++){
			if($($props[index]).find("a.checked").length > 0){
				num++;
			}
		}
		$props.find("a").each(function(){
			$(this).css("color","black").removeAttr("disable");
		});
		if(num == $props.length - 1){
			_this.changeDisplayGuige(val, $props, false);
			return;
		}
		if(num == $props.length){
			_this.changeDisplayGuige(val, $props, true);
			_this.closeggts();
		}
		$props.find("a").each(function(){
			if($(this).attr("disable")){
				$(this).removeClass("checked");
			}
		});
		var goodsSpec = _this.getGoodsSpec();
		var price = val.sku[goodsSpec];
		if(price){
			if(new Number(price) > 5){
				price = new Number(price) - _this.subPrice;
			}
			$(".total-product-price").attr("data", price);
			$(".product-price").text(price);
			var pval = $(".p-text").val();
			pval = Number(pval);
			_this.modifyPrice(pval);
			_this.hidTip();
		}
	});
};
//规格选择提示
CartCenter.prototype.showTip = function(){
	$(".info-tips").show();
};
//规格选择隐藏提示
CartCenter.prototype.hidTip = function(){
	$(".info-tips").hide();
};
CartCenter.prototype.changeDisplayGuige = function(val, $props, islastChange){
	var _this = this;
	var goodsSpec = "";
	var tempGoodsSpecs = null;
	var $noCheckProps = null;
	for(var index = 0; index < $props.length; index++){
		var $prop = $($props[index]);
		var $checked = $prop.find("a.checked");
		if((islastChange && index < $props.length - 1) || (!islastChange && $checked.length > 0)){
			if(!tempGoodsSpecs){
				goodsSpec = goodsSpec + _this.oneSpec($checked);
			}else{
				for(var i = 0; i < tempGoodsSpecs.length; i++){
					tempGoodsSpecs[i] = tempGoodsSpecs[i] +  _this.oneSpec($checked);
				}
			}
		}else{
			$nosel = $prop;
			$noCheckProps = $prop.find("a");
			tempGoodsSpecs = new Array($noCheckProps.length);
			var _num = 0;
			$noCheckProps.each(function(){
				tempGoodsSpecs[_num] = goodsSpec + _this.oneSpec($(this));
				_num++;
			});
		}
	}
	for(var index = 0; index < tempGoodsSpecs.length; index++){
		tempGoodsSpecs[index] = _this.removeLastSemicolon(tempGoodsSpecs[index]);
		if(!val.sku[tempGoodsSpecs[index]]){
			$($noCheckProps[index]).css("color","#ccc").attr("disable", true);
		}
	}
};

CartCenter.prototype.oneSpec = function($a){
	var propName = $a.parent().prev().text();
	var propValue = $a.text();
	return propName + propValue + ";";
};

CartCenter.prototype.haveSelGuige = function(){
	var price =  $(".total-product-price").val();
	if(isNaN(price)) return false;
	var $props = $(".gg_prop");
	for(var index = 0; index < $props.length; index++){
		if($($props[index]).find("a.checked").length<=0){
			//$(".centre-details-r").addClass("error");
			//$(".centre-error-msg").show();
			this.showTip();
			return false;
		}
	}
	this.hidTip();
	return true;
};
CartCenter.prototype.clickBtn = function(){
	var _this = this;
	$(".btn-orange").click(function(){
		if(!_this.haveSelGuige()) return;
		_this.addCart(function(json){
			try{b5m.ui.setProductNum();}catch(e){}
			$(".add-cart-success").show();
		});
	});
	$(".btn-red-two").click(function(){
		var _thisClick = $(this);
		if(!_this.haveSelGuige()) return false;
		_this.addCart(function(json){
			if(json.code != 1){
				alert(json.msg);
			}else{
				_thisClick.attr("href", json.val);
			}
		});
		try{b5m.ui.setProductNum();}catch(e){}
		return true;
	});
};
//CartCenter.prototype.clickBtn = function(){
//	var _this = this;
//	$(".btn-orange").click(function(){
//		if(!_this.haveSelGuige()) return;
//		_this.addCart(function(json){
//			try{b5m.ui.setProductNum();}catch(e){}
//			$(".add-cart-success").show();
//		});
//	});
//	$(".btn-red-two").click(function(){
//		if(!_this.haveSelGuige()) return false;
//		_this.addCart(function(json){
//			if(json.code != 1){
//				alert(json.msg);
//			}else{
//				$(document.body).append('<form action="'+json.val+'" class="rdurl" target="_blank"></form>');
//				$('.rdurl').submit(); 
//			}
//			return false;
//		},1);
//		try{b5m.ui.setProductNum();}catch(e){}
//		return true;
//	});
//};
var Ajax = {};
Ajax.post = function(url, data, callback){
	$.ajax({
		type : "POST",
		url : url,
		data : data,
		async: false,
		success : function(result) {
			callback(result);
		}});
};
Ajax.jsonpPost = function(url, data, callback){
	$.ajax({
		url: url,
		async: false,
		dataType: 'jsonp',
		data: data,
		jsonp:'jsonCallback',
		success : function(result) {
			callback(result);
		}});
};
CartCenter.prototype.addCart = function(call){
	var _this = this;
	var _docId = _this.docId;
	var _priceAvg = _this.priceAvg;
	var _goodsSpec = _this.getGoodsSpec();
	var pval = $("#quantity005").val();
	var url = this.searchPath + "/daigoucart/add.htm";
	var data = { docId : _docId,
			     priceAvg : _priceAvg,
			     goodsSpec : _goodsSpec,
			     count : pval,
			     ref: _this.ref,
			     url: _this.paramurl,
			     col: _this.getCol()
		       };
	if(window.location.host.indexOf('search.') == 0 || window.location.host.indexOf('s.') == 0){
		Ajax.post(url, data, call);
		return;
	}
	Ajax.jsonpPost(url, data, call);
	/*$.ajax({
		type : "post",
		url : this.searchPath + "/daigoucart/add.htm",
		async: false,
		data : {
			docId : _docId,
			priceAvg : _priceAvg,
			goodsSpec : _goodsSpec,
			count : pval,
			ref: _this.ref,
			url: _this.paramurl,
			col: _this.getCol()
		},success : function(json) {
			call(json);
		}
	});*/
};
CartCenter.prototype.getCol = function(){
	var location = window.location;
	if(!location.port || location.port == 80){
		var host = location.hostname;
		if(host.indexOf('haiwai') >= 0 || host.indexOf('usa') >= 0 || host.indexOf('korea') >= 0) return 'haiwaip';
		if(host.indexOf('jp') >= 0) return 'japan';
		if(host.indexOf('tao') >= 0) return 'taosha';
		return '';
	}
	if(location.href.indexOf('haiwai/s') >= 0 || location.href.indexOf('usa/s') >= 0 || location.href.indexOf('korea/s') >= 0) return 'haiwaip';
	if(location.href.indexOf('jp/s') >= 0) return 'japan';
	if(location.href.indexOf('tao/s') >= 0) return 'taosha';
	return '';
};
CartCenter.prototype.getGoodsSpec = function(){
	var goosSpec = "";
	$(".gg_prop .checked").each(function(){
		var propName = $(this).parent().prev().text();
		var propValue = $(this).text();
		goosSpec = goosSpec + propName + propValue + ";";
	});
	return this.removeLastSemicolon(goosSpec);
};
CartCenter.prototype.removeLastSemicolon = function(goosSpec){
	if(goosSpec.length > 0 && goosSpec.charAt(goosSpec.length - 1) == ';'){
		goosSpec = goosSpec.substring(0, goosSpec.length - 1);
	}
	return goosSpec;
};
function jumpTo(url){
	var el = document.createElement("a");
	document.body.appendChild(el);
	el.href=url;
	el.target = "_blank";
	if(el.click) {
        el.click();
    }else{//safari浏览器click事件处理
        try{
            var evt = document.createEvent('Event');
            evt.initEvent('click',true,true);
            el.dispatchEvent(evt);
        }catch(e){//alert(e)
        	window.localhost.href = url;
        };       
    }
}