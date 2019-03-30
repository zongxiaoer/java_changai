package com.yuntongxun.itsys.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.servlet.MultipartConfigElement;

@EnableDiscoveryClient
@SpringBootApplication
@EnableScheduling // 定时任务
@EnableCaching
public class BaseService extends SpringBootServletInitializer {

//	public static void main(String[] args) {
////		new SpringApplicationBuilder(BaseService.class).web(true).run(args);
//		SpringApplication.run(BaseService.class, args);
//	}
//
//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//		return application.sources(BaseService.class);
//	}


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BaseService.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(BaseService.class, args);
    }
}
