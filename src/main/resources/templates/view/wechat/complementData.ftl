<!DOCTYPE html>
<html>

<head>
    <title>补全资料</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no" />
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/font-awesome/css/font-awesome.min.css" />
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/css/common.css" />
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/css/page/complementData.css" />
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/select/css/mobileSelect.css" />
    <script src="${request.contextPath}/js/jquery-2.1.0.js"></script>
    <script src="${request.contextPath}/js/jquery.form.js"></script>
    <script src="${request.contextPath}/select/js/mobileSelect.min.js"></script>
    <script src="${request.contextPath}/js/angular.min.js"></script>
    <script src="${request.contextPath}/js/config.js"></script>
</head>
<body ng-app="webApp" ng-controller="appController">
<div class="app-content">

    <div class="title">补全资料</div>
    <div class="data-form">
        <ul class="app-form">
            <li>
                <label>工作单位</label>
                <textarea ng-model="userInfo.job" placeholder="请输入工作单位" rows="4">{{userInfo.job}}</textarea>
            </li>
            <li>
                <label>家庭住址</label>
                <input id="trigger1" type="text" placeholder="请选择家庭住址" value="{{userInfo.city.mergerName}}"  onfocus="this.blur();" readonly="readonly"/>
            </li>
            <li>
                <label>详细地址</label>
                <textarea ng-model="userInfo.address" placeholder="请输入详细地址" rows="4">{{userInfo.address}}</textarea>
            </li>
            <li>
                <div class="title-img">身份证上传</div>
                <div class="preview">
                    <div ng-repeat="url in userInfo.idCardImg" class="preview-img ng-scope" url="{{url}}" style="background: url('{{ip}}{{url}}');"><i ng-click="deleteCardImg($event)"></i></div>
                    <div onclick="$('#file').click()" class="uploading-btn">+</div>
                </div>
            </li>
            <li>
                <label>父亲姓名</label>
                <input ng-model="userInfo.fatherName" type="text" placeholder="请输入父亲姓名" value="{{userInfo.fatherName}}"/>
            </li>
            <li>
                <label>父亲手机号</label>
                <input ng-model="userInfo.fatherPhone" type="text" placeholder="请输入父亲手机号" value="{{userInfo.fatherPhone}}" maxlength="11" onkeyup="this.value=this.value.replace(/\D/g,'')"/>
            </li>
            <li>
                <label>父亲工作地址</label>
                <textarea ng-model="userInfo.fatherJobAddress" placeholder="请输入父亲工作地址" rows="4">{{userInfo.fatherJobAddress}}</textarea>
            </li>
            <li>
                <label>母亲姓名</label>
                <input ng-model="userInfo.momName" type="text" placeholder="请输入母亲姓名" value="{{userInfo.momName}}"/>
            </li>
            <li>
                <label>母亲手机号</label>
                <input ng-model="userInfo.momPhone" type="text" placeholder="请输入母亲手机号" value="{{userInfo.momPhone}}" maxlength="11" onkeyup="this.value=this.value.replace(/\D/g,'')"/>
            </li>
            <li>
                <label>母亲工作地址</label>
                <textarea ng-model="userInfo.momJobAddress" placeholder="请输入母亲工作地址" rows="4">{{userInfo.momJobAddress}}</textarea>
            </li>
            <div class="linkmanContacts" ng-if="userInfo.personJsonArys == null || userInfo.personJsonArys.length == 0">
                <li>
                    <label>常联系人1</label>
                    <input type="text" name="name" placeholder="请输入常联系人"/>
                </li>
                <li>
                    <label>电话</label>
                    <input type="text" name="phone" placeholder="请输入电话" maxlength="11" onkeyup="this.value=this.value.replace(/\D/g,'')"/>
                </li>
            </div>
            <div class="linkmanContacts" ng-repeat="person in userInfo.personJsonArys">
                <li>
                    <label>常联系人{{$index + 1}}</label>
                    <input type="text" name="name" placeholder="请输入常联系人" value="{{person.name}}"/>
                </li>
                <li>
                    <label>电话</label>
                    <input type="text" name="phone" placeholder="请输入电话" value="{{person.phone}}" maxlength="11" onkeyup="this.value=this.value.replace(/\D/g,'')"/>
                </li>
            </div>
        </ul>
    </div>

    <button class="add-linkmanContacts-btn" ng-click="addPerson()">+</button>

    <div class="button-bar">
        <button class="submit-btn" ng-click="submit()">提交</button>
    </div>

    <form id="uploadImg" method="post" enctype="multipart/form-data">
        <input class="hide" name="file" id="file" type="file" accept="image/*">
    </form>

