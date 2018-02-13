package com.magic.weijd.controller.wechat;


import com.magic.weijd.cache.RedisCache;
import com.magic.weijd.controller.BaseController;
import com.magic.weijd.util.*;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

/**
 * Created by Eric Xie on 2017/4/6 0006.
 */

@RestController("WechatWXClientController")
@RequestMapping("/wechat/wxClient")
public class WXClientController extends BaseController {


    /**
     *  微信客户端 获取 签名接口
     * @return
     */
    @RequestMapping(value = "/jsSign",method = RequestMethod.POST)
    public ViewData wxSign(String url){
        try {
            String accessToken = (String) RedisCache.get(WeChatConfig.ACCESS_TOKEN);
            if(CommonUtil.isEmpty(accessToken)){
                String requestUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + WeChatConfig.appId + "&secret=" + WeChatConfig.secret;
                String resultStr = SendRequestUtil.sendRequest(requestUrl,"GET");
                logger.info("微信客户端获取 toke :"+resultStr);
                JSONObject jsonObject = JSONObject.fromObject(resultStr);
                accessToken = jsonObject.getString("access_token");
                RedisCache.put(WeChatConfig.ACCESS_TOKEN,accessToken,Long.valueOf(jsonObject.getString("expires_in")));
            }

            String ticketStr = (String) RedisCache.get(WeChatConfig.TICKET);
            if(CommonUtil.isEmpty(ticketStr)){
                String jsapiTicketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+accessToken+"&type=jsapi";
                String ticketResult = SendRequestUtil.sendRequest(jsapiTicketUrl,"GET");
                logger.info("微信客户端获取 ticketResult :"+ticketResult);
                JSONObject object = JSONObject.fromObject(ticketResult);
                ticketStr = object.getString("ticket");
                RedisCache.put(WeChatConfig.TICKET,ticketStr,Long.valueOf(object.getString("expires_in")));
            }

            Map<String,Object> data = new TreeMap<String, Object>();
            data.put("jsapi_ticket",ticketStr);
            data.put("noncestr", UUID.randomUUID().toString().replaceAll("-", ""));
            data.put("timestamp",System.currentTimeMillis() / 1000);
            data.put("url",url);
            data = wxSign(data); // 代签名
            data.put("appId",WeChatConfig.appId);
            return buildSuccessJson(StatusConstant.SUCCESS_CODE, "获取成功",data);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return buildFailureJson(StatusConstant.Fail_CODE,"获取失败");
        }
    }





    public static Map<String, Object> wxSign(Map<String, Object> param) {
        String ret = "";
        String signature = "";
        for (String key : param.keySet()) {
            if (key.equals("sign")) {
                continue;
            } else {
                ret += key + "=" + param.get(key) + "&";
            }
        }
        ret = ret.substring(0,ret.length() - 1);
        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(ret.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        param.put("signature", signature);
        return param;
    }
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }



}
