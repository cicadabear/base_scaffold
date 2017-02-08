<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
	<title>组织机构</title>
	<%@ include file="../../include/head.jsp"%>
    <link rel="stylesheet" href="${ctxStatic}/3rd-lib/jquery-ztree/3.5/css/zTreeStyle.css">
</head>
<body>
<ul id="tree" class="ztree"></ul>
<script src="${ctxStatic}/3rd-lib/jquery-ztree/3.5/js/jquery.ztree.core-3.5.min.js"></script>
<script>
    $(function () {
        var setting = {
            data: {
                simpleData: {
                    enable: true
                }
            },
            callback : {
                onClick : function(event, treeId, treeNode) {
                    parent.frames['content'].location.href = "${ctx}/organization/list?id="+treeNode.id;
                }
            }
        };
        var zNodes =[
            <c:forEach items="${organizationList}" var="o" varStatus="status">
                { id:${o.id}, pId:${o.parentId}, name:"${o.name}", open:${o.rootNode}}<c:if test="${!status.last}">,</c:if>
            </c:forEach>
        ];
        $(document).ready(function(){
            var ztree = $.fn.zTree.init($("#tree"), setting, zNodes);
            ztree.expandAll(true);
            var nodes = ztree.getNodes();
            if(nodes.length>0){
                //默认点击第一个
                ztree.selectNode(nodes[0]);
                parent.frames['content'].location.href = "${ctx}/organization/list?id="+nodes[0].id;
            }

        });
    });
</script>
</body>
</html>