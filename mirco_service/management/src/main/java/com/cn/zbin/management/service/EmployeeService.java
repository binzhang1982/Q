package com.cn.zbin.management.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cn.zbin.management.dto.EmployeeInfo;
import com.cn.zbin.management.dto.EmployeeInfoExample;
import com.cn.zbin.management.dto.EmployeeRole;
import com.cn.zbin.management.dto.EmployeeRoleExample;
import com.cn.zbin.management.exception.BusinessException;
import com.cn.zbin.management.mapper.EmployeeInfoMapper;
import com.cn.zbin.management.mapper.EmployeeRoleMapper;
import com.cn.zbin.management.utils.MgmtConstants;
import com.cn.zbin.management.utils.Utils;

@Service
public class EmployeeService {
	@Autowired
	private EmployeeInfoMapper employeeInfoMapper;
	@Autowired
	private EmployeeRoleMapper employeeRoleMapper;
	
	public EmployeeInfo getEmployeeByPwd(String empname, String oldpwd) 
		throws BusinessException, Exception {
		String oldPwdSha = Utils.HMACSHA256(oldpwd);
		EmployeeInfoExample exam_ei = new EmployeeInfoExample();
		exam_ei.createCriteria().andEmployeeNameEqualTo(empname);
		List<EmployeeInfo> emps = employeeInfoMapper.selectByExample(exam_ei);
		if (Utils.listNotNull(emps)) {
			if (emps.size() > 1) throw new BusinessException(MgmtConstants.CHK_ERR_80014);
			
			EmployeeInfo emp = emps.get(0);
			Integer errCnt = emp.getErrorCount();
			try {
				if (!emp.getActiveFlag()) 
					throw new BusinessException(MgmtConstants.CHK_ERR_80011);
				if (emp.getLeaveDate().compareTo(Utils.getChinaCurrentTime()) < 0) 
					throw new BusinessException(MgmtConstants.CHK_ERR_80011);
				if (!emp.getPassword().equals(oldPwdSha)) 
					throw new BusinessException(MgmtConstants.CHK_ERR_80012);
				if (errCnt > 3)
					throw new BusinessException(MgmtConstants.CHK_ERR_80013);
			} catch(BusinessException be) {
				updateErrorCount(emp.getEmployeeId(), errCnt);
				throw be;
			}
		} else {
			throw new BusinessException(MgmtConstants.CHK_ERR_80010);
		}
		
		return emps.get(0);
	}
	
	public EmployeeInfo getEmployeeByToken(String empid, String token)
			throws BusinessException, Exception {
		EmployeeInfo emp = employeeInfoMapper.selectByPrimaryKey(empid);
		if (emp != null) {
			Integer errCnt = emp.getErrorCount();
			try {
				if (!emp.getActiveFlag()) 
					throw new BusinessException(MgmtConstants.CHK_ERR_80011);
				if (emp.getLeaveDate().compareTo(Utils.getChinaCurrentTime()) < 0) 
					throw new BusinessException(MgmtConstants.CHK_ERR_80011);
				if (emp.getToken() == null) 
					throw new BusinessException(MgmtConstants.CHK_ERR_80012);
				if (emp.getToken() != null && !emp.getToken().equals(token)) 
					throw new BusinessException(MgmtConstants.CHK_ERR_80012);
				if (Utils.isOverDue(emp.getExpiredTime(), 1, Utils.INTERVAL_TYPE_HOUR))
					throw new BusinessException(MgmtConstants.CHK_ERR_80015);
				if (errCnt > 3)
					throw new BusinessException(MgmtConstants.CHK_ERR_80013);
			} catch(BusinessException be) {
				updateErrorCount(emp.getEmployeeId(), errCnt);
				throw be;
			}
		} else {
			throw new BusinessException(MgmtConstants.CHK_ERR_80010);
		}
		return emp;
	}
	
	public void checkAuth(String empid, String auth) 
			throws BusinessException, Exception {
		EmployeeRoleExample exam_er = new EmployeeRoleExample();
		exam_er.createCriteria().andEmployeeIdEqualTo(empid)
								.andRoleCodeEqualTo(auth);
		List<EmployeeRole> roles = employeeRoleMapper.selectByExample(exam_er);
		if (!Utils.listNotNull(roles)) {
			throw new BusinessException(MgmtConstants.CHK_ERR_80016);
		}
	}
	
	public void updateErrorCount(String empid, Integer errorCount) {
		EmployeeInfo record = new EmployeeInfo();
		record.setEmployeeId(empid);
		record.setErrorCount(errorCount + 1);
		employeeInfoMapper.updateByPrimaryKeySelective(record);
	}
	
	@Transactional
	public void updatePassword(String empid, String newpwd) 
			throws Exception {
		EmployeeInfo record = new EmployeeInfo();
		record.setEmployeeId(empid);
		record.setPassword(Utils.HMACSHA256(newpwd));
		record.setErrorCount(0);
		employeeInfoMapper.updateByPrimaryKeySelective(record);
	}
	
	public EmployeeInfo updateToken(String empid, String token) 
			throws Exception {
		EmployeeInfo record = new EmployeeInfo();
		record.setEmployeeId(empid);
		record.setToken(token);
		record.setExpiredTime(Utils.getChinaCurrentTime());
		employeeInfoMapper.updateByPrimaryKeySelective(record);
		return record;
	}
}
