package top.yukino.shpee.control;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import top.yukino.shpee.base.DefaultConfigure;
import top.yukino.shpee.base.Super;
import top.yukino.shpee.bean.TUpload;

@Controller
public class Index extends Super {
	@RequestMapping(value = "/", method = RequestMethod.GET)
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
}
