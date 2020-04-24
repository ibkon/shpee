package top.yukino.shpee.control;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import top.yukino.shpee.base.DefaultConfigure;
import top.yukino.shpee.base.Super;
import top.yukino.shpee.bean.TUpload;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
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
	private String	upLoadPath	= DefaultConfigure.getSConfigure("uploadpath");
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
	@ResponseBody
	@PostMapping("/upload/save")
	public Map<String, Object> uploadSave(HttpServletRequest request,@RequestParam("file") MultipartFile upfile) throws IOException {
		if(upfile==null) {
			return buildJson(1,"不能上传空文件",null);
		}
		Integer	retVal	= null;
		//数据库表必备信息
		TUpload		tUpload	= new TUpload(mapper);
		tUpload.setFILE_NAME(upfile.getOriginalFilename());
		tUpload.setHASH(DigestUtils.sha256Hex(upfile.getInputStream()).toUpperCase());
		tUpload.setTYPE(tUpload.getFILE_NAME().substring(tUpload.getFILE_NAME().lastIndexOf('.')+1));
		tUpload.setFILE_SIZE(upfile.getSize());
		tUpload.setPATH(this.upLoadPath + new SimpleDateFormat("yyyy_MM_dd").format(new Date()));

		retVal	= tUpload.select();
		if(retVal==null||retVal>0){
			return buildJson(0,"已存在相同文件",null);
		}
		//根据年月日创建目录
		File fCache	= new File(tUpload.getPATH());
		if(!fCache.exists()) {
			fCache.mkdirs();
		}
		//文件保存前将信息存入数据库
		tUpload.insert();
		fCache	= new File(tUpload.getPATH()+"/"+tUpload.getHASH());
		InputStream in		= upfile.getInputStream();
		OutputStream out		= new FileOutputStream(fCache);
		byte[]			buffer	= new byte[0x4000];
		int				length	= 0;
		while((length=in.read(buffer, 0, 0x4000))!=-1) {
			out.write(buffer,0,length);
		}
		out.close();
		in.close();
		return buildJson(0,"上传文件成功",null);
	}
}
