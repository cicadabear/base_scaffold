<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>公共配置表</title>
    <%@ include file="../../include/head.jsp"%>
    <style type="text/css">
        [class*=am-u-] {
            padding: 0px 15px;
        }
    </style>
</head>
<body>
<div class="admin-content">
    <div class="admin-content-body" style="margin:10px 0px;">
        <div class="am-g">
            <div class="am-u-sm-12 am-u-md-8">
                <form class="am-form am-form-horizontal" data-am-validator modelAttribute="config" action="${ctx}/sys/config/<c:choose><c:when test="${empty config.id}">create</c:when><c:otherwise>update</c:otherwise></c:choose>" method="post">
                    <input type="hidden" name="id" value="${config.id}"/>
                    <div class="am-form-group">
                        <label class="am-u-sm-3 am-form-label"><span class="error">*</span>系统名称：</label>
                        <div class="am-u-sm-9">
                            <input type="text" name="sysName" placeholder="系统名称" value="${config.sysName}" required/>
                        </div>
                    </div>
                    <div class="am-form-group">
                        <label class="am-u-sm-3 am-form-label"><span class="error">*</span>模块名称：</label>
                        <div class="am-u-sm-9">
                            <input type="text" name="moduleName" placeholder="模块名称" value="${config.moduleName}" required/>
                        </div>
                    </div>
                    <div class="am-form-group">
                        <label class="am-u-sm-3 am-form-label"><span class="error">*</span>key：</label>
                        <div class="am-u-sm-9">
                            <input type="text" name="configName" placeholder="key" value="${config.configName}" required/>
                        </div>
                    </div>
                    <div class="am-form-group">
                        <label class="am-u-sm-3 am-form-label"><span class="error">*</span>value：</label>
                        <div class="am-u-sm-9">
                            <input type="text" name="configValue" placeholder="value" value="${config.configValue}" required/>
                        </div>
                    </div>
                    <div class="am-form-group">
                        <label class="am-u-sm-3 am-form-label">备注：</label>
                        <div class="am-u-sm-9">
                            <textarea name="remarks">${config.remarks}</textarea>
                        </div>
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
<script type="text/javascript">
    $(document).ready(function () {
        var msg = '${msg}';
        if(msg!=''){
            showMsg(msg);
            closeModel(true);//关闭窗口
        }
    });
</script>
</body>
</html>
