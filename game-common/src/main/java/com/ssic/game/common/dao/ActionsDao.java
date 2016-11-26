/**   
 * bare_field_name   
 * com.ssic.game.common.dao	
 * @return  the bare_field_name 
 */

package com.ssic.game.common.dao;

import java.util.List;
import java.util.Map;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ssic.game.common.mapper.ActionsExMapper;
import com.ssic.game.common.mapper.ActionsMapper;
import com.ssic.game.common.pojo.Actions;
import com.ssic.game.common.pojo.ActionsExample;
import com.ssic.game.common.pojo.ActionsExample.Criteria;
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
public class ActionsDao extends MyBatisBaseDao<Actions>
{

    @Autowired
    @Getter
    private ActionsMapper mapper;

    @Autowired
    
    @Getter
    private ActionsExMapper exMapper;
    


    public List<Actions> findAllBy(Actions actions)
    {
        ActionsExample example = new ActionsExample();
        Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(actions.getId()))
        {
            criteria.andIdEqualTo(actions.getId());
        }
        if (!StringUtils.isEmpty(actions.getProjId()))
        {
            criteria.andProjIdEqualTo(actions.getProjId());
        }
        if (!StringUtils.isEmpty(actions.getType()) && actions.getType() != 0)
        {
            criteria.andTypeEqualTo(actions.getType());
        }
        if (!StringUtils.isEmpty(actions.getName()))
        {
            criteria.andNameEqualTo(actions.getName());
        }
        if(!StringUtils.isEmpty(actions.getNextTaskId())){
            criteria.andNextTaskIdEqualTo(actions.getNextTaskId());
        }
        if(!StringUtils.isEmpty(actions.getProcId())){
            criteria.andProcIdEqualTo(actions.getProcId());
        }
        if(!StringUtils.isEmpty(actions.getTaskId())){
            criteria.andTaskIdEqualTo(actions.getTaskId());
        }
        criteria.andStatEqualTo(1);
        return mapper.selectByExample(example) != null ? mapper.selectByExample(example) : null;
    }
    public List<Map<Object, Object>> findPageBy(Actions actions,int beginRows,int rows,int total)

    {
        int beginPage = 0;
        int endPage = 0;
        int tempTotal=0;
        if((beginRows-1)>0){
           beginPage = (beginRows-1)*rows; 
        }
        if(total%rows>0){
         tempTotal = (total/rows)+1;   
        }else{
            tempTotal=total/rows;
        }
        if(tempTotal<beginRows){
            beginRows=tempTotal;
        }
        endPage = beginRows*rows;

        return exMapper.getAll(actions,beginPage,endPage);
    }
    public List<Map<Object,Object>>findPageFinish(Actions actions,int beginRows,int rows){
        return exMapper.getFinsh(actions,beginRows,rows);
    }

    public int findCountAll(Actions actions)
    {
        int count1 = exMapper.getCount(actions);
//        int count2 = exMapper.getCountFinish(actions);
        return count1;
    }
    public void del(Actions actions){
        if(!StringUtils.isEmpty(actions.getId())){
            mapper.updateByPrimaryKeySelective(actions);
        }
    }
  

}
