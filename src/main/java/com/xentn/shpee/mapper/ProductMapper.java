package com.xentn.shpee.mapper;

import com.xentn.shpee.bean.product.TProduct;
import com.xentn.shpee.bean.product.TProductGroup;
import com.xentn.shpee.bean.product.TProductParameter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/***
 * @Description: TODO
 *
 * @author: ibkon
 * @date: 2021/6/22
 *
 */
@Mapper
public interface ProductMapper {
    List<TProductGroup> selectGroup(TProductGroup group);
    int insertGroup(TProductGroup group);

    int insertProduct(TProduct product);
    List<TProduct>  selectProduct(TProduct product);
    TProduct    selectProductById(String pid);
    int updateProduct(TProduct product);

    int insertParameter(TProductParameter parameter);
    List<TProductParameter>  selectParameter(TProductParameter parameter);


}
