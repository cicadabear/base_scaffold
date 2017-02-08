<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>${fnc:getConfig("iutils.name")}后台管理</title>
    <meta name="apple-mobile-web-app-title" content="${fnc:getConfig("iutils.name")}管理系统" />
    <link rel="stylesheet" href="${ctxStatic}/3rd-lib/jquery-ztree/3.5/css/zTreeStyle.css">
    <link rel="stylesheet" href="${ctxStatic}/3rd-lib/rightMenu/smartMenu.css">

    <%@ include file="include/head.jsp"%>

    <link rel="stylesheet" href="${ctxStatic}/custom/css/index.css">
    <link rel="stylesheet" href="${ctxStatic}/skin/blue/skin.css">
</head>
<body>
<!--[if lte IE 9]>
<p class="browsehappy">你正在使用<strong>过时</strong>的浏览器，平台暂不支持。 请 <a href="http://browsehappy.com/" target="_blank">升级浏览器</a>
  以获得更好的体验！</p>
<![endif]-->
<header class="am-topbar am-topbar-inverse admin-header">
  <div class="am-topbar-brand">
    <a href="${ctx}" style="margin-left: 15px;">管理中心<small><sup class="error">beta</sup></small></a>
  </div>
  <button class="am-topbar-btn am-topbar-toggle am-btn am-btn-sm am-btn-success am-show-sm-only" data-am-collapse="{target: '#topbar-collapse'}"><span class="am-sr-only">导航切换</span> <span class="am-icon-bars"></span></button>
  <div class="am-collapse am-topbar-collapse" id="topbar-collapse">
    <ul class="am-nav am-nav-pills am-topbar-nav am-topbar-right admin-header-list">
        <li class="am-dropdown" data-am-dropdown id="user-dropdown">
            <a class="am-dropdown-toggle" data-am-dropdown-toggle href="javascript:;">
                欢迎你， <shiro:principal/> <span class="am-icon-caret-down"></span>
            </a>
            <ul class="am-dropdown-content">
                <li><a href="javascript:;" onclick="openMeun('个人信息','${ctx}/user/userInfo')" target="maincontent"><span class="am-icon-user am-icon-fw"></span> 个人信息</a></li>
                <li class="am-divider"></li>
                <li><a href="javascript:;" onclick="openMeun('修改密码','${ctx}/user/changePassword')" target="maincontent"><span class="am-icon-key am-icon-fw"></span> 修改密码</a></li>
                <li class="am-divider"></li>
                <li><a href="javascript:;" onclick="openMeun('系统设置','${ctx}/user/configure')" target="maincontent"><span class="am-icon-cog am-icon-fw"></span> 系统设置</a></li>
            </ul>
        </li>
        <%--<li><a href="javascript:;"><span class="am-icon-envelope"></span>&nbsp;&nbsp;&nbsp;&nbsp;<span class="am-badge am-badge-success am-round" style="position:absolute;top:9px;left:25px;">5</span></a></li>--%>
        <%--<li><a href="javascript:;"><span class="am-icon-bell"></span>&nbsp;&nbsp;&nbsp;&nbsp;<span class="am-badge am-badge-warning am-round" style="position:absolute;top:9px;left:25px;">5</span></a></li>--%>
        <li><a href="${ctx}/logout" onclick="return confirm('确认要退出吗？', this.href)" class="am-text-danger"><span class="am-icon-sign-out"></span> 退出</a></li>
    </ul>
  </div>
</header>
<div class="am-cf admin-main" style="bottom: 40px;">
  <!-- sidebar start -->
  <div class="admin-sidebar am-offcanvas" id="admin-left" style="padding-left:10px;">
    <div class="am-offcanvas-bar admin-offcanvas-bar">
      	<ul id="tree" class="ztree showIcon"></ul>
    </div>
  </div>
  <div class="dislpayArrow"><a class="pngfix" href="javascript:;" onclick="displaynavbar(this)"></a></div>
  <!-- sidebar end -->
  <!-- content start -->
  <div class="admin-content" style="overflow:hidden;">
  	<div class="admin-content-body">
        <div class="am-tabs" data-am-tabs="{noSwipe: 1}" id="am-tabs">
            <ul class="am-tabs-nav am-nav am-nav-tabs">
                <li class="am-active"><a href="javascript:;">首页</a></li>
            </ul>
            <div class="am-tabs-bd">
                <div class="am-tab-panel am-active">
                    <iframe src="${ctx}/welcome" style="width:100%;height:97%;" frameborder="no"></iframe>
                </div>
            </div>
        </div>

  	</div>
  </div>
  <!-- content end -->
</div>
<footer data-am-widget="footer" class="am-footer am-footer-default" style="border-top: 1px solid #e5e5e5;margin-top:-40px;">
  <div class="am-footer-miscs " style="margin-top: -10px;">
      <p>${fnc:getConfig("iutils.copyright")}</p>
  </div>
