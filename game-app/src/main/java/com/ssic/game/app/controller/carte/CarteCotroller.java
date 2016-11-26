package com.ssic.game.app.controller.carte;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.ssic.catering.base.dto.CarteDto;
import com.ssic.catering.base.dto.CarteTypeDto;
import com.ssic.catering.base.pojo.NextWeekCarte;
import com.ssic.catering.base.service.ICarteService;
import com.ssic.catering.base.service.ICarteTypeService;
import com.ssic.catering.common.util.DateUtil;
import com.ssic.game.app.service.NextMenuOperateSerivce;
import com.ssic.game.common.util.HttpDataResponse;
import com.ssic.util.DateUtils;
import com.ssic.util.StringUtils;
import com.ssic.util.model.Response;

/**
 * 菜单控制器
 * 
 * @author 张亚伟
 */
@RequestMapping("/carte")
@Controller
public class CarteCotroller
{

    private static Logger log = Logger.getLogger(CarteCotroller.class);

    @Autowired
    private ICarteService carteService;

    @Autowired
    private ICarteTypeService iCarteTypeService;

    @Autowired
    private NextMenuOperateSerivce nextMenuOperateSerivce;

    // /**
    // * 获取所有菜单
    // */
    // @RequestMapping("/list")
    // @ResponseBody
    // public Response<List<CarteTypeDto>> getCarte(CarteDto carteDto,
    // String userId) {
    //
    // Response<List<CarteTypeDto>> response = new Response<>();
    // //判断用户是否在本周进行过评价
    // //获取当前周数
    // int localWeek = DateUtil.localWeek(new Date()) + 1;
    // // //获取本周菜品投票
    // // List<NextWeekCarte> is = nextMenuOperateSerivce
    // // .findNextWeekCarteByCarteWeekAndOpenIdAndCafetoriumId(
    // // localWeek, userId, carteDto.getCafetoriumId());
    // //如果投票部位空,表示本周已经进行过投票
    // // if (is != null && is.size() > 0) {
    // // response.setStatus(500);
    // // response.setMessage("每周只可以进行一次投票");
    // // return response;
    // // }
    //
    // // 通过周期来获取所有菜品
    // carteDto.setCarteWeek(DateUtil.localWeek(new Date()));
    // // List<Carte> carteList = carteService.getCarteListByCarteWeek(carteDto);
    // //加入查询最近的下周菜单
    // List<Carte> carteList = carteService.getCarteListByWeekAndCafetoriumId(carteDto.getCafetoriumId(),
    // carteDto.getCarteWeek());
    // if (carteList != null) {
    // Map<String, List<CarteDto>> carteMap = new TreeMap<String, List<CarteDto>>();
    //
    // // 循环所有菜品进行菜品分类
    // for (Carte carte : carteList) {
    // // 判断菜品是否已经存在分类中,如果不存在添加产品分类,存在的话将菜品添加在原有分类集合中
    // if (carteMap.get(carte.getCarteTypeId()) == null) {
    //
    // //获取菜品投票数量
    // Integer
    // vote=nextMenuOperateSerivce.findNextWeekVoteByLocalWeekAndCarteNameAndCafetoriumId(localWeek,carte.getCarteName(),carteDto.getCafetoriumId());
    // carteDto.getCafetoriumId();
    // List<CarteDto> carteTemp = new ArrayList<>();
    // CarteDto carteVote=new CarteDto();
    // BeanUtils.copyProperties(carte, carteVote);
    // carteVote.setVote(vote);
    // carteTemp.add(carteVote);
    // carteMap.put(carte.getCarteTypeId(), carteTemp);
    //
    // } else {
    // //获取菜品投票数量
    // Integer
    // vote=nextMenuOperateSerivce.findNextWeekVoteByLocalWeekAndCarteNameAndCafetoriumId(localWeek,carte.getCarteName(),carteDto.getCafetoriumId());
    // CarteDto carteVote=new CarteDto();
    // BeanUtils.copyProperties(carte, carteVote);
    // carteVote.setVote(vote);
    // carteMap.get(carte.getCarteTypeId()).add(carteVote);
    // }
    // }
    //
    // List<CarteTypeDto> carteTypeList = new ArrayList<>();
    // // 遍历菜品分类,进行数据规整
    // for (String carteTypeId : carteMap.keySet()) {
    // CarteTypeDto carteTypeDto = iCarteTypeService
    // .findById(carteTypeId);
    // if (carteTypeDto != null) {
    // if (carteMap.get(carteTypeId) != null
    // && carteMap.get(carteTypeId).size() > 0) {
    // carteTypeDto.setCarteList(carteMap.get(carteTypeId));
    // carteTypeList.add(carteTypeDto);
    // }
    // }
    //
    // }
    //
    // //排序 add by 朱振
    // Collections.sort(carteTypeList, new Comparator<CarteTypeDto>() {
    // public int compare(CarteTypeDto arg0, CarteTypeDto arg1) {
    // return arg0.getCreateTime().compareTo(arg1.getCreateTime());
    // }
    // });
    //
    //
    // if (carteTypeList.size() < 1) {
    // response.setStatus(500);
    // response.setMessage("没有查询到菜单");
    // return response;
    // } else {
    // response.setStatus(200);
    // response.setMessage("success");
    // response.setData(carteTypeList);
    // return response;
    // }
    // } else {
    // response.setStatus(500);
    // response.setMessage("没有查询到菜单");
    // return response;
    // }
    //
    // }

