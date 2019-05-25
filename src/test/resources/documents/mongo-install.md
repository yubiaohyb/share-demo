## MongoDB安装
参照来源：https://www.cnblogs.com/web424/p/6928992.html

### 1.手动创建MongoDB yum源文件
添加源文件
````
vim /etc/yum.repos.d/mongodb-org-3.4.repo
````
配置源信息
````
[mongodb-org-3.4]  
name=MongoDB Repository  
baseurl=https://repo.mongodb.org/yum/redhat/$releasever/mongodb-org/3.4/x86_64/  
gpgcheck=1  
enabled=1  
gpgkey=https://www.mongodb.org/static/pgp/server-3.4.asc
````
其中gpgcheck可以设置为0，关闭gpg验证。<br>
升级所有包
````
[root@yubiaohyb yum.repos.d]# yum update
已加载插件：fastestmirror
Determining fastest mirrors
epel/x86_64/metalink                                     |  18 kB     00:00     
 * base: repo1.sea.innoscale.net
 ...   
 mongodb-org-3.4                                          | 2.5 kB     00:00     
 ...
更新完毕:
  device-mapper.x86_64 7:1.02.149-10.el7_6.7                                    
  ...                                                
完毕！
[root@yubiaohyb yum.repos.d]# 
````

### 2.安装MongoDB
````
[root@yubiaohyb yum.repos.d]# yum -y install mongodb-org
已加载插件：fastestmirror
Loading mirror speeds from cached hostfile
 * base: repo1.sea.innoscale.net
 * elrepo: ftp.osuosl.org
 ...
已安装:
  mongodb-org.x86_64 0:3.4.20-1.el7                                             

作为依赖被安装:
  mongodb-org-mongos.x86_64 0:3.4.20-1.el7                                      
  mongodb-org-server.x86_64 0:3.4.20-1.el7                                      
  mongodb-org-shell.x86_64 0:3.4.20-1.el7                                       
  mongodb-org-tools.x86_64 0:3.4.20-1.el7                                       

完毕！
[root@yubiaohyb yum.repos.d]# 
````

#### 查看MongoDB安装位置
````
[root@yubiaohyb yum.repos.d]# whereis mongod
mongod: /usr/bin/mongod /etc/mongod.conf /usr/share/man/man1/mongod.1
````

### 3.启动或关闭MongoDB
````
[root@yubiaohyb db]# systemctl start  mongod.service
[root@yubiaohyb db]# ps -ef | grep mongod
mongod    8212     1  9 14:11 ?        00:00:00 /usr/bin/mongod -f /etc/mongod.conf
root      8239 13798  0 14:11 pts/1    00:00:00 grep --color=auto mongod
[root@yubiaohyb db]# 
[root@yubiaohyb db]# systemctl stop  mongod.service
[root@yubiaohyb db]# ps -ef | grep mongod
root      8258 13798  0 14:13 pts/1    00:00:00 grep --color=auto mongod
[root@yubiaohyb db]# 
````

#### 查看MongoDB状态
````
[root@yubiaohyb db]# systemctl status mongod.service
● mongod.service - High-performance, schema-free document-oriented database
   Loaded: loaded (/usr/lib/systemd/system/mongod.service; enabled; vendor preset: disabled)
   Active: active (running) since 六 2019-05-25 14:16:43 CST; 1min 54s ago
     Docs: https://docs.mongodb.org/manual
  Process: 8298 ExecStart=/usr/bin/mongod $OPTIONS (code=exited, status=0/SUCCESS)
  Process: 8297 ExecStartPre=/usr/bin/chmod 0755 /var/run/mongodb (code=exited, status=0/SUCCESS)
  Process: 8294 ExecStartPre=/usr/bin/chown mongod:mongod /var/run/mongodb (code=exited, status=0/SUCCESS)
  Process: 8292 ExecStartPre=/usr/bin/mkdir -p /var/run/mongodb (code=exited, status=0/SUCCESS)
 Main PID: 8304 (mongod)
   CGroup: /system.slice/mongod.service
           └─8304 /usr/bin/mongod -f /etc/mongod.conf

5月 25 14:16:42 yubiaohyb systemd[1]: Starting High-performance, schema-fr.....
5月 25 14:16:42 yubiaohyb mongod[8298]: about to fork child process, waitin....
5月 25 14:16:42 yubiaohyb mongod[8298]: forked process: 8304
5月 25 14:16:43 yubiaohyb systemd[1]: Started High-performance, schema-fre...e.
Hint: Some lines were ellipsized, use -l to show in full.
[root@yubiaohyb db]# 

````

