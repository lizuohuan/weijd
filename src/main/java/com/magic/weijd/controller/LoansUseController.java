package com.magic.weijd.controller;

import com.magic.weijd.entity.LoansUse;
import com.magic.weijd.exception.InterfaceCommonException;
import com.magic.weijd.service.LoansUseService;
import com.magic.weijd.util.StatusConstant;
import com.magic.weijd.util.ViewData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 借款用途
 * @author lzh
 * @create 2017/9/13 9:51
 */
@RestController
@RequestMapping("/loansUse")
public class LoansUseController extends BaseController {

    @Resource
    private LoansUseService loansUseService;




    /**
     * 新增
     * @param loansUse
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public ViewData save(LoansUse loansUse){
        try {
            loansUseService.save(loansUse);
            return buildFailureJson(StatusConstant.SUCCESS_CODE,"创建成功 ");
        }catch (InterfaceCommonException e){
            return buildFailureJson(e.getErrorCode(),e.getMessage());
        }catch (Exception e){
            return buildFailureJson(StatusConstant.Fail_CODE,"创建失败 ");
        }

    }


    /**
     * 更新
     * @param loansUse
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public ViewData update(LoansUse loansUse){
        try {
            loansUseService.update(loansUse);
            return buildFailureJson(StatusConstant.SUCCESS_CODE, "操作成功 ");
        }catch (InterfaceCommonException e){
            return buildFailureJson(e.getErrorCode(),e.getMessage());
        }catch (Exception e){
            return buildFailureJson(StatusConstant.Fail_CODE,"操作失败 ");
        }

    }


    /**
     * 列表
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ViewData list(){
        try {
            return buildSuccessJson(StatusConstant.SUCCESS_CODE, "操作成功",loansUseService.list());
        }catch (InterfaceCommonException e){
            return buildFailureJson(e.getErrorCode(),e.getMessage());
        }catch (Exception e){
            return buildFailureJson(StatusConstant.Fail_CODE,"操作失败");
        }

    }
}
