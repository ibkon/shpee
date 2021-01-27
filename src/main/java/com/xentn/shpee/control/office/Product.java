package com.xentn.shpee.control.office;

import com.xentn.shpee.base.BuildDocx;
import com.xentn.shpee.base.Super;
import org.apache.poi.xwpf.usermodel.Document;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class Product extends Super {
    @GetMapping("work/docx")
    @ResponseBody
    public String getDocx(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String title = request.getParameter("title");
        String  imageId = request.getParameter("image");

        //TUpload upload  = mapper.selectTUpload(buildMap("UID",imageId)).get(0);;

        BuildDocx   docx    = new BuildDocx(response.getOutputStream());

        docx.addText(title);
        docx.nextPage();
        docx.addImage("D:\\code\\javas\\shpee\\upload\\2020_12_21\\681049817D949449182BD46CD88BE2765338003D5E354D956E4444BEEAFA07D0","", Document.PICTURE_TYPE_JPEG);

        response.setHeader("Content-Disposition", "attachment;filename=test.docx");
        response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        docx.flush();
        return "ok";
    }


    @GetMapping("/configurator")
    public String configurator(){
        return "configurator";
    }
}
