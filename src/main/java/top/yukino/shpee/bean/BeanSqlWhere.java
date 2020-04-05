package top.yukino.shpee.bean;

/***
 * 添加条件化sql获取语句
 */
public abstract class BeanSqlWhere implements BeanSql{
    /**
     * sql缓存
     */
    private StringBuilder   builder;
    /**
     * 判断是否有前置where
     */
    protected boolean         isWhere;

    /**
     * 查询条件语句生成
     * @param valName   键值名
     * @param val       字符值
     * @return
     */
    protected String selectWhere(String valName,String val){
        if(valName==null||valName.equals(""))
            return "";
        if(val!=null&&!val.equals("")){
            this.builder    = new StringBuilder();
            if(this.isWhere){
                builder.append(" AND ");
            }else {
                builder.append(" WHERE ");
                this.isWhere=true;
            }
            builder.append(valName);
            builder.append("='");
            builder.append(val);
            builder.append("' ");
            return builder.toString();
        }
        return "";
    }

    /**
     * 查询条件语句生成
     * @param valName   键值名
     * @param val       数值
     * @return
     */
    protected String selectWhere(String valName,Long val,String sign){
        if(valName==null||valName.equals(""))
            return "";
        if(val!=null){
            this.builder    = new StringBuilder();
            if(this.isWhere){
                builder.append(" AND ");
            }else {
                builder.append(" WHERE ");
                this.isWhere=true;
            }
            builder.append(valName);
            builder.append(sign);
            builder.append(val);
            return builder.toString();
        }
        return "";
    }

    /**
     * 查询条件语句生成的简化方法
     * @param valName
     * @param val
     * @return
     */
    protected String selectWhere(String valName,Long val){
        return selectWhere(valName,val,"=");
    }
    protected String selectWhere(String valName,Integer val,String sign){
        return selectWhere(valName,Long.valueOf(val),sign);
    }
    protected String selectWhere(String valName,Integer val){
        return selectWhere(valName,val,"=");
    }

    /**
     * 生成插入语句需要插入的值
     * @param valName   值名
     * @param val       字符值
     * @return
     */
    protected String insertWhere(String valName,String val){
        if(val==null||val.equals(""))
            return "";
        if(this.isWhere){
            return ","+valName;
        }else {
            this.isWhere=true;
            return valName;
        }
    }

    /**
     * 插入语句插入的值
     * @param val       字符值
     * @return
     */
    protected String insertWhere(String val){
        if(val==null||val.equals(""))
            return "";
        if(this.isWhere){
            return ",'"+val+"'";
        }else {
            this.isWhere=true;
            return "'"+val+"'";
        }
    }
    protected String insertWhere(Long val){
        if(val==null)
            return "";
        if(this.isWhere)
            return ","+val;
        else {
            this.isWhere=true;
            return val.toString();
        }
    }
    protected String insertWhere(Integer val){
        if(val==null)
            return "";
        if(this.isWhere)
            return ","+val;
        else {
            this.isWhere=true;
            return val.toString();
        }
    }
}
