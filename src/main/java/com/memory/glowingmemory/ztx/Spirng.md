# Spring

### 一、如何理解`IOC`和`AOP`
> - IOC : 控制反转，是一种设计思想。它的实现是DI即依赖注入
> - AOP : 面向切面编程

```markdown
- IOC
1. 在实际项目开发中，我们会使用很多类来描述他们特有的功能，并且通过类与类之间的相互协作来完成特定的业务逻辑。这时，每个类都需要负责管理与自己有交互的类的引用和依赖，代码将变得异常难以维护和极度的耦合。
   IOC正是为解决这个问题出现的。通过IOC将这些相互依赖的对象的创建、协调工作交个Spring容器去处理，每个对象只需要关注自身的业务逻辑即可。在这样的角度上看，获得依赖对象的方式发生了反转，变成了由Spring
   容器控制对象如何获取外部资源(对象、文件资料等)。
   通俗点说：以前如果A类要调用B类，通过要new B类，现在我们把new B的事情交给Spring来做，在我们调用的时候容器为我们实例化。 
2. 将对象的创建交由Sring框架管理。
3. IOC生产对象的方式：反射方式。在运行效率上有一定损耗
4. 所谓控制反转是指，本来被调用者的实例是有调用者来创建的，这样的缺点是耦合性太强，IOC则是统一交给spring来管理创建，将对象交给容器管理，你只需要在spring配置文件总配置相应的bean，以及设置相关的属性，
   让spring容器来生成类的实例对象以及管理对象。在spring容器启动的时候，spring会把你在配置文件中配置的bean都初始化好，然后在你需要调用的时候，就把它已经初始化好的那些bean分配给你需要调用这些bean的类

- IOC装配对象
1. 装配方式：手动装配、自动装配
2. 手动装配：一般进行配置信息都采用手动方式，基于XML装配：构造方法、setter方法
   自动装配：基于注解装配
   
- DI : 依赖注入
1. 依赖注入方式：构造函数、setter方法、接口注入

- AOP
1. AOP核心思想：就是将业务逻辑中通用的功能以切面的形式提取出来，让多个类共享一个行为。一旦行为发生改变，不必修改每个类。只需修改行为即可。实现业务关注点分离。
2. 利用AOP可以对业务逻辑的各个部分进行隔离，从而使得业务逻辑各部分之间耦合度降低，提高程序可重用性，和开发效率。
3. 主要功能：日志记录、事务处理、异常处理、权限校验
4. 实现方式：代理模式
```

