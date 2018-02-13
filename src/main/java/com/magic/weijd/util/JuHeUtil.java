package com.magic.weijd.util;

import net.sf.json.JSONObject;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 聚合数据 身份证验证
 * @author lzh
 * @create 2017/12/26 21:17
 */
public class JuHeUtil {

    //聚合 请求地址
    private static final String url = "http://op.juhe.cn/idcard/query";

    private static final String key = "52da40cd0bdfdf29fa19b12228ba7ea2";


    private static final String send_url = url+"?key=" + key;


    public static Boolean verifyIdCard(String userName,String idCard) throws Exception {

        JSONObject result = JSONObject.fromObject(SendRequestUtil.httpPost(send_url+"&idcard="+idCard+"&realname="+ URLEncoder.encode(userName,"utf-8"),""));
        System.out.println(result);
        if (Integer.parseInt(result.getString("error_code")) == 0) {
            if (Integer.parseInt(JSONObject.fromObject(result.get("result")).getString("res")) == 1) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) throws Exception {
        verifyIdCard("李作焕","510121199411120655");
    }
}
