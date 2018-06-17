package com.cn.zbin.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cn.zbin.store.bto.ProductOverView;
import com.cn.zbin.store.service.ProductService;

@RestController
@RequestMapping("prod")
public class ProductController {
	@Autowired
	private ProductService productService;

	@RequestMapping(value = "/list", produces = {"application/json;charset=UTF-8"}, method = { RequestMethod.GET })
	public ProductOverView getProductList(@RequestParam(value = "search", required = false) String strSearch, 
			@RequestParam(value = "scope", required = false) String strScope,
			@RequestParam(value = "cate", required = false) String strCate,
			@RequestParam(value = "offset", required = false) Integer offset, 
			@RequestParam(value = "limit", required = false) Integer limit) {
		return productService.getProdOverviewList(strSearch, strScope, strCate, offset, limit);
	}
}
