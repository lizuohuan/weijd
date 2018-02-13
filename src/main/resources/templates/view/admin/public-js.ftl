
<script type="text/javascript" src="${request.contextPath}/admin/js/common/jquery-2.1.0.js"></script>
<script type="text/javascript" src="${request.contextPath}/admin/plugins/layui/lay/dest/layui.all.js"></script>
<script type="text/javascript" src="${request.contextPath}/admin/js/angular.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/admin/datatables/media/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="${request.contextPath}/admin/datatables/plugins/bootstrap/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="${request.contextPath}/admin/js/config.js"></script>
<!--时间控件-->
<script type="text/javascript" src="${request.contextPath}/admin/My97DatePicker/WdatePicker.js"></script>

<script>
    $(function () {
        $(".fa-refresh").click(function () {
            location.reload();
        });
    });


    //省
    function selectProvince(selectId,source) {
        AM.ajaxRequestData("get", false, AM.ip + "/city/queryCityByParentId", {levelType : 1} , function(result){
            var html = "<option value=''>请选择或搜索省</option>";
            if (selectId == 0) {
                if (source == null || source == "") {
                    html += "<option value=\"100000\" selected=\"selected\">全国</option>";
                }
            }
            else {
                html += "<option value=\"100000\">全国</option>";
            }
            for (var i = 0; i < result.data.length; i++) {
                if (result.data[i].id == selectId) {
                    html += "<option selected=\"selected\" value=\"" + result.data[i].id + "\">" + result.data[i].shortName + "</option>";
                }
                else {
                    html += "<option value=\"" + result.data[i].id + "\">" + result.data[i].shortName + "</option>";
                }
            }
            if (result.data.length == 0) {
                html += "<option value=\"0\" disabled>暂无</option>";
            }
            $("select[name='province']").html(html);
            $("select[name='province']").parent().parent().show();
        });
    }

    //市
    function selectCity(cityId, selectId) {
        AM.ajaxRequestData("get", false, AM.ip + "/city/queryCityByParentId", {cityId : cityId, levelType : 2} , function(result){
            var html = "<option value=\"\">请选择或搜索市</option>";
            for (var i = 0; i < result.data.length; i++) {
                if (result.data[i].id == selectId) {
                    html += "<option selected=\"selected\" value=\"" + result.data[i].id + "\">" + result.data[i].shortName + "</option>";
                }
                else {
                    html += "<option value=\"" + result.data[i].id + "\">" + result.data[i].shortName + "</option>";
                }
            }
            if (result.data.length == 0) {
                html += "<option value=\"0\" disabled>暂无</option>";
            }
            $("select[name='city']").html(html);
            $("select[name='city']").parent().parent().show();
        });
    }

    //区
    function selectCounty(cityId, selectId) {
        AM.ajaxRequestData("get", false, AM.ip + "/city/queryCityByParentId", {cityId : cityId, levelType : 3} , function(result){
            var html = "<option value=\"\">请选择或搜索县/区</option>";
            for (var i = 0; i < result.data.length; i++) {
                if (result.data[i].id == selectId) {
                    html += "<option selected=\"selected\" value=\"" + result.data[i].id + "\">" + result.data[i].shortName + "</option>";
                }
                else {
                    html += "<option value=\"" + result.data[i].id + "\">" + result.data[i].shortName + "</option>";
                }
            }
            if (result.data.length == 0) {
                html += "<option value=\"0\" disabled>暂无</option>";
            }
            $("select[name='district']").html(html);
            $("select[name='district']").parent().parent().show();
        });
    }



    $(function () {
        document.onkeydown = function(event) {
            var code;
            if (!event) {
                event = window.event; //针对ie浏览器
                code = event.keyCode;
                if (code == 13) {
                    if (document.getElementsByClassName("layui-layer-btn0").length > 0) {
                        document.getElementsByClassName("layui-layer-btn0")[0].click();
                    }
                    if (document.getElementById("unlock")) {
                        document.getElementById("unlock").click();
                    }
                }
            }
            else {
                code = event.keyCode;
                if (code == 13) {
                    if (document.getElementsByClassName("layui-layer-btn0").length > 0) {
                        document.getElementsByClassName("layui-layer-btn0")[0].click();
                    }
                    if (document.getElementById("unlock")) {
                        document.getElementById("unlock").click();
                    }
                }
            }
        };

        $(".layui-btn").blur();

    });

</script>