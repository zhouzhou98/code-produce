package com.knowledge.common.web.utils;
import cn.hutool.json.JSONObject;
import com.knowledge.core.constant.SecurityConstants;


/**
 * JWT工具类
 *
 * @author suyuzhou
 * @date 2022/2/5
 */
public class UserUtils {

    /**
     * 解析JWT获取用户ID
     *
     * @return
     */
    public static Long getUserId() {
        Long userId = null;
        JSONObject jwtPayload = JwtUtils.getJwtPayload();
        if (jwtPayload != null) {
            userId = jwtPayload.getLong("userId");
        }
        return userId;
    }

    /**
     * 解析JWT获取获取用户名
     *
     * @return
     */
    public static String getUsername() {
        String username =  JwtUtils.getJwtPayload().getStr(SecurityConstants.USER_NAME_KEY);
        return username;
    }
}
