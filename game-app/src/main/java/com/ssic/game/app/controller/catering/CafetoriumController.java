package com.ssic.game.app.controller.catering;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.catering.base.dto.AddressDto;
import com.ssic.catering.base.dto.CafetoriumDto;
import com.ssic.catering.base.dto.ConfigDto;
import com.ssic.catering.base.dto.ConfigScoreDto;
import com.ssic.catering.base.service.IAddressService;
import com.ssic.catering.base.service.ICafetoriumService;
import com.ssic.catering.base.service.IConfigScoreService;
import com.ssic.util.model.Response;

/**
 * <p>Title: CafetoriumController </p>
 * <p>Description: 食堂应用层接口</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 张亚伟	
 * @date 2015年8月12日 下午5:40:52	
 * @version 1.0
 * <p>修改人：张亚伟</p>
 * <p>修改时间：2015年8月12日 下午5:40:52</p>
 * <p>修改备注：</p>
 */
@RequestMapping("/cafetorium")
@Controller
public class CafetoriumController {

	@Autowired
	private ICafetoriumService iCafetoriumService;

	@Autowired
	private IAddressService iAddressService;
	@Autowired
	private IConfigScoreService iConfigScoreService;

	/**
	 * findCafetoriumAll：获取所有食堂和食堂的平均分
	 * @return
	 * @exception
	 * @author 张亚伟
	 * @date 2015年8月26日 下午4:02:09
	 */
	@RequestMapping("/findCafetoriumAll")
	@ResponseBody
	public Response<List<CafetoriumDto>> findCafetoriumAll() {
		Response<List<CafetoriumDto>> response = new Response<List<CafetoriumDto>>();
		//获取区域下的食堂信息并封装食堂的平均分
		List<CafetoriumDto> cafetoriumList = iCafetoriumService
				.findCafetoriumAll();
		if (cafetoriumList == null) {
			response.setStatus(500);
			response.setMessage("未查找到食堂信息");
			return response;
		}

		response.setStatus(200);
		response.setMessage("success");
		response.setData(cafetoriumList);
		return response;
	}

	/**
	 * getCafetoriumList：通过区域Id获取本区域下所有的食堂
	 * @param addressId 区域ID
	 * @return
	 * @exception	
	 * @author 张亚伟
	 * @date 2015年8月10日 下午8:47:31
	 */
	@RequestMapping("/getCafetoriumList")
	@ResponseBody
	public Response<List<CafetoriumDto>> getCafetoriumList(String addressId) {

		Response<List<CafetoriumDto>> response = new Response<List<CafetoriumDto>>();
		//获取区域信息
		AddressDto addressDto = iAddressService.findById(addressId);
		if (addressDto == null) {
			response.setStatus(500);
			response.setMessage("区域信息错误");
			return response;
		}

		//获取区域下的食堂信息并封装食堂的平均分
		List<CafetoriumDto> cafetoriumList = iCafetoriumService
				.findCafetoriumListByAddressId(addressId);
		if (cafetoriumList == null) {
			response.setStatus(500);
			response.setMessage("未查找到食堂信息");
			return response;
		}
		
		for(CafetoriumDto cafetoriumDto:cafetoriumList){
			//获取根据分页获取日期区间
			String cafetoriumId = cafetoriumDto.getId();
			List<String> timeList = iConfigScoreService.findCreateTimeDistinct(
					cafetoriumId, 1, 1000);
			if (timeList == null || timeList.size()==0) {
				cafetoriumDto.setHistoryScore("0.0");
			}else{
				//通过日期获取评分餐厅历史评分信息
				Double allScore2 = 0.0;
				for (String time : timeList) {
					//获取食堂评分信息
					List<ConfigScoreDto> configScoreList = iConfigScoreService
							.findConfigScoreListToCafetoriumIdByCreateTime(
									cafetoriumId, time);
					if(configScoreList!=null && configScoreList.size()>0){
						Double allScore1 = 0.0;
						for(ConfigScoreDto configDto2 : configScoreList){
							String score2 = configDto2.getScore();
							Double dScore2 = Double.parseDouble(score2);
							allScore1 = allScore1 +dScore2;
						}
						Double avgScore1 = allScore1 / configScoreList.size();
						//dayHistoryDto.setDayScore(avgScore1.toString().substring(0,3));
						allScore2 = allScore2+avgScore1;
					}
				}
				allScore2 = allScore2 / timeList.size();
				cafetoriumDto.setHistoryScore(allScore2.toString().substring(0,3));
			}
		}


		response.setStatus(200);
		response.setMessage("success");
		response.setData(cafetoriumList);
		return response;
	}

}
