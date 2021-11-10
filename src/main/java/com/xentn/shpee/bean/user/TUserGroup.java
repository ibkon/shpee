package com.xentn.shpee.bean.user;

import lombok.Data;

import java.io.Serializable;

/***
 * @Description: user group
 *
 * @author: ibkon
 * @date: 2021/4/19
 * @version: 1.0
 */
@Data
public class TUserGroup implements Serializable {
    private String groupId;
    private String groupAdminUserId;
    private String groupName;
    private boolean enable = true;
}
