package com.cn.zbin.store.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cn.zbin.store.bto.MsgData;
import com.cn.zbin.store.bto.ShoppingTrolleyOverView;
import com.cn.zbin.store.dto.ShoppingTrolleyInfo;
import com.cn.zbin.store.exception.BusinessException;
import com.cn.zbin.store.service.TrolleyService;
import com.cn.zbin.store.utils.StoreConstants;

@RestController
@RequestMapping("trolley")
public class TrolleyController {
	protected static final Logger logger = LoggerFactory.getLogger(TrolleyController.class);
			
	@Autowired
	private TrolleyService trolleyService;
	
	@RequestMapping(value = "", 
			consumes = {"application/json;charset=UTF-8"}, 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.POST })
	public MsgData add2Trolley(@RequestBody List<ShoppingTrolleyInfo> trolleyList) {
		logger.info("post api: /trolley || trolleyList: " + trolleyList.toString());
		MsgData ret = new MsgData();
		try {
			trolleyService.add2Trolley(trolleyList);
		} catch (BusinessException be) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(be.getMessage());
		} catch (Exception e) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(StoreConstants.CHK_ERR_99999);
		}
		return ret;
	}

	@RequestMapping(value = "/salecount", 
			consumes = {"application/json;charset=UTF-8"}, 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.POST })
	public MsgData updateTrolley(@RequestBody ShoppingTrolleyInfo trolley) {
		logger.info("post api: /trolley/salecount || trolley: " + trolley.toString());
		MsgData ret = new MsgData();
		try {
			String msg = trolleyService.updateTrolley(trolley);
			if (StringUtils.isNotBlank(msg)) {
				ret.setStatus(MsgData.status_ng);
				ret.setMessage(msg);
			}
		} catch (BusinessException be) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(be.getMessage());
		} catch (Exception e) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(StoreConstants.CHK_ERR_99999);
		}
		return ret;
	}
	
	@RequestMapping(value = "/list", 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.GET })
	public ShoppingTrolleyOverView getTrolleyList(
			@RequestParam(value = "customerid", required = true) String custid,
			@RequestParam(value = "scope", required = false) String strScope) {
		logger.info("get api: /trolley/list || customerid: " + custid
				+ " || scope: " + strScope);
		return trolleyService.getTrolleyList(custid, strScope);
	}
}
