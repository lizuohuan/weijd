var config = function () {};
var toastTimeOut1 = null,toastTimeOut2 = null,toastTimeOut3 = null,toastTimeOut4 = null; //用于toast
var alertTimeOut1 = null,alertTimeOut2 = null; //用于alert
var confirmTimeOut1 = null,confirmTimeOut2 = null,confirmTimeOut3 = null; //用于confirm
var promptTimeOut1 = null,promptTimeOut2 = null,promptTimeOut3 = null; //用于prompt
var shadeTimeOut = null, shadeTimeOut1 = null; //用于遮罩
config.prototype = {
	/**是否开启测试模式**/
	isDebug : false,
	ip: 'http://' + window.location.host + '/weijd/',
	//ip: 'http://192.168.31.205/weijd/',
	ipUrl: location.href.split('#')[0],
	/**手机号码正则表达式**/
	isMobile : /^(((13[0-9]{1})|(18[0-9]{1})|(17[6-9]{1})|(15[0-9]{1}))+\d{8})$/,
	/**电话号码正则表达式**/
	isPhone : /[0-9-()（）]{7,18}/,
	/**身份证正则表达式**/
	isIdentityCard : /^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/,
	/**6-16的密码**/
	isPwd : /[A-Za-z0-9]{6,16}/,
	/**输入的是否为数字**/
	isNumber : /^[0-9]*$/,
	/**检查小数**/
	isDouble : /^\d+(\.\d+)?$/,
	/**输入的只能为数字和字母**/
	isNumberChar: /[A-Za-z0-9]{3,16}/,
	/**用户名**/
	isUserName : /[\w\u4e00-\u9fa5]/,
	/**emoji 表情正则**/
	isEmoji : /\uD83C[\uDF00-\uDFFF]|\uD83D[\uDC00-\uDE4F]/g,
	/**验证邮箱**/
	isEmail : /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/,
	/**只能输入汉字**/
	isChinese : /[\u4e00-\u9fa5]/gm,
	/**控制台打印输出**/
	log : function (obj) {
		if (config.isDebug) console.log(obj);
	},
	/**获取url中的参数**/
	getUrlParam : function(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
		var r = window.location.search.substr(1).match(reg); //匹配目标参数
		if(r != null){
			return unescape(r[2]);
		}else{
			return null; //返回参数值
		}
	},
	/**时间戳转日期**/
	timeStampConversion : function (timestamp){
		var d = new Date(timestamp);
		var date = (d.getFullYear()) + "-" +
			(d.getMonth() + 1) + "-" +
			(d.getDate())+ " " +
			(d.getHours()) + ":" +
			(d.getMinutes()) + ":" +
			(d.getSeconds());
		return date;
	},
	/**日期转换为时间戳**/
	getTimeStamp : function (time){
		time=time.replace(/-/g, '/');
		var date=new Date(time);
		return date.getTime();
	},
	/**计算两个时间相差天数**/
	dateDiff : function  DateDiff(sDate1,  sDate2){
		var  aDate,  oDate1,  oDate2,  iDays
		aDate  =  sDate1.split("-")
		oDate1  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0])    //转换为12-18-2006格式
		aDate  =  sDate2.split("-")
		oDate2  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0])
		iDays  =  parseInt(Math.abs(oDate1  -  oDate2)  /  1000  /  60  /  60  /24)    //把相差的毫秒数转换为天数
		config.log("相差" + iDays + "天");
		return  iDays;
	},
	/**秒计算出分钟数**/
	getFormatTime: function(time) {
		var time = time;
		var h = parseInt(time / 3600),
			m = parseInt(time % 3600 / 60),
			s = parseInt(time % 60);
		h = h < 10 ? "0" + h : h;
		m = m < 10 ? "0" + m : m;
		s = s < 10 ? "0" + s : s;

		h = h == NaN ? "00" : h;
		m = m == NaN ? "00" : m;
		s = s == NaN ? "00" : s;
		return h + ":" + m + ":" + s;
	},
	/**秒计算出分钟数--汉字**/
	getFormat: function (value) {
		var theTime = parseInt(value);// 秒
		var theTime1 = 0;// 分
		var theTime2 = 0;// 小时
		if(theTime > 60) {
			theTime1 = parseInt(theTime/60);
			theTime = parseInt(theTime%60);
			if(theTime1 > 60) {
				theTime2 = parseInt(theTime1/60);
				theTime1 = parseInt(theTime1%60);
			}
		}
		var result = ""+parseInt(theTime)+"秒";
		if(theTime1 > 0) {
			result = ""+parseInt(theTime1)+"分"+result;
		}
		if(theTime2 > 0) {
			result = ""+parseInt(theTime2)+"小时"+result;
		}
		return result;
	},
	/**获取时间格式--一般用于评论**/
	getCountTime : function (createTime, dateTimeStamp) {
		var minute = 1000 * 60;
		var hour = minute * 60;
		var day = hour * 24;
		var halfamonth = day * 15;
		var month = day * 30;
		var result = "";
		var now = dateTimeStamp;
		var diffValue = now - createTime;
		var monthC =diffValue/month;
		var weekC =diffValue/(7*day);
		var dayC =diffValue/day;
		var hourC =diffValue/hour;
		var minC =diffValue/minute;
		if(monthC>=1){
			result = parseInt(monthC) + "个月前";
		}
		else if(weekC>=1){
			result = parseInt(weekC) + "周前";
		}
		else if(dayC>=1){
			result = parseInt(dayC) +"天前";
		}
		else if(hourC>=1){
			result = parseInt(hourC) +"小时前";
		}
		else if(minC>=1){
			result = parseInt(minC) +"分钟前";
		}else
			result="1分钟前";
		return result;

	},
	/**自定义四舍五入**/
	getRound : function (floatvar) {
		var f_x = parseFloat(floatvar);
		if (isNaN(f_x)){
			return '0.00';
		}
		var f_x = Math.round(f_x*100)/100;
		var s_x = f_x.toString();
		var pos_decimal = s_x.indexOf('.');
		if (pos_decimal < 0){
			pos_decimal = s_x.length;
			s_x += '.';
		}
		while (s_x.length <= pos_decimal + 2){
			s_x += '0';
		}
		return s_x;
	},
	intToChinese : function  (str) {
		str = str+'';
		var len = str.length-1;
		var idxs = ['','十','百','千','万','十','百','千','亿','十','百','千','万','十','百','千','亿'];
		var num = ['零','壹','贰','叁','肆','伍','陆','柒','捌','玖'];
		return str.replace(/([1-9]|0+)/g,function( $, $1, idx, full) {
			var pos = 0;
			if( $1[0] != '0' ){
				pos = len-idx;
				if( idx == 0 && $1[0] == 1 && idxs[len-idx] == '十'){
					return idxs[len-idx];
				}
				return num[$1[0]] + idxs[len-idx];
			} else {
				var left = len - idx;
				var right = len - idx + $1.length;
				if( Math.floor(right/4) - Math.floor(left/4) > 0 ){
					pos = left - left%4;
				}
				if( pos ){
					return idxs[pos] + num[$1[0]];
				} else if( idx + $1.length >= len ){
					return '';
				}else {
					return num[$1[0]]
				}
			}
		});
	},
	/**返回上一页并刷新，在需要刷新的页面调用 -- ios端 不支持**/
	reload : function () {
		window.onpageshow = function(e){
			var a = e || window.event;
			if(a.persisted){
				window.location.reload();
			}
		}
	},
	/** 图片压缩
	 * 调用：
	 * 	var imageUrl = config.getObjectURL($(this)[0].files[0]);
	 	config.convertImgToBase64(imageUrl, function(base64Img) {
            checkFace(base64Img.split(",")[1], base64Img); //回调方法
        });
	 * **/
	convertImgToBase64 : function (url, callback, outputFormat) {
		var canvas = document.createElement('CANVAS');
		var ctx = canvas.getContext('2d');
		var img = new Image;
		img.crossOrigin = 'Anonymous';
		img.onload = function() {
			var width = img.width;
			var height = img.height;
			// 按比例压缩4倍
			var rate = (width < height ? width / height : height / width) / 4;
			canvas.width = width * rate;
			canvas.height = height * rate;
			ctx.drawImage(img, 0, 0, width, height, 0, 0, width * rate, height * rate);
			var dataURL = canvas.toDataURL(outputFormat || 'image/png');
			callback.call(this, dataURL);
			canvas = null;
		};
		img.src = url;
	},
	/** 获取图片路径 **/
	getObjectURL : function (file) {
		var url = null;
		if(window.createObjectURL != undefined) { // basic
			url = window.createObjectURL(file);
		} else if(window.URL != undefined) { // mozilla(firefox)
			url = window.URL.createObjectURL(file);
		} else if(window.webkitURL != undefined) { // web_kit or chrome
			url = window.webkitURL.createObjectURL(file);
		}
		return url;
	},
	/**选择文件处理文件流配合处理ios拍照旋转问题**/
	selectFileImage : function (file) {
		//判断ios旋转代码
		var Orientation = null;
		//获取照片方向角属性，用户旋转控制
		EXIF.getData(file, function() {
			EXIF.getAllTags(this);
			Orientation = EXIF.getTag(this, 'Orientation');
		});
		var oReader = new FileReader();
		oReader.onload = function(e) {
			var image = new Image();
			image.src = e.target.result;
			image.onload = function() {
				var expectWidth = this.naturalWidth;
				var expectHeight = this.naturalHeight;
				if (this.naturalWidth > this.naturalHeight && this.naturalWidth > 800) {
					expectWidth = 800;
					expectHeight = expectWidth * this.naturalHeight / this.naturalWidth;
				} else if (this.naturalHeight > this.naturalWidth && this.naturalHeight > 1200) {
					expectHeight = 1200;
					expectWidth = expectHeight * this.naturalWidth / this.naturalHeight;
				}
				var canvas = document.createElement("canvas");
				var ctx = canvas.getContext("2d");
				canvas.width = expectWidth;
				canvas.height = expectHeight;
				ctx.drawImage(this, 0, 0, expectWidth, expectHeight);
				var base64 = null;
				//修复ios
				if (navigator.userAgent.match(/iphone/i)) {
					config.log('iphone');
					//alert(expectWidth + ',' + expectHeight);
					//如果方向角不为1，都需要进行旋转 added by lzk
					if(Orientation != "" && Orientation != 1){
						//alert('旋转处理');
						switch(Orientation){
							case 6://需要顺时针（向左）90度旋转
								//alert('需要顺时针（向左）90度旋转');
								config.rotateImg(this,'left',canvas);
								break;
							case 8://需要逆时针（向右）90度旋转
								//alert('需要顺时针（向右）90度旋转');
								config.rotateImg(this,'right',canvas);
								break;
							case 3://需要180度旋转
								//alert('需要180度旋转');
								config.rotateImg(this,'right',canvas);//转两次
								config.rotateImg(this,'right',canvas);
								break;
						}
					}
					base64 = canvas.toDataURL("image/jpeg", 0.8);
				}else if (navigator.userAgent.match(/Android/i)) {// 修复android
					var encoder = new JPEGEncoder();
					base64 = encoder.encode(ctx.getImageData(0, 0, expectWidth, expectHeight), 80);
				}else{
					//alert(Orientation);
					if(Orientation != "" && Orientation != 1){
						//alert('旋转处理');
						switch(Orientation){
							case 6://需要顺时针（向左）90度旋转
								//alert('需要顺时针（向左）90度旋转');
								config.rotateImg(this,'left',canvas);
								break;
							case 8://需要逆时针（向右）90度旋转
								//alert('需要顺时针（向右）90度旋转');
								config.rotateImg(this,'right',canvas);
								break;
							case 3://需要180度旋转
								//alert('需要180度旋转');
								config.rotateImg(this,'right',canvas);//转两次
								config.rotateImg(this,'right',canvas);
								break;
						}
					}
					base64 = canvas.toDataURL("image/jpeg", 0.8);
				}
				$("#tempBase64").val(base64);
			};
		};
		oReader.readAsDataURL(file);
	},
	/**对图片旋转处理**/
	rotateImg : function (img, direction,canvas) {
		//最小与最大旋转方向，图片旋转4次后回到原方向
		var min_step = 0;
		var max_step = 3;
		//var img = document.getElementById(pid);
		if (img == null)return;
		//img的高度和宽度不能在img元素隐藏后获取，否则会出错
		var height = img.height;
		var width = img.width;
		//var step = img.getAttribute('step');
		var step = 2;
		if (step == null) {
			step = min_step;
		}
		if (direction == 'right') {
			step++;
			//旋转到原位置，即超过最大值
			step > max_step && (step = min_step);
		} else {
			step--;
			step < min_step && (step = max_step);
		}
		//旋转角度以弧度值为参数
		var degree = step * 90 * Math.PI / 180;
		var ctx = canvas.getContext('2d');
		switch (step) {
			case 0:
				canvas.width = width;
				canvas.height = height;
				ctx.drawImage(img, 0, 0);
				break;
			case 1:
				canvas.width = height;
				canvas.height = width;
				ctx.rotate(degree);
				ctx.drawImage(img, 0, -height);
				break;
			case 2:
				canvas.width = width;
				canvas.height = height;
				ctx.rotate(degree);
				ctx.drawImage(img, -width, -height);
				break;
			case 3:
				canvas.width = height;
				canvas.height = width;
				ctx.rotate(degree);
				ctx.drawImage(img, -width, 0);
				break;
		}
	},
	/**显示loading**/
	showLoading : function (context) {
		var loading = '<div class="ball-loading"><div></div><div></div></div>';
		var html = "<div class='loading-bar'>" +
			"			<div class='loading-content'>" +
			"				<div class='loading-object'>" + loading + "</div>" +
			"				<div class='loading-context'>" + context + "</div>" +
			"			</div>" +
			"		</div>";
		return html;
	},
	/**隐藏loading**/
	hideLoading : function () {
		$("body .app-content").addClass("app-content-show");
		$(".loading-bar").addClass("loading-bar-hide");
		setTimeout(function () {
			$(".loading-bar").remove();
		}, 500);
	},
	/**显示遮罩**/
	showShade : function () {
		$("body").append("<div class='app-shade'></div>");
		shadeTimeOut = setTimeout(function () {
			$(".app-shade").addClass("app-shade-show");
			clearTimeout(shadeTimeOut);
		}, 100);
	},
	/**关闭遮罩**/
	hideShade : function () {
		shadeTimeOut1 = setTimeout(function () {
			$(".app-shade").remove();
			clearTimeout(shadeTimeOut1);
		}, 200);
	},
	/**显示 toast**/
	toast : function (message) {
		clearTimeout(toastTimeOut1);
		clearTimeout(toastTimeOut2);
		clearTimeout(toastTimeOut3);
		clearTimeout(toastTimeOut4);
		$("body").find(".app-toast").remove(); //先删除之前的toast
		$("body").append("<div class='app-toast'>" + message + "</div>");
		toastTimeOut1 = setTimeout(function () {
			$(".app-toast").addClass("app-toast-show");
			toastTimeOut2 = setTimeout(function () {
				$(".app-toast").addClass("app-toast-show-min");
			}, 200);
			toastTimeOut3 = setTimeout(function () {
				$(".app-toast").removeClass("app-toast-show app-toast-show-min");
				toastTimeOut4 = setTimeout(function () {
					$(".app-toast").remove();
				}, 1000);
			}, 3000);
		}, 100);
	},
	/**显示 alert**/
	alert : function (title, content, button, callback) {
		var html = '<div class="app-modal">' +
					'	<div class="app-modal-title">' + title + '</div>' +
					'	<div class="app-modal-context">' + content + '</div>' +
					'	<div class="app-modal-buttons">' +
					'		<span class="app-modal-confirm app-alert-modal-confirm">' + button + '</span>' +
					'	</div>' +
					'</div>' +
					'<div class="app-shade"></div>';
		$("body").append(html);
		alertTimeOut1 = setTimeout(function () {
			$(".app-shade").addClass("app-shade-show");
			$(".app-modal").addClass("app-modal-show");
		}, 100);
        $("body").find('.app-modal-confirm').click(function () {
        	$(".app-modal").removeClass("app-modal-show");
			$(".app-shade").removeClass("app-shade-show");
        	alertTimeOut2 = setTimeout(function () {
        		$(".app-modal").remove();
        		$(".app-shade").remove();
        		clearTimeout(alertTimeOut1);
        		clearTimeout(alertTimeOut2);
        	}, 200);
        	callback();
        });
	},
	/**显示 confirm**/
	confirm : function (title, content, buttons, callback) {
		var html = '<div class="app-modal">' +
					'	<div class="app-modal-title">' + title + '</div>' +
					'	<div class="app-modal-context">' + content + '</div>' +
					'	<div class="app-modal-buttons">' +
					'		<span class="app-modal-cancel">' + buttons[0] + '</span>' +
					'		<span class="app-modal-confirm">' + buttons[1] + '</span>' +
					'	</div>' +
					'</div>' +
					'<div class="app-shade"></div>';
		$("body").append(html);
		confirmTimeOut1 = setTimeout(function () {
			$(".app-shade").addClass("app-shade-show");
			$(".app-modal").addClass("app-modal-show");
		}, 100);
        $("body").find('.app-modal-cancel').click(function () {
        	$(".app-modal").removeClass("app-modal-show");
			$(".app-shade").removeClass("app-shade-show");
        	confirmTimeOut2 = setTimeout(function () {
        		$(".app-modal").remove();
        		$(".app-shade").remove();
        		clearTimeout(confirmTimeOut1);
        		clearTimeout(confirmTimeOut2);
        		clearTimeout(confirmTimeOut3);
        	}, 200);
        	callback(0); //取消按钮回调
        });
        $("body").find('.app-modal-confirm').click(function () {
        	$(".app-modal").removeClass("app-modal-show");
			$(".app-shade").removeClass("app-shade-show");
        	confirmTimeOut3 = setTimeout(function () {
        		$(".app-modal").remove();
        		$(".app-shade").remove();
        		clearTimeout(confirmTimeOut1);
        		clearTimeout(confirmTimeOut2);
        		clearTimeout(confirmTimeOut3);
        	}, 200);
        	callback(1); //确定按钮回调
        });
	},
	prompt : function (title, content, buttons, type, callback) {
		var html = '<div class="app-modal">' +
			'	<div class="app-modal-title">' + title + '</div>' +
			'	<div class="app-modal-context"><input class="app-modal-input" type="' + type + '" placeholder="' + content + '" maxlength=""/></div>' +
			'	<div class="app-modal-buttons">' +
			'		<span class="app-modal-cancel">' + buttons[0] + '</span>' +
			'		<span class="app-modal-confirm">' + buttons[1] + '</span>' +
			'	</div>' +
			'</div>' +
			'<div class="app-shade"></div>';
		$("body").append(html);
		promptTimeOut1 = setTimeout(function () {
			$(".app-shade").addClass("app-shade-show");
			$(".app-modal").addClass("app-modal-show");
		}, 100);
		$("body").find('.app-modal-cancel').click(function () {
			$(".app-modal").removeClass("app-modal-show");
			$(".app-shade").removeClass("app-shade-show");
			promptTimeOut2 = setTimeout(function () {
				$(".app-modal").remove();
				$(".app-shade").remove();
				clearTimeout(promptTimeOut1);
				clearTimeout(promptTimeOut2);
				clearTimeout(promptTimeOut3);
			}, 200);
			callback(0); //取消按钮回调
		});
		$("body").find('.app-modal-confirm').click(function () {
			$(".app-modal").removeClass("app-modal-show");
			$(".app-shade").removeClass("app-shade-show");
			promptTimeOut3 = setTimeout(function () {
				$(".app-modal").remove();
				$(".app-shade").remove();
				clearTimeout(promptTimeOut1);
				clearTimeout(promptTimeOut2);
				clearTimeout(promptTimeOut3);
			}, 200);
			callback(1); //确定按钮回调
		});
	},
	/**判断是否是json对象**/
	isJson : function (obj){
		var isjson = typeof(obj) == "object" && Object.prototype.toString.call(obj).toLowerCase() == "[object object]" && !obj.length;
		return isjson;
	},
	/**获取登录人信息**/
	getUserInfo : function () {
		var userInfo = JSON.parse(localStorage.getItem("userInfo"));
		if (userInfo == null) {
			$.ajax({
				type: "post",
				async: false,
				data: {},
				url: config.ip + "wechat/user/info",
				success: function (result) {
					if(result.flag == 0 && result.code == 200){
						userInfo = result.data;
						localStorage.setItem("userInfo", JSON.stringify(result.data));
					}
				}
			});
			/*config.ajaxRequestData(false, "wechat/user/info", null, true, function(result){

			});*/
		}
		return userInfo;
	},
	/**输入框输入只保留三位小数**/
	clearNoNum : function (obj) {
		//先把非数字的都替换掉，除了数字和.
		obj.value = obj.value.replace(/[^\d.]/g,"");
		//保证只有出现一个.而没有多个.
		obj.value = obj.value.replace(/\.{3,}/g,".");
		//必须保证第一个为数字而不是.
		obj.value = obj.value.replace(/^\./g,"");
		//保证.只出现一次，而不能出现两次以上
		obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
		//只能输入两个小数
		obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');
	},
	/**生成图片验证码**/
	createCode : function () {
		var code = ""; //存放生成好的验证码
		var codeLength = 4; //验证码的长度
		var codeChars = new Array(0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
			'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'); //所有候选组成验证码的字符，当然也可以用中文的
		for(var i = 0; i < codeLength; i++) {
			var charNum = Math.floor(Math.random() * 52);
			code += codeChars[charNum];
		}
		return code;
	},
	/**微信支付appId**/
	wxAppId : "wxc451c8ccd0926f75",
	/**微信端--微信支付调起**/
	wechatPay : function (appId, timeStamp, nonceStr, prepayId, signType, paySign, callback) {
		if (typeof WeixinJSBridge == "undefined"){
			if( document.addEventListener ){
				document.addEventListener('WeixinJSBridgeReady', config.onBridgeReady, false);
			}else if (document.attachEvent){
				document.attachEvent('WeixinJSBridgeReady', config.onBridgeReady);
				document.attachEvent('onWeixinJSBridgeReady', config.onBridgeReady);
			}
		}else{
			//config.onBridgeReady(appId, timeStamp, nonceStr, prepayId, signType, paySign, jumpUrl, orderId);
			WeixinJSBridge.invoke(
				'getBrandWCPayRequest', {
					"appId" : appId,     //公众号名称，由商户传入
					"timeStamp" : timeStamp,         //时间戳，自1970年以来的秒数
					"nonceStr" : nonceStr, //随机串
					"package" : prepayId,
					"signType" : signType,         //微信签名方式：
					"paySign" : paySign //微信签名
				},
				function(res){
					callback(res);
				}
			);
		}
	},
	/**微信端--微信支付签名**/
	onBridgeReady : function (appId, timeStamp, nonceStr, prepayId, signType, paySign, jumpUrl, orderId) {
		WeixinJSBridge.invoke(
			'getBrandWCPayRequest', {
				"appId" : appId,     //公众号名称，由商户传入
				"timeStamp" : timeStamp,         //时间戳，自1970年以来的秒数
				"nonceStr" : nonceStr, //随机串
				"package" : prepayId,
				"signType" : signType,         //微信签名方式：
				"paySign" : paySign //微信签名
			},
			function(res){
				if(res.err_msg == "get_brand_wcpay_request:ok" ) {// 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。
					config.alert("", "支付成功.", "确认", null);
					location.href = config.ip + jumpUrl + "?isSucceed=1&orderId=" + orderId;
				}
				else {
					config.alert("", "支付成功.", "确认", null);
					location.href = config.ip + jumpUrl + "?isSucceed=0&orderId=" + orderId;
				}
			}
		);
	},
	/**微信API签名**/
	getWxJsSign : function () {
		var jsSignList = null;
		config.ajaxRequestData(false, "wechat/wxClient/jsSign", {url : this.ipUrl}, true, function(result){
			jsSignList = result.data;
		});
		return jsSignList;
	},
	/**
	 * ajax请求数据 全部使用post
	 * async : 同步/异步
	 * requestUrl : 请求接口
	 * parameter : 请求参数
	 * isItRight : 是否验证
	 * callback : 回调函数
	 **/
	ajaxRequestData : function(async, requestUrl, parameter, isItRight, callback){
		config.log("************请求参数**************");
		config.log("请求接口：" + requestUrl);
		config.log(parameter);
		config.log("************请求参数**************");
		$.ajax({
			type: "POST",
			async: async,
			url: config.ip + requestUrl,
			data: parameter,
			//dataType:"jsonp",    //跨域json请求一定是jsonp
			headers: {
				"token" : config.getUserInfo() == null ? null : config.getUserInfo().token,
			},
			success:function(json){
				if (!config.isJson(json)) {
					json = JSON.parse(json);
				}
				config.log("************请求结果**************");
				config.log(json);
				config.log("************请求结果**************");
				if (isItRight) {
					if(json.flag == 0 && json.code == 200){
						if (callback) {
							callback(json);
						}
					}
					else if(json.code == 1005){
						localStorage.removeItem("userInfo");
						config.confirm('未登录', '登录已失效，是否登录？', ["取消","确认"], function(index) {
							if (index == 0) {
								location.href = config.ip + "wechat/page/index";
							} else {
								location.href = config.ip + "wechat/page/login";
								sessionStorage.setItem("url", window.location.href);
							}
						});
					}
					else {
						config.toast(json.msg);
					}
				}
				else {
					if (callback) {
						callback(json);
					}
				}
				
			},
			error: function(json) {
				config.toast(json.responseText);
			}
		})
	},
};

