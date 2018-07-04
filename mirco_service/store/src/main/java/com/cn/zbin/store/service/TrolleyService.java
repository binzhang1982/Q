package com.cn.zbin.store.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.zbin.store.dto.ProductInfo;
import com.cn.zbin.store.dto.ShoppingTrolleyInfo;
import com.cn.zbin.store.mapper.ProductInfoMapper;
import com.cn.zbin.store.mapper.ShoppingTrolleyInfoMapper;
import com.cn.zbin.store.utils.StoreConstants;

@Service
public class TrolleyService {

	@Autowired
	private ProductInfoMapper productInfoMapper;
	@Autowired
	private ShoppingTrolleyInfoMapper shoppingTrolleyInfoMapper;
	
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
