package top.yukino.shpee.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
				"SELECT path,hash,type FROM T_UPLOAD WHERE isdelete=0 AND (type='png' OR type='jpg' OR type='jpeg' OR type='gif') AND size<0x100000");
		List<String> paths = new ArrayList<String>();
		Map<String, Object> mbuffer = new HashMap<String, Object>();
		Random random = new Random();
		String path, hash, type;
		for (int i = 0; i < (3 > lMaps.size() ? lMaps.size() : 3); i++) {
			mbuffer = lMaps.get(random.nextInt(lMaps.size()));
			if(mbuffer==null)
				break;
			path = mbuffer.get("PATH").toString();
			hash = mbuffer.get("HASH").toString();
			type = mbuffer.get("TYPE").toString();
			paths.add(path.replaceAll("upload", "static")+"/"+DigestUtils.md5Hex(hash).toUpperCase()+"."+type);
			lMaps.remove(mbuffer);
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