### 4.启动 mongo shell
````
[root@yubiaohyb db]# mongo
MongoDB shell version v3.4.20
connecting to: mongodb://127.0.0.1:27017
MongoDB server version: 3.4.20
Welcome to the MongoDB shell.
For interactive help, type "help".
For more comprehensive documentation, see
	http://docs.mongodb.org/
Questions? Try the support group
	http://groups.google.com/group/mongodb-user
Server has startup warnings: 
2019-05-25T14:16:42.700+0800 I STORAGE  [initandlisten] 
2019-05-25T14:16:42.700+0800 I STORAGE  [initandlisten] ** WARNING: Using the XFS filesystem is strongly recommended with the WiredTiger storage engine
2019-05-25T14:16:42.700+0800 I STORAGE  [initandlisten] **          See http://dochub.mongodb.org/core/prodnotes-filesystem
2019-05-25T14:16:43.238+0800 I CONTROL  [initandlisten] 
2019-05-25T14:16:43.238+0800 I CONTROL  [initandlisten] ** WARNING: Access control is not enabled for the database.
2019-05-25T14:16:43.238+0800 I CONTROL  [initandlisten] **          Read and write access to data and configuration is unrestricted.
2019-05-25T14:16:43.238+0800 I CONTROL  [initandlisten] 
2019-05-25T14:16:43.239+0800 I CONTROL  [initandlisten] 
2019-05-25T14:16:43.239+0800 I CONTROL  [initandlisten] ** WARNING: /sys/kernel/mm/transparent_hugepage/enabled is 'always'.
2019-05-25T14:16:43.239+0800 I CONTROL  [initandlisten] **        We suggest setting it to 'never'
2019-05-25T14:16:43.239+0800 I CONTROL  [initandlisten] 
> show dbs
admin  0.000GB
local  0.000GB
> 
````

### 5.开放 MongoDB 远程访问
````
[root@yubiaohyb db]# vim /etc/mongod.conf 
...
# network interfaces
net:
  port: 27017
  #bindIp: 127.0.0.1  # Listen to local interface only, comment to listen on all interfaces.
...
[root@yubiaohyb db]# systemctl restart mongod.service
[root@yubiaohyb db]# 
````
---
### 6.问题
MongoDB启动提示文件夹路径不存在
````
[root@yubiaohyb bin]# mongod
2019-05-25T13:49:55.312+0800 I CONTROL  [initandlisten] MongoDB starting : pid=7911 port=27017 dbpath=/data/db 64-bit host=yubiaohyb
2019-05-25T13:49:55.312+0800 I CONTROL  [initandlisten] db version v3.4.20
2019-05-25T13:49:55.312+0800 I CONTROL  [initandlisten] git version: 447847d93d6e0a21b018d5df45528e815c7c13d8
2019-05-25T13:49:55.312+0800 I CONTROL  [initandlisten] OpenSSL version: OpenSSL 1.0.1e-fips 11 Feb 2013
2019-05-25T13:49:55.312+0800 I CONTROL  [initandlisten] allocator: tcmalloc
2019-05-25T13:49:55.312+0800 I CONTROL  [initandlisten] modules: none
2019-05-25T13:49:55.312+0800 I CONTROL  [initandlisten] build environment:
2019-05-25T13:49:55.312+0800 I CONTROL  [initandlisten]     distmod: rhel70
2019-05-25T13:49:55.312+0800 I CONTROL  [initandlisten]     distarch: x86_64
2019-05-25T13:49:55.312+0800 I CONTROL  [initandlisten]     target_arch: x86_64
2019-05-25T13:49:55.312+0800 I CONTROL  [initandlisten] options: {}
2019-05-25T13:49:55.313+0800 I STORAGE  [initandlisten] exception in initAndListen: 29 Data directory /data/db not found., terminating
2019-05-25T13:49:55.313+0800 I NETWORK  [initandlisten] shutdown: going to close listening sockets...
2019-05-25T13:49:55.313+0800 I NETWORK  [initandlisten] shutdown: going to flush diaglog...
2019-05-25T13:49:55.313+0800 I CONTROL  [initandlisten] now exiting
2019-05-25T13:49:55.313+0800 I CONTROL  [initandlisten] shutting down with code:100
````
解决办法：
手动创建路径
或者
mongod --dbpath /Users/pantao/Workspace/MongoDB/db 重新指定存储数据路径

设置开机启动
````
[root@yubiaohyb db]# systemctl enable mongod.service
````

关闭防火墙
````
systemctl stop firewalld.service #停止firewall
systemctl disable firewalld.service #禁止firewall开机启动
````

