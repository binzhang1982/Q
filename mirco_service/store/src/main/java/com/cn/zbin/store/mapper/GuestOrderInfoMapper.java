package com.cn.zbin.store.mapper;

import com.cn.zbin.store.dto.GuestOrderInfo;
import com.cn.zbin.store.dto.GuestOrderInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GuestOrderInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table guestorder
     *
     * @mbggenerated
     */
    int countByExample(GuestOrderInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table guestorder
     *
     * @mbggenerated
     */
    int deleteByExample(GuestOrderInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table guestorder
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(String orderId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table guestorder
     *
     * @mbggenerated
     */
    int insert(GuestOrderInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table guestorder
     *
     * @mbggenerated
     */
    int insertSelective(GuestOrderInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table guestorder
     *
     * @mbggenerated
     */
    List<GuestOrderInfo> selectByExample(GuestOrderInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table guestorder
     *
     * @mbggenerated
     */
    GuestOrderInfo selectByPrimaryKey(String orderId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table guestorder
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") GuestOrderInfo record, @Param("example") GuestOrderInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table guestorder
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") GuestOrderInfo record, @Param("example") GuestOrderInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table guestorder
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(GuestOrderInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table guestorder
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(GuestOrderInfo record);
    
    
    List<GuestOrderInfo> selectOnePageByExample(@Param("example") GuestOrderInfoExample example,
    		@Param("offset") Integer offset, @Param("limit") Integer limit, 
    		@Param("orderByClause") String orderByClause);
}