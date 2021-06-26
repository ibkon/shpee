package com.xentn.shpee.controller.admin.api;

import com.xentn.shpee.bean.tool.Supper;
import com.xentn.shpee.bean.tool.ShpeeInfoCode;
import com.xentn.shpee.bean.user.UserAction;
import com.xentn.shpee.config.RoleList;
import com.xentn.shpee.mapper.ShpeeConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    private UserAction  userAction;
    private ShpeeConfigMapper   mapper;

    @Autowired
    public void setUserAction(UserAction userAction) {
        this.userAction = userAction;
    }

    @Autowired
    public void setMapper(ShpeeConfigMapper mapper) {
        this.mapper = mapper;
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
        if(mapper.getConfig("shpee_name")!=null){
            return buildInfo(ShpeeInfoCode.SHPEE_PROHIBITED_OPERATION,"403");
        }
        String  shpeeName   = request.getParameter("shpee_name");
        String  adminUser   = request.getParameter("admin_user_name");
        String  password    = request.getParameter("password");
        String  email       = request.getParameter("email");

        if(shpeeName==null||adminUser==null||password==null||email==null){
            return buildInfo(ShpeeInfoCode.SHPEE_ARGS_ERROR,"信息不完善");
        }

        this.userAction.addUserGroup("System administrator",
                adminUser,
                email,
                password,
                RoleList.getAllRoles()
        );
        mapper.setConfig("shpee_name",shpeeName);
        return buildInfo(ShpeeInfoCode.SHPEE_SUCCESS,"初始化站点成功");
    }
}
