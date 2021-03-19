package com.xentn.shpee.mapper;

import com.xentn.shpee.bean.user.TUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
}
