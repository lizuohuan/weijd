<!-- 解决layer.open 不居中问题   -->
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>统计</title>
    <!--引入抽取css文件-->
<#include "../../admin/public-css.ftl">

<body ng-app="webApp">

<div class="admin-main">

    <fieldset class="layui-elem-field">
        <legend>总统计</legend>
        </legend>
        <div class="layui-field-box layui-form">
            <table id="" class="layui-table admin-table table-bordered display" cellspacing="0" width="100%">
                <thead>
                <tr>
                    <th>总借款金额</th>
                    <th>总出借金额</th>
                    <#--<th>大学生数量</th>-->
                    <th>总人数</th>
                </tr>
                <tr ng-controller="editOrderCtr" ng-cloak>
                    <th>{{order.totalPrice}}</th>
                    <th>{{order.totalPrice}}</th>
                    <#--<th>{{order.bigStudentNum}}</th>-->
                    <th>{{order.allPersonNum}}</th>
                </tr>
                </thead>
            </table>
        </div>
    </fieldset>
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
                                <input type="text" onkeyup="value=value.replace(/[^\w\.\/]/ig,'')" id="idCard" lay-verify="" placeholder="身份证号" autocomplete="off"
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
                        <#--<div class="layui-inline">-->
                            <#--<label class="layui-form-label">反馈的开始时间</label>-->
                            <#--<div class="layui-input-inline">-->
                                <#--<input type="text" id="startTimes"  readonly lay-verify="" placeholder="请输入开始时间" autocomplete="off" class="layui-input" maxlength="20">-->
                            <#--</div>-->
                        <#--</div>-->
                        <#--<div class="layui-inline">-->
                            <#--<label class="layui-form-label">反馈的结束时间</label>-->
                            <#--<div class="layui-input-inline">-->
                                <#--<input type="text" id="endTimes"  readonly lay-verify="" placeholder="请输入结束时间" autocomplete="off" class="layui-input" maxlength="20">-->
                            <#--</div>-->
                        <#--</div>-->
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
        <legend>用户统计列表&nbsp;<i class="fa fa-refresh" aria-hidden="true"></i></legend>
        </legend>
        <div class="layui-field-box layui-form">
            <table id="dataTable" class="layui-table admin-table table-bordered display" cellspacing="0" width="100%">
                <thead>
                <tr>
                    <th>姓名</th>
                    <th>年龄</th>
                    <th>手机号</th>
                    <th>身份证号</th>
                    <th>借款金额</th>
                    <th>出借金额</th>
                    <#--<th>是否是在校生</th>-->
                    <th>信用值</th>
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
                url: AM.ip + "/user/statisticsListForAdmin",
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
//                    data.startTimes = $("#startTimes").val();
//                    data.endTimes = $("#endTimes").val();
                }
            },
            "columns": [
                {"data": "userName"},
                {"data": "age"},
                {"data": "phone"},
                {"data": "idCard"},
                {"data": "borrowJoinTotalPrice"},
                {"data": "borrowOutTotalPrice"},
//                {"data": "isAtSchool"},
                {"data": "creditPoints"},
            ],
            "columnDefs": [

//                {
//                    "render": function (data, type, row) {
//                        if (data == null || data == "") {
//                            return "--";
//                        }
//                        return new Date(data).format("yyyy-MM-dd hh:mm:ss");
//                    },
//                    "targets": 3
//                },
                {
                    "render": function (data, type, row) {
                        if (null == data) {
                            return "--";
                        } else {
                            return data;
                        }
                    },
                    "targets": 0
                },
                {
                    "render": function (data, type, row) {
                        if (null == data) {
                            return "--";
                        } else {
                            return data;
                        }
                    },
                    "targets": 1
                },
                {
                    "render": function (data, type, row) {
                        if (null == data) {
                            return "--";
                        } else {
                            return data;
                        }
                    },
                    "targets": 3
                },
//                {
//                    "render": function (data, type, row) {
//
//                        if (data == 0) return "否";
//                        else if (data == 1) return "是";
//                        else return "--";
//                    },
//                    "targets": 6
//                },
                {
                    "render": function (data, type, row) {
                        var btn = "";
                        return  "<button onclick='queryList(" + row.id +",1)' class='layui-btn layui-btn-small'><i class='fa fa-list fa-edit'></i>&nbsp;借出列表</button>" +
                                "<button onclick='queryList(" + row.id +",0)' class='layui-btn layui-btn-small'><i class='fa fa-list fa-edit'></i>&nbsp;借入列表</button>"
                                + btn
                                ;
                    },
                    "targets": 7
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


    layui.use(['form', 'layedit', 'laydate'], function () {
        form = layui.form(),
                layer = layui.layer,
                laydate = layui.laydate;

//        var start = {
//            max: '2099-06-16 23:59:59'
//            ,format: 'YYYY-MM-DD hh:mm:ss'
//            ,istoday: false
//            ,choose: function(datas){
//                end.min = datas; //开始日选好后，重置结束日的最小日期
//                end.start = datas //将结束日的初始值设定为开始日
//            }
//        };
//
//        var end = {
//            max: '2099-06-16 23:59:59'
//            ,format: 'YYYY-MM-DD hh:mm:ss'
//            ,istoday: false
//            ,choose: function(datas){
//                start.max = datas; //结束日选好后，重置开始日的最大日期
//            }
//        };
//
//        document.getElementById('startTimes').onclick = function(){
//            start.elem = this;
//            laydate(start);
//        }
//        document.getElementById('endTimes').onclick = function(){
//            end.elem = this
//            laydate(end);
//        }


    });

    var webApp=angular.module('webApp',[]);
    webApp.controller("editOrderCtr", function($scope,$http,$timeout){
        $scope.order = null; //统计
        $scope.imgUrl = AM.ip;
        AM.ajaxRequestData("post", false, AM.ip + "/order/statistics", {} , function(result) {
            if (result.flag == 0 && result.code == 200) {
                $scope.order = result.data;
            }
        });

    });

    //查看借出/借入列表
    function queryList(id,type) {
        //sessionStorage.setItem("curriculum", JSON.stringify(obj));
        var index = layer.open({
            type: 2,
            title: type==1?'借出列表':'借入列表',
            shadeClose: true,
            maxmin: true, //开启最大化最小化按钮
            area: ['400px', '500px'],
            content: AM.ip + "/page/order/list?userId=" + id + "&type=" + type
        });
        layer.full(index);
    }

</script>
</body>
</head></html>
