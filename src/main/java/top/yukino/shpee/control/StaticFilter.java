package top.yukino.shpee.control;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import top.yukino.shpee.base.Buffers;
import top.yukino.shpee.base.Super;
import top.yukino.shpee.bean.TUpload;
import top.yukino.shpee.conf.FileHtmlType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@Controller
public class StaticFilter extends Super {
    @GetMapping("/static/files")
    public void filter(HttpServletRequest request, HttpServletResponse response)throws IOException {
        String  uid = request.getParameter("uid");
        Long    timeout=Long.parseLong(request.getParameter("timeout"));
        OutputStream    out = response.getOutputStream();
        System.out.println(request.getRequestURL());
        if(timeout<System.currentTimeMillis()){
            out.write("403,Time out.".getBytes());
            out.close();
            return;
        }
        String  url ="/static/files?uid="+uid
                +"&timeout="+timeout;
        String  code= DigestUtils.md5Hex(url).substring(20);
        if(!code.equals(request.getParameter("code"))){
            out.write("403,Bad request.".getBytes());
            out.close();
            return;
        }

        TUpload upload=new TUpload();
        upload.setUID(uid);
        upload.setReturnListMap(mapper.select(upload.select()));
        if(upload.getBean(0)!=null){
            byte[]  data;
            upload  = (TUpload) upload.getBean(0);
            data    = Buffers.getData(upload.getHASH());
            if(data==null){
                FileInputStream in  = new FileInputStream(
                        new File(upload.getPATH()+"/"+upload.getHASH())
                );
                data=in.readAllBytes();
                in.close();
                Buffers.setData(upload.getHASH(),data);
            }
            response.setContentType(FileHtmlType.getmFileType(upload.getTYPE()));
            out.write(data);
            out.close();
            return;
        }
        out.write("404".getBytes());
        out.close();
    }
}
