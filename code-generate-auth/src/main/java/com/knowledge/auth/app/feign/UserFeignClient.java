package com.knowledge.auth.app.feign;

import com.knowledge.core.dto.UserAuthDto;
import com.knowledge.core.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "coder-user")
public interface UserFeignClient {
    @GetMapping("/api/v1/user/selectByUsername")
    Result<UserAuthDto> selectByUsername(@RequestParam String username);
}
