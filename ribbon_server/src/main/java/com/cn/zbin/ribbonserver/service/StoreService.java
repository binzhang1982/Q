package com.cn.zbin.ribbonserver.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cn.zbin.ribbonserver.utils.RibbonKeyConstants;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class StoreService {
    @Autowired
    RestTemplate restTemplate;
    
    @HystrixCommand(fallbackMethod = "getSlideListError")
    public String getSlideList() {
    	return restTemplate.getForObject("http://SERVICE-STORE/slide/list", String.class);
    }
    public String getSlideListError() {
    	return "failed";
    }

    @HystrixCommand(fallbackMethod = "getProductListError")
    public String getProductList(String strSearch, String strScope, 
    		String strCate, Integer offset, Integer limit) {
    	Map<String, Object> uriVariables = new HashMap<String, Object>();
    	uriVariables.put("search", strSearch != null ? strSearch : "");
    	uriVariables.put("scope", strScope != null ? strScope : "");
    	uriVariables.put("cate", strCate != null ? strCate : "");
    	uriVariables.put("offset", offset != null ? offset : "");
    	uriVariables.put("limit", limit != null ? limit : "");
    	
    	String url = "http://SERVICE-STORE/prod/list"
    				+ "?search={search}"
    				+ "&scope={scope}"
    				+ "&cate={cate}"
    				+ "&offset={offset}"
    				+ "&limit={limit}";
    	return restTemplate.getForObject(url, String.class, uriVariables);
    }
    public String getProductListError(String strSearch, String strScope, 
    		String strCate,Integer offset, Integer limit) {
    	return "failed";
    }
    
    @HystrixCommand(fallbackMethod = "getProductDetailError")
    public String getProductDetail(String prodID) {
    	Map<String, Object> uriVariables = new HashMap<String, Object>();
    	uriVariables.put("prodID", prodID);
    	String url = "http://SERVICE-STORE/prod/detail/{prodID}";
    	return restTemplate.getForObject(url, String.class, uriVariables);
    }
    public String getProductDetailError(String prodID) {
    	return "failed";
    }

    @HystrixCommand(fallbackMethod = "addProductViewHistoryError")
    public String addProductViewHistory(String prodID, String customerid) {
    	Map<String, Object> uriVariables = new HashMap<String, Object>();
    	uriVariables.put("prodID", prodID);
    	uriVariables.put("customerid", customerid);
    	
    	String url = "http://SERVICE-STORE/prod/viewhist/{prodID}?customerid={customerid}";
    	return restTemplate.postForObject(url, null, String.class, uriVariables);
    }
    public String addProductViewHistoryError(String prodID, String openid) {
    	return "failed";
    }

    @HystrixCommand(fallbackMethod = "getProductFavoriteError")
    public String getProductFavorite(String customerid, Integer limit) {
    	Map<String, Object> uriVariables = new HashMap<String, Object>();
    	uriVariables.put("customerid", customerid);
    	uriVariables.put("limit", limit != null ? limit : "");
    	
    	String url = "http://SERVICE-STORE/prod/favorite"
    				+ "?customerid={customerid}"
    				+ "&limit={limit}";
    	return restTemplate.getForObject(url, String.class, uriVariables);
    }
    public String getProductFavoriteError(String openid, Integer limit) {
    	return "failed";
    }

    @HystrixCommand(fallbackMethod = "getProductCommentError")
    public String getProductComment(String prodID, Integer offset, Integer limit) {
    	Map<String, Object> uriVariables = new HashMap<String, Object>();
    	uriVariables.put("prodID", prodID);
    	uriVariables.put("offset", offset != null ? offset : "");
    	uriVariables.put("limit", limit != null ? limit : "");
    	
    	String url = "http://SERVICE-STORE/prod/comment"
    				+ "?prodid={prodID}"
    				+ "&offset={offset}"
    				+ "&limit={limit}";
    	return restTemplate.getForObject(url, String.class, uriVariables);
    }
    public String getProductCommentError(String prodID, Integer offset, Integer limit) {
    	return "failed";
    }

    @HystrixCommand(fallbackMethod = "getTrolleyListError")
    public String getTrolleyList(String custid, String strScope) {
    	Map<String, Object> uriVariables = new HashMap<String, Object>();
    	uriVariables.put("customerid", custid);
    	uriVariables.put("scope", strScope != null ? strScope : "");
    	
    	String url = "http://SERVICE-STORE/trolley/list"
    				+ "?customerid={customerid}"
    				+ "&scope={scope}";
    	return restTemplate.getForObject(url, String.class, uriVariables);
    }
    public String getTrolleyListError(String custid, String strScope) {
    	return "failed";
    }

    @HystrixCommand(fallbackMethod = "add2TrolleyError")
    public String add2Trolley(String bean) {
    	String url = "http://SERVICE-STORE/trolley";
        HttpHeaders headers =new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        HttpEntity<String> request = new HttpEntity<String>(bean, headers);
    	return restTemplate.postForObject(url, request, String.class);
    }
    public String add2TrolleyError(String bean) {
    	return "failed";
    }

    @HystrixCommand(fallbackMethod = "updateTrolleyError")
    public String updateTrolley(String bean) {
    	String url = "http://SERVICE-STORE/trolley/salecount";
        HttpHeaders headers =new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        HttpEntity<String> request = new HttpEntity<String>(bean, headers);
    	return restTemplate.postForObject(url, request, String.class);
    }
    public String updateTrolleyError(String bean) {
    	return "failed";
    }

    @HystrixCommand(fallbackMethod = "initOrderError")
    public String initOrder(String custid, String type, String bean) {
    	Map<String, Object> uriVariables = new HashMap<String, Object>();
    	uriVariables.put("customerid", custid);
    	uriVariables.put("type", type);
    	
    	String url = "http://SERVICE-STORE/order/init"
    				+ "?customerid={customerid}"
    				+ "&type={type}";
        HttpHeaders headers =new HttpHeaders();
        MediaType mtype = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(mtype);
        HttpEntity<String> request = new HttpEntity<String>(bean, headers);
    	return restTemplate.postForObject(url, request, String.class, uriVariables);
    }
    public String initOrderError(String custid, String type, String bean) {
    	return "failed";
    }

    @HystrixCommand(fallbackMethod = "insertOrderError")
    public String insertOrder(String bean) {
    	String url = "http://SERVICE-STORE/order/create";
        HttpHeaders headers =new HttpHeaders();
        MediaType mtype = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(mtype);
        HttpEntity<String> request = new HttpEntity<String>(bean, headers);
    	return restTemplate.postForObject(url, request, String.class);
    }
    public String insertOrderError(String bean) {
    	return "failed";
    }

    @HystrixCommand(fallbackMethod = "getGuestOrderListError")
    public String getGuestOrderList(String customerid,
			String status, Integer offset, Integer limit) {
    	Map<String, Object> uriVariables = new HashMap<String, Object>();
    	uriVariables.put("customerid", customerid);
    	uriVariables.put("status", status != null ? status : "");
    	uriVariables.put("offset", offset != null ? offset : "");
    	uriVariables.put("limit", limit != null ? limit : "");
    	
    	String url = "http://SERVICE-STORE/order/list"
    				+ "?customerid={customerid}"
    				+ "&status={status}"
    				+ "&offset={offset}"
    				+ "&limit={limit}";
    	return restTemplate.getForObject(url, String.class, uriVariables);
    }
    public String getGuestOrderListError(String customerid,
			String status, Integer offset, Integer limit) {
    	return "failed";
    }

    @HystrixCommand(fallbackMethod = "getGuestOrderError")
    public String getGuestOrder(String customerid, String orderid) {
    	Map<String, Object> uriVariables = new HashMap<String, Object>();
    	uriVariables.put("customerid", customerid);
    	uriVariables.put("orderid", orderid);
    	
    	String url = "http://SERVICE-STORE/order"
    				+ "?customerid={customerid}"
    				+ "&orderid={orderid}";
    	return restTemplate.getForObject(url, String.class, uriVariables);
    }
    public String getGuestOrderError(String customerid, String orderid) {
    	return "failed";
    }

    @HystrixCommand(fallbackMethod = "calcPendingCountError")
    public String calcPendingCount(String pendingStartDate, String pendingEndDate) {
    	Map<String, Object> uriVariables = new HashMap<String, Object>();
    	uriVariables.put("startdate", pendingStartDate);
    	uriVariables.put("enddate", pendingEndDate);
    	
    	String url = "http://SERVICE-STORE/prod/lease/calc?startdate={startdate}"
    				+ "&enddate={enddate}";
    	return restTemplate.getForObject(url, String.class, uriVariables);
    }
    public String calcPendingCountError(String pendingStartDate, String pendingEndDate) {
    	return "failed";
    }
    
    @HystrixCommand(fallbackMethod = "cancelOrderByCustomerError")
    public String cancelOrderByCustomer(String customerid, String bean) {
    	Map<String, Object> uriVariables = new HashMap<String, Object>();
    	uriVariables.put("customerid", customerid);
    	String url = "http://SERVICE-STORE/order/cancel/cust?customerid={customerid}";
    	
        HttpHeaders headers =new HttpHeaders();
        MediaType mtype = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(mtype);
        HttpEntity<String> request = new HttpEntity<String>(bean, headers);
    	return restTemplate.postForObject(url, request, String.class, uriVariables);
    }
    public String cancelOrderByCustomerError(String customerid, String bean) {
    	return "failed";
    }

    @HystrixCommand(fallbackMethod = "unifiedOrderPayError")
    public String unifiedOrderPay(String orderid, String customerid, String ip) {
    	Map<String, Object> uriVariables = new HashMap<String, Object>();
    	uriVariables.put("orderid", orderid);
    	uriVariables.put("customerid", customerid);
    	uriVariables.put("ip", ip);
    	uriVariables.put("appid", RibbonKeyConstants.APPID);
    	
    	String url = "http://SERVICE-STORE/order/pay/unified?orderid={orderid}"
    				+ "&customerid={customerid}&ip={ip}&appid={appid}";
    	return restTemplate.getForObject(url, String.class, uriVariables);
    }
    public String unifiedOrderPayError(String orderid, String customerid, String ip) {
    	return "failed";
    }

    @HystrixCommand(fallbackMethod = "confirmDeliveryError")
    public String confirmDelivery(String orderid, String customerid) {
    	Map<String, Object> uriVariables = new HashMap<String, Object>();
    	uriVariables.put("orderid", orderid);
    	uriVariables.put("id", customerid);
    	uriVariables.put("type", 1);

        return restTemplate.postForObject("http://SERVICE-STORE/order/delivery/confirm" 
				+ "?orderid={orderid}&id={id}&type={type}", null, String.class, 
				uriVariables);
    }
    public String confirmDeliveryError(String orderid, String customerid) {
    	return "failed";
    }
    
    @HystrixCommand(fallbackMethod = "commentOrderByCustomerError")
    public String commentOrderByCustomer(String customerid, String orderid, String bean) {
    	Map<String, Object> uriVariables = new HashMap<String, Object>();
    	uriVariables.put("orderid", orderid);
    	uriVariables.put("customerid", customerid);
    	
        HttpHeaders headers =new HttpHeaders();
        MediaType mtype = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(mtype);
        HttpEntity<String> request = new HttpEntity<String>(bean, headers);

        return restTemplate.postForObject("http://SERVICE-STORE/order/comment/customer" 
				+ "?orderid={orderid}&customerid={customerid}", request, String.class, 
				uriVariables);
    }
    public String commentOrderByCustomerError(String customerid, String orderid, String bean) {
    	return "failed";
    }

    @HystrixCommand(fallbackMethod = "askEndLeaseProdError")
    public String askEndLeaseProd(String customerid, String bean) {
    	Map<String, Object> uriVariables = new HashMap<String, Object>();
    	uriVariables.put("customerid", customerid);
    	
        HttpHeaders headers =new HttpHeaders();
        MediaType mtype = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(mtype);
        HttpEntity<String> request = new HttpEntity<String>(bean, headers);

        return restTemplate.postForObject("http://SERVICE-STORE/order/lease/ask/end" 
				+ "?customerid={customerid}", request, String.class, uriVariables);
    }
    public String askEndLeaseProdError(String customerid, String bean) {
    	return "failed";
    }
    
    @HystrixCommand(fallbackMethod = "askDeferLeaseProdError")
    public String askDeferLeaseProd(String customerid, String bean) {
    	Map<String, Object> uriVariables = new HashMap<String, Object>();
    	uriVariables.put("customerid", customerid);
    	
        HttpHeaders headers =new HttpHeaders();
        MediaType mtype = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(mtype);
        HttpEntity<String> request = new HttpEntity<String>(bean, headers);

        return restTemplate.postForObject("http://SERVICE-STORE/order/lease/ask/defer" 
				+ "?customerid={customerid}", request, String.class, uriVariables);
    }
    public String askDeferLeaseProdError(String customerid, String bean) {
    	return "failed";
    }
    
    @HystrixCommand(fallbackMethod = "askChangeProdError")
    public String askChangeProd(String customerid, String bean) {
    	Map<String, Object> uriVariables = new HashMap<String, Object>();
    	uriVariables.put("customerid", customerid);
    	
        HttpHeaders headers =new HttpHeaders();
        MediaType mtype = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(mtype);
        HttpEntity<String> request = new HttpEntity<String>(bean, headers);

        return restTemplate.postForObject("http://SERVICE-STORE/order/ask/change" 
				+ "?customerid={customerid}", request, String.class, uriVariables);
    }
    public String askChangeProdError(String customerid, String bean) {
    	return "failed";
    }
    
    @HystrixCommand(fallbackMethod = "askReturnSalesProdError")
    public String askReturnSalesProd(String customerid, String bean) {
    	Map<String, Object> uriVariables = new HashMap<String, Object>();
    	uriVariables.put("customerid", customerid);
    	
        HttpHeaders headers =new HttpHeaders();
        MediaType mtype = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(mtype);
        HttpEntity<String> request = new HttpEntity<String>(bean, headers);

        return restTemplate.postForObject("http://SERVICE-STORE/order/sales/ask/return" 
				+ "?customerid={customerid}", request, String.class, uriVariables);
    }
    public String askReturnSalesProdError(String customerid, String bean) {
    	return "failed";
    }
}
