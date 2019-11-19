package com.shitao;


import java.lang.annotation.ElementType;


import java.lang.annotation.Retention;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by shitao on 2018/7/30.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BigAnno {
    String value();
}
