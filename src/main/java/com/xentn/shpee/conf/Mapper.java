package com.xentn.shpee.conf;

import com.xentn.shpee.bean.TUpload;
import com.xentn.shpee.bean.TUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface Mapper {
    public List<Map<String,Object>> select(String sql);
    public Integer  insert(String sql);
    public Integer  update(String sql);
    public Integer  delete(String sql);
    public Integer  count(String sql);

    public List<TUpload>    selectTUpload(Map<String,Object> args);
    public List<TUser>      selectTUser(Map<String,Object> args);
    public List<String>     selectRole(String name);
    public String           getConfigure(String key);


    public Integer  insertTUser(TUser user);
    public Integer  insertTUserRole(@Param("NAME") String username,@Param("ROLE") String role);
    public Integer  insertTUpload(TUpload upload);
    public Integer  setConfigure(@Param("key") String key,@Param("values") String values);
    public Integer  upConfigure(@Param("key") String key,@Param("values") String values);
}
