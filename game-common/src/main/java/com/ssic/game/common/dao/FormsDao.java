/**
 * 
 */
package com.ssic.game.common.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ssic.game.common.dto.FormsDto;
import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.game.common.mapper.FormsMapper;
import com.ssic.game.common.pojo.Fields;
import com.ssic.game.common.pojo.Forms;
import com.ssic.game.common.pojo.FormsExample;
import com.ssic.game.common.pojo.FormsExample.Criteria;
import com.ssic.game.common.pojo.FormsFields;
import com.ssic.util.base.MyBatisBaseDao;

/**		
 * <p>Title: FormsDao </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年6月24日 上午9:08:24	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年6月24日 上午9:08:24</p>
 * <p>修改备注：</p>
 */
@Repository
public class FormsDao extends MyBatisBaseDao<Forms>
{
    @Getter
    @Autowired
    private FormsMapper mapper;

    @Autowired
    private FieldsDao fieldsDao;

    @Autowired
    private FieldsRoleDao fieldsRoleDao;

    /**
     * 按照ID查询FORM表单
     */
    public Forms findById(String id){
        return mapper.selectByPrimaryKey(id);
    }
    /**     
     * findAll：一句话描述方法功能
     * @param formsDto
     * @param phDto 
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年6月24日 下午6:51:11	 
     */
    public List<FormsDto> findAll(FormsDto formsDto, PageHelperDto ph)
    {
        FormsExample example = new FormsExample();
        Criteria criteria = example.createCriteria();

        example.setDistinct(true);
        if (ph != null && !StringUtils.isEmpty(ph.getBeginRow()) && !StringUtils.isEmpty(ph.getRows()))
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

        if (!StringUtils.isEmpty(formsDto.getName()))
        {
            criteria.andNameLike("%" + formsDto.getName() + "%");

        }

        if (!StringUtils.isEmpty(formsDto.getId()))
        {
            criteria.andIdEqualTo(formsDto.getId());
        }
        if (!StringUtils.isEmpty(formsDto.getProjId()))
        {
            criteria.andProjIdEqualTo(formsDto.getProjId());
        }
        if (!StringUtils.isEmpty(formsDto.getProcId()))
        {
            criteria.andProcIdEqualTo(formsDto.getProcId());
        }
        if (!StringUtils.isEmpty(formsDto.getTaskId()))
        {
            criteria.andTaskIdEqualTo(formsDto.getTaskId());
        }

        if (formsDto.getCreateTimeStart() != null)
        {
            criteria.andCreateTimeGreaterThanOrEqualTo(formsDto.getCreateTimeStart());
        }
        if (formsDto.getCreateTimeEnd() != null)
        {
            criteria.andCreateTimeLessThanOrEqualTo(formsDto.getCreateTimeEnd());
        }
        if (formsDto.getLastUpdateTimeStart() != null)
        {
            criteria.andLastUpdateTimeGreaterThanOrEqualTo(formsDto.getLastUpdateTimeStart());
        }
        if (formsDto.getLastUpdateTimeEnd() != null)
        {
            criteria.andLastUpdateTimeLessThanOrEqualTo(formsDto.getLastUpdateTimeEnd());
        }
        List<FormsDto> list = new ArrayList<FormsDto>();

        List<Forms> forms = mapper.selectByExample(example);
        for (Forms form : forms)
        {
            FormsDto dto = new FormsDto();
            BeanUtils.copyProperties(form, dto);
            list.add(dto);
        }

        return list;
    }

    /**     
     * findCountBy：一句话描述方法功能
     * @param formsDto
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年6月24日 下午6:51:19	 
     */
    public int findCountBy(FormsDto formsDto)
    {
        FormsExample example = new FormsExample();
        Criteria criteria = example.createCriteria();
        example.setDistinct(true);
        criteria.andStatEqualTo(1);
        if (!StringUtils.isEmpty(formsDto.getName()))
        {
            criteria.andNameLike("%" + formsDto.getName() + "%");

        }

        if (!StringUtils.isEmpty(formsDto.getId()))
        {
            criteria.andIdEqualTo(formsDto.getId());
        }
        if (!StringUtils.isEmpty(formsDto.getProjId()))
        {
            criteria.andProjIdEqualTo(formsDto.getProjId());
        }
        if (!StringUtils.isEmpty(formsDto.getProcId()))
        {
            criteria.andProcIdEqualTo(formsDto.getProcId());
        }
        if (!StringUtils.isEmpty(formsDto.getTaskId()))
        {
            criteria.andTaskIdEqualTo(formsDto.getTaskId());
        }

    
    
        int count = mapper.countByExample(example);
        return count;
    }

