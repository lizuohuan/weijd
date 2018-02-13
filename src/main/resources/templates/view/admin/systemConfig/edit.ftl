<!-- 解决layer.open 不居中问题   -->
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>系统配置</title>
    <!--引入抽取css文件-->
    <#include "../../admin/public-css.ftl">
    <style>
        .preview{height: 250px;width: 400px;margin-right: 10px;margin-bottom: 10px;float: left;text-align: center}
        .preview img{width: 100%;height:210px;border: 1px solid #eee;}

    </style>
</head>
<body ng-app="webApp">
<div style="margin: 15px;">
    <blockquote class="layui-elem-quote"><i class="fa fa-refresh" aria-hidden="true"></i>&nbsp;表单带有 <span class="font-red">“*”</span> 号的为必填项.</blockquote>
   <#-- <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
        <legend>公司信息</legend>
    </fieldset>-->
    <form class="layui-form" action="" id="formData" ng-controller="editCompanyCtr" ng-cloak>


        <div class="layui-form-item">
            <label class="layui-form-label">服务费</label>
            <div class="layui-input-block">
                <input type="text" value="{{systemConfig.serviceFee}}" name="serviceFee" placeholder="请输入服务费" autocomplete="off" class="layui-input" maxlength="50">
            </div>
        </div>



        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit="" lay-filter="demo1">立即提交</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
        </div>
    </form>
</div>
<!--引入抽取公共js-->
<#include "../../admin/public-js.ftl">
<script>

    var webApp=angular.module('webApp',[]);
    webApp.controller("editCompanyCtr", function($scope,$http,$timeout){
        $scope.systemConfig = null; //系统配置
        $scope.imgUrl = AM.ipImg;
        AM.ajaxRequestData("post", false, AM.ip + "/systemConfig/info", {} , function(result) {
            if (result.flag == 0 && result.code == 200) {
                $scope.systemConfig = result.data;
            }
        });

        layui.use(['form', 'layedit', 'laydate', 'upload', 'layedit'], function() {
            var form = layui.form(),
                    layer = layui.layer,
                    layedit = layui.layedit
                    ,$ = layui.jquery,
                    laydate = layui.laydate;
            form.render();

            //监听提交
            form.on('submit(demo1)', function(data) {
                console.log(data.field);
                if (!AM.isDouble.test(data.field.serviceFee)) {
                    layer.msg("服务费格式错误.");
                    return false;
                }

                AM.ajaxRequestData("post", false, AM.ip + "/systemConfig/update", data.field , function(result) {
                    if (result.flag == 0 && result.code == 200) {
                        var index =layer.alert('修改成功.', {
                            skin: 'layui-layer-molv' //样式类名
                            ,closeBtn: 0
                            ,anim: 4 //动画类型
                        }, function(){
                            layer.close(index);
                        });
                    }
                });
                return false;
            });
        });
    });


</script>
</body>
</html>
