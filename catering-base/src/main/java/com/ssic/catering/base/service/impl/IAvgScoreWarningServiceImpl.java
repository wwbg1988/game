package com.ssic.catering.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssic.catering.base.dao.AvgScoreWarningDao;
import com.ssic.catering.base.dto.AvgScoreWarningDto;
import com.ssic.catering.base.pojo.AvgScoreWarning;
import com.ssic.catering.base.service.IAvgScoreWarningService;
import com.ssic.util.BeanUtils;

/**
 * 		
 * <p>Title: IAvgScoreWarningServiceImpl </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年8月10日 下午3:37:15	
 * @version 1.0
 * <p>修改人：pengcheng.yang</p>
 * <p>修改时间：2015年8月10日 下午3:37:15</p>
 * <p>修改备注：</p>
 */
@Service
public class IAvgScoreWarningServiceImpl implements IAvgScoreWarningService
{

    @Autowired
    private AvgScoreWarningDao avgScoreWarningDao;
    
    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.catering.base.service.IAvgScoreWarningService#inserAvgScoreWarnInfo(com.ssic.catering.base.dto.AvgScoreWarningDto)
     */
    @Override
    public void inserAvgScoreWarnInfo(AvgScoreWarningDto avgs)
    {
        this.avgScoreWarningDao.inserAvgScoreWarnInfo(avgs);
    }

}

