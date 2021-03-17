package com.xentn.shpee;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/***
 * @Description: shpee start
 *
 * @author: ibkon
 * @date: 2021/3/14
 * @version: 1.0
 */
@MapperScan(value = "com.xentn.shpee.mapper")
@SpringBootApplication
public class ShpeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShpeeApplication.class, args);
	}

}
