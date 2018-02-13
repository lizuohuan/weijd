package com.magic.weijd.controller.wechat;

import com.magic.weijd.controller.BaseController;
import com.magic.weijd.entity.Order;
import com.magic.weijd.entity.User;
import com.magic.weijd.exception.InterfaceCommonException;
import com.magic.weijd.service.OrderService;
import com.magic.weijd.service.UserService;
import com.magic.weijd.sms.SMSCode;
import com.magic.weijd.util.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 订单（借款）
 * @author lzh
 * @create 2017/9/12 18:25
 */
@RestController("WechatOrderController")
@RequestMapping("/wechat/order")
public class OrderController extends BaseController {

    @Resource
    private OrderService orderService;
    @Resource
    private UserService userService;


    /**
     * 申请借款/出借 创建订单
     * @param order {
     *              type 类型：0：出借 1：借款
     *              isUploadVideo 是否上传视频 0：否 1：是
     * }
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public ViewData save(Order order,String payPwd){
        try {
            User user = LoginHelper.getCurrentUser();
            if (null == user) {
                return buildFailureJson(StatusConstant.NOTLOGIN,"未登录");
            }
            if (null != order.getType() && order.getType() == 0 && !user.getPayPwd().equals(payPwd)) {
                return buildFailureJson(StatusConstant.Fail_CODE,"支付密码错误");
            }
            if (null != order.getFromUserId() && (null == user.getIsFromUser() || user.getIsFromUser() != 1)) {
                return buildFailureJson(StatusConstant.ORDER_STATUS_ABNORMITY,"您还不是出借人，不能进行出借操作");
            }
            if(null == order.getType() || null == order.getPrice() ||
                    null == order.getRepaymentTime() || null == order.getLoansUseId() ||
                    null == order.getInterestId() || null == order.getRepaymentMethodId()){
                return buildFailureJson(StatusConstant.FIELD_NOT_NULL,"字段不能为空");
            }
            order.setStatus(0);
            if (order.getType().equals(0) || order.getType().equals(1)) {
                if (order.getType().equals(0)) {
                    order.setFromUserId(user.getId());
                } else {
                    order.setToUserId(user.getId());
                }
            } else {
                return buildFailureJson(StatusConstant.ARGUMENTS_EXCEPTION,"类型异常");
            }
            String number = "YXY" + Timestamp.DateTimeStamp(new Date(),"yyyyMMdd") + user.getId().toString() + SMSCode.createRandomCode();
            order.setNumber(number);
            order.setPayStatus(StatusConstant.NO_PAY);
            orderService.save(order);
            return buildSuccessJson(StatusConstant.SUCCESS_CODE,"创建成功",order.getId());
        }catch (InterfaceCommonException e){
            e.printStackTrace();
            return buildFailureJson(e.getErrorCode(),e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            return buildFailureJson(StatusConstant.Fail_CODE,"创建失败");
        }

    }



    /**
     * 更新订单信息（状态）
     * @param order {
     *              type 类型：0：出借 1：借款
                    status借款状态 ：
                        1：确认申请
                        2：拒绝借款申请/拒绝出借申请
                        3：同意借款/同意出借（待还款）
                        4：申请还款
                        5：确认还款（完成）
                        6：拒绝延期还款（此状态不记录数据库，记录状态改为3待还款状态）
                        7：申请延期还款
                    isUploadVideo 是否上传视频 0：否 1：是
                    }
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public ViewData update(Order order,String payPwd){
        try {
            User user = LoginHelper.getCurrentUser();
            if (null == user) {
                return buildFailureJson(StatusConstant.NOTLOGIN,"未登录");
            }
            if(null == order.getType() || null == order.getStatus()){
                return buildFailureJson(StatusConstant.FIELD_NOT_NULL,"字段不能为空");
            }
            if (!order.getType().equals(0) && !order.getType().equals(1)) {
                return buildFailureJson(StatusConstant.ARGUMENTS_EXCEPTION,"类型异常");
            } else {
                if (order.getStatus().equals(3)) {
                    Order o = orderService.info(order.getId());
                    if (null == o.getPayStatus() || o.getPayStatus() != 1 && o.getServiceFee() > 0) {
                        return buildFailureJson(StatusConstant.NO_PAY,"请先支付服务费");
                    } else {
                        order.setPayStatus(1);
                    }
                }
                orderService.update(order, user.getId(),payPwd,0);
                return buildFailureJson(StatusConstant.SUCCESS_CODE, "操作成功");
            }
        }catch (InterfaceCommonException e){
            logger.info(e.getMessage(),e.getErrorCode());
            return buildFailureJson(e.getErrorCode(),e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            return buildFailureJson(StatusConstant.Fail_CODE,"操作失败");
        }

    }


    /**
     * 提醒还款
     * @param id
     * @return
     */
    @RequestMapping(value = "/remind",method = RequestMethod.POST)
    public ViewData remind(Integer id , HttpServletRequest request){
        try {
            User user = LoginHelper.getCurrentUser();
            if (null == user) {
                return buildFailureJson(StatusConstant.NOTLOGIN,"未登录");
            }
            Order order = orderService.info(id);
            if (null == order) {
                return buildFailureJson(StatusConstant.NO_DATA,"未知订单");
            }

            if (user.getId().equals(order.getToUserId())) {
                return buildFailureJson(StatusConstant.NOT_AGREE,"对不起，您没有权限操作");
            }
            User user1 = userService.info(order.getToUserId());
            // TODO: 2017/9/12 向借款人发出还款提醒
            String msg = "您的订单还有"+ String.valueOf(DateTimeHelper.getDifference(new Date() ,order.getRepaymentTime(), Calendar.DATE)).replace("-","") +"天";
            String number = order.getNumber();
            String statusMsg = "还款提醒";
            String lastMsg = "请及时还款，逾期会影响个人信用！";
            WxTemplateSend.sendTemplateOrderStatus(msg,number,statusMsg,lastMsg,user1.getOpenId(),id);

//            WechatTemplateMsg wechatTemplateMsg = new WechatTemplateMsg();
//            TreeMap<String, TreeMap<String, String>> data = new TreeMap<>();
//            data.put("msg",WechatTemplateMsg.item("您的订单还有"+ DateTimeHelper.getDifference(new Date() ,order.getRepaymentTime(), Calendar.DATE)+"天","#173177"));
//            data.put("number",WechatTemplateMsg.item(order.getNumber(),"#FFC125"));
////            data.put("msg",WechatTemplateMsg.item("您的订单还有1天","#173177"));
////            data.put("number",WechatTemplateMsg.item("00000000000","#FFC125"));
//            data.put("statusMsg",WechatTemplateMsg.item("还款提醒","#FFC125"));
//            data.put("lastMsg",WechatTemplateMsg.item("请及时还款，逾期会影响个人信用",""));
//            wechatTemplateMsg.setTouser(user.getOpenId());
////            wechatTemplateMsg.setTouser("oHWfiwxHPAznM2Ko8-5HnhoUd8YA");
//            wechatTemplateMsg.setTemplate_id(WeChatConfig.template_order_status);
//            wechatTemplateMsg.setData(data);
//            String utl = request.getLocalAddr() + ":" + request.getLocalPort() + WeChatConfig.order_info_http + id;
//            if (request.getLocalPort()==80) {
//                utl = request.getLocalAddr() + WeChatConfig.order_info_http + id;
//            }
//            wechatTemplateMsg.setUrl(utl);
//            SendRequestUtil.httpPost(WeChatConfig.template_http + RedisCache.get(WeChatConfig.ACCESS_TOKEN), JSONObject.toJSONString(wechatTemplateMsg));
            return buildFailureJson(StatusConstant.SUCCESS_CODE,"提醒成功");
        } catch (InterfaceCommonException e){
            return buildFailureJson(e.getErrorCode(),e.getMessage());
        } catch (Exception e){
            return buildFailureJson(StatusConstant.Fail_CODE,"失败");
        }
    }


