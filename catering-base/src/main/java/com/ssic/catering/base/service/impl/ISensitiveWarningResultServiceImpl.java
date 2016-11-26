/**
 * 
 */
package com.ssic.catering.base.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssic.catering.base.dto.CafetoriumDto;
import com.ssic.catering.base.dto.CommentDto;
import com.ssic.catering.base.dto.CountSensitiveDto;
import com.ssic.catering.base.dto.PushAddressUserDto;
import com.ssic.catering.base.dto.QjyCateringUserDto;
import com.ssic.catering.base.dto.SensitiveValveCountDto;
import com.ssic.catering.base.dto.SensitiveWarningReportDto;
import com.ssic.catering.base.eventBus.EventRegisterC;
import com.ssic.catering.base.service.ICafetoriumService;
import com.ssic.catering.base.service.IConfigSearchService;
import com.ssic.catering.base.service.IQueryLevelLeaderService;
import com.ssic.catering.base.service.ISensitiveWarningReportService;
import com.ssic.catering.base.service.ISensitiveWarningResultService;

/**		
 * <p>Title: ISensitiveWarningResultServiceImpl </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author pengcheng.yang	
 * @date 2015年8月11日 下午4:38:41	
 * @version 1.0
 * <p>修改人：pengcheng.yang</p>
 * <p>修改时间：2015年8月11日 下午4:38:41</p>
 * <p>修改备注：</p>
 */
@Service("sensitiveWarningResultServiceImpl")
public class ISensitiveWarningResultServiceImpl implements ISensitiveWarningResultService
{
    @Autowired
    private IConfigSearchService configSearchService;
    
    @Autowired
    private ISensitiveWarningReportService sensitiveReport;
    
    @Autowired
    private IQueryLevelLeaderService queryLevelLeaderService;
    
    @Autowired
    private ICafetoriumService cafetoriumService;
    
    @Autowired
    private EventRegisterC eventRegister;
    
    /** 
     * (non-Javadoc)   
     * @see com.ssic.catering.base.service.ISensitiveWarningResultService#findSensitiveCount()
     * 统计每个食堂当天的评论人数   
     */
    @Override
    public void findSensitiveCount()
    {
        List<CommentDto> commentList = this.configSearchService.findCountComments();
        if(commentList.size()>0){
            //计算敏感词预警
            countSensitiveWarningReport(commentList);
        }
        
    }
    
