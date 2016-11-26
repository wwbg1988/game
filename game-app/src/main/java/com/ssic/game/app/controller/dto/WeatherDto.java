/**
 * 
 */
package com.ssic.game.app.controller.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: WeatherDto </p>
 * <p>Description: 天气DTO</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博
 * @date 2015年10月6日 下午3:43:30	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年10月6日 下午3:43:30</p>
 * <p>修改备注：</p>
 */
@SuppressWarnings("serial")
public class WeatherDto implements Serializable
{
    /**   
     * 天氣所屬日期;  
     */
    @Getter
    @Setter
    private String weatherDate;
    /**   
     * 天气:(阴;晴)  
     */
    @Getter
    @Setter
    private String weather;
    /**   
     * 风向等级;  
     */
    @Getter
    @Setter
    private String wind;
    /**   
     * 日期所属周几(周一，周二等);  
     */
    @Getter
    @Setter
    private String dayOfWeek;
    /**   
     * 实时温度;  
     */
    @Getter
    @Setter
    private String nowTemperature;
}
