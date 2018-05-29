package com.cn.zbin.store;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@EnableTransactionManagement
@MapperScan("com.cn.zbin.store.mapper")  
@ComponentScan(basePackages = {"com.cn.zbin.store.*"})
public class StoreApplication {
    @Autowired
    private RestTemplateBuilder builder;  
  
    // 使用RestTemplateBuilder来实例化RestTemplate对象，spring默认已经注入了RestTemplateBuilder实例  
    @Bean
    public RestTemplate restTemplate() {  
        return builder.build();
    } 
    
	public static void main(String[] args) {
		SpringApplication.run(StoreApplication.class, args);
	}
}
