package top.yukino.shpee.bean;

import org.apache.commons.codec.digest.DigestUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TUpload extends BeanSqlWhere{
    private String UID;
    private String FILE_NAME;
    private String HASH;
    private String PATH;
    private String TYPE;
    private long   FILE_SIZE;
    private Timestamp   UPTIME;
    private int    ISDELETE;

    private List<TUpload>   beans;

    public TUpload(){
        this.ISDELETE   = 0;
    }

    /**
     * 构建查询sql
     * @return  sql语句
     */
    @Override
    public String select() {
        StringBuilder   builder = new StringBuilder();
        this.isWhere    = false;
        builder.append("SELECT UID,FILE_NAME,HASH,PATH,TYPE,FILE_SIZE,UPTIME FROM T_UPLOAD");
        builder.append(selectWhere("UID",this.UID));
        builder.append(selectWhere("FILE_NAME",this.FILE_NAME));
        builder.append(selectWhere("HASH",this.HASH));
        builder.append(selectWhere("PATH",this.PATH));
        builder.append(selectWhere("ISDELETE",this.ISDELETE));
        //根据多文件类型查询，并生成相应语句
        if(this.TYPE!=null&&!this.TYPE.equals("")){
            if(this.TYPE.indexOf(",")==-1){
                builder.append(selectWhere("TYPE",this.TYPE));
            }else {
                String[]    types   = this.TYPE.split(",");
                if(this.isWhere)
                    builder.append(" AND ");
                boolean fast    = true;
                builder.append("(");
                for(String s:types){
                    if(!fast)
                        builder.append(" OR ");
                    builder.append("TYPE='");
                    builder.append(s.trim());
                    builder.append("'");
                    fast=false;
                }
                builder.append(") ");
            }
        }
        //文件大小范围查找待更细
        if(this.FILE_SIZE>0)
            builder.append(selectWhere("FILE_SIZE",this.FILE_SIZE,"<="));
        //时间范围查找待更新
        if(this.UPTIME!=null){
            if(this.isWhere){
                builder.append(" AND ");
            }
            else {
                builder.append(" WHERE ");
                this.isWhere=true;
            }
            builder.append("TO_CHAR(UPTIME,'yyyy-MM-dd HH:mm:ss')>SUBSTR('");
            builder.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.UPTIME.getTime()));
            builder.append("',0,19)");
        }
        return builder.toString();
    }

    @Override
    public String update() {
        //仅根据唯一ID进行修改文件名，若其条件都不存在则无法生成sql
        if(this.UID==null)
            return null;
        StringBuilder   builder = new StringBuilder();
        this.isWhere = false;
        builder.append("UPDATE T_UPLOAD SET ");
        builder.append(insertWhere("FILE_NAME='"+this.FILE_NAME+"'",this.FILE_NAME));
        builder.append(insertWhere("HASH='"+this.HASH+"'",this.HASH));
        builder.append(insertWhere("PATH='"+this.PATH+"'",this.PATH));
        builder.append(insertWhere("TYPE='"+this.TYPE+"'",this.TYPE));
        builder.append(insertWhere("FILE_SIZE="+this.FILE_SIZE+"",this.FILE_SIZE==0?null:Long.toString(this.FILE_SIZE)));
        builder.append(insertWhere("ISDELETE="+this.ISDELETE,this.FILE_SIZE!=0?null:Integer.toString(this.ISDELETE)));
        if(this.UPTIME==null){
            this.UPTIME=new Timestamp(System.currentTimeMillis());
        }
        if(this.isWhere){
            builder.append(",UPTIME='");
        }else {
            builder.append("UPTIME='");
        }
        builder.append(this.UPTIME);
        builder.append("' WHERE UID='");
        builder.append(this.UID);
        builder.append("'");
        return builder.toString();
    }

    @Override
    public String insert() {
        if(this.UID==null||this.UID.equals("")){
            this.UID= UUID.randomUUID().toString().replaceAll("-","");
        }
        if(this.UPTIME==null){
            this.UPTIME = new Timestamp(System.currentTimeMillis());
        }
        StringBuilder   builder = new StringBuilder();
        builder.append("INSERT INTO T_UPLOAD(");
        this.isWhere  = false;
        builder.append(insertWhere("UID",this.UID));
        builder.append(insertWhere("FILE_NAME",this.FILE_NAME));
        builder.append(insertWhere("HASH",this.HASH));
        builder.append(insertWhere("PATH",this.PATH));
        builder.append(insertWhere("TYPE",this.TYPE));
        builder.append(insertWhere("FILE_SIZE","not zero"));
        builder.append(insertWhere("ISDELETE","not zero"));
        if(this.isWhere)
            builder.append(",UPTIME");
        else {
            builder.append("UPTIME");
            this.isWhere=true;
        }
        builder.append(") VALUES(");
        this.isWhere=false;
        builder.append(insertWhere(this.UID));
        builder.append(insertWhere(this.FILE_NAME));
        builder.append(insertWhere(this.HASH));
        builder.append(insertWhere(this.PATH));
        builder.append(insertWhere(this.TYPE));
        builder.append(insertWhere(this.FILE_SIZE));
        builder.append(insertWhere(this.ISDELETE));
        if(this.isWhere){
            builder.append(",'");
            builder.append(this.UPTIME);
        }
        else{
            builder.append("'");
            builder.append(this.UPTIME);
            this.isWhere=true;
        }
        builder.append("') ");
        return builder.toString();
    }

    @Override
    public String delete() {
        this.ISDELETE   = -1;
        return update();
    }

    @Override
    public String toString() {
        return "TUpload{" +
                "UID='" + UID + '\'' +
                ", FILE_NAME='" + FILE_NAME + '\'' +
                ", HASH='" + HASH + '\'' +
                ", PATH='" + PATH + '\'' +
                ", TYPE='" + TYPE + '\'' +
                ", FILE_SIZE=" + FILE_SIZE +
                ", UPTIME=" + UPTIME +
                ", ISDELETE=" + ISDELETE +
                '}';
    }

    @Override
    public void setReturnListMap(List<Map<String, Object>> lMaps) {
        TUpload upload  = null;
        this.beans      = new ArrayList<>();
        for(Map<String,Object> m:lMaps){
            upload  = new TUpload();
            try {
                upload.setUID(m.get("UID").toString());
                upload.setFILE_NAME(m.get("FILE_NAME").toString());
                upload.setHASH(m.get("HASH").toString());
                upload.setPATH(m.get("PATH").toString());
                upload.setTYPE(m.get("TYPE").toString());
                upload.setFILE_SIZE(Long.parseLong(m.get("FILE_SIZE").toString()));
                upload.setUPTIME(Timestamp.valueOf(m.get("UPTIME").toString()));
                upload.setISDELETE(0);
                this.beans.add(upload);
            }catch (NullPointerException e){
                System.err.println(m);
                e.printStackTrace();
            }
        }
    }

    @Override
    public BeanSql getBean(int id) {
        if(this.beans==null||this.beans.size()<=id)
            return null;
        return this.beans.get(id);
    }

    public String getUrl() {
        String  url ="/static/files?uid="+this.UID
                +"&timeout="+(System.currentTimeMillis()+0x1b7740);
        String  hash=DigestUtils.md5Hex(url).substring(20);
        return url+"&code="+hash;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getFILE_NAME() {
        return FILE_NAME;
    }

    public void setFILE_NAME(String FILE_NAME) {
        this.FILE_NAME = FILE_NAME;
    }

    public String getHASH() {
        return HASH;
    }

    public void setHASH(String HASH) {
        this.HASH = HASH;
    }

    public String getPATH() {
        return PATH;
    }

    public void setPATH(String PATH) {
        this.PATH = PATH;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public long getFILE_SIZE() {
        return FILE_SIZE;
    }

    public void setFILE_SIZE(long FILE_SIZE) {
        this.FILE_SIZE = FILE_SIZE;
    }

    public Timestamp getUPTIME() {
        return UPTIME;
    }

    public void setUPTIME(long  UPTIME) {
        this.UPTIME = new Timestamp(UPTIME);
    }
    public void setUPTIME(Timestamp  UPTIME) {
        this.UPTIME = UPTIME;
    }
    public int getISDELETE() {
        return ISDELETE;
    }

    public void setISDELETE(int ISDELETE) {
        this.ISDELETE = ISDELETE;
    }
}
