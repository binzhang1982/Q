package com.cn.zbin.store.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("prod")
public class ProductController {

	@RequestMapping(value = "/list", produces = {"application/json;charset=UTF-8"}, method = { RequestMethod.GET })
	public void getProductList() {
		
	}
}
