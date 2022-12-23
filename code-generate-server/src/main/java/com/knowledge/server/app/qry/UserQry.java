package com.knowledge.server.app.qry;

import com.knowledge.common.web.exception.BizException;
import com.knowledge.core.dto.UserDto;
import com.knowledge.core.enums.ResultCodeEnum;
import com.knowledge.core.result.Result;
import com.knowledge.server.app.feign.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserQry {
  @Autowired
  private UserFeignClient userFeignClient;

  public UserDto getUserAuthDto(Long id) {
      Result<UserDto> result = userFeignClient.selectById(id);
      if (Result.isSuccess(result)) {
          return result.getData();
      }
      throw new BizException(ResultCodeEnum.USER_NOT_EXIST);
  }
}
