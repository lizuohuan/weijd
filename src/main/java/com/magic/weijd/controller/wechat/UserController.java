package com.magic.weijd.controller.wechat;/**
 * Created by admin on 2017/9/7.
 */

import com.magic.weijd.cache.RedisCache;
import com.magic.weijd.controller.BaseController;
import com.magic.weijd.entity.User;
import com.magic.weijd.exception.InterfaceCommonException;
import com.magic.weijd.service.UserService;
import com.magic.weijd.sms.SMSCode;
import com.magic.weijd.util.*;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;

/**
 * 用户控制器
 *
 * @author lzh
 * @create 2017-09-07 20:25
 **/
@RestController("WechatUserController")
@RequestMapping("/wechat/user")
public class UserController extends BaseController {


    @Resource
    private UserService userService;

    /**
     * 注册
     * @param mobile 电话
     * @param mobileCode 短信验证码
     * @param payPwd 支付密码
     * @return
     */
    @RequestMapping("/register")
    public ViewData register(String mobile , String mobileCode,String payPwd, HttpServletRequest request) {
        try {
            if (null == mobile || null == mobileCode || null == payPwd) {
                return buildFailureJson(StatusConstant.FIELD_NOT_NULL, "字段不能为空!");
            }
            User verMember = userService.queryUserByPhone(mobile);
            if (null != verMember) {
                return buildFailureJson(StatusConstant.PHONE_NUMBER_THERE,"手机号已存在");
            }
            if (mobileCode.trim().length() > 0) {

                Object smsCodes = RedisCache.get(SessionConfig.SMS_REGISTER_SESSION + mobile,String.class);
                RedisCache.remove(SessionConfig.SMS_REGISTER_SESSION + mobile);
                String smsCode = String.valueOf(smsCodes);
                if (null == smsCode) {
                    return buildFailureJson(StatusConstant.Fail_CODE,"未获取到验证码或者验证码已失效，请重新获取验证码");
                }
                if (!mobileCode.trim().equals(smsCode)) {
                    return buildFailureJson(StatusConstant.Fail_CODE,"验证码错误，以前的验证码已失效，请重新获取验证码");
                }
            }

            User member = new User();
            member.setPhone(mobile);
            //注册
            member.setIsValid(1);
            member.setPayPwd(payPwd);
            // 与微信绑定.
            WxMpUser wxUser = (WxMpUser) request.getSession().getAttribute(WeChatConfig.SESSION_WX_USER);
            if (null != wxUser) {
                // 将原openid设置为null
                User original = userService.findByWxOpenId(wxUser.getOpenId());
                if (null != original) {
                    original.setOpenId("");
                    userService.updateAll(original);
                }

                member.setUserName(/*wxUser.getNickname()*/"");
                member.setAvatar(wxUser.getHeadImgUrl());
                member.setOpenId(wxUser.getOpenId());
            }
            member.setCreditPoints(10);
            userService.save(member);
            //放入缓存
            request.getSession().setAttribute(LoginHelper.SESSION_USER,member);
            return buildSuccessJson(StatusConstant.SUCCESS_CODE, "注册成功!",member);
        } catch (InterfaceCommonException e) {
            logger.info(e.getMessage(),e.getErrorCode());
            return buildFailureJson(e.getErrorCode(),e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return buildFailureJson(StatusConstant.Fail_CODE,"服务器超时");
        }
    }


    /**
     * 更新用户 不为空的字段 通过ID
     * @param user
     */
    @RequestMapping("/update")
    public ViewData update(User user, HttpServletRequest request){
        try {
            User user1 = LoginHelper.getCurrentUser();
            if (null == user1) {
                return buildFailureJson(StatusConstant.NOTLOGIN,"未登录");
            }
            if (null == user) {
                return buildFailureJson(StatusConstant.FIELD_NOT_NULL,"字段不能为空");
            }
            if (null == user.getAvatar() || "".equals(user.getAvatar())) {
                user.setAvatar(null);
            }
            user.setId(user1.getId());
            userService.update(user);
            //放入缓存
            request.getSession().setAttribute(LoginHelper.SESSION_USER,userService.info(user.getId()));
            return buildSuccessCodeJson(StatusConstant.SUCCESS_CODE,"更新成功");
        } catch (InterfaceCommonException e) {
            logger.info(e.getMessage(),e.getErrorCode());
            return buildFailureJson(e.getErrorCode(),e.getMessage());
        } catch (Exception e) {
            logger.error("服务器超时，更新失败",e);
            return buildFailureJson(StatusConstant.Fail_CODE,"服务器超时，更新失败");
        }
    }



    /**
     * 补全信息
     * @param
     */
    @RequestMapping("/updateInfo")
    public ViewData updateInfo(User user1,String mobileCode, HttpServletRequest request){
        try {
            User user = LoginHelper.getCurrentUser();
            if (null == user) {
                return buildFailureJson(StatusConstant.NOTLOGIN,"未登录");
            }
            if (mobileCode.trim().length() > 0) {
                Object smsCodes = RedisCache.get(SessionConfig.SMS_UPDATE_INFO_SESSION + user.getPhone(),String.class);
                String smsCode = String.valueOf(smsCodes);
                if (null == smsCode || "".equals(smsCode)) {
                    return buildFailureJson(StatusConstant.Fail_CODE,"未获取到验证码或者验证码已失效，请重新获取验证码");
                }
                if (!mobileCode.trim().equals(smsCode)) {
                    return buildFailureJson(StatusConstant.Fail_CODE,"验证码错误，以前的验证码已失效，请重新获取验证码");
                }
            }
            user1.setId(user.getId());
            userService.update(user1);
            //放入缓存
            request.getSession().setAttribute(LoginHelper.SESSION_USER,userService.info(user.getId()));
            RedisCache.remove(SessionConfig.SMS_UPDATE_INFO_SESSION + user.getPhone());
            return buildSuccessCodeJson(StatusConstant.SUCCESS_CODE,"更新成功");
        } catch (InterfaceCommonException e) {
            logger.info(e.getMessage(),e.getErrorCode());
            return buildFailureJson(e.getErrorCode(),e.getMessage());
        }  catch (Exception e) {
            logger.error("服务器超时，更新失败",e);
            return buildFailureJson(StatusConstant.Fail_CODE,"服务器超时，更新失败");
        }
    }


    /**
     * 换绑手机号
     * @param phone
     */
    @RequestMapping("/updatePhone")
    public ViewData updatePhone(String phone,String mobileCode, HttpServletRequest request){
        try {
            User user = LoginHelper.getCurrentUser();
            if (null == user) {
                return buildFailureJson(StatusConstant.NOTLOGIN,"未登录");
            }
            if (null == phone) {
                return buildFailureJson(StatusConstant.FIELD_NOT_NULL,"字段不能为空");
            }
            if (mobileCode.trim().length() > 0) {

                Object smsCodes = RedisCache.get(SessionConfig.SMS_UPDATE_SESSION + phone,String.class);
                RedisCache.remove(SessionConfig.SMS_UPDATE_SESSION + phone);
                String smsCode = String.valueOf(smsCodes);
                if (null == smsCode || "".equals(smsCode)) {
                    return buildFailureJson(StatusConstant.Fail_CODE,"未获取到验证码或者验证码已失效，请重新获取验证码");
                }
                if (!mobileCode.trim().equals(smsCode)) {
                    return buildFailureJson(StatusConstant.Fail_CODE,"验证码错误，以前的验证码已失效，请重新获取验证码");
                }
            }
            user.setPhone(phone);
            userService.update(user);
            //放入缓存
            request.getSession().setAttribute(LoginHelper.SESSION_USER,userService.info(user.getId()));
            return buildSuccessCodeJson(StatusConstant.SUCCESS_CODE,"更新成功");
        } catch (InterfaceCommonException e) {
            logger.info(e.getMessage(),e.getErrorCode());
            return buildFailureJson(e.getErrorCode(),e.getMessage());
        }  catch (Exception e) {
            logger.error("服务器超时，更新失败",e);
            return buildFailureJson(StatusConstant.Fail_CODE,"服务器超时，更新失败");
        }
    }


    /**
     * 修改密码 1 知道原支付密码
     * @param oldPwd 旧密码
     * @param newPwd 新密码
     * @param request
     * @return
     */
    @RequestMapping("/updatePwd1")
    public ViewData updatePwd1(String oldPwd,String newPwd, HttpServletRequest request){
        try {
            User user = LoginHelper.getCurrentUser();
            if (null == user) {
                return buildFailureJson(StatusConstant.NOTLOGIN,"未登录");
            }
            if (null == oldPwd || null == newPwd) {
                return buildFailureJson(StatusConstant.FIELD_NOT_NULL,"字段不能为空");
            }
            if (!user.getPayPwd().equals(oldPwd)) {
                return buildFailureJson(StatusConstant.Fail_CODE,"您填写的旧密码不正确");
            }
            user.setPayPwd(newPwd);
            userService.update(user);
            //放入缓存
            request.getSession().setAttribute(LoginHelper.SESSION_USER,userService.info(user.getId()));
            return buildSuccessCodeJson(StatusConstant.SUCCESS_CODE,"更新成功");
        } catch (Exception e) {
            logger.error("服务器超时，更新失败",e);
            return buildFailureJson(StatusConstant.Fail_CODE,"服务器超时，更新失败");
        }
    }


    /**
     * 修改密码 2 忘记原支付密码
     * @param phone
     * @param newPwd
     * @param mobileCode
     * @param request
     * @return
     */
    @RequestMapping("/updatePwd2")
    public ViewData updatePwd2(String phone,String newPwd,String mobileCode, HttpServletRequest request){
        try {
            User user = LoginHelper.getCurrentUser();
            if (null == user) {
                return buildFailureJson(StatusConstant.NOTLOGIN,"未登录");
            }
            if (CommonUtil.isEmpty(phone,newPwd,mobileCode)) {
                return buildFailureJson(StatusConstant.FIELD_NOT_NULL,"字段不能为空");
            }
            if (!user.getPhone().equals(phone)) {
                return buildFailureJson(StatusConstant.Fail_CODE,"手机号不正确");
            }
            if (mobileCode.trim().length() > 0) {
                Object smsCodes = RedisCache.get(SessionConfig.SMS_UPDATE_PAY_PWD_SESSION + phone,String.class);
                RedisCache.remove(SessionConfig.SMS_UPDATE_PAY_PWD_SESSION + phone);
                String smsCode = String.valueOf(smsCodes);
                if (null == smsCode || "".equals(smsCode)) {
                    return buildFailureJson(StatusConstant.Fail_CODE,"未获取到验证码或者验证码已失效，请重新获取验证码");
                }
                if (!mobileCode.trim().equals(smsCode)) {
                    return buildFailureJson(StatusConstant.Fail_CODE,"验证码错误，以前的验证码已失效，请重新获取验证码");
                }
            }
            user.setPayPwd(newPwd);
            userService.update(user);
            //放入缓存
            request.getSession().setAttribute(LoginHelper.SESSION_USER,userService.info(user.getId()));
            return buildSuccessCodeJson(StatusConstant.SUCCESS_CODE,"修改成功");
        } catch (Exception e) {
            logger.error("服务器超时，更新失败",e);
            return buildFailureJson(StatusConstant.Fail_CODE,"服务器超时，更新失败");
        }
    }


    /**
     * 查询用户的基础信息
     * @return
     */
    @RequestMapping("/info")
    public ViewData info(){
        try {
            User user = LoginHelper.getCurrentUser();
            user = userService.info(user.getId());
            return buildSuccessViewData(StatusConstant.SUCCESS_CODE,"获取成功 ",user);
        } catch (Exception e) {
            logger.error("服务器超时，获取失败",e);
            return buildFailureJson(StatusConstant.Fail_CODE,"服务器超时，获取失败");
        }
    }

    /**
     * 查询个人信用
     * @param userName
     * @param idCard
     * @return
     */
    @RequestMapping("/findUserCredit")
    public ViewData findUserCredit(String userName , String idCard){
        try {
            if (null == userName || null == idCard) {
                return buildFailureJson(StatusConstant.FIELD_NOT_NULL,"字段不能为空");
            }
            return buildSuccessViewData(StatusConstant.SUCCESS_CODE,"获取成功 ",
                    userService.findUserCredit(userName, idCard));
        } catch (Exception e) {
            logger.error("服务器超时，获取失败",e);
            return buildFailureJson(StatusConstant.Fail_CODE,"服务器超时，获取失败");
        }
    }

    /**
     * 查询个人信用 根据id
     * @param id
     * @return
     */
    @RequestMapping("/findUserCreditById")
    public ViewData findUserCreditById(Integer id){
        try {
            if (null == id) {
                return buildFailureJson(StatusConstant.FIELD_NOT_NULL,"字段不能为空");
            }
            return buildSuccessViewData(StatusConstant.SUCCESS_CODE,"获取成功 ",
                    userService.findUserCreditById(id));
        } catch (Exception e) {
            logger.error("服务器超时，获取失败",e);
            return buildFailureJson(StatusConstant.Fail_CODE,"服务器超时，获取失败");
        }
    }


    /**
     * 统计 逾期次数 待还金额 借入总金额
     *  @param type 0：查看借入 1：借出  null:全部
     * @return
     */
    @RequestMapping("/statistics")
    public ViewData statistics(Integer type){
        try {
            User user = LoginHelper.getCurrentUser();
            if (null == user) {
                return buildFailureJson(StatusConstant.NOTLOGIN,"未登录");
            }
            return buildSuccessViewData(StatusConstant.SUCCESS_CODE,"获取成功 ",
                    userService.statistics(user.getId(),type));
        } catch (Exception e) {
            logger.error("服务器超时，获取失败",e);
            return buildFailureJson(StatusConstant.Fail_CODE,"服务器超时，获取失败");
        }
    }

}
