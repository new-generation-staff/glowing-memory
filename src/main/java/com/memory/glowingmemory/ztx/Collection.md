# Collection

### 一、JAVA中集合框架的构成
```markdown
JAVA中的常用集合类型有: List\Set\Map，其中List\Set接口继承于Collection接口；

List类型的实现有：ArrayList\Vector\LinkedList
Set类型的实现有：HashSet\TreeSet; HashSet的实现有LinkedHashSet;
Map类型的实现有：HashMap\Hashtable\TreeMap; HashMap的实现有LinkedHashMap;
```

### 二、各种集合类型底层的数据结构及其优缺点
```markdown
List类型的集合实现中
List集合是有序且允许重复的;
 - ArrayList的数据结构是: 数组; 查询快,增删慢(要移动位置)。线程不安全 扩容为原来的1.5倍
 - Vector的数据结构是：数组; 查询快,增删慢(要移动位置)。线程安全      扩容为原来的2倍
 - LinkedList的数据结构是：链表; 查询慢，增删快。线程不安全
Set类型的集合实现中
Set集合是无序且不允许重复的;
 - HashSet的数据结构是：哈希表; 其元素无序但唯一; 可用hashCode()和equals()来保证元素的唯一
 - TreeSet的数据结构是：红黑树; 其元素有序且唯一; 底层是TreeMap; (可用自然排序、比较器排序保证有序,根据比较返回值是否是0来决定是否唯一)
 - LinkdeHashSet的数据结构是：链表+哈希表; 其链表保证元素有序，哈希表保证唯一;(FIFO插入有序)
Map类型的集合实现中
 - HashMap的数据结构是：数组+链表或红黑树; 允许Null键null值;
 - LinkedHashMap的数据结构是: 数组+双向链表的结构
 - Hashtable的数据结构是：
 - TreeMap的数据结构是：二叉搜索树  
```

### 三、怎么选择什么情况下该使用哪个集合
```markdown
D: 是否为单值元素：
   - 是：Collection
     元素是否唯一
     - 是：Set
       是否需要排序：
       - 是：TreeSet 或 LinkedHashSet
       - 否：HashSet  
       如果只知道用Set,但不知道用哪个就用HashSet 
     - 否：List
       是否需要线程安全：
       - 是：Vector
       - 否：ArrayList 或 LinkedList
       查询多：ArrayList
       增删多：LinkedList
       如果只知道是List,但不知道用哪个就用ArrayList
   - 否：Map
     是否需要排序：
     - 是：TreeMap
     - 否：HashMap
     是否需要线程安全：
     - 是：Hashtable ConcurrentHashMap
     - 否：HashMap  
```
 
### 四、`HashSet\TreeSet\LinkedHashSet`之间的区别
```markdown
D: 1.介绍
     HashSet 只是通用的存储数据集合
     TreeSet 主要用于排序
     LinkedHashSet 主要用于保证FIFO(先进先出)即有序集合         
   2.不同
     HashSet插入最快，TreeSet最慢因为内部要实现排序。
     HashSet、LinkedHashSet允许存在Null数据; TreeSet不允许数据为Null 
   3.TreeSet两种排序方式的比较?
     自然排序：比较对象实现Comparable接口，重写CompareTo()方法
     比较器排序：自定义比较器实现Comparator接口，重写Compare()方法。在构造函数中指定比较器;(用于多个类有相同的排序算法)
```       

### 五、`1HashMap\Hashtable\TreeMap`之间的区别
```markdown
D: 1. 是否允许null键null值
   HashMap允许Null键Null值，但只允许一条记录的键为Null，允许多条记录的值为Null
   Hashtable不允许Null键Null值。没有处理Null的情况，会报空指针异常
   TreeMap不允许Null键 允许Null值
   2. 是否线程安全
   HashMap\TreeMap 不支持线程同步。线程安全可以使用Collections.synchronizedMap()或ConcurrentHashMap
   Hashtable支持线程同步
   3. 是否有序
   HashMap\Hashtable 无序
   TreeMap基于红黑树(Red-Black-Tree)实现,有序。基于键的自然顺序进行排序或者根据指定的比较器进行排序。
```

