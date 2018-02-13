<!-- 解决layer.open 不居中问题   -->
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>联系我们</title>
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
    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
        <legend>公司信息</legend>
    </fieldset>
    <form class="layui-form" action="" id="formData" ng-controller="editCompanyCtr" ng-cloak>


        <div class="layui-form-item">
            <label class="layui-form-label">客服电话</label>
            <div class="layui-input-block">
                <input type="text" value="{{contactUs.phone}}" name="phone" placeholder="请输入客服电话" autocomplete="off" class="layui-input" maxlength="50">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">客服QQ</label>
            <div class="layui-input-block">
                <input type="text" value="{{contactUs.qq}}" name="qq" placeholder="请输入客服QQ" autocomplete="off" class="layui-input" maxlength="50">
            </div>
        </div>


        <div class="layui-form-item">
            <label class="layui-form-label">工作时间段</label>
            <div class="layui-input-block">
                <input type="text" value="{{contactUs.jobTime}}" name="jobTime" placeholder="请输入工作时间段" autocomplete="off" class="layui-input" maxlength="50">
            </div>
            <label class="layui-form-label">节假日是否除外</label>
            <div class="layui-input-block">
                <input type="radio" name="isHolidaysAndFestivalsExcept" value="0" title="否" checked="">
                <input type="radio" name="isHolidaysAndFestivalsExcept" value="1" title="是">
            </div>
        </div>

        <#--<div class="layui-form-item" ng-show="curriculum.type != 0">-->
            <#--<label class="layui-form-label">是否是推荐课程</label>-->
            <#--<div class="layui-input-block">-->
                <#--<input type="radio" name="isRecommend" value="0" title="否" checked="">-->
                <#--<input type="radio" name="isRecommend" value="1" title="是">-->
            <#--</div>-->
        <#--</div>-->



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
        $scope.contactUs = null; //公司信息
        $scope.imgUrl = AM.ipImg;
        AM.ajaxRequestData("post", false, AM.ip + "/contactUs/info", {} , function(result) {
            if (result.flag == 0 && result.code == 200) {
                $scope.contactUs = result.data;
            }
        });

        layui.use(['form', 'layedit', 'laydate', 'upload', 'layedit'], function() {
            var form = layui.form(),
                    layer = layui.layer,
                    layedit = layui.layedit
                    ,$ = layui.jquery,
                    laydate = layui.laydate;

            //选中是否推荐
            $("input[name='isHolidaysAndFestivalsExcept']").each(function () {
                if (Number($(this).val()) == Number($scope.contactUs.isHolidaysAndFestivalsExcept)) {
                    $(this).click();
                }
            });

            form.render();

            //监听提交
            form.on('submit(demo1)', function(data) {
                console.log(data.field);
                AM.ajaxRequestData("post", false, AM.ip + "/contactUs/update", data.field , function(result) {
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


    //删除图片
    function deleteImg (object) {
        var url = $(object).attr("logo");
        var logos = $("#logo").val().split(",");
        for (var i = 0; i < logos.length; i++) {
            if (logos[i] == url) {
                logos.splice(i, 1);
                $(object).parent().fadeOut();
                break;
            }
        }
        if (logos.length == 0) $("#preview").parent().hide();
        console.log(logos);
        $("#logo").val(logos.toString());
        $("#uploadImgDiv").show();
    }
</script>
</body>
</html>
