package com.xentn.shpee.mapper;

import com.xentn.shpee.bean.product.TProductGroup;
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

}
