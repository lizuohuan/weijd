<!DOCTYPE html>
<html>

<head>
    <title>修改支付密码</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no" />
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/css/common.css" />
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/css/page/updatePassword.css" />
    <script src="${request.contextPath}/js/jquery-2.1.0.js"></script>
    <script src="${request.contextPath}/js/jQuery.md5.js"></script>
    <script src="${request.contextPath}/js/vue.min.js"></script>
    <script src="${request.contextPath}/js/config.js"></script>
</head>
<body>
<div id="app" class="app-content">

    <div class="title">修改支付密码</div>
    <div class="data-form">
        <ul class="app-form">
            <li>
                <label>原支付密码</label>
                <input v-model="oldPwd" type="password" placeholder="请输入原支付密码" maxlength="6"/>
            </li>
            <li>
                <label>新支付密码</label>
                <input v-model="newPwd" type="password" placeholder="请输入6位纯数字的支付密码" maxlength="6"/>
            </li>
            <li>
                <label>确认新密码</label>
                <input v-model="newPwd2" type="password" placeholder="请输入6位纯数字的支付密码" maxlength="6"/>
            </li>
        </ul>
    </div>

    <div class="button-bar">
        <button class="submit-btn" @click="submit">提交</button>
    </div>

</div>
</body>
<script>
    var app = new Vue({
        el: '#app',
        data: {
            oldPwd: "",
            newPwd: "",
            newPwd2: "",
        },
        methods: {
            /**提交数据**/
            submit : function () {
                if (this.oldPwd == "") {
                    config.toast("请输入原支付密码");
                    return false;
                }
                else if (this.newPwd == "") {
                    config.toast("请输入新支付密码");
                    return false;
                }
                else if (!config.isNumber.test(this.newPwd) || this.newPwd.length < 6 || this.newPwd.length > 6) {
                    config.toast("请输入6位纯数字的支付密码");
                    return false;
                }
                else if (this.newPwd2 == "") {
                    config.toast("请输入确认新密码");
                    return false;
                }
                else if (!config.isNumber.test(this.newPwd2) || this.newPwd2.length < 6 || this.newPwd2.length > 6) {
                    config.toast("请输入6位纯数字的支付密码");
                    return false;
                }
                else if (this.newPwd != this.newPwd2) {
                    config.toast("两次密码不一致");
                    return false;
                }
                config.ajaxRequestData(true, "wechat/user/updatePwd1", {
                    oldPwd : $.md5(this.oldPwd),
                    newPwd : $.md5(this.newPwd),
                }, true, function (result) {
                    location.href = config.ip + "wechat/page/index";
                });
            }
        }
    })

</script>

</html>
