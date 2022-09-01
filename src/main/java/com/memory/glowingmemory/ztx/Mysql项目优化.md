## MySQL项目优化

### select count(*) 是否会导致全表扫描
```markdown
- 针对无where条件的 count(*) 

1. Mysql5.6版本后是有优化的
   优化器会选择成本最小的 辅助索引查询计数,其实性能最高。
   select count(*)与select count(1),Mysql都会用成本最小的辅助索引查询方式来计数


2. select count(*)  可以用来查询表的行数
   select count(id) 查询id字段的列数。这里要注意的是，列值不为NULL。count(id)不会统计NULL。
   

``` 

### select * 带来问题
```markdown
- 业务方面
  1. 如果表结构有修改，select * 返回的数据也必然会变化。客户端是否对数据库变化做好了适配。
  2. 业务中不需要的列会，传输过程中会有不必要的性能损耗。
  3. 客户端解析查询结果也需要更多损耗
  
- 数据库方面
  1. select * 必然导致数据库需要先解析代表哪些字段，从数据字段中将 * 转换为具体的含义字段。存在性能开销
  2. 不可能对所有字段建索引，在索引优化方面会有局限性。导致查询性能差   
  3. 连接查询时，* 会放入无用的列占用缓冲空间。浪费提高性能的机会。

```
[为什么不建议用select * ](https://blog.csdn.net/xx123698/article/details/100014545)
--- 

### SQL选用索引的执行成本如何计算
```markdown
- 在有多个索引的情况下，MySQL在查询数据前会根据成本最小的原则来使用对应的索引。
  
  这里的成本包括：
  a. IO成本：即把数据从磁盘加载到内存的成本。MySQL以页的形式读取数据，当读取某个数据时，并不会只读取这个数据而是把相邻的数据也一起读到内存中。IO成本与页的大小有关
  b. CPU成本：将数据读入内存后，检测数据是否满足条件和排序等。显然与行数有关


  
``` 

### SQL优化步骤
```markdown
1. 通过show status和应用特点了解各种SQL执行频率
   
  
2. 定位执行效率较低的SQL语句
   a. 通过慢查询日志定位那些执行效率较低的SQL语句
      /etc/mysql/conf.d/mysql.cnf
      [mysqld]
      slow_query_log = ON
      slow_query_log_file = /var/lib/mysql/instance-1-slow.log
      long_query_time = 2
      
      # 查询是否开启慢查询日志及日志存放位置
      show variables like 'slow_query%';
      # 查询慢查询设置时间
      show variables like 'long_query_time';
      
      
   b. 使用show processlist查看当前mysql的线程

3. 通过EXPLAIN分析低效SQL的执行计划

``` 

[MySQL 慢查询日志](https://www.cnblogs.com/magic-chenyang/p/10557002.html)
[]()
[]()

### 
```markdown

``` 

### 
```markdown

``` 

### 
```markdown

``` 

### 
```markdown

``` 

### 
```markdown

``` 

### 
```markdown

``` 

### 
```markdown

``` 

### 
```markdown

``` 

### 
```markdown

``` 

### 
```markdown

``` 

### 
```markdown

``` 

### 
```markdown

``` 

### 
```markdown

``` 

### 索引失效的情况
```markdown

1. like %abc%：在使用like时，不在关键词加%，否则会导致全表扫描。
2. where子句使用 or 关键字，索引将失效。通常使用 union all 或 union 代替
3. where子句使用 is null 或 is not null,索引将失效进行全表扫描。
4. where子句中进行表达式运算或函数，索引会失效

- 优化
5. 排序索引问题
   如果where子句中已经使用了索引，那么order by中的列是不会使用索引的。
   尽量不要包含多个列的排序，如果要用最好给这些列创建复合索引。
6. 确认不可能出现重复结果时，尽量用union all替换union。
   union需要在将结果合并后，在进行过滤、排序操作，加大资源消耗。
   


``` 

### MySQL性能优化总结
```markdown
1. 对于 select * 要时刻保持谨慎态度
   使用select * 会让优化器无法完成索引覆盖扫描这类优化，而且会额外增加IO、内存、CPU的消耗。
   合理使用select *可以简化开发，提高相同代码的复用性。
   
2. 是否扫描太多的额外的记录
   有时候会发现某些查询可能需要读取几千行数据，但仅返回几条或很少的结果。可以使用以下方式优化：
   a. 能否改表结构。如：使用汇总表
   b. 获取数据结果的方式是否最优，获取路径是否最短
   c. 使用覆盖索引，把所有需要的列都放到索引中，以减少回表的动作
   
   
3. 切分某些SQL语句
   传统互联网系统中，强调网络连接尽量少，数据尽可能在一次连接中完成。防止建立多次连接，但这种对于Mysql并不适用。
   Mysql在设计上，建立连接和断开都很轻量。
   在有些场景下可以将一个大查询，切分成小查询，然后在组合起来。
   a. 对全量数据查询变成分页。
   b. 删除大量旧数据时，推荐一次删除一万条。

4. 慎用join操作
   很多互联网公司都杜绝使用join操作。换成：先从一张表中取出数据id，在从另一张表中使用where in查询获取结果。
   原因：
   a. 让应用的缓存更高效。如果通过id查询可以使用缓存命中
   b. 更容易应对业务的发展，方便对数据库进行拆分，更容易做到高性能和高扩展
   c. 对where in中的id进行升序排序后，查询效率比join的随机关联更高效
   d. 减少多余的查询。在应用层中两次查询，意味着对某条记录应用只需要查询一次，而使用join可能需要重复的扫描访问一部分数据
   e. 单张表查询可以减少锁的竞争

5. 在性能要求高的场景中，杜绝使用临时表
   Mysql临时表没有任何索引，使用临时表一般意味着性能比较低。因此性能比较高的场景中，最好不要使用带临时表的操作
   会产生临时表的操作：
   a. 未带索引的字段上的 group by操作
   b. union查询
   c. 查询语句中的子查询
   d. 部分order by操作，例如：distinct函数和order by一起使用且distinct和order by同一个字段。
   
   具体到是否使用临时表，可以通过explain来查看。若Extra列出现: Useing temporary则需要注意
   

6. count()函数优化
   count()函数不统计值为null的字段，所以不能用来查询某一列的行数。
   如果统计结果集：count(*) 性能也会很好

7. 尽量不使用子查询
   尽量使用关联查询来代替子查询


8. 优化分页limit
   使用limit 50,10这种语句，数据量少还可以。如果数据量非常大的时候，性能会出现问题。
   a. 利用between and 和主键索引
      > 利用自增主键id，可以知道分页的上边界。查询可以改为：select xxx,xxx from table where id between xxx and xxx。
      
   b. 利用自增主键索引、order by加limit，不使用offset
      > select xxx from table where_id < '上页id分界值' order by id desc limit 20

9. 熟练使用explain
   


734
1066.5


18538.41
12

1544.87
61.18

734.14
1606.5

3

``` 




