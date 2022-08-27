package com.itheima.reggie;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.itheima.reggie.mapper")
@EnableTransactionManagement
@Slf4j
public class WebAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebAppApplication.class,args);
        log.info("项目启动成功");
    }
}
