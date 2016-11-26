package com.ssic.catering.base.service;

import java.util.List;

import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.dto.SensitiveDto;
import com.ssic.catering.base.pojo.Sensitive;
/**
 * 		
 * <p>Title: ISensitiveService </p>
 * <p>Description: 敏感词操作逻辑层接口</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 张亚伟	
 * @date 2015年8月11日 下午9:58:09	
 * @version 1.0
 * <p>修改人：张亚伟</p>
 * <p>修改时间：2015年8月11日 下午9:58:09</p>
 * <p>修改备注：</p>
 */
public interface ISensitiveService {

	/**
	 * 
	 * getSensitiveById：通过ID获取敏感词列表
	 * @param id 主键Id
	 * @return
	 * @exception	
	 * @author 张亚伟
	 * @date 2015年8月11日 下午9:58:31
	 */
	public Sensitive getSensitiveById(String id);

    

    /**     
     * delete：一句话描述方法功能
     * @param tempSensitive
     * @exception	
     * @author 刘博
     * @date 2015年8月14日 上午10:15:36	 
     */
    public void delete(Sensitive tempSensitive);


    
    /**     
     * finByName：一句话描述方法功能
     * @param sensitiveName
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年8月14日 上午10:15:55	 
     */
    public SensitiveDto finByName(String sensitiveName,String cafetoriumId);


    
    /**     
     * add：一句话描述方法功能
     * @param sensitiveDto
     * @exception	
     * @author 刘博
     * @date 2015年8月14日 上午10:16:07	 
     */
    public void add(SensitiveDto sensitiveDto);


    
    /**     
     * findALL：一句话描述方法功能
     * @param sensitiveDto
     * @param phDto
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年8月14日 上午10:17:39	 
     */
    public List<SensitiveDto> findALL(SensitiveDto sensitiveDto, PageHelperDto phDto,List<String> listStr);


    
    /**     
     * findCount：一句话描述方法功能
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年8月14日 上午10:17:50	 
     */
    public int findCount(SensitiveDto sensitiveDto,List<String> listStr);



    
    /**     
     * update：一句话描述方法功能
     * @param sensitiveDto
     * @exception	
     * @author 刘博
     * @date 2015年8月14日 上午11:04:20	 
     */
    public void update(SensitiveDto sensitiveDto);
}

