package com.ssic.catering.base.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssic.catering.base.dto.PageConfigDto;
import com.ssic.catering.base.pojo.PageConfigExample;

/**
 * 		
 * <p>Title: PageConfigExMapper </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author zhuzhen	
 * @date 2015年11月23日 下午3:08:54	
 * @version 1.0
 * <p>修改人：zhuzhen</p>
 * <p>修改时间：2015年11月23日 下午3:08:54</p>
 * <p>修改备注：</p>
 */
public interface PageConfigExMapper 
{
  /**
   * 
   * selectPageConfigDtoBy：多表分页查询
   * @return
   * @exception	
   * @author zhuzhen
   * @date 2015年11月23日 下午3:09:34
   */
  List<PageConfigDto> selectPageConfigDtoBy(@Param("example")PageConfigExample example, @Param("increment")PageConfigDto pageConfigDto);
  
  /**
   * 
   * selectPageConfigDtoCountBy：查询总条数
   * @param example
   * @param pageConfigDto
   * @return
   * @exception	
   * @author zhuzhen
   * @date 2015年11月23日 下午5:25:21
   */
  long selectPageConfigDtoCountBy(@Param("example")PageConfigExample example, @Param("increment")PageConfigDto pageConfigDto);
}