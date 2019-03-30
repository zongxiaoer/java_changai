package com.yuntongxun.itsys.gateway.common.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * String.format 字符串以%s替换字符
 * @author lilong
 *
 */
public abstract class AbstractLogger {

    protected Logger logger; 
//    private static final String SEPARATOR = "----------------------------";
    
    public AbstractLogger(){
        super();
        if(logger == null){
            logger = LogManager.getLogger(getClassName());
        }
    }
    public AbstractLogger(Class<?> c){
        logger = LogManager.getLogger(c);
    }
    
    /**
     * 获取类名
     * @return
     */
    protected abstract Class<?> getClassName();
    
    
    public void debug(String message,Object... args) {
        if(logger.isDebugEnabled()){
            logger.debug(message,args);
        }
    }
    
    public void debug(Class<?> c,String message,Object... args) {
        if(logger.isDebugEnabled()){
            logger.debug(message,args);
        }
    }
    
    public void debug(String message) {
        if(logger.isDebugEnabled()){
            logger.debug(message);
        }
    }
    
    public void debug(Class<?> c,String message) {
        if(logger.isDebugEnabled()){
            logger.debug(message);
        }
    }
    
    
    public void info(String message,Object... args) {
        if(logger.isInfoEnabled()){
            logger.info(message,args);
        }
    }
    
    public void info(Class<?> c,String message,Object... args) {
        if(logger.isInfoEnabled()){
            logger.info(message,args);
        }
    }
    
    public void info(String message) {
        if(logger.isInfoEnabled()){
            logger.info(message);
        }
    }
    
    public void info(Class<?> c,String message) {
        if(logger.isInfoEnabled()){
            logger.info(message);
        }
    }
    
    public void error(Object message, Throwable t) {
        logger.error(message, t);
    }
    
    public void warn(String message,Object... args) {
        logger.warn(message, args);
    }
    
    public void warn(Object message) {
        logger.warn(message);
    }
    
    public void warn(Object message, Throwable t) {
        logger.warn(message, t);
    }
    
    /**
     * print start tag margin 1 with up.
     * 
     * @param name
     * @param space
     * @return
     */
    public void printStartTag(String tagName) {
        printStartTag(tagName, MARGIN_0);
    }
    
    /**
     * print start tag
     * 
     * @param name
     * @param space
     * @return
     */
    public void printStartTag(String tagName, int space) {
        StringBuilder sb = new StringBuilder();
        switch (space) {
        case MARGIN_0:
            break;
        case MARGIN_1:
            sb.append("\r\n");
            break;
        case MARGIN_2:
            sb.append("\r\n\r\n");
            break;
        default:
            sb.append("\r\n");
            break;
        }
        sb.append("----------------------------[").append(tagName).append(" Start").append(
                "]-------------------------------");
        logger.info(sb.toString());
    }

    /**
     * print end tag
     * 
     * @param name
     * @param space
     * @return
     */
    public final void printEndTag(String tagName) {
        printEndTag(tagName, MARGIN_1);
    }

    /**
     * distance of up or down
     */
    public static final int MARGIN_0 = 0;
    public static final int MARGIN_1 = 1;
    public static final int MARGIN_2 = 2;

    /**
     * print end tag
     * 
     * @param name
     * @param space
     * @return
     */
    public final void printEndTag(String tagName, int space) {
        StringBuilder sb = new StringBuilder("----------------------------[").append(tagName).append(" End").append(
                "]-------------------------------");
        switch (space) {
        case MARGIN_0:
            break;
        case MARGIN_1:
            sb.append("\r\n");
            break;
        case MARGIN_2:
            sb.append("\r\n\r\n");
            break;
        default:
            sb.append("\r\n");
            break;
        }
        logger.info(sb.toString());
    }
}
