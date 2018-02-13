package com.magic.weijd.mapper;

import com.magic.weijd.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 用户接口
 * @author lzh
 * @create 2017/9/11 9:01
 */
@Mapper
public interface IUserMapper {

    /**
     * 通过电话号码 查询用户基础信息
     * @param phone
     * @return
     */
    User queryUserByPhone(@Param("phone") String phone);

    /**
     * 通过身份证 查询用户基础信息
     * @param idCard
     * @return
     */
    User findByIdCard(@Param("idCard") String idCard);

    /**
     * 通过openId 查询用户基础信息
     * @param openId
     * @return
     */
    User findByWxOpenId(@Param("openId") String openId);

    /**
     * 通过ID 查询用户 详情 包含公司名  地区
     * @param id
     * @return
     */
    User info(@Param("id") Integer id);

    /**
     * 添加用户
     * @param user
     */
    void save(User user);

    /**
     * 更新不为空的字段
     * @param user
     */
    void update(User user);

    /**
     * 更新所有字段
     * @param user
     */
    void updateAll(User user);

    /**
     * 查询个人信用
     * @param userName
     * @param idCard
     * @return
     */
    User findUserCredit(@Param("userName") String userName , @Param("idCard") String idCard);

    /**
     * 查询个人信用 根据id
     * @param id
     * @return
     */
    User findUserCreditById(@Param("id") Integer id);

    /**
     * 统计 逾期次数 待还金额 借入总金额
     * @param userId
     * @return
     */
    User statistics(@Param("userId") Integer userId,@Param("type") Integer type);


    /**
     * 后台页面 分页 通过各种条件 查询用户集合
     * @param map map
     * @return
     */
    List<User> listForAdmin(Map<String , Object> map);

    /**
     * 后台页面 统计 用户数量
     * @param map map
     * @return
     */
    int listForAdminCount(Map<String , Object> map);

    /**
     * 后台页面 分页 通过各种条件 查询统计用户收支集合
     * @param map map
     * @return
     */
    List<User> statisticsListForAdmin(Map<String , Object> map);

    /**
     * 后台页面 统计 查询统计用户收支集合数量
     * @param map map
     * @return
     */
    int statisticsListCount(Map<String , Object> map);
}
