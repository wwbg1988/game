/**
 * 
 */
package com.ssic.game.app.controller.catering;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ssic.catering.base.dto.AddressUserDto;
import com.ssic.catering.base.dto.CafetoriumDto;
import com.ssic.catering.base.dto.ResultsData;
import com.ssic.catering.base.dto.SensitiveWarningReportDto;
import com.ssic.catering.base.pojo.Address;
import com.ssic.catering.base.pojo.SensitiveWarningReport;
import com.ssic.catering.base.service.IAddressService;
import com.ssic.catering.base.service.IAddressUserService;
import com.ssic.catering.base.service.ICafetoriumService;
import com.ssic.catering.base.service.ICommentScoreService;
import com.ssic.catering.base.service.ISensitiveWarningReportService;
import com.ssic.catering.base.service.SensitiveConmentService;
import com.ssic.game.app.controller.dto.ScoreReportDto;
import com.ssic.game.app.dao.JsonDto;
import com.ssic.game.common.dto.ImsUsersDto;
import com.ssic.game.common.service.IImsUserService;
import com.ssic.util.constants.DataStatus;
import com.ssic.util.model.Response;

/**		
 * <p>Title: SensitiveController </p>
 * <p>Description: 关键词预警</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author yuanbin	
 * @date 2015年8月13日 上午9:02:51	
 * @version 1.0
 * <p>修改人：yuanbin</p>
 * <p>修改时间：2015年8月13日 上午9:02:51</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping("/http/api/catering")
public class SensitiveController {
	  
	@Autowired
	private IAddressUserService iAddressUserService;
	
	@Autowired
	private IImsUserService iImsUserService;
	
	@Autowired
	SensitiveConmentService sensitiveConmentService;
	
	@Autowired
    private ISensitiveWarningReportService report;
	
	@Autowired
	private ICafetoriumService iCafetoriumService;
	
	@Autowired
	private IAddressService iAddressService;
	
	@Autowired
	private ICommentScoreService iCommentScoreService;
    
    @ResponseBody
    @RequestMapping(value = "/sensitive.do")
	/**
	 * getAddressUser：通过用户ID获取用户区域负责人信息
	 * @param userId 用户ID
	 * @return
	 * @exception	
	 * @author  yuanbin
	 * @date 2015年8月13日 上午9:36:32
	 */
    public Response<ScoreReportDto> getSensitive(String reportId,String sensitiveId) {
    	Response<ScoreReportDto> response = new Response<>();
    	ScoreReportDto scoreReportDto =new ScoreReportDto();
    	AddressUserDto addressUser=new AddressUserDto();
    	//查到addressId；
    	SensitiveWarningReport sensitiveWarningReport=sensitiveConmentService.selectByPrimaryKey(reportId);
    	String addressId=sensitiveWarningReport.getAddressId();
    	addressUser.setSensitiveName(sensitiveWarningReport.getSensitiveName());//预警关键字
    	Float per=getPer(reportId);//关键字预警小数；
    	//百分比转换
    	DecimalFormat df = new DecimalFormat("0.00%");
    	addressUser.setSensitivePer(df.format(per));//关键字预警百分比
    	
    	//获取关键词预警饼状图数据
    	List<SensitiveWarningReportDto> warnReportList=sensitiveConmentService.warnaReportList (reportId);
    	if(warnReportList.isEmpty()){
    		response.setStatus(DataStatus.HTTP_FAILE);
			response.setMessage("评论预警没有数据");
    	}
    	//addressUser.setWarnReportList(warnReportList);
    	
    	//根据addressUser表食堂编码唯一性找到唯一的食堂负责人userId;
    	CafetoriumDto cafetoriumDto=iCafetoriumService.findById(sensitiveWarningReport.getCafetoriumId());
    	
    	//addressUser对象；
		AddressUserDto addressUserDto = iAddressUserService
				.finAddressUserByCafeCode(cafetoriumDto.getCafeCode());

		if (addressUserDto != null) {
			//获取用户手机信息和姓名
			ImsUsersDto imsUsersDto = iImsUserService.findByUserId(addressUserDto
					.getUserId());

			if (imsUsersDto != null) {  
				addressUser.setPhone(imsUsersDto.getMobilePhone());
				addressUser.setUserName(imsUsersDto.getName());
				addressUser.setUserImg(imsUsersDto.getUserImage());
			}
		}
		//addressUser.setUserName("联系人");
		
		//大区、城市、食堂地址
		addressUser.setCafetorium(cafetoriumDto.getCafeName());//食堂地址
		addressUser.setAddressCafetorium(getLevelAddress(addressId));//大区、城市
		//对应食堂评论数量
		addressUser.setCommentCount(getAccount(reportId));
		scoreReportDto.setAddressUser(addressUser);
		
    	Date crTime=sensitiveWarningReport.getCreateTime();
    	DateFormat d2 = DateFormat.getDateInstance(); 
        String str2 = d2.format(crTime); 
        
		//进行json拼接
			List<JsonDto> list = new ArrayList<>();
			
			List<JsonDto> data = new ArrayList<>();
			
			Float countFloat = (float) 0;
			
			for (SensitiveWarningReportDto configDto : warnReportList) {
			    	Date conTime=configDto.getCreateTime();
			        String str3 = d2.format(conTime); 
			        
			        if(str2.equals(str3)){
	                       JsonDto jsonDto=new JsonDto();
	                       jsonDto.setName(configDto.getSensitiveName());
	                       jsonDto.setValue(String.valueOf(getPer(configDto.getId())));
	                       data.add(jsonDto);
	                    }
			        
					if(str2.equals(str3) && sensitiveId.equals(configDto.getSensitiveId())){
					   JsonDto jsonDto=new JsonDto();
					   jsonDto.setName(configDto.getSensitiveName());
					   jsonDto.setValue(String.valueOf(getPer(configDto.getId())));
					   list.add(jsonDto);
					}
					//计算除了当前敏感词的其他敏感词占比之和
					if(str2.equals(str3) &&!sensitiveId.equals(configDto.getSensitiveId())){
					    countFloat += getPer(configDto.getId());
					}
			}
			
			if(countFloat>0){
			    JsonDto dto = new JsonDto();
			    dto.setValue(String.valueOf(countFloat));
			    dto.setName("其他");
			    list.add(dto);
			}
			    
				String json = JSON.toJSONString(list);
				String jon = JSON.toJSONString(data);
				
				JSON parse = (JSON) JSONObject.parse(json);
				JSON par = (JSON) JSONObject.parse(jon);
				
				scoreReportDto.setDatas(parse);
				scoreReportDto.setData(par);
				response.setData(scoreReportDto);
				response.setMessage("SUCCESS");
				response.setData(scoreReportDto);
				return response;
				
    }
    
	/**
	 * getAccount：同一食堂同一天的评论条数
	 * @param reportId 
	 * @return
	 * @exception	
	 * @author yuanbin
	 * @date 2015年9月6日 下午17:20:12
	 */
	private String getAccount(String reportId) {
		SensitiveWarningReport sensitiveWarningReport=sensitiveConmentService.selectByPrimaryKey(reportId);
		List<SensitiveWarningReportDto> warnReportList=sensitiveConmentService.warnaReportList (reportId);
		String cafetoriumId=sensitiveWarningReport.getCafetoriumId();
		//时间判断
    	Date crTime=sensitiveWarningReport.getCreateTime();
    	DateFormat d2 = DateFormat.getDateInstance(); 
        String str2 = d2.format(crTime); 
        //同一食堂同一天的评论条数
        int tatol=0;
		for(SensitiveWarningReportDto st:warnReportList){
			Date conTime = st.getCreateTime();
	        String str3 = d2.format(conTime); 
            if(str2.equals(str3)){ 
				tatol=tatol+st.getCount();
            }
		}
		String tatols=String.valueOf(tatol);
		return tatols;
	}

	/**
	 * getPer：计算当天每一条评论的百分比
	 * @param reportId 
	 * @return
	 * @exception	
	 * @author yuanbin
	 * @date 2015年9月6日 下午14:20:12
	 */
    //计算当天评论的百分比
    private Float getPer(String reportId) {
    	SensitiveWarningReport sensitiveWarningReport=sensitiveConmentService.selectByPrimaryKey(reportId);
    	Date crTime=sensitiveWarningReport.getCreateTime();
    	DateFormat d2 = DateFormat.getDateInstance(); 
        String str2 = d2.format(crTime); 
        
        //当天评论总数量
        int tatol=0;
        List<SensitiveWarningReportDto> warnReportList=sensitiveConmentService.warnaReportList (reportId);
        for (SensitiveWarningReportDto configDto : warnReportList) {
        	Date conTime=configDto.getCreateTime();
	        String str3 = d2.format(conTime); 
			if(str2.equals(str3)){
				//计算过滤食堂和时间的评论百分比
				Integer i=configDto.getCount();
				int pin=i.intValue();
				tatol=pin+tatol;
			}
        }
        Float tatols=Float.valueOf(tatol);
        //当天该条评论的数量
        Float con=Float.valueOf(sensitiveWarningReport.getCount());
        Float per=con/tatols;
    	return per;
    }

    public static void main(String[] args) {
    	List<JsonDto> list = new ArrayList<>();
    	JsonDto jsonDto=new JsonDto();
    	jsonDto.setName("a");
    	jsonDto.setValue("b");
    	list.add(jsonDto);
    	String json = JSON.toJSONString(list);
    	JSON parse = (JSON) JSONObject.parse(json);
    	System.out.println(parse.toJSONString());	
    }

			/**
			 * getLevelAddress：通过区域ID获取地区分级
			 * @param addressId 区域ID
			 * @return
			 * @exception	
			 * @author yuanbin
			 * @date 2015年8月27日 下午14:20:12
			 */
			public String getLevelAddress(String addressId) {
				Address address = iAddressService.findAddressById(addressId);
				if (address == null) {
					return null;
				}
				if (address.getAddressCode() == null) {
					return null;
				}
				char[] addressCodeArr = address.getAddressCode().toCharArray();
				List<String> list = new ArrayList<>();
				//拆分区域编码
				String s = "";
				for (int i = 0; i < addressCodeArr.length; i++) {
					s += addressCodeArr[i];
					if (i != 0 && i % 2 == 1) {
						list.add(s);
					}
				}

				//将区域分级拼接成字符串
				String levelAddress = "";
				for (String addressCode : list) {
					//通过区域辅助码获取区域信息
					Address add = iAddressService.findAddressByAddressCode(addressCode);
					if (add != null && add.getAddressCode() != null) {
						levelAddress += add.getAddressName() + "-";
					}
				}
				if (levelAddress.length() > 1) {
					return levelAddress.substring(0, levelAddress.length() - 1);
				}
				return levelAddress;
			}

    /**
     * getAddressUser：通过用户ID获取用户区域id
     * @param userId 用户ID
     * @return
     * @exception   
     * @author  pengcheng.yang
     * @date 2015年8月14日 上午10:49:32
     */
    @ResponseBody
    @RequestMapping(value = "/sensitiveReport.do",method = {RequestMethod.POST,RequestMethod.GET})
    public Response<List<ResultsData>> report(String userId){
        return this.report.querySensitiveWarning(userId);
    }

}

