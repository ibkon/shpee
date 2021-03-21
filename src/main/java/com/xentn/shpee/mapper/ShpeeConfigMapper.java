package com.xentn.shpee.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/***
 * @Description: shpee config mapper
 *
 * @author: ibkon
 * @date: 2021/3/22
 * @version: 1.0
 */
@Mapper
public interface ShpeeConfigMapper {
    String  getConfig(@Param("shpee_key")String key);
    int     setConfig(@Param("shpee_key")String key,@Param("shpee_value")String value);
    int     updateConfig(@Param("shpee_key")String key,@Param("shpee_value")String value);
    int     deleteConfig(@Param("shpee_key")String key);
}
