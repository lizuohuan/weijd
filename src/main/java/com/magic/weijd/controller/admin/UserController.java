package com.magic.weijd.controller.admin;

import com.magic.weijd.controller.BaseController;
import com.magic.weijd.entity.PageArgs;
import com.magic.weijd.entity.PageList;
import com.magic.weijd.entity.User;
import com.magic.weijd.service.UserService;
import com.magic.weijd.util.StatusConstant;
import com.magic.weijd.util.Timestamp;
import com.magic.weijd.util.ViewData;
import com.magic.weijd.util.ViewDataPage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * User Controller
 * Created by Eric Xie on 2017/7/21 0021.
 */
@RestController
@RequestMapping("/admin/user")
public class UserController extends BaseController {

    @Resource
    private UserService userService;



    /**
     * 后台页面 分页获取用户
     *
     * @param pageArgs    分页属性
     * @param phone       手机号
     * @param age         年龄
     * @param idCard      身份证号
     * @param userName    姓名
     * @param isFromUser  是否为出借人 0：否  1：是  2：申请中  3：拒绝
     * @return
     */
    @RequestMapping("/list")
    public ViewDataPage list(PageArgs pageArgs , String phone , Integer age,
                             String idCard , String userName,Integer isFromUser) {
        try {
            PageList pageList = userService.listForAdmin(pageArgs, phone, age,idCard,userName,isFromUser);
            return buildSuccessViewDataPage(StatusConstant.SUCCESS_CODE,"获取成功",pageList.getTotalSize(),pageList.getList());
        } catch (Exception e) {
            logger.error("服务器超时，获取失败",e);
            return buildFailureJsonPage(StatusConstant.Fail_CODE,"服务器超时，获取失败");
        }
    }

    /**
     * 查询用户的基础信息
     * @param id
     * @return
     */
    @RequestMapping("/info")
    public ViewData info(Integer id){
        try {
            if (null == id) {
                return buildFailureJson(StatusConstant.FIELD_NOT_NULL,"字段不能为空");
            }
            return buildSuccessViewData(StatusConstant.SUCCESS_CODE,"获取成功 ",userService.info(id));
        } catch (Exception e) {
            logger.error("服务器超时，获取失败",e);
            return buildFailureJson(StatusConstant.Fail_CODE,"服务器超时，获取失败");
        }
    }


    /**
     * 后台页面 查询统计用户收支集合
     *
     * @param pageArgs    分页属性
     * @param phone       手机号
     * @param age         年龄
     * @param idCard      身份证号
     * @param userName    姓名
     * @return
     */
    @RequestMapping("/statisticsListForAdmin")
    public ViewDataPage statisticsListForAdmin(PageArgs pageArgs , String phone , Integer age,
                             String idCard , String userName,String startTimes , String endTimes) {
        try {
            Date startTime = null;
            Date endTime = null;
            if (null != startTimes && !startTimes.equals("")) {
                startTime = Timestamp.parseDate2(startTimes,"yyyy-MM-dd HH:mm:ss");
            }
            if (null != endTimes && !endTimes.equals("")) {
                endTime = Timestamp.parseDate2(endTimes,"yyyy-MM-dd HH:mm:ss");
            }
            PageList pageList = userService.statisticsListForAdmin(pageArgs, phone, age,idCard,userName,startTime,endTime);
            return buildSuccessViewDataPage(StatusConstant.SUCCESS_CODE,"获取成功",pageList.getTotalSize(),pageList.getList());
        } catch (Exception e) {
            logger.error("服务器超时，获取失败",e);
            return buildFailureJsonPage(StatusConstant.Fail_CODE,"服务器超时，获取失败");
        }
    }


    /**
     * 更新用户
     * @param user
     * @return
     */
    @RequestMapping("/update")
    public ViewData update(User user){
        try {
            if (null == user.getId()) {
                return buildFailureJson(StatusConstant.FIELD_NOT_NULL,"字段不能为空");
            }
            userService.update(user);
            return buildSuccessCodeJson(StatusConstant.SUCCESS_CODE,"获取成功 ");
        } catch (Exception e) {
            logger.error("服务器超时，获取失败",e);
            return buildFailureJson(StatusConstant.Fail_CODE,"服务器超时，获取失败");
        }
    }


}
