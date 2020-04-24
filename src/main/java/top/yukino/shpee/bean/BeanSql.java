package top.yukino.shpee.bean;

/***
 * bean生成sql接口
 */
public interface BeanSql {
    public Integer select();
    public Integer update();
    public Integer insert();
    public Integer delete();
}
