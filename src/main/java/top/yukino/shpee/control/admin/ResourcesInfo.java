package top.yukino.shpee.control.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.yukino.shpee.base.BuildUrl;
import top.yukino.shpee.base.Super;
import top.yukino.shpee.bean.TUpload;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
		String	page	= request.getParameter("page");
		String	limit	= request.getParameter("limit");

		List<Map<String,Object>>	maps	= null;
		maps	= mapper.select("SELECT * FROM T_UPLOAD WHERE ISDELETE=0 LIMIT "+page+","+limit);
		if(maps!=null){
			return buildJson(0,"上传文件查询成功",maps);
		}
		return buildJson(1,"上传文件查询失败",null);
	}


	@PostMapping("/admin/resources/remove")
	@ResponseBody
	public Map<String,Object> deleteFile(HttpServletRequest request){
		String	uid	= request.getParameter("uid");
		if(uid==null||uid.equals(""))
			return buildJson(1,"文件不存在",null);
		if(mapper.update("UPDATE TABLE T_UPDATE ISDELETE=1 WHERE UID='"+uid+"'")==1){
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
			Integer	retVal	= null;
			TUpload	upload	= new TUpload(mapper);
			upload.setUID(uid);
			retVal	= upload.select();
			if(retVal==null||retVal==0){
				val.put("type","msg");
				val.put("msg","找不到该文件");
			}
			else {
				switch (upload.getTYPE()){
					case	"JPG":;
					case 	"JPEG":;
					case 	"PNG":;
					case 	"GIF":
						val.put("type","image");
						val.put("src",upload.getUrl());
						break;
					default:
						val.put("type","other");
						val.put("src",upload.getUrl());
				}
			}
		}
		return "tool/showResources";
	}
}
