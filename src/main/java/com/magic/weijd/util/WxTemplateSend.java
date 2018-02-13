package com.magic.weijd.util;

import com.alibaba.fastjson.JSONObject;
import com.magic.weijd.cache.RedisCache;
import com.magic.weijd.entity.WechatTemplateMsg;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.TreeMap;

/**
 * 发送模板消息
 * @author lzh
 * @create 2017/9/13 18:56
 */
public class WxTemplateSend {

    /**
     * 发送推送消息 订单状态更新
     * @param msg
     * @param number
     * @param statusMsg
     * @param lastMsg
     * @param openId
     * @param orderId
     */
    public static void sendTemplateOrderStatus(String msg,String number ,String statusMsg ,String lastMsg ,String openId ,Integer orderId) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        WechatTemplateMsg wechatTemplateMsg = new WechatTemplateMsg();
        TreeMap<String, TreeMap<String, String>> data = new TreeMap<>();
        data.put("first",WechatTemplateMsg.item(msg,"#173177"));
        data.put("keyword1",WechatTemplateMsg.item(number,"#FFC125"));
        data.put("keyword2",WechatTemplateMsg.item(Timestamp.DateTimeStamp(new Date(),"yyyy年MM月dd日 HH:mm"),"#FFC125"));
        data.put("keyword3",WechatTemplateMsg.item(statusMsg,"#FFC125"));
        data.put("remark",WechatTemplateMsg.item("点击“详情”查看借款单信息，"+lastMsg,""));
        wechatTemplateMsg.setTouser(openId);
        wechatTemplateMsg.setTemplate_id(WeChatConfig.template_order_status);
        wechatTemplateMsg.setData(data);
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + WeChatConfig.order_info_http + orderId;
        wechatTemplateMsg.setUrl(url);
        SendRequestUtil.httpPost(WeChatConfig.template_http + RedisCache.get(WeChatConfig.ACCESS_TOKEN), JSONObject.toJSONString(wechatTemplateMsg));
    }


    /**
     * 发送推送消息 还款申请通知
     * @param msg
     * @param name
     * @param userName
     * @param lastMsg
     * @param openId
     * @param orderId
     */
    public static void sendTemplateOrderDelayApplyFor(String msg,String name ,String userName ,String type  ,String lastMsg ,String openId ,Integer orderId) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        WechatTemplateMsg wechatTemplateMsg = new WechatTemplateMsg();
        TreeMap<String, TreeMap<String, String>> data = new TreeMap<>();
        data.put("first",WechatTemplateMsg.item(msg,"#173177"));
        data.put("keyword1",WechatTemplateMsg.item(name,"#FFC125"));
        data.put("keyword2",WechatTemplateMsg.item(userName,"#FFC125"));
        data.put("keyword3",WechatTemplateMsg.item(type,"#FFC125"));
        data.put("keyword4",WechatTemplateMsg.item(Timestamp.DateTimeStamp(new Date(),"yyyy年MM月dd日 HH:mm"),"#FFC125"));
        data.put("remark",WechatTemplateMsg.item("点击\"详情\"查看借款单信息，"+lastMsg,""));
        wechatTemplateMsg.setTouser(openId);
        wechatTemplateMsg.setTemplate_id(WeChatConfig.template_order_delay_apply);
        wechatTemplateMsg.setData(data);
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + WeChatConfig.order_info_http + orderId;
        wechatTemplateMsg.setUrl(url);
        SendRequestUtil.httpPost(WeChatConfig.template_http + RedisCache.get(WeChatConfig.ACCESS_TOKEN), JSONObject.toJSONString(wechatTemplateMsg));
    }


    /**
     * 发送推送消息 还款申请通知
     * @param msg
     * @param name
     * @param userName
     * @param lastMsg
     * @param openId
     * @param orderId
     */
    public static void sendTemplateOrderRepayApplyFor(String msg,String name ,String userName ,String type  ,String lastMsg ,String openId ,Integer orderId) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        WechatTemplateMsg wechatTemplateMsg = new WechatTemplateMsg();
        TreeMap<String, TreeMap<String, String>> data = new TreeMap<>();
        data.put("first",WechatTemplateMsg.item(msg,"#173177"));
        data.put("keyword1",WechatTemplateMsg.item(name,"#FFC125"));
        data.put("keyword2",WechatTemplateMsg.item(userName,"#FFC125"));
        data.put("keyword3",WechatTemplateMsg.item(type,"#FFC125"));
        data.put("keyword4",WechatTemplateMsg.item(Timestamp.DateTimeStamp(new Date(),"yyyy年MM月dd日 HH:mm"),"#FFC125"));
        data.put("remark",WechatTemplateMsg.item("点击\"详情\"查看借款单信息，"+lastMsg,""));
        wechatTemplateMsg.setTouser(openId);
        wechatTemplateMsg.setTemplate_id(WeChatConfig.template_order_repay_apply);
        wechatTemplateMsg.setData(data);
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + WeChatConfig.order_info_http + orderId;
        wechatTemplateMsg.setUrl(url);
        SendRequestUtil.httpPost(WeChatConfig.template_http + RedisCache.get(WeChatConfig.ACCESS_TOKEN), JSONObject.toJSONString(wechatTemplateMsg));
    }


    /**
     * 发送推送消息 申请通过提醒
     * @param msg
     * @param number
     * @param userName
     * @param lastMsg
     * @param openId
     * @param orderId
     */
    public static void sendTemplateOrderApplyPassFor(String msg,String number ,String userName ,String lastMsg ,String openId ,Integer orderId) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        WechatTemplateMsg wechatTemplateMsg = new WechatTemplateMsg();
        TreeMap<String, TreeMap<String, String>> data = new TreeMap<>();
        data.put("first",WechatTemplateMsg.item(msg,"#173177"));
        data.put("keyword1",WechatTemplateMsg.item(number,"#FFC125"));
        data.put("keyword2",WechatTemplateMsg.item(userName,"#FFC125"));
        data.put("keyword3",WechatTemplateMsg.item(Timestamp.DateTimeStamp(new Date(),"yyyy年MM月dd日 HH:mm"),"#FFC125"));
        data.put("remark",WechatTemplateMsg.item(lastMsg,""));
        wechatTemplateMsg.setTouser(openId);
        wechatTemplateMsg.setTemplate_id(WeChatConfig.template_order_apply_pass_result);
        wechatTemplateMsg.setData(data);
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + WeChatConfig.order_info_http + orderId;
        wechatTemplateMsg.setUrl(url);
        SendRequestUtil.httpPost(WeChatConfig.template_http + RedisCache.get(WeChatConfig.ACCESS_TOKEN), JSONObject.toJSONString(wechatTemplateMsg));
    }


    /**
     * 发送推送消息 申请结果通知
     * @param msg
     * @param price
     * @param dayMsg
     * @param lastMsg
     * @param openId
     * @param orderId
     */
    public static void sendTemplateOrderApplyForResult(String msg,Double price ,String dayMsg ,String resultMsg,
                                                       String lastMsg ,String openId ,Integer orderId) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        WechatTemplateMsg wechatTemplateMsg = new WechatTemplateMsg();
        TreeMap<String, TreeMap<String, String>> data = new TreeMap<>();
        data.put("first",WechatTemplateMsg.item(msg,"#173177"));
        data.put("keyword1",WechatTemplateMsg.item(price.toString(),"#FFC125"));
        data.put("keyword2",WechatTemplateMsg.item(dayMsg,"#FFC125"));
        data.put("keyword3",WechatTemplateMsg.item(resultMsg,"#FFC125"));
        data.put("keyword4",WechatTemplateMsg.item(Timestamp.DateTimeStamp(new Date(),"yyyy年MM月dd日 HH:mm"),"#FFC125"));
        data.put("remark",WechatTemplateMsg.item("点击\"详情\"查看借款单信息，"+lastMsg,""));
        wechatTemplateMsg.setTouser(openId);
        wechatTemplateMsg.setTemplate_id(WeChatConfig.template_order_apply_result);
        wechatTemplateMsg.setData(data);
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() +WeChatConfig.order_info_http + orderId;
        wechatTemplateMsg.setUrl(url);
        SendRequestUtil.httpPost(WeChatConfig.template_http + RedisCache.get(WeChatConfig.ACCESS_TOKEN), JSONObject.toJSONString(wechatTemplateMsg));
    }


}
