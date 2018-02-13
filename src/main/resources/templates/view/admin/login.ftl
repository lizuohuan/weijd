<!-- 解决layer.open 不居中问题   -->
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>易信缘后台登录</title>
    <!--引入抽取css文件-->
    <#include "../admin/public-css.ftl">
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/admin/css/login.css" media="all">
</head>
<body>
<div class="layui-canvs"></div>
<div class="layui-layout layui-layout-login">
    <h1>
        <strong>易信缘后台管理系统</strong>
        <em>YXY SYSTEM</em>
    </h1>
    <div class="layui-user-icon larry-login">
        <input type="text" placeholder="账号" class="login_txtbx" id="account"/>
    </div>
    <div class="layui-pwd-icon larry-login">
        <input type="password" placeholder="密码" class="login_txtbx" id="password"/>
    </div>
    <div class="layui-submit larry-login">
        <input type="button" value="立即登录" class="submit_btn"/>
    </div>
    <div class="layui-login-text">
        <p>© 2017-2018 magic-beans 版权所有</p>
        <p>魔豆互动科技 <a href="http://www.magic-beans.cn/" title="">http://www.magic-beans.cn/</a></p>
    </div>
</div>

<!--引入抽取公共js-->
<#include "../admin/public-js.ftl">
<script type="text/javascript" src="${request.contextPath}/admin/js/common/jparticle.jquery.js"></script>
<script src="${request.contextPath}/admin/js/common/jQuery.md5.js"></script>
<script type="text/javascript">


    if(window.top != window.self){
        window.top.location = window.location;
    }
    layui.use(['jquery'],function(){
        window.jQuery = window.$ = layui.jquery;
        $(".layui-canvs").width($(window).width());
        $(".layui-canvs").height($(window).height());
    });



    $(function(){
        $(".layui-canvs").jParticle({
            background: "#393D49",
            color: "#E6E6E6"
        });

        $(".submit_btn").click(function(){
            login ();
        });

        document.onkeydown = function(event) {
            var code;
            if (!event) {
                event = window.event; //针对ie浏览器
                code = event.keyCode;
                if (code == 13) {
                    login();
                }
            }
            else {
                code = event.keyCode;
                if (code == 13) {
                    login();
                }
            }
        };
    });

    function login () {
        if ($("#account").val() == "") {
            layer.msg("账号不能为空.", {icon: 2, anim: 6});
            $("#account").focus();
            return false;
        }
        if ($("#password").val() == "") {
            layer.msg("密码不能为空.", {icon: 2, anim: 6});
            $("#password").focus();
            return false;
        }
        $(".error-hint").hide();
        $.ajax({
            type : "post",
            url : AM.ip + "/admins/login",
            data : {
                account : $("#account").val(),
                pwd : $.md5($("#password").val()),
            },
            success : function (data) {
                console.log(data);
                if (data.code == 200) {
                    localStorage.removeItem("isShowLock");
                    localStorage.setItem("userInfo", JSON.stringify(data.data));
                    location.href = AM.ip + "/page/index";
                }
                else {
                    layer.msg(data.msg, {icon: 2, anim: 6});
                }
            },
            error : function (data) {
                layer.msg(data.msg, {icon: 2, anim: 6});
            }
        });
    }

</script>
</body>
</html>
