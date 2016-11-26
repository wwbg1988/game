/**
 * 
 */
package com.ssic.catering.base.service;

import java.util.List;

import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.dto.SensitiveValveConfDto;
import com.ssic.catering.base.pojo.SensitiveValveConf;

/**		
 * <p>Title: SensitiveValveConfService </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author pengcheng.yang	
 * @date 2015年8月18日 上午10:35:48	
 * @version 1.0
 * <p>修改人：pengcheng.yang</p>
 * <p>修改时间：2015年8月18日 上午10:35:48</p>
 * <p>修改备注：</p>
 */
public interface ISensitiveValveConfService
{
    public List<SensitiveValveConfDto> findBy(SensitiveValveConfDto param,PageHelperDto ph,List<String> listStr);
    public int findCountBy(List<String> listStr);
    
    public int sensitiveVconfAdd(SensitiveValveConfDto conf);
    
    public int sensitiveVconfDelete(SensitiveValveConf conf);
    
    public SensitiveValveConf getSensitiveConfById(String id);
    
    public int sensitiveVconfUpdate(SensitiveValveConfDto conf);
}

