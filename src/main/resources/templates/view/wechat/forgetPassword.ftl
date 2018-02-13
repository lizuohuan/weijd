<!DOCTYPE html>
<html>

<head>
    <title>忘记密码</title>
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
                <label>手机号码</label>
                <input id="phone" v-model="phone" type="text" class="input" placeholder="请输入手机号码" readonly/>
            </li>
            <li id="qrBar">
                <label>验证码</label>
                <input v-model="qr" type="text" placeholder="请输入验证码" maxlength="6"/>
                <button class="qr-btn" @click="getNoteCode">获取验证码</button>
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
            newPwd: "",
            newPwd2: "",
            qr: "",
            isQr: false, //是否获取过验证码
            noteCode: "",
            phone: config.getUserInfo().phone,
        },
        methods: {
            /**获取短信验证码**/
            getNoteCode : function () {
                var _self = this;
                config.ajaxRequestData(false, "wechat/sms/sendMessageUpdatePayPwd", {phone : _self.phone}, true, function (result) {
                    if (config.isDebug) {
                        config.toast(result.data);
                    }
                    _self.noteCode = result.data;
                    _self.isQr = true;
                    var count = 59, setIntervalId = 0;
                    $(".qr-btn").html(count + "s").attr("disabled", true);
                    setIntervalId = setInterval(function () {
                        count --;
                        if (count <= 0) {
                            $(".qr-btn").html("获取验证码").attr("disabled", false);
                            clearInterval(setIntervalId);
                        }
                        else {
                            $(".qr-btn").html(count + "s").attr("disabled", true);
                        }
                    }, 1000);
                });
            },
            /**提交数据**/
            submit : function () {
                if (!this.isQr) {
                    config.toast("请获取验证码");
                    return false;
                }
                else if (this.qr != this.noteCode) {
                    config.toast("验证码输入错误");
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
                var isEmoji = false;
                $(".app-form").find("input").each(function () {
                    if (config.isEmoji.test($(this).val())) {
                        isEmoji = true;
                    }
                });
                if (isEmoji) {
                    config.toast("不支持emoji表情");
                    return false;
                }
                config.ajaxRequestData(true, "wechat/user/updatePwd2", {
                    phone: this.phone,
                    mobileCode: this.noteCode,
                    newPwd : $.md5(this.newPwd),
                }, true, function (result) {
                    location.href = config.ip + "wechat/page/index";
                });
            }
        }
    })

</script>

</html>
