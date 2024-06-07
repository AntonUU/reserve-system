# reserve-system(出入预约系统)

> 本系统借鉴与**平安西大公众号**校门管理模块进行开发

## 当前项目辅助开发工具类使用: 
    人人开源代码生成器模板 https://gitee.com/renrenio/renren-generator
    人人开源工具类 https://gitee.com/renrenio/renren-fast

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
    1) 用户提交预约表信息 [ReserveAppRequest.java](reserve-system%2Fsrc%2Fmain%2Fjava%2Fcn%2Fanton%2Freservesystem%2Frequest%2FReserveAppRequest.java)
        --- 被访人选填 [VisitInfoRequest.java](reserve-system%2Fsrc%2Fmain%2Fjava%2Fcn%2Fanton%2Freservesystem%2Frequest%2FVisitInfoRequest.java)
    2) 用户得到等待受理通知
        2.1)  将表单数据转为Json字符串
        2.2)  通过KafkaProducer发送给KafkaBroker
        2.3)  通知用户 "已预约, 等待受理"
        2.4)  KafkaConsumer收到消息
            1. 将json字符串转成对象
            2. 将对象copy成对应实体类
            3. 分别保存到对应表中
            4. 将json字符串保存到redis中, 方便用户查看预约状态
> **ps: 这里建议将生产与消费分别部署, 因本项目作为课程作业不想过多处理统一写在一个项目中。**
        
        
    
