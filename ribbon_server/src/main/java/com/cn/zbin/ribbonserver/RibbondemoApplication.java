package com.cn.zbin.ribbonserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
@EnableScheduling
public class RibbondemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RibbondemoApplication.class, args);
	}

	@Bean
	@LoadBalanced
	RestTemplate restTemplate() {return new RestTemplate();}

}
