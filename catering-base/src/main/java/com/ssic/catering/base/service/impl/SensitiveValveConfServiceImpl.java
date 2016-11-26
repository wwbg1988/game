/**
 * 
 */
package com.ssic.catering.base.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssic.catering.base.dao.SensitiveValveConfDao;
import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.dto.SensitiveValveConfDto;
import com.ssic.catering.base.pojo.SensitiveValveConf;
import com.ssic.catering.base.service.ISensitiveValveConfService;
import com.ssic.util.BeanUtils;
import com.ssic.util.constants.DataStatus;

/**		
 * <p>Title: SensitiveValveConfServiceImpl </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author pengcheng.yang	
 * @date 2015年8月18日 上午10:37:09	
 * @version 1.0
 * <p>修改人：pengcheng.yang</p>
 * <p>修改时间：2015年8月18日 上午10:37:09</p>
 * <p>修改备注：</p>
 */
@Service
public class SensitiveValveConfServiceImpl implements ISensitiveValveConfService
{

    @Autowired
    private SensitiveValveConfDao confDao;
    
    
    /** 
     * (non-Javadoc)   
     * @see com.ssic.catering.base.service.ISensitiveValveConfService#findCountBy()   
     * @author pengcheng.yang
     */
    @Override
    public int findCountBy(List<String> listStr)
    {
        int flag = confDao.findCountBy(listStr);
        if(flag>0){
            return flag;
        }
        return 0;
    }


    
     /** 
     * (non-Javadoc)   
     * @see com.ssic.catering.base.service.ISensitiveValveConfService#findBy(com.ssic.catering.base.service.SensitiveValveConfDto, com.ssic.catering.base.dto.PageHelperDto)
     * @author pengcheng.yang   
     */
    @Override
    public List<SensitiveValveConfDto> findBy(SensitiveValveConfDto dto,PageHelperDto ph,List<String> listStr)
    {
        SensitiveValveConf conf = new SensitiveValveConf();
        BeanUtils.copyProperties(dto,conf);
        List<SensitiveValveConf> list  = confDao.findBy(conf,ph,listStr);
        if(!StringUtils.isEmpty(list)){
            return BeanUtils.createBeanListByTarget(list, SensitiveValveConfDto.class);
        }
        return null;
    }



    
     /** 
     * (non-Javadoc)   
     * @see com.ssic.catering.base.service.ISensitiveValveConfService#sensitiveVconfAdd(com.ssic.catering.base.pojo.SensitiveValveConf)
     * @author pengcheng.yang   
     */
    @Override
    public int sensitiveVconfAdd(SensitiveValveConfDto dto)
    {
        SensitiveValveConf conf = new SensitiveValveConf();
        BeanUtils.copyProperties(dto,conf);
        conf.setCreateTime(new Date());
        conf.setStat(DataStatus.ENABLED);
        return confDao.sensitiveVconfAdd(conf);
    }



    
     /** 
     * (non-Javadoc)   
     * @see com.ssic.catering.base.service.ISensitiveValveConfService#sensitiveVconfDelete(com.ssic.catering.base.dto.SensitiveValveConfDto)   
     * @author pengcheng.yang
     */
    @Override
    public int sensitiveVconfDelete(SensitiveValveConf conf)
    {
        if(!StringUtils.isEmpty(conf)){
            conf.setStat(DataStatus.DISABLED);
            conf.setLastUpdateTime(new Date());
            return confDao.sensitiveVconfDelete(conf);
        }
        return 0;
    }



    
     /** 
     * (non-Javadoc)   
     * @see com.ssic.catering.base.service.ISensitiveValveConfService#getSensitiveById(java.lang.String)   
     * @author pengcheng.yang
     */
    @Override
    public SensitiveValveConf getSensitiveConfById(String id)
    {
        return confDao.getSensitiveConfById(id);
    }



    
     /** 
     * (non-Javadoc)   
     * @see com.ssic.catering.base.service.ISensitiveValveConfService#sensitiveVconfUpdate(com.ssic.catering.base.dto.SensitiveValveConfDto)   
     * @author pengcheng.yang
     */
    @Override
    public int sensitiveVconfUpdate(SensitiveValveConfDto dto)
    {
        if(!StringUtils.isEmpty(dto)){
            SensitiveValveConf conf = new SensitiveValveConf();
            BeanUtils.copyProperties(dto, conf);
            conf.setStat(DataStatus.ENABLED);
            return confDao.sensitiveVconfUpdate(conf);
        }
        return 0;
    }

}

