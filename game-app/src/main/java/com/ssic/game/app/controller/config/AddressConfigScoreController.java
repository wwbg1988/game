package com.ssic.game.app.controller.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ssic.catering.base.dto.AddressDto;
import com.ssic.catering.base.dto.AddressUserDto;
import com.ssic.catering.base.dto.CafetoriumDto;
import com.ssic.catering.base.dto.ConfigDto;
import com.ssic.catering.base.dto.ConfigScoreDto;
import com.ssic.catering.base.pojo.Address;
import com.ssic.catering.base.pojo.Comment;
import com.ssic.catering.base.pojo.Config;
import com.ssic.catering.base.pojo.Sensitive;
import com.ssic.catering.base.service.IAddressService;
import com.ssic.catering.base.service.IAddressUserService;
import com.ssic.catering.base.service.ICafetoriumService;
import com.ssic.catering.base.service.ICommentSensitiveService;
import com.ssic.catering.base.service.ICommentService;
import com.ssic.catering.base.service.IConfigScoreService;
import com.ssic.catering.base.service.IConfigService;
import com.ssic.catering.base.service.ISensitiveService;
import com.ssic.catering.common.util.AppResponse;
import com.ssic.game.app.controller.catering.CafetoriumController;
import com.ssic.game.app.controller.dto.CafetoriumConfigScoreReportDto;
import com.ssic.game.app.controller.dto.CafetoriumHistoryConfigScoreDto;
import com.ssic.game.app.controller.dto.HistoryConfigScoreDto;
import com.ssic.game.app.controller.dto.ScoreReportDto;
import com.ssic.game.app.dao.JsonDto;
import com.ssic.game.common.dto.ImsUsersDto;
import com.ssic.game.common.dto.ProjectUsersDto;
import com.ssic.game.common.service.IImsUserService;
import com.ssic.game.common.service.ProjectServices;
import com.ssic.util.BeanUtils;
import com.ssic.util.constants.DataStatus;
import com.ssic.util.model.Response;

