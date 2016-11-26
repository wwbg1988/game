package com.ssic.shop.manage.mapper;

import com.ssic.shop.manage.pojo.ShoppingCart;
import com.ssic.shop.manage.pojo.ShoppingCartExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ShoppingCartMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_shopping_cart
     *
     * @mbggenerated Fri Sep 25 15:56:01 CST 2015
     */
    int countByExample(ShoppingCartExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_shopping_cart
     *
     * @mbggenerated Fri Sep 25 15:56:01 CST 2015
     */
    int deleteByExample(ShoppingCartExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_shopping_cart
     *
     * @mbggenerated Fri Sep 25 15:56:01 CST 2015
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_shopping_cart
     *
     * @mbggenerated Fri Sep 25 15:56:01 CST 2015
     */
    int insert(ShoppingCart record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_shopping_cart
     *
     * @mbggenerated Fri Sep 25 15:56:01 CST 2015
     */
    int insertSelective(ShoppingCart record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_shopping_cart
     *
     * @mbggenerated Fri Sep 25 15:56:01 CST 2015
     */
    List<ShoppingCart> selectByExample(ShoppingCartExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_shopping_cart
     *
     * @mbggenerated Fri Sep 25 15:56:01 CST 2015
     */
    ShoppingCart selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_shopping_cart
     *
     * @mbggenerated Fri Sep 25 15:56:01 CST 2015
     */
    int updateByExampleSelective(@Param("record") ShoppingCart record, @Param("example") ShoppingCartExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_shopping_cart
     *
     * @mbggenerated Fri Sep 25 15:56:01 CST 2015
     */
    int updateByExample(@Param("record") ShoppingCart record, @Param("example") ShoppingCartExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_shopping_cart
     *
     * @mbggenerated Fri Sep 25 15:56:01 CST 2015
     */
    int updateByPrimaryKeySelective(ShoppingCart record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_shopping_cart
     *
     * @mbggenerated Fri Sep 25 15:56:01 CST 2015
     */
    int updateByPrimaryKey(ShoppingCart record);
}