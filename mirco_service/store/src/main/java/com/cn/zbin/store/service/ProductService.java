package com.cn.zbin.store.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.zbin.store.bto.ProductCategory;
import com.cn.zbin.store.bto.ProductDetail;
import com.cn.zbin.store.bto.ProductOutline;
import com.cn.zbin.store.bto.ProductOverView;
import com.cn.zbin.store.dto.CodeDictInfo;
import com.cn.zbin.store.dto.CodeDictInfoExample;
import com.cn.zbin.store.dto.ProductComment;
import com.cn.zbin.store.dto.ProductCommentExample;
import com.cn.zbin.store.dto.ProductExtend;
import com.cn.zbin.store.dto.ProductExtendExample;
import com.cn.zbin.store.dto.ProductImage;
import com.cn.zbin.store.dto.ProductImageExample;
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
	
	public ProductDetail getProductDetail(String prodID) {
		ProductDetail ret = new ProductDetail();
		ret.setProductInfo(productInfoMapper.selectByPrimaryKey(prodID));
		if (ret.getProductInfo() != null) {
			ProductCommentExample exam_pc = new ProductCommentExample();
			exam_pc.createCriteria().andProductIdEqualTo(prodID);
			ret.setCommentCount(productCommentMapper.countByExample(exam_pc));
			exam_pc.setOrderByClause("update_time desc");
			List<ProductComment> commentList = productCommentMapper.selectByExample(exam_pc);
			if (Utils.listNotNull(commentList)) {
				ret.setLastestComment(commentList.get(0));
			}
			
			ProductPriceExample exam_pp = new ProductPriceExample();
			exam_pp.createCriteria().andProductIdEqualTo(prodID);
			List<ProductPrice> priceList = productPriceMapper.selectByExample(exam_pp);
			if (Utils.listNotNull(priceList)) {
				ret.setPriceList(priceList);
			} else {
				ret.setPriceList(new ArrayList<ProductPrice>());
			}
			
			ProductImageExample exam_pi = new ProductImageExample();
			exam_pi.createCriteria().andProductIdEqualTo(prodID);
			List<ProductImage> imageList = productImageMapper.selectByExample(exam_pi);
			if (Utils.listNotNull(imageList)) {
				ret.setImageList(imageList);
			} else {
				ret.setImageList(new ArrayList<ProductImage>());
			}
			
			ProductExtendExample exam_pe = new ProductExtendExample();
			exam_pe.createCriteria().andProductIdEqualTo(prodID);
			List<ProductExtend> extendList = productExtendMapper.selectByExample(exam_pe);
			if (Utils.listNotNull(extendList)) {
				ret.setExtendList(extendList);
			} else {
				ret.setExtendList(new ArrayList<ProductExtend>());
			}
		} else {
			
		}
		return ret;
	}
	
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
			prodOutLine.setFrontCoverImage(getFrontCoverImage(pi.getProductId()));
			prodCate.getProdList().add(prodOutLine);
		}
		return prodCate;
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
	
	private ProductPrice getMinProductPrice(String prodID) {
		ProductPriceExample exam_pp = new ProductPriceExample();
		exam_pp.createCriteria().andProductIdEqualTo(prodID);
		exam_pp.setOrderByClause("real_price asc");
		List<ProductPrice> priceList = productPriceMapper.selectByExample(exam_pp);
		if (Utils.listNotNull(priceList)) {
			return priceList.get(0);
		} else {
			return new ProductPrice();
		}
	}
}
