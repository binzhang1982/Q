package com.cn.zbin.management.dto;

import java.util.Date;

public class EmployeeInfo {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column employee.employee_id
     *
     * @mbggenerated
     */
    private String employeeId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column employee.employee_name
     *
     * @mbggenerated
     */
    private String employeeName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column employee.province_code
     *
     * @mbggenerated
     */
    private String provinceCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column employee.city_code
     *
     * @mbggenerated
     */
    private String cityCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column employee.register_type
     *
     * @mbggenerated
     */
    private Integer registerType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column employee.register_id
     *
     * @mbggenerated
     */
    private String registerId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column employee.password
     *
     * @mbggenerated
     */
    private String password;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column employee.token
     *
     * @mbggenerated
     */
    private String token;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column employee.expired_time
     *
     * @mbggenerated
     */
    private Date expiredTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column employee.error_count
     *
     * @mbggenerated
     */
    private Integer errorCount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column employee.valid_code
     *
     * @mbggenerated
     */
    private String validCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column employee.login_address
     *
     * @mbggenerated
     */
    private String loginAddress;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column employee.active_flag
     *
     * @mbggenerated
     */
    private Boolean activeFlag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column employee.hire_date
     *
     * @mbggenerated
     */
    private Date hireDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column employee.leave_date
     *
     * @mbggenerated
     */
    private Date leaveDate;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column employee.employee_id
     *
     * @return the value of employee.employee_id
     *
     * @mbggenerated
     */
    public String getEmployeeId() {
        return employeeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column employee.employee_id
     *
     * @param employeeId the value for employee.employee_id
     *
     * @mbggenerated
     */
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId == null ? null : employeeId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column employee.employee_name
     *
     * @return the value of employee.employee_name
     *
     * @mbggenerated
     */
    public String getEmployeeName() {
        return employeeName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column employee.employee_name
     *
     * @param employeeName the value for employee.employee_name
     *
     * @mbggenerated
     */
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName == null ? null : employeeName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column employee.province_code
     *
     * @return the value of employee.province_code
     *
     * @mbggenerated
     */
    public String getProvinceCode() {
        return provinceCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column employee.province_code
     *
     * @param provinceCode the value for employee.province_code
     *
     * @mbggenerated
     */
    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode == null ? null : provinceCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column employee.city_code
     *
     * @return the value of employee.city_code
     *
     * @mbggenerated
     */
    public String getCityCode() {
        return cityCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column employee.city_code
     *
     * @param cityCode the value for employee.city_code
     *
     * @mbggenerated
     */
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column employee.register_type
     *
     * @return the value of employee.register_type
     *
     * @mbggenerated
     */
    public Integer getRegisterType() {
        return registerType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column employee.register_type
     *
     * @param registerType the value for employee.register_type
     *
     * @mbggenerated
     */
    public void setRegisterType(Integer registerType) {
        this.registerType = registerType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column employee.register_id
     *
     * @return the value of employee.register_id
     *
     * @mbggenerated
     */
    public String getRegisterId() {
        return registerId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column employee.register_id
     *
     * @param registerId the value for employee.register_id
     *
     * @mbggenerated
     */
    public void setRegisterId(String registerId) {
        this.registerId = registerId == null ? null : registerId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column employee.password
     *
     * @return the value of employee.password
     *
     * @mbggenerated
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column employee.password
     *
     * @param password the value for employee.password
     *
     * @mbggenerated
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column employee.token
     *
     * @return the value of employee.token
     *
     * @mbggenerated
     */
    public String getToken() {
        return token;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column employee.token
     *
     * @param token the value for employee.token
     *
     * @mbggenerated
     */
    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column employee.expired_time
     *
     * @return the value of employee.expired_time
     *
     * @mbggenerated
     */
    public Date getExpiredTime() {
        return expiredTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column employee.expired_time
     *
     * @param expiredTime the value for employee.expired_time
     *
     * @mbggenerated
     */
    public void setExpiredTime(Date expiredTime) {
        this.expiredTime = expiredTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column employee.error_count
     *
     * @return the value of employee.error_count
     *
     * @mbggenerated
     */
    public Integer getErrorCount() {
        return errorCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column employee.error_count
     *
     * @param errorCount the value for employee.error_count
     *
     * @mbggenerated
     */
    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column employee.valid_code
     *
     * @return the value of employee.valid_code
     *
     * @mbggenerated
     */
    public String getValidCode() {
        return validCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column employee.valid_code
     *
     * @param validCode the value for employee.valid_code
     *
     * @mbggenerated
     */
    public void setValidCode(String validCode) {
        this.validCode = validCode == null ? null : validCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column employee.login_address
     *
     * @return the value of employee.login_address
     *
     * @mbggenerated
     */
    public String getLoginAddress() {
        return loginAddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column employee.login_address
     *
     * @param loginAddress the value for employee.login_address
     *
     * @mbggenerated
     */
    public void setLoginAddress(String loginAddress) {
        this.loginAddress = loginAddress == null ? null : loginAddress.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column employee.active_flag
     *
     * @return the value of employee.active_flag
     *
     * @mbggenerated
     */
    public Boolean getActiveFlag() {
        return activeFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column employee.active_flag
     *
     * @param activeFlag the value for employee.active_flag
     *
     * @mbggenerated
     */
    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column employee.hire_date
     *
     * @return the value of employee.hire_date
     *
     * @mbggenerated
     */
    public Date getHireDate() {
        return hireDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column employee.hire_date
     *
     * @param hireDate the value for employee.hire_date
     *
     * @mbggenerated
     */
    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column employee.leave_date
     *
     * @return the value of employee.leave_date
     *
     * @mbggenerated
     */
    public Date getLeaveDate() {
        return leaveDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column employee.leave_date
     *
     * @param leaveDate the value for employee.leave_date
     *
     * @mbggenerated
     */
    public void setLeaveDate(Date leaveDate) {
        this.leaveDate = leaveDate;
    }
}