</div>
</body>
<script>

    var webApp = angular.module('webApp', []);
    webApp.controller("appController", function($scope,$compile) {
        $scope.userInfo = null;
        $scope.idCardImg = [];
        $scope.ip = config.ip;
        $scope.addressList = [];

        /**提交数据**/
        $scope.submit = function () {
            $scope.userInfo.personJsonAry = null;
            if ($scope.idCardImg.length != 0) {
                $scope.userInfo.idCardImg = $scope.idCardImg.toString();
            }
            delete $scope.userInfo.qq;
            delete $scope.userInfo.wx;
            delete $scope.userInfo.createTime;
            delete $scope.userInfo.updateTime;
            delete $scope.userInfo.payPwd;
            delete $scope.userInfo.openId;
            delete $scope.userInfo.avatar;
            delete $scope.userInfo.phone;
            delete $scope.userInfo.isValid;
            delete $scope.userInfo.idCard;
            delete $scope.userInfo.age;
            delete $scope.userInfo.borrowJoinTotalPrice;
            delete $scope.userInfo.borrowOutTotalPrice;
            delete $scope.userInfo.creditPoints;
            delete $scope.userInfo.city;
            delete $scope.userInfo.isAtSchool;
            delete $scope.userInfo.nowToBePaidPrice;
            delete $scope.userInfo.overdueNum;
            delete $scope.userInfo.repayOrders;
            delete $scope.userInfo.userName;
            delete $scope.userInfo.personJsonArys;
            delete $scope.userInfo.outOrders;

            var isEmoji = false;
            $(".app-form").find("input").each(function () {
                if (config.isEmoji.test($(this).val())) {
                    isEmoji = true;
                }
            });
            $(".app-form").find("textarea").each(function () {
                if (config.isEmoji.test($(this).val())) {
                    isEmoji = true;
                }
            });
            if (isEmoji) {
                config.toast("不支持emoji表情");
                return false;
            }

            var personJsonAry = [];
            var isPhone = false;
            $(".linkmanContacts").each(function () {
                if (!config.isNumber.test($(this).find("input[name='phone']").val())) {
                    isPhone = true;
                }
                personJsonAry.push({
                    name : $(this).find("input[name='name']").val(),
                    phone : $(this).find("input[name='phone']").val(),
                });
            });
            if (isPhone) {
                config.toast("电话请输入整数");
                return false;
            }
            $scope.userInfo.personJsonAry = JSON.stringify(personJsonAry);
            config.log($scope.userInfo);
            config.ajaxRequestData(false, "wechat/user/update", $scope.userInfo, true, function (result) {
                config.toast(result.msg);
                setTimeout(function () {
                    window.history.back();
                }, 300);
            });
        }

        /**新增一个联系人**/
        $scope.addPerson = function () {
            var zz = "this.value=this.value.replace(/\D/g,'')";
            var len = $(".linkmanContacts").size();
            var html = '<div class="linkmanContacts">' +
                        '   <li>' +
                        '       <label>常联系人' + (len + 1) + '</label>' +
                        '       <input type="text" name="name" placeholder="请输入常联系人"/>' +
                        '   </li>' +
                        '   <li>' +
                        '       <label>电话</label>' +
                        '       <input type="text" name="phone" placeholder="请输入电话" maxlength="11" onkeyup="' + zz + '"/>' +
                        '   </li>' +
                        '</div>';
            $(".linkmanContacts:last").append(html);
        }

        /**初始化**/
        $scope.initData = function () {
            /**获取个人信息**/
            config.ajaxRequestData(false, "wechat/user/info", {}, true, function (result) {
                $scope.userInfo = result.data;
                if ($scope.userInfo.idCardImg != null) {
                    $scope.userInfo.idCardImg = $scope.userInfo.idCardImg.split(",");
                }
                if ($scope.userInfo.idCardImg != null) {
                    $scope.idCardImg = $scope.userInfo.idCardImg;
                }
                localStorage.setItem("userInfo", JSON.stringify(result.data));
            });
            /**获取地址**/
            config.ajaxRequestData(false, "city/list", {}, true, function (result) {
                $scope.addressList = result.data;
                for (var i = 0; i < $scope.addressList.length; i++) {
                    var level1 = $scope.addressList[i];
                    level1.value = level1.name;
                    level1.childs = level1.cityList;
                    for (var j = 0; j < level1.cityList.length; j++) {
                        var level2 = level1.cityList[j];
                        level2.value = level2.name;
                        level2.childs = level2.cityList;
                        for (var k = 0; k < level2.cityList.length; k++) {
                            var level3 = level2.cityList[k];
                            level3.value = level3.name;
                        }
                    }
                }
            });

            /**地区选择**/
            new MobileSelect({
                trigger: '#trigger1',
                title: '地区选择',
                wheels: [
                    {data: $scope.addressList}
                ],
                position:[0], //初始化定位 打开时默认选中的哪个 如果不填默认为0
                transitionEnd:function(indexArr, data){ },
                callback:function(indexArr, data){
                    config.log(data);
                    $scope.userInfo.cityId = data[2].id;
                    $("#trigger1").val(data[0].value + "," + data[1].value + "," + data[2].value);
                }
            });

            $('#file').change(function(){
                $("body").append(config.showLoading("上传中..."));
                var file = this.files[0];
                if(window.FileReader) {
                    var fr = new FileReader();
                    fr.onloadend = function(e) {
                        $("#uploadImg").ajaxSubmit({
                            type: "POST",
                            url: config.ip + 'res/upload3',
                            success: function(json) {
                                config.log(json);
                                if (json.code == 200) {
                                    $('#file').val("");
                                    $scope.idCardImg.push(json.data.url);
                                    var url = config.ip + json.data.url;
                                    var html = '<div class="preview-img" url="' + json.data.url + '" style="background: url(' + url + ');"><i ng-click="deleteCardImg($event)"></i></div>';
                                    $(".uploading-btn").before($compile(html)($scope)); //angular 动态绑定事件
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
        }
        $scope.initData();

        //删除身份证
        $scope.deleteCardImg = function (event) {
            var obj = event.target;
            var url = $(obj).parent().attr("url");
            config.log(url);
            $scope.idCardImg.removeValue(url);
            $(obj).parent().remove();
        }
    });



</script>

</html>
