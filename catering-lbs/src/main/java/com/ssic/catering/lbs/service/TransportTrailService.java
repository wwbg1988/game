package com.ssic.catering.lbs.service;

import java.util.List;

import com.ssic.catering.lbs.dto.TransportTrailMongoDto;
import com.ssic.game.common.dto.PageHelperDto;

/**
 * 		
 * <p>Title: TransportTrailService </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author pengcheng.yang	
 * @date 2015年11月25日 下午2:52:32	
 * @version 1.0
 * <p>修改人：apple</p>
 * <p>修改时间：2015年11月25日 下午2:52:32</p>
 * <p>修改备注：</p>
 */
public interface TransportTrailService {
	
	/**
	 * 
	 * findBy：一句话描述方法功能
	 * @param transportTrailMongoDto
	 * @param ph
	 * @return
	 * @exception	
	 * @author zhuzhen
	 * @date 2015年11月27日 下午5:17:21
	 */
	List<TransportTrailMongoDto> findBy(TransportTrailMongoDto transportTrailMongoDto, PageHelperDto ph);
	
	/**
	 * 
	 * findCountBy：一句话描述方法功能
	 * @param param
	 * @return
	 * @exception	
	 * @author zhuzhen
	 * @date 2015年11月27日 下午3:56:27
	 */
	long findCountBy(TransportTrailMongoDto transportTrailMongoDto);
}

