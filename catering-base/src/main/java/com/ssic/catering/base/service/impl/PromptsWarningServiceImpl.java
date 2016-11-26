package com.ssic.catering.base.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssic.catering.base.dto.AvgScoreWarningDto;
import com.ssic.catering.base.dto.CafetoriumDto;
import com.ssic.catering.base.dto.CommentDto;
import com.ssic.catering.base.dto.ConfigScoreDto;
import com.ssic.catering.base.dto.ConfigScoreValveConfDto;
import com.ssic.catering.base.dto.PushAddressUserDto;
import com.ssic.catering.base.dto.QjyCateringUserDto;
import com.ssic.catering.base.eventBus.EventRegisterC;
import com.ssic.catering.base.mapper.ConfigScoreExMapper;
import com.ssic.catering.base.service.IAvgScoreWarningService;
import com.ssic.catering.base.service.ICafetoriumService;
import com.ssic.catering.base.service.IConfigScoreValveConfService;
import com.ssic.catering.base.service.IConfigSearchService;
import com.ssic.catering.base.service.IPromptsWarningService;
import com.ssic.catering.base.service.IQueryLevelLeaderService;
/**
 * 
 * <p>
 * Title: PromptsWarningServiceImpl
 * </p>
 * <p>
 * Description: 类描述
 * </p>
 * <p>
 * Copyright (c) 2015
 * </p>
 * <p>
 * Company: 上海天坊信息科技有限公司
 * </p>
 * 
 * @author Administrator
 * @date 2015年8月8日 上午11:12:13
 * @version 1.0
 *          <p>
 *          修改人：pengcheng.yang
 *          </p>
 *          <p>
 *          修改时间：2015年8月8日 上午11:12:13
 *          </p>
 *          <p>
 *          修改备注：
 *          </p>
 */
@Service("promptsWarningServiceImpl")
public class PromptsWarningServiceImpl implements IPromptsWarningService
{

    @Autowired
    private IConfigSearchService configSearchService;

    @Autowired
    private ConfigScoreExMapper configScoreExMapper;

    @Autowired
    private IConfigScoreValveConfService configScoreValveConfService;

    @Autowired
    private ICafetoriumService cafeService;
    
    @Autowired
    private IAvgScoreWarningService avgScoreWarningService;
    
    @Autowired
    private IQueryLevelLeaderService queryLevelLeaderService;
    
    @Autowired
    private ICafetoriumService cafetoriumService;
    
    @Autowired
    private EventRegisterC eventRegister;

    /**
     * 
     * (non-Javadoc)
     * 
     * @see com.ssic.catering.base.service.IPromptsWarningService#countAvgScore()
     */
    @Override
    public void findCountComment()
    {
        List<CommentDto> list = this.configSearchService.findCountComments();
        for (int j = 0; j < list.size(); j++)
        {
            System.out.println("评论餐厅=" + list.get(j).getCafetoriumId() + "======评论总数="
                + list.get(j).getCountComments());
        }
        if(list.size()>0){
            findCountAvgScore(list);
        }
    }

