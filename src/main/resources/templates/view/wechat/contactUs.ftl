<!DOCTYPE html>
<html>

<head>
    <title>联系我们</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no" />
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/css/common.css" />
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/css/page/contactUs.css" />
    <script src="${request.contextPath}/js/jquery-2.1.0.js"></script>
    <script src="${request.contextPath}/js/angular.min.js"></script>
    <script src="${request.contextPath}/js/config.js"></script>
</head>
<body>
<div ng-app="webApp" ng-controller="appController" class="app-content">

    <div class="contactUs-bg">
        <div class="title">
            <i></i>
            <div class="title-han">联系我们</div>
            <div class="title-en">contact us</div>
        </div>
        <div class="bottom">
            <div class="">客服电话</div>
            <div class="service">{{info.phone}}</div>
            <div class="">客服QQ</div>
            <div class="service">{{info.qq}}</div>
            <div class="">工作时间</div>
            <div class="service last-service">{{info.jobTime}}</div>
            <div class="hint" ng-if="info.isHolidaysAndFestivalsExcept == 1">(国家法定节假日除外)</div>
        </div>
    </div>

</div>
</body>
<script>

    var webApp = angular.module('webApp', []);
    webApp.controller("appController", function($scope) {
        $scope.info = [];
        $scope.ip = config.ip;
        $scope.getInfo = function () {
            config.ajaxRequestData(false, "wechat/contactUs/info", {}, true, function (result) {
                $scope.info = result.data;
            });
        }
        $scope.getInfo();
    });

</script>

</html>
