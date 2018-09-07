package com.cn.zbin.management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cn.zbin.management.dto.EmployeeInfo;
import com.cn.zbin.management.dto.EmployeeInfoExample;
import com.cn.zbin.management.exception.BusinessException;
import com.cn.zbin.management.mapper.EmployeeInfoMapper;
import com.cn.zbin.management.utils.Utils;

@Service
public class EmployeeService {
	@Autowired
	private EmployeeInfoMapper employeeInfoMapper;
	
	@Transactional
	public Integer updatePassword(String empid, String oldpwd, String newpwd) 
			throws BusinessException, Exception {
		String oldPwdSha = Utils.HMACSHA256(oldpwd);
		EmployeeInfoExample exam_ei = new EmployeeInfoExample();
		exam_ei.createCriteria().andEmployeeIdEqualTo(empid)
								.andPasswordEqualTo(oldPwdSha)
								.andActiveFlagEqualTo(Boolean.TRUE);

		Integer ret = employeeInfoMapper.countByExample(exam_ei);
		if (ret > 0) {
			EmployeeInfo record = new EmployeeInfo();
			record.setEmployeeId(empid);
			record.setPassword(Utils.HMACSHA256(newpwd));
			employeeInfoMapper.updateByPrimaryKeySelective(record);
		} else {
			
		}
		
		return ret;
	}
}
