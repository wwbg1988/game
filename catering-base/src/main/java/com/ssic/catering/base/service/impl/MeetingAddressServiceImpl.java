package com.ssic.catering.base.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssic.catering.base.dao.MeetingAddressDao;
import com.ssic.catering.base.dto.MeetingAddressDto;
import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.pojo.MeetingAddress;
import com.ssic.catering.base.service.IMeetingAddressService;
import com.ssic.game.common.pageModel.PageHelper;
import com.ssic.util.BeanUtils;

@Service
public class MeetingAddressServiceImpl implements IMeetingAddressService
{

    private static final Logger log = Logger.getLogger(MeetingServiceImpl.class);

    @Autowired
    private MeetingAddressDao meetingAddressDao;

    @Override
    public List<MeetingAddressDto> findBy(MeetingAddressDto meetingAddressDto)
    {
        // TODO Auto-generated method stub
        MeetingAddress meetingAddress = new MeetingAddress();
        BeanUtils.copyProperties(meetingAddressDto, meetingAddress);
        List<MeetingAddress> list = meetingAddressDao.findBy(meetingAddress);
        List<MeetingAddressDto> listDto = BeanUtils.createBeanListByTarget(list, MeetingAddressDto.class);
        return listDto;
    }

    @Override
    public MeetingAddressDto findById(String id)
    {
        // TODO Auto-generated method stub
        MeetingAddress meetingAddress = meetingAddressDao.findById(id);
        MeetingAddressDto mdto = new MeetingAddressDto();
        BeanUtils.copyProperties(meetingAddress, mdto);
        return mdto;
    }

    @Override
    public void insertMeetingA(MeetingAddressDto meetingAddressDto)
    {
        // TODO Auto-generated method stub
        MeetingAddress meetingAddress = new MeetingAddress();
        BeanUtils.copyProperties(meetingAddressDto, meetingAddress);
        meetingAddressDao.insertMeetingA(meetingAddress);
    }

    @Override
    public void updateMeetingA(MeetingAddressDto meetingAddressDto)
    {
        // TODO Auto-generated method stub
        MeetingAddress meetingAddress = new MeetingAddress();
        BeanUtils.copyProperties(meetingAddressDto, meetingAddress);
        meetingAddressDao.updateMeetingA(meetingAddress);
    }

    @Override
    public List<MeetingAddressDto> findBy(MeetingAddressDto meetingAddressDto, PageHelper ph)
    {
        // TODO Auto-generated method stub
        PageHelperDto phdto = new PageHelperDto();
        phdto.setOrder(ph.getOrder());
        phdto.setPage(ph.getPage());
        phdto.setRows(ph.getRows());
        phdto.setSort(ph.getSort());
        phdto.setBeginRow((ph.getPage() - 1) * ph.getRows());

        MeetingAddress meetingAddress = new MeetingAddress();
        BeanUtils.copyProperties(meetingAddressDto, meetingAddress);
        List<MeetingAddress> list = meetingAddressDao.findBy(meetingAddress, phdto);
        List<MeetingAddressDto> listDto = BeanUtils.createBeanListByTarget(list, MeetingAddressDto.class);
        return listDto;
    }

    @Override
    public int findCount(MeetingAddressDto meetingAddressDto)
    {
        // TODO Auto-generated method stub
        MeetingAddress meetingAddress = new MeetingAddress();
        BeanUtils.copyProperties(meetingAddressDto, meetingAddress);
        int count = meetingAddressDao.findCount(meetingAddress);
        return count;
    }

    /**
     * 
     * (non-Javadoc)
     * 
     * @see com.ssic.catering.base.service.IMeetingAddressService#findBy(com.ssic.catering.base.dto.MeetingAddressDto,
     *      java.util.List, com.ssic.game.common.pageModel.PageHelper)
     */
    @Override
    public List<MeetingAddressDto> findBy(MeetingAddressDto meetingAddressDto, List<String> projectIds,
        com.ssic.catering.base.pojo.PageHelper ph)
    {
        if (meetingAddressDto == null)
        {
            log.error("参数meetingAddressDto不能为空");
            return null;
        }

        if (ph == null)
        {
            log.error("pageHelper不能为空");
            return null;
        }

        MeetingAddress meetingAddress = new MeetingAddress();
        BeanUtils.copyProperties(meetingAddressDto, meetingAddress);
        List<MeetingAddress> list = meetingAddressDao.findBy(meetingAddress, projectIds, ph);
        if (CollectionUtils.isEmpty(list))
        {
            return null;
        }
        return BeanUtils.createBeanListByTarget(list, MeetingAddressDto.class);
    }

    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.catering.base.service.IMeetingAddressService#findCountBy(com.ssic.catering.base.dto.MeetingAddressDto, java.util.List)
     */
    @Override
    public Integer findCountBy(MeetingAddressDto meetingAddressDto, List<String> projectIds)
    {
        if (meetingAddressDto == null)
        {
            log.error("参数meetingAddressDto不能为空");
            return null;
        }
        
        MeetingAddress meetingAddress = new MeetingAddress();
        BeanUtils.copyProperties(meetingAddressDto, meetingAddress);
        return meetingAddressDao.findCountBy(meetingAddress, projectIds);
    }

}
