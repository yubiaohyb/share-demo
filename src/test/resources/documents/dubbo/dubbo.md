# 两大设计原则
  1、微内核 + 插件
  2、URL作为传递配置信息的统一方式
  
# 三大领域模型
  protocol服务域
    invoker暴露和引用的主功能入口，负责invoker的生命周期管理。
    
  invoker实体域
    dubbo的核心模型，其它模型向其靠拢或转换成它。
    代表一个可执行体，可向其发起invoke调用，可能是本地实现，也可能是远程实现，也可能是集群实现。
    就是provider的代理

  invocation会话域
    持有调用过程中的变量，比如方法名、参数等。
    
# 四大组件
  provider、consumer、registry、monitor
  
  
  
  