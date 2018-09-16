package com.cn.zbin.store.mapper;

import com.cn.zbin.store.dto.WeChatMessageHistory;
import com.cn.zbin.store.dto.WeChatMessageHistoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WeChatMessageHistoryMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wechatmessagehistory
     *
     * @mbggenerated
     */
    int countByExample(WeChatMessageHistoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wechatmessagehistory
     *
     * @mbggenerated
     */
    int deleteByExample(WeChatMessageHistoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wechatmessagehistory
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(String messageId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wechatmessagehistory
     *
     * @mbggenerated
     */
    int insert(WeChatMessageHistory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wechatmessagehistory
     *
     * @mbggenerated
     */
    int insertSelective(WeChatMessageHistory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wechatmessagehistory
     *
     * @mbggenerated
     */
    List<WeChatMessageHistory> selectByExample(WeChatMessageHistoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wechatmessagehistory
     *
     * @mbggenerated
     */
    WeChatMessageHistory selectByPrimaryKey(String messageId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wechatmessagehistory
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") WeChatMessageHistory record, @Param("example") WeChatMessageHistoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wechatmessagehistory
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") WeChatMessageHistory record, @Param("example") WeChatMessageHistoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wechatmessagehistory
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(WeChatMessageHistory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wechatmessagehistory
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(WeChatMessageHistory record);
}