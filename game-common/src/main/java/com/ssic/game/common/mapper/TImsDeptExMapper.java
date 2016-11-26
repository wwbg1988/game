package com.ssic.game.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssic.game.common.dto.DeptDto;
import com.ssic.game.common.dto.PageHelperDto;

public interface TImsDeptExMapper
{
    List<DeptDto> findAll(@Param("dept") DeptDto deptDto);

    int findCountBy(@Param("dept") DeptDto deptDto);

    List<DeptDto> findPageBy(@Param("dept") DeptDto deptDto, @Param("ph") PageHelperDto ph);

    void insertDept(@Param("dept") DeptDto deptDto);

    void updateDept(@Param("dept") DeptDto deptDto);

    void updateDelDept(@Param("deptId") String id);

    DeptDto findById(@Param("deptId") String id);

    /**     
     * findEditCountBy：一句话描述方法功能
     * @param tempDept
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年7月7日 下午4:10:44	 
     */
    int findEditCountBy(@Param("dept") DeptDto deptDto);

    /**     
     * findChildListByParentId：一句话描述方法功能
     * @param parentId
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年7月8日 下午1:39:18	 
     */
    List<DeptDto> findChildListByParentId(@Param("deptId") String parentId);

    
    /**     
     * validDeptRootExists：一句话描述方法功能
     * @param id 
     * @param projId 
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年7月8日 下午3:12:20	 
     */
    DeptDto validDeptRootExists(@Param("deptId") String id, @Param("projId")String projId);

    
    /**     
     * findCodeByLastCreateTime：一句话描述方法功能
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年7月9日 下午2:33:24	 
     */
    List<DeptDto> findCodeByLastCreateTime();
    
    int findManager(@Param("deptId") String deptId);
}