### 六、集合框架中常用的工具类
```markdown
D: Collections
   Arrays
```


### 七、HashMap的put()和get()方法执行了什么
```markdown
D: put()方法
   1. 将key进行hash散列
   2. 判断哈希桶数组table为空是，通过resize()进行初始化
   3. 判断key是否存在，若存在直接替换value
   4. 若不存在，将键值插入到对应的链表或红黑树中
   5. 插入链表时，判断是否需要转红黑树
   6. 判断是否需要扩容
   
   
   get()方法
   1. 根据key计算出hash值
   2. 根据hash值定位到具体的桶中
   3. 判断该位置是否为链表，不是链表就根据key、hash值返回value
   4. 为链表则需要遍历直到key及hash值相等则返回
   5. 啥都没有返回null
```



### 八、HashMap为什么要进行扩容?扩容后的长度为什么是原来的2倍?(HashMap的长度为什么是2的幂次方)
```markdown
D: (影响HashMap性能的主要参数是：初始容量和负载因子。负载因子0.75是对时间和空间成本平衡的结果，太高会减少空间开销，但会增加查找成本) 
   HashMap的容量是有限的。经过多次插入元素后，HashMap达到一定的饱和度，Key的映射位置发生冲突的几率会逐渐提高。  
  
   HashMap初始容量为16，扩容是2的幂次。目的是：key值经过hash后可以实现均匀分布。
   
```

      
### 九、(在JDK1.8以下版本)高并发情况下,为什么HashMap可能会出现死锁问题,导致CPU占用率接近100%
```markdown
D: 死锁说法并不是很准确，应该说是一种死循环。形成环形链表;
   HashMap在插入元素容量超过：初始容量*负载因子 时，会进行扩容，即Resize()。Resize()的本质是创建新的Entry数组，将原Map的元素重新计算位置，加入到新Map中。
   Resize()过程分两步：扩容和Rehash。ReHash在高并发情况下可能会形成链表循环(两个线程同时进行扩容操作)。
   当调用get()方法时，查找一个不存在的key，而这个key的Hash结果恰好处于环形链表上。就会导致形成死循环，最终导致CPU100%…
   
   解决方案：
   1.使用Hashtable或者Collections.synchronizedMap()方法来达到线程安全的目的。但是加锁后串行化的执行方式效率很低;
   2.使用ConcurrentHashMap兼顾了线程安全和性能。
  
   JDK1.8做了什么优化：
   1.优化hash算法，只进行一次位移操作
   2.引入红黑树，在链表长度大于8时。转化成红黑树存储。
   3.JDK1.8采用尾节点插入法。JDK1.7以前都是头节点插入元素。
   3.JDK1.8用head和tail来保证在resize链表的顺序和之前一样
```

