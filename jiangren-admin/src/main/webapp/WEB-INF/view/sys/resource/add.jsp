<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<html>
<head>
    <title>资源编辑</title>
    <%@ include file="../../include/head.jsp" %>
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
                <form class="am-form am-form-horizontal" data-am-validator modelAttribute="resource"
                      action="${ctx}/resource/update" method="post">
                    <input type="hidden" name="parentId" value="${resource.id}"/>
                    <input type="hidden" name="parentIds" value="${resource.parentIds}${resource.id}/"/>
                    <div class="am-form-group">
                        <label class="am-u-sm-3 am-form-label">上级资源：</label>
                        <div class="am-u-sm-9">
                            <input type="text" id="parentName" minlength="1" value="${resource.name}" readonly/>
                        </div>
                    </div>
                    <div class="am-form-group">
                        <label class="am-u-sm-3 am-form-label"><span class="error">*</span>资源名称：</label>
                        <div class="am-u-sm-9">
                            <input type="text" name="name" minlength="1" placeholder="资源名称（必填）" required/>
                        </div>
                    </div>
                    <div class="am-form-group">
                        <label class="am-u-sm-3 am-form-label"><span class="error">*</span>排序：</label>
                        <div class="am-u-sm-9">
                            <input type="number" name="sort" minlength="1" value="0" placeholder="排序（必填）" required/>
                        </div>
                    </div>
                    <div class="am-form-group">
                        <label class="am-u-sm-3 am-form-label">类型：</label>
                        <div class="am-u-sm-9">
                            <select name="type" data="menu">
                                <c:forEach items="${types}" var="m">
                                    <option value="${m}">${m.info}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="am-form-group">
                        <label class="am-u-sm-3 am-form-label">图标：</label>
                        <div class="am-u-sm-9">
                            <input type="text" id="icon" name="icon" placeholder="图标"
                                   value="${resource.icon}"/>
                            <%--<div class="am-input-group">--%>
                            <%--<input type="text" id="icon" name="icon" class="am-form-field" placeholder="图标"--%>
                            <%--value="${resource.icon}" />--%>
                            <%--<span class="am-input-group-btn">--%>
                            <%--<input type="file" name="file" id="file" style="display: none;" />--%>
                            <%--<button class="am-btn am-btn-default" id="btnPicture" type="button">上传图标</button>--%>
                            <%--</span>--%>
                            <%--</div>--%>
                        </div>
                    </div>
                    <div class="am-form-group">
                        <label class="am-u-sm-3 am-form-label">URL路径：</label>
                        <div class="am-u-sm-9">
                            <input type="text" name="url"/>
                        </div>
                    </div>
                    <div class="am-form-group">
                        <label class="am-u-sm-3 am-form-label">权限字符串：</label>
                        <div class="am-u-sm-9">
                            <input type="text" name="permission"/>
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
                            <shiro:hasPermission name="sys:organization:edit">
                                <button type="submit" class="am-btn am-btn-primary">保存</button>
                            </shiro:hasPermission>
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
        if (msg != '') {
            showMsg(msg);
            //刷新tree
            parent.parent.frames['tree'].location.reload();
            closeModel(true);//关闭窗口
        }
        initSelectValue(true);//初始化下拉框的值
    });
    $(document).ready(function () {
        //触发选择文件
        $("#btnPicture").click(function () {
            $("#file").click();
        });
        //选择文件后
        $("#file").change(function () {
            $.ajaxFileUpload({
                url: '${ctx}/upload/qiniu',
                type: 'post',
                secureuri: false,
                fileElementId: 'file',
                dataType: 'text',
                success: function (data, status) {
                    data = JSON.parse(delHtmlTag(data));
                    if (data.ret == 1) {
                        $("#icon").val(data.data);
                    } else {
                        alert(data.msg);
                    }
                },
                error: function (data, status, e) {
                    alert(e);
                }
            });
        });
    });
</script>
</body>
</html>