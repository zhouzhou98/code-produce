package com.knowledge.server.domain.fieldtype;

import com.knowledge.common.mybatis.model.BaseDomainObjEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FieldType extends BaseDomainObjEntity {

    private String columnType;

    private String javaType;

    private String javaPackageName;

    private String jsPackageName;

    private String jsType;
}
