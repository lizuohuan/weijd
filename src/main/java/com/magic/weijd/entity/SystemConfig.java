package com.magic.weijd.entity;

import java.io.Serializable;

/**
 * 系统配置
 * @author lzh
 * @create 2017/12/25 19:00
 */
public class SystemConfig implements Serializable {


    private Integer id;

    /** 服务费配置 */
    private Double serviceFee;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /** 获取 服务费配置 */
    public Double getServiceFee() {
        if (null == serviceFee) {
            return 0.0;
        }
        return this.serviceFee;
    }

    /** 设置 服务费配置 */
    public void setServiceFee(Double serviceFee) {
        this.serviceFee = serviceFee;
    }
}
