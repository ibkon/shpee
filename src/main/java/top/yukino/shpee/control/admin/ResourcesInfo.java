package top.yukino.shpee.control.admin;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import top.yukino.shpee.bean.TUpload;
import top.yukino.shpee.control.Super;
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
		String	sql	= "SELECT uid,name,hash,type,size,uptime FROM T_UPLOAD WHERE isdelete=0";
		return buildJson(mapper.select(sql), "");
	}


	@PostMapping("/admin/resources/remove")
	@ResponseBody
	public Map<String,Object> deleteFile(HttpServletRequest request){
		String	uid	= request.getParameter("uid");
		TUpload upload	= new TUpload();
		upload.setUid(uid);
		if(mapper.delete(upload.delete())>0){
			return retCode(0,"文件删除成功",null);
		}
		return retCode(1,"删除失败",null);
	}
}
