package com.cn.zbin.store.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cn.zbin.store.bto.CustomerAddressOverView;
import com.cn.zbin.store.bto.CustomerInvoiceOverView;
import com.cn.zbin.store.bto.GuestOrderOverView;
import com.cn.zbin.store.bto.LeaseCalcAmountMsgData;
import com.cn.zbin.store.bto.MsgData;
import com.cn.zbin.store.bto.OrderProductOverView;
import com.cn.zbin.store.bto.WxPayH5Param;
import com.cn.zbin.store.dto.CodeDictInfo;
import com.cn.zbin.store.dto.CustomerAddress;
import com.cn.zbin.store.dto.CustomerInfo;
import com.cn.zbin.store.dto.CustomerInvoice;
import com.cn.zbin.store.dto.EmployeeInfo;
import com.cn.zbin.store.dto.EmployeeRole;
import com.cn.zbin.store.dto.EmployeeRoleExample;
import com.cn.zbin.store.dto.GuestOrderInfo;
import com.cn.zbin.store.dto.GuestOrderInfoExample;
import com.cn.zbin.store.dto.MasterCity;
import com.cn.zbin.store.dto.MasterProvince;
import com.cn.zbin.store.dto.MessageHistory;
import com.cn.zbin.store.dto.OrderOperationHistory;
import com.cn.zbin.store.dto.OrderProduct;
import com.cn.zbin.store.dto.OrderProductExample;
import com.cn.zbin.store.dto.ProductComment;
import com.cn.zbin.store.dto.ProductCommentExample;
import com.cn.zbin.store.dto.ProductImage;
import com.cn.zbin.store.dto.ProductImageExample;
import com.cn.zbin.store.dto.ProductInfo;
import com.cn.zbin.store.dto.ProductPrice;
import com.cn.zbin.store.dto.ProductPriceExample;
import com.cn.zbin.store.dto.ShoppingTrolleyInfo;
import com.cn.zbin.store.dto.ShoppingTrolleyInfoExample;
import com.cn.zbin.store.dto.WeChatMessageHistory;
import com.cn.zbin.store.dto.WxPayHistory;
import com.cn.zbin.store.dto.WxPayHistoryExample;
import com.cn.zbin.store.dto.WxRefundHistory;
import com.cn.zbin.store.exception.BusinessException;
import com.cn.zbin.store.mapper.CodeDictInfoMapper;
import com.cn.zbin.store.mapper.CustomerAddressMapper;
import com.cn.zbin.store.mapper.CustomerInfoMapper;
import com.cn.zbin.store.mapper.CustomerInvoiceMapper;
import com.cn.zbin.store.mapper.EmployeeInfoMapper;
import com.cn.zbin.store.mapper.EmployeeRoleMapper;
import com.cn.zbin.store.mapper.GuestOrderInfoMapper;
import com.cn.zbin.store.mapper.MasterCityMapper;
import com.cn.zbin.store.mapper.MasterProvinceMapper;
import com.cn.zbin.store.mapper.MessageHistoryMapper;
import com.cn.zbin.store.mapper.OrderOperationHistoryMapper;
import com.cn.zbin.store.mapper.OrderProductMapper;
import com.cn.zbin.store.mapper.ProductCommentMapper;
import com.cn.zbin.store.mapper.ProductImageMapper;
import com.cn.zbin.store.mapper.ProductInfoMapper;
import com.cn.zbin.store.mapper.ProductPriceMapper;
import com.cn.zbin.store.mapper.ShoppingTrolleyInfoMapper;
import com.cn.zbin.store.mapper.WeChatMessageHistoryMapper;
import com.cn.zbin.store.mapper.WxPayHistoryMapper;
import com.cn.zbin.store.utils.QLHWXPayConfig;
import com.cn.zbin.store.utils.StoreConstants;
import com.cn.zbin.store.utils.StoreKeyConstants;
import com.cn.zbin.store.utils.Utils;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;

@Service
public class OrderService {
	@Autowired
	private ProductInfoMapper productInfoMapper;
	@Autowired
	private ProductPriceMapper productPriceMapper;
	@Autowired
	private ProductCommentMapper productCommentMapper;
	@Autowired
	private ShoppingTrolleyInfoMapper shoppingTrolleyInfoMapper;
	@Autowired
	private GuestOrderInfoMapper guestOrderInfoMapper;
	@Autowired
	private OrderProductMapper orderProductMapper;
	@Autowired
	private OrderOperationHistoryMapper orderOperationHistoryMapper;
	@Autowired
	private ProductImageMapper productImageMapper;
	@Autowired
	private CustomerAddressMapper customerAddressMapper;
	@Autowired
	private CustomerInvoiceMapper customerInvoiceMapper;
	@Autowired
	private MasterProvinceMapper masterProvinceMapper;
	@Autowired
	private MasterCityMapper masterCityMapper;
	@Autowired
	private CustomerInfoMapper customerInfoMapper;
	@Autowired
	private WxPayHistoryMapper wxPayHistoryMapper;
	@Autowired
	private WeChatMessageHistoryMapper wechatMessageHistoryMapper;
	@Autowired
	private EmployeeInfoMapper employeeInfoMapper;
	@Autowired
	private EmployeeRoleMapper employeeRoleMapper;
	@Autowired
	private CodeDictInfoMapper codeDictInfoMapper;
	@Autowired
	private MessageHistoryMapper messageHistoryMapper;
	
	public List<String> getOrderDesktopOpenID(String roleCode) {
		List<String> ret = new ArrayList<String>();
		EmployeeRoleExample exam_er = new EmployeeRoleExample();
		exam_er.createCriteria().andRoleCodeEqualTo(roleCode);
		List<EmployeeRole> roles = employeeRoleMapper.selectByExample(exam_er);
		if (Utils.listNotNull(roles)) {
			for (EmployeeRole role : roles) {
				if (role.getEmployeeId() != null) {
					EmployeeInfo emp = employeeInfoMapper.selectByPrimaryKey(role.getEmployeeId());
					if (emp != null && emp.getRegisterId() != null) ret.add(emp.getRegisterId()); 
				}
			}
		}
		return ret;
	}
	
	public String getDesktopProcNotifyMessage() {
		String ret = "";
		OrderProductExample exam_op = new OrderProductExample();
		exam_op.createCriteria().andStatusCodeEqualTo(StoreKeyConstants.ORDER_PROD_STATUS_WAIT_RETURN);
		Integer waitReturningCnt = orderProductMapper.countByExample(exam_op);
		
		List<String> statusLst = new ArrayList<String>();
		statusLst.add(StoreKeyConstants.ORDER_STATUS_CHANGE);
		statusLst.add(StoreKeyConstants.ORDER_STATUS_RETURN);
		GuestOrderInfoExample exam_goi = new GuestOrderInfoExample();
		exam_goi.createCriteria().andStatusCodeIn(statusLst);
		Integer waitChgRetCnt = guestOrderInfoMapper.countByExample(exam_goi);
		
		if (waitReturningCnt != 0 || waitChgRetCnt != 0) 
			ret = StringUtils.replaceEachRepeatedly(
					"回收中:{1},退换中:{2}", 
					new String[] {"{1}", "{2}"}, 
					new String[] {String.valueOf(waitReturningCnt), String.valueOf(waitChgRetCnt)});
		
		return ret;
	}

	@Transactional
	public void addDesktopProcNotifyMessage(String openId, String msg) {
		addWechatMessage(openId, msg);
	}
	
	public List<OrderProduct> getOverDueLeaseProd() {
		Date dueTime = Utils.addTimeFromCurrentTime(Utils.INTERVAL_TYPE_DAY
				, StoreKeyConstants.END_PENDING_DAYS);
		OrderProductExample exam_op = new OrderProductExample();
		exam_op.createCriteria().andActualPendingEndDateLessThanOrEqualTo(dueTime)
								.andStatusCodeEqualTo(StoreKeyConstants.ORDER_PROD_STATUS_USING)
								.andPendingCountGreaterThan(new Long(0));
		List<OrderProduct> ret = orderProductMapper.selectByExample(exam_op);
		if (!Utils.listNotNull(ret)) ret = new ArrayList<OrderProduct>();
		return ret;
	}
	
