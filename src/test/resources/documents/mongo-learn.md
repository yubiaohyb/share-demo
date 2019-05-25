## mongo 学习
参考文档：https://www.runoob.com/mongodb/mongodb-dropdatabase.html

---
### 1.建库、表（集合）
````
> show dbs
admin  0.000GB
local  0.000GB
> use student
switched to db student
> db.student.insert({"name":"孙建运"})
WriteResult({ "nInserted" : 1 })
> show dbs
admin    0.000GB
local    0.000GB
student  0.000GB
> db.dropDatabase()
{ "dropped" : "student", "ok" : 1 }
> show dbs

admin  0.000GB
local  0.000GB
> 
> use student
switched to db student
> show dbs
admin  0.000GB
local  0.000GB
> db.createCollection("grade")
{ "ok" : 1 }
> show tables
grade
> db.grade.drop()
true
> show tables
> 

建表的的一些小实验

> db.student.insert({"name":"孙建运"})
WriteResult({ "nInserted" : 1 })
> show tables
student
> show dbs
admin    0.000GB
local    0.000GB
student  0.000GB
> db.student.grade.insert({"grade":"老年班"})
WriteResult({ "nInserted" : 1 })
> show tables
student
student.grade
> db.parents.insert({"name":"谁知道","sex":"male"})
WriteResult({ "nInserted" : 1 })
> show tables
parents
student
student.grade
> db.createCollection("score")
{ "ok" : 1 }
> show tables
parents
score
student
student.grade
> 
> show collections
parents
score
student
student.grade
> 

设置建表（集合）参数

> db.createCollection("mycol", { capped : true, autoIndexId : true, size : 
   6142800, max : 10000 } )
{ "ok" : 1 }
>
````

### 2.文档操作
````
查询文档

find() 方法以非结构化的方式来显示所有文档。
db.collection.find(query, projection)
参数说明：
query ：可选，使用查询操作符指定查询条件
projection ：可选，使用投影操作符指定返回的键。查询时返回文档中所有键值， 只需省略该参数即可（默认省略）。

pretty() 方法以格式化的方式来显示所有文档

MongoDB AND 条件

>db.col.find({key1:value1, key2:value2}).pretty()

MongoDB OR 条件

>db.col.find(
   {
      $or: [
         {key1: value1}, {key2:value2}
      ]
   }
).pretty()

MongoDB Limit与Skip方法
skip()方法默认参数为 0 。
>db.COLLECTION_NAME.find().limit(NUMBER)
>db.COLLECTION_NAME.find().limit(NUMBER).skip(NUMBER)

在 MongoDB 中使用 sort() 方法对数据进行排序，sort() 方法可以通过参数指定排序的字段，并使用 1 和 -1 来指定排序的方式，其中 1 为升序排列，而 -1 是用于降序排列。
>db.COLLECTION_NAME.find().sort({KEY:1})

插入文档

> db.parents.find()
{ "_id" : ObjectId("5ce8fbdb42c840f836dd0935"), "name" : "谁知道", "sex" : "male" }
> document = ({"name":"孙建运他妈", "sex":"female", "studentName":"孙建运", "membership":"mother-child"});
{
	"name" : "孙建运他妈",
	"sex" : "female",
	"studentName" : "孙建运",
	"membership" : "mother-child"
}
> db.parents.find()
{ "_id" : ObjectId("5ce8fbdb42c840f836dd0935"), "name" : "谁知道", "sex" : "male" }
> db.parents.insert(document)
WriteResult({ "nInserted" : 1 })
> db.parents.find()
{ "_id" : ObjectId("5ce8fbdb42c840f836dd0935"), "name" : "谁知道", "sex" : "male" }
{ "_id" : ObjectId("5ce8ffae42c840f836dd0936"), "name" : "孙建运他妈", "sex" : "female", "studentName" : "孙建运", "membership" : "mother-child" }
> 

save操作保存或更新文档（看是否指定 _id）
insertMany/insertOne 区分插入一个或多个文档

> db.parents.find()
{ "_id" : ObjectId("5ce8fbdb42c840f836dd0935"), "name" : "谁知道", "sex" : "male" }
{ "_id" : ObjectId("5ce8ffae42c840f836dd0936"), "name" : "孙建运他妈", "sex" : "female", "studentName" : "孙建运", "membership" : "mother-child" }
> db.parents.save({ "_id" : ObjectId("5ce8ffae42c840f836dd0936"), "name" : "孙建运的妈", "sex" : "female", "studentName" : "孙建运", "membership" : "mother-child" })
WriteResult({ "nMatched" : 1, "nUpserted" : 0, "nModified" : 1 })
> db.parents.insertMany([{"name" : "谁知道2", "sex" : "male" }, {"name" : "谁知道3", "sex" : "male" }])
{
	"acknowledged" : true,
	"insertedIds" : [
		ObjectId("5ce901bb42c840f836dd0937"),
		ObjectId("5ce901bb42c840f836dd0938")
	]
}
> db.parents.find()
{ "_id" : ObjectId("5ce8fbdb42c840f836dd0935"), "name" : "谁知道", "sex" : "male" }
{ "_id" : ObjectId("5ce8ffae42c840f836dd0936"), "name" : "孙建运的妈", "sex" : "female", "studentName" : "孙建运", "membership" : "mother-child" }
{ "_id" : ObjectId("5ce901bb42c840f836dd0937"), "name" : "谁知道2", "sex" : "male" }
{ "_id" : ObjectId("5ce901bb42c840f836dd0938"), "name" : "谁知道3", "sex" : "male" }
> 

