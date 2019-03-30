package com.yuntongxun.itsys.gateway.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yuntongxun.itsys.gateway.filter.logpost.LogPostFilter;
import com.yuntongxun.itsys.gateway.filter.post.AccessPostFilter;
import com.yuntongxun.itsys.gateway.filter.pre.AccessPreFilter;

@Configuration
public class FilterConfig {

    /**
     * 拦截请求之前
     * order:0
     * @return
     */
    @Bean
    public AccessPreFilter accessPreFilter() {
        return new AccessPreFilter();
    }
    
    @Bean
    public LogPostFilter logPostFilter(){
    	return new LogPostFilter();
    }
    /**
     * 拦截请求之后
     * order:0
     * @return
     */
    @Bean
    public AccessPostFilter accessPostFilter() {
        return new AccessPostFilter();
    }
}
