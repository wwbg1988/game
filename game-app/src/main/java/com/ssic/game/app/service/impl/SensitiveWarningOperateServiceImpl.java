/**
 * 
 */
package com.ssic.game.app.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssic.catering.base.dto.AddressDto;
import com.ssic.catering.base.dto.AddressUserDto;
import com.ssic.catering.base.dto.CafetoriumDto;
import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.dto.SensitiveWarningReportDto;
import com.ssic.catering.base.service.IAddressService;
import com.ssic.catering.base.service.IAddressUserService;
import com.ssic.catering.base.service.ICafetoriumService;
import com.ssic.catering.base.service.ISensitiveWarningReportService;
import com.ssic.catering.common.util.AppResponse;
import com.ssic.game.app.service.ISensitiveWarningOperateService;
import com.ssic.game.common.constant.WorkSearchConstants;
import com.ssic.game.common.dto.WorkSearchDto;
import com.ssic.game.common.service.WorkRoleService;

/**		
 * <p>Title: SensitiveWarningOperateServiceImpl </p>
 * <p>Description:食堂敏感词预警信息</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年8月11日 下午8:38:06	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年8月11日 下午8:38:06</p>
 * <p>修改备注：</p>
 */
@Service
public class SensitiveWarningOperateServiceImpl implements ISensitiveWarningOperateService
{
    @Autowired
    private ISensitiveWarningReportService sensitiveReportService;
    @Autowired
    private IAddressUserService addressUserService;
    @Autowired
    private ICafetoriumService cafetoriumService;
    @Autowired
    private IAddressService addressService;
    @Autowired
    private WorkRoleService workRoleService;

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.app.service.ISensitiveWarningOperateService#sensitiveWarningList(java.lang.String, java.lang.String, int, int)   
    */
    @Override
    public List<WorkSearchDto> sensitiveWarningList(String userId, String searchDate, int beginRow, int endRow)
    {
        List<WorkSearchDto> workDtoList = new ArrayList<WorkSearchDto>();
        List<CafetoriumDto> cafeList = new ArrayList<CafetoriumDto>();
        PageHelperDto ph = new PageHelperDto();
        ph.setBeginRow(beginRow);
        ph.setRows(endRow);
        List<String> roleIdList = workRoleService.findByUserId(userId);// 根据用户查角色
        if (!CollectionUtils.isEmpty(roleIdList))
        {
            AddressDto dto = this.addressService.queryAddressId(userId);
            Map<String, String> maps = new HashMap<String, String>();
            List<AddressDto> childrenList = new ArrayList<AddressDto>();
            String cafeCode = null;
            if (dto != null)
            {
                childrenList = addressService.findChildListByParentId(dto.getId());
                if (!CollectionUtils.isEmpty(childrenList))
                {
                    for (AddressDto addressDto : childrenList)
                    {
                        List<AddressDto> dtoList = addressService.queryCityId(addressDto.getAddressCode());
                        List<AddressUserDto> addressUserList =
                            addressUserService.finAddressListByUserId(userId);

                        //遍历区域用户
                        if (!CollectionUtils.isEmpty(addressUserList))
                        {
                            for (AddressUserDto addUserDto : addressUserList)
                            {
                                if (addUserDto.getAddressType() == 4
                                    && !StringUtils.isEmpty(addUserDto.getCafeCode()))
                                {//如果区域用户是食堂管理员;
                                    cafeCode = addUserDto.getCafeCode();
                                }

                            }
                        }
                        if (!CollectionUtils.isEmpty(dtoList))
                        {
                            for (AddressDto dtos : dtoList)
                            {
                                List<CafetoriumDto> cafeList2 =
                                    cafetoriumService.findCafetoriumListByAddressId(dtos.getId());
                                if (!StringUtils.isEmpty(cafeCode) && !CollectionUtils.isEmpty(cafeList2))
                                {
                                    for (CafetoriumDto caDto : cafeList2)
                                    {
                                        if (caDto.getCafeCode().equals(cafeCode))
                                        {
                                            cafeList.add(caDto);
                                        }
                                    }
                                }
                                else
                                {
                                    cafeList.addAll(cafeList2);
                                }

                            }
                        }
                    }
                }
                else
                {//没有子区域，当前区域为市
                    List<AddressUserDto> addressUserList = addressUserService.finAddressListByUserId(userId);
                    //遍历区域用户
                    if (!CollectionUtils.isEmpty(addressUserList))
                    {
                        for (AddressUserDto addUserDto : addressUserList)
                        {
                            if (addUserDto.getAddressType() == 4
                                && !StringUtils.isEmpty(addUserDto.getCafeCode()))
                            {//如果区域用户是食堂管理员;
                                cafeCode = addUserDto.getCafeCode();
                            }

                        }
                    }
                    List<CafetoriumDto> cafeList2 =
                        cafetoriumService.findCafetoriumListByAddressId(dto.getId());

                    if (!StringUtils.isEmpty(cafeCode) && !CollectionUtils.isEmpty(cafeList2))
                    {
                        for (CafetoriumDto caDto : cafeList2)
                        {
                            if (caDto.getCafeCode().equals(cafeCode))
                            {
                                cafeList.add(caDto);
                            }
                        }
                    }
                    else
                    {
                        cafeList.addAll(cafeList2);
                    }
                }
            }

            for (CafetoriumDto caDto : cafeList)
            {
                maps.put(caDto.getId(), caDto.getCafeName());
            }

            for (String cafeId : maps.keySet())
            {
                List<SensitiveWarningReportDto> reportList =
                    sensitiveReportService.findByCafetoriumId(cafeId, ph, searchDate);
                if (!CollectionUtils.isEmpty(reportList))
                {
                    for (SensitiveWarningReportDto dto2 : reportList)
                    {
                        WorkSearchDto wDto = new WorkSearchDto();
                        wDto.setBeginRow(beginRow);
                        wDto.setEndRow(endRow);
                        wDto.setSearchDate(searchDate);
                        wDto.setType(WorkSearchConstants.JINGGAO);
                        wDto.setMessage(dto2.getMessage());
                        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                        String cTime = sdf2.format(dto2.getCreateTime());
                        String monthTime = cTime.substring(5, 7);
                        String dayTime = cTime.substring(8, 10);
                        String meetMD = monthTime + "月" + dayTime + "日";
                        wDto.setMeetMD(meetMD);
                        wDto.setCreateTime(dto2.getCreateTime());
                        wDto.setUserId(userId);
                        //另一个接口会传来参数;
                        wDto.setUrl(AppResponse.SENSITIVE_WARNING_URL + "?reportId=" + dto2.getId()
                            + "&sensitiveId=" + dto2.getSensitiveId());
                        //新增预警餐厅名称
                        wDto.setWorkName(maps.get(dto2.getCafetoriumId()));
                        workDtoList.add(wDto);
                    }
                }
            }
            
            //
            if(!CollectionUtils.isEmpty(workDtoList))
            {
                Collections.sort(workDtoList,new Comparator<WorkSearchDto>()
                {
                    @Override
                    public int compare(WorkSearchDto o1, WorkSearchDto o2)
                    {
                        if(o1.getCreateTime().getTime() - o2.getCreateTime().getTime()>0)
                        {
                            return -1;
                        }
                        else if(o1.getCreateTime().getTime() - o2.getCreateTime().getTime()<0)
                        {
                            return 1;
                        }
                        else
                        {
                            return 0;
                        }
                    }
                });
            }
                
        }
        return workDtoList;
    }
}
