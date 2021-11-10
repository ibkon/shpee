package com.xentn.shpee.mapper;

import java.util.List;
import java.util.Map;

@org.apache.ibatis.annotations.Mapper
public interface Mapper {
    List<Map<String,Object>>    select(String sql);
}
