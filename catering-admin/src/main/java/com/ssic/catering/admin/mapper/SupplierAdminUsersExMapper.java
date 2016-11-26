package com.ssic.catering.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssic.catering.lbs.dto.SupplierDto;
import com.ssic.catering.lbs.pojo.SupplierAdminUsersExample;

public interface SupplierAdminUsersExMapper 
{
   /**
    * 
    * selectSupplierDtoBy：查询用户下面关联的所有供应商
    * @param example
    * @return
    * @exception	
    * @author zhuzhen
    * @date 2015年12月8日 下午12:35:22
    */
    List<SupplierDto> selectSupplierDtoBy(@Param("example")SupplierAdminUsersExample example);
}