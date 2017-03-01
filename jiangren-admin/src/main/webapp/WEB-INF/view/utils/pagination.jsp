<!-- 分页工具 -->
<%@ page contentType="text/html;charset=UTF-8"%>
<div class="am-pagination-tip">显示 ${page.pageNo+1}/${page.pageNumber} 页，共 ${page.total} 条</div>
<c:if test="${page.total>page.pageSize}">
<ul class="am-pagination am-pagination-right" style="margin:0;padding-bottom: 50px;padding-right: 10px;">
	<li class="<c:if test="${page.hasFirstPage}">am-disabled</c:if>"><a href="javascript:page(${page.first});">首页</a></li>
	<li class="<c:if test="${page.hasFirstPage}">am-disabled</c:if>"><a href="javascript:page(${page.prev});">上一页</a></li>
	<li class="<c:if test="${page.hasLastPage}">am-disabled</c:if>"><a href="javascript:page(${page.next});">下一页</a></li>
	<li class="<c:if test="${page.hasLastPage}">am-disabled</c:if>"><a href="javascript:page(${page.last});">末页</a></li>
</ul>
<script type="text/javascript">
     //分页查询
     function page(n){
		$("#pageNo").val(n);
		$("#searchForm").submit();
     	return false;
     }
</script>
</c:if>