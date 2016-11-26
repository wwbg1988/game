/**
 * 
 */
package com.ssic.game.app.controller.weixin;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.aspectj.bridge.Message;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.game.app.controller.dto.WeatherDto;
import com.ssic.game.common.constant.WeatherConstants;
import com.ssic.util.constants.DataStatus;
import com.ssic.util.model.Response;

/**		
 * <p>Title: WeatherController </p>
 * <p>Description:微信天气接口调用</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博
 * @date 2015年10月6日 下午3:40:13	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年10月6日 下午3:40:13</p>
 * <p>修改备注：</p>
 */
@RequestMapping("/weather")
@Controller
public class WeatherController
{

    /**
     * 根据用户所在城市获取用户天气
     * city：用户所在城市
     * @param json (格式:{type:选择类型,cafetoriumId:食堂ID})
     * @param request
     * @return
     * @exception   
     * @author 刘博
     * @date 2015年10月06日 下午3:45:58
     */
    @RequestMapping("/getWeatherByCity")
    @ResponseBody
    public Response<WeatherDto> getWeatherByCity(String city)
    {
        //百度天气url: http://api.map.baidu.com/telematics/v3/weather?output=json&ak=7d4f25ba814088e01c850f47f91c40e1&location=%E4%B8%8A%E6%B5%B7
        Response<WeatherDto> r = new Response<WeatherDto>();
        WeatherDto weatherDto = new WeatherDto();
        if (StringUtils.isEmpty(city))
        {
            r.setData(weatherDto);
            r.setMessage("返回天气失败，地理位置获取失败!");
            r.setStatus(DataStatus.HTTP_FAILE);
        }
        //天气url拼接
        String weatherUrl = WeatherConstants.WEATHER_URL + "&location=" + city;
        //用HttpClient发送请求，分为五步
        //第一步：创建HttpClient对象
        HttpClient httpCient = new DefaultHttpClient();
        //第二步：创建代表请求的对象,参数是访问的服务器地址
        HttpGet httpGet = new HttpGet(weatherUrl);
        try
        {
            //第三步：执行请求，获取服务器发还的相应对象
            HttpResponse httpResponse = httpCient.execute(httpGet);
            //第四步：检查相应的状态是否正常：检查状态码的值是200表示正常
            if (httpResponse.getStatusLine().getStatusCode() == 200)
            {
                //第五步：从相应对象当中取出数据，放到entity当中
                HttpEntity entity = httpResponse.getEntity();
                String response = EntityUtils.toString(entity, "utf-8");//将entity当中的数据转换为字符串
                String aa = response.toString();
                JSONObject jb = JSONObject.fromObject(aa);
                Object results = jb.get("results");

                if (results != null)
                {
                    JSONArray jsArray = JSONArray.fromObject(results);
                    if (jsArray.size() > 0)
                    {
                        JSONObject jbs = JSONObject.fromObject(jsArray.get(0));
                        if (jbs != null)
                        {
                            Object weatherData = jbs.get("weather_data");

                            JSONArray weatherDataArray = JSONArray.fromObject(weatherData);
                            if (weatherDataArray.size() > 0)
                            {
                                JSONObject weatherDataObject = JSONObject.fromObject(weatherDataArray.get(0));
                                if (weatherDataObject != null)
                                {
                                    //日期以及实时温度  周二 10月06日 (实时：23℃)
                                    String weather_date = weatherDataObject.getString("date");
                                    if (!StringUtils.isEmpty(weather_date))
                                    {
                                        weatherDto.setWeatherDate(weather_date);

                                        String[] weekArry = weather_date.split(" ");
                                        if (weekArry.length > 0)
                                        {
                                            //当前日期 如:10月06号
                                            weatherDto.setWeatherDate(weekArry[1]);
                                            //一周的第几天:如:周二
                                            weatherDto.setDayOfWeek(weekArry[0]);
                                            String temperature =
                                                weekArry[2].substring(4, weekArry[2].length() - 1);
                                            if (!StringUtils.isEmpty(temperature))
                                            {
                                                //当前温度:35℃
                                                weatherDto.setNowTemperature(temperature);
                                            }

                                        }
                                    }
                                    //天气
                                    String weather = weatherDataObject.getString("weather");
                                    if (!StringUtils.isEmpty(weather))
                                    { //当前天气状况:阴
                                        weatherDto.setWeather(weather);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        r.setData(weatherDto);
        r.setMessage("返回天气成功!");
        r.setStatus(DataStatus.HTTP_SUCCESS);
        return r;
    }
}