    /**
     * 查询下周菜单
     * 
     * @author 朱振
     * @date 2015年10月10日 下午2:23:38
     * @version 1.0
     * @param cafetoriumId
     * @return <p>
     *         修改人：朱振
     *         </p>
     *         <p>
     *         修改时间：2015年10月10日 下午2:23:38
     *         </p>
     *         <p>
     *         修改备注：
     *         </p>
     */
    @RequestMapping("/list.do")
    @ResponseBody
    public Response<List<CarteTypeDto>> getNextMenuByCafetoriumId(String cafetoriumId)
    {
        Response<List<CarteTypeDto>> response = new Response<>();

        log.debug("参数cafetoriumId=" + cafetoriumId);

        if (!StringUtils.isEmpty(cafetoriumId))
        {
            List<CarteDto> nexMenuItems = carteService.getLastMenuByCafetoriumId(cafetoriumId);
            if (!CollectionUtils.isEmpty(nexMenuItems))
            {
                Map<String, List<CarteDto>> nextMenu = new HashMap<String, List<CarteDto>>();

                // 循环所有菜品进行菜品分类
                for (CarteDto menuItem : nexMenuItems)
                {
                    //不对空数据进行处理
                    if(menuItem == null)
                    {
                        continue;
                    }
                    
                    // 判断菜品是否已经存在分类中,如果不存在添加产品分类,存在的话将菜品添加在原有分类集合中
                    if (menuItem.getCarteTypeId() != null && nextMenu.get(menuItem.getCarteTypeId()) == null)
                    {
                        List<CarteDto> menuItems = new ArrayList<>();
                        menuItems.add(menuItem);
                        if(menuItem.getCarteTypeId() != null)
                        {
                            nextMenu.put(menuItem.getCarteTypeId(), menuItems);
                        }
                    }
                    else
                    {
                        if(menuItem.getCarteTypeId() != null && nextMenu.get(menuItem.getCarteTypeId()) != null)
                        {
                            nextMenu.get(menuItem.getCarteTypeId()).add(menuItem);
                        }
                    }
                }
                
                if(!MapUtils.isEmpty(nextMenu))
                {
                    List<CarteTypeDto> data = iCarteTypeService.finByCafetoriumId(cafetoriumId);
                    
                    //将查询出来的所有菜单（t_ctr_carte）的类别与食堂的菜单类别(t_ctr_carte_type)对比，将匹配的菜单放入到结果中
                    if(!CollectionUtils.isEmpty(data))
                    {
                        //遍历食堂的菜单类别(t_ctr_carte_type)
                        for(CarteTypeDto item: data)
                        {
                          //遍历查询出来的所有菜单（t_ctr_carte）的类别
                            for(String key : nextMenu.keySet())
                            {
                                if(item.getId() != null && item.getId().equals(key) && item!=null &&nextMenu.get(key) !=null)
                                {
                                   item.setCarteList(nextMenu.get(key));
                                }
                            }
                        }
                        
                        response.setStatus(HttpDataResponse.HTTP_SUCCESS);
                        response.setMessage("查询成功");
                        response.setData(data);
                        return response;
                    }
                    
                    log.debug("该食堂的菜单类型为空,cafetoriumId="+cafetoriumId);
                    
                    response.setStatus(HttpDataResponse.HTTP_SUCCESS);
                    response.setMessage("该食堂没有菜单类型");
                    return response;
                    
                }
                
                log.debug("该食堂没有菜单,cafetoriumId="+cafetoriumId);
                
                response.setStatus(HttpDataResponse.HTTP_FAILE);
                response.setMessage("该食堂没有菜单");
                return response;
                
            }

            log.debug("没有菜单的详细信息");
        }

        response.setStatus(HttpDataResponse.HTTP_FAILE);
        response.setMessage("查询失败");
        return response;
    }

