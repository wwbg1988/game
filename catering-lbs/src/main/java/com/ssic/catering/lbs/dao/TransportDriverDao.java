package com.ssic.catering.lbs.dao;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ssic.catering.lbs.dto.TransportDriverDto;
import com.ssic.catering.lbs.mapper.TransportDriverExMapper;
import com.ssic.catering.lbs.mapper.TransportDriverMapper;
import com.ssic.catering.lbs.pojo.TransportDriver;
import com.ssic.catering.lbs.pojo.TransportDriverExample;
import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.util.ArrayUtils;
import com.ssic.util.base.MyBatisBaseDao;
import com.ssic.util.constants.DataStatus;

/**
 * 		
 * <p>Title: TransportDriverDao </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author pengcheng.yang	
 * @date 2015年11月25日 上午11:12:46	
 * @version 1.0
 * <p>修改人：apple</p>
 * <p>修改时间：2015年11月25日 上午11:12:46</p>
 * <p>修改备注：</p>
 */
@Repository
public  class TransportDriverDao extends MyBatisBaseDao<TransportDriver>{
	
	@Autowired
	@Getter
	private TransportDriverMapper mapper;
	
	@Autowired
	private TransportDriverExMapper exMapper;

	/**
	 * 
	 * findUserNameAndPwd：验证用户登录信息是否正确
	 * @param username
	 * @param pwd
	 * @return
	 * @exception	
	 * @author pengcheng.yang
	 * @date 2015年11月25日 上午11:35:42
	 */
	public TransportDriverDto findUserNameAndPwd(String username,String pwd){
		TransportDriverDto transportDriverDto = null;
		if(!StringUtils.isEmpty(username) && !StringUtils.isEmpty(pwd)){
			transportDriverDto = exMapper.findUserInfo(username, pwd);
		}else{
			return null;
		}
		return transportDriverDto;
	}
	
	/**
	 * 
	 * updateDriverState：标识用户是否有任务
	 * @param username
	 * @return
	 * @exception	
	 * @author pengcheng.yang
	 * @date 2015年11月26日 上午11:31:04
	 */
	public int updateDriverState(String driverId,int state){
		return exMapper.updateDriverState(driverId,state);
	}
	
