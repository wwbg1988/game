package com.ssic.game.admin.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssic.game.admin.dao.ResourceDao;
import com.ssic.game.admin.dao.ResourceTypeDao;
import com.ssic.game.admin.dao.TImsMenuDao;
import com.ssic.game.admin.dao.TImsMenuTypeDao;
import com.ssic.game.admin.dao.UserDao;
import com.ssic.game.admin.dto.TImsMenuDto;
import com.ssic.game.admin.model.Tresource;
import com.ssic.game.admin.pageModel.Resource;
import com.ssic.game.admin.pageModel.SessionInfo;
import com.ssic.game.admin.pageModel.Tree;
import com.ssic.game.admin.service.ResourceServiceI;


@Service
public class ResourceServiceImpl implements ResourceServiceI {

	@Autowired
	private ResourceDao resourceDao;

	@Autowired
	private ResourceTypeDao resourceTypeDao;

	@Autowired
	private UserDao userDao;

	
	@Autowired
	private TImsMenuDao menuDao;
	@Autowired
	private TImsMenuTypeDao menuTypeDao;
	
	public List<Tree> tree(SessionInfo sessionInfo) {
	
		List<Tree> lt = new ArrayList<Tree>();

		List<TImsMenuDto> menuDtoList=	menuDao.treeGrid(sessionInfo);

		if (menuDtoList != null && menuDtoList.size() > 0) {
			for (TImsMenuDto menuDto : menuDtoList) {
				// 菜单类型的资源
                if (menuDto.getMenuTypeId().equals("0")
                    && menuDto.getTabType().equals(0))
                {
					Tree tree = new Tree();
					BeanUtils.copyProperties(menuDto, tree);
					if (!StringUtils.isEmpty(menuDto.getPid())) {
						tree.setPid(menuDto.getPid());
					}
					tree.setText(menuDto.getName());
					// 设置借点url属性
					Map<String, Object> attr = new HashMap<String, Object>();
					attr.put("url", menuDto.getUrl());
					tree.setAttributes(attr);
					lt.add(tree);
				}
			}
		}		

		return lt;
	}

	
	public List<Tree> allTree(SessionInfo sessionInfo) {
		List<Tresource> l = null;
		List<Tree> lt = new ArrayList<Tree>();
		List<TImsMenuDto> list_menu = new ArrayList<TImsMenuDto>();

		Map<String, Object> params = new HashMap<String, Object>();
		if (sessionInfo != null) {
			params.put("userId", sessionInfo.getId());// 查自己有权限的资源
			
		 list_menu =menuDao.treeGrid(sessionInfo);
		//	l = resourceDao.find("select distinct t from Tresource t join fetch t.tresourcetype type join fetch t.troles role join role.tusers user where user.id = :userId order by t.seq", params);
		}
		//else {
		//	l = resourceDao.find("select distinct t from Tresource t join fetch t.tresourcetype type order by t.seq", params);
		//}
		
		if (list_menu != null && list_menu.size() > 0) {
			for (TImsMenuDto menuDto : list_menu) {
 			        Tree tree = new Tree();
					BeanUtils.copyProperties(menuDto, tree);
					if (!StringUtils.isEmpty(menuDto.getPid())) {
						tree.setPid(menuDto.getPid());
					}
 					tree.setText(menuDto.getName());
					// 设置借点url属性
					Map<String, Object> attr = new HashMap<String, Object>();
					tree.setIconCls("status_online");
					lt.add(tree);
			}
		}

		
		return lt;
	}


	public List<TImsMenuDto> treeGrid(SessionInfo sessionInfo) {
		List<TImsMenuDto> lr=	menuDao.treeGrid(sessionInfo);
	    return lr;
	}

	
	public void add(TImsMenuDto menuDto, SessionInfo sessionInfo) {
		menuDao.insertBy(menuDto,sessionInfo);
	}

	
	public void delete(String id) {
		if (id != null) {
			menuDao.deleteById(id);
		}
	}

//	private void del(Tresource t) {
//		if (t.getTresources() != null && t.getTresources().size() > 0) {
//			for (Tresource r : t.getTresources()) {
//				del(r);
//			}
//		}
//		resourceDao.delete(t);
//	}

	
	public void edit(TImsMenuDto menuDto) {
		menuDao.editMenu(menuDto);

	}

