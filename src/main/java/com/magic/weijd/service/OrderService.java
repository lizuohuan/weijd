package com.magic.weijd.service;

import com.magic.weijd.entity.*;
import com.magic.weijd.exception.InterfaceCommonException;
import com.magic.weijd.mapper.IOrderMapper;
import com.magic.weijd.mapper.ISystemConfigMapper;
import com.magic.weijd.mapper.IUserMapper;
import com.magic.weijd.util.*;
import org.apache.commons.collections.functors.IfClosure;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 订单
 * @author lzh
 * @create 2017/9/12 16:52
 */
@Service("OrderService")
public class OrderService {

    @Resource
    private IOrderMapper orderMapper;
    @Resource
    private IUserMapper userMapper;
    @Resource
    private ISystemConfigMapper systemConfigMapper;

    /**
     * 生成订单
     * @param order
     * @throws Exception
     */
    public void save(Order order) throws Exception {
        SystemConfig config = systemConfigMapper.info();
        //设置服务费
        order.setServiceFee(config.getServiceFee());
        if (config.getServiceFee() <= 0) {
            order.setStatus(1);
            order.setPayStatus(1);
        }
        orderMapper.save(order);
    }

    /**
     * 更新订单信息（状态）
     * @param order
     * @param userId
     * @param isAdmin 是否是管理员操作  0：否  1：是
     * @throws Exception
     */
    @Transactional
    public void update(Order order,Integer userId,String pwd,Integer isAdmin) {

        Order order1 = orderMapper.info(order.getId());
        if (null == order1) {
            throw new InterfaceCommonException(StatusConstant.NO_DATA,"未知的订单");
        }
        System.out.println(order.getId()+"---"+order1.getStatus());
        String msg = "";
        String number = order1.getNumber();
        String statusMsg = "还款提醒";
        String lastMsg = "欢迎使用易信缘";
        String openId = "";
        //出借人
        User fu;
        //借款人
        User tu;
        if (isAdmin == 0) {
            if (null != order1.getFromUserId()) {
                if (order1.getFromUserId().equals(userId) && order.getStatus().equals(0)) {
                    throw new InterfaceCommonException(StatusConstant.Fail_CODE,"对不起，您不能向自己出借");
                }
                fu = userMapper.info(order1.getFromUserId());
            } else {
                fu = userMapper.info(userId);
                order.setFromUserId(userId);
            }

            if (null != order1.getToUserId()) {
                if (order1.getToUserId().equals(userId) && order.getStatus().equals(0)) {
                    throw new InterfaceCommonException(StatusConstant.Fail_CODE,"对不起，您不能向自己借款");
                }
                tu = userMapper.info(order1.getToUserId());
            } else {
                tu = userMapper.info(userId);
                order.setToUserId(userId);
            }
        } else {
            tu = userMapper.info(order1.getToUserId());
            fu = userMapper.info(order1.getFromUserId());
        }


        //type 类型：0：出借 1：借款
        //status借款状态 ：
        //0：借款/出借申请
        //1：确认申请
        //2：拒绝借款申请/拒绝出借申请
        //3：同意借款/同意出借（待还款）
        //4：申请还款
        //5：确认还款（完成）
        //6：拒绝延期还款（此状态不记录数据库，记录状态改为3待还款状态）
        //7：申请延期还款
        //isUploadVideo 是否上传视频 0：否 1：是
        switch (order.getStatus()) {
            case 1 :
                //status
                if (!order1.getStatus().equals(0) && !order1.getStatus().equals(1)) {
                    throw new InterfaceCommonException(StatusConstant.ORDER_STATUS_ABNORMITY,"状态异常");
                } else {
                    if (order1.getIsUploadVideo().equals(1)) {
                        if (order1.getType().equals(0) && (null == order.getVideoUrl() ||  "".equals(order.getVideoUrl()))) {
                            throw new InterfaceCommonException(StatusConstant.FIELD_NOT_NULL,"请上传视频");
                        }
                        if (order1.getType().equals(1) && order1.getToUserId().equals(userId) &&
                                (null == order.getVideoUrl() ||  "".equals(order.getVideoUrl()))) {
                            throw new InterfaceCommonException(StatusConstant.FIELD_NOT_NULL,"请上传视频");
                        }
                    }
                }

                if (order1.getType().equals(0) && null == order1.getToUserId()) {
                    order.setToUserId(userId);
                    openId = fu.getOpenId();
                    msg = "借款人"+tu.getUserName()+"同意向您借款";
                    statusMsg = "申请出借成功";
                }
                if (order1.getType().equals(1) && null == order1.getFromUserId()) {
                    order.setFromUserId(userId);
                    openId = tu.getOpenId();
                    if (order1.getIsUploadVideo().equals(1)) {
                        // TODO: 2017/9/12 要求借款人上传视频推送
                        msg = "出借人"+fu.getUserName()+"需要您上传视频";
                        statusMsg = "请上传视频";
                    } else {
                        msg = "出借人"+fu.getUserName()+"已同意您的借款申请";
                        statusMsg = "申请借款成功";
                    }
                }
                if (tu.getId().equals(userId) && order1.getIsUploadVideo().equals(1) && null != order.getVideoUrl()) {
                    openId = fu.getOpenId();
                    msg = "借款人"+tu.getUserName()+"已上传视频";
                    statusMsg = "待审核视频";
                }
                WxTemplateSend.sendTemplateOrderStatus(msg,number,statusMsg,lastMsg,openId,order1.getId());
                break;
            case 2 :
                if (order1.getStatus().equals(0) || order1.getStatus().equals(1)) {
                    if (order1.getType().equals(0)) {
                        openId = fu.getOpenId();
                        // TODO: 2017/9/12 向出借人发出拒绝推送
                        msg = "您的出借申请借款人"+tu.getUserName()+"拒绝！";
                        statusMsg = "申请出借被拒";
                        order.setToUserId(userId);
                    } else {
                        // TODO: 2017/9/12 向借款人发出拒绝推送
                        openId = tu.getOpenId();
                        msg = "您的借款申请被出借人"+fu.getUserName()+"拒绝！";
                        statusMsg = "申请借款被拒";
                        order.setFromUserId(userId);
                    }
                    lastMsg = "欢迎再次使用易信缘";
                    WxTemplateSend.sendTemplateOrderStatus(msg,number,statusMsg,lastMsg,openId,order1.getId());
                } else {
                    throw new InterfaceCommonException(StatusConstant.ORDER_STATUS_ABNORMITY,"状态异常");
                }
                break;
            case 3 :
                if (order1.getStatus().equals(0) || order1.getStatus().equals(1) || order1.getStatus().equals(4) || order1.getStatus().equals(7)) {
                    if (order1.getStatus().equals(0) || order1.getStatus().equals(1)) {

                        if (isAdmin == 0 && fu.getId().equals(userId) && !fu.getPayPwd().equals(pwd)) {
                            throw new InterfaceCommonException(StatusConstant.Fail_CODE,"支付密码错误");
                        }
                        if (isAdmin == 0 && tu.getId().equals(userId) && !tu.getPayPwd().equals(pwd)) {
                            throw new InterfaceCommonException(StatusConstant.Fail_CODE,"支付密码错误");
                        }
                        // TODO: 2017/9/12  如果为上一个状态为1 同意申请借款
                        msg = "出借人"+fu.getUserName()+"已同意您的借款申请！";
                        statusMsg = "申请借款成功";
                        lastMsg = "请及时还款，逾期会影响个人信用！";
                        WxTemplateSend.sendTemplateOrderStatus(msg,number,statusMsg ,lastMsg,tu.getOpenId(),order1.getId());

                        if (tu.getId().equals(userId)) {
                            // TODO: 2017/9/12  如果为上一个状态为1 同意申请借款
                            msg = "借款人"+tu.getUserName()+"已同意您的出款申请！";
                            statusMsg = "申请出款成功";
                            lastMsg = "";
                            WxTemplateSend.sendTemplateOrderStatus(msg,number,statusMsg ,lastMsg,fu.getOpenId(),order1.getId());
                        }



//                        msg = "借款人"+tu.getUserName()+"已借款！";
//                        statusMsg = "申请借款成功";
//                        lastMsg = "请及时还款，逾期会影响个人信用！";
//                        WxTemplateSend.sendTemplateOrderStatus(msg,number,statusMsg ,lastMsg,tu.getOpenId(),order1.getId());
                    }

                    if (order1.getStatus().equals(4)) {
                        // TODO: 2017/9/12  如果为上一个状态为4 申请还款 此地方推送 拒绝确认还款
                        msg = "您的还款申请被出借人"+fu.getUserName()+"拒绝！";
                        Double price = order1.getPrice();
                        String dayMsg = "剩余"+ String.valueOf(DateTimeHelper.getDifference(new Date() ,order1.getRepaymentTime(), Calendar.DATE)).replace("-","") + "天";
                        String resultMsg = "申请还款被拒";
                        lastMsg = "请联系出借人后再次申请还款！";
                        WxTemplateSend.sendTemplateOrderApplyForResult(msg,price,dayMsg,resultMsg ,lastMsg,tu.getOpenId(),order1.getId());
                    }
                    if (order1.getStatus().equals(7)) {
                        // TODO: 2017/9/12  如果为上一个状态为7 申请延期还款 此地方推送 同意申请延期还款
                        msg = "出借人"+fu.getUserName()+"已通过您的延期申请";
                        lastMsg = "欢迎您使用易信缘！";
                        WxTemplateSend.sendTemplateOrderApplyPassFor(msg,number,tu.getUserName(),lastMsg,tu.getOpenId(),order1.getId());
                        //同意延期 把申请的延期时间 设置为待还款时间
                        order.setRepaymentTime(order1.getPostponeRepaymentTime());
                    }
                } else {
                    System.out.println(order.getId()+"---"+order1.getStatus());
                    throw new InterfaceCommonException(StatusConstant.ORDER_STATUS_ABNORMITY,"状态异常"+order1.getStatus());
                }
                break;
            case 4 :
                if (!order1.getStatus().equals(3) && !order1.getStatus().equals(7)) {
                    throw new InterfaceCommonException(StatusConstant.ORDER_STATUS_ABNORMITY,"状态异常");
                }
                if (order1.getFromUserId().equals(userId)) {
                    throw new InterfaceCommonException(StatusConstant.NOT_AGREE,"你没有权限申请还款");
                }
                if (isAdmin == 0 && !tu.getPayPwd().equals(pwd)) {
                    throw new InterfaceCommonException(StatusConstant.Fail_CODE,"支付密码错误");
                }

                // TODO: 2017/9/12 向出借人发出还款推送
                msg = "您有新的申请需要审核";
                String name = "借款人申请还款";
                String userName = tu.getUserName();
                String type = "申请还款";
                lastMsg = "欢迎使用易信缘";
                WxTemplateSend.sendTemplateOrderRepayApplyFor(msg,name,userName,type,lastMsg,fu.getOpenId(),order1.getId());
                break;
            case 5 :
                if (!order1.getStatus().equals(3) && !order1.getStatus().equals(4) && !order1.getStatus().equals(7)) {
                    throw new InterfaceCommonException(StatusConstant.ORDER_STATUS_ABNORMITY,"状态异常");
                }
                if (isAdmin == 0 && !fu.getPayPwd().equals(pwd)) {
                    throw new InterfaceCommonException(StatusConstant.Fail_CODE,"支付密码错误");
                }
                if (order1.getStatus().equals(4)) {
                    // TODO: 2017/9/12 向借款人推送 出款人已确认收款
                    openId = tu.getOpenId();
                    msg = "您的还款被出借人"+fu.getUserName()+"确认";
                    statusMsg = "确认还款提醒";
                }
                lastMsg = "欢迎再次使用易信缘";
                WxTemplateSend.sendTemplateOrderStatus(msg,number,statusMsg,lastMsg,openId,order1.getId());
                if (null == order.getTrueRepaymentTime()) {
                    order.setTrueRepaymentTime(new Date());
                }
                // TODO: 2017/9/19  设置最终的还款金额
                order.setTrueRepaymentPrice(order1.getInterest() + order1.getPrice());
//                order.setTrueRepaymentPrice(ComputeAccrual.computeAccrualDay(
//                        order1.getPrice(),Double.parseDouble(order1.getInterestName()) / 100,
//                        Timestamp.getDaysNum(order1.getCreateTime(),Calendar.DAY_OF_YEAR),
//                        Integer.parseInt(String.valueOf(
//                                DateTimeHelper.getDifference(
//                                        new Date(),
//                                        order1.getCreateTime(),
//                                        Calendar.DATE
//                                )))));
                //设置信用值
                tu.setCreditPoints(
                        Integer.parseInt(String.valueOf(
                                DateTimeHelper.getDifference(
                                        new Date(),
                                        order1.getCreateTime(),
                                        Calendar.DATE
                                ))) < 0 ?
                                tu.getCreditPoints() - 1 : tu.getCreditPoints() + 1);
                //更新用户
                userMapper.update(tu);
                break;
            case 6 :
                if (!order1.getStatus().equals(7)) {
                    throw new InterfaceCommonException(StatusConstant.ORDER_STATUS_ABNORMITY,"状态异常");
                }
                msg = "您的延期申请被出借人"+fu.getUserName()+"拒绝！";
                Double price = order1.getPrice();
                order1.setPostponeRepaymentTime(null);
                String dayMsg = "剩余"+ String.valueOf(DateTimeHelper.getDifference(new Date() ,order1.getRepaymentTime(), Calendar.DATE)).replace("-","") + "天";
                String resultMsg = "申请延期还款被拒";
                lastMsg = "请联系出借人后再次申请延期还款！";
                WxTemplateSend.sendTemplateOrderApplyForResult(msg,price,dayMsg,resultMsg ,lastMsg,tu.getOpenId(),order1.getId());
                //状态设为待还款状态
                order.setStatus(3);
                // 置空 延期还款时间
                orderMapper.updateNULL(order.getId());
                break;
            case 7 :
                if (!order1.getStatus().equals(3) && !order1.getStatus().equals(4)) {
                    throw new InterfaceCommonException(StatusConstant.ORDER_STATUS_ABNORMITY,"状态异常");
                }
                if (order1.getFromUserId().equals(userId)) {
                    throw new InterfaceCommonException(StatusConstant.NOT_AGREE,"你没有权限申请还款延期");
                }

                if (null == order.getPostponeRepaymentTime()) {
                    throw new InterfaceCommonException(StatusConstant.FIELD_NOT_NULL,"请选择延期还款日期");
                }
                if (order.getPostponeRepaymentTime().compareTo(new Date()) < 1) {
                    throw new InterfaceCommonException(StatusConstant.Fail_CODE,"延期还款日期请大于当前日期");
                }
                // TODO: 2017/9/12 向出借人发出还款延期申请推送
                msg = "您有新的申请需要审核";
                name = "借款人申请延期";
                userName = tu.getUserName();
                type = "申请延期";
                lastMsg = "欢迎使用易信缘";
                WxTemplateSend.sendTemplateOrderDelayApplyFor(msg,name,userName,type,lastMsg,fu.getOpenId(),order1.getId());
                break;
            default:
                throw new InterfaceCommonException(StatusConstant.ORDER_STATUS_ABNORMITY,"状态异常");
        }
        orderMapper.update(order);
    }


