package top.yukino.shpee.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.yukino.shpee.base.DefaultConfigure;
import top.yukino.shpee.base.Super;
import top.yukino.shpee.bean.TUser;
import top.yukino.shpee.bean.TUserRole;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/***
 * 主页跳转
 */
@Controller
public class Index extends Super {
	@GetMapping(value = "/")
	public String indexController(HttpServletRequest request,Map<String,Object> mVal){
		if(!DefaultConfigure.isInit()){
			return "init";
		}
		return indexPage(request,mVal);
	}


	/**
	 * @Author Sagiri
	 * @Description //TODO 
	 * @Date 0:28 2020/5/1
	 * @Param [request, map]
	 * @return java.lang.String
	 **/
	public String indexPage(HttpServletRequest request, Map<String, Object> map) {
		checkLogin(map);

		checkDriver(request,map);
		return "index";
	}

	/**
	 * 站点初始化设置
	 * @param request
	 * @param mVal
	 * @return
	 */
	@PostMapping("/init")
	@ResponseBody
	public Map<String,Object> init(HttpServletRequest request,Map<String,Object> mVal){
		String	appName		= request.getParameter("app-name");
		String	username	= request.getParameter("username");
		String	password	= request.getParameter("password");

		TUser	user	= new TUser();
		TUserRole	tus	= new TUserRole();
		user.setNAME(username);
		user.setPASSWORD(passwordEncode(password));
		tus.setNAME(username);
		tus.setRID("ADMIN");
		if(mapper.insertTUser(user)==1&&mapper.insertTUserRole(tus)==1)
		{
			DefaultConfigure.setConfigure("applicationName",appName);
			DefaultConfigure.setInit(true);
		}
		return buildJson(0,"init",null);
	}
}
