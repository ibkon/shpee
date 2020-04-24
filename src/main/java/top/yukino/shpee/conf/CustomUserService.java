package top.yukino.shpee.conf;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import top.yukino.shpee.base.Super;
import top.yukino.shpee.bean.TUser;

import java.util.ArrayList;
import java.util.List;

public class CustomUserService extends Super implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            TUser   user    = new TUser(mapper);
            Integer retVal  = null;
            user.setNAME(username);
            retVal  = user.select();
            if(retVal==null||retVal==0){
                throw new UsernameNotFoundException("验证失败："+retVal);
            }
            List<SimpleGrantedAuthority>    authorities = new ArrayList<>();
            for(String s:user.getRole()){
                authorities.add(new SimpleGrantedAuthority(s));
            }
            return new org.springframework.security.core.userdetails.User(user.getNAME(),user.getPASSWORD(),authorities);
    }
}
