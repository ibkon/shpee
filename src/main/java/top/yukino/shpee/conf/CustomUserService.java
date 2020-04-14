package top.yukino.shpee.conf;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import top.yukino.shpee.bean.User;
import top.yukino.shpee.base.Super;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomUserService extends Super implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            User user    = new User();
            user.setROLE(new ArrayList<>());
            List<Map<String,Object>>    lMap;
            lMap=mapper.select("select * from T_USER where NAME='"+username+"'");
            if(lMap==null||lMap.size()==0){
                throw new UsernameNotFoundException("无效用户名");
            }
            user.setNAME(lMap.get(0).get("NAME").toString());
            user.setPASSWORD(lMap.get(0).get("PASSWORD").toString());
            lMap=mapper.select("select * from T_USER_ROLE where NAME='"+username+"'");
            List<SimpleGrantedAuthority>    authorities = new ArrayList<>();
            if(lMap==null||lMap.size()==0){
                throw new UsernameNotFoundException("用户无权限");
            }
            for(Map<String,Object> m:lMap){
                authorities.add(new SimpleGrantedAuthority(mapper.select("select ROLE from T_ROLE where RID='"+
                        m.get("RID").toString()+"'").get(0).get("ROLE").toString()));
            }
            System.out.println(user.getNAME()+":"+user.getPASSWORD());
            System.out.println(authorities);
            System.out.println();
            return new org.springframework.security.core.userdetails.User(user.getNAME(),user.getPASSWORD(),authorities);
    }
}
