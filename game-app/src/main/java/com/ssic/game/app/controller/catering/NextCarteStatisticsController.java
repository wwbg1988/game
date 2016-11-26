/**
 * 
 */
package com.ssic.game.app.controller.catering;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ssic.catering.base.dto.AddressUserDto;
import com.ssic.catering.base.dto.CafetoriumDto;
import com.ssic.catering.base.dto.CarteTypeDto;
import com.ssic.catering.base.dto.ConfigDto;
import com.ssic.catering.base.dto.NextWeekCarteDtos;
import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.service.IAddressUserService;
import com.ssic.catering.base.service.ICafetoriumService;
import com.ssic.catering.base.service.ICarteTypeService;
import com.ssic.catering.base.validator.NextCarteStatisticsValidator;
import com.ssic.catering.common.util.AppResponse;
import com.ssic.game.app.controller.dto.CafetoriumData;
import com.ssic.game.app.controller.dto.DishDetailDto;
import com.ssic.game.app.controller.dto.DishPercentDto;
import com.ssic.game.app.controller.dto.DishTypeDto;
import com.ssic.game.app.controller.dto.ProjectDto;
import com.ssic.game.app.dao.JsonDto;
import com.ssic.game.app.listener.InitApplicationContext;
import com.ssic.game.app.service.IUserOperateSerivce;
import com.ssic.game.app.service.NextMenuOperateSerivce;
import com.ssic.util.BeanUtils;
import com.ssic.util.model.RequestResult;
import com.ssic.util.model.Response;

