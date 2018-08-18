package com.cn.zbin.store.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import com.cn.zbin.store.bto.MsgData;
import com.cn.zbin.store.bto.OrderProductOverView;
import com.cn.zbin.store.dto.CustomerAddress;
import com.cn.zbin.store.dto.CustomerInvoice;
import com.cn.zbin.store.dto.GuestOrderInfo;
import com.cn.zbin.store.dto.GuestOrderInfoExample;
import com.cn.zbin.store.dto.MasterCity;
import com.cn.zbin.store.dto.MasterProvince;
import com.cn.zbin.store.dto.OrderOperationHistory;
import com.cn.zbin.store.dto.OrderProduct;
import com.cn.zbin.store.dto.OrderProductExample;
import com.cn.zbin.store.dto.ProductImage;
import com.cn.zbin.store.dto.ProductImageExample;
import com.cn.zbin.store.dto.ProductInfo;
import com.cn.zbin.store.dto.ProductPrice;
import com.cn.zbin.store.dto.ProductPriceExample;
import com.cn.zbin.store.dto.ShoppingTrolleyInfo;
import com.cn.zbin.store.dto.ShoppingTrolleyInfoExample;
import com.cn.zbin.store.exception.BusinessException;
import com.cn.zbin.store.mapper.CustomerAddressMapper;
import com.cn.zbin.store.mapper.CustomerInvoiceMapper;
import com.cn.zbin.store.mapper.GuestOrderInfoMapper;
import com.cn.zbin.store.mapper.MasterCityMapper;
import com.cn.zbin.store.mapper.MasterProvinceMapper;
import com.cn.zbin.store.mapper.OrderOperationHistoryMapper;
import com.cn.zbin.store.mapper.OrderProductMapper;
import com.cn.zbin.store.mapper.ProductImageMapper;
import com.cn.zbin.store.mapper.ProductInfoMapper;
import com.cn.zbin.store.mapper.ProductPriceMapper;
import com.cn.zbin.store.mapper.ShoppingTrolleyInfoMapper;
import com.cn.zbin.store.utils.StoreConstants;
import com.cn.zbin.store.utils.StoreKeyConstants;
import com.cn.zbin.store.utils.Utils;

@Service
public class OrderService {
	@Autowired
	private ProductInfoMapper productInfoMapper;
	@Autowired
	private ProductPriceMapper productPriceMapper;
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
	public void operateOrder(OrderOperationHistory operation) 
			throws BusinessException, Exception {
		switch (operation.getOperateCode()) {
			case StoreKeyConstants.ORDER_OPERATION_CANCEL:
				GuestOrderInfo guestOrder = guestOrderInfoMapper.selectByPrimaryKey(
						operation.getOrderId());
				
				if (guestOrder == null) 
					throw new BusinessException(StoreConstants.CHK_ERR_90016);
				
				if (!guestOrder.getCustomerId().equals(guestOrder.getOrderId())) 
					throw new BusinessException(StoreConstants.CHK_ERR_90017);
				
				if (!StoreKeyConstants.ORDER_STATUS_UNPAID.equals(guestOrder.getStatusCode())) 
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
				record.setUpdateEmpId(operation.getOperatorId());
				guestOrderInfoMapper.updateByPrimaryKeySelective(record);
				break;
			default:
				throw new BusinessException(StoreConstants.CHK_ERR_90019);
		}
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
	public void insertGuestOrder(GuestOrderOverView orderView) 
			throws BusinessException, Exception {
		BigDecimal actualAmount = new BigDecimal(0);
		GuestOrderInfo order = orderView.getGuestOrderInfo();
		if (StringUtils.isBlank(order.getCustAddressId()))
			throw new BusinessException(StoreConstants.CHK_ERR_90012);
		
		BigDecimal totalAmount = order.getTotalAmount();
		actualAmount = actualAmount.add(order.getCarriage())
									.add(order.getService());
		order.setOrderId(UUID.randomUUID().toString());
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
