package top.yukino.shpee.control;

import org.springframework.beans.factory.annotation.Autowired;

import top.yukino.shpee.conf.Mapper;

public class Super {
	@Autowired(required=true)
	protected Mapper	mapper;

}
