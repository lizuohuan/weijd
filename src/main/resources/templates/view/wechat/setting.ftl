<!DOCTYPE html>
<html>

<head>
    <title>设置</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no" />
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/css/common.css" />
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/css/page/setting.css" />
    <script src="${request.contextPath}/js/jquery-2.1.0.js"></script>
    <script src="${request.contextPath}/js/vue.min.js"></script>
    <script src="${request.contextPath}/js/config.js"></script>
</head>
<body>
<div id="app" class="app-content">

    <ul class="app-list-menu">
        <li><a @click="setUrl('myCard')">我的名片</a></li>
        <li><a @click="setUrl('updatePassword')">修改支付密码</a></li>
        <li><a @click="setUrl('updatePhone')">换绑手机号</a></li>
        <li><a @click="setUrl('forgetPassword')">忘记密码</a></li>
    </ul>

</div>
</body>
<script>

    var app = new Vue({
        el: '#app',
        data: {
        },
        methods: {
            /**跳转**/
            setUrl : function (url) {
                location.href = config.ip + "wechat/page/" + url;
            }
        }
    });

</script>

</html>
