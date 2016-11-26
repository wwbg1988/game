/**
 * 
 */
package com.ssic.game.common.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ssic.game.common.dao.ProcInstantsDao;
import com.ssic.game.common.dto.DataGridDto;
import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.game.common.dto.ProcInstantsDto;
import com.ssic.game.common.dto.TasksDto;
import com.ssic.game.common.pojo.ProcInstance;
import com.ssic.game.common.service.IProcInstanceService;
import com.ssic.game.common.service.ITasksService;
import com.ssic.game.common.util.DataStatusUtils;
import com.ssic.util.BeanUtils;

/**		
 * <p>Title: ProcInstanceServiceImpl </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博
 * @date 2015年7月2日 上午9:55:29	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年7月2日 上午9:55:29</p>
 * <p>修改备注：</p>
 */
@Service("ProcInstanceServiceImpl")
public class ProcInstanceServiceImpl implements IProcInstanceService
{
    @Autowired
    private ProcInstantsDao procInstanceDao;

    @Autowired
    private ITasksService taskService;

    /** 
     * (non-Javadoc)   
     * @see com.ssic.game.admin.service.IProcInstantsService#findAllBy(com.ssic.game.admin.dto.ProcInstantsDto)   
     */
    @Override
    public List<ProcInstantsDto> findAllBy(ProcInstantsDto procInstantsDto)
    {
        ProcInstance procInstance = new ProcInstance();
        BeanUtils.copyProperties(procInstantsDto, procInstance);
        List<ProcInstance> list = procInstanceDao.findAllBy(procInstance);
        if (list != null && list.size() > 0)
        {
            List<ProcInstantsDto> result = BeanUtils.createBeanListByTarget(list, ProcInstantsDto.class);
            return result;
        }
        return null;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.common.service.IProcInstanceService#findByInstanceId(java.lang.String)   
    */
    @Override
    @Cacheable(value = "default", key = "'game.common.dto.ProcInstantsDto:' + #instanceId")
    public ProcInstantsDto findByInstanceId(String instanceId)

    {
        ProcInstance procInstance = procInstanceDao.selectByPrimaryKey(instanceId);
        if (isNotExist(procInstance))
        {
            return null;
        }
        return BeanUtils.createBeanByTarget(procInstance, ProcInstantsDto.class);
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.common.service.IProcInstanceService#updateById(java.lang.String)   
    */
    @Override
    // @CacheEvict(value="default", key = "game.common.dto.ProcInstantsDto.procId:' + #procInstanceDto.getId()", beforeInvocation=true)
    @Caching(evict = {
        @CacheEvict(value = "default", key = "'game.common.dto.ProcInstantsDto.procId:' + #procInstanceDto.getProcId()"),
        @CacheEvict(value = "default", key = "'game.common.dto.ProcInstantsDto:' + #procInstanceDto.getId()") })
    public void updateProcInstance(ProcInstantsDto procInstanceDto)
    {

        ProcInstance procInstance = new ProcInstance();
        BeanUtils.copyProperties(procInstanceDto, procInstance);
        procInstance.setLastUpdateTime(new Date());
        procInstanceDao.updateByPrimaryKeySelective(procInstance);

    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.common.service.IProcInstanceService#findByProcId(java.lang.String)   
    */
    @Override
    @Cacheable(value = "default", key = "'game.common.dto.ProcInstantsDto.procId:' + #procId")
    public ProcInstantsDto findByProcId(String procId)
    {
        ProcInstance param = new ProcInstance();
        param.setProcId(procId);
        List<ProcInstance> procInstances = procInstanceDao.findAllBy(param);
        ProcInstance procInstance = CollectionUtils.isEmpty(procInstances) ? null : procInstances.get(0);
        if (isNotExist(procInstance))
        {
            return null;
        }
        return BeanUtils.createBeanByTarget(procInstance, ProcInstantsDto.class);
    }

    private boolean isNotExist(ProcInstance procInstance)
    {
        return procInstance == null || DataStatusUtils.isNotEnabled(procInstance.getStat());
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.common.service.IProcInstanceService#findAll(com.ssic.game.common.dto.ProcInstantsDto, com.ssic.game.common.dto.PageHelperDto)   
    */
    @Override
    public DataGridDto findAll(ProcInstantsDto instanceDto, PageHelperDto phDto)
    {
        List<ProcInstantsDto> listDtos = new ArrayList<ProcInstantsDto>();
        DataGridDto dg = new DataGridDto();
        ProcInstance procInstance = new ProcInstance();
        BeanUtils.copyProperties(instanceDto, procInstance);
        List<ProcInstance> listDto = procInstanceDao.findAll(procInstance, phDto);
        for (ProcInstance instance : listDto)
        {
            ProcInstantsDto instanceD = new ProcInstantsDto();
            BeanUtils.copyProperties(instance, instanceD);
            if (!StringUtils.isEmpty(instance.getNowTaskId()))
            {
                if (instance.getNowTaskId().equals("FINISH"))
                {
                    instanceD.setTaskName("FINISH");
                }
                else
                {
                    TasksDto taskDto = taskService.findByTaskId(instance.getNowTaskId());
                    instanceD.setTaskName(taskDto.getName());
                }
            }

            listDtos.add(instanceD);

        }
        int counts = procInstanceDao.findCountBy(procInstance);
        dg.setTotal(Long.valueOf(counts));
        dg.setRows(listDtos);
        return dg;
    }
}
