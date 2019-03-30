package com.yuntongxun.itsys.gateway.common.simple;


import com.yuntongxun.itsys.gateway.common.base.AbstractLogger;

public class SimpleLogger extends AbstractLogger{
    
    private Class<?> c;
    
    public SimpleLogger(Class<?> c){
        super(c);
        this.c = c;
    }

    
    @Override
    protected Class<?> getClassName() {
        return c;
    }
    

}
