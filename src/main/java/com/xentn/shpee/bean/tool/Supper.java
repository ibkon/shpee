package com.xentn.shpee.bean.tool;

import com.xentn.shpee.mapper.ProductMapper;
import com.xentn.shpee.mapper.ShpeeConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

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
    private ProductMapper       productMapper;

    protected String    getUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }


    @Autowired
    private void setConfigMapper(ShpeeConfigMapper mapper){
        this.configMapper   = mapper;
    }

    @Autowired
    private void setProductMapper(ProductMapper mapper){
        this.productMapper  = mapper;
    }

    protected ShpeeConfigMapper getConfigMapper(){
        return this.configMapper;
    }


    public ProductMapper getProductMapper() {
        return productMapper;
    }

    protected Map<String,Object> buildInfo(int code, String msg){
        Map<String,Object>  map = new HashMap<>();
        map.put("code",code);
        map.put("msg",msg);
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
