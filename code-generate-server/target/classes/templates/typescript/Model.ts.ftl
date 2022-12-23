import { EntityModel } from '@midwayjs/orm';
import { Column } from 'typeorm';

@EntityModel('${ClassName}')
export class ${ClassName}Model {
<#list fieldList as field>
<#if field.fieldComment!?length gt 0>
    /**
    * ${field.fieldComment}
    */
</#if>

<#if field.primaryPk>
    @PrimaryColumn()
<#else>
    @Column()
</#if>
    ${field.attrName} : ${field.jsAttrType} ;
</#list>
}