    /**     
     * vailField：一句话描述方法功能
     * @param formsDto
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年6月24日 下午6:51:25	 
     */
    public int vailField(FormsDto formsDto)
    {
        FormsExample example = new FormsExample();
        Criteria criteria = example.createCriteria();
        example.setDistinct(true);
        criteria.andStatEqualTo(1);
        criteria.andNameEqualTo(formsDto.getName());
        int count = mapper.countByExample(example);
        return count;
    }

    /**     
     * insert：一句话描述方法功能
     * @param formDto
     * @exception	
     * @author 刘博
     * @date 2015年6月24日 下午6:51:39	 
     */
    public void insert(FormsDto formDto)
    {
        Forms forms = new Forms();
        BeanUtils.copyProperties(formDto, forms);
        forms.setStat(1);
        forms.setCreateTime(new Date());
        forms.setLastUpdateTime(new Date());
        mapper.insert(forms);
    }

    /**     
     * updateStat：一句话描述方法功能
     * @param id
     * @exception	
     * @author 刘博
     * @date 2015年6月24日 下午6:51:45	 
     */
    public void updateStat(String id)
    {
        if (id != null)
        {
            Forms form = mapper.selectByPrimaryKey(id);
            form.setStat(0);
            mapper.updateByPrimaryKeySelective(form);
        }

    }

    /**     
     * updateField：一句话描述方法功能
     * @param formsDto
     * @exception	
     * @author 刘博
     * @date 2015年6月24日 下午6:51:53	 
     */
    public void updateForm(FormsDto formsDto)
    {
        if (formsDto.getId() != null)
        {
            Forms form = new Forms();
            BeanUtils.copyProperties(formsDto, form);
            form.setLastUpdateTime(new Date());
            mapper.updateByPrimaryKeySelective(form);
        }

    }

    /**     
     * findFormsByFieldId：一句话描述方法功能
     * @param id
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年6月26日 下午8:40:38	 
     **/
    public FormsDto findFormsByFieldId(String id)
    {
        FormsDto dto = new FormsDto();
        Fields f = fieldsDao.selectByPrimaryKey(id);
        Forms form = mapper.selectByPrimaryKey(f.getFormId());
        BeanUtils.copyProperties(form, dto);
        return dto;
    }

    /**     
     * findAllByNotInclude：一句话描述方法功能
     * @param formId
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年6月27日 下午8:42:44	 
     */
    public List<FormsDto> findAllByNotInclude(String formId)
    {
        List<FormsDto> listDto = new ArrayList<FormsDto>();
        List<String> ids = new ArrayList<String>();
        ids.add(formId);
        FormsExample example = new FormsExample();
        Criteria criteria = example.createCriteria();
        example.setDistinct(true);
        criteria.andStatEqualTo(1);
        criteria.andIdNotIn(ids);

        List<Forms> list = mapper.selectByExample(example);
        for (Forms form : list)
        {
            FormsDto dto = new FormsDto();
            BeanUtils.copyProperties(form, dto);
            listDto.add(dto);
        }
        return listDto;
    }

    /**     
     * findSameProcForms：查找同一流程下的除当前表单的所有表单
     * @param formsDto
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年6月30日 上午8:43:11	 
     */
    public List<FormsDto> findSameProcForms(String procId, String formId)
    {
        List<FormsDto> dtoList = new ArrayList<FormsDto>();
        FormsExample example = new FormsExample();
        Criteria criteria = example.createCriteria();
        example.setDistinct(true);
        criteria.andStatEqualTo(1);
        if (!StringUtils.isEmpty(formId))
        {
            criteria.andIdNotEqualTo(formId);
        }
        if (!StringUtils.isEmpty(procId))
        {
            criteria.andProcIdEqualTo(procId);
        }
        List<Forms> forms = mapper.selectByExample(example);
        if (forms != null && forms.size() > 0)
        {
            for (Forms form : forms)
            {
                FormsDto fDto = new FormsDto();
                BeanUtils.copyProperties(form, fDto);
                dtoList.add(fDto);
            }
        }
        return dtoList;
    }
}
