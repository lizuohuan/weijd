<!DOCTYPE html>
<html>

<head>
    <title>投诉举报</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no" />
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/css/common.css" />
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/css/page/feedback.css" />
    <script src="${request.contextPath}/js/jquery-2.1.0.js"></script>
    <script src="${request.contextPath}/js/jquery.form.js"></script>
    <script src="${request.contextPath}/js/angular.min.js"></script>
    <script src="${request.contextPath}/js/config.js"></script>
</head>
<body>
<div ng-app="webApp" ng-controller="appController" class="app-content">

    <div class="title">投诉举报</div>
    <div class="data-form">
        <ul class="app-form">
            <li>
                <label>姓名</label>
                <input type="text" ng-model="userName" placeholder="请输入您的姓名" />
            </li>
            <li>
                <label>手机号码</label>
                <input type="text" ng-model="phone" placeholder="请输入您的手机号码"  maxlength="11" onKeypress="return (/[\d.]/.test(String.fromCharCode(event.keyCode)))"/>
            </li>
            <li>
                <label>反馈内容</label>
                <textarea ng-model="content" placeholder="请输入反馈内容" rows="4"></textarea>
            </li>
            <li>
                <div class="title-img">图片上传</div>
                <div class="preview">
                    <div onclick="$('#file').click()" class="uploading-btn">+</div>
                </div>
            </li>
        </ul>
    </div>

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
        $scope.imgList = [];
        $scope.userName = "";
        $scope.content = "";
        $scope.phone = "";
        /**提交**/
        $scope.submit = function () {
            if ($scope.userName == "") {
                config.toast("请输入您的姓名");
                return false;
            }
            else if ($scope.phone == "") {
                config.toast("请输入您的手机号码");
                return false;
            }
            else if ($scope.content == "") {
                config.toast("请输入反馈内容");
                return false;
            }
            config.ajaxRequestData(false, "wechat/suggest/save", {
                userName : $scope.userName,
                content : $scope.content,
                phone : $scope.phone,
                imgUrl : $scope.imgList.toString(),
            }, true, function (result) {
                config.alert("温馨提示", "一至三个工作日给您的反馈处理","确定", function () {
                    window.history.back();
                });
            });
        }
        /**初始化**/
        $scope.initData = function () {
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
                                    $scope.imgList.push(json.data.url);
                                    var url = config.ip + json.data.url;
                                    var html = '<div class="preview-img" url="' + json.data.url + '" style="background: url(' + url + ');"><i ng-click="deleteImg($event)"></i></div>';
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

        //删除图片
        $scope.deleteImg = function (event) {
            var obj = event.target;
            var url = $(obj).parent().attr("url");
            config.log(url);
            $scope.imgList.removeValue(url);
            $(obj).parent().remove();
        }
    });

</script>

</html>
