package top.yukino.shpee.base;

import java.util.HashMap;
import java.util.Map;

/***
 * 缓存
 */
public class Buffers extends Super{
    /***
     * 缓存节点
     */
    static class NoteData{
        byte[]  data;       //数据
        long    time;       //最后使用时间
    }
    //缓存最大值
    private static  long    bufferSize  = 0x1400000L;
    //缓存计数器
    private static  long    count       = 0;
    //缓存映射
    private static Map<String,NoteData> dataMap = new HashMap<>();

    /**
     * 获取数据
     * @param key   数据名
     * @return
     */
    public static byte[]   getData(String key){
        NoteData    data    = dataMap.get(key);
        if(data==null)
            return null;
        return data.data;
    }

    /**
     * 将数据加入缓存
     * @param key   数据名
     * @param data  数据
     * @return      添加数据长度
     */
    public static synchronized long  setData(String key,byte[] data){
        if(data==null||data.length==0||data.length>bufferSize)
            return -1;
        if(dataMap.get(key)!=null&&dataMap.get(key).equals(data)) {
            dataMap.get(key).time=System.currentTimeMillis();
            return data.length;
        }
        NoteData    timeLow=null;
        String      aKey=null;
        //如果当前数据大于缓存数据，则执行清除操作
        while((count+data.length)>bufferSize){
            for(String k:dataMap.keySet()){
                if(timeLow==null) {
                    timeLow = dataMap.get(k);
                    aKey=k;
                }
                if(dataMap.get(k).time<timeLow.time) {
                    timeLow = dataMap.get(k);
                    aKey=k;
                }
            }
            if(aKey!=null) {
                count-=timeLow.data.length;
                timeLow=null;
                dataMap.remove(aKey);
                aKey=null;
            }
        }
        NoteData    nData   = new NoteData();
        nData.time=System.currentTimeMillis();
        nData.data=data;
        dataMap.put(key,nData);
        return 0;
    }
}
