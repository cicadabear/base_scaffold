<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
	<title>用户编辑</title>
	<%@ include file="../../include/head.jsp"%>
	<style type="text/css">
		[class*=am-u-]{
			padding:0px 15px;
		}
	</style>
</head>
<body>
	<div class="admin-content">
		<div class="admin-content-body" style="margin:30px 0px;">
			<div class="am-g">
				<div class="am-u-sm-12 am-u-md-8">
					<form class="am-form am-form-horizontal" action="${ctx}/user/changePassword" method="post">
				        <div class="am-form-group">
							<label class="am-u-sm-3 am-form-label">原密码：</label>
							<div class="am-u-sm-9">
								<input type="password" name="oldPassword" placeholder="原密码" required />
							</div>
						</div>
						<div class="am-form-group">
							<label class="am-u-sm-3 am-form-label">新密码：</label>
							<div class="am-u-sm-9">
								<input type="password" name="newPassword" placeholder="新密码" required />
							</div>
						</div>
						<div class="am-form-group">
							<label class="am-u-sm-3 am-form-label">再次输入：</label>
							<div class="am-u-sm-9">
								<input type="password" name="rePassword" placeholder="再次输入" required />
							</div>
						</div>
						<div class="am-form-group">
			              <div class="am-u-sm-9 am-u-sm-push-3">
			                <button type="submit" class="am-btn am-btn-primary">修改密码</button>
			                <button type="button" class="am-btn am-btn-danger" onclick="closeModel(false)">关闭</button>
			              </div>
			            </div>
				    </form>
				</div>  
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			var msg = '${msg}';
			if(msg!=''){
				showMsg(msg);
				if(msg=="修改密码成功"){
					closeModel(true);//关闭窗口
				}
			}
		});
	</script>
</body>
</html>