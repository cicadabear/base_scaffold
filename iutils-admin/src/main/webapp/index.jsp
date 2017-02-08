<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${fnc:getConfig("iutils.name")}正在加载...</title>
</head>
<body>
<%= System.getProperty("user.dir")%>
<br/>
<%= getClass().getResource("/")%>
<br/>
<%= request.getServletContext().getRealPath("/")%>
<br/>
${ctx}
<script>
    //window.location.href='${fnc:getConfig("adminPath")}';
</script>
</body>
</html>