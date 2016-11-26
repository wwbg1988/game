package com.ssic.shop.manage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssic.shop.manage.dao.VisitServiceDao;
import com.ssic.shop.manage.dto.VisitServiceDto;
import com.ssic.shop.manage.pojo.VisitService;
import com.ssic.shop.manage.service.IVisitServiceService;
import com.ssic.util.BeanUtils;

/**		
 * <p>Title: VisitServiceServiceImpl </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 朱振	
 * @date 2015年9月15日 下午5:14:04	
 * @version 1.0
 * <p>修改人：朱振</p>
 * <p>修改时间：2015年9月15日 下午5:14:04</p>
 * <p>修改备注：</p>
 */
@Service
public class VisitServiceServiceImpl implements IVisitServiceService
{
    @Autowired
    private VisitServiceDao visitServiceDao;

    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.shop.manage.service.VisitServiceService#save(com.ssic.shop.manage.dto.VisitServiceDto)
     */
    @Override
    public int save(VisitServiceDto visitServiceDto)
    {
        if(visitServiceDto != null)
        {
            VisitService visitService = new VisitService();
            BeanUtils.copyProperties(visitServiceDto, visitService);
            
            return visitServiceDao.insert(visitService);
        }
   
        return 0;
    }


}

