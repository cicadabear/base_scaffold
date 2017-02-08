<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<%@ page import="java.util.Properties" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<html>
<head>
<title>欢迎页面</title>
<%@ include file="include/head.jsp"%>
</head>
<body>
	<div class="admin-content">
		<div class="admin-content-body" style="padding: 5px 10px;">
			<p>欢迎使用iutils 程序员工具 后台管理系统！</p>

			<div class="am-panel am-panel-default">
				<div class="am-panel-hd">服务器信息</div>
				<div class="am-panel-bd" style="padding:0px;">
					<%
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Properties props = System.getProperties();
						Runtime runtime = Runtime.getRuntime();
						long freeMemoery = runtime.freeMemory();
						long totalMemory = runtime.totalMemory();
						long usedMemory = totalMemory - freeMemoery;
						long maxMemory = runtime.maxMemory();
						long useableMemory = maxMemory - totalMemory + freeMemoery;
					%>
					<table class="am-table am-table-bordered" style="margin-bottom:0px;border:0;">
						<tr>
							<td style="border-left: 0;">服务器时间: </td>
							<td><%=sdf.format(new Date()) %></td>
						</tr>
						<tr>
							<td style="border-left: 0;">内存(MB): </td>
							<td>已用:<%=usedMemory/1024/1024 %>MB / 剩余:<%=useableMemory/1024/1024 %>MB / 最大:<%=maxMemory/1024/1024 %>MB</td>
						</tr>
					</table>
				</div>
			</div>

			<div class="am-panel am-panel-default">
				<div class="am-panel-hd">信息统计</div>
				<div class="am-panel-bd" style="padding:0px;">
					<table class="am-table am-table-bordered" style="margin-bottom:0px;border:0;">
						<tr>
							<th>统计</th>
							<th>访问</th>
							<th>用户</th>
							<th>异常</th>
						</tr>
						<tr>
							<td>总数</td>
							<td>92</td>
							<td>9</td>
							<td>20</td>
						</tr>
						<tr>
							<td>今日</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
						</tr>
						<tr>
							<td>昨日</td>
							<td>0</td>
							<td>0</td>
							<td>0</td>
						</tr>
						<tr>
							<td>本周</td>
							<td>2</td>
							<td>0</td>
							<td>0</td>
						</tr>
						<tr>
							<td>本月</td>
							<td>2</td>
							<td>0</td>
							<td>0</td>
						</tr>
					</table>
				</div>
			</div>

		</div>
	</div>
</body>
</html>