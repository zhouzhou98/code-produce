# 研发背景 
项目的创作主要来源于工作中，在工作中经常遇到数据库的表变成具体的某个类时，需要写大量无意义的代码。生成的好的代码的代码生成器需要找资源破解，框架自带的生成器又无法满足自己的想法<br>
同时，当需要数据测试，又不想自己构造数据。借鉴了maku-generator和sqlfather，诞生了这款代码生成器。
# 项目架构图
![image](img/1.png)
![image](img/2.png)
# 项目前端地址
前端项目地址：[https://github.com/kitori7/code-produce](https://github.com/kitori7/code-produce)  

# 技术栈 
|  依赖   | 
|  ----  |
| Springboot-2.7.0  |
| SpringCloudAlibaba-2021.1  |
| Spring OpenFeign |
|Mysql8.0|
|Redis6.2.6|
|FreeMarker|
|DataFaker|
|OSS|
|Mybatis-Plus|
|FastJson|

Maven聚合工程划分  
(1)code-generate-auth  
认证模块：主要进行用户的认证功能  
(2)code-generate-common  
公共依赖模块:
+ code-generate-core
+ code-generate-log
+ code-generate-mybatis-plus
+ code-generate-oss
+ code-generate-redis
+ code-generate-web  

(3)code-generate-gateway  
网关模块：提供统一的访问接口  
(4)code-generate-server  
生成器服务：生成数据和sql  
(5)code-generate-user
用户服务
项目还使用了多种设计模式，诸如单例工厂、建造者模式

登陆访问端口post请求：ip:8000/coder-auth/oauth/token
```java
X-www-form-urkencoded
username：xxx
password:   xxx
grant_type： password
```
header
```java
  即 Authorization Basic Y29kZS1nZW5lcmF0b3I6MTIzNDU2
```
response
```java
code
data： {
  "access_token":
  "token_type": "bearer",
  "refresh_token":
  "expires_in"：
  "scope":
  "userInfo": {
      "id": 
      "username": ""
  }
  "jti"
}
```
![image](img/3.png)
![image](img/4.png)
![image](img/5.png)
获取到access_token,通过access_token访问服务
# 应用场景  
##字段服务  
展示常用的字段类型
![image](img/16.png)
## 导入数据库
![image](img/17.png)
![image](img/18.png)
## 工程服务
![image](img/22.png)
# 功能特色
##项目演示图  
![image](img/15.png)
## 多语言生成
不仅可以生成Java代码，还能生成TYPESCRIPT代码，并通过oss下载出zip覆盖即可
请求访问端口post请求：ip:8000/coder-server/api/v1/generate/code/{数据源id}/{工程id}/{类型}，其中类型可以通过输入JAVA或者TYPESCRIPT来生成不同的语言  
![image](img/19.png)
header
```java
  即 Authorization bearer token
```
body
```java
[
   表id     
]
```
![image](img/6.png)
![image](img/7.png)
![image](img/8.png)
![image](img/9.png)
![image](img/10.png)
![image](img/11.png)
## 数据生成
请求访问端口post请求：ip:8000/coder-server/api/v1/generate/code/data
![image](img/20.png)
![image](img/21.png)
header
```java
  即 Authorization bearer token
```
```java
{
    "tableName": "xx", // 表名
    "fakerNum": xx, // 生成数量
    "fieldList": [  // 表的字段
        {
            "fieldName": "xx", // 字段名称
            "fieldType": "xx", // 字段类型
            "fieldComment": "xx", // 字段注释
            "fieldDefault": null,  // 字段默认值
            "primaryPk": xx,  // 是否是主键
            "fakerType": "xx", // 模拟类型
            "fakerParam": ""   // 模拟参数
        }
    ]
}
```
![image](img/12.png)
![image](img/13.png)
```java
{
    "tableName": "friend_request",
    "fakerNum": 20,
    "fieldList": [
        {
            "fieldName": "id",
            "fieldType": "bigint",
            "fieldComment": "主键",
            "fieldDefault": null,
            "primaryPk": 1,
            "fakerType": "SNOWFLOWER",
            "fakerParam": ""
        },
        {
            "fieldName": "apply_user_id",
            "fieldType": "bigint",
            "fieldComment": "申请人id",
            "fieldDefault": null,
            "primaryPk": 0,
            "fakerType": "SNOWFLOWER",
            "fakerParam": ""
        },
        {
            "fieldName": "audit_user_id",
            "fieldType": "bigint",
            "fieldComment": "审核人id",
            "fieldDefault": null,
            "primaryPk": 0,
            "fakerType": "SNOWFLOWER",
            "fakerParam": ""
        },
        {
            "fieldName": "comments",
            "fieldType": "varchar",
            "fieldComment": "备注",
            "fieldDefault": null,
            "primaryPk": 0,
            "fakerType": "SNOWFLOWER",
            "fakerParam": ""
        },
        {
            "fieldName": "status",
            "fieldType": "tinyint",
            "fieldComment": "审核状态：0.已申请，1.已同意，2.已拒绝",
            "fieldDefault": "0",
            "primaryPk": 0,
            "fakerType": "RANDOM",
            "fakerParam": "NAME"
        },
        {
            "fieldName": "read_flag",
            "fieldType": "tinyint",
            "fieldComment": "是否已读 0：未读，1：已读",
            "fieldDefault": "0",
            "primaryPk": 0,
            "fakerType": "NONE",
            "fakerParam": ""
        },
        {
            "fieldName": "created_at",
            "fieldType": "datetime",
            "fieldComment": "创建时间",
            "fieldDefault": "CURRENT_TIMESTAMP",
            "fakerType": "NONE",
            "primaryPk": 0,
            "fakerParam": ""
        },
        {
            "fieldName": "updated_at",
            "fieldType": "datetime",
            "fieldComment": "更新时间",
            "fieldDefault": "CURRENT_TIMESTAMP",
            "fakerType": "NONE",
            "primaryPk": 0,
            "fakerParam": ""
        },
        {
            "fieldName": "data_version",
            "fieldType": "int",
            "fieldComment": "版本号",
            "fieldDefault": "0",
            "fakerType": "NONE",
            "primaryPk": 0,
            "fakerParam": ""
        }
    ]
}
```
![image](img/14.png)
```java
{
    "code": 0,
    "data": {
        "dataList": [
            {
                "audit_user_id": "1606298389316636673",
                "comments": "1606298389320830977",
                "apply_user_id": "1606298389316636673",
                "updated_at": "2022-12-23 14:39:14",
                "data_version": "0",
                "created_at": "2022-12-23 14:39:14",
                "id": "1606298389316636672",
                "read_flag": "0",
                "status": "许志泽"
            },
            {
                "audit_user_id": "1606298389316636674",
                "comments": "1606298389320830978",
                "apply_user_id": "1606298389316636674",
                "updated_at": "2022-12-23 14:39:14",
                "data_version": "0",
                "created_at": "2022-12-23 14:39:14",
                "id": "1606298389316636673",
                "read_flag": "0",
                "status": "马旭尧"
            },
            {
                "audit_user_id": "1606298389316636675",
                "comments": "1606298389320830979",
                "apply_user_id": "1606298389316636675",
                "updated_at": "2022-12-23 14:39:14",
                "data_version": "0",
                "created_at": "2022-12-23 14:39:14",
                "id": "1606298389316636674",
                "read_flag": "0",
                "status": "马浩轩"
            },
            {
                "audit_user_id": "1606298389316636676",
                "comments": "1606298389320830980",
                "apply_user_id": "1606298389316636676",
                "updated_at": "2022-12-23 14:39:14",
                "data_version": "0",
                "created_at": "2022-12-23 14:39:14",
                "id": "1606298389316636675",
                "read_flag": "0",
                "status": "崔睿渊"
            },
            {
                "audit_user_id": "1606298389316636677",
                "comments": "1606298389320830981",
                "apply_user_id": "1606298389316636677",
                "updated_at": "2022-12-23 14:39:14",
                "data_version": "0",
                "created_at": "2022-12-23 14:39:14",
                "id": "1606298389316636676",
                "read_flag": "0",
                "status": "龙修杰"
            },
            {
                "audit_user_id": "1606298389320830976",
                "comments": "1606298389320830982",
                "apply_user_id": "1606298389316636678",
                "updated_at": "2022-12-23 14:39:14",
                "data_version": "0",
                "created_at": "2022-12-23 14:39:14",
                "id": "1606298389316636677",
                "read_flag": "0",
                "status": "严鹏煊"
            },
            {
                "audit_user_id": "1606298389320830977",
                "comments": "1606298389320830983",
                "apply_user_id": "1606298389316636679",
                "updated_at": "2022-12-23 14:39:14",
                "data_version": "0",
                "created_at": "2022-12-23 14:39:14",
                "id": "1606298389316636678",
                "read_flag": "0",
                "status": "胡健柏"
            },
            {
                "audit_user_id": "1606298389320830978",
                "comments": "1606298389320830984",
                "apply_user_id": "1606298389316636680",
                "updated_at": "2022-12-23 14:39:14",
                "data_version": "0",
                "created_at": "2022-12-23 14:39:14",
                "id": "1606298389316636679",
                "read_flag": "0",
                "status": "陆擎宇"
            },
            {
                "audit_user_id": "1606298389320830979",
                "comments": "1606298389320830985",
                "apply_user_id": "1606298389316636681",
                "updated_at": "2022-12-23 14:39:14",
                "data_version": "0",
                "created_at": "2022-12-23 14:39:14",
                "id": "1606298389316636680",
                "read_flag": "0",
                "status": "蒋炫明"
            },
            {
                "audit_user_id": "1606298389320830980",
                "comments": "1606298389320830986",
                "apply_user_id": "1606298389316636682",
                "updated_at": "2022-12-23 14:39:14",
                "data_version": "0",
                "created_at": "2022-12-23 14:39:14",
                "id": "1606298389316636681",
                "read_flag": "0",
                "status": "夏锦程"
            },
            {
                "audit_user_id": "1606298389320830981",
                "comments": "1606298389320830987",
                "apply_user_id": "1606298389316636683",
                "updated_at": "2022-12-23 14:39:14",
                "data_version": "0",
                "created_at": "2022-12-23 14:39:14",
                "id": "1606298389316636682",
                "read_flag": "0",
                "status": "吕峻熙"
            },
            {
                "audit_user_id": "1606298389320830982",
                "comments": "1606298389320830988",
                "apply_user_id": "1606298389316636684",
                "updated_at": "2022-12-23 14:39:14",
                "data_version": "0",
                "created_at": "2022-12-23 14:39:14",
                "id": "1606298389316636683",
                "read_flag": "0",
                "status": "熊驰"
            },
            {
                "audit_user_id": "1606298389320830983",
                "comments": "1606298389320830989",
                "apply_user_id": "1606298389316636685",
                "updated_at": "2022-12-23 14:39:14",
                "data_version": "0",
                "created_at": "2022-12-23 14:39:14",
                "id": "1606298389316636684",
                "read_flag": "0",
                "status": "范胤祥"
            },
            {
                "audit_user_id": "1606298389320830984",
                "comments": "1606298389320830990",
                "apply_user_id": "1606298389316636686",
                "updated_at": "2022-12-23 14:39:14",
                "data_version": "0",
                "created_at": "2022-12-23 14:39:14",
                "id": "1606298389316636685",
                "read_flag": "0",
                "status": "顾明杰"
            },
            {
                "audit_user_id": "1606298389320830985",
                "comments": "1606298389320830991",
                "apply_user_id": "1606298389316636687",
                "updated_at": "2022-12-23 14:39:14",
                "data_version": "0",
                "created_at": "2022-12-23 14:39:14",
                "id": "1606298389316636686",
                "read_flag": "0",
                "status": "戴伟宸"
            },
            {
                "audit_user_id": "1606298389320830986",
                "comments": "1606298389320830992",
                "apply_user_id": "1606298389316636688",
                "updated_at": "2022-12-23 14:39:14",
                "data_version": "0",
                "created_at": "2022-12-23 14:39:14",
                "id": "1606298389316636687",
                "read_flag": "0",
                "status": "林昊然"
            },
            {
                "audit_user_id": "1606298389320830987",
                "comments": "1606298389320830993",
                "apply_user_id": "1606298389316636689",
                "updated_at": "2022-12-23 14:39:14",
                "data_version": "0",
                "created_at": "2022-12-23 14:39:14",
                "id": "1606298389316636688",
                "read_flag": "0",
                "status": "谭鹤轩"
            },
            {
                "audit_user_id": "1606298389320830988",
                "comments": "1606298389320830994",
                "apply_user_id": "1606298389316636690",
                "updated_at": "2022-12-23 14:39:14",
                "data_version": "0",
                "created_at": "2022-12-23 14:39:14",
                "id": "1606298389316636689",
                "read_flag": "0",
                "status": "洪浩宇"
            },
            {
                "audit_user_id": "1606298389320830989",
                "comments": "1606298389320830995",
                "apply_user_id": "1606298389316636691",
                "updated_at": "2022-12-23 14:39:14",
                "data_version": "0",
                "created_at": "2022-12-23 14:39:14",
                "id": "1606298389316636690",
                "read_flag": "0",
                "status": "马博文"
            },
            {
                "audit_user_id": "1606298389320830990",
                "comments": "1606298389320830996",
                "apply_user_id": "1606298389316636692",
                "updated_at": "2022-12-23 14:39:14",
                "data_version": "0",
                "created_at": "2022-12-23 14:39:14",
                "id": "1606298389316636691",
                "read_flag": "0",
                "status": "邵修杰"
            }
        ],
        "sql": "insert into friend_request (id, apply_user_id, audit_user_id, comments, status, read_flag, created_at, updated_at, data_version) values (1606298389316636672, 1606298389316636673, 1606298389316636673, '1606298389320830977', 许志泽, 0, '2022-12-23 14:39:14', '2022-12-23 14:39:14', 0);\ninsert into friend_request (id, apply_user_id, audit_user_id, comments, status, read_flag, created_at, updated_at, data_version) values (1606298389316636673, 1606298389316636674, 1606298389316636674, '1606298389320830978', 马旭尧, 0, '2022-12-23 14:39:14', '2022-12-23 14:39:14', 0);\ninsert into friend_request (id, apply_user_id, audit_user_id, comments, status, read_flag, created_at, updated_at, data_version) values (1606298389316636674, 1606298389316636675, 1606298389316636675, '1606298389320830979', 马浩轩, 0, '2022-12-23 14:39:14', '2022-12-23 14:39:14', 0);\ninsert into friend_request (id, apply_user_id, audit_user_id, comments, status, read_flag, created_at, updated_at, data_version) values (1606298389316636675, 1606298389316636676, 1606298389316636676, '1606298389320830980', 崔睿渊, 0, '2022-12-23 14:39:14', '2022-12-23 14:39:14', 0);\ninsert into friend_request (id, apply_user_id, audit_user_id, comments, status, read_flag, created_at, updated_at, data_version) values (1606298389316636676, 1606298389316636677, 1606298389316636677, '1606298389320830981', 龙修杰, 0, '2022-12-23 14:39:14', '2022-12-23 14:39:14', 0);\ninsert into friend_request (id, apply_user_id, audit_user_id, comments, status, read_flag, created_at, updated_at, data_version) values (1606298389316636677, 1606298389316636678, 1606298389320830976, '1606298389320830982', 严鹏煊, 0, '2022-12-23 14:39:14', '2022-12-23 14:39:14', 0);\ninsert into friend_request (id, apply_user_id, audit_user_id, comments, status, read_flag, created_at, updated_at, data_version) values (1606298389316636678, 1606298389316636679, 1606298389320830977, '1606298389320830983', 胡健柏, 0, '2022-12-23 14:39:14', '2022-12-23 14:39:14', 0);\ninsert into friend_request (id, apply_user_id, audit_user_id, comments, status, read_flag, created_at, updated_at, data_version) values (1606298389316636679, 1606298389316636680, 1606298389320830978, '1606298389320830984', 陆擎宇, 0, '2022-12-23 14:39:14', '2022-12-23 14:39:14', 0);\ninsert into friend_request (id, apply_user_id, audit_user_id, comments, status, read_flag, created_at, updated_at, data_version) values (1606298389316636680, 1606298389316636681, 1606298389320830979, '1606298389320830985', 蒋炫明, 0, '2022-12-23 14:39:14', '2022-12-23 14:39:14', 0);\ninsert into friend_request (id, apply_user_id, audit_user_id, comments, status, read_flag, created_at, updated_at, data_version) values (1606298389316636681, 1606298389316636682, 1606298389320830980, '1606298389320830986', 夏锦程, 0, '2022-12-23 14:39:14', '2022-12-23 14:39:14', 0);\ninsert into friend_request (id, apply_user_id, audit_user_id, comments, status, read_flag, created_at, updated_at, data_version) values (1606298389316636682, 1606298389316636683, 1606298389320830981, '1606298389320830987', 吕峻熙, 0, '2022-12-23 14:39:14', '2022-12-23 14:39:14', 0);\ninsert into friend_request (id, apply_user_id, audit_user_id, comments, status, read_flag, created_at, updated_at, data_version) values (1606298389316636683, 1606298389316636684, 1606298389320830982, '1606298389320830988', 熊驰, 0, '2022-12-23 14:39:14', '2022-12-23 14:39:14', 0);\ninsert into friend_request (id, apply_user_id, audit_user_id, comments, status, read_flag, created_at, updated_at, data_version) values (1606298389316636684, 1606298389316636685, 1606298389320830983, '1606298389320830989', 范胤祥, 0, '2022-12-23 14:39:14', '2022-12-23 14:39:14', 0);\ninsert into friend_request (id, apply_user_id, audit_user_id, comments, status, read_flag, created_at, updated_at, data_version) values (1606298389316636685, 1606298389316636686, 1606298389320830984, '1606298389320830990', 顾明杰, 0, '2022-12-23 14:39:14', '2022-12-23 14:39:14', 0);\ninsert into friend_request (id, apply_user_id, audit_user_id, comments, status, read_flag, created_at, updated_at, data_version) values (1606298389316636686, 1606298389316636687, 1606298389320830985, '1606298389320830991', 戴伟宸, 0, '2022-12-23 14:39:14', '2022-12-23 14:39:14', 0);\ninsert into friend_request (id, apply_user_id, audit_user_id, comments, status, read_flag, created_at, updated_at, data_version) values (1606298389316636687, 1606298389316636688, 1606298389320830986, '1606298389320830992', 林昊然, 0, '2022-12-23 14:39:14', '2022-12-23 14:39:14', 0);\ninsert into friend_request (id, apply_user_id, audit_user_id, comments, status, read_flag, created_at, updated_at, data_version) values (1606298389316636688, 1606298389316636689, 1606298389320830987, '1606298389320830993', 谭鹤轩, 0, '2022-12-23 14:39:14', '2022-12-23 14:39:14', 0);\ninsert into friend_request (id, apply_user_id, audit_user_id, comments, status, read_flag, created_at, updated_at, data_version) values (1606298389316636689, 1606298389316636690, 1606298389320830988, '1606298389320830994', 洪浩宇, 0, '2022-12-23 14:39:14', '2022-12-23 14:39:14', 0);\ninsert into friend_request (id, apply_user_id, audit_user_id, comments, status, read_flag, created_at, updated_at, data_version) values (1606298389316636690, 1606298389316636691, 1606298389320830989, '1606298389320830995', 马博文, 0, '2022-12-23 14:39:14', '2022-12-23 14:39:14', 0);\ninsert into friend_request (id, apply_user_id, audit_user_id, comments, status, read_flag, created_at, updated_at, data_version) values (1606298389316636691, 1606298389316636692, 1606298389320830990, '1606298389320830996', 邵修杰, 0, '2022-12-23 14:39:14', '2022-12-23 14:39:14', 0);"
    },
    "msg": "一切ok"
}
```