    /**
     * 删除订单
     * @param id
     * @throws Exception
     */
    public void delete(Integer id) throws Exception {
        orderMapper.delete(id);
    }

    /**
     * 订单详情
     * @param id
     * @throws Exception
     */
    public Order info(Integer id) throws Exception {
         return orderMapper.info(id);
    }

    /**
     * 订单列表 微信端使用
     * @param map
     * @return
     */
    public List<Order> listForWechat(Map<String ,Object> map){
        List<Order> orders = orderMapper.listForWechat(map);
        if(null != orders && orders.size() > 0){
            // 拆分
            List<Order> finished = new ArrayList<>(); // 已经还了的
            List<Order> other = new ArrayList<>(); // 逾期 和 没到还款时间的
            for (Order order : orders) {
                if(null != order.getStatus() && order.getStatus() == 5){
                    finished.add(order);
                }else{
                    other.add(order);
                }
            }
            // 排序
            Collections.sort(finished, new Comparator<Order>() {
                @Override
                public int compare(Order o1, Order o2) {
                    if(o1.getTrueRepaymentTime().getTime() > o2.getTrueRepaymentTime().getTime()){
                        return 1;
                    }else if(o1.getTrueRepaymentTime().getTime() < o2.getTrueRepaymentTime().getTime()){
                        return -1;
                    }
                    return 0;
                }
            });
            Collections.sort(other, new Comparator<Order>() {
                @Override
                public int compare(Order o1, Order o2) {
                    if (o1.getRemainDays() >= 0 && o2.getRemainDays() >= 0) {
                        if(o1.getRemainDays() > o2.getRemainDays()){
                            return 1;
                        }
                        else if(o1.getRemainDays() < o2.getRemainDays()){
                            return -1;
                        }
                    } else {
                        if(o1.getRemainDays() < o2.getRemainDays()){
                            return 1;
                        }
                        else if(o1.getRemainDays() > o2.getRemainDays()){
                            return -1;
                        }
                    }


                    return 0;
                }
            });
            other.addAll(finished);
            orders = other;
        }
        return orders;
    }

