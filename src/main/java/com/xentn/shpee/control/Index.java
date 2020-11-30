package com.xentn.shpee.control;

import com.xentn.shpee.base.Super;
import com.xentn.shpee.bean.TUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
		List<String>	paths		= new ArrayList<>();
		List<Map<String,Object>>	mapList	=
				mapper.select("SELECT PATH,HASH FROM T_UPLOAD "+
						"WHERE ISDELETE=0 AND (TYPE='jpg' OR TYPE='png' OR TYPE='jpeg')");
		System.out.println(mapList);
		if(mapList==null||mapList.size()==0){
			paths.add("images/index1.jpg");
			paths.add("images/index2.jpeg");
		}
		if(mapList.size()<=3){
			for(Map<String,Object> upFile:mapList){
				paths.add(upFile.get("PATH")+"/"+upFile.get("HASH")+".jpg");
			}
		}
		else{
			HashSet<Integer>	set	=	new HashSet<>();
			Random	random	= new Random(System.currentTimeMillis());
			int		count	= 0;
			for(int i=0;i<mapList.size();i++){
				count	= random.nextInt(mapList.size());
				if(set.add(count)){
					paths.add(mapList.get(count).get("PATH")+"/"+mapList.get(count).get("HASH")+".jpg");
				}
				if(set.size()==3)
					break;
			}
		}


		Object	principal	= SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal instanceof UserDetails) {
			UserDetails details = (UserDetails) principal;
			List<Map<String,Object>>	RIDs	= mapper.select("SELECT RID FROM T_USER_ROLE WHERE NAME='"+details.getUsername()+"'");
			if(RIDs!=null&&RIDs.size()>0){
				if(RIDs.get(0).get("RID").toString().equals("ADMIN")){
					map.put("rid","admin");
				}
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
