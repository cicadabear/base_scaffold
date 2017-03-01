<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<%@ page isErrorPage="true"%>
<%
	response.setStatus(HttpServletResponse.SC_OK);
	String path = request.getContextPath();
%>
<html>
<head>
<title>500 ${fnc:getConfig("iutils.name")} iutils.cn</title>
<%@ include file="../include/head.jsp"%>
</head>
<body>
	<div class="admin-content">
		<div class="admin-content-body">
			<div class="am-g">
				<div class="am-u-sm-12">
					<h2 class="am-text-center am-text-xxxl am-margin-top-lg">500.
						Internal Server Error</h2>
					<p class="am-text-center">内部服务器错误</p>
					<p class="error-info">您可以：<a href="javascript:;" onclick="history.go(-1)">&lt; 返回上一页</a><span> | </span><a href="javascript:;" onclick="top.location='${ctx}'">去首页 &gt;</a></p>
					<div class="am-g" style="padding: 0px 30px;">
						<div>系统执行发生错误，信息描述如下：</div>
						<div>错误状态代码是：${pageContext.errorData.statusCode}</div>
						<div>错误发生页面是：${pageContext.errorData.requestURI}</div>
						<div>错误信息：${pageContext.exception}</div>
						<div>
							错误堆栈信息：<br />
							<c:forEach var="trace"
								items="${pageContext.exception.stackTrace}">
								<div>${trace}</div>
							</c:forEach>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>