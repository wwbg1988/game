package com.ssic.game.manage.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ssic.game.admin.pageModel.DataGrid;
import com.ssic.game.admin.pageModel.PageHelper;
import com.ssic.game.admin.pageModel.Tree;
import com.ssic.game.common.dao.DeptDao;
import com.ssic.game.common.dao.ProjectUsersDao;
import com.ssic.game.common.dto.DeptDto;
import com.ssic.game.manage.service.DeptService;

@Service
public class DeptServiceImpl implements DeptService
{

    @Autowired
    private DeptDao deptDao;

    @Autowired
    private ProjectUsersDao projectUsersDao;

    public List<DeptDto> findAll()
    {
        DeptDto d = new DeptDto();
        return deptDao.findAll(d);
    }


    public void insert(DeptDto deptDto)
    {
        deptDto.setCreateTime(new Date());
        deptDto.setStat(1);
        deptDao.insertDept(deptDto);
    }

    public void update(DeptDto deptDto)
    {
        deptDto.setStat(1);
        deptDto.setLastUpdateTime(new Date());
        deptDao.updateDept(deptDto);
    }

    public void delete(DeptDto deptDto)
    {
        List<DeptDto> list = deptDao.findChildListByParentId(deptDto.getId());
        if (list != null && list.size() > 0)
        {
            for (DeptDto dto : list)
            {
                deptDao.deleDept(dto.getId());
            }
        }
        deptDao.deleDept(deptDto.getId());
    }

    public int vailDeptName(DeptDto deptDto)
    {
        return deptDao.findCountByDeptName(deptDto);
    }

    public void add(DeptDto dept)
    {
        deptDao.insertDept(dept);

    }

