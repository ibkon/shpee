package com.xentn.shpee.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/***
 * @Description: bean config
 *
 * @author: ibkon
 * @date: 2021/4/19
 * @version: 1.0
 */
@Configuration
public class BeanConfig {
    @Bean
    public PasswordEncoder shpeePasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return new CustomUser();
    }
}
