1、JdbcType
在枚举类JdbcType中，根据String获取枚举类是使用的默认方法 JdbcType.valueOf(alias)
同时，通过code来获取时，内部通过提前准备的map来简化查询耗时：
``` java
public final int TYPE_CODE;
private static Map<Integer, JdbcType> codeLookup = new HashMap();

  static {
      JdbcType[] var0 = values();
      int var1 = var0.length;

      for(int var2 = 0; var2 < var1; ++var2) {
          JdbcType type = var0[var2];
          codeLookup.put(type.TYPE_CODE, type);
      }
  }
```

2、ResolverUtil
在里面有个只有一个matches()方法的Test接口，这种写法类似于之前我在天猫导购中的做法，都是传递接口的方式来泛化判断
``` java
  public interface Test {
    boolean matches(Class<?> type);
  }

  public static class IsA implements Test {
    private Class<?> parent;

    public IsA(Class<?> parentType) {
      this.parent = parentType;
    }

    @Override
    public boolean matches(Class<?> type) {
      return type != null && parent.isAssignableFrom(type);
    }

    @Override
    public String toString() {
      return "is assignable to " + parent.getSimpleName();
    }
  }

  public static class AnnotatedWith implements Test {
    private Class<? extends Annotation> annotation;

    public AnnotatedWith(Class<? extends Annotation> annotation) {
      this.annotation = annotation;
    }

    @Override
    public boolean matches(Class<?> type) {
      return type != null && type.isAnnotationPresent(annotation);
    }

    @Override
    public String toString() {
      return "annotated with @" + annotation.getSimpleName();
    }
  }
```

3、VFS可用于查询文件路径
``` java
List<String> children = VFS.getInstance().list(path);
```

4、方法命名方式值得参考，类似于我在商品打标用的命名
``` java
addIfMatching(test, child);

hasTypeHandlerForResultObject(rsw, resultMap.getType());
```

5、我是否也能注意到这种map访问限制细节？
``` java
  public Map<String, Class<?>> getTypeAliases() {
    return Collections.unmodifiableMap(TYPE_ALIASES);
  }
```

6、注意上下文ErrorContext
``` java
ErrorContext.instance().resource(ms.getResource()).activity("executing a query").object(ms.getId());4
```
在关键方法的调用开始，都会调用ErrorContext记录当前所处阶段上下文，在发生异常时，进行输出
拓展开来还能做什么？

7、RoutingStatementHandler使用了委派模式（静态代理+策略模式），忘了的时候可以再回顾下
``` java
public class RoutingStatementHandler implements StatementHandler {

  private final StatementHandler delegate;

  public RoutingStatementHandler(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {

    switch (ms.getStatementType()) {
      case STATEMENT:
        delegate = new SimpleStatementHandler(executor, ms, parameter, rowBounds, resultHandler, boundSql);
        break;
      case PREPARED:
        delegate = new PreparedStatementHandler(executor, ms, parameter, rowBounds, resultHandler, boundSql);
        break;
      case CALLABLE:
        delegate = new CallableStatementHandler(executor, ms, parameter, rowBounds, resultHandler, boundSql);
        break;
      default:
        throw new ExecutorException("Unknown statement type: " + ms.getStatementType());
    }

  }
```

8、有泛型成员方法的类，类并不一定也要带泛型参数
```java
public interface StatementHandler {

  Statement prepare(Connection connection, Integer transactionTimeout) throws SQLException;

  void parameterize(Statement statement) throws SQLException;

  void batch(Statement statement) throws SQLException;

  int update(Statement statement) throws SQLException;

  <E> List<E> query(Statement statement, ResultHandler resultHandler) throws SQLException;

  <E> Cursor<E> queryCursor(Statement statement) throws SQLException;

  BoundSql getBoundSql();

  ParameterHandler getParameterHandler();
}
```

