package com.ssic.catering.base.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssic.catering.base.dao.SensitiveDao;
import com.ssic.catering.base.dao.SensitiveValveConfDao;
import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.dto.SensitiveDto;
import com.ssic.catering.base.pojo.Sensitive;
import com.ssic.catering.base.pojo.SensitiveValveConf;
import com.ssic.catering.base.service.ISensitiveService;
import com.ssic.util.BeanUtils;
import com.ssic.util.constants.DataStatus;

/**
 * 		
 * <p>Title: SensitiveServiceImpl </p>
 * <p>Description: 敏感词操作实现类</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 张亚伟	
 * @date 2015年8月11日 下午9:59:46	
 * @version 1.0
 * <p>修改人：张亚伟</p>
 * <p>修改时间：2015年8月11日 下午9:59:46</p>
 * <p>修改备注：</p>
 */
@Service
public class SensitiveServiceImpl implements ISensitiveService
{

    @Autowired
    private SensitiveDao sensitiveDao;
    
    @Autowired
    private SensitiveValveConfDao confDao;

    @Override
    public Sensitive getSensitiveById(String id)
    {

        return sensitiveDao.getSensitiveById(id);
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.ISensitiveService#delete(com.ssic.catering.base.dto.SensitiveDto)   
    */
    @Override
    public void delete(Sensitive tempSensitive)
    {
        tempSensitive.setStat(DataStatus.DISABLED);
        sensitiveDao.updateByPrimaryKey(tempSensitive);

    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.ISensitiveService#finByName(java.lang.String)   
    */
    @Override
    public SensitiveDto finByName(String sensitiveName,String cafetoriumId)
    {
        Sensitive param = new Sensitive();
        param.setSensitiveName(sensitiveName);
        param.setCafetoriumId(cafetoriumId);
        List<Sensitive> list = sensitiveDao.findBy(param);
        if (!CollectionUtils.isEmpty(list))
        {
            SensitiveDto sivive = new SensitiveDto();
            BeanUtils.copyProperties(list.get(0), sivive);
            return sivive;
        }
        return null;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.ISensitiveService#add(com.ssic.catering.base.dto.SensitiveDto)   
    */
    @Override
    public void add(SensitiveDto sensitiveDto)
    {
        Sensitive sensitive = new Sensitive();
        BeanUtils.copyProperties(sensitiveDto, sensitive);
        sensitive.setCreateTime(new Date());
        sensitive.setStat(DataStatus.ENABLED);
        sensitiveDao.insert(sensitive);
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.ISensitiveService#findALL(com.ssic.catering.base.dto.SensitiveDto, com.ssic.catering.base.dto.PageHelperDto)   
    */
    @Override
    public List<SensitiveDto> findALL(SensitiveDto sensitiveDto, PageHelperDto phDto,List<String> listStr)
    {
        List<SensitiveDto> listsen = new ArrayList<SensitiveDto>();
        Sensitive param = new Sensitive();
        BeanUtils.copyProperties(sensitiveDto, param);
        List<Sensitive> list = sensitiveDao.findAllBy(param, phDto,listStr);
        if (!CollectionUtils.isEmpty(list))
        {
            List<SensitiveDto> slist = new ArrayList<SensitiveDto>();
            for(Sensitive s : list){
                SensitiveDto dto=new SensitiveDto();
                dto.setId(s.getId());
                dto.setSensitiveName(s.getSensitiveName());
                dto.setCreateTime(s.getCreateTime());
                dto.setLastUpdateTime(s.getLastUpdateTime());
                dto.setStat(s.getStat());
                dto.setWarning(s.getWarning());
                dto.setValveId(s.getValveId());
                dto.setCafetoriumId(s.getCafetoriumId());
                if(s.getValveId() != null){
                    SensitiveValveConf conf = confDao.getSensitiveConfById(s.getValveId());
                    dto.setValveCount(conf.getValveCount());
                    dto.setValvePercent(conf.getValvePercent());
                }
                slist.add(dto);
            }
            return slist;

        }
        return listsen;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.ISensitiveService#findCount()   
    */
    @Override
    public int findCount(SensitiveDto sensitiveDto,List<String> listStr)
    {
        Sensitive param = new Sensitive();
        BeanUtils.copyProperties(sensitiveDto, param);
        int counts = sensitiveDao.findCountBy(param,listStr);
        return counts;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.ISensitiveService#update(com.ssic.catering.base.dto.SensitiveDto)   
    */
    @Override
    public void update(SensitiveDto sensitiveDto)
    {
        Sensitive sensitive = new Sensitive();
        BeanUtils.copyProperties(sensitiveDto, sensitive);
        sensitive.setStat(DataStatus.ENABLED);
        sensitiveDao.updateByPrimaryKey(sensitive);
    }

}
