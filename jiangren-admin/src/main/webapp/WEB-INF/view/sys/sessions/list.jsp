<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<head>
<title>会话管理</title>
<%@ include file="../../include/head.jsp"%>
</head>
<body>
	<div class="admin-content">
		<div class="admin-content-body">
			<div class="am-g ui-tools">
				<div class="am-u-sm-12 am-u-md-6">当前在线人数：${sessionCount}人</div>
			</div>
			<div class="am-g">
				<div class="am-u-sm-12">
					<form class="am-form">
						<table id="contentTable"
							class="am-table am-table-striped am-table-hover table-main">
							<thead>
								<tr>
									<th>序号</th>
									<th>用户</th>
									<th>主机地址</th>
									<th>最后访问时间</th>
									<th>已强制退出</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${sessions}" var="session" varStatus="status">
									<tr>
										<td>${status.index+1}</td>
										<td>${fnc:principal(session)}</td>
										<td>${session.host}</td>
										<td><fmt:formatDate value="${session.lastAccessTime}"
												pattern="yyyy-MM-dd HH:mm:ss" /></td>
										<td>${fnc:isForceLogout(session) ? '<span class="am-badge am-badge-danger am-radius">是</span>':'<span class="am-badge am-badge-success am-radius">否</span>'}</td>
										<td><c:if test="${not fnc:isForceLogout(session)}">
												<a href="${ctx}/sessions/${session.id}/forceLogout" onclick="return confirm('确认要强制退出吗？', this.href)" title="强制退出"><span class="am-text-danger am-icon-sign-out"></span></a>
											</c:if></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
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
			}
		});
	</script>
</body>
</html>