package com.xentn.shpee.control.admin;

import com.xentn.shpee.base.Super;
import com.xentn.shpee.bean.TProduct;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class AddProduct extends Super {
    @GetMapping("admin/configurator")
    public String addProduct(){
        return "admin/addProduct";
    }

    @ResponseBody
    @RequestMapping("/admin/product_list")
    public Map<String,Object> productList(HttpServletRequest request){

        System.out.println("进入/admin/product_list");
        List<TProduct>  products    = mapper.selectTProduct(null);
        if(products!=null&&products.size()>0)
            return buildJson(0,"",products,products.size());
        return buildJson(1,"未查到数据",null);
    }
}
