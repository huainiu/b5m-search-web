function _search(){
	var key = $("#search-text").val();
	ResultPage.toJump(_basePath + "s/Search?key=" + key);
}
$(function(){
	$("#search-text").keydown(function(e){
        if(e.keyCode == 13){ // enter 键
        	_search();
        }
	});
});
function ResultPage(opt) {
	if(!opt){ return;}
	this.$startPrice = opt.$startPrice || $("#startPrice");
	this.$endPrice = opt.$endPrice || $("#endPrice");
	//价格按钮点击
	this.$innerSearchBtn = opt.$innerSearchBtn || $("#inner-search-btn");
	//取消过滤按钮
	this.$cancelFilter = opt.$cancelFilter || $("#cancel-filter");
	//属性多选按钮
	this.$multSelBtn = opt.$multSelBtn || $("#btn-sure");
	this.currentUrl = opt.currentUrl;
};
ResultPage.prototype.init = function(){
	var _this = this;
	_this.$startPrice.keyup(function() {
		_this.validatePrice(this);
	}).keydown(function(e){
		if (e && e.keyCode == 13) { // enter 键
			_this.searchByPrice();
		}
	});
	_this.$endPrice.keyup(function() {
		_this.validatePrice(this);
	}).keydown(function(e){
		if (e && e.keyCode == 13) { // enter 键
			_this.searchByPrice();
		}
	});
	_this.$innerSearchBtn.click(function(){
		_this.searchByPrice();
	});
	_this.$cancelFilter.click(function(){
		var paramIndex = _this.currentUrl.indexOf("?");
		var url = _this.currentUrl.substring(0, paramIndex) + "?";
		var urlParams = _this.currentUrl.substring(paramIndex + 1);
		var urlParamArray = urlParams.split("&");
		for(var i = 0; i < urlParamArray.length; i++){
			var urlParam = urlParamArray[i];
			var urlParamIndex = urlParam.indexOf("=");
			var key = urlParam.substring(0, urlParamIndex);
			if(key == "sprice" || key == "eprice" || key == "attrs"){
				continue;
			}
			var value = urlParam.substring(urlParamIndex + 1, urlParam.length);
			url = url + key + "=" + value;
			if(i != urlParamArray.length - 1){
				url = url + "&";
			}
		}
		var lastChar = url.substring(url.length - 1, url.length);
		if(lastChar == '?' || lastChar == '&'){
			url = url.substring(0, url.length - 1);
		}
		$(this).attr('href', url);
	});
};
ResultPage.prototype.validatePrice = function(obj){
	var temp = obj.value;
	var reg = /^[\d]+(\.[\d]*)?$/;
	// var reg = /^[\d]+$/;
	var max = 9999999;
	if (!reg.test(temp)) {
		if (obj.value)
			obj.value = origValue;
	}
	origValue = obj.value;
	if (parseInt(temp, '10') > parseInt(max, '10')) {
		obj.value = max;
	}
};
ResultPage.prototype.searchByPrice = function(){
	ResultPage.toJump(this.addPriceParamUrl());
};
ResultPage.prototype.addPriceParamUrl = function(){
	var sprice = this.$startPrice.val();
	var eprice = this.$endPrice.val();
	var paramIndex =  this.currentUrl.indexOf("?");
	if(!sprice && !eprice) return this.currentUrl;
	if(paramIndex < 0){
		if(sprice && eprice) return this.currentUrl + "?sprice=" + sprice + "&eprice=" + eprice;
		if(sprice) return this.currentUrl + "?sprice=" + sprice;
		if(eprice) return this.currentUrl + "?eprice=" + eprice;
		return this.currentUrl;
	}else{
		var url = this.getUrl(function(key, value, _url){
			if(key != "sprice" && key != "eprice"){
				_url = _url + key + "=" + value;
			}else if(key == "sprice" && sprice){
				_url = _url + "sprice=" + sprice;
			}else if(key == "eprice" && eprice){
				_url = _url + "eprice=" + eprice;
			}
			return _url;
		});
		if(url.indexOf("sprice") < 0 && sprice){
			url = url + "&sprice=" + sprice;
		}
		if(url.indexOf("eprice") < 0 && eprice){
			url = url + "&eprice=" + eprice;
		}
		return url;
	}
};
ResultPage.prototype.getAttrUrl = function(attr){
	var url = this.getUrl(function(key, value, url){
		if(key == 'attrs'){
			if(value) value = value + "," + attr;
		}
		url = url + key + "=" + value;
		return url;
	});
	if(url.indexOf('attrs') > 0) return url;
	if(url.indexOf('?') < 0){
		return url + "?attrs=" + attr;
	};
	if(url.substring(url.length - 1, url.length) == '?'){
		return url + "attrs=" + attr;
	}
	return url + "&attrs=" + attr;
};
ResultPage.prototype.getUrl = function(callback){
	var paramIndex =  this.currentUrl.indexOf("?");
	var url = this.currentUrl.substring(0, paramIndex) + "?";
	var urlParams = this.currentUrl.substring(paramIndex + 1);
	var urlParamArray = urlParams.split("&");
	for(var i = 0; i < urlParamArray.length; i++){
		var urlParam = urlParamArray[i];
		var urlParamIndex = urlParam.indexOf("=");
		var key = urlParam.substring(0, urlParamIndex);
		var value = urlParam.substring(urlParamIndex + 1, urlParam.length);
		url = callback(key, value, url);
		if(i != urlParamArray.length - 1){
			url = url + "&";
		};
	}
	return url;
};
ResultPage.toJump = function(url){
	var el = document.createElement("a");
	document.body.appendChild(el);
	el.href=url;
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
};
function searchMulti(attrs) {
	ResultPage.toJump(resultPage.getAttrUrl(attrs));
}
ResultPage.prototype.showRecomand = function(opt){
	var container = opt.container;
	if(!opt.keywords){
		opt.maxcontaier.hide();
		return;
	}
	this.getRelKeywords(opt.keywords, function(relkeywords){
		if(!relkeywords){
			container.parent().parent().hide();
			return;
		}
		var url = _basePath + "s/relGoods.html?t=" + new Date().getTime();
		var data = {title : relkeywords};
		Ajax.post(url, data, function(result){
			var index = 0;
			$.each(result, function(i, item) {
				var $li = $('<li></li>');
				var href = item.Url;
				/*b5mcdn*/
				var content = '<a href="' + href + '" target="_blank">';
				    if(item.Picture.indexOf('b5mcdn') > 0){
				    	item.Picture = item.Picture + '/140x140';
				    }
				    content += '<img src="' + item.Picture + '" width="140" height="140" alt="' + item.Title + '"></a>'+
					           '<p><a href="' + href + '" target="_blank">' + item.Title + '</a></p><p>';
				if(item.isFreeDelivery == 1){
					content = content + '<span>免运费</span>';
				}
				content = content + '<i>￥' + item.Price + '</i></p>';
				if(index < 10){
					$li.append(content).appendTo(container);
				}
				index++;
			});
			if(index == 0){
				opt.maxcontaier.hide();
			}
		});
	});
	
};
ResultPage.prototype.getRelKeywords = function(keywords, callback){
//	http://search.b5m.com/allAutoFill.htm?keyWord=ip
	var url = "http://s.b5m.com/allAutoFill.htm";
	Ajax.jsonpPost(url, {keyWord : keywords}, function(result){
		var relkeywordsList = result.b5mo;
		var relkeywords = null;
		if(relkeywordsList && relkeywordsList.length > 0){
			relkeywords = relkeywordsList[0].value;
			//如果相同 则取第二个
			if(relkeywords == keywords && relkeywordsList.length > 1){
				relkeywords = relkeywordsList[1].value;
			}
		}
		callback(relkeywords);
	});
};
var Ajax = {};
Ajax.post = function(url, data, callback){
	$.ajax({
		type : "POST",
		url : url,
		data : data,
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
		jsonp:'jsoncallback',
		success : function(result) {
			callback(result);
		}});
};