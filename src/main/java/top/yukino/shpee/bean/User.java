package top.yukino.shpee.bean;

import java.util.List;

/***
 * 用户类
 */
public class User {
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
}
