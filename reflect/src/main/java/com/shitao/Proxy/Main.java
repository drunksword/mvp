package com.shitao.Proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

/**
 * Created by shitao on 2018/7/30.
 */
public class Main {
    public static void main(String... args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        B b = new B();

        A proxy = (A) Proxy.newProxyInstance(A.class.getClassLoader(), B.class.getInterfaces(), (proxy1, method, args1) -> {

            System.out.println(String.format("before method[%s] running...", method.getName()));
            Object res = method.invoke(b, args1);
            System.out.println(String.format("after method[%s] running...", method.getName()));
            return res;
        });

        proxy.fuck();

//        proxy.getClass().getMethod("fuck").invoke(proxy);
    }
}
