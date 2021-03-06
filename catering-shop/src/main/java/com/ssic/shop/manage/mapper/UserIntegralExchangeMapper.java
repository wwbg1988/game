package com.ssic.shop.manage.mapper;

import com.ssic.shop.manage.pojo.UserIntegralExchange;
import com.ssic.shop.manage.pojo.UserIntegralExchangeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserIntegralExchangeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_user_integral_exchange
     *
     * @mbggenerated Fri Sep 18 16:04:54 CST 2015
     */
    int countByExample(UserIntegralExchangeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_user_integral_exchange
     *
     * @mbggenerated Fri Sep 18 16:04:54 CST 2015
     */
    int deleteByExample(UserIntegralExchangeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_user_integral_exchange
     *
     * @mbggenerated Fri Sep 18 16:04:54 CST 2015
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_user_integral_exchange
     *
     * @mbggenerated Fri Sep 18 16:04:54 CST 2015
     */
    int insert(UserIntegralExchange record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_user_integral_exchange
     *
     * @mbggenerated Fri Sep 18 16:04:54 CST 2015
     */
    int insertSelective(UserIntegralExchange record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_user_integral_exchange
     *
     * @mbggenerated Fri Sep 18 16:04:54 CST 2015
     */
    List<UserIntegralExchange> selectByExample(UserIntegralExchangeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_user_integral_exchange
     *
     * @mbggenerated Fri Sep 18 16:04:54 CST 2015
     */
    UserIntegralExchange selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_user_integral_exchange
     *
     * @mbggenerated Fri Sep 18 16:04:54 CST 2015
     */
    int updateByExampleSelective(@Param("record") UserIntegralExchange record, @Param("example") UserIntegralExchangeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_user_integral_exchange
     *
     * @mbggenerated Fri Sep 18 16:04:54 CST 2015
     */
    int updateByExample(@Param("record") UserIntegralExchange record, @Param("example") UserIntegralExchangeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_user_integral_exchange
     *
     * @mbggenerated Fri Sep 18 16:04:54 CST 2015
     */
    int updateByPrimaryKeySelective(UserIntegralExchange record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_user_integral_exchange
     *
     * @mbggenerated Fri Sep 18 16:04:54 CST 2015
     */
    int updateByPrimaryKey(UserIntegralExchange record);
}