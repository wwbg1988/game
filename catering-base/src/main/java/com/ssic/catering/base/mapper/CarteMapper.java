package com.ssic.catering.base.mapper;

import com.ssic.catering.base.pojo.Carte;
import com.ssic.catering.base.pojo.CarteExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CarteMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_carte
     *
     * @mbggenerated Tue Sep 22 10:32:06 CST 2015
     */
    int countByExample(CarteExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_carte
     *
     * @mbggenerated Tue Sep 22 10:32:06 CST 2015
     */
    int deleteByExample(CarteExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_carte
     *
     * @mbggenerated Tue Sep 22 10:32:06 CST 2015
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_carte
     *
     * @mbggenerated Tue Sep 22 10:32:06 CST 2015
     */
    int insert(Carte record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_carte
     *
     * @mbggenerated Tue Sep 22 10:32:06 CST 2015
     */
    int insertSelective(Carte record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_carte
     *
     * @mbggenerated Tue Sep 22 10:32:06 CST 2015
     */
    List<Carte> selectByExample(CarteExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_carte
     *
     * @mbggenerated Tue Sep 22 10:32:06 CST 2015
     */
    Carte selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_carte
     *
     * @mbggenerated Tue Sep 22 10:32:06 CST 2015
     */
    int updateByExampleSelective(@Param("record") Carte record, @Param("example") CarteExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_carte
     *
     * @mbggenerated Tue Sep 22 10:32:06 CST 2015
     */
    int updateByExample(@Param("record") Carte record, @Param("example") CarteExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_carte
     *
     * @mbggenerated Tue Sep 22 10:32:06 CST 2015
     */
    int updateByPrimaryKeySelective(Carte record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ctr_carte
     *
     * @mbggenerated Tue Sep 22 10:32:06 CST 2015
     */
    int updateByPrimaryKey(Carte record);
}