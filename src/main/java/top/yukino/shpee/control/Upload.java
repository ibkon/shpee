package top.yukino.shpee.control;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;

/***
 * 文件上传控制
 * @author Sagiri
 *
 */
@Controller
public class Upload {
	/**
	 * 获取文件页面
	 * @param request
	 * @param setVal
	 * @return	页面URL
	 */
	public String getUploadPageUrl(HttpServletRequest request,Map<String, Object> setVal) {
		return "tool/upload";
	}
}
