package com.magic.weijd.controller;

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
@RestController
@RequestMapping("/Interest")
public class InterestController extends BaseController {

    @Resource
    private InterestService interestService;



    /**
     * 新增年利率
     * @param interest
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public ViewData save(Interest interest){
        try {
            interestService.save(interest);
            return buildFailureJson(StatusConstant.SUCCESS_CODE,"创建成功");
        }catch (InterfaceCommonException e){
            return buildFailureJson(e.getErrorCode(),e.getMessage());
        }catch (Exception e){
            return buildFailureJson(StatusConstant.Fail_CODE,"创建失败");
        }

    }


    /**
     * 年利率
     * @param interest
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public ViewData update(Interest interest){
        try {
            interestService.update(interest);
            return buildFailureJson(StatusConstant.SUCCESS_CODE, "操作成功");
        }catch (InterfaceCommonException e){
            return buildFailureJson(e.getErrorCode(),e.getMessage());
        }catch (Exception e){
            return buildFailureJson(StatusConstant.Fail_CODE,"操作失败");
        }

    }

    /**
     * 年利率 列表
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
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
