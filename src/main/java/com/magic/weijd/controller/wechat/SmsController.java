package com.magic.weijd.controller.wechat;

import com.magic.weijd.cache.RedisCache;
import com.magic.weijd.controller.BaseController;
import com.magic.weijd.entity.User;
import com.magic.weijd.exception.InterfaceCommonException;
import com.magic.weijd.service.UserService;
import com.magic.weijd.sms.SMSCode;
import com.magic.weijd.util.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.MessageFormat;

/**
 * 发送短信
 * @author lzh
 * @create 2017/9/13 14:07
 */
@RestController("WechatSmsController")
@RequestMapping("/wechat/sms")
public class SmsController extends BaseController {

    @Resource
    private UserService userService;

    /**
     * 短信发送 注册
     * @param phone
     * @return
     */
    @RequestMapping(value = "/sendMessageRegister",method = RequestMethod.POST)
    public ViewData sendMessageRegister(String phone){
        try {
            if(CommonUtil.isEmpty(phone)){
                return buildFailureJson(StatusConstant.FIELD_NOT_NULL,"手机号不能为空");
            }
            String code = SMSCode.createRandomCode();
            User sqlUser = userService.queryUserByPhone(phone);
            if(null != sqlUser){
                return buildFailureJson(StatusConstant.OBJECT_EXIST,"手机号已经被注册");
            }
            boolean b = SMSCode.sendMessage(MessageFormat.format(TextMessage.MSG_CODE, code), phone);
            if(!b){
                return buildFailureJson(StatusConstant.Fail_CODE,"短信发送失败");
            }
            RedisCache.remove(SessionConfig.SMS_REGISTER_SESSION + phone);
            RedisCache.put(SessionConfig.SMS_REGISTER_SESSION + phone,code,600);
            return buildSuccessJson(StatusConstant.SUCCESS_CODE,"发送成功",code);
        }catch (InterfaceCommonException e){
            return buildFailureJson(e.getErrorCode(),e.getMessage());
        }catch (Exception e){
            return buildFailureJson(StatusConstant.Fail_CODE,"短信发送失败");
        }

    }



    /**
     * 短信发送 换绑手机号
     * @param phone
     * @return
     */
    @RequestMapping(value = "/sendMessageUpdatePhone",method = RequestMethod.POST)
    public ViewData sendMessageUpdatePhone(String phone){
        try {
            if(CommonUtil.isEmpty(phone)){
                return buildFailureJson(StatusConstant.FIELD_NOT_NULL,"手机号不能为空");
            }
            String code = SMSCode.createRandomCode();
            User sqlUser = userService.queryUserByPhone(phone);
            if(null != sqlUser){
                return buildFailureJson(StatusConstant.OBJECT_EXIST,"手机号已经被注册");
            }
            boolean b = SMSCode.sendMessage(MessageFormat.format(TextMessage.MSG_CODE, code), phone);
            if(!b){
                return buildFailureJson(StatusConstant.Fail_CODE,"短信发送失败");
            }
            RedisCache.remove(SessionConfig.SMS_UPDATE_SESSION + phone);
            RedisCache.put(SessionConfig.SMS_UPDATE_SESSION + phone,code ,600);
            return buildSuccessJson(StatusConstant.SUCCESS_CODE,"发送成功",code);
        }catch (InterfaceCommonException e){
            return buildFailureJson(e.getErrorCode(),e.getMessage());
        }catch (Exception e){
            return buildFailureJson(StatusConstant.Fail_CODE,"短信发送失败");
        }

    }

    /**
     * 短信发送 补全信息
     * @param phone
     * @return
     */
    @RequestMapping(value = "/sendMessageUpdateInfo",method = RequestMethod.POST)
    public ViewData sendMessageUpdateInfo(String phone){
        try {
            if(CommonUtil.isEmpty(phone)){
                return buildFailureJson(StatusConstant.FIELD_NOT_NULL,"手机号不能为空");
            }
            String code = SMSCode.createRandomCode();
            User sqlUser = userService.queryUserByPhone(phone);
            if(null == sqlUser){
                return buildFailureJson(StatusConstant.OBJECT_EXIST,"此手机号不存在");
            }
            boolean b = SMSCode.sendMessage(MessageFormat.format(TextMessage.MSG_CODE, code), phone);
            if(!b){
                return buildFailureJson(StatusConstant.Fail_CODE,"短信发送失败");
            }
            RedisCache.remove(SessionConfig.SMS_UPDATE_INFO_SESSION + phone);
            RedisCache.put(SessionConfig.SMS_UPDATE_INFO_SESSION + phone,code,600);
            return buildSuccessJson(StatusConstant.SUCCESS_CODE,"发送成功",code);
        }catch (InterfaceCommonException e){
            return buildFailureJson(e.getErrorCode(),e.getMessage());
        }catch (Exception e){
            return buildFailureJson(StatusConstant.Fail_CODE,"短信发送失败");
        }
    }

    /**
     * 短信发送 忘记支付密码
     * @param phone
     * @return
     */
    @RequestMapping(value = "/sendMessageUpdatePayPwd",method = RequestMethod.POST)
    public ViewData sendMessageUpdatePayPwd(String phone){
        try {
            if(CommonUtil.isEmpty(phone)){
                return buildFailureJson(StatusConstant.FIELD_NOT_NULL,"手机号不能为空");
            }
            String code = SMSCode.createRandomCode();
            User sqlUser = userService.queryUserByPhone(phone);
            if(null == sqlUser){
                return buildFailureJson(StatusConstant.OBJECT_EXIST,"此手机号不存在");
            }
            boolean b = SMSCode.sendMessage(MessageFormat.format(TextMessage.MSG_CODE, code), phone);
            if(!b){
                return buildFailureJson(StatusConstant.Fail_CODE,"短信发送失败");
            }
            RedisCache.remove(SessionConfig.SMS_UPDATE_PAY_PWD_SESSION + phone);
            RedisCache.put(SessionConfig.SMS_UPDATE_PAY_PWD_SESSION + phone,code,600);
            return buildSuccessJson(StatusConstant.SUCCESS_CODE,"发送成功",code);
        }catch (InterfaceCommonException e){
            return buildFailureJson(e.getErrorCode(),e.getMessage());
        }catch (Exception e){
            return buildFailureJson(StatusConstant.Fail_CODE,"短信发送失败");
        }
    }


}
