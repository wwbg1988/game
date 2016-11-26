package com.ssic.catering.base.dao;

import java.util.Date;
import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ssic.catering.base.dto.ConfigScoreDto;
import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.mapper.ConfigScoreExMapper;
import com.ssic.catering.base.mapper.ConfigScoreMapper;
import com.ssic.catering.base.pojo.Cafetorium;
import com.ssic.catering.base.pojo.CommentExample;
import com.ssic.catering.base.pojo.ConfigScore;
import com.ssic.catering.base.pojo.ConfigScoreExample;
import com.ssic.catering.base.pojo.ConfigScoreExample.Criteria;
import com.ssic.util.UUIDGenerator;
import com.ssic.util.base.MyBatisBaseDao;
import com.ssic.util.constants.DataStatus;

/**
 * 		
 * <p>Title: ConfigScoreDao </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author pengcheng.yang	
 * @date 2015年8月3日 下午9:12:25	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年8月3日 下午9:12:25</p>
 * <p>修改备注：</p>
 */
@Repository
public class ConfigScoreDao extends MyBatisBaseDao<ConfigScore> {
	@Autowired
	@Getter
	private ConfigScoreMapper mapper;

	@Autowired
	private ConfigScoreExMapper exMapper;





	/**
	 * 
	 * insertConfigScore：保存用户提交的评分
	 * @param configScore
	 * @exception	
	 * @author pengcheng.yang
	 * @date 2015年8月4日 上午10:30:35
	 */
	public void insertConfigScore(List<ConfigScore> configScore) {
		for (int i = 0; i < configScore.size(); i++) {
			ConfigScore score = configScore.get(i);
			score.setId(UUIDGenerator.getUUID());
			score.setCreateTime(new Date());
			score.setStat(DataStatus.ENABLED);
			mapper.insertSelective(score);
		}
	}

	/**
	 * 
	 * findCountByCafetoriumAndCreateTime：通过食堂集合和时间查询评分条数
	 * @param cafetoriumList 食堂集合
	 * @param createTime 创建时间
	 * @return
	 * @exception	
	 * @author 张亚伟
	 * @date 2015年8月10日 下午1:50:21
	 */
	public Integer findCountByCafetoriumAndCreateTime(
			List<Cafetorium> cafetoriumList, Date createTime) {


		return exMapper.findCountByCafetoriumAndCreateTime(cafetoriumList,createTime);
	}


