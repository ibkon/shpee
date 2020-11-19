package com.xentn.shpee.conf;

import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBatisConf {
	@Bean
	public ConfigurationCustomizer configurationCustomizer() {
		return new ConfigurationCustomizer() {
			
			@Override
			public void customize(org.apache.ibatis.session.Configuration configuration) {
				// TODO Auto-generated method stub
				configuration.setMapUnderscoreToCamelCase(true);
			}
		};
	}
}
