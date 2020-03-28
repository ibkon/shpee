package top.yukino.shpee.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class Index extends Super {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String indexPage(HttpServletRequest request, Map<String, Object> map) {

		List<Map<String, Object>> lMaps = mapper.select(
				"SELECT path,hash,type FROM T_UPLOAD WHERE isdelete=0 AND (type='png' OR type='jpg' OR type='jpeg' OR type='gif') AND size<0x200000");
		List<String> paths = new ArrayList<String>();
		Map<String, Object> mbuffer = new HashMap<String, Object>();
		Set<Integer> 	number	= new TreeSet<Integer>();
		Random random = new Random(System.currentTimeMillis());
		String path, hash, type;
		if(lMaps.size()<=3) {
			for(Map<String, Object> m:lMaps) {
				path = m.get("PATH").toString();
				hash = m.get("HASH").toString();
				type = m.get("TYPE").toString();
				paths.add(path.replaceAll("upload", "static") + "/" + DigestUtils.md5Hex(hash).toUpperCase() + "."
						+ type);
			}
		}
		else {
			//循环10次，生成三个不重复随机数，但不保证必须够3个
			int thisNumber	= 0;
			for(int i=0;i<10;i++) {
				thisNumber=random.nextInt((int)System.currentTimeMillis())%lMaps.size();
				if(number.add(thisNumber))
				{
					mbuffer = lMaps.get(thisNumber);
					path = mbuffer.get("PATH").toString();
					hash = mbuffer.get("HASH").toString();
					type = mbuffer.get("TYPE").toString();
					paths.add(path.replaceAll("upload", "static") + "/" + DigestUtils.md5Hex(hash).toUpperCase() + "."
							+ type);
				}
				if(number.size()==1)
					break;
			}
		}
		map.put("imageList", paths);
		if (request.getHeader("User-Agent").contains("iPhone") || request.getHeader("User-Agent").contains("Android")) {
			map.put("device", "mobile");
		} else {
			map.put("device", "comput");
		}
		return "index";
	}
}
