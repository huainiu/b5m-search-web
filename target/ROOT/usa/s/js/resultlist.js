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
	//初始化
	$('li.grid-ls').on('click', function() {
		_this.setHistory($(this));
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
	if(!opt.keyword){
		opt.maxcontaier.hide();
		return;
	}
	this.getRelKeywords(opt.keyword, function(relkeyword){
		if(!relkeyword){
			container.parent().parent().hide();
			return;
		}
		var url = _basePath + "s/relGoods.html?t=" + new Date().getTime();
		var data = {title : relkeyword};
		Ajax.post(url, data, function(result){
			var index = 0;
			$.each(result, function(i, shop) {
				var $li = $('<li class="grid-ls"></li>');
				var href = getDetailUrl(shop.DOCID);
				var content = '<div class="grid-mod">' + 
								'<div class="grid-in">' + 
									'<div class="pic-wrap">' + 
										'<div class="pic-mod">' + 
											'<a class="pic" target="_blank" href="'+href+'"><img src="' + shop.Picture + '/118x118" lazy-src="'+shop.Picture + '/118x118" alt=""></a>' + 
										'</div>' + 
									'</div>' + 
									'<div class="summary"><a target="_blank" href="'+href+'">'+shop.Title+'</a></div>' + 
									'<div class="price-mod">' + 
										'<strong class="price"><b>¥</b><span  class="cl-f00">'+shop.price+'</span></strong>' + 
									'</div>' + 
								'</div>' + 
							  '</div>';
				if(index < 10){
					$li.append(content).appendTo(container);
				}
				index++;
			});
			if(index == 0 && opt.maxcontaier){
				opt.maxcontaier.hide();
			}else{
				opt.maxcontaier.show();
			}
		});
	});
};
ResultPage.prototype.getDetailUrl = function(docId){
	return getDetailUrl(docId);
	/*if (!docId) {
        return '';
    }
    var host = 's.b5m.com';
    if(location.host.indexOf("haiwai") >= 0){
    	docId = '__' + docId;
    	host = 'haiwai.b5m.com';
    }
    if(location.host.indexOf("korea") >= 0){
    	docId = '__' + docId;
    	host = 'korea.b5m.com';
    }
    if(location.host.indexOf("usa") >= 0){
    	docId = '__' + docId;
    	host = 'usa.b5m.com';
    }
    if(location.port && location.port != 80){
    	var link = location.href;
    	if(link.indexOf("?") > 0){
    		link = link.substring(0, link.indexOf("?"));
    	}
    	if(link.indexOf("haiwai") >= 0){
    		docId = "__" + docId;
    		host = 'haiwai.b5m.com';
    	}
    }
    var url = 'http://' + host + '/item/' + docId + '.html';
    return url;*/
}
ResultPage.prototype.getRelKeywords = function(keyword, callback){
//	http://search.b5m.com/allAutoFill.htm?keyWord=ip
	var url = "http://s.b5m.com/allAutoFill.htm";
	Ajax.jsonpPost(url, {keyWord : keyword}, function(result){
		var relkeywordsList = result.b5mo;
		var relkeyword = null;
		if(relkeywordsList && relkeywordsList.length > 0){
			relkeyword = relkeywordsList[0].value;
			//如果相同 则取第二个
			if(relkeyword == keyword && relkeywordsList.length > 1){
				relkeyword = relkeywordsList[1].value;
			}
		}
		callback(relkeyword);
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
//显示广告
ResultPage.prototype.showAd = function(opt) {
	// 广告展示
	$.ajax({
		type : "get",
		async : false,
		url : "http://click.simba.taobao.b5m.com/s/data/" + opt.adSize + "_0_V.html",
		dataType : "jsonp",
		data : {
			keywords : opt.keywords,
			cid : opt.cookId
		},
		dataType : "jsonp",
		jsonp : 'jsoncallback',
		success : function(json) {
			var ads = json.val;
			if (ads.length == 0) {
				return;
			}
			$(".side.side-r").append("<div class='goods-recommend'><h3>商品推荐</h3><ul class='grid-view cf'></ul></div>");
			for (var i = 0; i < ads.length; i++) {
				var jumpUlr = ads[i].Url;
				var picUrl = ads[i].Picture;
				var title = ads[i].Title;
				var price = ads[i].Price;
				picUrl = picUrl.replace('img.b5m.com','tfs01.b5mcdn.com');
				if(picUrl.indexOf('b5mcdn.com') > 0){
					picUrl = picUrl + "/260x258";
				}
				var pichtml = "<div class='pic-wrap'><a class='pic' href='" + jumpUlr + "' target='_blank'><img alt='' src='" + picUrl + "'></a></div>";
				var pricehtml = "<div class='price'><strong><b>¥</b>" + price + "</strong></div>";
				var summaryhtml = "<div class='summary'><a href='" + jumpUlr + "' target='_blank'>" + title + "</a></div>";
				var adrshtml = "<img alt='' src='" + opt.recordUrl + "?cid=" + opt.cookId + "&aid=" + ads[i].aid + "&da=V" + (i + 1) + "&ad=108&dl=" + encodeURIComponent(window.location.href) + "&dstl=" + ads[i].durl + "&lt=8800&t="+new Date().getTime()+"&rp=1002&dd=" + ads[i].DOCID + "' style='display: none;'>";
				var html = "<li class='grid-ls'><div class='grid-mod'><div class='grid-in'>" + pichtml + summaryhtml + adrshtml + pricehtml + "<div class='grid-in'></div></div></li>";
				$(".side-r .grid-view").append(html);
			}
		},
		error : function() {
		}
	});
};

var his_length = 2;
ResultPage.prototype.setHistory = function(e) {
	var his = Cookies.get('his');
	var url = e.find('a.pic').attr('href');
	var pic = e.find("a.pic img").attr('src');
	var title = e.find(".summary a").attr('title');
	var price = e.find(".price strong").text().replace('¥', '');
	var val = url + "^" + pic + "^" + title + "^" + price;
	if (his && his != "undefined") {
		var array = his.split(";");
		var flag = true;
		for ( var i = 0; i < array.length; i++) {
			if (url.indexOf(array[i].split("^")[0]) >=0 || array[i].split("^")[0].indexOf(url) >=0) {
				flag = false;
				break;
			}
		}
		if (flag) {
			if (array.length == his_length) {
				array.shift();
			}
			array.push(val);
			his = array.join(";");
			Cookies.set('his', his);
			var len = $("#show_his").find('li.grid-ls').length;
			if (len >= his_length) {
				$("#show_his").find('li.grid-ls')[len-1].remove();
			}
			$("#show_his").prepend(ResultPage.addHistory(val.split("^")));
		}
	} else {
		Cookies.set('his', val);
		$("#show_his").html(ResultPage.addHistory(val.split("^")));
	}
	var hisList = $('.scroll-history .grid-view').find('li'),
	len = hisList.length ;
	if(len > 2){
	        $('.scroll-history').css('height',400);
	}
};
ResultPage.prototype.showHistory = function() {
	var history_info = Cookies.get("his");
	var content = "";
	if (history_info && history_info != "undefined") {
		history_arg = history_info.split(";");
		var length = history_arg.length;
		for (var i = length - 1; i >= 0; i--) {
			if (history_arg[i] && history_arg[i] != "undefined") {
				var wlink = history_arg[i].split("^");
					content += ResultPage.addHistory(wlink);
			}
		}
		$("#show_his").html(content);
	} else {
		$("#show_his").html("对不起，您没有任何浏览记录");
	}
};
ResultPage.addHistory = function(wlink) {
	wlink[1] = wlink[1].replace("img.b5m.com", "tfs01.b5mcdn.com");
	var dom = '<li class="grid-ls"><div class="grid-mod"><div class="grid-in"><div class="pic-wrap"> <div class="pic-mod">' + ' <a class="pic" href="' + wlink[0] + '" target="_blank"><img src="' + wlink[1] + '" lazy-src="' + wlink[1] + '" alt=""></a> </div> </div> <div class="summary">' + '<a href="' + wlink[0] + '">' + wlink[2] + '</a></div> <div class="price-mod"> <strong class="price">' + '<b>¥</b><span  class="cl-f00">' + wlink[3] + '</span></strong> </div> </div> </div> </li>';
	return dom;
}