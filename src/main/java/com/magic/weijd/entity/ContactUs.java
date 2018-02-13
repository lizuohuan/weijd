package com.magic.weijd.entity;

import java.io.Serializable;

/**
 * 联系我们
 * @author lzh
 * @create 2017/9/14 16:43
 */
public class ContactUs implements Serializable {


    private Integer id;

    /** 客服电话 */
    private String phone;

    /** 客服QQ */
    private String qq;

    /** 工作时间 */
    private String jobTime;

    /** 节假日是否除外 0：否  1：是 */
    private Integer isHolidaysAndFestivalsExcept;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /** 获取 客服电话 */
    public String getPhone() {
        return this.phone;
    }

    /** 设置 客服电话 */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /** 获取 客服QQ */
    public String getQq() {
        return this.qq;
    }

    /** 设置 客服QQ */
    public void setQq(String qq) {
        this.qq = qq;
    }

    /** 获取 工作时间 */
    public String getJobTime() {
        return this.jobTime;
    }

    /** 设置 工作时间 */
    public void setJobTime(String jobTime) {
        this.jobTime = jobTime;
    }

    /** 获取 节假日是否除外 0：否  1：是 */
    public Integer getIsHolidaysAndFestivalsExcept() {
        return this.isHolidaysAndFestivalsExcept;
    }

    /** 设置 节假日是否除外 0：否  1：是 */
    public void setIsHolidaysAndFestivalsExcept(Integer isHolidaysAndFestivalsExcept) {
        this.isHolidaysAndFestivalsExcept = isHolidaysAndFestivalsExcept;
    }
}
