# ChattingRoom
多功能聊天室，包括消息的在线收发、离线转储；好友系统；消息提醒等功能。
### 客户端程序
ChatClient
### 服务端程序
ChatServer
### 注意事项
项目使用mysql数据库存储用户信息，在使用前需创建相应数据表，并修改项目中db.properties配置文件中的数据库连接信息。
建表：
```sql
DROP TABLE users;
CREATE TABLE users(
	qq INT PRIMARY KEY,
	username VARCHAR(20) NOT NULL,
	PASSWORD VARCHAR(20) NOT NULL
)
DROP TABLE ur;
CREATE TABLE UR(
	qs INT NOT NULL,
	qo INT NOT NULL,
	FOREIGN KEY (qs) REFERENCES users(qq),
	FOREIGN KEY (qo) REFERENCES users(qq)
)
```
