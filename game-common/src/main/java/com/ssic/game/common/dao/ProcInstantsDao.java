/**   
 * bare_field_name   
 * com.ssic.game.common.dao	
 * @return  the bare_field_name 
 */

package com.ssic.game.common.dao;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssic.game.common.dto.DataGridDto;
import com.ssic.game.common.dto.FormsDto;
import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.game.common.mapper.ProcInstanceMapper;
import com.ssic.game.common.pojo.ProcInstance;
import com.ssic.game.common.pojo.ProcInstanceExample;
import com.ssic.game.common.pojo.ProcInstanceExample.Criteria;
import com.ssic.util.StringUtils;
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
public class ProcInstantsDao extends MyBatisBaseDao<ProcInstance>
{

    @Autowired
    @Getter
    private ProcInstanceMapper mapper;

    

    public List<ProcInstance> findAllBy(ProcInstance param)
    {
        ProcInstanceExample example = new ProcInstanceExample();
        Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(param.getId())){
            criteria.andIdEqualTo(param.getId());
        }
        if(!StringUtils.isEmpty(param.getProcId())){
            criteria.andProcIdEqualTo(param.getProcId());
        }
        if(!StringUtils.isEmpty(param.getProjId())){
            criteria.andProjIdEqualTo(param.getProjId());
        }
        criteria.andStatEqualTo(1);
        return mapper.selectByExample(example)!=null?mapper.selectByExample(example):null;
        
    }
    
    public ProcInstance findById(String id){
        return mapper.selectByPrimaryKey(id);
    }
    public List<ProcInstance>findAllIncludeFinish(ProcInstance param){
        ProcInstanceExample example = new ProcInstanceExample();
        Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(param.getId())){
            criteria.andIdEqualTo(param.getId());
        }
        if(!StringUtils.isEmpty(param.getProcId())){
            criteria.andProcIdEqualTo(param.getProcId());
        }
        if(!StringUtils.isEmpty(param.getProjId())){
            criteria.andProjIdEqualTo(param.getProjId());
        }
        return mapper.selectByExample(example)!=null?mapper.selectByExample(example):null;
    }
    



    
    /**     
     * findAll：一句话描述方法功能
     * @param instanceDto
     * @param phDto
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年7月24日 下午4:05:22	 
     */
    public List<ProcInstance> findAll(ProcInstance procInstance, PageHelperDto ph)
    {
        ProcInstanceExample example = new ProcInstanceExample();
        Criteria criteria = example.createCriteria();

        example.setDistinct(true);
        if (ph != null && !StringUtils.isEmpty(String.valueOf(ph.getBeginRow())) && !StringUtils.isEmpty(String.valueOf(ph.getRows())))
        {
            int beginRow = ph.getBeginRow();
            int rows = ph.getRows();
            example.setOrderByClause("create_time desc limit " + beginRow + "," + rows);
        }
        else
        {
            example.setOrderByClause("create_time desc");
        }
        criteria.andStatEqualTo(1);
        if(!StringUtils.isEmpty(procInstance.getProcId())){
            criteria.andProcIdEqualTo(procInstance.getProcId());
        }
        if(!StringUtils.isEmpty(procInstance.getProjId())){
            criteria.andProjIdEqualTo(procInstance.getProjId());
        }
        List<ProcInstance> list =  mapper.selectByExample(example);
        return list;
    }




    
    /**     
     * findCountBy：一句话描述方法功能
     * @param procInstance
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年7月24日 下午4:12:52	 
     */
    public int findCountBy(ProcInstance procInstance)
    {
        ProcInstanceExample example = new ProcInstanceExample();
        Criteria criteria = example.createCriteria();
        example.setDistinct(true);
        criteria.andStatEqualTo(1);
        if(!StringUtils.isEmpty(procInstance.getProcId())){
            criteria.andProcIdEqualTo(procInstance.getProcId());
        }
        if(!StringUtils.isEmpty(procInstance.getProjId())){
            criteria.andProjIdEqualTo(procInstance.getProjId());
        }
        if(!StringUtils.isEmpty(procInstance.getProcName())){
            criteria.andProcNameEqualTo(procInstance.getProcName());
        }
        int count = mapper.countByExample(example);
        return count;
    }

  

}
