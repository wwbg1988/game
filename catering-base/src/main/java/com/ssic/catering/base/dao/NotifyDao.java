package com.ssic.catering.base.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import lombok.Getter;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.mapper.NotifyMapper;
import com.ssic.catering.base.pojo.Notify;
import com.ssic.catering.base.pojo.NotifyExample;
import com.ssic.catering.base.pojo.NotifyExample.Criteria;
import com.ssic.catering.base.pojo.PageHelper;
import com.ssic.catering.common.util.DateUtil;
import com.ssic.util.constants.DataStatus;

@Repository
public class NotifyDao
{
    private static final Logger log = Logger.getLogger(NotifyDao.class);

    @Autowired
    @Getter
    private NotifyMapper mapper;

    public List<Notify> findBy(Notify param)
    {
        NotifyExample example = new NotifyExample();
        Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(param.getId()))
        {
            criteria.andIdEqualTo(param.getId());
        }
        criteria.andStatEqualTo(DataStatus.ENABLED);
        return mapper.selectByExample(example);
    }

    public List<Notify> findBy(Notify param, PageHelperDto phdto)
    {
        NotifyExample example = new NotifyExample();
        Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(param.getId()))
        {
            criteria.andIdEqualTo(param.getId());
        }
        if (!StringUtils.isEmpty(param.getName()))
        {
            criteria.andNameEqualTo(param.getName());
        }
        if (!StringUtils.isEmpty(param.getTitle()))
        {
            criteria.andTitleEqualTo(param.getTitle());
        }
        if (!StringUtils.isEmpty(param.getListTitle()))
        {
            criteria.andListTitleEqualTo(param.getListTitle());
        }
        if (param.getState() != null)
        {
            criteria.andStateEqualTo(param.getState());
        }
        if (!StringUtils.isEmpty(param.getProjId()))
        {
            criteria.andProjIdEqualTo(param.getProjId());
        }
        criteria.andStatEqualTo(DataStatus.ENABLED);
        example.setOrderByClause("  notify_time desc  limit " + phdto.getBeginRow() + "," + phdto.getRows());
        return mapper.selectByExample(example);
    }

    public List<Notify> findBy(Notify param, int beginRow, int endRow, String searchDate)
    {
        NotifyExample example = new NotifyExample();
        Criteria criteria = example.createCriteria();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date localDate;
        if (searchDate != null)
        { // 查询日期为localDate的记录;
          // 获取当天日期
            try
            {
                localDate = simpleDateFormat.parse(searchDate);
                // 获取下一天
                Date nextDate = DateUtil.getSpecifiedDayBefore(searchDate);
                criteria.andCreateTimeBetween(localDate, nextDate);
            }
            catch (java.text.ParseException e)
            {
                // 对异常进行简要描述
            }
        }
        if (!StringUtils.isEmpty(param.getProjId()))
        {
            criteria.andProjIdEqualTo(param.getProjId());
        }
        criteria.andStatEqualTo(DataStatus.ENABLED);
        example.setOrderByClause("  notify_time desc  limit " + beginRow + "," + endRow);
        return mapper.selectByExample(example);
    }

    public int findCount(Notify param)
    {
        NotifyExample example = new NotifyExample();
        Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(param.getId()))
        {
            criteria.andIdEqualTo(param.getId());
        }
        if (!StringUtils.isEmpty(param.getName()))
        {
            criteria.andNameEqualTo(param.getName());
        }
        if (!StringUtils.isEmpty(param.getTitle()))
        {
            criteria.andTitleEqualTo(param.getTitle());
        }
        if (!StringUtils.isEmpty(param.getListTitle()))
        {
            criteria.andListTitleEqualTo(param.getListTitle());
        }
        if (param.getState() != null)
        {
            criteria.andStateEqualTo(param.getState());
        }
        if (!StringUtils.isEmpty(param.getProjId()))
        {
            criteria.andProjIdEqualTo(param.getProjId());
        }
        criteria.andStatEqualTo(DataStatus.ENABLED);
        return mapper.countByExample(example);
    }

    public void insertNotify(Notify param)
    {
        mapper.insert(param);
    }

    public void updateNotify(Notify param)
    {
        mapper.updateByPrimaryKeySelective(param);
    }

    public void deleteNotify(Notify param)
    {
        mapper.updateByPrimaryKeySelective(param);
    }

    public Notify findById(String id)
    {
        return mapper.selectByPrimaryKey(id);
    }

    /**
     * 查询所有符合条件的结果集	 
     * @author 朱振	
     * @date 2015年10月27日 下午2:21:14	
     * @version 1.0
     * @param param 条件
     * @param projectIds 项目id列表
     * @param ph
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年10月27日 下午2:21:14</p>
     * <p>修改备注：</p>
     */
    public List<Notify> findBy(Notify param, List<String> projectIds, PageHelper ph)
    {
        log.info("parameters:notifyDto=" + param + ";projectIds=" + projectIds + ";ph=" + ph);

        //        if (param == null)
        //        {
        //            log.error("参数notifyDto不能为空");
        //            return null;
        //        }
        //
        //        if (ph == null)
        //        {
        //            log.error("参数pageHelper不能为空");
        //            return null;
        //        }

        if (ph.getRows() < 0)
        {
            log.error("参数rows不能小于0");
            return null;
        }

        if (ph.getPage() < 1)
        {
            log.error("参数page不能小于1");
            return null;
        }

        NotifyExample example = new NotifyExample();
        Criteria criteria = example.createCriteria();

        // id
        if (!StringUtils.isEmpty(param.getId()))
        {
            criteria.andIdEqualTo(param.getId());
        }

        // name
        if (!StringUtils.isEmpty(param.getName()))
        {
            criteria.andNameEqualTo(param.getName());
        }

        // title
        if (!StringUtils.isEmpty(param.getTitle()))
        {
            criteria.andTitleEqualTo(param.getTitle());
        }

        // listTitle
        if (!StringUtils.isEmpty(param.getListTitle()))
        {
            criteria.andListTitleEqualTo(param.getListTitle());
        }

        // state
        if (param.getState() != null)
        {
            criteria.andStateEqualTo(param.getState());
        }

        // projectId
        if (!CollectionUtils.isEmpty(projectIds))
        {
            criteria.andProjIdIn(projectIds);
        }
        criteria.andStatEqualTo(DataStatus.ENABLED);
        example.setOrderByClause("  notify_time desc  limit " + (ph.getPage() - 1) * ph.getRows() + ","
            + ph.getRows());
        return mapper.selectByExample(example);
    }

    /**
     * 获取符合条件的结果集的总条数	 
     * @author 朱振	
     * @date 2015年10月27日 下午2:22:05	
     * @version 1.0
     * @param param 条件
     * @param projectIds 项目id列表
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年10月27日 下午2:22:05</p>
     * <p>修改备注：</p>
     */
    public Integer findCountBy(Notify param, List<String> projectIds)
    {
        log.info("parameters:notifyDto=" + param + ";projectIds=" + projectIds);

        if (param == null)
        {
            log.error("参数notifyDto不能为空");
            return null;
        }

        NotifyExample example = new NotifyExample();
        Criteria criteria = example.createCriteria();

        // id
        if (!StringUtils.isEmpty(param.getId()))
        {
            criteria.andIdEqualTo(param.getId());
        }

        // name
        if (!StringUtils.isEmpty(param.getName()))
        {
            criteria.andNameEqualTo(param.getName());
        }

        // title
        if (!StringUtils.isEmpty(param.getTitle()))
        {
            criteria.andTitleEqualTo(param.getTitle());
        }

        // listTitle
        if (!StringUtils.isEmpty(param.getListTitle()))
        {
            criteria.andListTitleEqualTo(param.getListTitle());
        }

        // state
        if (param.getState() != null)
        {
            criteria.andStateEqualTo(param.getState());
        }

        // projectId
        if (!CollectionUtils.isEmpty(projectIds))
        {
            criteria.andProjIdIn(projectIds);
        }
        criteria.andStatEqualTo(DataStatus.ENABLED);

        return mapper.countByExample(example);
    }

}
