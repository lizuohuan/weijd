<!DOCTYPE html>
<html>

<head>
    <title>我的名片</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no" />
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/css/common.css" />
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/css/page/myCard.css" />
    <script src="${request.contextPath}/js/jquery-2.1.0.js"></script>
    <script src="${request.contextPath}/js/vue.min.js"></script>
    <script src="${request.contextPath}/js/config.js"></script>
</head>
<body>
<div id="app" class="app-content">

    <ul class="app-list-menu">
        <li><a @click="setUrl('personData')">个人资料<span class="hint">必填</span></a></li>
        <li><a @click="setUrl('complementData')">补充资料<span class="hint">选填</span></a></li>
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
