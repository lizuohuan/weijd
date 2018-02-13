package com.magic.weijd.controller.wechat;

import com.magic.weijd.controller.BaseController;
import com.magic.weijd.service.SystemConfigService;
import com.magic.weijd.util.StatusConstant;
import com.magic.weijd.util.ViewData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 系统配置
 * @author lzh
 * @create 2017/12/25 19:08
 */
@RestController("WechatSystemConfigController")
@RequestMapping("/wechat/systemConfig")
public class SystemConfigController extends BaseController {

    @Resource
    private SystemConfigService systemConfigService;


    @RequestMapping("/info")
    public ViewData info() {
        try {
            return buildSuccessJson(StatusConstant.SUCCESS_CODE,"获取成功",systemConfigService.info());
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("服务器超时，获取失败",StatusConstant.Fail_CODE);
            return buildFailureJson(StatusConstant.Fail_CODE,"服务器超时，获取失败");
        }
    }

}
