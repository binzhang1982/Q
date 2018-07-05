package com.cn.zbin.store.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.zbin.store.bto.ShoppingTrolleyOverView;
import com.cn.zbin.store.dto.ProductInfo;
import com.cn.zbin.store.dto.ShoppingTrolleyInfo;
import com.cn.zbin.store.dto.ShoppingTrolleyInfoExample;
import com.cn.zbin.store.mapper.ProductInfoMapper;
import com.cn.zbin.store.mapper.ShoppingTrolleyInfoMapper;
import com.cn.zbin.store.utils.StoreConstants;
import com.cn.zbin.store.utils.Utils;

@Service
public class TrolleyService {

	@Autowired
	private ProductInfoMapper productInfoMapper;
	@Autowired
	private ShoppingTrolleyInfoMapper shoppingTrolleyInfoMapper;
	
	public ShoppingTrolleyOverView getTrolleyList(String custid, String strScope) {
		ShoppingTrolleyOverView ret = new ShoppingTrolleyOverView();
		ret.setLeaseTrolley(new ArrayList<ShoppingTrolleyInfo>());
		ret.setSellTrolley(new ArrayList<ShoppingTrolleyInfo>());
		ShoppingTrolleyInfoExample exam_trolley;
		if (StringUtils.isBlank(strScope) || StoreConstants.PRODUCT_SCOPE_SELL.equals(strScope)) {
			exam_trolley = new ShoppingTrolleyInfoExample();
			exam_trolley.createCriteria().andIsDeleteEqualTo(Boolean.FALSE)
										.andCustomerIdEqualTo(custid)
										.andLeaseFlagEqualTo(Boolean.FALSE);
			List<ShoppingTrolleyInfo> trolleyList = shoppingTrolleyInfoMapper.selectByExample(exam_trolley);
			if (Utils.listNotNull(trolleyList)) ret.setSellTrolley(trolleyList);
		}
		if (StringUtils.isBlank(strScope) || StoreConstants.PRODUCT_SCOPE_LEASE.equals(strScope)) {
			exam_trolley = new ShoppingTrolleyInfoExample();
			exam_trolley.createCriteria().andIsDeleteEqualTo(Boolean.FALSE)
										.andCustomerIdEqualTo(custid)
										.andLeaseFlagEqualTo(Boolean.TRUE);
			List<ShoppingTrolleyInfo> trolleyList = shoppingTrolleyInfoMapper.selectByExample(exam_trolley);
			if (Utils.listNotNull(trolleyList)) ret.setLeaseTrolley(trolleyList);
		}
		return ret;
	}
	
	public String add2Trolley(ShoppingTrolleyInfo trolleyBean) {
		if (trolleyBean.getSaleCount() == null) return StoreConstants.CHK_ERR_90003;
		else if (trolleyBean.getSaleCount() == 0) return StoreConstants.CHK_ERR_90003;
		
		if (trolleyBean.getProductId() != null) {
			ProductInfo prod = productInfoMapper.selectByPrimaryKey(trolleyBean.getProductId());
			if (prod == null) return StoreConstants.CHK_ERR_90001; 
			else if (prod.getLeaseFlag() && trolleyBean.getSaleCount() > 1) return StoreConstants.CHK_ERR_90005;
			else if (prod.getLeaseFlag() && trolleyBean.getPendingCount() <= 0) return StoreConstants.CHK_ERR_90007;
			else if (prod.getLeaseFlag() && trolleyBean.getReservePendingDate() == null) return StoreConstants.CHK_ERR_90006;

			trolleyBean.setLeaseFlag(prod.getLeaseFlag());
		} else {
			return StoreConstants.CHK_ERR_90001;
		}
		if (trolleyBean.getCustomerId() == null) return StoreConstants.CHK_ERR_90004;

		trolleyBean.setIsDelete(Boolean.FALSE);
		trolleyBean.setTrolleyId(UUID.randomUUID().toString());
		shoppingTrolleyInfoMapper.insertSelective(trolleyBean);
		
		return "";
	}
}
