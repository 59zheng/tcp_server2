package com.utils;

import org.springframework.context.ApplicationContext;

public class DataContext {

    public static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext context){
       applicationContext = context;
    }
}
