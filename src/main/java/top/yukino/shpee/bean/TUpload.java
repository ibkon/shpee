package top.yukino.shpee.bean;

import top.yukino.shpee.control.Super;
import top.yukino.shpee.control.Upload;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class TUpload extends BeanSqlWhere{
    private String uid;
    private String name;
    private String hash;
    private String path;
    private String type;
    private long   size;
    private Timestamp   uptime;
    private int    isDelete;

    public TUpload(){
        this.isDelete   = 0;
    }
    public TUpload(String uid, String name, String hash, String path, String type, long size, Timestamp uptime, int isDelete) {
        this.uid = uid;
        this.name = name;
        this.hash = hash;
        this.path = path;
        this.type = type;
        this.size = size;
        this.uptime = uptime;
        this.isDelete = isDelete;
    }

    @Override
    public String select() {
        StringBuilder   builder = new StringBuilder();
        this.isWhere    = false;
        builder.append("SELECT uid,name,hash,path,type,size,uptime,isdelete FROM T_UPLOAD ");
        builder.append(selectWhere("uid",uid));
        builder.append(selectWhere("name",name));
        builder.append(selectWhere("path",path));
        builder.append(selectWhere("type",type));
        builder.append(selectWhere("hash",hash));
        builder.append(selectWhere("isdelete",isDelete));
        if(size>0)
            builder.append(selectWhere("size",size,"<="));
        if(uptime!=null){
            if(this.isWhere){
                builder.append(" AND ");
            }
            else {
                builder.append(" WHERE ");
                this.isWhere=true;
            }
            builder.append("TO_CHAR(uptime,'yyyy-MM-dd HH:mm:ss')>SUBSTR('");
            builder.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(uptime.getTime()));
            builder.append("',0,19)");
        }
        return builder.toString();
    }

    @Override
    public String update() {
        //仅根据唯一ID进行修改文件名，若其条件都不存在则无法生成sql
        if(this.uid==null)
            return null;
        StringBuilder   builder = new StringBuilder();
        this.isWhere = false;
        builder.append("UPDATE T_UPLOAD SET ");
        builder.append(insertWhere("name='"+name+"'",name));
        builder.append(insertWhere("hash='"+hash+"'",hash));
        builder.append(insertWhere("path='"+path+"'",path));
        builder.append(insertWhere("type='"+type+"'",type));
        builder.append(insertWhere("size="+size+"",size==0?null:Long.toString(size)));
        builder.append(insertWhere("isdelete="+isDelete,size==0?null:Integer.toString(isDelete)));
        if(this.uptime==null){
            this.uptime=new Timestamp(System.currentTimeMillis());
        }
        if(this.isWhere){
            builder.append(",uptime='");
        }else {
            builder.append("uptime='");
        }
        builder.append(uptime);
        builder.append("' WHERE uid='");
        builder.append(this.uid);
        builder.append("'");
        return builder.toString();
    }

    @Override
    public String insert() {
        if(this.uid==null||this.uid.equals("")){
            this.uid= UUID.randomUUID().toString().replaceAll("-","");
        }
        if(this.uptime==null){
            this.uptime = new Timestamp(System.currentTimeMillis());
        }
        StringBuilder   builder = new StringBuilder();
        builder.append("INSERT INTO T_UPLOAD(");
        this.isWhere  = false;
        builder.append(insertWhere("uid",uid));
        builder.append(insertWhere("name",name));
        builder.append(insertWhere("hash",hash));
        builder.append(insertWhere("path",path));
        builder.append(insertWhere("type",type));
        builder.append(insertWhere("size","not zero"));
        builder.append(insertWhere("isdelete","not zero"));
        if(this.isWhere)
            builder.append(",uptime");
        else {
            builder.append("uptime");
            this.isWhere=true;
        }
        builder.append(") VALUES(");
        this.isWhere=false;
        builder.append(insertWhere(uid));
        builder.append(insertWhere(name));
        builder.append(insertWhere(hash));
        builder.append(insertWhere(path));
        builder.append(insertWhere(type));
        builder.append(insertWhere(size));
        builder.append(insertWhere(isDelete));
        if(this.isWhere){
            builder.append(",'");
            builder.append(uptime);
        }
        else{
            builder.append("'");
            builder.append(uptime);
            this.isWhere=true;
        }
        builder.append("') ");
        return builder.toString();
    }

    @Override
    public String delete() {
        this.isDelete   = -1;
        return update();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public Timestamp getUptime() {
        return uptime;
    }

    public void setUptime(Timestamp uptime) {
        this.uptime = uptime;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }
}
