<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<html>
<head>
    <title>会员管理</title>
    <%@ include file="../../include/head.jsp" %>
    <link href="${ctxStatic}/custom/css/amazeui.select.css" type="text/css" rel="stylesheet" charset="UTF-8"/>
</head>
<body>
<div class="admin-content">
    <div class="admin-content-body">
        <div class="am-g ui-tools">
            <div class="am-u-sm-12 am-u-md-3">
                <div class="am-btn-toolbar">
                    <div class="am-btn-group am-btn-group-xs">
                        <shiro:hasPermission name="profile:member:create">
                            <button type="button" class="am-btn am-btn-default"
                                    onclick="openModel('新增用户','${ctx}/user/create?organizationId=${page.entity.organizationId}')">
                                <span class="am-icon-plus"></span> 新增
                            </button>
                        </shiro:hasPermission>
                    </div>
                </div>
            </div>
            <form id="searchForm" action="${ctx}/member/list" method="post">
                <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                <div class="am-u-sm-12 am-u-md-9" style="float: right;">
                    <div class="am-input-group am-input-group-sm">
                        <div class="tagsinput">
                            <c:if test="${not empty page.entity.username}"><span class="tags"><input type="hidden"
                                                                                                     name="username"
                                                                                                     value="${page.entity.username}"/>账号=${page.entity.username} <a
                                    href="javascript:;" onclick="$(this).parent().remove()">x</a></span></c:if>
                            <c:if test="${not empty page.entity.phone}"><span class="tags"><input type="hidden"
                                                                                                  name="mobile"
                                                                                                  value="${page.entity.phone}"/>手机号=${page.entity.phone} <a
                                    href="javascript:;" onclick="$(this).parent().remove()">x</a></span></c:if>
                            <span class="am-select am-input-group-sm">
                                 <input type="text" class="am-select-input" autocomplete="off"
                                        placeholder="关键字"
                                        am-data='[{"field":"username","desc":"账号","type":"string"},{"field":"phone","desc":"手机号","type":"number"}]'>
                                <ul class="am-select-ul"></ul>
                            </span>
                        </div>
                        <span class="am-input-group-btn">
							<button class="am-btn am-btn-default" type="submit" onclick="initSearchForm()">搜索</button>
						</span>
                    </div>
                </div>
            </form>
        </div>
        <form class="am-form">
            <table id="contentTable"
                   class="am-table am-table-striped am-table-hover table-main">
                <thead>
                <tr>
                    <th>序号</th>
                    <th>账号</th>
                    <th>Email</th>
                    <th>电话号码</th>
                    <th>是否锁定</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.list}" var="user" varStatus="status">
                    <tr>
                        <td>${status.index+1}</td>
                        <td>${user.username}</td>
                        <td>${user.email}</td>
                        <td>${user.phone}</td>
                        <td>${user.locked?'<span class="am-badge am-badge-danger am-radius">是</span>':'<span class="am-badge am-badge-success am-radius">否</span>'}</td>
                        <td>
                            <shiro:hasPermission name="profile:member:update"><a href="javascript:;"
                                                                                 onclick="openModel('修改用户','${ctx}/user/update?id=${user.id}')"
                                                                                 title="编辑"><span
                                    class="am-icon-pencil"></span></a></shiro:hasPermission>
                            <shiro:hasPermission name="sys:user:delete"><c:if
                                    test="${user.id!=1 && user.id!=fnc:getLoginUser().id}">
                                <a href="${ctx}/user/delete?id=${user.id}&pageNo=${page.pageNo}&pageSize=${page.pageSize}"
                                   onclick="return confirm('确认要删除该条数据吗？', this.href)" title="删除"><span
                                        class="am-text-danger am-icon-trash-o"></span></a>
                            </c:if></shiro:hasPermission>
                            <a href="javascript:;" onclick="openModel('修改密码','${ctx}/user/${user.id}/changePassword')"
                               title="改密"><span class="am-text-success am-icon-key"></span></a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </form>
        <%@ include file="../../utils/pagination.jsp" %>
    </div>
</div>
<script type="text/javascript" src="${ctxStatic}/custom/js/amazeui.select.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        var msg = '${msg}';
        if (msg != '') {
            showMsg(msg);
        }
    });
</script>
</body>
</html>