    /**
     * 后台页面 分页获取订单
     * @param pageArgs      分页工具
     * @param userName      用户名
     * @param idCard        身份证号
     * @param phone         手机号
     * @param age           年龄
     * @param priceMin      最小借款金额
     * @param priceMax      最大借款金额
     * @param startTimeC    最早借款时间
     * @param endTimeC      最晚借款时间
     * @param startTimeR    最早预期还款时间
     * @param endTimeR      最晚预期还款时间
     * @param startTimeT    最早真实还款时间
     * @param endTimeT      最晚真实还款时间
     * @return
     */
    public PageList<Order> listForAdmin(PageArgs pageArgs , String userName ,String idCard ,
                                        String phone ,Integer age ,
                                        Double priceMin ,Double priceMax ,
                                        Date startTimeC , Date endTimeC,
                                        Date startTimeR , Date endTimeR,
                                        Date startTimeT , Date endTimeT) {
        PageList<Order> pageList = new PageList<Order>();
        Map<String ,Object> map = new HashMap<String, Object>();
        map.put("userNameF",userName);
        map.put("userNameT",userName);
        map.put("idCardF",idCard);
        map.put("idCardT",idCard);
        map.put("phoneF",phone);
        map.put("phoneT",phone);
        map.put("ageF",age);
        map.put("ageT",age);
        map.put("priceMin",priceMin);
        map.put("priceMax",priceMax);
        map.put("startTimeC",startTimeC);
        map.put("endTimeC",endTimeC);
        map.put("startTimeR",startTimeR);
        map.put("endTimeR",endTimeR);
        map.put("startTimeT",startTimeT);
        map.put("startTimeT",endTimeT);
        int count = orderMapper.listForAdminCount(map);
        if (count > 0) {
            map.put("pageArgs",pageArgs);
            pageList.setList(orderMapper.listForAdmin(map));
        }
        pageList.setTotalSize(count);
        return pageList;
    }

