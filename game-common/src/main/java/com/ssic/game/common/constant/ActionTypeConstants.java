/**
 * 
 */
package com.ssic.game.common.constant;

/**		
 * <p>Title: ActionTypeConstants </p>
 * <p>Description: 用户处理动作类型定义</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年7月6日 下午8:43:43	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年7月6日 下午8:43:43</p>
 * <p>修改备注：</p>
 */
public interface ActionTypeConstants
{

    static final Integer ACTION_TTPE_ADD = 1;//action动作类型：新增
    static final Integer ACTION_TTPE_UPDATE = 2;//action动作类型：更新
    static final Integer ACTION_TTPE_REVERT = 3;//action动作类型：退回
    static final Integer ACTION_TTPE_CUSTOM = 4;//action动作类型：自定义
    static final Integer ACTION_TTPE_PASS= 5;   //action动作类型：审核通过
    static final Integer ACTION_TTPE_REFUSE= 6; //action动作类型：审核拒绝
    static final Integer ACTION_TYPE_NO_CHECKED = 7; //action动作类型：未审核
}
