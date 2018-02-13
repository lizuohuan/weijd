package com.magic.weijd.controller.admin;

import com.magic.weijd.controller.BaseController;
import com.magic.weijd.entity.Order;
import com.magic.weijd.entity.PageArgs;
import com.magic.weijd.entity.PageList;
import com.magic.weijd.entity.User;
import com.magic.weijd.exception.InterfaceCommonException;
import com.magic.weijd.service.OrderService;
import com.magic.weijd.util.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 订单（借款）
 * @author lzh
 * @create 2017/9/12 18:25
 */
@RestController
@RequestMapping("/admin/order")
public class OrderController extends BaseController {

    @Resource
    private OrderService orderService;


//    /**
//     * 后台页面 分页获取订单
//     * @param pageArgs      分页工具
//     * @param userName      用户名
//     * @param idCard        身份证号
//     * @param phone         手机号
//     * @param age           年龄
//     * @param priceMin      最小借款金额
//     * @param priceMax      最大借款金额
//     * @param startTimeCs    最早借款时间
//     * @param endTimeCs      最晚借款时间
//     * @param startTimeRs    最早预期还款时间
//     * @param endTimeRs      最晚预期还款时间
//     * @param startTimeTs    最早真实还款时间
//     * @param endTimeTs      最晚真实还款时间
//     * @return
//     */
//    @RequestMapping("/list")
//    public ViewDataPage list(PageArgs pageArgs , String userName ,String idCard ,
//                             String phone ,Integer age ,
//                             Double priceMin ,Double priceMax ,
//                             String startTimeCs , String endTimeCs,
//                             String startTimeRs , String endTimeRs,
//                             String startTimeTs , String endTimeTs) {
//        try {
//            Date startTimeC = null;
//            Date endTimeC = null;
//            Date startTimeR = null;
//            Date endTimeR = null;
//            Date startTimeT = null;
//            Date endTimeT = null;
//            if (null != startTimeCs && !startTimeCs.equals("")) {
//                startTimeC = Timestamp.parseDate2(startTimeCs,"yyyy-MM-dd HH:mm:ss");
//            }
//            if (null != endTimeCs && !endTimeCs.equals("")) {
//                endTimeC = Timestamp.parseDate2(endTimeCs,"yyyy-MM-dd HH:mm:ss");
//            }
//            if (null != startTimeRs && !startTimeRs.equals("")) {
//                startTimeR = Timestamp.parseDate2(startTimeRs,"yyyy-MM-dd HH:mm:ss");
//            }
//            if (null != endTimeRs && !endTimeRs.equals("")) {
//                endTimeR = Timestamp.parseDate2(endTimeRs,"yyyy-MM-dd HH:mm:ss");
//            }
//            if (null != startTimeTs && !startTimeTs.equals("")) {
//                startTimeT = Timestamp.parseDate2(startTimeTs,"yyyy-MM-dd HH:mm:ss");
//            }
//            if (null != endTimeTs && !endTimeTs.equals("")) {
//                endTimeT = Timestamp.parseDate2(endTimeTs,"yyyy-MM-dd HH:mm:ss");
//            }
//            PageList pageList = orderService.listForAdmin(pageArgs, userName, idCard ,
//                     phone , age , priceMin , priceMax , startTimeC ,  endTimeC,
//                     startTimeR ,  endTimeR, startTimeT ,  endTimeT);
//            return buildSuccessViewDataPage(StatusConstant.SUCCESS_CODE,"获取成功",pageList.getTotalSize(),pageList.getList());
//        } catch (Exception e) {
//            logger.error("服务器超时，获取失败",e);
//            return buildFailureJsonPage(StatusConstant.Fail_CODE,"服务器超时，获取失败");
//        }
//    }

    /**
     * 总统计 总借款/总出款/大学生数量/总人数
     * @return
     */
    @RequestMapping("/statistics")
    public ViewData statistics() {
        try {
            return buildSuccessViewData(StatusConstant.SUCCESS_CODE,"获取成功",orderService.statistics());
        } catch (Exception e) {
            e.printStackTrace();
            return buildFailureJson(StatusConstant.Fail_CODE,"服务器超时，获取失败");
        }
    }


