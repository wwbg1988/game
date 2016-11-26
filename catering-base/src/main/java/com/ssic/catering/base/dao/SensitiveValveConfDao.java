/**
 * 
 */
package com.ssic.catering.base.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.dto.SensitiveValveConfDto;
import com.ssic.catering.base.mapper.SensitiveValveConfMapper;
import com.ssic.catering.base.pojo.SensitiveValveConf;
import com.ssic.catering.base.pojo.SensitiveValveConfExample;
import com.ssic.catering.base.pojo.SensitiveValveConfExample.Criteria;
import com.ssic.util.base.MyBatisBaseDao;
import com.ssic.util.constants.DataStatus;

/**		
 * <p>Title: SensitiveValveConfDao </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author pengcheng.yang	
 * @date 2015年8月18日 上午10:06:04	
 * @version 1.0
 * <p>修改人：pengcheng.yang</p>
 * <p>修改时间：2015年8月18日 上午10:06:04</p>
 * <p>修改备注：</p>
 */
@Repository
public class SensitiveValveConfDao extends MyBatisBaseDao<SensitiveValveConf>
{
    @Autowired
    @Getter
    private SensitiveValveConfMapper mapper;
    
    /**
     * 
     * findBy：分页查询敏感词预警阀值信息
     * @param param
     * @param ph
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年8月18日 上午10:15:07
     */
    public List<SensitiveValveConf> findBy(SensitiveValveConf param,PageHelperDto ph,List<String> listStr){
        SensitiveValveConfExample example = new SensitiveValveConfExample();
        Criteria criteria = example.createCriteria();
        if (ph != null && !StringUtils.isEmpty(String.valueOf(ph.getBeginRow()))
            && !StringUtils.isEmpty(String.valueOf(ph.getRows())))
        {
            int beginRow = ph.getBeginRow();
            int rows = ph.getRows();
            example.setOrderByClause("create_time desc limit " + beginRow + "," + rows);
        }
        else
        {
            example.setOrderByClause("create_time desc");
        }
        if(!StringUtils.isEmpty(param.getProjId())){
            criteria.andProjIdEqualTo(param.getProjId());
        }
        criteria.andProjIdIn(listStr);
        criteria.andStatEqualTo(DataStatus.ENABLED);
        return mapper.selectByExample(example);
    }
    /**
     * 
     * findCountBy：统计敏感词预警阀值信息条数
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年8月18日 上午10:33:22
     */
    public int findCountBy(List<String> listStr){
        SensitiveValveConfExample example = new SensitiveValveConfExample();
        Criteria criteria = example.createCriteria();
        criteria.andProjIdIn(listStr);
        criteria.andStatEqualTo(DataStatus.ENABLED);
        return mapper.countByExample(example);
    }
    
    /**
     * 
     * sensitiveVconfAdd：添加敏感词预警阀值
     * @param conf
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年8月18日 下午2:26:33
     */
    public int sensitiveVconfAdd(SensitiveValveConf conf){
        if(!StringUtils.isEmpty(conf)){
            return mapper.insert(conf);
        }
        return 0;
    }
    
    /**
     * 
     * sensitiveVconfDelete：删除敏感词预警阀值
     * @param conf
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年8月18日 下午2:48:35
     */
    public int sensitiveVconfDelete(SensitiveValveConf conf){
        if(!StringUtils.isEmpty(conf)){
            return mapper.updateByPrimaryKeySelective(conf);
        }
        return 0;
    }
    
    /**
     * 
     * getSensitiveById：根据前Id查询出敏感词预警阀值
     * @param id
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年8月18日 下午3:33:14
     */
    public SensitiveValveConf getSensitiveConfById(String id){
        if(!StringUtils.isEmpty(id)){
            return mapper.selectByPrimaryKey(id);
        }
        return null;
    }
    
    /**
     * 
     * sensitiveVconfUpdate：修改敏感词预警阀值
     * @param conf
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年8月18日 下午4:27:53
     */
    public int sensitiveVconfUpdate(SensitiveValveConf conf){
          return mapper.updateByPrimaryKey(conf);
    }
}

