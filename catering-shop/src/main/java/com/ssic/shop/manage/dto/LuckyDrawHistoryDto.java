/**
 * 
 */
package com.ssic.shop.manage.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: LuckyDrawHistoryDto </p>
 * <p>Description: 抽奖历史记录Dto</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年9月22日 下午4:20:50	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年9月22日 下午4:20:50</p>
 * <p>修改备注：</p>
 */
public class LuckyDrawHistoryDto implements Serializable
{

    /**   
    * serialVersionUID: （一句话描述这个变量表示什么）      
    */

    private static final long serialVersionUID = -3296834285811134159L;

    @Getter
    @Setter
    private String id;

    /**
     * 微信用户唯一标识id
     */
    @Getter
    @Setter
    private String openId;

    /**
     *是否当天抽奖:1:是;0:否
     */
    @Getter
    @Setter
    private Integer isNowLuckyDraw;

    /**
     * 总评论条数
     */
    @Getter
    @Setter
    private Integer totalCommentCount;

    /**
     * 总的抽奖次数
     */
    @Getter
    @Setter
    private Integer totalLuckyCount;

    /**
     * 创建时间
     */
    @Getter
    @Setter
    private Date createTime;

    /**
     *更新时间
     */
    @Getter
    @Setter
    private Date lastUpdateTime;

    /**
     *是否中奖:1:是;0:否
     */
    @Getter
    @Setter
    private Integer isLottery;

    /**
     *中奖时间
     */
    @Getter
    @Setter
    private Date lotteryTime;

    /**
     *是否兑换:1:是;0:否
     */
    @Getter
    @Setter
    private Integer isExchange;

    /**
     *兑换时间
     */
    @Getter
    @Setter
    private Date exchangeTime;
    /**
     *兑换奖品id
     */
    @Getter
    @Setter
    private String drawId;

    /**
     *兑换手机号码
     */
    @Getter
    @Setter
    private String exchargePhone;
    /**
     * 是否有效
     */
    @Getter
    @Setter
    private Integer stat;

    /**
     *食堂id
     */
    @Getter
    @Setter
    private String cafetoriumId;

}
