package com.app.rxjava.java_test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 描述：动态代理学习Demo
 * 在某些情况下，我们不希望或是不能直接访问对象 A，而是通过访问一个中介对象 B，由 B 去访问 A 达成目的，这种方式我们就称为代理。
 * 这里对象 A 所属类我们称为委托类，也称为被代理类，对象 B 所属类称为代理类。
 * 代理优点：
 * ①隐藏委托类的实现
 * ②解耦，不改变委托类代码情况下做一些额外处理，比如添加初始判断及其他公共操作
 */
public class ProxyClassTest {


    /**
     * 委托类实现的接口
     */
    public static interface Operator{
        void operatorMethed01();
        void operatorMethed02();
    }

    /**
     * 委托类
     */
    public static class OperatorImpl implements Operator{
        @Override
        public void operatorMethed01() {
            System.out.println("执行了operatorMethed01( )方法");
        }

        @Override
        public void operatorMethed02() {
            System.out.println("执行了operatorMethed02( )方法");
        }
    }


    /**
     * 连接代理类和委托类的中间类
     */
    public static class MyInvocationHandler implements InvocationHandler{

        Operator mOperator; //委托类

        public MyInvocationHandler(Operator operator) {
            mOperator = operator;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println(method.getName() + "执行前，当前时间：" + System.currentTimeMillis());
            Object object = method.invoke(mOperator, args);
            System.out.println(method.getName() + "执行后，当前时间：" + System.currentTimeMillis());
            return object;
        }
    }


    public static void main(String[] args){
        //动态代理
        MyInvocationHandler invocationHandler = new MyInvocationHandler(new OperatorImpl());
        Operator operator = (Operator) Proxy.newProxyInstance(Operator.class.getClassLoader(), new Class[]{Operator.class}, invocationHandler);
        operator.operatorMethed01();
        operator.operatorMethed02();
    }
}
