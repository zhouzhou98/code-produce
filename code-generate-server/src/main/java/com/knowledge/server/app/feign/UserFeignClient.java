package com.knowledge.server.app.feign;

import com.knowledge.core.dto.UserDto;
import com.knowledge.core.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "coder-user")
public interface UserFeignClient {
    @GetMapping("/api/v1/user/selectById")
    Result<UserDto> selectById(@RequestParam Long id);
}
