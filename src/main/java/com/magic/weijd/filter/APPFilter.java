package com.magic.weijd.filter;

import com.magic.weijd.cache.RedisCache;
import com.magic.weijd.util.CommonUtil;
import com.magic.weijd.util.FilterEmojiUtil;
import com.magic.weijd.util.SendRequestUtil;
import com.magic.weijd.util.WeChatConfig;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric Xie on 2017/3/21 0021.
 */

@WebFilter("/wechat/*")
public class APPFilter implements Filter {


    private Logger logger = Logger.getLogger(this.getClass());

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String ua = request.getHeader("user-agent").toLowerCase();

        if(request.getRequestURL().toString().indexOf(".css") > 0 ||
                request.getRequestURL().toString().indexOf(".js") > 0 || request.getRequestURL().toString().indexOf("font-awesome") > 0
                || request.getRequestURL().toString().indexOf(".js") > 0 || request.getRequestURL().toString().indexOf("/img") > 0 ){
            logger.info("static-----------"+request.getRequestURL().toString());
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        logger.debug("UA: "+ua);
        if (ua.indexOf("micromessenger") < 0) {// 非微信浏览器不进行请求
            logger.info("进入了非微信浏览器不进行请求.....");
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String requestURI = request.getRequestURI();

//        //分享出来的
//        if (requestURI.contains("borrowingDetail")){
//            String orderId = request.getParameter("orderId");
//            System.out.println("filter:" + orderId);
//            if (null != orderId && "".equals(orderId)) {
//                System.out.println(orderId);
//                request.getSession().setAttribute("orderId",orderId);
//                System.out.println(request.getSession().getAttribute("orderId"));
//            }
//        }

        String contextPath = request.getContextPath();
        String url = requestURI.substring(contextPath.length());
        List<String> excludePath = new ArrayList<String>();
        excludePath.add("cook");
        for (String exclude : excludePath) {
            if (exclude.contains(url)) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }

        // 不拦截付款回调
//        if (url.indexOf("wxPayCallBack") >= 0) {
//            logger.info("进入不拦截付款回调.....");
//            filterChain.doFilter(servletRequest, servletResponse);
//            return;
//        }
        // 不拦截付款回调
        if (url.indexOf("wxPayCallBackApi") >= 0) {
            logger.info("进入不拦截付款回调.....");
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
//        // 不拦截支付宝付款回调
//        if (url.indexOf("aliPay") >= 0) {
//            logger.info("不拦截支付宝付款回调.....");
//            filterChain.doFilter(servletRequest, servletResponse);
//            return;
//        }

        HttpSession session = request.getSession();
        Object object = session.getAttribute(WeChatConfig.SESSION_WX_USER);
        String code = request.getParameter("code");

        logger.info("object 对象："+ object);
        logger.info("code 对象："+ code);

        if (null == object && CommonUtil.isEmpty(code)) {

            // 无微信用户信息, 跳转获取code
            String redirectUrl = request.getRequestURL().toString();
            logger.info("进入无微信用户信息, 跳转获取code.....");
            logger.info("进入无微信用户信息, 跳转获取code  redirectUrl....." + redirectUrl);
//            String redirectUrl = "http://tiger.magic-beans.cn/app/page/index";
            String authUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WeChatConfig.appId + "&redirect_uri=" + redirectUrl + "&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
            logger.info("authUrl:       "+authUrl);
            response.sendRedirect(authUrl);
            return;
        } else if (null == object && !CommonUtil.isEmpty(code)) {
            logger.info("进入获取openId.....");
            // 已获取code, 调用获取openid
            String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + WeChatConfig.appId+ "&secret=" + WeChatConfig.secret + "&code=" + code + "&grant_type=authorization_code";
            try {
                String resultStr = SendRequestUtil.sendRequest(requestUrl,"GET");
                JSONObject result = JSONObject.fromObject(resultStr);
                logger.info("resultStr:"+resultStr);
                logger.info("resultStr:"+resultStr);
                String openId = result.getString("openid");

                String accessToken = (String) RedisCache.get(WeChatConfig.ACCESS_TOKEN);
                if (null == accessToken || "".equals(accessToken)) {
                    //获取AccessToken
                    String getAccessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + WeChatConfig.appId+ "&secret=" + WeChatConfig.secret;
                    String accessTokenStr = SendRequestUtil.sendRequest(getAccessTokenUrl,"GET");
                    JSONObject accessTokenObj = JSONObject.fromObject(accessTokenStr);
                    logger.info("accessTokenObj:"+accessTokenObj);
                    accessToken = accessTokenObj.getString("access_token");
                    RedisCache.put(WeChatConfig.ACCESS_TOKEN,accessToken,Long.valueOf(accessTokenObj.getString("expires_in")));
                }
                logger.info("access_token:"+accessToken);


                //获取微信用户基本信息
                WxMpUser wxUser = new WxMpUser();
                String getWxUserInfoUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+accessToken+"&openid="+openId+"&lang=zh_CN";
                String wxUserInfo = SendRequestUtil.sendRequest(getWxUserInfoUrl,"GET");
                if (!wxUserInfo.contains("errcode")) {
                    wxUser = com.alibaba.fastjson.JSONObject.parseObject(wxUserInfo,WxMpUser.class);
                    wxUser.setNickname(FilterEmojiUtil.revert(wxUser.getNickname()));
                }
                logger.info("wxUserInfoStr:"+wxUserInfo);
                session.setAttribute(WeChatConfig.SESSION_WX_USER, wxUser);
                logger.info("openId:"+openId);
            }catch (Exception e){
                logger.error("请求appid异常.",e);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {

    }

}
