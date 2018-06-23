package com.cn.zbin.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cn.zbin.store.bto.ProductDetail;
import com.cn.zbin.store.bto.ProductOverView;
import com.cn.zbin.store.dto.ProductComment;
import com.cn.zbin.store.dto.ProductViewHistory;
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
	
	@RequestMapping(value = "/detail/{id}", produces = {"application/json;charset=UTF-8"}, method = { RequestMethod.GET })
	public ProductDetail getProductDetail(@PathVariable("id") String prodID) {
		return productService.getProductDetail(prodID);
	}
	
	@RequestMapping(value = "/viewhist/{id}", produces = {"application/json;charset=UTF-8"}, method = { RequestMethod.POST })
	public String addViewHistory(@PathVariable("id") String prodID,
			@RequestParam("openid") String openid) {
		return productService.addViewHistory(prodID, openid);
	}

	@RequestMapping(value = "/favorite", produces = {"application/json;charset=UTF-8"}, method = { RequestMethod.GET })
	public List<ProductViewHistory> getViewHistoryFavorite(
			@RequestParam(value = "openid", required = true) String openid,
			@RequestParam(value = "limit", required = false) Integer limit) {
		return productService.getViewHistoryFavorite(openid, limit);
	}

	@RequestMapping(value = "/comment", produces = {"application/json;charset=UTF-8"}, method = { RequestMethod.GET })
	public List<ProductComment> getProductComment(
			@RequestParam(value = "prodid", required = true) String prodID,
			@RequestParam(value = "offset", required = false) Integer offset, 
			@RequestParam(value = "limit", required = false) Integer limit) {
		return productService.getProductCommentList(prodID, offset, limit);
	}
}
