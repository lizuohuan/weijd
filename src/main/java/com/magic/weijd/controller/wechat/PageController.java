package com.magic.weijd.controller.wechat;/**
 * Created by admin on 2017/9/7.
 */

import com.magic.weijd.entity.Order;
import com.magic.weijd.entity.User;
import com.magic.weijd.service.OrderService;
import com.magic.weijd.util.LoginHelper;
import com.magic.weijd.util.SpringUtil;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;


/**
 * 页面中心控制器
 *
 * @author lzh
 * @create 2017-09-07 21:00
 **/
@Controller("WechatPageController")
@RequestMapping("/wechat/page")
@EnableAutoConfiguration
public class PageController {

    /**首页**/
    @RequestMapping("/index")
    public String index(){ return "/wechat/index"; }

    /**联系我们**/
    @RequestMapping("/contactUs")
    public String contactUs(){ return "/wechat/contactUs"; }

    /**意见反馈**/
    @RequestMapping("/feedback")
    public String feedback(){ return "/wechat/feedback"; }

    /**设置**/
    @RequestMapping("/setting")
    public String setting(){ return "/wechat/setting"; }

    /**我的名片**/
    @RequestMapping("/myCard")
    public String myCard(){ return "/wechat/myCard"; }

    /**个人资料**/
    @RequestMapping("/personData")
    public String personData(Model model){ model.addAttribute("orderId", ""); return "/wechat/personData"; }

    /**补全资料**/
    @RequestMapping("/complementData")
    public String complementData(){ return "/wechat/complementData"; }

    /**注册**/
    @RequestMapping("/register")
    public String register(){ return "/wechat/register"; }

    /**修改支付密码**/
    @RequestMapping("/updatePassword")
    public String updatePassword(){ return "/wechat/updatePassword"; }

    /**换绑手机号**/
    @RequestMapping("/updatePhone")
    public String updatePhone(){ return "/wechat/updatePhone"; }

    /**上传视频**/
    @RequestMapping("/uploadingVideo")
    public String uploadingVideo(){ return "/wechat/uploadingVideo"; }

    /**我的借入**/
    @RequestMapping("/myBorrow")
    public String myBorrow(){ return "/wechat/myBorrow"; }

    /**我的借出**/
    @RequestMapping("/myBorrowOut")
    public String myBorrowOut(){ return "/wechat/myBorrowOut"; }

    /**查信用**/
    @RequestMapping("/findCredit")
    public String findCredit(){ return "/wechat/findCredit"; }

    /**个人信用**/
    @RequestMapping("/personCredit")
    public String personCredit(){ return "/wechat/personCredit"; }

    /**修改支付密码**/
    @RequestMapping("/forgetPassword")
    public String forgetPassword(){ return "/wechat/forgetPassword"; }

    /**借款明细**/
    @RequestMapping(value = "/borrowingDetail/{orderId}/**" ,method = RequestMethod.GET)
    public String borrowingDetail(@PathVariable("orderId") Integer orderId,
                                  String videoUrl,Model model) throws Exception {
        User user = LoginHelper.getCurrentUser();
        model.addAttribute("orderId",orderId);
        if (null != user && null == user.getIdCard()) {
            return "/wechat/personData";
        }

        if (null == orderId) {
            return "/wechat/index";
        }

        OrderService orderService = (OrderService) SpringUtil.getBean("OrderService");
        Order order = orderService.info(orderId);

        if (order.getStatus() == 0 || order.getStatus() == 1) {
            if (order.getType() == 1) {
                if (order.getIsUploadVideo() == 1 && null == order.getVideoUrl() && null == videoUrl
                        && null != user && !user.getId().equals(order.getFromUserId()) && user.getId().equals(order.getToUserId())) {
                    return "redirect:/wechat/page/uploadingVideo?orderId=" + orderId + "&type=" + order.getType();
                }
            } else {
                if (order.getIsUploadVideo() == 1 && null == order.getVideoUrl() && null == videoUrl
                        && null != user && !user.getId().equals(order.getFromUserId())
                        && (user.getId().equals(order.getToUserId()) || null == order.getToUserId())) {
                    return "redirect:/wechat/page/uploadingVideo?orderId=" + orderId + "&type=" + order.getType();
                }
            }
        }



        return "/wechat/borrowingDetail";
    }

    /**申请借款**/
    @RequestMapping("/loanApplication")
    public String loanApplication(){ return "/wechat/loanApplication"; }

    /**申请延期**/
    @RequestMapping("/fileAnExtension")
    public String fileAnExtension(){ return "/wechat/fileAnExtension"; }

    /**注册协议**/
    @RequestMapping("/agreement")
    public String agreement(){ return "/wechat/agreement"; }

    /**借款协议**/
    @RequestMapping("/borrowMoneyAgreement")
    public String borrowMoneyAgreement(){ return "/wechat/borrowMoneyAgreement"; }

    /**电子借条**/
    @RequestMapping("/electronicIou")
    public String electronicIou(){ return "/wechat/electronicIou"; }
    
}
