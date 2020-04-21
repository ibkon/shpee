package top.yukino.shpee.control;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import top.yukino.shpee.base.DefaultConfigure;
import top.yukino.shpee.base.Super;
import top.yukino.shpee.bean.TUpload;

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
		List<Map<String, Object>> lMaps=null;
		TUpload	upload	= new TUpload();
		upload.setTYPE("png,jpg,jpeg");
		upload.setFILE_SIZE(0x200000);

		lMaps	= mapper.select(upload.select());
		upload.setReturnListMap(lMaps);

		List<String> paths = new ArrayList<String>();
		Set<Integer> 	number	= new TreeSet<Integer>();
		Random random = new Random(System.currentTimeMillis());
		if(lMaps.size()<=3) {
			for(int i=0;i<lMaps.size();i++){
				paths.add(((TUpload)upload.getBean(i)).getUrl());
			}
		}
		else {
			//循环10次，生成三个不重复随机数，但不保证必须够3个
			int thisNumber	= 0;
			for(int i=0;i<10;i++) {
				thisNumber=random.nextInt((int)System.currentTimeMillis())%lMaps.size();
				if(number.add(thisNumber))
				{
					paths.add(((TUpload)upload.getBean(thisNumber)).getUrl());
				}
				//控制首页轮播图片数
				if(DefaultConfigure.getIConfigure("indexImageCount").equals(number.size()))
					break;
			}
		}
		map.put("imageList", paths);
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
	public Map<String,Object> init(HttpServletRequest request,Map<String,Object> mVal){
		String	appName		= request.getParameter("app-name");
		String	username	= request.getParameter("username");
		String	password	= request.getParameter("password");

		//设置以初始化站点
		DefaultConfigure.setInit(true);
		return buildJson(0,"init",null);
	}
}
