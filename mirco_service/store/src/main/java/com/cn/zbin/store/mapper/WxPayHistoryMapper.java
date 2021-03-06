package com.cn.zbin.store.mapper;

import com.cn.zbin.store.dto.WxPayHistory;
import com.cn.zbin.store.dto.WxPayHistoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WxPayHistoryMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxpayhistory
     *
     * @mbggenerated
     */
    int countByExample(WxPayHistoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxpayhistory
     *
     * @mbggenerated
     */
    int deleteByExample(WxPayHistoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxpayhistory
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(String outTradeNo);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxpayhistory
     *
     * @mbggenerated
     */
    int insert(WxPayHistory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxpayhistory
     *
     * @mbggenerated
     */
    int insertSelective(WxPayHistory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxpayhistory
     *
     * @mbggenerated
     */
    List<WxPayHistory> selectByExample(WxPayHistoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxpayhistory
     *
     * @mbggenerated
     */
    WxPayHistory selectByPrimaryKey(String outTradeNo);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxpayhistory
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") WxPayHistory record, @Param("example") WxPayHistoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxpayhistory
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") WxPayHistory record, @Param("example") WxPayHistoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxpayhistory
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(WxPayHistory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxpayhistory
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(WxPayHistory record);
}