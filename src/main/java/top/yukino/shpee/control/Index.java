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
				"SELECT path,hash,type FROM T_UPLOAD WHERE isdelete=0 AND (type='png' OR type='jpg' OR type='jpeg' OR type='gif') AND size<0x200000");
		List<String> paths = new ArrayList<String>();
		Map<String, Object> mbuffer = new HashMap<String, Object>();
		Random random = new Random();
		String path, hash, type;
		if (lMaps.size() > 3) {
			int count[] = new int[3];
			count[0] = random.nextInt(lMaps.size());
			count[1] = random.nextInt(lMaps.size());
			count[2] = random.nextInt(lMaps.size());
			while (true) {
				if (count[0] == count[1]) {
					count[1] = random.nextInt(lMaps.size());
					continue;
				}
				if (count[0] == count[2]) {
					count[2] = random.nextInt(lMaps.size());
					continue;
				}
				if (count[1] == count[2]) {
					count[2] = random.nextInt(lMaps.size());
					continue;
				}

				if ((count[0] != count[1]) && (count[0] != count[2]) && (count[1] != count[2]))
					break;
			}
			for (int i : count) {
				mbuffer = lMaps.get(i);
				path = mbuffer.get("PATH").toString();
				hash = mbuffer.get("HASH").toString();
				type = mbuffer.get("TYPE").toString();
				paths.add(path.replaceAll("upload", "static") + "/" + DigestUtils.md5Hex(hash).toUpperCase() + "."
						+ type);

			}
		} else {
			for (int i = 0; i < lMaps.size(); i++) {
				mbuffer = lMaps.get(i);
				path = mbuffer.get("PATH").toString();
				hash = mbuffer.get("HASH").toString();
				type = mbuffer.get("TYPE").toString();
				paths.add(path.replaceAll("upload", "static") + "/" + DigestUtils.md5Hex(hash).toUpperCase() + "."
						+ type);

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
