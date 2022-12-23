package com.knowledge.server.domain.table;

import cn.hutool.core.text.NamingCase;
import cn.hutool.core.util.StrUtil;
import com.knowledge.common.mybatis.model.BaseDomainObjEntity;
import com.knowledge.core.dto.UserDto;
import com.knowledge.server.domain.datasource.Datasource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Table extends BaseDomainObjEntity {
   private Datasource datasource;
   private String tableName;
   private UserDto user;
   private String comment;
   private List<TableField> tableFieldList;

   public String getClassName() {
      return NamingCase.toPascalCase(this.tableName);
   }

   public String getModuleName(String packageName) {
      return StrUtil.subAfter(packageName, ".", true);
   }

   public String getFunctionName() {
      String functionName = StrUtil.subAfter(this.tableName, "_", true);
      if (StrUtil.isBlank(functionName)) {
         functionName = tableName;
      }
      return functionName;
   }
}
