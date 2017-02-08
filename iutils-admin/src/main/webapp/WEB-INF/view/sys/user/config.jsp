<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
	<title>用户配置</title>
	<%@ include file="../../include/head.jsp"%>
	<style type="text/css">
		[class*=am-u-]{
			padding:0px 15px;
		}
	</style>
</head>
<body>
	<div class="admin-content">
		<div class="admin-content-body">
			<div class="am-tabs am-margin" data-am-tabs>
		      <ul class="am-tabs-nav am-nav am-nav-tabs">
		        <li class="am-active"><a href="#qiniu">七牛配置</a></li>
		        <li><a href="#weixin">微信配置</a></li>
		      </ul>
		      <div class="am-tabs-bd">
		        <div class="am-tab-panel am-fade am-in am-active" id="qiniu">
		        	<iframe src="${ctx}/user/qiniu/create" style="width:100%;height:78%;" frameborder="no"></iframe>
		        </div>
		        <div class="am-tab-panel am-fade" id="weixin">
		        	暂未开通
		        </div>
		      </div>
		    </div>
		  </div>
		</div>
	</div>
	<script type="text/javascript">
        $(document).ready(function() {

        });
    </script>
</body>
</html>