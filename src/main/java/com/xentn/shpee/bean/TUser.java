package com.xentn.shpee.bean;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/***
 * 用户类
 */
@Data
public class TUser implements Serializable {
    private String  NAME;
    private String  PASSWORD;
    private Timestamp   UPTIME;
}
