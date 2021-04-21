package com.xentn.shpee.mapper;

import com.xentn.shpee.bean.user.TRole;
import com.xentn.shpee.bean.user.TUser;
import com.xentn.shpee.bean.user.TUserGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/***
 * @Description: user mapper
 *
 * @author: ibkon
 * @date: 2021/3/14
 *
 */
@Mapper
public interface UserMapper {
     TUser selectByUsername(@Param("username") String username);
     TUser selectByEmail(@Param("email") String email);
     TUserGroup     selectUserGroup(@Param("groupId") int groupId);
     List<String>   selectRole(@Param("groupId") int groupId);

     int  insert(TUser user);
     int  update(TUser user);
     int  delete(TUser user);

     int  insertG(TUserGroup group);
     int  updateG(TUserGroup group);
     int  deleteG(TUserGroup group);

     int  selectR(@Param("roleName") String roleName);
     int  insertR(@Param("roleName") String roleName);
     int  updateR(TRole  role);
     int  deleteR(TRole role);

     int  insertGR(@Param("groupId") int groupId,@Param("roleId") int roleId);
     int  deleteGR(@Param("groupId") int groupId,@Param("roleId") int roleId);
}
