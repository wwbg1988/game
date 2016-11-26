package com.ssic.catering.base.service;

import java.util.List;

import com.ssic.catering.base.pojo.PageHelper;
import com.ssic.game.common.dto.SignDto;

public interface ICaterSignService {

	List<SignDto> findBy(SignDto signDto);
	
	void insertSign (SignDto signDto);
	
	void updateSign (SignDto signDto);
	
	SignDto findById(String id);
	
	List<SignDto> findBy(SignDto signDto,PageHelper ph);
	
	/**
	 * 使用自定义sql查询多表     	 
	 * @author 朱振	
	 * @date 2015年10月27日 上午9:52:32	
	 * @version 1.0
	 * @param signDto
	 * @param projectIds
	 * @param ph
	 * @return
	 * <p>修改人：朱振</p>
	 * <p>修改时间：2015年10月27日 上午9:52:32</p>
	 * <p>修改备注：</p>
	 */
	List<SignDto> findSignsBy(SignDto signDto,PageHelper ph);
	
	/**
	 * 显示 findSignsBy 查询出来的总条数
	 * @see findSignsBy
	 * @author 朱振	
	 * @date 2015年10月27日 上午10:44:22	
	 * @version 1.0
	 * @return
	 * <p>修改人：朱振</p>
	 * <p>修改时间：2015年10月27日 上午10:44:22</p>
	 * <p>修改备注：</p>
	 */
	Long countSignsBy(SignDto signDto);
	
	int count(SignDto signDto);
	
	List<SignDto> findByProjs(SignDto signDto);
}
