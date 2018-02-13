package com.magic.weijd.entity;

import com.alibaba.fastjson.JSONArray;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

/**
 * 用户实体
 * @author lzh
 * @create 2017/9/8 16:39
 */
public class User implements Serializable {

    private Integer id;

    /** 姓名 */
    private String userName;

    /** 手机号 */
    private String phone;

    /** 身份证号 */
    private String idCard;

    /** 头像 */
    private String avatar;

    /** 身份证正面 */
    private String idCardImg;

    /** QQ号 */
    private String qq;

    /** 微信号 */
    private String wx;

    /** 工作单位 */
    private String job;

    /** 年龄 */
    private Integer age;

    /** 是否是在校 */
    private Integer isAtSchool;

    /** 地区id */
    private Integer cityId;

    /** 城市 */
    private City city;

    /** 家庭地址 */
    private String address;

    /** 父亲姓名 */
    private String fatherName;

    /** 父亲手机号 */
    private String fatherPhone;

    /** 父亲工作地址 */
    private String fatherJobAddress;

    /** 母亲姓名 */
    private String momName;

    /** 母亲手机号 */
    private String momPhone;

    /** 母亲工作地址 */
    private String momJobAddress;

    /** 常用联系人 格式json数组 [{姓名1：'',电话1：''},{姓名2：'',电话2：'',....}] */
    private String personJsonAry;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

    /** 账户是否有效 0：无效 1：有效  缺省值：1 */
    private Integer isValid;

    /**微信用户的openId*/
    private String openId;

    /**支付密码*/
    private String payPwd;

    /** 信用分 */
    private Integer creditPoints;

    /** 借出的总金额 */
    private Double borrowOutTotalPrice;

    /** 借入的总金额 */
    private Double borrowJoinTotalPrice;

    /** 借入的订单集合 */
    private List<Order> repayOrders;

    /** 借出的订单集合 */
    private List<Order> outOrders;

    /** 待还/收的总金额 */
    private Double nowToBePaidPrice;

    /** 逾期次数 */
    private Integer overdueNum;

    /** 借入次数 */
    private Integer joinNum;

    /** 借出次数 */
    private Integer outNum;

    /** 待还总金额  个人信用使用 */
    private Double nowToBePaidOutPrice;

    /** 待收总金额  个人信用使用 */
    private Double nowToBePaidJoinPrice;

    /** 逾期还款总金额 */
    private Double overdueTotalPrice;

    /** 当前逾期且还未归还总金额 */
    private Double overdueNowTotalPrice;

    /** 逾期总次数 */
    private Integer overdueTotalNum;

    /** 是否为出借人 0：否  1：是  2：申请中  3：拒绝 */
    private Integer isFromUser;

    /** 备注 */
    private String remark;



    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /** 获取 姓名 */
    public String getUserName() {
        if (null == userName) {
            return this.phone;
        }
        return this.userName;
    }

    /** 设置 姓名 */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /** 获取 手机号 */
    public String getPhone() {
        return this.phone;
    }

    /** 设置 手机号 */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /** 获取 身份证号 */
    public String getIdCard() {
        return this.idCard;
    }

    /** 设置 身份证号 */
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    /** 获取 头像 */
    public String getAvatar() {
        return this.avatar;
    }

    /** 设置 头像 */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /** 获取 身份证正面 */
    public String getIdCardImg() {
        return this.idCardImg;
    }

    /** 设置 身份证正面 */
    public void setIdCardImg(String idCardImg) {
        this.idCardImg = idCardImg;
    }

    /** 获取 QQ号 */
    public String getQq() {
        return this.qq;
    }

    /** 设置 QQ号 */
    public void setQq(String qq) {
        this.qq = qq;
    }

    /** 获取 微信号 */
    public String getWx() {
        return this.wx;
    }

    /** 设置 微信号 */
    public void setWx(String wx) {
        this.wx = wx;
    }

    /** 获取 工作单位 */
    public String getJob() {
        return this.job;
    }

    /** 设置 工作单位 */
    public void setJob(String job) {
        this.job = job;
    }

    /** 获取 年龄 */
    public Integer getAge() {
        return this.age;
    }

    /** 设置 年龄 */
    public void setAge(Integer age) {
        this.age = age;
    }

    /** 获取 是否是在校 */
    public Integer getIsAtSchool() {
        return this.isAtSchool;
    }

    /** 设置 是否是在校 */
    public void setIsAtSchool(Integer isAtSchool) {
        this.isAtSchool = isAtSchool;
    }

