/**   
 * bare_field_name   
 * com.ssic.game.common.dao	
 * @return  the bare_field_name 
 */

package com.ssic.game.common.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;



import com.ssic.game.common.mapper.ProjectRoleMapper;
import com.ssic.game.common.pojo.ProjectRole;
import com.ssic.game.common.pojo.ProjectRoleExample;
import com.ssic.game.common.pojo.ProjectRoleExample.Criteria;
import com.ssic.util.base.MyBatisBaseDao;

/**		
 * <p>Title: ActionsDao </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年6月24日 上午9:03:59	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年6月24日 上午9:03:59</p>
 * <p>修改备注：</p>
 */
@Repository
public class ProjRoleDao extends MyBatisBaseDao<ProjectRole>
{

    @Autowired
    @Getter
    private ProjectRoleMapper mapper;  
    
    public List<ProjectRole> findAll(){
        ProjectRoleExample example = new ProjectRoleExample();
        Criteria criteria = example.createCriteria();
        criteria.andStatEqualTo(1);
        return mapper.selectByExample(example);
    }
    



}
