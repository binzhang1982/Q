package com.cn.zbin.store.mapper;

import com.cn.zbin.store.dto.ProductComment;
import com.cn.zbin.store.dto.ProductCommentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductCommentMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table productcomment
     *
     * @mbggenerated
     */
    int countByExample(ProductCommentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table productcomment
     *
     * @mbggenerated
     */
    int deleteByExample(ProductCommentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table productcomment
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(String productCommentId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table productcomment
     *
     * @mbggenerated
     */
    int insert(ProductComment record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table productcomment
     *
     * @mbggenerated
     */
    int insertSelective(ProductComment record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table productcomment
     *
     * @mbggenerated
     */
    List<ProductComment> selectByExample(ProductCommentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table productcomment
     *
     * @mbggenerated
     */
    ProductComment selectByPrimaryKey(String productCommentId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table productcomment
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") ProductComment record, @Param("example") ProductCommentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table productcomment
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") ProductComment record, @Param("example") ProductCommentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table productcomment
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(ProductComment record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table productcomment
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(ProductComment record);
}