package com.xentn.shpee.controller.content;

import com.xentn.shpee.bean.tool.Supper;
import com.xentn.shpee.bean.user.TUser;
import com.xentn.shpee.bean.user.TUserGroup;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * @Description: index controller
 *
 * @author: ibkon
 * @date: 2021/3/14
 * @version: 1.0
 */
@Controller
public class MainController extends Supper {

    private boolean isInitial;

    @GetMapping("/")
    public String index(){
        if(!isInitial){
            String  shpeeName   = getConfigMapper().getConfig("shpee_name");
            if(shpeeName==null||shpeeName.equals("")){
                this.isInitial  = false;
            }else {
                this.isInitial  = true;
            }
            if(!isInitial){
                return "admin/install";
            }
            return "admin/install";
            //return "view/index";
        }
        if(getShpeeCache().get("user-group-id")==null){
            List<TUserGroup> userGroups  =    getUserMapper().selectUserGroups(null);
            Map<String,String> key     = new HashMap<>();
            for(TUserGroup tg:userGroups){
                key.put(tg.getGroupId(),tg.getGroupName());
            }
            setShpeeCache("user-group-id",key);
        }
        if(getUserDetails()!=null){
            TUser user    = new TUser();
            user.setUsername(getUserDetails().getUsername());
            user    = getUserMapper().selectUsers(user).get(0);
            Map<String,String>  cache   = (Map<String,String>)getShpeeCache().get("user-group-id");
            System.err.println(user);
            System.err.println(cache.get(user.getUserGroup()));
        }
        return "admin/install";
        //return "view/index";
    }
}
