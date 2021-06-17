package com.xentn.shpee.controller.tool;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/***
 * @Description: TODO
 *
 * @author: ibkon
 * @date: 2021/5/23
 * @version: 1.0
 */
@Controller
@RequestMapping("/tool/upload")



public class Upload {
    @GetMapping("/")
    public String page(HttpServletRequest request, Map<String,Object> map){
        String  fileType    = request.getParameter("file-type");
        if(fileType == null|| fileType.equals(""))
            fileType    = "files";
        map.put("file-type", fileType);
        return "tool/upload";
    }
}