var config = new config();

/**转format yyyy-MM-dd HH:mm:ss **/
Date.prototype.format = function (fmt) { 
	var o = {
		"M+": this.getMonth() + 1, //月份
		"d+": this.getDate(), //日
		"h+": this.getHours(), //小时
		"m+": this.getMinutes(), //分
		"s+": this.getSeconds(), //秒
		"q+": Math.floor((this.getMonth() + 3) / 3), //季度
		"S": this.getMilliseconds() //毫秒
	};
	if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	for (var k in o)
		if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}

/**删除数组指定元素**/
Array.prototype.removeValue = function(val) {
	for(var i=0; i<this.length; i++) {
		if(this[i] == val) {
			this.splice(i, 1);
			break;
		}
	}
}

/*********************************初始化********************************/
console.log('%c欢迎使用易信缘', 'background-image:-webkit-gradient( linear, left top, right top, color-stop(0, #f22), color-stop(0.15, #f2f), color-stop(0.3, #22f), color-stop(0.45, #2ff), color-stop(0.6, #2f2),color-stop(0.75, #2f2), color-stop(0.9, #ff2), color-stop(1, #f22) );color:transparent;-webkit-background-clip: text;font-size:5em;');
if (!config.isDebug) {
	// 对浏览器的UserAgent进行正则匹配，不含有微信独有标识的则为其他浏览器
	var useragent = navigator.userAgent;
	if (useragent.match(/MicroMessenger/i) != 'MicroMessenger') {
		// 这里警告框会阻塞当前页面继续加载
		alert('已禁止本次访问：您必须使用微信内置浏览器访问本页面！');
		$("body").html("<div class='warning'>已禁止本次访问：<br>您必须使用微信内置浏览器访问本页面！<div/>");
	}
	else { }
}
/**加载loading**/
$(document).ready(function(){
	$("body").append(config.showLoading("请稍后..."));
});
/**加载完成关闭**/
window.onload = function () {
	config.hideLoading();
	/*setTimeout(function () {
		config.hideLoading();
	}, 1000);*/
}