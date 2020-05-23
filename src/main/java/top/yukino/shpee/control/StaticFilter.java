package top.yukino.shpee.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import top.yukino.shpee.base.Buffers;
import top.yukino.shpee.base.BuildUrl;
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
    @GetMapping("/static/image")
    public void filter(HttpServletRequest request, HttpServletResponse response)throws IOException {
        String  uid = request.getParameter("uid");
        String  code=request.getParameter("code");
        Long    timeout=Long.parseLong(request.getParameter("timeout"));
        OutputStream    out = response.getOutputStream();
        if(timeout<System.currentTimeMillis()){
            System.err.println("超时:"+uid);
            out.write("403".getBytes());
        }
        else if(BuildUrl.checkUrl("static/image?uid="+uid+"&timeout="+timeout+"&code="+code)){
            try {
                TUpload upload  = mapper.selectTUpload(buildMap("UID",uid)).get(0);
                byte[]  data    = null;
                data    = Buffers.getData(upload.getUID());
                if(data!=null) {
                    out.write(data);
                    response.setContentType(FileHtmlType.getmFileType("jpg"));
                }
                if(data==null){
                    File    file   = new File(upload.getPATH()+"/"+upload.getHASH());
                    if(file.exists()&&!file.isDirectory()){
                        FileInputStream inputStream = new FileInputStream(file);
                        data    = inputStream.readAllBytes();
                        inputStream.close();
                        Buffers.setData(upload.getUID(),data);
                        response.setContentType(FileHtmlType.getmFileType("jpg"));
                        out.write(data);
                    }else {
                        System.err.println("找不到文件:"+file.getAbsolutePath());
                        out.write("404".getBytes());
                    }
                }
            }catch (ArrayIndexOutOfBoundsException e){
                System.err.println("数组越界:"+uid);
                out.write("404".getBytes());
            }

        }else {
            System.err.println("验证失败:"+uid);
            out.write("404".getBytes());
        }
        out.close();
    }
}
