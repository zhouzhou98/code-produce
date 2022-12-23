package com.knowledge.auth.app.qry;

import com.knowledge.auth.app.feign.UserFeignClient;
import com.knowledge.common.web.exception.BizException;
import com.knowledge.core.dto.UserAuthDto;
import com.knowledge.core.enums.ResultCodeEnum;
import com.knowledge.core.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserQry {
  @Autowired
  private UserFeignClient userFeignClient;

  public UserAuthDto getUserAuthDto(String username) {

      Result<UserAuthDto> result = userFeignClient.selectByUsername(username);
      if (Result.isSuccess(result)) {
          return result.getData();
      }
      throw new BizException(ResultCodeEnum.USER_NOT_EXIST);
  }
}
