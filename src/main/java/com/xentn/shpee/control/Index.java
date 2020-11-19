package com.xentn.shpee.control;

import com.xentn.shpee.bean.TUpload;
import com.xentn.shpee.bean.TUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xentn.shpee.base.Super;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.*;

/***
 * 主页跳转
 */
@Controller
public class Index extends Super {
	@GetMapping(value = "/")
	public String indexController(HttpServletRequest request,Map<String,Object> mVal){
		//根据站点名，判断站点是否初始化
		if(mapper.getConfigure("ApplicationName")==null){
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
		List<TUpload>	tUploads	= mapper.selectTUpload(null);
		List<String>	paths		= new ArrayList<>();
		if(tUploads==null||tUploads.size()==0){
			paths.add("images/index1.jpg");
			paths.add("images/index2.jpeg");
		}
		if(tUploads.size()<=3){
			for(TUpload t:tUploads){
				paths.add("static/"+t.getPATH().replaceAll("upload/","")+"/"+t.getHASH());
			}
		}
		else {
			HashSet<Integer>	set	=	new HashSet<>();
			Random	random	= new Random(System.currentTimeMillis());
			int		count	= 0;
			for(int i=0;i<tUploads.size();i++){
				count	= random.nextInt(tUploads.size());
				if(set.add(count)){
					paths.add("static/"+tUploads.get(count).getPATH().replaceAll("upload/","")+"/"+tUploads.get(count).getHASH());
				}
				if(set.size()==3)
					break;
			}
		}
		map.put("imageList",paths);
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
		if(mapper.getConfigure("ApplicationName")!=null)
		{
			return buildJson(1,"/",null);
		}
		String	appName		= request.getParameter("app-name");
		String	username	= request.getParameter("username");
		String	password	= request.getParameter("password");

		TUser user	= new TUser();
		user.setNAME(username);
		user.setPASSWORD(passwordEncode(password));
		user.setUPTIME(new Timestamp(System.currentTimeMillis()));
		if(mapper.insertTUser(user)==1&&mapper.insertTUserRole(username,"ADMIN")==1)
		{
			mapper.setConfigure("ApplicationName",appName);
		}
		return buildJson(0,"init",null);
	}

	@GetMapping("/login")
	public String login(HttpServletRequest request, Map<String, Object> mVal){
		checkDriver(request,mVal);
		return "login";
	}
	@GetMapping("/logout")
	public String logout(){
		return "logout";
	}

}
