package com.cn.zbin.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cn.zbin.store.dto.ShoppingTrolleyInfo;
import com.cn.zbin.store.service.TrolleyService;

@RestController
@RequestMapping("trolley")
public class TrolleyController {
	@Autowired
	private TrolleyService trolleyService;
	
	@RequestMapping(value = "/add", consumes = {"application/json;charset=UTF-8"}, 
			produces = {"application/json;charset=UTF-8"}, method = { RequestMethod.POST })
	public String add2Trolley(@RequestBody ShoppingTrolleyInfo trolleyBean) {
		String ret = "";
		
		return ret;
	}
	
	@RequestMapping(value = "/list", produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.GET })
	public List<ShoppingTrolleyInfo> getTrolleyList() {
		return null;
	}
}
