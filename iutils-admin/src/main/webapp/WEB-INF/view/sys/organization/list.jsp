<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
   <title>组织机构列表</title>
   <%@ include file="../../include/head.jsp"%>
</head>
<body>
  <div class="admin-content">
    <div class="admin-content-body">
        <div class="am-g ui-tools">
            <div class="am-u-sm-12 am-u-md-6">
                <div class="am-btn-toolbar">
                    <div class="am-btn-group am-btn-group-xs">
                        <button type="button" class="am-btn am-btn-default" onclick="openModel('新增机构','${ctx}/organization/create?id=${page.entity.id}')">
                            <span class="am-icon-plus"></span> 新增
                        </button>
                    </div>
                </div>
            </div>
            <div class="am-u-sm-12 am-u-md-3">
                <form id="searchForm" action="${ctx}/organization/list" method="post" style="display: none;">
                    <input name="id" value="${page.entity.id}" />
                    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                </form>
            </div>
        </div>
        <form class="am-form">
            <table id="contentTable" class="am-table am-table-striped am-table-hover table-main">
                <thead>
                <tr>
                    <th>序号</th>
                    <th>归属机构</th>
                    <th>机构名称</th>
                    <th>是否可用</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.list}" var="org" varStatus="status">
                    <tr>
                        <td>${status.index+1}</td>
                        <td>${org.organization.name}</td>
                        <td>${org.name}</td>
                        <td>${org.available?'<span class="am-badge am-badge-success am-radius">可用</span>':'<span class="am-badge am-badge-danger am-radius">禁用</span>'}</td>
                        <td>
                            <a href="javascript:;" onclick="openModel('修改机构','${ctx}/organization/update?id=${org.id}')" title="编辑"><span class="am-icon-pencil"></span></a> <a
                                href="${ctx}/organization/delete?id=${org.id}&pageNo=${page.pageNo}&pageSize=${page.pageSize}"
                                onclick="return confirm('确认要删除该条数据吗？', this.href)" title="删除"><span class="am-text-danger am-icon-trash-o"></span></a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </form>
        <%@ include file="../../utils/pagination.jsp"%>
    </div>
  </div>
  <script>
      $(document).ready(function () {
          var msg = '${msg}';
          if(msg!=''){
              parent.frames['tree'].location.reload();
              showMsg(msg);
          }
      });
  </script>
</body>
</html>