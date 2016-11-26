package com.ssic.game.common.mapper;

import com.ssic.game.common.pojo.Fields;
import com.ssic.game.common.pojo.FieldsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FieldsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_fields
     *
     * @mbggenerated Sat Jun 27 14:44:45 CST 2015
     */
    int countByExample(FieldsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_fields
     *
     * @mbggenerated Sat Jun 27 14:44:45 CST 2015
     */
    int deleteByExample(FieldsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_fields
     *
     * @mbggenerated Sat Jun 27 14:44:45 CST 2015
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_fields
     *
     * @mbggenerated Sat Jun 27 14:44:45 CST 2015
     */
    int insert(Fields record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_fields
     *
     * @mbggenerated Sat Jun 27 14:44:45 CST 2015
     */
    int insertSelective(Fields record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_fields
     *
     * @mbggenerated Sat Jun 27 14:44:45 CST 2015
     */
    List<Fields> selectByExample(FieldsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_fields
     *
     * @mbggenerated Sat Jun 27 14:44:45 CST 2015
     */
    Fields selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_fields
     *
     * @mbggenerated Sat Jun 27 14:44:45 CST 2015
     */
    int updateByExampleSelective(@Param("record") Fields record, @Param("example") FieldsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_fields
     *
     * @mbggenerated Sat Jun 27 14:44:45 CST 2015
     */
    int updateByExample(@Param("record") Fields record, @Param("example") FieldsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_fields
     *
     * @mbggenerated Sat Jun 27 14:44:45 CST 2015
     */
    int updateByPrimaryKeySelective(Fields record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_fields
     *
     * @mbggenerated Sat Jun 27 14:44:45 CST 2015
     */
    int updateByPrimaryKey(Fields record);
}