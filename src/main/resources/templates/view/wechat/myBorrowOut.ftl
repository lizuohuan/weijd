<!DOCTYPE html>
<html>

<head>
    <title>我的借出</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no" />
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/font-awesome/css/font-awesome.min.css" />
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/css/mui.min.css" />
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/css/common.css" />
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/css/page/myBorrow.css" />
    <script src="${request.contextPath}/js/mui.min.js"></script>
    <script src="${request.contextPath}/js/jquery-2.1.0.js"></script>
    <script src="${request.contextPath}/js/jQuery.md5.js"></script>
    <script src="${request.contextPath}/js/vue.min.js"></script>
    <script src="${request.contextPath}/js/config.js"></script>
</head>
<body>
<div id="app">
    <div class="app-content">
        <div class="balance-bar">
            <div class="center">
                <div class="title-hint">待收金额</div>
                <div class="money">{{user.nowToBePaidOutPrice == null ? 0 : user.nowToBePaidOutPrice}}</div>
                <div class="money-item">
                    <div class="number">{{user.overdueNum == null ? 0 : user.overdueNum}}</div>
                    <div class="hint">当期逾期</div>
                </div>
                <div class="money-item">
                    <div class="number">{{user.borrowOutTotalPrice == null ? 0 : user.borrowOutTotalPrice}}</div>
                    <div class="hint">累计放款</div>
                </div>
            </div>
        </div>
        <div class="search-bar">
            <input v-model="userName" type="text" placeholder="搜索借款人" />
            <span @click="getOrderList"><i class="fa fa-search"></i></span>
        </div>
        <div class="data-list">
            <div class="data-head">
                <div>借款人</div>
                <div>待还金额</div>
                <div>还款日期</div>
            </div>
            <div class="data-body">
                <div class="data-item" v-for="order in orderLst" @click="operation(order)">
                    <div>{{order.toUserName}}</div>
                    <div>{{order.price}}</div>
                    <!--<div>{{order.repaymentTime}}</div>-->
                    <div v-if="order.status == 0 || order.status == 1">
                        <span v-if="order.isUploadVideo == 0">待审核放款</span>
                        <span v-if="order.isUploadVideo == 1 && order.videoUrl == null">待上传视频</span>
                        <span v-if="order.isUploadVideo == 1 && order.videoUrl != null">待审核视频</span>
                    </div>
                    <div v-if="order.status == 2">拒绝借款申请</div>
                    <div v-if="order.status == 3">
                        <span v-if="order.remainDays == 0" style="color: red;">今天还款</span>
                        <span v-else-if="order.remainDays < 0" style="color: red;">逾期{{order.remainDay}}天</span>
                        <span v-else>剩余{{order.remainDays}}天</span>
                    </div>
                    <div v-if="order.status == 4">待审核收款</div>
                    <div v-if="order.status == 5">
                        <span v-if="order.remainDays < 0">逾期{{-order.remainDays}}天(已还)</span>
                        <span v-else>还款成功</span>
                    </div>
                    <div v-if="order.status == 6">
                        <span v-if="order.remainDays == 0" style="color: red;">今天还款</span>
                        <span v-else-if="order.remainDays < 0" style="color: red;">逾期{{order.remainDay}}天</span>
                        <span v-else>剩余{{order.remainDays}}天</span>
                    </div>
                    <div v-if="order.status == 7">延期待审核</div>
                </div>
            </div>

            <div class="not-data" v-if="orderLst.length == 0">
                暂无订单.
            </div>
        </div>
    </div>

    <div id="picture" class="mui-popover mui-popover-action mui-popover-bottom">
        <ul class="mui-table-view">
            <li class="mui-table-view-cell" @click="findDetail()">
                查看明细
            </li>
            <li v-if="order.status >= 3" class="mui-table-view-cell" @click="borrowStrip()">
                电子借条
            </li>
            <li v-if="order.status == 1" class="mui-table-view-cell" @click="reviewTheLoan()">
                审核借款
            </li>
            <li v-if="order.status == 3" class="mui-table-view-cell" @click="remindRepayment()">
                提醒还款
            </li>
            <li v-if="order.status == 7" class="mui-table-view-cell" @click="deferredAudit()">
                确认审核
            </li>
            <li v-if="order.status >= 3 && order.status != 5" class="mui-table-view-cell" @click="affirmRepayment()">
                确认还款
            </li>
            <li v-if="order.status < 3" class="mui-table-view-cell" @click="cancelThePayment()">
                取消借款
            </li>
        </ul>
        <ul class="mui-table-view">
            <li class="mui-table-view-cell">
                <a href="#picture"><b>取消</b></a>
            </li>
        </ul>
    </div>
