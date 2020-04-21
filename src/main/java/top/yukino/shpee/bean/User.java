package top.yukino.shpee.bean;

import java.util.List;
import java.util.Map;

/***
 * 用户类
 */
public class User extends BeanSqlWhere{
    private String  NAME;
    private String  PASSWORD;
    private List<Role> ROLE;

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public List<Role> getROLE() {
        return ROLE;
    }

    public void setROLE(List<Role> ROLE) {
        this.ROLE = ROLE;
    }

    @Override
    public String select() {
        return null;
    }

    @Override
    public String update() {
        return null;
    }

    @Override
    public String insert() {
        return null;
    }

    @Override
    public String delete() {
        return null;
    }

    @Override
    public void setReturnListMap(List<Map<String, Object>> lMaps) {

    }

    @Override
    public BeanSql getBean(int id) {
        return null;
    }
}
