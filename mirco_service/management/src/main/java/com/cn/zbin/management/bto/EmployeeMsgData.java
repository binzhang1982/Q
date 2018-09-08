package com.cn.zbin.management.bto;

import com.cn.zbin.management.dto.EmployeeInfo;

public class EmployeeMsgData extends MsgData {
	private EmployeeInfo emp;

	public EmployeeInfo getEmp() {
		return emp;
	}

	public void setEmp(EmployeeInfo emp) {
		this.emp = emp;
	}
}
