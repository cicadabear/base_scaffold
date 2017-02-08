<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
	<title>资源管理列表</title>
	<%@ include file="../../include/head.jsp"%>
</head>
<body>
<div class="admin-content">
	<div class="admin-content-body">
		<div class="am-g ui-tools">
			<div class="am-u-sm-12 am-u-md-6">
				<div class="am-btn-toolbar">
					<div class="am-btn-group am-btn-group-xs">
						<shiro:hasPermission name="sys:resource:edit">
						<button type="button" class="am-btn am-btn-default" onclick="openModel('新增资源','${ctx}/resource/create?id=${page.entity.id}')">
							<span class="am-icon-plus"></span> 新增
						</button>
						</shiro:hasPermission>
					</div>
				</div>
			</div>
			<div class="am-u-sm-12 am-u-md-3">
				<form id="searchForm" action="${ctx}/resource/list" method="post" style="display: none;">
					<input name="id" value="${page.entity.id}" />
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				</form>
			</div>
		</div>
		<form class="am-form">
			<table id="contentTable" class="am-table am-table-striped am-table-hover table-main">
				<thead>
				<tr>
					<th>编号</th>
					<th>归属资源</th>
					<th>资源名称</th>
					<th>资源类型</th>
					<th>链接</th>
					<th>排序</th>
					<th>是否可用</th>
					<th>权限标识</th>
					<shiro:hasPermission name="sys:resource:edit"><th>操作</th></shiro:hasPermission>
				</tr>
				</thead>
				<tbody>
				<c:forEach items="${page.list}" var="resource" varStatus="status">
					<tr>
						<td>${status.index+1}</td>
						<td>${resource.resource.name}</td>
						<td>${resource.name}</td>
						<td><span class="am-badge am-badge-primary am-radius">${resource.type.info}</span></td>
						<td>${resource.url}</td>
						<td>${resource.sort}</td>
						<td>${resource.available?'<span class="am-badge am-badge-success am-radius">可用</span>':'<span class="am-badge am-badge-danger am-radius">禁用</span>'}</td>
						<td>${resource.permission}</td>
						<shiro:hasPermission name="sys:resource:edit"><td>
							<a href="javascript:;" onclick="openModel('修改资源','${ctx}/resource/update?id=${resource.id}')" title="编辑"><span class="am-icon-pencil"></span></a>
							<a href="${ctx}/resource/delete?id=${resource.id}&pageNo=${page.pageNo}&pageSize=${page.pageSize}"
								onclick="return confirm('确认要删除该条数据吗？', this.href)" title="删除"><span class="am-text-danger am-icon-trash-o"></span></a></td></shiro:hasPermission>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</form>
		<%@ include file="../../utils/pagination.jsp"%>
	</div>
</div>
<script>
	$(document).ready(function () {
		var msg = '${msg}';
		if(msg!=''){
			parent.frames['tree'].location.reload();
			showMsg(msg);
		}
	});
</script>
</body>
</html>