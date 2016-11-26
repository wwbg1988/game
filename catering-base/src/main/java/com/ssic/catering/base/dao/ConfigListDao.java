package com.ssic.catering.base.dao;

import java.util.Date;
import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ssic.catering.base.mapper.ConfigListMapper;
import com.ssic.catering.base.pojo.Cafetorium;
import com.ssic.catering.base.pojo.ConfigExample;
import com.ssic.catering.base.pojo.ConfigList;
import com.ssic.catering.base.pojo.ConfigListExample;
import com.ssic.catering.base.pojo.ConfigListExample.Criteria;
import com.ssic.util.base.MyBatisBaseDao;
import com.ssic.util.constants.DataStatus;

import freemarker.core.ReturnInstruction.Return;

/**
 * 		
 * <p>Title: ConfigListDao </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author pengcheng.yang	
 * @date 2015年8月4日 上午9:01:26	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年8月4日 上午9:01:26</p>
 * <p>修改备注：</p>
 */
@Repository
public class ConfigListDao extends MyBatisBaseDao<ConfigList> {


	@Autowired
	@Getter
	private ConfigListMapper mapper;

    /**
     * 
     * findBy：一句话描述方法功能
     * @param param
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年8月4日 上午9:26:44
     */
    public List<ConfigList> findBy(ConfigList param){
        ConfigListExample example = new ConfigListExample();
        Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(param.getCafetoriumId())){
            criteria.andCafetoriumIdEqualTo(param.getCafetoriumId());
        }
        criteria.andStatEqualTo(DataStatus.ENABLED);
        
        return mapper.selectByExample(example);
    }
    /**
     * 
     * findById：一句话描述方法功能
     * @param id
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年8月4日 上午9:27:17
     */
    public ConfigList findById(String id){
        if(!StringUtils.isEmpty(id)){
            return mapper.selectByPrimaryKey(id);
        }
        return null;
    }
    public void delByPrimaryKey(String id){
        ConfigList record = new ConfigList();
        record.setId(id);
        record.setStat(DataStatus.DISABLED);
        mapper.updateByPrimaryKeySelective(record);
    }
    

}
