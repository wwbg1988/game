package com.ssic.game.common.mapper;

import com.ssic.game.common.pojo.Position;
import com.ssic.game.common.pojo.PositionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PositionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_position
     *
     * @mbggenerated Fri Jun 26 21:19:17 CST 2015
     */
    int countByExample(PositionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_position
     *
     * @mbggenerated Fri Jun 26 21:19:17 CST 2015
     */
    int deleteByExample(PositionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_position
     *
     * @mbggenerated Fri Jun 26 21:19:17 CST 2015
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_position
     *
     * @mbggenerated Fri Jun 26 21:19:17 CST 2015
     */
    int insert(Position record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_position
     *
     * @mbggenerated Fri Jun 26 21:19:17 CST 2015
     */
    int insertSelective(Position record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_position
     *
     * @mbggenerated Fri Jun 26 21:19:17 CST 2015
     */
    List<Position> selectByExample(PositionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_position
     *
     * @mbggenerated Fri Jun 26 21:19:17 CST 2015
     */
    Position selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_position
     *
     * @mbggenerated Fri Jun 26 21:19:17 CST 2015
     */
    int updateByExampleSelective(@Param("record") Position record, @Param("example") PositionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_position
     *
     * @mbggenerated Fri Jun 26 21:19:17 CST 2015
     */
    int updateByExample(@Param("record") Position record, @Param("example") PositionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_position
     *
     * @mbggenerated Fri Jun 26 21:19:17 CST 2015
     */
    int updateByPrimaryKeySelective(Position record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_ims_position
     *
     * @mbggenerated Fri Jun 26 21:19:17 CST 2015
     */
    int updateByPrimaryKey(Position record);
}