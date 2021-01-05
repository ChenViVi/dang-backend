package com.yellowzero.backend.model;

public enum Status {

    /**
     * 请求成功
     */
    SUCCESS(200, "OK"),

    /**
     * 客户端请求的语法错误，服务器无法理解
     */
    BAD_REQUEST(400, "参数不合法"),

    /**
     * 请求要求用户的身份认证
     */
    UNAUTHORIZED(401, "授权认证失败，请重新登录"),

    /**
     * 服务器理解请求客户端的请求，但是拒绝执行此请求
     */
    FORBIDDEN(403, "不，你不想"),

    /**
     * 服务器无法根据客户端的请求找到资源（网页）。通过此代码，网站设计人员可设置"您所请求的资源无法找到"的个性页面
     */
    NOT_FOUND(404, "未找到资源"),

    /**
     * 服务器内部错误，无法完成请求
     */
    INTERNAL_INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    /**
     * 作为网关或者代理工作的服务器尝试执行请求时，从远程服务器接收到了一个无效的响应
     */
    BAD_GATEWAY(502, "上传失败，请重试");


    private Integer code;
    private String msg;

    Status(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
