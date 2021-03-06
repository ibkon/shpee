package com.xentn.shpee.config;

import com.xentn.shpee.bean.user.TUser;
import com.xentn.shpee.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;

/***
 * @Description: Login based on user
 *
 * @author: ibkon
 * @date: 2021/3/14
 * @version: 1.0
 */
@Component
public class CustomUser implements UserDetailsService {

    private UserMapper  mapper;

    @Autowired
    public CustomUser(UserMapper mapper){
        this.mapper=mapper;
    }

    /**
     * Account login verification
     * @param s username or email
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        if(s==null||s.equals("")){
            throw  new UsernameNotFoundException("Username is null");
        }
        List<SimpleGrantedAuthority>    authorities = null;
        TUser   tUser   = null;
        //Verify that it is an email address, Query by email
        if(Pattern.matches("[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?",s)){
            tUser   = mapper.selectByEmail(s);
        }
        //Query by username
        else{
            tUser   = mapper.selectByUsername(s);
        }
        if(tUser==null){
            throw new UsernameNotFoundException("User does not exist");
        }
        return new User(tUser.getUsername(),tUser.getPassword(),tUser.isEnabled(),tUser.isAccountNonExpired(),tUser.isCredentialsNonExpired(),tUser.isAccountNonLocked(),authorities);
    }
}
