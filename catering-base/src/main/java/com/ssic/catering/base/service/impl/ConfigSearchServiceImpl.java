package com.ssic.catering.base.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssic.catering.base.dao.AddressDao;
import com.ssic.catering.base.dao.CafetoriumDao;
import com.ssic.catering.base.dao.CommentDao;
import com.ssic.catering.base.dao.ConfigDao;
import com.ssic.catering.base.dao.ConfigListDao;
import com.ssic.catering.base.dao.ConfigScoreDao;
import com.ssic.catering.base.dao.SensitiveDao;
import com.ssic.catering.base.dto.CommentDto;
import com.ssic.catering.base.dto.ConfigDto;
import com.ssic.catering.base.dto.FindDataResults;
import com.ssic.catering.base.dto.PageConfigDto;
import com.ssic.catering.base.dto.SensitiveDto;
import com.ssic.catering.base.pojo.Address;
import com.ssic.catering.base.pojo.Cafetorium;
import com.ssic.catering.base.pojo.Config;
import com.ssic.catering.base.pojo.ConfigList;
import com.ssic.catering.base.pojo.Sensitive;
import com.ssic.catering.base.service.IConfigSearchService;
import com.ssic.catering.base.service.IPageConfigService;
import com.ssic.catering.base.util.MyComparator;
import com.ssic.shop.manage.dto.LuckyDrawHistoryDto;
import com.ssic.shop.manage.service.LuckyDrawHistoryService;
import com.ssic.util.BeanUtils;
import com.ssic.util.UUIDGenerator;
import com.ssic.util.constants.DataStatus;
import com.ssic.util.model.Response;

/**
 * 
 * <p>Title: ConfigSearchService</p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015</p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author pengcheng.yang
 * @date 2015年8月3日 下午8:41:35
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年8月3日 下午8:41:35</p>
 * <p>修改备注：</p>
 */
@Service
public class ConfigSearchServiceImpl implements IConfigSearchService {

	@Autowired
	private ConfigListDao configListdao;
	
	@Autowired
	private ConfigScoreDao configScoreDao;

	@Autowired
	private ConfigDao configDao;

	@Autowired
	private SensitiveDao sensitiveDao;

	@Autowired
	private CommentDao commentDao;

	@Autowired
	private AddressDao addressDao;

	@Autowired
	private CafetoriumDao cafetoriumDao;
	
	@Autowired
    private LuckyDrawHistoryService luckyDrawHistoryService;
	
	@Autowired
	private IPageConfigService pageConfigService;

