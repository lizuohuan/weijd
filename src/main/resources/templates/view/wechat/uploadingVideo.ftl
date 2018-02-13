<!DOCTYPE html>
<html>

<head>
    <title>上传视频</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no" />
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/css/common.css" />
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/css/page/uploadingVideo.css" />
    <script src="${request.contextPath}/js/jquery-2.1.0.js"></script>
    <script src="${request.contextPath}/js/jquery.form.js"></script>
    <script src="${request.contextPath}/js/vue.min.js"></script>
    <script src="${request.contextPath}/js/config.js"></script>
</head>
<body>
<div id="app" class="app-content">

    <div class="div-bg">
        <div class="user-name">出借人：{{orderInfo.fromUserName}}</div>
        <div class="user-time">借款时间：{{orderInfo.createTime}}</div>
        <div class="title">温馨提示</div>
        <div class="content">录制视频时请使用前置摄像头，视频录制时间控制在25秒以内!请正对镜头说以下内容</div>
        <div class="read">本人{{userInfo.userName}}，于{{orderInfo.createTime}}，向{{orderInfo.fromUserName}}借款人民币{{orderInfo.price}}元，约定于{{orderInfo.repaymentTime}}归还，逾期不还，产生的逾期费用由本人{{orderInfo.toUserName}}承担</div>
        <div class="course">按照以上内容拍摄视频，如安卓手机上传视频不成功，请参考<span>上传教程</span></div>
        <div class="video-div">
            <video id="video" class="video" src=""
                   preload="auto"
                   x5-playsinline="true"
                   webkit-playsinline="true"
                   playsinline="true"
            ></video>
            <div class="play-icon" onclick="document.getElementById('video').play()"></div>
        </div>

        <button class="submit-btn" onclick="$('#file').click()">上传</button>
    </div>


    <form id="uploadVideo" method="post" enctype="multipart/form-data">
        <input class="hide" name="file" id="file" type="file" accept="video/*" capture="camcorder">
    </form>

</div>
<div class="share-hint"><div class="share-arrows"></div></div>

</body>
<script>

    var app = new Vue({
        el: '#app',
        data: {
            userInfo: config.getUserInfo(),
            videoUrl: "",
            orderInfo: [],
            orderId: config.getUrlParam("orderId"),
            type: config.getUrlParam("type"),
        },
        methods: {
            /**获取订单详情**/
            getOrderInfo : function () {
                var _self = this;
                config.ajaxRequestData(false, "wechat/order/info", {id : this.orderId}, true, function (result) {
                    _self.orderInfo = result.data;
                    _self.orderInfo.createTime = new Date(_self.orderInfo.createTime).format("yyyy-MM-dd");
                    _self.orderInfo.repaymentTime = new Date(_self.orderInfo.repaymentTime).format("yyyy-MM-dd");
                });
            },
        }
    });
    app.getOrderInfo();

    $('#file').change(function(){
        $("body").append(config.showLoading("上传中..."));
        var file = this.files[0];
        if(window.FileReader) {
            var fr = new FileReader();
            fr.onloadend = function(e) {
                $("#uploadVideo").ajaxSubmit({
                    type: "POST",
                    url: config.ip + 'res/upload',
                    success: function(json) {
                        config.log(json);
                        if (json.code == 200) {
                            if (config.getUrlParam("type") == 1) {
                                var parameter = {
                                    id: config.getUrlParam("orderId"),
                                    status: 1,
                                    type: config.getUrlParam("type"),
                                    videoUrl: json.data.url,
                                }
                                config.ajaxRequestData(false, "wechat/order/update", parameter, true, function (result) {
                                    config.toast("上传成功");
                                    setTimeout(function () {
                                        location.href = config.ip + "wechat/page/index";
                                    }, 1000);
                                });
                            }
                            else {
                                sessionStorage.setItem("videoUrl", json.data.url);
                                $("#video").attr("src", config.ip + json.data.url);
                                location.href = config.ip + "wechat/page/borrowingDetail/" + config.getUrlParam("orderId") + "?videoUrl=" + json.data.url;
                            }
                        } else {
                            config.toast(json.msg);
                        }
                        config.hideLoading();
                    }
                });
            };
            fr.readAsDataURL(file);
        }
    });

</script>

</html>
