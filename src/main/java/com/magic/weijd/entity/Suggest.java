package com.magic.weijd.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 意见反馈
 * Created by Eric Xie on 2017/7/13 0013.
 */
public class Suggest implements Serializable {

    private Integer id;

    private String content;

    private String userName;

    private String phone;

    /** 反馈图片url 逗号隔开 */
    private String imgUrl;

    private Date createTime;


    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getCreateTime() {
        if (null == createTime) {
            return new Date();
        }
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /** 获取 反馈图片url 逗号隔开 */
    public String getImgUrl() {
        return this.imgUrl;
    }

    /** 设置 反馈图片url 逗号隔开 */
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
