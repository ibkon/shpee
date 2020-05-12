package top.yukino.shpee.conf;

import org.apache.ibatis.annotations.Param;
import top.yukino.shpee.bean.TUpload;
import top.yukino.shpee.bean.TUser;

import java.util.List;
import java.util.Map;


public interface Mapper {
    public List<Map<String,Object>> select(String sql);
    public Integer  insert(String sql);
    public Integer  update(String sql);
    public Integer  delete(String sql);

    public List<TUpload>    selectTUpload(Map<String,Object> args);
    public List<TUser>      selectTUser(Map<String,Object> args);
    public List<String>     selectRole(@Param("username") String username);

    public Integer  insertTUser(TUser user);
    public Integer  insertTUserRole(@Param("username") String username,@Param("role") String role);
    public Integer  insertTUpload(TUpload upload);

}
