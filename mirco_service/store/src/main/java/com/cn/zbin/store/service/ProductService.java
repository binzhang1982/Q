package com.cn.zbin.store.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cn.zbin.store.bto.FavoriteProduct;
import com.cn.zbin.store.bto.ProductCategory;
import com.cn.zbin.store.bto.ProductCommentDetail;
import com.cn.zbin.store.bto.ProductCommentOverView;
import com.cn.zbin.store.bto.ProductDetail;
import com.cn.zbin.store.bto.ProductExtendDetail;
import com.cn.zbin.store.bto.ProductOutline;
import com.cn.zbin.store.bto.ProductOverView;
import com.cn.zbin.store.dto.CodeDictInfo;
import com.cn.zbin.store.dto.CodeDictInfoExample;
import com.cn.zbin.store.dto.CustomerInfo;
import com.cn.zbin.store.dto.GuestOrderInfo;
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
import com.cn.zbin.store.dto.ProductViewHistory;
import com.cn.zbin.store.dto.WeChatUserInfo;
import com.cn.zbin.store.mapper.CodeDictInfoMapper;
import com.cn.zbin.store.mapper.CustomerInfoMapper;
import com.cn.zbin.store.mapper.GuestOrderInfoMapper;
import com.cn.zbin.store.mapper.ProductCommentMapper;
import com.cn.zbin.store.mapper.ProductExtendMapper;
import com.cn.zbin.store.mapper.ProductImageMapper;
import com.cn.zbin.store.mapper.ProductInfoMapper;
import com.cn.zbin.store.mapper.ProductPriceMapper;
import com.cn.zbin.store.mapper.ProductServiceAreaMapper;
import com.cn.zbin.store.mapper.ProductViewHistoryMapper;
import com.cn.zbin.store.mapper.WeChatUserInfoMapper;
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
	@Autowired
	private GuestOrderInfoMapper guestOrderInfoMapper;
	@Autowired
	private CustomerInfoMapper customerInfoMapper;
	@Autowired
	private WeChatUserInfoMapper weChatUserInfoMapper;
	
	
	public ProductCommentOverView getProductCommentList(String prodID,
			Integer offset, Integer limit) {
		ProductCommentOverView ret = new ProductCommentOverView();
		ProductCommentExample exam_pc = new ProductCommentExample();
		exam_pc.createCriteria().andProductIdEqualTo(prodID);
		List<ProductComment> commentList = productCommentMapper.selectOnePageByExample(
				 exam_pc, offset, limit, "update_time desc");
		if (Utils.listNotNull(commentList)) {
			ret.setCommentList(new ArrayList<ProductCommentDetail>());
			ret.setCommentCount(commentList.size());
			BigDecimal sumCommentLevel = new BigDecimal(0);
			for(ProductComment comment : commentList) {
				ProductCommentDetail detail = new ProductCommentDetail();
				detail.setComment(comment);
				GuestOrderInfo order = guestOrderInfoMapper.selectByPrimaryKey(comment.getOrderId());
				if (order != null) {
					CustomerInfo cust = customerInfoMapper.selectByPrimaryKey(order.getCustomerId());
					if (cust != null) {
						WeChatUserInfo user = weChatUserInfoMapper.selectByPrimaryKey(cust.getRegisterId());
						if (user != null) {
							detail.setHeadImgurl(user.getHeadImgurl());
							detail.setNickName(user.getNickName());
						}
					}
				}
				ret.getCommentList().add(detail);
				sumCommentLevel = sumCommentLevel.add(comment.getCommentLevel());
			}
			ret.setAvgCommentLevel(sumCommentLevel.divide(new BigDecimal(commentList.size()),2,RoundingMode.HALF_UP));
		} else {
			ret.setAvgCommentLevel(new BigDecimal(0));
			ret.setCommentCount(0);
			ret.setCommentList(new ArrayList<ProductCommentDetail>());
		}
		return ret;
	}
	
	public List<FavoriteProduct> getViewHistoryFavorite(String customerId, Integer limit) {
		List<FavoriteProduct> ret = new ArrayList<FavoriteProduct>();
		List<ProductViewHistory> viewList = productViewHistoryMapper.selectOnePageFavorite(customerId, limit);
		if (Utils.listNotNull(viewList)) {
			for (ProductViewHistory view : viewList) {
				FavoriteProduct favorite = new FavoriteProduct();
				favorite.setProdInfo(productInfoMapper.selectByPrimaryKey(view.getProductId()));
				favorite.setMinProdPrice(getMinProductPrice(view.getProductId()));
				favorite.setFrontCoverImage(getFrontCoverImage(view.getProductId()));
				ret.add(favorite);
			}
		}
		return ret;
	}
	
	@Transactional
	public String addViewHistory(String prodID, String customerid) {
		String viewID = UUID.randomUUID().toString();
		ProductViewHistory viewHist = new ProductViewHistory();
		viewHist.setIsHistory(Boolean.FALSE);
		viewHist.setCustomerId(customerid);
		viewHist.setProductId(prodID);
		viewHist.setViewId(viewID);
		viewHist.setViewCount(1);
		productViewHistoryMapper.insert(viewHist);
		return viewID;
	}
	
	public ProductDetail getProductDetail(String prodID) {
		ProductDetail ret = new ProductDetail();
		ret.setProductInfo(productInfoMapper.selectByPrimaryKey(prodID));
		if (ret.getProductInfo() != null) {
			ProductCommentExample exam_pc = new ProductCommentExample();
			exam_pc.createCriteria().andProductIdEqualTo(prodID);
			ret.setCommentCount(productCommentMapper.countByExample(exam_pc));
			exam_pc.setOrderByClause("update_time desc");
			List<ProductComment> commentList = productCommentMapper.selectByExample(exam_pc);
			ret.setLastestComment(new ProductCommentDetail());
			if (Utils.listNotNull(commentList)) {
				ret.getLastestComment().setComment(commentList.get(0)); 
				GuestOrderInfo order = guestOrderInfoMapper.selectByPrimaryKey(commentList.get(0).getOrderId());
				if (order != null) {
					CustomerInfo cust = customerInfoMapper.selectByPrimaryKey(order.getCustomerId());
					if (cust != null) {
						WeChatUserInfo user = weChatUserInfoMapper.selectByPrimaryKey(cust.getRegisterId());
						if (user != null) {
							ret.getLastestComment().setHeadImgurl(user.getHeadImgurl());
							ret.getLastestComment().setNickName(user.getNickName());
						}
					}
				}
//				ret.setLastestComment(commentList.get(0));
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
			exam_pi.setOrderByClause("seq_no asc");
			List<ProductImage> imageList = productImageMapper.selectByExample(exam_pi);
			if (Utils.listNotNull(imageList)) {
				ret.setImageList(imageList);
			} else {
				ret.setImageList(new ArrayList<ProductImage>());
			}
			
			ProductExtendExample exam_pe = new ProductExtendExample();
			exam_pe.createCriteria().andProductIdEqualTo(prodID);
			List<ProductExtend> extendList = productExtendMapper.selectByExample(exam_pe);
			ret.setExtendList(new ArrayList<ProductExtendDetail>());
			if (Utils.listNotNull(extendList)) {
				ProductPriceExample exam_epp = null;
				for (ProductExtend extend : extendList) {
					ProductExtendDetail extendDetail = new ProductExtendDetail();
					extendDetail.setProdInfo(
							productInfoMapper.selectByPrimaryKey(extend.getPartId()));
					exam_epp = new ProductPriceExample();
					exam_epp.createCriteria().andProductIdEqualTo(extend.getPartId());
					List<ProductPrice> exPriceList = productPriceMapper.selectByExample(exam_epp);
					if (Utils.listNotNull(exPriceList)) {
						extendDetail.setProdPrice(exPriceList);
					} else {
						extendDetail.setProdPrice(new ArrayList<ProductPrice>());
					}
					extendDetail.setFrontCoverImage(getFrontCoverImage(extend.getPartId()));
					ret.getExtendList().add(extendDetail);
				}
			}
		} else {
			
		}
		return ret;
	}
	
	public ProductOverView getProdOverviewList(String strSearch, String strScope, 
			String strCate, Integer offset, Integer limit) {
		if (StringUtils.isBlank(strSearch)) strSearch = null;
		if (StringUtils.isBlank(strScope)) strScope = null;
		if (StringUtils.isBlank(strCate)) strCate = null;
		
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
		prodCate.setProductCategoryCode(code.getDictcode());
		prodCate.setProductCategoryName(code.getCodename());

		ProductInfoExample exam_pi = new ProductInfoExample();
		exam_pi.createCriteria().andLeaseFlagEqualTo(leaseFlag)
					.andProductCategoryCodeEqualTo(code.getDictcode())
					.andSaleStartTimeLessThanOrEqualTo(new Date())
					.andSaleEndTimeGreaterThanOrEqualTo(new Date())
					.andListingFlagEqualTo(Boolean.TRUE);
		if (strSearch != null) exam_pi.getOredCriteria().get(0)
					.andProductNameLike("%" + strSearch + "%");
		List<ProductInfo> pi_list = productInfoMapper.selectOnePageByExample(
					exam_pi, offset, limit, "sale_start_time desc");
		if (leaseFlag) {
			prodCate.setProdList2(new ArrayList<>());
			if (Utils.listNotNull(pi_list)) {
				List<ProductOutline> prodList = new ArrayList<ProductOutline>();
				ProductOutline prodOutLine;
				ProductInfo pi;
				for (int i = 0; i <= pi_list.size() - 1; i++) {
					if (i%2 == 0) {
						prodList = new ArrayList<ProductOutline>();
					}
					prodOutLine = new ProductOutline();
					pi = pi_list.get(i);
					prodOutLine.setProdInfo(pi);
					prodOutLine.setMinProdPrice(getMinProductPrice(pi.getProductId()));
					prodOutLine.setFrontCoverImage(getFrontCoverImage(pi.getProductId()));
					prodList.add(prodOutLine);
					if (i%2 != 0 ||	i == pi_list.size() - 1) {
						prodCate.getProdList2().add(prodList);
					}
				}
			}
		} else {
			prodCate.setProdList(new ArrayList<ProductOutline>());
			if (Utils.listNotNull(pi_list)) {
				ProductOutline prodOutLine;
				for (ProductInfo pi : pi_list) {
					prodOutLine = new ProductOutline();
					prodOutLine.setProdInfo(pi);
					prodOutLine.setMinProdPrice(getMinProductPrice(pi.getProductId()));
					prodOutLine.setFrontCoverImage(getFrontCoverImage(pi.getProductId()));
					prodCate.getProdList().add(prodOutLine);
				}
			}
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
