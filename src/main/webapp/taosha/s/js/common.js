ResultPage.prototype.recommendProduct = function(opt, call){
	var count = opt.count || 8;
	$.ajax({
		type : "get",
		async : false,
		url : opt.url + "?count="+count+"&col=" + opt.col,
		dataType : "jsonp",
		jsonp : 'jsonCallback',
		success : function(json) {
			call(json);
		}
	});
};