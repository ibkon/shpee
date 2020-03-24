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
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
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
public class Tool extends Super{
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
	public Map<String, Object> uploadSave(HttpServletRequest request,@RequestParam("file")MultipartFile upfile) throws IOException{
		Map<String, Object> 	rmap	= new HashMap<>();
		if(upfile==null) {
			rmap.put("code",1);
			rmap.put("msg","上传参数为空");
			return rmap;
		}
		//数据库表必备信息
		String				uid		= UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
		String				fileName= upfile.getOriginalFilename();
		String				hash	= DigestUtils.sha256Hex(upfile.getInputStream()).toUpperCase();
		String				type	= fileName.substring(fileName.lastIndexOf('.')+1);
		Date				update	= new Date();
		long				size	= upfile.getSize();
		String				path	= this.upLoadPath + new SimpleDateFormat("yyyy_MM_dd").format(update);
		
		//根据年月日创建目录
		File				fcache	= new File(path);
		if(!fcache.exists()) {
			fcache.mkdirs();
		}
		//文件保存前将信息存入数据库
		mapper.insert("INSERT INTO T_UPLOAD(uid,name,hash,path,type,size,uptime,isdelete) values('"+
		uid+"','"+fileName+"','"+hash+"','"+path+"','"+type+"',"+size+",'"+new java.sql.Timestamp(update.getTime())+"',0"+")");
		fcache	= new File(path+"/"+DigestUtils.md5Hex(hash).toUpperCase()+"."+type);
		InputStream		in		= upfile.getInputStream();
		OutputStream	out		= new FileOutputStream(fcache);
		byte[]			buffer	= new byte[0x4000];
		int				length	= 0;
		while((length=in.read(buffer, 0, 0x4000))!=-1) {
			out.write(buffer,0,length);
		}
		out.close();
		in.close();
		rmap.put("code",0);
		return rmap;
	}
}
