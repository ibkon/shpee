package top.yukino.shpee.base;


import org.apache.commons.codec.digest.DigestUtils;

import java.util.Base64;
import java.util.Random;

/***
 class name:BuildUrl
 edit user:Sagiri
 create time:2020/5/3
 TODO:  Build url and handle related verification
 */
public class BuildUrl {
    final private static Base64.Decoder   decoder = Base64.getDecoder();
    final private static Base64.Encoder   encoder = Base64.getEncoder();
    final private static String random  = Integer.toString(new Random().nextInt(0X7FFFFFFF));

    /**
     * @Author Sagiri
     * @Description url add verification information
     * @Date 0:52 2020/5/3
     * @Param [url]
     * @return java.lang.String
     **/
    public static String    enUrl(String url,Long timeout){
        StringBuilder   builder = new StringBuilder();
        builder.append("timeout=");
        builder.append(System.currentTimeMillis()+timeout);
        builder.append("&captcha=");
        builder.append(DigestUtils.md5Hex(url+random));
        return url+"&verification="+encoder.encodeToString(builder.toString().getBytes());
    }

    public static String    deUrl(String url){
        return null;
    }
}