	/**
     * 
     * findTransportDriverDtosBy：多表查询
     * @param param
     * @param ph
     * @return
     * @exception   
     * @author zhuzhen
     * @date 2015年11月26日 下午7:10:34
     */
    public List<TransportDriverDto> findTransportDriverDtosBy(TransportDriver param, PageHelperDto ph)
    {
        TransportDriverExample example = new TransportDriverExample();
        TransportDriverExample.Criteria criteria = example.createCriteria();
        
        if(param != null)
        {
          //id
            if(!StringUtils.isEmpty(param.getId()))
            {
              criteria.andIdEqualTo(param.getId());
            }
            //adminUserId
            if(!StringUtils.isEmpty(param.getAdminUserId()))
            {
                if(param.getAdminUserId().contains(","))
                {
                    String[] suppliers = param.getAdminUserId().split(",");
                    if(!ArrayUtils.isEmpty(suppliers))
                    {
                        criteria.andAdminUserIdIn(Arrays.asList(suppliers));
                    }
                }
                else
                {
                    criteria.andAdminUserIdEqualTo(param.getAdminUserId());
                }
            }
            //account
            if(!StringUtils.isEmpty(param.getAccount()))
            {
              criteria.andAccountEqualTo(param.getAccount());
            }
            //password
            if(!StringUtils.isEmpty(param.getPassword()))
            {
              criteria.andPasswordEqualTo(param.getPassword());
            }
            //name
            if(!StringUtils.isEmpty(param.getName()))
            {
              criteria.andNameEqualTo(param.getName());
            }
            //nickName
            if(!StringUtils.isEmpty(param.getNickName()))
            {
              criteria.andNickNameEqualTo(param.getNickName());
            }
            //imei
            if(!StringUtils.isEmpty(param.getImei()))
            {
              criteria.andImeiEqualTo(param.getImei());
            }
            //phone
            if(!StringUtils.isEmpty(param.getPhone()))
            {
              criteria.andPhoneEqualTo(param.getPhone());
            }
            //state
            if(null != param.getState())
            {
              criteria.andStateEqualTo(param.getState());
            }
            //createTime
            //lastUpdateTime
            //stat
            if(null == param.getStat())
            {
              criteria.andStatEqualTo(DataStatus.ENABLED);
            }

        }
        else
        {
            criteria.andStatEqualTo(DataStatus.ENABLED);
        }
        
        if(ph != null)
        {
            //当前页
            if(ph.getPage() <= 0)
            {
                ph.setPage(1);
            }
            
            //页面大小
            if(ph.getRows() <= 0)
            {
                ph.setRows(1);
            }
            
            //排序字段 
            if(!StringUtils.isEmpty(ph.getSort()) && !StringUtils.isEmpty(ph.getOrder()))
            {
                example.setOrderByClause(ph.getSort()+" "+ph.getOrder()+" limit "+(ph.getPage()-1)*ph.getRows()+","+ph.getRows());
            }
            else
            {
               example.setOrderByClause("create_time Desc limit "+(ph.getPage()-1)*ph.getRows()+","+ph.getRows());
            }
        }
        
        return exMapper.selectTransportDriverDtosBy(example);
    }
    
    
    /**
     * 
     * findTransportDriverDtoCountBy：一句话描述方法功能
     * @param param
     * @return
     * @exception   
     * @author zhuzhen
     * @date 2015年11月26日 下午8:11:36
     */
    public long findTransportDriverDtoCountBy(TransportDriver param)
    {
        TransportDriverExample example = new TransportDriverExample();
        TransportDriverExample.Criteria criteria = example.createCriteria();
        
        if(param != null)
        {
          //id
            if(!StringUtils.isEmpty(param.getId()))
            {
              criteria.andIdEqualTo(param.getId());
            }
            //adminUserId
            if(!StringUtils.isEmpty(param.getAdminUserId()))
            {
                if(param.getAdminUserId().contains(","))
                {
                    String[] suppliers = param.getAdminUserId().split(",");
                    if(!ArrayUtils.isEmpty(suppliers))
                    {
                        criteria.andAdminUserIdIn(Arrays.asList(suppliers));
                    }
                }
                else
                {
                    criteria.andAdminUserIdEqualTo(param.getAdminUserId());
                }
            }
            //account
            if(!StringUtils.isEmpty(param.getAccount()))
            {
              criteria.andAccountEqualTo(param.getAccount());
            }
            //password
            if(!StringUtils.isEmpty(param.getPassword()))
            {
              criteria.andPasswordEqualTo(param.getPassword());
            }
            //name
            if(!StringUtils.isEmpty(param.getName()))
            {
              criteria.andNameEqualTo(param.getName());
            }
            //nickName
            if(!StringUtils.isEmpty(param.getNickName()))
            {
              criteria.andNickNameEqualTo(param.getNickName());
            }
            //imei
            if(!StringUtils.isEmpty(param.getImei()))
            {
              criteria.andImeiEqualTo(param.getImei());
            }
            //phone
            if(!StringUtils.isEmpty(param.getPhone()))
            {
              criteria.andPhoneEqualTo(param.getPhone());
            }
            //state
            if(null != param.getState())
            {
              criteria.andStateEqualTo(param.getState());
            }
            //createTime
            //lastUpdateTime
            //stat
            if(null == param.getStat())
            {
              criteria.andStatEqualTo(DataStatus.ENABLED);
            }

        }
        else
        {
            criteria.andStatEqualTo(DataStatus.ENABLED);
        }
        
        return exMapper.countTransportDriverDtoBy(example);
    }
    
    
    public TransportDriver findTransportDiverById(String id)
    {
        if(!StringUtils.isEmpty(id))
        {
            return mapper.selectByPrimaryKey(id);
        }
        return null;
    }
}

