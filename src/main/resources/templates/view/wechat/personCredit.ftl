<!DOCTYPE html>
<html>

<head>
    <title>个人信用</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no" />
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/css/common.css" />
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/css/page/personCredit.css" />
    <script src="${request.contextPath}/js/jquery-2.1.0.js"></script>
    <script src="${request.contextPath}/js/vue.min.js"></script>
    <script src="${request.contextPath}/js/config.js"></script>
</head>
<body>
<div id="app" class="app-content">

    <div class="balance-bar">
        <div class="center">
            <#--<div class="title-hint">当前信用</div>
            <div class="money">{{userInfo.creditPoints == null ? 0 : userInfo.creditPoints}}</div>-->
            <div class="money-item" style="width: 50%;margin-bottom: 30px;">
                <div class="hint">姓名：{{userInfo.userName}}</div>
                <div class="number"></div>
            </div>
            <div class="money-item" style="width: 50%;margin-bottom: 30px;">
                <div class="hint">当前信用</div>
                <div class="number">{{userInfo.creditPoints == null ? 0 : userInfo.creditPoints}}</div>
            </div>
            <div class="money-item">
                <div class="number">{{userInfo.nowToBePaidJoinPrice == null ? 0 : userInfo.nowToBePaidJoinPrice}}</div>
                <div class="hint">待还金额</div>
            </div>
            <div class="money-item">
                <div class="number">{{userInfo.overdueNum == null ? 0 : userInfo.overdueNum}}</div>
                <div class="hint">当期逾期</div>
            </div>
            <div class="money-item">
                <div class="number">{{userInfo.borrowJoinTotalPrice == null ? 0 : userInfo.borrowJoinTotalPrice}}</div>
                <div class="hint">累计借款</div>
            </div>
        </div>
    </div>
    <div class="data-list">
        <div class="data-head">
            <div>类型</div>
            <div>借入</div>
            <div>逾期</div>
            <div>借出</div>
        </div>
        <div class="data-body">
            <div class="data-item">
                <div>累计金额</div>
                <div>{{userInfo.borrowJoinTotalPrice == null ? 0 : userInfo.borrowJoinTotalPrice}}</div>
                <div>{{userInfo.overdueTotalPrice == null ? 0 : userInfo.overdueTotalPrice}}</div>
                <div>{{userInfo.borrowOutTotalPrice == null ? 0 : userInfo.borrowOutTotalPrice}}</div>
            </div>
            <div class="data-item">
                <div>累计笔数</div>
                <div>{{userInfo.joinNum == null ? 0 : userInfo.joinNum}}</div>
                <div>{{userInfo.overdueTotalNum == null ? 0 : userInfo.overdueTotalNum}}</div>
                <div>{{userInfo.outNum == null ? 0 : userInfo.outNum}}</div>
            </div>
            <div class="data-item">
                <div>当前金额</div>
                <div>{{userInfo.nowToBePaidJoinPrice == null ? 0 : userInfo.nowToBePaidJoinPrice}}</div>
                <div>{{userInfo.overdueNowTotalPrice == null ? 0 : userInfo.overdueNowTotalPrice}}</div>
                <div>{{userInfo.nowToBePaidOutPrice == null ? 0 : userInfo.nowToBePaidOutPrice}}</div>
            </div>
        </div>
    </div>

    <div class="data-list-tow">
        <div class="data-head">
            <div>出借人</div>
            <div>借/还款日期</div>
            <div>金额</div>
            <div>状态</div>
        </div>
        <div class="data-body">
            <div class="data-item" v-for="order in userInfo.repayOrders">
                <div>{{order.toUserName}}</div>
                <div>{{order.createTime}}<br/>{{order.repaymentTime}}</div>
                <div>{{order.price}}</div>
                <div v-if="order.status == 0 || order.status == 1">
                    <span v-if="order.isUploadVideo == 0 || order.isUploadVideo == null">待审核放款</span>
                    <span v-if="order.isUploadVideo == 1 && order.videoUrl == null">待上传视频</span>
                    <span v-if="order.isUploadVideo == 1 && order.videoUrl != null">待审核视频</span>
                </div>
                <div v-if="order.status == 2">拒绝借款申请</div>
                <div v-if="order.status == 3">
                    <span v-if="order.remainDays == 0" style="color: red;">今天还款</span>
                    <span v-else-if="order.remainDays < 0" style="color: red;">逾期{{-order.remainDays}}天</span>
                    <span v-else>剩余{{order.remainDays}}天</span>
                </div>
                <div v-if="order.status == 4">待审核还款</div>
                <div v-if="order.status == 5">
                    <span v-if="order.remainDays < 0">逾期{{-order.remainDays}}天(已还)</span>
                    <span v-else>还款成功</span>
                </div>
                <div v-if="order.status == 6">
                    <span v-if="order.remainDays == 0" style="color: red;">今天还款</span>
                    <span v-else-if="order.remainDays < 0" style="color: red;">逾期{{-order.remainDays}}天</span>
                    <span v-else>剩余{{order.remainDays}}天</span>
                </div>
                <div v-if="order.status == 7">延期待审核</div>
            </div>
        </div>
        <div class="not-data" v-if="userInfo.repayOrders == null">
            暂无订单.
        </div>
    </div>

</div>
</body>
<script>

    var app = new Vue({
        el: '#app',
        data: {
            idCard: sessionStorage.getItem("idCard"),
            userName: sessionStorage.getItem("userName"),
            userId: config.getUrlParam("userId"),
            userInfo: [],
        },
        methods: {
            /**查询个人信用**/
            findUserCredit: function () {
                var _self = this;
                if (_self.userId == "" || _self.userId == null) {
                    config.ajaxRequestData(true, "wechat/user/findUserCredit", {
                        idCard : this.idCard,
                        userName : this.userName,
                    }, true, function (result) {
                        _self.userInfo = result.data;
                        for (var i = 0 ;_self.userInfo.repayOrders.length > i ; i ++) {
                            _self.userInfo.repayOrders[i].repaymentTime =
                                    new Date(_self.userInfo.repayOrders[i].repaymentTime).format("yyyy.MM.dd");
                            _self.userInfo.repayOrders[i].createTime =
                                    new Date(_self.userInfo.repayOrders[i].createTime).format("yyyy.MM.dd");
                        }
                    });
                }
                else {
                    config.ajaxRequestData(true, "wechat/user/findUserCreditById", {
                        id : _self.userId,
                    }, true, function (result) {
                        _self.userInfo = result.data;
                        for (var i = 0 ;_self.userInfo.repayOrders.length > i ; i ++) {
                            _self.userInfo.repayOrders[i].repaymentTime =
                                    new Date(_self.userInfo.repayOrders[i].repaymentTime).format("yyyy.MM.dd");
                            _self.userInfo.repayOrders[i].createTime =
                                    new Date(_self.userInfo.repayOrders[i].createTime).format("yyyy.MM.dd");
                        }
                    });
                }

            },
        }
    })
    app.findUserCredit();

</script>

</html>
