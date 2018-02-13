package com.magic.weijd.entity;

import com.magic.weijd.util.ComputeAccrual;
import com.magic.weijd.util.DateTimeHelper;
import com.magic.weijd.util.Timestamp;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.io.Serializable;

/**
 * 订单
 * @author lzh
 * @create 2017/9/12 15:19
 */
public class Order implements Serializable {

    private Integer id;

    /** 编号 */
    private String number;

    /** 出借人 */
    private Integer fromUserId;

    /** 出借人 */
    private String fromUserName;

    /** 出借人身份证 */
    private String fromUserIdCard;

    /** 出借人电话 */
    private String fromUserPhone;

    /** 出借人头像 */
    private String fromUserAvatar;

    /** 借款人 */
    private Integer toUserId;

    /** 借款人 */
    private String toUserName;

    /** 借款人身份证 */
    private String toUserIdCard;

    /** 借款人电话 */
    private String toUserPhone;

    /** 借款人头像 */
    private String toUserAvatar;

    /** 借款时间 */
    private Date createTime;

    /** 还款日 */
    private Date repaymentTime;

    /** 真实的还款时间 */
    private Date trueRepaymentTime;

    /** 真实的还款金额 */
    private Double trueRepaymentPrice;

    /** 延期还款日 */
    private Date postponeRepaymentTime;

    /** 借款状态 ：
     0：借款/出借申请
     1：确认申请
     2：拒绝借款申请/拒绝出借申请
     3：同意借款/同意出借（待还款）
     4：申请还款
     5：确认还款（完成）
     6：拒绝延期还款（此状态不记录数据库，记录状态改为3待还款状态）
     7：申请延期还款 */
    private Integer status;

    /** 类型：0：出借 1：借款 */
    private Integer type;

    /** 金额 */
    private Double price;

    /** 是否上传视频 */
    private Integer isUploadVideo;

    /** 上传的视频url */
    private String videoUrl;

    /** 还款方式id */
    private Integer repaymentMethodId;

    /** 还款方式名 */
    private String repaymentMethodName;

    /** 年利率id */
    private Integer interestId;

    /** 年利率名 */
    private String interestName;

    /** 借款用途id */
    private Integer loansUseId;
    /** 借款用途名 */
    private String loansUseName;


    /** 支付状态 0：未支付  1：已支付 */
    private Integer payStatus;

    /** 服务费 */
    private Double serviceFee;

    /** 第三方 支付 的订单号 */
    private String outTradeNO;

    /** 是否有效 0：无效 1：有效 */
    private Integer isValid;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /** 获取 编号 */
    public String getNumber() {
        return this.number;
    }

    /** 设置 编号 */
    public void setNumber(String number) {
        this.number = number;
    }

    /** 获取 出借人 */
    public Integer getFromUserId() {
        return this.fromUserId;
    }

    /** 设置 出借人 */
    public void setFromUserId(Integer fromUserId) {
        this.fromUserId = fromUserId;
    }

    /** 获取 出借人 */
    public String getFromUserName() {
        return this.fromUserName;
    }

    /** 设置 出借人 */
    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    /** 获取 借款人 */
    public Integer getToUserId() {
        return this.toUserId;
    }

    /** 设置 借款人 */
    public void setToUserId(Integer toUserId) {
        this.toUserId = toUserId;
    }

    /** 获取 借款人 */
    public String getToUserName() {
        return this.toUserName;
    }

    /** 设置 借款人 */
    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    /** 获取 借款时间 */
    public Date getCreateTime() {
        return this.createTime;
    }

    /** 设置 借款时间 */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /** 获取 还款日 */
    public Date getRepaymentTime() {
        if (null != postponeRepaymentTime && null != repaymentTime) {
            return postponeRepaymentTime;
        }
        return this.repaymentTime;
    }

    /** 设置 还款日 */
    public void setRepaymentTime(Date repaymentTime) {
        this.repaymentTime = repaymentTime;
    }


