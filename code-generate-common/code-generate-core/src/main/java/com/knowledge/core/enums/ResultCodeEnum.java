package com.knowledge.core.enums;

import com.knowledge.core.result.IResultCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * 返回结果枚举
 * @author suyuzhou
 * @date 2022/7/15
 */
@AllArgsConstructor
public enum ResultCodeEnum implements IResultCode, Serializable {
    SUCCESS(0, "一切ok"),
    SYSTEM_EXECUTION_ERROR(1024, "系统执行出错"),

    // 参数异常 400 ~ 401
    PARAM_ERROR(400, "用户请求参数错误"),
    RESOURCE_NOT_FOUND(401, "请求资源不存在"),
    PARAM_IS_NULL(402, "请求必填参数为空"),

    // 用户异常 200～
    USER_NOT_EXIST(201, "用户不存在"),
    USER_ACCOUNT_LOCKED(202, "用户账户被冻结"),
    USER_ACCOUNT_INVALID(203, "用户账户已作废"),
    USERNAME_OR_PASSWORD_ERROR(210, "用户名或密码错误"),
    PASSWORD_ENTER_EXCEED_LIMIT(211, "用户输入密码次数超限"),
    CLIENT_AUTHENTICATION_FAILED(212, "客户端认证失败"),
    TOKEN_INVALID_OR_EXPIRED(230, "token无效或已过期"),
    TOKEN_ACCESS_FORBIDDEN(231, "token已被禁止访问"),
    NO_RIGHT(234, "无权限操作"),
    REGISTER_FAIL(232, "用户注册失败"),
    USER_IS_EXIST(233, "用户已经存在"),
    AUTHORIZED_ERROR(300, "访问权限异常"),
    ACCESS_UNAUTHORIZED(301, "访问未授权"),
    UPDATE_USER_INFO_FAIL(303, "更新用户信息失败"),


    ADD_FAIL(501, "数据添加失败"),
    UPDATE_FAIL(502, "数据更新失败"),
    DELETE_FAIL(503, "数据删除失败"),
    AUTH_FAIL(603, "审核失败"),

    SERVICE_NOT_FOUND(604, "未找到认证服务器"),

    DATASOURCE_NOT_FOUND(601, "数据源不存在"),
    DATASOURCE_ERROR_SET(602, "数据源错误配置"),
    DATASOURCE_IS_EXIST(603, "数据源已经存在"),
    PROJECT_NOT_FOUND(701, "工程不存在"),
    PROJECT_IS_EXIST(702, "工程已经存在"),

    TABLE_NOT_EXIST(801, "表已经不存在"),

    GENERATOR_TYPE_NOT_FOUND(901, "生成类型丢失"),
    ;



    @Getter
    private Integer code;

    @Getter
    private String msg;

    @Override
    public String toString() {
        return "{" +
                "\"code\":\"" + code + '\"' +
                ", \"msg\":\"" + msg + '\"' +
                '}';
    }


    public static ResultCodeEnum getValue(String code){
        for (ResultCodeEnum value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return SYSTEM_EXECUTION_ERROR; // 默认系统执行错误
    }


}