### 十、如何解决hash冲突(碰撞)
> 哈希碰撞：即多个key经过hash计算后得出相同的hash值
```markdown
1. 拉链法：当发生冲突时，对象会存在链表的下一个节点。见于HashMap中处理HashCode碰撞问题。
2. 线性寻址法：当发生冲突时，就去寻找下一个空的散列地址，只要散列表足够大，空的散列地址总能找到。
3. 在哈希法：当发生冲突时，用哈希函数计算另一个哈希函数地址，直到无冲突为止。缺点：计算时间增加。
4. 建立公共溢出区：将哈希表分为基本表和溢出表两部分，发生冲突的元素都放在溢出表中。


- HashMap中的hashCode()和equals()方法
  a. hashCode()决定了key放在桶里的哪个位置，也就是数组里的index。
  b. equals()用来比较两个object是否相同，是否是同一个对象。


- 为什么重写equals()一定要重写hahsCode()呢？
  a. equals和hashcode的关系：如果两个对象的equals返回true,那么他们的hashCode值一定相同。如果hahsCode值相同，equals不一定返回true。
  b. 实现两个对象equals相等，则他们的地址也一定相同。即hahsCode一定相同。
  c. 提高效率，重写hashcode方法。如果两个对象hashcode不一样，则他们的equals一定不一样，即不是同一个对象。
  
  
```   

  
### 十一、ConcurrentHashMap
```markdown
D: HashMap数据存储在Entry数组中。
   CHM采用分段锁技术，最外层由Segment数组来管理，Segment中数据又由HashEntry来管理，最终数据存储在HashEntry对象中，拥有相同hash值的对象由HashEntry的next属性来关联。
   ConcurrentHashMap->Segment->HashEntry。其中Segment是继承于ReentrantLock的;
   
   CHM的get()方法是非常高效的，整个过程不需要加锁。通过key的hash值定位到具体的Segment，在通过一次Hash定位到具体HashEntry。由于Node中的value属性是用volatile修改的，保
   证了内存可见性，所以每次获取都是最新的值。
   CHM的put()方法。虽然Node中的value用volatile修饰但并不能保证并发的原子性，所以put()操作仍然需要加锁。
```

   
### 十二、ThreadLocal
```markdown
- 谈谈你对threadLocal的理解
  ThreadLocal用在什么地方
  ThreadLocal的细节
  ThreadLocal的最佳实践
  
  ThreaLocal线程的局部变量，和普通变量不同的是每个线程都持有变量的一个副本，可以独立修改访问。线程之间不会发生冲突。
  
1. ThreadLocal用在什么地方
   a. 保存线程上下文信息，在任意需要的地方可以获取
   b. 线程安全，避免某些情况需要考虑线程安全必须同步带来的性能损失
   c. 减少参数传递
   
   
2. ThreadLocal原理
   a.Thread内部都有一个属性变量threadLocals(ThreadLocal.ThreadLocalMap),每个线程都有一个自己的ThreadLocalMap映射表，其key是ThreadLocal本身，value是真正存储的Object
     每个线程操作ThreadLocal时，都是操作自己线程内部的ThreadLocalMap，线程与线程之间是相互隔离的。
   b.一个ThreadLocal只能存储一个Object对象。如果需要存储多个Object对象那就需要多个ThreadLocal  
   c. 
   
   
   
6. ThreadLocal内存泄露分析
   由于ThreadLocalMap是以弱引用方式引用着ThreadLocal,即ThreadLocal是被ThreadLocalMap以弱引用方式关联着，因此如果ThreadLocal没有被ThreadLocalMap以外的对象引用。
   则在下一次GC时，ThreadLocal实例会被回收，那么此时ThreadLocalMap里的一组KV的K就是null了，因此在没有额外操作的情况下，此处的V不会被外部访问到，且只要Thread实例
   一直存在,Thread实例就强引用着ThreadLocalMap。因此ThreadLocalMap就不会被回收，这里的K为null的V就一直占用着内存。
   
   综上，发生内存泄露的条件是
   a. ThreadLocal没有被外部实例强引用
   b. ThreadLocal实例被回收，但是在ThreadLocalMap中的V没有被任何清理机制有效清理
   c. 当前Thread实例一直存在，则会一直强引用着ThreadLocalMap。即ThreadLocalMap不会被GC
   
   
   场景：
   a. 
   b. 
   
   
7. 内存泄露优化
   ThreadLocalMap的设计中已经考虑到这种情况，也做了一些优化措施
   a. 在ThreadLocal的get\set\remove时，都会清除线程ThreadLocalMap里key为null的value
   b. 
   
   
   
       

```


       

     
     
[Java集合中List,Set以及Map等集合体系详解](https://blog.csdn.net/zhangqunshuai/article/details/80660974)  

[Java 集合看这一篇就够了](https://www.cnblogs.com/nycsde/p/13819051.html)
[]()
[]()
[]()
[]()
[]()
[]()
[]()














































































































   