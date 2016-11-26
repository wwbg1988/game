/**
 * 
 */
package com.ssic.game.ims.model;

import java.io.Serializable;
import java.util.List;

import com.ssic.game.common.dto.DeptUsersDto;

import lombok.Getter;
import lombok.Setter;


/**		
 * <p>Title: DeptListDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author milkteaye	
 * @date 2015年8月18日 下午2:00:53	
 * @version 1.0
 * <p>修改人：milkteaye</p>
 * <p>修改时间：2015年8月18日 下午2:00:53</p>
 * <p>修改备注：</p>
 */
public class DeptListDto implements Serializable
{
    @Getter
    @Setter
    private List<LoadCompletionDto> loadCompletionList;
    
    @Getter
    @Setter
    private List<DeptUsersDto> deptUsersDtoList;
    
}

