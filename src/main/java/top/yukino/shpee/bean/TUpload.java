package top.yukino.shpee.bean;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class TUpload{
    private String UID;
    private String FILE_NAME;
    private String HASH;
    private String PATH;
    private String TYPE;
    private long   FILE_SIZE;
    private Timestamp   UPTIME;
    private int    ISDELETE;
}
