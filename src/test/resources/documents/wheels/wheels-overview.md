>### 注解
* [@NotNullLabel](#notnulllabel) 对@NotNull进行了优化，只需指定属性名即可
* [@DateEndTime](#dateendtime) 将页面请求传递的日期参数（yyyy-MM-dd）转换为Date类型值（yyyy-MM-dd 23:59:59.999）
* [@ExcelColumn](#excelcolumn) 标记对象属性在excel中对应的列标题，用于简化excel生成逻辑
* [@ResponseHeader](#responseheader) 自定义响应头，用于减少文件下载时响应头部信息和响应体输入的硬编码
>### 多线程
* [AbstractElegantThread](#abstractelegantthread) 对Thread进行了封装，简化异常捕捉处理


## *具体实现*
>### 注解
#### @NotNullLabel
#### 背景
    相信大家对@NotNull及其他的校验注解并不陌生，也肯定有过这样的想法：每次指定message时，不仅要指定属性名称，还要附加“不能为空”之类的废话。
    既然如此，那我们就来优化一下。
#### 核心类
    NotNullLabel/NotNullLabelAnnotationValidator
#### 思路
```java
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
        ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {NotNullLabelAnnotationValidator.class})
public @interface NotNullLabel {

    String label();

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
            ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {

        NotNullLabel[] value();
    }
}
```
    如上，仿照@NotNull注解，这里我添加了label属性用于指定属性名，同时message属性和原来的用法一致。
    字段校验如果发现为null，首先检查message属性是否为空，如果不为空，则提示此message信息。否则提示“{属性名}不能为空”。
    具体逻辑查看NotNullLabelAnnotationValidator。
#### 思考
    @NotBlank/@NotEmpty等注解的类似实现，各位看官自行脑补。
---
#### @DateEndTime
#### 背景
     在数据统计场景中，经常需要指定的起止日期。我们在接收前端请求表单时，对日期字段的接收，通常是使用Stirng或Date类型。
     而在使用Mybatis作为数据访问层时，会遇到一些问题：如果底层是Oracle数据库，那字符串不能直接作为日期，必须进行转换。
     或在DAO接口前人为转换类型，或在写xml配置时特别指定转换器。如果一开始表单中使用Date类型，那这也是可以避免的。
     但是还有另一个问题需要注意：精确到每天的最后时刻即23:59:59.999。
     通常来说，我们可以在参数表单接收后进行处理，代码行处理抑或是在mybatis的xml中进行sql运算都可以。
     但实际上这样并不优雅，很快我们就会发现逻辑中类似的代码块到处都是，给人一种bad smell。
     相信了解spring的同学都会意识到，这是一个切面性的问题，那么这里来看一下我的处理。
#### 核心类
    DateEndTimeFormatter/DateEndTimeAnnotationFormatterFactory
#### 思路
    这里我的灵感来自于@NotNull注解，当然这是一个校验注解，而我的@DateEndTime则是类型转换注解，发生在后者的前面。
    1.通过注解完成对HTTP请求传来的日期字符串转换成Date类型，并设置到当天的最后一刻，这里交给DateEndTimeFormatter实现；
    2.通过自定义注解@DateEndTime，并通过DateEndTimeAnnotationFormatterFactory将注解与Formatter关联起来；
    3.将DateEndTimeAnnotationFormatterFactory注入到应用上下文的Formatters列表中。
#### 思考
    DateEndTimeFormatter中在转换失败时，会抛出异常，可以通过BindingResult获取到。
    也可以选择使用校验类型的注解完成此项工作，只是相对比较乱，不符合单一责任原则。
---
#### @ExcelColumn
#### 背景
     开发中有时候会有excel导出的需要，如果没有成熟的工具，一个单元格一个单元格的勉强还可以接受，但是一旦遇到导出列非常多的情况时，那就不好办法了。
     如果需求突然说，帮我加几个列/这几个列调一下位置的时候，不知道你心里有多少个草泥马？
     那么作为程序员，我们是不是可以动动脑经，让自己少生点气，多活两年呢？看一下我这里提供的实现：
#### 核心类
    ExcelColumn/ColumnName2IndexHelper/ExcelHelper
#### 思路
```java
public @interface ExcelColumn {

    String name();

    int sameNameIndex() default 0;

}
```
    这里我设置了两个属性：name用于指定excel中列标题的名称；sameNameIndex如果存在多个相同的列标题名称时用于区分具体的先后顺序。
    ExcelHelper是整个实现的核心：
    1.ExcelHelper在实例化后，调用init方法完成对excel列标题的设置；
    2.调用setDataRows完成数据的写入，在此过程执行之前会借助ColumnName2IndexHelper解析出列标题的序号与对象属性之间的对应关系。
#### 思考
    ExcelHelper目前只适合于简单对象集合的处理，对于复杂对象暂时没有提供比较好的处理方式，因此将ColumnName2IndexHelper独立出来方便使用。
---
#### @ResponseHeader
#### 背景
     spring社区的各位大佬确实伟大。但是难免有考虑不到的地方，让人觉得sb。就比如下面这段代码，不知道你会不会看着就烦：
     // TODO 待补充
     如果只需要通过一个注解，设置一下该有多好。
#### 核心类
    ResponseHeader/ResponseHeaderBodyAdvice
#### 思路
```java
public @interface ResponseHeader {

    String mediaTypeValue() default MediaType.APPLICATION_OCTET_STREAM_VALUE;

    String fileName();

}
```
    这里直接使用的spring中定义好的MediaType来指定媒介类型；
    fileName顾名思义就是文件名。
    将注解标注在控制器路径映射的公共方法上，设置好文件名（没有特别需要的话，默认类型就很好）。
    在控制器方法执行完成进行响应体填充前，会被ResponseHeaderBodyAdvice拦截，然后就下面这段代码：
```java
/**
     @Override
    public Object beforeBodyWrite(Object obj, MethodParameter returnType, MediaType mediaType,
        Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        Assert.notNull(obj, "ResponseHeader注解的方法返回值不允许为null");
        ResponseHeader responseHeader = getResponseHeader(returnType);
        String mediaTypeValue = responseHeader.mediaTypeValue();
        String fileName = responseHeader.fileName();
        if (mediaTypeValue.equals(MediaType.APPLICATION_OCTET_STREAM_VALUE)) {
            serverHttpResponse.getHeaders().setContentType(MediaType.APPLICATION_OCTET_STREAM);
            serverHttpResponse.getHeaders().setContentDispositionFormData("attachment", fileName);
            if (obj instanceof POIDocument) {
                try {
                    ((POIDocument) obj).write(serverHttpResponse.getBody());
                    return null;
                } catch (IOException e) {
                    LOGGER.error("数据写入响应体失败", e);
                }
            }
        }
        return obj;
    }
*/
```
    方法根据媒介类型等来决定是自行处理响应，还是交由框架继续处理。
    来看一下调用的地方：
```java
/**
    @ResponseHeader(fileName = "543.xls")
    @GetMapping("/test4")
    public HSSFWorkbook test4() {
        LOGGER.debug("invoke test4");
        return WriteExcel.getWorkbook();
    }
*/
```
    是不是看起来很清爽～

#### 思考
    或许有人会说用工具类实现也同样可以，当然，不过如果不去提升自己的逼格又怎么涨工资呢
    目前文件名的生成还不是动态的，后续有时间再考虑实现下。

---
>### 多线程
#### AbstractElegantThread
#### 背景
     多线程调用出现异常，如果不进行异常捕捉，发生问题经常会不知道具体哪里出了问题。如果重复去catch异常也显得不是那么美观。
#### 核心类
    AbstractElegantThread
#### 思路
```java
/**
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public final void run() {
        try {
            runElegantly();
        } catch (Exception e) {
            handleException(e);
        }
    }
*/
```
    这里我对Thread类进行了一层简单的封装：
    final修饰run方法，使其不能被子类修改；
    提供了两个hook方法：runElegantly和handleException和一个日志对象。
#### 思考
    类名意为优雅线程，但事实并不是真正的优雅，具体业务里的资源释放等，还是需要在使用时自己解决的。
