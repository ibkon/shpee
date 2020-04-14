package top.yukino.shpee.control.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import top.yukino.shpee.bean.TUpload;
import top.yukino.shpee.base.Super;
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
		String	sPage	= request.getParameter("page");
		String	sLimit	= request.getParameter("limit");
		Integer	page	= Integer.parseInt(sPage==null?"0":sPage);
		Integer	limit	= Integer.parseInt(sLimit==null?"0":sLimit);
		//查询结果和返回结果缓存
		List<Map<String,Object>>	rMaps;
		List<Map<String,Object>>	lMaps	= new ArrayList<>();
		//构建查询选项
		TUpload	upload	= new TUpload();
		rMaps	= mapper.select(upload.select());
		if(rMaps!=null){
			//如果未设置查询页面和一页条数，则返回全部查询结果
			if(page==0||limit==0){
				return buildJson(0,"查询全部文件成功",rMaps);
			}
			for(int i=0;i<limit;i++){
				//判断是否查询到最后一个，是则退出
				if((((page-1)*10)+i)>=rMaps.size())
					break;
				lMaps.add(rMaps.get((page-1)*10+i));
			}
			Map<String,Object>	rMap	= buildJson(0,"文件查询成功",lMaps);
			//将count设置为总条目数
			rMap.put("count",rMaps.size());
			return rMap;
		}
		return buildJson(1,"上传文件查询失败",null);
	}


	@PostMapping("/admin/resources/remove")
	@ResponseBody
	public Map<String,Object> deleteFile(HttpServletRequest request){
		String	uid	= request.getParameter("uid");
		TUpload upload	= new TUpload();
		upload.setUID(uid);
		if(mapper.delete(upload.delete())>0){
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
			TUpload	upload	= new TUpload();
			upload.setUID(uid);
			List<Map<String,Object>>	rMaps	= mapper.select(upload.select());
			if(rMaps==null||rMaps.size()==0){
				val.put("type","msg");
				val.put("msg","找不到该文件");
			}
			else {
				Map<String,Object>	map	= rMaps.get(0);
				String	type=map.get("TYPE").toString();
				String	path=map.get("PATH").toString();
				if(type!=null&&path!=null) {
					path=path.replaceAll("upload","/static")+"/"+ DigestUtils.md5Hex(map.get("HASH").toString()).toUpperCase() + "."
							+ type;
					type = type.toUpperCase();
				}
				upload.setReturnListMap(rMaps);
				switch (type){
					case	"JPG":;
					case 	"JPEG":;
					case 	"PNG":;
					case 	"GIF":
						val.put("type","image");
						val.put("src",((TUpload)upload.getBean(0)).getPATH());
						break;
					default:
						val.put("type","other");
						val.put("src",path);
				}
			}
		}
		return "tool/showResources";
	}
}
