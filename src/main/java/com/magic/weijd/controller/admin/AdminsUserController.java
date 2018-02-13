package com.magic.weijd.controller.admin;

import com.magic.weijd.controller.BaseController;
import com.magic.weijd.entity.Admins;
import com.magic.weijd.entity.PageArgs;
import com.magic.weijd.entity.PageList;
import com.magic.weijd.entity.Suggest;
import com.magic.weijd.exception.InterfaceCommonException;
import com.magic.weijd.service.AdminsService;
import com.magic.weijd.util.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 后台管理员
 * @author lzh
 * @create 2017/9/14 22:15
 */
@RestController
@RequestMapping("/admin/admins")
public class AdminsUserController extends BaseController {

    @Resource
    private AdminsService adminsService;


    /**
     * 新增管理员
     * @param account
     * @param pwd
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ViewData login(String account , String pwd , HttpServletRequest request){
        if(CommonUtil.isEmpty(account) || CommonUtil.isEmpty(pwd)){
            return buildFailureJson(StatusConstant.FIELD_NOT_NULL,"字段不能为空");
        }
        try {
            Admins admins = adminsService.login(account, pwd);
            request.getSession().setAttribute(LoginHelper.SESSION_USER_ADMIN,admins);
            return buildSuccessViewData(StatusConstant.SUCCESS_CODE,"登录成功",admins);
        } catch (InterfaceCommonException e) {
            logger.error(e.getMessage(),e);
            return buildFailureJson(StatusConstant.Fail_CODE,"登录失败");
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return buildFailureJson(StatusConstant.Fail_CODE,"登录失败");
        }

    }

    @RequestMapping("/logout")
    public ViewData logout(){
        LoginHelper.clearToken(null);
        return buildSuccessCodeJson(StatusConstant.SUCCESS_CODE,"操作成功");

    }

    /**
     * 新增管理员
     * @param admins
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public ViewData save(Admins admins){
        if(CommonUtil.isEmpty(admins.getAccount()) || CommonUtil.isEmpty(admins.getPwd()) ||
                CommonUtil.isEmpty(admins.getUserName())){
            return buildFailureJson(StatusConstant.FIELD_NOT_NULL,"字段不能为空");
        }
        try {
            adminsService.save(admins);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return buildFailureJson(StatusConstant.Fail_CODE,"新增失败");
        }
        return buildSuccessCodeJson(StatusConstant.SUCCESS_CODE,"新增成功");
    }


    /**
     * 修改管理员
     * @param admins
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public ViewData update(Admins admins){
        if(CommonUtil.isEmpty(admins.getId())){
            return buildFailureJson(StatusConstant.FIELD_NOT_NULL,"字段不能为空");
        }
        try {
            adminsService.update(admins);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return buildFailureJson(StatusConstant.Fail_CODE,"修改失败");
        }
        return buildSuccessCodeJson(StatusConstant.SUCCESS_CODE,"修改成功");
    }


    /**
     * 修改管理员密码
     * @param oldPwd
     * @param newPwd
     * @return
     */
    @RequestMapping(value = "/updatePwd",method = RequestMethod.POST)
    public ViewData updatePwd(String oldPwd ,String newPwd){

        Admins admins1 = LoginHelper.getCurrentAdmin();



        if(CommonUtil.isEmpty(oldPwd) || CommonUtil.isEmpty(newPwd)){
            return buildFailureJson(StatusConstant.FIELD_NOT_NULL,"字段不能为空");
        }
        try {
            Admins admins = adminsService.info(admins1.getId());
            if (!admins.getPwd().equals(oldPwd)) {
                return buildFailureJson(StatusConstant.Fail_CODE,"原始密码错误");
            }
            admins.setPwd(newPwd);
            adminsService.update(admins);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return buildFailureJson(StatusConstant.Fail_CODE,"修改失败");
        }
        return buildSuccessCodeJson(StatusConstant.SUCCESS_CODE,"修改成功");
    }

    /**
     * 后台页面 分页获取管理员
     * @param pageArgs 分页属性
     * @param userName 管理员姓名
     * @return
     */
    @RequestMapping("/list")
    public ViewDataPage list(PageArgs pageArgs , String userName,String account) {
        try {
            PageList pageList = adminsService.listForAdmin(pageArgs, userName ,account);
            return buildSuccessViewDataPage(StatusConstant.SUCCESS_CODE, "获取成功",
                    pageList.getTotalSize(), pageList.getList());
        } catch (Exception e) {
            logger.error("服务器超时，获取失败", e);
            return buildFailureJsonPage(StatusConstant.Fail_CODE, "服务器超时，获取失败");
        }
    }
}
