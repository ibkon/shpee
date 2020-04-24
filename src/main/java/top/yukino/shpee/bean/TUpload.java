package top.yukino.shpee.bean;

import lombok.Data;
import lombok.Getter;
import org.apache.commons.codec.digest.DigestUtils;
import top.yukino.shpee.conf.Mapper;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
public class TUpload extends AbsBeanSql {
    private String UID;
    private String FILE_NAME;
    private String HASH;
    private String PATH;
    private String TYPE;
    private long   FILE_SIZE;
    private Timestamp   UPTIME;
    private int    ISDELETE;

    private Map<String,String>  args;

    private @Getter
    List<TUpload>   tUploads;

    public TUpload(Mapper   mapper){
        super(mapper);
        this.ISDELETE   = 0;
        this.FILE_SIZE  = 0;
        this.UPTIME     = null;
    }

    public String getUrl() {
        String  url ="/static/files?uid="+this.UID
                +"&timeout="+(System.currentTimeMillis()+0x1b7740);
        String  hash= DigestUtils.md5Hex(url).substring(20);
        return url+"&code="+hash;
    }
    private void setValues(TUpload tUpload){
        if(map!=null){
            tUpload.UID         = o2s("UID");
            tUpload.FILE_NAME   = o2s("FILE_NAME");
            tUpload.HASH        = o2s("HASH");
            tUpload.PATH        = o2s("PATH");
            tUpload.TYPE        = o2s("TYPE");
            tUpload.FILE_SIZE   = o2l("FILE_SIZE");
            tUpload.UPTIME      = o2t("UPTIME");
        }
    }

    @Override
    public Integer select() {
        boolean where   = false;
        StringBuilder   sql = new StringBuilder();
        sql.append("SELECT UID,FILE_NAME,HASH,PATH,TYPE,FILE_SIZE,UPTIME FROM T_UPLOAD WHERE ");
        //优先按照UID查询
        if(UID!=null&&!UID.equals("")){
            sql.append("UID='");
            sql.append(UID);
            sql.append("' ");
            if(ISDELETE!=1){
                sql.append("AND ISDELETE=");
                sql.append(ISDELETE);
            }
            lMap    = mapper.select(sql.toString());
            if(lMap.size()>0){
                map = lMap.get(0);
                setValues(this);
            }
            return lMap.size();
        }
        //根据HASH查询
        if(HASH!=null&&!HASH.equals("")){
            sql.append("HASH='");
            sql.append(HASH);
            sql.append("' ");
            if(ISDELETE!=1){
                sql.append("AND ISDELETE=");
                sql.append(ISDELETE);
            }
            lMap    = mapper.select(sql.toString());
            if(lMap.size()>0){
                map = lMap.get(0);
                setValues(this);
            }
            return lMap.size();
        }
        //添加文件名条件
        if(FILE_NAME!=null&&!FILE_NAME.equals("")){
            if(where) sql.append(" AND ");
            where   = true;
            sql.append(" FILE_NAME='");
            sql.append(FILE_NAME);
            sql.append("' ");
        }
        //添加文件类型条件
        if(TYPE!=null&&!TYPE.equals("")){
            if(where) sql.append(" AND ");
            if(TYPE.indexOf(',')!=-1){
                String[]    types   = TYPE.split(",");
                where   = false;
                sql.append("(");
                for(String s:types){
                    if(where)sql.append(" OR ");
                    sql.append("TYPE='");
                    sql.append(s);
                    sql.append("'");
                    where=true;
                }
                sql.append(") ");
            }else {
                sql.append(" TYPE='");
                sql.append(TYPE);
                sql.append("' ");
            }
            where   = true;
        }
        //添加文件大小条件
        if(FILE_SIZE>0){
            if(where) sql.append(" AND ");
            where   = true;
            Long    minSize = null;
            if(args!=null&&args.get("minSize")!=null){
                minSize = Long.parseLong(args.get("minSize"));
            }
            if(minSize!=null){
                sql.append(" (FILE_SIZE>=");
                sql.append(minSize);
                sql.append(" AND FILE_SIZE<=");
                sql.append(FILE_SIZE);
                sql.append(") ");
            }else {
                sql.append(" FILE_SIZE<=");
                sql.append(FILE_SIZE);
            }
        }
        //添加上传时间条件
        if(UPTIME!=null){
            Long    minTime = null;
            String  format  = "yyyy-MM-dd HH:mm:ss";
            if(args!=null&&args.get("minTime")!=null){
                minTime = Long.parseLong(args.get("minTime"));
            }
            if(minTime!=null){
                sql.append("(TO_CHAR(UPTIME,'");
                sql.append(format);
                sql.append("')>=SUBSTR('");
                sql.append(new SimpleDateFormat(format).format(minTime));
                sql.append("',0,");
                sql.append(format.length());
                sql.append(") AND ");
                sql.append("TO_CHAR(UPTIME,'");
                sql.append(format);
                sql.append("')<=SUBSTR('");
                sql.append(new SimpleDateFormat(format).format(UPTIME.getTime()));
                sql.append("',0,");
                sql.append(format.length());
                sql.append(")) ");
            }else {
                sql.append("TO_CHAR(UPTIME,'");
                sql.append(format);
                sql.append("')<=SUBSTR('");
                sql.append(new SimpleDateFormat(format).format(UPTIME.getTime()));
                sql.append("',0,");
                sql.append(format.length());
                sql.append(") ");
            }
        }
        if(ISDELETE!=1){
            if(where) sql.append(" AND ");
            sql.append("ISDELETE=");
            sql.append(ISDELETE);
        }else {
            if(!where){
                sql.append("1=1");
            }
        }
        lMap    = mapper.select(sql.toString());
        if(lMap==null)
            return null;
        if(lMap.size()>0){
            if(lMap.size()==1){
                map = lMap.get(0);
                setValues(this);
            }else {
                tUploads    = new ArrayList<>();
                TUpload upload;
                for(Map<String,Object> m:lMap){
                    upload  = new TUpload(mapper);
                    map = m;
                    setValues(upload);
                    tUploads.add(upload);
                }
            }
        }
        return lMap.size();
    }

