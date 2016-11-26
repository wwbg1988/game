/**
 * 
 */
package com.ssic.game.app.controller.weixin;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.game.common.util.HttpDataResponse;
import com.ssic.shop.manage.dto.CarteUserDto;
import com.ssic.shop.manage.dto.LuckyDrawDto;
import com.ssic.shop.manage.dto.LuckyDrawHistoryDto;
import com.ssic.shop.manage.service.ICarteUserService;
import com.ssic.shop.manage.service.LuckDrawService;
import com.ssic.shop.manage.service.LuckyDrawHistoryService;
import com.ssic.util.DateUtils;
import com.ssic.util.constants.DataStatus;
import com.ssic.util.model.Response;

/**		
 * <p>Title: LuckDrawController </p>
 * <p>Description: 转盘抽奖controller</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年9月21日 下午3:02:56	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年9月21日 下午3:02:56</p>
 * <p>修改备注：</p>
 */
@RequestMapping("/luckDraw")
@Controller
public class LuckDrawController
{
    @Autowired
    private LuckDrawService luckDrawService;
    @Autowired
    private LuckyDrawHistoryService luckyDrawHistoryService;
    @Autowired
    private ICarteUserService carteUserService;

    /**     
     * getPonitsAngles：获取转盘指针
     * @param openId 用户id
     * @return
     * @exception   
     * @author 刘博
     * @date 2015年6月25日 下午3:47:41    
     */
    @RequestMapping(value = "/getPonitsAngles.do", method = {RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Response<LuckyDrawDto> getPonitsAngles(String openId, String cafetoriumId)
    {
        Response<LuckyDrawDto> result = new Response<LuckyDrawDto>();
        if (StringUtils.isEmpty(openId) || StringUtils.isEmpty(cafetoriumId))
        {
            result.setData(null);
            result.setStatus(DataStatus.HTTP_FAILE);
            result.setMessage("用户openId或食堂id不存在");
        }
        LuckyDrawHistoryDto historyDto = null;
        //根据微信用户唯一标识id获取当前用户抽奖历史记录;
        List<LuckyDrawHistoryDto> historyList =
            luckyDrawHistoryService.findLuckDrawHistoryByOpenIdAndCafeId(openId, cafetoriumId);
        if (!CollectionUtils.isEmpty(historyList))
        {
            historyDto = historyList.get(0);

        }

        //LuckyDrawDto luckDraw = luckDrawService.findLuckDrawById("1");
        List<LuckyDrawDto> luckDraws = luckDrawService.findByCafetoriumId(cafetoriumId);
        if (!CollectionUtils.isEmpty(luckDraws))
        {
            LuckyDrawDto luckDraw = luckDraws.get(0);
            Integer chances = luckDraw.getPrizeChances();
            int pointsNumber = getPointsNumber(chances, luckDraw.getPrizeCount(), openId);
            //当天时间
            String now_date = DateUtils.format(new Date(), "yyyy-MM-dd");
            if (luckDraw != null)
            {
                if (historyDto != null)
                {
                    String history_Date = DateUtils.format(historyDto.getLastUpdateTime(), "yyyy-MM-dd");
                    //查找有没有当天的抽奖记录,如果有,且当天抽奖字段(isNowLuckyDraw)的标识为1;则返回已经抽奖;
                    if (!StringUtils.isEmpty(history_Date) && history_Date.equals(now_date))
                    {
                        luckDraw.setIsNowLuckyDraw(historyDto.getIsNowLuckyDraw());
                    }
                    else
                    {//如果当天没有抽奖，则放入标识：0(未抽奖)
                        luckDraw.setIsNowLuckyDraw(DataStatus.DISABLED);
                    }

                    //放入最新的一条抽奖记录的总评论条数;
                    luckDraw.setTotalCommentCount(historyDto.getTotalCommentCount());
                    //放入最新的一条抽奖总条数;
                    luckDraw.setTotalLuckyCount(historyDto.getTotalLuckyCount());
                    luckDraw.setPoints(pointsNumber);
                    result.setData(luckDraw);
                    result.setStatus(DataStatus.HTTP_SUCCESS);
                    result.setMessage("返回信息成功");
                }
                else
                {
                    //放入最新的一条抽奖记录的总评论条数;
                    luckDraw.setTotalCommentCount(0);
                    //放入最新的一条抽奖总条数;
                    luckDraw.setTotalLuckyCount(1);
                    luckDraw.setPoints(pointsNumber);
                    result.setData(luckDraw);
                    result.setStatus(DataStatus.HTTP_SUCCESS);
                    result.setMessage("返回信息成功");
                }

            }
        }
        return result;
    }

    /**     
     * updateRestCount：更新用户抽奖剩余次数
     * @param openId 用户id
     * @return
     * @exception   
     * @author 刘博
     * @date 2015年6月25日 下午3:47:41    
     */
    @RequestMapping(value = "/updateRestCount.do", method = {RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public void updateRestCount(String openId, String cafetoriumId)
    {
        List<LuckyDrawHistoryDto> historyList =
            luckyDrawHistoryService.findLuckDrawHistoryByOpenIdAndCafeId(openId, cafetoriumId);
        if (!CollectionUtils.isEmpty(historyList))
        {
            LuckyDrawHistoryDto dto = historyList.get(0);
            //是否抽奖表示更新为:1（当天已抽奖）
            dto.setIsNowLuckyDraw(DataStatus.ENABLED);
            //更新总的剩余抽奖次数;
            dto.setTotalLuckyCount(dto.getTotalLuckyCount() - 1);
            dto.setLastUpdateTime(new Date());
            dto.setLotteryTime(new Date());
            luckyDrawHistoryService.upateLuckyDrawHistory(dto);
        }

    }

    /**     
     * updateLottery：转盘中奖：更新抽奖历史记录表的中奖字段为1
     * @param openId 用户id
     * @param drawId 兑换记录id
     * @return
     * @exception   
     * @author 刘博
     * @date 2015年6月25日 下午3:47:41    
     */
    @RequestMapping(value = "/updateLottery.do", method = {RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Response<String> updateLottery(String openId, String drawId, String cafetoriumId)
    {

        Response<String> result = new Response<>();

        if (StringUtils.isEmpty(openId) || StringUtils.isEmpty(drawId) || StringUtils.isEmpty(cafetoriumId))
        {
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("微信标识或兑换奖品或食堂id为空");
            return result;
        }
        List<LuckyDrawHistoryDto> historyList =
            luckyDrawHistoryService.findLuckDrawHistoryByOpenIdAndCafeId(openId, cafetoriumId);
        if (!CollectionUtils.isEmpty(historyList))
        {
            LuckyDrawHistoryDto dto = historyList.get(0);
            if (dto.getIsExchange() == 1)
            {//如果最新的抽奖记录已经兑换过，则需要创建一条新的抽奖记录
                LuckyDrawHistoryDto historyDto = new LuckyDrawHistoryDto();
                historyDto.setId(UUID.randomUUID().toString());
                historyDto.setOpenId(openId);
                historyDto.setTotalCommentCount(dto.getTotalCommentCount());
                historyDto.setTotalLuckyCount(dto.getTotalLuckyCount() - 1);
                //是否中奖更新为1(中奖)
                historyDto.setIsLottery(DataStatus.ENABLED);
                historyDto.setCreateTime(new Date());
                //更新中奖时间
                historyDto.setLotteryTime(new Date());
                historyDto.setLastUpdateTime(new Date());
                //是否兑换：否
                historyDto.setIsExchange(DataStatus.DISABLED);
                historyDto.setStat(DataStatus.ENABLED);
                //更新兑换奖品id
                historyDto.setDrawId(drawId);
                //是否抽奖表示更新为:1（当天已抽奖）
                historyDto.setIsNowLuckyDraw(DataStatus.ENABLED);
                luckyDrawHistoryService.insertLuckyDrawHistory(historyDto);
            }
            else
            {//如果最新的抽奖记录没有兑换过,则为当天第一次中奖,更新这条记录数据即可
             //更新时间
                dto.setLastUpdateTime(new Date());
                //是否中奖更新为1(中奖)
                dto.setIsLottery(DataStatus.ENABLED);
                //更新中奖时间
                dto.setLotteryTime(new Date());
                //更新兑换奖品id
                dto.setDrawId(drawId);
                //是否兑换：否
                dto.setIsExchange(DataStatus.DISABLED);
                //更新剩余抽奖次数
                dto.setTotalLuckyCount(dto.getTotalLuckyCount() - 1);
                //是否抽奖表示更新为:1（当天已抽奖）
                dto.setIsNowLuckyDraw(DataStatus.ENABLED);
                luckyDrawHistoryService.upateLuckyDrawHistory(dto);
            }
            result.setStatus(HttpDataResponse.HTTP_SUCCESS);
            result.setMessage("更新中奖记录成功");
            return result;
        }

        result.setStatus(HttpDataResponse.HTTP_FAILE);
        result.setMessage("未找到该抽奖记录");
        return result;

    }

    /**     
     * updateIsExchange：抽奖兑换字段更新
     * @param openId 用户id
     * @param drawId 兑换记录id
     * @return
     * @exception   
     * @author 刘博
     * @date 2015年6月25日 下午3:47:41    
     */
    @RequestMapping(value = "/updateIsExchange.do", method = {RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Response<String> updateIsExchange(LuckyDrawHistoryDto luckyDrawHistoryDto)
    {

        Response<String> result = new Response<>();
        if (StringUtils.isEmpty(luckyDrawHistoryDto.getOpenId())
            || StringUtils.isEmpty(luckyDrawHistoryDto.getCafetoriumId()))
        {
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("微信标识或食堂id为空");
            return result;
        }
        //更新兑换表的奖品数量
        List<LuckyDrawDto> luckDtos =
            luckDrawService.findByCafetoriumId(luckyDrawHistoryDto.getCafetoriumId());
        if (!CollectionUtils.isEmpty(luckDtos))
        {
            LuckyDrawDto luckDto = luckDtos.get(0);

            if (luckDto != null && luckDto.getPrizeChances() > 0)
            {//更新兑换奖品数量—1
                luckDto.setPrizeCount(luckDto.getPrizeCount() - 1);
                luckDrawService.updateLuckDraw(luckDto);
            }
            else
            {
                result.setStatus(HttpDataResponse.HTTP_FAILE);
                result.setMessage("兑换奖品不存在或库存不足");
                return result;
            }
        }
        else
        {
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("该食堂兑换奖品不存在");
            return result;
        }

        List<LuckyDrawHistoryDto> historyList =
            luckyDrawHistoryService.findLuckDrawHistoryByOpenIdAndCafeId(luckyDrawHistoryDto.getOpenId(),
                luckyDrawHistoryDto.getCafetoriumId());
        if (!CollectionUtils.isEmpty(historyList))
        {
            LuckyDrawHistoryDto dto = historyList.get(0);
            //更新时间
            dto.setLastUpdateTime(new Date());
            //兑换时间
            dto.setExchangeTime(new Date());
            //是否兑换：是
            dto.setIsExchange(DataStatus.ENABLED);
            //更新兑换手机号码
            dto.setExchargePhone(luckyDrawHistoryDto.getExchargePhone());
            //更新抽奖记录;
            luckyDrawHistoryService.upateLuckyDrawHistory(dto);

            result.setStatus(HttpDataResponse.HTTP_SUCCESS);
            result.setMessage("更新兑换字段成功");
            return result;
        }

        result.setStatus(HttpDataResponse.HTTP_FAILE);
        result.setMessage("未找到该抽奖记录");
        return result;

    }

    /**     
     * initTotalLuckyCount：抽奖页面初始化剩余抽奖次数
     * @param openId 用户id
     * @return
     * @exception   
     * @author 刘博
     * @date 2015年6月25日 下午3:47:41    
     */
    @RequestMapping(value = "/initTotalLuckyCount.do", method = {RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Response<LuckyDrawDto> initTotalLuckyCount(String openId, String cafetoriumId)
    {
        Response<LuckyDrawDto> result = new Response<LuckyDrawDto>();
        if (StringUtils.isEmpty(openId) || StringUtils.isEmpty(cafetoriumId))
        {
            result.setData(null);
            result.setStatus(DataStatus.HTTP_FAILE);
            result.setMessage("用户openId或食堂id不存在");
        }
        LuckyDrawDto luckDraw = new LuckyDrawDto();
        List<LuckyDrawHistoryDto> historyList =
            luckyDrawHistoryService.findLuckDrawHistoryByOpenIdAndCafeId(openId, cafetoriumId);
        if (!CollectionUtils.isEmpty(historyList))
        {
            LuckyDrawHistoryDto dto = historyList.get(0);
            if (dto != null)
            {
                luckDraw.setTotalLuckyCount(dto.getTotalLuckyCount());
            }
            else
            {
                luckDraw.setTotalLuckyCount(0);
            }

        }

        result.setData(luckDraw);
        result.setStatus(DataStatus.HTTP_SUCCESS);
        result.setMessage("返回信息成功");
        return result;
    }

    /**     
     * getPonitsAngles：获取转盘指针
     * @param userId 用户id
     * @return
     * @exception   
     * @author 刘博
     * @date 2015年6月25日 下午3:47:41    
     */
    @RequestMapping(value = "/getLuckyDrawHistoryList.do", method = {RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Response<List<LuckyDrawHistoryDto>> getLuckyDrawHistoryList(String openId, String cafetoriumId)
    {
        Response<List<LuckyDrawHistoryDto>> result = new Response<List<LuckyDrawHistoryDto>>();
        result.setData(null);
        result.setStatus(DataStatus.HTTP_FAILE);
        result.setMessage("返回信息不存在");
        List<LuckyDrawHistoryDto> luckyDrawHistoryDtoList =
            luckyDrawHistoryService.findLuckDrawHistoryByOpenIdAndCafeId(openId, cafetoriumId);

        if (CollectionUtils.isEmpty(luckyDrawHistoryDtoList))
        {

            result.setData(luckyDrawHistoryDtoList);
            result.setStatus(DataStatus.HTTP_SUCCESS);
            result.setMessage("返回信息成功");
        }
        return result;
    }

    private int getPointsNumber(Integer prizeChances, Integer count, String openId)
    {
        if (prizeChances != 0)
        {
            int prizeCount = 100 / prizeChances; //    1/5   
            int arr[] = new int[prizeCount];
            for (int i = 1; i <= prizeCount; i++)
            {
                arr[i - 1] = i;
            }

            int i = (int) ((arr.length) * Math.random());
            //如果数量小于1,则直接返回谢谢参与;
            if (count < 1)
            {

                return 230;
            }
            if (i == 0)
            {//0为：10元话费
                return 300;
            }
            else if (i == 1)
            {
                //更新积分
                //updateOrSaveUserIntegral(openId);
                //1:谢谢参与
                return 150;
            }
            else if (i == 2)
            {
                //更新积分
                // updateOrSaveUserIntegral(openId);
                //2:谢谢参与
                return 230;
            }
            else
            {
                //更新积分
                //updateOrSaveUserIntegral(openId);
                //谢谢参与
                return 70;
            }
        }
        else
        {
            //2:谢谢参与
            return 230;
        }
    }

    @RequestMapping(value = "/updateOrSaveUserIntegral.do", method = {RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public void updateOrSaveUserIntegral(String openId, String cafetoriumId)
    {
        CarteUserDto user = carteUserService.findByWxUniqueIdAndCafetoriumId(openId, cafetoriumId);
        if (user != null)
        {
            //没有抽中奖,积分+1
            user.setIntegral(user.getIntegral() + 1);
            carteUserService.updateCarteUser(user);
        }
        else
        {//新增一条记录
            CarteUserDto userDto = new CarteUserDto();
            userDto.setCreateTime(new Date());
            userDto.setLastUpdateTime(new Date());
            userDto.setOpenId(openId);
            userDto.setStat(DataStatus.ENABLED);
            //没有抽中奖，则积分+1
            userDto.setIntegral(1);
            userDto.setCafetoriumId(cafetoriumId);
            carteUserService.insertCarteUser(userDto);
        }
    }
}
