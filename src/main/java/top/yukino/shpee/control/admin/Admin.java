package top.yukino.shpee.control.admin;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class Admin {
	@RequestMapping("/admin")
	public String getAdminPage(HttpServletRequest request,Map<String, Object> setVal) {
		if (request.getHeader("User-Agent").contains("iPhone") || request.getHeader("User-Agent").contains("Android")) {
			return "redirect:/";
		}
		return "admin/admin";
	}
}