</div>
</body>
<script>
    config.reload();
    var app = new Vue({
        el: '#app',
        data: {
            userName: "",
            orderLst: [],
            order: [],
            user: [],
        },
        methods: {
            /**查询个人信用**/
            getOrderList: function () {
                var _self = this;
                config.ajaxRequestData(true, "wechat/order/list", {
                    type : 1,
                    userName : this.userName,
                }, true, function (result) {
                    _self.orderLst = result.data;
                    for (var i = 0; i < _self.orderLst.length; i++) {
                        var obj = _self.orderLst[i];
                        obj.repaymentTime = new Date(obj.repaymentTime).format("yyyy-MM-dd");
                        obj.price = Number(obj.price + obj.interest + obj.managementCost).toFixed(2);
                        obj.overdueNum = config.dateDiff(new Date(obj.repaymentTime).format("yyyy-MM-dd"), new Date().format("yyyy-MM-dd"));
                        if (obj.remainDays < 0) {
                            obj.remainDay = Math.abs(obj.remainDays);
                        }
                    }
                });
                config.ajaxRequestData(true, "wechat/user/statistics", { type : 0 }, true, function (result) {
                    _self.user = result.data;
                });
            },
            /**操作**/
            operation : function (order) {
                config.log(order);
                this.order = order;
                mui('#picture').popover('toggle');
            },
            /**查看明细**/
            findDetail : function () {
                location.href = config.ip + "wechat/page/borrowingDetail/" + this.order.id;
            },
            /**电子借条**/
            borrowStrip : function () {
                location.href = config.ip + "wechat/page/electronicIou?orderId=" + this.order.id;
            },
            /**审核借款**/
            reviewTheLoan : function () {
                location.href = config.ip + "wechat/page/borrowingDetail/" + this.order.id;
            },
            /**确认审核**/
            deferredAudit : function () {
                location.href = config.ip + "wechat/page/borrowingDetail/" + this.order.id;
            },
            /**提醒还款**/
            remindRepayment : function () {
                config.ajaxRequestData(true, "wechat/order/remind", { id : this.order.id }, true, function (result) {
                    config.toast(result.msg);
                });
                mui('#picture').popover('toggle');
            },
            /**确认还款**/
            affirmRepayment : function () {
                var _self = this;
                config.prompt("支付密码验证", "请输入支付密码", ["取消","确定"], "password", function (index) {
                    if (index == 1) {
                        if ($(".app-modal-input").val() == "") {
                            config.toast("请输入支付密码");
                        }
                        else {
                            config.ajaxRequestData(true, "wechat/order/update", {
                                payPwd : $.md5($(".app-modal-input").val()),
                                id : _self.order.id,
                                status: 5,
                                type: _self.order.type,
                            }, true, function (result) {
                                config.toast(result.msg);
                                location.reload();
                            });
                        }
                    }
                });
                mui('#picture').popover('toggle');
            },
            /**取消借款**/
            cancelThePayment : function () {
                var _self = this;
                config.confirm("温馨提示", "是否取消借款？", ["取消", "确认"], function (index) {
                    if (index == 1) {
                        config.ajaxRequestData(true, "wechat/order/delete", { id : _self.order.id }, true, function (result) {
                            config.toast(result.msg);
                            location.reload();
                        });
                    }
                });
                mui('#picture').popover('toggle');
            },
        }
    })
    app.getOrderList();

</script>

</html>
