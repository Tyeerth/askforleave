> 请销假系统
>
>  html+ajax+java+mysql实现
> 几乎纯ajax实现数据传递与获取
> 针对个人或组织用户
> 登录账号:xtmxzzb密码:xtmxzzb
>
> 数据库等配置文件：WEB-INF下db.properties(字段含义见附录)

[![img](https://github.com/Dreamlu/askforleave/raw/master/askForLeave/p1.png)](https://github.com/Dreamlu/askforleave/blob/master/askForLeave/p1.png)

特点：

***1.短信发送(请假或销假完成后发送短信)***

***2.短信回复(或发送给该系统)任务监听***

***3.核心：请假逻辑的实现(人员信息的录入、增删改查、审核、历史等等)***

***4.信息excel数据录入，查询结果导出excel***

***5.多选操作的增(共同领导的添加)、删(人员数据)、批量审核等***

***6.根据录入的信息，搜索姓名(重名处理)，展示结果，对于请假的天数规则判断(这里没有很好的抽取出来，不同地区请假规则基本不同)，请假结果打印成pdf***

***7.分页等等 ~自己保存下代码用的，勿要介意(省得写的项目又删除了不备份)***
***8.更新一个小弹框：每次登陆时提示"昨天应到假但未销假的人员信息"，如都已经销假，则不提示***

注意：

1.其中短信服务用的阿里云

2.自己用需要替换成相应阿里服务的AK信息(阿里服务接口)，常规短信发送在com.leave.service包中leavePasswdService.java中 定时任务进程短信发送和监听进程短信接受(上行短信)分别在com.leave.util包中的TimerMessage.java和ReceiveDemo.java中

3.短信发送中还要替换相应短信签名和模板，具体查看阿里云短信服务帮助文档

4.导入数据库文件(navicat导出的leave.sql文件，导入失败可以自行直接运行源码)后，leave_setup表前两个有用,尽量通过界面删除无用数据

修改项目名注意事项：(更新见附录)
~~1.config包下LoginFilter.java中response.sendRedirect("/askForLeave/login.html");项目名修改~~

 ~~2.util包下DBToExcel.java中rout.write("/askForLeave/upload/"+name);//返回一个路径提供访问下载,项目名需修改~~ 

~~3.util包下Find_History_DBToExcel.java中rout.write("/askForLeave/upload/"+name);//返回一个路径提供访问下载,项目名需修改~~

附录：

db.properties

> `db.drivername=com.mysql.jdbc.Driver`
>
> `db.url=jdbc:mysql://127.0.0.1:3306/leave?userUnicode=true&characterEncoding=utf-8`
>
> `db.username=root`//数据库账号
>
> `db.password=`//密码
>
> `db.pageListNum=6`//分页的每页记录基本数量
>
> `db.name1=xtmxzzb`//项目登录名
>
> `db.progectName=askForLeave`//项目名，修改项目名时替换
>
> `db.accessKeyId=`//AK信息，短信服务用到
>
> `db.accessKeySecret=`
>
> `db.queueName=Alicom-Queue-xxxxxxxx-SmsUp`//短信上行消息接收，xxxxx替换成自己的信息，用来接收发给系统的信息
>
> `db.signName=`//短信签名