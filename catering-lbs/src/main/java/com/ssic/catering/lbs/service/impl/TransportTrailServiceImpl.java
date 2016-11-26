package com.ssic.catering.lbs.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssic.catering.lbs.dao.TransportTrailDao;
import com.ssic.catering.lbs.dao.mongo.TransportTrailMongoDao;
import com.ssic.catering.lbs.documents.TransportTrail;
import com.ssic.catering.lbs.dto.TransportTrailMongoDto;
import com.ssic.catering.lbs.service.TransportTrailService;
import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.util.BeanUtils;

@Service
public class TransportTrailServiceImpl implements TransportTrailService {

	@Autowired
	private TransportTrailDao transportTrailDao;
	
	@Autowired
	private TransportTrailMongoDao transportTrailMongoDao;
	
	/**
	 * 
	 * (non-Javadoc)   
	 * @see com.ssic.catering.lbs.service.TransportTrailService#findBy(com.ssic.catering.lbs.dto.TransportTrailMongoDto, com.ssic.game.common.dto.PageHelperDto)
	 */
	@Override
	public List<TransportTrailMongoDto> findBy(TransportTrailMongoDto transportTrailMongoDto, PageHelperDto ph)
	{
	    if(transportTrailMongoDto != null)
        {
            TransportTrail param = new TransportTrail();
            BeanUtils.copyProperties(transportTrailMongoDto, param);
            List<TransportTrail> result = transportTrailMongoDao.findBy(param, ph);
            if(!CollectionUtils.isEmpty(result))
            {
                return BeanUtils.createBeanListByTarget(result, TransportTrailMongoDto.class);
            }
        }
	    
	    return null;
	}
	
	/**
	 * 
	 * (non-Javadoc)   
	 * @see com.ssic.catering.lbs.service.TransportTrailService#findCountBy(com.ssic.catering.lbs.dto.TransportTrailMongoDto)
	 */
	@Override
	public long findCountBy(TransportTrailMongoDto transportTrailMongoDto)
	{
	    if(transportTrailMongoDto != null)
	    {
	        TransportTrail param = new TransportTrail();
	        BeanUtils.copyProperties(transportTrailMongoDto, param);
	        return  transportTrailMongoDao.findCountBy(param);
	    }
	    
	    return 0;
	    
	}

}

