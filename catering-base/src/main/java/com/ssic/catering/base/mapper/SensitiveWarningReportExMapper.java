package com.ssic.catering.base.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssic.catering.base.dao.SensitiveWarningReportDao;
import com.ssic.catering.base.dto.AddressDto;
import com.ssic.catering.base.dto.CountSensitiveDto;
import com.ssic.catering.base.dto.SensitiveValveCountDto;
import com.ssic.catering.base.dto.SensitiveWarningReportDto;

/**
 * 		
 * <p>Title: SensitiveWarningReportExMapper </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author pengcheng.yang	
 * @date 2015年8月11日 下午4:10:56	
 * @version 1.0
 * <p>修改人：pengcheng.yang</p>
 * <p>修改时间：2015年8月11日 下午4:10:56</p>
 * <p>修改备注：</p>
 */
public interface SensitiveWarningReportExMapper {
    
    List<CountSensitiveDto> queryCountSensitive(@Param("cafetoriumId")String cafetoriumId);
    
    List<SensitiveValveCountDto> queryWarningNorm(@Param("sensitiveId")String sensitiveId);
    
    int  totalWarningReportMessages(@Param("cafetoriumId")String cafetoriumId);
    
    List<SensitiveWarningReportDto> querySwarningReport(@Param("dtoList")List<AddressDto> dtoList);
    
    List<SensitiveWarningReportDto> querySwarningReportCid(@Param("addressId")String addressId,@Param("userId")String userId);
}