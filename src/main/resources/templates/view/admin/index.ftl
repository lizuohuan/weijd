<!-- 解决layer.open 不居中问题   -->
<!DOCTYPE html>
<html>
<head>
    <title>易信缘后台管理系统</title>
    <!--引入抽取css文件-->
    <#include "../admin/public-css.ftl">
    <link rel="stylesheet" href="${request.contextPath}/admin/css/global.css" media="all">
    <style>
        ::-webkit-input-placeholder { /* WebKit browsers */
            color: #eee;
        }
        :-moz-placeholder { /* Mozilla Firefox 4 to 18 */
            color: #eee;
        }
        ::-moz-placeholder { /* Mozilla Firefox 19+ */
            color: #eee;
        }
        :-ms-input-placeholder { /* Internet Explorer 10+ */
            color: #eee;
        }
    </style>
</head>
<body>
<div class="layui-layout layui-layout-admin" style="border-bottom: solid 5px #1aa094;">
    <div class="layui-header header header-demo">
        <div class="layui-main">
            <div class="admin-login-box">
                <a class="logo" style="left: 0;" href="/page/index">
                    <span style="font-size: 22px;">WEIJD</span>
                </a>
                <div class="admin-side-toggle">
                    <i class="fa fa-bars" aria-hidden="true"></i>
                </div>
                <div class="admin-side-full">
                    <i class="fa fa-life-bouy" aria-hidden="true"></i>
                </div>
            </div>
            <ul class="layui-nav admin-header-item">
                <li class="layui-nav-item">
                    <a href="${request.contextPath}/admin/page/index">
                        刷新一波
                    </a>
                </li>
                <li class="layui-nav-item">
                    <a href="javascript:;" class="admin-header-user">
                        <img id="avatar" src="${request.contextPath}/admin/img/0.jpg" />
                        <span id="userName"></span>
                    </a>
                    <dl class="layui-nav-child">
                        <#--<dd>-->
                            <#--<a href="javascript:personalInfo();"><i class="fa fa-user" aria-hidden="true"></i> 个人资料</a>-->
                        <#--</dd>-->
                        <dd>
                            <a href="javascript:updatePassword();"><i class="fa fa-gear" aria-hidden="true"></i> 修改密码</a>
                        </dd>
                        <#--<dd id="lock">-->
                            <#--<a href="javascript:;">-->
                                <#--<i class="fa fa-lock" aria-hidden="true" style="padding-right: 3px;padding-left: 1px;"></i> 锁屏 (Alt+L)-->
                            <#--</a>-->
                        <#--</dd>-->
                        <dd>
                            <a href="javascript:loginOut()"><i class="fa fa-sign-out" aria-hidden="true"></i> 注销</a>
                        </dd>
                    </dl>
                </li>
            </ul>
        </div>
    </div>
    <div class="layui-side layui-bg-black" id="admin-side">
        <div class="layui-side-scroll" id="admin-navbar-side" lay-filter="side"></div>
    </div>
    <div class="layui-body" style="bottom: 0;border-left: solid 2px #1AA094;" id="admin-body">
        <div class="layui-tab admin-nav-card layui-tab-brief" lay-filter="admin-tab">
            <ul class="layui-tab-title">
                <li class="layui-this">
                    <i class="fa fa-home" aria-hidden="true"></i>
                    <cite>主页面板</cite>
                </li>
            </ul>
            <div class="layui-tab-content" style="min-height: 150px; padding: 5px 0 0 0;">
                <div class="layui-tab-item layui-show">
                    <iframe src="${request.contextPath}/admin/page/main"></iframe>
                </div>
            </div>
        </div>
    </div>
    <div class="layui-footer footer footer-demo" id="admin-footer">
        <div class="layui-main">
            <p>2017 &copy;
                <a href="http://www.magic-beans.cn/">http://www.magic-beans.cn/</a>
            </p>
        </div>
    </div>
    <div class="site-tree-mobile layui-hide">
        <i class="layui-icon">&#xe602;</i>
    </div>
    <div class="site-mobile-shade"></div>

    <#include "../admin/public-js.ftl">
    <script src="${request.contextPath}/admin/js/common/jQuery.md5.js"></script>
    <script src="${request.contextPath}/admin/js/pageJS/index.js"></script>

    <script>

        if (localStorage.getItem("isShowLock")) lock($, layer);

        //填充用户信息
        if (AM.getUserInfo() != null) {
            if (AM.getUserInfo().avatar != null && AM.getUserInfo().avatar != "") {
                $("#avatar").attr("src", AM.ipImg + "/" + AM.getUserInfo().avatar);
                $("#avatar2").attr("src", AM.ipImg + "/" + AM.getUserInfo().avatar);
            }
            $("#userName").html(AM.getUserInfo().showName);
            $("#lockUserName").html(AM.getUserInfo().showName);
        }


        //个人资料
        function personalInfo() {
            layer.open({
                type: 2,
                title: '个人资料',
                shadeClose: true,
                maxmin: true, //开启最大化最小化按钮
                area: ['893px', '600px'],
                anim: 1,
                content: AM.ip + "/page/user/personalDetail?userId=" + AM.getUserInfo().id
            });
        }

        //修改密码
        function updatePassword () {
            layer.open({
                type: 2,
                title: '修改密码',
                shadeClose: true,
                maxmin: false, //开启最大化最小化按钮
                area: ['500px', '500px'],
                anim: 1,
                content: AM.ip + "/page/admins/updatePassword"
            });
        }

        //注销
        function loginOut () {
            layer.confirm('是否确认注销登录？', {
                btn: ['确认','取消'] //按钮
            }, function(){
                AM.ajaxRequestData("post", false, AM.ip + "/admins/logout", {}, function (result) {
                    if(result.flag == 0 && result.code == 200){
                        location.href = AM.ip + "/page/login";
                    }
                });
            }, function(){

            });
        }

        //提供给子页面
        var closeNodeIframe = function () {
            layer.msg('操作成功.', {icon: 1});
            location.reload();
        }

    </script>
</div>
</body>
</html>
