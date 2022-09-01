# lock相关 
> 锁相关 lock synchronized volatile 

## `synchronized` JDK1.6后的优化
> - 在JDK1.6之前，使用`synchronized`只有一种方式即重量级锁。在JDK1.6之后，引入了 偏向锁、轻量级锁、重量级锁，减少竞争带来的上下文切换。
> - 伴随着锁竞争的情况，使的锁的状态状态发生了升级。这种情况被称为：锁膨胀。锁可以升级但不能降级

### 一、`Java`对象头
> 每个对象都拥有对象头。对象头由：Mark World，指向类的指针，数组长度 三部分组成。

- `Mark World`

```markdown
1. Mark World记录了对象和锁的有关信息。
   锁升级主要依赖Mark World中的锁标志位和释放偏向锁标识位。

2. 用户态和内核态
   ① 内核态：CPU可以访问内存所有数据，包括外围设备，例如磁盘、网卡。CPU也可以将自己从一个程序切换到另一个程序
   ② 用户态：只能受限访问内存，且不允许访问外围设备。占用CPU的能力被剥夺，CPU资源可以被其他程序获取。
         (之所以会有这样的区分是为了：防止用户进程获取别的程序的内存数据，或者获取外围设备的数据)
   
   什么时间切换：用户程序都是运行在用户态的，但有时候程序需要做一些内核的事情。例如：从磁盘读取数据、或者从磁盘获取输入。而唯一可以做这些事情的就是操作系统。
                这时候就要：将用户态切换到内核态。(synchronized中依赖的monitor也依赖操作系统，所以需要从用户态切换到内核态)
                

3. synchronized内核态切换
   在JVM中 monitorenter和monitorexit 字节码依赖于底层操作系统的Mutex Lock来实现的。但由于使用Mutex Lock需要将当前线程挂起并从用户态切换到内核态来执行。这种切换代价非常昂贵
   
4. 优化后的synchronized锁
   JDK1.6后对synchronized做了优化，通过Mark World来区分不同场景下同步锁的不同类型，来减少线程切换的次数
   
   
5.       

```

### 二、偏向锁
> 大多数情况下，锁不仅不存在多线程竞争。而且总是由一个线程多次获得。这种情况下使用的锁是偏向锁

```markdown
1. 在对象头和栈帧中的锁记录里：存储偏向锁的线程ID。当一个线程进入和退出同步块时，不需要进行CAS操作来加锁解锁，直接判断对象头的Mark World里是否保存了指向当前线程的的线程ID。
2. 如果存在，则当前线程已经获得了锁。如果线程ID不是当前线程，则锁膨胀成轻量级锁。
3. 

```

### 三、轻量级锁
> 当锁出现竞争后，偏向锁就会被撤销。

```markdown
1. 当另外线程竞争获取锁时，由于该锁已经是偏向锁。当发现对象头Mark World中的线程ID不是自己的线程ID，就会进行CAS操作获取锁。
2. 如果获取成功，直接替换Mark World中的线程ID为自己的线程ID。改锁保持偏向锁状态。如果获取失败，代表当前锁有竞争，偏向锁将升级为轻量级锁。
3. 轻量级锁意味着标示该资源处于竞争状态。
4. 当有其它线程访问加了轻量级锁的资源时，会使用自旋锁优化，来进行资源访问。

- 自旋策略
① JVM提供了自旋锁：通过不断尝试来获取锁，避免线程被挂起阻塞。这是基于大多数情况下，线程持有锁的时间不会太长。
② JDK1.7开始，自旋锁默认开启。自旋次数由JVM参数设置决定。不建议设置重试次数过多，自旋CAS操作要长时间占用CPU资源。
③ 自旋锁重试失败后，同步锁就升级为重量级锁。锁标志位：10。
④ 这个状态下，未抢到锁的线程都会进入Monitor，然后被阻塞在_WaitSet队列中


```

### 四、重量级锁
> 

```markdown
1. 重量级锁都会被阻塞，进入阻塞队列。
2. 

```

### 五、偏向锁、轻量级锁、重量级锁的适用场景
```markdown
1. 偏向锁：适用于单线程的情况
2. 轻量级锁：适用于竞争较不激烈的情况 (和乐观锁的使用类似)
3. 重量级锁：适用于竞争激励的情况
```



