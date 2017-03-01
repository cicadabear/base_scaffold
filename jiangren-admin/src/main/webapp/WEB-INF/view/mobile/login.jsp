<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>${fnc:getConfig("iutils.name")}&&用户登录</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="format-detection" content="telephone=no">
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <link rel="stylesheet" href="${ctxStatic}/3rd-lib/amazeui/2.7.2/css/amazeui.min.css"/>
    <style>
        .header {
            text-align: center;
        }
        .header h1 {
            font-size: 200%;
            color: #333;
            margin-top: 30px;
        }
        .header p {
            font-size: 14px;
        }
    </style>
</head>
<body>
<div class="header">
    <div class="am-g">
        <h1>${fnc:getConfig("iutils.name")}</h1>
    </div>
    <hr />
</div>
<div class="am-g">
    <div class="am-u-lg-6 am-u-md-8 am-u-sm-centered">
        <form action="login" method="post" class="am-form">
            <label for="username">账号:</label>
            <input type="text" name="username" id="username" required>
            <br>
            <label for="password">密码:</label>
            <input type="password" name="password" id="password" required>
            <br>
            <label for="rememberMe">
                <input id="rememberMe" name="rememberMe" type="checkbox">
                记住我
            </label>
            <br />
            <div class="am-cf">
                <input type="submit" name="" value="登 录" class="am-btn am-btn-primary am-btn-block">
            </div>
        </form>
        <hr>
    </div>
</div>
<script type="text/javascript" src="${ctxStatic}/3rd-lib/jquery/1.9.0/jquery.min.js"></script>
<script src="${ctxStatic}/3rd-lib/layer/layer.js"></script>
<script>
    var message = '${message}';
    if(message!=''){
        layer.msg(message,{anim: 6});
    }
</script>
<script type="text/javascript">
    // 如果在框架或在对话框中，则弹出提示并跳转到首页
    if (top.location !== self.location) {
        top.location="${ctx}";
    }
</script>
</body>
</html>

