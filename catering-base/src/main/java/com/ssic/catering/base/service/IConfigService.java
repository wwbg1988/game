package com.ssic.catering.base.service;

import java.util.List;

import com.ssic.catering.base.dto.ConfigDto;
import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.dto.Tree;
import com.ssic.catering.base.pojo.Config;
/**
 * 		
 * <p>Title: IConfigService </p>
 * <p>Description: 食堂评分项逻辑层接口</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 张亚伟	
 * @date 2015年8月11日 下午7:33:42	
 * @version 1.0
 * <p>修改人：张亚伟</p>
 * <p>修改时间：2015年8月11日 下午7:33:42</p>
 * <p>修改备注：</p>
 */
public interface IConfigService {

	/**
	 * 
	 * findConfigToId：通过主键ID获取食堂评分项
	 * @param id 主键ID
	 * @return
	 * @exception	
	 * @author 张亚伟
	 * @date 2015年8月11日 下午7:33:33
	 */
	public ConfigDto findConfigToId(String id);

	
	/**
	 * 
	 * findAll：获取所有评分项
	 * @return
	 * @exception	
	 * @author 张亚伟
	 * @date 2015年8月12日 下午2:22:08
	 */
	public List<Config> findAll();


    
    /**     
     * findALL：一句话描述方法功能
     * @param configDto
     * @param phDto
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年8月14日 上午11:24:42	 
     */
    public List<ConfigDto> findALL(ConfigDto configDto,List<String> listStr,PageHelperDto phDto);


    
    /**     
     * findCount：一句话描述方法功能
     * @param configDto
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年8月14日 上午11:24:52	 
     */
    public int findCount(ConfigDto configDto,List<String> listStr);


    
    /**     
     * finByName：一句话描述方法功能
     * @param configName
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年8月14日 上午11:25:02	 
     */
    public List<ConfigDto> finByName(String configName,String projId);


    
    /**     
     * add：一句话描述方法功能
     * @param configDto
     * @exception	
     * @author 刘博
     * @date 2015年8月14日 上午11:25:10	 
     */
    public void add(ConfigDto configDto);


    
    /**     
     * update：一句话描述方法功能
     * @param configDto
     * @exception	
     * @author 刘博
     * @date 2015年8月14日 上午11:25:16	 
     */
    public void update(ConfigDto configDto);


    
    /**     
     * delete：一句话描述方法功能
     * @param r
     * @exception	
     * @author 刘博
     * @date 2015年8月14日 上午11:27:15	 
     */
    public void delete(ConfigDto r);
    /**
     * FindTree
     * milkteaye
     */
    public List<Tree> findTree(String searchName,List<String> listStr);
    public String chooseConfig(String cafeId);
    
    public int grant(String id,String configIds);
    /**
     * 
     * findByConfigName：检查修改的的配置项名称除自己之外是否和其他名称相等
     * @param param
     * @param projId
     * @param listStr
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年11月3日 下午4:56:02
     */
    public List<ConfigDto> findByConfigName(ConfigDto configDto,List<String> listStr);

}