    /** 获取 地区id */
    public Integer getCityId() {
        return this.cityId;
    }

    /** 设置 地区id */
    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    /** 获取 城市 */
    public City getCity() {
        return this.city;
    }

    /** 设置 城市 */
    public void setCity(City city) {
        this.city = city;
    }

    /** 获取 家庭地址 */
    public String getAddress() {
        return this.address;
    }

    /** 设置 家庭地址 */
    public void setAddress(String address) {
        this.address = address;
    }

    /** 获取 父亲姓名 */
    public String getFatherName() {
        return this.fatherName;
    }

    /** 设置 父亲姓名 */
    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    /** 获取 父亲手机号 */
    public String getFatherPhone() {
        return this.fatherPhone;
    }

    /** 设置 父亲手机号 */
    public void setFatherPhone(String fatherPhone) {
        this.fatherPhone = fatherPhone;
    }

    /** 获取 父亲工作地址 */
    public String getFatherJobAddress() {
        return this.fatherJobAddress;
    }

    /** 设置 父亲工作地址 */
    public void setFatherJobAddress(String fatherJobAddress) {
        this.fatherJobAddress = fatherJobAddress;
    }

    /** 获取 母亲姓名 */
    public String getMomName() {
        return this.momName;
    }

    /** 设置 母亲姓名 */
    public void setMomName(String momName) {
        this.momName = momName;
    }

    /** 获取 母亲手机号 */
    public String getMomPhone() {
        return this.momPhone;
    }

    /** 设置 母亲手机号 */
    public void setMomPhone(String momPhone) {
        this.momPhone = momPhone;
    }

    /** 获取 母亲工作地址 */
    public String getMomJobAddress() {
        return this.momJobAddress;
    }

    /** 设置 母亲工作地址 */
    public void setMomJobAddress(String momJobAddress) {
        this.momJobAddress = momJobAddress;
    }

    /** 获取 常用联系人 格式json数组 [{姓名1：'',电话1：''},{姓名2：'',电话2：'',....}] */
    public String getPersonJsonAry() {
        return this.personJsonAry;
    }

    /** 设置 常用联系人 格式json数组 [{姓名1：'',电话1：''},{姓名2：'',电话2：'',....}] */
    public void setPersonJsonAry(String personJsonAry) {
        this.personJsonAry = personJsonAry;
    }

    /** 获取 创建时间 */
    public Date getCreateTime() {
        return this.createTime;
    }

    /** 设置 创建时间 */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /** 获取 更新时间 */
    public Date getUpdateTime() {
        return this.updateTime;
    }

    /** 设置 更新时间 */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /** 获取 账户是否有效 0：无效 1：有效  缺省值：1 */
    public Integer getIsValid() {
        return this.isValid;
    }

    /** 设置 账户是否有效 0：无效 1：有效  缺省值：1 */
    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    /**微信用户的openId*/
    public String getOpenId() {
        return this.openId;
    }

    /**微信用户的openId*/
    public void setOpenId(String openId) {
        this.openId = openId;
    }

    /**支付密码*/
    public String getPayPwd() {
        return this.payPwd;
    }

    /**支付密码*/
    public void setPayPwd(String payPwd) {
        this.payPwd = payPwd;
    }

    /** 获取 信用分 */
    public Integer getCreditPoints() {
        return this.creditPoints;
    }

    /** 设置 信用分 */
    public void setCreditPoints(Integer creditPoints) {
        this.creditPoints = creditPoints;
    }

    /** 获取 借出的总金额 */
    public Double getBorrowOutTotalPrice() {
        return this.borrowOutTotalPrice;
    }

    /** 设置 借出的总金额 */
    public void setBorrowOutTotalPrice(Double borrowOutTotalPrice) {
        this.borrowOutTotalPrice = borrowOutTotalPrice;
    }

    /** 获取 借入的总金额 */
    public Double getBorrowJoinTotalPrice() {
        return this.borrowJoinTotalPrice;
    }

    /** 设置 借入的总金额 */
    public void setBorrowJoinTotalPrice(Double borrowJoinTotalPrice) {
        this.borrowJoinTotalPrice = borrowJoinTotalPrice;
    }

    /** 获取 借入的订单集合 */
    public List<Order> getRepayOrders() {
        return this.repayOrders;
    }

    /** 设置 借入的订单集合 */
    public void setRepayOrders(List<Order> repayOrders) {
        this.repayOrders = repayOrders;
    }

