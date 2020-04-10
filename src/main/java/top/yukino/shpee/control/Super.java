package top.yukino.shpee.control;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import top.yukino.shpee.conf.Mapper;

public class Super {
	@Autowired(required=true)
	protected Mapper	mapper;

	//存储配置信息
	private static Map<String,Object>	mConfig;
	private static String	configName	= "config.data";
	static {
		ObjectInputStream	objectInputStream;
		File	file	= new File(configName);
		if(file!=null&&!file.exists()&&file.isFile()){
			try {
				objectInputStream	= new ObjectInputStream(
						new FileInputStream(file)
				);
				mConfig	= (Map<String, Object>) objectInputStream.readObject();
				objectInputStream.close();
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		else{
			mConfig	= new HashMap<>();
		}
	}
	/**
	 * 构建layui表格数据格式
	 * @param lMap
	 * @return
	 */
	protected Map<String, Object> buildJson(List<Map<String,Object>> data,String msg) {
		Map<String, Object> 	jsonMap		= new HashMap<String, Object>();
		Map<String, String> 	totalRow	= new HashMap<String, String>();
		totalRow.put("score","666");
		totalRow.put("experience","999");
		jsonMap.put("code", 0);
		jsonMap.put("msg",msg);
		jsonMap.put("count",data.size());
		jsonMap.put("data",data);
		jsonMap.put("totalRow",totalRow);
		return jsonMap;
	}

	public String	uuid(){
		return UUID.randomUUID().toString().replaceAll("-","");
	}

	public Map<String,Object>	retCode(int code,String msg,Object data){
		Map<String,Object>	hMap	= new HashMap<>();
		hMap.put("code",code);
		hMap.put("msg",msg);
		if(data!=null)
			hMap.put("data",data);
		return hMap;
	}

	public static Object getmConfig(String key) {
		return mConfig.get(key);
	}

	public static synchronized void setmConfig(String key,Object val){
		mConfig.put(key,val);
		try {
			ObjectOutputStream	objectOutputStream	= new ObjectOutputStream(
					new FileOutputStream(new File(configName))
			);
			objectOutputStream.writeObject(mConfig);
			objectOutputStream.close();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}

}
