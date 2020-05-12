package top.yukino.shpee.conf;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import top.yukino.shpee.base.Super;
import top.yukino.shpee.bean.TUser;

import java.util.ArrayList;
import java.util.List;

/***
 * @Author Sagiri
 * @Date 2020/5/3
 * Custom UserDetailsService query
 */
public class CustomUserService extends Super implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TUser   user    = mapper.selectTUser(username);
        if(user==null)
            throw new UsernameNotFoundException("验证失败：用户不存在");
        String  userRole    = mapper.selectUserRole(user.getNAME());
        List<SimpleGrantedAuthority>    authorities = new ArrayList<>();
        List<String>    roles   = null;
        if(userRole==null||(roles = mapper.selectRole(userRole))==null)
            return new User(user.getNAME(),user.getPASSWORD(),authorities);
        for(String s:roles)
            authorities.add(new SimpleGrantedAuthority(s));
        return new org.springframework.security.core.userdetails.User(user.getNAME(),user.getPASSWORD(),authorities);
    }
}