    /**
     * 订单详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/info",method = RequestMethod.POST)
    public ViewData info(Integer id){
        try {
            User user = LoginHelper.getCurrentUser();
            if (null == user) {
                return buildFailureJson(StatusConstant.NOTLOGIN,"未登录");
            }
            Order order = orderService.info(id);
            if (null == order) {
                return buildFailureJson(StatusConstant.ORDER_INVALID,"借款人已取消借款");
            }
            return buildSuccessJson(StatusConstant.SUCCESS_CODE,"获取成功",order);
        } catch (InterfaceCommonException e){
            return buildFailureJson(e.getErrorCode(),e.getMessage());
        } catch (Exception e){
            return buildFailureJson(StatusConstant.Fail_CODE,"获取失败");
        }
    }


    /**
     * 订单详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public ViewData delete(Integer id){
        try {
            User user = LoginHelper.getCurrentUser();
            if (null == user) {
                return buildFailureJson(StatusConstant.NOTLOGIN,"未登录");
            }
            orderService.delete(id);
            return buildSuccessCodeJson(StatusConstant.SUCCESS_CODE,"取消成功");
        } catch (InterfaceCommonException e){
            return buildFailureJson(e.getErrorCode(),e.getMessage());
        } catch (Exception e){
            return buildFailureJson(StatusConstant.Fail_CODE,"取消失败");
        }
    }

    /**
     * 订单列表
     * @param type 0：查看借入 1：借出  null:全部
     * @param userName 用户名
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public ViewData list(Integer type,String userName){
        try {
            User user = LoginHelper.getCurrentUser();
            if (null == user) {
                return buildFailureJson(StatusConstant.NOTLOGIN,"未登录");
            }
            Map<String , Object> map = new HashMap<>();
            map.put("type",type);
            map.put("userName",userName);
            map.put("userId",user.getId());
            return buildSuccessJson(StatusConstant.SUCCESS_CODE,"获取成功",orderService.listForWechat(map));
        } catch (InterfaceCommonException e){
            return buildFailureJson(e.getErrorCode(),e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return buildFailureJson(StatusConstant.Fail_CODE,"获取失败");
        }
    }

}
