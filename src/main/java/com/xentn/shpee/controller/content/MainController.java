package com.xentn.shpee.controller.content;

import com.xentn.shpee.mapper.ShpeeConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/***
 * @Description: index controller
 *
 * @author: ibkon
 * @date: 2021/3/14
 * @version: 1.0
 */
@Controller
public class MainController {

    private boolean isInitial;
    private ShpeeConfigMapper   mapper;

    @Autowired
    MainController(ShpeeConfigMapper mapper){
        this.mapper = mapper;
        String  shpeeName   = mapper.getConfig("shpee_name");
        if(shpeeName==null||shpeeName.equals("")){
            this.isInitial  = false;
        }else {
            this.isInitial  = true;
        }
    }

    @GetMapping("/")
    public String index(){
        if(!isInitial){
            return "admin/install";
        }
        return "view/index";
    }

    @GetMapping("/install")
    public String install(){
        return "admin/install";
    }
}