    /** 借款状态 ：
     0：借款/出借申请
     1：确认申请
     2：拒绝借款申请/拒绝出借申请
     3：同意借款/同意出借（待还款）
     4：申请还款
     5：确认还款（完成）
     6：拒绝延期还款（此状态不记录数据库，记录状态改为3待还款状态）
     7：申请延期还款 */
    public Integer getStatus() {
        return this.status;
    }

    /** 借款状态 ：
     0：借款/出借申请
     1：确认申请
     2：拒绝借款申请/拒绝出借申请
     3：同意借款/同意出借（待还款）
     4：申请还款
     5：确认还款（完成）
     6：拒绝延期还款（此状态不记录数据库，记录状态改为3待还款状态）
     7：申请延期还款 */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /** 获取 类型：0：出借 1：借款 */
    public Integer getType() {
        return this.type;
    }

    /** 设置 类型：0：出借 1：借款 */
    public void setType(Integer type) {
        this.type = type;
    }

    /** 获取 出借人头像 */
    public String getFromUserAvatar() {
        return this.fromUserAvatar;
    }

    /** 设置 出借人头像 */
    public void setFromUserAvatar(String fromUserAvatar) {
        this.fromUserAvatar = fromUserAvatar;
    }

    /** 获取 借款人头像 */
    public String getToUserAvatar() {
        return this.toUserAvatar;
    }

    /** 设置 借款人头像 */
    public void setToUserAvatar(String toUserAvatar) {
        this.toUserAvatar = toUserAvatar;
    }

    /** 获取 金额 */
    public Double getPrice() {
        return this.price;
    }

    /** 设置 金额 */
    public void setPrice(Double price) {
        this.price = price;
    }

    /** 获取 是否上传视频 */
    public Integer getIsUploadVideo() {
        return this.isUploadVideo;
    }

    /** 设置 是否上传视频 */
    public void setIsUploadVideo(Integer isUploadVideo) {
        this.isUploadVideo = isUploadVideo;
    }

    /** 获取 上传的视频url */
    public String getVideoUrl() {
        return this.videoUrl;
    }

    /** 设置 上传的视频url */
    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    /** 获取 还款方式id */
    public Integer getRepaymentMethodId() {
        return this.repaymentMethodId;
    }

    /** 设置 还款方式id */
    public void setRepaymentMethodId(Integer repaymentMethodId) {
        this.repaymentMethodId = repaymentMethodId;
    }

    /** 获取 年利率id */
    public Integer getInterestId() {
        return this.interestId;
    }

    /** 设置 年利率id */
    public void setInterestId(Integer interestId) {
        this.interestId = interestId;
    }


    /** 获取 延期还款日 */
    public Date getPostponeRepaymentTime() {
        return this.postponeRepaymentTime;
    }

    /** 设置 延期还款日 */
    public void setPostponeRepaymentTime(Date postponeRepaymentTime) {
        this.postponeRepaymentTime = postponeRepaymentTime;
    }

    /** 获取 还款方式名 */
    public String getRepaymentMethodName() {
        return this.repaymentMethodName;
    }

    /** 设置 还款方式名 */
    public void setRepaymentMethodName(String repaymentMethodName) {
        this.repaymentMethodName = repaymentMethodName;
    }

    /** 获取 年利率名 */
    public String getInterestName() {
        return this.interestName;
    }

    /** 设置 年利率名 */
    public void setInterestName(String interestName) {
        this.interestName = interestName;
    }

    /** 获取 借款用途id */
    public Integer getLoansUseId() {
        return this.loansUseId;
    }

    /** 设置 借款用途id */
    public void setLoansUseId(Integer loansUseId) {
        this.loansUseId = loansUseId;
    }

    /** 获取 借款用途名 */
    public String getLoansUseName() {
        return this.loansUseName;
    }

    /** 设置 借款用途名 */
    public void setLoansUseName(String loansUseName) {
        this.loansUseName = loansUseName;
    }

    /** 获取 真实的还款时间 */
    public Date getTrueRepaymentTime() {
        return this.trueRepaymentTime;
    }

    /** 设置 真实的还款时间 */
    public void setTrueRepaymentTime(Date trueRepaymentTime) {
        this.trueRepaymentTime = trueRepaymentTime;
    }

