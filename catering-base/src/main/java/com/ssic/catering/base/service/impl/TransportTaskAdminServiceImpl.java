package com.ssic.catering.base.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ssic.catering.base.service.ITransportTaskAdminService;
import com.ssic.catering.lbs.dao.TransportTaskAdminDao;
import com.ssic.catering.lbs.dto.TransportTaskAdminDto;
import com.ssic.catering.lbs.pojo.TransportTask;
import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.util.BeanUtils;

@Service
public class TransportTaskAdminServiceImpl implements ITransportTaskAdminService
{
    private static final Logger log = Logger.getLogger(TransportTaskAdminServiceImpl.class);
    
    @Autowired
    private TransportTaskAdminDao transportTaskAdminDao;
    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.catering.lbs.service.TransportTaskService#getTransportTaskDtoBy(com.ssic.catering.lbs.dto.TransportTaskDto, com.ssic.game.common.dto.PageHelperDto)
     */
    @Override
    public List<TransportTaskAdminDto> getTransportTaskAdminDtoBy(TransportTaskAdminDto transportTaskDto,
        PageHelperDto pageHelperDto)
    {
        if(transportTaskDto != null)
        {
            TransportTask param = new TransportTask();
            BeanUtils.copyProperties(transportTaskDto, param);
            return transportTaskAdminDao.findBy(param, pageHelperDto);
        }
        
        return null;        
    }
    
    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.catering.lbs.service.TransportTaskService#getTransportTaskDtoCountBy(com.ssic.catering.lbs.dto.TransportTaskDto)
     */
    @Override
    public long getTransportTaskAdminDtoCountBy(TransportTaskAdminDto transportTaskDto)
    {
        if(transportTaskDto != null)
        {
            TransportTask param = new TransportTask();
            BeanUtils.copyProperties(transportTaskDto, param);
            return transportTaskAdminDao.findCoutBy(param);
        }
        
        return 0;    
    }
    
    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.catering.lbs.service.TransportTaskService#addTransportTask(com.ssic.catering.lbs.dto.TransportTaskDto)
     */
    @Override
    public int addTransportTask(TransportTaskAdminDto transportTaskDto)
    {
        if(transportTaskDto != null)
        {
            TransportTask param = new TransportTask();
            BeanUtils.copyProperties(transportTaskDto, param);
            return transportTaskAdminDao.insert(param);
        }
        
        return 0;    
    }
    
    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.catering.lbs.service.TransportTaskService#updateTransportTask(com.ssic.catering.lbs.dto.TransportTaskDto)
     */
    @Override
    public int updateTransportTask(TransportTaskAdminDto transportTaskDto)
    {
        if(transportTaskDto != null)
        {
            TransportTask param = new TransportTask();
            BeanUtils.copyProperties(transportTaskDto, param);
            return transportTaskAdminDao.updateByPrimaryKeySelective(param);
        }
        
        return 0;   
    }
    
    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.catering.lbs.service.TransportTaskService#findById(java.lang.String)
     */
    @Override
    public TransportTaskAdminDto findById(String id)
    {
        if(!StringUtils.isEmpty(id))
        {
            TransportTask resultset = transportTaskAdminDao.findById(id);
            if(resultset != null)
            {
                TransportTaskAdminDto result = new TransportTaskAdminDto();
                BeanUtils.copyProperties(resultset, result);
                return result;
            }
        }
        return null;
    }


    


    
    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.catering.lbs.service.TransportTaskService#dispatchingTask(com.ssic.catering.lbs.dto.TransportTaskDto)
     */
    @Override
    @Transactional
    public int dispatchingTask(TransportTaskAdminDto transportTaskDto)
    {
        if(transportTaskDto == null)
        {
            log.error("参数transportTaskDto不能为空");
            return 0;
        }
        
        //任务id
        if(StringUtils.isEmpty(transportTaskDto.getId()))
        {
            log.error("任务不能为空");
            return 0;
        }
        
        //配送人id
        if(StringUtils.isEmpty(transportTaskDto.getDriverId()))
        {
            log.error("配送人不能为空");
            return 0;
        }
        
        TransportTask param = new TransportTask();
        param.setId(transportTaskDto.getId());//id
        param.setDriverId(transportTaskDto.getDriverId());//配送人
        param.setDepartPlace(transportTaskDto.getDepartPlace());//出发地
        
        int count = transportTaskAdminDao.updateByPrimaryKeySelective(param);
        if(count > 0)
        {
            return count;
        }
        
        log.error("配送失败");
        return 0;
    }



}

