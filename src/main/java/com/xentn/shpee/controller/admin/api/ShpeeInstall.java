package com.xentn.shpee.controller.admin.api;

import com.xentn.shpee.bean.tool.Supper;
import com.xentn.shpee.bean.tool.shpeeInfoCode;
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
    private int adminRoleId = 100000;

    @Autowired
    private void setEncoder(PasswordEncoder encoder){
        this.encoder    = encoder;
    }

    /**
     * @Author xentn
     * @Description //install mapping
     * @Date 2021/6/16
     * @Param [request]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @ResponseBody
    @RequestMapping("/admin/install")
    public Map<String,Object> install(HttpServletRequest request){
        String  shpeeName   = request.getParameter("shpee_name");
        String  adminUser   = request.getParameter("admin_user_name");
        String  password    = request.getParameter("password");
        String  email       = request.getParameter("email");

        if(shpeeName==null||adminUser==null||password==null||email==null){
            return buildInfo(shpeeInfoCode.SHPEE_ARGS_ERROR,"信息不完善");
        }

        TUser   user    = new TUser();

        user.setUserId(getUUID());
        user.setUsername(adminUser);
        user.setEmail(email);
        user.setPassword(encoder.encode(password));
        user.setUserGroup(this.adminGroupId);
        initial();

        getUserMapper().insert(user);
        getConfigMapper().setConfig("shpee_name",shpeeName);
        return buildInfo(shpeeInfoCode.SHPEE_SUCCESS,"初始化站点成功");
    }
    /**
     * @Author xentn
     * @Description //Initialize permissions
     * @Date 2021/6/16
     * @Param []
     * @return void
     */
    private void initial(){
        TUserGroup  adminGroup  = getUserMapper().selectUserGroup(this.adminGroupId);
        Integer   roles   = getUserMapper().selectR("ADMIN");
        //Determine whether the user group has been initialized
        if(adminGroup==null){
            adminGroup  = new TUserGroup();
            adminGroup.setGroupId(this.adminGroupId);
            adminGroup.setEnable(true);
            adminGroup.setGroupName("ADMIN");
            getUserMapper().insertG(adminGroup);
        }
        //Determine whether the role has been initialized
        if(roles==null||roles==0){
            getUserMapper().insertR("ADMIN");
        }
        getUserMapper().insertGR(this.adminGroupId,getUserMapper().selectR("ADMIN"));
    }
}
