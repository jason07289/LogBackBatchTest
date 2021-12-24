package com.jaehyuk.batch;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // 스케쥴링을 허용한다.
@MapperScan("com.jaehyuk.batch.dao")
public class LogBackBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogBackBatchApplication.class, args);
	}

}