</footer>
<script src="${ctxStatic}/3rd-lib/jquery-ztree/3.5/js/jquery.ztree.core-3.5-jk.js" type="text/javascript"></script>
<script src="${ctxStatic}/3rd-lib/rightMenu/jquery-smartMenu.js" type="text/javascript"></script>
<script>
    var tabCounter = 0;
    var $tab = $('#am-tabs');
    var $nav = $tab.find('.am-tabs-nav');
    var $bd = $tab.find('.am-tabs-bd');

    // 添加tab
    function addTab(title,url) {
        //判断当前页是否存在
        var isExist = false;
        $tab.find('iframe').each(function(index){
            if(url==$(this).attr("src")){
                isExist = true;
                $tab.tabs('open', index);
            }
        });
        if(!isExist){
            var nav = '<li><span class="am-icon-close"></span>' +
                    '<a href="javascript: void(0)">' + title + '</a></li>';
            var content = '<div class="am-tab-panel"><iframe src="'+url+'" style="width:100%;height:97%;" frameborder="no"></iframe></div>';
            $nav.append(nav);
            $bd.append(content);
            tabCounter++;
            $tab.tabs('refresh');
            $tab.tabs('open', tabCounter);
        }
    }

    // 移除标签页
    $nav.on('click', '.am-icon-close', function() {
        var $item = $(this).closest('li');
        var index = $nav.children('li').index($item);
        $item.remove();
        tabCounter--;
        $bd.find('.am-tab-panel').eq(index).remove();
        $tab.tabs('open', index > 0 ? index - 1 : index + 1);
        $tab.tabs('refresh');
    });

    var $selectTabItem,selectTabIndex;
    //tab增加右键事件
    $nav.on('mousedown','li',function(e){
        if(e.which==3){//鼠标右键
            $selectTabItem = $(this).closest('li');
            selectTabIndex = $nav.children('li').index($selectTabItem);
            if(selectTabIndex==0){
                return false;
            }
            //右键菜单配置
            var dataMenu = [[
                {text: "刷新页面",func: function() {
                    var iframe = $bd.find('.am-tab-panel').eq(selectTabIndex).find("iframe").eq(0);
                    iframe.attr('src', iframe.attr('src'));
                }},
                {text: "关闭全部",func: function() {
                    var iframes = $tab.find('iframe');
                    for(var i=iframes.length;i>0;i--){
                        $nav.find('li').eq(i).remove();
                        $bd.find('.am-tab-panel').eq(i).remove();
                    }
                    tabCounter=0;
                    $tab.tabs('open', 0);
                    $tab.tabs('refresh');
                }},
                {text: "关闭左边",func: function() {
                    var iframes = $tab.find('iframe');
                    for(var i=iframes.length;i>0;i--){
                        if(i<selectTabIndex){
                            tabCounter--;
                            $nav.find('li').eq(i).remove();
                            $bd.find('.am-tab-panel').eq(i).remove();
                            $tab.tabs('refresh');
                        }
                    }
                    $tab.tabs('open', 1);
                    $tab.tabs('refresh');
                }},
                {text: "关闭右边",func: function() {
                    var iframes = $tab.find('iframe');
                    for(var i=iframes.length;i>selectTabIndex;i--){
                        tabCounter--;
                        $nav.find('li').eq(i).remove();
                        $bd.find('.am-tab-panel').eq(i).remove();
                    }
                    $tab.tabs('open', selectTabIndex);
                    $tab.tabs('refresh');
                }}
            ]];
            $(this).smartMenu(dataMenu);
        }else{
            return false;
        }
    });

	//打开菜单
	function openMeun(title,url){
        addTab(title,url);
		$("#user-dropdown").dropdown('close');
	}
    //显示与隐藏左侧菜单
    function displaynavbar(obj){
        if($(obj).hasClass("open")){
            $(obj).removeClass("open");
            $(obj).parent().css("left","185px");
            $("#admin-left").show();
        }else{
            $(obj).addClass("open");
            $(obj).parent().css("left","0px");
            $("#admin-left").hide();
        }
    }
    $(function () {
    	 $(document).ready(function () {
    		var treeObj = $("#tree");
 			$.fn.zTree.init(treeObj, setting, zNodes);
 			zTree_Menu = $.fn.zTree.getZTreeObj("tree");
 			treeObj.hover(function () {
 				if (!treeObj.hasClass("showIcon")) {
 					treeObj.addClass("showIcon");
 				}
 			}, function() {
 				treeObj.removeClass("showIcon");
 			});
         });
        var setting = {
        		view: {
    				showLine: false,
    				selectedMulti: false,
    				dblClickExpand: false,
    				addDiyDom: addDiyDom
    			},
                data: {
                    simpleData: {
                        enable: true
                    }
                },
                callback : {
                	beforeClick: function(treeId, treeNode) {
        				var zTree = $.fn.zTree.getZTreeObj("tree");
        				if (treeNode.isParent) {
        					zTree.expandNode(treeNode);
        					return false;
        				} else {
        					if(treeNode.path=="" || treeNode.path=="#"){
        						return;
        					}
        					//添加Tabs
                            addTab(treeNode.name,'${ctx}/'+treeNode.path);
        					return true;
        				}
        			}
                }
            };
	        function addDiyDom(treeId, treeNode) {
				var spaceWidth = 5;
				var switchObj = $("#" + treeNode.tId + "_switch"),
				icoObj = $("#" + treeNode.tId + "_ico");
				switchObj.remove();
				icoObj.before(switchObj);
				if (treeNode.level > 1) {
					var spaceStr = "<span style='display: inline-block;width:" + (spaceWidth * treeNode.level)+ "px'></span>";
					switchObj.before(spaceStr);
				}
			}
            var zNodes =[
                <c:forEach items="${menus}" var="m" varStatus="status">
                    { id:${m.id}, pId:${m.parentId}, name:"${m.name}",open:${m.rootNode},iconClass:"am-icon-fw ${m.icon}<c:if test="${empty m.icon}">am-icon-gear</c:if>",path:"${m.url}"}<c:if test="${!status.last}">,</c:if>
                </c:forEach>
            ];
    });
</script>
</body>
</html>