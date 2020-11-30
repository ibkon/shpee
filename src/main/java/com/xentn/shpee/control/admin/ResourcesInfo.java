package com.xentn.shpee.control.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xentn.shpee.base.Super;
import com.xentn.shpee.bean.TUpload;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
/***
 * 系统资源管理
 * @author Sagiri
 *
 */
@Controller
public class ResourcesInfo extends Super{
	/**
	 * 资源信息拦截器
	 * @param request
	 * @param setVal
	 * @return
	 */
	@RequestMapping("/admin/resources")
	public String getResourcesPage(HttpServletRequest request,Map<String, Object> setVal){
		String sClass	= request.getParameter("class");
		switch (sClass) {
		//跳转到上传文件管理页
		case "upload":
			return "admin/resources_info/upload_list";
		case "recycle":
			return "admin/resources_info/recycle_list";
		default:
			break;
		}
		return "redirect:/";
	}
	/**
	 * 资源处理拦截
	 * @param request
	 * @return
	 */
	@RequestMapping("/admin/resources/list")
	@ResponseBody
	public Map<String, Object> resourcesList(HttpServletRequest request) {
		String	listType	= request.getParameter("list-type");
		switch (listType) {
		case "upload":
			return	searchUpload(request);
		case  "recycle":
			return	recycle(request);
		default:
			break;
		}
		return null;
	}
	/**
	 * 上传文件查询处理
	 * @param request
	 * @return
	 */
	private Map<String, Object> searchUpload(HttpServletRequest request){
		//获取查询文件页和一页大小
		Integer	page	= (Integer.parseInt(request.getParameter("page"))-1)*10;
		Integer	limit	= Integer.parseInt(request.getParameter("limit"));

		List<Map<String,Object>>	maps	= null;
		maps	= mapper.select("SELECT * FROM T_UPLOAD WHERE ISDELETE=0 LIMIT "+page+","+limit);
		if(maps!=null){
			return buildJson(0,"上传文件查询成功",maps,mapper.count("select  count(*) from T_UPLOAD where ISDELETE=0;"));
		}
		return buildJson(1,"上传文件查询失败",null);
	}

	private Map<String,Object> recycle(HttpServletRequest request){
		//获取查询文件页和一页大小
		Integer	page	= (Integer.parseInt(request.getParameter("page"))-1)*10;
		Integer	limit	= Integer.parseInt(request.getParameter("limit"));

		List<Map<String,Object>>	maps	= null;
		maps	= mapper.select("SELECT * FROM T_UPLOAD WHERE ISDELETE=1 LIMIT "+page+","+limit);
		if(maps!=null){
			return buildJson(0,"回收站文件查询成功",maps);
		}
		return buildJson(1,"回收站文件查询失败",null);
	}

	@PostMapping("/admin/resources/remove")
	@ResponseBody
	public Map<String,Object> deleteFile(HttpServletRequest request){
		String	uid	= request.getParameter("uid");
		if(uid==null||uid.equals(""))
			return buildJson(1,"文件不存在",null);
		if(mapper.update("UPDATE  T_UPLOAD SET ISDELETE=1 WHERE UID='"+uid+"'")==1){
			return buildJson(0,"文件删除成功",null);
		}
		return buildJson(1,"删除失败",null);
	}

	@GetMapping("/admin/resources/show")
	public String showResources(HttpServletRequest request,Map<String,Object> val){
		String	uid	= request.getParameter("uid");
		if(uid==null||uid.equals("")){
			val.put("type","msg");
			val.put("msg","文件UID为空，无法查询！！！");
		}
		else {
			TUpload	upload	= null;

			try {
				upload	= mapper.selectTUpload(buildMap("UID",uid)).get(0);
				switch (upload.getTYPE()){
					case	"jpg":;
					case 	"jpeg":;
					case 	"png":;
					case 	"gif":
						val.put("type","image");
						break;
					default:
						val.put("type","other");
				}
				val.put("src", upload.getPATH()+"/"+upload.getHASH()+".jpg");
			}catch (ArrayIndexOutOfBoundsException e){
				val.put("type","msg");
				val.put("msg","查询失败");
			}
		}
		return "tool/showResources";
	}
}
