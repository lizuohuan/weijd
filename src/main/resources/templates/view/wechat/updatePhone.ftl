<!DOCTYPE html>
<html>

<head>
    <title>换绑手机号</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no" />
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/font-awesome/css/font-awesome.min.css" />
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/css/common.css" />
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/css/page/updatePhone.css" />
    <script src="${request.contextPath}/js/jquery-2.1.0.js"></script>
    <script src="${request.contextPath}/js/jQuery.md5.js"></script>
    <script src="${request.contextPath}/js/vue.min.js"></script>
    <script src="${request.contextPath}/js/config.js"></script>
</head>
<body>
<div id="app" class="app-content">

    <div class="title">换绑手机号</div>
    <div class="data-form">
        <ul class="app-form">
            <li>
                <label>手机号码</label>
                <input v-model="phone" type="text" placeholder="请输入手机号码" maxlength="11" onKeypress="return (/[\d.]/.test(String.fromCharCode(event.keyCode)))"/>
            </li>
            <li>
                <label>图形验证</label>
                <input v-model="graphQr" type="text" placeholder="请输入图形验证" />
                <div class="graph-qr-btn" @click="getCode"></div>
            </li>
            <li>
                <label>验证码</label>
                <input v-model="qr" type="text" placeholder="请输入验证码" />
                <button class="qr-btn" @click="checkPhone">获取验证码</button>
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
            code: "", //存放验证码
            phone: "",
            affirmPhone: "", //用于判断手机号是否改过
            graphQr: "",
            qr: "",
            isQr: false, //是否获取过验证码
            noteCode: "",
        },
        methods: {
            /**获取验证码**/
            getCode : function () {
                this.code = config.createCode();
                $(".graph-qr-btn").html(this.code);
            },
            /**验证验证码和手机号**/
            checkPhone : function () {
                if (this.phone == "") {
                    config.toast("请输入手机号");
                    return false;
                }
                else if (!config.isNumber.test(this.phone) || this.phone.length < 11 || this.phone.length > 11) {
                    config.toast("请输入11位的手机号");
                    return false;
                }
                else if (this.graphQr == "") {
                    config.toast("请输入图形验证码");
                    return false;
                }
                else if (this.graphQr.toUpperCase() != this.code.toUpperCase()) {
                    config.toast("图形验证码输入错误");
                    return false;
                }
                else {
                    this.getNoteCode();
                }
            },
            /**获取短信验证码**/
            getNoteCode : function () {
                var _self = this; //TODO 为了区分Ajax this 把vue  this 存起来
                config.ajaxRequestData(false, "wechat/sms/sendMessageUpdatePhone", {phone : this.phone}, true, function (result) {
                    if (config.isDebug) {
                        config.toast(result.data);
                    }
                    _self.noteCode = result.data;
                    _self.affirmPhone = _self.phone;
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
                var checkbox = $("input[type=checkbox]").is(":checked");
                if (this.phone == "") {
                    config.toast("请输入手机号");
                    return false;
                }
                else if (!config.isNumber.test(this.phone) || this.phone.length < 11 || this.phone.length > 11) {
                    config.toast("请输入11位的手机号");
                    return false;
                }
                else if (this.graphQr == "") {
                    config.toast("请输入图形验证码");
                    return false;
                }
                else if (this.graphQr.toUpperCase() != this.code.toUpperCase()) {
                    config.toast("图形验证码输入错误");
                    return false;
                }
                else if (!this.isQr) {
                    config.toast("请获取验证码");
                    return false;
                }
                else if (this.qr != this.noteCode) {
                    config.toast("验证码输入错误");
                    return false;
                }
                else if (this.phone != this.affirmPhone) {
                    config.toast("检测手机号被修改");
                    return false;
                }
                config.ajaxRequestData(true, "wechat/user/updatePhone", {
                    phone : this.phone,
                    mobileCode : this.noteCode,
                }, true, function (result) {
                    window.history.back();
                });
            }
        }
    })

    app.getCode(); //默认调用

</script>

</html>
