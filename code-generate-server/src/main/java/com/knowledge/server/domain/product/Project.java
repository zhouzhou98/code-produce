package com.knowledge.server.domain.product;

import com.knowledge.common.mybatis.model.BaseDomainObjEntity;
import com.knowledge.core.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Project extends BaseDomainObjEntity {
    private UserDto user;
    private String projectName;
    private String projectCode;
    private String projectPackage;
    private String version;
    private String author;
    private String email;

    public Map<String, Object> toJSON() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", this.getId());
        map.put("user", this.user);
        map.put("projectName", this.projectName);
        map.put("projectCode", this.projectCode);
        map.put("projectPackage", this.projectPackage);
        map.put("version", this.version);
        map.put("author", this.author);
        map.put("email", this.email);
        return map;
    }
}
