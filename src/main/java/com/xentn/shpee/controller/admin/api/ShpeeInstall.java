package com.xentn.shpee.controller.admin.api;

import com.xentn.shpee.bean.tool.Supper;
import com.xentn.shpee.bean.user.TUser;
import com.xentn.shpee.bean.user.TUserGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/***
 * @Description: install
 *
 * @author: ibkon
 * @date: 2021/3/22
 * @version: 1.0
 */
@Controller
public class ShpeeInstall extends Supper {

    private PasswordEncoder encoder;

    private int adminGroupId  = 10000;
    private int userGroupId   = 20001;

    @Autowired
    private void setEncoder(PasswordEncoder encoder){
        this.encoder    = encoder;
    }

    @ResponseBody
    @RequestMapping("/admin/install")
    public Map<String,Object> install(HttpServletRequest request){
        String  shpeeName   = request.getParameter("shpee_name");
        String  adminUser   = request.getParameter("admin_user_name");
        String  password    = request.getParameter("password");
        String  email       = request.getParameter("email");
        TUser   user    = new TUser();

        user.setUserId(getUUID());
        user.setUsername(adminUser);
        user.setEmail(email);
        user.setPassword(encoder.encode(password));
        user.setGroup(this.adminGroupId);
        System.out.println(user);
        initialGroup();
        initialRole();

        getUserMapper().insert(user);
        getConfigMapper().setConfig("shpee_name",shpeeName);
        return buildInfo(0,"初始化站点成功");
    }
    /**
     * @Author xentn
     * @Description //initial user group
     * @Date 2021/4/19
     * @Param []
     * @return void
     */
    private void initialGroup(){
        TUserGroup  adminGroup  = new TUserGroup();
        TUserGroup  userGroup   = new TUserGroup();

        adminGroup.setGroupId(this.adminGroupId);
        adminGroup.setGroupName("root");
        adminGroup.setEnable(true);

        userGroup.setGroupId(this.userGroupId);
        userGroup.setGroupName("user");
        adminGroup.setEnable(true);

        getUserMapper().insertG(adminGroup);
        getUserMapper().insertG(userGroup);

    }

    /**
     * @Author xentn
     * @Description //initial role
     * @Date 2021/4/21
     * @Param []
     * @return void
     */
    private void initialRole(){
        String  administrator    = "ROLE_ADMINISTRATOR";
        String  general          = "ROLE_GENERAL";
        int     administratorID  = -1;
        int     generalID  = -1;

        getUserMapper().insertR(administrator);
        getUserMapper().insertR(general);
        administratorID = getUserMapper().selectR(administrator);
        generalID = getUserMapper().selectR(general);
        getUserMapper().insertGR(this.adminGroupId,administratorID);
        getUserMapper().insertGR(this.adminGroupId,generalID);
        getUserMapper().insertGR(this.userGroupId,generalID);

    }
}
