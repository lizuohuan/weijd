<!-- 解决layer.open 不居中问题   -->
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>修改用户</title>
    <!--引入抽取css文件-->
<#include "../../admin/public-css.ftl">
    <style>
        .preview{height: 200px;width: 300px;margin-right: 10px;margin-bottom: 10px;float: left;text-align: center}
        .preview img{width: 100%;height:210px;border: 1px solid #eee;}
        input[disabled],input[disabled]:hover{color: #0C0C0C !important;}
    </style>
</head>
<body ng-app="webApp">
<div style="margin: 15px;">
    <blockquote class="layui-elem-quote"><i class="fa fa-refresh" aria-hidden="true"></i>&nbsp;表单带有 <span class="font-red">“*”</span> 号的为必填项.</blockquote>
    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
        <legend>用户详情</legend>
    </fieldset>
    <form class="layui-form" action="" id="formData" ng-controller="editUserCtr" ng-cloak>

        <fieldset class="layui-elem-field">
            <legend>个人资料</legend>
            <div class="layui-field-box">
                <div class="layui-form-item">
                    <label class="layui-form-label">头像</label>
                    <div class="layui-input-block" id="preview">
                        <div class="preview" ng-if="user.avatar != null">
                            <a target="_blank" href="{{user.avatar}}"><img src="{{user.avatar}}"></a><br>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">姓名</label>
                    <div class="layui-input-inline">
                        <input type="text" name="userName" value="{{user.userName}}" lay-verify="required" disabled autocomplete="off" class="layui-input" maxlength="50">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">手机号</label>
                    <div class="layui-input-inline">
                        <input type="text" name="phone" value="{{user.phone}}" lay-verify="required" disabled autocomplete="off" class="layui-input" maxlength="50">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">身份证号</label>
                    <div class="layui-input-inline">
                        <input type="text" name="idCard" value="{{user.idCard}}" lay-verify="required" disabled autocomplete="off" class="layui-input" maxlength="50">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">微信号</label>
                    <div class="layui-input-inline">
                        <input type="text" name="wx" value="{{user.wx}}" lay-verify="required" disabledautocomplete="off" class="layui-input" maxlength="50">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">QQ</label>
                    <div class="layui-input-inline">
                        <input type="text" name="qq" value="{{user.qq}}" lay-verify="required" disabled autocomplete="off" class="layui-input" maxlength="50">
                    </div>
                </div>
            </div>
        </fieldset>

        <fieldset class="layui-elem-field">
            <legend>补充资料</legend>
            <div class="layui-field-box">
                <div class="layui-form-item">
                    <label class="layui-form-label">工作单位</label>
                    <div class="layui-input-inline">
                        <input type="text" name="job" value="{{user.job}}" lay-verify="required" disabled autocomplete="off" class="layui-input" maxlength="50">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">家庭住址</label>
                    <div class="layui-input-inline">
                        <input type="text" name="mergerName" value="{{user.city.mergerName}}" lay-verify="required" disabled autocomplete="off" class="layui-input" maxlength="50">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">详细地址</label>
                    <div class="layui-input-inline">
                        <input type="text" name="address" value="{{user.address}}" lay-verify="required" disabled autocomplete="off" class="layui-input" maxlength="50">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">身份证正面</label>
                    <div class="layui-input-block" id="preview">
                        <div class="preview" ng-if="user.idCardImg != null">
                            <img src="{{imgUrl}}/{{user.idCardImg}}"><br>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">父亲姓名</label>
                    <div class="layui-input-inline">
                        <input type="text" name="fatherName" value="{{user.fatherName}}" lay-verify="required" disabled autocomplete="off" class="layui-input" maxlength="50">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">父亲手机号</label>
                    <div class="layui-input-inline">
                        <input type="text" name="fatherPhone" value="{{user.fatherPhone}}" lay-verify="required" disabled autocomplete="off" class="layui-input" maxlength="50">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">父亲工作地址</label>
                    <div class="layui-input-inline">
                        <input type="text" name="fatherJobAddress" value="{{user.fatherJobAddress}}" lay-verify="required" disabled autocomplete="off" class="layui-input" maxlength="50">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">母亲姓名</label>
                    <div class="layui-input-inline">
                        <input type="text" name="momName" value="{{user.momName}}" lay-verify="required" disabled autocomplete="off" class="layui-input" maxlength="50">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">母亲手机号</label>
                    <div class="layui-input-inline">
                        <input type="text" name="momPhone" value="{{user.momPhone}}" lay-verify="required" disabled autocomplete="off" class="layui-input" maxlength="50">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">母亲工作地址</label>
                    <div class="layui-input-inline">
                        <input type="text" name="momJobAddress" value="{{user.momJobAddress}}" lay-verify="required" disabled autocomplete="off" class="layui-input" maxlength="50">
                    </div>
                </div>

                <div class="layui-form-item" ng-repeat="person in user.personJsonArys">
                    <label class="layui-form-label">常用联系人{{$index+1}}</label>
                    <div class="layui-input-inline">
                        <input type="text" name="name" value="{{person.name}}" lay-verify="required" disabled autocomplete="off" class="layui-input" maxlength="50">
                    </div>
                    <label class="layui-form-label">手机号</label>
                    <div class="layui-input-inline">
                        <input type="text" name="phone" value="{{person.phone}}" lay-verify="required" disabled autocomplete="off" class="layui-input" maxlength="50">
                    </div>
                </div>

            </div>
        </fieldset>





        <#--<div class="layui-form-item">-->
            <#--<div class="layui-input-block">-->
                <#--<button class="layui-btn" lay-submit="" lay-filter="demo1">立即提交</button>-->
                <#--<button type="reset" class="layui-btn layui-btn-primary">重置</button>-->
            <#--</div>-->
        <#--</div>-->
    </form>
</div>
<!--引入抽取公共js-->
<#include "../../admin/public-js.ftl">
<script>

    var webApp=angular.module('webApp',[]);
    webApp.controller("editUserCtr", function($scope,$http,$timeout){

        $scope.user = null;//JSON.parse(sessionStorage.getItem("curriculum"));//获取课程对象
        $scope.id = AM.getUrlParam("id");
        AM.ajaxRequestData("get", false, AM.ip + "/user/info", {id : $scope.id} , function(result) {
            $scope.user = result.data;
        });
        console.log($scope.user);
        $scope.imgUrl = AM.ipImg;
        layui.use(['form', 'layedit', 'laydate', 'upload', 'layedit'], function() {
            var form = layui.form(),
                    layer = layui.layer,
                    layedit = layui.layedit
                    ,$ = layui.jquery,
                    laydate = layui.laydate;



            form.render();

        });
    });






</script>
</body>
</html>
