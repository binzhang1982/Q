package com.cn.zbin.store.dto;

public class CodeDictInfo {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column codedict.DictCode
     *
     * @mbggenerated
     */
    private String dictcode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column codedict.CodeCate
     *
     * @mbggenerated
     */
    private String codecate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column codedict.CodeName
     *
     * @mbggenerated
     */
    private String codename;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column codedict.DictCode
     *
     * @return the value of codedict.DictCode
     *
     * @mbggenerated
     */
    public String getDictcode() {
        return dictcode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column codedict.DictCode
     *
     * @param dictcode the value for codedict.DictCode
     *
     * @mbggenerated
     */
    public void setDictcode(String dictcode) {
        this.dictcode = dictcode == null ? null : dictcode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column codedict.CodeCate
     *
     * @return the value of codedict.CodeCate
     *
     * @mbggenerated
     */
    public String getCodecate() {
        return codecate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column codedict.CodeCate
     *
     * @param codecate the value for codedict.CodeCate
     *
     * @mbggenerated
     */
    public void setCodecate(String codecate) {
        this.codecate = codecate == null ? null : codecate.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column codedict.CodeName
     *
     * @return the value of codedict.CodeName
     *
     * @mbggenerated
     */
    public String getCodename() {
        return codename;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column codedict.CodeName
     *
     * @param codename the value for codedict.CodeName
     *
     * @mbggenerated
     */
    public void setCodename(String codename) {
        this.codename = codename == null ? null : codename.trim();
    }
}