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
public class UserInfoDto implements Serializable
{

    /**   
     * name: 用户ID(UUID)      
     */
    @Getter
    @Setter
    private String userId;
    /**   
    * name: 用户姓名      
    */
    @Getter
    @Setter
    private String name;
    /**   
     * userAccount: 用户登录帐号
     */
    @Getter
    @Setter
    private String userAccount;

    /**   
    * password: 用户密码
    */
    @Getter
    @Setter
    private String password;

    /**   
    * qjyAccount: 亲加云帐号     
    */
    @Getter
    @Setter
    private String qjyAccount;

    /**   
    * email: 用户邮箱      
    */
    @Getter
    @Setter
    private String email;

    /**   
    * userImage: 用户头像
    */
    @Getter
    @Setter
    private String userImage;

    @Getter
    @Setter
    private List<ProjectDto> projList;

}
