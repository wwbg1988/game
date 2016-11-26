/**
 * 
 */
package com.ssic.game.app.controller.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: UuserInfoDto </p>
 * <p>Description: 用户描述类</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Vincent	
 * @date 2015年6月24日 下午8:13:14	
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ProcessDto implements Serializable
{
    /**   
     * id: 流程ID      
     */
     @Getter
     @Setter
     private String id;

    /**   
    * projId: 所属项目ID      
    */
    @Getter
    @Setter
    private String projId;
    /**   
    * proj_name: 流程名称     
    */
    @Getter
    @Setter
    private String procName;

    /**   
    * img_url: 流程模块图片
    */
    @Getter
    @Setter
    private String imgUrl;

    /**   
    * start_task: 流程开始节点
    */
    @Getter
    @Setter
    private String startTask;

    /**   
     * state: 流程状态 1未开始 2赛中进行 3 已结束     
     */
    @Getter
    @Setter
    private int state;

}
