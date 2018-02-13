package com.magic.weijd.mapper;

import com.magic.weijd.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 订单
 */
@Mapper
public interface IOrderMapper {

    /**
     * 删除订单
     * @param id
     * @return
     */
    int delete(Integer id);

    /**
     * 添加订单
     * @param record
     * @return
     */
    int save(Order record);

    /**
     * 订单详情
     * @param id
     * @return
     */
    Order info(Integer id);

    /**
     * 更新订单
     * @param record
     * @return
     */
    int update(Order record);

    /**
     * 置空延期还款时间
     * @param orderId
     * @return
     */
    int updateNULL(@Param("orderId") Integer orderId);

    /**
     * 用户查看订单
     * @param map
     * @return
     */
    List<Order> listForWechat(Map<String ,Object> map);

    /**
     * 后台页面 分页获取订单列表
     * @param map map
     * @return
     */
    List<Order> listForAdmin(Map<String , Object> map);


    /**
     * 后台页面 分页获取订单 条数
     * @param map map
     * @return
     */
    int listForAdminCount(Map<String , Object> map);

    /**
     * 总统计 总借款/总出款/大学生数量/总人数
     * @return
     */
    Map<String,Object> statistics();

    /**
     * 后台页面 分页获取订单列表 type 0:借出列表  1：借入列表
     * @param map map
     * @return
     */
    List<Order> list(Map<String , Object> map);


    /**
     * 后台页面 分页获取订单  type 0:借出列表  1：借入列表 条数
     * @param map map
     * @return
     */
    int listCount(Map<String , Object> map);




    /**
     * 后台页面 分页获取订单列表 借条列表
     * @param map map
     * @return
     */
    List<Order> list2(Map<String , Object> map);


    /**
     * 后台页面 分页获取订单 借条列表
     * @param map map
     * @return
     */
    int listCount2(Map<String , Object> map);

    /**
     * 查询订单集合 由于用户关联查询 统计总待还/总待收
     * @param userId 用户id
     * @param type 0：待收  1：待还
     * @return
     */
    List<Order> nowToBePaidPrice(@Param("userId") Integer userId ,@Param("type") Integer type);
}