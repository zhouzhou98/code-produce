package com.knowledge.auth.handler;

import com.knowledge.common.web.exception.BizException;
import com.knowledge.core.enums.ResultCodeEnum;
import com.knowledge.core.result.Result;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 性别枚举
 * @author suyuzhou
 * @date 2022/7/15
 */
@RestControllerAdvice
@Slf4j
public class AuthExceptionHandler {

    /**
     * 用户不存在
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UsernameNotFoundException.class)
    public Result handleUsernameNotFoundException(UsernameNotFoundException e) {
        return Result.failed(ResultCodeEnum.USER_NOT_EXIST);
    }

    /**
     * 用户名和密码异常
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidGrantException.class)
    public Result handleInvalidGrantException(InvalidGrantException e) {
        return Result.failed(ResultCodeEnum.USERNAME_OR_PASSWORD_ERROR);
    }


    /**
     * 账户异常(禁用、锁定、过期)
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public Result handleInternalAuthenticationServiceException(InternalAuthenticationServiceException e) {
        log.info("调用异常:{}", e.getMessage());
        return Result.failed(e.getMessage());
    }

    /**
     * token 无效或已过期
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({InvalidTokenException.class})
    public Result handleInvalidTokenExceptionException(InvalidTokenException e) {
        return Result.failed(e.getMessage());
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FeignException.BadRequest.class)
    public <T> Result<T> processException(FeignException.BadRequest e) {
        log.info("微服务feign调用异常:{}", e.getMessage());
        return Result.failed(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BizException.class)
    public <T> Result<T> handleBizException(BizException e) {
        log.error("业务异常，异常原因：{}", e.getMessage(), e);
        if (e.getResultCode() != null) {
            return Result.failed(e.getResultCode());
        }
        return Result.failed(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public <T> Result<T> handleException(Exception e) {
        return Result.failed(e.getLocalizedMessage());
    }
}
