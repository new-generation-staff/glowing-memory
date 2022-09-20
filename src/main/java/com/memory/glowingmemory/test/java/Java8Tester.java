package com.memory.glowingmemory.test.java;

/**
 * @author zc
 * 函数式编程
 */
public class Java8Tester {
    public static void main(String args[]) {
        Java8Tester tester = new Java8Tester();

        // 类型声明
        MathOperation addition = (int a, int b) -> a + b;

        // 不用类型声明
        MathOperation subtraction = (a, b) -> a - b;

        // 大括号中的返回语句
        MathOperation multiplication = (int a, int b) -> {
            return a * b;
        };

        MathOperation tradition = new MathOperation() {
            @Override
            public int operation(int a, int b) {
                return a - b;
            }
        };

        // 没有大括号及返回语句
        MathOperation division = (int a, int b) -> a / b;

        System.out.println("10 + 5 = " + tester.operate(10, 5, addition));
        System.out.println("10 - 5 = " + tester.operate(10, 5, subtraction));
        System.out.println("10 x 5 = " + tester.operate(10, 5, multiplication));
        System.out.println("10 / 5 = " + tester.operate(10, 5, division));

        // 不用括号
        GreetingService greetService1 = message ->
                System.out.println("Hello " + message);

        // 用括号
        GreetingService greetService2 = (message) ->
                System.out.println("Hello " + message);

        GreetingService greetService3 = new GreetingService() {
            @Override
            public void sayMessage(String message) {
                System.out.println("Hello " + message);
            }
        };

        greetService1.sayMessage("Runoob");
        greetService2.sayMessage("Google");
        greetService3.sayMessage("tradition");
    }

    interface MathOperation {
        int operation(int a, int b);
    }

    interface GreetingService {
        void sayMessage(String message);
    }

    private int operate(int a, int b, MathOperation mathOperation) {
        return mathOperation.operation(a, b);
    }
}
