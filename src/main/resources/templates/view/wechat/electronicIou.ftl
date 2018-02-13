<!DOCTYPE html>
<html>

<head>
    <title>电子借条</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no" />
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/css/common.css" />
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/css/page/electronicIou.css" />
    <script src="${request.contextPath}/js/jquery-2.1.0.js"></script>
    <script src="${request.contextPath}/js/vue.min.js"></script>
    <script src="${request.contextPath}/js/config.js"></script>
    <script src="${request.contextPath}/js/html2canvas.js"></script>
</head>
<body>
<div id="app" class="app-content">
    <div class="content">
        <h2 class="max-title">借款协议</h2>
        <br>
        <p>单号：{{orderInfo.number}} <span @click="generateImages()" class="download-btn hide">点击下载</span><a id="downloadImg" download="0.png"></a></p>
        <br>
        <p>甲方（出借人）：{{orderInfo.fromUserName}}　联系电话：{{orderInfo.fromUserPhone}}</p>
        <p>身份证号：{{orderInfo.fromUserIdCard}}</p>
        <br>
        <p>乙方（借款人）：{{orderInfo.toUserName}}　联系电话：{{orderInfo.toUserPhone}}</p>
        <p>身份证号：{{orderInfo.toUserIdCard}}</p>
        <br>
        <p>丙方（服务商）：成都鑫区网络科技有限公司（“易信缘”平台运营商）客服电话：{{contactUsInfo.phone}}</p>
        <br>
        <p class="text-indent">甲乙双方均系丙方“易信缘”平台已注册的实名用户，同意并自愿遵守丙方提供的《用户注册协议》及其他平台操作准则，现基于平等、自愿、诚信的原则，就借贷相关事宜达成如下约定：</p>
        <p class="text-indent">一、借款内容</p>
        <br>
        <table border="1" cellspacing="0">
            <tr>
                <td width="80">借款金额</td>
                <td>人民币{{orderInfo.price}}元，大写{{orderInfo.hanZiPrice}}元</td>
            </tr>
            <tr>
                <td>出借方式</td>
                <td>线下</td>
            </tr>
            <tr>
                <td>借款期限</td>
                <td>自{{orderInfo.createTime}}至{{orderInfo.repaymentTime}}</td>
            </tr>
            <tr>
                <td>借款利率</td>
                <td>{{orderInfo.interestName}}%</td>
            </tr>
            <tr>
                <td>利息算法</td>
                <td>自收到借款当日24时起计息，每过24时计息一次。利息=借款金额×借款利率÷360日×借款期限</td>
            </tr>
            <tr>
                <td>还款方式</td>
                <td>本次到期一次付清</td>
            </tr>
            <tr>
                <td>到期偿还</td>
                <td>{{orderInfo.price}}元</td>
            </tr>
        </table>
        <br>
        <p>借款金额人民币{{orderInfo.price}}元，大写{{orderInfo.hanZiPrice}}元出借方式；线下借款期限自{{orderInfo.createTime}}至{{orderInfo.repaymentTime}}借款利率年化利率{{orderInfo.interestName}}%利息算法自收到借款当日24时起计息，每过24时计息一次。利息=借款金额×借款利率÷360日×借款期限。还款方式本息到期一次付清到期偿还{{orderInfo.price}}元</p>
        <br>
        <p class="text-indent">二、承诺条款</p>
        <br>
        <p class="text-indent">1、甲方承诺本次出借的款项并非套取金融机构信贷资金且来源合法。</p>
        <p class="text-indent">2、乙方承诺向甲方及丙方提供的信息资料真实，在所提交的信息发生变更时及时上传告知，不得用借款进行违法活动，并按期还本付息。</p>
        <p class="text-indent">3、甲乙双方承诺已认真阅读《用户注册协议》并清楚填选本借款协议最终点击确认后，借款协议所产生的法律效力。</p>
        <br>
        <p class="text-indent">三、违约责任</p>
        <br>
        <p class="text-indent">1.乙方若未按借款协议约定期限足额归还本息的，经甲方同意：</p>
        <p class="text-indent">a）按照先还息再还本的方式结算，将本借款协议拖欠的金额作为本金另行续订电子借款协议；</p>
        <p class="text-indent">b)甲方可给予_____日宽限期，若宽限期后仍未归还的，甲方有权随时解除本借款协议，并向乙方主张借款本金及按约履行本借款协议所应产生的利息。</p>
        <p class="text-indent">2．在未及时续订电子借款协议或未在宽限期内全额支付本息的，乙方应另行支付_____/日的逾期违约金，并承担甲方实现该债权所产生的合理费用包括但不限于交通、误工、代理费等。</p>
        <br>
        <p class="text-indent">四、争议解决</p>
        <br>
        <p class="text-indent">本借款协议在履行过程中发生的争议，由双方友好协商解决，也可申请丙方平台在线调解。协商或调解不成的，可依法向甲方所在地提起诉讼。</p>
        <br>
        <p class="text-indent">五、借款协议形成</p>
        <br>
        <p class="text-indent">本借款协议系由丙方平台提供的电子居间服务提供。经甲方和乙方在丙方平台实名注册并有借贷意愿或发布借贷信息后，经丙方平台筛选、匹配，甲乙双方通过对借款金额、期限、利息计算、出借方式、还款方式等各选项进行选择与确认，达成协议后，由丙方平台自动生成。</p>
        <br>
        <p class="text-indent">六、法律效力</p>
        <br>
        <p class="text-indent">基于甲乙双方均为实名注册并同意本平台《用户注册协议》，双方在进行了逐项选择并最终确认之后，经上述复杂验证、操作流程及密码验证码等，甲乙双方均确认如下事实：</p>
        <p class="text-indent">1.均系中华人民共和国境内年满18周岁公民并具有完全民事行为能力；</p>
        <p class="text-indent">2.具有一定阅读能力并能够正常运用互联网相关服务商、金融机构进行收付款；</p>
        <p class="text-indent">3.经手机密码及验证码、软件密码、支付密码等确认，本次操作确系甲乙双方在丙方平台上传的信息资料本人进行的操作。</p>
        <p class="text-indent">结合上述事实，依照我国境内相关法律法规，本借款协议具有法律约束力，应当共同遵守。</p>
        <br>
        <p class="text-indent">七、其他</p>
        <br>
        <p class="text-indent">丙方作为网络借贷信息中介服务商，见证甲乙双方本次借款协议的签订，但无法保证甲乙双方的实际履约能力，也依法不对本协议线下的支付、催收提供任何承诺和服务。本借款协议留存于易信缘平台，甲乙双方可按照用户《用户注册协议》进行相应操作。</p>
        <p>甲方：{{orderInfo.fromUserName}}</p>
        <p>{{orderInfo.createTimeHanZi}}</p>
        <br>
        <p>乙方：{{orderInfo.toUserName}}</p>
        <p>{{orderInfo.repaymentTimeHanZi}}</p>
        <br>
        <p>服务商：成都鑫区网络科技有限公司</p>
        <p>{{orderInfo.createTimeHanZi}}</p>
        <img src="${request.contextPath}/img/16.png" width="100px" style="position: relative;top: -50px;">
        <br>
    </div>
