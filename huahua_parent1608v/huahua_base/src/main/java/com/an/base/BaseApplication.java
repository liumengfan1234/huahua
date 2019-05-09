package com.an.base;

import huahua.common.utils.IdWorker;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@MapperScan("com.an.base.mapper")
@EnableEurekaClient //声明使用当前的服务注册到eureka中
public class BaseApplication {


    public static void main(String[] args){
        SpringApplication.run(BaseApplication.class,args);
    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker(1,1);
    }

}