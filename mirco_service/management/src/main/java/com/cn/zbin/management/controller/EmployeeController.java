package com.cn.zbin.management.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cn.zbin.management.bto.MsgData;
import com.cn.zbin.management.dto.EmployeeInfo;
import com.cn.zbin.management.exception.BusinessException;
import com.cn.zbin.management.service.EmployeeService;
import com.cn.zbin.management.utils.MgmtConstants;

@RestController
@RequestMapping("emp")
public class EmployeeController {
	protected static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	@Autowired
	private EmployeeService employeeService;
	
	@RequestMapping(value = "/pwd/{new}",
			consumes = {"application/json;charset=UTF-8"}, 
			produces = {"application/json;charset=UTF-8"}, 
			method = { RequestMethod.POST })
	public MsgData updEmpPwd(
			@PathVariable("new") String newpwd,
			@RequestBody EmployeeInfo emp) {
		MsgData ret = new MsgData();
		try {
			String msg = employeeService.updatePassword(emp.getEmployeeId(), emp.getPassword(), newpwd);
			if (!"".equals(msg)) {
				throw new BusinessException(msg);
			}
		} catch (BusinessException be) {
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(be.getMessage());
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			ret.setStatus(MsgData.status_ng);
			ret.setMessage(MgmtConstants.CHK_ERR_99999);
			return ret;
		}
		
		return ret;
	}
	

//	logger.info(Utils.MD5(UUID.randomUUID().toString()));
}
