package com.xentn.shpee.config;

import com.xentn.shpee.mapper.ShpeeConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/***
 * @Description: security config
 *
 * @author: ibkon
 * @date: 2021/3/14
 * @version: 1.0
 */
@Configuration
@EnableWebSecurity
public class Security extends WebSecurityConfigurerAdapter {
    private PasswordEncoder encoder;
    private UserDetailsService  userDetailsService;
    private ShpeeConfigMapper   mapper;

    @Autowired
    private void setEncoder(PasswordEncoder encoder){
        this.encoder    = encoder;
    }

    @Autowired
    private void setUserDetailsService(UserDetailsService userDetailsService){
        this.userDetailsService = userDetailsService;
    }
    @Autowired
    private void setMapper(ShpeeConfigMapper mapper){
        this.mapper = mapper;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().antMatchers("/").permitAll()
                .antMatchers("/admin/install").permitAll()
                .antMatchers("/admin/**").hasRole(RoleList.ROLE_SYSTEM_ADMIN)
                .antMatchers("/cloud/**").hasRole(RoleList.ROLE_CLOUD_DISK)
                .antMatchers("/upload/**").hasRole(RoleList.ROLE_UPLOAD);
        http.headers().frameOptions().disable();
        http.formLogin();
        http.logout();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
    }
}
