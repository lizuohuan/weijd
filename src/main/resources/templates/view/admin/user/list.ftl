<!-- 解决layer.open 不居中问题 -->
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>用户列表</title>
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
                            <label class="layui-form-label">姓名</label>
                            <div class="layui-input-inline">
                                <input type="text" name="userName" id="userName" lay-verify=""
                                       placeholder="姓名" autocomplete="off" class="layui-input"
                                       maxlength="20">
                            </div>
                        </div>

                        <div class="layui-inline">
                            <label class="layui-form-label">手机号</label>
                            <div class="layui-input-inline">
                                <input type="text" id="phone" lay-verify="" placeholder="请输入手机号" autocomplete="off"
                                       class="layui-input" maxlength="20">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">身份证号</label>
                            <div class="layui-input-inline">
                                <input type="number" id="idCard" lay-verify="" placeholder="身份证号" autocomplete="off"
                                       class="layui-input" maxlength="20">
                            </div>
                        </div>
                    </div>

                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label">年龄</label>
                            <div class="layui-input-inline">
                                <input type="number" name="age" id="age" lay-verify=""
                                       placeholder="年龄" autocomplete="off" class="layui-input"
                                       maxlength="20">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">是否为出借人</label>
                            <div class="layui-input-inline">
                                <select name="isFromUser" id="isFromUser" lay-verify="required" lay-search>
                                    <option value="">全部</option>
                                    <option value="">全部</option>
                                    <option value="0">否</option>
                                    <option value="1">是</option>
                                    <option value="2">已申请/审核中</option>
                                    <option value="3">已拒绝</option>
                                </select>
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
        </legend>
        <div class="layui-field-box layui-form">
            <table id="dataTable" class="layui-table admin-table table-bordered display" cellspacing="0" width="100%">
                <thead>
                <tr>
                    <th>头像</th>
                    <th>姓名</th>
                    <th>年龄</th>
                    <th>手机号</th>
                    <th>身份证号</th>
                    <#--<th>是否是在校生</th>-->
                    <th>注册时间</th>
                    <th>审核内容</th>
                    <th>是否为出借人</th>
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
                url: AM.ip + "/user/list",
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
                    data.idCard = $("#idCard").val();
                    data.phone = $("#phone").val();
                    data.age = $("#age").val();
                    data.isFromUser = $("#isFromUser").val();
                }
            },
            "columns": [
                {"data": "avatar"},
                {"data": "userName"},
                {"data": "age"},
                {"data": "phone"},
                {"data": "idCard"},
//                {"data": "isAtSchool"},
                {"data": "createTime"},
                {"data": "remark"},
                {"data": "isFromUser"},
            ],
            "columnDefs": [
                {
                    "render": function (data, type, row) {

                        if (null != data) {
                            var imgUrl = data;

                            if (imgUrl.indexOf("wx") < 0) {
                                imgUrl = AM.ip + "/" + data;
                            }
                            return "<img height='auto' width='100' src='" + imgUrl + "'>";
                        } else {
                            return "--";
                        }
                    },
                    "targets": 0
                },
                {
                    "render": function (data, type, row) {
                        if (data == null) {
                            return "--";
                        }
                        return new Date(data).format("yyyy-MM-dd hh:mm:ss");
                    },
                    "targets": 5
                },
                {
                    "render": function (data, type, row) {
                        if (data == null || data == "") {
                            return "--";
                        }
                        return data;
                    },
                    "targets": 6
                },
                {
                    "render": function (data, type, row) {
                        if (null == data || data == "") { return "--"; }
                        if (data == 0) {
                            return "否";
                        }
                        else if (data == 1) {
                            return "是";
                        }
                        else if (data == 2) {
                            return "已申请/审核中";
                        }
                        else if (data == 3) {
                            return "已拒绝";
                        }
                    },
                    "targets": 7
                },
                {
                    "render": function (data, type, row) {
                        var btn = "";
                        if (row.isFromUser == 2) {
                            btn += "<button onclick='updateStatus(" + row.id + ", 1)' class='layui-btn layui-btn-small layui-btn-warm'><i class='fa fa-list fa-edit'></i>&nbsp;通过审核</button>";
                            btn += "<button onclick='updateStatus(" + row.id + ", 3)' class='layui-btn layui-btn-small layui-btn-danger'><i class='fa fa-list fa-edit'></i>&nbsp;拒绝审核</button>";
                        }

                        if (row.isFromUser == 1) {
                            btn += "<button onclick='updateStatus(" + row.id + ", 3)' class='layui-btn layui-btn-small layui-btn-danger'><i class='fa fa-list fa-edit'></i>&nbsp;取消通过</button>";
                        }
                        return "<button onclick='updateData(" + row.id + ")' class='layui-btn layui-btn-small'><i class='fa fa-list fa-edit'></i>&nbsp;查看</button>"
                                + btn
                                ;
                    },
                    "targets": 8
                },
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

    /**修改状态**/
    function updateStatus (id, status) {
        var index = layer.load(1, {shade: [0.5, '#eee']});
        AM.ajaxRequestData("post", false, AM.ip + "/user/update", {id : id, isFromUser: status} , function(result) {
            dataTable.ajax.reload();
        });
        setTimeout(function () { layer.close(index); }, 600);
    }


    layui.use(['form', 'layedit', 'laydate'], function () {
        form = layui.form(),
                layer = layui.layer,
                laydate = layui.laydate;

    });


</script>
</body>
</head>
</html>
