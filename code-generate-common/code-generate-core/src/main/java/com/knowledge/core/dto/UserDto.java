package com.knowledge.core.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserDto {
    private Long id;

    private String username;

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