更新文档

db.collection.update(
   <query>,
   <update>,
   {
     upsert: <boolean>,
     multi: <boolean>,
     writeConcern: <document>
   }
)
参数说明：
query : update的查询条件，类似sql update查询内where后面的。
update : update的对象和一些更新的操作符（如$,$inc...）等，也可以理解为sql update查询内set后面的
upsert : 可选，这个参数的意思是，如果不存在update的记录，是否插入objNew,true为插入，默认是false，不插入。
multi : 可选，mongodb 默认是false,只更新找到的第一条记录，如果这个参数为true,就把按条件查出来多条记录全部更新。
writeConcern :可选，抛出异常的级别

>db.col.update({'title':'MongoDB 教程'},{$set:{'title':'MongoDB'}},{multi:true})

db.collection.save(
   <document>,
   {
     writeConcern: <document>
   }
)
参数说明：
document : 文档数据。
writeConcern :可选，抛出异常的级别。

删除文档

db.collection.remove(
   <query>,
   {
     justOne: <boolean>,
     writeConcern: <document>
   }
)

参数说明：
query :（可选）删除的文档的条件。
justOne : （可选）如果设为 true 或 1，则只删除一个文档，如果不设置该参数，或使用默认值 false，则删除所有匹配条件的文档。
writeConcern :（可选）抛出异常的级别。

remove() 方法已经过时了，现在官方推荐使用 deleteOne() 和 deleteMany() 方法。

删除集合下全部文档
db.inventory.deleteMany({})

删除 status 等于 A 的全部文档
db.inventory.deleteMany({ status : "A" })

删除 status 等于 D 的一个文档
db.inventory.deleteOne( { status: "D" } )

emove() 方法 并不会真正释放空间。
需要继续执行 db.repairDatabase() 来回收磁盘空间。
> db.repairDatabase()
或者
> db.runCommand({ repairDatabase: 1 })


````


MongoDB 与 RDBMS Where 语句比较

|操作|格式|范例|RDBMS中的类似语句|
|--|--|--|--|
|等于|{<key>:<value>}|db.col.find({"by":"菜鸟教程"}).pretty()|where by = '菜鸟教程'|
|小于|{<key>:{$lt:<value>}}|db.col.find({"likes":{$lt:50}}).pretty()|where likes < 50|
|小于或等于|{<key>:{$lte:<value>}}|db.col.find({"likes":{$lte:50}}).pretty()|where likes <= 50|
|大于|{<key>:{$gt:<value>}}|db.col.find({"likes":{$gt:50}}).pretty()|where likes > 50|
|大于或等于|{<key>:{$gte:<value>}}|db.col.find({"likes":{$gte:50}}).pretty()|where likes >= 50|
|不等于|{<key>:{$ne:<value>}}|db.col.find({"likes":{$ne:50}}).pretty()|where likes != 50|


### 3.进阶
索引是特殊的数据结构，索引存储在一个易于遍历读取的数据集合中，索引是对数据库表中一列或多列的值进行排序的一种结构。
>db.collection.createIndex(keys, options)

>db.col.createIndex({"title":1,"description":-1})

MongoDB 聚合
>db.COLLECTION_NAME.aggregate(AGGREGATE_OPERATION)

详细：https://www.runoob.com/mongodb/mongodb-aggregate.html


MongoDB 副本集
![](https://www.runoob.com/wp-content/uploads/2013/12/replication.png)
````
设置副本集合
mongod --port "PORT" --dbpath "YOUR_DB_DATA_PATH" --replSet "REPLICA_SET_INSTANCE_NAME"

mongod --port 27017 --dbpath "D:\set up\mongodb\data" --replSet rs0

在Mongo客户端使用命令rs.initiate()来启动一个新的副本集。
我们可以使用rs.conf()来查看副本集的配置
查看副本集状态使用 rs.status() 命令

副本集添加成员
>rs.add(HOST_NAME:PORT)

>rs.add("mongod1.net:27017")

MongoDB中你只能通过主节点将Mongo服务添加到副本集中， 判断当前运行的Mongo服务是否为主节点可以使用命令db.isMaster() 。
MongoDB的副本集与我们常见的主从有所不同，主从在主机宕机后所有服务将停止，而副本集在主机宕机后，副本会接管主节点成为主节点，不会出现宕机的情况。

````

MongoDB 分片
![](https://www.runoob.com/wp-content/uploads/2013/12/sharding.png)
详细：https://www.runoob.com/mongodb/mongodb-sharding.html

MongoDB 备份(mongodump)与恢复(mongorestore)
详细：https://www.runoob.com/mongodb/mongodb-mongodump-mongorestore.html

MongoDB 监控
详细：https://www.runoob.com/mongodb/mongodb-mongostat-mongotop.html








