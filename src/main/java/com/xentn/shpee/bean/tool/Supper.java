package com.xentn.shpee.bean.tool;

import com.xentn.shpee.mapper.ShpeeConfigMapper;
import com.xentn.shpee.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/***
 * @Description: controller toole
 *
 * @author: ibkon
 * @date: 2021/4/18
 * @version: 1.0
 */
public class Supper {
    private ShpeeConfigMapper   configMapper;
    private UserMapper          userMapper;

    protected String    getUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }


    @Autowired
    private void setConfigMapper(ShpeeConfigMapper mapper){
        this.configMapper   = mapper;
    }

    @Autowired
    private void setUserMapper(UserMapper mapper){
        this.userMapper = mapper;
    }

    protected ShpeeConfigMapper getConfigMapper(){
        return this.configMapper;
    }

    protected UserMapper    getUserMapper(){
        return this.userMapper;
    }

    protected Map<String,Object> buildInfo(int code,String msg){
        Map<String,Object>  map = new HashMap<>();
        map.put("code",code);
        map.put("msg",msg);
        return map;
    }
}
