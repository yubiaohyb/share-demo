share-demo
====
个人学习分享<br>
项目使用springboot2.0.4为基础框架，jdk1.8作为运行环境。
### *内容简介*
>#### 注解
* [@NotNullLabel](#####@NotNullLabel) 对@NotNull进行了优化，只需指定属性名即可
* [@DateEndTime](#@DateEndTime) 将页面请求传递的日期参数（yyyy-MM-dd）转换为Date类型值（yyyy-MM-dd 23:59:59.999）
* [@ExcelColumn](#@ExcelColumn) 标记对象属性在excel中对应的列标题，用于简化excel生成逻辑
* [@ResponseHeader](#@ResponseHeader) 自定义响应头，用于减少文件下载时响应头部信息和响应体输入的硬编码

### *具体实现*
>#### 注解
##### @NotNullLabel
###### 背景
    相信大家对@NotNull及其他的校验注解并不陌生，也肯定有过这样的想法：每次指定message时，不仅要指定属性名称，还要附加“不能为空”之类的废话。既然如此，那我们就来优化一下。
###### 核心类
    NotNullLabel/NotNullLabelAnnotationValidator
###### 思路

    如上，仿照@NotNull注解，这里我添加了label属性用于指定属性名，同时message属性和原来的用法一致。字段校验如果发现为null，首先检查message属性是否为空，如果不为空，则提示此message信息。否则提示“{属性名}不能为空”。具体逻辑查看NotNullLabelAnnotationValidator。
###### 思考
    @NotBlank/@NotEmpty等注解的类似实现，各位看官自行脑补。
##### @DateEndTime
###### 背景
     在数据统计场景中，经常需要指定的起止日期。我们在接收前端请求表单时，对日期字段的接收，通常是使用Stirng或Date类型。而在使用Mybatis作为数据访问层时，会遇到一些问题：如果底层是Oracle数据库，那字符串不能直接作为日期，必须进行转换，或在DAO接口前人为转换类型，或在写xml配置时特别指定转换器。如果一开始表单中使用Date类型，那这也是可以避免的。但是还有另一个问题需要注意：精确到每天的最后时刻即23:59:59.999。通常来说，我们可以在参数表单接收后进行处理，代码行处理抑或是在mybatis的xml中进行sql运算都可以。但实际上这样并不优雅，很快我们就会发现逻辑中类似的代码块到处都是，给人一种bad smell。相信了解spring的同学都会意识到，这是一个切面性的问题，那么这里来看一下我的处理。
###### 核心类
    DateEndTimeFormatter/DateEndTimeAnnotationFormatterFactory
###### 思路
    这里我的灵感来自于@NotNull注解，当然这是一个校验注解，而我的@DateEndTime则是类型转换注解，发生在后者的前面。
1.通过注解完成对HTTP请求传来的日期字符串转换成Date类型，并设置到当天的最后一刻，这里交给DateEndTimeFormatter实现；
2.通过自定义注解@DateEndTime，并通过DateEndTimeAnnotationFormatterFactory将注解与Formatter关联起来；
3.将DateEndTimeAnnotationFormatterFactory注入到应用上下文的Formatters列表中。
###### 思考
    DateEndTimeFormatter中在转换失败时，会抛出异常，可以通过BindingResult获取到。
也可以选择使用校验类型的注解完成此项工作，只是相对比较乱，不符合单一责任原则。

### *联系方式*
>电子邮箱：<971449932@qq.com>


大标题
====

中标题
-------

#一级标题
##二级标题
###三级标题
####四级标题
#####五级标题
######六级标题

* 列表一
* 列表二
* 列表三

* 列表一
    * 列表二
        *列表三
        
>缩进一
>>缩进二
>>>缩进三
>>>>缩进四
>>>>>缩进五

![baidu](http://www.baidu.com/img/bdlogo.gif "百度logo")

[![baidu]](http://baidu.com)  
[baidu]:http://www.baidu.com/img/bdlogo.gif "百度Logo"  

```Java
public static void main(String[] args){}
```

```javascript
document.getElementById("ts").innerHTML="Hello"
```




        
