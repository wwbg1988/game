/**
 * 
 */
package com.ssic.catering.base.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssic.catering.base.dao.SensitiveWarningReportDao;
import com.ssic.catering.base.dto.AddressDto;
import com.ssic.catering.base.dto.AddressUserDto;
import com.ssic.catering.base.dto.CountSensitiveDto;
import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.dto.ResultsData;
import com.ssic.catering.base.dto.SensitiveValveCountDto;
import com.ssic.catering.base.dto.SensitiveWarningReportDto;
import com.ssic.catering.base.pojo.Address;
import com.ssic.catering.base.pojo.SensitiveWarningReport;
import com.ssic.catering.base.service.IAddressService;
import com.ssic.catering.base.service.IAddressUserService;
import com.ssic.catering.base.service.ISensitiveWarningReportService;
import com.ssic.catering.common.util.AppResponse;
import com.ssic.util.BeanUtils;
import com.ssic.util.constants.DataStatus;
import com.ssic.util.model.Response;

/**		
 * <p>Title: ISensitiveWarningReportServiceImpl </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author pengcheng.yang	
 * @date 2015年8月11日 下午4:32:09	
 * @version 1.0
 * <p>修改人：pengcheng.yang</p>
 * <p>修改时间：2015年8月11日 下午4:32:09</p>
 * <p>修改备注：</p>
 */
@Service
public class SensitiveWarningReportServiceImpl implements ISensitiveWarningReportService
{

    @Autowired
    private SensitiveWarningReportDao sensitiveReport;
    
    @Autowired
    private IAddressService addressService;
    
    @Autowired
    private IAddressUserService addressUserService;

