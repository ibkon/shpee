package com.xentn.shpee.controller.admin.api;

import com.xentn.shpee.bean.product.TProduct;
import com.xentn.shpee.bean.product.TProductGroup;
import com.xentn.shpee.bean.product.TProductParameter;
import com.xentn.shpee.bean.tool.Supper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.xentn.shpee.bean.tool.ShpeeInfoCode.SHPEE_ARGS_ERROR;
import static com.xentn.shpee.bean.tool.ShpeeInfoCode.SHPEE_SUCCESS;

/***
 * @Description: product API
 * Product operation related API.
 *
 * @author: ibkon
 * @date: 2021/6/22
 * @version: 1.0
 */
@Controller
public class ProductApi extends Supper {
    /**
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Author xentn
     * @Description //add product line
     * @Date 2021/6/22
     * @Param []
     */
    @ResponseBody
    @RequestMapping("/admin/api/product/add/{type}")
    public Map<String, Object> add(HttpServletRequest request, @PathVariable("type") String type) {
        if (type.equals("group")) {
            String groupName = request.getParameter("product-line-name");
            if (groupName == null || groupName.equals("")) {
                return buildInfo(SHPEE_ARGS_ERROR, "404");
            }
            TProductGroup group = new TProductGroup();
            List<TProductGroup> groups;
            group.setProductGroupName(groupName);
            groups = getProductMapper().selectGroup(group);
            if (groups != null && groups.size() != 0) {
                return buildInfo(SHPEE_ARGS_ERROR, groups.get(0).getProductGroupName());
            }
            group.setProductGroupId(getUUID());
            getProductMapper().insertGroup(group);
            return buildInfo(SHPEE_SUCCESS, "success");
        } else if (type.equals("product")) {
            TProduct product = new TProduct();
            TProductParameter parameter = new TProductParameter();

            String productGroupId = request.getParameter("product_line");
            String productName = request.getParameter("product_name");
            String productLabel = request.getParameter("product_label");
            String productParameter = request.getParameter("product_doc");

            product.setProductName(productName);
            product.setEnabled(true);
            if (getProductMapper().selectProduct(product).size() > 0) {
                return buildInfo(SHPEE_ARGS_ERROR, "产品已存在");
            }

            parameter.setProductParameterId(getUUID());
            product.setProductParameterId(parameter.getProductParameterId());
            product.setProductId(getUUID());
            product.setProductGroupId(productGroupId);
            product.setProductLabel(productLabel);


            if (productParameter.indexOf("<p>") == -1) {
                if (productParameter.indexOf("##") == 0) {
                    parameter.setKeyItem(true);
                    productParameter = productParameter.substring(2);
                }
                parameter.setProductParameterText(productParameter);
                parameter.setProductParameterNextId("null");
                System.out.println(parameter);
            } else {
                String[] ps = productParameter.split("<p>");
                for (int i = 0; i < ps.length; i++) {
                    if (ps[i].equals(""))
                        continue;
                    parameter.setKeyItem(false);

                    if (i > 0 && parameter.getProductParameterNextId() != null) {
                        parameter.setProductParameterId(parameter.getProductParameterNextId());
                    }
                    if (ps[i].indexOf("##") == 0) {
                        parameter.setKeyItem(true);
                        ps[i] = ps[i].substring(2);
                    }
                    if (i == ps.length - 1) {
                        parameter.setProductParameterNextId("null");
                    } else {
                        parameter.setProductParameterNextId(getUUID());
                    }
                    parameter.setProductParameterText(ps[i].replaceAll("</p>", ""));
                    if (parameter.getProductParameterText().length() > 256) {
                        return buildInfo(SHPEE_ARGS_ERROR, "文本输入异常，请检查输入文本");
                    }
                    getProductMapper().insertParameter(parameter);
                }

            }
            getProductMapper().insertProduct(product);
            return buildInfo(SHPEE_SUCCESS, "success");
        }
        return buildInfo(SHPEE_ARGS_ERROR, "403");
    }

