package com.cn.zbin.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cn.zbin.management.dto.SlideMasterInfo;
import com.cn.zbin.management.service.SlideService;

@RestController
public class SlideController {
	@Autowired
	private SlideService slideService;
	
	@RequestMapping(value = "/slides/all", produces = {"application/json;charset=UTF-8"}, method = { RequestMethod.GET })
	public List<SlideMasterInfo> getAllSildes() {
		List<SlideMasterInfo> ret = slideService.getAllSlides();
		return ret;
	}
}
