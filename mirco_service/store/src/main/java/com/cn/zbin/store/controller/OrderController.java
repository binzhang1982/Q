package com.cn.zbin.store.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cn.zbin.store.bto.GuestOrderOverView;
import com.cn.zbin.store.bto.MsgData;
import com.cn.zbin.store.dto.ShoppingTrolleyInfo;
import com.cn.zbin.store.service.OrderService;

@RestController
@RequestMapping("order")
public class OrderController {
	@Autowired
	private OrderService orderService;

	@RequestMapping(value = "/init", consumes = {"application/json;charset=UTF-8"}, 
			produces = {"application/json;charset=UTF-8"}, method = { RequestMethod.POST })
	public GuestOrderOverView initGuestOrder(
			@RequestParam(value = "type", required = true) String type,
			@RequestParam(value = "customerid", required = true) String custid,
			@RequestBody List<ShoppingTrolleyInfo> trolleyList) {
		return orderService.initGuestOrder(type, custid, trolleyList);
	}

	@RequestMapping(value = "/create", consumes = {"application/json;charset=UTF-8"}, 
			produces = {"application/json;charset=UTF-8"}, method = { RequestMethod.POST })
	public MsgData insertGuestOrder(@RequestBody GuestOrderOverView order) {
		MsgData ret = new MsgData();
		String msg = orderService.insertGuestOrder(order);
		if (StringUtils.isNotBlank(msg)) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(msg);
		}
		return ret;
	}
	
	@RequestMapping(value = "/list", produces = {"application/json;charset=UTF-8"}, method = { RequestMethod.GET })
	public List<GuestOrderOverView> getGuestOrderList(
			@RequestParam(value = "customerid", required = true) String customerid,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "offset", required = false) Integer offset, 
			@RequestParam(value = "limit", required = false) Integer limit) {
		return orderService.getGuestOrderList(customerid, status, offset, limit);
	}
	
	@RequestMapping(value = "", produces = {"application/json;charset=UTF-8"}, method = { RequestMethod.GET })
	public GuestOrderOverView getGuestOrder(@RequestParam("customerid") String customerid,
			@RequestParam("orderid") String orderid) {
		return orderService.getGuestOrder(customerid, orderid);
	}
}
