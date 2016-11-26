package com.ssic.catering.lbs.dao;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ssic.catering.lbs.dto.TransportTaskAdminDto;
import com.ssic.catering.lbs.mapper.TransportTaskExMapper;
import com.ssic.catering.lbs.mapper.TransportTaskMapper;
import com.ssic.catering.lbs.pojo.TransportTask;
import com.ssic.catering.lbs.pojo.TransportTaskExample;
import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.util.ArrayUtils;
import com.ssic.util.base.MyBatisBaseDao;
import com.ssic.util.constants.DataStatus;

/**
 * 		
 * <p>Title: TransportTaskDao </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author pengcheng.yang	
 * @date 2015年11月25日 下午12:31:43	
 * @version 1.0
 * <p>修改人：apple</p>
 * <p>修改时间：2015年11月25日 下午12:31:43</p>
 * <p>修改备注：</p>
 */
@Repository
public class TransportTaskAdminDao extends MyBatisBaseDao<TransportTask>{

    private static final Logger log = Logger.getLogger(TransportTaskAdminDao.class);
    
	@Autowired
	@Getter
	private TransportTaskMapper mapper;
	
	@Autowired
	private TransportTaskExMapper exMapper;
	
	
	/**
     * 
     * findBy：分页查询
     * 
     * @param param
     * @param pageHelperDto
     * @return
     * @exception
     * @author zhuzhen
     * @date 2015年11月26日 上午8:55:08
     */
    public List<TransportTaskAdminDto> findBy(TransportTask param, PageHelperDto ph)
    {
        TransportTaskExample example = new TransportTaskExample();
        TransportTaskExample.Criteria criteria = example.createCriteria();

        if (param != null)
        {
            // id
            if (!StringUtils.isEmpty(param.getId()))
            {
                criteria.andIdEqualTo(param.getId());
            }
            // projectId
            if (!StringUtils.isEmpty(param.getProjectId()))
            {
                if (param.getProjectId().contains(","))
                {
                    String[] projectIds = param.getProjectId().split(",");
                    if (!ArrayUtils.isEmpty(projectIds))
                    {
                        criteria.andProjectIdIn(Arrays.asList(projectIds));
                    }
                }
                else
                {
                    criteria.andProjectIdEqualTo(param.getProjectId());
                }
            }
            // adminUserId
            if (!StringUtils.isEmpty(param.getAdminUserId()))
            {
                criteria.andAdminUserIdEqualTo(param.getAdminUserId());
            }
            // adminSupplierUserId
            if (!StringUtils.isEmpty(param.getAdminSupplierUserId()))
            {
                if(param.getAdminSupplierUserId().contains(","))
                {
                    String[] suppliers = param.getAdminSupplierUserId().split(",");
                    if(!ArrayUtils.isEmpty(suppliers))
                    {
                        criteria.andAdminSupplierUserIdIn(Arrays.asList(suppliers));
                    }
                }
                else
                {
                    criteria.andAdminSupplierUserIdEqualTo(param.getAdminSupplierUserId());
                }
            }
            // transportDestId
            if (!StringUtils.isEmpty(param.getTransportDestId()))
            {
                criteria.andTransportDestIdEqualTo(param.getTransportDestId());
            }
            // driverId
            if (!StringUtils.isEmpty(param.getDriverId()))
            {
                criteria.andDriverIdEqualTo(param.getDriverId());
            }
            // taskName
            if (!StringUtils.isEmpty(param.getTaskName()))
            {
                criteria.andTaskNameEqualTo(param.getTaskName());
            }
            // departPlace
            if (!StringUtils.isEmpty(param.getDepartPlace()))
            {
                criteria.andDepartPlaceEqualTo(param.getDepartPlace());
            }
            // merchandise
            if (!StringUtils.isEmpty(param.getMerchandise()))
            {
                criteria.andMerchandiseEqualTo(param.getMerchandise());
            }
            // state
            if (null != param.getState())
            {
                criteria.andStateEqualTo(param.getState());
            }
            // createTime
            // lastUpdateTime
            // stat
            if (null == param.getStat())
            {
                criteria.andStatEqualTo(DataStatus.ENABLED);
            }

        }

        if (ph != null)
        {

            // 当前页
            if (ph.getPage() <= 0)
            {
                ph.setPage(1);
            }

            // 页面大小
            if (ph.getRows() <= 0)
            {
                ph.setRows(1);
            }
            
            ph.setBeginRow((ph.getPage()-1)*ph.getRows());

            // 排序字段
            if (!StringUtils.isEmpty(ph.getSort()) && !StringUtils.isEmpty(ph.getOrder()))
            {
                example.setOrderByClause(ph.getSort() + " " + ph.getOrder() + " limit " + ph.getBeginRow()
                    + "," + ph.getRows());
            }
            else
            {
                example.setOrderByClause("create_time DESC limit " + ph.getBeginRow() + "," +ph.getRows());
            }
        }

        return exMapper.selectTransportTaskAdminDtosByExample(example);
    }

