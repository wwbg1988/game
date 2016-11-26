/**
 * 
 */
package com.ssic.game.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.ssic.util.DateUtils;

/**
 * 		
 * <p>Title: DateUtil </p>
 * <p>Description: 周数计算</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author milkteaye
 * @date 2015年8月10日 上午11:01:52	
 * @version 1.0
 * <p>修改人：milkteaye</p>
 * <p>修改时间：2015年8月10日 上午11:01:52</p>
 * <p>修改备注：</p>
 */

public class DateUtil
{

    /**
     * 
     * localWeek：获取所传日期是今年的第几周
     * @param paramDate 日期
     * @return
     * @exception	
     * @author 张亚伟
     * @date 2015年8月10日 上午11:02:10
     */
    public static int localWeek(Date paramDate)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(paramDate);
        Date date2;
        try
        {
            date2 = sdf.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date2);
            // 一年中的第几周
            int week = calendar.get(Calendar.WEEK_OF_YEAR);

            //本月的第几周
            //int week = calendar.get(Calendar.WEEK_OF_MONTH);//获取是本月的第几周

            // 第几天，从周日开始
            // int day = calendar.get(Calendar.DAY_OF_WEEK);
            return week;
        }
        catch (ParseException e)
        {
        }
        return 0;
    }

    /**
     * monthOfWeek：获取当前日期是本月的第几周
     * @param paramDate 日期
     * @return
     * @exception	
     * @author 张亚伟
     * @date 2015年8月10日 上午11:03:47
     */
    public static int monthOfWeek(Date paramDate)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(paramDate);
        Date date2;
        try
        {
            date2 = sdf.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date2);
            // 一年中的第几周
            //int week = calendar.get(Calendar.WEEK_OF_YEAR);

            //本月的第几周
            int week = calendar.get(Calendar.WEEK_OF_MONTH);//获取是本月的第几周

            // 第几天，从周日开始
            // int day = calendar.get(Calendar.DAY_OF_WEEK);
            return week;
        }
        catch (ParseException e)
        {
        }
        return 0;

    }

    /**
     * dayOfWeek：获取当前日期是周的第几天
     * @param paramDate 日期
     * @return
     * @exception	
     * @author 张亚伟
     * @date 2015年8月10日 上午11:03:47
     */
    public static int dayOfWeek(Date paramDate)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(paramDate);
        Date date2;
        try
        {
            date2 = sdf.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date2);
            // 一年中的第几周
            //int week = calendar.get(Calendar.WEEK_OF_YEAR);

            //本月的第几周
            //int week = calendar.get(Calendar.WEEK_OF_MONTH);//获取是本月的第几周

            // 第几天，从周日开始
            int week = calendar.get(Calendar.DAY_OF_WEEK);
            return week;
        }
        catch (ParseException e)
        {
        }
        return 0;

    }

    public static void main(String[] args)
    {
        String dayArr[]={"七","一","二","三","四","五","六",};
        System.out.println("今天是本年的第"+(localWeek(new Date()))+"周");
        System.out.println("今天是本月的第"+(monthOfWeek(new Date()))+"周");
        System.out.println("今天是本周的第"+(dayOfWeek(new Date())-1)+"天");
        System.out.println(DateUtils.format(new Date(), "yyyy年M月")+"第"+dayArr[DateUtil.monthOfWeek(new Date())]+"周");
        Date localDate = getSpecifiedDayBefore("2015-08-12");
        String dayAfter= new SimpleDateFormat("yyyy-MM-dd").format(localDate);
        System.out.println("-----明天日期---------:"+dayAfter);
    }

    /** 
    * 获得指定日期的下一天 
    * @param specifiedDay 
    * @return 
    * @throws Exception 
    */
    public static Date getSpecifiedDayBefore(String specifiedDay)
    {
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
        Calendar c = Calendar.getInstance();
        Date date = null;
        try
        {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day +1);

        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return c.getTime();
    }
    /** 
    * 获得指定日期的下一天 
    * @param specifiedDay 
    * @return 
    * @throws Exception 
    */
    public static Date getSpecifiedDayBeforeWeek(String specifiedDay)
    {
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
        Calendar c = Calendar.getInstance();
        Date date = null;
        try
        {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day -7);

        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return c.getTime();
    }
}
