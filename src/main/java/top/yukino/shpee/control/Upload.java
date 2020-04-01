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
		System.out.println("Upload type "+upLoadType);
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
		Map<String, Object> 	rMap	= new HashMap<>();
		if(upfile==null) {
			rMap.put("code",1);
			rMap.put("msg","不能上传空文件");
			return rMap;
		}
		//数据库表必备信息
		String				uid		= uuid();
		String				fileName= upfile.getOriginalFilename();
		String				hash	= DigestUtils.sha256Hex(upfile.getInputStream()).toUpperCase();
		String				type	= fileName.substring(fileName.lastIndexOf('.')+1);
		Date update	= new Date();
		long				size	= upfile.getSize();
		String				path	= this.upLoadPath + new SimpleDateFormat("yyyy_MM_dd").format(update);
		if(mapper.select("select hash from T_UPLOAD where hash='"+hash+"'")!=null){
			rMap.put("code",0);
			rMap.put("msg","相同文件已存在");
			System.out.println("相同文件已存在");
			return rMap;
		}
		//根据年月日创建目录
		File fCache	= new File(path);
		if(!fCache.exists()) {
			fCache.mkdirs();
		}
		//文件保存前将信息存入数据库
		mapper.insert("INSERT INTO T_UPLOAD(uid,name,hash,path,type,size,uptime,isdelete) values('"+
				uid+"','"+fileName+"','"+hash+"','"+path+"','"+type+"',"+size+",'"+new java.sql.Timestamp(update.getTime())+"',0"+")");
		fCache	= new File(path+"/"+ DigestUtils.md5Hex(hash).toUpperCase()+"."+type);
		InputStream in		= upfile.getInputStream();
		OutputStream out		= new FileOutputStream(fCache);
		byte[]			buffer	= new byte[0x4000];
		int				length	= 0;
		while((length=in.read(buffer, 0, 0x4000))!=-1) {
			out.write(buffer,0,length);
		}
		out.close();
		in.close();
		rMap.put("code",0);
		return rMap;
	}
}