	@Transactional
	public void askEndLeaseProdSys(OrderProduct dueOrderProd) {
		OrderOperationHistory orderOperation = new OrderOperationHistory();
		orderOperation.setPendingEndDate(dueOrderProd.getActualPendingEndDate());
		orderOperation.setOrderId(dueOrderProd.getOrderId());
		orderOperation.setOrderProductId(dueOrderProd.getOrderProductId());
		
		askEndLeaseProd(orderOperation, StoreKeyConstants.OPERATION_TYPE_MANAGEMENT);
		
		GuestOrderInfo order = guestOrderInfoMapper.selectByPrimaryKey(dueOrderProd.getOrderId());
		if (order == null) return;
		CustomerInfo cust = customerInfoMapper.selectByPrimaryKey(order.getCustomerId());
		if (cust == null) return;
		
		MessageHistory sms = new MessageHistory();
		sms.setMessageId(UUID.randomUUID().toString());
		sms.setPhoneNumber(cust.getTelephone());
		sms.setSignName(StoreKeyConstants.SMS_SIGN_NAME);
		sms.setTemplateCode(StoreKeyConstants.SMS_LEASEEND_TEMPLATE_ID);
		String end = new SimpleDateFormat("yyyy-MM-dd").format(dueOrderProd.getActualPendingEndDate());
		String phone = StoreKeyConstants.DEFAULT_COMPANY_PHONE;
		CodeDictInfo cd = codeDictInfoMapper.selectByPrimaryKey(StoreKeyConstants.CODE_DICT_COMPANY_PHONE);
		if (cd != null && cd.getCodename() != null) phone = cd.getCodename(); 
		String params = "{\"end\":\"" + end + "\", \"phone\":\"" + phone + "\"}";
		sms.setTemplateParams(params);
		messageHistoryMapper.insertSelective(sms);
	}
	
	private void addWechatMessage(String openId, String msg) {
		WeChatMessageHistory record = new WeChatMessageHistory();
		record.setMessageId(UUID.randomUUID().toString());
		record.setMessageContent(msg);
		record.setRecvOpenId(openId);
		record.setSendFlag(Boolean.FALSE);
		wechatMessageHistoryMapper.insertSelective(record);
	}
	
	@Transactional
	public void askEndLeaseProd(String customerid, OrderOperationHistory orderOperation) {
		if (orderOperation.getOrderId() == null)
			throw new BusinessException(StoreConstants.CHK_ERR_90016);
		GuestOrderInfo order = guestOrderInfoMapper.selectByPrimaryKey(orderOperation.getOrderId());
		if (order == null) 
			throw new BusinessException(StoreConstants.CHK_ERR_90016);
		if (!order.getCustomerId().equals(customerid)) 
			throw new BusinessException(StoreConstants.CHK_ERR_90017);
		if (!order.getStatusCode().equals(StoreKeyConstants.ORDER_STATUS_COMMENT_CONFIRM) && 
				!order.getStatusCode().equals(StoreKeyConstants.ORDER_STATUS_WAIT_COMMENT))
			throw new BusinessException(StoreConstants.CHK_ERR_90018);
		
		if (orderOperation.getPendingEndDate() == null) 
			throw new BusinessException(StoreConstants.CHK_ERR_90021);
		if (Utils.addTimeFromCurrentTime(Utils.INTERVAL_TYPE_DAY, StoreKeyConstants.END_PENDING_DAYS)
				.compareTo(orderOperation.getPendingEndDate()) > 0 ) 
			throw new BusinessException(StoreConstants.CHK_ERR_90022);
		
		if (orderOperation.getOrderProductId() == null)
			throw new BusinessException(StoreConstants.CHK_ERR_90023);
		OrderProduct orderProd = orderProductMapper.selectByPrimaryKey(
				orderOperation.getOrderProductId());
		if (orderProd == null) 
			throw new BusinessException(StoreConstants.CHK_ERR_90023);
		if (!orderProd.getOrderId().equals(orderOperation.getOrderId()))
			throw new BusinessException(StoreConstants.CHK_ERR_90023);
		if (orderProd.getActualPendingEndDate().compareTo(orderOperation.getPendingEndDate()) < 0)
			throw new BusinessException(StoreConstants.CHK_ERR_90024);
		if (orderProd.getStatusCode() == null) 
			throw new BusinessException(StoreConstants.CHK_ERR_90025);
		if (!orderProd.getStatusCode().equals(StoreKeyConstants.ORDER_PROD_STATUS_USING)
				&& !orderProd.getStatusCode().equals(StoreKeyConstants.ORDER_PROD_STATUS_RELET))
			throw new BusinessException(StoreConstants.CHK_ERR_90025);

		Long pendingCount = 
				TimeUnit.MILLISECONDS.toDays(orderOperation.getPendingEndDate().getTime() - 
						orderProd.getActualPendingEndDate().getTime());
		ProductInfo prod = productInfoMapper.selectByPrimaryKey(orderProd.getProductId());
		if (pendingCount < prod.getLeaseMinDays()) 
			throw new BusinessException(StoreConstants.CHK_ERR_90008);
		
		askEndLeaseProd(orderOperation, StoreKeyConstants.OPERATION_TYPE_CUSTOMER);
	}
	
	private void askEndLeaseProd(OrderOperationHistory orderOperation, Integer type) {
		OrderOperationHistory record = new OrderOperationHistory();
		record.setOrderOperId(UUID.randomUUID().toString());
		record.setOrderId(orderOperation.getOrderId());
		record.setOrderProductId(orderOperation.getOrderProductId());
		record.setPendingEndDate(orderOperation.getPendingEndDate());
		if (StoreKeyConstants.OPERATION_TYPE_CUSTOMER.equals(type)) {
			record.setCustOperCode(StoreKeyConstants.ORDER_OPERATION_ASK_END);
			record.setCustOperTime(Utils.getChinaCurrentTime());
		} else {
			record.setMgmtOperCode(StoreKeyConstants.ORDER_OPERATION_ASK_END);
			record.setMgmtOperTime(Utils.getChinaCurrentTime());
			record.setMgmtEmpId(StoreKeyConstants.SYSTEM_EMP_ID);
		}
		orderOperationHistoryMapper.insertSelective(record);
		
		OrderProduct rec = new OrderProduct();
		rec.setOrderProductId(orderOperation.getOrderProductId());
		rec.setStatusCode(StoreKeyConstants.ORDER_PROD_STATUS_WAIT_RETURN);
		orderProductMapper.updateByPrimaryKeySelective(rec);
	}
	
