<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
	<title>组织机构编辑</title>
	<%@ include file="../../include/head.jsp"%>
	<style type="text/css">
		[class*=am-u-]{
			padding:0px 15px;
		}
	</style>
</head>
<body>
	<div class="admin-content">
		<div class="admin-content-body" style="margin:10px 0px;">
			<div class="am-g">
				<div class="am-u-sm-12 am-u-md-8">
					<form class="am-form am-form-horizontal" data-am-validator modelAttribute="organization" action="${ctx}/organization/update" method="post">
						<input type="hidden" name="parentId" value="${organization.id}" />
						<input type="hidden" name="parentIds" value="${organization.parentIds}${organization.id}/" />
						<div class="am-form-group">
							<label class="am-u-sm-3 am-form-label">上级机构：</label>
							<div class="am-u-sm-9">
								<input type="text" id="parentName" value="${organization.name}" readonly/>
							</div>
						</div>
						<div class="am-form-group">
							<label class="am-u-sm-3 am-form-label"><span class="error">*</span>机构名称：</label>
							<div class="am-u-sm-9">
								<input type="text" minlength="2" name="name" placeholder="机构名称（至少2个字符）" required />
							</div>
						</div>
						<div class="am-form-group">
							<label class="am-u-sm-3 am-form-label">是否可用：</label>
							<div class="am-u-sm-9">
								<select name="available" data="true">
									<option value="true">可用</option>
									<option value="false">禁用</option>
								</select>
							</div>
						</div>
						<div class="am-form-group">
			              <div class="am-u-sm-9 am-u-sm-push-3">
							<shiro:hasPermission name="sys:organization:edit"><button type="submit" class="am-btn am-btn-primary">保存</button></shiro:hasPermission>
							<button type="button" class="am-btn am-btn-danger" onclick="closeModel(false)">关闭</button>
			              </div>
			            </div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div id="menuContent" class="menuContent" style="display:none; position: absolute;">
		<ul id="tree" class="ztree" style="margin-top:0;"></ul>
	</div>
	<script type="text/javascript">
		$(function () {
			//消息提醒
			var msg = '${msg}';
			if(msg!=''){
				showMsg(msg);
				//刷新tree
				parent.parent.frames['tree'].location.reload();
				closeModel(true);//关闭窗口
			}
			initSelectValue(true);//初始化下拉框的值
		});
	</script>
</body>
</html>
