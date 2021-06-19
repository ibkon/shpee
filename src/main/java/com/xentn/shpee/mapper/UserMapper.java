package com.xentn.shpee.mapper;

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
     List<TUser>    selectUsers(TUser user);
     int  insert(TUser user);
     int  update(TUser user);
     int  delete(String userId);

     List<TUserGroup>    selectUserGroups(TUserGroup group);
     int insertGroup(TUserGroup group);
     int updateGroup(TUserGroup group);
     int deleteGroup(String groupId);

     List<String> selectGroupRoles(String groupId);
     int  insertGroupRole(@Param("groupId") String groupId,@Param("roleName") String roleName);
     int  deleteGroupRole(@Param("groupId") String groupId,@Param("roleName") String roleName);
}