	public LeaseCalcAmountMsgData calcLeaseAmount(String orderOperId, Date recycleDate) {
		OrderOperationHistory operation = orderOperationHistoryMapper.selectByPrimaryKey(orderOperId);
		if (operation == null) 
			throw new BusinessException(StoreConstants.CHK_ERR_90026);
		Date maxRecycle = DateUtils.addDays(operation.getPendingEndDate(), StoreKeyConstants.QA_INTERVAL_DAYS);
		Date minRecycle = DateUtils.addDays(operation.getPendingEndDate(), 0 - StoreKeyConstants.QA_INTERVAL_DAYS);
		if (maxRecycle.compareTo(recycleDate) < 0 || minRecycle.compareTo(recycleDate) > 0)
			throw new BusinessException(StoreConstants.CHK_ERR_90027);
		
		String orderProductId = operation.getOrderProductId();
		OrderProduct prod = orderProductMapper.selectByPrimaryKey(orderProductId);
		if (prod == null) 
			throw new BusinessException(StoreConstants.CHK_ERR_90023);
		if (prod.getStatusCode() == null) 
			throw new BusinessException(StoreConstants.CHK_ERR_90025);
		if (!StoreKeyConstants.ORDER_PROD_STATUS_WAIT_RETURN.equals(prod.getStatusCode())) 
			throw new BusinessException(StoreConstants.CHK_ERR_90025);
		if (recycleDate.compareTo(prod.getActualPendingEndDate()) > 0) 
			throw new BusinessException(StoreConstants.CHK_ERR_90024);
		
		LeaseCalcAmountMsgData ret = new LeaseCalcAmountMsgData();
		ret.setOrderProductId(orderProductId);
		BigDecimal amount = new BigDecimal(0);
		BigDecimal paid = prod.getPrePayAmount(); 
		BigDecimal bail = prod.getBail();
		ret.setBail(bail);
		ret.setPaid(paid);
		ret.setStartDate(prod.getActualPendingDate());
		ret.setOrginEndDate(prod.getActualPendingEndDate());
		ret.setRecycleDate(recycleDate);
		
		Date startDate = prod.getActualPendingDate();
		Long pendingCount = TimeUnit.MILLISECONDS.toDays(recycleDate.getTime() - startDate.getTime());
		ProductPrice unitPrice = getUnitPrice(Boolean.TRUE, pendingCount, prod.getProductId());
		if (unitPrice == null) 
			throw new BusinessException(StoreConstants.CHK_ERR_90008);
		amount = amount.add(paid).add(bail)
				.subtract(unitPrice.getRealPrice().multiply(new BigDecimal(pendingCount)));

		ret.setUnitPrice(unitPrice.getRealPrice());
		ret.setAmount(amount);
		ret.setPendingCount(pendingCount);
		if (amount.compareTo(new BigDecimal(0)) > 0) ret.setType("待退款");
		else ret.setType("待缴款");
		
		return ret;
	}
	
	@Transactional
	public void addOrderComments(String customerid, String orderid, List<ProductComment> comments) 
			throws BusinessException, Exception {
		GuestOrderInfo order = guestOrderInfoMapper.selectByPrimaryKey(orderid);
		if (order == null) 
			throw new BusinessException(StoreConstants.CHK_ERR_90016);
		if (!order.getCustomerId().equals(customerid)) 
			throw new BusinessException(StoreConstants.CHK_ERR_90017);
		if (!order.getStatusCode().equals(StoreKeyConstants.ORDER_STATUS_WAIT_COMMENT))
			throw new BusinessException(StoreConstants.CHK_ERR_90018);
		addOrderComments(orderid, comments);
		
		GuestOrderInfo record = new GuestOrderInfo();
		record.setOrderId(orderid);
		record.setStatusCode(StoreKeyConstants.ORDER_STATUS_COMMENT_CONFIRM);
		guestOrderInfoMapper.updateByPrimaryKeySelective(record);
	}
	
	public List<GuestOrderInfo> getDefaultWaitCommentOrders() {
		GuestOrderInfoExample exam_goi = new GuestOrderInfoExample();
		Date defaultCommentDate = DateUtils.addDays(Utils.getChinaCurrentTime(), 
				0 - StoreKeyConstants.DEFAULT_COMMENT_DAYS);
		exam_goi.createCriteria().andStatusCodeEqualTo(StoreKeyConstants.ORDER_STATUS_WAIT_COMMENT)
								.andUpdateTimeLessThan(defaultCommentDate);
		List<GuestOrderInfo> guestOrderLst = guestOrderInfoMapper.selectByExample(exam_goi);
		return guestOrderLst;
	} 
	
	@Transactional
	public void addOrderDefaultComments(String orderid) throws BusinessException, Exception {
		addOrderComments(orderid, null);
		GuestOrderInfo record = new GuestOrderInfo();
		record.setOrderId(orderid);
		record.setStatusCode(StoreKeyConstants.ORDER_STATUS_COMMENT_CONFIRM);
		guestOrderInfoMapper.updateByPrimaryKeySelective(record);
	}
	
	private void addOrderComments(String orderid, List<ProductComment> comments) {
		String commentContent = "";
		BigDecimal commentLevel = new BigDecimal(0);
		String prodid = "";
		
		OrderProductExample exam_op = new OrderProductExample();
		exam_op.createCriteria().andOrderIdEqualTo(orderid);
		List<OrderProduct> orderProdLst = orderProductMapper.selectByExample(exam_op);
		if (Utils.listNotNull(orderProdLst)) {
			for (OrderProduct prod : orderProdLst) {
				commentContent = StoreKeyConstants.DEFAULT_COMMENT_CONTENT;
				commentLevel = new BigDecimal(StoreKeyConstants.DEFAULT_COMMENT_LEVEL);
				prodid = prod.getProductId();
				if (comments != null) {
					for (ProductComment comment : comments) {
						if (prodid.equals(comment.getProductId())) {
							commentContent = comment.getCommentContent();
							commentLevel = comment.getCommentLevel();
							break;
						}
					}
				}		
				ProductCommentExample exam_pc = new ProductCommentExample();
				exam_pc.createCriteria().andOrderIdEqualTo(orderid)
										.andProductIdEqualTo(prodid);
				List<ProductComment> prodComments = productCommentMapper.selectByExample(exam_pc);
				if (Utils.listNotNull(prodComments)) continue;
				ProductComment record = new ProductComment();
				record.setProductCommentId(UUID.randomUUID().toString());
				record.setOrderId(orderid);
				record.setProductId(prodid);
				record.setCommentContent(commentContent);
				record.setCommentLevel(commentLevel);
				productCommentMapper.insertSelective(record);
			}
		}
	}
	
	@Transactional
	public void setOrderCourierNumber(String empid, String orderid, String courierno) 
			throws BusinessException, Exception {
		GuestOrderInfo order = guestOrderInfoMapper.selectByPrimaryKey(orderid);
		if (order == null) throw new BusinessException(StoreConstants.CHK_ERR_90016);
		if (order.getStatusCode().equals(StoreKeyConstants.ORDER_STATUS_WAIT_DELIVERY)) {
			GuestOrderInfo record = new GuestOrderInfo();
			record.setOrderId(orderid);
			record.setUpdateEmpId(empid);
			record.setCourierNumber(courierno);
			guestOrderInfoMapper.updateByPrimaryKeySelective(record);
		} else {
			throw new BusinessException(StoreConstants.CHK_ERR_90018);
		}
	}
	
