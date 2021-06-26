package com.xentn.shpee.controller.cloud;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/***
 * @Description: TODO
 *
 * @author: ibkon
 * @date: 2021/6/19
 * @version: 1.0
 */
@Controller
public class CloudDisk {
    @RequestMapping("/cloud")
    public String cloud(){
        return "cloud/cloud";
    }
}
