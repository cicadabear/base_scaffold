<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
	<title>用户编辑</title>
	<%@ include file="../../include/head.jsp"%>
    <link rel="stylesheet" href="${ctxStatic}/3rd-lib/jquery-ztree/3.5/css/zTreeStyle.css">
    <style>
		ul.ztree {
			margin-top: 10px;
			border: 1px solid #ddd;
			background: #fff;
			width: 198px;
			height: 200px;
			overflow-y: auto;
			overflow-x: auto;
		}
		[class*=am-u-]{
			padding:0px 15px;
		}
		.am-selected{width: 100%;}
	</style>
</head>
<body>
	<div class="admin-content">
		<div class="admin-content-body" style="margin:10px 0px;">
			<div class="am-g">
				<div class="am-u-sm-12 am-u-md-8">
					<form class="am-form am-form-horizontal" data-am-validator modelAttribute="user" action="${ctx}/user/<c:choose><c:when test="${empty user.id}">create</c:when><c:otherwise>update</c:otherwise></c:choose>" method="post">
						<input type="hidden" name="id" value="${user.id}" />
						<div class="am-form-group">
							<label class="am-u-sm-3 am-form-label"><span class="error">*</span>账号：</label>
							<div class="am-u-sm-9">
								<input type="text" name="username" minlength="3" placeholder="账号（至少3个字符）"
									value="${user.username}" ${empty user.id?'':'readonly'} required />
							</div>
						</div>
						<c:if test="${empty user.id}">
						<div class="am-form-group">
							<label class="am-u-sm-3 am-form-label"><span class="error">*</span>密码：</label>
							<div class="am-u-sm-9">
								<input type="password" name="password" minlength="6" placeholder="密码（至少6个字符）"
									value="${user.password}" required />
							</div>
						</div>
						</c:if>
						<div class="am-form-group">
							<label class="am-u-sm-3 am-form-label"><span class="error">*</span>是否部门管理员：</label>
							<div class="am-u-sm-5">
								<select name="isDept" data="${user.isDept}">
									<option value="false">否</option>
									<option value="true">是</option>
								</select>
							</div>
							<label class="am-u-sm-4"></label>
						</div>
						<div class="am-form-group" hidden>
							<label class="am-u-sm-3 am-form-label"><span class="error">*</span>所属机构：</label>
							<div class="am-u-sm-5">
								<div class="am-input-group">
									<input type="text" id="parentName" class="am-form-field" value="${user.organization.name}" readonly />
									<input type="hidden" id="parentId" name="organizationId" value="${user.organizationId}" required />
							      <span class="am-input-group-btn">
							        <button class="am-btn am-btn-default" id="menuBtn" type="button">选择</button>
							      </span>
								</div>
							</div>
							<label class="am-u-sm-4"></label>
						</div>
						<div class="am-form-group">
							<label class="am-u-sm-3 am-form-label">角色列表：</label>
							<div class="am-u-sm-9">
								<select name="roleIds" data="<c:forEach items="${user.roleIds}" var="item">${item},</c:forEach>" multiple>
									<c:forEach items="${roleList}" var="m">
										<option value="${m.id}">${m.name}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="am-form-group">
							<label class="am-u-sm-3 am-form-label">姓名：</label>
							<div class="am-u-sm-9">
								<input type="text" name="name" placeholder="姓名" value="${user.name}" />
							</div>
						</div>
						<div class="am-form-group">
							<label class="am-u-sm-3 am-form-label">邮箱：</label>
							<div class="am-u-sm-9">
								<input type="text" name="email" placeholder="邮箱" value="${user.email}" />
							</div>
						</div>
						<div class="am-form-group">
							<label class="am-u-sm-3 am-form-label">手机：</label>
							<div class="am-u-sm-9">
								<input type="text" name="mobile" placeholder="手机" value="${user.mobile}" />
							</div>
						</div>
						<div class="am-form-group">
							<label class="am-u-sm-3 am-form-label">电话：</label>
							<div class="am-u-sm-9">
								<input type="text" name="phone" placeholder="电话" value="${user.phone}" />
							</div>
						</div>
						<div class="am-form-group">
							<label class="am-u-sm-3 am-form-label">是否锁定：</label>
							<div class="am-u-sm-5">
								<select name="locked" data="${user.locked}">
									<option value="false">否</option>
									<option value="true">是</option>
								</select>
							</div>
							<label class="am-u-sm-4"></label>
						</div>

						<div class="am-form-group">
			              <div class="am-u-sm-9 am-u-sm-push-3">
			                <button type="submit" class="am-btn am-btn-primary">保存</button>
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

    <script src="${ctxStatic}/3rd-lib/jquery-ztree/3.5/js/jquery.ztree.core-3.5.min.js"></script>
	<script src="${ctxStatic}/custom/js/ztree.select.js"></script>
    <script>
		$(document).ready(function() {
			//消息提醒
			var msg = '${msg}';
			if(msg!=''){
				showMsg(msg);
				if(msg=="保存成功"){
					closeModel(true);//关闭窗口
				}
			}
			initSelectValue(true);//初始化下拉框的值
		});
        $(function () {
            var zNodes =[
                <c:forEach items="${organizationList}" var="o" varStatus="status">
                    <c:if test="${not o.rootNode}">
                        { id:${o.id}, pId:${o.parentId}, name:"${o.name}"}<c:if test="${!status.last}">,</c:if>
                    </c:if>
                </c:forEach>
            ];
			$.fn.ztreeSelect($("#tree"),zNodes);
        });
		//ztree点击回调
		function ztreeOnClickCall(treeNode){
			$("#parentId").val(treeNode.id);
		}
    </script>

</body>
</html>