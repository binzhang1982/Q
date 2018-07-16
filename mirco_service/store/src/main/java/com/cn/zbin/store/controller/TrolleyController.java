package com.cn.zbin.store.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cn.zbin.store.bto.MsgData;
import com.cn.zbin.store.bto.ShoppingTrolleyOverView;
import com.cn.zbin.store.dto.ShoppingTrolleyInfo;
import com.cn.zbin.store.service.TrolleyService;

@RestController
@RequestMapping("trolley")
public class TrolleyController {
	@Autowired
	private TrolleyService trolleyService;
	
	@RequestMapping(value = "", consumes = {"application/json;charset=UTF-8"}, 
			produces = {"application/json;charset=UTF-8"}, method = { RequestMethod.POST })
	public MsgData add2Trolley(@RequestBody List<ShoppingTrolleyInfo> trolleyList) {
		MsgData ret = new MsgData();
		String msg = trolleyService.add2Trolley(trolleyList);
		if (StringUtils.isNotBlank(msg)) {
			ret.setMessage(msg);
			ret.setStatus(MsgData.status_ng);
		}
		return ret;
	}

	@RequestMapping(value = "/salecount", consumes = {"application/json;charset=UTF-8"}, 
			produces = {"application/json;charset=UTF-8"}, method = { RequestMethod.POST })
	public MsgData updateTrolley(@RequestBody ShoppingTrolleyInfo trolley) {
		MsgData ret = new MsgData();
		String errMsg = trolleyService.updateTrolley(trolley);
		if (StringUtils.isNotBlank(errMsg)) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(errMsg);
		}
		return ret;
	}
	
	@RequestMapping(value = "/list", produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.GET })
	public ShoppingTrolleyOverView getTrolleyList(
			@RequestParam(value = "customerid", required = true) String custid,
			@RequestParam(value = "scope", required = false) String strScope) {
		return trolleyService.getTrolleyList(custid, strScope);
	}
}
