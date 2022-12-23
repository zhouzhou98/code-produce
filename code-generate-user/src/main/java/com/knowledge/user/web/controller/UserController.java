package com.knowledge.user.web.controller;

import com.knowledge.common.web.exception.BizException;
import com.knowledge.core.dto.UserAuthDto;
import com.knowledge.core.dto.UserDto;
import com.knowledge.core.enums.ResultCodeEnum;
import com.knowledge.core.result.Result;
import com.knowledge.user.app.cmd.UserCmd;
import com.knowledge.user.app.qry.UserQry;
import com.knowledge.user.web.request.UserRegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserQry userQry;
    @Autowired
    private UserCmd userCmd;
    @GetMapping("/selectByUsername")
    public Result<UserAuthDto> selectByUsername(@RequestParam String username) {
        UserAuthDto userAuthDto = userQry.selectByUsername(username);
        return Result.success(userAuthDto);
    }

    @GetMapping("/selectById")
    public Result<UserDto> selectById(@RequestParam Long id) {
        UserDto userDto = userQry.selectById(id);
        return Result.success(userDto);
    }

    @PostMapping("/register")
    public Result<String> register(@Validated @RequestBody UserRegisterRequest req) {
        String password = req.password;
        String username = req.username;
        int row = userCmd.register(username, password);
        if (row > 0) {
            return Result.success();
        }
        return Result.failed();
    }

    @GetMapping("/hello")
    public String test() {
        throw new BizException(ResultCodeEnum.USER_IS_EXIST);
//        return "hello world";
    }
}
