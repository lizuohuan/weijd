package com.magic.weijd.service;

import com.magic.weijd.entity.Order;
import com.magic.weijd.entity.PageArgs;
import com.magic.weijd.entity.PageList;
import com.magic.weijd.entity.User;
import com.magic.weijd.enums.Common;
import com.magic.weijd.exception.InterfaceCommonException;
import com.magic.weijd.mapper.IUserMapper;
import com.magic.weijd.util.IdCardValidator;
import com.magic.weijd.util.JuHeUtil;
import com.magic.weijd.util.StatusConstant;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 用户
 * @author lzh
 * @create 2017/9/11 18:36
 */
@Service("UserService")
public class UserService {

    @Resource
    private IUserMapper userMapper;

    /**
     * 登录
     *
     * @param phone  手机号
     * @param code  手机验证码
     * @return
     * @throws Exception
     */
    public User login(String phone, String code) throws Exception {
        User user = userMapper.queryUserByPhone(phone);
        if (null == user) {
            throw new InterfaceCommonException(StatusConstant.Fail_CODE, "用户不存在");
        }
        if (Common.YES.ordinal() != user.getIsValid()) {
            throw new InterfaceCommonException(StatusConstant.Fail_CODE, "帐号被封");
        }
        return user;
    }

    /**
     * 根据手机号获取用户详情
     * @param phone
     * @return
     */
    public User queryUserByPhone(String phone) {
        return userMapper.queryUserByPhone(phone);
    }

    /**
     * 根据id获取用户详情
     * @param id
     * @return
     */
    public User info(Integer id) {
        return userMapper.info(id);
    }

    /**
     * 根据openId获取用户信息
     * @param openId
     * @return
     */
    public User findByWxOpenId(String openId) {
        return userMapper.findByWxOpenId(openId);
    }


    /**
     * 新增用户
     *
     * @param user
     * @throws Exception
     */
    public User save(User user) throws Exception {
        User sqlUser = userMapper.queryUserByPhone(user.getPhone());
        if (null != sqlUser) {
            throw new InterfaceCommonException(StatusConstant.OBJECT_EXIST, "该手机号已被注册");
        }
        if (null != user.getIdCard()) {
            if (!IdCardValidator.isValidatedAllIdCard(user.getIdCard())) {
                throw new InterfaceCommonException(StatusConstant.Fail_CODE, "改身份证不合法");
            }
            sqlUser = userMapper.findByIdCard(user.getIdCard());
            if (null != sqlUser) {
                throw new InterfaceCommonException(StatusConstant.OBJECT_EXIST, "该身份证号已存在");
            }
            user.setAge(new IdCardValidator.IdCardInfoExtractor(user.getIdCard()).getAge());
        }
        userMapper.save(user);
        return user;
    }

    /**
     * 更新不为空的字段
     *
     * @param user
     * @throws Exception
     */
    public void update(User user) throws Exception {
        if (null != user.getIdCard()) {
            if (!IdCardValidator.isValidatedAllIdCard(user.getIdCard())) {
                throw new InterfaceCommonException(StatusConstant.Fail_CODE, "该身份证不合法");
            }
            if (!JuHeUtil.verifyIdCard(user.getUserName(), user.getIdCard())) {
                throw new InterfaceCommonException(StatusConstant.Fail_CODE, "该身份证和姓名不匹配");
            }

            User sqlUser = userMapper.findByIdCard(user.getIdCard());
            if ( null != sqlUser && !user.getId().equals(sqlUser.getId()) ) {
                throw new InterfaceCommonException(StatusConstant.OBJECT_EXIST, "该身份证号已存在");
            }

            user.setAge(new IdCardValidator.IdCardInfoExtractor(user.getIdCard()).getAge());
        }
        userMapper.update(user);
    }

    /**
     * 更新所有字段
     *
     * @param user
     * @throws Exception
     */
    public void updateAll(User user) throws Exception {
        userMapper.updateAll(user);
    }

    /**
     * 查询个人信用
     * @param userName
     * @param idCard
     * @return
     */
    public User findUserCredit(String userName , String idCard) {
        User userCredit = userMapper.findUserCredit(userName, idCard);
        sortOrder(userCredit);
        return userCredit;
    }

    private void sortOrder(User userCredit) {
        if(null != userCredit && null != userCredit.getRepayOrders()){
            // 拆分
            List<Order> finished = new ArrayList<>(); // 已经还了的
            List<Order> other = new ArrayList<>(); // 逾期 和 没到还款时间的
            for (Order order : userCredit.getRepayOrders()) {
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
                    if(o1.getRemainDays() > o2.getRemainDays()){
                        return 1;
                    }
                    else if(o1.getRemainDays() < o2.getRemainDays()){
                        return -1;
                    }
                    return 0;
                }
            });
            other.addAll(finished);
            userCredit.setRepayOrders(other);
        }
    }

    /**
     * 查询个人信用 根据id
     * @param id
     * @return
     */
    public User findUserCreditById(Integer id) {
        User userCreditById = userMapper.findUserCreditById(id);
        sortOrder(userCreditById);
        return userCreditById;
    }


    /**
     * 统计 逾期次数 待还金额 借入总金额
     * @param userId
     * @return
     */
    public User statistics(Integer userId,Integer type) {
        return userMapper.statistics(userId,type);
    }

    /**
     * 后台页面 分页获取用户
     *
     * @param pageArgs    分页属性
     * @param phone       手机号
     * @param age         年龄
     * @param idCard      身份证号
     * @param userName    姓名
     * @return
     */
    public PageList<User> listForAdmin(PageArgs pageArgs , String phone , Integer age,
                                       String idCard , String userName,Integer isFromUser) {
        PageList<User> pageList = new PageList<User>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("phone", phone);
        map.put("age", age);
        map.put("idCard", idCard);
        map.put("userName", userName);
        map.put("isFromUser", isFromUser);
        int count = userMapper.listForAdminCount(map);
        if (count > 0) {
            map.put("pageArgs", pageArgs);
            pageList.setList(userMapper.listForAdmin(map));
        }
        pageList.setTotalSize(count);
        return pageList;
    }


    /**
     * 后台页面 通过各种条件 查询统计用户收支集合
     *
     * @param pageArgs    分页属性
     * @param phone       手机号
     * @param age         年龄
     * @param idCard      身份证号
     * @param userName    姓名
     * @return
     */
    public PageList<User> statisticsListForAdmin(PageArgs pageArgs , String phone , Integer age,
                                                 String idCard , String userName,Date startTime , Date endTime) {
        PageList<User> pageList = new PageList<User>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("phone", phone);
        map.put("age", age);
        map.put("idCard", idCard);
        map.put("userName", userName);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        int count = userMapper.statisticsListCount(map);
        if (count > 0) {
            map.put("pageArgs", pageArgs);
            pageList.setList(userMapper.statisticsListForAdmin(map));
        }
        pageList.setTotalSize(count);
        return pageList;
    }

}
