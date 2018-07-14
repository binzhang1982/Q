package com.cn.zbin.store.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.zbin.store.bto.GuestOrderOverView;
import com.cn.zbin.store.bto.MsgData;
import com.cn.zbin.store.bto.OrderProductOverView;
import com.cn.zbin.store.dto.GuestOrderInfo;
import com.cn.zbin.store.dto.OrderProduct;
import com.cn.zbin.store.dto.ProductInfo;
import com.cn.zbin.store.dto.ProductPrice;
import com.cn.zbin.store.dto.ProductPriceExample;
import com.cn.zbin.store.dto.ShoppingTrolleyInfo;
import com.cn.zbin.store.dto.ShoppingTrolleyInfoExample;
import com.cn.zbin.store.mapper.GuestOrderInfoMapper;
import com.cn.zbin.store.mapper.OrderProductMapper;
import com.cn.zbin.store.mapper.ProductInfoMapper;
import com.cn.zbin.store.mapper.ProductPriceMapper;
import com.cn.zbin.store.mapper.ShoppingTrolleyInfoMapper;
import com.cn.zbin.store.utils.StoreConstants;
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
	
	public String savaGuestOrder(GuestOrderOverView order) {
		return "";
	}
	
	public GuestOrderOverView initGuestOrder(String type, String custid,
			List<ShoppingTrolleyInfo> trolleyList) {
		if (StoreConstants.ORDER_TYPE_GUEST.equals(type)) {
			return createGuestOrderByGuest(trolleyList, custid);
		} else if (StoreConstants.ORDER_TYPE_TROLLEY.equals(type)) {
			return createGuestOrderByTrolley(trolleyList, custid);
		} else {
			return new GuestOrderOverView();
		}
	}
	
	private GuestOrderOverView createGuestOrderByGuest(
			List<ShoppingTrolleyInfo> trolleyList, String custid) {
		GuestOrderOverView ret = new GuestOrderOverView();
		ret.setGuestOrderInfo(new GuestOrderInfo());
		ret.getGuestOrderInfo().setCustomerId(custid);
		ret.getGuestOrderInfo().setCarriage(new BigDecimal(0));
		ret.getGuestOrderInfo().setService(new BigDecimal(0));
		ret.setOrderProductList(new ArrayList<OrderProductOverView>());
		
		BigDecimal totalPayAmount = new BigDecimal(0);
		for (ShoppingTrolleyInfo shoppingTrolley : trolleyList) {
			//需要check
			shoppingTrolley.setCustomerId(custid);
			String msg = checkTrolley(shoppingTrolley);
			if (!StringUtils.isBlank(msg)) {
				ret.setStatus(MsgData.status_ng);
				ret.setMessage(msg);
				ret.setGuestOrderInfo(new GuestOrderInfo());
				ret.setOrderProductList(new ArrayList<OrderProductOverView>());
				break;
			} else {
				totalPayAmount = totalPayAmount.add(createOverView(ret, shoppingTrolley));
			}
		}
		
		if (ret.getStatus() != MsgData.status_ng) {
			if (ret.getGuestOrderInfo().getService() != null) ret.getGuestOrderInfo().setCarriage(new BigDecimal(0));
			totalPayAmount = totalPayAmount.add(ret.getGuestOrderInfo().getCarriage())
						.add(ret.getGuestOrderInfo().getService());
			ret.getGuestOrderInfo().setTotalAmount(totalPayAmount);	
		}	
		return ret;
	}
	
	private GuestOrderOverView createGuestOrderByTrolley(
			List<ShoppingTrolleyInfo> trolleyList, String custid) {
		GuestOrderOverView ret = new GuestOrderOverView();
		ret.setGuestOrderInfo(new GuestOrderInfo());
		ret.getGuestOrderInfo().setCustomerId(custid);
		ret.getGuestOrderInfo().setCarriage(new BigDecimal(0));
		ret.getGuestOrderInfo().setService(new BigDecimal(0));
		ret.setOrderProductList(new ArrayList<OrderProductOverView>());

		BigDecimal totalPayAmount = new BigDecimal(0);
		ShoppingTrolleyInfoExample exam_trolley;
		for (ShoppingTrolleyInfo trolley : trolleyList) {
			exam_trolley = new ShoppingTrolleyInfoExample();
			exam_trolley.createCriteria().andTrolleyIdEqualTo(trolley.getTrolleyId())
										.andCustomerIdEqualTo(custid)
										.andIsDeleteEqualTo(Boolean.FALSE);
			List<ShoppingTrolleyInfo> shoppingTrolleyLst = shoppingTrolleyInfoMapper.selectByExample(exam_trolley);
			if (Utils.listNotNull(shoppingTrolleyLst)) {
				ShoppingTrolleyInfo shoppingTrolley = shoppingTrolleyLst.get(0);
				totalPayAmount = totalPayAmount.add(createOverView(ret, shoppingTrolley));
				deleteShoppingTrolley(shoppingTrolley);
			}
		}
		
		if (ret.getGuestOrderInfo().getService().compareTo(new BigDecimal(0)) > 0) ret.getGuestOrderInfo().setCarriage(new BigDecimal(0));
		totalPayAmount = totalPayAmount.add(ret.getGuestOrderInfo().getCarriage())
				.add(ret.getGuestOrderInfo().getService());
		ret.getGuestOrderInfo().setTotalAmount(totalPayAmount);
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
			else if (prod.getLeaseFlag() && trolleyBean.getPendingCount() <= 0) return StoreConstants.CHK_ERR_90007;
			else if (prod.getLeaseFlag() && trolleyBean.getPendingCount() < prod.getLeaseMinDays()) return StoreConstants.CHK_ERR_90008;
			else if (prod.getLeaseFlag() && trolleyBean.getReservePendingDate() == null) return StoreConstants.CHK_ERR_90006;

			trolleyBean.setLeaseFlag(prod.getLeaseFlag());
		} else {
			return StoreConstants.CHK_ERR_90001;
		}

		return ret;
	}
	
	private void deleteShoppingTrolley(ShoppingTrolleyInfo shoppingTrolley) {
		shoppingTrolley.setIsDelete(Boolean.TRUE);
		shoppingTrolley.setDeleteCode(StoreConstants.TROLLEY_DEL_REASON_ORDER);
		shoppingTrolleyInfoMapper.updateByPrimaryKeySelective(shoppingTrolley);
	}
	
	private BigDecimal createOverView(GuestOrderOverView ov, ShoppingTrolleyInfo shoppingTrolley) {
		BigDecimal ret = new BigDecimal(0);
		ProductInfo prod = productInfoMapper.selectByPrimaryKey(shoppingTrolley.getProductId());
		if (prod != null) {
			ProductPriceExample exam_pp = new ProductPriceExample();
			BigDecimal realUnitPrice = new BigDecimal(0);
			if (prod.getLeaseFlag()) {
				exam_pp.createCriteria().andDaysGreaterThanOrEqualTo(shoppingTrolley.getPendingCount())
										.andProductIdEqualTo(prod.getProductId());
				exam_pp.setOrderByClause("days asc");
				List<ProductPrice> prodPriceLst = productPriceMapper.selectByExample(exam_pp);
				if (Utils.listNotNull(prodPriceLst)) {
					realUnitPrice = prodPriceLst.get(0).getRealPrice();
				}
			} else {
				exam_pp.createCriteria().andProductIdEqualTo(prod.getProductId());
				List<ProductPrice> prodPriceLst = productPriceMapper.selectByExample(exam_pp);
				if (Utils.listNotNull(prodPriceLst)) {
					realUnitPrice = prodPriceLst.get(0).getRealPrice();
				}
			}
			
			if (realUnitPrice.compareTo(new BigDecimal(0)) > 0) {
				OrderProductOverView orderProductOV = new OrderProductOverView();
				orderProductOV.setOrderProduct(new OrderProduct());
				orderProductOV.getOrderProduct().setBail(prod.getBail()==null?new BigDecimal(0):prod.getBail());
				orderProductOV.getOrderProduct().setRefundBail(new BigDecimal(0));
				if (prod.getLeaseFlag()) {
					orderProductOV.getOrderProduct().setPrePayAmount(
							realUnitPrice.multiply(new BigDecimal(shoppingTrolley.getPendingCount())));
				} else {
					orderProductOV.getOrderProduct().setPrePayAmount(
							realUnitPrice.multiply(new BigDecimal(shoppingTrolley.getSaleCount())));
				}
				orderProductOV.getOrderProduct().setPaidAmount(new BigDecimal(0));
				orderProductOV.getOrderProduct().setRefundAmount(new BigDecimal(0));
				orderProductOV.getOrderProduct().setProductId(shoppingTrolley.getProductId());
				orderProductOV.getOrderProduct().setReservePendingDate(shoppingTrolley.getReservePendingDate());
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
		
		return ret;
	}
	
}