    /** 
     * (non-Javadoc)   
     * @see com.ssic.catering.base.service.ISensitiveWarningReportService#queryCountSensitive(java.lang.String) 
     * 统计每个食堂的预警敏感词出现的次数  
     */
    @Override
    public List<CountSensitiveDto> queryCountSensitive(String cafetoriumId)
    {
        if (!StringUtils.isEmpty(cafetoriumId))
        {
            return this.sensitiveReport.queryCountSensitive(cafetoriumId);
        }
        return null;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.ISensitiveWarningReportService#queryWarningNorm(java.lang.String)   
    */
    @Override
    public List<SensitiveValveCountDto> queryWarningNorm(String sensitiveId)
    {
        if (!StringUtils.isEmpty(sensitiveId))
        {
            return this.sensitiveReport.queryWarningNorm(sensitiveId);
        }
        return null;
    }

    /**
     * 
     * totalWarningReportMessages：查询当天有没有产生预警记录
     * @param cafetoriumId
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年8月11日 下午6:13:05
     */
    public int totalWarningReportMessages(String cafetoriumId)
    {
        if (!StringUtils.isEmpty(cafetoriumId))
        {
            return this.sensitiveReport.totalWarningReportMessages(cafetoriumId);
        }
        return -1;
    }

    /**
     * findByCafetoriumId：通过食堂id查询食堂敏感词预警报表
     * @param cafeId
     * @return
     * @exception   
     * @author刘博
     * @date 2015年8月12日 上午9:13:05
     */
    @Override
    public List<SensitiveWarningReportDto> findByCafetoriumId(String cafeId, PageHelperDto ph,
        String searchDate)
    {
        List<SensitiveWarningReport> list = sensitiveReport.findByCafetoriumId(cafeId, ph, searchDate);
        if (!CollectionUtils.isEmpty(list))
        {
            return BeanUtils.createBeanListByTarget(list, SensitiveWarningReportDto.class);
        }
        return null;
    }

    
    /** 
     * (non-Javadoc)   
     * @see com.ssic.catering.base.service.ISensitiveWarningReportService#insertSensitiveWarning(java.util.List)   
     */
    @Override
    public void insertSensitiveWarning(List<SensitiveWarningReportDto> list)
    {
        if(list != null){
            sensitiveReport.insertSensitiveWarning(list);
        }
    }

    /**
     * querySensitiveWarning：分区域级别去查询预警信息
     * @param userId
     * @return
     * @exception   
     * @author pengcheng.yang
     * @date 2015年8月14日 上午10:52:50
     */
    @Override
    public Response<List<ResultsData>> querySensitiveWarning(String userId)
    {
        List<ResultsData> resultList = new ArrayList<ResultsData>();
        //根据当前登录用户Id查询出addressId
        AddressDto dto = this.addressService.queryAddressId(userId);
        //查询下级地区的Id
        List<AddressDto> childrenList = addressService.queryAddressIdAndParentIds(dto.getId());
        if(childrenList != null && (childrenList.size()>0)){
            for (int i = 0; i < childrenList.size(); i++)
            {
                //查询出每一个下级Id下面包含的下级Id
                List<AddressDto> dtoList = addressService.queryCityId(childrenList.get(i).getAddressCode());
                //把所有包含的下级Id作为条件去敏感词预警表中查询对应区域的信息
                List<SensitiveWarningReportDto> list = sensitiveReport.querySwarningReport(dtoList);
                if(list != null){
                    for (int j = 0; j < list.size(); j++)
                    {
                        //为每一条预警消息拼接Url地址
                        list.get(j).setUrl(AppResponse.SENSITIVE_WARNING_URL + "?reportId=" + list.get(j).getId()+"&sensitiveId="+list.get(j).getSensitiveId());
                        Date date = list.get(j).getCreateTime();
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        String dateString = formatter.format(date);
                        list.get(j).setDateString(dateString);
                        
                    }
                    ResultsData result = new ResultsData();
                    //标注这条预警消息属于那个区域的
                    result.setAddressId(childrenList.get(i).getId());
                    result.setAddressName(childrenList.get(i).getAddressName());
                    result.setSenList(list);
                    resultList.add(result);
                }
            }
        }else{
             //此种情况为进来一个市级用户登录进来只显示一个市的预警信息
            List<AddressDto> address = new ArrayList<AddressDto>();
            address.add(dto);
            
          //查询出一个市级的所有预警信息
            List<SensitiveWarningReportDto> list = null;
            //根据userId查询当前登录用户的addressType
            List<AddressUserDto> listDto =  addressUserService.finAddressListByUserId(userId);
            
            if(listDto.size()>0){
                //食堂负责人查看自己负责食堂的
                if(listDto.get(0).getAddressType().equals(4)){
                    list = sensitiveReport.querySwarningReportCid(address.get(0).getId(), userId);
                }
                //市负责人查看该市下面的所有的食堂
                if(listDto.get(0).getAddressType().equals(3)){
                    //查询出一个市级的所有预警信息
                    list = sensitiveReport.querySwarningReport(address);
                }
            }
            
            if(list != null){
                ResultsData result = null;
                for (int j = 0; j < list.size(); j++)
                {
                    //为每一条预警消息拼接Url地址
                    list.get(j).setUrl(AppResponse.SENSITIVE_WARNING_URL + "?reportId=" + list.get(j).getId()+"&sensitiveId="+list.get(j).getSensitiveId());
                    result = new ResultsData();
                    result.setAddressId(list.get(j).getId());
                    Address addresssen = addressService.findAddressById(list.get(j).getAddressId());
                    if(addresssen!=null){
                    	result.setAddressName(addresssen.getAddressName());
                    }else{
                    	result.setAddressName("");
                    }
                    result.setSenList(list);
                }
                resultList.add(result);
            }
        }
        
        if(resultList != null){
            return new Response<List<ResultsData>>(DataStatus.HTTP_SUCCESS, "",resultList);
        }
            return new Response<List<ResultsData>>(DataStatus.HTTP_FAILE,"无法查询到预警词信息", null);
    }
    
     /** 
     * (non-Javadoc)   
     * @see com.ssic.catering.base.service.ISensitiveWarningReportService#querySwarningReport(java.lang.String)
     * @author pengcheng.yang  
     * @desc 根据区域Id查询出对应区域的预警信息
     */
    @Override
    public List<SensitiveWarningReportDto> querySwarningReport(List<AddressDto> dtoList)
    {
        return sensitiveReport.querySwarningReport(dtoList);
    }

}
