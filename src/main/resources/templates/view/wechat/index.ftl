<!DOCTYPE html>
<html>

<head>
    <title>我的</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no" />
    <link rel="stylesheet" type="text/css"  href="${request.contextPath}/css/common.css" />
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/css/page/index.css" />
    <script src="${request.contextPath}/js/jquery-2.1.0.js"></script>
    <script src="${request.contextPath}/js/vue.min.js"></script>
    <script src="${request.contextPath}/js/config.js"></script>
</head>
<body>
<div id="app">
    <div class="app-content">
        <div class="my-head">
            <div class="bg">
                <div class="bg-blur user-avatar"></div>
            </div>
            <div class="user-detail">
                <div class="user-head-img user-avatar"></div>
                <div class="user-name" v-if="userInfo.idCard == null || userInfo.idCard == ''">暂未实名</div>
                <div class="user-name" v-if="userInfo.idCard != null && userInfo.idCard != ''">{{userInfo.userName}}</div>
                <div class="user-integral">
                    <i></i>
                    <span>{{userInfo.creditPoints == null ? 0 : userInfo.creditPoints}}</span>
                </div>
                <div class="user-phone">{{userInfo.phone}}</div>
            </div>
            <div class="user-info">
                <div>
                    <i></i>
                    <span class="hint">待还</span>
                    <span class="number">{{userInfo.nowToBePaidJoinPrice == null ? 0 : userInfo.nowToBePaidJoinPrice}}</span>
                </div>
                <div>
                    <i></i>
                    <span class="hint">待收</span>
                    <span class="number">{{userInfo.nowToBePaidOutPrice == null ? 0 : userInfo.nowToBePaidOutPrice}}</span>
                </div>
            </div>
        </div>

        <div class="user-menu">
            <ul class="app-list-menu">
                <li><a @click="setUrl('myBorrow', 1)"><i></i><span>待还</span></a></li>
                <li><a @click="setUrl('findCredit', 1)"><i></i><span>查信用</span></a></li>
                <li><a @click="setUrl('myBorrowOut', 1)"><i></i><span>待收</span></a></li>
                <li><a @click="setUrl('setting', 0)"><i></i><span>设置</span></a></li>
                <li><a @click="setUrl('feedback', 0)"><i></i><span style="color: red;;">举报</span></a></li>
            </ul>
        </div>

    </div>

    <div class="fix-buttons">
        <div class="fix-left">
            <button @click="setUrl('loanApplication?type=1', 1)"><i></i><span>求借款</span></button>
        </div>
        <div class="fix-right">
            <button @click="setUrl('loanApplication?type=0', 1)"><i></i><span>去出借</span></button>
        </div>
    </div>
</div>
</body>
<script>
    config.reload();
    var app = new Vue({
        el: '#app',
        data: {
            userInfo : [],
        },
        methods: {
            /**获取用户信息**/
            getInfo : function () {
                var _self = this; //TODO 为了区分Ajax this 把vue  this 存起来
                config.ajaxRequestData(false, "wechat/user/info", {}, true, function (result) {
                    _self.userInfo = result.data;
                    $(".user-avatar").css("background", "url(" + _self.userInfo.avatar + ")");
                    localStorage.setItem("userInfo", JSON.stringify(_self.userInfo));
                });
            },
            /**跳转**/
            setUrl : function (url, type) {
                var _self = this;
                if (type == 1) {
                    if (this.userInfo.idCard == null || this.userInfo.idCard == "") {
                        location.href = config.ip + "wechat/page/personData";
                    }
                    else {
                        if (url == "loanApplication?type=0") {
                            this.getInfo();
                            if (this.userInfo.isFromUser == null || this.userInfo.isFromUser == 0 || this.userInfo.isFromUser == 3) {
                                config.prompt("是否立即申请为出借人？","请输入申请内容", ["取消", "确认"], "text", function (index) {
                                    if (index == 1) {
                                        if ($(".app-modal-input").val() == "") {
                                            config.toast("请输入申请内容");
                                        }
                                        else {
                                            config.ajaxRequestData(false, "wechat/user/update", {
                                                isFromUser : 2,
                                                remark : $(".app-modal-input").val()
                                            }, true, function () {
                                                config.toast("提交成功，请耐心等待审核");
                                            });
                                        }
                                    }
                                });
                                $(".app-modal-input").css("height", "80px");
                            }
                            else if (this.userInfo.isFromUser == 2) {
                                config.toast("审核中，请稍后.");
                            }
                            else if (this.userInfo.isFromUser == 1) {
                                location.href = config.ip + "wechat/page/" + url;
                            }
                        }
                        else {
                            location.href = config.ip + "wechat/page/" + url;
                        }
                    }
                }
                else {
                    location.href = config.ip + "wechat/page/" + url;
                }
            }
        }
    });
    app.getInfo();
</script>

</html>
