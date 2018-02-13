<!DOCTYPE html>
<html>

<head>
    <title></title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no" />
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/font-awesome/css/font-awesome.min.css" />
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/css/common.css" />
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/css/mobiscroll_date.css" />
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/css/page/loanApplication.css" />
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/select/css/mobileSelect.css" />
    <script src="${request.contextPath}/js/jquery-2.1.0.js"></script>
    <script src="${request.contextPath}/js/jQuery.md5.js"></script>
    <script src="${request.contextPath}/select/js/mobileSelect.min.js"></script>
    <script src="${request.contextPath}/js/mobiscroll_date.js" charset="gb2312"></script>
    <script src="${request.contextPath}/js/mobiscroll.js"></script>
    <script src="${request.contextPath}/js/vue.min.js"></script>
    <script src="${request.contextPath}/js/config.js"></script>
    <style>
        .app-modal-context{text-align: left}
        .app-modal{width: 90%}
    </style>
</head>
<body>
<div id="app" class="app-content">

    <div class="title">申请借款</div>
    <div class="data-form">
        <ul class="app-form">
            <li>
                <label>借款金额</label>
                <input id="price" v-model="price" type="text" placeholder="请输入借款金额" @change="onChange" onkeyup="config.clearNoNum(this);" oninput="if(value.length>10)value=value.slice(0,10)" maxlength="10"/>
            </li>
            <li>
                <label>还款方式</label>
                <input v-model="repaymentMethodName" id="trigger1" type="text" placeholder="请选择还款方式" onfocus="this.blur();" unselectable="true" readonly="readonly"/>
            </li>
            <li>
                <label>还款日期</label>
                <input v-model="repaymentTime" id="trigger2" type="text" placeholder="请选择还款日期" onfocus="this.blur();" readonly="readonly"/>
            </li>
            <li>
                <label>年化利率</label>
                <input v-model="interestName" id="trigger3" type="text" placeholder="请选择年化利率" onfocus="this.blur();" readonly="readonly"/>
            </li>
            <li>
                <label>借款用途</label>
                <input v-model="loansUseName" id="trigger4" type="text" placeholder="请选择借款用途" onfocus="this.blur();" readonly="readonly"/>
            </li>
            <#--<li class="transcribe-video" v-if="type == 0">-->
                <#--<label>-->
                    <#--是否录制视频-->
                    <#--<input v-model="isUploadVideo" type="checkbox" class="fa app-checkbox isUploadVideo"/>-->
                <#--</label>-->
            <#--</li>-->
        </ul>
    </div>

    <div class="hint">
        <div class="principal">还款金额：<span>{{priceInterest}}</span></div>
        <div class="principal">本金：<span>{{price == null || price == "" ? 0 : price}}</span> + 利息：<span>{{interestAll}}</span>(假设是今天借到款)</div>
    </div>

    <div class="agreement-bar">
        <label>
            <input type="checkbox" class="fa app-checkbox agreement"/>
            <span>我已阅读并理解易信缘平台的</span>
        </label>
        <span class="agreement-btn" @click="setUrl">《借款协议》</span>
    </div>

    <input type="hidden" v-model="repaymentMethodId">
    <input type="hidden" v-model="interestId">
    <input type="hidden" v-model="loansUseId">

    <div class="button-bar">
        <button class="submit-btn" @click="submit">提交</button>
    </div>

