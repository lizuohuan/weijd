package com.magic.weijd.controller;

import com.magic.weijd.cache.RedisCache;
import com.magic.weijd.util.SendRequestUtil;
import com.magic.weijd.util.ServerIp;
import com.magic.weijd.util.WeChatConfig;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lzh
 * @create 2017/9/25 15:24
 */
@RestController
@RequestMapping("/wxMenu")
public class WxMenuController extends BaseController {

    @RequestMapping("/createWxMenu")
    public void createWxMenu() {
        try {
            String accessToken = (String) RedisCache.get(WeChatConfig.ACCESS_TOKEN);
            if (null == accessToken || "".equals(accessToken)) {
                //获取AccessToken
                String getAccessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + WeChatConfig.appId+ "&secret=" + WeChatConfig.secret;
                String accessTokenStr = SendRequestUtil.sendRequest(getAccessTokenUrl,"GET");
                JSONObject accessTokenObj = JSONObject.fromObject(accessTokenStr);
                accessToken = accessTokenObj.getString("access_token");
                RedisCache.put(WeChatConfig.ACCESS_TOKEN,accessToken,Long.valueOf(accessTokenObj.getString("expires_in")));
            }
            logger.info("access_token:"+accessToken);

            if (getWxMenu(accessToken).equals("46003")) {
                createMenu(accessToken);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("access_token获取失败");
        }
    }

    /**
     * 获取微信首页菜单
     * @return
     */
    public String getWxMenu(String accessToken) throws Exception {
        String getWxMenuUrl = WeChatConfig.get_menu_http+accessToken;
        String wxMenuStr = SendRequestUtil.sendRequest(getWxMenuUrl,"GET");
        System.out.println(wxMenuStr);
        String errcode = "46003";
        if (wxMenuStr.contains("errcode")) {
            JSONObject accessTokenObj = JSONObject.fromObject(wxMenuStr);
            errcode = accessTokenObj.getString("errcode");
        } else {
            delWxMenu(accessToken);
        }
        return errcode;
    }

    /**
     * 删除微信首页菜单
     * @return
     */
    public void delWxMenu(String accessToken) throws Exception {
        SendRequestUtil.sendRequest(WeChatConfig.delete_menu_http+accessToken,"GET");
    }


    /**
     * 创建自定义菜单
     * @param accessToken
     * @return
     */
    public String createMenu(String accessToken) throws Exception {
        String createWxMenuUrl = WeChatConfig.create_menu_http+accessToken;
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        //我的 主页url
        String myUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + WeChatConfig.my_http;
        //联系我们url
        String contactUsUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + WeChatConfig.contact_us_http;
        //意见反馈url
        String feedbackUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + WeChatConfig.feedback_http;
//        if (request.getLocalPort()==80) {
//            myUrl = request.getLocalAddr() + WeChatConfig.my_http;
//            contactUsUrl = request.getLocalAddr() + WeChatConfig.contact_us_http;
//            feedbackUrl = request.getLocalAddr() + WeChatConfig.feedback_http;
//        }
//        if (!myUrl.contains("http")) {
//            myUrl = "http://" + myUrl;
//            contactUsUrl = "http://" + contactUsUrl;
//            feedbackUrl = "http://" + feedbackUrl;
//        }
        JSONObject menuJson = new JSONObject();
        {
            {
                //菜单
                List<JSONObject> list = new ArrayList<>();
                {   //我的

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("type","view");
                    jsonObject.put("name","我的");
                    jsonObject.put("key","V1001_MY");
                    jsonObject.put("url",myUrl);
                    list.add(jsonObject);
                }
                {   //联系我们
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("name","联系我们");
                    List<JSONObject> subList = new ArrayList<>();
                    {
                        //联系我们
                        JSONObject subObj = new JSONObject();
                        subObj.put("type","view");
                        subObj.put("name","联系我们");
                        subObj.put("url",contactUsUrl);
                        subList.add(subObj);
                    }
                    {
                        //意见反馈
                        JSONObject subObj = new JSONObject();
                        subObj.put("type","view");
                        subObj.put("name","意见反馈");
                        subObj.put("url",feedbackUrl);
                        subList.add(subObj);
                    }
                    jsonObject.put("sub_button",subList);
                    list.add(jsonObject);
                }
                menuJson.put("button",list);
            }
            //匹配规则
            {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("country","中国");
                jsonObject.put("language","zh_CN");
                menuJson.put("matchrule",jsonObject);
            }
        }
        String result = SendRequestUtil.httpPost(createWxMenuUrl,menuJson.toString());
        System.out.println(result);
        return result;
    }

}
