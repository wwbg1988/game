/**
 * 
 */
package com.ssic.shop.manage.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: LuckDrawDto </p>
 * <p>Description:转盘抽奖dto</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年9月21日 下午2:40:03	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年9月21日 下午2:40:03</p>
 * <p>修改备注：</p>
 */
public class LuckyDrawDto implements Serializable
{

    /**   
    * serialVersionUID: （一句话描述这个变量表示什么）      
    */

    private static final long serialVersionUID = -4380041258858087303L;

    @Getter
    @Setter
    private String id;

    /**
     * 奖品名称
     */
    @Getter
    @Setter
    private String prizeName;

    /**
     * 奖品数量
     */
    @Getter
    @Setter
    private Integer prizeCount;

    /**
     * 抽中概率
     */
    @Getter
    @Setter
    private Integer prizeChances;

    /**
     * 奖品图片
     */
    @Getter
    @Setter
    private String imageUrl;

    /**
     * 创建时间
     */
    @Getter
    @Setter
    private Date createTime;

    /**
     * 更新时间
     */
    @Getter
    @Setter
    private Date lastUpdateTime;

    /**
     * 指针
     */
    @Getter
    @Setter
    private Integer points;

    /**
     * 状态
     */
    @Getter
    @Setter
    private Integer stat;

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
     *是否当天抽奖:1:是;0:否
     */
    @Getter
    @Setter
    private Integer isNowLuckyDraw;

    /**
     *食堂id
     */
    @Getter
    @Setter
    private String cafetoriumId;

}
