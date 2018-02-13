var index = layer.load(1, {
    shade: [0.1,'#fff'] //0.1透明度的白色背景
});
setTimeout(function(){layer.close(index);}, 600);


var navs;

var navs = [{
        "title": "平台用户列表",
        "icon": "fa-drivers-license",
        "spread": false,
        "href": AM.ip + "/page/admins/list"
    },{
        "title": "用户列表",
        "icon": "fa-drivers-license",
        "spread": false,
        "href": AM.ip + "/page/user/list"
    },{
        "title": "统计列表",
        "icon": "fa-drivers-license",
        "spread": false,
        "href": AM.ip + "/page/order/statisticsList"
    },{
        "title": "借条列表",
        "icon": "fa-drivers-license",
        "spread": false,
        "href": AM.ip + "/page/record/list"
    },{
        "title": "意见反馈",
        "icon": "fa-drivers-license",
        "spread": false,
        "href": AM.ip + "/page/suggest/list"
    },{
        "title": "联系我们配置",
        "icon": "fa-drivers-license",
        "spread": false,
        "href": AM.ip + "/page/contactUs/edit"
    },{
        "title": "系统配置",
        "icon": "fa-drivers-license",
        "spread": false,
        "href": AM.ip + "/page/systemConfig/edit"
    }];
console.log(navs);


var tab;

layui.config({
    base: AM.ip + "/js/common/",
    version: new Date().getTime()
}).use(['element', 'layer', 'navbar', 'tab'], function () {
    var element = layui.element(),
        $ = layui.jquery,
        layer = layui.layer,
        navbar = layui.navbar();
    tab = layui.tab({
        elem: '.admin-nav-card', //设置选项卡容器
        maxSetting: {
        	max: 10,
        	tipMsg: '最多只能打开10个页面.'
        },
        contextMenu: true,
        onSwitch: function (data) {
            /*console.log(data.id); //当前Tab的Id
            console.log(data.index); //得到当前Tab的所在下标
            console.log(data.elem); //得到当前的Tab大容器
            console.log(tab.getCurrentTabId());//当前Tab的Id*/
        }
    });

    //iframe自适应
    if(isFirefox = navigator.userAgent.indexOf("Firefox") > 0){
        $(window).on('resize', function () {
            var $content = $('.admin-nav-card .layui-tab-content');
            $content.height($(document).height() - 147);
            $content.find('iframe').each(function () {
                $(this).height($content.height());
            });
        }).resize();
    }
    else {
        $(window).on('resize', function () {
            var $content = $('.admin-nav-card .layui-tab-content');
            $content.height($(this).height() - 147);
            $content.find('iframe').each(function () {
                $(this).height($content.height());
            });
        }).resize();
    }

    //设置navbar
    navbar.set({
        spreadOne: true,
        elem: '#admin-navbar-side',
        cached: true,
        data: navs
        /*cached:true,
         url: 'datas/nav.json'*/
    });
    //渲染navbar
    navbar.render();
    //监听点击事件
    navbar.on('click(side)', function (data) {
        console.log(data);
        tab.tabAdd(data.field);
        console.log(data.field.href);
        //location.href = data.field.href;
        var index = layer.load(1, {shade: [0.5,'#eee']});
        setTimeout(function () {layer.close(index);}, 600);
    });

    //单击收起菜单
    $('.admin-side-toggle').on('click', function () {
        var sideWidth = $('#admin-side').width();
        if (sideWidth === 200) {
            $('#admin-body').animate({
                left: '0'
            }); //admin-footer
            $('#admin-footer').animate({
                left: '0'
            });
            $('#admin-side').animate({
                width: '0'
            });
        } else {
            $('#admin-body').animate({
                left: '200px'
            });
            $('#admin-footer').animate({
                left: '200px'
            });
            $('#admin-side').animate({
                width: '200px'
            });
        }
    });

    //单击全屏
    $('.admin-side-full').on('click', function () {
        var docElm = document.documentElement;
        //W3C
        if (docElm.requestFullscreen) {
            docElm.requestFullscreen();
        }
        //FireFox
        else if (docElm.mozRequestFullScreen) {
            docElm.mozRequestFullScreen();
        }
        //Chrome等
        else if (docElm.webkitRequestFullScreen) {
            docElm.webkitRequestFullScreen();
        }
        //IE11
        else if (elem.msRequestFullscreen) {
            elem.msRequestFullscreen();
        }
        layer.msg('按Esc即可退出全屏');
    });



    //锁屏
    $(document).on('keydown', function () {
        var e = window.event;
        if (e.keyCode === 76 && e.altKey) {
            //alert("你按下了alt+l");
            lock($, layer);
        }
    });
    $('#lock').on('click', function () {
        lock($, layer);
    });

    //手机设备的简单适配
    var treeMobile = $('.site-tree-mobile'),
        shadeMobile = $('.site-mobile-shade');
    treeMobile.on('click', function () {
        $('body').addClass('site-mobile');
    });
    shadeMobile.on('click', function () {
        $('body').removeClass('site-mobile');
    });
});

var isShowLock = false;
function lock($, layer) {
    localStorage.setItem("isShowLock", true);
    if (isShowLock)
        return;
    var avatar = "";
    if (AM.getUserInfo().avatar == "" || AM.getUserInfo().avatar == null) {
        avatar = AM.ip + "/img/0.jpg";
    }
    else {
        avatar = AM.ip + '/' + AM.getUserInfo().avatar;
    }
    //自定页
    layer.open({
        title: false,
        type: 1,
        closeBtn: 0,
        anim: 6,
        content: '<div id="lock-temp">' +
        '<div class="admin-header-lock" id="lock-box">' +
        '<div class="admin-header-lock-img">' +
        '<img id="avatar2" src="' + avatar + '" />' +
        '</div>' +
        '<div class="admin-header-lock-name" id="lockUserName">' + AM.getUserInfo().account + '</div>' +
        '<input type="password" class="admin-header-lock-input" placeholder="输入登录密码解锁." name="lockPwd" id="lockPwd" autocomplete="off" />&nbsp;&nbsp;' +
        '<button class="layui-btn layui-btn-small" id="unlock" style="margin-top: -1px;">解锁</button>' +
        '</div></div>',
        shade: [0.9, '#393D49'],
        success: function (layero, lockIndex) {
            isShowLock = true;
            //给显示用户名赋值
            //绑定解锁按钮的点击事件
            $('button#unlock').on('click', function () {
                var $lockBox = $('div#lock-box');
                var account = $lockBox.find('div#lockUserName').html();
                var pwd = $lockBox.find('input[name=lockPwd]').val();
                if (pwd.length === 0) {
                    layer.msg('请输入密码..', {
                        icon: 2,
                        time: 1000,
                        anim: 6,
                        offset: 't'
                    });
                    $("#lockPwd").focus();
                    return;
                }
                unlock(account, pwd);
            });
            /**
             * 解锁操作方法
             * @param {String} 用户名
             * @param {String} 密码
             */
            var unlock = function (account, pwd) {
                //这里可以使用ajax方法解锁
                var arr = {
                    account : account,
                    password : $.md5(pwd)
                }
                if (arr.password == AM.getUserInfo().password) {
                    localStorage.removeItem("isShowLock");
                    isShowLock = false;
                    layer.close(lockIndex);
                }
                else {
                    layer.msg("密码错误.", {icon: 2, anim: 6, time: 1000, offset: 't'});
                    return false;
                }
            };
        }
    });
};