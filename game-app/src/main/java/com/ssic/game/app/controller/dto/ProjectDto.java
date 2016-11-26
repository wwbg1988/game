/**
 * 
 */
package com.ssic.game.app.controller.dto;

import java.io.Serializable;
import java.util.List;

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
public class ProjectDto implements Serializable
{

    /**   
     * id: 项目ID     
     */
    @Getter
    @Setter
    private String id;
    /**   
    * proj_name: 项目名称     
    */
    @Getter
    @Setter
    private String projName;
    
    @Getter
    @Setter
    private String deptId;
    
    @Getter
    @Setter
    private String deptName;

//    @Getter
//    @Setter
//    private List<ProcessDto> processList;

}
