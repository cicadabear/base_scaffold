/**
 * 富文本编辑器插件
 */
(function ($) {

    //初始化编辑器
    $.fn.initEditor = function(ctx,ctxStatic,id){
        //初始化多文本框
        var editor = new wangEditor(id);
        editor.config.uploadImgUrl = ctx+'/upload/local';// 上传图片
        editor.config.mapAk = 'H6hvQKCtGCuvagZ7IfX0Bgbs9ryC7VCt';//百度地图key
        editor.config.emotions = {//定义表情
            'default': {
                title: '默认',
                data: ctxStatic+'/3rd-lib/wangEditor/emotions.data'
            }
        }
        editor.create();
    }

})(jQuery);