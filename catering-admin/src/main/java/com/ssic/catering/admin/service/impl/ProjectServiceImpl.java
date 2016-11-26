package com.ssic.catering.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssic.catering.admin.pageModel.Tree;
import com.ssic.catering.admin.service.ProjectService;
import com.ssic.game.common.dao.ProjectDao;
import com.ssic.game.common.dto.ProjectDto;

@Service
public class ProjectServiceImpl implements ProjectService
{

    @Autowired
    private ProjectDao projectDao;

    public List<ProjectDto> findAll()
    {
        return projectDao.findAll();
    }

    @Override
    public List<Tree> allTree()
    {
        // TODO Auto-generated method stub
        List<Tree> lt = new ArrayList<Tree>();
        List<ProjectDto> list_project = new ArrayList<ProjectDto>();
        list_project = projectDao.findAll();
        //组装数据tree模型
        if (list_project != null && list_project.size() > 0)
        {
            for (ProjectDto projectDto : list_project)
            {
                Tree tree = new Tree();
                BeanUtils.copyProperties(projectDto, tree);
                tree.setText(projectDto.getProjName());
                tree.setIconCls("bin");
                lt.add(tree);
            }
        }
        return lt;
    }

    /**
     * @desc 根据projId查询对应的项目信息
     * @author pengcheng.yang
     * @date 2015-10-28
     * (non-Javadoc)   
     * @see com.ssic.catering.admin.service.ProjectService#findById(java.lang.String)
     */
    @Override
    public ProjectDto findById(String id)
    {
        return projectDao.findById(id);
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.admin.service.ProjectService#allTreeByProjId(java.lang.String)   
    */
    @Override
    public List<Tree> allTreeByProjId(String projId)
    {
        List<Tree> lt = new ArrayList<Tree>();
        List<ProjectDto> list_project = new ArrayList<ProjectDto>();
        list_project = projectDao.findAll();
        //组装数据tree模型
        if (list_project != null && list_project.size() > 0)
        {
            for (ProjectDto projectDto : list_project)
            {
                if (projectDto.getId().equals(projId))
                {
                    Tree tree = new Tree();
                    BeanUtils.copyProperties(projectDto, tree);
                    tree.setText(projectDto.getProjName());
                    tree.setIconCls("bin");
                    lt.add(tree);
                }
            }
        }
        return lt;
    }

}