    /**
     * 提交喜欢的下周菜单处理,通过存储的cook来判断是否提交过
     * 
     * @param suMessage 用户投票的菜单json
     * @param cafetoriumId 餐厅ID
     * 
     * @param userId 用户Id
     * @return
     */
    @RequestMapping("/sucarte")
    @ResponseBody
    public Response<List<NextWeekCarte>> suCarte(String suMessage, String cafetoriumId, String userId,
        HttpServletRequest request, HttpServletResponse response)
    {
        Response<List<NextWeekCarte>> r = new Response<List<NextWeekCarte>>();

        long s = new Date().getTime();

        // 判断用户是否在本周进行过评价
        // 获取当前周数
        int localWeek = DateUtil.localWeek(new Date()) + 1;
        // 获取本周菜品投票
        List<NextWeekCarte> is =
            nextMenuOperateSerivce.findNextWeekCarteByCarteWeekAndOpenIdAndCafetoriumId(localWeek,
                userId,
                cafetoriumId);
        // 如果投票部位空,表示本周已经进行过投票
        if (is != null && is.size() > 0)
        {
            r.setStatus(500);
            r.setMessage("每周只可以进行一次投票");
            return r;
        }

        // 进行下周菜单数据存储
        r = nextWeekCarteSort(suMessage, cafetoriumId, userId, r);
        // 进行投票
        if (r.getStatus() == 200)
        {
            List<NextWeekCarte> list = r.getData();
            for (NextWeekCarte nextWeekCarte : list)
            {
                nextMenuOperateSerivce.insertNextWeekCarte(nextWeekCarte);
            }

        }
        else
        {
            return r;
        }
        r.setStatus(200);
        r.setMessage("投票成功");

        long e = new Date().getTime();
        double n = (e - s) / 1000;
        System.out.println("本次操作使用" + (e - s) + "毫秒");
        System.out.println("本次操作使用" + n + "秒");

        return r;
    }

    /**
     * 对用户提交的投票菜单进行数据封装
     * 
     * @param suMessage 用户投票的菜单json
     * @param cafetoriumId 餐厅ID
     * @param userId 微信用户的唯一标识
     * @param r 用来封装数据的实体
     * @return
     */
    public Response<List<NextWeekCarte>> nextWeekCarteSort(String suMessage, String cafetoriumId,
        String userId, Response<List<NextWeekCarte>> r)
    {
        // 获取本周是今年的第几周
        String localWeek = (DateUtil.localWeek(new Date()) + 1) + "";

        String dayArr[] = {"七", "一", "二", "三", "四", "五", "六", };

        // 解析用户所选菜单json
        List<NextWeekCarte> carteArray = JSONArray.parseArray(suMessage, NextWeekCarte.class);
        if (carteArray == null || carteArray.size() == 0)
        {
            r.setStatus(500);
            r.setMessage("选择为空");
        }

        // 判断用户投票时间
        if ((DateUtil.dayOfWeek(new Date()) - 1) > 5)
        {
            r.setStatus(500);
            r.setMessage("只能在周一至周五进行投票");
        }

        // 获取下周时间
        Date date = DateUtils.dayOffset(new Date(), 7);

        // 将用户菜单进行分类
        Map<String, List<NextWeekCarte>> map = new HashMap<>();
        for (NextWeekCarte nextWeekCarte : carteArray)
        {
            if (map.get(nextWeekCarte.getCarteTypeId()) == null)
            {
                List<NextWeekCarte> list = new ArrayList<>();
                nextWeekCarte.setId(UUID.randomUUID() + "");
                nextWeekCarte.setCarteWeek(localWeek);
                nextWeekCarte.setCreateTime(new Date());
                nextWeekCarte.setStat(1);
                if (DateUtil.monthOfWeek(new Date()) != 0)
                {
                    nextWeekCarte.setCarteWeekDesc(DateUtils.format(date, "yyyy年M月") + "第"
                        + dayArr[DateUtil.monthOfWeek(date)] + "周");
                }
                nextWeekCarte.setCafetoriumId(cafetoriumId);

                nextWeekCarte.setOpenId(userId);
                list.add(nextWeekCarte);
                map.put(nextWeekCarte.getCarteTypeId(), list);
            }
            else
            {
                List<NextWeekCarte> list = map.get(nextWeekCarte.getCarteTypeId());
                nextWeekCarte.setId(UUID.randomUUID() + "");
                nextWeekCarte.setCarteWeek(localWeek);
                nextWeekCarte.setCreateTime(new Date());
                nextWeekCarte.setStat(1);
                if (DateUtil.monthOfWeek(new Date()) != 0)
                {
                    nextWeekCarte.setCarteWeekDesc(DateUtils.format(date, "yyyy年M月") + "第"
                        + dayArr[DateUtil.monthOfWeek(date)] + "周");
                }
                nextWeekCarte.setCafetoriumId(cafetoriumId);
                nextWeekCarte.setOpenId(userId);
                list.add(nextWeekCarte);
            }
        }

        // 定义用来存储所有正常的用户投票菜单
        List<NextWeekCarte> nextWeekCarteList = new ArrayList<>();

        // 判断用户所选菜单是否超出数量限制
        for (String carteTypeId : map.keySet())
        {
            CarteTypeDto CarteType = iCarteTypeService.findById(carteTypeId);
            if (map.get(carteTypeId) != null && CarteType.getUpperLimit() != null
                && map.get(carteTypeId).size() > Integer.parseInt(CarteType.getUpperLimit()))
            {
                r.setStatus(500);
                r.setMessage(CarteType.getCarteTypeName() + "选择不能大于" + CarteType.getUpperLimit() + "个");
                return r;
            }
            else
            {
                nextWeekCarteList.addAll(map.get(carteTypeId));
            }
        }
        r.setStatus(200);
        r.setMessage("分类成功");
        r.setData(nextWeekCarteList);
        return r;
    }

}
