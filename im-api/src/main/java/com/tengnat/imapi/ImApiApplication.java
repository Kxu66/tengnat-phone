package com.tengnat.imapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;


@MapperScan(basePackages = {"com.tengnat.mybatis.mapper"})
@SpringBootApplication(scanBasePackages = "com.tengnat")
public class ImApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImApiApplication.class, args);
    }
}