	/**
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.ssic.catering.common.service.IConfigSearchService#findBy(java.lang.String)
	 */
	@Override
	public Response<FindDataResults> findBy(String cafetoriumId, String userId) {
		if (!StringUtils.isEmpty(cafetoriumId)) {

			FindDataResults findDataResults = new FindDataResults();
			//检查用户有没有评论过
			int flag = commentDao.queryIfComments(cafetoriumId, userId);
			//flag判断用户今天有没有评论过 1:已经评论 0:未评论
			if (flag == 0) {
				// 根据餐厅Id查询餐厅评分项
				findDataResults.setResultConfig(findConfigDto(cafetoriumId));
				// 查询出敏感词
				findDataResults.setResultSensitive(findSensitiveDto(cafetoriumId));
				// 餐厅ID
				findDataResults.setCafetoriumId(cafetoriumId);
				//评论的UUID
				findDataResults.setUUID(UUIDGenerator.getUUID());
				//当天没有评论
				findDataResults.setFlag("0");
			} else {
			    //是否中奖(IsLottery):1是;0:否; //是否兑换奖品(IsExchange):1是;0:否;
			    
			    PageConfigDto param = new PageConfigDto();
			    param.setCafetoriumId(cafetoriumId);
			    param.setName("抽奖页面");
			    List<PageConfigDto> pageConfigDtos = pageConfigService.getPageConfigDtoBy(param, null);
			    if(!CollectionUtils.isEmpty(pageConfigDtos) && pageConfigDtos.size()==1)
			    {
			        PageConfigDto item = pageConfigDtos.get(0);
			        
			        //微信页面的配置
			        findDataResults.setPageConfig(item);
			        
			        if(item.getIsEnabled() != null && 1 == item.getIsEnabled())
			        {
			            //使用抽奖页面
			            LuckyDrawHistoryDto dto = luckyDrawHistoryService.queryLuckyDrawHoistoryInfo(userId);
		                if(!StringUtils.isEmpty(dto)){
		                    //表示用户还有抽奖机会
		                    if(dto.getTotalLuckyCount()>0){
		                        //判断如果中奖没有兑换的情况跳转兑换页面
		                        if(dto.getIsLottery() == 1 && dto.getIsExchange() == 0){
		                            findDataResults.setFlag("1");
		                        }
		                        //判断没有中奖并且还有抽奖机会跳转抽奖转盘页面
		                        if(dto.getIsLottery() == 0){
		                            findDataResults.setFlag("2");
		                        }
		                        //判断已中奖兑换并且还有抽奖机会跳转抽奖转盘页面
		                        if(dto.getIsLottery() == 1 && dto.getIsExchange() == 1){
		                            findDataResults.setFlag("2");
		                        }
		                    }else{ //用户没有抽奖机会
		                        //判断如果中奖且没有兑换的情况跳转兑换页面
		                        if(dto.getIsLottery() == 1 && dto.getIsExchange() == 0){
		                            findDataResults.setFlag("1");
		                        }
		                        //判断没中奖跳转没中奖感谢页面
		                        if(dto.getIsLottery() == 0){
		                            findDataResults.setFlag("3");
		                        }
		                        //判断中奖了并且已兑换跳转兑换成功感谢页面    
		                        if(dto.getIsLottery() == 1 && dto.getIsExchange() == 1){
		                            findDataResults.setFlag("4");
		                        }
		                        
		                    }
		                }else{
		                    findDataResults.setFlag("-1");
		                }
			        }
			            
			    }
			    else//没有配置时，默认使用抽奖页面
			    {
			      //使用抽奖页面
                    LuckyDrawHistoryDto dto = luckyDrawHistoryService.queryLuckyDrawHoistoryInfo(userId);
                    if(!StringUtils.isEmpty(dto)){
                        //表示用户还有抽奖机会
                        if(dto.getTotalLuckyCount()>0){
                            //判断如果中奖没有兑换的情况跳转兑换页面
                            if(dto.getIsLottery() == 1 && dto.getIsExchange() == 0){
                                findDataResults.setFlag("1");
                            }
                            //判断没有中奖并且还有抽奖机会跳转抽奖转盘页面
                            if(dto.getIsLottery() == 0){
                                findDataResults.setFlag("2");
                            }
                            //判断已中奖兑换并且还有抽奖机会跳转抽奖转盘页面
                            if(dto.getIsLottery() == 1 && dto.getIsExchange() == 1){
                                findDataResults.setFlag("2");
                            }
                        }else{ //用户没有抽奖机会
                            //判断如果中奖且没有兑换的情况跳转兑换页面
                            if(dto.getIsLottery() == 1 && dto.getIsExchange() == 0){
                                findDataResults.setFlag("1");
                            }
                            //判断没中奖跳转没中奖感谢页面
                            if(dto.getIsLottery() == 0){
                                findDataResults.setFlag("3");
                            }
                            //判断中奖了并且已兑换跳转兑换成功感谢页面    
                            if(dto.getIsLottery() == 1 && dto.getIsExchange() == 1){
                                findDataResults.setFlag("4");
                            }
                            
                        }
                    }else{
                        findDataResults.setFlag("-1");
                    }
			    }
			    
			   
			}

			return new Response<FindDataResults>(DataStatus.HTTP_SUCCESS, "",findDataResults);
		} else {
			return new Response<FindDataResults>(DataStatus.HTTP_FAILE,
					"无法查询到query信息", null);
		}
	}


	/**
	 * 
	 * findConfigDto：根据餐厅Id查询餐厅评分项
	 * 
	 * @param cafetoriumId
	 * @return
	 * @exception
	 * @author pengcheng.yang
	 * @date 2015年8月4日 下午1:40:35
	 */
	private List<ConfigDto> findConfigDto(String cafetoriumId) {
		List<ConfigDto> configDtoList = new ArrayList<ConfigDto>();
		ConfigList param = new ConfigList();
		param.setCafetoriumId(cafetoriumId);
		List<ConfigList> configList = configListdao.findBy(param);
		for (int i = 0; i < configList.size(); i++) {
			String configId = configList.get(i).getConfigId();
			if (!StringUtils.isEmpty(configId)) {
				Config config = configDao.findById(configId);
				if (config != null) {
					ConfigDto configDto = new ConfigDto();
					BeanUtils.copyProperties(config, configDto);
					configDtoList.add(configDto);
				}
			}
		}

		Collections.sort(configDtoList, new MyComparator());
		return configDtoList;

	}