    /**
     * 总统计 总借款/总出款/大学生数量/总人数
     * @return
     */
    public Map<String,Object> statistics() {
        return orderMapper.statistics();
    }


    /**
     * 后台页面 分页获取订单
     * @param pageArgs      分页工具
     * @param userName      用户名
     * @param idCard        身份证号
     * @param phone         手机号
     * @param age           年龄
     * @param startTimeC    最早借款时间
     * @param endTimeC      最晚借款时间
     * @param startTimeR    最早预期还款时间
     * @param endTimeR      最晚预期还款时间
     * @param startTimeT    最早真实还款时间
     * @param endTimeT      最晚真实还款时间
     * @param type          0:借出列表  1：借入列表
     * @param userId        用户id
     * @return
     */
    public PageList<Order> list(PageArgs pageArgs , String userName ,String idCard ,
                                        String phone ,Integer age ,
                                        Date startTimeC , Date endTimeC,
                                        Date startTimeR , Date endTimeR,
                                        Date startTimeT , Date endTimeT, Integer type, Integer userId) {
        PageList<Order> pageList = new PageList<Order>();
        Map<String ,Object> map = new HashMap<String, Object>();
        map.put("userName",userName);
        map.put("idCard",idCard);
        map.put("phone",phone);
        map.put("age",age);
        map.put("startTimeC",startTimeC);
        map.put("endTimeC",endTimeC);
        map.put("startTimeR",startTimeR);
        map.put("endTimeR",endTimeR);
        map.put("startTimeT",startTimeT);
        map.put("startTimeT",endTimeT);
        map.put("type",type);
        map.put("userId",userId);
        int count = orderMapper.listCount(map);
        if (count > 0) {
            map.put("pageArgs",pageArgs);
            pageList.setList(orderMapper.list(map));
        }
        pageList.setTotalSize(count);
        return pageList;
    }



