//初始化执行任务
$(document).ready(function(){
	//监听页面大小改变
	$(window).bind("resize", resizeWindow);
	function resizeWindow(e) {
		try{
			$("iframe")[0].contentWindow.reloadPage();
		}catch(e){}
	}
});
//初始化表单
function initSearchForm(){
	$("#pageNo").val(0);
	return true;
}
//初始化下拉框的值 默认存储到data属性
function initSelectValue(flag) {
	$("select").each(function() {
		var data = $(this).attr("data");
		if (data != "") {
			data = data.split(",");
			$(this).val(data);
		}
	});
	if(flag){
		$('select').selected();
	}
}
//过滤特殊字符
function filterCharacter(s) {
    var pattern = new RegExp("[` ~!@#$^&*()=|{}':;'\\[\\].<>/?~！@#￥……&*（）&mdash;—|{}【】‘；：”“'。，、？]")
        var rs = "";
    for (var i = 0; i < s.length; i++) {
        rs = rs + s.substr(i, 1).replace(pattern, '');
    }
    return rs;
}
//去掉所有的html标记
function delHtmlTag(html){
	return html.replace(/<[^>]+>/g,"");
}
//询问对话框
function confirm(msg, href) {
	layer.confirm(msg, {
		shade:0.01,
		offset: '30px',
		btn : [ '确定', '取消' ]
	}, function() {
		window.location.href=href;
	});
	return false;
}
//弹出提示
function alert(msg){
	layer.alert(msg);
}
//弹出消息提示
function showMsg(msg){
	top.layer.msg(msg,{offset: '55px',anim: 6});
}
//打开全屏框 是否显示顶部和底部
function openModel(title,url,hideTop,hideBar){
	if(hideTop){
		$(parent.document).find(".iutils-header").hide();
	}
	if(hideBar){
		$(parent.document).find(".iutils-navbar").hide();
	}
	var index = layer.open({
		type: 2,
		move: false,
		title: title,
		content: url,
		cancel: function(index){
			if(hideTop){
				$(parent.document).find(".iutils-header").show();
			}
			if(hideBar){
				$(parent.document).find(".iutils-navbar").show();
			}
			layer.close(index);
		}
	});
	layer.full(index);
}
//关闭弹出框
function closeModel(isRefresh,showTop,showBar){
	if(showTop){
		$(parent.parent.document).find(".iutils-header").show();
	}
	if(showBar){
		$(parent.parent.document).find(".iutils-navbar").show();
	}
	var index = parent.layer.getFrameIndex(window.name);
	if(isRefresh){
		//刷新列表页
		$(parent.document).find("#searchForm").submit();
	}
	parent.layer.close(index);
}
//重新加载页面
function reloadPage(){
	var index = parent.layer.getFrameIndex(window.name);
	parent.layer.full(index)
}