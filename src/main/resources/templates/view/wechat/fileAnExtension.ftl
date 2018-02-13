<!DOCTYPE html>
<html>

<head>
    <title>申请延期</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no" />
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/font-awesome/css/font-awesome.min.css" />
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/css/common.css" />
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/css/mobiscroll_date.css" />
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/css/page/fileAnExtension.css" />
    <script src="${request.contextPath}/js/jquery-2.1.0.js"></script>
    <script src="${request.contextPath}/js/mobiscroll_date.js" charset="gb2312"></script>
    <script src="${request.contextPath}/js/mobiscroll.js"></script>
    <script src="${request.contextPath}/js/vue.min.js"></script>
    <script src="${request.contextPath}/js/config.js"></script>
</head>
<body>
<div id="app" class="app-content">
    <div class="title">申请延期</div>
    <div class="data-form">
        <ul class="app-form">
            <li>
                <label>单号</label>
                <input v-model="orderInfo.number" type="text" onfocus="this.blur();" readonly="readonly"/>
            </li>
            <li>
                <label>借款人</label>
                <input v-model="orderInfo.toUserName" type="text" onfocus="this.blur();" readonly="readonly"/>
            </li>
            <li>
                <label>出借人</label>
                <input v-model="orderInfo.fromUserName" type="text" onfocus="this.blur();" readonly="readonly"/>
            </li>
            <li>
                <label>还款时间</label>
                <input v-model="orderInfo.repaymentTime" type="text" onfocus="this.blur();" readonly="readonly"/>
            </li>
            <li>
                <label>延期日期</label>
                <input v-model="postponeRepaymentTime" id="dateTime" type="text" placeholder="请选择延期日期" onfocus="this.blur();" readonly="readonly"/>
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
            orderId: config.getUrlParam("orderId"),
            postponeRepaymentTime: null,
            orderInfo: [],
        },
        methods: {
            /**还款方式**/
            getOrderInfo : function () {
                var _self = this;
                config.ajaxRequestData(false, "wechat/order/info", {id : this.orderId}, true, function (result) {
                    _self.orderInfo = result.data;
                    _self.orderInfo.repaymentTime = new Date(_self.orderInfo.repaymentTime).format("yyyy-MM-dd");
                });
            },
            /**初始化数据**/
            initData : function () {
                var _self = this;
                /**还款日期**/
                $(function () {
                    var currYear = (new Date()).getFullYear();
                    var opt={};
                    opt.date = {preset : 'date'};
                    opt.datetime = {preset : 'datetime'};
                    opt.time = {preset : 'time'};
                    opt.default = {
                        theme: 'android-ics light', //皮肤样式
                        display: 'modal', //显示方式
                        mode: 'scroller', //日期选择模式
                        dateFormat: 'yyyy-mm-dd',
                        lang: 'zh',
                        showNow: true,
                        nowText: "今天",
                        //startYear: currYear, //开始年份
                        minDate: new Date(), //最小时间
                        endYear: currYear + 50, //结束年份
                        onSelect: function (valueText, inst) {//选择时事件（点击确定后），valueText 为选择的时间，
                            config.log(valueText);
                            _self.postponeRepaymentTime = valueText;
                        },
                    };
                    $("#dateTime").mobiscroll($.extend(opt['date'], opt['default']));
                });
            },
            /**提交**/
            submit : function () {
                var _self = this;
                var date1 = new Date(_self.orderInfo.repaymentTime);
                var beginDate = _self.postponeRepaymentTime;
                var endDate = date1.getFullYear() + "-" + (date1.getMonth() + 1) + "-" + date1.getDate();
                var date1 = new Date(beginDate.replace(/\-/g, "\/"));
                var date2 = new Date(endDate.replace(/\-/g, "\/"));
                if (date1 < date2) {
                    config.toast("延期日期不能小于还款日期");
                    return false;
                }
                config.prompt("支付密码验证", "请输入支付密码", ["取消","确定"], "password", function (index) {
                    if (index == 1) {
                        if ($(".app-modal-input").val() == "") {
                            config.toast("请输入支付密码");
                        }
                        else {
                            config.ajaxRequestData(false, "wechat/order/update", {
                                id: _self.orderId,
                                status: 7,
                                type: _self.orderInfo.type,
                                postponeRepaymentTime: new Date(_self.postponeRepaymentTime),
                            }, true, function (result) {
                                config.toast(result.msg);
                                window.history.back();
                            });
                        }
                    }
                });
            }
        }
    });
    app.getOrderInfo();
    app.initData();

</script>

</html>
