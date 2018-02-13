package com.magic.weijd.controller.admin;/**
 * Created by admin on 2017/9/7.
 */

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 页面中心控制器
 *
 * @author lzh
 * @create 2017-09-07 21:00
 **/
@Controller
@RequestMapping("/admin/page")
@EnableAutoConfiguration
public class PageController {

    /**登录**/
    @RequestMapping("/login")
    public String login(){ return "/admin/login"; }

    /**首页**/
    @RequestMapping("/index")
    public String index(){ return "/admin/index"; }

    /**欢迎页**/
    @RequestMapping("/main")
    public String main(){ return "/admin/main"; }

    /**
     * 意见反馈列表
     * @return
     */
    @RequestMapping("/suggest/list")
    public String suggestList(){return "/admin/suggest/list";}

    /**
     * 联系我们配置
     * @return
     */
    @RequestMapping("/contactUs/edit")
    public String contactUsEdit(){return "/admin/contactUs/edit";}

    /**
     * 系统配置
     * @return
     */
    @RequestMapping("/systemConfig/edit")
    public String systemConfigEdit(){return "/admin/systemConfig/edit";}

    /**
     * 意见反馈列表
     * @return
     */
    @RequestMapping("/user/list")
    public String userList(){return "/admin/user/list";}

    /**
     * 进入用户详情
     * @return
     */
    @RequestMapping("/user/edit")
    public String userUpdate(Integer id , ModelMap modelMap){
        modelMap.addAttribute("id",id);
        return "/admin/user/edit";
    }

    /**
     * 总统计 列表
     * @return
     */
    @RequestMapping("/order/statisticsList")
    public String statisticsList(){return "/admin/order/statisticsList";}
    /**
     * 用户借款统计 列表
     * @return
     */
    @RequestMapping("/order/list")
    public String orderList(ModelMap modelMap,Integer type,Integer userId){
        modelMap.addAttribute("type",type);
        modelMap.addAttribute("userId",userId);
        return "/admin/order/list";
    }

    /**平台用户--修改密码页面*/
    @RequestMapping("/admins/updatePassword")
    public String updatePassword() { return "/admin/admins/updatePassword"; }

    /**平台用户--新增*/
    @RequestMapping("/admins/save")
    public String adminSave() { return "/admin/admins/save"; }

    /**平台用户--列表*/
    @RequestMapping("/admins/list")
    public String adminList() { return "/admin/admins/list"; }

    /**借条列表*/
    @RequestMapping("/record/list")
    public String recordList() { return "/admin/record/list"; }
}
