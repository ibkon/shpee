package com.xentn.shpee;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(value = "com.xentn.shpee.conf")
@SpringBootApplication
public class ShpeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShpeeApplication.class, args);
	}

}
