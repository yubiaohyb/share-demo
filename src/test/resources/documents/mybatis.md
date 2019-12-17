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
```

5、我是否也能注意到这种map访问限制细节？
``` java
  public Map<String, Class<?>> getTypeAliases() {
    return Collections.unmodifiableMap(TYPE_ALIASES);
  }
```

