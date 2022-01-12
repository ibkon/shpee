package com.xentn.shpee.controller.admin.api;

import com.xentn.shpee.bean.tool.ShpeeInfoCode;
import com.xentn.shpee.bean.tool.Supper;
import com.xentn.shpee.bean.user.UserAction;
import com.xentn.shpee.config.RoleList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
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

    private UserAction  userAction;

    @Autowired
    public void setUserAction(UserAction userAction) {
        this.userAction = userAction;
    }

    /**
     * @Author xentn
     * @Description //install mapping
     * @Date 2021/6/16
     * @Param [request]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @ResponseBody
    @PostMapping("/admin/install")
    public Map<String,Object> install(HttpServletRequest request){
        System.err.println("in admin install.");
        if(getConfigMapper().getConfig("shpee_name")!=null){
            return buildInfo(ShpeeInfoCode.SHPEE_PROHIBITED_OPERATION,"站点已注册");
        }
        String  shpeeName   = request.getParameter("shpee_name");
        String  adminUser   = request.getParameter("admin_user_name");
        String  password    = request.getParameter("password");
        String  email       = request.getParameter("email");

        if(shpeeName==null||adminUser==null||password==null||email==null){
            return buildInfo(ShpeeInfoCode.SHPEE_ARGS_ERROR,"信息不完善");
        }

        //Add system administrator user group.
        this.userAction.addUserGroup("administrator",
                adminUser,
                email,
                password,
                RoleList.getAllRoles()
        );
        getConfigMapper().setConfig("shpee_name",shpeeName);
        return buildInfo(ShpeeInfoCode.SHPEE_SUCCESS,"初始化站点成功");
    }
}