[面试题，说说你对spring IOC和AOP的理解](https://blog.csdn.net/qq_32534441/article/details/94889895)
> 主要参考原理

[理解IOC和AOP的核心思想和原理](https://www.cnblogs.com/ma-yuanhao/p/9879782.html)

[如果让你自己设计IOC，AOP如何处理(百度)](https://www.cnblogs.com/aspirant/p/9187973.html)

[Spring框架IOC和AOP介绍](https://www.cnblogs.com/li150dan/p/9524305.html)


> DI: 依赖注入的实现
```markdown
1. @Autowired和@Resource 都可以标注在字段或属性的setter方法上。
2. @Autowired是Spring支持的注解。默认按类型装配且类型对象必须存在，且只有一个实现，否则抛出异常。如果允许为null，设置required=false。
   如果想使用名称装配，可以结合@Qualifier("name")注解
3. @Resource是JDK支持的注解。默认按名称装配，可以通过name指定名称。也提供按类型注入。当找不到与名称匹配的bean时才按照类型进行装配。
   一旦指定name属性，则只会按照名称进行装配。   
4. 装配顺序
　　①、如果同时指定了name和type，则从Spring上下文中找到唯一匹配的bean进行装配，找不到则抛出异常
　　②、如果指定了name，则从上下文中查找名称（id）匹配的bean进行装配，找不到则抛出异常
　　③、如果指定了type，则从上下文中找到类型匹配的唯一bean进行装配，找不到或者找到多个，都会抛出异常
　　④、如果既没有指定name，又没有指定type，则自动按照byName方式进行装配；如果没有匹配，则回退为一个原始类型进行匹配，如果匹配则自动装配；
```

[IOC、AOP](https://www.cnblogs.com/weixiaotao/p/10551628.html)

-----

### 二、`BeanFactory`与`FactoryBean`
> - BeanFactory 是一个接口,本质是一个Factory。生产用于管理Bean的IOC容器工厂,给具体的IOC容器实现提供了规范。
> - FactroyBean 是一个接口,本质是一个Bean。为IOC容器中的Bean实现提供了更加灵活的方式。

```markdown
- BeanFactory
1. 在Spring中BeanFactory是IOC容器的核心接口。它的职责包括：实例化、定位、配置应用程序中的对象及建立这些对象间的依赖关系。
2. Spring中实现了BeanFactory，就可以从Spring中获取注册到IOC中的bean
2. 实际开发中用的比较多的BeanFactory的子类是: ClassPathXmlApplicationContext,它是ApplicationContext接口的一个子类。它从xml配置文件中获取bean并管理它们。
   ApplicatonContext是BeanFactory的扩展，功能得到进一步增强。如：更容易与AOP集成、消息国际化、事务传递及各种不同应用层context的实现(如：针对web应用的WebApplicationContext)

- FactoryBean
1. FactoryBean本质是一个Bean。实现了FactoryBean接口的类有能力改变bean。
2. Spring中提供了两种类型的Bean。普通bean和FactoryBean。普通的bean通过getBean(id)方式获取的是该bean的实际类型。另一个种是FactoryBean，工厂bean，通过getBean(id)获取的是该工厂所生产的Bean实例，而不是FactoryBean实例。
3. 通常情况，bean无须实现自己的工厂bean，Spring容器担任工厂角色。少数情况下容器中的bean本身即是工厂，作用是产生其它bean实例。由工厂bean产生的其它bean实例不再由Spring容器产生。
4. 如果实例化一个Bean的过程比较复杂，可以实现FactroyBean接口，定制化实例Bean的逻辑。这样的好处是：屏蔽了一些复杂Bean的细节，给上层应用带来了便利。
```
- `BeanFactory`
```java
public interface BeanFactory {
    String FACTORY_BEAN_PREFIX = "&";

    Object getBean(String var1) throws BeansException;

    <T> T getBean(String var1, Class<T> var2) throws BeansException;

    Object getBean(String var1, Object... var2) throws BeansException;

    <T> T getBean(Class<T> var1) throws BeansException;

    <T> T getBean(Class<T> var1, Object... var2) throws BeansException;
    
    // 省略....
}
```

- `FactoryBean`
```java
public interface FactoryBean<T> {
    String OBJECT_TYPE_ATTRIBUTE = "factoryBeanObjectType";
    //返回具体的bean实例
    @Nullable
    T getObject() throws Exception;
    //返回bean类型
    @Nullable
    Class<?> getObjectType();
    //是否单例
    default boolean isSingleton() {
        return true;
    }
}
```

> 原理介绍

[Spring BeanFactory和FactoryBean的区别](https://www.jianshu.com/p/05c909c9beb0)

[BeanFactory 简介以及它 和FactoryBean的区别(阿里面试)](https://www.cnblogs.com/tiancai/p/9604040.html)

[Spring的BeanFactory和FactoryBean](https://www.jianshu.com/p/a8036f3cc1e6)

> 使用FactroyBean生产定时任务

[FactoryBean使用场景](https://developer.51cto.com/art/201909/602808.htm)


#### `BeanFactory`与`ApplicationContext`的区别
> 
```markdown
1. BeanFactory和ApplicationContext都是接口，并且ApplicationContext间接继承了BeanFactory。
2. BeanFactory是Spring中最底层的接口，提供了最简单的容器功能。实例化对象和获取对象功能。而ApplicationContext是Spring的一个更高级的容器，提供了更多功能。
   如：获取Bean的详细信息(如定义、类型)、国际化、统一加载资源的功能、强大的事务机制、对web应用的支持等。
3. 加载方式的区别：BeanFactory采用的是延迟加载的形式来注入Bean。ApplicationContext是在IOC容器启动时就一次性创建所有的Bean，好处是可以马上发现Spirng配置文件中的错误。

```
> `Spring`及`SprnigBoot`的问题总结的非常好

[SPRINGBOOT启动流程及其原理](https://www.cnblogs.com/theRhyme/p/11057233.html)


-----

### 三、`Spring`与`SpringBoot`中事务的使用
> `Spring`事务
```markdown
1. 事务特性：ACID
   ① 原子性
   ② 一致性
   ③ 隔离性
   ④ 持久性

2. Spring事务的配置方式
   ① 编程式事务管理
   ② 声明式事务管理；官方倡导，非侵入式编程
   声明式事务：是建立在AOP基础上的，本质是对方法执行前后进行拦截，在目标方法开始执行前创建或加入一个事务，执行完成后根据执行情况进行提交或回滚。
   
3. 事务传播机制(propagation)：一般用在事务嵌套的场景
   ① PROPAGATION_REQUIRED : 默认传播机制，如果有事务则加入，没有则新建事务
   ② PROPAGATION_REQUES_NEW : 每次都新建事务。如果外层有事务则挂起，当前事务执行完成后恢复。
   ③ PROPAGATION_SUPPORT : 外层有事务则加入，没有则不使用事务。
   ④ PROPAGATION_NOT_SUPPORT : 不支持事务。外层有事务则挂起。
   ⑤ PROPAGATION_NEVER : 不支持外层有事务，否则报错
   ⑥ PROPAGATION_MANDATORY : 外层必须有事务，否则报错
   ⑦ PROPAGATION_NESTED : 各自维护各自的事务
代码：
   @Transactional(propagation=Propagation.REQUIRED)   

4. 事务的隔离级别(isolation)
   并发场景下会遇到的数据问题：脏读、不可重复读、幻读
   ① ISOLATION_READ_UNCOMMITED : 读未提交 
   ② ISOLATION_READ_COMMITED: 读已提交
   ③ ISOLATION_REPEAT_ABLE_READ: 可重复度
   ④ ISOLATION_SERIALIZABLE: 序列化

代码：
   @Transactional(isolaion=Isolation.READ_UNCOMMITED)   
    
5. 只读事务
代码：
   @Transactional(readOnly=true) 
   
6. 事务超时      
代码：
   @Transactional(timeout=30) 
   
7. 事务回滚
代码：
   @Transactional(rollbackFor=Exception.class) 
   
``` 

> - `SpringBoot`事务管理器
```markdown
1. Spring中事务管理器接口的核心接口：PlatformTransactionManager。SpringBoot中事务管理器是根据AutoConfigure来进行决定的。
   如果使用starter-jdbc,框架默认注入: DataSourceTransactionManager实例。
   如果使用starter-jpa, 框架默认注入：JpaTransactionManager实例。
2. 如果我们手动配置注入了事务管理器，Springboot就不会再我们自动配置事务管理器。
3. 如果使用多个事务管理器，需要手动配置多个。在使用@Transactionl(value="指定事务管理器名称")   
4. Springboot中不需要通过@EnableTransactionManagement 注解来开启事务。只要pom依赖中有数据源相关的starter配置，框架默认会注入事务管理器。、

```

> Spring事务讲的相对全面

[有关Spring事务，看这一篇就足够了](https://www.cnblogs.com/mseddl/p/11577846.html)

[Springboot2（47）注解事务声明式事务](https://blog.csdn.net/cowbin2012/article/details/90751044)
> 多数据源手动指定多个事务管理器，@Transactionl可以指定使用那个事务管理器

[Spring Boot的事务管理注解@EnableTransactionManagement的使用](https://blog.csdn.net/u010963948/article/details/79208328)


[@EnableTransactionManagement是不是必须的](http://www.360doc.com/content/19/0117/16/10072361_809491235.shtml)

[Spring Boot中不需要加 @EnableTransactionManagement 来开启事务](https://yuanyu.blog.csdn.net/article/details/106597952)


-----

### 四、事务不生效的原因
> 在`Spring`或`SpringBoot`中，有时我们使用@Transactional注解后发现事务并未生效

```markdown
1. 数据库引擎不支持事务。Mysql数据库中InnoDB引擎支持是否，MyISAM引擎不支持事务。
2. 事务注解的方法必须是：private\final\static修饰的方法 事务都不生效。(这个是AOP特性决定的) 必须public修饰。
3. Spring事务默认只对RuntimeException异常回滚。其它异常不回滚；可以配置指定回滚异常
4. 如果是自己手动注入的事务管理器，要使用@EnableTransactionManagement开启事务。
5. 业务和事务是在同一线程里。多线程或异步线程中事务不生效。
6. 同一个类中，一个无事务的方法调用另一个有事务的方法，事务不生效。

```

[Spring事务不生效的原因大解读](https://blog.csdn.net/f641385712/article/details/80445933)
> Transaction rolled back because it has been marked as rollback-only

[Spring事务嵌套引发的血案](https://blog.csdn.net/f641385712/article/details/80445912)

[深入理解 Spring 事务原理](https://blog.csdn.net/xiaolyuh123/article/details/56278121)

-----

### 五、`SpringBoot`启动流程
> 

> 代码
```java
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(
    excludeFilters = {@Filter(
    type = FilterType.CUSTOM,
    classes = {TypeExcludeFilter.class}
), @Filter(
    type = FilterType.CUSTOM,
    classes = {AutoConfigurationExcludeFilter.class}
)}
)
public @interface SpringBootApplication {
    
}

```

```markdown
1. @SpringBootApplication注解引用了三个很重要的注解
   @SpringBootConfiguration注解，继承了@Configuration注解，表示当前类是Java Config配置类，会被扫描并加载到IOC容器
   @ComponentScan 自动扫描默认包或指定包下符合条件的组件并加载。如：@Controller @Component
   @EnableAutoConfiguration 
    ① 从classpath中搜寻所有META-INF/spring.factories配置文件，并将其中EnableAutoConfiguration对应的配置，通过反射实例化为对应的标注了@Configuration的java Config形式的IOC容器配置类，然后汇总为一个，并加载到IOC容器
    ② @AutoConfigurationPackages @Import(AutoConfigurationPackages.Registrar.class) 注册当前主程序类的同级及子级的包中的符合条件的Bean的定义(@Configuration)
    ③ @Import(AutoConfigurationImportSelector.class) 扫描各个组件jar META-INF目录下spring.factories文件，将里面的：包名.类名 中的工厂类全部加载到IOC容器中，将所有符合条件的bean(如：java Config配置类)都加载到Spring的IOC容器中
      SpringFactoriesLoader属于Spring框架私有的一种扩展方案，主要功能就是从指定的配置文件META-INF/spring.factories加载配置，加载工厂类。
       
```

---

### 六、`Bean`的循环依赖
> Spring循环依赖问题：循环依赖是指通过 属性setter方式注入对象。如果是构造器依赖，则会导致死锁，项目启动失败

> 三级缓存的实现: 从BeanFactory -> AbstractBeanFactory
```java
public class DefaultSingletonBeanRegistry extends SimpleAliasRegistry implements SingletonBeanRegistry {
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap(256);
    private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap(16);
    private final Map<String, Object> earlySingletonObjects = new HashMap(16);
}
```
```markdown
- Spring创建一个对象分为三步：实例化 -> 填充属性 -> 初始化

1. Spring中Bean的实例化：先调用构造函数实例化，然后填充属性，再接着进行其他附加操作和初始化。这种解决机制是采用Spring框架内定义的三级缓存来实现的。  
   三级缓存解决了Bean之间的循环依赖。
   
2. singletonObjects: 第一级缓存，里面放置了已经实例化好的单例对象
   earlySingletonObjects: 第二级缓存，里面放置的是提前曝光的单例对象
   singletonFactories: 第三级缓存，里面放置的是将要被实例化的对象的对象工厂
   
3. 当一个Bean调用构造函数进行实例化后，即是set属性还未填充。就可以通过三级缓存向外暴露依赖的引用值进行set。
   (所以循环依赖问题的解决也是基于Java的引用传递)基于构造函数的注入，如果有依赖循环，Spring是不能够解决的。      

```

[Spring循环依赖（缩略版）](https://blog.csdn.net/LO_YUN/article/details/108578328)

> `Spring`及`SprnigBoot`的问题总结的非常好

[SPRINGBOOT启动流程及其原理](https://www.cnblogs.com/theRhyme/p/11057233.html)

> 整个启动流程带画图讲解很清晰

[SpringBoot启动流程解析](https://www.cnblogs.com/trgl/p/7353782.html)

[面试题题目:SpringBoot启动流程](https://blog.csdn.net/s_htp/article/details/106777933)

-----































