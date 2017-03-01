<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
	<title>角色管理</title>
	<%@ include file="../../include/head.jsp"%>
</head>
<body>
	<div class="admin-content">
		<div class="admin-content-body">
			<div class="am-g ui-tools">
				<div class="am-u-sm-12 am-u-md-6">
					<div class="am-btn-toolbar">
						<div class="am-btn-group am-btn-group-xs">
							<shiro:hasPermission name="sys:role:edit">
							<button type="button" class="am-btn am-btn-default" onclick="openModel('新增角色','${ctx}/role/update')">
								<span class="am-icon-plus"></span> 新增
							</button></shiro:hasPermission>
						</div>
					</div>
				</div>
				<div class="am-u-sm-12 am-u-md-3">
					<form id="searchForm" action="${ctx}/role" method="post" style="display: none;">
						<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
						<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					</form>
				</div>
			</div>
			<div class="am-g">
				<div class="am-u-sm-12">
					<form class="am-form">
						<table id="contentTable"
							class="am-table am-table-striped am-table-hover table-main">
							<thead>
								<tr>
									<th>序号</th>
									<%--<th>归属机构</th>--%>
									<th>角色名称</th>
					            	<th>角色标识</th>
									<th>是否可用</th>
									<th>备注</th>
						            <th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${page.list}" var="role" varStatus="status">
						            <tr>
										<td>${status.index+1}</td>
										<%--<td>${role.organization.name}</td>--%>
						                <td>${role.name}</td>
						                <td>${role.role}</td>
										<td>${role.available?'<span class="am-badge am-badge-success am-radius">可用</span>':'<span class="am-badge am-badge-danger am-radius">禁用</span>'}</td>
						                <td>${role.remarks}</td>
										<td>
											<shiro:hasPermission name="sys:role:view"><a href="javascript:;" onclick="openModel('修改角色','${ctx}/role/update?id=${role.id}')" title="编辑"><span class="am-icon-pencil"></span></a></shiro:hasPermission>
											<shiro:hasPermission name="sys:role:edit"><c:if test="${role.id>1}">
												<a href="${ctx}/role/delete?id=${role.id}&pageNo=${page.pageNo}&pageSize=${page.pageSize}" onclick="return confirm('确认要删除该条数据吗？', this.href)" title="删除"><span class="am-text-danger am-icon-trash-o"></span></a>
											</c:if></shiro:hasPermission>
						                </td>
						            </tr>
						        </c:forEach>
							</tbody>
						</table>
					</form>
					<%@ include file="../../utils/pagination.jsp"%>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
        $(document).ready(function() {
			var msg = '${msg}';
			if(msg!=''){
				showMsg(msg);
			}
        });
    </script>
</body>
</html>