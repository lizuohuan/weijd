package com.magic.weijd.controller.wechat;

import com.magic.weijd.controller.BaseController;
import com.magic.weijd.entity.Interest;
import com.magic.weijd.exception.InterfaceCommonException;
import com.magic.weijd.service.InterestService;
import com.magic.weijd.util.StatusConstant;
import com.magic.weijd.util.ViewData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 年利率
 * @author lzh
 * @create 2017/9/12 21:37
 */
@RestController("/WechatInterestController")
@RequestMapping("/wechat/Interest")
public class InterestController extends BaseController {

    @Resource
    private InterestService interestService;

    /**
     * 年利率 列表
     * @return
     */
    @RequestMapping(value = "/list")
    public ViewData list(){
        try {
            return buildSuccessJson(StatusConstant.SUCCESS_CODE, "操作成功",interestService.list());
        }catch (InterfaceCommonException e){
            return buildFailureJson(e.getErrorCode(),e.getMessage());
        }catch (Exception e){
            return buildFailureJson(StatusConstant.Fail_CODE,"操作失败");
        }

    }

}
