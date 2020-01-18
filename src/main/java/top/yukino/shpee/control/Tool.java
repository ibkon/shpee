package top.yukino.shpee.control;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/***
 * 通用工具类
 * @author ibkon
 * 1、文件上传页面、文件处理方法
 *
 */
@Controller
public class Tool {
	/**
	 * 文件上传页面
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/upload")
	public String upload(HttpServletRequest request, Map<String, Object> map) {
		String	upLoadType	= request.getParameter("uploadtype");
		if(upLoadType==null)
			upLoadType ="";
		switch (upLoadType) {
		case "image":;
		case "files":
			break;
		default:
			upLoadType	= "files";
			break;
		}
		map.put("uploadType",upLoadType);
		return "tool/upload";
	}
	@ResponseBody
	@PostMapping("/upload/save")
	public Map<String, Object> uploadSave(HttpServletRequest request,@RequestParam("file")MultipartFile upfile) throws IOException{
		String				uploadPath			= "upload/";
		SimpleDateFormat	simpleDateFormat	= new SimpleDateFormat("yyyy_MM_dd");
		Map<String, Object> 	rmap	= new HashMap<>();
		uploadPath	+= simpleDateFormat.format(new Date());
		File				fcache				= new File(uploadPath);
		if(!fcache.exists()) {
			fcache.mkdirs();
		}
		if(upfile==null) {
			System.out.println("上传参数为空");
			rmap.put("code",1);
			rmap.put("msg","上传参数为空");
			return rmap;
		}
		fcache	= new File(uploadPath+"/"+upfile.getOriginalFilename());
		InputStream		in		= upfile.getInputStream();
		OutputStream	out		= new FileOutputStream(fcache);
		byte[]			buffer	= new byte[0x4000];
		int				length	= 0;
		while((length=in.read(buffer, 0, 0x4000))!=-1) {
			out.write(buffer,0,length);
		}
		in.close();
		out.close();
		rmap.put("code",0);
		return rmap;
	}
}
