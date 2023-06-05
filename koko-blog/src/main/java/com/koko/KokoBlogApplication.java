package com.koko;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author koko
 * @Email c_wkoko@qq.com
 * @creat 2023-03-02-20:00
 */
@SpringBootApplication
@MapperScan("com.koko.mapper")
@EnableScheduling
@EnableSwagger2
public class KokoBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(KokoBlogApplication.class,args);
    }
}
