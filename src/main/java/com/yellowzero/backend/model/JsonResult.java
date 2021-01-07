package com.yellowzero.backend.model;

public class JsonResult {

    /**
     * 返回的状态码，0：失败，1：成功
     */
    private Integer code;

    /**
     * 返回信息
     */
    private String msg;

    /**
     * 返回的数据
     */
    private Object data;

    /**
     * 只返回状态码
     *
     * @param code 状态码
     */
    public JsonResult(Integer code) {
        this.code = code;
    }

    /**
     * 不返回数据的构造方法
     *
     * @param code 状态码
     * @param msg  信息
     */
    public JsonResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 不返回数据的构造方法
     *
     * @param responseStatus Status
     */
    public JsonResult(Status responseStatus) {
        this.code = responseStatus.getCode();
        this.msg = responseStatus.getMsg();
    }

    /**
     * 返回数据的构造方法
     *
     * @param code   状态码
     * @param msg    信息
     * @param data 数据
     */
    public JsonResult(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 不返回数据的构造方法
     *
     * @param responseStatus Status
     * @param data 数据
     */
    public JsonResult(Status responseStatus, Object data) {
        this.code = responseStatus.getCode();
        this.msg = responseStatus.getMsg();
        this.data = data;
    }

    /**
     * 返回状态码和数据
     *
     * @param code   状态码
     * @param data 数据
     */
    public JsonResult(Integer code, Object data) {
        this.code = code;
        this.data = data;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}