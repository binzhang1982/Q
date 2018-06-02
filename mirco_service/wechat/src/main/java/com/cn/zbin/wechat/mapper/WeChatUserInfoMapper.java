package com.cn.zbin.wechat.mapper;

import com.cn.zbin.wechat.dto.WeChatUserInfo;
import com.cn.zbin.wechat.dto.WeChatUserInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WeChatUserInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wechatuser
     *
     * @mbggenerated
     */
    int countByExample(WeChatUserInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wechatuser
     *
     * @mbggenerated
     */
    int deleteByExample(WeChatUserInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wechatuser
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(String openId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wechatuser
     *
     * @mbggenerated
     */
    int insert(WeChatUserInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wechatuser
     *
     * @mbggenerated
     */
    int insertSelective(WeChatUserInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wechatuser
     *
     * @mbggenerated
     */
    List<WeChatUserInfo> selectByExample(WeChatUserInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wechatuser
     *
     * @mbggenerated
     */
    WeChatUserInfo selectByPrimaryKey(String openId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wechatuser
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") WeChatUserInfo record, @Param("example") WeChatUserInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wechatuser
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") WeChatUserInfo record, @Param("example") WeChatUserInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wechatuser
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(WeChatUserInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wechatuser
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(WeChatUserInfo record);
}