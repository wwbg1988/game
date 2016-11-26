/**
 * 
 */
package com.ssic.carteing;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ssic.game.app.controller.catering.NextCarteStatisticsController;
import com.ssic.game.app.controller.catering.OurOrderController;
import com.ssic.game.app.controller.dto.CafetoriumData;
import com.ssic.game.app.controller.dto.WeatherDto;
import com.ssic.game.app.controller.weixin.WeatherController;
import com.ssic.game.app.test.BaseTestCase;
import com.ssic.game.im.service.IQjyImService;
import com.ssic.shop.manage.dto.OrderDto;
import com.ssic.util.model.Response;

/**		
 * <p>Title: NextCarteStatisticsTest </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年8月8日 下午1:36:25	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年8月8日 下午1:36:25</p>
 * <p>修改备注：</p>
 */
public class NextCarteStatisticsTest extends BaseTestCase
{
    protected static final Log logger = LogFactory.getLog(NextCarteStatisticsTest.class);
    @Autowired
    private NextCarteStatisticsController nextCarteStatisticsController;
    @Autowired
    private WeatherController weatherController;
    @Autowired
    private OurOrderController ourOrderController;
    @Autowired
    private IQjyImService qjyImService;

    // @Test
    public void nextCarteStatisticsTest()
    {
        String userId = "81d5f62a-3898-4081-9ea3-7cb4fc38bfb0";
        String cafetoriumId = "10";
        String carteWeek = "35";
        String carteTypeId = "3";
        Response<List<CafetoriumData>> dataList = nextCarteStatisticsController.cafetoriumlist(userId, 1, 20);
        /*    Response<DishPercentDto> dataList =
               nextCarteStatisticsController.cartePercent(cafetoriumId, carteWeek);*/
        logger.info("输出信息:------------" + dataList.message);
    }

    @Test
    public void fidngroupTest()
    {
        String qjAccount = "qjy_Terry";
        System.out.println("-------开始查询radis------");
        qjyImService.findRadisGroup(qjAccount);
        //qjyImService.findNewGroups(qjAccount);
        System.out.println("--------结束查询radis--------");
        System.out.println("-------删除缓存开始------");
        qjyImService.deleteRadisGroup(qjAccount);
        System.out.println("-------删除缓存结束------");
        System.out.println("-------开始查询radis------");
        qjyImService.findRadisGroup(qjAccount);
        //qjyImService.findNewGroups(qjAccount);
        System.out.println("--------结束查询radis--------");

    }

    @Test
    public void getWeather()
    {
              Response<WeatherDto> dto = weatherController.getWeatherByCity("上海");
              logger.info(dto.message);
    /*    OrderDto order = new OrderDto();
        order.setOpenId("12");
        order.setId("075ed903-253a-486f-89ac-5e749f7ceaad");
        order.setIsCancelPay(1);
        ourOrderController.cancelPay(order);*/
    }

    public static void main(String[] args)
    {
        String week = "周二 10月06日 (实时：23℃)";
        String[] weekArry = week.split(" ");
        for (int i = 0; i < weekArry.length; i++)
        {
            // System.out.println("weekArry[i]:-----" + weekArry[i]);

        }
        String array = weekArry[2].substring(4, weekArry[2].length() - 1);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
        String strDate = df.format("2015-10-08T13:07:29.682Z");
        System.out.println("strDate:-----" + strDate);
        logger.info("strDate日志-----:-----" + strDate);

    }

}
