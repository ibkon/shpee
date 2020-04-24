package top.yukino.shpee.bean;

import lombok.Data;
import top.yukino.shpee.conf.Mapper;

/***
 * 用户包含的角色
 */
@Data
public class TUserRole extends AbsBeanSql{
    private String  RID;
    private String  NAME;
    public TUserRole(Mapper mapper){
        super(mapper);
    }
    @Override
    public Integer insert() {
        if(RID==null||RID.equals("")||NAME==null||NAME.equals(""))
            return null;
        return mapper.insert("INSERT INTO T_USER_ROLE(RID,NAME) VALUES('"+RID+"','"+NAME+"')");
    }

    @Override
    public Integer delete() {
        if(RID==null||RID.equals("")||NAME==null||NAME.equals(""))
            return null;
        return mapper.delete("DELETE T_USER_ROLE WHERE RID='"+RID+"' AND NAME='"+NAME+"'");
    }
}
