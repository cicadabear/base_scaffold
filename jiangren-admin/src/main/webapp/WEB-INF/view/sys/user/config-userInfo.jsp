<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
	<title>用户信息</title>
	<%@ include file="../../include/head.jsp"%>
	<style type="text/css">
		[class*=am-u-]{
			padding:0px 15px;
		}
	</style>
</head>
<body>
	<div class="admin-content">
		<div class="admin-content-body" style="margin-top:10px;">
			<div class="am-g">
		        <div class="am-u-sm-12 am-u-md-4 am-u-md-push-8">
		          <div class="am-panel am-panel-default">
		            <div class="am-panel-bd">
		              <div class="am-g">
		                <div class="am-u-md-4">
		                  <img id="showPic" class="am-img-circle am-img-thumbnail" src="${user.photo}" alt=""/>
		                </div>
		                <div class="am-u-md-8">
		                  <p>头像选择 </p>
		                    <div class="am-form-group">
		                      <input type="file" name="file" id="file">
		                      <p class="am-form-help">请选择要上传的文件...</p>
		                      <button id="uploadPic" type="button" class="am-btn am-btn-primary am-btn-xs">上传</button>
		                    </div>
		                </div>
		              </div>
		            </div>
		          </div>
		
		          <div class="am-panel am-panel-default">
		            <div class="am-panel-bd">
		              <div class="user-info">
		                <p>等级信息</p>
		                <div class="am-progress am-progress-sm">
		                  <div class="am-progress-bar" style="width: 60%"></div>
		                </div>
		                <p class="user-info-order">当前等级：<strong>LV8</strong> 活跃天数：<strong>587</strong> 距离下一级别：<strong>160</strong></p>
		              </div>
		              <div class="user-info">
		                <p>信用信息</p>
		                <div class="am-progress am-progress-sm">
		                  <div class="am-progress-bar am-progress-bar-success" style="width: 80%"></div>
		                </div>
		                <p class="user-info-order">信用等级：正常当前 信用积分：<strong>80</strong></p>
		              </div>
		            </div>
		          </div>
		
		        </div>
		
		        <div class="am-u-sm-12 am-u-md-8 am-u-md-pull-4">
		          <form class="am-form am-form-horizontal" modelAttribute="user" action="${ctx}/user/saveUserInfo" method="post">
		            <input type="hidden" name="id" value="${user.id}" />
		            <input type="hidden" id="photo" name="photo" value="${user.photo}" />
		            <div class="am-form-group">
		              <label for="name" class="am-u-sm-3 am-form-label">姓名：</label>
		              <div class="am-u-sm-9">
		                <input type="text" id="name" name="name" placeholder="姓名" value="${user.name}">
		              </div>
		            </div>
		            <div class="am-form-group">
		              <label for="email" class="am-u-sm-3 am-form-label">邮箱：</label>
		              <div class="am-u-sm-9">
		                <input type="email" id="email" name="email" placeholder="输入你的邮箱" value="${user.email}">
		              </div>
		            </div>
					<div class="am-form-group">
		              <label for="mobile" class="am-u-sm-3 am-form-label">手机：</label>
		              <div class="am-u-sm-9">
		                <input type="text" id="mobile" name="mobile" placeholder="输入你的手机" value="${user.mobile}">
		              </div>
		            </div>
		            <div class="am-form-group">
		              <label for="phone" class="am-u-sm-3 am-form-label">电话：</label>
		              <div class="am-u-sm-9">
		                <input type="tel" id="phone" name="phone" placeholder="输入你的电话" value="${user.phone}">
		              </div>
		            </div>
		            <div class="am-form-group">
		              <label for="remarks" class="am-u-sm-3 am-form-label">简介：</label>
		              <div class="am-u-sm-9">
		                <textarea rows="10" id="remarks" name="remarks" placeholder="输入个人简介">${user.remarks}</textarea>
		                <small>250字以内写出你的一生...</small>
		              </div>
		            </div>
		            <div class="am-form-group">
		              <div class="am-u-sm-9 am-u-sm-push-3">
		                <button type="submit" class="am-btn am-btn-primary">保存修改</button>
		              </div>
		            </div>
		          </form>
		        </div>
		      </div>
		    </div>
		</div>
	</div>
	<script type="text/javascript" src="${ctxStatic}/custom/js/ajaxfileupload.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			var msg = '${msg}';
			if(msg!=''){
				showMsg(msg);
			}
		});
	</script>
	<script type="text/javascript">
        $(document).ready(function() {
			$("#uploadPic").click(function(){
				$.ajaxFileUpload({
	                    url: '${ctx}/upload/qiniu', 
	                    type: 'post',
	                    secureuri: false,
	                    fileElementId: 'file',
	                    dataType: 'text',
	                    success: function (data, status){
	                    	data = JSON.parse(delHtmlTag(data));
	                    	if(data.ret==1){
	                    		$("#showPic").attr("src",data.data);
	                    		$("#photo").val(data.data);
	                    	}else{
	                    		alert(data.msg);
	                    	}
	                    },
	                    error: function (data, status, e){
	                    	alert("上传失败");
	                    }
	              });
			});
        });
    </script>
</body>
</html>