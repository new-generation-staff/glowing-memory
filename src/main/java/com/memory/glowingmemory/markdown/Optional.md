#Optional
```markdown
Optional.of(T t)：创建一个Optional实例
Optional.empty()：创建一个空的Optional实例
Optional.ofNullable(T t)：若t不为null,创建Optional实列，否则创建空实例
isPresent()：判断是否包含值
orElse(T t)：如果调用对象包含值，返回该值，否则返回t
orElseGet(Supplier s)：如果调用对象包含值，返回该值，否则返回s获取的值
map(Function f)：如果有值对其处理，并返回处理后的Optional，否则返回Optional.empty()
flatMap(Function mapper)：与map类似，要求返回值必须是Optional
```

```markdown
    /**
     * Optional容器类的常用方法：
     * Optional.of(T t)：创建一个Optional实例
     * Optional.empty()：创建一个空的Optional实例
     * Optional.ofNullable(T t)：若t不为null,创建Optional实列，否则创建空实例
     * isPresent()：判断是否包含值
     * orElse(T t)：如果调用对象包含值，返回该值，否则返回t
     * orElseGet(Supplier s)：如果调用对象包含值，返回该值，否则返回s获取的值
     * map(Function f)：如果有值对其处理，并返回处理后的Optional，否则返回Optional.empty()
     * flatMap(Function mapper)：与map类似，要求返回值必须是Optional
     */
    @Test
    public void test1() {
        Optional<Map> empty = Optional.of(new HashMap());
        Map map = empty.get();
        map.put("bbb","111");
    }

    @Test
    public void test2() {
        Optional<Employee> op = Optional.empty();
        if(op.isPresent()){
            System.out.println(op.get());
        }
    }

    @Test
    public void test3() {
        Optional<Employee> op = Optional.ofNullable(null);
        if (op.isPresent()) {
            System.out.println(op.get());
        }
        System.out.println("-------------------------------------------------------");
        Employee emp = op.orElse(new Employee("zhangsan", 18, 888.88, Employee.Status.FREE));
        System.out.println(emp);
        System.out.println("-------------------------------------------------------");
        Employee emp1 = op.orElseGet(() -> new Employee());
        System.out.println(emp1);
    }

    @Test
    public void test4() {
        Optional<Employee> op = Optional.ofNullable(new Employee("zhangsan", 18, 888.88, Employee.Status.FREE));
//        Optional<String> s = op.map((e) -> e.getName());
//        System.out.println(s.get());
        //多一步判空
        Optional<String> s = op.flatMap((e) -> Optional.of(e.getName()));
        System.out.println(s.get());

    }

    @Test
    public void test5() {
        Man man = new Man();
        String name = getGodnessName(man);
        System.out.println(name);

    }

    //需求：获取男生心中女神的名字
    public String getGodnessName(Man man) {
//        return man.getGoddness().getName();
        if (man != null) {
            Godness gn = man.getGoddness();
            if (gn != null) {
                return gn.getName();
            }
        }
        return "小花";
    }

    public String getGodnessName2(Optional<NewMan> man) {
        return man.orElse(new NewMan())
                .getGodness()
                .orElse(new Godness("小花花"))
                .getName();
    }

    @Test
    public void test6(){
        Optional<Godness> gn = Optional.ofNullable(new Godness("小红"));
        Optional<NewMan> op = Optional.ofNullable(new NewMan(gn));
//        Optional<Godness> gn = Optional.ofNullable(null);
//        Optional<NewMan> op = Optional.ofNullable(null);
        String str = getGodnessName2(op);
        System.out.println(str);

    }

```