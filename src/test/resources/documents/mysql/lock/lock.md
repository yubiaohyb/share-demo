# 锁篇
## 总览
![](./lock-overview.png)

## 表级锁
查看表级锁争用状态
![](./lock-status-table-level.png)

表锁表现形式
* 共享读锁
* 排他写锁

### 操作
    手动加表锁
    lock table 表名称 read(write),表名称2 read(write)，其他;
    
    查看表锁情况
    show open tables;

    移除表锁
    unlock tables;
 

### 演示 //todo


