package top.yukino.shpee.bean;

import lombok.Getter;
import top.yukino.shpee.conf.Mapper;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public abstract class AbsBeanSql implements BeanSql{
    protected Mapper    mapper;
    protected @Getter List<Map<String,Object>>    lMap;
    protected Map<String,Object>          map;

    public AbsBeanSql(){}
    public AbsBeanSql(Mapper mapper){
        this.mapper = mapper;
    }
    protected String    o2s(String key){
        if(map.get(key)==null)
            return null;
        return map.get(key).toString();
    }
    protected Integer   o2i(String key){
        if(map.get(key)==null)
            return null;
        return Integer.parseInt(map.get(key).toString());
    }
    protected Long      o2l(String key){
        if(map.get(key)==null)
            return null;
        return Long.parseLong(map.get(key).toString());
    }
    protected Timestamp o2t(String key){
        if(map.get(key)==null)
            return null;
        return Timestamp.valueOf(map.get(key).toString());
    }

    @Override
    public Integer select() {
        return null;
    }

    @Override
    public Integer update() {
        return null;
    }

    @Override
    public Integer insert() {
        return null;
    }

    @Override
    public Integer delete() {
        return null;
    }
}