    /** 获取 待还的总金额 */
    public Double getNowToBePaidPrice() {
        return this.nowToBePaidPrice;
    }

    /** 设置 待还的总金额 */
    public void setNowToBePaidPrice(Double nowToBePaidPrice) {
        this.nowToBePaidPrice = nowToBePaidPrice;
    }

    /** 获取 逾期次数 */
    public Integer getOverdueNum() {
        return this.overdueNum;
    }

    /** 设置 逾期次数 */
    public void setOverdueNum(Integer overdueNum) {
        this.overdueNum = overdueNum;
    }

    /** 获取 借入次数 */
    public Integer getJoinNum() {
        return this.joinNum;
    }

    /** 设置 借入次数 */
    public void setJoinNum(Integer joinNum) {
        this.joinNum = joinNum;
    }

    /** 获取 借出次数 */
    public Integer getOutNum() {
        return this.outNum;
    }

    /** 设置 借出次数 */
    public void setOutNum(Integer outNum) {
        this.outNum = outNum;
    }

    /** 获取 待还总金额  个人信用使用 */
    public Double getNowToBePaidOutPrice() {

        if (null != nowToBePaidOutPrice) {
            if (null != repayOrders && repayOrders.size() > 0) {
                for (Order order : repayOrders) {
                    nowToBePaidOutPrice += order.getInterest() + order.getManagementCost();
                }
            }
            return Double.parseDouble(new DecimalFormat("#0.00").format(nowToBePaidOutPrice));
        }
        return this.nowToBePaidOutPrice;
    }

    /** 设置 待还总金额  个人信用使用 */
    public void setNowToBePaidOutPrice(Double nowToBePaidOutPrice) {
        this.nowToBePaidOutPrice = nowToBePaidOutPrice;
    }

    /** 获取 待收总金额  个人信用使用 */
    public Double getNowToBePaidJoinPrice() {
        if (null != nowToBePaidJoinPrice) {
            if (null != outOrders && outOrders.size() > 0) {
                for (Order order : outOrders) {
                    nowToBePaidJoinPrice += order.getInterest() + order.getManagementCost();
                }
            }
            return Double.parseDouble(new DecimalFormat("#0.00").format(nowToBePaidJoinPrice));
        }
        return this.nowToBePaidJoinPrice;
    }

    /** 设置 待收总金额  个人信用使用 */
    public void setNowToBePaidJoinPrice(Double nowToBePaidJoinPrice) {
        this.nowToBePaidJoinPrice = nowToBePaidJoinPrice;
    }

    /** 常用联系人 json字符串转为json数组 */
    public JSONArray getPersonJsonArys() {
        if (null != personJsonAry) {
            return JSONArray.parseArray(personJsonAry);
        }
        return null;
    }

    /** 获取 借出的订单集合 */
    public List<Order> getOutOrders() {
        return this.outOrders;
    }

    /** 设置 借出的订单集合 */
    public void setOutOrders(List<Order> outOrders) {
        this.outOrders = outOrders;
    }

    /** 获取 逾期还款总金额 */
    public Double getOverdueTotalPrice() {
        return this.overdueTotalPrice;
    }

    /** 设置 逾期还款总金额 */
    public void setOverdueTotalPrice(Double overdueTotalPrice) {
        this.overdueTotalPrice = overdueTotalPrice;
    }

    /** 获取 当前逾期且还未归还总金额 */
    public Double getOverdueNowTotalPrice() {
        return this.overdueNowTotalPrice;
    }

    /** 设置 当前逾期且还未归还总金额 */
    public void setOverdueNowTotalPrice(Double overdueNowTotalPrice) {
        this.overdueNowTotalPrice = overdueNowTotalPrice;
    }

    /** 获取 逾期总次数 */
    public Integer getOverdueTotalNum() {
        return this.overdueTotalNum;
    }

    /** 设置 逾期总次数 */
    public void setOverdueTotalNum(Integer overdueTotalNum) {
        this.overdueTotalNum = overdueTotalNum;
    }

    /** 获取 是否为出借人 */
    public Integer getIsFromUser() {
        return this.isFromUser;
    }

    /** 设置 是否为出借人 */
    public void setIsFromUser(Integer isFromUser) {
        this.isFromUser = isFromUser;
    }

    /** 获取 备注 */
    public String getRemark() {
        return this.remark;
    }

    /** 设置 备注 */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
