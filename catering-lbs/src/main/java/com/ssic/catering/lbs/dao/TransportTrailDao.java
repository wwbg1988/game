package com.ssic.catering.lbs.dao;

import java.util.Date;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssic.catering.lbs.mapper.TransportTrailMapper;
import com.ssic.catering.lbs.pojo.TransportTrail;
import com.ssic.util.UUIDGenerator;
import com.ssic.util.base.MyBatisBaseDao;
import com.ssic.util.constants.DataStatus;

/**
 * 		
 * <p>Title: TransportTrailDao </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author zhuzhen	
 * @date 2015年11月25日 上午11:11:16	
 * @version 1.0
 * <p>修改人：zhuzhen</p>
 * <p>修改时间：2015年11月25日 上午11:11:16</p>
 * <p>修改备注：</p>
 */
@Repository
public class TransportTrailDao extends MyBatisBaseDao<TransportTrail>
{
    @Getter
    @Autowired
    private TransportTrailMapper mapper;
    /**
     * 
     * insertTranSportTrailInf：保存用户开始和结束的轨迹坐标信息
     * @param trail
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年11月25日 下午2:52:49
     */
    public String insertTranSportTrailInfo(TransportTrail trail){
    	trail.setId(UUIDGenerator.getUUID());
    	trail.setStat(DataStatus.ENABLED);
    	trail.setCreateTime(new Date());
    	trail.setCoordinateType(1);
    	int flag = mapper.insert(trail);
    	if(flag > 0){
    		return "success";
    	}
    	return "error";
    }
}

