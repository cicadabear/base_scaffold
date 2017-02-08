/**
 * ztree权限树控件
 */
(function ($) {
    var setting = {
        check: {
            enable: true ,
            chkboxType: { "Y": "ps", "N": "ps" }//父子关联关系
        },
        view: {
            dblClickExpand: false
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            onCheck: onCheck
        }
    };
    //初始化ztree机构
    $.fn.ztreeOrg=function(tree,zNodes){
        $.fn.zTree.init(tree, setting, zNodes);
    }
    //点击事件
    function onCheck(e, treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj(treeId),
            nodes = zTree.getCheckedNodes(true),
            id = "";
        nodes.sort(function compare(a,b){return a.id-b.id;});
        for (var i=0, l=nodes.length; i<l; i++) {
            id += nodes[i].id + ",";
        }
        if (id.length > 0 ) id = id.substring(0, id.length-1);
        //点击回调方法
        ztreeOrgOnClickCall && ztreeOrgOnClickCall(id);
    }

})(jQuery);
