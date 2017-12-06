## 进程保活研究 ##

　　监听系统广播实现自启动service

　　KeepAliveManager创建时向系统发送时钟任务。监听对应的action来接受广播，在广播中检测service，如果被杀死则再次启动service。