    @Override
    public Integer update() {
        if(UID==null||UID.equals(""))
            return null;
        StringBuilder   sql = new StringBuilder();
        boolean         where   = false;
        sql.append("UPDATE T_UPLOAD ");
        if(FILE_NAME!=null&&!FILE_NAME.equals("")){
            if(where) sql.append(",");
            where=true;
            sql.append("FILE_NAME='");
            sql.append(FILE_SIZE);
            sql.append("'");
        }
        if(where)sql.append(",");
        sql.append("UPTIME='");
        sql.append(UPTIME);
        sql.append("'");
        sql.append(" WHERE UID='");
        sql.append(UID);
        sql.append("'");
        return mapper.update(sql.toString());
    }

    @Override
    public Integer insert() {
        UID = UUID.randomUUID().toString().replaceAll("-","");
        UPTIME  = new Timestamp(System.currentTimeMillis());
        return mapper.insert("INSERT INTO T_UPLOAD(UID,FILE_NAME,HASH,PATH,TYPE,FILE_SIZE,UPTIME,ISDELETE) " +
                "VALUES('"+UID+"','"+FILE_NAME+"','"+HASH+
                "','"+PATH+"','"+TYPE+"',"+FILE_SIZE+",'"+UPTIME+"',"+ISDELETE+")");
    }

    @Override
    public Integer delete() {
        if(UID==null||UID.equals(""))
            return null;
        UPTIME  = new Timestamp(System.currentTimeMillis());
        return mapper.update("UPDATE T_UPLOAD ISDELETE=-1,UPTIME='"+UPTIME+"' WHERE UID='"+UID+"'");
    }


}
