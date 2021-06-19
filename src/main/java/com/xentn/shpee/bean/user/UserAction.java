package com.xentn.shpee.bean.user;

import com.xentn.shpee.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/***
 * @Description: User action
 *
 * @author: ibkon
 * @date: 2021/6/19
 * @version: 1.0
 */
@Component
public class UserAction {

    private UserMapper mapper;
    private PasswordEncoder encoder;

    /**
     * @Author xentn
     * @Description //Create a user group, and must add the user group administrator
     * @Date 2021/6/20
     * @Param [group, user]
     * @return void
     */
    public String addUserGroup(String groupName, String userName, String userEmail, String userPassword, List<String> roles){
        TUserGroup  group   = new TUserGroup();
        TUser       user    = new TUser();

        String  groupId     = getUUID();
        String  userId      = getUUID();

        //set group info
        group.setGroupId(groupId);
        group.setGroupName(groupName);
        group.setGroupAdminUserId(userId);

        //set user info
        user.setUserId(userId);
        user.setUserGroup(groupId);
        user.setUsername(userName);
        user.setEmail(userEmail);
        user.setPassword(encoder.encode(userPassword));

        mapper.insertGroup(group);
        mapper.insert(user);
        for(String role:roles){
            mapper.insertGroupRole(groupId,role);
        }

        return groupId;

    }
    /**
     * @Author xentn
     * @Description //add user
     * @Date 2021/6/20
     * @Param [groupId, userName, userEmail, userPassword]
     * @return java.lang.String
     */
    public String addUser(String groupId,String userName, String userEmail, String userPassword){
        TUser       user    = new TUser();
        String  userId      = getUUID();

        user.setUserId(userId);
        user.setUserGroup(groupId);
        user.setUsername(userName);
        user.setEmail(userEmail);
        user.setPassword(encoder.encode(userPassword));

        mapper.insert(user);

        return userId;
    }
    /**
     * @Author xentn
     * @Description //remove group role
     * @Date 2021/6/20
     * @Param [groupId, roleName]
     * @return void
     */
    public int removeRole(String groupId,String roleName){
        return mapper.deleteGroupRole(groupId,roleName);
    }

    @Autowired
    public void setMapper(UserMapper mapper){
        this.mapper = mapper;
    }
    @Autowired
    private void setEncoder(PasswordEncoder encoder){
        this.encoder    = encoder;
    }
    protected String    getUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
