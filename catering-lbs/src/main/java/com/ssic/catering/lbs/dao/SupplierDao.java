/**
 * 
 */
package com.ssic.catering.lbs.dao;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.ssic.catering.lbs.mapper.SupplierMapper;
import com.ssic.catering.lbs.pojo.Supplier;
import com.ssic.catering.lbs.pojo.SupplierExample;
import com.ssic.catering.lbs.pojo.SupplierExample.Criteria;
import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.util.ArrayUtils;
import com.ssic.util.StringUtils;
import com.ssic.util.base.MyBatisBaseDao;
import com.ssic.util.constants.DataStatus;

/**		
 * <p>Title: SupplierDao </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年12月3日 下午4:14:07	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年12月3日 下午4:14:07</p>
 * <p>修改备注：</p>
 */
@Repository
public class SupplierDao extends MyBatisBaseDao<Supplier>
{

    @Getter
    @Autowired
    private SupplierMapper mapper;

    /**
     * 
     * findBy：查询TransportDest
     * @param param
     * @return
     * @exception   
     * @author zhuzhen
     * @date 2015年11月25日 上午11:16:39
     */
    public List<Supplier> findBy(Supplier param, PageHelperDto phDto)
    {
        SupplierExample example = new SupplierExample();
        Criteria criteria = example.createCriteria();
        if (phDto != null && !StringUtils.isEmpty(String.valueOf(phDto.getBeginRow()))
            && !StringUtils.isEmpty(String.valueOf(phDto.getRows())))
        {
            int beginRow = phDto.getBeginRow();
            int rows = phDto.getRows();
            example.setOrderByClause("create_time desc limit " + beginRow + "," + rows);
        }
        else
        {
            example.setOrderByClause("create_time desc");
        }
        //id
        if (!StringUtils.isEmpty(param.getId()))
        {
            criteria.andIdEqualTo(param.getId());
        }
        //supplierName
        if (!StringUtils.isEmpty(param.getSupplierName()))
        {
            criteria.andSupplierNameEqualTo(param.getSupplierName());
        }
        //projectId
        if (!StringUtils.isEmpty(param.getProjectId()))
        {
            criteria.andProjectIdEqualTo(param.getProjectId());
        }
        if (!StringUtils.isEmpty(param.getLongitude()))
        {
            criteria.andLongitudeEqualTo(param.getLongitude());
        }
        if (!StringUtils.isEmpty(param.getLatitude()))
        {
            criteria.andLatitudeEqualTo(param.getLatitude());
        }
        //address
        if (!StringUtils.isEmpty(param.getAddress()))
        {
            criteria.andAddressEqualTo(param.getAddress());
        }
        //createTime
        //lastUpdateTime
        //stat
        if (null == param.getStat())
        {
            criteria.andStatEqualTo(DataStatus.ENABLED);
        }

        return mapper.selectByExample(example);
    }

    /**     
     * findCount：一句话描述方法功能
     * @param dest 
     * @return
     * @exception   
     * @author 刘博
     * @date 2015年11月25日 下午3:11:08   
     */
    public int findCount(Supplier param)
    {
        SupplierExample example = new SupplierExample();
        Criteria criteria = example.createCriteria();

        example.setOrderByClause("create_time desc");

        //id
        if (!StringUtils.isEmpty(param.getId()))
        {
            criteria.andIdEqualTo(param.getId());
        }
        //projectId
        if (!StringUtils.isEmpty(param.getProjectId()))
        {
            criteria.andProjectIdEqualTo(param.getProjectId());
        }
        //address
        if (!StringUtils.isEmpty(param.getAddress()))
        {
            criteria.andAddressEqualTo(param.getAddress());
        }
        //createTime
        //lastUpdateTime
        //stat
        if (null == param.getStat())
        {
            criteria.andStatEqualTo(DataStatus.ENABLED);
        }

        return mapper.countByExample(example);
    }

    /**     
     * findById：一句话描述方法功能
     * @param id
     * @return
     * @exception   
     * @author 刘博
     * @date 2015年11月25日 下午3:53:11   
     */
    public Supplier findById(String id)
    {
        SupplierExample example = new SupplierExample();
        Criteria criteria = example.createCriteria();
        example.setDistinct(true);
        criteria.andStatEqualTo(DataStatus.ENABLED);

        if (!StringUtils.isEmpty(id))
        {
            criteria.andIdEqualTo(id);

            example.setOrderByClause("create_time asc");

            List<Supplier> list = mapper.selectByExample(example);
            if (!CollectionUtils.isEmpty(list))
            {
                return list.get(0);
            }
        }
        return null;
    }

    /**
     * 
     * findBy：一句话描述方法功能
     * @param param
     * @return
     * @exception   
     * @author zhuzhen
     * @date 2015年11月26日 下午2:21:58
     */
    public List<Supplier> findBy(Supplier param)
    {
        SupplierExample example = new SupplierExample();
        Criteria criteria = example.createCriteria();

        if (param != null)
        {
            //id
            if (!StringUtils.isEmpty(param.getId()))
            {
                if (param.getId().contains(","))
                {
                    String[] ids = param.getId().split(",");
                    if (!ArrayUtils.isEmpty(ids))
                    {
                        criteria.andIdIn(Arrays.asList(ids));
                    }
                }
                else
                {
                    criteria.andIdEqualTo(param.getId());
                }
            }
            //projectId
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
            
            //supplierName
            if(!StringUtils.isEmpty(param.getSupplierName()))
            {
                criteria.andSupplierNameEqualTo(param.getSupplierName());
            }
            
            //address
            if (!StringUtils.isEmpty(param.getAddress()))
            {
                criteria.andAddressEqualTo(param.getAddress());
            }
            //longitude
            if (!StringUtils.isEmpty(param.getLongitude()))
            {
                criteria.andLongitudeEqualTo(param.getLongitude());
            }
            //latitude
            if (!StringUtils.isEmpty(param.getLatitude()))
            {
                criteria.andLatitudeEqualTo(param.getLatitude());
            }
            //createTime
            //lastUpdateTime
            //stat
            if (null == param.getStat())
            {
                criteria.andStatEqualTo(DataStatus.ENABLED);
            }

        }
        else
        {
            criteria.andStatEqualTo(DataStatus.ENABLED);
        }

        return mapper.selectByExample(example);
    }

}
