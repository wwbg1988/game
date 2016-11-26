/**
 * 
 */
package com.ssic.catering.lbs.dao.test;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ssic.catering.lbs.dao.mongo.TransportTrailMongoDao;
import com.ssic.catering.lbs.documents.TransportTrail;
import com.ssic.game.app.test.BaseTestCase;
import com.ssic.util.UUIDGenerator;
import com.ssic.util.constants.DataStatus;

/**		
 * <p>Title: TransportTrailDaoTest </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author rkzhang	
 * @date 2015年11月25日 下午1:52:57	
 * @version 1.0
 * <p>修改人：rkzhang</p>
 * <p>修改时间：2015年11月25日 下午1:52:57</p>
 * <p>修改备注：</p>
 */
public class TransportTrailDaoTest extends BaseTestCase {
    
    @Autowired
    private TransportTrailMongoDao transportTrailDao;

    @Test
    public void saveTest(){
    	TransportTrail tranil = new TransportTrail();
		tranil.setDriverId("1");
		tranil.setTransportTaskId("1");
		tranil.setLongitude("1");
//		tranil.setDimensions("1");
		tranil.setCoordinateAddress("1");
		tranil.setTypeId("1");
		tranil.setId(UUIDGenerator.getUUID());
		tranil.setStat(DataStatus.ENABLED);
		tranil.setCreateTime(new Date());
		tranil.setCoordinateType(1);
		//保存行车轨迹
		transportTrailDao.insertTranSportTrailInfo(tranil);
	
//	transportTrailDao.save(trail);
    }
}

