package com.xentn.shpee.controller.admin;

import com.xentn.shpee.bean.product.TProductGroup;
import com.xentn.shpee.bean.tool.Supper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/***
 * @Description: product admin controller
 *
 * @author: ibkon
 * @date: 2021/6/20
 * @version: 1.0
 */
@Controller
public class adminProduct extends Supper{
    @RequestMapping("/admin/product/list")
    public String list(){
        return "admin/product/list";
    }
    @RequestMapping("/admin/product/add/{type}")
    public String add(@PathVariable("type") String addType, Map<String,Object> map){
        if (addType.equals("product_line")){
            return "admin/product/addProductLine";
        }
        map.put("product_lines",getProductMapper().selectGroup(new TProductGroup()));
        return "admin/product/add";
    }
}