/**
 * 		
 * <p>Title: AddressController </p>
 * <p>Description: 获取地区评论</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 张亚伟	
 * @date 2015年8月8日 下午1:57:10	
 * @version 1.0
 * <p>修改人：张亚伟</p>
 * <p>修改时间：2015年8月8日 下午1:57:10</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping("/addressConfigScore")
public class AddressConfigScoreController
{

    @Autowired
    private IAddressUserService iAddressUserService;

    @Autowired
    private IAddressService iAddressService;

    @Autowired
    private IConfigScoreService iConfigScoreService;

    @Autowired
    private IConfigService iConfigService;

    @Autowired
    private ICommentService iCommentService;

    @Autowired
    private ICommentSensitiveService iCommentSensitiveService;

    @Autowired
    private ISensitiveService iSensitiveService;

    @Autowired
    private IImsUserService iImsUserService;

    @Autowired
    private ICafetoriumService iCafetoriumService;

    @Autowired
    private CafetoriumController cafetoriumController;

    @Autowired
    private ProjectServices projectServices;

    /**
     * getAddress：通过区域ID获取区域列表
     * @param searchType 获取类型(1为获取区域平级,2为获取区域下级)
     * @param addressId 区域ID
     * @return
     * @exception	
     * @author 张亚伟
     * @date 2015年8月25日 下午1:19:33
     */
    public List<AddressDto> findAddressByTypeAndAddressId(Integer searchType, String addressId, String projId)
    {

        Address address = new Address();
        //判断addressId是否为空,如果为空,默认获取一起区域列表
        if (addressId != null)
        {
            //获取区域信息
            address = iAddressService.findAddressById(addressId);
            if (address == null)
            {
                return null;
            }
            if (address.getAddressCode() == null)
            {
                return null;
            }
        }
        else
        {
            address.setAddressCode("00");
        }
        //通过区域辅助码获取区域集合
        List<Address> list =
            iAddressService.findAddressByTypeAndAddressCode(searchType, address.getAddressCode(), projId);
        if (list == null || list.size() == 0)
        {
            return null;
        }

        //将区域负责人封装进区域集合中
        List<AddressDto> addressDtoList = new ArrayList<>();
        for (Address add : list)
        {
            String addressCode = add.getAddressCode();
            Integer addressType = 3;
            if(addressCode.length() == 2){//总公司
            	addressType = 0;
            }
            if (addressCode.length() == 4)
            {//大区
                addressType = 1;
            }
            else if (addressCode.length() == 6)
            {//省
                addressType = 2;
            }
            else if (addressCode.length() == 8)
            {//市
                addressType = 3;
            }
            Response<AddressUserDto> addressUser =
                getAddressUserById(add.getId(), projId, addressType.toString());
            if (addressUser.getStatus() == 200)
            {
                AddressDto addressDto = new AddressDto();
                BeanUtils.copyProperties(add, addressDto);
                addressDto.setAddressUserDto(addressUser.getData());
                addressDtoList.add(addressDto);
            }
        }

        if (addressDtoList.size() == 0)
        {
            return null;
        }
        return addressDtoList;
    }

    @RequestMapping("/findAddressByUserId2Cafetorium")
    @ResponseBody
    public Response<List<AddressDto>> findAddressByUserId2Cafetorium(String userId)
    {

        return null;
    }

    /**
     * 
     * findAddressByUserId：通过用户ID获取区域列表和区域负责人
     * @param userId 用户ID
     * @return
     * @exception	
     * @author 张亚伟
     * @date 2015年8月25日 下午2:50:50
     */
    @RequestMapping("/findAddressByUserId")
    @ResponseBody
    public Response<List<AddressDto>> findAddressByUserId(String userId)
    {

        Response<List<AddressDto>> response = new Response<List<AddressDto>>();

        //获取用户信息
        AddressUserDto addressUser = iAddressUserService.getAddressUserByUserId(userId);

        if (addressUser == null)
        {
            response.setStatus(500);
            response.setMessage("用户不存在");
            return response;
        }

        String projId = "";
        Map<String, Object> mapproj = findProjByUserId(userId);
        Boolean projState = (Boolean) mapproj.get("SUCCESS");
        if (projState)
        {
            ProjectUsersDto projectUsersDto = (ProjectUsersDto) mapproj.get("projectUsersDto");
            projId = projectUsersDto.getProjId();
        }
        else
        {
            response.setMessage("项目ID不存在！");
            response.setStatus(AppResponse.RETURN_FAILE);
            return response;
        }

        //判断用户是不是最高管理员
        if (addressUser.getAddressType() == 0)
        {
            //如果用户为最高管理员,获取当前区域列表
            List<AddressDto> list = findAddressByTypeAndAddressId(1, null, projId);//findAddressByAddressId(addressUser.getAddressId()).getData();
            if (list == null)
            {
                response.setStatus(500);
                response.setMessage("未查询到区域");
                return response;
            }
            response.setStatus(200);
            response.setMessage("SUCCESS");
            response.setData(list);
            //判断当前登录用户是不是食堂用户,如果是食堂用户,将食堂ID放入AddressUserDto的cafetorium字段中
        }
        else if (addressUser.getAddressType() == 4)
        {

            Response<List<CafetoriumDto>> cafetoriumResponse =
                cafetoriumController.getCafetoriumList(addressUser.getAddressId());
            List<CafetoriumDto> cafetoriumList = cafetoriumResponse.getData();
            if (cafetoriumList == null || cafetoriumList.size() == 0)
            {
                response.setStatus(500);
                response.setMessage("未查询食堂信息");
                return response;
            }
            AddressDto addressDto = null;
            for (CafetoriumDto cafetoriumDto : cafetoriumList)
            {
                if (cafetoriumDto.getCafeCode().equals(addressUser.getCafeCode()))
                {
                    addressDto = new AddressDto();
                    addressUser.setCafetorium(cafetoriumDto.getId());
                    addressDto.setAddressUserDto(addressUser);
                    break;
                }
            }
            if (addressDto != null)
            {
                List<AddressDto> list = new ArrayList<>();
                list.add(addressDto);
                response.setStatus(200);
                response.setData(list);
                response.setMessage("SUCCESS");
                return response;
            }
            else
            {
                response.setStatus(500);
                response.setMessage("未查询到信息");
                return response;
            }

        }
        else
        {
            //如果用户不是最高管理员,获取当前自己负责的区域信息
            List<AddressDto> list = new ArrayList<>();
            Response<List<AddressDto>> addressAll = getAddressAll();
            List<AddressDto> addressList = addressAll.getData();
            for (AddressDto addressDto : addressList)
            {
                if (addressUser.getAddressId().equals(addressDto.getId()))
                {
                    list.add(addressDto);
                    break;
                }
            }
            if (list.size() == 0)
            {
                response.setStatus(500);
                response.setMessage("未查询到区域信息");
            }
            else
            {
                AddressDto addressDto = list.get(0);
                Response<AddressUserDto> addressUserById =
                    getAddressUserById(addressDto.getId(), projId, addressUser.getAddressType().toString());
                if (addressUserById.getStatus() != 200)
                {
                    response.setStatus(500);
                    response.setMessage("未查询到区域信息");
                }
                else
                {
                    addressDto.setAddressUserDto(addressUserById.getData());
                    response.setStatus(200);
                    response.setData(list);
                    response.setMessage("SUCCESS");
                }
            }

        }
        return response;
    }

    /**
     * 
     * findAddressByAddressId：通过区域ID获取区域列表和区域负责人
     * @param addressId 区域
     * @return
     * @exception	
     * @author 张亚伟
     * @date 2015年8月25日 下午4:28:56
     */
    @RequestMapping("/findAddressByAddressId")
    @ResponseBody
    public Response<List<AddressDto>> findAddressByAddressId(String addressId, String projId)
    {
        Response<List<AddressDto>> response = new Response<List<AddressDto>>();
        List<AddressDto> list = findAddressByTypeAndAddressId(2, addressId, projId);
        if (list == null)
        {
            response.setStatus(500);
            response.setMessage("未查询到区域信息");
            return response;
        }
        response.setStatus(200);
        response.setMessage("SUCCESS");
        response.setData(list);
        return response;
    }

    /**
     * getAddressAll：获取地区列表
     * @return
     * @exception	
     * @author 张亚伟
     * @date 2015年8月8日 下午4:02:02
     */
    @RequestMapping("/getAddressAll")
    @ResponseBody
    public Response<List<AddressDto>> getAddressAll()
    {

        //获取所有地区列表
        Response<List<AddressDto>> response = new Response<>();
        List<AddressDto> findAll = iAddressService.findAll();
        if (findAll != null)
        {
            response.setStatus(200);
            response.setMessage("success");
            response.setData(findAll);
        }
        else
        {
            response.setStatus(500);
            response.setMessage("error");
        }
        return response;
    }

    /**
     * getAddressUser：通过用户ID获取用户区域负责人信息
     * @param userId 用户ID
     * @return
     * @exception	
     * @author 张亚伟
     * @date 2015年8月8日 下午4:36:32
     */
    @RequestMapping("/getAddressUserByUserId")
    @ResponseBody
    public Response<AddressUserDto> getAddressUserByUserId(String userId)
    {
        Response<AddressUserDto> response = new Response<>();
        //获取用户信息
        AddressUserDto addressUser = iAddressUserService.getAddressUserByUserId(userId);

        if (addressUser == null)
        {
            response.setStatus(500);
            response.setMessage("用户不存在");
            return response;
        }

        //获取用户手机信息和姓名
        ImsUsersDto imsUsersDto = iImsUserService.findByUserId(addressUser.getUserId());

        if (imsUsersDto != null)
        {
            addressUser.setPhone(imsUsersDto.getMobilePhone());
            addressUser.setUserName(imsUsersDto.getName());
        }

        response.setStatus(200);
        response.setMessage("success");
        response.setData(addressUser);
        return response;
    }

    /**
     * getAddressUserById：通过区域主键ID获取用户区域负责人信息
     * @param addressId  区域Id
     * @return
     * @exception	
     * @author 张亚伟
     * @date 2015年8月10日 下午4:47:31
     */
    @RequestMapping("/getAddressUserById")
    @ResponseBody
    public Response<AddressUserDto> getAddressUserById(String addressId, String projId, String addresstype)
    {
        Response<AddressUserDto> response = new Response<>();

        //获取区域负责人
        //AddressUserDto addressUserDto = iAddressUserService.findAddressUserByID(id);

        AddressUserDto addressUserDto =
            iAddressUserService.finAddressUserByAddressId(addressId, projId, addresstype);

        if (addressUserDto == null)
        {
            response.setStatus(DataStatus.HTTP_FAILE);
            response.setMessage("error");
        }

        //通过区域负责人ID获取区域的评分
        AddressUserDto addressUser = iAddressUserService.getAddressUserByUserId(addressUserDto.getUserId());
        if (addressUser == null)
        {
            response.setStatus(DataStatus.HTTP_FAILE);
            response.setMessage("error");
            return response;
        }

        //获取用户手机信息和姓名
        ImsUsersDto imsUsersDto = iImsUserService.findByUserId(addressUser.getUserId());

        if (imsUsersDto != null)
        {
            addressUser.setPhone(imsUsersDto.getMobilePhone());
            addressUser.setUserName(imsUsersDto.getName());
        }

        response.setStatus(DataStatus.HTTP_SUCCESS);
        response.setMessage("success");
        response.setData(addressUser);
        return response;
    }

    /**
     * 
     * getAddressUserByAddressIdAndCafeCode：通过区域ID和食堂编号获取食堂负责人
     * @param addressId 区域ID
     * @param cafeCode 食堂编号
     * @return
     * @exception	
     * @author 张亚伟
     * @date 2015年8月18日 下午2:56:01
     */
    @RequestMapping("/getAddressUserByAddressIdAndCafeCode")
    @ResponseBody
    public Response<AddressUserDto> getAddressUserByAddressIdAndCafeCode(String addressId, String cafeCode)
    {
        Response<AddressUserDto> response = new Response<>();

        //获取区域负责人
        //AddressUserDto addressUserDto = iAddressUserService.findAddressUserByID(id);

        AddressUserDto addressUserDto =
            iAddressUserService.finAddressUserByAddressIdAndCafeCode(addressId, cafeCode);

        if (addressUserDto == null)
        {
            response.setStatus(DataStatus.HTTP_FAILE);
            response.setMessage("error");
            return response;
        }

        //通过区域负责人ID获取区域的评分
        AddressUserDto addressUser = iAddressUserService.getAddressUserByUserId(addressUserDto.getUserId());
        if (addressUser == null)
        {
            response.setStatus(DataStatus.HTTP_FAILE);
            response.setMessage("error");
            return response;
        }

        //获取用户手机信息和姓名
        ImsUsersDto imsUsersDto = iImsUserService.findByUserId(addressUser.getUserId());

        if (imsUsersDto != null)
        {
            addressUser.setPhone(imsUsersDto.getMobilePhone());
            addressUser.setUserName(imsUsersDto.getName());
            addressUser.setUserImg(imsUsersDto.getUserImage());
        }

        response.setStatus(DataStatus.HTTP_SUCCESS);
        response.setMessage("success");
        response.setData(addressUser);
        return response;
    }

    /**
     * 
     * configAll：获取所有服务评分项分类
     * @return
     * @exception	
     * @author 张亚伟
     * @date 2015年8月12日 下午2:19:21
     */
    @RequestMapping("/configAll")
    @ResponseBody
    public Response<List<Config>> configAll()
    {
        Response<List<Config>> response = new Response<>();
        List<Config> configList = iConfigService.findAll();
        if (configList == null)
        {
            response.setStatus(500);
            response.setMessage("未查询到服务评分项");
            return response;
        }
        response.setData(configList);
        response.setStatus(200);
        response.setMessage("success");
        return response;
    }

    /**
     * historyConfigScore：通过食堂ID获取历史评分
     * @param cafetoriumId 食堂ID
     * @param index 第几页
     * @param size 每页多少条信息
     * @return
     * @exception	
     * @author 张亚伟
     * @date 2015年8月11日 下午1:08:19
     */
    @RequestMapping("/historyConfigScore")
    @ResponseBody
    public Response<CafetoriumHistoryConfigScoreDto> historyConfigScore(String cafetoriumId, Integer index,
        Integer size)
    {
        Response<CafetoriumHistoryConfigScoreDto> response = new Response<CafetoriumHistoryConfigScoreDto>();

        CafetoriumHistoryConfigScoreDto cafetoriumHistoryConfigScoreDto =
            new CafetoriumHistoryConfigScoreDto();

        if (index < 1 || size < 1)
        {
            response.setStatus(500);
            response.setMessage("分页异常");
            return response;
        }

        //获取食堂信息
        CafetoriumDto cafetoriumDto = iCafetoriumService.findById(cafetoriumId);

        //获取食堂信息和食堂地址分级信息
        cafetoriumDto.setLevelAddress(getLevelAddress(cafetoriumDto.getAddressId()));

        //获取食堂负责人信息
        Response<AddressUserDto> addressUserResponse =
            getAddressUserByAddressIdAndCafeCode(cafetoriumDto.getAddressId(), cafetoriumDto.getCafeCode());
        
        AddressUserDto addressUser = addressUserResponse.getData();
        if(addressUser != null)
        {
            ImsUsersDto user = iImsUserService.findByUserId(addressUser.getId());
            if(user != null)
            {
                addressUser.setUserImg(user.getUserImage());
            }
            
        }

        if (addressUserResponse.getStatus() == 200)
        {
            AddressUserDto addressUserDto = addressUserResponse.getData();
            cafetoriumDto.setUserName(addressUserDto.getUserName());
            cafetoriumDto.setMobileNo(addressUserDto.getPhone());
            cafetoriumDto.setUserImg(addressUserDto.getUserImg());
        }

        //获取评分项
        List<Config> configList = iConfigService.findAll();
        if (configList != null)
        {
            cafetoriumHistoryConfigScoreDto.setConfigList(configList);
        }

        //获取根据分页获取日期区间
        List<String> timeList = iConfigScoreService.findCreateTimeDistinct(cafetoriumId, index, size);
        if (timeList == null)
        {
            response.setStatus(500);
            response.setMessage("未查询到记录");
            return response;
        }

        List<HistoryConfigScoreDto> historyConfigScoreDtoList = new ArrayList<>();

        //通过日期获取评分餐厅历史评分信息
        for (String time : timeList)
        {
            HistoryConfigScoreDto historyConfigScoreDto = new HistoryConfigScoreDto();
            historyConfigScoreDto.setTime(time);

            //获取食堂评分信息
            List<ConfigScoreDto> configScoreList =
                iConfigScoreService.findConfigScoreListToCafetoriumIdByCreateTime(cafetoriumId, time);

            //获取评分分类
            Map<String, List<ConfigScoreDto>> map = new HashMap<String, List<ConfigScoreDto>>();
            if (configScoreList != null)
            {
                for (ConfigScoreDto configScoreDto : configScoreList)
                {
                    if (map.get(configScoreDto.getConfigId()) == null)
                    {
                        List<ConfigScoreDto> list = new ArrayList<>();
                        list.add(configScoreDto);
                        map.put(configScoreDto.getConfigId(), list);
                    }
                    else
                    {
                        List<ConfigScoreDto> list = map.get(configScoreDto.getConfigId());
                        list.add(configScoreDto);
                    }
                }
            }

            //进行食堂评分数据封装
            List<ConfigDto> configDtoList = new ArrayList<ConfigDto>();
            for (String key : map.keySet())
            {
                //获取评分项
                ConfigDto configDto = iConfigService.findConfigToId(key);
                List<ConfigScoreDto> list = map.get(key);
                //计算评分
                int sum = 0;
                for (ConfigScoreDto configScoreDto : list)
                {
                    if (configScoreDto.getScore() != null)
                    {
                        sum += Integer.parseInt(configScoreDto.getScore());
                    }
                }

                if (sum != 0 && list != null && list.size() > 0)
                {
                    configDto.setScore((Double.parseDouble(sum + "") / Double.parseDouble(list.size() + ""))
                        + "");
                }
                configDtoList.add(configDto);
            }

            //通过食堂Id和时间获取评论集合
            List<Comment> commentList =
                iCommentService.findCommentListByCafetoriumIdAndCreateTime(cafetoriumId, time);

            //通过评论集合获取敏感词
            if (commentList != null && commentList.size() > 0)
            {

                //获取评论条数
                historyConfigScoreDto.setCount(commentList.size());

                List<String> list =
                    iCommentSensitiveService.findCommentSensitiveListByCommentList(commentList);
                //通过敏感词Id获取敏感词
                List<String> sensitiveList = new ArrayList<>();
                for (String sensitiveId : list)
                {
                    Sensitive sensitive = iSensitiveService.getSensitiveById(sensitiveId);
                    if (sensitive != null && sensitive.getSensitiveName() != null)
                    {
                        sensitiveList.add(sensitive.getSensitiveName());
                    }
                }
                if (sensitiveList.size() > 0)
                {
                    historyConfigScoreDto.setSensitiveList(sensitiveList);
                }
            }

            historyConfigScoreDto.setConfigList(configDtoList);
            historyConfigScoreDtoList.add(historyConfigScoreDto);
        }

        //获取食堂当天的平均分
        for (HistoryConfigScoreDto dayHistoryDto : historyConfigScoreDtoList)
        {
            List<ConfigDto> list1 = dayHistoryDto.getConfigList();
            if (list1 != null && list1.size() > 0)
            {
                Double allScore1 = 0.0;
                for (ConfigDto configDto2 : list1)
                {
                    String score2 = configDto2.getScore();
                    Double dScore2 = Double.parseDouble(score2);
                    allScore1 = allScore1 + dScore2;
                }
                Double avgScore1 = allScore1 / list1.size();
                dayHistoryDto.setDayScore(avgScore1.toString().substring(0, 3));
            }
        }

        //获取全部历史平均分
        Double allScoreAll = 0.0;
        if (historyConfigScoreDtoList != null && historyConfigScoreDtoList.size() > 0)
        {
            for (HistoryConfigScoreDto dayHistoryDto : historyConfigScoreDtoList)
            {
                String dayScore = dayHistoryDto.getDayScore();
                Double dayScore2 = Double.parseDouble(dayScore);
                allScoreAll = allScoreAll + dayScore2;
            }
            Double avgScoreAll = allScoreAll / historyConfigScoreDtoList.size();
            cafetoriumDto.setScore(avgScoreAll.toString().substring(0, 3));
        }
        else
        {
            cafetoriumDto.setScore("0.0");
        }

        if (cafetoriumDto != null)
        {
            cafetoriumHistoryConfigScoreDto.setCafetoriumDto(cafetoriumDto);
        }
        cafetoriumHistoryConfigScoreDto.setHistoryConfigScoreDtoList(historyConfigScoreDtoList);
        response.setStatus(200);
        response.setMessage("success");
        response.setData(cafetoriumHistoryConfigScoreDto);
        return response;
    }

    /**
     * cafetoriumConfigScoreReport：通过食堂Id和时间获取食堂评分报告
     * @param cafetoriumId 食堂Id
     * @param time 时间
     * @return
     * @exception	
     * @author 张亚伟
     * @date 2015年8月12日 下午4:28:06
     */
    @RequestMapping("/cafetoriumConfigScoreReport")
    @ResponseBody
    public Response<CafetoriumConfigScoreReportDto> cafetoriumConfigScoreReport(String cafetoriumId,
        String time)
    {
        Response<CafetoriumConfigScoreReportDto> response = new Response<CafetoriumConfigScoreReportDto>();
        CafetoriumConfigScoreReportDto cafetoriumConfigScoreReportDto = new CafetoriumConfigScoreReportDto();
        //获取食堂信息
        CafetoriumDto cafetoriumDto = iCafetoriumService.findById(cafetoriumId);
        if (cafetoriumDto != null)
        {
            cafetoriumConfigScoreReportDto.setCafetoriumDto(cafetoriumDto);
        }

        //获取评分项
        List<Config> configList = iConfigService.findAll();
        if (configList != null)
        {
            cafetoriumConfigScoreReportDto.setConfigList(configList);
        }

        HistoryConfigScoreDto historyConfigScoreDto = new HistoryConfigScoreDto();
        //设置时间
        historyConfigScoreDto.setTime(time);

        //获取食堂评分信息
        List<ConfigScoreDto> configScoreList =
            iConfigScoreService.findConfigScoreListToCafetoriumIdByCreateTime(cafetoriumId, time);

        //获取评分分类
        Map<String, List<ConfigScoreDto>> map = new HashMap<String, List<ConfigScoreDto>>();
        if (configScoreList != null)
        {
            for (ConfigScoreDto configScoreDto : configScoreList)
            {
                if (map.get(configScoreDto.getConfigId()) == null)
                {
                    List<ConfigScoreDto> list = new ArrayList<>();
                    list.add(configScoreDto);
                    map.put(configScoreDto.getConfigId(), list);
                }
                else
                {
                    List<ConfigScoreDto> list = map.get(configScoreDto.getConfigId());
                    list.add(configScoreDto);
                }
            }
        }

        //进行食堂评分数据封装
        List<ConfigDto> configDtoList = new ArrayList<ConfigDto>();
        for (String key : map.keySet())
        {
            //获取评分项
            ConfigDto configDto = iConfigService.findConfigToId(key);
            List<ConfigScoreDto> list = map.get(key);
            //计算评分
            int sum = 0;
            for (ConfigScoreDto configScoreDto : list)
            {
                if (configScoreDto.getScore() != null)
                {
                    sum += Integer.parseInt(configScoreDto.getScore());
                }
            }

            if (sum != 0 && list != null && list.size() > 0)
            {
                String score = (Double.parseDouble(sum + "") / Double.parseDouble(list.size() + "")) + "";
                if (score.length() > 3)
                {
                    score = score.substring(0, 3);
                }
                configDto.setScore(score);
            }
            configDtoList.add(configDto);
        }

        //通过食堂Id和时间获取评论集合
        List<Comment> commentList =
            iCommentService.findCommentListByCafetoriumIdAndCreateTime(cafetoriumId, time);

        if (commentList != null && commentList.size() > 0)
        {
            //获取评论条数
            historyConfigScoreDto.setCount(commentList.size());

            //通过评论集合获取敏感词
            List<String> list = iCommentSensitiveService.findCommentSensitiveListByCommentList(commentList);
            //通过敏感词Id获取敏感词
            List<String> sensitiveList = new ArrayList<>();
            for (String sensitiveId : list)
            {
                Sensitive sensitive = iSensitiveService.getSensitiveById(sensitiveId);
                if (sensitive != null && sensitive.getSensitiveName() != null)
                {
                    sensitiveList.add(sensitive.getSensitiveName());
                }
            }
            if (sensitiveList.size() > 0)
            {
                historyConfigScoreDto.setSensitiveList(sensitiveList);
            }
        }
        historyConfigScoreDto.setConfigList(configDtoList);
        cafetoriumConfigScoreReportDto.setHistoryConfigScoreDto(historyConfigScoreDto);
        response.setStatus(200);
        response.setMessage("success");
        response.setData(cafetoriumConfigScoreReportDto);
        return response;
    }

    /**
     * getLevelAddress：通过区域ID获取地区分级
     * @param addressId 区域ID
     * @return
     * @exception	
     * @author 张亚伟
     * @date 2015年8月26日 上午10:07:12
     */
    public String getLevelAddress(String addressId)
    {
        Address address = iAddressService.findAddressById(addressId);
        if (address == null)
        {
            return null;
        }
        if (address.getAddressCode() == null)
        {
            return null;
        }
        char[] addressCodeArr = address.getAddressCode().toCharArray();
        List<String> list = new ArrayList<>();
        //拆分区域编码
        String s = "";
        for (int i = 0; i < addressCodeArr.length; i++)
        {
            s += addressCodeArr[i];
            if (i != 0 && i % 2 == 1)
            {
                list.add(s);
            }
        }

        //将区域分级拼接成字符串
        String levelAddress = "";
        for (String addressCode : list)
        {
            //通过区域辅助码获取区域信息
            Address add = iAddressService.findAddressByAddressCode(addressCode);
            if (add != null && add.getAddressCode() != null)
            {
                levelAddress += add.getAddressName() + "-";
            }
        }
        if (levelAddress.length() > 1)
        {
            return levelAddress.substring(0, levelAddress.length() - 1);
        }
        return levelAddress;
    }

    /**
     * cafetoriumConfigScoreReport：通过食堂Id和时间获取食堂评分报告
     * @param cafetoriumId 食堂Id
     * @param time 时间
     * @return
     * @exception	
     * @author 张亚伟
     * @date 2015年8月12日 下午4:28:06
     */
    @RequestMapping("/cafetoriumConfigScoreReportJson")
    @ResponseBody
    public Response<HistoryConfigScoreDto> cafetoriumConfigScoreReportJson(String cafetoriumId, String time)
    {
        Response<HistoryConfigScoreDto> response = new Response<HistoryConfigScoreDto>();
        Response<CafetoriumConfigScoreReportDto> responseJson =
            cafetoriumConfigScoreReport(cafetoriumId, time);
        Response<CafetoriumConfigScoreReportDto> historyResponseJson =
            cafetoriumConfigScoreReport(cafetoriumId, null);
        if (responseJson.getStatus() != 200)
        {
            response.setStatus(responseJson.getStatus());
            response.setMessage(responseJson.getMessage());
            return response;
        }
        CafetoriumConfigScoreReportDto cafetoriumConfigScoreReportDto = responseJson.getData();
        CafetoriumConfigScoreReportDto historyCafetoriumConfigScoreReportDto = historyResponseJson.getData();

        //获取食堂信息和食堂地址分级信息
        CafetoriumDto cafetoriumDto = cafetoriumConfigScoreReportDto.getCafetoriumDto();
        cafetoriumDto.setLevelAddress(getLevelAddress(cafetoriumDto.getAddressId()));

        //获取食堂负责人信息
        Response<AddressUserDto> addressUserResponse =
            getAddressUserByAddressIdAndCafeCode(cafetoriumDto.getAddressId(), cafetoriumDto.getCafeCode());

        if (addressUserResponse.getStatus() == 200)
        {
            AddressUserDto addressUserDto = addressUserResponse.getData();
            cafetoriumDto.setUserName(addressUserDto.getUserName());
            cafetoriumDto.setMobileNo(addressUserDto.getPhone());
            cafetoriumDto.setUserImg(addressUserDto.getUserImg());
        }

        //获取食堂评分信息
        HistoryConfigScoreDto configScoreDto = cafetoriumConfigScoreReportDto.getHistoryConfigScoreDto();

        //获取食堂历史评分信息
        HistoryConfigScoreDto historyConfigScoreDto =
            historyCafetoriumConfigScoreReportDto.getHistoryConfigScoreDto();

        if (configScoreDto == null)
        {
            HistoryConfigScoreDto historyConfigScore = new HistoryConfigScoreDto();
            historyConfigScore.setCafetoriumDto(cafetoriumDto);
            response.setData(historyConfigScore);
            response.setStatus(500);
            response.setMessage("未查询到食堂评分报告");
            return response;
        }

        //获取评分项
        List<ConfigDto> configList = configScoreDto.getConfigList();

        //获取历史评分项
        List<ConfigDto> historyConfigList = historyConfigScoreDto.getConfigList();

        if (configList == null || configList.size() == 0)
        {
            HistoryConfigScoreDto historyConfigScore = new HistoryConfigScoreDto();
            historyConfigScore.setCafetoriumDto(cafetoriumDto);
            response.setData(historyConfigScore);
            response.setStatus(500);
            response.setMessage("未查询到食堂评分报告");
            return response;
        }

        //进行评分项json拼接
        List<JsonDto> list = new ArrayList<>();
        for (ConfigDto configDto : configList)
        {
            JsonDto jsonDto = new JsonDto();
            jsonDto.setName(configDto.getConfigName());
            jsonDto.setValue(configDto.getScore());
            for (ConfigDto config : historyConfigList)
            {
                if (configDto.getId().equals(config.getId()))
                {
                    jsonDto.setHisvalue(config.getScore());
                    break;
                }
            }
            list.add(jsonDto);
        }
        String json = JSON.toJSONString(list);
        JSON parse = (JSON) JSONObject.parse(json);
        ScoreReportDto scoreReportDto = new ScoreReportDto();
        scoreReportDto.setDatas(parse);
        configScoreDto.setScoreReportDto(scoreReportDto);

        configScoreDto.setCafetoriumDto(cafetoriumDto);
        response.setStatus(200);
        response.setMessage("SUCCESS");
        response.setData(configScoreDto);
        return response;

    }

    public static void main(String[] args)
    {
        System.out.println(0 & 2);

    }

    //根据userid查询项目id
    public Map<String, Object> findProjByUserId(String userid)
    {
        ProjectUsersDto projectUsersDto = projectServices.findByUserID(userid);
        Map<String, Object> map = new HashMap<String, Object>();
        if (projectUsersDto != null && projectUsersDto.getProjId() != null)
        {
            map.put("SUCCESS", true);
            map.put("projectUsersDto", projectUsersDto);
        }
        else
        {
            map.put("SUCCESS", false);
            map.put("projectUsersDto", projectUsersDto);
        }
        return map;
    }

}
