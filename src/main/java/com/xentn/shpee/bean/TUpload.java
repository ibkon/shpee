package com.xentn.shpee.bean;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class TUpload implements Serializable {
    private String UID;
    private String FILENAME;
    private String HASH;
    private String PATH;
    private String TYPE;
    private String USER_ID;
    private long   SIZE;
    private Timestamp   UPTIME;
    private int    ISDELETE;
}
