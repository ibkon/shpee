package top.yukino.shpee.bean;

import lombok.Data;

import java.sql.Timestamp;

/***
 * 用户类
 */
@Data
public class TUser{
    private String  NAME;
    private String  PASSWORD;
    private Timestamp   UPTIME;
}
