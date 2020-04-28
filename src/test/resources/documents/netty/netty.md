# 读写空闲检测
  规定动作外的所有事件都可以通过下面的方式触发：
  IdleStateHandler  ->  ChannelInBoundHandlerAdapter#userEventTriggered()
  读动作未发生触发读超时事件；
  写动作未发生触发写超时事件；
  读或写动作有一个未发生触发读写超时事件
  
# 心跳检测

  
  

