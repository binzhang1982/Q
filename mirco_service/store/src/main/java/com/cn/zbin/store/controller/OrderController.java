package com.cn.zbin.store.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cn.zbin.store.bto.GuestOrderOverView;
import com.cn.zbin.store.bto.MsgData;
import com.cn.zbin.store.bto.WxPayOverView;
import com.cn.zbin.store.dto.GuestOrderInfo;
import com.cn.zbin.store.dto.OrderOperationHistory;
import com.cn.zbin.store.dto.ShoppingTrolleyInfo;
import com.cn.zbin.store.dto.WxPayHistory;
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
	@Autowired
    private RestTemplate restTemplate;

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

	@RequestMapping(value = "/cancel/cust", 
			consumes = {"application/json;charset=UTF-8"}, 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.POST })
	public MsgData cancelOrderByCustomer(
			@RequestParam(value = "customerid", required = true) String customerid,
			@RequestBody List<OrderOperationHistory> operationList) {
		logger.info("post api: /order/cancel/cust || customerid: " + customerid
				+ " || operationList: " + operationList);
		MsgData ret = new MsgData();
		try {
			for (OrderOperationHistory operation : operationList) {
				operation.setOperateCode(StoreKeyConstants.ORDER_OPERATION_CANCEL);
				operation.setOperateType(StoreKeyConstants.OPERATION_TYPE_CUSTOMER);
				operation.setOperatorId(customerid);
				orderService.operateOrder(operation);
			}
		} catch (BusinessException be) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(be.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(StoreConstants.CHK_ERR_99999);
		}
		
		return ret;
	}
	
	@RequestMapping(value = "/cancel/sys", 
			method = { RequestMethod.POST })
	public MsgData cancelOrderBySystem() {
		logger.info("post api: /order/cancel/sys");
		MsgData ret = new MsgData();
		try {
			List<GuestOrderInfo> orderList = orderService.getExpiredUnpaidOrderList();
			for (GuestOrderInfo order : orderList) {
				OrderOperationHistory operation = new OrderOperationHistory();
				operation.setOperateCode(StoreKeyConstants.ORDER_OPERATION_CANCEL);
				operation.setOperateType(StoreKeyConstants.OPERATION_TYPE_MANAGEMENT);
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

	@RequestMapping(value = "/pay/unified", 
			method = { RequestMethod.GET })
	public WxPayOverView unifiedOrderPay(
			@RequestParam(value = "orderid", required = true) String orderId, 
			@RequestParam(value = "customerid", required = true) String customerId, 
			@RequestParam(value = "ip", required = true) String spbillCreateIp, 
			@RequestParam(value = "appid", required = true) String appid) {
		logger.info("get api: /order/pay/unified || orderid: " + orderId
				+ " || customerid: " + customerId + " || ip: " + spbillCreateIp);
		WxPayOverView ret = new WxPayOverView();
		WxPayHistory hist = new WxPayHistory();
		try {
			hist = orderService.applyPayUnified(orderId, customerId, 
					spbillCreateIp, appid, StoreKeyConstants.MCHID, StoreKeyConstants.PAYSECRET);
			logger.info("return_code : {} | return_msg : {} | prepay_id : {}", 
					hist.getReturnCode(), hist.getReturnMsg(), hist.getPrepayId());
		} catch (BusinessException be) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(be.getMessage());
			logger.info(be.getMessage());
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(StoreConstants.CHK_ERR_99999);
			return ret;
		}
		
		if (hist.getOutTradeNo() != null) {
			try {
				orderService.logPayHistory(hist);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		hist.setNonceStr(null);
		hist.setSign(null);
		hist.setWxApi(null);
		
		ret.setPay(hist);
		
		return ret;
	}

	@RequestMapping(value = "/pay/query", 
			method = { RequestMethod.GET })
	public void queryOrderPay(
			@RequestParam(value = "orderid", required = true) String orderId, 
			@RequestParam(value = "appid", required = true) String appid) {
		try {
			Map<String, String> resp = orderService.queryPay(orderId, appid, 
					StoreKeyConstants.MCHID, StoreKeyConstants.PAYSECRET);
			String msg = "return_code : " + resp.get("return_code") + 
					" | return_msg : " + resp.get("return_msg");
			if (resp.containsKey("trade_state")) { 
				msg = msg + " | trade_state : " + resp.get("trade_state");
			}
			logger.info(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/pay/close", 
			method = { RequestMethod.GET })
	public void closeOrderPay(
			@RequestParam(value = "orderid", required = true) String orderId, 
			@RequestParam(value = "appid", required = true) String appid) {
		try {
			Map<String, String> resp = orderService.closePay(orderId, appid, 
					StoreKeyConstants.MCHID, StoreKeyConstants.PAYSECRET);
			String msg = "return_code : " + resp.get("return_code") + 
					" | return_msg : " + resp.get("return_msg");
			if (resp.containsKey("result_code")) { 
				msg = msg + " | result_code : " + resp.get("result_code");
			}
			logger.info(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
