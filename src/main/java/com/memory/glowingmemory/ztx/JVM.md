## JVM学习及调优


### 一、项目调优(线程和JVM)
```markdown
- 项目优化方向
  a. 线程数
  b. 超时时间
  c. JVM优化
  
  线程数：初始线程数和最大线程数。初始线程数保障项目启动的时候，如有大量用户访问，能够稳定的接受请求。最大线程数用来保证系统的稳定性。
  超时时间：保障如果连接数不被压垮。如果大批量的请求过来，延迟比较高，很容易把线程打满。这种情况生产中比较常见，一旦网络不稳定，宁愿丢包也不愿压垮机房。

- application.yaml
  server:
    # 对tomcat进行了优化
    tomcat:
      min-spare-threads: 20  #初始线程数20
      max-threads: 100       #最大线程数100
    connection-timeout: 5000 #超时时间5000ms 

  JVM优化：
  a. jvm优化是需要场景化的
  b. jvm优化没有特定的方法，更多的是经验之谈
  c. 主要优化方向是：减少 Full Gc的次数和缩短Full Gc执行时间
  
  
  项目JVM优化设置:
  java -jar -server -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=128m -Xms1024m -Xmx1024m -Xmn256m -Xss256k -XX:SurvivorRatio=8 -XX:+UseConcMarkSweepGC eip-app.jar
  -server                     启用server模式
  -XX:MetaspaceSize=512m      元空间默认大小
  -XX:MaxMetaspaceSize=512m   元空间最大大小
  -Xms2048m                   堆最大大小
  -Xmx2048m                   堆默认大小
  -Xmn512m                    新生代大小
  -Xss1024k                   线程栈最大深度大小
  -XX:SurvivorRatio=8         新生代分区比例 8:2
  -XX:+UserConcMarkSweepGC    指定垃圾收集器(这里用CMS收集器)
  -XX:+UseCMSCompactAtFullCollection  打开对年老代的压缩。可能会影响性能，但是可以消除内存碎片
  
  -Xloggc:/usr/local/gdt/logs/gc.log  保存GC日志
  -XX:+PrintGCDetails                 打印详细的GC日志
  -XX:+PrintGCTimeStamps              打印每次GC的时间戳
  -XX:+PrintGCApplicationStoppedTime  打印应用由于GC而产生的停顿时间
  -XX:+UseGCLogFileRotation           使用日志文件旋转
  -XX:NumberOfGCLogFiles=12           日志文件数量
  -XX:GCLogFileSize=128M              日志文件大小

  -XX:+HeapDumpOnOutOfMemoryError           当遇到内存溢出时导出堆栈信息
  -XX:HeapDumpPath=/tmp/heapdump.hprof      指定堆栈信息到指定路径或文件名
        
- JDK1.8之后把永久代移除了，取而代之的是元空间。把类的元数据放到本地堆内存(native heap)中,这一块区域叫元空间。使用本地内存的好处是OutOfMemoryError: PermGen将不库存在
  也就是说本地内存剩余多少，理论上Metasapce就可以有多大。


```
> application配置tomcat优化
```yaml
# 对tomcat进行了优化
server:
  tomcat:
    min-spare-threads: 20  #初始线程数20
    max-threads: 100       #最大线程数100
  connection-timeout: 5000 #超时时间5000ms 

```
> jvm参数
```bash
java -jar 
-server 
-XX:MetaspaceSize=128m 
-XX:MaxMetaspaceSize=128m 
-Xms1024m 
-Xmx1024m 
-Xmn256m 
-Xss256k 
-XX:SurvivorRatio=8 
-XX:+UseConcMarkSweepGC 
-XX:+UseCMSCompactAtFullCollection

-Xloggc:/usr/local/gdt/logs/gc.log  
-XX:+PrintGCDetails                 
-XX:+PrintGCTimeStamps              
-XX:+PrintGCApplicationStoppedTime  
-XX:+UseGCLogFileRotation           
-XX:NumberOfGCLogFiles=12           
-XX:GCLogFileSize=128M              

-XX:+HeapDumpOnOutOfMemoryError 
-XX:HeapDumpPath=/tmp/heapdump.hprof  

eip-app.jar
```


