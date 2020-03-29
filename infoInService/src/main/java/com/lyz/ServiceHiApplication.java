package com.lyz;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 *  @Author: liyuzhan
 *  @classDesp： spring启动类
 *  @Date: 2020/3/22 12:10
 *  @Email: 1031759184@qq.com
 */
@SpringBootApplication
@EnableEurekaClient
public class ServiceHiApplication {

    public static void main(String[] args) {
        SpringApplication.run( ServiceHiApplication.class, args );
    }

}
