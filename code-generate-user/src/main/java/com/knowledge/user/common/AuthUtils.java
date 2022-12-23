package com.knowledge.user.common;

import com.knowledge.core.dto.UserDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import java.util.Map;

public class AuthUtils {
    public static UserDto getUserDto() {
        Authentication authentication
                = SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthenticationDetails details =
                (OAuth2AuthenticationDetails)authentication.getDetails();
        Map<String, Object> map = (Map<String, Object>) details.getDecodedDetails();
        Map<String, Object>  userInfo = (Map<String, Object>) map.get("userInfo");
        UserDto userDto = new UserDto(Long.valueOf(userInfo.get("id").toString()), userInfo.get("username").toString());
        return userDto;
    }
}
