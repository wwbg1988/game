package com.ssic.shop.manage.mapper;

import com.ssic.shop.manage.pojo.OrderConsignee;
import com.ssic.shop.manage.pojo.OrderConsigneeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderConsigneeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_order_consignee
     *
     * @mbggenerated Thu Aug 27 09:25:08 CST 2015
     */
    int countByExample(OrderConsigneeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_order_consignee
     *
     * @mbggenerated Thu Aug 27 09:25:08 CST 2015
     */
    int deleteByExample(OrderConsigneeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_order_consignee
     *
     * @mbggenerated Thu Aug 27 09:25:08 CST 2015
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_order_consignee
     *
     * @mbggenerated Thu Aug 27 09:25:08 CST 2015
     */
    int insert(OrderConsignee record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_order_consignee
     *
     * @mbggenerated Thu Aug 27 09:25:08 CST 2015
     */
    int insertSelective(OrderConsignee record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_order_consignee
     *
     * @mbggenerated Thu Aug 27 09:25:08 CST 2015
     */
    List<OrderConsignee> selectByExample(OrderConsigneeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_order_consignee
     *
     * @mbggenerated Thu Aug 27 09:25:08 CST 2015
     */
    OrderConsignee selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_order_consignee
     *
     * @mbggenerated Thu Aug 27 09:25:08 CST 2015
     */
    int updateByExampleSelective(@Param("record") OrderConsignee record, @Param("example") OrderConsigneeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_order_consignee
     *
     * @mbggenerated Thu Aug 27 09:25:08 CST 2015
     */
    int updateByExample(@Param("record") OrderConsignee record, @Param("example") OrderConsigneeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_order_consignee
     *
     * @mbggenerated Thu Aug 27 09:25:08 CST 2015
     */
    int updateByPrimaryKeySelective(OrderConsignee record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_order_consignee
     *
     * @mbggenerated Thu Aug 27 09:25:08 CST 2015
     */
    int updateByPrimaryKey(OrderConsignee record);
}