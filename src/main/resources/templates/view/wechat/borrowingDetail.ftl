<!DOCTYPE html>
<html>

<head>
    <title>借款明细</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no" />
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/font-awesome/css/font-awesome.min.css" />
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/css/common.css" />
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/css/page/borrowingDetail.css" />
    <script src="${request.contextPath}/js/jquery-2.1.0.js"></script>
    <script src="${request.contextPath}/js/jQuery.md5.js"></script>
    <script src="${request.contextPath}/js/angular.min.js"></script>
    <script src="${request.contextPath}/js/config.js"></script>
</head>
<body>
<div ng-app="webApp" ng-controller="appController" class="app-content">
    <input value="${orderId}" id="orderId" type="hidden">
    <div class="top">
        <div class="circle"></div>
        <span>易信缘-借款单</span>
        <a ng-click="findUserCredit()">查看平台信用<span>&gt;</span></a>
    </div>
    <div class="borrowing-detail">
        <div class="detail">
            <div class="user-names">
                <div class="user-item">
                    <span class="icon-red"></span>
                    <div class="hint">借款人</div>
                    <div class="name" id="toUserName">{{orderInfo.toUserName == null ? '暂未确认' : orderInfo.toUserName}}</div>
                </div>
                <div class="user-item">
                    <i class="arrows"></i>
                </div>
                <div class="user-item">
                    <span class="icon-blue"></span>
                    <div class="hint">出借人</div>
                    <div class="name" id="fromUserName">{{orderInfo.fromUserName == null ? '暂未放款' : orderInfo.fromUserName}}</div>
                </div>
            </div>
            <div class="money-bar">
                <span>待还金额</span>
                <div class="money">￥{{orderInfo.price + orderInfo.interest + orderInfo.managementCost | number : 2}}</div>
            </div>
            <div class="profit-bar">
                <div ng-class="{true:'profit-item',false:'profit-item profit-item2'}[orderInfo.managementCost == 0]">
                    <div class="hint-title">借款金额</div>
                    <div class="content">{{orderInfo.price}}</div>
                </div>
                <div ng-class="{true:'profit-item',false:'profit-item profit-item2'}[orderInfo.managementCost == 0]">
                    <div class="hint-title">年化利率</div>
                    <div class="content">{{orderInfo.interestName}}%</div>
                </div>
                <div ng-if="orderInfo.managementCost > 0" class="profit-item profit-item2">
                    <div class="hint-title">逾期管理费</div>
                    <div class="content">{{orderInfo.managementCost | number : 2}}</div>
                </div>
                <div ng-class="{true:'profit-item',false:'profit-item profit-item2'}[orderInfo.managementCost == 0]">
                    <div class="hint-title">借/<span ng-if="orderInfo.status != 7">还</span><span ng-if="orderInfo.status == 7">延</span>款日期</div>
                    <div class="content">
                        <div>{{orderInfo.createTime}}</div>
                        <div ng-if="orderInfo.status != 7">{{orderInfo.repaymentTime}}</div>
                        <div ng-if="orderInfo.status == 7">{{orderInfo.postponeRepaymentTime}}</div>
                    </div>
                </div>

            </div>

            <#--<div class="transcribe-video" ng-if="orderInfo.type == 1 && orderInfo.status == 0 && userInfo.id != orderInfo.toUserId">-->
                <#--<label>-->
                    <#--是否录制视频-->
                    <#--<input type="checkbox" class="fa app-checkbox isUploadVideo"/>-->
                <#--</label>-->
            <#--</div>-->

        </div>

        <#--<div id="videoDiv" ng-show="(orderInfo.isUploadVideo == 1 && orderInfo.videoUrl != null) || (userInfo.id == orderInfo.fromUserId && orderInfo.status == 1)">-->
            <#--<div class="border"></div>-->
            <#--<div class="detail">-->
                <#--<div class="video-div">-->
                    <#--<video class="video" src="" preload="auto" x5-playsinline="true" webkit-playsinline="true" playsinline="true" ></video>-->
                    <#--<div ng-if="isVideoIcon" class="play-icon" ng-click="videoPlay()"></div>-->
                <#--</div>-->
            <#--</div>-->
        <#--</div>-->

        <!--出借--情况-->
        <div ng-if="orderInfo.type == 0">
            <!-- 出借人 -->
            <div ng-if="userInfo.id == orderInfo.fromUserId && orderInfo.toUserId != null && orderInfo.status == 1">
                <div class="fix-buttons">
                    <div class="fix-left"><button ng-click="updateStatus(0, 2)">驳回</button></div>
                    <div class="fix-right"><button ng-click="updateStatus(1, 3)">确认</button></div>
                </div>
            </div>

            <!-- 借款人 -- 永远都是 toUserId 给服务费 -->
            <div ng-if="userInfo.id != orderInfo.fromUserId && orderInfo.toUserId == null && (orderInfo.status == 0 ||orderInfo.status == 1)">
                <div ng-if="orderInfo.serviceFee <= 0" class="fix-buttons">
                    <div class="fix-left"><button ng-click="updateStatus(0, 2)">驳回</button></div>
                    <div class="fix-right"><button ng-click="updateStatus(1, 3)">确认</button></div>
                </div>
                <div ng-if="orderInfo.serviceFee > 0" class="fix-buttons">
                    <button class="paymentBtn" ng-click="weChatPayment()">支付服务费</button>
                </div>
            </div>

        </div>

        <!--借入--情况-->
        <div ng-if="orderInfo.type == 1">

            <!-- 借款人同意 -- 出钱的人同意 -->
            <div ng-if="userInfo.id != orderInfo.toUserId && orderInfo.fromUserId == null && orderInfo.status == 1 && orderInfo.payStatus == 1">
                <div class="fix-buttons">
                    <div class="fix-left"><button ng-click="updateStatus(0, 2)">驳回</button></div>
                    <div class="fix-right"><button ng-click="updateStatus(1, 3)">确认</button></div>
                </div>
            </div>

            <!-- 借款人支付 -->
            <div ng-if="userInfo.id == orderInfo.toUserId && (orderInfo.status == 0 || orderInfo.status == 1) && orderInfo.payStatus == 0">
                <div class="fix-buttons">
                    <button class="paymentBtn" ng-click="weChatPayment()">支付服务费</button>
                </div>
            </div>

        </div>

        <!-- 延期还款审核 -->
        <div ng-if="userInfo.id == orderInfo.fromUserId && orderInfo.status == 7">
            <div class="fix-buttons">
                <div class="fix-left"><button ng-click="updateStatus(0, 6)">驳回</button></div>
                <div class="fix-right"><button ng-click="updateStatus(1, 3)">确认</button></div>
            </div>
        </div>

        <!-- 确认收款审核 -->
        <div ng-if="userInfo.id == orderInfo.fromUserId && orderInfo.status == 4">
            <div class="fix-buttons">
                <div class="fix-left"><button ng-click="updateStatus(0, 3)">驳回</button></div>
                <div class="fix-right"><button ng-click="updateStatus(1, 5)">确认</button></div>
            </div>
        </div>

    </div>