    public DeptDto findById(String id)
    {
        if (id != null)
        {
            DeptDto DeptDto = deptDao.findById(id);
            return DeptDto;
        }
        return null;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.manage.service.DeptService#vailEditDeptName(com.ssic.game.admin.dto.DeptDto)   
    */
    @Override
    public int vailEditDeptName(DeptDto tempDept)
    {
        return deptDao.findEditCountByDeptName(tempDept);
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.manage.service.DeptService#tree(com.ssic.game.admin.pageModel.SessionInfo)   
    */
    @Override
    public List<Tree> tree(String deptId, String projId)
    {
        DeptDto d = new DeptDto();
        d.setProjId(projId);
        List<DeptDto> deptDtoList = deptDao.findAll(d);
        List<DeptDto> filterDtoList = new ArrayList<DeptDto>();
        List<Tree> lt = new ArrayList<Tree>();
        Map<String, DeptDto> maps = new HashMap<String, DeptDto>();
        for (DeptDto dto : deptDtoList)
        {
            maps.put(dto.getId(), dto);
        }
        DeptDto localdto = deptDao.findById(deptId);
        if (!StringUtils.isEmpty(deptId))
        {//查找过滤不包括当前deptId的集合;

            List<DeptDto> needFilterList = findChild(deptId, filterDtoList);
            needFilterList.add(localdto);
            for (DeptDto dto : needFilterList)
            {
                if (maps.containsKey(dto.getId()))
                {
                    System.out.println("-------------------------部门名称:-------------" + dto.getDeptName());
                    maps.remove(dto.getId());

                }
            }

        }

        if (!CollectionUtils.isEmpty(maps))
        {
            for (String id : maps.keySet())
            {
                DeptDto deptDto = deptDao.findById(id);
                Tree tree = new Tree();
                BeanUtils.copyProperties(deptDto, tree);
                if (!StringUtils.isEmpty(deptDto.getPid()))
                {
                    tree.setPid(deptDto.getPid());
                }
                tree.setText(deptDto.getDeptName());
                tree.setIconCls("bin");
                lt.add(tree);
            }
        }
        return lt;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.manage.service.DeptService#treeGrid()   
    */
    @Override
    public List<DeptDto> treeGrid(DeptDto deptDto)
    {
        List<DeptDto> lr = deptDao.treeGrid(deptDto);
        return lr;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.manage.service.DeptService#findChildListByParentId(java.lang.String)   
    */
    @Override
    public List<DeptDto> findChildListByParentId(String id)
    {
        List<DeptDto> list = deptDao.findChildListByParentId(id);
        return list;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.manage.service.DeptService#validDeptRootExists()   
    */
    @Override
    public DeptDto validDeptRootExists(String id, String projId)
    {
        DeptDto dto = deptDao.validDeptRootExists(id, projId);
        return dto;
    }

    /**    
    * (non-Javadoc)   
    * @see com.ssic.game.manage.service.DeptService#setDeptCode(com.ssic.game.admin.dto.DeptDto)   
    */
    @Override
    public void setDeptCode(DeptDto dept)
    {
        //当前对象如果移动了，则需要重新更新他原来父节点下的子节点的部门编码信息;
        //当前部门对象;
        DeptDto dto = deptDao.findById(dept.getId());
        if (null != dto)
        {
            //当前部门原来的父部门对象;
            DeptDto dto2 = deptDao.findById(dto.getPid());
            //当前部门原来的父部门对象编码;
            String lastParentDeptCode = dto2.getDeptCode();
            //当前部门原来的父部门对象下的子部门集合;
            List<DeptDto> dtoLists = deptDao.findChildListByParentId(dto.getPid());
            List<DeptDto> newDtoList = new ArrayList<DeptDto>();
            for (DeptDto dtos : dtoLists)
            {
                deptDao.findChildListByParentId(dtos.getId());

                if (!dtos.getId().equals(dept.getId()))
                {
                    DeptDto d = new DeptDto();
                    BeanUtils.copyProperties(dtos, d);
                    newDtoList.add(d);
                }
            }

            if (!CollectionUtils.isEmpty(newDtoList))
            {
                //如果原父部门下的子部门有3个（除当前部门外），则需要重新赋值deptCode;
                //如：原部门下有:0001 0002 0003这3个，现在移动0002,则0001 0003需要变为0001,0002 ;
                //如 原来父部门编码是00，有3个子部门，现在循环（3-1）次，当前子部门编码为："00"+(01)=0001,"00"+(02)=0002;

                for (int i = 0; i < newDtoList.size(); i++)
                {
                    DeptDto deptDto = newDtoList.get(i);
                    String dCode = lastParentDeptCode + ("0" + (i + 1));
                    deptDto.setDeptCode(dCode);
                    deptDto.setStat(1);
                    deptDao.updateDept(deptDto);
                    //更新原父部门下的子部门的编码;
                    updateChildDept(deptDto.getId(), dCode);

                }
            }
        }
        if (StringUtils.isEmpty(dept.getPid()))
        {
            //查找当前部门编码
            String localDeptCode=getRootDeptCode();
            dept.setDeptCode(localDeptCode);
        } //如果不是父节点
        else
        { //如果当前节点是3级节点第2条：如00（1级）01（2级）02（当前），则父节点是0001;如果父节点下有3个子节点，
          //则第一个是000101，第二个是000102,第三个是000103;当前添加的则应该是父节点的第4个节点:000104;
            String parentDeptCode = deptDao.findById(dept.getPid()).getDeptCode();
            String resultDeptCode = null;
            //查找父节点下的子节点;
            List<DeptDto> dtoList = deptDao.findChildListByParentId(dept.getPid());
            //List<DeptDto> dtoList2 = deptDao.findChildListByParentId(dept.getId());
            if (!CollectionUtils.isEmpty(dtoList))
            { //当前的code;
                String localDeptCode = "0" + String.valueOf(dtoList.size() + 1);
                resultDeptCode = parentDeptCode + localDeptCode;
                dept.setDeptCode(resultDeptCode);
            }
            else
            {
                resultDeptCode = parentDeptCode + "01";
                dept.setDeptCode(resultDeptCode);
            }

            /*   if (!CollectionUtils.isEmpty(dtoList2))
               {
                   for (int i = 0; i < dtoList2.size(); i++)
                   {
                       DeptDto child = dtoList2.get(i);
                       String localDeptCode = resultDeptCode + ("0" + (i + 1));
                       child.setDeptCode(localDeptCode);
                       child.setStat(1);
                       deptDao.updateDept(child);
                   }
               }*/
            //更新当前部门下的子部门的编码;
            updateChildDept(dept.getId(), resultDeptCode);
        }
    }

    public void updateChildDept(String deptId, String parentDeptCode)
    {
        List<DeptDto> childs = deptDao.findChildListByParentId(deptId);
        if (!CollectionUtils.isEmpty(childs))
        {
            for (int j = 0; j < childs.size(); j++)
            {
                String lastParentDeptCode = parentDeptCode + ("0" + (j + 1));
                DeptDto deptChild = childs.get(j);
                deptChild.setDeptCode(lastParentDeptCode);
                deptChild.setStat(1);
                deptDao.updateDept(deptChild);
                updateChildDept(deptChild.getId(), lastParentDeptCode);

            }
        }

    }

    public List<DeptDto> findChild(String parentId, List<DeptDto> resultList)
    {
        List<DeptDto> childList = deptDao.findChildListByParentId(parentId);
        if (!CollectionUtils.isEmpty(childList))
        {
            for (DeptDto dto : childList)
            {
                resultList.add(dto);
                findChild(dto.getId(), resultList);
            }
        }
        return resultList;
    }

    public String getRootDeptCode()
    {
        String defaultCode = "01";
        DeptDto dto = deptDao.findCodeByLastCreateTime();
        if (dto != null)
        {//如果存在根节点的deptCode,如：08；则当前应给部门的部门编码为09
            String lastDeptCode = dto.getDeptCode();

            if (Integer.valueOf(lastDeptCode) < 9)
            {
                defaultCode = "0" + (Integer.valueOf(lastDeptCode) + 1);
            }
            else
            {
                defaultCode = String.valueOf((Integer.valueOf(lastDeptCode) + 1));
            }
        }
        return defaultCode;
    }


	@Override
	public List<DeptDto> finddeptByProj(DeptDto deptDto) {
	     
	      return deptDao.findAll(deptDto);
	}
}
