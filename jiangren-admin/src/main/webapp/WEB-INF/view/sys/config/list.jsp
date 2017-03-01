<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <title>公共配置表</title>
    <%@ include file="../../include/head.jsp"%>
</head>
<body>
<div class="admin-content">
    <div class="admin-content-body">
        <div class="am-g ui-tools">
            <div class="am-u-sm-12 am-u-md-6">
                <div class="am-btn-toolbar">
                    <div class="am-btn-group am-btn-group-xs">
                        <button type="button" class="am-btn am-btn-default" onclick="openModel('新增公共配置表','${ctx}/sys/config/create')">
                            <span class="am-icon-plus"></span> 新增
                        </button>
                    </div>
                </div>
            </div>
            <div class="am-u-sm-12 am-u-md-3">
                <form id="searchForm" action="${ctx}/sys/config" method="post">
                    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>

                    <div class="am-input-group am-input-group-sm">
                        <input type="text" class="am-form-field" name="key" value="${page.key}" placeholder="">
                        <span class="am-input-group-btn">
                            <button class="am-btn am-btn-default" type="submit" onclick="initSearchForm()">搜索</button>
                        </span>
                    </div>
                </form>
            </div>
        </div>
        <div class="am-g">
            <div class="am-u-sm-12">
                <form class="am-form">
                    <table id="contentTable" class="am-table am-table-striped am-table-hover table-main">
                        <thead>
                        <tr>
                                <th>序号</th>
                                <th>系统名称</th>
                                <th>模块名称</th>
                                <th>key</th>
                                <th>value</th>
                                <th>备注</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${page.list}" var="config" varStatus="status">
                            <tr>
                                    <td>${config.id}</td>
                                    <td>${config.sysName}</td>
                                    <td>${config.moduleName}</td>
                                    <td>${config.configName}</td>
                                    <td>${config.configValue}</td>
                                    <td>${config.remarks}</td>
                                <td>
                                    <a href="javascript:;" onclick="openModel('修改公共配置表','${ctx}/sys/config/update?id=${config.id}')" title="编辑"><span class="am-icon-pencil"></span></a>
                                    <a href="${ctx}/sys/config/${config.id}/delete?pageNo=${page.pageNo}&pageSize=${page.pageSize}" onclick="return confirm('确认要删除该条数据吗？', this.href)" title="删除"><span class="am-text-danger am-icon-trash-o"></span></a></td>
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
    $(document).ready(function () {
        var msg = '${msg}';
        if(msg!=''){
            showMsg(msg);
        }
    });
</script>
</body>
</html>