    /**
     * 
     * findCountAvgScore：实时查询每个餐厅当天各项评分总分
     * 
     * @param cafetoriumId
     * @exception
     * @author pengcheng.yang
     * @date 2015年8月8日 下午1:40:34
     */
    public void findCountAvgScore(List<CommentDto> commentList){
        
        //每个餐厅的总平均分
        float totalAvgScore = 0;
        
        for (int i = 0; i < commentList.size(); i++)
        {   
            
            //从缓存中读取每个食堂的平均分预警的标准
            List<ConfigScoreValveConfDto> conf = this.configScoreValveConfService.queryConfigId(commentList.get(i).getCafetoriumId());
            //取出当前餐厅设定的平均分阀值
            int commentNum = conf.get(0).getValveCount();
            
           //查询餐厅今天有没有预警消息，如果有则不进行计算推送,没有则进行计算推送。
           int totalNum = this.cafeService.totalWarningMessages(commentList.get(i).getCafetoriumId());
           
           if(totalNum == 0 && (commentList.get(i).getCountComments() >= commentNum)){
                   //每项平均分之和
                   float oneAvgScore = 0;
                   List<ConfigScoreDto> configScoreList = this.configScoreExMapper.CountScore(commentList.get(i).getCafetoriumId());
                   for (int j = 0; j < configScoreList.size(); j++)
                    {
                        System.out.println("评分项目="+configScoreList.get(j).getConfigId()+"评论总分="+configScoreList.get(j).getCountScore()+"========评论餐厅="+configScoreList.get(j).getCafetoriumId());
                        float avgScore = (float)configScoreList.get(j).getCountScore()/(float)commentList.get(i).getCountComments();
                        System.out.println("评论项Id"+configScoreList.get(j).getConfigId()+"平均分是："+avgScore);
                        BigDecimal  big = new BigDecimal(avgScore);
                        oneAvgScore += big.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
                    }
                   System.out.println("============总平均分是:"+oneAvgScore);
                   //计算每个餐厅的总平均分 //每天计算时每个食堂的评论总数:configScoreList.size()
                   System.out.println("评分项数："+configScoreList.size());
                   totalAvgScore = oneAvgScore/configScoreList.size();
                   System.out.println("============餐厅平均分是:"+totalAvgScore);
                   
                   //保存每个餐厅的平均分预警消息保存到t_ctr_avg_score_warning表
                   AvgScoreWarningDto avgs = new AvgScoreWarningDto();
                   BigDecimal big = new BigDecimal(totalAvgScore); 
                   float bigAvg = big.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue(); 
                   System.out.println("保留一位小数的平均分========="+bigAvg);
                   avgs.setAvgScore(bigAvg);
                   avgs.setCafetoriumId(commentList.get(i).getCafetoriumId());
                   //推送消息告诉对应级别的负责人平均分情况
                   
                   //取出食堂名称
                   CafetoriumDto cafetoriumDto = this.cafetoriumService.findById(commentList.get(i).getCafetoriumId());
                   
                   //城市经理
                   if(bigAvg <= 4 && bigAvg > 3.5){
                       List<PushAddressUserDto> cityList = this.queryLevelLeaderService.queryCityManager(commentList.get(i).getCafetoriumId());
                       avgs.setMessage(cafetoriumDto.getCafeName()+"综合评分情况是:"+bigAvg);
                       avgs.setUserId(cityList.get(0).getUserId());
                       avgs.setParentId(cityList.get(0).getParentId());
                       avgs.setAddressName(cityList.get(0).getAddressName());
                       avgs.setQjyAccount(cityList.get(0).getQjyAccount());
                       this.avgScoreWarningService.inserAvgScoreWarnInfo(avgs);
                       //调用千佳云接口推送消息内容
//                       System.out.println("要推送城市经理的千佳云帐号是："+cityList.get(i).getQjyAccount());
                       
                       List<String> toIdList = new ArrayList<String>();
                       toIdList.add(cityList.get(0).getQjyAccount());
                       QjyCateringUserDto qjyCateringUserDto = new QjyCateringUserDto();
                       qjyCateringUserDto.setToIdList(toIdList);
                       String text= new String(cafetoriumDto.getCafeName()+"的用户体验有新情况");
                       qjyCateringUserDto.setText(text);
                       eventRegister.getQjyEvent().post(qjyCateringUserDto);
                   }
                   //省市经理
                   if(bigAvg <= 3.5 && bigAvg > 3){
                       List<PushAddressUserDto> cityList = this.queryLevelLeaderService.queryCityManager(commentList.get(i).getCafetoriumId());
                       List<PushAddressUserDto> provincialList = this.queryLevelLeaderService.queryProvincialManager(commentList.get(i).getCafetoriumId());
                       avgs.setMessage(cafetoriumDto.getCafeName()+"综合评分情况是:"+bigAvg);
                       avgs.setUserId(provincialList.get(0).getUserId());
                       avgs.setParentId(provincialList.get(0).getParentId());
                       avgs.setAddressName(provincialList.get(0).getAddressName());
                       avgs.setQjyAccount(provincialList.get(0).getQjyAccount());
                       this.avgScoreWarningService.inserAvgScoreWarnInfo(avgs);
                       
                       //调用千佳云接口推送消息内容
//                       System.out.println("要推送省市经理的千佳云帐号是："+provincialList.get(i).getQjyAccount());
                       
                       List<String> toIdList = new ArrayList<String>();
                       toIdList.add(cityList.get(0).getQjyAccount());
                       toIdList.add(provincialList.get(0).getQjyAccount());
                       QjyCateringUserDto qjyCateringUserDto = new QjyCateringUserDto();
                       qjyCateringUserDto.setToIdList(toIdList);
                       String text= new String(cafetoriumDto.getCafeName()+"的用户体验有新情况");
                       qjyCateringUserDto.setText(text);
                       eventRegister.getQjyEvent().post(qjyCateringUserDto);
                   }
                   //大区经理
                   if(bigAvg <= 3 && bigAvg > 2.5){
                       List<PushAddressUserDto> cityList = this.queryLevelLeaderService.queryCityManager(commentList.get(i).getCafetoriumId());
                       List<PushAddressUserDto> provincialList = this.queryLevelLeaderService.queryProvincialManager(commentList.get(i).getCafetoriumId());
                       List<PushAddressUserDto> regionalList = this.queryLevelLeaderService.queryRegionalManager(commentList.get(i).getCafetoriumId());
                       avgs.setMessage(cafetoriumDto.getCafeName()+"综合评分情况是:"+bigAvg);
                       avgs.setUserId(regionalList.get(0).getUserId());
                       avgs.setParentId(regionalList.get(0).getParentId());
                       avgs.setAddressName(regionalList.get(0).getAddressName());
                       avgs.setQjyAccount(regionalList.get(0).getQjyAccount());
                       this.avgScoreWarningService.inserAvgScoreWarnInfo(avgs);
                       
                       //调用千佳云接口推送消息内容
//                       System.out.println("要推送大区经理的千佳云帐号是："+regionalList.get(i).getQjyAccount());
                       
                       List<String> toIdList = new ArrayList<String>();
                       toIdList.add(cityList.get(0).getQjyAccount());
                       toIdList.add(provincialList.get(0).getQjyAccount());
                       toIdList.add(regionalList.get(0).getQjyAccount());
                       QjyCateringUserDto qjyCateringUserDto = new QjyCateringUserDto();
                       qjyCateringUserDto.setToIdList(toIdList);
                       String text= new String(cafetoriumDto.getCafeName()+"的用户体验有新情况");
                       qjyCateringUserDto.setText(text);
                       eventRegister.getQjyEvent().post(qjyCateringUserDto);
                   }
                   //总公司负责人
                   if(bigAvg <= 2.5){
                       List<PushAddressUserDto> cityList = this.queryLevelLeaderService.queryCityManager(commentList.get(i).getCafetoriumId());
                       List<PushAddressUserDto> provincialList = this.queryLevelLeaderService.queryProvincialManager(commentList.get(i).getCafetoriumId());
                       List<PushAddressUserDto> regionalList = this.queryLevelLeaderService.queryRegionalManager(commentList.get(i).getCafetoriumId());
                       List<PushAddressUserDto> companyList = this.queryLevelLeaderService.queryCompanyLeader(commentList.get(i).getCafetoriumId());
                       avgs.setMessage(cafetoriumDto.getCafeName()+"综合评分情况是:"+bigAvg);
                       avgs.setUserId(companyList.get(0).getUserId());
                       avgs.setParentId(companyList.get(0).getParentId());
                       avgs.setAddressName(companyList.get(0).getAddressName());
                       avgs.setQjyAccount(companyList.get(0).getQjyAccount());
                       this.avgScoreWarningService.inserAvgScoreWarnInfo(avgs);
                       
                       //调用千佳云接口推送消息内容
//                       System.out.println("要推送总公司负责人的千佳云帐号是："+companyList.get(i).getQjyAccount());
                       
                       List<String> toIdList = new ArrayList<String>();
                       toIdList.add(cityList.get(0).getQjyAccount());
                       toIdList.add(provincialList.get(0).getQjyAccount());
                       toIdList.add(regionalList.get(0).getQjyAccount());
                       toIdList.add(companyList.get(0).getQjyAccount());
                       QjyCateringUserDto qjyCateringUserDto = new QjyCateringUserDto();
                       qjyCateringUserDto.setToIdList(toIdList);
                       String text= new String(cafetoriumDto.getCafeName()+"的用户体验有新情况");
                       qjyCateringUserDto.setText(text);
                       eventRegister.getQjyEvent().post(qjyCateringUserDto);
                   }
             }
       }
    }
}
