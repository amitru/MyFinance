package com.amitru.mystockalert.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StockAlertMain 
{
    public static void main( String[] args ) throws Exception
    {
    	ApplicationContext myAppContext = new ClassPathXmlApplicationContext("Spring-Module.xml");
    }
}
