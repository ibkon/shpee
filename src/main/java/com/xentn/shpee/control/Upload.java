package com.xentn.shpee.control;

import com.xentn.shpee.bean.TUpload;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.xentn.shpee.base.Super;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/***
 * 文件上传控制
 * @author Sagiri
 *
 */
@Controller
public class Upload extends Super {
	private static String	upLoadPath="upload";
	/**
	 * 文件上传页面
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/upload")
	public String upload(HttpServletRequest request, Map<String, Object> map) {
		//获取上传文件类型，以渲染不同的上传文件页面
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
	/**
	 * @Author ibkon
	 * @Description Save the uploaded file to the server.
	 * @Date 21:43 2020/5/31
	 * @Param [request, upfile]
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 **/
	@ResponseBody
	@PostMapping("/upload/save")
	public Map<String, Object> uploadSave(HttpServletRequest request,@RequestParam("file") MultipartFile upfile) throws IOException {
		if(upfile==null) {
			return buildJson(1,"不能上传空文件",null);
		}
		String	fileName	= upfile.getOriginalFilename();
		String	hash		= DigestUtils.sha256Hex(upfile.getInputStream()).toUpperCase();
		String	type		= fileName.substring(fileName.lastIndexOf('.')+1);
		long	size		= upfile.getSize();
		String	path		= this.upLoadPath+new SimpleDateFormat("yyyy_MM_dd").format(new Date());

		TUpload upload	= null;
		try {
			upload	= mapper.selectTUpload(buildMap("hash",hash)).get(0);
			//快传
			if(upload.getTYPE().equals(type)&&upload.getSIZE()==size){
				upload.setUID(uuid());
				upload.setUPTIME(new Timestamp(System.currentTimeMillis()));
				if(mapper.insertTUpload(upload)==1){
					return buildJson(0,"上传成功",null);
				}
			}
		}
		catch (IndexOutOfBoundsException e){}


		upload	= new TUpload();
		upload.setUID(uuid());
		upload.setUPTIME(new Timestamp(System.currentTimeMillis()));
		upload.setISDELETE(0);
		upload.setFILENAME(fileName);
		upload.setTYPE(type);
		upload.setHASH(hash);
		upload.setPATH(path);
		upload.setSIZE(size);

		//根据年月日创建目录
		File fCache	= new File(path);
		if(!fCache.exists()) {
			fCache.mkdirs();
		}
		if(mapper.insertTUpload(upload)==1){
			fCache	= new File(path+"/"+hash);
			InputStream in		= upfile.getInputStream();
			OutputStream out		= new FileOutputStream(fCache);
			byte[]			buffer	= new byte[0x4000];
			int				length	= 0;
			while((length=in.read(buffer, 0, 0x4000))!=-1) {
				out.write(buffer,0,length);
			}
			out.close();
			in.close();
			return buildJson(0,"File upload success.",null);
		}
		return buildJson(1,"上传文件失败",null);
	}
}
