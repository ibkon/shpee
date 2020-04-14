package top.yukino.shpee.bean;

import java.util.List;
import java.util.Map;

/***
 * bean生成sql接口
 */
public interface BeanSql {
    public String select();
    public String update();
    public String insert();
    public String delete();
    public void   setReturnListMap(List<Map<String,Object>> lMaps);
    public BeanSql  getBean(int id);
}
