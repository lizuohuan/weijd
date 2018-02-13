<!-- 解决layer.open 不居中问题   -->
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>新增平台用户</title>
    <!--引入抽取css文件-->
<#include "../../admin/public-css.ftl">
    <style>
        .preview{height: 250px;width: 400px;margin-right: 10px;margin-bottom: 10px;float: left;text-align: center}
        .preview img{width: 100%;height:210px;border: 1px solid #eee;}

    </style>
</head>
<body>
<div style="margin: 15px;">
    <blockquote class="layui-elem-quote"><i class="fa fa-refresh" aria-hidden="true"></i>&nbsp;表单带有 <span class="font-red">“*”</span> 号的为必填项.</blockquote>
    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
        <legend>新增平台用户</legend>
    </fieldset>
    <form class="layui-form" action="" id="formData">

        <div class="layui-form-item">
            <label class="layui-form-label"><span>用户名</span><span class="font-red">*</span></label>
            <div class="layui-input-inline">
                <input type="text" value="" name="userName" lay-verify="required" placeholder="请输入默认用户名" autocomplete="off" class="layui-input" maxlength="20">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label"><span>账号</span><span class="font-red">*</span></label>
            <div class="layui-input-inline">
                <input type="text" value="" name="account"
                        ime-mode:disabled"
                        onkeydown="if(event.keyCode==13)event.keyCode=9"
                        onkeyup="value=value.replace(/[/W]/g,'') "
                        onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^/d]/g,''))"
                        lay-verify="required" placeholder="请输入默认登录密码" autocomplete="off" class="layui-input" maxlength="50">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label"><span>默认登录密码</span><span class="font-red">*</span></label>
            <div class="layui-input-inline">
                <input type="text" value="111111" name="pwd" onkeyup="value=value.replace(/[\W]/g,'')"
                       onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" lay-verify="required" placeholder="请输入默认登录密码" autocomplete="off" class="layui-input" maxlength="50">
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
<script src="${request.contextPath}/admin/js/common/jQuery.md5.js"></script>
<script>
    //上传的类型 0：头像 1：营业执照/介绍信
    var type = 0;
    layui.use(['form', 'layedit', 'laydate', 'upload', 'layedit'], function() {
        var form = layui.form(),
                layer = layui.layer,
                layedit = layui.layedit
                ,$ = layui.jquery,
                laydate = layui.laydate;

        form.render();

        //监听提交
        form.on('submit(demo1)', function(data) {

            data.field.pwd = $.md5("111111");


            console.log(data.field);
            AM.ajaxRequestData("post", false, AM.ip + "/admins/save", data.field  , function(result) {
                if (result.flag == 0 && result.code == 200) {
                    layer.alert('添加成功.', {
                        skin: 'layui-layer-molv' //样式类名
                        ,closeBtn: 0
                        ,anim: 3 //动画类型
                    }, function(){
                        //关闭iframe页面
                        var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                        parent.layer.close(index);
                        window.parent.closeNodeIframe();
                    });
                } else {
                    layer.msg(result.msg, {icon: 2,anim: 6});
                    return false;
                }
            });
            return false;
        });
    });

</script>
</body>
</html>
