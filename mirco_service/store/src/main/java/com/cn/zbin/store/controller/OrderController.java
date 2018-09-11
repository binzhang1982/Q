package com.cn.zbin.store.controller;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
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
import com.cn.zbin.store.dto.ProductComment;
import com.cn.zbin.store.dto.ShoppingTrolleyInfo;
import com.cn.zbin.store.dto.WxPayHistory;
import com.cn.zbin.store.exception.BusinessException;
import com.cn.zbin.store.service.OrderService;
import com.cn.zbin.store.utils.StoreConstants;
import com.cn.zbin.store.utils.StoreKeyConstants;
import com.cn.zbin.store.utils.Utils;

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
	public GuestOrderOverView insertGuestOrder(@RequestBody GuestOrderOverView order) {
		logger.info("post api: /order/create || order: " + order.toString());
		GuestOrderOverView ret = new GuestOrderOverView();
		try {
			GuestOrderInfo newOrder = new GuestOrderInfo();
			newOrder.setOrderId(orderService.insertGuestOrder(order));
			ret.setGuestOrderInfo(newOrder);
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
				operation.setCustOperCode(StoreKeyConstants.ORDER_OPERATION_CANCEL);
				operation.setCustOperTime(new Date());
				orderService.operateOrder(operation, customerid, 
						StoreKeyConstants.OPERATION_TYPE_CUSTOMER);
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
				operation.setMgmtOperCode(StoreKeyConstants.ORDER_OPERATION_CANCEL);
				operation.setMgmtEmpId(StoreKeyConstants.SYSTEM_EMP_ID);
				operation.setOrderId(order.getOrderId());
				operation.setMgmtOperTime(new Date());
				orderService.operateOrder(operation, StoreKeyConstants.SYSTEM_EMP_ID, 
						StoreKeyConstants.OPERATION_TYPE_MANAGEMENT);
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
			produces = {"application/json;charset=UTF-8"}, 
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
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(StoreConstants.CHK_ERR_99999);
			return ret;
		}
		
		if (hist.getOutTradeNo() != null && hist.getCreateTime() == null) {
			try {
				orderService.logPayHistory(hist);
				orderService.updateTradeState(hist);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		try {
			ret.setParam(orderService.returnPayUnifiedParams(appid, hist.getPrepayId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ret;
	}

	@RequestMapping(value = "/pay/query", 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.GET })
	public WxPayOverView queryOrderPay(
			@RequestParam(value = "orderid", required = true) String orderId, 
			@RequestParam(value = "customerid", required = true) String customerId, 
			@RequestParam(value = "appid", required = true) String appid) {
		logger.info("get api: /order/pay/query || orderid: " + orderId
				+ " || customerid: " + customerId);
		WxPayOverView ret = new WxPayOverView();
		WxPayHistory hist = new WxPayHistory();
		try {
			hist = orderService.queryPay(orderId, customerId, appid, 
					StoreKeyConstants.MCHID, StoreKeyConstants.PAYSECRET);
			logger.info("return_code : {} | return_msg : {} | trade_state : {}", 
					hist.getReturnCode(), hist.getReturnMsg(), hist.getTradeState());
		} catch (BusinessException be) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(be.getMessage());
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (hist.getOutTradeNo() != null) {
			try {
				orderService.logPayHistory(hist);
				orderService.updateTradeState(hist);
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

	@RequestMapping(value = "/pay/close", 
			method = { RequestMethod.GET })
	public WxPayOverView closeOrderPay(
			@RequestParam(value = "outtradeno", required = true) String outTradeNo, 
			@RequestParam(value = "appid", required = true) String appid) {
		logger.info("get api: /order/pay/close || outtradeno: " + outTradeNo);
		WxPayOverView ret = new WxPayOverView();
		WxPayHistory hist = new WxPayHistory();
		try {
			hist = orderService.closePay(outTradeNo, appid, 
					StoreKeyConstants.MCHID, StoreKeyConstants.PAYSECRET);
			logger.info("return_code : {} | return_msg : {} | result_code : {}", 
					hist.getReturnCode(), hist.getReturnMsg(), hist.getResultCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
		ret.setPay(hist);
		return ret;
	}

	@RequestMapping(value = "/pay/scan", 
			method = { RequestMethod.GET })
	public MsgData scanPayInfo(
			@RequestParam(value = "interval", required = true) Integer interval,
			@RequestParam(value = "expiredhour", required = true, defaultValue = "2") Integer expiredhour,
			@RequestParam(value = "appid", required = true) String appid) {
		logger.info("get api: /order/pay/scan || interval: " + String.valueOf(interval) +
				" || expiredhour: " + String.valueOf(expiredhour));
		MsgData ret = new MsgData();
		try {
			WxPayHistory hist = new WxPayHistory();
			for (WxPayHistory pay : orderService.scanPayOrder(interval)) {
				try {
					hist = orderService.queryPay(pay.getOutTradeNo(), appid, 
							StoreKeyConstants.MCHID, StoreKeyConstants.PAYSECRET);
					hist.setOrderId(pay.getOrderId());
					logger.info("return_code : {} | return_msg : {} | trade_state : {}", 
							hist.getReturnCode(), hist.getReturnMsg(), hist.getTradeState());
				} catch (BusinessException be) {
					ret.setStatus(MsgData.status_ng);
					ret.setMessage(be.getMessage());
					return ret;
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				if (pay.getCreateTime().compareTo(
						DateUtils.addHours(Utils.getChinaCurrentTime(), 0 - expiredhour)) < 0 &&
						StoreKeyConstants.PAY_STATE_NOTPAY.equals(hist.getTradeState())) {
					try {
						orderService.closePay(pay.getOutTradeNo(), appid, 
								StoreKeyConstants.MCHID, StoreKeyConstants.PAYSECRET);
						logger.info("return_code : {} | return_msg : {} | result_code : {}", 
								hist.getReturnCode(), hist.getReturnMsg(), hist.getResultCode());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				if (hist.getOutTradeNo() != null) {
					try {
						orderService.logPayHistory(hist);
						orderService.updateTradeState(hist);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	@RequestMapping(value = "/wxpay/notify", 
			method = { RequestMethod.POST })
	public MsgData notifyPay(@RequestBody String bean) {
		logger.info("post api: /order/wxpay/notify || bean: " + bean);
		MsgData ret = new MsgData();
		WxPayHistory hist = new WxPayHistory();
		try {
			hist = orderService.notifyPayOrder(bean);
		} catch (BusinessException be) {
			logger.error(be.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (hist.getOutTradeNo() != null) {
			try {
				orderService.logPayHistory(hist);
				orderService.updateTradeState(hist);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ret;
	}
	
	@RequestMapping(value = "/return/agree", 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.GET })
	public MsgData agreeReturn(
			@RequestParam(value = "orderoperid", required = true) String orderoperid,
			@RequestParam(value = "empid", required = true) String empid,
			@RequestParam(value = "appid", required = true) String appid) {
		logger.info("get api: /order/return/agree || orderoperid: " + orderoperid +
				" || empid: " + empid);
		MsgData ret = new MsgData();
		try {
//			hist = orderService.applyPayUnified(orderId, customerId, 
//					spbillCreateIp, appid, StoreKeyConstants.MCHID, StoreKeyConstants.PAYSECRET);
		} catch (BusinessException be) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(be.getMessage());
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(StoreConstants.CHK_ERR_99999);
			return ret;
		}
		return ret;
	}

	@RequestMapping(value = "/delivery/confirm", 
			method = { RequestMethod.POST })
	public MsgData confirmDelivery(
			@RequestParam(value = "orderid", required = true) String orderid, 
			@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "type") Integer type) {
		logger.info("post api: /order/delivery/confirm || orderid: " + orderid +
				" || id: " + id + " || type: " + type);
		MsgData ret = new MsgData();
		try {
			orderService.confirmDelivery(orderid, id, type);
		} catch (BusinessException be) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(be.getMessage());
		} catch (Exception e) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(StoreConstants.CHK_ERR_99999);
		}
		return ret;
	}

	@RequestMapping(value = "/courier", 
			method = { RequestMethod.POST })
	public MsgData setOrderCourierNumber(
			@RequestParam(value = "empid", required = true) String empid,
			@RequestParam(value = "orderid", required = true) String orderid, 
			@RequestParam(value = "courierno", required = true) String courierno) {
		logger.info("post api: /order/courier || empid: " + empid +
				" || orderid: " + orderid + " || courierno: " + courierno);
		MsgData ret = new MsgData();
		try {
			orderService.setOrderCourierNumber(empid, orderid, courierno);
		} catch (BusinessException be) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(be.getMessage());
		} catch (Exception e) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(StoreConstants.CHK_ERR_99999);
		}
		return ret;
	}

	@RequestMapping(value = "/comments", 
			consumes = {"application/json;charset=UTF-8"}, 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.POST })
	public MsgData addOrderComments(
			@RequestParam("customerid") String customerid,
			@RequestParam("orderid") String orderid,
			@RequestBody List<ProductComment> comments) {
		logger.info("post api: /order/comment || customerid: " + customerid +
				" || orderid: " + orderid + " || comments: " + comments);
		MsgData ret = new MsgData();
		try {
			orderService.addOrderComments(customerid, orderid, comments);
		} catch (BusinessException be) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(be.getMessage());
		} catch (Exception e) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(StoreConstants.CHK_ERR_99999);
		}
		
		return ret;
	}

	@RequestMapping(value = "/comment/sys", 
			method = { RequestMethod.POST })
	public MsgData commentOrderBySystem() {
		logger.info("post api: /order/comment/sys");
		MsgData ret = new MsgData();
		try {
			List<GuestOrderInfo> guestOrderLst = orderService.getDefaultWaitCommentOrders();
			if (Utils.listNotNull(guestOrderLst)) {
				for (GuestOrderInfo order : guestOrderLst) {
					orderService.addOrderDefaultComments(order.getOrderId());
				}
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
