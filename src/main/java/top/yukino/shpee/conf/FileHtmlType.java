package top.yukino.shpee.conf;

import java.util.HashMap;
import java.util.Map;

public class FileHtmlType {
    private static Map<String,String>   mFileType;
    static {
        mFileType   = new HashMap<>();
        mFileType.put("*","application/octet-stream");
        mFileType.put("jpg","image/jpeg");
        mFileType.put("jpeg","image/jpeg");
        mFileType.put("png","image/png");
        mFileType.put("mp3","audio/mp3");
        mFileType.put("mp4","video/mpeg4");
        mFileType.put("pdf","application/pdf");
    }
    public static void setmFileType(String key,String value) {
        FileHtmlType.mFileType.put(key,value);
    }
    public static String getmFileType(String key) {
        if(mFileType.get(key.toLowerCase())!=null)
            return mFileType.get(key.toLowerCase());
        return mFileType.get("*");
    }
}
