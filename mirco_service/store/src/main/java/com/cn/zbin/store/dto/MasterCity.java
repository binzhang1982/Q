package com.cn.zbin.store.dto;

public class MasterCity {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mastercity.city_code
     *
     * @mbggenerated
     */
    private String cityCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mastercity.province_code
     *
     * @mbggenerated
     */
    private String provinceCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mastercity.city_name
     *
     * @mbggenerated
     */
    private String cityName;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mastercity.city_code
     *
     * @return the value of mastercity.city_code
     *
     * @mbggenerated
     */
    public String getCityCode() {
        return cityCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mastercity.city_code
     *
     * @param cityCode the value for mastercity.city_code
     *
     * @mbggenerated
     */
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mastercity.province_code
     *
     * @return the value of mastercity.province_code
     *
     * @mbggenerated
     */
    public String getProvinceCode() {
        return provinceCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mastercity.province_code
     *
     * @param provinceCode the value for mastercity.province_code
     *
     * @mbggenerated
     */
    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode == null ? null : provinceCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mastercity.city_name
     *
     * @return the value of mastercity.city_name
     *
     * @mbggenerated
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mastercity.city_name
     *
     * @param cityName the value for mastercity.city_name
     *
     * @mbggenerated
     */
    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }
}