[synchronized锁升级优化](https://zhuanlan.zhihu.com/p/92808298)

[synchronized的实现原理及锁优化](https://www.cnblogs.com/xdyixia/p/9364247.html)

[Java多线程-4-LOCK](https://blog.csdn.net/adsl624153/article/details/103865288)

-----

## lock

### 一、为什么要使用锁
```markdown
锁是为了解决并发操作引起的脏读、数据不一致问题。
```


## volatile

### 一、volatile关键字的理解
```markdown
- 被volatile修饰的共享变量有以下两点特性：
a. 保证多线程下对该共享变量操作的内存可见性
b. 禁止指令重排序
```

### 二、什么是内存可见性、指令重排序?
```markdown
D:
内存可见性：当线程修改了某一个共享变量的值，其他线程应该立即得知这次修改
重排序：CPU为了提高程序执行的效率和更充分运用计算单元，会将代码执行顺序打乱重新排序。但规定了as-if-serial语义，即不管怎么重排序程序的执行结果不能改变。

JAVA虚拟机规范定义了一种内存模型(JMM)来屏蔽各种硬件和操作系统访问内存的差异。JAVA内存模型规定所有的变量都是存在主存中的，每个线程又有自己的工作内存。所以线程的操作都以自己的工作内存为主，在操作前后
都要把值同步回主存中。
JAVA内存模型主要围绕在并发过程中如何处理原子性、可见性、有序性这三个特性

原子性：指操作不可中断，要么一定做完，要么就没有执行。JAVA中对基本数据类型的读取和赋值操作就是原子操作。像i++这种操作，必须借助于synchronized和lock来保证代码块的原子性。
可见性：当一个共享变量被修改后，对它的修改会立即刷新到主存。其它线程需要读取该变量时，就会去主存中读取。JAVA中volatile、synchronized\lock都可以实现这一点。
有序性：JAVA内存模型允许编译器和处理器对指令重排序，但规定了as-if-serial语义,即不管怎么重排序，程序的执行结果不能变。JAVA中volatile、synchronized\lock都可以实现这一点。

```

### 三、volatile如何满足并发编程的三大特性?
```markdown
D:
a. 当写一个volatile变量时，会把该线程对应的工作内存中的共享变量刷新到主存中去。
b. 当读一个volatile变量时，会把该线程对应的本地内存置为无效，线程接下来将从主存中读取共享变量。
```

### 四、volatile可以保证可见性和有序性，但不能保证原子性?
```markdown
D:
a. 不能保证原子性。对单个基本数据类型的volatile变量读写具有原子性。
```

### 五、volatile底层的实现机制?
```markdown
D:
a. 通过查看汇编代码，volatile修饰的关键字代码会多出一个lock前缀指令(lock前缀指令实际相当于一个内存屏障)
b. 内存屏障提供以下功能：
- 1. 重排序时不能把后面的指令重排序到内存屏障之前的位置
- 2. 使得本CPU的Cache写入内存
- 3. 写入动作也会引起别的CPU或者别的内核无效化其Cache,相当于新写入的值对别的线程可见 
```


### 六、实际业务中哪里用到了volatile?
```markdown
D: 
a. 共享状态量标记(使用volatile修饰可以保证对线程立刻可见，比synchronized、lock有一定效率提升)
b. 懒汉式双锁单例模式中，对instance使用volatile修饰。避免初始化操作的指令重排序
```
> 饿汉式单例
```java
class Singleton{
    private volatile static Singleton instance = null;
    private Singleton(){}
    public static Singleton getInstance(){
        if(instance == null){
        synchronized(Singleton.class){
            if(instance == null){
                instance == new Singleton();
            }
        }
        return instance;
    }
}
```


[面试官最爱的volatile关键字](https://www.jianshu.com/p/e513bc7e4806)


## `CAS`与`AQS`
> 

### JAVA8如何优化CAS性能
> - CAS 是 compare and set/swap(先比较再设置/交换,其操作时原子的)，可以看做是乐观锁的一种实现。
> - CAS解决了多线程并发更新数据时的正确性和一致性。

```markdown
1. CAS涉及三个属性：
   需要读写的内存值： V
   需要比较的预期值： A
   需要写入的新值：   B
   
2. 多线程并发执行中，线程获取共享内存中的值后先与自己线程内存中的旧的值进行比较。如果一致，那就设置新的值进去。若不一致则说明，共享内存的值已经被改变，要继续循环获取修改。
   这个比较并设置的过程是原子的。
   (Java并发包中很多Atomic系列类底层都是使用了无锁化的CAS机制，保证多线程修改一个数据的安全性)
   
3. CAS优化: CAS算法需要不断自旋，长时间自旋造成CPU不必要的性能开销
   JAVA8推出了LongAdder，使用分段CAS以及自动分段迁移的方式来大幅度提升多线程并发执行CAS操作的性能。 
   
4. JAVA8对CAS的优化：分段CAS机制
   如果并发更新线程数过多，则开始使用分段CAS机制：
   ① 在LongAdder实现中，有一个base值，线程不停的累加base值。
   ② LongAdder内部会维护一个Cell数组，每个数组是一个数值分段。让大量的线程分别去对不同Cell数组内部的value值进行CAS累加操作。把CAS计算压力分散到不同的Cell分段数值中。
     这样可以大幅度降低多线程并发更新同一个数值时出现的无限循环问题，大幅度提升多线程并发更新数值的性能和效率。
   ③ LogAdder内部实现了自动分段迁移机制。如果某个Cell的value执行CAS失败，则自动寻找另外一个Cell分段内的value执行CAS操作。这就解决了线程空旋转、自旋不停等问题，让一个
     线程过来执行CAS时可以尽快完成这个操作。
   ④ 如果要从LongAdder中获取当前累加值的总值，就把base值和所有Cell分段数值加起来返回。
   
5. 问题：ABA
   ① 解决ABA问题可以引入版本控制。每次有线程修改了引用的值后，就会进行版本号的更新。虽然两个线程持有相同的引用，但版本不同，这样就可以预防ABA问题。

   
场景：多线程并发累加数值
1. 如果不是使用锁，并发执行的结果是不正确的。

```

[Java并发面试问题之Java 8如何优化CAS性能](https://mp.weixin.qq.com/s?__biz=MzU0OTk3ODQ3Ng==&mid=2247484070&idx=1&sn=c1d49bce3c9da7fcc7e057d858e21d69&chksm=fba6eaa5ccd163b3a935303f10a54a38f15f3c8364c7c1d489f0b1aa1b2ef293a35c565d2fda&mpshare=1&scene=1&srcid=0608QzOXG2l0z2QyfVaCKqRH%23rd)

[一文彻底搞懂CAS](https://segmentfault.com/a/1190000022342403?utm_source=tag-newest)

-----

### 聊聊你对`AQS`的理解
> - AQS `AbstractQueueSynchronizer`(抽象队列同步器) 是并发包的基础组件，用来实现各种锁，各种同步组件。它包含了：state变量、加锁线程、等待队列等并发中的核心组件，维护了加锁状态。
> - 并发包中的 `ReentrantLock\ReentrantReadWriteLock` 底层都是基于AQS来实现的加锁和释放锁的操作。


```markdown
以ReentrantLock来说明AQS的使用

1. ReentrantLock底层是基于AQS实现的。当一个线程过来加锁的时候，此时state值为0，“加锁线程”变量为null。
2. 首先使用基于CAS的原子操作，将state的值从0变成1。
3. 若设置成功，则加锁成功。设置加锁线程变量是自己的线程。可重入锁就是判断加锁线程是当前自己线程，每次更新state的值加1。
4. 若其他线程来加锁时，先执行CAS更新state值从0到1，CAS执行失败。说明已被加锁，接着判断加锁线程是否是自己线程，此时锁被其他线程持有。加锁失败
5. 其他加锁失败的线程会进入等待队列。
6. 释放锁的过程就是将：state的值递减1，如果state值为0。则彻底释放锁，会将“加锁线程”变量设置为null
7. 从等待队列头唤醒等待线程进行加锁。

```

[Java并发面试问题之谈谈你对AQS的理解](https://mp.weixin.qq.com/s?__biz=MzU0OTk3ODQ3Ng==&mid=2247484094&idx=1&sn=b337161f934b1c27ff1f059350ef5e65&chksm=fba6eabdccd163abc8978b65e155d79a133f20ee8a5bff79a33ed20a050c2bd576581db69fe6&mpshare=1&scene=1&srcid=0608yIcfsyrDG1NIBSsF58jq%23rd)

