package com.cn.zbin.ribbonserver.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.cn.zbin.ribbonserver.utils.RibbonKeyConstants;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class DesktopService {
    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "checkTokenError")
    public String checkToken(String empid, String token) {
    	Map<String, Object> uriVariables = new HashMap<String, Object>();
    	uriVariables.put("empid", empid);
    	uriVariables.put("token", token);
        return restTemplate.getForObject("http://SERVICE-MGMT/emp/token" 
				+ "?empid={empid}&token={token}", String.class, uriVariables);
    }
    public String checkTokenError(String empid, String token) {
    	return "failed";
    }
    @HystrixCommand(fallbackMethod = "checkAuthError")
    public String checkAuth(String empid, String auth) {
    	Map<String, Object> uriVariables = new HashMap<String, Object>();
    	uriVariables.put("empid", empid);
    	uriVariables.put("auth", auth);
        return restTemplate.getForObject("http://SERVICE-MGMT/emp/auth" 
				+ "?empid={empid}&auth={auth}", String.class, uriVariables);
    }
    public String checkAuthError(String empid, String auth) {
    	return "failed";
    }

    @HystrixCommand(fallbackMethod = "setOrderCourierNumberError")
    public String setOrderCourierNumber(String empid, String orderid, String courierno) {
    	Map<String, Object> uriVariables = new HashMap<String, Object>();
    	uriVariables.put("empid", empid);
    	uriVariables.put("orderid", orderid);
    	uriVariables.put("courierno", courierno);

        return restTemplate.postForObject("http://SERVICE-STORE/order/courier" 
    			+ "?empid={empid}&orderid={orderid}&courierno={courierno}", null, String.class, 
    			uriVariables);
    }
    public String setOrderCourierNumberError(String empid, String orderid, String courierno) {
    	return "failed";
    }
    
    @HystrixCommand(fallbackMethod = "confirmDeliveryError")
    public String confirmDelivery(String orderid, String empid) {
    	Map<String, Object> uriVariables = new HashMap<String, Object>();
    	uriVariables.put("orderid", orderid);
    	uriVariables.put("id", empid);
    	uriVariables.put("type", 2);

        return restTemplate.postForObject("http://SERVICE-STORE/order/delivery/confirm" 
				+ "?orderid={orderid}&id={id}&type={type}", null, String.class, 
				uriVariables);
    }
    public String confirmDeliveryError(String orderId, String customerId) {
    	return "failed";
    }
    
    @HystrixCommand(fallbackMethod = "calcRecycleAmountError")
    public String calcRecycleAmount(String orderOperId, String recycleDate) {
    	Map<String, Object> uriVariables = new HashMap<String, Object>();
    	uriVariables.put("orderoperid", orderOperId);
    	uriVariables.put("recycledate", recycleDate);

        return restTemplate.getForObject("http://SERVICE-STORE/order/lease/recycle/calc" 
				+ "?orderoperid={orderoperid}&recycledate={recycledate}", String.class, 
				uriVariables);
    }
    public String calcRecycleAmountError(String orderOperId, String recycleDate) {
    	return "failed";
    }
    
    @HystrixCommand(fallbackMethod = "agreeRecycleLeaseProductError")
    public String agreeRecycleLeaseProduct(String empid, String bean) {
    	Map<String, Object> uriVariables = new HashMap<String, Object>();
    	uriVariables.put("empid", empid);
    	
        HttpHeaders headers =new HttpHeaders();
        MediaType mtype = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(mtype);
        HttpEntity<String> request = new HttpEntity<String>(bean, headers);

        return restTemplate.postForObject("http://SERVICE-STORE/order/lease/recycle/agree" 
    			+ "?empid={empid}", request, String.class, uriVariables);
    }
    public String agreeRecycleLeaseProductError(String empid, String bean) {
    	return "failed";
    }
    
    @HystrixCommand(fallbackMethod = "rejectRecycleLeaseProductError")
    public String rejectRecycleLeaseProduct(String empid, String bean) {
    	Map<String, Object> uriVariables = new HashMap<String, Object>();
    	uriVariables.put("empid", empid);
    	
        HttpHeaders headers =new HttpHeaders();
        MediaType mtype = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(mtype);
        HttpEntity<String> request = new HttpEntity<String>(bean, headers);

        return restTemplate.postForObject("http://SERVICE-STORE/order/lease/recycle/reject" 
    			+ "?empid={empid}", request, String.class, uriVariables);
    }
    public String rejectRecycleLeaseProductError(String empid, String bean) {
    	return "failed";
    }
    
    @HystrixCommand(fallbackMethod = "agreeChangeAllProductError")
    public String agreeChangeAllProduct(String empid, String bean) {
    	Map<String, Object> uriVariables = new HashMap<String, Object>();
    	uriVariables.put("empid", empid);
    	
        HttpHeaders headers =new HttpHeaders();
        MediaType mtype = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(mtype);
        HttpEntity<String> request = new HttpEntity<String>(bean, headers);

        return restTemplate.postForObject("http://SERVICE-STORE/order/change/agree" 
    			+ "?empid={empid}", request, String.class, uriVariables);
    }
    public String agreeChangeAllProductError(String empid, String bean) {
    	return "failed";
    }
    
    @HystrixCommand(fallbackMethod = "rejectChangeAllProductError")
    public String rejectChangeAllProduct(String empid, String bean) {
    	Map<String, Object> uriVariables = new HashMap<String, Object>();
    	uriVariables.put("empid", empid);
    	
        HttpHeaders headers =new HttpHeaders();
        MediaType mtype = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(mtype);
        HttpEntity<String> request = new HttpEntity<String>(bean, headers);

        return restTemplate.postForObject("http://SERVICE-STORE/order/change/reject" 
    			+ "?empid={empid}", request, String.class, uriVariables);
    }
    public String rejectChangeAllProductError(String empid, String bean) {
    	return "failed";
    }
    
    @HystrixCommand(fallbackMethod = "agreeReturnSalesProductError")
    public String agreeReturnSalesProduct(String empid, String bean) {
    	Map<String, Object> uriVariables = new HashMap<String, Object>();
    	uriVariables.put("empid", empid);
    	
        HttpHeaders headers =new HttpHeaders();
        MediaType mtype = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(mtype);
        HttpEntity<String> request = new HttpEntity<String>(bean, headers);

        return restTemplate.postForObject("http://SERVICE-STORE/order/sales/return/agree" 
    			+ "?empid={empid}", request, String.class, uriVariables);
    }
    public String agreeReturnSalesProductError(String empid, String bean) {
    	return "failed";
    }
    
    @HystrixCommand(fallbackMethod = "rejectReturnSalesProductError")
    public String rejectReturnSalesProduct(String empid, String bean) {
    	Map<String, Object> uriVariables = new HashMap<String, Object>();
    	uriVariables.put("empid", empid);
    	
        HttpHeaders headers =new HttpHeaders();
        MediaType mtype = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(mtype);
        HttpEntity<String> request = new HttpEntity<String>(bean, headers);

        return restTemplate.postForObject("http://SERVICE-STORE/order/sales/return/reject" 
    			+ "?empid={empid}", request, String.class, uriVariables);
    }
    public String rejectReturnSalesProductError(String empid, String bean) {
    	return "failed";
    }

    @HystrixCommand(fallbackMethod = "countCustomerError")
    public String countCustomer() {
        return restTemplate.getForObject("http://SERVICE-MGMT/customer/count", String.class);
    }
    public String countCustomerError() {
    	return "failed";
    }
    
    @HystrixCommand(fallbackMethod = "getCustomerListError")
    public String getCustomerList(String name, String telno, 
    		Integer offset, Integer limit) {
    	Map<String, Object> uriVariables = new HashMap<String, Object>();
    	uriVariables.put("name", name != null ? name : "");
    	uriVariables.put("telno", telno != null ? telno : "");
    	uriVariables.put("offset", offset != null ? offset : "");
    	uriVariables.put("limit", limit != null ? limit : "");
    	String url = "http://SERVICE-MGMT/customer/list/sys?name={name}"
    			+ "&telno={telno}&offset={offset}&limit={limit}";
        return restTemplate.getForObject(url, String.class, uriVariables);
    }
    public String getCustomerListError(String name, String telno, 
    		Integer offset, Integer limit) {
    	return "failed";
    }

    @HystrixCommand(fallbackMethod = "countOrderError")
    public String countOrder(Integer lease) {
    	Map<String, Object> uriVariables = new HashMap<String, Object>();
    	uriVariables.put("lease", lease);
        return restTemplate.getForObject("http://SERVICE-STORE/order/count?lease={lease}", 
        		String.class, uriVariables);
    }
    public String countOrderError(Integer lease) {
    	return "failed";
    }
    
    @HystrixCommand(fallbackMethod = "getOrderListError")
    public String getOrderList(String status, String createdate, String customerid,
    		String name, String telno, Integer offset, Integer limit) {
    	Map<String, Object> uriVariables = new HashMap<String, Object>();
    	uriVariables.put("status", status != null ? status : "");
    	uriVariables.put("createdate", createdate != null ? createdate : "");
    	uriVariables.put("customerid", customerid != null ? customerid : "");
    	uriVariables.put("name", name != null ? name : "");
    	uriVariables.put("telno", telno != null ? telno : "");
    	uriVariables.put("offset", offset != null ? offset : "");
    	uriVariables.put("limit", limit != null ? limit : "");
    	
        return restTemplate.getForObject("http://SERVICE-STORE/order/list/sys?status={status}"
        		+ "&createdate={createdate}&customerid={customerid}&name={name}&telno={telno}"
        		+ "&offset={offset}&limit={limit}", 
        		String.class, uriVariables);
    }
    public String getOrderListError(String status, String createdate, String customerid,
    		String name, String telno, Integer offset, Integer limit) {
    	return "failed";
    }
    
    @HystrixCommand(fallbackMethod = "getOrderOperationListError")
    public String getOrderOperationList(String askcode, String anscode, 
    		Integer offset, Integer limit) {
    	Map<String, Object> uriVariables = new HashMap<String, Object>();
    	uriVariables.put("askcode", askcode != null ? askcode : "");
    	uriVariables.put("anscode", anscode != null ? anscode : "");
    	uriVariables.put("offset", offset != null ? offset : "");
    	uriVariables.put("limit", limit != null ? limit : "");
    	
        return restTemplate.getForObject("http://SERVICE-STORE/order/oper/list/sys?askcode={askcode}"
        		+ "&anscode={anscode}&offset={offset}&limit={limit}", 
        		String.class, uriVariables);
    }
    public String getOrderOperationListError(String askcode, String anscode, 
    		Integer offset, Integer limit) {
    	return "failed";
    }

    @HystrixCommand(fallbackMethod = "getDueOrderListError")
    public String getDueOrderList(Integer offset, Integer limit) {
    	Map<String, Object> uriVariables = new HashMap<String, Object>();
    	uriVariables.put("offset", offset != null ? offset : "");
    	uriVariables.put("limit", limit != null ? limit : "");
    	
        return restTemplate.getForObject("http://SERVICE-STORE/order/due/list/sys"
        		+ "?offset={offset}&limit={limit}", String.class, uriVariables);
    }
    public String getDueOrderListError(Integer offset, Integer limit) {
    	return "failed";
    }
    
    @HystrixCommand(fallbackMethod = "createPartnerError")
    public String createPartner(String bean) {
        String ret = "";
		try {
	    	Map<String, Object> uriVariables = new HashMap<String, Object>();
	    	uriVariables.put("atk", URLEncoder.encode(RibbonKeyConstants.APPTOKEN, "UTF-8"));
	    	String url = "http://SERVICE-WECHAT/qr/partner/create?atk={atk}";

	        HttpHeaders headers =new HttpHeaders();
	        MediaType mtype = MediaType.parseMediaType("application/json; charset=UTF-8");
	        headers.setContentType(mtype);
	        HttpEntity<String> request = new HttpEntity<String>(bean, headers);
	        
			ret = restTemplate.postForObject(url, request, String.class, uriVariables);
		} catch (RestClientException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return ret;
    }
    public String createPartnerError(String bean) {
        return "failed";
    }
}
