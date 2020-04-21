package top.yukino.shpee.base;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/***
 * 本项目的默认配置
 */
public class DefaultConfigure {
    private static Map<String,Object>  mConfigure;
    private static String   mConfigureFile  = "shpee.map";
    private static boolean  init = false;
    static {
        File    configureFile   = new File(mConfigureFile);
        if(configureFile.exists()&&configureFile.isFile()){
            //加载配置参数
            try {
                ObjectInputStream   ois = new ObjectInputStream(
                        new FileInputStream(
                                new File(mConfigureFile)
                        )
                );
                mConfigure  = (Map<String, Object>) ois.readObject();
                ois.close();
                init=true;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        else{
            //设置默认参数
            mConfigure  = new HashMap<>();
            //默认首页轮播图片数
            setConfigure("indexImageCount",3);
            //默认上传文件目录
            setConfigure("uploadpath","upload/");
        }
    }

    /**
     * 设置配置参数
     * @param key   参数名
     * @param val   参数值
     */
    public static synchronized void setConfigure(String key,Object val){
        mConfigure.put(key,val);
        try {
            ObjectOutputStream  oos = new ObjectOutputStream(
                    new FileOutputStream(
                            new File(mConfigureFile)
                    )
            );
            oos.writeObject(mConfigure);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取配置参数
     * @param key   配置名
     * @return
     */
    public static Object    getOConfigure(String key){
        return mConfigure.get(key);
    }

    /**
     * 获取字符串配置参数
     * @param key   配置名
     * @return
     */
    public static String    getSConfigure(String key){
        return mConfigure.get(key).toString();
    }

    /**
     * 获取32位整型配置参数
     * @param key   配置名
     * @return
     */
    public static Integer   getIConfigure(String key){
        Object  o   = mConfigure.get(key);
        if(o==null)
            return null;
        return Integer.parseInt(o.toString());
    }

    /**
     * 获取长整型配置参数
     * @param key   配置名
     * @return
     */
    public static Long   getLConfigure(String key){
        Object  o   = mConfigure.get(key);
        if(o==null)
            return null;
        return Long.parseLong(o.toString());
    }

    /**
     * 获取双精度浮点型型配置参数
     * @param key   配置名
     * @return
     */
    public static Double   getDConfigure(String key){
        Object  o   = mConfigure.get(key);
        if(o==null)
            return null;
        return Double.parseDouble(o.toString());
    }

    public static boolean isInit() {
        return init;
    }

    public static void setInit(boolean init) {
        DefaultConfigure.init = init;
    }
}
