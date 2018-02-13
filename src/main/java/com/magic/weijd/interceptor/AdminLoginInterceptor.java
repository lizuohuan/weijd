package com.magic.weijd.interceptor;


import com.magic.weijd.controller.BaseController;
import com.magic.weijd.entity.Admins;
import com.magic.weijd.entity.User;
import com.magic.weijd.service.UserService;
import com.magic.weijd.util.LoginHelper;
import com.magic.weijd.util.SpringUtil;
import com.magic.weijd.util.WeChatConfig;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Eric Xie on 2017/7/12 0012.
 */
public class AdminLoginInterceptor extends BaseController implements HandlerInterceptor {
    private Logger logger = Logger.getLogger(this.getClass());

    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        logger.info("AdminLoginInterceptor.....");
        String requestURI = httpServletRequest.getRequestURI();
        Admins user = LoginHelper.getCurrentAdmin();
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setCharacterEncoding("UTF-8");
        if (!requestURI.contains("login") &&
                requestURI.indexOf("/admin") > 0 &&
                requestURI.indexOf(".css") < 1 &&
                requestURI.indexOf(".js") < 1 &&
                requestURI.indexOf("font-awesome") < 1 &&
                requestURI.indexOf("/img") < 1 ) {
            if(null == user){
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+"/admin/page/login");
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
