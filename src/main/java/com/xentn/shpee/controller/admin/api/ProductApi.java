package com.xentn.shpee.controller.admin.api;

import com.xentn.shpee.bean.product.TProductGroup;
import com.xentn.shpee.bean.tool.ShpeeInfoCode;
import com.xentn.shpee.bean.tool.Supper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/***
 * @Description: TODO
 *
 * @author: ibkon
 * @date: 2021/6/22
 * @version: 1.0
 */
@Controller
public class ProductApi extends Supper {
    /**
     * @Author xentn
     * @Description //add product line
     * @Date 2021/6/22
     * @Param []
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @ResponseBody
    @RequestMapping("/admin/api/product/add/{type}")
    public Map<String,Object> add(HttpServletRequest request,@PathVariable("type") String type){
        if(type.equals("group")){
            String  groupName = request.getParameter("product-line-name");
            if(groupName==null||groupName.equals("")){
                return buildInfo(ShpeeInfoCode.SHPEE_ARGS_ERROR,"404");
            }
            TProductGroup   group   = new TProductGroup();
            List<TProductGroup> groups;
            group.setProductName(groupName);
            groups  = getProductMapper().selectGroup(group);
            if(groups!=null&&groups.size()!=0){
                return buildInfo(ShpeeInfoCode.SHPEE_ARGS_ERROR,groups.get(0).getProductName());
            }
            group.setProductGroupId(getUUID());
            getProductMapper().insertGroup(group);
            return buildInfo(ShpeeInfoCode.SHPEE_SUCCESS,"success");
        }
        return buildInfo(ShpeeInfoCode.SHPEE_ARGS_ERROR,"403");
    }
}
