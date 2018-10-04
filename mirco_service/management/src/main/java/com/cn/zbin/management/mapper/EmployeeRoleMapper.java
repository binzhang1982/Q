package com.cn.zbin.management.mapper;

import com.cn.zbin.management.dto.EmployeeRole;
import com.cn.zbin.management.dto.EmployeeRoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EmployeeRoleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table employeerole
     *
     * @mbggenerated
     */
    int countByExample(EmployeeRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table employeerole
     *
     * @mbggenerated
     */
    int deleteByExample(EmployeeRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table employeerole
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(String employeeRoleId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table employeerole
     *
     * @mbggenerated
     */
    int insert(EmployeeRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table employeerole
     *
     * @mbggenerated
     */
    int insertSelective(EmployeeRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table employeerole
     *
     * @mbggenerated
     */
    List<EmployeeRole> selectByExample(EmployeeRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table employeerole
     *
     * @mbggenerated
     */
    EmployeeRole selectByPrimaryKey(String employeeRoleId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table employeerole
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") EmployeeRole record, @Param("example") EmployeeRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table employeerole
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") EmployeeRole record, @Param("example") EmployeeRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table employeerole
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(EmployeeRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table employeerole
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(EmployeeRole record);
}