	@Transactional
	public void confirmDelivery(String orderid, String id, Integer type) 
			throws BusinessException, Exception {
		GuestOrderInfo order = guestOrderInfoMapper.selectByPrimaryKey(orderid);
		if (order == null) throw new BusinessException(StoreConstants.CHK_ERR_90016);
		if (StoreKeyConstants.OPERATION_TYPE_CUSTOMER.equals(type) && !order.getCustomerId().equals(id))
			throw new BusinessException(StoreConstants.CHK_ERR_90017);
		if (order.getStatusCode().equals(StoreKeyConstants.ORDER_STATUS_WAIT_DELIVERY)) {
			GuestOrderInfo record = new GuestOrderInfo();
			record.setOrderId(orderid);
			record.setStatusCode(StoreKeyConstants.ORDER_STATUS_WAIT_COMMENT);
			if (StoreKeyConstants.OPERATION_TYPE_MANAGEMENT.equals(type))
				record.setUpdateEmpId(id);
			guestOrderInfoMapper.updateByPrimaryKeySelective(record);
			
			OrderProductExample exam_op = new OrderProductExample();
			exam_op.createCriteria().andOrderIdEqualTo(orderid);
			List<OrderProduct> prods = orderProductMapper.selectByExample(exam_op);
			if (Utils.listNotNull(prods)) {
				for (OrderProduct prod : prods) {
					OrderProduct rec = new OrderProduct();
					rec.setOrderProductId(prod.getOrderProductId());
					rec.setStatusCode(StoreKeyConstants.ORDER_PROD_STATUS_USING);
					rec.setActualSendDate(Utils.getChinaCurrentTimeInDay());
					if (rec.getReservePendingDate() != null) {
						rec.setActualPendingDate(Utils.getChinaCurrentTimeInDay());
						rec.setActualPendingEndDate(DateUtils.addDays(
								rec.getActualPendingDate(), prod.getPendingCount().intValue()));
					}
					orderProductMapper.updateByPrimaryKeySelective(rec);
				}
			}
			
			OrderOperationHistory operation = new OrderOperationHistory();
			operation.setOrderOperId(UUID.randomUUID().toString());
			if (StoreKeyConstants.OPERATION_TYPE_MANAGEMENT.equals(type)) {
				operation.setMgmtOperCode(StoreKeyConstants.ORDER_OPERATION_CONFIRM);
				operation.setMgmtEmpId(id);
				operation.setMgmtOperTime(Utils.getChinaCurrentTime());
			} else {
				operation.setCustOperCode(StoreKeyConstants.ORDER_OPERATION_CONFIRM);
				operation.setCustOperTime(Utils.getChinaCurrentTime());
			}
			operation.setOrderId(orderid);
			orderOperationHistoryMapper.insertSelective(operation);
		} else {
			throw new BusinessException(StoreConstants.CHK_ERR_90018);
		}
	}
	
	public WxRefundHistory applyRefund(WxPayHistory payHist, OrderOperationHistory operHist,
			String appid, String mch_id, String key) throws BusinessException, Exception {
		WxRefundHistory ret = new WxRefundHistory();
		QLHWXPayConfig config = new QLHWXPayConfig(StoreKeyConstants.CERT_PATH, appid, key, mch_id);
        WXPay wxpay = new WXPay(config);
        Map<String, String> data = new HashMap<String, String>();
        data.put("transaction_id", payHist.getTransactionId());
        String outRefundNo = Utils.getRefundNo();
        data.put("out_refund_no", outRefundNo);
        data.put("total_fee", String.valueOf(payHist.getTotalFee()));
        Integer refundFee = operHist.getAdjustAmount().multiply(new BigDecimal(100)).intValue();
        data.put("refund_fee", String.valueOf(refundFee));
        //TODO 异步通知地址（请注意必须是外网）
        data.put("notify_url", "http://106.15.88.109/store/order/wxrefund/notify");
        Map<String, String> resp = wxpay.refund(data);
        
        if (resp != null) {
        	ret.setOutRefundNo(outRefundNo);
        	ret.setOrderId(payHist.getOrderId());
        	ret.setOrderOperId(operHist.getOrderOperId());
        	ret.setOutTradeNo(payHist.getOutTradeNo());
        	ret.setRefundFee(refundFee);
        	ret.setTotalFee(payHist.getTotalFee());
        	ret.setTransactionId(payHist.getTransactionId());
            ret.setReturnCode(resp.get("return_code"));
            ret.setReturnMsg(resp.get("return_msg"));
            ret.setRefundStatus(StoreKeyConstants.REFUND_STATE_PROCESSING);
        	ret.setWxApi("refund");
            if (resp.containsKey("nonce_str")) ret.setNonceStr(resp.get("nonce_str"));
            if (resp.containsKey("sign")) ret.setSign(resp.get("sign"));
            if (resp.containsKey("result_code")) ret.setResultCode(resp.get("result_code"));
            if (resp.containsKey("err_code")) ret.setErrCode(resp.get("err_code"));
            if (resp.containsKey("err_code_des")) ret.setErrCodeDes(resp.get("err_code_des"));
            if (resp.containsKey("refund_id")) ret.setRefundId(resp.get("refund_id"));
            if (resp.containsKey("cash_fee")) ret.setCashFee(Integer.valueOf(resp.get("cash_fee")));
        }
		return ret;
	}
	
	public WxPayHistory notifyPayOrder(String bean) throws Exception {
		WxPayHistory ret = new WxPayHistory();
		Map<String, String> resp = WXPayUtil.xmlToMap(bean);
		if (!WXPayUtil.isSignatureValid(resp, StoreKeyConstants.PAYSECRET))
			throw new BusinessException(StoreConstants.CHK_ERR_99998);

		String return_code = resp.get("return_code");
		if (!StoreKeyConstants.WXPAY_API_RC_SUCCESS.equals(return_code)) {
			throw new BusinessException(ret.getReturnMsg());
		} else {
			ret.setTradeState(StoreKeyConstants.PAY_STATE_SUCCESS);
			ret.setTradeStateDesc(StoreKeyConstants.PAY_STATE_DESC_SUCCESS);
		}

		ret.setReturnCode(return_code);
		ret.setReturnMsg(resp.get("return_msg"));
		ret.setWxApi("notify");
		if (resp.containsKey("out_trade_no")) {
			ret.setOutTradeNo(resp.get("out_trade_no"));
			WxPayHistory temp = wxPayHistoryMapper.selectByPrimaryKey(resp.get("out_trade_no"));
			if (temp == null) throw new BusinessException(StoreConstants.CHK_ERR_99997);
			ret.setOrderId(temp.getOrderId());
		} else {
			 throw new BusinessException(StoreConstants.CHK_ERR_99996);
		}
		
		if (resp.containsKey("nonce_str"))
			ret.setNonceStr(resp.get("nonce_str"));
		if (resp.containsKey("sign"))
			ret.setSign(resp.get("sign"));
		if (resp.containsKey("result_code"))
			ret.setResultCode(resp.get("result_code"));
		if (resp.containsKey("err_code"))
			ret.setErrCode(resp.get("err_code"));
		if (resp.containsKey("err_code_des"))
			ret.setErrCodeDes(resp.get("err_code_des"));
		if (resp.containsKey("bank_type"))
			ret.setBankType(resp.get("bank_type"));
		if (resp.containsKey("cash_fee"))
			ret.setCashFee(Integer.parseInt(resp.get("cash_fee")));
		if (resp.containsKey("transaction_id"))
			ret.setTransactionId(resp.get("transaction_id"));
		if (resp.containsKey("time_end")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			ret.setTimeEnd((Date) sdf.parse(resp.get("time_end")));
		}
		return ret;
	}
	
	public List<WxPayHistory> scanPayOrder(Integer interval) {
		Date bef = DateUtils.addMinutes(Utils.getChinaCurrentTime(), 0-interval);
		WxPayHistoryExample exam_wph = new WxPayHistoryExample();
		exam_wph.createCriteria().andCreateTimeLessThan(bef)
								.andTradeStateEqualTo(StoreKeyConstants.PAY_STATE_NOTPAY);
		List<WxPayHistory> ret = wxPayHistoryMapper.selectByExample(exam_wph);
		if (!Utils.listNotNull(ret)) ret = new ArrayList<WxPayHistory>();
		return ret;
	}
	
	public WxPayH5Param returnPayUnifiedParams(String appId, String prepayId) throws Exception {
		Map<String, String> data = new HashMap<String, String>();
		data.put("appId", appId);
		data.put("timeStamp", String.valueOf(Utils.getChinaCurrentTimeInSeconds()));
		data.put("nonceStr", WXPayUtil.generateNonceStr());
		data.put("package", "prepay_id=" + prepayId);
		data.put("signType", "MD5");
		
		WxPayH5Param ret = new WxPayH5Param();
		ret.setAppId(appId);
		ret.setNonceStr(data.get("nonceStr"));
		ret.setPaySign(WXPayUtil.generateSignature(data, StoreKeyConstants.PAYSECRET));
		ret.setPkg(data.get("package"));
		ret.setSignType(data.get("signType"));
		ret.setTimeStamp(data.get("timeStamp"));
		return ret;
	}
	