    /**
     * 
     * findCoutBy：总条数
     * 
     * @param param
     * @return
     * @exception
     * @author zhuzhen
     * @date 2015年11月26日 上午10:02:34
     */
    public long findCoutBy(TransportTask param)
    {
        TransportTaskExample example = new TransportTaskExample();
        TransportTaskExample.Criteria criteria = example.createCriteria();

        if (param != null)
        {
            // id
            if (!StringUtils.isEmpty(param.getId()))
            {
                criteria.andIdEqualTo(param.getId());
            }
            // projectId
            if (!StringUtils.isEmpty(param.getProjectId()))
            {
                if (param.getProjectId().contains(","))
                {
                    String[] projectIds = param.getProjectId().split(",");
                    if (!ArrayUtils.isEmpty(projectIds))
                    {
                        criteria.andProjectIdIn(Arrays.asList(projectIds));
                    }
                }
                else
                {
                    criteria.andProjectIdEqualTo(param.getProjectId());
                }
            }
            // adminUserId
            if (!StringUtils.isEmpty(param.getAdminUserId()))
            {
                criteria.andAdminUserIdEqualTo(param.getAdminUserId());
            }
            // adminSupplierUserId
            if (!StringUtils.isEmpty(param.getAdminSupplierUserId()))
            {
                if(param.getAdminSupplierUserId().contains(","))
                {
                    String[] suppliers = param.getAdminSupplierUserId().split(",");
                    if(!ArrayUtils.isEmpty(suppliers))
                    {
                        criteria.andAdminSupplierUserIdIn(Arrays.asList(suppliers));
                    }
                }
                else
                {
                    criteria.andAdminSupplierUserIdEqualTo(param.getAdminSupplierUserId());
                }
            }
            // transportDestId
            if (!StringUtils.isEmpty(param.getTransportDestId()))
            {
                criteria.andTransportDestIdEqualTo(param.getTransportDestId());
            }
            // driverId
            if (!StringUtils.isEmpty(param.getDriverId()))
            {
                criteria.andDriverIdEqualTo(param.getDriverId());
            }
            // departPlace
            if (!StringUtils.isEmpty(param.getDepartPlace()))
            {
                criteria.andDepartPlaceEqualTo(param.getDepartPlace());
            }
            // merchandise
            if (!StringUtils.isEmpty(param.getMerchandise()))
            {
                criteria.andMerchandiseEqualTo(param.getMerchandise());
            }
            // state
            if (null != param.getState())
            {
                criteria.andStateEqualTo(param.getState());
            }
            // createTime
            // lastUpdateTime
            // stat
            if (null == param.getStat())
            {
                criteria.andStatEqualTo(DataStatus.ENABLED);
            }

        }
        else
        {
            criteria.andStatEqualTo(DataStatus.ENABLED);
        }

        return exMapper.countTransportTaskAdminDtosByExample(example);
    }
    
   /**
    * 
    * findById：一句话描述方法功能
    * @param id
    * @return
    * @exception    
    * @author zhuzhen
    * @date 2015年11月26日 下午3:24:25
    */
   public TransportTask findById(String id)
   {
       if(StringUtils.isEmpty(id))
       {
           log.error("参数id不能为空");
           return null;
       }
       
       return mapper.selectByPrimaryKey(id);
   }
}

