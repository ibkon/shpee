package top.yukino.shpee.conf;

import java.util.List;
import java.util.Map;

public interface Mapper {
    public List<Map<String,Object>> select(String sql);
    public Integer  insert(String sql);
    public Integer  update(String sql);
    public Integer  delete(String sql);
}