</div>
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

    if (config.getUrlParam("type") == 1) {
        $("title").html("求借款");
        $(".title").html("求借款");
    }
    else {
        $("title").html("去出借");
        $(".title").html("去出借");
    }
    var app = new Vue({
        el: '#app',
        data: {
            type: config.getUrlParam("type"),
            repaymentMethodList: [], //还款方式
            repaymentMethod: [],
            interestList: [], //年化利率
            interest: [],
            loansUseList: [], //借款用途
            loansUse: [],
            price: null,repaymentMethodId: 0,repaymentMethodName:"",interestId: 0,interestName:"",loansUseId: 0,loansUseName:"",repaymentTime:"",isUploadVideo: null,
            interestAll: 0,
            priceInterest: 0,
            orderId: null, //存放orderId
        },
        methods: {
            /**输入金额改变**/
            onChange : function () {
                if (this.interestName != "" && this.repaymentTime != "") {
                    this.interestAll = 0;
                    this.priceInterest = 0;
                    this.countDay(this.price, this.interestName, this.repaymentTime);
                }
            },
            /**还款方式**/
            getRepaymentMethod : function () {
                var _self = this;
                config.ajaxRequestData(false, "wechat/repaymentMethod/list", {}, true, function (result) {
                    _self.repaymentMethodList = result.data;
                    for (var i = 0; i < result.data.length; i++) {
                        _self.repaymentMethod.push(result.data[i].name);
                    }
                });
            },
            /**年化利率**/
            getInterest : function () {
                var _self = this;
                config.ajaxRequestData(false, "wechat/Interest/list", {}, true, function (result) {
                    _self.interestList = result.data;
                    for (var i = 0; i < result.data.length; i++) {
                        _self.interest.push(result.data[i].interest);
                    }
                });
            },
            /**借款用途**/
            getLoansUse : function () {
                var _self = this;
                config.ajaxRequestData(false, "wechat/loansUse/list", {}, true, function (result) {
                    _self.loansUseList = result.data;
                    for (var i = 0; i < result.data.length; i++) {
                        _self.loansUse.push(result.data[i].name);
                    }
                });
            },
            /**初始化数据**/
            initData : function () {
                var _self = this;
                /**还款方式**/
                new MobileSelect({
                    trigger: '#trigger1',
                    title: '还款方式',
                    wheels: [
                        {data: this.repaymentMethod}
                    ],
                    position:[0], //初始化定位 打开时默认选中的哪个 如果不填默认为0
                    transitionEnd:function(indexArr, data){
                        //改变事件
                    },
                    callback:function(indexArr, data){
                        for (var i = 0; i < _self.repaymentMethodList.length; i++) {
                            var obj = _self.repaymentMethodList[i];
                            if (data == obj.name) {
                                _self.repaymentMethodId = obj.id;
                            }
                        }
                        _self.repaymentMethodName = data;
                    }
                });

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
                        endYear: currYear + 30, //结束年份
                        onSelect: function (valueText, inst) {//选择时事件（点击确定后），valueText 为选择的时间，
                            config.log(valueText);
                            _self.repaymentTime = valueText;
                            if (_self.interestName != "" && _self.price != null) {
                                _self.interestAll = 0;
                                _self.priceInterest = 0;
                                _self.countDay(_self.price, _self.interestName, _self.repaymentTime);
                            }
                        },
                    };
                    $("#trigger2").mobiscroll($.extend(opt['date'], opt['default']));
                });

                /**年化利率**/
                new MobileSelect({
                    trigger: '#trigger3',
                    title: '年化利率',
                    wheels: [
                        {data: this.interest}
                    ],
                    position:[0], //初始化定位 打开时默认选中的哪个 如果不填默认为0
                    transitionEnd:function(indexArr, data){},
                    callback:function(indexArr, data){
                        for (var i = 0; i < _self.interestList.length; i++) {
                            var obj = _self.interestList[i];
                            if (data == obj.interest) {
                                _self.interestId = obj.id;
                            }
                        }
                        _self.interestName = data;
                        if (_self.repaymentTime != "" && _self.price != null) {
                            _self.interestAll = 0;
                            _self.priceInterest = 0;
                            _self.countDay(_self.price, _self.interestName, _self.repaymentTime);
                        }
                    }
                });
                /**借款用途**/
                new MobileSelect({
                    trigger: '#trigger4',
                    title: '借款用途',
                    wheels: [
                        {data: this.loansUse}
                    ],
                    position:[0], //初始化定位 打开时默认选中的哪个 如果不填默认为0
                    transitionEnd:function(indexArr, data){},
                    callback:function(indexArr, data){
                        for (var i = 0; i < _self.loansUseList.length; i++) {
                            var obj = _self.loansUseList[i];
                            if (data == obj.name) {
                                _self.loansUseId = obj.id;
                            }
                        }
                        _self.loansUseName = data;
                    }
                });
            },
            /**提交**/
            submit : function () {
                var agreement = $(".agreement").is(":checked");
                var isUploadVideo = $(".isUploadVideo").is(":checked");
                var date1 = new Date();
                var beginDate = this.repaymentTime;
                var endDate = date1.getFullYear() + "-" + (date1.getMonth() + 1) + "-" + date1.getDate();
                date1 = new Date(beginDate.replace(/\-/g, "\/"));
                var date2 = new Date(endDate.replace(/\-/g, "\/"));
                if (this.price == null) {
                    config.toast("请输入借款金额");
                    return false;
                }
                else if (this.price < 200 || this.price > 200000) {
                    config.toast("借款金额范围(200元-200000元)");
                    return false;
                }
                else if (this.repaymentMethodName == "") {
                    config.toast("请选择还款方式");
                    return false;
                }
                else if (this.repaymentTime == "") {
                    config.toast("请选择还款日期");
                    return false;
                }
                else if (date1 < date2) {
                    config.toast("还款日期不能小于今天");
                    return false;
                }
                else if (this.interestName == "") {
                    config.toast("请选择年化利率");
                    return false;
                }
                else if (this.loansUseName == "") {
                    config.toast("请选择借款用途");
                    return false;
                }
                else if (!agreement) {
                    config.toast("请阅读并同意借款协议");
                    return false;
                }
                this.isUploadVideo = isUploadVideo ? 1 : 0;
                var parameter = {
                    price: Number(this.price),
                    repaymentMethodId: this.repaymentMethodId,
                    interestId: this.interestId,
                    loansUseId: this.loansUseId,
                    repaymentTime: new Date(this.repaymentTime.toString()),
                    isUploadVideo: this.isUploadVideo,
                    type: Number(this.type),
                };
                var thas = this;
                //借出
                if (Number(this.type) == 1) {
                    config.ajaxRequestData(true, "wechat/systemConfig/info", {}, true, function (result) {
                        if (result.data.serviceFee <= 0) {
                            config.prompt("支付密码验证", "请输入支付密码", ["取消","确定"], "password", function (index) {
                                if (index == 1) {
                                    if ($(".app-modal-input").val() == "") {
                                        config.toast("请输入支付密码");
                                    }
                                    else {
                                        parameter.payPwd = $.md5($(".app-modal-input").val());
                                        config.log(parameter);
                                        config.ajaxRequestData(true, "wechat/order/save", parameter, true, function (result) {
                                            location.href = config.ip + "wechat/page/borrowingDetail/" + result.data;
                                        });
                                    }
                                }
                            });
                        }
                        else if (thas.orderId == null) {
                            config.ajaxRequestData(true, "wechat/order/save", parameter, true, function (result) {
                                thas.orderId = result.data;
                                config.ajaxRequestData(false, "jsPay/sign", {orderId : thas.orderId}, true, function (result) {
                                    config.wechatPay(result.data.appId, result.data.timeStamp, result.data.nonceStr, result.data.package, result.data.signType, result.data.sign, function (json) {
                                        if(json.err_msg == "get_brand_wcpay_request:ok" ) {
                                            config.alert("温馨提示", "支付成功", "确定", function () {
                                                location.href = config.ip + "wechat/page/borrowingDetail/" + thas.orderId;
                                            });
                                        }
                                        else {
                                            config.toast("支付失败.");
                                        }
                                    });
                                });
                            });
                        }
                        else {
                            config.ajaxRequestData(false, "jsPay/sign", {orderId : thas.orderId}, true, function (result) {
                                config.wechatPay(result.data.appId, result.data.timeStamp, result.data.nonceStr, result.data.package, result.data.signType, result.data.sign, function (json) {
                                    if(json.err_msg == "get_brand_wcpay_request:ok" ) {
                                        config.alert("温馨提示", "支付成功", "确定", function () {
                                            location.href = config.ip + "wechat/page/borrowingDetail/" + thas.orderId;
                                        });
                                    }
                                    else {
                                        config.toast("支付失败.");
                                    }
                                });
                            });
                        }
                    });
                }
                //求借
                else {
                    config.prompt("支付密码验证", "请输入支付密码", ["取消","确定"], "password", function (index) {
                        if (index == 1) {
                            if ($(".app-modal-input").val() == "") {
                                config.toast("请输入支付密码");
                            }
                            else {
                                parameter.payPwd = $.md5($(".app-modal-input").val());
                                config.log(parameter);
                                config.ajaxRequestData(true, "wechat/order/save", parameter, true, function (result) {
                                    location.href = config.ip + "wechat/page/borrowingDetail/" + result.data;
                                });
                            }
                        }
                    });
                }
            },
            /**跳转**/
            setUrl : function () {
                var content = "单号：__________" +
                                "<br><br>甲方（出借人）：______联系电话：______" +
                                "<br><br>身份证号：______" +
                                "<br><br>乙方（借款人）：______联系电话：______" +
                                "<br>身份证号：______" +
                                "<br><br>丙方（服务商）：成都鑫区网络科技有限公司（“易信缘”平台运营商）客服电话：_______" +
                                "<br><br>　　甲乙双方均系丙方“易信缘”平台已注册的实名用户，同意并自愿遵守丙方提供的《用户注册协议》及其他平台操作准则，现基于平等、自愿、诚信的原则，就借贷相关事宜达成如下约定：" +
                                "<br><br>　　一、借款内容" +
                                "<br><br>借款金额人民币____元，大写____出借方式1.线上；2.线下借款期限自___________至_____________借款利率年化利率______%利息算法自收到借款当日24时起计息，每过24时计息一次。利息=借款金额×借款利率÷360日×借款期限。还款方式本息到期一次付清到期偿还______元" +
                                "<br><br>　　二、承诺条款" +
                                "<br><br>　　1、甲方承诺本次出借的款项并非套取金融机构信贷资金且来源合法。" +
                                "<br>　　2、乙方承诺向甲方及丙方提供的信息资料真实，在所提交的信息发生变更时及时上传告知，不得用借款进行违法活动，并按期还本付息。" +
                                "<br>　　3、甲乙双方承诺已认真阅读《用户注册协议》并清楚填选本借款协议最终点击确认后，借款协议所产生的法律效力。" +
                                "<br><br>　　三、违约责任" +
                                "<br>　　1.乙方若未按借款协议约定期限足额归还本息的，经甲方同意：" +
                                "<br>　　a）按照先还息再还本的方式结算，将本借款协议拖欠的金额作为本金另行续订电子借款协议；" +
                                "<br>　　b)甲方可给予_____日宽限期，若宽限期后仍未归还的，甲方有权随时解除本借款协议，并向乙方主张借款本金及按约履行本借款协议所应产生的利息。" +
                                "<br>　　2．在未及时续订电子借款协议或未在宽限期内全额支付本息的，乙方应另行支付_____/日的逾期违约金，并承担甲方实现该债权所产生的合理费用包括但不限于交通、误工、代理费等。" +
                                "<br><br>　　四、争议解决" +
                                "<br><br>　　本借款协议在履行过程中发生的争议，由双方友好协商解决，也可申请丙方平台在线调解。协商或调解不成的，可依法向甲方所在地提起诉讼。" +
                                "<br><br>　　五、借款协议形成" +
                                "<br><br>　　本借款协议系由丙方平台提供的电子居间服务提供。经甲方和乙方在丙方平台实名注册并有借贷意愿或发布借贷信息后，经丙方平台筛选、匹配，甲乙双方通过对借款金额、期限、利息计算、出借方式、还款方式等各选项进行选择与确认，达成协议后，由丙方平台自动生成。" +
                                "<br><br>　　六、法律效力" +
                                "<br><br>　　基于甲乙双方均为实名注册并同意本平台《用户注册协议》，双方在进行了逐项选择并最终确认之后，经上述复杂验证、操作流程及密码验证码等，甲乙双方均确认如下事实：" +
                                "<br>　　1.均系中华人民共和国境内年满18周岁公民并具有完全民事行为能力；" +
                                "<br>　　2.具有一定阅读能力并能够正常运用互联网相关服务商、金融机构进行收付款；" +
                                "<br>　　3.经手机密码及验证码、软件密码、支付密码等确认，本次操作确系甲乙双方在丙方平台上传的信息资料本人进行的操作。" +
                                "<br>　　结合上述事实，依照我国境内相关法律法规，本借款协议具有法律约束力，应当共同遵守。" +
                                "<br><br>　　七、其他" +
                                "<br><br>　　丙方作为网络借贷信息中介服务商，见证甲乙双方本次借款协议的签订，但无法保证甲乙双方的实际履约能力，也依法不对本协议线下的支付、催收提供任何承诺和服务。本借款协议留存于易信缘平台，甲乙双方可按照用户《用户注册协议》进行相应操作。" +
                                "<br><br>　　甲方：______" +
                                "<br>　　_____年______月____日" +
                                "<br><br>　　服务商：成都鑫区网络科技有限公司" +
                                "<br>　　_____年______月____日"
                        ;
                config.alert("借款协议", content, "同意借款协议", function () {

                });
                //location.href = config.ip + "wechat/page/borrowMoneyAgreement";
            },
            /**计算利息**/
            countInterests : function (price, interestRate, day, yearDay) {
                //总利息
                this.interestAll += (interestRate * price / yearDay);
                //本金加利息
                price = price + (interestRate * price / yearDay);
                if (day > 0) {
                    price = this.countInterests(price, interestRate, (day - 1), yearDay);
                }
                else {
                    this.interestAll = this.interestAll.toFixed(2);
                    this.priceInterest = price.toFixed(2);
                }
                return price;
            },
            /**计算天数**/
            countDay : function (price, interestRate, repaymentTime) {
                var yearDay = Number(new Date(repaymentTime).getFullYear()) / 4 % 0 ? 366 : 365;
                var day = config.dateDiff(new Date(repaymentTime).format("yyyy-MM-dd"), new Date().format("yyyy-MM-dd"));
                this.countInterests(Number(price), Number(interestRate) / 100, day, yearDay);
            }
        }
    });
    app.getRepaymentMethod();
    app.getInterest();
    app.getLoansUse();
    app.initData();

    $(function () {
        $('#price').bind('input propertychange', function() {
            app.onChange();
        });
    })

</script>

</html>
