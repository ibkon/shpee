package com.xentn.shpee.bean.user;

import lombok.Data;

/***
 * @Description: user group
 *
 * @author: ibkon
 * @date: 2021/4/19
 * @version: 1.0
 */
@Data
public class TUserGroup {
    private int groupId;
    private String groupName;
    private boolean enable;
}
