/**
 * 
 */
package com.ssic.game.ims.model;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**		
 * <p>Title: TaskData </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author rkzhang	
 * @date 2015年6月25日 上午9:05:02	
 * @version 1.0
 * <p>修改人：rkzhang</p>
 * <p>修改时间：2015年6月25日 上午9:05:02</p>
 * <p>修改备注：</p>
 */
@ToString
public class TaskData
{

    /**   
    * id: 任务节点唯一标示
    */
    @Getter
    @Setter
    private String id;

    /**   
    * name: 任务节点名称   
    */
    @Getter
    @Setter
    private String name;

    /**   
    * projId: 所属项目ID
    */
    @Getter
    @Setter
    private String projId;
    /**
     * userId
     */
    @Getter
    @Setter
    private String userId;

    /**   
    * procInstanceId: 所属项目流程实例ID
    */
    @Getter
    @Setter
    private String procInstanceId;

    /**   
    * type: 1开始节点 2任务节点 3结束节点
    */
    @Getter
    @Setter
    private Integer type;

    /**   
    * state: 节点状态 0—未激活  1—办理中(激活) 2—已办 3-否决 4-退回 前驱节点已办后当前节点才进入激活状态用户可见
    */
    @Getter
    @Setter
    private Integer state;

    @Getter
    @Setter
    private String formId;

    /**   
    * fields: 表单数据集合, 每一个Field对象是表单的一个控件描述及其值 
    */
    @Getter
    @Setter
    private List<Field> fields;

    /**
     * 会签人员列表
     */
    @Getter
    @Setter
    private List<CountSignUser> countList;

    /**   
    * actions: 动作属性
    */
    @Getter
    @Setter
    private List<Action> actions;

    //最后修改时间
    @Getter
    @Setter
    private Date lastUpdateTime;

    @Getter
    @Setter
    private String userName;

    //指派审批人id
    @Getter
    @Setter
    private String assignUserId;

}
