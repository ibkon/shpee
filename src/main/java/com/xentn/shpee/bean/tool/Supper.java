package com.xentn.shpee.bean.tool;

import com.xentn.shpee.mapper.Mapper;
import com.xentn.shpee.mapper.ProductMapper;
import com.xentn.shpee.mapper.ShpeeConfigMapper;
import com.xentn.shpee.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;
import java.util.List;
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
    private Mapper              mapper;
    private ShpeeConfigMapper   configMapper;
    private ProductMapper       productMapper;
    private UserMapper          userMapper;

    private static Map<String,Object> shpeeCache;

    protected String    getUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    @Autowired
    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }

    @Autowired
    private void setConfigMapper(ShpeeConfigMapper mapper){
        this.configMapper   = mapper;
    }

    @Autowired
    private void setProductMapper(ProductMapper mapper){
        this.productMapper  = mapper;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    protected ShpeeConfigMapper getConfigMapper(){
        return this.configMapper;
    }

    public static void setShpeeCache(String key,Object value) {
        if(shpeeCache==null){
            shpeeCache  = new HashMap<>();
        }
        shpeeCache.put(key,value);
    }

    public static Map<String, Object> getShpeeCache() {
        if(shpeeCache==null){
            shpeeCache  = new HashMap<>();
        }
        return shpeeCache;
    }

    public Mapper getMapper() {
        return mapper;
    }

    public ProductMapper getProductMapper() {
        return productMapper;
    }

    public UserMapper getUserMapper() {
        return userMapper;
    }

    protected Map<String,Object> buildInfo(int code, String msg){
        return buildInfo(code,msg,null,0);
    }

    protected  Map<String,Object> buildInfo(int code, String msg, List<?> data,int count){
        Map<String,Object>  map = new HashMap<>();
        map.put("code",code);
        if(msg==null){
            map.put("msg","not msg.");
        }else{
            map.put("msg",msg);
        }
        if(data!=null){
            map.put("data",data);
            map.put("count",count);
        }
        return map;
    }

    /**
     * @Author xentn
     * @Description //get user details
     * @Date 2021/6/20
     * @Param [mVal]
     * @return org.springframework.security.core.userdetails.UserDetails
     */
    public UserDetails getUserDetails(){
        Object	principal	= SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            UserDetails	details	= (UserDetails)principal;
            return details;
        }
        return null;
    }
}