	@Transactional
	public void logPayHistory(WxPayHistory hist) throws Exception {
		WxPayHistoryExample exam_wph = new WxPayHistoryExample();
		exam_wph.createCriteria().andOutTradeNoEqualTo(hist.getOutTradeNo());
		if (wxPayHistoryMapper.countByExample(exam_wph) > 0)
			wxPayHistoryMapper.updateByPrimaryKeySelective(hist);
		else
			wxPayHistoryMapper.insertSelective(hist);
	}
	
	@Transactional
	public void updateTradeState(WxPayHistory hist) throws Exception {
		if (hist.getTradeState() == null) return;
		if (hist.getOrderId() == null) return;
		GuestOrderInfo order = guestOrderInfoMapper.selectByPrimaryKey(hist.getOrderId());
		switch (hist.getTradeState()) {
			case StoreKeyConstants.PAY_STATE_SUCCESS:
				order.setOrderId(hist.getOrderId());
				order.setPaymentVoucher(hist.getOutTradeNo());
				order.setStatusCode(StoreKeyConstants.ORDER_STATUS_WAIT_DELIVERY);
				guestOrderInfoMapper.updateByPrimaryKeySelective(order);
				break;
			case StoreKeyConstants.PAY_STATE_NOTPAY:
				if (StoreKeyConstants.ORDER_STATUS_UNPAID.equals(order.getStatusCode())) {
					order.setOrderId(hist.getOrderId());
					order.setPaymentVoucher(hist.getOutTradeNo());
					guestOrderInfoMapper.updateByPrimaryKeySelective(order);
				}
				break;
			default:
				break;
		}
	}
	
	public WxPayHistory closePay(String outTradeNo, String appid, 
			String mch_id, String key) throws Exception {
		WxPayHistory ret = new WxPayHistory();
		QLHWXPayConfig config = new QLHWXPayConfig(null, appid, key, mch_id);
        WXPay wxpay = new WXPay(config);
        Map<String, String> data = new HashMap<String, String>();
        data.put("out_trade_no", outTradeNo);
        Map<String, String> resp = wxpay.closeOrder(data);
        if (resp != null) {
            ret.setOutTradeNo(outTradeNo);
            ret.setReturnCode(resp.get("return_code"));
            ret.setReturnMsg(resp.get("return_msg"));
			ret.setWxApi("close");
            if (resp.containsKey("nonce_str")) ret.setNonceStr(resp.get("nonce_str"));
            if (resp.containsKey("sign")) ret.setSign(resp.get("sign"));
            if (resp.containsKey("result_code")) ret.setResultCode(resp.get("result_code"));
            if (resp.containsKey("err_code")) ret.setErrCode(resp.get("err_code"));
            if (resp.containsKey("err_code_des")) ret.setErrCodeDes(resp.get("err_code_des"));
        }
        return ret;
	}
	
	public WxPayHistory queryPay(String orderId, String customerid, String appid, 
			String mch_id, String key) throws BusinessException, Exception {
		GuestOrderInfo order = guestOrderInfoMapper.selectByPrimaryKey(orderId);
		if (order == null) throw new BusinessException(StoreConstants.CHK_ERR_90016);
		if (!order.getCustomerId().equals(customerid)) 
			throw new BusinessException(StoreConstants.CHK_ERR_90017);
		if (order.getPaymentVoucher() == null) 
			throw new BusinessException(StoreConstants.CHK_ERR_90020);
        String outTradeNo = order.getPaymentVoucher();
		return queryPay(outTradeNo, appid, mch_id, key);
	}
	
	public WxPayHistory queryPay(String outTradeNo, String appid, 
			String mch_id, String key) throws BusinessException, Exception {
		WxPayHistory ret = new WxPayHistory();
		QLHWXPayConfig config = new QLHWXPayConfig(null, appid, key, mch_id);
        WXPay wxpay = new WXPay(config);
        Map<String, String> data = new HashMap<String, String>();
        data.put("out_trade_no", outTradeNo);
        Map<String, String> resp = wxpay.orderQuery(data);
        if (resp != null) {
            ret.setOutTradeNo(outTradeNo);
            ret.setReturnCode(resp.get("return_code"));
            ret.setReturnMsg(resp.get("return_msg"));
			ret.setWxApi("query");
            if (resp.containsKey("nonce_str")) ret.setNonceStr(resp.get("nonce_str"));
            if (resp.containsKey("sign")) ret.setSign(resp.get("sign"));
            if (resp.containsKey("result_code")) ret.setResultCode(resp.get("result_code"));
            if (resp.containsKey("err_code")) ret.setErrCode(resp.get("err_code"));
            if (resp.containsKey("err_code_des")) ret.setErrCodeDes(resp.get("err_code_des"));
            if (resp.containsKey("trade_state")) ret.setTradeState(resp.get("trade_state"));;
            if (resp.containsKey("bank_type")) ret.setBankType(resp.get("bank_type"));
            if (resp.containsKey("cash_fee")) ret.setCashFee(Integer.parseInt(resp.get("cash_fee")));
            if (resp.containsKey("transaction_id")) ret.setTransactionId(resp.get("transaction_id"));
            if (resp.containsKey("time_end")) {
            	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            	ret.setTimeEnd((Date)sdf.parse(resp.get("time_end")));
            }
            if (resp.containsKey("trade_state_desc")) ret.setTradeStateDesc(resp.get("trade_state_desc"));
        }
        return ret;
	}
	
	public WxPayHistory applyPayUnified(String orderId, String customerid, 
			String spbillCreateIp, String appid, String mch_id, String key)
			throws BusinessException, Exception {
		CustomerInfo cust = customerInfoMapper.selectByPrimaryKey(customerid);
		if (cust == null) throw new BusinessException(StoreConstants.CHK_ERR_90004);
		String openid = cust.getRegisterId();
		if (openid == null) throw new BusinessException(StoreConstants.CHK_ERR_90004);
		
		GuestOrderInfo order = guestOrderInfoMapper.selectByPrimaryKey(orderId);
		if (order == null) throw new BusinessException(StoreConstants.CHK_ERR_90016);
		if (!order.getCustomerId().equals(customerid)) 
			throw new BusinessException(StoreConstants.CHK_ERR_90017);
		if (!StoreKeyConstants.ORDER_STATUS_UNPAID.equals(order.getStatusCode()))
			throw new BusinessException(StoreConstants.CHK_ERR_90018);
		BigDecimal totalAmount = order.getTotalAmount();
		if (totalAmount == null) throw new BusinessException(StoreConstants.CHK_ERR_90013);
		if (totalAmount.compareTo(new BigDecimal(0)) <= 0) 
			throw new BusinessException(StoreConstants.CHK_ERR_90013);
		
		WxPayHistory ret = new WxPayHistory();
		if (order.getPaymentVoucher() != null) {
			ret = wxPayHistoryMapper.selectByPrimaryKey(order.getPaymentVoucher());
			if (ret != null) {
				if (StoreKeyConstants.PAY_STATE_NOTPAY.equals(ret.getTradeState())) {
					return ret;
				} else if (StoreKeyConstants.PAY_STATE_CLOSED.equals(ret.getTradeState())) {
					ret = new WxPayHistory();
				} else {
					throw new BusinessException(StoreConstants.CHK_ERR_90018);
				}
			} else {
				ret = new WxPayHistory();
			}
		}
		
		
		QLHWXPayConfig config = new QLHWXPayConfig(null, appid, key, mch_id);
        WXPay wxpay = new WXPay(config);
        Map<String, String> data = new HashMap<String, String>();
        data.put("body", StoreKeyConstants.PAY_BODY);
        //32位,建议根据当前系统时间加随机序列来生成订单号
        String outTradeNo = Utils.getTradeNo();
        data.put("out_trade_no", outTradeNo);
        String totalFee = String.valueOf(totalAmount.multiply(new BigDecimal(100)).intValue());
        data.put("total_fee", totalFee);
        data.put("spbill_create_ip",spbillCreateIp);
        //TODO 异步通知地址（请注意必须是外网）
        data.put("notify_url", "http://106.15.88.109/store/order/wxpay/notify");
//        data.put("notify_url", "http://52.231.194.85/store/order/wxpay/notify");
        data.put("trade_type", "JSAPI");
        data.put("openid", openid);
        Map<String, String> resp = wxpay.unifiedOrder(data);
        
        if (resp != null) {
            ret.setOrderId(orderId);
            ret.setSpbillCreateIp(spbillCreateIp);
            ret.setOpenid(openid);
            ret.setOutTradeNo(outTradeNo);
            ret.setTotalFee(Integer.valueOf(totalFee));
            ret.setReturnCode(resp.get("return_code"));
            ret.setReturnMsg(resp.get("return_msg"));
            ret.setTradeState(StoreKeyConstants.PAY_STATE_NOTPAY);
			ret.setWxApi("unifiedorder");
            if (resp.containsKey("nonce_str")) ret.setNonceStr(resp.get("nonce_str"));
            if (resp.containsKey("sign")) ret.setSign(resp.get("sign"));
            if (resp.containsKey("result_code")) ret.setResultCode(resp.get("result_code"));
            if (resp.containsKey("err_code")) ret.setErrCode(resp.get("err_code"));
            if (resp.containsKey("err_code_des")) ret.setErrCodeDes(resp.get("err_code_des"));
            if (resp.containsKey("prepay_id")) ret.setPrepayId(resp.get("prepay_id"));
        }
		return ret;
	}
	