    /**
     * 后台页面 分页获取订单 借条列表
     * @param pageArgs      分页工具
     * @param fromUserName      出款人
     * @param toUserName        借款人
     * @param startTimeC    最早借款时间
     * @param endTimeC      最晚借款时间
     * @param startTimeR    最早预期还款时间
     * @param endTimeR      最晚预期还款时间
     * @param startTimeT    最早真实还款时间
     * @param endTimeT      最晚真实还款时间
     * @param type          0:借出列表  1：借入列表
     * @param userId        用户id
     * @return
     */
    public PageList<Order> list2(PageArgs pageArgs , String fromUserName ,String toUserName ,
                                        Date startTimeC , Date endTimeC,
                                        Date startTimeR , Date endTimeR,
                                        Date startTimeT , Date endTimeT, Integer type, Integer userId) {
        PageList<Order> pageList = new PageList<Order>();
        Map<String ,Object> map = new HashMap<String, Object>();
        map.put("fromUserName",fromUserName);
        map.put("toUserName",toUserName);
        map.put("startTimeC",startTimeC);
        map.put("endTimeC",endTimeC);
        map.put("startTimeR",startTimeR);
        map.put("endTimeR",endTimeR);
        map.put("startTimeT",startTimeT);
        map.put("startTimeT",endTimeT);
        int count = orderMapper.listCount2(map);
        if (count > 0) {
            map.put("pageArgs",pageArgs);
            pageList.setList(orderMapper.list2(map));
        }
        pageList.setTotalSize(count);
        return pageList;
    }


    /**
     * 支付成功后 的业务处理
     * @param order 订单
     * @param outTradeNO 第三方支付订单号
     */
    @Transactional
    public void paySuccess(Order order,String outTradeNO,Integer toUserId){

        Order temp2 = orderMapper.info(order.getId());

        Order temp = new Order();
        temp.setId(order.getId());
        temp.setPayStatus(StatusConstant.YES_PAY);
        temp.setStatus(StatusConstant.ACCOUNT_APPROVED);
        temp.setOutTradeNO(outTradeNO);
        User user = userMapper.info(toUserId);
        if (null != temp2.getFromUserId() && null == temp2.getToUserId()) {
            temp.setToUserId(toUserId);
            temp.setStatus(3);
            update(temp,toUserId,user.getPayPwd(),0);
            return;
        }
        orderMapper.update(temp);
    }
}