	/**
	 * 判断是否是将当前节点修改到当前节点的子节点
	 * 
	 * @param t
	 *            当前节点
	 * @param pt
	 *            要修改到的节点
	 * @return
	 */
	private boolean isChildren(Tresource t, Tresource pt) {
		if (pt != null && pt.getTresource() != null) {
			if (pt.getTresource().getId().equalsIgnoreCase(t.getId())) {
				pt.setTresource(null);
				return true;
			} else {
				return isChildren(t, pt.getTresource());
			}
		}
		return false;
	}

	
	public Resource get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		Tresource t = new Tresource();
		//Tresource t = resourceDao.get("from Tresource t left join fetch t.tresource resource left join fetch t.tresourcetype resourceType where t.id = :id", params);
		Resource r = new Resource();
		BeanUtils.copyProperties(t, r);
		if (t.getTresource() != null) {
			r.setPid(t.getTresource().getId());
			r.setPname(t.getTresource().getName());
		}
		r.setTypeId(t.getTresourcetype().getId());
		r.setTypeName(t.getTresourcetype().getName());
		if (t.getIcon() != null && !t.getIcon().equalsIgnoreCase("")) {
			r.setIconCls(t.getIcon());
		}
		return r;
	}


	public void add(Resource resource, SessionInfo sessionInfo) {
		// TODO Auto-generated method stub
		
	}


    
     /** 
     * (non-Javadoc)   
     * @see com.ssic.game.admin.service.ResourceServiceI#tabTree(com.ssic.game.admin.pageModel.SessionInfo)   
     */
    @Override
    public List<Tree> tabTree(SessionInfo sessionInfo)
    {
        List<Tree> lt = new ArrayList<Tree>();

        List<TImsMenuDto> menuDtoList=  menuDao.treeGrid(sessionInfo);

        if (menuDtoList != null && menuDtoList.size() > 0) {
            for (TImsMenuDto menuDto : menuDtoList) {
                // 菜单类型的资源
                if (menuDto.getMenuTypeId().equals("0")
                    && menuDto.getTabType().equals(1))
                {
                    Tree tree = new Tree();
                    BeanUtils.copyProperties(menuDto, tree);
                    if (!StringUtils.isEmpty(menuDto.getPid())) {
                        tree.setPid(menuDto.getPid());
                    }
                    tree.setText(menuDto.getName());
                    // 设置借点url属性
                    Map<String, Object> attr = new HashMap<String, Object>();
                    attr.put("url", menuDto.getUrl());
                    tree.setAttributes(attr);
                    lt.add(tree);
                }
            }
        }       

        return lt;
    }


    
     /** 
     * (non-Javadoc)   
     * @see com.ssic.game.admin.service.ResourceServiceI#allsTree(com.ssic.game.admin.pageModel.SessionInfo)   
     */
    @Override
    public List<Tree> allsTree(SessionInfo sessionInfo)
    {
        List<Tree> lt = new ArrayList<Tree>();

        List<TImsMenuDto> menuDtoList=  menuDao.treeGrid(sessionInfo);

        if (menuDtoList != null && menuDtoList.size() > 0) {
            for (TImsMenuDto menuDto : menuDtoList) {
                // 菜单类型的资源
                if (menuDto.getMenuTypeId().equals("0"))
                {
                    Tree tree = new Tree();
                    BeanUtils.copyProperties(menuDto, tree);
                    if (!StringUtils.isEmpty(menuDto.getPid())) {
                        tree.setPid(menuDto.getPid());
                    }
                    tree.setText(menuDto.getName());
                    // 设置借点url属性
                    Map<String, Object> attr = new HashMap<String, Object>();
                    attr.put("url", menuDto.getUrl());
                    tree.setAttributes(attr);
                    lt.add(tree);
                }
            }
        }       

        return lt;
    }

}
