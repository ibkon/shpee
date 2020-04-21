package top.yukino.shpee;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.util.*;

@MapperScan(value = "top.yukino.shpee.conf")
@SpringBootApplication
public class ShpeeAppliction {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(ShpeeAppliction.class, args);
	}

}
