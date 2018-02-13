package com.magic.weijd.entity;

import java.io.Serializable;

/**
 * 还款方式
 * @author lzh
 * @create 2017/9/12 20:22
 */
public class RepaymentMethod implements Serializable {


    private Integer id;

    /** 方式名 */
    private String name;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /** 获取 方式名 */
    public String getName() {
        return this.name;
    }

    /** 设置 方式名 */
    public void setName(String name) {
        this.name = name;
    }
}
