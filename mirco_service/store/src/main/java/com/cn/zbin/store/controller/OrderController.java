package com.cn.zbin.store.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cn.zbin.store.bto.GuestOrderOverView;
import com.cn.zbin.store.bto.MsgData;
import com.cn.zbin.store.dto.GuestOrderInfo;
import com.cn.zbin.store.dto.OrderOperationHistory;
import com.cn.zbin.store.dto.ShoppingTrolleyInfo;
import com.cn.zbin.store.exception.BusinessException;
import com.cn.zbin.store.service.OrderService;
import com.cn.zbin.store.utils.StoreConstants;
import com.cn.zbin.store.utils.StoreKeyConstants;

@RestController
@RequestMapping("order")
public class OrderController {
	protected static final Logger logger = LoggerFactory.getLogger(OrderController.class);
	@Autowired
	private OrderService orderService;

	@RequestMapping(value = "/init", 
			consumes = {"application/json;charset=UTF-8"}, 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.POST })
	public GuestOrderOverView initGuestOrder(
			@RequestParam(value = "type", required = true) String type,
			@RequestParam(value = "customerid", required = true) String custid,
			@RequestBody List<ShoppingTrolleyInfo> trolleyList) {
		logger.info("post api: /order/init || type: " + type
					+ " || customerid: " + custid + " || trolleyList: " + trolleyList.toString());
		return orderService.initGuestOrder(type, custid, trolleyList);
	}

	@RequestMapping(value = "/create", 
			consumes = {"application/json;charset=UTF-8"}, 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.POST })
	public MsgData insertGuestOrder(@RequestBody GuestOrderOverView order) {
		logger.info("post api: /order/create || order: " + order.toString());
		MsgData ret = new MsgData();
		try {
			orderService.insertGuestOrder(order);
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
	public List<GuestOrderOverView> getGuestOrderList(
			@RequestParam(value = "customerid", required = true) String customerid,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "offset", required = false) Integer offset, 
			@RequestParam(value = "limit", required = false) Integer limit) {
		logger.info("get api: /order/list || customerid: " + customerid
				+ " || status: " + status + " || offset: " + offset
				+ " || limit: " + limit);
		return orderService.getGuestOrderList(customerid, status, offset, limit);
	}
	
	@RequestMapping(value = "", 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.GET })
	public GuestOrderOverView getGuestOrder(@RequestParam("customerid") String customerid,
			@RequestParam("orderid") String orderid) {
		logger.info("get api: /order || customerid: " + customerid
				+ " || orderid: " + orderid);
		return orderService.getGuestOrder(customerid, orderid);
	}

	@RequestMapping(value = "/cancel/cust/{id}", 
			consumes = {"application/json;charset=UTF-8"}, 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.POST })
	public MsgData cancelOrderByCustomer(
			@RequestParam(value = "id", required = true) String customerid,
			@RequestBody List<OrderOperationHistory> operationList) {
		MsgData ret = new MsgData();
		try {
			for (OrderOperationHistory operation : operationList) {
				operation.setOperateType(StoreKeyConstants.OPERATION_TYPE_CUSTOMER);
				operation.setOperatorId(customerid);
				orderService.operateOrder(operation);
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
	
	@RequestMapping(value = "/cancel/sys", 
			method = { RequestMethod.POST })
	public MsgData cancelOrderBySystem() {
		MsgData ret = new MsgData();
		try {
			List<GuestOrderInfo> orderList = orderService.getExpiredUnpaidOrderList();
			for (GuestOrderInfo order : orderList) {
				OrderOperationHistory operation = new OrderOperationHistory();
				operation.setOperateCode(StoreKeyConstants.ORDER_OPERATION_CANCEL);
				operation.setOperateType(StoreKeyConstants.OPERATION_TYPE_CUSTOMER);
				operation.setOperatorId(StoreKeyConstants.SYSTEM_EMP_ID);
				operation.setOrderId(order.getOrderId());
				orderService.operateOrder(operation);
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
}
