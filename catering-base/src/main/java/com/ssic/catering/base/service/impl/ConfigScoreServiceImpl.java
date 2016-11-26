package com.ssic.catering.base.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ssic.catering.base.dao.ConfigScoreDao;
import com.ssic.catering.base.dto.CafetoriumDto;
import com.ssic.catering.base.dto.ConfigDto;
import com.ssic.catering.base.dto.ConfigScoreDto;
import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.pojo.ConfigScore;
import com.ssic.catering.base.service.ICafetoriumService;
import com.ssic.catering.base.service.IConfigScoreService;
import com.ssic.catering.base.service.IConfigService;
import com.ssic.util.BeanUtils;
/**
 * 		
 * <p>Title: ConfigScoreServiceImpl </p>
 * <p>Description: 食堂评分逻辑层</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 张亚伟	
 * @date 2015年8月11日 下午1:33:55	
 * @version 1.0
 * <p>修改人：张亚伟</p>
 * <p>修改时间：2015年8月11日 下午1:33:55</p>
 * <p>修改备注：</p>
 */
@Service
public class ConfigScoreServiceImpl implements IConfigScoreService {

	@Autowired
	private ConfigScoreDao configScoreDao;
	@Autowired
	private ICafetoriumService cafetoriumService;
	@Autowired
	private IConfigService configService;
	
	
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
		
		return configScoreDao.findCreateTimeDistinct(cafetoriumId,
				index, size);
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
	@Override
	public List<ConfigScoreDto> findConfigScoreListToCafetoriumIdByCreateTime(
			String cafetoriumId, String time) {
		List<ConfigScore> configScoreList = null;
		//如果时间为空,获取所有评分信息
		if (time==null) {
			configScoreList = configScoreDao.findConfigScoreListToCafetoriumId(cafetoriumId);
		}else{
			configScoreList = configScoreDao.findConfigScoreListToCafetoriumIdByCreateTime(cafetoriumId,time);
		}
		if (configScoreList==null) {
			return null;
		}
		List<ConfigScoreDto>ConfigScoreDtoList=new ArrayList<>();
		for (ConfigScore configScore : configScoreList) {
			ConfigScoreDto configScoreDto=new ConfigScoreDto();
			BeanUtils.copyProperties(configScore, configScoreDto);
			ConfigScoreDtoList.add(configScoreDto);
		}
		return ConfigScoreDtoList;
	}


    
     /** 
     * (non-Javadoc)   
     * @see com.ssic.catering.base.service.IConfigScoreService#findALL(java.lang.String, com.ssic.catering.base.dto.PageHelperDto)   
     */
    @Override
    public List<ConfigScoreDto> findALL(String caftrotiumId, PageHelperDto phDto)
    {     
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<ConfigScoreDto> result = new ArrayList<ConfigScoreDto>();
        ConfigScore configScore = new ConfigScore();
        configScore.setCafetoriumId(caftrotiumId);
        List<ConfigScore> list = configScoreDao.findAll(configScore, phDto);
        if (!CollectionUtils.isEmpty(list))
        {
            result = BeanUtils.createBeanListByTarget(list, ConfigScoreDto.class);
            for (ConfigScoreDto dto : result)
            {
                if (!StringUtils.isEmpty(dto.getCafetoriumId()))
                {
                    CafetoriumDto cafe = cafetoriumService.findById(dto.getCafetoriumId());
                    dto.setCafeName(cafe.getCafeName());
                }
                if (!StringUtils.isEmpty(dto.getConfigId()))
                {
                    ConfigDto config= configService.findConfigToId(dto.getConfigId());
                    dto.setConfigName(config.getConfigName());
                }
                String date = sdf.format(dto.getCreateTime());
                dto.setGroupTime(date);
            }
            return result;
        }
        return result;
    }


    
     /** 
     * (non-Javadoc)   
     * @see com.ssic.catering.base.service.IConfigScoreService#findCount(java.lang.String)   
     */
    @Override
    public int findCount(String caftrotiumId)
    {
        ConfigScore configScore = new ConfigScore();
        configScore.setCafetoriumId(caftrotiumId);
        int counts = configScoreDao.findCount(configScore);
        return counts;
    }

}

