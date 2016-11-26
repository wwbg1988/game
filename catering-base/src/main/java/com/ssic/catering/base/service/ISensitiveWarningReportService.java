package com.ssic.catering.base.service;

import java.util.List;

import com.ssic.catering.base.dto.AddressDto;
import com.ssic.catering.base.dto.CountSensitiveDto;
import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.dto.ResultsData;
import com.ssic.catering.base.dto.SensitiveValveCountDto;
import com.ssic.catering.base.dto.SensitiveWarningReportDto;
import com.ssic.util.model.Response;

/**		
 * <p>Title: SensitiveWarningReportService </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author pengcheng.yang	
 * @date 2015年8月11日 下午4:30:28	
 * @version 1.0
 * <p>修改人：pengcheng.yang</p>
 * <p>修改时间：2015年8月11日 下午4:30:28</p>
 * <p>修改备注：</p>
 */
public interface ISensitiveWarningReportService
{
    /**
     * 
     * queryCountSensitive：统计每个食堂的预警敏感词出现的次数接口
     * @param cafetoriumId
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年8月11日 下午4:31:33
     */
    List<CountSensitiveDto> queryCountSensitive(String cafetoriumId);
    
    /**
     * 
     * queryWarningNorm：查询敏感词阀值
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年8月11日 下午5:42:45
     */
    List<SensitiveValveCountDto> queryWarningNorm(String sensitiveId);
    /**
     * 
     * totalWarningReportMessages：查询当天有没有产生预警记录
     * @param cafetoriumId
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年8月11日 下午6:15:52
     */
    int totalWarningReportMessages(String cafetoriumId);

    
    /**     
     * findByCafetoriumId：通过食堂id查询食堂敏感词预警报表
     * @param cafeId
     * @param searchDate 
     * @param ph 
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年8月12日 上午9:08:53	 
     */
    List<SensitiveWarningReportDto> findByCafetoriumId(String cafeId, PageHelperDto ph, String searchDate);
    /**
     * 
     * insertSensitiveWarning：保存敏感词预警 消息
     * @param list
     * @exception	
     * @author pengcheng.yang
     * @date 2015年8月12日 上午9:48:39
     */
    void insertSensitiveWarning(List<SensitiveWarningReportDto> list);
    
    
    /**
     * 
     * querySensitiveWarning：查询用户拥有权限查看的预警信息
     * @param addressId
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年8月13日 下午1:23:02
     */
    Response<List<ResultsData>> querySensitiveWarning(String userId);
    
    /**
     * 
     * querySwarningReport：查询拥有权限的敏感词预警
     * @param addressId
     * @return
     * @exception   
     * @author pengcheng.yang
     * @date 2015年8月13日 下午2:38:17
     */
   List<SensitiveWarningReportDto> querySwarningReport(List<AddressDto> dtoList);
}

