<!-- 解决layer.open 不居中问题   -->
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>借条列表</title>
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
                            <label class="layui-form-label">借款人</label>
                            <div class="layui-input-inline">
                                <input type="text" id="toUserName" placeholder="请输入借款人" autocomplete="off" class="layui-input" maxlength="20">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">借款的开始时间</label>
                            <div class="layui-input-inline">
                                <input type="text" id="startTimeCs"  readonly lay-verify="" placeholder="请输入开始时间" autocomplete="off" class="layui-input" maxlength="20">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">借款的结束时间</label>
                            <div class="layui-input-inline">
                                <input type="text" id="endTimeCs"  readonly lay-verify="" placeholder="请输入结束时间" autocomplete="off" class="layui-input" maxlength="20">
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label">出借人</label>
                            <div class="layui-input-inline">
                                <input type="text" id="fromUserName" placeholder="请输入出借人" autocomplete="off" class="layui-input" maxlength="20">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">预计还款的开始时间</label>
                            <div class="layui-input-inline">
                                <input type="text" id="startTimeRs"  readonly lay-verify="" placeholder="请输入开始时间" autocomplete="off" class="layui-input" maxlength="20">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">预计还款的结束时间</label>
                            <div class="layui-input-inline">
                                <input type="text" id="endTimeRs"  readonly lay-verify="" placeholder="请输入结束时间" autocomplete="off" class="layui-input" maxlength="20">
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label">实际还款的开始时间</label>
                            <div class="layui-input-inline">
                                <input type="text" id="startTimeTs"  readonly lay-verify="" placeholder="请输入开始时间" autocomplete="off" class="layui-input" maxlength="20">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">实际还款的结束时间</label>
                            <div class="layui-input-inline">
                                <input type="text" id="endTimeTs"  readonly lay-verify="" placeholder="请输入结束时间" autocomplete="off" class="layui-input" maxlength="20">
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
        <legend>借条列表&nbsp;<i class="fa fa-refresh" aria-hidden="true"></i></legend>
        </legend>
        <div class="layui-field-box layui-form">
            <table id="dataTable" class="layui-table admin-table table-bordered display" cellspacing="0" width="100%">
                <thead>
                <tr>
                    <th>借款人</th>
                    <th>出借人</th>
                    <th>借款金额</th>
                    <th>产生利息</th>
                    <th>实际还款金额</th>
                    <th>借款时间</th>
                    <th>预计还款时间</th>
                    <th>实际还款时间</th>
                    <th>操作</th>
                </tr>
                </thead>
            </table>
        </div>
    </fieldset>
</div>

<!--引入抽取公共js-->
<#include "../../admin/public-js.ftl">
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
                url: AM.ip + "/order/list2",
                "dataSrc": function (json) {
                    console.log(json);
                    if (json.code == 200) {
                        return json.data;
                    }
                    return [];
                },
                "data": function (data) {
                    //高级查询参数
                    data.fromUserName = $("#fromUserName").val();
                    data.toUserName = $("#toUserName").val();
                    data.startTimeCs = $("#startTimeCs").val();
                    data.endTimeCs = $("#endTimeCs").val();
                    data.startTimeRs = $("#startTimeRs").val();
                    data.endTimeRs = $("#endTimeRs").val();
                    data.startTimeTs = $("#startTimeTs").val();
                    data.endTimeTs = $("#endTimeTs").val();
                }
            },
            "columns": [
                {"data": "toUserName"},
                {"data": "fromUserName"},
                {"data": "price"},
                {"data": "interest"},
                {"data": "trueRepaymentPrice"},
                {"data": "createTime"},
                {"data": "repaymentTime"},
                {"data": "trueRepaymentTime"},
            ],
            "columnDefs": [
                {
                    "render": function (data, type, row) {
                        if (data == null || data == "") {
                            return "--";
                        }
                        return data;
                    },
                    "targets": 0
                },
                {
                    "render": function (data, type, row) {
                        if (data == null || data == "") {
                            return "--";
                        }
                        return data;
                    },
                    "targets": 1
                },
                {
                    "render": function (data, type, row) {
                        if (data == null || data == "") {
                            return "--";
                        }
                        return data;
                    },
                    "targets": 4
                },
                {
                    "render": function (data, type, row) {
                        if (data == null || data == "") {
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
                        return new Date(data).format("yyyy-MM-dd hh:mm:ss");
                    },
                    "targets": 6
                },
                {
                    "render": function (data, type, row) {
                        if (data == null || data == "") {
                            return "--";
                        }
                        return new Date(data).format("yyyy-MM-dd hh:mm:ss");
                    },
                    "targets": 7
                },
                {
                    "render": function (data, type, row) {
                        var btn = "";
                        if (row.status == 4) {
                            return "<button onclick='updateData(" + row.id + ")' class='layui-btn layui-btn-small'><i class='fa fa-list fa-edit'></i>&nbsp;设置还款</button>";
                        }
                        else {
                            return "<button class='layui-btn layui-btn-small layui-btn-disabled'><i class='fa fa-list fa-edit'></i>&nbsp;设置还款</button>";
                        }
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

    /**设置还款日**/
    function updateData (id) {
        console.log(id);
        var html = '<form class="layui-form" action="">' +
                '       <div class="layui-form-item">' +
                '           <label class="layui-form-label">还款日</label>' +
                '           <div class="layui-input-inline">' +
                '               <input readonly placeholder="请选择还款日" class="layui-input" type="text" id="repaymentTime" onclick="layui.laydate({elem: this})">' +
                '           </div>' +
                '       </div>' +
                '    </form>';

        var index = layer.confirm(html, {
            btn: ['提交','取消'],
            title: "设置还款日",
            area: ["600px", "500px"],
        }, function () {
            if ($("#repaymentTime").val() == "") {
                layer.msg("请选择还款日");
                return false;
            }
            AM.ajaxRequestData("post", false, AM.ip + "/order/update", {
                id: id,
                trueRepaymentTime : new Date($("#repaymentTime").val()),
            }, function (result) {
                layer.close(index);
                dataTable.ajax.reload();
            });
        }, function () {});
    }

    layui.use(['form', 'layedit', 'laydate'], function () {
        form = layui.form(),
                layer = layui.layer,
                laydate = layui.laydate;

        var start = {
            max: '2099-06-16 23:59:59'
            ,format: 'YYYY-MM-DD hh:mm:ss'
            ,istoday: false
            ,choose: function(datas){
                end.min = datas; //开始日选好后，重置结束日的最小日期
                end.start = datas //将结束日的初始值设定为开始日
            }
        };

        var end = {
            max: '2099-06-16 23:59:59'
            ,format: 'YYYY-MM-DD hh:mm:ss'
            ,istoday: false
            ,choose: function(datas){
                start.max = datas; //结束日选好后，重置开始日的最大日期
            }
        };

        document.getElementById('startTimeCs').onclick = function(){
            start.elem = this;
            laydate(start);
        }
        document.getElementById('endTimeCs').onclick = function(){
            end.elem = this
            laydate(end);
        }

        document.getElementById('startTimeRs').onclick = function(){
            start.elem = this;
            laydate(start);
        }
        document.getElementById('endTimeRs').onclick = function(){
            end.elem = this
            laydate(end);
        }

        document.getElementById('startTimeTs').onclick = function(){
            start.elem = this;
            laydate(start);
        }
        document.getElementById('endTimeTs').onclick = function(){
            end.elem = this
            laydate(end);
        }

    });


</script>
</body>
</head></html>
