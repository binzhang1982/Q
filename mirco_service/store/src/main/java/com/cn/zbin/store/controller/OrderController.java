package com.cn.zbin.store.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cn.zbin.store.bto.CountMsgData;
import com.cn.zbin.store.bto.GuestOrderListMsgData;
import com.cn.zbin.store.bto.GuestOrderOverView;
import com.cn.zbin.store.bto.LeaseCalcAmountMsgData;
import com.cn.zbin.store.bto.MsgData;
import com.cn.zbin.store.bto.OrderOperationListMsgData;
import com.cn.zbin.store.bto.WxPayOverView;
import com.cn.zbin.store.dto.GuestOrderInfo;
import com.cn.zbin.store.dto.OrderOperationHistory;
import com.cn.zbin.store.dto.OrderProduct;
import com.cn.zbin.store.dto.ProductComment;
import com.cn.zbin.store.dto.ShoppingTrolleyInfo;
import com.cn.zbin.store.dto.WxPayHistory;
import com.cn.zbin.store.dto.WxRefundHistory;
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

	@RequestMapping(value = "/count", 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.GET })
	public CountMsgData countOrder(
			@RequestParam("lease") Integer lease) {
		logger.info("get api: /order/count || lease: " + lease);
		CountMsgData ret = new CountMsgData();
		try {
			ret.setCount(orderService.countOrder(lease));
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

	@RequestMapping(value = "/due/list/sys",
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.GET })
	public GuestOrderListMsgData getDueList(
			@RequestParam(value = "offset", required = false) Integer offset, 
			@RequestParam(value = "limit", required = false) Integer limit) {
		logger.info("get api: /order/due/list/sys"
				+ " || offset: " + offset + " || limit: " + limit);
		GuestOrderListMsgData ret = new GuestOrderListMsgData();
		try {
			List<String> dueOrderIds = orderService.getOverDueOrderIds(
					StoreKeyConstants.NOTIFY_END_INTERVAL_DAYS);
			if (Utils.listNotNull(dueOrderIds)) {
				ret.setTotalCount(orderService.countGuestOrderList(
						null, null, null, null, dueOrderIds));
				ret.setGuestOrderList(orderService.getGuestOrderList(
						null, null, null, null, dueOrderIds, offset, limit));
			} else {
				ret.setGuestOrderList(new ArrayList<GuestOrderOverView>());
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

	@RequestMapping(value = "/oper/list/sys", 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.GET })
	public OrderOperationListMsgData getOrderOperationListSys(
			@RequestParam(value = "askcode", required = false) String askcode,
			@RequestParam(value = "anscode", required = false) String anscode,
			@RequestParam(value = "offset", required = false) Integer offset, 
			@RequestParam(value = "limit", required = false) Integer limit) {
		logger.info("get api: /order/oper/list/sys || askcode: " + askcode +
				" || anscode: " + anscode + " || offset: " + offset + " || limit: " + limit);
		OrderOperationListMsgData ret = new OrderOperationListMsgData();
		try {
			ret.setTotalCount(orderService.countOrderOperationList(
					askcode, anscode));
			ret.setOrderOperationList(orderService.getOrderOperationList(
					askcode, anscode, offset, limit));
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
	
	@RequestMapping(value = "/list/sys", 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.GET })
	public GuestOrderListMsgData getGuestOrderListSys(
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "createdate", required = false) 
			@DateTimeFormat(pattern="yyyy-MM-dd") Date createdate,
			@RequestParam(value = "customerid", required = true) String customerid,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "telno", required = false) String telno,
			@RequestParam(value = "offset", required = false) Integer offset, 
			@RequestParam(value = "limit", required = false) Integer limit) {
		logger.info("get api: /order/list/sys || status: " + status 
				+ " || createdate: " + createdate + " || name: " + name + " || telno: " + telno
				+ " || offset: " + offset + " || limit: " + limit);
		GuestOrderListMsgData ret = new GuestOrderListMsgData();
		try {
			List<String> custAddressIds = orderService.getCustAddress(name, telno);
			ret.setTotalCount(orderService.countGuestOrderList(status, createdate, customerid, 
					custAddressIds, null));
			ret.setGuestOrderList(orderService.getGuestOrderList(status, createdate, customerid, 
					custAddressIds, null, offset, limit));
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
		return orderService.getGuestOrder(orderid);
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
				operation.setAskOperCode(StoreKeyConstants.ORDER_OPERATION_CANCEL);
				operation.setAskTime(Utils.getChinaCurrentTime());
				operation.setAskErId(customerid);
				orderService.cancelOrder(operation, customerid, 
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
				operation.setAskOperCode(StoreKeyConstants.ORDER_OPERATION_CANCEL);
				operation.setAskErId(StoreKeyConstants.SYSTEM_EMP_ID);
				operation.setOrderId(order.getOrderId());
				operation.setAskTime(Utils.getChinaCurrentTime());
				orderService.cancelOrder(operation, StoreKeyConstants.SYSTEM_EMP_ID, 
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

	@RequestMapping(value = "/delivery/confirm", 
			produces = {"application/json;charset=UTF-8"}, 
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
			produces = {"application/json;charset=UTF-8"}, 
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

	@RequestMapping(value = "/comment/customer", 
			consumes = {"application/json;charset=UTF-8"}, 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.POST })
	public MsgData commentOrderByCustomer(
			@RequestParam("customerid") String customerid,
			@RequestParam("orderid") String orderid,
			@RequestBody List<ProductComment> comments) {
		logger.info("post api: /order/comment/customer || customerid: " + customerid +
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
	
	@RequestMapping(value = "/lease/ask/end",
			consumes = {"application/json;charset=UTF-8"}, 
			produces = {"application/json;charset=UTF-8"}, 
			method = {RequestMethod.POST})
	public MsgData askEndLeaseProdCust(
			@RequestParam("customerid") String customerid, 
			@RequestBody OrderOperationHistory orderOperation) {
		logger.info("post api: /order/lease/ask/end || customerid: " + customerid +
				" || orderProductId: " + orderOperation.getOrderProductId());
		MsgData ret = new MsgData();
		try {
			orderService.askEndLeaseProdCust(customerid, orderOperation);
		} catch (BusinessException be) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(be.getMessage());
		} catch (Exception e) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(StoreConstants.CHK_ERR_99999);
		}
		return ret;
	}
	
	@RequestMapping(value = "/lease/due/sys",
			method = {RequestMethod.POST})
	public MsgData notifyDue() {
		logger.info("post api: /order/lease/due/sys");
		MsgData ret = new MsgData();
		//给顾客:即将租赁到期
		try {
			List<OrderProduct> dueOrderProds = orderService.getOverDueLeaseProd(
					StoreKeyConstants.END_INTERVAL_DAYS);
			for (OrderProduct prod : dueOrderProds) {
				try {
					orderService.askEndLeaseProdSys(prod);
				} catch (BusinessException be) {
					logger.info(prod.getOrderProductId() + " || " + be.getMessage());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	@RequestMapping(value = "/lease/recycle/calc", 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.GET })
	public LeaseCalcAmountMsgData calcRecycleAmount(
			@RequestParam("orderoperid") String orderOperId, 
			@RequestParam("recycledate") @DateTimeFormat(pattern="yyyy-MM-dd") Date recycleDate) {
		logger.info("get api: /order/lease/recycle/calc || orderoperid: " + orderOperId +
				" || recycledate: " + recycleDate);
		LeaseCalcAmountMsgData ret = new LeaseCalcAmountMsgData();
		try {
			ret = orderService.calcLeaseAmount(orderOperId, recycleDate);
		} catch (BusinessException be) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(be.getMessage());
		} catch (Exception e) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(StoreConstants.CHK_ERR_99999);
		}
		
		return ret;
	}

	@RequestMapping(value = "/lease/recycle/reject", 
			consumes = {"application/json;charset=UTF-8"}, 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.POST })
	public MsgData rejectRecycleOrders(
			@RequestParam(value = "empid", required = true) String empid,
			@RequestBody OrderOperationHistory orderOperation) {
		logger.info("post api: /order/lease/recycle/reject || empid: " + empid +
				" || orderOperationID: " + orderOperation.getOrderOperId());
		MsgData ret = new MsgData();
		try {
			orderService.rejectRecycleOrders(empid, orderOperation);
		} catch(BusinessException be) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(be.getMessage());
		} catch(Exception e) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(StoreConstants.CHK_ERR_99999);
		}
		return ret;
	}
	
	@RequestMapping(value = "/lease/recycle/agree", 
			consumes = {"application/json;charset=UTF-8"}, 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.POST })
	public MsgData agreeRecycleOrders(
			@RequestParam(value = "empid", required = true) String empid,
			@RequestBody OrderOperationHistory orderOperation) {
		logger.info("post api: /order/lease/recycle/agree || empid: " + empid +
				" || orderOperationID: " + orderOperation.getOrderOperId());
		MsgData ret = new MsgData();
		try {
			orderService.agreeRecycleOrders(empid, orderOperation);
		} catch(BusinessException be) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(be.getMessage());
		} catch(Exception e) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(StoreConstants.CHK_ERR_99999);
		}
		return ret;
	}
	
	@RequestMapping(value = "/lease/ask/defer",
			consumes = {"application/json;charset=UTF-8"}, 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.POST })
	public MsgData askDeferLeaseProdCust(
			@RequestParam("customerid") String customerid, 
			@RequestBody OrderOperationHistory orderOperation) {
		logger.info("post api: /order/lease/ask/defer || customerid: " + customerid +
				" || orderProductID: " + orderOperation.getOrderProductId());
		MsgData ret = new MsgData();
		try {
			ret.setMessage(orderService.askDeferLeaseProdCust(customerid, orderOperation));
		} catch(BusinessException be) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(be.getMessage());
		} catch(Exception e) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(StoreConstants.CHK_ERR_99999);
		}
		return ret;
	}

	@RequestMapping(value = "/wxrefund/unified/sys", 
			method = { RequestMethod.POST })
	public MsgData wxRefundOrderBySystem(
			@RequestParam(value = "appid", required = true) String appid) {
		logger.info("post api: /order/wxrefund/sys");
		MsgData ret = new MsgData();
		try {
			List<WxRefundHistory> refunds = orderService.getUnRefundHistory();
			for (WxRefundHistory refund : refunds) {
				try {
					orderService.updateRefundHistory(orderService.applyRefund(refund, appid,
							StoreKeyConstants.MCHID, StoreKeyConstants.PAYSECRET));
				} catch(BusinessException be) {
					logger.info(be.getMessage());
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		} catch(BusinessException be) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(be.getMessage());
		} catch(Exception e) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(StoreConstants.CHK_ERR_99999);
		}
		return ret;
	}

	@RequestMapping(value = "/wxrefund/query/sys", 
			method = { RequestMethod.POST })
	public MsgData wxRefundOrderQueryBySystem(
			@RequestParam(value = "appid", required = true) String appid) {
		logger.info("post api: /order/wxrefund/query/sys");
		MsgData ret = new MsgData();
		try {
			List<WxRefundHistory> refunds = orderService.getProcessingRefundHistory();
			for (WxRefundHistory refund : refunds) {
				try {
					orderService.updateRefundHistory(orderService.queryRefund(refund, appid,
							StoreKeyConstants.MCHID, StoreKeyConstants.PAYSECRET), refund.getOrderOperId());
				} catch(BusinessException be) {
					logger.info(be.getMessage());
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		} catch(BusinessException be) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(be.getMessage());
		} catch(Exception e) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(StoreConstants.CHK_ERR_99999);
		}
		return ret;
	}

	@RequestMapping(value = "/ask/change",
			consumes = {"application/json;charset=UTF-8"}, 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.POST })
	public MsgData askChangeProdCust(
			@RequestParam("customerid") String customerid, 
			@RequestBody OrderOperationHistory orderOperation) {
		logger.info("post api: /order/ask/change || customerid: " + customerid +
				" || orderProductId: " + orderOperation.getOrderProductId());
		MsgData ret = new MsgData();
		try {
			orderService.askChangeProdCust(customerid, orderOperation);
		} catch (BusinessException be) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(be.getMessage());
		} catch (Exception e) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(StoreConstants.CHK_ERR_99999);
		}
		return ret;
	}

	@RequestMapping(value = "/change/agree",
			consumes = {"application/json;charset=UTF-8"}, 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.POST })
	public MsgData agreeChangeOrders(
			@RequestParam(value = "empid", required = true) String empid,
			@RequestBody OrderOperationHistory orderOperation) {
		logger.info("post api: /order/change/agree || empid: " + empid +
				" || orderOperationID: " + orderOperation.getOrderOperId());
		MsgData ret = new MsgData();
		try {
			ret.setMessage(orderService.agreeChangeOrders(empid, orderOperation));
		} catch (BusinessException be) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(be.getMessage());
		} catch (Exception e) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(StoreConstants.CHK_ERR_99999);
		}
		return ret;
	}

	@RequestMapping(value = "/change/reject",
			consumes = {"application/json;charset=UTF-8"}, 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.POST })
	public MsgData rejectChangeOrders(
			@RequestParam(value = "empid", required = true) String empid,
			@RequestBody OrderOperationHistory orderOperation) {
		logger.info("post api: /order/change/reject || empid: " + empid +
				" || orderOperationID: " + orderOperation.getOrderOperId());
		MsgData ret = new MsgData();
		try {
			orderService.rejectChangeOrders(empid, orderOperation);
		} catch (BusinessException be) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(be.getMessage());
		} catch (Exception e) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(StoreConstants.CHK_ERR_99999);
		}
		return ret;
	}

	@RequestMapping(value = "/sales/ask/return",
			consumes = {"application/json;charset=UTF-8"}, 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.POST })
	public MsgData askReturnSalesCust(
			@RequestParam("customerid") String customerid, 
			@RequestBody OrderOperationHistory orderOperation) {
		logger.info("post api: /order/sales/ask/return || customerid: " + customerid +
				" || orderProductId: " + orderOperation.getOrderProductId());
		MsgData ret = new MsgData();
		try {
			orderService.askReturnSalesProdCust(customerid, orderOperation);
		} catch (BusinessException be) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(be.getMessage());
		} catch (Exception e) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(StoreConstants.CHK_ERR_99999);
		}
		return ret;
	}
	
	@RequestMapping(value = "/sales/return/agree",
			consumes = {"application/json;charset=UTF-8"}, 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.POST })
	public MsgData agreeReturnOrders(
			@RequestParam(value = "empid", required = true) String empid,
			@RequestBody OrderOperationHistory orderOperation) {
		logger.info("post api: /order/sales/return/agree || empid: " + empid +
				" || orderOperationID: " + orderOperation.getOrderOperId());
		MsgData ret = new MsgData();
		try {
			ret.setMessage(orderService.agreeReturnOrders(empid, orderOperation));
		} catch (BusinessException be) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(be.getMessage());
		} catch (Exception e) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(StoreConstants.CHK_ERR_99999);
		}
		return ret;
	}

	@RequestMapping(value = "/sales/return/reject",
			consumes = {"application/json;charset=UTF-8"}, 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.POST })
	public MsgData rejectReturnOrders(
			@RequestParam(value = "empid", required = true) String empid,
			@RequestBody OrderOperationHistory orderOperation) {
		logger.info("post api: /order/sales/return/reject || empid: " + empid +
				" || orderOperationID: " + orderOperation.getOrderOperId());
		MsgData ret = new MsgData();
		try {
			orderService.rejectReturnOrders(empid, orderOperation);
		} catch (BusinessException be) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(be.getMessage());
		} catch (Exception e) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(StoreConstants.CHK_ERR_99999);
		}
		return ret;
	}

	@RequestMapping(value = "/close/sys", 
			method = { RequestMethod.POST })
	public MsgData closeOrderBySystem() {
		logger.info("post api: /order/close/sys");
		MsgData ret = new MsgData();
		try {
			orderService.closeOrder();
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
