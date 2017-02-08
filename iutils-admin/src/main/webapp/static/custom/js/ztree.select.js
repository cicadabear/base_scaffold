/**
 * 树型结构选择
 */
(function ($) {
    var setting = {
        view: {
            dblClickExpand: false
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            onClick: onClick
        }
    };

    //初始化ztree选择
    $.fn.ztreeSelect=function(tree,zNodes){
        var ztree = $.fn.zTree.init(tree, setting, zNodes);
        ztree.expandAll(true);//默认展开
    }

    //点击事件
    function onClick(e, treeId, treeNode) {
        //点击回调方法
        ztreeOnClickCall && ztreeOnClickCall(treeNode);
        $("#parentName").val(treeNode.name);
        hideMenu();
    }

    function showMenu() {
        var cityObj = $("#parentName");
        var cityOffset = $("#parentName").offset();
        $("#menuContent").css({
            left: cityOffset.left + "px",
            top: cityOffset.top + cityObj.outerHeight() + "px"
        }).slideDown("fast");

        $("body").bind("mousedown", onBodyDown);
    }

    function hideMenu() {
        $("#menuContent").fadeOut("fast");
        $("body").unbind("mousedown", onBodyDown);
    }

    function onBodyDown(event) {
        if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length > 0)) {
            hideMenu();
        }
    }
    $("#menuBtn").click(showMenu);
})(jQuery);
