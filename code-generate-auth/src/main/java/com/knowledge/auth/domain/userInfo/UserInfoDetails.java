package com.knowledge.auth.domain.userInfo;

import cn.hutool.core.collection.CollectionUtil;
import com.knowledge.core.dto.UserAuthDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户认证信息
 *
 * @author suyuzhou
 * @date 2022/07/19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoDetails implements UserDetails {
    // 用户id
    private Long id;
    // 手机
    private String username;
    // 密码
    private String password;

    private String authenticationIdentity;

    private Collection<SimpleGrantedAuthority> authorities;
    public UserInfoDetails(UserAuthDto userAuthDto) {
        this.setId(userAuthDto.getId());
        this.setUsername(userAuthDto.getUsername());
        this.setPassword(userAuthDto.getPassword());
        if (CollectionUtil.isNotEmpty(userAuthDto.getRoles())) {
            authorities = new ArrayList<>();
            userAuthDto.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
        }
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Map<String, Object> toJSON() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", this.id);
        map.put("username", this.username);
        return map;
    }
}
