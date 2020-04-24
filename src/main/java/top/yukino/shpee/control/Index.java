package top.yukino.shpee.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.yukino.shpee.base.DefaultConfigure;
import top.yukino.shpee.base.Super;
import top.yukino.shpee.bean.TUpload;
import top.yukino.shpee.bean.TUser;
import top.yukino.shpee.bean.TUserRole;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

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


	public String indexPage(HttpServletRequest request, Map<String, Object> map) {
		checkLogin(map);
		Integer	retVal	= null;
		TUpload	upload	= new TUpload(mapper);
		upload.setTYPE("png,jpg,jpeg");
		upload.setFILE_SIZE(0x200000);
		retVal = upload.select();
		if(retVal!=null&&retVal>0){
			List<String>	paths	= new ArrayList<>();
			if(retVal==1){
				paths.add(upload.getUrl());
			}else if(retVal<=3){
				for(int i=0;i<retVal;i++){
					paths.add(upload.getTUploads().get(i).getUrl());
				}
			}else {
				Random	random	= new Random(retVal);
				Set<Integer>	set	= new HashSet<>();
				Integer		randomVal	= 0;
				for(int i=0;i<10;i++){
					randomVal	= random.nextInt(0x7FFFFFFF)%retVal;
					if(set.add(randomVal)){
						paths.add(upload.getTUploads().get(randomVal).getUrl());
						if(DefaultConfigure.getIConfigure("indexImageCount").equals(set.size()))
							break;
					}
				}
			}
			map.put("imageList", paths);
		}
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
		TUser	user	= new TUser(mapper);
		TUserRole	tus	= new TUserRole(mapper);
		user.setNAME(username);
		user.setPASSWORD(password);
		user.insert();
		tus.setNAME(username);
		tus.setRID("ADMIN");
		tus.insert();
		//设置以初始化站点
		DefaultConfigure.setConfigure("applicationName",appName);
		DefaultConfigure.setInit(true);
		return buildJson(0,"init",null);
	}
}
