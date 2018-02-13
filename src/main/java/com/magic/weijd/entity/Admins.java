package com.magic.weijd.entity;

import java.io.Serializable;

/**
 * 后台用户
 * @author lzh
 * @create 2017/9/14 17:47
 */
public class Admins implements Serializable {


    private Integer id;

    /** 账号 */
    private String account;

    /** 密码 */
    private String pwd;

    /** 用户名 */
    private String userName;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /** 获取 账号 */
    public String getAccount() {
        return this.account;
    }

    /** 设置 账号 */
    public void setAccount(String account) {
        this.account = account;
    }

    /** 获取 密码 */
    public String getPwd() {
        return this.pwd;
    }

    /** 设置 密码 */
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    /** 获取 用户名 */
    public String getUserName() {
        return this.userName;
    }

    /** 设置 用户名 */
    public void setUserName(String userName) {
        this.userName = userName;
    }
}
