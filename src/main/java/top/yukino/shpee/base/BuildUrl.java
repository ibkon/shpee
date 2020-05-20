package top.yukino.shpee.base;


import org.apache.commons.codec.digest.DigestUtils;
import top.yukino.shpee.bean.TUpload;

import java.util.Random;

/***
 class name:BuildUrl
 edit user:Sagiri
 create time:2020/5/3
 TODO:  Build url and handle related verification
 */
public class BuildUrl {
    final private static String random  = Integer.toString(new Random().nextInt(0X7FFFFFFF));
    final private static String staticResourceAddress   =
            DefaultConfigure.getSConfigure("staticResourceAddress")==null
                    ?"":DefaultConfigure.getSConfigure("staticResourceAddress");
    public static Long  timeoutHMS(int h,int m,int s)
    {
        return  (long)h*0X36EE80+m*0XEA60+s*0X3EE8;
    }

    public static String    buildUploadUrl(TUpload upload,Long timeout){
        if(upload==null)
            return "404";
        //If the file is not saved locally, return the address in the upload.PATH .
        if(upload.getPATH().indexOf("http")!=-1)
            return upload.getPATH();
        //Build url, add timeout, verification code
        String  url = staticResourceAddress+"/static/image?uid="+upload.getUID()
                +"&timeout="+(System.currentTimeMillis()+timeout);
        String  code   = DigestUtils.md5Hex(url+random);
        return url+"&code="+code;
    }

    public static boolean   checkUrl(String url){
        return true;
    }
}