</div>
</body>
<script>
    var app = new Vue({
        el: '#app',
        data: {
            orderId : config.getUrlParam("orderId"),
            orderInfo : [],
            contactUsInfo : [],
        },
        methods: {
            /**获取订单详情**/
            getInfo : function () {
                var _self = this;
                config.ajaxRequestData(false, "wechat/order/info", {id : this.orderId}, true, function (result) {
                    _self.orderInfo = result.data;
                    _self.orderInfo.createTime = new Date(_self.orderInfo.createTime).format("yyyy-MM-dd");
                    _self.orderInfo.repaymentTime = new Date(_self.orderInfo.repaymentTime).format("yyyy-MM-dd");
                    _self.orderInfo.createTimeHanZi = new Date(_self.orderInfo.createTime).format("yyyy年MM月dd日");
                    _self.orderInfo.repaymentTimeHanZi = new Date(_self.orderInfo.repaymentTime).format("yyyy年MM月dd日");
                    _self.orderInfo.hanZiPrice = config.intToChinese(_self.orderInfo.price);
                    var date1 = new Date();
                    var beginDate = _self.orderInfo.repaymentTime;
                    var endDate = date1.getFullYear() + "-" + (date1.getMonth() + 1) + "-" + date1.getDate();
                    date1 = new Date(beginDate.replace(/\-/g, "\/"));
                    var date2 = new Date(endDate.replace(/\-/g, "\/"));
                    _self.orderInfo.overdueNum = 0;
                    if (date1 < date2) {
                        _self.orderInfo.overdueNum = config.dateDiff(new Date(_self.orderInfo.repaymentTime).format("yyyy-MM-dd"), new Date().format("yyyy-MM-dd"));
                    }
                });

                config.ajaxRequestData(false, "wechat/contactUs/info", {}, true, function (result) {
                    _self.contactUsInfo = result.data;
                });
            },
            generateImages : function () {
                $(".download-btn").hide();
                html2canvas(document.getElementById("app"), {
                    allowTaint: true,
                    taintTest: false,
                    onrendered: function(canvas) {
                        canvas.id = "mycanvas";
                        //生成base64图片数据
                        var dataUrl = canvas.toDataURL();
                        $("#downloadImg").attr("href", dataUrl);
                        document.getElementById("downloadImg").click();
                        $(".download-btn").show();
                    }
                });
            }
        }
    })
    app.getInfo();

</script>

</html>
