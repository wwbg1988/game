package com.ssic.shop.manage.dao;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssic.shop.manage.mapper.VisitServiceMapper;
import com.ssic.shop.manage.pojo.VisitService;
import com.ssic.util.base.MyBatisBaseDao;

/**		
 * <p>Title: VisitService </p>
 * <p>Description: 大厨上门烧菜</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 朱振	
 * @date 2015年9月15日 下午4:45:54	
 * @version 1.0
 * <p>修改人：朱振</p>
 * <p>修改时间：2015年9月15日 下午4:45:54</p>
 * <p>修改备注：</p>
 */
@Repository
public class VisitServiceDao extends MyBatisBaseDao<VisitService>
{
    @Autowired
    @Getter
    private VisitServiceMapper mapper;
    
}

