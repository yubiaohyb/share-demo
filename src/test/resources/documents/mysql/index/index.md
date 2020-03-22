# 索引篇
## 是什么
    一种帮助mysql高效查询记录的数据结构
    
### 分类
* 单列索引
* 组合索引
* 全文索引
* 空间索引
* 位图索引    

## 为什么
### 优点
    * 提高检索效率，降低IO耗时 -- 检索
    * 降低排序成本（索引列）    -- 排序
    
### 缺点
    * 额外占用磁盘空间
    * 写的效率会降低
    
### 原理分析
索引是在存储引擎中实现的，不同的存储引擎会使用不同的索引
#### 基础存储结构
* B-树（即B树）
* B+树    

#### 具体实现
* 非聚集索引
    * 主键索引
    * 辅助索引（次要索引） 
* 聚集索引
    * 主键索引
    * 辅助索引（次要索引）
    
配图（略）
       
## 怎么用
### 创建
> 单列普通索引<br>
  CREATE INDEX index_name ON table(column(length));<br>
  ALTER TABLE table_name ADD INDEX index_name(column(length));<br>
  单列唯一索引<br>
  CREATE UNIQUE INDEX index_name ON table(column(length));<br>
  alter table table_name add unique index index_name(column);<br>
  单列全文索引<br>
  CREATE FULLTEXT INDEX index_name ON table(column(length));<br>
  alter table table_name add fulltext index_name(column);<br>
  组合索引<br>
  ALTER TABLE article ADD INDEX index_titme_time(title(50),time(10));<br>
  
### 删除
> DROP INDEX index_name ON table

### 查看
> SHOW INDEX FROM table_name