	public List<GuestOrderInfo> getExpiredUnpaidOrderList() {
		GuestOrderInfoExample exam_goi = new GuestOrderInfoExample();
		exam_goi.createCriteria()
			.andUpdateTimeLessThanOrEqualTo(DateUtils.addHours(new Date(), -5))
			.andStatusCodeEqualTo(StoreKeyConstants.ORDER_STATUS_UNPAID);
		List<GuestOrderInfo> ret = guestOrderInfoMapper.selectByExample(exam_goi);
		if (!Utils.listNotNull(ret)) ret = new ArrayList<GuestOrderInfo>();
		return ret;
	}
	
	@Transactional
	public void operateOrder(OrderOperationHistory operation, String operatorId, 
			Integer opertionType) throws BusinessException, Exception {
		String operationCode = operation.getCustOperCode();
		if (opertionType == StoreKeyConstants.OPERATION_TYPE_MANAGEMENT)
			operationCode = operation.getMgmtOperCode();

		GuestOrderInfo guestOrder = guestOrderInfoMapper.selectByPrimaryKey(
				operation.getOrderId());
		if (guestOrder == null) 
			throw new BusinessException(StoreConstants.CHK_ERR_90016);
		if (!guestOrder.getCustomerId().equals(operatorId) &&
				opertionType == StoreKeyConstants.OPERATION_TYPE_CUSTOMER)
			throw new BusinessException(StoreConstants.CHK_ERR_90017);

		String orderStatus = guestOrder.getStatusCode();
		
		switch (operationCode) {
			case StoreKeyConstants.ORDER_OPERATION_CANCEL:
				if (!StoreKeyConstants.ORDER_STATUS_UNPAID.equals(orderStatus)) 
					throw new BusinessException(StoreConstants.CHK_ERR_90018);
	
				operation.setOrderOperId(UUID.randomUUID().toString());
				operation.setOrderProductId(null);
				operation.setDeferDate(null);
				operation.setPendingEndDate(null);
				operation.setReturnCount(null);

				GuestOrderInfo record = new GuestOrderInfo();
				record.setOrderId(operation.getOrderId());
				record.setStatusCode(StoreKeyConstants.ORDER_STATUS_CANCELED);
				if (opertionType == StoreKeyConstants.OPERATION_TYPE_MANAGEMENT)
					record.setUpdateEmpId(operatorId);
				guestOrderInfoMapper.updateByPrimaryKeySelective(record);
				break;
			case StoreKeyConstants.ORDER_OPERATION_CONF_RETURN:
				if (!StoreKeyConstants.ORDER_STATUS_WAIT_DELIVERY.equals(orderStatus) &&
					!StoreKeyConstants.ORDER_STATUS_WAIT_COMMENT.equals(orderStatus) &&
					!StoreKeyConstants.ORDER_STATUS_COMMENT_CONFIRM.equals(orderStatus)) 
					throw new BusinessException(StoreConstants.CHK_ERR_90018);
				//TODO 7天无理由退货
				
				break;
			default:
				throw new BusinessException(StoreConstants.CHK_ERR_90019);
		}
		
		orderOperationHistoryMapper.insertSelective(operation);
	}
	
	public List<GuestOrderOverView> getGuestOrderList(String customerid,
			String status, Integer offset, Integer limit) {
		List<GuestOrderOverView> ret = new ArrayList<GuestOrderOverView>();
		GuestOrderInfoExample exam_go = new GuestOrderInfoExample();
		exam_go.createCriteria().andCustomerIdEqualTo(customerid);
		if (StringUtils.isNotBlank(status)) 
			exam_go.getOredCriteria().get(0).andStatusCodeEqualTo(status);
		else
			exam_go.getOredCriteria().get(0).andStatusCodeNotEqualTo(
					StoreKeyConstants.ORDER_STATUS_CANCELED);
		List<GuestOrderInfo> guestOrderList = guestOrderInfoMapper.selectOnePageByExample(
				exam_go, offset, limit, "update_time desc");
		if (Utils.listNotNull(guestOrderList)) {
			for (GuestOrderInfo guestOrder : guestOrderList) {
				ret.add(getGuestOrderOverView(guestOrder));
			}
		}
		
		return ret;
	}
	
	private GuestOrderOverView getGuestOrderOverView(GuestOrderInfo guestOrder) {
		GuestOrderOverView orderOV = new GuestOrderOverView();
		orderOV.setGuestOrderInfo(guestOrder);
		CustomerAddress addr = customerAddressMapper.selectByPrimaryKey(
				guestOrder.getCustAddressId());
		CustomerAddressOverView addrOV = new CustomerAddressOverView();
		addrOV.setAddress(addr);
		if (addr != null) {
			MasterProvince province = masterProvinceMapper.selectByPrimaryKey(addr.getProvinceCode());
			if (province != null) addrOV.setProvinceName(province.getProvinceName());
			MasterCity city = masterCityMapper.selectByPrimaryKey(addr.getCityCode());
			if (city != null) addrOV.setCityName(city.getCityName());
		}
		orderOV.setCustomerAddress(addrOV);
		
		CustomerInvoice invoice = customerInvoiceMapper.selectByPrimaryKey(
				guestOrder.getCustInvoiceId());
		CustomerInvoiceOverView invoiceOV = new CustomerInvoiceOverView();
		invoiceOV.setInvoice(invoice);
		if (invoice != null) {
			if (invoice.getInvoiceType().equals(StoreKeyConstants.INVOICE_TYPE_PER))
				invoiceOV.setInvoiceTypeName(StoreKeyConstants.INVOICE_TYPE_NM_PER);
			else
				invoiceOV.setInvoiceTypeName(StoreKeyConstants.INVOICE_TYPE_NM_CORP);
		}
		orderOV.setCustomerInvoice(invoiceOV);
		
		orderOV.setOrderProductList(new ArrayList<OrderProductOverView>());
		OrderProductExample exam_op = new OrderProductExample();
		exam_op.createCriteria().andOrderIdEqualTo(guestOrder.getOrderId());
		List<OrderProduct> orderProdList = orderProductMapper.selectByExample(exam_op);
		if (Utils.listNotNull(orderProdList)) {
			for (OrderProduct orderProd : orderProdList) {
				OrderProductOverView orderProdOV = new OrderProductOverView();
				orderProdOV.setOrderProduct(orderProd);
				ProductInfo prod = productInfoMapper.selectByPrimaryKey(orderProd.getProductId());
				if (prod != null) {
					orderProdOV.setProdInfo(prod);
					orderProdOV.setIsLease(prod.getLeaseFlag());
					ProductPrice unitPrice = getUnitPrice(prod.getLeaseFlag(), 
							orderProd.getPendingCount(), orderProd.getProductId());
					orderProdOV.setRealUnitPrice(unitPrice.getRealPrice());
					orderProdOV.setFrontCoverImage(getFrontCoverImage(orderProd.getProductId()));
				}
				orderOV.getOrderProductList().add(orderProdOV);
			}
		}
		
		return orderOV;
	}
	
