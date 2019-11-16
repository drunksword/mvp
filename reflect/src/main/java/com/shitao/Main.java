package com.shitao;


import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws NoSuchFieldException {
        Foo foo = new Foo();
        Field field = foo.getClass().getField("name");

        BigAnno bigAnno = field.getAnnotation(BigAnno.class);

        System.out.println(bigAnno.value());
    }

    @Test
    public void other() throws NoSuchFieldException, IllegalAccessException {
        Foo foo = new Foo();
        Field field = foo.getClass().getField("name");

        BigAnno bigAnno = field.getAnnotation(BigAnno.class);
        System.out.println(bigAnno.value());

        InvocationHandler handler = Proxy.getInvocationHandler(bigAnno);
        Field hField = handler.getClass().getDeclaredField("memberValues");
        hField.setAccessible(true);

        Map map = (Map) hField.get(handler);
        map.put("value", "shit!");
        System.out.println(bigAnno.value());
    }
}
