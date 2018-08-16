package com.cn.zbin.store.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cn.zbin.store.bto.FavoriteProduct;
import com.cn.zbin.store.bto.MsgData;
import com.cn.zbin.store.bto.PendingDate;
import com.cn.zbin.store.bto.ProductCommentOverView;
import com.cn.zbin.store.bto.ProductDetail;
import com.cn.zbin.store.bto.ProductOverView;
import com.cn.zbin.store.exception.BusinessException;
import com.cn.zbin.store.service.ProductService;
import com.cn.zbin.store.utils.StoreConstants;

@RestController
@RequestMapping("prod")
public class ProductController {
	protected static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	@Autowired
	private ProductService productService;

	@RequestMapping(value = "/list", 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.GET })
	public ProductOverView getProductList(@RequestParam(value = "search", required = false) String strSearch, 
			@RequestParam(value = "scope", required = false) String strScope,
			@RequestParam(value = "cate", required = false) String strCate,
			@RequestParam(value = "offset", required = false) Integer offset, 
			@RequestParam(value = "limit", required = false) Integer limit) {
		logger.info("get api: /prod/list || search: " + strSearch
				+ " || scope: " + strScope + " || cate: " + strCate
				+ " || offset: " + offset + " || limit: " + limit);
		return productService.getProdOverviewList(strSearch, strScope, strCate, offset, limit);
	}
	
	@RequestMapping(value = "/detail/{id}", 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.GET })
	public ProductDetail getProductDetail(@PathVariable("id") String prodID) {
		logger.info("get api: /prod/detail/{id} || id: " + prodID);
		return productService.getProductDetail(prodID);
	}
	
	@RequestMapping(value = "/viewhist/{id}", 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.POST })
	public MsgData addViewHistory(@PathVariable("id") String prodID,
			@RequestParam("customerid") String customerid) {
		logger.info("post api: /prod/viewhist/{id} || id: " + prodID
				+ " || customerid: " + customerid);
		MsgData ret = new MsgData();
		try {
			productService.addViewHistory(prodID, customerid);
		} catch (BusinessException be) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(be.getMessage());
		} catch (Exception e) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(StoreConstants.CHK_ERR_99999);
		}
		return ret;
	}

	@RequestMapping(value = "/favorite", 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.GET })
	public List<FavoriteProduct> getViewHistoryFavorite(
			@RequestParam(value = "customerid", required = true) String customerid,
			@RequestParam(value = "limit", required = false) Integer limit) {
		logger.info("get api: /prod/favorite || customerid: " + customerid
				+ " || limit: " + limit);
		return productService.getViewHistoryFavorite(customerid, limit);
	}

	@RequestMapping(value = "/comment", 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.GET })
	public ProductCommentOverView getProductComment(
			@RequestParam(value = "prodid", required = true) String prodID,
			@RequestParam(value = "offset", required = false) Integer offset, 
			@RequestParam(value = "limit", required = false) Integer limit) {
		logger.info("get api: /prod/comment || prodid: " + prodID
				+ " || offset: " + offset + " || limit: " + limit);
		return productService.getProductCommentList(prodID, offset, limit);
	}
	
	@RequestMapping(value = "/lease/calc", 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.GET })	
	public PendingDate calcPendingCount(@RequestParam("startdate") String pendingStartDate, 
			@RequestParam("enddate") String pendingEndDate) {
		logger.info("get api: /prod/lease/calc || startdate: " + pendingStartDate
				+ " || enddate: " + pendingEndDate);
		return productService.calcPendingCount(pendingStartDate, pendingEndDate);
	}
}
