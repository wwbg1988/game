package com.ssic.game.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssic.game.common.dto.SignDto;
import com.ssic.game.common.pojo.Sign;
import com.ssic.game.common.pojo.SignExample;

public interface SignExMapper {
    Integer findCount(@Param("sign")Sign sign);
    
    List<SignDto> findByDate(@Param("sign")Sign sign,@Param("beginRows")int beginRows,@Param("endRows") int endRows);
    
    List<SignDto> findByUserId(@Param("signDto")SignDto signdto);
    
    /**
     * 表连接查询
     * @author 朱振	
     * @date 2015年10月26日 下午7:48:02	
     * @version 1.0
     * @param example
     * @param beginRows
     * @param endRows
     * @return SignDto
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年10月26日 下午7:48:02</p>
     * <p>修改备注：</p>
     */
    List<SignDto> findSignDtosBy(@Param("example")SignExample example,@Param("userName")String userName,@Param("userAccount")String userAccount);
    
    
    /**
     * 查询出findSignDtosBy的结果集的条数
     * @see com.ssic.game.common.mapper.SignExMapper.findSignDtosBy(@Param("example")SignExample example, @Param("beginRows")Integer beginRows, @Param("endRows")Integer endRows)
     * @author 朱振	
     * @date 2015年10月27日 上午10:56:47	
     * @version 1.0
     * @param example
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年10月27日 上午10:56:47</p>
     * <p>修改备注：</p>
     */
    Long findSignDtosCountBy(@Param("example")SignExample example,@Param("userName")String userName,@Param("userAccount")String userAccount);
}