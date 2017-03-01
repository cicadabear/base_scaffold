<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>${fnc:getConfig("iutils.name")}管理系统</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <link href="${ctxStatic}/custom/css/login.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<h1>${fnc:getConfig("iutils.name")}管理系统<sup class="error">beta</sup></h1>
<div class="login" style="margin-top:50px;">
    <div class="header">
        <%--<div class="switch" id="switch"><a class="switch_btn_focus" id="switch_qlogin" href="javascript:void(0);"--%>
        <%--tabindex="7">快速登录</a>--%>
        <%--<a class="switch_btn" id="switch_login" href="javascript:void(0);" tabindex="8">快速注册</a>--%>
        <%--<div class="switch_bottom" id="switch_bottom" style="position: absolute; width: 64px; left: 0px;"></div>--%>
        <%--</div>--%>
        <div style="line-height: 50px;text-align: center;font-size: 16px;">登录</div>

    </div>
    <div class="web_qr_login" id="web_qr_login" style="display: block; height: 235px;">
        <!--登录-->
        <div class="web_login" id="web_login">
            <div class="login-box">
                <div class="login_form">
                    <form action="login" name="loginform" accept-charset="utf-8" id="login_form" class="loginForm"
                          method="post">
                        <div class="uinArea" id="uinArea">
                            <label class="input-tips" for="u">帐号：</label>
                            <div class="inputOuter" id="uArea">
                                <input type="text" id="u" name="username" class="inputstyle" value="${username}"
                                       required/>
                            </div>
                        </div>
                        <div class="pwdArea" id="pwdArea">
                            <label class="input-tips" for="p">密码：</label>
                            <div class="inputOuter" id="pArea">
                                <input type="password" id="p" name="password" class="inputstyle" required/>
                            </div>
                        </div>
                        <label for="rememberMe"> <input id="rememberMe" name="rememberMe" type="checkbox"> 记住我 (公共电脑慎用)
                        </label> <span class="error">${message}</span><br/>
                        <div style="padding-left:50px;margin-top:20px;"><input type="submit" value="登 录"
                                                                               style="width:150px;"
                                                                               class="button_blue"/></div>
                    </form>
                </div>
            </div>
        </div>
        <!--登录end-->
    </div>
    <!--注册-->
    <%--<div class="qlogin" id="qlogin" style="display:none;">--%>
    <%--<div class="web_login">--%>
    <%--<form name="form2" id="regUser" accept-charset="utf-8" method="post">--%>
    <%--<ul class="reg_form" id="reg-ul">--%>
    <%--<div id="userCue" class="cue">快速注册请注意格式</div>--%>
    <%--<li>--%>
    <%--<label for="user" class="input-tips2">帐号：</label>--%>
    <%--<div class="inputOuter2">--%>
    <%--<input type="text" id="user" name="user" maxlength="16" class="inputstyle2"/>--%>
    <%--</div>--%>
    <%--</li>--%>
    <%--<li>--%>
    <%--<label for="passwd" class="input-tips2">密码：</label>--%>
    <%--<div class="inputOuter2">--%>
    <%--<input type="password" id="passwd" name="passwd" maxlength="16" class="inputstyle2"/>--%>
    <%--</div>--%>
    <%--</li>--%>
    <%--<li>--%>
    <%--<label for="passwd2" class="input-tips2">确认密码：</label>--%>
    <%--<div class="inputOuter2">--%>
    <%--<input type="password" id="passwd2" name="" maxlength="16" class="inputstyle2"/>--%>
    <%--</div>--%>
    <%--</li>--%>
    <%--<li>--%>
    <%--<div class="inputArea">--%>
    <%--<input type="button" id="reg" style="margin-top:10px;margin-left:85px;" class="button_blue"--%>
    <%--value="同意协议并注册"/> <a href="javascript:;" class="zcxy" target="_blank">注册协议</a>--%>
    <%--</div>--%>
    <%--</li>--%>
    <%--<div class="cl"></div>--%>
    <%--</ul>--%>
    <%--</form>--%>
    <%--</div>--%>
    <%--</div>--%>
    <!--注册end-->
</div>
<div class="jianyi"><p>${fnc:getConfig("iutils.copyright")}</p></div>
<script type="text/javascript" src="${ctxStatic}/3rd-lib/jquery/1.9.0/jquery.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/custom/js/login.js"></script>
<script type="text/javascript">
    var rest = '${rest}';
    // 如果在框架或在对话框中，则弹出提示并跳转到首页
    if (top.location !== self.location) {
        alert("未登录或登录超时。请重新登录，谢谢！");
        top.location = "${ctx}";
    }
</script>
</body>
</html>