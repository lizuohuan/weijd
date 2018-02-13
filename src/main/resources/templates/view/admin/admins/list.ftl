<!-- 解决layer.open 不居中问题 -->
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>平台用户列表</title>
    <!--引入抽取css文件-->
<#include "../../admin/public-css.ftl">

<body>

<div class="admin-main">

    <blockquote class="layui-elem-quote">
        <fieldset class="layui-elem-field">
            <legend>高级筛选</legend>
            <div class="layui-field-box layui-form">
                <form class="layui-form" action="" id="formData">
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label">用户名</label>
                            <div class="layui-input-inline">
                                <input type="text" name="userName" id="userName" lay-verify=""
                                       placeholder="姓名" autocomplete="off" class="layui-input"
                                       maxlength="20">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">账号</label>
                            <div class="layui-input-inline">
                                <input type="text" name="account" id="account" lay-verify=""
                                       placeholder="账号" autocomplete="off" class="layui-input"
                                       maxlength="20">
                            </div>
                        </div>
                    </div>

                    <div class="layui-form-item">
                        <div class="layui-input-block">
                            <button class="layui-btn" id="search"><i class="layui-icon">&#xe615;</i> 搜索</button>
                            <button type="reset" class="layui-btn layui-btn-primary">清空</button>
                        </div>
                    </div>
                </form>
            </div>
        </fieldset>
    </blockquote>

    <fieldset class="layui-elem-field">
        <legend>用户列表&nbsp;<i class="fa fa-refresh" aria-hidden="true"></i></legend>
        <blockquote class="layui-elem-quote">
            <button onclick="addData()" class="layui-btn layui-btn layui-btn-small layui-btn-normal"><i class="layui-icon">&#xe608;</i> 新增平台用户</button>
        </blockquote>
        </legend>
        <div class="layui-field-box layui-form">
            <table id="dataTable" class="layui-table admin-table table-bordered display" cellspacing="0" width="100%">
                <thead>
                <tr>
                    <th>用户名</th>
                    <th>账号</th>
                    <th>操作</th>
                </tr>
                </thead>
            </table>
        </div>
    </fieldset>
</div>

<!--引入抽取公共js-->
<#include "../../admin/public-js.ftl">
<script src="${request.contextPath}/admin/js/common/jQuery.md5.js"></script>
<script>

    var form = null;
    var dataTable = null;
    $(document).ready(function () {
        dataTable = $('#dataTable').DataTable({
            "searching": false, "bStateSave": true, //状态保存，使用了翻页或者改变了每页显示数据数量，会保存在cookie中，下回访问时会显示上一次关闭页面时的内容。
            "processing": true,
            "serverSide": true,
            "bLengthChange": false, "bSort": false, //关闭排序功能
            //"pagingType": "bootstrap_full_number",
            'language': {
                'emptyTable': '没有数据',
                'loadingRecords': '加载中...',
                'processing': '查询中...',
                'search': '全局搜索:',
                'lengthMenu': '每页 _MENU_ 件',
                'zeroRecords': '没有您要搜索的内容',
                'paginate': {
                    'first': '第一页',
                    'last': '最后一页',
                    'next': '下一页',
                    'previous': '上一页'
                },
                'info': '第 _PAGE_ 页 / 总 _PAGES_ 页',
                'infoEmpty': '没有数据',
                'infoFiltered': '(过滤总件数 _MAX_ 条)'
            },
            //dataTable 加载加载完成回调函数
            "fnDrawCallback": function (sName, oData, sExpires, sPath) {
                form.render();
            },
            "ajax": {
                url: AM.ip + "/admins/list",
                "dataSrc": function (json) {
                    console.log(json);
                    if (json.code == 200) {
                        return json.data;
                    }
                    return [];
                },
                "data": function (data) {
                    //高级查询参数
                    data.userName = $("#userName").val();
                    data.account = $("#account").val();
                }
            },
            "columns": [
                {"data": "userName"},
                {"data": "account"},
            ],
            "columnDefs": [
                {
                    "render": function (data, type, row) {
                        var btn = "";
                        return "<button onclick='updateUserName(" + row.id + ")' class='layui-btn layui-btn-small'><i class='fa fa-list fa-edit'></i>&nbsp;修改用户名</button>"
                                + btn
                                ;
                    },
                    "targets": 2
                }
            ]
        });

        $("#search").click(function () {
            dataTable.ajax.reload();
            return false;
        });

    });

    //提供给子页面
    var closeNodeIframe = function () {
        dataTable.ajax.reload();
        var index = layer.load(1, {shade: [0.5, '#eee']});
        setTimeout(function () {
            layer.close(index);
        }, 600);
    }

    //添加
    function addData () {
        var index = layer.open({
            type: 2,
            title: '新增平台用户',
            shadeClose: true,
            maxmin: true, //开启最大化最小化按钮
            area: ['400px', '500px'],
            content: AM.ip + "/page/admins/save"
        });
        layer.full(index);
    }

    //查看/修改数据
    function updateData(id) {
        //sessionStorage.setItem("curriculum", JSON.stringify(obj));
        var index = layer.open({
            type: 2,
            title: '修改课程',
            shadeClose: true,
            maxmin: true, //开启最大化最小化按钮
            area: ['400px', '500px'],
            content: AM.ip + "/page/user/edit?id=" + id
        });
        layer.full(index);
    }


    layui.use(['form', 'layedit', 'laydate'], function () {
        form = layui.form(),
                layer = layui.layer,
                laydate = layui.laydate;

    });
    //查看/修改数据
    function updateUserName(id) {
        var html = '<form class="layui-form" action="" style="height: 100px;">' +
                '<div class="layui-form-item">' +
                '<div class="layui-inline">' +
                '<label class="layui-form-label">修改用户名</label>' +
                '<div class="layui-input-inline">' +
                '<input type="text" id="userName1" lay-verify="" placeholder="修改用户名" autocomplete="off" maxlength="20" class="layui-input">' +
                '</div>' +
                '</div>' +
                '</div>' +
                '</form>';


        var index = layer.confirm(html, {
            btn: ['确认','取消'],
            title: "导入用户",
            area: ["600px", "100px"], //宽高
        }, function () {
            if ($("#score").val() == "") {
                layer.msg('请输入用户名.', {icon: 2, anim: 6});
                $("#score").focus();
                return false;
            }
            var arr={
                id:id,
                userName:$("#userName1").val()
            }
            AM.ajaxRequestData("post", false, AM.ip + "/admins/update", arr  , function(result) {
                if (result.flag == 0 && result.code == 200) {
                    var index = layer.alert('操作成功.', {
                        skin: 'layui-layer-molv' //样式类名
                        ,closeBtn: 0
                        ,anim: 3 //动画类型
                    }, function(){
                        layer.close(index);
                        dataTable.ajax.reload();
                    });
                }
            });
            layer.close(index);
        }, function () {

        });

    }

</script>
</body>
</head>
</html>
