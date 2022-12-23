package com.knowledge.server.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FieldTypeDto {
    private Long id;
    private String columnType;

    private String javaType;

    private String javaPackageName;

    private String jsPackageName;

    private String jsType;
}