    /**
     * 后台页面 分页获取订单
     * @param pageArgs      分页工具
     * @param userName      用户名
     * @param idCard        身份证号
     * @param phone         手机号
     * @param age           年龄
     * @param startTimeCs    最早借款时间
     * @param endTimeCs      最晚借款时间
     * @param startTimeRs    最早预期还款时间
     * @param endTimeRs      最晚预期还款时间
     * @param startTimeTs    最早真实还款时间
     * @param endTimeTs      最晚真实还款时间
     * @param type           0:借出列表  1：借入列表
     * @param userId         用户id
     * @return
     */
    @RequestMapping("/list")
    public ViewDataPage list(PageArgs pageArgs , String userName ,String idCard ,
                             String phone ,Integer age ,
                             String startTimeCs , String endTimeCs,
                             String startTimeRs , String endTimeRs,
                             String startTimeTs , String endTimeTs,Integer type,Integer userId) {
        try {
            Date startTimeC = null;
            Date endTimeC = null;
            Date startTimeR = null;
            Date endTimeR = null;
            Date startTimeT = null;
            Date endTimeT = null;
            if (null != startTimeCs && !startTimeCs.equals("")) {
                startTimeC = Timestamp.parseDate2(startTimeCs,"yyyy-MM-dd HH:mm:ss");
            }
            if (null != endTimeCs && !endTimeCs.equals("")) {
                endTimeC = Timestamp.parseDate2(endTimeCs,"yyyy-MM-dd HH:mm:ss");
            }
            if (null != startTimeRs && !startTimeRs.equals("")) {
                startTimeR = Timestamp.parseDate2(startTimeRs,"yyyy-MM-dd HH:mm:ss");
            }
            if (null != endTimeRs && !endTimeRs.equals("")) {
                endTimeR = Timestamp.parseDate2(endTimeRs,"yyyy-MM-dd HH:mm:ss");
            }
            if (null != startTimeTs && !startTimeTs.equals("")) {
                startTimeT = Timestamp.parseDate2(startTimeTs,"yyyy-MM-dd HH:mm:ss");
            }
            if (null != endTimeTs && !endTimeTs.equals("")) {
                endTimeT = Timestamp.parseDate2(endTimeTs,"yyyy-MM-dd HH:mm:ss");
            }
            PageList pageList = orderService.list(pageArgs, userName, idCard ,
                    phone , age , startTimeC ,  endTimeC,
                    startTimeR ,  endTimeR, startTimeT ,  endTimeT, type,userId);
            return buildSuccessViewDataPage(StatusConstant.SUCCESS_CODE,"获取成功",pageList.getTotalSize(),pageList.getList());
        } catch (Exception e) {
            logger.error("服务器超时，获取失败",e);
            e.printStackTrace();
            return buildFailureJsonPage(StatusConstant.Fail_CODE,"服务器超时，获取失败");
        }
    }

    /**
     * 后台页面 分页获取订单
     * @param pageArgs      分页工具
     * @param fromUserName      出款人
     * @param toUserName        借款人
     * @param startTimeCs    最早借款时间
     * @param endTimeCs      最晚借款时间
     * @param startTimeRs    最早预期还款时间
     * @param endTimeRs      最晚预期还款时间
     * @param startTimeTs    最早真实还款时间
     * @param endTimeTs      最晚真实还款时间
     * @param type           0:借出列表  1：借入列表
     * @param userId         用户id
     * @return
     */
    @RequestMapping("/list2")
    public ViewDataPage list2(PageArgs pageArgs , String fromUserName ,String toUserName ,
                             String startTimeCs , String endTimeCs,
                             String startTimeRs , String endTimeRs,
                             String startTimeTs , String endTimeTs,Integer type,Integer userId) {
        try {
            Date startTimeC = null;
            Date endTimeC = null;
            Date startTimeR = null;
            Date endTimeR = null;
            Date startTimeT = null;
            Date endTimeT = null;
            if (null != startTimeCs && !startTimeCs.equals("")) {
                startTimeC = Timestamp.parseDate2(startTimeCs,"yyyy-MM-dd HH:mm:ss");
            }
            if (null != endTimeCs && !endTimeCs.equals("")) {
                endTimeC = Timestamp.parseDate2(endTimeCs,"yyyy-MM-dd HH:mm:ss");
            }
            if (null != startTimeRs && !startTimeRs.equals("")) {
                startTimeR = Timestamp.parseDate2(startTimeRs,"yyyy-MM-dd HH:mm:ss");
            }
            if (null != endTimeRs && !endTimeRs.equals("")) {
                endTimeR = Timestamp.parseDate2(endTimeRs,"yyyy-MM-dd HH:mm:ss");
            }
            if (null != startTimeTs && !startTimeTs.equals("")) {
                startTimeT = Timestamp.parseDate2(startTimeTs,"yyyy-MM-dd HH:mm:ss");
            }
            if (null != endTimeTs && !endTimeTs.equals("")) {
                endTimeT = Timestamp.parseDate2(endTimeTs,"yyyy-MM-dd HH:mm:ss");
            }
            PageList pageList = orderService.list2(pageArgs, fromUserName, toUserName , startTimeC ,  endTimeC,
                    startTimeR ,  endTimeR, startTimeT ,  endTimeT, type,userId);
            return buildSuccessViewDataPage(StatusConstant.SUCCESS_CODE,"获取成功",pageList.getTotalSize(),pageList.getList());
        } catch (Exception e) {
            logger.error("服务器超时，获取失败",e);
            e.printStackTrace();
            return buildFailureJsonPage(StatusConstant.Fail_CODE,"服务器超时，获取失败");
        }
    }


    /**
     * 更新订单信息（状态）  后台修改 确认还款
     * @param order {
     *              type 类型：0：出借 1：借款
    status借款状态 ：
    1：确认申请
    2：拒绝借款申请/拒绝出借申请
    3：同意借款/同意出借（待还款）
    4：申请还款
    5：确认还款（完成）
    6：拒绝延期还款（此状态不记录数据库，记录状态改为3待还款状态）
    7：申请延期还款
    isUploadVideo 是否上传视频 0：否 1：是
    }
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public ViewData update(Order order){
        try {
            if (CommonUtil.isEmpty(order.getTrueRepaymentTime())) {
                return buildFailureJson(StatusConstant.SUCCESS_CODE, "操作成功");
            }
            order.setStatus(5);
            orderService.update(order, null,null,1);
            return buildFailureJson(StatusConstant.SUCCESS_CODE, "操作成功");
        }catch (InterfaceCommonException e){
            return buildFailureJson(e.getErrorCode(),e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            return buildFailureJson(StatusConstant.Fail_CODE,"操作失败");
        }

    }

}