	/**
	 * 
	 * findSensitiveDto：查询出敏感词热度最高的9个
	 * 
	 * @param cafetoriumId
	 * @return
	 * @exception
	 * @author pengcheng.yang
	 * @date 2015年8月4日 下午1:46:21
	 */
	public List<SensitiveDto> findSensitiveDto(String cafetoriumId) {
		List<Sensitive> tempList = sensitiveDao
				.findBySensitiveTop9(cafetoriumId);
		List<SensitiveDto> resultList = null;
		if (tempList != null && tempList.size() > 0) {
			resultList = BeanUtils.createBeanListByTarget(tempList,
					SensitiveDto.class);
		}
		return resultList;
	}

	/**
	 * findConfigScoreByAddressCode：通过区域辅助码获取这个区域的平均评分
	 * @param addressCode
	 * @return
	 * @exception	
	 * @author 张亚伟
	 * @date 2015年8月10日 上午9:55:41
	 */
	@Override
	public String findConfigScoreByAddressCode(String addressCode) {
		//通过区域辅助码获取所有区域
		List<Address> addressList = addressDao
				.findAddressListByAddressCode(addressCode);

		if (CollectionUtils.isEmpty(addressList)) {
			return null;
		}

		//定义存储所有食堂的变量
		List<Cafetorium> cafetoriumList = new ArrayList<>();

		//获取这个区域下的所有食堂
		for (Address address : addressList) {
			List<Cafetorium> cafetoriumTempList = cafetoriumDao
					.findCafetoriumListByaddressId(address.getId());
			if (cafetoriumTempList != null) {
				cafetoriumList.addAll(cafetoriumTempList);
			}
		}

		//获取食堂集合的总评分和条数

		if (CollectionUtils.isEmpty(cafetoriumList)) {
			return null;
		}
		//食堂评分条数
		Integer count = configScoreDao.findCountByCafetoriumAndCreateTime(cafetoriumList,new Date());
		if (count == 0) {
			return null;
		}
		//获取食堂集合总分数
		Integer score=configScoreDao.findScoreByCafetoriumAndCreateTime(cafetoriumList,new Date());
		
		if (score==0) {
			return null;
		}
		
		//获取平均分
		double aveScore=Double.parseDouble(score+"")/Double.parseDouble(count+"");
		
		return aveScore+"";
	}
	
	
	
	public String findConfigHistoryScoreByAddressCode(String addressCode) {
		//通过区域辅助码获取所有区域
		List<Address> addressList = addressDao
				.findAddressListByAddressCode(addressCode);

		if (CollectionUtils.isEmpty(addressList)) {
			return null;
		}

		//定义存储所有食堂的变量
		List<Cafetorium> cafetoriumList = new ArrayList<>();

		//获取这个区域下的所有食堂
		for (Address address : addressList) {
			List<Cafetorium> cafetoriumTempList = cafetoriumDao
					.findCafetoriumListByaddressId(address.getId());
			if (cafetoriumTempList != null) {
				cafetoriumList.addAll(cafetoriumTempList);
			}
		}

		//获取食堂集合的总评分和条数

		if (CollectionUtils.isEmpty(cafetoriumList)) {
			return null;
		}
		//食堂评分条数
		Integer count = configScoreDao.findHistoryCountByCafetorium(cafetoriumList);
		if (count == 0) {
			return null;
		}
		//获取食堂集合总分数
		Integer score=configScoreDao.findHistoryScoreByCafetorium(cafetoriumList);
		
		if (score==0) {
			return null;
		}
		
		//获取平均分
		double aveScore=Double.parseDouble(score+"")/Double.parseDouble(count+"");
		
		return aveScore+"";
	}

    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.catering.base.service.IConfigSearchService#findCountComments()
     */
    @Override
    public List<CommentDto> findCountComments()
    {
        return commentDao.findCountComments();
    }


}
