package com.cn.zbin.store.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cn.zbin.store.dto.SlideMasterInfo;
import com.cn.zbin.store.service.SlideService;

@RestController
@RequestMapping("slide")
public class SlideController {
	protected static final Logger logger = LoggerFactory.getLogger(SlideController.class);
	@Autowired
	private SlideService slideService;
	
	@RequestMapping(value = "/list", 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.GET })
	public List<SlideMasterInfo> getSildeList() {
		logger.info("get api: /slide/list");
		List<SlideMasterInfo> ret = slideService.getSildeList();
		return ret;
	}
}
