package com.cn.zbin.store.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
import com.cn.zbin.store.dto.CustomerAddressExample;
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
import com.cn.zbin.store.dto.OrderOperationHistoryExample;
import com.cn.zbin.store.dto.OrderProduct;
import com.cn.zbin.store.dto.OrderProductExample;
import com.cn.zbin.store.dto.ProductComment;
import com.cn.zbin.store.dto.ProductCommentExample;
import com.cn.zbin.store.dto.ProductImage;
import com.cn.zbin.store.dto.ProductImageExample;
import com.cn.zbin.store.dto.ProductInfo;
import com.cn.zbin.store.dto.ProductPrice;
import com.cn.zbin.store.dto.ProductPriceExample;
import com.cn.zbin.store.dto.ProductStock;
import com.cn.zbin.store.dto.ProductStockExample;
import com.cn.zbin.store.dto.ShoppingTrolleyInfo;
import com.cn.zbin.store.dto.ShoppingTrolleyInfoExample;
import com.cn.zbin.store.dto.WeChatMessageHistory;
import com.cn.zbin.store.dto.WxPayHistory;
import com.cn.zbin.store.dto.WxPayHistoryExample;
import com.cn.zbin.store.dto.WxRefundHistory;
import com.cn.zbin.store.dto.WxRefundHistoryExample;
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
import com.cn.zbin.store.mapper.ProductStockMapper;
import com.cn.zbin.store.mapper.ShoppingTrolleyInfoMapper;
import com.cn.zbin.store.mapper.WeChatMessageHistoryMapper;
import com.cn.zbin.store.mapper.WxPayHistoryMapper;
import com.cn.zbin.store.mapper.WxRefundHistoryMapper;
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
	@Autowired
	private WxRefundHistoryMapper wxRefundHistoryMapper;
	@Autowired
	private ProductStockMapper productStockMapper;
	
	public void closeOrder() {
		GuestOrderInfoExample exam_goi = new GuestOrderInfoExample();
		exam_goi.createCriteria().andStatusCodeEqualTo(StoreKeyConstants.ORDER_STATUS_COMMENT_CONFIRM);
		List<GuestOrderInfo> orderList = guestOrderInfoMapper.selectByExample(exam_goi);
		if (Utils.listNotNull(orderList))
			for (GuestOrderInfo order : orderList) {
				OrderProductExample exam_op = new OrderProductExample();
				exam_op.createCriteria().andOrderIdEqualTo(order.getOrderId());
				List<OrderProduct> orderProds = orderProductMapper.selectByExample(exam_op);
				if (Utils.listNotNull(orderProds)) {
					boolean op_status = true;
					for (OrderProduct orderProd : orderProds) {
						if (orderProd.getPendingCount() > 0) {
							if (!StoreKeyConstants.ORDER_PROD_STATUS_REFUNDED
									.equals(orderProd.getStatusCode())) {
								op_status = false;
								break;
							} else if (Utils.addTimeFromCurrentTime(Utils.INTERVAL_TYPE_DAY, 
									0 - StoreKeyConstants.CLOSED_INTERVAL_DAYS)
									.compareTo(orderProd.getActualPendingEndDate()) < 0) {
								op_status = false;
								break;
							}
						} else {
							if (!StoreKeyConstants.ORDER_PROD_STATUS_USING
									.equals(orderProd.getStatusCode()) && 
								!StoreKeyConstants.ORDER_PROD_STATUS_REFUNDED
									.equals(orderProd.getStatusCode())) {
								op_status = false;
								break;
							} else if (Utils.addTimeFromCurrentTime(Utils.INTERVAL_TYPE_DAY, 
									0 - StoreKeyConstants.CLOSED_INTERVAL_DAYS)
									.compareTo(orderProd.getActualSendDate()) < 0) {
								op_status = false;
								break;
							}
						}
					}
					if (op_status) {
						GuestOrderInfo record = new GuestOrderInfo();
						record.setOrderId(order.getOrderId());
						record.setStatusCode(StoreKeyConstants.ORDER_STATUS_CLOSED);
						record.setUpdateEmpId(StoreKeyConstants.SYSTEM_EMP_ID);
						guestOrderInfoMapper.updateByPrimaryKeySelective(record);
					}
				}
			}
	}

	@Transactional
	public String agreeReturnOrders(String empid, OrderOperationHistory orderOperation) {
		OrderOperationHistory operation = checkAskingOperation(
				orderOperation.getOrderOperId(), StoreKeyConstants.ORDER_OPERATION_ASK_RETURN);
		OrderProduct orderProd = checkAskingOrderProduct(operation.getOrderProductId(),
				operation.getOrderId(), StoreKeyConstants.ORDER_PROD_STATUS_USING);
		operation.setAnsErId(empid);
		operation.setAnsComment(orderOperation.getAnsComment());
		operation.setAnsOperCode(StoreKeyConstants.ORDER_OPERATION_CONF_RETURN);
		operation.setAnsTime(Utils.getChinaCurrentTime());
		orderOperationHistoryMapper.updateByPrimaryKey(operation);
		
		OrderProduct subsidy = initSubsidyOrderProd(orderProd.getProductId(), empid,
				operation.getOrderProductId(), StoreKeyConstants.REF_TYPE_RETURN);
		orderProductMapper.insertSelective(subsidy);
		GuestOrderInfo order = initSubsidyOrder(operation.getOrderId(), empid, subsidy);
		guestOrderInfoMapper.insertSelective(order);
		return "已生成缴纳运费补差额的订单，请及时通知顾客支付完成退换货!";
	}
	
	@Transactional
	public void rejectReturnOrders(String empid, OrderOperationHistory orderOperation) {
		OrderOperationHistory operation = checkAskingOperation(
				orderOperation.getOrderOperId(), StoreKeyConstants.ORDER_OPERATION_ASK_RETURN);
		checkAskingOrderProduct(operation.getOrderProductId(),
				operation.getOrderId(), StoreKeyConstants.ORDER_PROD_STATUS_USING);
		operation.setAnsErId(empid);
		operation.setAnsComment(orderOperation.getAnsComment());
		operation.setAnsOperCode(StoreKeyConstants.ORDER_OPERATION_REJECT_RETURN);
		operation.setAnsTime(Utils.getChinaCurrentTime());
		orderOperationHistoryMapper.updateByPrimaryKey(operation);
	}
	
	@Transactional
	public void askReturnSalesProdCust(String customerid, OrderOperationHistory orderOperation) {
		GuestOrderInfo order = checkPaidGuestOrder(orderOperation.getOrderId());
		if (!order.getCustomerId().equals(customerid)) 
			throw new BusinessException(StoreConstants.CHK_ERR_90017);
		
		OrderProduct orderProd = checkAskingOrderProduct(orderOperation.getOrderProductId(),
				orderOperation.getOrderId(), StoreKeyConstants.ORDER_PROD_STATUS_USING);
		if (orderOperation.getReturnCount() == null) 
			throw new BusinessException(StoreConstants.CHK_ERR_90037);
		if (orderOperation.getReturnCount() > orderProd.getSaleCount())
			throw new BusinessException(StoreConstants.CHK_ERR_90038);
		if (Utils.addTimeFromCurrentTime(Utils.INTERVAL_TYPE_DAY, 0 - StoreKeyConstants.RETURN_INTERVAL_DAYS)
				.compareTo(orderProd.getActualSendDate()) > 0)
			throw new BusinessException(StoreConstants.CHK_ERR_90041);
		
		ProductInfo prod = productInfoMapper.selectByPrimaryKey(orderProd.getProductId());
		if (prod == null) 
			throw new BusinessException(StoreConstants.CHK_ERR_90001);
		if (prod.getLeaseFlag())
			throw new BusinessException(StoreConstants.CHK_ERR_90042);
		
		askReturnSalesProd(customerid, orderOperation);
	}

	private void askReturnSalesProd(String id, OrderOperationHistory orderOperation) {
		OrderOperationHistoryExample exam_ooh = new OrderOperationHistoryExample();
		exam_ooh.createCriteria().andOrderIdEqualTo(orderOperation.getOrderId())
								.andOrderProductIdEqualTo(orderOperation.getOrderProductId())
								.andAskOperCodeIsNotNull()
								.andAnsOperCodeIsNull();
		if (orderOperationHistoryMapper.countByExample(exam_ooh) > 0) 
			throw new BusinessException(StoreConstants.CHK_ERR_90028);
		
		OrderOperationHistory record = new OrderOperationHistory();
		record.setOrderOperId(UUID.randomUUID().toString());
		record.setOrderId(orderOperation.getOrderId());
		record.setOrderProductId(orderOperation.getOrderProductId());
		record.setReturnCount(orderOperation.getReturnCount());
		record.setAskOperCode(StoreKeyConstants.ORDER_OPERATION_ASK_RETURN);
		record.setAskErId(id);
		record.setAskComment(orderOperation.getAskComment());
		record.setAskTime(Utils.getChinaCurrentTime());
		orderOperationHistoryMapper.insertSelective(record);
	}
	
	@Transactional
	public String agreeChangeOrders(String empid, OrderOperationHistory orderOperation) {
		OrderOperationHistory operation = checkAskingOperation(
				orderOperation.getOrderOperId(), StoreKeyConstants.ORDER_OPERATION_ASK_CHANGE);
		OrderProduct orderProd = checkAskingOrderProduct(operation.getOrderProductId(),
				operation.getOrderId(), StoreKeyConstants.ORDER_PROD_STATUS_USING);
		operation.setAnsErId(empid);
		operation.setAnsComment(orderOperation.getAnsComment());
		operation.setAnsOperCode(StoreKeyConstants.ORDER_OPERATION_CONF_CHANGE);
		operation.setAnsTime(Utils.getChinaCurrentTime());
		orderOperationHistoryMapper.updateByPrimaryKey(operation);
		
		OrderProduct subsidy = initSubsidyOrderProd(orderProd.getProductId(), empid,
				operation.getOrderProductId(), StoreKeyConstants.REF_TYPE_CHANGE);
		orderProductMapper.insertSelective(subsidy);
		GuestOrderInfo order = initSubsidyOrder(operation.getOrderId(), empid, subsidy);
		guestOrderInfoMapper.insertSelective(order);
		return "已生成缴纳运费补差额的订单，请及时通知顾客支付完成退换货!";
	}
	
	private GuestOrderInfo initSubsidyOrder(String orderId, String empid, 
			OrderProduct subsidy) {
		GuestOrderInfo ret = new GuestOrderInfo();
		GuestOrderInfo order = guestOrderInfoMapper.selectByPrimaryKey(orderId);
		if (order == null) throw new BusinessException(StoreConstants.CHK_ERR_90016);
		
		ret.setOrderId(subsidy.getOrderId());
		ret.setStatusCode(StoreKeyConstants.ORDER_STATUS_UNPAID);
		ret.setPaymentCode(order.getPaymentCode());
		ret.setTotalAmount(subsidy.getPrePayAmount());
		ret.setTotalBail(new BigDecimal(0));
		ret.setTotalLeaseAmount(new BigDecimal(0));
		ret.setCarriage(new BigDecimal(0));
		ret.setService(new BigDecimal(0));
		ret.setCustAddressId(order.getCustAddressId());
		ret.setCustInvoiceId(order.getCustInvoiceId());
		ret.setCustomerId(order.getCustomerId());
		ret.setIsOffered(Boolean.FALSE);
		ret.setRemark(subsidy.getRemark());
		ret.setCreateEmpId(empid);
		ret.setUpdateEmpId(empid);
		return ret;
	}
	
	private OrderProduct initSubsidyOrderProd(String prodId, String empid, 
			String refOrderProdId, String refTypeCode) {
		ProductPrice subsidyUnitPrice = getUnitPrice(Boolean.FALSE, null, 
				StoreKeyConstants.SUBSIDY_PROD_ID);
		OrderProduct ret = new OrderProduct();
		ProductInfo prod = productInfoMapper.selectByPrimaryKey(prodId);
		if (prod == null) throw new BusinessException(StoreConstants.CHK_ERR_90001);
		if (prod.getService().compareTo(new BigDecimal(0)) == 0 && 
				prod.getCarriage().compareTo(new BigDecimal(0)) == 0)
			throw new BusinessException(StoreConstants.CHK_ERR_90040);
		BigDecimal unitPrice = subsidyUnitPrice.getRealPrice();
		BigDecimal saleCnt = new BigDecimal(0);
		if (prod.getService().compareTo(new BigDecimal(0)) != 0) 
			saleCnt = prod.getService().divide(unitPrice, 0, RoundingMode.FLOOR);
		else if (prod.getCarriage().compareTo(new BigDecimal(0)) != 0) 
			saleCnt = prod.getCarriage().divide(unitPrice, 0, RoundingMode.FLOOR);
		
		ret.setOrderId(UUID.randomUUID().toString());
		ret.setOrderProductId(UUID.randomUUID().toString());
		ret.setSaleCount(saleCnt.intValue());
		ret.setPrePayAmount(prod.getService().compareTo(new BigDecimal(0)) != 0?
				prod.getService():prod.getCarriage());
		ret.setBail(new BigDecimal(0));
		ret.setPendingCount(new Long(0));
		ret.setIsDelete(Boolean.FALSE);
		ret.setProductId(StoreKeyConstants.SUBSIDY_PROD_ID);
		ret.setRefOrderProductId(refOrderProdId);
		ret.setRefTypeCode(refTypeCode);
		ret.setRemark("退换货补贴");
		ret.setCreateEmpId(empid);
		ret.setUpdateEmpId(empid);
		return ret;
	}
	
	@Transactional
	public void rejectChangeOrders(String empid, OrderOperationHistory orderOperation) {
		OrderOperationHistory operation = checkAskingOperation(
				orderOperation.getOrderOperId(), StoreKeyConstants.ORDER_OPERATION_ASK_CHANGE);
		checkAskingOrderProduct(operation.getOrderProductId(),
				operation.getOrderId(), StoreKeyConstants.ORDER_PROD_STATUS_USING);
		operation.setAnsErId(empid);
		operation.setAnsComment(orderOperation.getAnsComment());
		operation.setAnsOperCode(StoreKeyConstants.ORDER_OPERATION_REJECT_CHANGE);
		operation.setAnsTime(Utils.getChinaCurrentTime());
		orderOperationHistoryMapper.updateByPrimaryKey(operation);
	}
	
	@Transactional
	public void askChangeProdCust(String customerid, OrderOperationHistory orderOperation) {
		GuestOrderInfo order = checkPaidGuestOrder(orderOperation.getOrderId());
		if (!order.getCustomerId().equals(customerid)) 
			throw new BusinessException(StoreConstants.CHK_ERR_90017);
		
		OrderProduct orderProd = checkAskingOrderProduct(orderOperation.getOrderProductId(),
				orderOperation.getOrderId(), StoreKeyConstants.ORDER_PROD_STATUS_USING);
		if (orderOperation.getReturnCount() == null) 
			throw new BusinessException(StoreConstants.CHK_ERR_90037);
		if (orderOperation.getReturnCount() > orderProd.getSaleCount())
			throw new BusinessException(StoreConstants.CHK_ERR_90038);
		if (Utils.addTimeFromCurrentTime(Utils.INTERVAL_TYPE_DAY, 0 - StoreKeyConstants.CHANGE_INTERVAL_DAYS)
				.compareTo(orderProd.getActualSendDate()) > 0)
			throw new BusinessException(StoreConstants.CHK_ERR_90039);
		
		askChangeProd(customerid, orderOperation);
	}

	private void askChangeProd(String id, OrderOperationHistory orderOperation) {
		OrderOperationHistoryExample exam_ooh = new OrderOperationHistoryExample();
		exam_ooh.createCriteria().andOrderIdEqualTo(orderOperation.getOrderId())
								.andOrderProductIdEqualTo(orderOperation.getOrderProductId())
								.andAskOperCodeIsNotNull()
								.andAnsOperCodeIsNull();
		if (orderOperationHistoryMapper.countByExample(exam_ooh) > 0) 
			throw new BusinessException(StoreConstants.CHK_ERR_90028);
		
		OrderOperationHistory record = new OrderOperationHistory();
		record.setOrderOperId(UUID.randomUUID().toString());
		record.setOrderId(orderOperation.getOrderId());
		record.setOrderProductId(orderOperation.getOrderProductId());
		record.setReturnCount(orderOperation.getReturnCount());
		record.setAskOperCode(StoreKeyConstants.ORDER_OPERATION_ASK_CHANGE);
		record.setAskErId(id);
		record.setAskComment(orderOperation.getAskComment());
		record.setAskTime(Utils.getChinaCurrentTime());
		orderOperationHistoryMapper.insertSelective(record);
	}
	
	@Transactional
	public String askDeferLeaseProdCust(String customerid, OrderOperationHistory orderOperation) {
		String ret = "";
		GuestOrderInfo order = checkPaidGuestOrder(orderOperation.getOrderId());
		if (!order.getCustomerId().equals(customerid)) 
			throw new BusinessException(StoreConstants.CHK_ERR_90017);
		
		if (orderOperation.getDeferDate() == null) 
			throw new BusinessException(StoreConstants.CHK_ERR_90034);

		OrderProduct orderProd = checkAskingOrderProduct(orderOperation.getOrderProductId(),
				orderOperation.getOrderId(), StoreKeyConstants.ORDER_PROD_STATUS_USING);
		if (orderOperation.getDeferDate()
				.compareTo(orderProd.getActualPendingEndDate()) <= 0 ) 
			throw new BusinessException(StoreConstants.CHK_ERR_90035);

		Long pendingCount = getPendingCount(orderProd.getActualPendingDate(), 
				orderOperation.getDeferDate());
		BigDecimal realPrice = getLeaseRealPrice(pendingCount, orderProd.getProductId());
		BigDecimal preLeaseAmount = getLeaseAmount(pendingCount, realPrice);
		
		BigDecimal paidAmount = calcLeaseOrderProductPaid(orderProd);
		GuestOrderInfo newGuestOrder = deferGuestOrder(order, orderOperation);
		OrderProduct newOrderProd = deferOrderProduct(newGuestOrder, pendingCount, 
				orderProd, orderOperation);

		orderOperation.setOrderOperId(UUID.randomUUID().toString());
		orderOperation.setAskErId(customerid);
		orderOperation.setAskOperCode(StoreKeyConstants.ORDER_OPERATION_ASK_DEFER);
		orderOperation.setAskTime(Utils.getChinaCurrentTime());
		
		if (paidAmount.compareTo(preLeaseAmount) >= 0) {
			//生成新订单商品状态为使用中，预付已付押金为0等。
			newOrderProd.setPaidAmount(new BigDecimal(0));
			newOrderProd.setPrePayAmount(new BigDecimal(0));
			newOrderProd.setStatusCode(StoreKeyConstants.ORDER_PROD_STATUS_USING);
			orderProductMapper.insertSelective(newOrderProd);

			//生成新订单状态为待评价
			newGuestOrder.setTotalAmount(new BigDecimal(0));
			newGuestOrder.setTotalLeaseAmount(new BigDecimal(0));
			newGuestOrder.setPaymentVoucher("");
			newGuestOrder.setStatusCode(StoreKeyConstants.ORDER_STATUS_WAIT_COMMENT);
			guestOrderInfoMapper.insertSelective(newGuestOrder);
			
			//更新旧订单商品状态为已续租。
			orderProd.setStatusCode(StoreKeyConstants.ORDER_PROD_STATUS_RELET);
			orderProductMapper.updateByPrimaryKeySelective(orderProd);
			
			//更新操作表(同意续租)
			orderOperation.setAnsComment("已续租成功!");
			orderOperation.setAnsErId(StoreKeyConstants.SYSTEM_EMP_ID);
			orderOperation.setAnsOperCode(StoreKeyConstants.ORDER_OPERATION_CONF_DEFER);
			orderOperation.setAnsTime(Utils.getChinaCurrentTime());
			orderOperation.setCalcAmount(new BigDecimal(0));
			orderOperationHistoryMapper.insertSelective(orderOperation);
			
			ret = "已续租成功!";
		} else {
			//生成新订单商品无状态，预付款为差额
			BigDecimal prePayAmount = preLeaseAmount.subtract(paidAmount);
			newOrderProd.setPrePayAmount(prePayAmount);
			orderProductMapper.insertSelective(newOrderProd);

			//生成新订单状态为未付款并保存应付款
			newGuestOrder.setTotalAmount(prePayAmount);
			newGuestOrder.setTotalLeaseAmount(prePayAmount);
			newGuestOrder.setStatusCode(StoreKeyConstants.ORDER_STATUS_UNPAID);
			guestOrderInfoMapper.insertSelective(newGuestOrder);

			//更新操作表(同意续租)
			orderOperation.setAnsComment("已生成缴纳续租费用的订单，请及时支付完成续租!");
			orderOperation.setCalcAmount(prePayAmount);
			orderOperationHistoryMapper.insertSelective(orderOperation);
			
			ret = "已生成缴纳续租费用的订单，请及时支付完成续租!";
		}
		return ret;
	}
	
	private GuestOrderInfo deferGuestOrder(GuestOrderInfo order, 
			OrderOperationHistory orderOperation) {
		GuestOrderInfo newGuestOrder = new GuestOrderInfo();
		newGuestOrder.setOrderId(UUID.randomUUID().toString());
		newGuestOrder.setCustomerId(order.getCustomerId());
		newGuestOrder.setCarriage(new BigDecimal(0));
		newGuestOrder.setService(new BigDecimal(0));
		newGuestOrder.setTotalBail(new BigDecimal(0));
		newGuestOrder.setCustAddressId(order.getCustAddressId());
		newGuestOrder.setCustInvoiceId(order.getCustInvoiceId());
		newGuestOrder.setIsOffered(Boolean.FALSE);
		newGuestOrder.setCustMessage(orderOperation.getAskComment());
		newGuestOrder.setCourierNumber(order.getCourierNumber());
		newGuestOrder.setPaymentCode(order.getPaymentCode());
		newGuestOrder.setRemark("续租订单");
		newGuestOrder.setCreateEmpId(StoreKeyConstants.SYSTEM_EMP_ID);
		newGuestOrder.setUpdateEmpId(StoreKeyConstants.SYSTEM_EMP_ID);
		return newGuestOrder;
	}
	
	private OrderProduct deferOrderProduct(GuestOrderInfo newGuestOrder, Long pendingCount,
			OrderProduct orderProd, OrderOperationHistory orderOperation) {
		OrderProduct newOrderProd = new OrderProduct();
		newOrderProd.setOrderProductId(UUID.randomUUID().toString());
		newOrderProd.setOrderId(newGuestOrder.getOrderId());
		newOrderProd.setProductId(orderProd.getProductId());
		newOrderProd.setIsDelete(Boolean.FALSE);
		newOrderProd.setRefOrderProductId(orderProd.getOrderProductId());
		newOrderProd.setRefTypeCode(StoreKeyConstants.REF_TYPE_DEFER);
		newOrderProd.setActualPendingDate(orderProd.getActualPendingDate());
		newOrderProd.setActualPendingEndDate(orderOperation.getDeferDate());
		newOrderProd.setReservePendingDate(orderProd.getActualPendingDate());
		newOrderProd.setReservePendingEndDate(orderOperation.getDeferDate());
		newOrderProd.setActualSendDate(orderProd.getActualSendDate());
		newOrderProd.setPendingCount(pendingCount);
		newOrderProd.setSaleCount(orderProd.getSaleCount());
		newOrderProd.setBail(new BigDecimal(0));
		newOrderProd.setRemark("续租订单商品");
		newOrderProd.setCreateEmpId(StoreKeyConstants.SYSTEM_EMP_ID);
		newOrderProd.setUpdateEmpId(StoreKeyConstants.SYSTEM_EMP_ID);
		return newOrderProd;
	}

	private BigDecimal calcLeaseOrderProductPaid(OrderProduct orderProd) {
		BigDecimal paid = orderProd.getPaidAmount();
		
		String refOrderProductId = orderProd.getRefOrderProductId();
		if (refOrderProductId== null) {
			return paid;
		} else {
			OrderProduct record = orderProductMapper.selectByPrimaryKey(refOrderProductId);
			return paid.add(calcLeaseOrderProductPaid(record));
		}
	}
	
	@Transactional
	public void rejectRecycleOrders(String empid, OrderOperationHistory orderOperation) {
		OrderOperationHistory operation = checkAskingOperation(
				orderOperation.getOrderOperId(), StoreKeyConstants.ORDER_OPERATION_ASK_END);
		OrderProduct orderProd = checkAskingOrderProduct(operation.getOrderProductId(),
				operation.getOrderId(), StoreKeyConstants.ORDER_PROD_STATUS_WAIT_RETURN);
		operation.setAnsErId(empid);
		operation.setAnsComment(orderOperation.getAnsComment());
		operation.setAnsOperCode(StoreKeyConstants.ORDER_OPERATION_REJECT_END);
		operation.setAnsTime(Utils.getChinaCurrentTime());
		orderOperationHistoryMapper.updateByPrimaryKey(operation);
		orderProd.setStatusCode(StoreKeyConstants.ORDER_PROD_STATUS_USING);
		orderProd.setUpdateEmpId(empid);
		orderProductMapper.updateByPrimaryKey(orderProd);
	}
	
	@Transactional
	public void agreeRecycleOrders(String empid, OrderOperationHistory orderOperation) {
		OrderOperationHistory operation = checkAskingOperation(
				orderOperation.getOrderOperId(), StoreKeyConstants.ORDER_OPERATION_ASK_END);
		OrderProduct orderProd = checkAskingOrderProduct(operation.getOrderProductId(),
				operation.getOrderId(), StoreKeyConstants.ORDER_PROD_STATUS_WAIT_RETURN);
		LeaseCalcAmountMsgData calcAmount = calcLeaseAmount(
				orderOperation.getOrderOperId(), orderOperation.getPendingEndDate());
		if (calcAmount.getAmount().compareTo(new BigDecimal(0)) < 0) 
			throw new BusinessException(StoreConstants.CHK_ERR_90032);
		
		operation.setAnsErId(empid);
		operation.setAnsComment(orderOperation.getAnsComment());
		operation.setAnsOperCode(StoreKeyConstants.ORDER_OPERATION_CONF_END);
		operation.setAnsTime(Utils.getChinaCurrentTime());
		operation.setCalcAmount(calcAmount.getAmount());
		operation.setPayRefundType(StoreKeyConstants.REFUND_TYPE);
		orderOperationHistoryMapper.updateByPrimaryKey(operation);
		
		ProductStock stock = new ProductStock();
		stock.setProductStockId(UUID.randomUUID().toString());
		stock.setProductId(orderProd.getProductId());
		stock.setQuantity(orderProd.getSaleCount());
		stock.setStockType(StoreKeyConstants.STOCK_TYPE_RECYCLE);
		productStockMapper.insertSelective(stock);
		
		//逐级退款
		refundLeaseOrderProduct(orderProd, empid, calcAmount.getAmount(), orderOperation.getOrderOperId());
	}
	
	private void refundLeaseOrderProduct(OrderProduct orderProd, String empid, BigDecimal calcAmount,
			String orderOperId) {
		orderProd.setUpdateEmpId(empid);
		if (orderProd.getPaidAmount().compareTo(new BigDecimal(0)) == 0 && 
				orderProd.getBail().compareTo(new BigDecimal(0)) == 0) {
			orderProd.setRefundAmount(new BigDecimal(0));
			orderProd.setRefundBail(new BigDecimal(0));
			orderProd.setStatusCode(StoreKeyConstants.ORDER_PROD_STATUS_REFUNDED);
		} else {
			if (calcAmount.subtract(orderProd.getBail()).compareTo(new BigDecimal(0)) >= 0) {
				orderProd.setRefundBail(orderProd.getBail());
				calcAmount = calcAmount.subtract(orderProd.getBail());
			} else {
				orderProd.setRefundBail(calcAmount);
				calcAmount = new BigDecimal(0);
			}
			if (calcAmount.subtract(orderProd.getPaidAmount()).compareTo(new BigDecimal(0)) >= 0) {
				orderProd.setRefundAmount(orderProd.getPaidAmount());
				calcAmount = calcAmount.subtract(orderProd.getPaidAmount());
			} else {
				orderProd.setRefundAmount(calcAmount);
				calcAmount = new BigDecimal(0);
			}
			orderProd.setStatusCode(StoreKeyConstants.ORDER_PROD_STATUS_WAIT_REFUND);
			
			BigDecimal refundTotalAmount = orderProd.getRefundAmount().add(orderProd.getRefundBail());
			if (refundTotalAmount.compareTo(new BigDecimal(0)) > 0) {
				String orderId = orderProd.getOrderId();
				GuestOrderInfo order = checkPaidGuestOrder(orderId);
				WxPayHistory pay = checkPaidHistory(order.getPaymentVoucher(), orderId);
				
				WxRefundHistory refund = new WxRefundHistory();
				refund.setOutRefundNo(Utils.getRefundNo());
				refund.setOrderOperId(orderOperId);
				refund.setOrderId(orderId);
				refund.setTransactionId(pay.getTransactionId());
				refund.setOutTradeNo(pay.getOutTradeNo());
				refund.setTotalFee(pay.getTotalFee());
				refund.setRefundFee(refundTotalAmount.multiply(new BigDecimal(100)).intValue());
				refund.setRefundStatus(StoreKeyConstants.REFUND_STATE_NOREFUND);
				refund.setWxApi("leaseEnd");
				wxRefundHistoryMapper.insertSelective(refund);
				
				orderProd.setRefundCode(refund.getOutRefundNo());
			}
			
		}
		orderProductMapper.updateByPrimaryKey(orderProd);
		
		String refOrderProductId = orderProd.getRefOrderProductId();
		if (refOrderProductId== null) return;
		OrderProduct record = orderProductMapper.selectByPrimaryKey(refOrderProductId);
		refundLeaseOrderProduct(record, empid, calcAmount, orderOperId);
	}
	
	private WxPayHistory checkPaidHistory(String outTradeNo, String orderId) {
		WxPayHistory pay = wxPayHistoryMapper.selectByPrimaryKey(outTradeNo);
		if (!orderId.equals(pay.getOrderId())) 
			throw new BusinessException(StoreConstants.CHK_ERR_90016);
		if (!StoreKeyConstants.WXPAY_API_RC_SUCCESS.equals(pay.getTradeState()) ||
				pay.getTransactionId() == null) 
			throw new BusinessException(StoreConstants.CHK_ERR_90020);
	
		return pay;
	}
	
	private GuestOrderInfo checkPaidGuestOrder(String orderId) {
		if (orderId == null)
			throw new BusinessException(StoreConstants.CHK_ERR_90016);
		GuestOrderInfo order = guestOrderInfoMapper.selectByPrimaryKey(orderId);
		if (order == null) 
			throw new BusinessException(StoreConstants.CHK_ERR_90016);
		if (!StoreKeyConstants.ORDER_STATUS_WAIT_COMMENT.equals(order.getStatusCode()) && 
				!StoreKeyConstants.ORDER_STATUS_COMMENT_CONFIRM.equals(order.getStatusCode()))
			throw new BusinessException(StoreConstants.CHK_ERR_90018);
		if (order.getPaymentVoucher() == null)
			throw new BusinessException(StoreConstants.CHK_ERR_90031);
		
		return order;
	} 
	
	private OrderOperationHistory checkAskingOperation(String orderOperId, String status) {
		if (orderOperId == null)
			throw new BusinessException(StoreConstants.CHK_ERR_90029);
		OrderOperationHistory operation = orderOperationHistoryMapper
				.selectByPrimaryKey(orderOperId);
		if (operation.getAnsOperCode() != null) 
			throw new BusinessException(StoreConstants.CHK_ERR_90030);
		if (!status.equals(operation.getAskOperCode()))
			throw new BusinessException(StoreConstants.CHK_ERR_90030);
		return operation;
	}
	
	private OrderProduct checkAskingOrderProduct(String orderProductId, String orderId, 
			String status) {
		if (orderProductId == null)
			throw new BusinessException(StoreConstants.CHK_ERR_90026);
		OrderProduct orderProd = orderProductMapper
				.selectByPrimaryKey(orderProductId);
		if (orderProd == null) 
			throw new BusinessException(StoreConstants.CHK_ERR_90023);
		if (!orderProd.getOrderId().equals(orderId))
			throw new BusinessException(StoreConstants.CHK_ERR_90023);
		if (orderProd.getStatusCode() == null) 
			throw new BusinessException(StoreConstants.CHK_ERR_90025);
		if (!status.equals(orderProd.getStatusCode()))
			throw new BusinessException(StoreConstants.CHK_ERR_90025);
		return orderProd;
	}
	
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
		statusLst.add(StoreKeyConstants.ORDER_STATUS_CHANGING);
		statusLst.add(StoreKeyConstants.ORDER_STATUS_RETURNING);
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

	public List<String> getOverDueOrderIds(Integer dueDays) {
		List<String> ret = new ArrayList<String>();
		List<OrderProduct> orderProds = getOverDueLeaseProd(dueDays);
		if (Utils.listNotNull(orderProds))
			for (OrderProduct orderProd : orderProds)
				if (!ret.contains(orderProd.getOrderId()))
					ret.add(orderProd.getOrderId());
		return ret;
	}
	
	public List<OrderProduct> getOverDueLeaseProd(Integer dueDays) {
		Date dueTime = Utils.addTimeFromCurrentTime(Utils.INTERVAL_TYPE_DAY, dueDays);
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
		
		askEndLease(StoreKeyConstants.SYSTEM_EMP_ID, orderOperation);
		
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
		CodeDictInfo cd = codeDictInfoMapper.selectByPrimaryKey(
				StoreKeyConstants.CODE_DICT_COMPANY_PHONE);
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
	public void askEndLeaseProdCust(String customerid, OrderOperationHistory orderOperation) {
		GuestOrderInfo order = checkPaidGuestOrder(orderOperation.getOrderId());
		if (!order.getCustomerId().equals(customerid)) 
			throw new BusinessException(StoreConstants.CHK_ERR_90017);
		
		if (orderOperation.getPendingEndDate() == null) 
			throw new BusinessException(StoreConstants.CHK_ERR_90021);
		if (Utils.addTimeFromCurrentTime(Utils.INTERVAL_TYPE_DAY, StoreKeyConstants.END_INTERVAL_DAYS)
				.compareTo(orderOperation.getPendingEndDate()) > 0 ) 
			throw new BusinessException(StoreConstants.CHK_ERR_90022);

		OrderProduct orderProd = checkAskingOrderProduct(orderOperation.getOrderProductId(),
				orderOperation.getOrderId(), StoreKeyConstants.ORDER_PROD_STATUS_USING);
		if (orderProd.getActualPendingEndDate().compareTo(orderOperation.getPendingEndDate()) < 0)
			throw new BusinessException(StoreConstants.CHK_ERR_90024);

		Long pendingCount = getPendingCount(orderProd.getActualPendingDate(), 
				orderOperation.getPendingEndDate());
		ProductInfo prod = productInfoMapper.selectByPrimaryKey(orderProd.getProductId());
		if (pendingCount < prod.getLeaseMinDays()) 
			throw new BusinessException(StoreConstants.CHK_ERR_90008);
		
		askEndLease(customerid, orderOperation);
	}
	
	private void askEndLease(String id, OrderOperationHistory orderOperation) {
		OrderOperationHistoryExample exam_ooh = new OrderOperationHistoryExample();
		exam_ooh.createCriteria().andOrderIdEqualTo(orderOperation.getOrderId())
								.andOrderProductIdEqualTo(orderOperation.getOrderProductId())
								.andAskOperCodeIsNotNull()
								.andAnsOperCodeIsNull();
		if (orderOperationHistoryMapper.countByExample(exam_ooh) > 0) 
			throw new BusinessException(StoreConstants.CHK_ERR_90028);
		
		OrderOperationHistory record = new OrderOperationHistory();
		record.setOrderOperId(UUID.randomUUID().toString());
		record.setOrderId(orderOperation.getOrderId());
		record.setOrderProductId(orderOperation.getOrderProductId());
		record.setPendingEndDate(orderOperation.getPendingEndDate());
		record.setAskOperCode(StoreKeyConstants.ORDER_OPERATION_ASK_END);
		record.setAskErId(id);
		record.setAskComment(orderOperation.getAskComment());
		record.setAskTime(Utils.getChinaCurrentTime());
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
		Date maxRecycle = DateUtils.addDays(operation.getPendingEndDate(), StoreKeyConstants.SRVC_INTERVAL_DAYS);
		Date minRecycle = DateUtils.addDays(operation.getPendingEndDate(), 0 - StoreKeyConstants.SRVC_INTERVAL_DAYS);
		if (maxRecycle.compareTo(recycleDate) < 0 || minRecycle.compareTo(recycleDate) > 0)
			throw new BusinessException(StoreConstants.CHK_ERR_90027);
		
		String orderProductId = operation.getOrderProductId();
		OrderProduct orderProd = orderProductMapper.selectByPrimaryKey(orderProductId);
		if (orderProd == null) 
			throw new BusinessException(StoreConstants.CHK_ERR_90023);
		if (orderProd.getStatusCode() == null) 
			throw new BusinessException(StoreConstants.CHK_ERR_90025);
		if (!StoreKeyConstants.ORDER_PROD_STATUS_WAIT_RETURN.equals(orderProd.getStatusCode())) 
			throw new BusinessException(StoreConstants.CHK_ERR_90025);
		if (recycleDate.compareTo(orderProd.getActualPendingEndDate()) > 0) 
			throw new BusinessException(StoreConstants.CHK_ERR_90024);
		
		ProductInfo prod = productInfoMapper.selectByPrimaryKey(orderProd.getProductId());
		
		LeaseCalcAmountMsgData ret = new LeaseCalcAmountMsgData();
		ret.setOrderProductId(orderProductId);
		BigDecimal amount = new BigDecimal(0);
		BigDecimal paid = calcLeaseOrderProductPaid(orderProd); 
		BigDecimal bail = prod != null?prod.getBail():new BigDecimal(0);
		ret.setBail(bail);
		ret.setPaid(paid);
		ret.setStartDate(orderProd.getActualPendingDate());
		ret.setOrginEndDate(orderProd.getActualPendingEndDate());
		ret.setRecycleDate(recycleDate);
		
		Long pendingCount = getPendingCount(orderProd.getActualPendingDate(), recycleDate);
		BigDecimal realPrice = getLeaseRealPrice(pendingCount, orderProd.getProductId());
		BigDecimal preLeaseAmount = getLeaseAmount(pendingCount, realPrice);
		amount = amount.add(paid).add(bail).subtract(preLeaseAmount);

		ret.setUnitPrice(realPrice);
		ret.setAmount(amount);
		ret.setPendingCount(pendingCount);
		if (amount.compareTo(new BigDecimal(0)) > 0) ret.setType("待退款");
		else ret.setType("待缴款");
		
		return ret;
	}
	private BigDecimal getLeaseAmount(Long pendingCount, BigDecimal realPrice) {
		return realPrice.multiply(new BigDecimal(pendingCount));
	}
	private BigDecimal getLeaseRealPrice(Long pendingCount, String prodId) {
		ProductPrice unitPrice = getUnitPrice(Boolean.TRUE, pendingCount, prodId);
		if (unitPrice == null) 
			throw new BusinessException(StoreConstants.CHK_ERR_90008);
		return unitPrice.getRealPrice();
	}
	private Long getPendingCount(Date startDate, Date endDate) {
		return TimeUnit.MILLISECONDS.toDays(endDate.getTime() - startDate.getTime());
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
		if (StoreKeyConstants.OPERATION_TYPE_CUSTOMER.equals(type) && 
				!order.getCustomerId().equals(id))
			throw new BusinessException(StoreConstants.CHK_ERR_90017);
		if (!StoreKeyConstants.ORDER_STATUS_WAIT_DELIVERY.equals(order.getStatusCode()) &&
				!StoreKeyConstants.ORDER_STATUS_CHANGING.equals(order.getStatusCode()) &&
				!StoreKeyConstants.ORDER_STATUS_RETURNING.equals(order.getStatusCode()))
			throw new BusinessException(StoreConstants.CHK_ERR_90018);
			
		GuestOrderInfo record = new GuestOrderInfo();
		record.setOrderId(orderid);
		if (StoreKeyConstants.ORDER_STATUS_WAIT_DELIVERY.equals(order.getStatusCode()))
			record.setStatusCode(StoreKeyConstants.ORDER_STATUS_WAIT_COMMENT);
		else
			record.setStatusCode(StoreKeyConstants.ORDER_STATUS_CLOSED);
		if (StoreKeyConstants.OPERATION_TYPE_MANAGEMENT.equals(type))
			record.setUpdateEmpId(id);
		guestOrderInfoMapper.updateByPrimaryKeySelective(record);
		
		if (StoreKeyConstants.ORDER_STATUS_WAIT_DELIVERY.equals(order.getStatusCode())) {
			OrderProductExample exam_op = new OrderProductExample();
			exam_op.createCriteria().andOrderIdEqualTo(orderid);
			List<OrderProduct> prods = orderProductMapper.selectByExample(exam_op);
			if (Utils.listNotNull(prods)) {
				for (OrderProduct prod : prods) {
					OrderProduct rec = new OrderProduct();
					rec.setOrderProductId(prod.getOrderProductId());
					rec.setStatusCode(StoreKeyConstants.ORDER_PROD_STATUS_USING);
					rec.setActualSendDate(Utils.getChinaCurrentTimeInDay());
					if (prod.getReservePendingDate() != null) {
						rec.setActualPendingDate(Utils.getChinaCurrentTimeInDay());
						rec.setActualPendingEndDate(DateUtils.addDays(
								rec.getActualPendingDate(), prod.getPendingCount().intValue()));
					}
					orderProductMapper.updateByPrimaryKeySelective(rec);
				}
			}
		} else if (StoreKeyConstants.ORDER_STATUS_RETURNING.equals(order.getStatusCode())) {
			//订单为退货中时，关联订单商品的状态改为待退款，生成退款操作历史。
			OrderProductExample exam_op = new OrderProductExample();
			exam_op.createCriteria().andOrderIdEqualTo(orderid);
			List<OrderProduct> prods = orderProductMapper.selectByExample(exam_op);
			if (Utils.listNotNull(prods)) {
				for (OrderProduct prod : prods) {
					OrderProduct rec = new OrderProduct();
					rec.setOrderProductId(prod.getOrderProductId());
					rec.setActualSendDate(Utils.getChinaCurrentTimeInDay());
					orderProductMapper.updateByPrimaryKeySelective(rec);
					
					OrderProduct refOrderProd = orderProductMapper.selectByPrimaryKey(prod.getRefOrderProductId());
					OrderOperationHistoryExample exam_ooh = new OrderOperationHistoryExample();
					exam_ooh.createCriteria().andOrderIdEqualTo(refOrderProd.getOrderId())
											.andOrderProductIdEqualTo(refOrderProd.getOrderProductId())
											.andAskOperCodeEqualTo(StoreKeyConstants.ORDER_OPERATION_ASK_RETURN)
											.andAnsOperCodeEqualTo(StoreKeyConstants.ORDER_OPERATION_CONF_RETURN);
					List<OrderOperationHistory> opers = orderOperationHistoryMapper.selectByExample(exam_ooh);
					if (Utils.listNotNull(opers)) {
						OrderOperationHistory oper = opers.get(0);
						Integer returnCnt = oper.getReturnCount();
						ProductPrice unitPrice = getUnitPrice(Boolean.FALSE, null, refOrderProd.getProductId());
						BigDecimal calcAmount = unitPrice.getRealPrice().multiply(new BigDecimal(returnCnt));
						if (StoreKeyConstants.OPERATION_TYPE_MANAGEMENT.equals(type))
							refundSalesOrderProduct(refOrderProd, id, calcAmount, oper.getOrderOperId());
						else
							refundSalesOrderProduct(refOrderProd, StoreKeyConstants.SYSTEM_EMP_ID, calcAmount, oper.getOrderOperId());
					}
					
					ProductStock stock = new ProductStock();
					stock.setProductStockId(UUID.randomUUID().toString());
					stock.setProductId(refOrderProd.getProductId());
					stock.setQuantity(refOrderProd.getSaleCount());
					stock.setStockType(StoreKeyConstants.STOCK_TYPE_RETURN);
					productStockMapper.insertSelective(stock);
				}
			}
		} else if (StoreKeyConstants.ORDER_STATUS_CHANGING.equals(order.getStatusCode())) {
			//订单为换货中时
			OrderProductExample exam_op = new OrderProductExample();
			exam_op.createCriteria().andOrderIdEqualTo(orderid);
			List<OrderProduct> prods = orderProductMapper.selectByExample(exam_op);
			if (Utils.listNotNull(prods)) {
				for (OrderProduct prod : prods) {
					OrderProduct rec = new OrderProduct();
					rec.setOrderProductId(prod.getOrderProductId());
					rec.setActualSendDate(Utils.getChinaCurrentTimeInDay());
					orderProductMapper.updateByPrimaryKeySelective(rec);
				}
			}
		}
		
		OrderOperationHistory operation = new OrderOperationHistory();
		operation.setOrderOperId(UUID.randomUUID().toString());
		operation.setAskOperCode(StoreKeyConstants.ORDER_OPERATION_CONFIRM);
		operation.setAskErId(id);
		operation.setAskTime(Utils.getChinaCurrentTime());
		operation.setOrderId(orderid);
		orderOperationHistoryMapper.insertSelective(operation);
	}
	

	private void refundSalesOrderProduct(OrderProduct orderProd, String empid, BigDecimal calcAmount,
			String orderOperId) {
		orderProd.setUpdateEmpId(empid);
		if (orderProd.getPaidAmount().compareTo(new BigDecimal(0)) == 0 && 
				orderProd.getBail().compareTo(new BigDecimal(0)) == 0) {
			orderProd.setRefundAmount(new BigDecimal(0));
			orderProd.setRefundBail(new BigDecimal(0));
			orderProd.setStatusCode(StoreKeyConstants.ORDER_PROD_STATUS_REFUNDED);
		} else {
			if (calcAmount.subtract(orderProd.getBail()).compareTo(new BigDecimal(0)) >= 0) {
				orderProd.setRefundBail(orderProd.getBail());
				calcAmount = calcAmount.subtract(orderProd.getBail());
			} else {
				orderProd.setRefundBail(calcAmount);
				calcAmount = new BigDecimal(0);
			}
			if (calcAmount.subtract(orderProd.getPaidAmount()).compareTo(new BigDecimal(0)) >= 0) {
				orderProd.setRefundAmount(orderProd.getPaidAmount());
				calcAmount = calcAmount.subtract(orderProd.getPaidAmount());
			} else {
				orderProd.setRefundAmount(calcAmount);
				calcAmount = new BigDecimal(0);
			}
			orderProd.setStatusCode(StoreKeyConstants.ORDER_PROD_STATUS_WAIT_REFUND);
			
			BigDecimal refundTotalAmount = orderProd.getRefundAmount().add(orderProd.getRefundBail());
			if (refundTotalAmount.compareTo(new BigDecimal(0)) > 0) {
				String orderId = orderProd.getOrderId();
				GuestOrderInfo order = checkPaidGuestOrder(orderId);
				WxPayHistory pay = checkPaidHistory(order.getPaymentVoucher(), orderId);
				
				WxRefundHistory refund = new WxRefundHistory();
				refund.setOutRefundNo(Utils.getRefundNo());
				refund.setOrderOperId(orderOperId);
				refund.setOrderId(orderId);
				refund.setTransactionId(pay.getTransactionId());
				refund.setOutTradeNo(pay.getOutTradeNo());
				refund.setTotalFee(pay.getTotalFee());
				refund.setRefundFee(refundTotalAmount.multiply(new BigDecimal(100)).intValue());
				refund.setRefundStatus(StoreKeyConstants.REFUND_STATE_NOREFUND);
				refund.setWxApi("salesReturn");
				wxRefundHistoryMapper.insertSelective(refund);
				
				orderProd.setRefundCode(refund.getOutRefundNo());
			}
		}
		orderProductMapper.updateByPrimaryKey(orderProd);
		
		String refOrderProductId = orderProd.getRefOrderProductId();
		if (refOrderProductId== null) return;
		OrderProduct record = orderProductMapper.selectByPrimaryKey(refOrderProductId);
		refundLeaseOrderProduct(record, empid, calcAmount, orderOperId);
	}
	
	@Transactional
	public void updateRefundHistory(WxRefundHistory refund, String orderOperId) {
		wxRefundHistoryMapper.updateByPrimaryKeySelective(refund);
		if (StoreKeyConstants.REFUND_STATE_SUCCESS.equals(refund.getRefundStatus())) {
			OrderOperationHistory operation = orderOperationHistoryMapper.selectByPrimaryKey(orderOperId);
			if (operation != null) {
				OrderProduct orderProd = orderProductMapper.selectByPrimaryKey(
						operation.getOrderProductId());
				if (orderProd != null) {
					if (!StoreKeyConstants.ORDER_PROD_STATUS_REFUND_FAILED.equals(
							orderProd.getStatusCode())) {
						orderProd.setStatusCode(StoreKeyConstants.ORDER_PROD_STATUS_REFUNDED);
						orderProductMapper.updateByPrimaryKeySelective(orderProd);
					}
				}
			}
		} else if (StoreKeyConstants.REFUND_STATE_CHANGE.equals(refund.getRefundStatus()) || 
				StoreKeyConstants.REFUND_STATE_CLOSE.equals(refund.getRefundStatus())) {
			OrderOperationHistory operation = orderOperationHistoryMapper.selectByPrimaryKey(orderOperId);
			if (operation != null) {
				OrderProduct orderProd = orderProductMapper.selectByPrimaryKey(
						operation.getOrderProductId());
				if (orderProd != null) {
					orderProd.setStatusCode(StoreKeyConstants.ORDER_PROD_STATUS_REFUNDED);
					orderProductMapper.updateByPrimaryKeySelective(orderProd);
				}
			}
		}
	}
	
	public List<WxRefundHistory> getProcessingRefundHistory() {
		WxRefundHistoryExample exam_wrh = new WxRefundHistoryExample();
		exam_wrh.createCriteria().andRefundStatusEqualTo(StoreKeyConstants.REFUND_STATE_PROCESSING);
		List<WxRefundHistory> ret = wxRefundHistoryMapper.selectByExample(exam_wrh);
		if (!Utils.listNotNull(ret)) ret = new ArrayList<WxRefundHistory>();
		return ret;
	}
	
	public WxRefundHistory queryRefund(WxRefundHistory refund, String appid, 
			String mch_id, String key) throws BusinessException, Exception {
		WxRefundHistory ret = new WxRefundHistory();
		QLHWXPayConfig config = new QLHWXPayConfig(null, appid, key, mch_id);
        WXPay wxpay = new WXPay(config);
        Map<String, String> data = new HashMap<String, String>();
        data.put("out_refund_no", refund.getOutRefundNo());
        Map<String, String> resp = wxpay.refundQuery(data);
        
        if (resp != null) {
        	ret.setOutRefundNo(refund.getOutRefundNo());
            ret.setReturnCode(resp.get("return_code"));
            ret.setReturnMsg(resp.get("return_msg"));
			ret.setWxApi("queryrefund");
            if (resp.containsKey("nonce_str")) ret.setNonceStr(resp.get("nonce_str"));
            if (resp.containsKey("sign")) ret.setSign(resp.get("sign"));
            if (resp.containsKey("result_code")) ret.setResultCode(resp.get("result_code"));
            if (resp.containsKey("err_code")) ret.setErrCode(resp.get("err_code"));
            if (resp.containsKey("err_code_des")) ret.setErrCodeDes(resp.get("err_code_des"));
            if (resp.containsKey("cash_fee")) ret.setCashFee(Integer.parseInt(resp.get("cash_fee")));
            if (resp.containsKey("refund_count")) 
            	ret.setRefundCount(Integer.parseInt(resp.get("refund_count")));
            if (resp.containsKey("refund_status_0"))
            	ret.setRefundStatus(resp.get("refund_status_0"));
            if (resp.containsKey("refund_success_time_0")) {
            	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            	ret.setSuccessTime((Date)sdf.parse(resp.get("refund_success_time_0")));
            }
            if (resp.containsKey("refund_recv_accout_0"))
            	ret.setRefundRecvAccout(resp.get("refund_recv_accout_0"));
            if (resp.containsKey("refund_account_0"))
            	ret.setRefundAccount(resp.get("refund_account_0"));
        }
        return ret;
	}
	
	public List<WxRefundHistory> getUnRefundHistory() {
		WxRefundHistoryExample exam_wrh = new WxRefundHistoryExample();
		exam_wrh.createCriteria().andRefundStatusEqualTo(StoreKeyConstants.REFUND_STATE_NOREFUND);
		List<WxRefundHistory> ret = wxRefundHistoryMapper.selectByExample(exam_wrh);
		if (!Utils.listNotNull(ret)) ret = new ArrayList<WxRefundHistory>();
		return ret;
	}
	
	@Transactional
	public void updateRefundHistory(WxRefundHistory refund) {
		wxRefundHistoryMapper.updateByPrimaryKeySelective(refund);
	}
	
	public WxRefundHistory applyRefund(WxRefundHistory refund, String appid, 
			String mch_id, String key) throws BusinessException, Exception {
		WxRefundHistory ret = new WxRefundHistory();
        ret.setOutRefundNo(refund.getOutRefundNo());
        
		QLHWXPayConfig config = new QLHWXPayConfig(StoreKeyConstants.CERT_PATH, appid, key, mch_id);
        WXPay wxpay = new WXPay(config);
        Map<String, String> data = new HashMap<String, String>();
        data.put("transaction_id", refund.getTransactionId());
        data.put("out_refund_no", refund.getOutRefundNo());
        data.put("total_fee", String.valueOf(refund.getTotalFee()));
        data.put("refund_fee", String.valueOf(refund.getRefundFee()));
        data.put("notify_url", "http://106.15.88.109/store/order/wxrefund/notify");
        Map<String, String> resp = wxpay.refund(data);
        
        if (resp != null) {
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
				String refType = "";
				order.setOrderId(hist.getOrderId());
				order.setPaymentVoucher(hist.getOutTradeNo());
				OrderProductExample exam_op = new OrderProductExample();
				exam_op.createCriteria().andOrderIdEqualTo(hist.getOrderId());
				List<OrderProduct> prods = orderProductMapper.selectByExample(exam_op);
				if (Utils.listNotNull(prods)) {
					for (OrderProduct prod : prods) {
						OrderProduct record = new OrderProduct();
						if (prod.getRefOrderProductId() != null) {
							if (StoreKeyConstants.REF_TYPE_DEFER.equals(prod.getRefTypeCode())) {
								//延期
								refType = StoreKeyConstants.REF_TYPE_DEFER;
								record.setStatusCode(StoreKeyConstants.ORDER_PROD_STATUS_USING);
								
								//订单商品
								OrderProduct refProd = new OrderProduct();
								refProd.setOrderProductId(prod.getRefOrderProductId());
								refProd.setStatusCode(StoreKeyConstants.ORDER_PROD_STATUS_RELET);
								orderProductMapper.updateByPrimaryKeySelective(refProd);
								
								//操作表
								OrderOperationHistoryExample exam_ooh = new OrderOperationHistoryExample();
								exam_ooh.createCriteria().andOrderProductIdEqualTo(prod.getRefOrderProductId());
								List<OrderOperationHistory> operLst = orderOperationHistoryMapper.selectByExample(exam_ooh);
								if (Utils.listNotNull(operLst)) {
									OrderOperationHistory orderOperation = operLst.get(0);
									orderOperation.setAnsComment("已续租成功!");
									orderOperation.setAnsErId(StoreKeyConstants.SYSTEM_EMP_ID);
									orderOperation.setAnsOperCode(StoreKeyConstants.ORDER_OPERATION_CONF_DEFER);
									orderOperation.setAnsTime(Utils.getChinaCurrentTime());
									orderOperationHistoryMapper.updateByPrimaryKeySelective(orderOperation);
								}
							} else if (StoreKeyConstants.REF_TYPE_CHANGE.equals(prod.getRefTypeCode())) {
								refType = StoreKeyConstants.REF_TYPE_CHANGE;
							} else if (StoreKeyConstants.REF_TYPE_RETURN.equals(prod.getRefTypeCode())) {
								refType = StoreKeyConstants.REF_TYPE_RETURN;
								//关联订单商品状态：待回收
								OrderProduct refProd = new OrderProduct();
								refProd.setOrderProductId(prod.getRefOrderProductId());
								refProd.setStatusCode(StoreKeyConstants.ORDER_PROD_STATUS_WAIT_RETURN);
								orderProductMapper.updateByPrimaryKeySelective(refProd);
							}
						}
						
						record.setOrderProductId(prod.getOrderProductId());
						record.setPaidAmount(prod.getPrePayAmount());
						orderProductMapper.updateByPrimaryKeySelective(record);
					}
				}
				if (StoreKeyConstants.REF_TYPE_DEFER.equals(refType))
					order.setStatusCode(StoreKeyConstants.ORDER_STATUS_WAIT_COMMENT);
				else if (StoreKeyConstants.REF_TYPE_CHANGE.equals(refType))
					order.setStatusCode(StoreKeyConstants.ORDER_STATUS_CHANGING);
				else if (StoreKeyConstants.REF_TYPE_RETURN.equals(refType))
					order.setStatusCode(StoreKeyConstants.ORDER_STATUS_RETURNING);					
				else
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
        data.put("notify_url", "http://106.15.88.109/store/order/wxpay/notify");
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
	public void cancelOrder(OrderOperationHistory operation, String operatorId, 
			Integer opertionType) throws BusinessException, Exception {
		GuestOrderInfo guestOrder = guestOrderInfoMapper.selectByPrimaryKey(
				operation.getOrderId());
		if (guestOrder == null) 
			throw new BusinessException(StoreConstants.CHK_ERR_90016);
		if (!guestOrder.getCustomerId().equals(operatorId) &&
				opertionType == StoreKeyConstants.OPERATION_TYPE_CUSTOMER)
			throw new BusinessException(StoreConstants.CHK_ERR_90017);
		String orderStatus = guestOrder.getStatusCode();
		if (!StoreKeyConstants.ORDER_STATUS_UNPAID.equals(orderStatus)) 
			throw new BusinessException(StoreConstants.CHK_ERR_90018);
	
		operation.setOrderOperId(UUID.randomUUID().toString());
		operation.setOrderProductId(null);
		operation.setDeferDate(null);
		operation.setPendingEndDate(null);
		operation.setReturnCount(null);
		orderOperationHistoryMapper.insertSelective(operation);

		GuestOrderInfo record = new GuestOrderInfo();
		record.setOrderId(operation.getOrderId());
		record.setStatusCode(StoreKeyConstants.ORDER_STATUS_CANCELED);
		if (opertionType == StoreKeyConstants.OPERATION_TYPE_MANAGEMENT)
			record.setUpdateEmpId(operatorId);
		guestOrderInfoMapper.updateByPrimaryKeySelective(record);
		
		OrderProductExample exam_op = new OrderProductExample();
		exam_op.createCriteria().andOrderIdEqualTo(operation.getOrderId());
		List<OrderProduct> orderProds = orderProductMapper.selectByExample(exam_op);
		if (Utils.listNotNull(orderProds))
			for (OrderProduct orderProd : orderProds) {
				ProductStock stock = new ProductStock();
				stock.setProductStockId(UUID.randomUUID().toString());
				stock.setProductId(orderProd.getProductId());
				stock.setQuantity(orderProd.getSaleCount());
				stock.setStockType(StoreKeyConstants.STOCK_TYPE_CANCEL);
				productStockMapper.insertSelective(stock);
			}
	}
	
	public Long countOrder(Integer lease) {
		return new Long(orderProductMapper.countOrderByLeaseFlag(lease));
	}
	
	public List<String> getCustAddress(String name, String telno) {
		List<String> ret = new ArrayList<String>();
		if (name == null && telno == null) return ret;
		CustomerAddressExample exam_ca = new CustomerAddressExample();
		exam_ca.createCriteria();
		if (name != null)
			exam_ca.getOredCriteria().get(0).andRecipientNameEqualTo(name);
		if (telno != null)
			exam_ca.getOredCriteria().get(0).andRecipientPhoneEqualTo(telno);
		List<CustomerAddress> addrs = customerAddressMapper.selectByExample(exam_ca);
		if (Utils.listNotNull(addrs)) 
			for (CustomerAddress addr : addrs)
				ret.add(addr.getCustAddressId());
		
		return ret;
	}
	
	public Long countGuestOrderList(String status, Date createDate, 
			List<String> custAddressIds, List<String> orderIds) {
		return new Long(guestOrderInfoMapper.countByExample(
				createGuestOrderInfoExample(status, createDate, custAddressIds, orderIds)));
	}
	
	public List<GuestOrderOverView> getGuestOrderList(String status, Date createDate, 
			List<String> custAddressIds, List<String> orderIds, Integer offset, Integer limit) {
		List<GuestOrderOverView> ret = new ArrayList<GuestOrderOverView>();
		List<GuestOrderInfo> guestOrderList = guestOrderInfoMapper.selectOnePageByExample(
				createGuestOrderInfoExample(status, createDate, custAddressIds, orderIds), offset, 
				limit, "update_time desc");
		if (Utils.listNotNull(guestOrderList)) {
			for (GuestOrderInfo guestOrder : guestOrderList) {
				ret.add(getGuestOrderOverView(guestOrder));
			}
		}
		return ret;
	}

	private GuestOrderInfoExample createGuestOrderInfoExample(String status, 
			Date createDate, List<String> custAddressIds, List<String> orderIds) {
		GuestOrderInfoExample exam_go = new GuestOrderInfoExample();
		exam_go.createCriteria();
		if (status != null)
			exam_go.getOredCriteria().get(0).andStatusCodeEqualTo(status);
		if (createDate != null)
			exam_go.getOredCriteria().get(0)
				.andCreateTimeGreaterThanOrEqualTo(createDate)
				.andCreateTimeLessThan(DateUtils.addDays(createDate, 1));
		if (Utils.listNotNull(custAddressIds))
			exam_go.getOredCriteria().get(0).andCustAddressIdIn(custAddressIds);
		if (Utils.listNotNull(orderIds))
			exam_go.getOredCriteria().get(0).andOrderIdIn(orderIds);
		
		return exam_go;
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
//					.subtract(orderProd.getPaidAmount())
					.add(orderProd.getPrePayAmount());
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
			orderProd.setRefOrderProductId(null);
			orderProd.setPaidAmount(null);
			orderProd.setRefundAmount(null);
			orderProd.setRefundBail(null);
			orderProd.setIsDelete(Boolean.FALSE);
			orderProd.setOrderId(order.getOrderId());
			orderProd.setOrderProductId(UUID.randomUUID().toString());
			orderProd.setCreateEmpId(StoreKeyConstants.SYSTEM_EMP_ID);
			orderProd.setUpdateEmpId(StoreKeyConstants.SYSTEM_EMP_ID);
			orderProductMapper.insert(orderProd);
			
			ProductStock stock = new ProductStock();
			stock.setProductStockId(UUID.randomUUID().toString());
			stock.setProductId(orderProd.getProductId());
			stock.setQuantity(0 - orderProd.getSaleCount());
			stock.setStockType(StoreKeyConstants.STOCK_TYPE_BUY);
			productStockMapper.insertSelective(stock);
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
//				orderProductOV.getOrderProduct().setRefundBail(new BigDecimal(0));
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
//				orderProductOV.getOrderProduct().setPaidAmount(new BigDecimal(0));
//				orderProductOV.getOrderProduct().setRefundAmount(new BigDecimal(0));
				orderProductOV.getOrderProduct().setProductId(shoppingTrolley.getProductId());
				orderProductOV.getOrderProduct().setReservePendingDate(shoppingTrolley.getReservePendingDate());
				orderProductOV.getOrderProduct().setReservePendingEndDate(shoppingTrolley.getReservePendingEndDate());
				orderProductOV.getOrderProduct().setPendingCount(shoppingTrolley.getPendingCount());
				orderProductOV.getOrderProduct().setSaleCount(shoppingTrolley.getSaleCount());
				if (ov.getGuestOrderInfo().getService().compareTo(prod.getService()) < 0) 
					ov.getGuestOrderInfo().setService(ov.getGuestOrderInfo().getService()
							.add((prod.getService())));
				if (ov.getGuestOrderInfo().getCarriage().compareTo(prod.getCarriage()) < 0) 
					ov.getGuestOrderInfo().setCarriage(prod.getCarriage());
				orderProductOV.setRealUnitPrice(realUnitPrice);
				orderProductOV.setIsLease(prod.getLeaseFlag());
				ret = ret.add(orderProductOV.getOrderProduct().getPrePayAmount())
//					.subtract(orderProductOV.getOrderProduct().getPaidAmount())
//					.subtract(orderProductOV.getOrderProduct().getRefundAmount())
//					.subtract(orderProductOV.getOrderProduct().getRefundBail())
					.add(orderProductOV.getOrderProduct().getBail());
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
	
	private Integer getProductStockQuantity(String prodID) {
		ProductStockExample exam_ps = new ProductStockExample();
		exam_ps.createCriteria().andProductIdEqualTo(prodID);
		return productStockMapper.sumByExample(exam_ps);
	}
}
