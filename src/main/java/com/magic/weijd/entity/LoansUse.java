package com.magic.weijd.entity;

import java.io.Serializable;

/**
 * 借款用途实体
 * @author lzh
 * @create 2017/9/12 20:20
 */
public class LoansUse implements Serializable {

    private Integer id;

    /** 用途名 */
    private String name;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /** 获取 用途名 */
    public String getName() {
        return this.name;
    }

    /** 设置 用途名 */
    public void setName(String name) {
        this.name = name;
    }
}
