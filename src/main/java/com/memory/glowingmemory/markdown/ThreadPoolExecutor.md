# ThreadPoolExecutor

```markdown
ThreadPoolExecutor(
        int corePoolSize,
        int maximumPoolSize,
        long keepAliveTime,
        TimeUnit unit,
        BlockingQueue<Runnable> workQueue,
        ThreadFactory threadFactory,
        RejectedExecutionHandler handler)

参数如下
corePoolSize ：线程池中核心线程数的最大值
maximumPoolSize ：线程池中能拥有最多线程数
keepAliveTime ：表示空闲线程的存活时间
TimeUnit unit ：表示keepAliveTime的单位
workQueue：用于缓存任务的阻塞队列 （有界队列 和 无界队列）
threadFactory ：指定创建线程的工厂。（可以不指定）
handler 拒绝策略
```

```markdown
参数详解:
#KeepAliveTime:
当一个线程无事可做，超过一定的时间（keepAliveTime）时，线程池会判断，如果当前运行的线程数大于 corePoolSize，那么这个线程就被停掉。所以线程池的所有任务完成后，它最终会收缩到 corePoolSize 的大小。
注：如果线程池设置了allowCoreThreadTimeout参数为true（默认false），那么当空闲线程超过keepaliveTime后直接停掉。（不会判断线程数是否大于corePoolSize）即：最终线程数会变为0。

#workQueue 任务队列:
ThreadPoolExecutor线程池推荐了三种等待队列，它们是：SynchronousQueue 、LinkedBlockingQueue 和 ArrayBlockingQueue。
1）有界队列：
SynchronousQueue ：一个不存储元素的阻塞队列，每个插入操作必须等到另一个线程调用移除操作，否则插入操作一直处于 阻塞状态，吞吐量通常要高于LinkedBlockingQueue，静态工厂方法 Executors.newCachedThreadPool 使用了这个队列。

ArrayBlockingQueue：一个由数组支持的有界阻塞队列。此队列按 FIFO（先进先出）原则对元素进行排序。一旦创建了这样的缓存区，就不能再增加其容量。试图向已满队列中放入元素会导致操作受阻塞；试图从空队列中提取元素将导致类似阻塞。
2）无界队列：
LinkedBlockingQueue：基于链表结构的无界阻塞队列，它可以指定容量也可以不指定容量（实际上任何无限容量的队列/栈都是有容量的，这个容量就是Integer.MAX_VALUE）

PriorityBlockingQueue：是一个按照优先级进行内部元素排序的无界阻塞队列。队列中的元素必须实现 Comparable 接口，这样才能通过实现compareTo()方法进行排序。优先级最高的元素将始终排在队列的头部；PriorityBlockingQueue 不会保证优先级一样的元素的排序。

注：BlockingQueue如果是无界队列，则永远不会触发maximumPoolSize
#threadFactory ：指定创建线程的工厂

#handler 拒绝策略  表示当 workQueue 已满，且池中的线程数达到 maximumPoolSize 时，线程池拒绝添加新任务时采取的策略。（默认 AbortPolicy）

ThreadPoolExecutor.AbortPolicy() : 抛出RejectedExecutionException异常。默认策略

ThreadPoolExecutor.CallerRunsPolicy() : 由向线程池提交任务的线程来执行该任务

ThreadPoolExecutor.DiscardPolicy() : 抛弃当前的任务

ThreadPoolExecutor.DiscardOldestPolicy() : 抛弃最旧的任务（最先提交而没有得到执行的任务）
```

```markdown
调用 execute() 方法添加一个任务时，会进行以下判断
如果有空闲线程，则直接执行该任务；
如果没有空闲线程，且当前运行的线程数少于corePoolSize，则创建新的线程执行该任务；
如果没有空闲线程，且当前的线程数等于corePoolSize，同时阻塞队列未满，则将任务入队列，而不添加新的线程；
如果没有空闲线程，且阻塞队列已满，同时池中的线程数小于maximumPoolSize ，则创建新的线程执行任务；
如果没有空闲线程，且阻塞队列已满，同时池中的线程数等于maximumPoolSize ，则根据构造函数中的 handler 指定的策略来拒绝新的任务。
```