<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false"%>
<!doctype html>
<html lang="en">
<head>
	<title>exe</title>
	<style type="text/css">
	input:FOCUS {border:0px;border-bottom:solid 1px gray;width:500px;height:13px;}
	#show-content ul li{list-style-type: none;float: left;padding: 5px 10px}
	</style>
	<script src="http://y.b5mcdn.com/scripts/common/jquery-1.9.1.min.js"></script>
	<script type="text/javascript">
	function ExePage(){
		this.url = "${serverPath}/s/exe.html?token=${token}";
	};
	ExePage.prototype.exe = function(cmd){
		Ajax.post(this.url, {cmd:cmd,path:"${path}"}, function(result){
			$("#show-content").append("$: ${path}  " + cmd + "<br/>");
			if(result.code == -1){
				$("#show-content").append(result.message);
			}else{
				var $ul = $("<ul/>");
				for(var i = 0; i < result.message.length; i++){
					$ul.append("<li>"+result.message[i]+"</li>");
				}
				$("#show-content").append($ul);
				$("#show-content").append("<div style='clear:both'></div>");
			}
			$("#cmdId").val("");
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
	var exePage = new ExePage();
	function exeCmd(){
		var cmd = $("#cmdId").val();
		exePage.exe(cmd);
	}
	</script>
</head>
<body>
	$: ${path} <input style="border:0px;border-bottom:solid 1px gray;width:500px;height:13px;" id="cmdId" onchange="exeCmd()">
    <br/><br/>
	<div id="show-content"></div>
</body>
</html>