package top.yukino.shpee.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import top.yukino.shpee.conf.Mapper;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/***
 *
 */
public class Super {
	@Autowired(required=true)
	protected Mapper mapper;

	/**
	 * 构建默认Layui json对象
	 * @param data
	 * @param msg
	 * @return
	 */
	protected Map<String, Object> buildJson(int code,String msg,List<Map<String,Object>> data) {
		Map<String, Object> 	jsonMap		= new HashMap<String, Object>();
		Map<String, String> 	totalRow	= new HashMap<String, String>();
		jsonMap.put("code", code);
		jsonMap.put("msg",msg);
		if(data!=null) {
			totalRow.put("score", "666");
			totalRow.put("experience", "999");
			jsonMap.put("count", data.size());
			jsonMap.put("data", data);
			jsonMap.put("totalRow", totalRow);
		}
		return jsonMap;
	}

	/**
	 * 生成uuid
	 * @return	uuid
	 */
	public String	uuid(){
		return UUID.randomUUID().toString().replaceAll("-","");
	}

	/**
	 * 检查是否登录用户，并设置用户信息
	 * @param mVal
	 * @return
	 */
	public UserDetails	checkLogin(Map<String,Object> mVal){
		Object	principal	= SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal instanceof UserDetails){
			UserDetails	details	= (UserDetails)principal;
			if(mVal!=null) {
				mVal.put("login", "true");
				mVal.put("username", details.getUsername());
			}
			return details;
		}
		if(mVal!=null)
			mVal.put("login","false");
		return null;
	}

	/**
	 * 检查浏览器类型
	 * @param request
	 * @param mVal
	 */
	public void checkDriver(HttpServletRequest request, Map<String, Object> mVal){
		if (request.getHeader("User-Agent").contains("iPhone") || request.getHeader("User-Agent").contains("Android")) {
			mVal.put("device", "mobile");
		} else {
			mVal.put("device", "comput");
		}
	}

	public String passwordEncode(String password){
		return new BCryptPasswordEncoder().encode(password);
	}
}
