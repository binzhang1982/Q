package com.cn.zbin.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cn.zbin.management.dto.MasterCity;
import com.cn.zbin.management.dto.MasterProvince;
import com.cn.zbin.management.service.MasterService;

@RestController
@RequestMapping("master")
public class MasterController {
	@Autowired
	private MasterService masterService;

	@RequestMapping(value = "/city/list", 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.GET })
	public List<MasterCity> getCityList(
			@RequestParam(value = "search", required = false) String strSearch, 
			@RequestParam(value = "pcode", required = false) String strProvinceCode,
			@RequestParam(value = "ccode", required = false) String strCityCode,
			@RequestParam(value = "offset", required = false) Integer offset, 
			@RequestParam(value = "limit", required = false) Integer limit) {
		return masterService.getCityList(strSearch, strProvinceCode, strCityCode, offset, limit);
	}

	@RequestMapping(value = "/province/list", 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.GET })
	public List<MasterProvince> getProvinceList(
			@RequestParam(value = "search", required = false) String strSearch, 
			@RequestParam(value = "pcode", required = false) String strProvinceCode,
			@RequestParam(value = "offset", required = false) Integer offset, 
			@RequestParam(value = "limit", required = false) Integer limit) {
		return masterService.getProvinceList(strSearch, strProvinceCode, offset, limit);
	}
}
