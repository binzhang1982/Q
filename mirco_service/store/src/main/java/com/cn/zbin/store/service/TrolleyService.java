package com.cn.zbin.store.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cn.zbin.store.bto.MsgData;
import com.cn.zbin.store.bto.ShoppingProductDetail;
import com.cn.zbin.store.bto.ShoppingTrolleyOverView;
import com.cn.zbin.store.dto.ProductImage;
import com.cn.zbin.store.dto.ProductImageExample;
import com.cn.zbin.store.dto.ProductInfo;
import com.cn.zbin.store.dto.ProductPrice;
import com.cn.zbin.store.dto.ProductPriceExample;
import com.cn.zbin.store.dto.ShoppingTrolleyInfo;
import com.cn.zbin.store.dto.ShoppingTrolleyInfoExample;
import com.cn.zbin.store.mapper.ProductImageMapper;
import com.cn.zbin.store.mapper.ProductInfoMapper;
import com.cn.zbin.store.mapper.ProductPriceMapper;
import com.cn.zbin.store.mapper.ShoppingTrolleyInfoMapper;
import com.cn.zbin.store.utils.StoreConstants;
import com.cn.zbin.store.utils.Utils;

@Service
public class TrolleyService {

	@Autowired
	private ShoppingTrolleyInfoMapper shoppingTrolleyInfoMapper;
	@Autowired
	private ProductInfoMapper productInfoMapper;
	@Autowired
	private ProductImageMapper productImageMapper;
	@Autowired
	private ProductPriceMapper productPriceMapper;
	
	public ShoppingTrolleyOverView getTrolleyList(String custid, String strScope) {
		ShoppingTrolleyOverView ret = new ShoppingTrolleyOverView();
		ret.setLeaseTrolley(new ArrayList<ShoppingProductDetail>());
		ret.setSellTrolley(new ArrayList<ShoppingProductDetail>());
		if (StringUtils.isBlank(strScope) || StoreConstants.PRODUCT_SCOPE_SELL.equals(strScope)) 
			setTrolleyList(Boolean.FALSE, custid, ret);
		if (StringUtils.isBlank(strScope) || StoreConstants.PRODUCT_SCOPE_LEASE.equals(strScope)) 
			setTrolleyList(Boolean.TRUE, custid, ret);
		return ret;
	}
	
	private void setTrolleyList(Boolean leaseFlag, String custid, ShoppingTrolleyOverView ret) {
		ShoppingTrolleyInfoExample exam_trolley;
		exam_trolley = new ShoppingTrolleyInfoExample();
		exam_trolley.createCriteria().andIsDeleteEqualTo(Boolean.FALSE)
									.andCustomerIdEqualTo(custid)
									.andLeaseFlagEqualTo(leaseFlag);
		List<ShoppingTrolleyInfo> trolleyList = shoppingTrolleyInfoMapper.selectByExample(exam_trolley);
		if (Utils.listNotNull(trolleyList)) {
			for (ShoppingTrolleyInfo trolley : trolleyList) {
				ShoppingProductDetail trolleyDetail = new ShoppingProductDetail();
				trolleyDetail.setTrolley(trolley);
				trolleyDetail.setProdInfo(
						productInfoMapper.selectByPrimaryKey(trolley.getProductId()));
				trolleyDetail.setUnitPrice(getUnitPrice(leaseFlag, trolley.getPendingCount(), trolley.getProductId()));
				trolleyDetail.setFrontCoverImage(getFrontCoverImage(trolley.getProductId()));
				if (leaseFlag) ret.getLeaseTrolley().add(trolleyDetail);
				else ret.getSellTrolley().add(trolleyDetail);
			}
		}
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
	
	@Transactional
	public String updateTrolley(ShoppingTrolleyInfo trolley) {
		Integer saleCnt = trolley.getSaleCount();
		String prodID = trolley.getProductId();
		String trolleyID = trolley.getTrolleyId();
		String errMsg = "";
		if (shoppingTrolleyInfoMapper.selectByPrimaryKey(trolleyID) == null) {
			errMsg = StoreConstants.CHK_ERR_90010;
		}
		ProductInfo product = productInfoMapper.selectByPrimaryKey(prodID);
		ShoppingTrolleyInfo record = new ShoppingTrolleyInfo();
		if (product != null) {
			if (saleCnt == null || saleCnt <= 0) {
				record.setTrolleyId(trolleyID);
				record.setSaleCount(0);
				record.setIsDelete(Boolean.TRUE);
				record.setDeleteCode(StoreConstants.TROLLEY_DEL_REASON_GUEST);
				shoppingTrolleyInfoMapper.updateByPrimaryKeySelective(record);
				errMsg = StoreConstants.CHK_ERR_90011;
			} else if (saleCnt > product.getStock()) {
				errMsg = StoreConstants.CHK_ERR_90002;
			} else {
				record = new ShoppingTrolleyInfo();
				record.setTrolleyId(trolleyID);
				record.setSaleCount(saleCnt);
				shoppingTrolleyInfoMapper.updateByPrimaryKeySelective(record);
			}
		} else {
			record = new ShoppingTrolleyInfo();
			record.setTrolleyId(trolleyID);
			record.setIsDelete(Boolean.TRUE);
			record.setDeleteCode(StoreConstants.TROLLEY_DEL_REASON_PROD);
			shoppingTrolleyInfoMapper.updateByPrimaryKeySelective(record);
			errMsg = StoreConstants.CHK_ERR_90001;
		}
		return errMsg;
	}
	
	@Transactional
	public String add2Trolley(List<ShoppingTrolleyInfo> trolleyList) {
		if (Utils.listNotNull(trolleyList)) {
			for (ShoppingTrolleyInfo trolleyBean : trolleyList) {
				if (trolleyBean.getReservePendingDate() != null && trolleyBean.getReservePendingEndDate() != null) {
					trolleyBean.setPendingCount(
							TimeUnit.MILLISECONDS.toDays(
									trolleyBean.getReservePendingEndDate().getTime()
									-trolleyBean.getReservePendingDate().getTime())); 
				} else {
					trolleyBean.setPendingCount(new Long(0));
				}
				
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
				if (trolleyBean.getCustomerId() == null) return StoreConstants.CHK_ERR_90004;

				trolleyBean.setIsDelete(Boolean.FALSE);
				trolleyBean.setTrolleyId(UUID.randomUUID().toString());
				shoppingTrolleyInfoMapper.insertSelective(trolleyBean);
			}
		}
		return "";
	}
}
