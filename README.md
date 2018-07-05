# portal

## 项目介绍

### 网关

### 统一接入形式，为业务线开发加速

```mermaid
graph LR
A[统一接口] -->B[研发流程]
B --> C[API动态发布]
```

通用接口缓存服务

服务解耦

服务端快速向调用方输出业务能力

集中API治理

### 普通开发流程

```sequence
前端->restful: 接口调用
restful->restful: 请求校验/参数校验
Note over restful: 80%重复工作
restful-->herd: 请求无效
restful->service: 请求
service->biz: 业务调用，数据组装
biz->dal: 业务组装查询...
dal-->restful: result
restful-->前端: result

```

### 接入后开发流程：

```sequence
前端->gateway: 接口调用
gateway->gateway: 请求校验/参数规则校验
Note over gateway: 无需开发
gateway-->前端: 请求无效
gateway->service: 调用接口
service->biz: call
biz->dal: ORMapping
dal-->gateway: result
gateway-->前端: 请求结束


```

### 开发工作减少



![https://zakied.oss-cn-qingdao.aliyuncs.com/%E5%BC%80%E5%8F%91%E5%B7%A5%E4%BD%9C%E9%87%8F.png](https://zakied.oss-cn-qingdao.aliyuncs.com/%E5%BC%80%E5%8F%91%E5%B7%A5%E4%BD%9C%E9%87%8F.png)

![业务形态](https://zakied.oss-cn-qingdao.aliyuncs.com/%E4%B8%9A%E5%8A%A1%E5%BD%A2%E6%80%81.png)

#### 解决的问题

##### 服务端

1. 参数验证（参数规则、格式、数据）
2. 接口定义
3. 服务降级、接口及时上线下线
4. 接口并发控制（待定义）
5. 缓存
6. 自动生成文档
7. 接口调试，开发ut
8. 问题分析
9. 调用记录
10. 异常捕捉通知
11. 接口版本控制
12. 请求加解密
13. ...

##### 前端

1. mock接口定义
2. 接口验证调试
3. 文档查看
4. ...



## 网关功能

### 接口隔离

​	将接口分组，1个分组配置多个接口。

​	业务场景

​		一入口多项目

### 功能列表

| 功能          | dubbo   | http |
| ------------- | ------- | ---- |
| 参数校验      | product | ×    |
| 通用          | product | ×    |
| 接口上下线    | product | ×    |
| 接口测试      | product | ×    |
| 指定调度      | product | ×    |
| 负载均衡      | product | ×    |
| 版本控制      | product | ×    |
| 在线调试      | product | ×    |
| lock控制      | test    | ×    |
| 应用隔离      | test    | ×    |
| mock          | test    | test |
| 异常捕捉/通知 | ×       | ×    |
| 自动生成文档  | ×       | ×    |
| 调用记录      | ×       | ×    |
| 问题分析      | ×       | ×    |
| 重定向        | ×       |      |
| 请求加解密    | ×       |      |



### 参数类型

| 参数类型    | dubbo   |
| ----------- | ------- |
| String      | product |
| Integer     | product |
| Float       | product |
| Double      | product |
| Long        | product |
| Boolean     | product |
| Date        | product |
| List<Any>   | product |
| Object<Any> | product |
| Set         | ×       |
| 基本类型    | ×       |



### 参数校验/功能

| 功能                             | 支持程度 |
| -------------------------------- | -------- |
| 为空校验                         | product  |
| 默认值                           | product  |
| 必传                             | product  |
| 长度                             | product  |
| 正则校验                         | test     |
| json对象格式校验                 | ×        |
| 接口校验，例如：对象是否存在等等 | ×        |



### 取值范围

| 功能        | 支持程度        |
| ----------- | --------------- |
| bizParams   | product（推荐） |
| RequestBody | product         |
| URL         | product         |
| header      | product         |
| restful     | ×               |

### mock

接口定义，秒级切换

```flow
st=>start: 前端调用
cond=>condition: 接口是否上线
sub1=>subroutine: mock数据
io=>inputoutput: 后端业务服务
st(right)->cond(right)->cond
cond(yes)->io(bottom)
cond(no)->sub1(right)->op

```



### 参数透传

### cookie透传

## 接口调用规范

```支持POST,GET,DELETE,PUT```



### 统一入口

```/gateway.{format}```

{format}支持格式，JSON

请求参数：

| 参数名        | 参数类型 | require | 描述                                                         |
| ------------- | -------- | ------- | ------------------------------------------------------------ |
| method        | String   | Y       | 请求接口key                                                  |
| version       | String   | Y       | 接口版本号                                                   |
| bizParams     | JSON     | N       | 参数对象                                                     |
| format        | String   | N       | response格式，默认为JSON                                     |
| sso           | String   | N       | 单点登录key，透传                                            |
| timestamp     | Long     | N       | 时间戳                                                       |
| client        | String   | N       | 客户端类型收集，统计分析                                     |
| clientVersion | String   | N       | 客户端版本收集，统计分析                                     |
| router        | String   | N       | 指定路由，生产环境不推荐使用，不添加该参数时默认负载均衡（调试使用） |
| sign          | String   | N       | 签名（扩展安全使用）                                         |



### 统一error处理

正常时为无侵入，接口返回什么是什么。

异常时

| code   | 描述                         |
| ------ | ---------------------------- |
| 444446 | 方法未开放                   |
| 444447 | 方法不存在                   |
| 400000 | 重复提交                     |
| 300000 | 请求参数转换异常             |
| 300002 | 参数返回类型错误             |
| 300001 | 调用服务error                |
| 100001 | 调用服务error                |
|        | 未在注册中心发现该接口       |
|        | 发现服务提供者，未发现该方法 |





## 侵入性

## 性能影响

### 延迟性

方法短期内第一次调用：2~5ms延迟

### 吞吐量

