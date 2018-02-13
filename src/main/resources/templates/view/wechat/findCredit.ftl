<!DOCTYPE html>
<html>

<head>
    <title>查信用</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no" />
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/css/common.css" />
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/css/page/findCredit.css" />
    <script src="${request.contextPath}/js/jquery-2.1.0.js"></script>
    <script src="${request.contextPath}/js/vue.min.js"></script>
    <script src="${request.contextPath}/js/config.js"></script>
</head>
<body>
<div id="app" class="app-content">

    <div class="title">查信用</div>
    <div class="data-form">
        <ul class="app-form">
            <li>
                <label>姓名</label>
                <input v-model="userName" type="text" placeholder="请输入真实姓名" />
            </li>
            <li>
                <label>身份证号</label>
                <input v-model="idCard" type="text" placeholder="请输入身份证号" />
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
            idCard: "",
            userName: ""
        },
        methods: {
            /**提交数据**/
            submit : function () {
                if (this.userName == "") {
                    config.toast("请输入真实姓名");
                    return false;
                }
                else if (this.idCard == "") {
                    config.toast("请输入身份证号");
                    return false;
                }
                else if (!config.isIdentityCard.test(this.idCard)) {
                    config.toast("请输入正确的身份证号");
                    return false;
                }
                sessionStorage.setItem("userName", this.userName);
                sessionStorage.setItem("idCard", this.idCard);
                location.href = config.ip + "wechat/page/personCredit";
            }
        }
    })

</script>

</html>
