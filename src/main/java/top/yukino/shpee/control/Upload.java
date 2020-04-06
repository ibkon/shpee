package top.yukino.shpee.control;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import top.yukino.shpee.bean.TUpload;

/***
 * 文件上传控制
 * @author Sagiri
 *
 */
@Controller
public class Upload extends Super{
	private String	upLoadPath	="upload/";
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
	public Map<String, Object> uploadSave(HttpServletRequest request,@RequestParam("file") MultipartFile upfile) throws IOException {
		if(upfile==null) {
			return retCode(1,"不能上传空文件",null);
		}
		//数据库表必备信息
		TUpload		tUpload	= new TUpload();
		tUpload.setName(upfile.getOriginalFilename());
		tUpload.setHash(DigestUtils.sha256Hex(upfile.getInputStream()).toUpperCase());
		tUpload.setType(tUpload.getName().substring(tUpload.getName().lastIndexOf('.')+1));
		tUpload.setSize(upfile.getSize());
		tUpload.setPath(this.upLoadPath + new SimpleDateFormat("yyyy_MM_dd").format(new Date()));

		if(mapper.select(tUpload.select())==null||mapper.select(tUpload.select()).size()>0){
			return retCode(0,"已存在相同文件",null);
		}
		//根据年月日创建目录
		File fCache	= new File(tUpload.getPath());
		if(!fCache.exists()) {
			fCache.mkdirs();
		}
		//文件保存前将信息存入数据库
		mapper.insert(tUpload.insert());
		fCache	= new File(tUpload.getPath()+"/"+DigestUtils.md5Hex(tUpload.getHash()).toUpperCase()+"."+tUpload.getType());
		InputStream in		= upfile.getInputStream();
		OutputStream out		= new FileOutputStream(fCache);
		byte[]			buffer	= new byte[0x4000];
		int				length	= 0;
		while((length=in.read(buffer, 0, 0x4000))!=-1) {
			out.write(buffer,0,length);
		}
		out.close();
		in.close();
		return retCode(0,"上传文件成功",null);
	}
}
