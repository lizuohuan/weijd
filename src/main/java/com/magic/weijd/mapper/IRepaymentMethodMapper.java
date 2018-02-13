package com.magic.weijd.mapper;

import com.magic.weijd.entity.RepaymentMethod;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 还款方式
 * @author lzh
 * @create 2017/9/13 9:32
 */
@Mapper
public interface IRepaymentMethodMapper {

    /**
     * 添加
     * @param repaymentMethod
     */
    void save(RepaymentMethod repaymentMethod);

    /**
     * 更新不为空的字段
     * @param repaymentMethod
     */
    void update(RepaymentMethod repaymentMethod);

    /**
     * 获取所有利率
     * @return
     */
    List<RepaymentMethod> list();

}
