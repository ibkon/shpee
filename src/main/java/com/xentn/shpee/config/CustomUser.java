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

import java.util.ArrayList;
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
    private void setMapper(UserMapper mapper){
        this.mapper = mapper;
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
        List<String>    role    = null;
        TUser   tUser   = new TUser();
        List<TUser> tUsers  = null;

        //Verify that it is an email address, Query by email
        if(Pattern.matches("[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?",s)){
            tUser.setEmail(s);
        }
        //Query by username
        else{
            tUser.setUsername(s);
        }

        tUsers  = mapper.selectUsers(tUser);
        if(tUsers != null && tUsers.size()==1){
            tUser   = tUsers.get(0);
        }

        if(tUser==null){
            throw new UsernameNotFoundException("User does not exist");
        }
        role    = mapper.selectGroupRoles(tUser.getUserGroup());
        authorities = new ArrayList<>();
        for(String r:role){
            authorities.add(new SimpleGrantedAuthority(r));
        }
        return new User(tUser.getUsername(),tUser.getPassword(),tUser.isEnabled(),tUser.isAccountNonExpired(),tUser.isCredentialsNonExpired(),tUser.isAccountNonLocked(),authorities);
    }
}
