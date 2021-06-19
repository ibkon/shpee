package com.xentn.shpee.config;

import java.util.ArrayList;
import java.util.List;

/***
 * @Description: role list
 *
 * @author: ibkon
 * @date: 2021/6/19
 * @version: 1.0
 */
public class RoleList {
    public final static String ROLE_SYSTEM_ADMIN  = "SYSTEM_ADMIN";
    public final static String ROLE_UPLOAD        = "UPLOAD";
    public final static String ROLE_CLOUD_DISK    = "CLOUD_DISK";

    public static String getRoleSystemAdmin() {
        return "ROLE_"+ROLE_SYSTEM_ADMIN;
    }

    public static String getRoleUpload() {
        return "ROLE_"+ROLE_UPLOAD;
    }

    public static String getRoleCloudDisk() {
        return "ROLE_"+ROLE_CLOUD_DISK;
    }

    public static List<String> getAllRoles(){
        List<String>    roles   = new ArrayList<>();
        roles.add(getRoleSystemAdmin());
        roles.add(getRoleUpload());
        roles.add(getRoleCloudDisk());
        return roles;
    }

}
