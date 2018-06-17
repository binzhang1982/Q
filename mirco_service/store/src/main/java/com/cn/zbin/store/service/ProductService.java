package com.cn.zbin.store.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.zbin.store.bto.ProductCategory;
import com.cn.zbin.store.bto.ProductOutline;
import com.cn.zbin.store.bto.ProductOverView;
import com.cn.zbin.store.dto.CodeDictInfo;
import com.cn.zbin.store.dto.CodeDictInfoExample;
import com.cn.zbin.store.dto.ProductInfo;
import com.cn.zbin.store.dto.ProductInfoExample;
import com.cn.zbin.store.dto.ProductPrice;
import com.cn.zbin.store.dto.ProductPriceExample;
import com.cn.zbin.store.mapper.CodeDictInfoMapper;
import com.cn.zbin.store.mapper.ProductCommentMapper;
import com.cn.zbin.store.mapper.ProductExtendMapper;
import com.cn.zbin.store.mapper.ProductImageMapper;
import com.cn.zbin.store.mapper.ProductInfoMapper;
import com.cn.zbin.store.mapper.ProductPriceMapper;
import com.cn.zbin.store.mapper.ProductServiceAreaMapper;
import com.cn.zbin.store.mapper.ProductViewHistoryMapper;
import com.cn.zbin.store.utils.StoreConstants;
import com.cn.zbin.store.utils.Utils;

@Service
public class ProductService {

	@Autowired
	private ProductInfoMapper productInfoMapper;
	@Autowired
	private ProductCommentMapper productCommentMapper;
	@Autowired
	private ProductExtendMapper productExtendMapper;
	@Autowired
	private ProductImageMapper productImageMapper;
	@Autowired
	private ProductPriceMapper productPriceMapper;
	@Autowired
	private ProductServiceAreaMapper productServiceAreaMapper;
	@Autowired
	private ProductViewHistoryMapper productViewHistoryMapper;
	@Autowired
	private CodeDictInfoMapper codeDictInfoMapper;
	
	public ProductOverView getProdOverviewList(String strSearch, String strScope, 
			String strCate, Integer offset, Integer limit) {
		ProductOverView ret = new ProductOverView();
		ret.setLeaseProdCate(new ArrayList<ProductCategory>());
		ret.setSellProdCate(new ArrayList<ProductCategory>());
		
		CodeDictInfoExample exam_cdi = new CodeDictInfoExample();
		exam_cdi.createCriteria().andCodecateEqualTo(StoreConstants.CODE_CATE_PDCT);
		if (strCate != null) exam_cdi.getOredCriteria().get(0).andDictcodeEqualTo(strCate);
		List<CodeDictInfo> prodCates = codeDictInfoMapper.selectByExample(exam_cdi);
		
		for (CodeDictInfo code : prodCates) {
			if (strScope == null ||
					StoreConstants.PRODUCT_SCOPE_LEASE.equals(strScope)) {
				ret.getLeaseProdCate().add(getProductCategory(code, Boolean.TRUE, 
						strSearch, offset, limit));
			}
			if (strScope == null ||
					StoreConstants.PRODUCT_SCOPE_SELL.equals(strScope)) {
				ret.getSellProdCate().add(getProductCategory(code, Boolean.FALSE, 
						strSearch, offset, limit));
			}
		}
		return ret;
	}
	
	private ProductCategory getProductCategory(CodeDictInfo code, Boolean leaseFlag,
			String strSearch, Integer offset, Integer limit) {
		ProductCategory prodCate = new ProductCategory();
		prodCate.setProductCategoryCode(code.getCodecate());
		prodCate.setProductCategoryName(code.getCodename());

		ProductInfoExample exam_pi = new ProductInfoExample();
		exam_pi.createCriteria().andLeaseFlagEqualTo(leaseFlag)
					.andProductCategoryCodeEqualTo(code.getDictcode())
					.andSaleStartTimeLessThanOrEqualTo(new Date())
					.andSaleEndTimeGreaterThanOrEqualTo(new Date())
					.andListingFlagEqualTo(Boolean.TRUE);
		if (strSearch != null) exam_pi.getOredCriteria().get(0)
					.andProductNameLike(strSearch);
		List<ProductInfo> pi_list = productInfoMapper.selectOnePageByExample(
					exam_pi, offset, limit, "sale_start_time desc");
		prodCate.setProdList(new ArrayList<ProductOutline>());
		ProductOutline prodOutLine;
		for (ProductInfo pi : pi_list) {
			prodOutLine = new ProductOutline();
			prodOutLine.setProdInfo(pi);
			prodOutLine.setMinProdPrice(getMinProductPrice(pi.getProductId()));
			prodCate.getProdList().add(prodOutLine);
		}
		return prodCate;
	}
	
	private ProductPrice getMinProductPrice(String prodID) {
		ProductPriceExample exam_pp = new ProductPriceExample();
		exam_pp.createCriteria().andProductIdEqualTo(prodID);
		exam_pp.setOrderByClause("real_price asc");
		List<ProductPrice> prodPrice = productPriceMapper.selectByExample(exam_pp);
		if (Utils.listNotNull(prodPrice)) {
			return prodPrice.get(0);
		} else {
			return new ProductPrice();
		}
	}
}
