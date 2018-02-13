package com.magic.weijd.controller.wechat;

import com.magic.weijd.controller.BaseController;
import com.magic.weijd.entity.RepaymentMethod;
import com.magic.weijd.exception.InterfaceCommonException;
import com.magic.weijd.service.RepaymentMethodService;
import com.magic.weijd.util.StatusConstant;
import com.magic.weijd.util.ViewData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 还款方式
 * @author lzh
 * @create 2017/9/13 9:40
 */
@RestController("WechatRepaymentMethodController")
@RequestMapping("/wechat/repaymentMethod")
public class RepaymentMethodController extends BaseController {

    @Resource
    private RepaymentMethodService repaymentMethodService;


    /**
     * 列表
     * @return
     */
    @RequestMapping(value = "/list")
    public ViewData list(){
        try {
            return buildSuccessJson(StatusConstant.SUCCESS_CODE, "操作成功",repaymentMethodService.list());
        }catch (InterfaceCommonException e){
            return buildFailureJson(e.getErrorCode(),e.getMessage());
        }catch (Exception e){
            return buildFailureJson(StatusConstant.Fail_CODE,"操作失败");
        }

    }

}
