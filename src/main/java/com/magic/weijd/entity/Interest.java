package com.magic.weijd.entity;

import java.io.Serializable;

/**
 * 年利率实体
 * @author lzh
 * @create 2017/9/12 20:21
 */
public class Interest implements Serializable {

    private Integer id;

    /** 年利率 */
    private Integer interest;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /** 获取 年利率 */
    public Integer getInterest() {
        return this.interest;
    }

    /** 设置 年利率 */
    public void setInterest(Integer interest) {
        this.interest = interest;
    }
}