[jvm启动参数设置OOM异常时，自动生成dump文件](https://blog.csdn.net/qq_35745341/article/details/108073378)

[jvm启动参数设置OOM异常时，自动生成dump文件](https://blog.csdn.net/a718515028/article/details/86703186)

[java设置gc日志及oom的内存dump](https://blog.csdn.net/qq_35890572/article/details/103149478)
[]()
[]()


---


### 三、JVM
```markdown
1. JVM由三个主要的子系统构成
   a. 类加载子系统
   b. 运行时数据区(jvm内存)
   c. 执行引擎
   
2. JVM内存结构
   方法区
   堆
   栈
   程序计数器
   本地方法栈
   直接内存
  
  方法区和堆是线程共享区域
  栈、程序计数器、本地方法栈是线程私有的
  
  栈：编译时就为变量分配好了内存空间
  堆：在程序运行时动态分配内存
  
- 堆：是被所有线程共享的一块内存区域，在虚拟机启动时创建。几乎所有的对象实例都在这里分配内存。
  
- 方法区：逻辑概念具体实现 JDK1.7是永久代，JDK1.8是元空间。
  存放 静态变量、常量、类信息(构造器和接口)、运行时常量池、class类模板等 

- 程序计数器
  一块较小的内存空间，是当前线程所执行的字节码的行号指示器。每个线程都有一个独立的程序计数器。
  JVM中唯一没有OOM的区域

- 堆
  新生代 伊甸园区 幸存者区(Form区 To区)
  老年代
  
- 栈
  程序技术器
  栈帧(局部变量表、操作数栈、动态连接、方法出口)
  本地方法栈  

- 元空间：方法区的一种实现。JDK1.8中元空间并不在虚拟机中，而是在本地内存中。因此元空间的大小受限于本地内存
  -XX:MetaspaceSize: 初始空间大小。达到该值就会触发垃圾收集进行类型卸载，同时GC会对该值进行调整。 
  -XX:MaxMetaspaceSize: 最大空间
  

- 直接内存：不受JVM GC管理

7. JVM类加载机制
   JVM类加载机制分为五个部分：加载、验证、准备、解析、初始化
   
   类加载器
   a. Bootstrap ClassLoader 启动类加载器    加载JDk\lib目录中的类库
   b. Extension ClassLoader 扩展类加载器    加载JDK\jre\lib\ext中的类库
   c. Application ClassLoader 应用程序类加载器 加载classpath路径下的类，也是我们开发应用程序的类加载器
   d. User ClassLoader 自定义类加载器   可以自定义类加载器
   
   全盘负责委托机制
   当一个ClassLoader加载一个类时，除非显示的使用另一个ClassLoader,否则都有这个ClassLoader来加载类
   
   双亲委派模型
   当一个类加载器收到类加载请求,它首先不会自己去加载这个类。而是把请求委派给父类去完成，每一层类加载器都是如此，因此所有的加载请求都应该传送到启动类加载器中。
   只有当父加载器反馈自己无法加载这个类时(在它的加载路径下没有找到所需加载的class)，子加载器才会尝试自己去加载。
   
   好处：
   a. 沙箱安全机制：防止核心类库被随意篡改，如自己写的String类不会被加载
   b. 避免类的重复加载：当父ClassLoader加载了该类，子ClassLoader就不需要再加载
   

8. 对象的引用类型
   a. 强引用：GC时不会被回收
   b. 软引用：描述有用但不是必须的对象，发生在内存溢出异常之前被回收
   c. 若引用：描述有用但不是必须的对象，在下一次GC时被回收
   d. 虚引用：无法通过虚引用获得对象，虚引用用来在GC时返回一个通知

9. GC是什么
   GC垃圾回收的意思。java提供的GC功能可以自动监测对象是否超过作用域从而达到自动回收内存的目的。
   
   什么时候会触发Minor GC与Full GC
   a. 当新生代内存不够时，触发Minor GC
   b. JVM内存不够时，触发Full GC
   
   JDK1.7中,JVM永久代会发生垃圾回收嘛
   垃圾回收会发生在永久代。如果永久代满了或超过了临界值，会触发Full GC。如果仔细查看垃圾收集器输出的信息，会发现永久代也是被回收的。

10. 如何判断一个对象是否应该被垃圾回收
    a. 引用计数法：每个对象都有一个引用计数属性，新增一个引用计数器加1，释放一个引用计数器减1，计数器为0时可以回收。无法解决对象相互循环依赖问题。
    b. 可达性分析法：从跟节点开始向下搜索，搜索所走过的路径称为引用链。当一个对象到根节点灭有任何引用链相连时，则此对象不可用，不可达。可以被回收。
    可以做GC Root根节点：类加载器、Thread、虚拟机栈的局部变量表、static成员、常量、本地方法栈变量   
    
    
    
11. 有哪些垃圾回收算法
    a. 标记-清除：首先标记出所有需要回收的对象，在标记完成后统一回收所有标记对象。
    b. 复制算法：将内存按容量分为大小相等的两块，每次只用其中的一块。当一块内存满后，将还活着的对象复制到另一块上面。然后把已经使用过的内存空间一次清理掉。
    c. 标记-整理：首先标记出所有需要回收的对象，将存活对象移向内存的一端。然后清除端边界外的对象。
    d. 分代收集算法：根据各年代的特点采用不同的收集算法。

    新生代：复制算法，新生代每次垃圾回收都要回收大部分对象
    老年代：标记-清除，标记-整理算法。


12. 有哪些垃圾回收器
    a. Serial收集器：最老最稳定的收集器。只使用一个线程，串行收集。可能会产生较长时间的停顿。
    b. ParNew收集器：是Serial收集器的多线程版本。
    c. Prarllel收集器：
    d. Parallel Old收集器
    e. CMS收集器；是一种以获得最最短回收停顿时间为目标的收集器，是一种并发收集器。采用标记-整理算法。
    f. G1收集器

13. 如何判断一个常量是废弃常量
    运行时常量池主要回收废弃常量
    如果当前没有任何变量引用该字符串常量的话，该常量就是废弃常量
    
14. 如何判断一个类是无用类    
    满足三个条件
    a. 该类的所有实例都已经被回收。java堆中不存在该类的任何实例
    b. 加载该类的加载器已经被回收
    c. 该类对应的Class没有在任何地方被引用，无法在任何地方通过反射访问该类的方法


20. 怎么处理OutOfMemory问题
    常见原因
    a. 内存加载的数据量太大，一次性从数据库取太多数据
    b. 集合类中有对象的引用，使用后未清空，GC不能回收
    c. 代码中存在循环产生过多的重复对象
    d. 启动参数堆内存值太小
    
    
21. StackOverflow异常产生的原因
    栈内存溢出，一般由栈内存的局部变量过爆导致内存溢出。
    如：出现递归方法，参数过多，递归过深，递归没有出口。
        
    

```

---

### 四、JVM监控和调优常用命令
> 在Java应用和服务出现莫名卡顿和CPU飙升等问题时，要分析对应进程的JVM状态以定位问题和解决问题并作出相应优化。 


1. jps
> Java版的ps命令,查看java进程及其相关信息。如果想找到一个java进程的pid，可以使用jps命令

```bash
# 查看Java项目
[root@localhost ~]# jps
22195 Jps
8391 jar
[root@localhost ~]# 
```  
2. jinfo
> 用来查看JVM参数，动态修改部分JVM参数的命令

```bash
# 查看JVM参数配置
[root@localhost ~]# jinfo -flags 8391
Attaching to process ID 8391, please wait...
Debugger attached successfully.
Server compiler detected.
JVM version is 25.252-b09
Non-default VM flags: -XX:CICompilerCount=3 -XX:InitialHeapSize=130023424 -XX:MaxHeapSize=2051014656 -XX:MaxNewSize=683671552 -XX:MinHeapDeltaBytes=524288 -XX:NewSize=42991616 -XX:OldSize=87031808 -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseParallelGC 
Command line:  
[root@localhost ~]# 
``` 
3. jstat
> 查看JVM运行时的状态信息，包括内存状态、垃圾回收等
```bash
# 查看垃圾回收信息
[root@localhost ~]# jstat -gc 8391
S0C    S1C    S0U    S1U      EC       EU        OC         OU       MC     MU      CCSC    CCSU     YGC     YGCT    FGC    FGCT     GCT   
1024.0 1536.0 992.4   0.0   26624.0  12730.2   116736.0   69799.6   74520.0 70781.7  9000.0  8230.8   2090    9.549   3      0.334    9.883
[root@localhost ~]# 
```
4. jstack
> 查看JVM线程快照，线程快照是当前JVM线程正在执行的方法堆栈集合。

> jstack可以定位线程出现长时间卡顿的原因。例如：死锁、死循环等。
```bash
[root@localhost ~]# jstack 8391
[root@localhost ~]# jstack -l 8391
```
5. jmap
> 用来生成堆dump文件和查看堆相关的各类信息
```bash
# 查看堆的详细信息
[root@localhost ~]# jmap -heap 8391
Attaching to process ID 8391, please wait...
Debugger attached successfully.
Server compiler detected.
JVM version is 25.252-b09

using thread-local object allocation.
Parallel GC with 4 thread(s)

Heap Configuration:
  MinHeapFreeRatio         = 0
  MaxHeapFreeRatio         = 100
  MaxHeapSize              = 2051014656 (1956.0MB)
  NewSize                  = 42991616 (41.0MB)
  MaxNewSize               = 683671552 (652.0MB)
  OldSize                  = 87031808 (83.0MB)
  NewRatio                 = 2
  SurvivorRatio            = 8
  MetaspaceSize            = 21807104 (20.796875MB)
  CompressedClassSpaceSize = 1073741824 (1024.0MB)
  MaxMetaspaceSize         = 17592186044415 MB
  G1HeapRegionSize         = 0 (0.0MB)

Heap Usage:
PS Young Generation
Eden Space:
  capacity = 27262976 (26.0MB)
  used     = 2441576 (2.3284683227539062MB)
  free     = 24821400 (23.671531677246094MB)
  8.955647395207333% used
From Space:
  capacity = 1572864 (1.5MB)
  used     = 819520 (0.78155517578125MB)
  free     = 753344 (0.71844482421875MB)
  52.103678385416664% used
To Space:
  capacity = 1572864 (1.5MB)
  used     = 0 (0.0MB)
  free     = 1572864 (1.5MB)
  0.0% used
PS Old Generation
  capacity = 119537664 (114.0MB)
  used     = 71622328 (68.30437469482422MB)
  free     = 47915336 (45.69562530517578MB)
  59.916118153354574% used

[root@localhost ~]# 

# 导出dump文件
[root@localhost ~]# jmap -dump:file=dump.hprof 8391
Dumping heap to /root/dump.hprof ...
Heap dump file created
[root@localhost ~]# ll
``` 
   
   
6. jhat
> 用来分析jmap生成的dump文件。jhat内置了应用服务器，可以通过网页查看dump文件分析结果。一般用在离线分析上
```bash
# 分析dump文件
[root@localhost ~]# jhat dump.hprof 
Reading from dump.hprof...
Dump file created Thu Oct 22 11:12:02 CST 2020
Snapshot read, resolving...
Resolving 1363436 objects...
Chasing references, expect 272 dots............................................................................................................................
Eliminating duplicate references...............................................................................................................................
Snapshot resolved.
Started HTTP server on port 7000
Server is ready.
```
7. jconsole

8. jvisualvm
> JDK自带的分析JVM的工具


### 五、参考文档

[JVM监控和调优常用命令工具总结](https://www.cnblogs.com/wxisme/p/9878494.html)