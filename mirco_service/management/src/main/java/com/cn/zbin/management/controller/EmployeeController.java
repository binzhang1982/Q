package com.cn.zbin.management.controller;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cn.zbin.management.bto.EmployeeMsgData;
import com.cn.zbin.management.bto.MsgData;
import com.cn.zbin.management.dto.EmployeeInfo;
import com.cn.zbin.management.exception.BusinessException;
import com.cn.zbin.management.service.EmployeeService;
import com.cn.zbin.management.utils.MgmtConstants;
import com.cn.zbin.management.utils.Utils;

@RestController
@RequestMapping("emp")
public class EmployeeController {
	protected static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	@Autowired
	private EmployeeService employeeService;
	
	@RequestMapping(value = "/pwd",
			consumes = {"application/json;charset=UTF-8"}, 
			produces = {"application/json;charset=UTF-8"}, 
			method = {RequestMethod.POST})
	public MsgData updEmpPwd(
			@RequestParam("name") String empname,
			@RequestParam("old") String oldpwd,
			@PathVariable("new") String newpwd) {
		MsgData ret = new MsgData();
		try {
			EmployeeInfo emp = employeeService.getEmployeeByPwd(empname, oldpwd);
			employeeService.updatePassword(emp.getEmployeeId(), newpwd);
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
	
	@RequestMapping(value = "/access",
			produces = {"application/json;charset=UTF-8"},
			method = {RequestMethod.GET})
	public EmployeeMsgData access(
			@RequestParam("name") String empname,
			@RequestParam("old") String oldpwd) {
		EmployeeMsgData ret = new EmployeeMsgData();
		try {
			EmployeeInfo emp = employeeService.getEmployeeByPwd(empname, oldpwd);
			ret.setEmp(employeeService.updateToken(emp.getEmployeeId(), 
					Utils.MD5(UUID.randomUUID().toString())));
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

	@RequestMapping(value = "/token",
			produces = {"application/json;charset=UTF-8"},
			method = {RequestMethod.GET})
	public MsgData chkToken(
			@RequestParam("empid") String empid,
			@RequestParam("token") String token) {
		MsgData ret = new MsgData();
		try {
			EmployeeInfo emp = employeeService.getEmployeeByToken(empid, token);
			employeeService.updateToken(emp.getEmployeeId(),emp.getToken());
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


	@RequestMapping(value = "/auth",
			produces = {"application/json;charset=UTF-8"},
			method = {RequestMethod.GET})
	public MsgData chkAuth(
			@RequestParam("empid") String empid,
			@RequestParam("auth") String auth) {
		MsgData ret = new MsgData();
		try {
			employeeService.checkAuth(empid, auth);
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
}