	//获取该区域下的食堂历史评分
	public Integer findHistoryCountByCafetorium(List<Cafetorium> cafetoriumList){
		return exMapper.findHistoryCountByCafetorium(cafetoriumList);
	}
	

    
    /**
     * 
     * findBy：一句话描述方法功能
     * @param param
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年8月4日 上午8:49:16
     */
    public List<ConfigScore> findBy(ConfigScore param){
        ConfigScoreExample example = new ConfigScoreExample();
        Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(param.getCafetoriumId())){
            criteria.andCafetoriumIdEqualTo(param.getCafetoriumId());
        }
        criteria.andStatEqualTo(DataStatus.ENABLED);
        return   mapper.selectByExample(example);
    }
    
    /**
     * 
     * findById：一句话描述方法功能
     * @param id
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年8月4日 上午8:49:05
     */
    public ConfigScore findById(String id){
        if(!StringUtils.isEmpty(id)){
            return mapper.selectByPrimaryKey(id);
        }
        return null;
    }
    


    
    /**
     * countAvgScore：计算每个餐厅评分项的平均分
     * @param cafetoriumId
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年8月8日 上午10:29:48
     */
    public List<ConfigScoreDto> countAvgScore(String cafetoriumId){
        if(!StringUtils.isEmpty(cafetoriumId)){
           return this.exMapper.CountScore(cafetoriumId);
        }
        return null;
    }

    /**
     * 
     * findScoreByCafetoriumAndCreateTime：通过餐厅集合和时间获取评分条数
     * @param cafetoriumList 餐厅集合
     * @param date 时间
     * @return
     * @exception	
     * @author 张亚伟
     * @date 2015年8月10日 下午8:24:52
     */
	public Integer findScoreByCafetoriumAndCreateTime(
			List<Cafetorium> cafetoriumList, Date date) {
		return exMapper.findScoreByCafetoriumAndCreateTime(cafetoriumList, date);
	}
	
	//获取历史评分条数
	
	public Integer findHistoryScoreByCafetorium(List<Cafetorium> cafetoriumList){
		return exMapper.findHistoryScoreByCafetorium(cafetoriumList);
	}
	
	
	/**
	 * 
	 * findCafetoriumScoreByCafetoriumId：通过食堂Id获取食堂总分
	 * @param cafetoriumId 食堂Id
	 * @param createTime 创建时间
	 * @return
	 * @exception	
	 * @author 张亚伟
	 * @date 2015年8月11日 上午9:41:35
	 */
	public int findCafetoriumScoreByCafetoriumId(String cafetoriumId ,Date createTime) {
		
		return exMapper.findCafetoriumScoreByCafetoriumId(cafetoriumId, createTime);
	}
	
	/**
	 * 
	 * findCafetoriumCountByCafetoriumId：通过食堂Id获取食堂评分条数
	 * @param cafetoriumId 食堂Id
	 * @param createTime 创建时间
	 * @return
	 * @exception	
	 * @author 张亚伟
	 * @date 2015年8月11日 上午9:41:35
	 */
	public int findCafetoriumCountByCafetoriumId(String cafetoriumId ,Date createTime) {
		
		return exMapper.findCafetoriumCountByCafetoriumId(cafetoriumId, createTime);
	}

	
	/**
	 * findCreateTimeDistinct：通过食堂ID获取评分日期(去重)
	 * @param cafetoriumId 食堂ID
	 * @param index 第几页
	 * @param size 每页多少条信息
	 * @return
	 * @exception	
	 * @author 张亚伟
	 * @date 2015年8月11日 下午1:08:19
	 */
	public List<String> findCreateTimeDistinct(String cafetoriumId,
			Integer index, Integer size) {
		return exMapper.findCreateTimeDistinct(cafetoriumId, size*(index-1), size);
	}

	
	/**
	 * findConfigScoreToCreateTime：通过时间获取食堂评分信息
	 * @param cafetoriumId 食堂ID
	 * @param time 时间
	 * @return
	 * @exception	
	 * @author 张亚伟
	 * @date 2015年8月11日 下午3:12:19
	 */
	public List<ConfigScore> findConfigScoreListToCafetoriumIdByCreateTime(
			String cafetoriumId, String time) {
		// TODO 添加方法注释
		return exMapper.findConfigScoreListToCafetoriumIdByCreateTime(cafetoriumId,time);
	}
	
	/**
	 * findConfigScoreListToCafetoriumId：通过食堂ID获取食堂评分信息
	 * @param cafetoriumId 食堂ID
	 * @return
	 * @exception	
	 * @author 张亚伟
	 * @date 2015年8月11日 下午3:12:19
	 */
	public List<ConfigScore> findConfigScoreListToCafetoriumId(
			String cafetoriumId) {
		ConfigScoreExample example = new ConfigScoreExample();
        Criteria criteria = example.createCriteria();
        criteria.andCafetoriumIdEqualTo(cafetoriumId);
		return mapper.selectByExample(example);
	}

    
    /**     
     * findAll：一句话描述方法功能
     * @param configScore
     * @param phDto
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年8月14日 下午1:53:23	 
     */
    public List<ConfigScore> findAll(ConfigScore param, PageHelperDto ph)
    {
        ConfigScoreExample example = new ConfigScoreExample();
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

        if (!StringUtils.isEmpty(param.getId()))
        {
            criteria.andIdEqualTo(param.getId());
        }
        if (!StringUtils.isEmpty(param.getCafetoriumId()))
        {
            criteria.andCafetoriumIdEqualTo(param.getCafetoriumId());

        }
        criteria.andStatEqualTo(DataStatus.ENABLED);
        return mapper.selectByExample(example);
    }

    
    /**     
     * findCount：一句话描述方法功能
     * @param configScore
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年8月14日 下午1:54:07	 
     */
    public int findCount(ConfigScore param)
    {
        ConfigScoreExample example = new ConfigScoreExample();
        Criteria criteria = example.createCriteria();
        criteria.andStatEqualTo(DataStatus.ENABLED);
        if (!StringUtils.isEmpty(param.getId()))
        {
            criteria.andIdEqualTo(param.getId());
        }
        if (!StringUtils.isEmpty(param.getCafetoriumId()))
        {
            criteria.andCafetoriumIdEqualTo(param.getCafetoriumId());

        }
        int count = mapper.countByExample(example);
        return count;
    }

	
}
