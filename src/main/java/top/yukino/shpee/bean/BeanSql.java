package top.yukino.shpee.bean;

/***
 * bean生成sql接口
 */
public interface BeanSql {
    public String select();
    public String update();
    public String insert();
    public String delete();
}