9、InterceptorChain配合Plugin的拦截处理，动态代理
```java
public class InterceptorChain {
  private final List<Interceptor> interceptors = new ArrayList<Interceptor>();
  public Object pluginAll(Object target) {
    for (Interceptor interceptor : interceptors) {
      target = interceptor.plugin(target);
    }
    return target;
  }
  //ignore
}
```
```java
public class Plugin implements InvocationHandler {

  private final Object target;
  private final Interceptor interceptor;
  private final Map<Class<?>, Set<Method>> signatureMap;

  private Plugin(Object target, Interceptor interceptor, Map<Class<?>, Set<Method>> signatureMap) {
    this.target = target;
    this.interceptor = interceptor;
    this.signatureMap = signatureMap;
  }

  public static Object wrap(Object target, Interceptor interceptor) {
    Map<Class<?>, Set<Method>> signatureMap = getSignatureMap(interceptor);
    Class<?> type = target.getClass();
    Class<?>[] interfaces = getAllInterfaces(type, signatureMap);
    if (interfaces.length > 0) {
      return Proxy.newProxyInstance(
          type.getClassLoader(),
          interfaces,
          new Plugin(target, interceptor, signatureMap));
    }
    return target;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    try {
      Set<Method> methods = signatureMap.get(method.getDeclaringClass());
      if (methods != null && methods.contains(method)) {
        return interceptor.intercept(new Invocation(target, method, args));
      }
      return method.invoke(target, args);
    } catch (Exception e) {
      throw ExceptionUtil.unwrapThrowable(e);
    }
  }
  //ignore...
}
```

10、SqlNode的组合设计模式
``` java
  public class ForEachSqlNode implements SqlNode {
    private SqlNode contents;
  }
```

11、MapperBuilderAssistant的内部方法的组织方式以及构造器实现方式值得学习思考
``` java
    return new ParameterMapping.Builder(configuration, property, javaTypeClass)
        .jdbcType(jdbcType)
        .resultMapId(resultMap)
        .mode(parameterMode)
        .numericScale(numericScale)
        .typeHandler(typeHandlerInstance)
        .build();
```

12、MapperProxy和MapperProxyFactory的动态代理和工厂模式的组合，以及内部缓存的设计
```java
public class MapperProxyFactory<T> {

  private final Class<T> mapperInterface;
  private final Map<Method, MapperMethod> methodCache = new ConcurrentHashMap<Method, MapperMethod>();
  //ignore
  protected T newInstance(MapperProxy<T> mapperProxy) {
    return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[] { mapperInterface }, mapperProxy);
  }
  //ignore
}
```
为什么会多出来一个MapperProxyFactory？

13、SqlSessionFactory接口定义赏析
```java
public interface SqlSessionFactory {

  SqlSession openSession();

  SqlSession openSession(boolean autoCommit);
  SqlSession openSession(Connection connection);
  SqlSession openSession(TransactionIsolationLevel level);

  SqlSession openSession(ExecutorType execType);
  SqlSession openSession(ExecutorType execType, boolean autoCommit);
  SqlSession openSession(ExecutorType execType, TransactionIsolationLevel level);
  SqlSession openSession(ExecutorType execType, Connection connection);

  Configuration getConfiguration();

}
```
为什么SqlSession是接口，而Configuration却是实体类？

14、SqlSessionManager提供了接口SqlSessionFactory和SqlSession的组合
```java
public class SqlSessionManager implements SqlSessionFactory, SqlSession {

  private final SqlSessionFactory sqlSessionFactory;
  private final SqlSession sqlSessionProxy;

  private final ThreadLocal<SqlSession> localSqlSession = new ThreadLocal<SqlSession>();

  private SqlSessionManager(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
    this.sqlSessionProxy = (SqlSession) Proxy.newProxyInstance(
        SqlSessionFactory.class.getClassLoader(),
        new Class[]{SqlSession.class},
        new SqlSessionInterceptor());
  }

  public static SqlSessionManager newInstance(Reader reader) {
    return new SqlSessionManager(new SqlSessionFactoryBuilder().build(reader, null, null));
  }
  //ignore
  
   private class SqlSessionInterceptor implements InvocationHandler {
     public SqlSessionInterceptor() {
         // Prevent Synthetic Access
     }
 
     @Override
     public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
       final SqlSession sqlSession = SqlSessionManager.this.localSqlSession.get();
       if (sqlSession != null) {
         try {
           return method.invoke(sqlSession, args);
         } catch (Throwable t) {
           throw ExceptionUtil.unwrapThrowable(t);
         }
       } else {
         try (SqlSession autoSqlSession = openSession()) {
           try {
             final Object result = method.invoke(autoSqlSession, args);
             autoSqlSession.commit();
             return result;
           } catch (Throwable t) {
             autoSqlSession.rollback();
             throw ExceptionUtil.unwrapThrowable(t);
           }
         }
       }
     }
   }
}  
```
这里为什么添加了属性localSqlSession？