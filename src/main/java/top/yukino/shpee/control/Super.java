package top.yukino.shpee.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import top.yukino.shpee.conf.Mapper;

public class Super {
	@Autowired(required=true)
	protected Mapper	mapper;
	/**
	 * 构建layui表格数据格式
	 * @param lMap
	 * @return
	 */
	protected Map<String, Object> buildJson(List<Map<String,Object>> lMap,String msg) {
		Map<String, Object> 	jsonMap		= new HashMap<String, Object>();
		Map<String, String> 	totalRow	= new HashMap<String, String>();
		totalRow.put("score","666");
		totalRow.put("experience","999");
		jsonMap.put("code", 0);
		jsonMap.put("msg",msg);
		jsonMap.put("count",lMap.size());
		jsonMap.put("data",lMap);
		jsonMap.put("totalRow",totalRow);
		return jsonMap;
	}
	public String	uuid(){
		return UUID.randomUUID().toString().replaceAll("-","");
	}
}
