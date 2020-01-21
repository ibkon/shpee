package top.yukino.shpee.control;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class Index {
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String indexPage(Map<String, Object> map) {
		File root = new File("upload");
		File[] paths = null;
		if (root.exists()) {
			List<File> flist = new ArrayList<>();
			List<String> clist = new ArrayList<>();
			paths = root.listFiles();
			for (File f : paths) {
				for (File s : f.listFiles()) {
					if(s.getPath().indexOf(".jpg")>0||s.getPath().indexOf(".png")>0||s.getPath().indexOf(".gif")>0)
						flist.add(s);
				}
			}
			Random random = new Random();
			int length = 0;
			if(flist.size()>6) {
				for (int i = 0; i < 7; i++) {
					length = random.nextInt(flist.size());
					clist.add(flist.get(length).getPath().replaceAll("upload", "static"));
					flist.remove(length);
				}
			}
			else {
				for(int i=0;i<flist.size();i++) {
					clist.add(flist.get(i).getPath().replaceAll("upload", "static"));
				}
			}
			
			map.put("imageList",clist);
		}
		return "index";
	}
}
