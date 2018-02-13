package com.magic.weijd.entity;

import net.sf.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/18 0018.
 */
public class Result implements Serializable {

    /** 访问状态. */
    private Integer flag;
    /** 执行结果. */
    private Integer code;
    /** 返回消息. */
    private String msg;
    /** 返回数据. */
    private Object data;

    public Result(Integer flag, Integer code, String msg, Object data) {
        this.flag = flag;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result() {
    }

    public Integer getFlag() {
        return this.flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String toJson() {
        String dataFormat = "{\"flag\":\"${1}\",\"msg\":\"${2}\",\"code\":${3},\"data\":\"${4}\"}";
        dataFormat = dataFormat.replace("${1}", String.valueOf(this.flag));
        dataFormat = dataFormat.replace("${2}", null == this.msg ? "" : this.msg);
        dataFormat = dataFormat.replace("${3}", null == this.code ? "" :this.code.toString());
        dataFormat = dataFormat.replace("${4}", null == this.data ?"": JSONObject.fromObject(this.data).toString());
        return dataFormat;
    }

    public enum FlagEnum {
        NORMAL("正常结果"),
        ERROR("错误结果"),
        NEED_LOGIN("未登陆"),
        NO_AUTH("无权限");

        private String text;

        FlagEnum(String text) {
            this.text = text;
        }

        public String getText() {
            return this.text;
        }

        public static Result.FlagEnum parse(int i) {
            return values()[i];
        }
    }

}
