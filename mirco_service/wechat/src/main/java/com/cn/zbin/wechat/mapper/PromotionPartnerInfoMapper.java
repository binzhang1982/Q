package com.cn.zbin.wechat.mapper;

import com.cn.zbin.wechat.dto.PromotionPartnerInfo;
import com.cn.zbin.wechat.dto.PromotionPartnerInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PromotionPartnerInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table promotionpartner
     *
     * @mbggenerated
     */
    int countByExample(PromotionPartnerInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table promotionpartner
     *
     * @mbggenerated
     */
    int deleteByExample(PromotionPartnerInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table promotionpartner
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(String partnerId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table promotionpartner
     *
     * @mbggenerated
     */
    int insert(PromotionPartnerInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table promotionpartner
     *
     * @mbggenerated
     */
    int insertSelective(PromotionPartnerInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table promotionpartner
     *
     * @mbggenerated
     */
    List<PromotionPartnerInfo> selectByExample(PromotionPartnerInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table promotionpartner
     *
     * @mbggenerated
     */
    PromotionPartnerInfo selectByPrimaryKey(String partnerId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table promotionpartner
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") PromotionPartnerInfo record, @Param("example") PromotionPartnerInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table promotionpartner
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") PromotionPartnerInfo record, @Param("example") PromotionPartnerInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table promotionpartner
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(PromotionPartnerInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table promotionpartner
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(PromotionPartnerInfo record);
}