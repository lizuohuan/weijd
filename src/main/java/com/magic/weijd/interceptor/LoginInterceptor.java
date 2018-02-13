package com.magic.weijd.interceptor;


import com.magic.weijd.controller.BaseController;
import com.magic.weijd.entity.User;
import com.magic.weijd.service.UserService;
import com.magic.weijd.util.LoginHelper;
import com.magic.weijd.util.SpringUtil;
import com.magic.weijd.util.WeChatConfig;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Eric Xie on 2017/7/12 0012.
 */
public class LoginInterceptor extends BaseController implements HandlerInterceptor {
    private Logger logger = Logger.getLogger(this.getClass());

    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        logger.info("LoginInterceptor.....");
        String requestURI = httpServletRequest.getRequestURI();
        User user = LoginHelper.getCurrentUser();
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setCharacterEncoding("UTF-8");
        if (!requestURI.contains("register") &&
                !requestURI.contains("sendMessageRegister")&&
                !requestURI.contains("borrowMoneyAgreement")&&
                !requestURI.contains("contactUs") &&
                !requestURI.contains("agreement") &&
                requestURI.indexOf("/wechat") > 0 &&
                requestURI.indexOf(".css") < 1 &&
                requestURI.indexOf(".js") < 1 &&
                requestURI.indexOf("font-awesome") < 1 &&
                requestURI.indexOf("/img") < 1 ) {
            if(null == user){
                WxMpUser wxUser = (WxMpUser) httpServletRequest.getSession().getAttribute(WeChatConfig.SESSION_WX_USER);
                if (null != wxUser) {
                    logger.info("wxUser不为空........");
                    // 将原openid设置为null
                    UserService userService = (UserService) SpringUtil.getBean("UserService");
                    User original = userService.findByWxOpenId(wxUser.getOpenId());
                    if (null != original) {
                        //放入缓存
                        httpServletRequest.getSession().setAttribute(LoginHelper.SESSION_USER,original);
                        return true;
                    }

                }
                logger.info("wxUser为空........");
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+"/wechat/page/register");
                return false;
            }
        }

        return true;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }


}
