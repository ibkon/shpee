package top.yukino.shpee.control.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import top.yukino.shpee.base.DefaultConfigure;
import top.yukino.shpee.base.Super;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@Controller
public class Admin  extends Super {
	@RequestMapping("/admin")
	public String getAdminPage(HttpServletRequest request,Map<String, Object> setVal) {
		if (request.getHeader("User-Agent").contains("iPhone") || request.getHeader("User-Agent").contains("Android")) {
			return "redirect:/";
		}
		checkLogin(setVal);
		setVal.put("applicationName", DefaultConfigure.getSConfigure("applicationName"));
		return "admin/admin";
	}

}
