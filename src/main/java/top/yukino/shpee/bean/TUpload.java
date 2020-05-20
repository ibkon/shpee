package top.yukino.shpee.bean;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class TUpload implements Serializable {
    private String UID;
    private String FILE_NAME;
    private String HASH;
    private String PATH;
    private String TYPE;
    private long   FILE_SIZE;
    private Timestamp   UPTIME;
    private int    ISDELETE;
}
