package com.magic.weijd.mapper;

import com.magic.weijd.entity.LoansUse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 借款用途
 * @author lzh
 * @create 2017/9/13 9:29
 */
@Mapper
public interface ILoansUseMapper {

    /**
     * 添加
     * @param loansUse
     */
    void save(LoansUse loansUse);

    /**
     * 更新不为空的字段
     * @param loansUse
     */
    void update(LoansUse loansUse);

    /**
     * 获取所有利率
     * @return
     */
    List<LoansUse> list();

}