/**		
 * <p>Title: NextCarteStatisticsController </p>
 * <p>Description: 下周菜单百分比统计</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年8月8日 上午10:32:40	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年8月8日 上午10:32:40</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping("/http/api/carte")
public class NextCarteStatisticsController
{
    @Autowired
    private NextMenuOperateSerivce nextMenuOperateSerivce;
    @Autowired
    private IAddressUserService addressUserService;
    @Autowired
    private ICafetoriumService cafetoriumService;
    @Autowired
    private ICarteTypeService carteTypeService;
    @Autowired
    private NextCarteStatisticsValidator nextCarteStatisticsValidator;
    protected static final Log logger = LogFactory.getLog(NextCarteStatisticsController.class);

    @Autowired
    private IUserOperateSerivce userOperateSerivce;

    /**     
     * cafetoriumlist：查询当前用户下的所有食堂
     * @param userId 用户id
     * @param page    页数
     * @param rows    页大小
     * @return
     * @exception   
     * @author 刘博
     * @date 2015年6月25日 下午3:47:41    
     */
    @RequestMapping(value = "/cafetoriumlist.do", method = {RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Response<List<CafetoriumData>> cafetoriumlist(String userId, int page, int rows)
    {
        Response<List<CafetoriumData>> result = new Response<List<CafetoriumData>>();
        List<CafetoriumData> resultData = new ArrayList<CafetoriumData>();
        RequestResult validateResult = nextCarteStatisticsValidator.validate(userId);
        if (!validateResult.success)
        {
            //验证失败返回错误信息
            result.setMessage(validateResult.message);
            result.setStatus(AppResponse.RETURN_FAILE);
            result.setData(resultData);
            return result;
        }
        List<ProjectDto> projDtoList = userOperateSerivce.queryProjectInfo(userId);
        if (CollectionUtils.isEmpty(projDtoList))
        {
            result.setMessage("用户所属的项目不存在");
            result.setStatus(AppResponse.RETURN_FAILE);
            result.setData(resultData);
            logger.info("用户所属的项目不存在");
            return result;
        }
        if (projDtoList.size() > 1)
        {
            result.setMessage("用户属于多个项目!");
            result.setStatus(AppResponse.RETURN_FAILE);
            result.setData(resultData);
            logger.info("用户属于多个项目");
            return result;
        }
        //所属项目id
        String projId = projDtoList.get(0).getId();
        //分页对象;
        PageHelperDto ph = new PageHelperDto();
        ph.setPage(page);
        ph.setRows(rows);
        ph.setBeginRow((ph.getPage() - 1) * ph.getRows());
        //根据用户id获取所有用户区域关系集合;
        List<AddressUserDto> addressUserList = addressUserService.finAddressListByUserId(userId);
        if (!CollectionUtils.isEmpty(addressUserList))
        {
            for (AddressUserDto addressUser : addressUserList)
            {
                List<CafetoriumData> dataList = null;
                //如果负责人是食堂管理员，则只能查看当前食堂的信息
                if (addressUser.getAddressType() == 4)
                {
                    dataList =
                        findCafetoriumListByAddressId(addressUser.getAddressId(),
                            addressUser.getCafeCode(),
                            projId,
                            ph);
                }
                //如果负责人是城市经理，则查看该区域下的所有食堂信息
                else if (addressUser.getAddressType() == 3)
                {
                    //根据区域id获取该区域下的所有食堂,然后加入到返回食堂列表结果集中;
                    dataList = findCafetoriumListByAddressId(addressUser.getAddressId(), null, projId, ph);
                }
                if (!CollectionUtils.isEmpty(dataList) && dataList != null)
                {
                    resultData.addAll(dataList);
                }
            }
        }
        result.setMessage(String.valueOf(resultData.size()));
        result.setStatus(AppResponse.RETURN_SUCCESS);
        result.setData(resultData);
        return result;
    }

    /**     
     * cafetoriumlist：根据区域id获取该区域下的所有食堂
     * @param addressId 区域id
     * @param PageHelper 分页对象
     * @return 
     */
    private List<CafetoriumData> findCafetoriumListByAddressId(String addressId, String cafeCode,
        String projId, PageHelperDto ph)
    {
        List<CafetoriumData> cafetoriumDataList = new ArrayList<CafetoriumData>();
        List<CafetoriumData> resultDataList = new ArrayList<CafetoriumData>();
        SimpleDateFormat format = new SimpleDateFormat("MM月dd日");
        //查找某个区域下的所有食堂(某省，某市 等等)
        CafetoriumDto param = new CafetoriumDto();
        param.setAddressId(addressId);
        if (!StringUtils.isEmpty(cafeCode))
        {
            param.setCafeCode(cafeCode);
        }
        List<CafetoriumDto> dtoList = cafetoriumService.findALL(param, ph);

        //放入食堂下的所有菜单类型
        if (!CollectionUtils.isEmpty(dtoList))
        {
            List<NextWeekCarteDtos> nextWeekList = new ArrayList<NextWeekCarteDtos>();
            for (CafetoriumDto dto : dtoList)
            {
                if (dto.getProjId().equals(projId))
                {
                    CafetoriumData cafetoriumData = new CafetoriumData();
                    List<CarteTypeDto> typeDtoList = carteTypeService.finByCafetoriumId(dto.getId());
                    cafetoriumData.setCarteTypeList(typeDtoList);
                    cafetoriumData.setId(dto.getId());
                    cafetoriumData.setCafeName(dto.getCafeName());
                    cafetoriumDataList.add(cafetoriumData);
                    List<NextWeekCarteDtos> nextWeekCarteDtoList =
                        nextMenuOperateSerivce.findDistinctCarteWeekByCafeId(dto.getId());
                    if (!CollectionUtils.isEmpty(nextWeekCarteDtoList))
                    {
                        for (NextWeekCarteDtos nextWeekDto : nextWeekCarteDtoList)
                        {
                            nextWeekDto.setCafetoriumId(dto.getId());
                            nextWeekList.add(nextWeekDto);
                        }
                    }
                }
            }
            //遍历某一个食堂下的下周菜单,放入返回对象中;
            if (!CollectionUtils.isEmpty(nextWeekList) && !CollectionUtils.isEmpty(cafetoriumDataList))
            {
                for (NextWeekCarteDtos nextWeekDto : nextWeekList)
                {
                    for (CafetoriumData cafetoriumData : cafetoriumDataList)
                    {
                        if (cafetoriumData.getId().equals(nextWeekDto.getCafetoriumId()))
                        {
                            CafetoriumData cafeData = new CafetoriumData();
                            cafetoriumData.setCafeTitleStart(cafetoriumData.getCafeName()
                                + AppResponse.FOR_MEALS);
                            cafetoriumData.setCarteWeek(nextWeekDto.getCarteWeek());
                            cafetoriumData.setCarteWeekDesc(nextWeekDto.getCarteWeekDesc());
                            cafetoriumData.setCafeTitleEnd(AppResponse.CARTE_ADVICE);
                            String dateTime = format.format(nextWeekDto.getCarteTime());
                            cafetoriumData.setCarteTime(dateTime);
                            BeanUtils.copyProperties(cafetoriumData, cafeData);
                            resultDataList.add(cafeData);
                        }
                    }
                }
            }
        }
        return resultDataList;
    }

    /**     
     * cafetoriumlist：查询用户选择的当前食堂下的喜欢菜品百分比
     * @param cafetoriumId 所属食堂id
     * @param carteWeek    周数
     * @param carteTypeId  菜单类型id
     * @return
     * @exception   
     * @author 刘博
     * @date 2015年6月25日 下午3:47:41    
     */
    @RequestMapping(value = "/dishPercent.do", method = {RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Response<DishPercentDto> cartePercent(String cafetoriumId, String carteWeek)
    {
        List<CarteTypeDto> typeDtoList = carteTypeService.finByCafetoriumId(cafetoriumId);
        Response<DishPercentDto> result = new Response<DishPercentDto>();
        List<DishPercentDto> list = new ArrayList<DishPercentDto>();
        DishPercentDto dpDto = new DishPercentDto();
        if (StringUtils.isEmpty(cafetoriumId))
        {
            result.setMessage("食堂id为空");
            result.setStatus(AppResponse.RETURN_FAILE);
            result.setData(dpDto);
            logger.info("食堂id为空");
            return result;
        }
        if (StringUtils.isEmpty(carteWeek))
        {
            result.setMessage("菜单周期为空");
            result.setStatus(AppResponse.RETURN_FAILE);
            result.setData(dpDto);
            logger.info("菜单周期为空");
            return result;
        }
        if (!CollectionUtils.isEmpty(typeDtoList))
        {
            List<DishTypeDto> dishTypeList = new ArrayList<DishTypeDto>();

            for (CarteTypeDto type : typeDtoList)
            {
                List<DishDetailDto> dishList = new ArrayList<DishDetailDto>();
                //放入菜品类型的id和名称;
                DishTypeDto typeDto = new DishTypeDto();
                typeDto.setTypeId(type.getId());
                typeDto.setTypeName(type.getCarteTypeName());

                //查询条件dto:食堂id,周期,菜单类型id
                NextWeekCarteDtos nextWeekCarteDtos = new NextWeekCarteDtos();
                nextWeekCarteDtos.setCafetoriumId(cafetoriumId);
                nextWeekCarteDtos.setCarteWeek(carteWeek);
                nextWeekCarteDtos.setCarteTypeId(type.getId());
                //根据查询条件，获取所有去除重复的菜品，统计百分比.
                List<NextWeekCarteDtos> nextWeekCarteDtoList =
                    nextMenuOperateSerivce.findAllDistinctDish(nextWeekCarteDtos);
                if (!StringUtils.isEmpty(nextWeekCarteDtoList))
                {
                    for (NextWeekCarteDtos nextDto : nextWeekCarteDtoList)
                    {
                        //只取每种菜品类型下的前9条;
                        if (list.size() >= 9)
                        {
                            break;
                        }
                        NextWeekCarteDtos dto = new NextWeekCarteDtos();
                        dto.setCafetoriumId(cafetoriumId);
                        dto.setCarteWeek(carteWeek);
                        dto.setCarteTypeId(type.getId());
                        dto.setDishName(nextDto.getDishName());
                        //根据用户选择的某个食堂在某个星期的某个类型的某个菜,统计该菜品占总数的百分比，返回到手机端.
                        String percent = nextMenuOperateSerivce.findDishPercent(dto);
                        DishDetailDto dishDto = new DishDetailDto();
                        dishDto.setName(nextDto.getDishName());
                        dishDto.setValue(percent);
                        dishDto.setTypeId(type.getId());
                        dishList.add(dishDto);
                        String json = JSON.toJSONString(dishList);
                        JSON parse = (JSON) JSONObject.parse(json);
                        typeDto.setDatas(parse);
                    }
                }
                dishTypeList.add(typeDto);
            }
            dpDto.setDishTypeList(dishTypeList);
        }
        //返回结果。
        result.setMessage(AppResponse.RETURN_CARTE_PERCENT_SUCCESS);
        result.setStatus(AppResponse.RETURN_SUCCESS);
        result.setData(dpDto);
        return result;
    }
}
