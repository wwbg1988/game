package com.ssic.catering.base.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssic.catering.base.dao.NotifyDao;
import com.ssic.catering.base.dto.NotifyDto;
import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.pojo.Notify;
import com.ssic.catering.base.pojo.PageHelper;
import com.ssic.catering.base.service.INotifyService;
import com.ssic.util.BeanUtils;

@Service
public class NotifyServiceImpl implements INotifyService
{

    private static final Logger log = Logger.getLogger(NotifyServiceImpl.class);

    @Autowired
    private NotifyDao notifyDao;

    @Override
    public List<NotifyDto> findBy(NotifyDto notifyDto)
    {
        // TODO Auto-generated method stub
        Notify notify = new Notify();
        BeanUtils.copyProperties(notifyDto, notify);
        List<Notify> list = notifyDao.findBy(notify);
        List<NotifyDto> listdto = BeanUtils.createBeanListByTarget(list, NotifyDto.class);
        return listdto;
    }

    @Override
    public List<NotifyDto> findBy(NotifyDto notifyDto, PageHelper ph)
    {
        // TODO Auto-generated method stub
        PageHelperDto phdto = new PageHelperDto();
        phdto.setOrder(ph.getOrder());
        phdto.setPage(ph.getPage());
        phdto.setRows(ph.getRows());
        phdto.setSort(ph.getSort());
        phdto.setBeginRow((ph.getPage() - 1) * ph.getRows());

        Notify notify = new Notify();
        BeanUtils.copyProperties(notifyDto, notify);
        List<Notify> list = notifyDao.findBy(notify, phdto);
        List<NotifyDto> listdto = BeanUtils.createBeanListByTarget(list, NotifyDto.class);
        return listdto;
    }

    @Override
    public int findCount(NotifyDto notifyDto)
    {
        // TODO Auto-generated method stub
        Notify notify = new Notify();
        BeanUtils.copyProperties(notifyDto, notify);
        return notifyDao.findCount(notify);
    }

    @Override
    public void insertNotify(NotifyDto notifyDto)
    {
        // TODO Auto-generated method stub
        Notify notify = new Notify();
        BeanUtils.copyProperties(notifyDto, notify);
        notifyDao.insertNotify(notify);
    }

    @Override
    public void updateNotify(NotifyDto notifyDto)
    {
        // TODO Auto-generated method stub
        Notify notify = new Notify();
        BeanUtils.copyProperties(notifyDto, notify);
        notifyDao.updateNotify(notify);
    }

    @Override
    public void deleteNotify(NotifyDto notifyDto)
    {
        // TODO Auto-generated method stub
        Notify notify = new Notify();
        BeanUtils.copyProperties(notifyDto, notify);
        notifyDao.deleteNotify(notify);
    }

    @Override
    public NotifyDto findById(String id)
    {
        // TODO Auto-generated method stub
        Notify notify = notifyDao.findById(id);
        NotifyDto notifyDto = new NotifyDto();
        BeanUtils.copyProperties(notify, notifyDto);
        return notifyDto;
    }

    @Override
    public List<NotifyDto> findBy(NotifyDto notifyDto, int beginRow, int endRow, String searchDate)
    {
        // TODO Auto-generated method stub
        Notify notify = new Notify();
        BeanUtils.copyProperties(notifyDto, notify);
        List<Notify> list = notifyDao.findBy(notify, beginRow, endRow, searchDate);
        List<NotifyDto> listDto = BeanUtils.createBeanListByTarget(list, NotifyDto.class);
        return listDto;
    }

    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.catering.base.service.INotifyService#findBy(com.ssic.catering.base.dto.NotifyDto, java.util.List, com.ssic.catering.base.pojo.PageHelper)
     */
    @Override
    public List<NotifyDto> findBy(NotifyDto notifyDto, List<String> projectIds, PageHelper ph)
    {
        log.info("parameters:notifyDto=" + notifyDto + ";projectIds=" + projectIds + ";ph=" + ph);
        if (notifyDto == null)
        {
            log.error("参数notifyDto不能为空");
            return null;
        }

        if (ph == null)
        {
            log.error("参数pageHelper不能为空");
            return null;
        }

        Notify notify = new Notify();
        BeanUtils.copyProperties(notifyDto, notify);
        
        List<Notify> list = notifyDao.findBy(notify, projectIds, ph);
        if(CollectionUtils.isEmpty(list))
        {
            return null;
        }
        List<NotifyDto> listdto = BeanUtils.createBeanListByTarget(list, NotifyDto.class);
        return listdto;
    }

    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.catering.base.service.INotifyService#findCountBy(com.ssic.catering.base.dto.NotifyDto, java.util.List)
     */
    @Override
    public Integer findCountBy(NotifyDto notifyDto, List<String> projectIds)
    {
        log.info("parameters:notifyDto=" + notifyDto + ";projectIds=" + projectIds);
        if (notifyDto == null)
        {
            log.error("参数notifyDto不能为空");
            return null;
        }
        
        Notify param = new Notify();
        BeanUtils.copyProperties(notifyDto, param);
        
        return notifyDao.findCountBy(param, projectIds);
    }

}
