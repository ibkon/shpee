package com.xentn.shpee.bean.user;

import lombok.Data;

import java.io.Serializable;

/***
 * @Description: User table bean
 *
 * @author: xentn
 * @date: 2021/3/14
 * @version: 1.0
 */
@Data
public class TUser implements Serializable {
    private String  userId;
    private String  username;
    private String  password;
    private String  email;
    private int     userGroup;
    private boolean enabled;
    private boolean accountNonExpired;
    private boolean credentialsNonExpired;
    private boolean accountNonLocked;
}
