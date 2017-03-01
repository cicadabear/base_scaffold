<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
	<title>用户七牛配置</title>
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
			<div class="am-g">
				<div class="am-u-sm-12 am-u-md-8">
					<form class="am-form am-form-horizontal" modelAttribute="userQiniu" action="${ctx}/user/qiniu/<c:choose><c:when test="${empty userQiniu.id}">create</c:when><c:otherwise>${userQiniu.id}/update</c:otherwise></c:choose>" method="post">
						<input type="hidden" name="id" value="${userQiniu.id}" />
						<input type="hidden" name="userId" value="${userQiniu.userId}" />
						<div class="am-form-group">
							<label class="am-u-sm-3 am-form-label">域名：</label>
							<div class="am-u-sm-9">
								<input type="text" name="domain" placeholder="域名"
									value="${userQiniu.domain}" required />
							</div>
						</div>
						<div class="am-form-group">
							<label class="am-u-sm-3 am-form-label">AK：</label>
							<div class="am-u-sm-9">
								<input type="text" name="access" placeholder="AK"
									value="${userQiniu.access}" required />
							</div>
						</div>
						<div class="am-form-group">
							<label class="am-u-sm-3 am-form-label">SK：</label>
							<div class="am-u-sm-9">
								<input type="text" name="secret" placeholder="SK"
									value="${userQiniu.secret}" required />
							</div>
						</div>
						<div class="am-form-group">
							<label class="am-u-sm-3 am-form-label">PUB：</label>
							<div class="am-u-sm-9">
								<input type="text" name="pub" placeholder="PUB"
									value="${userQiniu.pub}" required />
							</div>
						</div>
						<div class="am-form-group">
							<label class="am-u-sm-3 am-form-label">PRI：</label>
							<div class="am-u-sm-9">
								<input type="text" name="pri" placeholder="PRI"
									value="${userQiniu.pri}" required />
							</div>
						</div>
						<div class="am-form-group">
			              <div class="am-u-sm-9 am-u-sm-push-3">
			                <button type="submit" class="am-btn am-btn-primary">保存</button>
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
			}
		});
	</script>
</body>
</html>
