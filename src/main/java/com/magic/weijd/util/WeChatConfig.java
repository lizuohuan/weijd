package com.magic.weijd.util;

/**
 * 微信公众号端
 * Created by Eric Xie on 2017/3/21 0021.
 */
public class WeChatConfig {

    public static final String SESSION_WX_USER = "weijd_wx_user";

    public static final String ACCESS_TOKEN= "weijd_access_token";

    public static final String TICKET= "weijd_ticket";

    public static String appId = "wx7ab31ae0dfa5178e"; //正式

    public static String secret = "d057f55db377d8e7a8f1c8620ba18a82";

    public static String mchId = "1494236412";


    public static final String SIGN_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    public static final String payKey = "3e6677cfca6644fab81494b360243cfd";


//    public static String appId = "wx8d7693a361f74b8d"; //李
//
//    public static String secret = "69caf329e1eed8ef1b3e7066eabc0f89";
//
//    public static String mchId = "1494236412";

//    public static String appId = "wx6c852451915d2149"; //2李
//
//    public static String secret = "76ee023565c0d6ac7a6a5042e66b7332";

//    public static String appId = "wxbedd99a7446ac215"; //袁
//
//    public static String secret = "703bec139b0790535f2cd0c19d1d4d17";

    /** 发送模板消息的POST请求 */
    public static String template_http = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";

    public static String order_info_http = "/wechat/page/borrowingDetail/";

    /** 我的主页 */
    public static String my_http = "/weijd/wechat/page/index";

    /** 意见反馈 */
    public static String feedback_http = "/weijd/wechat/page/feedback";

    /** 联系我们 */
    public static String contact_us_http = "/weijd/wechat/page/contactUs";

    /** 订单状态更新 */
//    public static String template_order_status = "tJlA5Zvo5N-IencN_8qe0ET9qnsC5NyO1bejzKk6GDM";
    public static String template_order_status = "RkXXGbc6bZCpGyHQXkdW7jusSDIGqUXdvZJAPiuQUV8";

    /** 延期申请通知 */
//    public static String template_order_apply = "pSg4Qc5WomJSi3wyhX4Hv5d544mVIuAQuHIHrh7tGTo";
    public static String template_order_delay_apply = "ukOqQfDvVLMEWJVsDASOS59_jZGTruvV5Wns75UptCw";

    /** 还款申请通知 */
    public static String template_order_repay_apply = "ul0bEpdcHldoL1_EibOMoy3tirqnlPxmU71iCpqGRHI";



    /** 申请结果 */
//    public static String template_order_apply_result = "r9JHuggcqUXyLs8dcYt2tsXGVPsMvzPHb3tBxTgrNtA";
    public static String template_order_apply_result = "Ka9Eb4VRoubUjwZZSMWx994sj_V_Vy2Fjky5wMCoi_g";

    /** 申请通过提醒 */
    public static String template_order_apply_pass_result = "xRkKmjb6PGMYyZi6VUwH1Fxk80SMAYOEwkdrXOlihpU";


    /** 创建微信公众号首页菜单的url */
    public static String create_menu_http = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";

    /** 获取微信公众号首页菜单的url */
    public static String get_menu_http = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=";

    /** 删除微信公众号首页菜单的url */
    public static String delete_menu_http = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=";

}
