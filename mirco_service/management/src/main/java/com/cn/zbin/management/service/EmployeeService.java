package com.cn.zbin.management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cn.zbin.management.dto.EmployeeInfo;
import com.cn.zbin.management.dto.EmployeeInfoExample;
import com.cn.zbin.management.exception.BusinessException;
import com.cn.zbin.management.mapper.EmployeeInfoMapper;
import com.cn.zbin.management.utils.MgmtConstants;
import com.cn.zbin.management.utils.Utils;

@Service
public class EmployeeService {
	@Autowired
	private EmployeeInfoMapper employeeInfoMapper;
	
	@Transactional
	public String updatePassword(String empid, String oldpwd, String newpwd) 
			throws BusinessException, Exception {
		String ret = "";
		String oldPwdSha = Utils.HMACSHA256(oldpwd);
		EmployeeInfo emp = employeeInfoMapper.selectByPrimaryKey(empid);
		if (emp != null) {
			EmployeeInfo record = new EmployeeInfo();
			if (emp.getActiveFlag() && 
				emp.getLeaveDate().compareTo(Utils.getChinaCurrentTime()) >= 0 &&
				emp.getPassword().equals(oldPwdSha) &&
				emp.getErrorCount() <= 3) {
				
				record.setEmployeeId(empid);
				record.setPassword(Utils.HMACSHA256(newpwd));
				record.setErrorCount(0);
				employeeInfoMapper.updateByPrimaryKeySelective(record);
			} else {
				record.setEmployeeId(empid);
				record.setErrorCount(emp.getErrorCount() + 1);
				employeeInfoMapper.updateByPrimaryKeySelective(record);
				
				if (!emp.getActiveFlag()) 
					ret = MgmtConstants.CHK_ERR_80011;
				if (emp.getLeaveDate().compareTo(Utils.getChinaCurrentTime()) < 0) 
					ret = MgmtConstants.CHK_ERR_80011;
				if (!emp.getPassword().equals(oldPwdSha)) 
					ret = MgmtConstants.CHK_ERR_80012;
				if (emp.getErrorCount() > 3)
					ret = MgmtConstants.CHK_ERR_80013;
			}
		} else {
			ret = MgmtConstants.CHK_ERR_80010;
		}
		
		return ret;
	}
}