    /**
     * @return java.lang.Object
     * @Author xentn
     * @Description Get product data interface
     * @Date 2021/10/26
     * @Param []
     */
    @ResponseBody
    @RequestMapping("/admin/api/product/list/{type}")
    public Object getData(HttpServletRequest request, @PathVariable("type") String type) {
        switch (type.toLowerCase()) {
            case "products":
                Integer page = Integer.parseInt(request.getParameter("page"));
                Integer limit = Integer.parseInt(request.getParameter("limit"));

                TProduct tsProduct = new TProduct();
                tsProduct.setEnabled(true);

                List<TProduct> products = getProductMapper().selectProduct(tsProduct);
                List<TProductGroup> productGroups = getProductMapper().selectGroup(null);
                List<Map<String, String>> maps = new ArrayList<>();
                Map<String, String> groupsMap = new HashMap<>();
                Map<String, String> map;

                for (TProductGroup tpg : productGroups) {
                    groupsMap.put(tpg.getProductGroupId(), tpg.getProductGroupName());
                }

                int count = 0;
                for (TProduct tp : products) {
                    if (count < (page - 1) * limit) {
                        count++;
                        continue;
                    }
                    map = new HashMap<>();
                    map.put("id", tp.getProductId());
                    map.put("label", tp.getProductLabel());
                    map.put("name", tp.getProductName());
                    map.put("groupName", groupsMap.get(tp.getProductGroupId()));
                    maps.add(map);
                    count++;
                    if (count == (page * limit)) {
                        break;
                    }
                }
                return buildInfo(SHPEE_SUCCESS, null, maps, products.size());
            case "parameter":
                String ptId = request.getParameter("product-id");
                //String          pName   = request.getParameter("product-name");
                List<String> parameters = new ArrayList<>();
                if (ptId == null || ptId.equals("")) {
                    return buildInfo(SHPEE_ARGS_ERROR, "error");
                }
                TProduct tProduct = new TProduct();
                tProduct.setProductId(ptId);
                tProduct.setEnabled(true);
                List<TProduct> tProducts = getProductMapper().selectProduct(tProduct);
                if (tProducts == null || tProducts.size() == 0) {
                    return buildInfo(SHPEE_SUCCESS, "null");
                }

                TProductParameter tProductParameter = new TProductParameter();
                tProductParameter.setProductParameterId(tProducts.get(0).getProductParameterId());
                tProductParameter = getProductMapper().selectParameter(tProductParameter).get(0);
                parameters.add(tProductParameter.getProductParameterText());
                int ppCount = 1;
                while (true) {
                    tProductParameter.setProductParameterId(tProductParameter.getProductParameterNextId());
                    tProductParameter = getProductMapper().selectParameter(tProductParameter).get(0);
                    parameters.add(tProductParameter.getProductParameterText());
                    if (tProductParameter.getProductParameterNextId().equals("null")) {
                        break;
                    }
                }
                return buildInfo(SHPEE_SUCCESS, tProducts.get(0).getProductName(), parameters, parameters.size());
            default:
                break;
        }
        return buildInfo(-1, "403");
    }

    /**
     * @return java.util.Map
     * @Author xentn
     * @Description Delete product data interface.
     * @Date 2021/10/26
     * @Param []
     */
    @ResponseBody
    @RequestMapping("/admin/api/product/del/{type}")
    public Map<String, Object> del(HttpServletRequest request, @PathVariable("type") String type) {
        String id = request.getParameter("id");
        switch (type) {
            case "product":
                TProduct product = null;
                product = getProductMapper().selectProductById(id);
                if (product != null) {
                    product.setEnabled(false);
                    if (getProductMapper().updateProduct(product) > 0)
                        return buildInfo(SHPEE_SUCCESS, "");
                }
                break;
            default:
                break;
        }

        return buildInfo(SHPEE_ARGS_ERROR, "delete error.");
    }
}
