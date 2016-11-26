/**
 * 
 */
package com.ssic.game.common.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.ssic.game.common.dto.DeptDto;
import com.ssic.game.common.dto.ProjectDto;
import com.ssic.game.common.mapper.TImsDeptExMapper;
import com.ssic.game.common.util.DeptComparator;
import com.ssic.util.BeanUtils;
import com.ssic.util.StringUtils;

/**		
 * <p>Title: DeptDao </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年7月11日 上午9:33:34	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年7月11日 上午9:33:34</p>
 * <p>修改备注：</p>
 */
@Repository
public class DeptDao
{
    @Autowired
    private TImsDeptExMapper mapper;

    @Autowired
    private ImsUserDao userDao;

    @Autowired
    private ProjectDao projectDao;

  

    public List<DeptDto> findAll(DeptDto deptDto)
    {
        List<DeptDto> list = mapper.findAll(deptDto);

        return list;
    }



    public void insertDept(DeptDto deptDto)
    {
        mapper.insertDept(deptDto);
    }

    public void updateDept(DeptDto deptDto)
    {
        mapper.updateDept(deptDto);
    }

    public void deleDept(String id)
    {
        mapper.updateDelDept(id);
    }

    public int findCountByDeptName(DeptDto deptDto)
    {
        if (deptDto != null)
        {
            return mapper.findCountBy(deptDto);
        }
        else
        {
            return -1;
        }
    }

    public DeptDto findById(String id)
    {
        DeptDto deptDto = mapper.findById(id);
        return deptDto;
    }

    /**     
     * findEditCountByDeptName：一句话描述方法功能
     * @param tempDept
     * @return
     * @exception   
     * @author Administrator
     * @date 2015年7月7日 下午4:45:35     
     */
    public int findEditCountByDeptName(DeptDto tempDept)
    {
        if (tempDept != null && !StringUtils.isEmpty(tempDept.getId())
            && !StringUtils.isEmpty(tempDept.getDeptName())  && !StringUtils.isEmpty(tempDept.getPid()))
        {
            return mapper.findEditCountBy(tempDept);
        }
        else
        {
            return -1;
        }
    }
    
    /**
     * 
     * combobox：下拉列表
     * @param deptDto2
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2016年2月2日 下午8:27:42
     */
    public List<DeptDto> combobox(DeptDto deptDto)
    {
        List<DeptDto> deptDtoList = new ArrayList<DeptDto>();
        List<DeptDto> list = mapper.findAll(deptDto);
        if (list != null && list.size() > 0)
        {
            for (DeptDto dept : list)
            {
                DeptDto deptResult = new DeptDto();
                BeanUtils.copyProperties(dept, deptResult);
                deptResult.setIconCls("bin");
                if (!StringUtils.isEmpty(dept.getProjId()))
                {

                    ProjectDto projectDto = projectDao.findById(dept.getProjId());
                    if (projectDto != null)
                    {
                        deptResult.setProjName(projectDto.getProjName());
                    }
                    else
                    {
                        deptResult.setProjName("暂无项目");
                    }
                }
                else
                {
                    deptResult.setProjName("暂无项目");
                }
                
                deptDtoList.add(deptResult);
            }

        }
        Collections.sort(deptDtoList, new DeptComparator());
        return deptDtoList;
    }

    /**     
     * treeGrid：一句话描述方法功能
     * @param deptDto2 
     * @return
     * @exception   
     * @author Administrator
     * @date 2015年7月8日 上午10:20:20    
     */
    public List<DeptDto> treeGrid(DeptDto deptDto2)
    {
        List<DeptDto> deptDtoList = new ArrayList<DeptDto>();
        List<DeptDto> list = mapper.findAll(deptDto2);
        if (list != null && list.size() > 0)
        {
            for (DeptDto dept : list)
            {
                DeptDto deptResult = new DeptDto();
                BeanUtils.copyProperties(dept, deptResult);
                deptResult.setIconCls("bin");
                if (!StringUtils.isEmpty(dept.getProjId()))
                {

                    ProjectDto projectDto = projectDao.findById(dept.getProjId());
                    if (projectDto != null)
                    {
                        deptResult.setProjName(projectDto.getProjName());
                    }
                    else
                    {
                        deptResult.setProjName("暂无项目");
                    }
                }
                else
                {
                    deptResult.setProjName("暂无项目");
                }
                
                //查询该部门是否存在部门长
                int countManager= mapper.findManager(dept.getId());
                if(countManager>0){
                	deptResult.setIsExistManager(1);
                }else{
                	deptResult.setIsExistManager(0);
                }
                
                if (!StringUtils.isEmpty(dept.getPid()))
                {
                    DeptDto deptdto = mapper.findById(dept.getPid());
                    deptResult.setPid(dept.getPid());
                    deptResult.setPname(deptdto.getDeptName());
                }
                deptDtoList.add(deptResult);
            }

        }
        Collections.sort(deptDtoList, new DeptComparator());
        return deptDtoList;
    }

    public List<DeptDto> findChildListByParentId(String parentId)
    {
        List<DeptDto> dtoList = mapper.findChildListByParentId(parentId);
        return dtoList;
    }

    /**     
     * validDeptRootExists：一句话描述方法功能
     * @param id 
     * @param projId 
     * @return
     * @exception   
     * @author Administrator
     * @date 2015年7月8日 下午3:11:49     
     */
    public DeptDto validDeptRootExists(String id, String projId)
    {
        DeptDto dto = mapper.validDeptRootExists(id, projId);
        return dto;
    }

    /**     
     * findCodeByLastCreateTime：一句话描述方法功能
     * @return
     * @exception   
     * @author Administrator
     * @date 2015年7月9日 下午2:32:58     
     */
    public DeptDto findCodeByLastCreateTime()
    {
        List<DeptDto> dtoList = mapper.findCodeByLastCreateTime();
        if (!CollectionUtils.isEmpty(dtoList))
        {
            return dtoList.get(0);
        }
        return null;
    }
}

