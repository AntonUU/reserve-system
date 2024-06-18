# reserve-system(出入预约系统)

> 本系统借鉴与**平安西大公众号**校门管理模块进行开发
>
> ​	**由于本项目只为了应付作业和个人时间问题，本项目只写核心功能与API。**
>
> ​	**剩下功能只是一些增删改查的基本功能， 有需要可自行补全。**
>
> 开发集成环境
>
> ​	MacOS 14.5 (23F79)
>
> ​	IntelliJ IDEA 2024.1
>
> ​	Navicat Premium 17
>
> ​	Redis Insight 2.50

## 当前项目辅助开发工具类使用: 

    人人开源代码生成器模板 https://gitee.com/renrenio/renren-generator
    人人开源工具类 https://gitee.com/renrenio/renren-fast
    小程序端: https://github.com/AntonUU/reserve-system-uniapp
    后台端: https://github.com/AntonUU/reserve-back



## 技术栈

`````text
JDK8
MySQL@8.x
Redis@6.2
Kafka@2.13-3.3.1
Mybatis-Plus@3.x
`````



## 基本数据表拟订:

```text
预约表 `tab_reserve`
	—-	个人/单位预约（单位预约  填写联系电话即可） 
	—-	开始日期
	—-	结束日期
	—-	预约人姓名
	—-	预约人性别
	—-	预约人手机号
	—-	预约人身份证号
	—-	人脸图
	—-	进出校门

被拜访信息 `tab_visit`
	—-	被拜访人姓名
	—-	被拜访人联系电话
	—-	被访人单位
	—-	随行人员
	—-	拜访事由

车辆预约 `tab_cat`
	—-	申请人
	—-	申请人单位
	—-	车辆号
	—-	联系电话
	—-	开始日期
	—-	结束日期
	—-	进出校门

预约管理员 `tab_admin`
	—-	管理员部门
	—-	管理员姓名
	—-	管理员电话
	—-	管理员权限
		—-	所有      111
		—-	读写      11
		—- 	读        1
```



预约功能流程 /reserve/api/reserve

    1) 用户提交预约表信息(被访人信息选填)
    2) 用户得到等待受理通知
            1) 将表单数据转为Json字符串
            2) 通过KafkaProducer发送给KafkaBroker 
            3) 通知用户 "已预约, 等待受理"
            4) KafkaConsumer收到消息
                      1) 将json字符串转成对象
                      2) 将对象copy成对应实体类
                      3) 分别保存到对应表中
                      4) 将json字符串保存到redis中设置ttl, 用于用户查看预约状态与键过期监听取消门禁授权

> [!NOTE]
>
> 这里建议将生产与消费分别部署, 因本项目作为课程作业不想过多处理统一写在一个项目中。

  

预约过期取消授权功能流程

```text
redis key = 预约类型 + 预约id + 预约信息(车牌或手机) + 过期时间 + 预约时时间戳

1) 监听到键过期
		1. 截取0与1, 获得预约类型与预约id
		2. 去相应表中修改预约状态码
```





