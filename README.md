# simple-admin
开箱即用，简单的单体应用模板，适用于小型项目快速开发

### 介绍
- 本项目定位就是小型项目，需要快速开发的单体项目
- 基于Mybatis-Plus自定义代码生成工具
- 包含完善的JWT鉴权、RBAC权限控制、API权限和菜单权限配置
- 服务端使用SpringBoot web端采用Vue，可将web打包在一个项目

### 环境
- jdk 1.8
- mysql 5.7+
- node 17
- yarn 1.22

### 代码生成器
##### 代码生成器可一键生成服务端代码以及web端代码，包含增删改查
##### 配置
在*CodeGeneratorSupport.java*中配置数据库；**注意：如果包名或项目名称修改，需要自己调整该项目类中的包路径配置**
##### 生成
工具类在*CodeGenerator.java* 填入tableName和entityName，运行main方法即可