    /** 获取 真实的还款金额 */
    public Double getTrueRepaymentPrice() {
        if (null != trueRepaymentPrice) {
           return Double.parseDouble(new DecimalFormat("#0.00").format(trueRepaymentPrice));
        }
        return this.trueRepaymentPrice;
    }

    /** 设置 真实的还款金额 */
    public void setTrueRepaymentPrice(Double trueRepaymentPrice) {
        this.trueRepaymentPrice = trueRepaymentPrice;
    }

    /** 计算利息 */
    public Double getInterest(){
        if (null != price && null != interestName && null != createTime) {
            Integer days = Integer.parseInt(String.valueOf(DateTimeHelper.getDifference(status == 5 ? repaymentTime : new Date(),createTime,Calendar.DATE)));
            return Double.parseDouble(new DecimalFormat("#0.00").format(ComputeAccrual.computeAccrualDayInterest(price,Double.parseDouble(interestName) / 100 ,
                    Timestamp.getDaysNum(createTime,Calendar.DAY_OF_YEAR),
                    days == 0 ? 1 : days < 0 ? 0 - days : days,0.0)));
        }
        return 0.0;
    }

    /**
     * 剩余还款天数
     * @return
     */
    public Integer getRemainDays() {
        if (null != repaymentTime) {
            Date trueDate = null == trueRepaymentTime ? new Date() : trueRepaymentTime;
            if (trueDate.compareTo(repaymentTime) < 0) {
                return Integer.parseInt(String.valueOf(DateTimeHelper.getDifference(trueDate ,repaymentTime, Calendar.DATE)).replace("-",""));
            } else {
                return 0 - Integer.parseInt(String.valueOf(DateTimeHelper.getDifference(trueDate ,repaymentTime, Calendar.DATE)).replace("-",""));
            }

        }
        return null;
    }

    /**
     * 逾期管理费
     * @return
     */
    public Double getManagementCost() {
        if (null != getRemainDays() && getRemainDays() < 0) {
            return 0.006 * (0 - getRemainDays()) * price;
        }
        return 0.0;
    }

    /** 获取 出借人身份证 */
    public String getFromUserIdCard() {
        return this.fromUserIdCard;
    }

    /** 设置 出借人身份证 */
    public void setFromUserIdCard(String fromUserIdCard) {
        this.fromUserIdCard = fromUserIdCard;
    }

    /** 获取 出借人电话 */
    public String getFromUserPhone() {
        return this.fromUserPhone;
    }

    /** 设置 出借人电话 */
    public void setFromUserPhone(String fromUserPhone) {
        this.fromUserPhone = fromUserPhone;
    }

    /** 获取 借款人身份证 */
    public String getToUserIdCard() {
        return this.toUserIdCard;
    }

    /** 设置 借款人身份证 */
    public void setToUserIdCard(String toUserIdCard) {
        this.toUserIdCard = toUserIdCard;
    }

    /** 获取 借款人电话 */
    public String getToUserPhone() {
        return this.toUserPhone;
    }

    /** 设置 借款人电话 */
    public void setToUserPhone(String toUserPhone) {
        this.toUserPhone = toUserPhone;
    }

    /** 获取 支付状态 0：未支付  1：已支付 */
    public Integer getPayStatus() {
        return this.payStatus;
    }

    /** 设置 支付状态 0：未支付  1：已支付 */
    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    /** 获取 服务费 */
    public Double getServiceFee() {
        return this.serviceFee;
    }

    /** 设置 服务费 */
    public void setServiceFee(Double serviceFee) {
        this.serviceFee = serviceFee;
    }

    /** 获取 第三方 支付 的订单号 */
    public String getOutTradeNO() {
        return this.outTradeNO;
    }

    /** 设置 第三方 支付 的订单号 */
    public void setOutTradeNO(String outTradeNO) {
        this.outTradeNO = outTradeNO;
    }

    /** 获取 是否有效 0：无效 1：有效 */
    public Integer getIsValid() {
        return this.isValid;
    }

    /** 设置 是否有效 0：无效 1：有效 */
    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }
}