	public GuestOrderOverView getGuestOrder(String customerid, String orderid) {
		GuestOrderInfo guestOrder = guestOrderInfoMapper.selectByPrimaryKey(orderid);
		return getGuestOrderOverView(guestOrder);
	}
	
	@Transactional
	public String insertGuestOrder(GuestOrderOverView orderView) 
			throws BusinessException, Exception {
		String orderid = "";
		BigDecimal actualAmount = new BigDecimal(0);
		GuestOrderInfo order = orderView.getGuestOrderInfo();
		if (StringUtils.isBlank(order.getCustAddressId()))
			throw new BusinessException(StoreConstants.CHK_ERR_90012);
		
		BigDecimal totalAmount = order.getTotalAmount();
		actualAmount = actualAmount.add(order.getCarriage())
									.add(order.getService());
		orderid = UUID.randomUUID().toString();
		order.setOrderId(orderid);
		order.setStatusCode(StoreKeyConstants.ORDER_STATUS_UNPAID);
		order.setCreateEmpId(StoreKeyConstants.SYSTEM_EMP_ID);
		order.setUpdateEmpId(StoreKeyConstants.SYSTEM_EMP_ID);
		order.setIsOffered(Boolean.FALSE);
		guestOrderInfoMapper.insert(order);
		List<OrderProductOverView> orderProductList = orderView.getOrderProductList();
		for (OrderProductOverView orderProduct : orderProductList) {
			OrderProduct orderProd = orderProduct.getOrderProduct();
			actualAmount = actualAmount.add(orderProd.getBail())
					.add(orderProd.getPrePayAmount())
					.subtract(orderProd.getPaidAmount());
			ProductInfo prod = productInfoMapper.selectByPrimaryKey(orderProd.getProductId());
			if (prod != null) {
				ProductPrice unitPrice = getUnitPrice(prod.getLeaseFlag(), 
						orderProd.getPendingCount(), orderProd.getProductId());
				if (prod.getLeaseFlag() && orderProd.getPrePayAmount().compareTo
						(unitPrice.getRealPrice().multiply(new BigDecimal(orderProd.getPendingCount()))) != 0) {
					throw new BusinessException(StoreConstants.CHK_ERR_90013);
				} else if (!prod.getLeaseFlag() &&  orderProd.getPrePayAmount().compareTo
						(unitPrice.getRealPrice().multiply(new BigDecimal(orderProd.getSaleCount()))) != 0) {
					throw new BusinessException(StoreConstants.CHK_ERR_90013);
				}
			}
			
			orderProd.setIsDelete(Boolean.FALSE);
			orderProd.setOrderId(order.getOrderId());
			orderProd.setOrderProductId(UUID.randomUUID().toString());
			orderProd.setCreateEmpId(StoreKeyConstants.SYSTEM_EMP_ID);
			orderProd.setUpdateEmpId(StoreKeyConstants.SYSTEM_EMP_ID);
			orderProductMapper.insert(orderProd);
		}
		
		if (totalAmount.compareTo(actualAmount) != 0) 
			throw new BusinessException(StoreConstants.CHK_ERR_90014);
		
		return orderid;
	}
	
	public GuestOrderOverView initGuestOrder(String type, String custid,
			List<ShoppingTrolleyInfo> trolleyList) {
		if (StoreKeyConstants.ORDER_TYPE_GUEST.equals(type)) {
			return initGuestOrderByGuest(trolleyList, custid);
		} else if (StoreKeyConstants.ORDER_TYPE_TROLLEY.equals(type)) {
			return initGuestOrderByTrolley(trolleyList, custid);
		} else {
			return new GuestOrderOverView();
		}
	}
	
	private GuestOrderOverView initGuestOrderByGuest(
			List<ShoppingTrolleyInfo> trolleyList, String custid) {
		GuestOrderOverView ret = new GuestOrderOverView();
		ret.setGuestOrderInfo(new GuestOrderInfo());
		ret.getGuestOrderInfo().setCustomerId(custid);
		ret.getGuestOrderInfo().setCarriage(new BigDecimal(0));
		ret.getGuestOrderInfo().setService(new BigDecimal(0));
		ret.getGuestOrderInfo().setTotalAmount(new BigDecimal(0));
		ret.getGuestOrderInfo().setTotalLeaseAmount(new BigDecimal(0));
		ret.getGuestOrderInfo().setTotalBail(new BigDecimal(0));
		ret.setOrderProductList(new ArrayList<OrderProductOverView>());
		
		for (ShoppingTrolleyInfo shoppingTrolley : trolleyList) {
			shoppingTrolley.setCustomerId(custid);
			if (shoppingTrolley.getReservePendingDate() != null && shoppingTrolley.getReservePendingEndDate() != null) {
				shoppingTrolley.setPendingCount(
						TimeUnit.MILLISECONDS.toDays(
								shoppingTrolley.getReservePendingEndDate().getTime() - 
								shoppingTrolley.getReservePendingDate().getTime())); 
			} else {
				shoppingTrolley.setPendingCount(new Long(0));
			}
			String msg = checkTrolley(shoppingTrolley);
			if (!StringUtils.isBlank(msg)) {
				ret.setStatus(MsgData.status_ng);
				ret.setMessage(msg);
				ret.setGuestOrderInfo(new GuestOrderInfo());
				ret.setOrderProductList(new ArrayList<OrderProductOverView>());
				break;
			} else {
				createOverView(ret, shoppingTrolley);
			}
		}
		
		if (ret.getStatus() != MsgData.status_ng) {
			if (ret.getGuestOrderInfo().getService() != null) ret.getGuestOrderInfo().setCarriage(new BigDecimal(0));
			ret.getGuestOrderInfo().setTotalAmount(ret.getGuestOrderInfo().getTotalAmount().add(ret.getGuestOrderInfo().getCarriage())
						.add(ret.getGuestOrderInfo().getService()));
		}	
		return ret;
	}
	
	private GuestOrderOverView initGuestOrderByTrolley(
			List<ShoppingTrolleyInfo> trolleyList, String custid) {
		GuestOrderOverView ret = new GuestOrderOverView();
		ret.setGuestOrderInfo(new GuestOrderInfo());
		ret.getGuestOrderInfo().setCustomerId(custid);
		ret.getGuestOrderInfo().setCarriage(new BigDecimal(0));
		ret.getGuestOrderInfo().setService(new BigDecimal(0));
		ret.getGuestOrderInfo().setTotalAmount(new BigDecimal(0));
		ret.getGuestOrderInfo().setTotalLeaseAmount(new BigDecimal(0));
		ret.getGuestOrderInfo().setTotalBail(new BigDecimal(0));
		ret.setOrderProductList(new ArrayList<OrderProductOverView>());

		ShoppingTrolleyInfoExample exam_trolley;
		for (ShoppingTrolleyInfo trolley : trolleyList) {
			exam_trolley = new ShoppingTrolleyInfoExample();
			exam_trolley.createCriteria().andTrolleyIdEqualTo(trolley.getTrolleyId())
										.andCustomerIdEqualTo(custid)
										.andIsDeleteEqualTo(Boolean.FALSE);
			List<ShoppingTrolleyInfo> shoppingTrolleyLst = shoppingTrolleyInfoMapper.selectByExample(exam_trolley);
			if (Utils.listNotNull(shoppingTrolleyLst)) {
				ShoppingTrolleyInfo shoppingTrolley = shoppingTrolleyLst.get(0);
				createOverView(ret, shoppingTrolley);
				deleteShoppingTrolley(shoppingTrolley);
			}
		}
		
		if (ret.getGuestOrderInfo().getService().compareTo(new BigDecimal(0)) > 0) ret.getGuestOrderInfo().setCarriage(new BigDecimal(0));
		ret.getGuestOrderInfo().setTotalAmount(ret.getGuestOrderInfo().getTotalAmount().add(ret.getGuestOrderInfo().getCarriage())
				.add(ret.getGuestOrderInfo().getService()));
		return ret;
	}
	