    /**
     * 
     * countSensitiveWarningReport：计算敏感词预警
     * @exception	
     * @author pengcheng.yang
     * @date 2015年8月11日 下午4:53:13
     */
    public void countSensitiveWarningReport(List<CommentDto> commentList){
        
        //敏感词预警词
        float sensitiveWarningM = 0;
        CafetoriumDto cafetoriumDto = null;
        for (int i = 0; i < commentList.size(); i++)
        {
            List<SensitiveWarningReportDto> reportlLst = new ArrayList<SensitiveWarningReportDto>();
            //统计当天有没有预警过
            int flag = this.sensitiveReport.totalWarningReportMessages(commentList.get(i).getCafetoriumId());
            //只有当天没有预警信息是才进行敏感词预警计算和推送
            if(flag == 0){
                //取出食堂名称
                cafetoriumDto = this.cafetoriumService.findById(commentList.get(i).getCafetoriumId());
                //统计每个食堂的预警敏感词出现的次数  
                List<CountSensitiveDto> sensitiveList = this.sensitiveReport.queryCountSensitive(commentList.get(i).getCafetoriumId());
                for (int j = 0; j < sensitiveList.size(); j++)
                {
                    SensitiveWarningReportDto swr = null;
                    //读取每个食堂的敏感词预警的标准
                    List<SensitiveValveCountDto> countList = this.sensitiveReport.queryWarningNorm(sensitiveList.get(j).getSensitiveId());
                    //当评论条数达到阀值并且当天没有推送过敏感词预警才进行敏感词百分比计算
                    if((commentList.get(i).getCountComments().intValue() >= countList.get(0).getValveCount().intValue()) && flag == 0){
                        swr = new SensitiveWarningReportDto();
                        sensitiveWarningM = (float)sensitiveList.get(j).getSensitiveTotals()/(float)commentList.get(i).getCountComments();
                        swr.setCafetoriumId(commentList.get(i).getCafetoriumId());
                        BigDecimal  big = new BigDecimal(sensitiveWarningM);
                        float sw = big.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
                        swr.setWarningproportion(sw);
                        swr.setSensitiveName(sensitiveList.get(j).getSensitiveName());
                        swr.setSensitiveId(sensitiveList.get(j).getSensitiveId());
                        swr.setCount(sensitiveList.get(j).getSensitiveTotals());
                        List<PushAddressUserDto> cityList = this.queryLevelLeaderService.queryCityManager(commentList.get(i).getCafetoriumId());
                        swr.setAddressId(cityList.get(0).getAddressId());
                        swr.setAddressName(cityList.get(0).getAddressName());
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        String date = format.format(new Date());
                        swr.setMessage(cafetoriumDto.getCafeName()+"供餐点"+date+"用户体验"+sensitiveList.get(j).getSensitiveName()+"关键词出现过多预警");
                    }
                    if(swr != null){
                        reportlLst.add(swr);
                    }
                }
                //保存敏感词预警消息
                this.sensitiveReport.insertSensitiveWarning(reportlLst);
            }
            
            if(flag==0){
                QjyCateringUserDto qjyCateringUserDto = new QjyCateringUserDto();
                List<String> toIdList = new ArrayList<String>();
                //城市经理
                if(sensitiveWarningM >= 0.03 && sensitiveWarningM < 0.05){
                    List<PushAddressUserDto> cityList = this.queryLevelLeaderService.queryCityManager(commentList.get(i).getCafetoriumId());
                    
                    toIdList.add(cityList.get(0).getQjyAccount());
                    
                    qjyCateringUserDto.setToIdList(toIdList);
                    String text= new String(cafetoriumDto.getCafeName()+"的用户体验有新情况");
                    qjyCateringUserDto.setText(text);
                    eventRegister.getQjyEvent().post(qjyCateringUserDto);
//                    System.out.println("要推送城市经理的千佳云帐号是："+cityList.get(i).getQjyAccount());
                }
                //省市经理
                if(sensitiveWarningM >= 0.05 && sensitiveWarningM < 0.08){
                    List<PushAddressUserDto> cityList = this.queryLevelLeaderService.queryCityManager(commentList.get(i).getCafetoriumId());
                    List<PushAddressUserDto> provincialList = this.queryLevelLeaderService.queryProvincialManager(commentList.get(i).getCafetoriumId());
                    
                    toIdList.add(cityList.get(0).getQjyAccount());
                    toIdList.add(provincialList.get(0).getQjyAccount());
                    
                    qjyCateringUserDto.setToIdList(toIdList);
                    String text= new String(cafetoriumDto.getCafeName()+"的用户体验有新情况");
                    qjyCateringUserDto.setText(text);
                    eventRegister.getQjyEvent().post(qjyCateringUserDto);
//                    System.out.println("要推送省市经理的千佳云帐号是："+provincialList.get(i).getQjyAccount());
                    
                }
                //大区经理
                if(sensitiveWarningM >= 0.08 && sensitiveWarningM < 1){
                    List<PushAddressUserDto> cityList = this.queryLevelLeaderService.queryCityManager(commentList.get(i).getCafetoriumId());
                    List<PushAddressUserDto> provincialList = this.queryLevelLeaderService.queryProvincialManager(commentList.get(i).getCafetoriumId());
                    List<PushAddressUserDto> regionalList = this.queryLevelLeaderService.queryRegionalManager(commentList.get(i).getCafetoriumId());
                    
                    toIdList.add(cityList.get(0).getQjyAccount());
                    toIdList.add(provincialList.get(0).getQjyAccount());
                    toIdList.add(regionalList.get(0).getQjyAccount());
                    
                    qjyCateringUserDto.setToIdList(toIdList);
                    String text= new String(cafetoriumDto.getCafeName()+"的用户体验有新情况");
                    qjyCateringUserDto.setText(text);
                    eventRegister.getQjyEvent().post(qjyCateringUserDto);
//                    System.out.println("要推送大区经理的千佳云帐号是："+regionalList.get(i).getQjyAccount());
                }
                //总公司负责人
                if(sensitiveWarningM >= 1 ){
                    List<PushAddressUserDto> cityList = this.queryLevelLeaderService.queryCityManager(commentList.get(i).getCafetoriumId());
                    List<PushAddressUserDto> provincialList = this.queryLevelLeaderService.queryProvincialManager(commentList.get(i).getCafetoriumId());
                    List<PushAddressUserDto> regionalList = this.queryLevelLeaderService.queryRegionalManager(commentList.get(i).getCafetoriumId());
                    List<PushAddressUserDto> companyList = this.queryLevelLeaderService.queryCompanyLeader(commentList.get(i).getCafetoriumId());
                    
                    toIdList.add(cityList.get(0).getQjyAccount());
                    toIdList.add(provincialList.get(0).getQjyAccount());
                    toIdList.add(regionalList.get(0).getQjyAccount());
                    toIdList.add(companyList.get(0).getQjyAccount());
                    
                    qjyCateringUserDto.setToIdList(toIdList);
                    String text= new String(cafetoriumDto.getCafeName()+"的用户体验有新情况");
                    qjyCateringUserDto.setText(text);
                    eventRegister.getQjyEvent().post(qjyCateringUserDto);
//                    System.out.println("要推送总公司负责人的千佳云帐号是："+companyList.get(i).getQjyAccount());
                }
            }
        }
    }
}

