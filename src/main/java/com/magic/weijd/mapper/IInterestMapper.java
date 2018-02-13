package com.magic.weijd.mapper;

import com.magic.weijd.entity.Interest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 年利率
 * @author lzh
 * @create 2017/9/12 20:34
 */
@Mapper
public interface IInterestMapper {


    /**
     * 添加利率
     * @param interest
     */
    void save(Interest interest);

    /**
     * 更新不为空的字段
     * @param interest
     */
    void update(Interest interest);

    /**
     * 获取所有利率
     * @return
     */
    List<Interest> list();
}
