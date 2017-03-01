<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
  <title>资源管理主页</title>
  <%@ include file="../../include/head.jsp"%>
  <style>
    iframe{width: 100%;height: 100%;}
  </style>
</head>
<body>
<div class="admin-content">
  <div class="admin-content-body">
    <div class="am-g">
      <div class="am-u-sm-12 am-u-md-2">
        <iframe name="tree" id="tree" src="${ctx}/resource/tree" frameborder="0" scrolling="auto"></iframe>
      </div>
      <div class="am-u-sm-12 am-u-md-10" style="border-left: 1px solid #ddd;height: 100%;">
        <iframe name="content" id="content" src="${ctx}/resource/list?id=1" frameborder="0" scrolling="auto"></iframe>
      </div>
    </div>
  </div>
</div>
</body>
</html>