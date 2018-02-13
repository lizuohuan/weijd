package com.magic.weijd.controller.wechat;

import com.magic.weijd.controller.BaseController;
import com.magic.weijd.service.ContactUsService;
import com.magic.weijd.util.StatusConstant;
import com.magic.weijd.util.ViewData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author lzh
 * @create 2017/9/14 17:35
 */
@RestController("WchatContactUsController")
@RequestMapping("/wechat/contactUs")
public class ContactUsController extends BaseController {

    @Resource
    private ContactUsService contactUsService;



    /**
     * 联系我们
     * @return
     */
    @RequestMapping("/info")
    public ViewData info(){
        try {
            return buildSuccessViewData(StatusConstant.SUCCESS_CODE,"获取成功 ",contactUsService.info());
        } catch (Exception e) {
            logger.error("服务器超时，获取失败",e);
            return buildFailureJson(StatusConstant.Fail_CODE,"服务器超时，获取失败");
        }
    }

}
