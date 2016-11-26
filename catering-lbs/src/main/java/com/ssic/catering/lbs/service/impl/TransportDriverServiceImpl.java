package com.ssic.catering.lbs.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssic.catering.lbs.dao.TransportDriverDao;
import com.ssic.catering.lbs.dto.TransportDriverDto;
import com.ssic.catering.lbs.pojo.TransportDriver;
import com.ssic.catering.lbs.service.TransportDriverService;
import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.util.BeanUtils;

@Service
public class TransportDriverServiceImpl implements TransportDriverService {

	@Autowired
	private TransportDriverDao transportDriverDao;
	
	/**
	 * 
	 * findUserNameAndPwd：验证用户登录信息是否正确
	 * @param username
	 * @param pwd
	 * @return
	 * @exception	
	 * @author pengcheng.yang
	 * @date 2015年11月25日 上午11:37:10
	 */
	@Override
	public TransportDriverDto findUserNameAndPwd(String username, String pwd) {
		TransportDriverDto dto = transportDriverDao.findUserNameAndPwd(username, pwd);
		if(dto != null){
			return dto;
		}
		return null;
	}
	
	/**
	 * 
	 * (non-Javadoc)   
	 * @see com.ssic.catering.lbs.service.TransportDriverService#findTransportDriverDtosBy(com.ssic.catering.lbs.dto.TransportDriverDto, com.ssic.game.common.dto.PageHelperDto)
	 */
	@Override
	public List<TransportDriverDto> findTransportDriverDtosBy(TransportDriverDto transportDriverDto,
	    PageHelperDto ph)
	{
	    if(transportDriverDto != null)
	    {
	        TransportDriver param = new TransportDriver();
	        BeanUtils.copyProperties(transportDriverDto, param);
	        
	        return transportDriverDao.findTransportDriverDtosBy(param, ph);
	    }
	    
	    return null;
	}
	
	/**
	 * 
	 * (non-Javadoc)   
	 * @see com.ssic.catering.lbs.service.TransportDriverService#findTransportDriverDtoCountBy(com.ssic.catering.lbs.dto.TransportDriverDto)
	 */
	@Override
	public long findTransportDriverDtoCountBy(TransportDriverDto transportDriverDto)
	{
	    if(transportDriverDto != null)
        {
            TransportDriver param = new TransportDriver();
            BeanUtils.copyProperties(transportDriverDto, param);
            
            return transportDriverDao.findTransportDriverDtoCountBy(param);
        }
        
        return 0;
	}
	
	@Override
	public int addTransportDiver(TransportDriverDto transportDiverDto)
	{
        if(transportDiverDto != null)
        {
            TransportDriver param = new TransportDriver();
            BeanUtils.copyProperties(transportDiverDto, param);
            
            return transportDriverDao.insert(param);
        }
        
        return 0;
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
	@Override
	public int updateDriverState(String driverId,int state) {
		return transportDriverDao.updateDriverState(driverId,state);
	}
	
	/**
	 * 
	 * (non-Javadoc)   
	 * @see com.ssic.catering.lbs.service.TransportDriverService#updateTransportDiver(com.ssic.catering.lbs.dto.TransportDriverDto)
	 */
	@Override
	public int updateTransportDiver(TransportDriverDto transportDriverDto)
	{
	    if(transportDriverDto != null)
	    {
	        TransportDriver param = new TransportDriver();
	        BeanUtils.copyProperties(transportDriverDto, param);
	        
	        return transportDriverDao.updateByPrimaryKeySelective(param);
	    }
	    return 0;
	}
	
	/**
	 * 
	 * (non-Javadoc)   
	 * @see com.ssic.catering.lbs.service.TransportDriverService#findTransportDiverById(java.lang.String)
	 */
	@Override
	public TransportDriverDto findTransportDiverById(String id)
	{
	    if(!StringUtils.isEmpty(id))
        {
            TransportDriver resultset = transportDriverDao.findTransportDiverById(id);
            if(resultset != null)
            {
                TransportDriverDto result = new TransportDriverDto();
                BeanUtils.copyProperties(resultset, result);
                
                return result;
            }
        }
	    return null;
	}
	
	/**
	 * 
	 * (non-Javadoc)   
	 * @see com.ssic.catering.lbs.service.TransportDriverService#findTransportDiverByAccount(java.lang.String)
	 */
	@Override
	public TransportDriverDto findTransportDiverByAccount(String account)
	{
	    if(!StringUtils.isEmpty(account))
        {
	        TransportDriver param = new TransportDriver();
	        param.setAccount(account);
            List<TransportDriverDto> resultset = transportDriverDao.findTransportDriverDtosBy(param, null);
            if(!CollectionUtils.isEmpty(resultset))
            {
                TransportDriverDto result = new TransportDriverDto();
                BeanUtils.copyProperties(resultset.get(0), result);
                
                return result;
            }
        }
        return null;
	}

}

