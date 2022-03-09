```markdown
# @Async注解的使用

@Async的作用:
异步执行
异步调用就是不用等待结果的返回就执行后面的逻辑；同步调用则需要等待结果再执行后面的逻辑。

简单使用:
1、启动类上加入 @EnableAsync 注解开启异步任务的执行
2、在需要异步执行的方法上加入 @Async注解

@Async失效的情况
1、同一个类中调用带有 @Async 注解的方法（因为@Async是spring的东西，要用spring bean,不能直接用java bean）
2、@Async在静态方法上（static）
3、@Async在私有方法上（private）
```