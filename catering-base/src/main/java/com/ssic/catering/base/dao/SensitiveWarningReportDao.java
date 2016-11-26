/**
 * 
 */
package com.ssic.catering.base.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ssic.catering.base.dto.AddressDto;
import com.ssic.catering.base.dto.CountSensitiveDto;
import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.dto.SensitiveValveCountDto;
import com.ssic.catering.base.dto.SensitiveWarningReportDto;
import com.ssic.catering.base.mapper.SensitiveWarningReportExMapper;
import com.ssic.catering.base.mapper.SensitiveWarningReportMapper;
import com.ssic.catering.base.pojo.SensitiveWarningReport;
import com.ssic.catering.base.pojo.SensitiveWarningReportExample;
import com.ssic.catering.base.pojo.SensitiveWarningReportExample.Criteria;
import com.ssic.catering.common.util.DateUtil;
import com.ssic.util.BeanUtils;
import com.ssic.util.UUIDGenerator;
import com.ssic.util.base.MyBatisBaseDao;
import com.ssic.util.constants.DataStatus;

/**		
 * <p>Title: SensitiveWarningReportDao </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author pengcheng.yang	
 * @date 2015年8月11日 下午4:23:38	
 * @version 1.0
 * <p>修改人：pengcheng.yang</p>
 * <p>修改时间：2015年8月11日 下午4:23:38</p>
 * <p>修改备注：</p>
 */
@Repository
public class SensitiveWarningReportDao extends MyBatisBaseDao<SensitiveWarningReport>
{

    @Autowired
    @Getter
    private SensitiveWarningReportMapper mapper;

    @Autowired
    private SensitiveWarningReportExMapper sMapper;

    /**
     * 
     * queryCountSensitive：统计每个食堂的预警敏感词出现的次数
     * @param cafetoriumId
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年8月11日 下午4:29:24
     */
    public List<CountSensitiveDto> queryCountSensitive(String cafetoriumId)
    {
        List<CountSensitiveDto> list = sMapper.queryCountSensitive(cafetoriumId);
        if (list != null)
        {
            return list;
        }
        return null;
    }

    /**
     * 
     * queryWarningNorm：查询敏感词阀值
     * @param sensitiveId
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年8月11日 下午5:39:13
     */
    public List<SensitiveValveCountDto> queryWarningNorm(String sensitiveId)
    {
        List<SensitiveValveCountDto> list = sMapper.queryWarningNorm(sensitiveId);
        if (list != null)
        {
            return list;
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
     * @date 2015年8月11日 下午6:10:48
     */
    public int totalWarningReportMessages(String cafetoriumId)
    {
        int num = sMapper.totalWarningReportMessages(cafetoriumId);
        if (num == 0)
        {
            return num;
        }
        return -1;
    }

    /**
     * 
     * findByCafetoriumId：通过食堂id查询食堂敏感词预警报表
     * @param cafeId
     * @param searchDate 
     * @param ph 
     * @return
     * @exception   
     * @author刘博
     * @date 2015年8月12日 上午9:13:05
     */
    public List<SensitiveWarningReport> findByCafetoriumId(String cafeId, PageHelperDto ph, String searchDate)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SensitiveWarningReportExample example = new SensitiveWarningReportExample();
        Criteria criteria = example.createCriteria();
        Date localDate;

        if (!StringUtils.isEmpty(cafeId))
        {
            criteria.andCafetoriumIdEqualTo(cafeId);
        }
        if (ph != null && !StringUtils.isEmpty(String.valueOf(ph.getBeginRow()))
            && !StringUtils.isEmpty(String.valueOf(ph.getRows())))
        {
            int beginRow = ph.getBeginRow();
            int rows = ph.getRows();
            example.setOrderByClause("create_time desc limit " + beginRow + "," + rows);
        }
        else
        {
            example.setOrderByClause("create_time desc");
        }

        if (searchDate != null)
        { //查询日期为localDate的记录;
          //获取当天日期
            try
            {
                localDate = simpleDateFormat.parse(searchDate);
                //获取下一天
                Date nextDate = DateUtil.getSpecifiedDayBefore(searchDate);
                criteria.andCreateTimeBetween(localDate, nextDate);
            }
            catch (java.text.ParseException e)
            {
                //  对异常进行简要描述
            }
        }
        criteria.andStatEqualTo(DataStatus.ENABLED);
        return mapper.selectByExample(example);
    }

    /**
     * 
     * findByCafetoriumId：通过食堂id查询食堂敏感词预警报表
     * @param cafeId
     * @return
     * @exception   
     * @author刘博
     * @date 2015年8月12日 上午9:13:05
     */
    public List<SensitiveWarningReport> findByCafetoriumId(String cafeId)
    {
        SensitiveWarningReportExample example = new SensitiveWarningReportExample();
        Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(cafeId))
        {
            criteria.andCafetoriumIdEqualTo(cafeId);
        }
        //criteria.andStatEqualTo(DataStatus.ENABLED);
        return mapper.selectByExample(example);
    }

    /**
     * 
     * insertSensitiveWarning：保存没项的预警信息
     * @exception   
     * @author pengcheng.yang
     * @date 2015年8月12日 上午9:15:47
     */
    public void insertSensitiveWarning(List<SensitiveWarningReportDto> list)
    {
        List<SensitiveWarningReport> wlist =
            BeanUtils.createBeanListByTarget(list, SensitiveWarningReport.class);
        for (int i = 0; i < wlist.size(); i++)
        {
            SensitiveWarningReport swr = wlist.get(i);
            swr.setCreateTime(new Date());
            swr.setId(UUIDGenerator.getUUID());
            swr.setStat(DataStatus.ENABLED);
            mapper.insertSelective(wlist.get(i));
        }
    }
    
    /**
     * 
     *selectByPrimaryKey ：查询关键字和预警百分比对象
     * @exception   
     * @author yuanbin 
     * @date 2015年8月13日 上午11:15:47
     */
    public SensitiveWarningReport selectByPrimaryKey(String id){
    	
    	return mapper.selectByPrimaryKey(id);
    }
    
    /**
     * 
     * querySwarningReport：查询拥有权限的敏感词预警
     * @param addressId
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年8月13日 下午2:38:17
     */
    public List<SensitiveWarningReportDto> querySwarningReport(List<AddressDto> dtoList){
        List<SensitiveWarningReportDto> list = sMapper.querySwarningReport(dtoList);
        if(list != null){
            return list;
        }
        return null;
    }
    /**
     * 
     * querySwarningReportCid：查询一个食堂负责人负责的食堂预警信息
     * @param addressId
     * @param cafeCode
     * @return
     * @exception   
     * @author pengcheng.yang
     * @date 2015年11月16日 下午12:24:41
     */
    public List<SensitiveWarningReportDto> querySwarningReportCid(String addressId,String userId){
        List<SensitiveWarningReportDto> list = sMapper.querySwarningReportCid(addressId, userId);
        if(list != null){
            return list;
        }
        return null;
    }

}