</div>

<input type="hidden" id="hintPrice">
<input type="hidden" id="hintTime">
<input type="hidden" id="hintInterestRate">

<div class="share-hint"><div class="share-arrows"></div></div>
</body>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script>

    var jsSignList = config.getWxJsSign(); //微信api签名
    wx.config({
        debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
        appId: jsSignList.appId, // 必填，公众号的唯一标识
        timestamp: jsSignList.timestamp, // 必填，生成签名的时间戳
        nonceStr: jsSignList.noncestr, // 必填，生成签名的随机串
        signature: jsSignList.signature,// 必填，签名，见附录1
        jsApiList: ["checkJsApi", "onMenuShareAppMessage", "hideMenuItems"] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
    });

    wx.ready(function() {
        wx.checkJsApi({
            jsApiList: ['onMenuShareAppMessage'],
            success: function (res) {
                //alert(JSON.stringify(res));
            }
        });
        wx.hideMenuItems({
            menuList: ["menuItem:exposeArticle", "menuItem:setFont",
                "menuItem:share:timeline", "menuItem:share:qq", "menuItem:share:QZone",
                "menuItem:copyUrl", "menuItem:openWithQQBrowser", "menuItem:openWithQQBrowser"]
        });

        //微信好友
        var shareTitle = "我在易信缘发起一个借款邀请，快来审批吧！";
        var shareImg = "http://www.cdxinqu.cn/weijd/img/icon.png";
        var desc = "金额：" + $("#hintPrice").val() + ",时长：" + $("#hintTime").val() + ",年化利率：" + $("#hintInterestRate").val() + "%";
        var link = location.href;

        wx.onMenuShareAppMessage({
            title : shareTitle,
            desc : desc,
            link : link,
            imgUrl : shareImg,
            type : "link",
            success : function() {
                $(".share-hint").hide();
                config.alert("温馨提示", "分享成功", "确定", function () {
                    location.href = config.ip + "wechat/page/index";
                });
            },
            cancel : function() {
                config.toast("取消分享");
            },
        });
    });

    var webApp = angular.module('webApp', []);
    webApp.controller("appController", function($scope) {
        $scope.userInfo = config.getUserInfo();
        $scope.orderId = Number($("#orderId").val().replace(",", ""));
        //alert("orderId: " + $scope.orderId);
        config.log($scope.userInfo);
        $scope.orderInfo = null;
        $scope.isVideoIcon = true;
        $scope.isUploadVideo = 0;

        /**绑定遮罩点击事件**/
        $scope.bindShade = function () {
            $(".app-shade").on("click", function () {
                $(".app-shade").removeClass("app-shade-show");
                $(".share-hint").hide();
                setTimeout(function () {
                    config.hideShade();
                }, 200);
            });
        }

        /**获取订单详情**/
        $scope.getOrderInfo = function () {
            config.ajaxRequestData(false, "wechat/order/info", {id : $scope.orderId}, true, function (result) {
                $scope.orderInfo = result.data;
                $scope.orderInfo.createTime = new Date($scope.orderInfo.createTime).format("yyyy-MM-dd");
                $scope.orderInfo.repaymentTime = new Date($scope.orderInfo.repaymentTime).format("yyyy-MM-dd");
                if ($scope.orderInfo.postponeRepaymentTime != null) {
                    $scope.orderInfo.postponeRepaymentTime = new Date($scope.orderInfo.postponeRepaymentTime).format("yyyy-MM-dd");
                }
                $scope.orderInfo.videoUrl = sessionStorage.getItem("videoUrl") == null ? $scope.orderInfo.videoUrl : sessionStorage.getItem("videoUrl");
                $("#videoDiv").find("video").attr("src", config.ip + $scope.orderInfo.videoUrl);

                //出借
                if ($scope.userInfo.id == $scope.orderInfo.fromUserId && ($scope.orderInfo.status == 0 || $scope.orderInfo.status == 1) && ($scope.orderInfo.payStatus == 0 || $scope.orderInfo.payStatus == 1)) {
                    config.showShade();
                    $(".share-hint").html("<div class='share-arrows'></div>点击右上角发给借款人").show();
                    $scope.bindShade();
                }

                //借入
                if ($scope.userInfo.id == $scope.orderInfo.toUserId && $scope.orderInfo.status == 1 && $scope.orderInfo.payStatus == 1) {
                    config.showShade();
                    $(".share-hint").html("<div class='share-arrows'></div>点击右上角发给出借人").show();
                    $scope.bindShade();
                }

                $("#hintPrice").val($scope.orderInfo.price);
                $("#hintTime").val($scope.orderInfo.remainDays);
                $("#hintInterestRate").val($scope.orderInfo.interestName);

            });
        }
        $scope.getOrderInfo();

        /**修改状态--作为提示**/
        $scope.updateStatus = function (type, status) {
            $("body").scrollTop(0, 0);
            //驳回按钮
            if (type == 0) {
                if (status == 6) {
                    config.confirm("温馨提示", "是否拒绝延期还款？", ["取消", "确认"], function (index) {
                        if (index == 1) {
                            $scope.setStatus(status, null);
                        }
                    });
                }
                else if ($scope.userInfo.id == $scope.orderInfo.fromUserId && status == 3) {
                    config.confirm("温馨提示", "是否拒绝收款？", ["取消", "确认"], function (index) {
                        if (index == 1) {
                            $scope.setStatus(status, null);
                        }
                    });
                }
                else {
                    config.confirm("温馨提示", "是否拒绝借款？", ["取消", "确认"], function (index) {
                        if (index == 1) {
                            $scope.setStatus(status, null);
                        }
                    });
                }
            }
            //确定按钮
            else if (type == 1){
                if ($scope.orderInfo.status == 7 && status == 3) {
                    //确认延期
                    config.prompt("支付密码验证", "请输入支付密码", ["取消","确定"], "password", function (index) {
                        if (index == 1) {
                            if ($(".app-modal-input").val() == "") {
                                config.toast("请输入支付密码");
                            }
                            else {
                                $scope.setStatus(status, null);
                            }
                        }
                    });
                }
                else if (status == 3) {
                    config.prompt("支付密码验证", "请输入支付密码", ["取消","确定"], "password", function (index) {
                        if (index == 1) {
                            if ($(".app-modal-input").val() == "") {
                                config.toast("请输入支付密码");
                            }
                            else {
                                $scope.setStatus(status, $.md5($(".app-modal-input").val()));
                            }
                        }
                    });
                }
                else if ($scope.userInfo.id == $scope.orderInfo.fromUserId) {
                    config.prompt("支付密码验证", "请输入支付密码", ["取消","确定"], "password", function (index) {
                        if (index == 1) {
                            if ($(".app-modal-input").val() == "") {
                                config.toast("请输入支付密码");
                            }
                            else {
                                $scope.setStatus(status, $.md5($(".app-modal-input").val()));
                            }
                        }
                    });
                }
                else {
                    config.confirm("温馨提示", "是否确认借款？", ["取消", "确认"], function (index) {
                        if (index == 1) {
                            $scope.setStatus(status, null);
                        }
                    });
                }
            }
        }

        /**修改状态**/
        $scope.setStatus = function (status, payPwd) {
            config.toast("status:" + status);
            //删除缓存videoUrl
            if ($scope.userInfo.id != $scope.orderInfo.toUserId && $scope.orderInfo.fromUserId == null && $scope.orderInfo.status == 0) {
                sessionStorage.removeItem("videoUrl");
            }
            var parameter = {
                id: $scope.orderId,
                status: status,
                type: $scope.orderInfo.type,
                videoUrl: sessionStorage.getItem("videoUrl"),
                payPwd: payPwd,
            }
            if ($scope.orderInfo.type == 1 && $scope.orderInfo.status == 0 && $scope.userInfo.id != $scope.orderInfo.toUserId) {
                var isUploadVideo = $(".isUploadVideo").is(":checked");
                parameter.isUploadVideo = isUploadVideo ? 1 : 0;
            }
            config.ajaxRequestData(false, "wechat/order/update", parameter, true, function (result) {
                config.toast(result.msg);

                $("#videoDiv").hide();
                $(".fix-buttons").hide();
                $(".transcribe-video").hide();

                //借款人同意
                if ($scope.userInfo.id != $scope.orderInfo.toUserId && $scope.orderInfo.fromUserId == null && $scope.orderInfo.status == 0) {
                    location.href = config.ip + "wechat/page/index";
                }
                else if (status == 0) { // || status == 1
                    var temp = setTimeout(function () {
                        config.showShade();
                        if ($scope.orderInfo.type == 0) {
                            $("#toUserName").html($scope.userInfo.userName);
                            $(".share-hint").html("<div class='share-arrows'></div>点击右上角发给出借人").show();
                        }
                        else {
                            $("#fromUserName").html($scope.userInfo.userName);
                            $(".share-hint").html("<div class='share-arrows'></div>点击右上角发给借款人").show();
                        }
                        $scope.bindShade();
                        clearTimeout(temp);
                    }, 300);
                }
                else if (($scope.orderInfo.status == 7 && status == 3) || ($scope.orderInfo.status == 7 && status == 6)) {
                    window.history.back();
                }
                else if (status == 2 || status == 3 || status == 5) {
                    location.href = config.ip + "wechat/page/index";
                }
            });
        }

        /**视频播放**/
        $scope.videoPlay = function () {
            var video = document.querySelector("video");
            video.play();
            $scope.isVideoIcon = false;
        }

        /**查询信用**/
        $scope.findUserCredit = function () {
            if ($scope.orderInfo.toUserId == null) {
                config.toast("借款人暂未确认");
            }
            else {
                location.href = config.ip + "wechat/page/personCredit?userId=" + $scope.orderInfo.toUserId;
            }
        }

        //微信支付
        $scope.weChatPayment = function () {
            config.ajaxRequestData(false, "jsPay/sign", {orderId : $scope.orderId}, true, function (result) {
                config.wechatPay(result.data.appId, result.data.timeStamp, result.data.nonceStr, result.data.package, result.data.signType, result.data.sign, function (json) {
                    if(json.err_msg == "get_brand_wcpay_request:ok" ) {
                        config.alert("温馨提示", "支付成功", "确定", function () {
                            //TODO 避免微信缓存
                            window.location.href = window.location.href+"?id="+10000*Math.random();
                        });
                    }
                    else {
                        config.toast("支付失败.");
                    }
                });
            });
        }

    });


</script>

</html>
