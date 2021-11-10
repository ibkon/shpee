package com.xentn.shpee.controller.admin;

import com.xentn.shpee.bean.product.TProduct;
import com.xentn.shpee.bean.product.TProductGroup;
import com.xentn.shpee.bean.product.TProductParameter;
import com.xentn.shpee.bean.tool.Supper;
import com.xentn.shpee.controller.admin.api.ProductApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.xentn.shpee.bean.tool.ShpeeMsgCode.SHPEE_MSG_ERR;
import static com.xentn.shpee.bean.tool.ShpeeMsgCode.SHPEE_MSG_LIST;

/***
 * @Description: product admin controller
 *
 * @author: ibkon
 * @date: 2021/6/20
 * @version: 1.0
 */
@Controller
public class adminProduct extends Supper{

    private ProductApi  api;

    @Autowired
    protected void setApi(ProductApi api){
        this.api    = api;
    }

    @RequestMapping("/admin/product/list")
    public String list(){
        return "admin/product/list";
    }
    @RequestMapping("/admin/product/add/{type}")
    public String add(@PathVariable("type") String addType, Map<String,Object> map){
        if (addType.equals("product_line")){
            return "admin/product/addProductLine";
        }
        map.put("product_lines",getProductMapper().selectGroup(new TProductGroup()));
        return "admin/product/add";
    }

    @GetMapping("/admin/product/show-parameter")
    public String showParameter(HttpServletRequest request,Map<String,Object> mVal){
        String id=request.getParameter("id");
        if(id==null||id.equals("")){
            mVal.put("type",SHPEE_MSG_ERR);
            mVal.put("msg","Unauthorized access.");
        }
        else{
            TProduct    product = new TProduct();
            product.setProductId(id);
            product.setEnabled(true);

            try {
                product = getProductMapper().selectProduct(product).get(0);
            }catch (IndexOutOfBoundsException e){
                mVal.put("type",SHPEE_MSG_ERR);
                mVal.put("msg","查无此项");
                return "tool/msg";
            }

            if(getShpeeCache().get(product.getProductParameterId())==null){
                List<String>    parameters  = new ArrayList<>();
                TProductParameter   tProductParameter   = new TProductParameter();
                tProductParameter.setProductParameterId(product.getProductParameterId());

                List<TProductParameter> productParameters;
                productParameters  = getProductMapper().selectParameter(tProductParameter);
                try {
                    tProductParameter   = productParameters.get(0);
                }catch (IndexOutOfBoundsException e){
                    mVal.put("type",SHPEE_MSG_ERR);
                    mVal.put("msg","查无此项");
                    return "tool/msg";
                }

                parameters.add(tProductParameter.getProductParameterText());

                while(!tProductParameter.getProductParameterNextId().equals("null")){
                    try{
                        tProductParameter.setProductParameterId(tProductParameter.getProductParameterNextId());
                        productParameters  = getProductMapper().selectParameter(tProductParameter);
                        tProductParameter   = productParameters.get(0);
                    }catch (IndexOutOfBoundsException e){
                        mVal.put("type",SHPEE_MSG_ERR);
                        mVal.put("msg","查无此项");
                        return "tool/msg";
                    }
                    parameters.add(tProductParameter.getProductParameterText());
                }
                mVal.put("parameterList",parameters);
                setShpeeCache(product.getProductParameterId(),parameters);
                System.gc();
            }
            else{
                mVal.put("parameterList",(List<String>)getShpeeCache().get(product.getProductParameterId()));
            }
            mVal.put("type",SHPEE_MSG_LIST);
            mVal.put("title",product.getProductName());
        }
        return "tool/msg";
    }
}
