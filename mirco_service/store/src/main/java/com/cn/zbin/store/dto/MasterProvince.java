package com.cn.zbin.store.dto;

public class MasterProvince {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column masterprovince.province_code
     *
     * @mbggenerated
     */
    private String provinceCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column masterprovince.province_name
     *
     * @mbggenerated
     */
    private String provinceName;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column masterprovince.province_code
     *
     * @return the value of masterprovince.province_code
     *
     * @mbggenerated
     */
    public String getProvinceCode() {
        return provinceCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column masterprovince.province_code
     *
     * @param provinceCode the value for masterprovince.province_code
     *
     * @mbggenerated
     */
    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode == null ? null : provinceCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column masterprovince.province_name
     *
     * @return the value of masterprovince.province_name
     *
     * @mbggenerated
     */
    public String getProvinceName() {
        return provinceName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column masterprovince.province_name
     *
     * @param provinceName the value for masterprovince.province_name
     *
     * @mbggenerated
     */
    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName == null ? null : provinceName.trim();
    }
}