$(document).ready(function(){
    //绑定文本框的多个事件
    $(document).on("blur focus keyup keypress",".am-select-input",function(event){
        var $this = $(this);
        var eType=event.type;
        switch(eType){
            case 'keypress':
                if(event.keyCode==13){
                    //阻止表单提交
                    event.preventDefault();
                }
                break;
            case 'focusin'://获取焦点
                var proposalList = $this.parent().find(".am-select-ul");
                proposalList.empty();
                //获取配置数据
                var items = $this.attr("am-data");
                if(items) {
                    items = JSON.parse(items);
                    for(var i in items){
                        if($('input[name='+items[i].field+']').length==0){
                            var element = $('<li></li>')
                                .html(items[i].desc+"=?");
                            proposalList.append(element);
                        }
                    }
                    $this.parent().find(".am-select-ul").show();
                }
                break;
            case 'focusout'://失去焦点
                $this.parent().find(".am-select-ul").hide(300);
                break;
            case 'keyup'://键盘按下
                var currentProposals = [];
                var v = event.which;
                if (v == 38 || v == 40 || v == 13){return;}
                var word = $this.val();
                //获取配置数据
                var items = $this.attr("am-data");
                if(items) {
                    items = JSON.parse(items);
                    var proposalList = $this.parent().find(".am-select-ul");
                    proposalList.empty();
                    for(var i in items){
                        if($('input[name='+items[i].field+']').length==0){
                            if(!isNaN(word) && items[i].type=='number'){
                                //数字
                                var element = $('<li></li>')
                                    .html('<input type="hidden" name="'+items[i].field+'" value="'+word+'" />'+items[i].desc+"="+word)
                                    .click(function(){
                                        if(word!=''){
                                            //新增tags
                                            var $prev = $this.parent().prev();
                                            if ($prev.size()){
                                                $('<span class="tags">'+$(this).html()+' <a href="javascript:;" onclick="$(this).parent().remove()">x</a></span>').insertAfter($prev);
                                            }else{
                                                $('<span class="tags">'+$(this).html()+' <a href="javascript:;" onclick="$(this).parent().remove()">x</a></span>').insertBefore($this.parent());
                                            }
                                            //清空文本框
                                            $this.val("");
                                            $this.focus();
                                        }
                                    });
                                proposalList.append(element);
                            }else if(isNaN(word) && items[i].type=='string'){
                                var element = $('<li></li>')
                                    .html('<input type="hidden" name="'+items[i].field+'" value="'+word+'" />'+items[i].desc+"="+word)
                                    .click(function(){
                                        if(word!=''){
                                            //新增tags
                                            var $prev = $this.parent().prev();
                                            if ($prev.size()){
                                                $('<span class="tags">'+$(this).html()+' <a href="javascript:;" onclick="$(this).parent().remove()">x</a></span>').insertAfter($prev);
                                            }else{
                                                $('<span class="tags">'+$(this).html()+' <a href="javascript:;" onclick="$(this).parent().remove()">x</a></span>').insertBefore($this.parent());
                                            }
                                            //清空文本框
                                            $this.val("");
                                            $this.focus();
                                        }
                                    });
                                proposalList.append(element);
                            }
                        }
                    }
                }
                break;
        }
    });

});