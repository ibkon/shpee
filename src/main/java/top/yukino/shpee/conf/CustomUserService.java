package top.yukino.shpee.conf;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
        TUser   user    = null;
        List<SimpleGrantedAuthority>    authorities = new ArrayList<>();
        try {
            user  = mapper.selectTUser(buildMap("NAME",username)).get(0);
            List<String>     userRole= mapper.selectRole(username);
            for(String s:userRole){
                authorities.add(new SimpleGrantedAuthority(s));
            }
        }catch (ArrayIndexOutOfBoundsException e){
            throw new UsernameNotFoundException("验证失败：用户不存在");
        }catch (NullPointerException e){
            throw new UsernameNotFoundException("验证失败：用户权限异常");
        }
        return new org.springframework.security.core.userdetails.User(user.getNAME(),user.getPASSWORD(),authorities);
    }
}
