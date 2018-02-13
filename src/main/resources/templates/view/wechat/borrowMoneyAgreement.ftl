<!DOCTYPE html>
<html>

<head>
    <title>借款协议</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no" />
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/css/common.css" />
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/css/page/agreement.css" />
    <script src="${request.contextPath}/js/jquery-2.1.0.js"></script>
    <script src="${request.contextPath}/js/angular.min.js"></script>
    <script src="${request.contextPath}/js/config.js"></script>

</head>
<body>
<div ng-app="webApp" ng-controller="appController" class="app-content">

    <h1 class="title">借款协议</h1>
    <div class="content">
        注册协议注册协议注册协议注册协议注册协议注册协议注册协议注册协议注册协议注册协议注册协议注册协议注册协议注册协议
        注册协议注册协议注册协议注册协议注册协议注册协议注册协议注册协议注册协议注册协议注册协议注册协议注册协议注册协议
        注册协议注册协议注册协议注册协议注册协议注册协议注册协议注册协议注册协议注册协议注册协议注册协议注册协议注册协议
        注册协议注册协议注册协议注册协议注册协议注册协议注册协议注册协议注册协议注册协议注册协议注册协议注册协议注册协议
        注册协议注册协议注册协议注册协议注册协议注册协议注册协议注册协议注册协议注册协议注册协议注册协议注册协议注册协议
        注册协议注册协议注册协议注册协议注册协议注册协议注册协议注册协议注册协议注册协议注册协议注册协议注册协议注册协议
    </div>

</div>
</body>
<script>

    config.reload();

    var webApp = angular.module('webApp', []);
    webApp.controller("appController", function($scope) {
        $scope.agreement = null;
        $scope.getAgreement = function () {
            config.ajaxRequestData(false, "wechat/agreement/info", {}, true, function (result) {
                $scope.agreement = result.data;
            });
        }
        //$scope.getAgreement();
    });


</script>

</html>
