package com.ssic.game.common.service;

import java.util.List;
import java.util.Map;

import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.game.common.dto.ParamConfigDto;

public interface IParamConfigService {

	List<ParamConfigDto> findBy(ParamConfigDto paramConfigDto);
	
	void insertParam(ParamConfigDto paramConfigDto);
	
	void updateParam(ParamConfigDto paramConfigDto);
	
	ParamConfigDto findByID(String id);
	
	/**
	 * 将查询出来的结果集按名值对形势作为返回值，参数名为key,参数值为value<BR>	 
	 * @author 朱振	
	 * @date 2015年10月28日 下午3:03:08	
	 * @version 1.0
	 * @param paramConfigDto 查询条件
	 * @return
	 * <p>修改人：朱振</p>
	 * <p>修改时间：2015年10月28日 下午3:03:08</p>
	 * <p>修改备注：</p>
	 */
	Map<String, String> getParametersBy(ParamConfigDto paramConfigDto);
	
	/**
	 * 分页获取(单表)参数配置数据	 
	 * @author 朱振	
	 * @date 2015年11月16日 下午3:21:15	
	 * @version 1.0
	 * @param paramConfigDto 查询条件
	 * @param pageHelperDto 分页
	 * @return
	 * <p>修改人：朱振</p>
	 * <p>修改时间：2015年11月16日 下午3:21:15</p>
	 * <p>修改备注：</p>
	 */
	List<ParamConfigDto> getParamConfigBy(ParamConfigDto paramConfigDto, PageHelperDto pageHelperDto);
	
	
	/**
     * 分页获取(多表)参数配置数据<BR>
     * t_ctr_param_config,t_ims_project<BR>
     * @author 朱振   
     * @date 2015年11月16日 下午3:21:15  
     * @version 1.0
     * @param paramConfigDto 查询条件
     * @param pageHelperDto 分页
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年11月16日 下午3:21:15</p>
     * <p>修改备注：</p>
     */
    List<ParamConfigDto> getParamConfigDtoBy(ParamConfigDto paramConfigDto, PageHelperDto pageHelperDto);
	
	/**
	 * 获取记录的数量	 
	 * @author 朱振	
	 * @date 2015年11月16日 下午3:24:07	
	 * @version 1.0
	 * @param paramConfigDto 查询条件
	 * @return
	 * <p>修改人：朱振</p>
	 * <p>修改时间：2015年11月16日 下午3:24:07</p>
	 * <p>修改备注：</p>
	 */
	long getRowsBy(ParamConfigDto paramConfigDto);
	
	/**
	 * 
	 * addParamConfig：添加记录
	 * @param paramConfigDto
	 * @return
	 * @exception	
	 * @author zhuzhen
	 * @date 2015年11月25日 下午1:00:23
	 */
	int addParamConfig(ParamConfigDto paramConfigDto);
	
	/**
	 * 
	 * updateParamConfig：一句话描述方法功能
	 * @param paramConfigDto
	 * @return
	 * @exception	
	 * @author zhuzhen
	 * @date 2015年12月1日 上午10:16:52
	 */
    int updateParamConfig(ParamConfigDto paramConfigDto);
}
