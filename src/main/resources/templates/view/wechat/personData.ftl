<!DOCTYPE html>
<html>

<head>
    <title>个人资料</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no" />
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/css/common.css" />
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/css/page/personData.css" />
    <script src="${request.contextPath}/js/jquery-2.1.0.js"></script>
    <script src="${request.contextPath}/js/vue.min.js"></script>
    <script src="${request.contextPath}/js/config.js"></script>
</head>
<body>
<div id="app" class="app-content">

    <div class="title">个人资料</div>
    <div class="data-form">
        <ul class="app-form">
            <li>
                <label>姓名</label>
                <input v-model="userInfo.userName" class="input" id="userName" type="text" placeholder="请输入真实姓名" maxlength="20"/>
            </li>
            <li>
                <label>身份证号</label>
                <input v-model="userInfo.idCard" id="idCard" class="input" type="text" placeholder="请输入身份证号" maxlength="18"/>
            </li>
            <li>
                <label>手机号码</label>
                <input id="phone" type="text" class="input" placeholder="请输入手机号码" readonly/>
            </li>
            <li id="qrBar">
                <label>验证码</label>
                <input v-model="qr" type="text" placeholder="请输入验证码" maxlength="6"/>
                <button class="qr-btn" @click="getNoteCode">获取验证码</button>
            </li>
            <li>
                <label>微信号</label>
                <input v-model="userInfo.wx" id="wx" type="text" placeholder="请输入微信号" maxlength="20"/>
            </li>
            <li>
                <label>QQ号</label>
                <input v-model="userInfo.qq" id="qq" type="text" placeholder="请输入QQ号" maxlength="20"/>
            </li>
        </ul>
    </div>

    <div class="button-bar">
        <button class="submit-btn" @click="submit">提交</button>
    </div>

    <input value="${orderId}" id="orderId" type="hidden">

</div>
</body>
<script>

    var app = new Vue({
        el: '#app',
        data: {
            userInfo : [],
            qr: "",
            isQr: false, //是否获取过验证码
            noteCode: "",
            isIdCard: false,
            orderId: Number($("#orderId").val().replace(",", "")),
        },
        methods: {
            /**获取用户信息**/
            getInfo : function () {
                var _self = this; //TODO 为了区分Ajax this 把vue  this 存起来
                config.ajaxRequestData(false, "wechat/user/info", {}, true, function (result) {
                    _self.userInfo = result.data;
                    localStorage.setItem("userInfo", JSON.stringify(result.data));
                    if (_self.userInfo.idCard != "" && _self.userInfo.idCard != null) {
                        _self.isIdCard = true;
                        $("#userName").val(_self.userInfo.userName);
                        $("#idCard").val(_self.userInfo.idCard);
                        $("#wx").val(_self.userInfo.wx);
                        $("#qq").val(_self.userInfo.qq);
                        $(".input").attr("readonly", true);
                        $(".input").attr("onfocus", "this.blur();");
                        $("#qrBar").hide();
                    }
                    $("#phone").val(_self.userInfo.phone);
                });
            },
            /**获取短信验证码**/
            getNoteCode : function () {
                var _self = this;
                config.ajaxRequestData(false, "wechat/sms/sendMessageUpdateInfo", {phone : _self.userInfo.phone}, true, function (result) {
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
                var _self = this;
                if (!this.isIdCard) {
                    if (this.userInfo.userName == "") {
                        config.toast("请输入真实姓名");
                        return false;
                    }
                    else if (!config.isChinese.test(this.userInfo.userName)) {
                        config.toast("请输入正确的真实姓名");
                        return false;
                    }
                    else if (this.userInfo.idCard == "") {
                        config.toast("请输入身份证号");
                        return false;
                    }
                    else if (!config.isIdentityCard.test(this.userInfo.idCard)) {
                        config.toast("请输入正确的身份证号");
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
                    config.ajaxRequestData(false, "wechat/user/updateInfo", {
                        userName : this.userInfo.userName,
                        idCard : this.userInfo.idCard,
                        qq : this.userInfo.qq,
                        wx : this.userInfo.wx,
                        id : this.userInfo.id,
                        mobileCode : this.noteCode,
                    }, true, function () {
                        if (_self.orderId == null || _self.orderId == "") {
                            location.href = config.ip + "wechat/page/index";
                        }
                        else {
                            location.href = config.ip + "wechat/page/borrowingDetail/" + _self.orderId;
                        }
                    });
                }
                else {
                    if (this.userInfo.qq == "" && this.userInfo.wx == "") {
                        location.href = config.ip + "wechat/page/index";
                    }
                    else {
                        config.ajaxRequestData(false, "wechat/user/update", {
                            qq : this.userInfo.qq,
                            wx : this.userInfo.wx,
                            id : this.userInfo.id,
                        }, true, function () {
                            if (_self.orderId == null || _self.orderId == "") {
                                location.href = config.ip + "wechat/page/index";
                            }
                            else {
                                location.href = config.ip + "wechat/page/borrowingDetail/" + _self.orderId;
                            }
                        });
                    }
                }
            }
        }
    });
    app.getInfo();
</script>

</html>
