package com.ssic.catering.base.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ssic.catering.base.dto.ConfigScoreValveConfDto;
import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.mapper.ConfigScoreValveConfExMapper;
import com.ssic.catering.base.mapper.ConfigScoreValveConfMapper;
import com.ssic.catering.base.pojo.ConfigScoreValveConf;
import com.ssic.catering.base.pojo.ConfigScoreValveConfExample;
import com.ssic.catering.base.pojo.SensitiveValveConf;
import com.ssic.catering.base.pojo.ConfigScoreValveConfExample.Criteria;
import com.ssic.util.BeanUtils;
import com.ssic.util.base.MyBatisBaseDao;
import com.ssic.util.constants.DataStatus;

/**
 * 		
 * <p>Title: ConfigScoreValveConfDao </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年8月10日 上午10:18:20	
 * @version 1.0
 * <p>修改人：pengcheng.yang</p>
 * <p>修改时间：2015年8月10日 上午10:18:20</p>
 * <p>修改备注：</p>
 */
@Repository
public class ConfigScoreValveConfDao extends MyBatisBaseDao<ConfigScoreValveConf>
{
    @Autowired
    @Getter
    private ConfigScoreValveConfMapper mapper;
    
    @Autowired
    private ConfigScoreValveConfExMapper cmapper;
    
    public List<ConfigScoreValveConf> queryConfigId(String cafetoriumId){
        ConfigScoreValveConfExample example = new ConfigScoreValveConfExample();
        Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(cafetoriumId)){
           criteria.andCafetoriumIdEqualTo(cafetoriumId);
        }
        criteria.andStatEqualTo(DataStatus.ENABLED);
        return mapper.selectByExample(example);
    }
    
    //保存餐厅评分预警标准
    public int insertConfScoreValue(ConfigScoreValveConfDto dto){
        return cmapper.insertConfScoreValue(dto);
    }
    //更新餐厅评分预警标准
    public int updateConfScoreValue(ConfigScoreValveConfDto dto){
        return cmapper.updateConfScoreValue(dto);
    }
    //删除餐厅评分预警标准
    public int deleteConfScoreValue(ConfigScoreValveConfDto dto){
        return cmapper.deleteConfScoreValue(dto);
    }
    
    /**
     * 
     * findBy：分页查询评分项预警阀值
     * @param param
     * @param ph
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年8月20日 下午1:53:41
     */
    public List<ConfigScoreValveConf> findBy(ConfigScoreValveConf param,List<String> list,PageHelperDto ph){
        ConfigScoreValveConfExample example = new ConfigScoreValveConfExample();
        Criteria criteria = example.createCriteria();
        if (ph != null && !StringUtils.isEmpty(String.valueOf(ph.getBeginRow()))
            && !StringUtils.isEmpty(String.valueOf(ph.getRows())))
        {
            int beginRow = ph.getBeginRow();
            int rows = ph.getRows();
            example.setOrderByClause("create_time desc limit " + beginRow + "," + rows);
            criteria.andCafetoriumIdIn(list);
        }
        else
        {
            criteria.andCafetoriumIdIn(list);
            example.setOrderByClause("create_time desc");
        }
        criteria.andStatEqualTo(DataStatus.ENABLED);
        return mapper.selectByExample(example);
    }
    /**
     * 
     * findCountBy：查询评分项预警数据总数
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年8月20日 下午2:21:41
     */
    public int findCountBy(List<String> list){
        ConfigScoreValveConfExample example = new ConfigScoreValveConfExample();
        Criteria criteria = example.createCriteria();
        criteria.andCafetoriumIdIn(list);
        criteria.andStatEqualTo(DataStatus.ENABLED);
        return mapper.countByExample(example);
    }
    
    /**
     * 
     * getConfigConfById：根据Id查询对应食堂的预警阀值
     * @param id
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年8月20日 下午2:54:12
     */
    public ConfigScoreValveConf getConfigConfById(String id){
        if(!StringUtils.isEmpty(id)){
            return mapper.selectByPrimaryKey(id);
        }
        return null;
    }
    
    /**
     * 
     * UpdateConfigConfById：删除评分项敏感词预警阀值
     * @param conf
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年8月20日 下午2:56:37
     */
    public int deleteConfigConfById(ConfigScoreValveConf conf){
        if(!StringUtils.isEmpty(conf)){
            return mapper.updateByPrimaryKeySelective(conf);
        }
        return 0;
    }
    /**
     * 
     * updateConfigConfById：修改评分项敏感词预警阀值
     * @param conf
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年8月20日 下午3:21:44
     */
    public int updateConfigConfById(ConfigScoreValveConf conf){
        if(!StringUtils.isEmpty(conf)){
            return mapper.updateByPrimaryKey(conf);
        }
        return 0;
    }
}

