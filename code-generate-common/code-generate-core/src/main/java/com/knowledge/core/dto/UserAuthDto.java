package com.knowledge.core.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserAuthDto {
    private Long id;

    private String username;

    private String password;

    private List<String> roles;

    public UserAuthDto(Long id, String username, String password, List<String> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
}
