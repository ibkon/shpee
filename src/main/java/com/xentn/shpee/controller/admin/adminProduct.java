package com.xentn.shpee.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/***
 * @Description: product admin controller
 *
 * @author: ibkon
 * @date: 2021/6/20
 * @version: 1.0
 */
@Controller
public class adminProduct {
    @RequestMapping("/admin/product/list")
    public String list(){
        return "admin/product/list";
    }
    @RequestMapping("/admin/product/add/{type}")
    public String add(@PathVariable("type") String addType){
        if (addType.equals("product_line")){
            return "admin/product/addProductLine";
        }
        return "admin/product/add";
    }
}
