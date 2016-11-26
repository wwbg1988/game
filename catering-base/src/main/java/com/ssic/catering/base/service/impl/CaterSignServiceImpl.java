package com.ssic.catering.base.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssic.catering.base.pojo.PageHelper;
import com.ssic.catering.base.service.ICaterSignService;
import com.ssic.game.common.dao.SignDao;
import com.ssic.game.common.dto.SignDto;
import com.ssic.game.common.pojo.Sign;
import com.ssic.util.BeanUtils;

@Service
public class CaterSignServiceImpl implements ICaterSignService{
    
    private static Logger log = Logger.getLogger(CaterSignServiceImpl.class);

	@Autowired
	private SignDao signDao;
	
	public List<SignDto> findBy(SignDto signDto){
		Sign sign = new Sign();
	    int	endRows=0;
	    int beginRows =0;
	    BeanUtils.copyProperties(signDto, sign);
		List<Sign> list=  signDao.findBy(sign, beginRows, endRows);
		List<SignDto> listDto =BeanUtils.createBeanListByTarget(list, SignDto.class);
		return listDto;
	}
	
	public void insertSign (SignDto signDto){
		signDao.insert(signDto);
	}
	
	public void updateSign (SignDto signDto){
		Sign sign = new Sign();
		BeanUtils.copyProperties(signDto, sign);
		signDao.updateByPrimaryKeySelective(sign);
	}
	
	public SignDto findById(String id){
    	Sign sign=	 signDao.findById(id);
    	SignDto signDto = new SignDto();
    	BeanUtils.copyProperties(sign, signDto);
    	return signDto;
	}

	@Override
	public List<SignDto> findBy(SignDto signDto, PageHelper ph) {
		// TODO Auto-generated method stub
			Sign sign = new Sign();
		    int	endRows=ph.getRows();
		    int beginRows =(ph.getPage()-1)*ph.getRows();
		    BeanUtils.copyProperties(signDto, sign);
			List<Sign> list=  signDao.findBy(sign, beginRows, endRows);
			List<SignDto> listDto =BeanUtils.createBeanListByTarget(list, SignDto.class);
			return listDto;
		}
	
	/**
	 * (non-Javadoc)   
	 * @see com.ssic.catering.base.service.ICaterSignService#findSignsBy(com.ssic.game.common.dto.SignDto, com.ssic.catering.base.pojo.PageHelper)
	 */
	@Override
	public List<SignDto> findSignsBy(SignDto signDto, PageHelper ph)
	{
	    if(signDto==null || ph==null)
	    {
	        log.error("parameters:signDto="+signDto+";PageHelper="+ph);
	        return null;
	    }
	    
	    if(ph.getPage() < 1)
	    {
	        log.error("parameters:page="+ph.getPage());
            return null;
	    }
	    
	    if(ph.getRows() <= 0)
        {
            log.error("parameters:rows="+ph.getRows());
            return null;
        }
	    
	    Sign sign = new Sign();
        int endRows=ph.getRows();
        int beginRows =(ph.getPage()-1)*ph.getRows();
        BeanUtils.copyProperties(signDto, sign);
        
        return signDao.findSignDtosBy(sign, beginRows, endRows,signDto.getUserName(),signDto.getUserAccount());
	}
	
	/**
	 * 
	 * (non-Javadoc)   
	 * @see com.ssic.catering.base.service.ICaterSignService#countSignsBy(com.ssic.game.common.dto.SignDto, java.util.List)
	 */
	@Override
	public Long countSignsBy(SignDto signDto)
	{
	    log.info("parameters:signDto="+signDto);
	    
	    if(signDto == null)
	    {
	        log.error("parameters:signDto为空");
	        return 0L;
	    }
	    
	    Sign sign = new Sign();
	    BeanUtils.copyProperties(signDto, sign);
	   Long count = signDao.findSignDtosCountBy(sign,signDto.getUserName(),signDto.getUserAccount());
	   System.out.println(count);
	   
	   return count;
	}
	
	public int count(SignDto signDto){
		Sign sign = new Sign();
		BeanUtils.copyProperties(signDto, sign);
		return  signDao.findCount(sign);
	}

	@Override
	public List<SignDto> findByProjs(SignDto signDto) {
		// TODO Auto-generated method stub
		Sign sign = new Sign();
		BeanUtils.copyProperties(signDto, sign);
		return signDao.findByProjs(sign, signDto.getUserName(), signDto.getUserAccount());
	}
}
