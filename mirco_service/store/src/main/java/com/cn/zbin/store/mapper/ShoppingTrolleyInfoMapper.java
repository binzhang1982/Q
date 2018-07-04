package com.cn.zbin.store.mapper;

import com.cn.zbin.store.dto.ShoppingTrolleyInfo;
import com.cn.zbin.store.dto.ShoppingTrolleyInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ShoppingTrolleyInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ShoppingTrolley
     *
     * @mbggenerated
     */
    int countByExample(ShoppingTrolleyInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ShoppingTrolley
     *
     * @mbggenerated
     */
    int deleteByExample(ShoppingTrolleyInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ShoppingTrolley
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(String trolleyId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ShoppingTrolley
     *
     * @mbggenerated
     */
    int insert(ShoppingTrolleyInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ShoppingTrolley
     *
     * @mbggenerated
     */
    int insertSelective(ShoppingTrolleyInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ShoppingTrolley
     *
     * @mbggenerated
     */
    List<ShoppingTrolleyInfo> selectByExample(ShoppingTrolleyInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ShoppingTrolley
     *
     * @mbggenerated
     */
    ShoppingTrolleyInfo selectByPrimaryKey(String trolleyId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ShoppingTrolley
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") ShoppingTrolleyInfo record, @Param("example") ShoppingTrolleyInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ShoppingTrolley
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") ShoppingTrolleyInfo record, @Param("example") ShoppingTrolleyInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ShoppingTrolley
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(ShoppingTrolleyInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ShoppingTrolley
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(ShoppingTrolleyInfo record);
}