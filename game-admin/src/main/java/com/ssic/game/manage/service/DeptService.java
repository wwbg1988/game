package com.ssic.game.manage.service;

import java.util.List;

import com.ssic.game.admin.pageModel.DataGrid;
import com.ssic.game.admin.pageModel.PageHelper;
import com.ssic.game.admin.pageModel.Tree;
import com.ssic.game.common.dto.DeptDto;

public interface DeptService {
	public List<DeptDto> findAll();

    public List<DeptDto> finddeptByProj(DeptDto deptDto);
	
	public void insert(DeptDto deptDto);
	
	public void update (DeptDto deptDto);
	
	public void delete(DeptDto deptDto);

	public int vailDeptName(DeptDto tempDept);

	public void add(DeptDto dept);

	public DeptDto findById(String id);

    
    /**     
     * vailEditDeptName：一句话描述方法功能
     * @param tempDept
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年7月7日 下午4:08:38	 
     */
    public int vailEditDeptName(DeptDto tempDept);

    
    /**     
     * tree：一句话描述方法功能
     * @param deptId 
     * @param projId 
     * @param sessionInfo
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年7月8日 上午9:58:14	 
     */
    public List<Tree> tree(String deptId, String projId);

    
    /**     
     * treeGrid：一句话描述方法功能
     * @param deptDto 
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年7月8日 上午10:19:20	 
     */
    public List<DeptDto> treeGrid(DeptDto deptDto);
    
    public List<DeptDto> findChildListByParentId(String id);

    
    /**     
     * validDeptRootExists：一句话描述方法功能
     * @param id 
     * @param projId 
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年7月8日 下午3:11:19	 
     */
    public DeptDto validDeptRootExists(String id, String projId);

    
    /**     
     * setDeptCode：一句话描述方法功能
     * @param dept
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年7月8日 下午3:21:39	 
     */
    public void setDeptCode(DeptDto dept);
	
}