	private String checkTrolley(ShoppingTrolleyInfo trolleyBean) {
		String ret = "";
		if (trolleyBean.getSaleCount() == null) return StoreConstants.CHK_ERR_90003;
		else if (trolleyBean.getSaleCount() == 0) return StoreConstants.CHK_ERR_90003;
		
		if (trolleyBean.getProductId() != null) {
			ProductInfo prod = productInfoMapper.selectByPrimaryKey(trolleyBean.getProductId());
			if (prod == null) return StoreConstants.CHK_ERR_90001; 
			else if (prod.getLeaseFlag() && trolleyBean.getSaleCount() > 1) return StoreConstants.CHK_ERR_90005;
			else if (prod.getLeaseFlag() && trolleyBean.getPendingCount() < prod.getLeaseMinDays()) return StoreConstants.CHK_ERR_90008;
			else if (prod.getLeaseFlag() && trolleyBean.getReservePendingDate() == null) return StoreConstants.CHK_ERR_90006;
			else if (prod.getLeaseFlag() && trolleyBean.getReservePendingEndDate() == null) return StoreConstants.CHK_ERR_90007;
			else if (prod.getLeaseFlag() && trolleyBean.getPendingCount() <= 0) return StoreConstants.CHK_ERR_90009;

			trolleyBean.setLeaseFlag(prod.getLeaseFlag());
		} else {
			return StoreConstants.CHK_ERR_90001;
		}

		return ret;
	}
	
	private void deleteShoppingTrolley(ShoppingTrolleyInfo shoppingTrolley) {
		shoppingTrolley.setIsDelete(Boolean.TRUE);
		shoppingTrolley.setDeleteCode(StoreKeyConstants.TROLLEY_DEL_REASON_ORDER);
		shoppingTrolleyInfoMapper.updateByPrimaryKeySelective(shoppingTrolley);
	}
	
	private void createOverView(GuestOrderOverView ov, ShoppingTrolleyInfo shoppingTrolley) {
		BigDecimal ret = new BigDecimal(0);
		ProductInfo prod = productInfoMapper.selectByPrimaryKey(shoppingTrolley.getProductId());
		if (prod != null) {
			ProductPrice unitPrice = getUnitPrice(
					prod.getLeaseFlag(), shoppingTrolley.getPendingCount(), 
					prod.getProductId());
			BigDecimal realUnitPrice = unitPrice.getRealPrice();
			
			if (realUnitPrice.compareTo(new BigDecimal(0)) > 0) {
				OrderProductOverView orderProductOV = new OrderProductOverView();
				orderProductOV.setProdInfo(prod);
				orderProductOV.setFrontCoverImage(getFrontCoverImage(prod.getProductId()));
				orderProductOV.setOrderProduct(new OrderProduct());
				orderProductOV.getOrderProduct().setBail(prod.getBail()==null?new BigDecimal(0):prod.getBail());
				orderProductOV.getOrderProduct().setRefundBail(new BigDecimal(0));
				if (prod.getLeaseFlag()) {
					orderProductOV.getOrderProduct().setPrePayAmount(
							realUnitPrice.multiply(new BigDecimal(shoppingTrolley.getPendingCount())));
					ov.getGuestOrderInfo().setTotalLeaseAmount(
							ov.getGuestOrderInfo().getTotalLeaseAmount().add(orderProductOV.getOrderProduct().getPrePayAmount()));
					ov.getGuestOrderInfo().setTotalBail(
							ov.getGuestOrderInfo().getTotalBail().add(orderProductOV.getOrderProduct().getBail()));
				} else {
					orderProductOV.getOrderProduct().setPrePayAmount(
							realUnitPrice.multiply(new BigDecimal(shoppingTrolley.getSaleCount())));
				}
				orderProductOV.getOrderProduct().setPaidAmount(new BigDecimal(0));
				orderProductOV.getOrderProduct().setRefundAmount(new BigDecimal(0));
				orderProductOV.getOrderProduct().setProductId(shoppingTrolley.getProductId());
				orderProductOV.getOrderProduct().setReservePendingDate(shoppingTrolley.getReservePendingDate());
				orderProductOV.getOrderProduct().setReservePendingEndDate(shoppingTrolley.getReservePendingEndDate());
				orderProductOV.getOrderProduct().setPendingCount(shoppingTrolley.getPendingCount());
				orderProductOV.getOrderProduct().setSaleCount(shoppingTrolley.getSaleCount());
				if (ov.getGuestOrderInfo().getService().compareTo(prod.getService()) < 0) 
					ov.getGuestOrderInfo().setService(ov.getGuestOrderInfo().getService().add((prod.getService())));
				if (ov.getGuestOrderInfo().getCarriage().compareTo(prod.getCarriage()) < 0) 
					ov.getGuestOrderInfo().setCarriage(prod.getCarriage());
				orderProductOV.setRealUnitPrice(realUnitPrice);
				orderProductOV.setIsLease(prod.getLeaseFlag());
				ret = ret.add(orderProductOV.getOrderProduct().getPrePayAmount())
					.subtract(orderProductOV.getOrderProduct().getPaidAmount())
					.subtract(orderProductOV.getOrderProduct().getRefundAmount())
					.add(orderProductOV.getOrderProduct().getBail())
					.subtract(orderProductOV.getOrderProduct().getRefundBail());
				ov.getOrderProductList().add(orderProductOV);
			}
		}
		
		ov.getGuestOrderInfo().setTotalAmount(ov.getGuestOrderInfo().getTotalAmount().add(ret));
	}
	
	private ProductImage getFrontCoverImage(String prodID) {
		ProductImageExample exam_pi = new ProductImageExample();
		exam_pi.createCriteria().andFrontCoverFlagEqualTo(Boolean.TRUE)
								.andProductIdEqualTo(prodID);
		List<ProductImage> imageList = productImageMapper.selectByExample(exam_pi);
		if (Utils.listNotNull(imageList)) {
			return imageList.get(0);
		} else {
			return new ProductImage();
		}
	}	
	
	private ProductPrice getUnitPrice(Boolean leaseFlag, Long pendingCount, String prodID) {
		ProductPriceExample exam_pp = new ProductPriceExample();
		if (leaseFlag) {
			exam_pp.createCriteria().andDaysLessThanOrEqualTo(pendingCount)
									.andProductIdEqualTo(prodID);
			exam_pp.setOrderByClause("days desc");
			List<ProductPrice> prodPriceLst = productPriceMapper.selectByExample(exam_pp);
			if (Utils.listNotNull(prodPriceLst)) {
				return prodPriceLst.get(0);
			}
		} else {
			exam_pp.createCriteria().andProductIdEqualTo(prodID);
			List<ProductPrice> prodPriceLst = productPriceMapper.selectByExample(exam_pp);
			if (Utils.listNotNull(prodPriceLst)) {
				return prodPriceLst.get(0);
			}
		}
		return null;
	}
}
