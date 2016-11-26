/**
 * 
 */
package com.ssic.game.common.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.search.AndTerm;

import lombok.Getter;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ssic.game.common.dto.FieldsDto;
import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.game.common.mapper.FieldsMapper;
import com.ssic.game.common.pojo.Fields;
import com.ssic.game.common.pojo.FieldsExample;
import com.ssic.game.common.pojo.FieldsExample.Criteria;
import com.ssic.game.common.pojo.FiledsClone;
import com.ssic.game.common.pojo.Forms;
import com.ssic.util.base.MyBatisBaseDao;
import com.ssic.util.constants.DataStatus;

/**		
 * <p>Title: FieldsDao </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年6月24日 上午9:19:00	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年6月24日 上午9:19:00</p>
 * <p>修改备注：</p>
 */
@Repository
public class FieldsDao extends MyBatisBaseDao<Fields>
{
    @Getter
    @Autowired
    private FieldsMapper mapper;

    @Autowired
    private FormsDao formDao;

    @Autowired
    private FieldsCloneDao fieldsCloneDao;

    @Autowired
    private FieldDictDao fieldDictDao;

    @Autowired
    private FieldsRoleDao fieldsRoleDao;

    /**     
     * findByFormId：根据formId找到该form下的所有field
     * @param formId
     * @return
     * @exception	
     * @author rkzhang
     * @date 2015年6月28日 上午11:13:52	 
     */
    public List<Fields> findByFormId(String formId)
    {
        FieldsExample example = new FieldsExample();
        Criteria criteria = example.createCriteria();
        criteria.andFormIdEqualTo(formId);
        criteria.andStatEqualTo(1);
        return mapper.selectByExample(example);
    }
    
    public FieldsDto findById(String id){
        FieldsDto fieldsDto = new FieldsDto();
        Fields fields = mapper.selectByPrimaryKey(id);
        if(fields!=null){
            BeanUtils.copyProperties(fields, fieldsDto);
            return fieldsDto;
        }
        return null;
    }

    public List<Fields> findAllBy(Fields fields)
    {
        FieldsExample example = new FieldsExample();
        Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(fields.getId()))
        {
            criteria.andIdEqualTo(fields.getId());
        }
        if (!StringUtils.isEmpty(fields.getProjId()))
        {
            criteria.andProjIdEqualTo(fields.getProjId());
        }
        if (!StringUtils.isEmpty(fields.getProcId()))
        {
            criteria.andProcIdEqualTo(fields.getProcId());
        }
        if (!StringUtils.isEmpty(fields.getFormId()))
        {
            criteria.andFormIdEqualTo(fields.getFormId());
        }
        criteria.andStatEqualTo(1);
        example.setOrderByClause("order_id");
        return mapper.selectByExample(example);
    }

    /**     
     * findAll：一句话描述方法功能
     * @param fieldsDto
     * @param phDto 
     * @param ph
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年6月24日 上午11:19:15	 
     */
    public List<FieldsDto> findAll(FieldsDto fieldsDto, PageHelperDto ph)
    {
        FieldsExample example = new FieldsExample();
        Criteria criteria = example.createCriteria();

        example.setDistinct(true);
        criteria.andStatEqualTo(1);
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
        if (!StringUtils.isEmpty(fieldsDto.getFormId()))
        {
            criteria.andFormIdEqualTo(fieldsDto.getFormId());
        }
        if (!StringUtils.isEmpty(fieldsDto.getParamDesc()))
        {
            criteria.andParamDescLike("%" + fieldsDto.getParamDesc() + "%");
        }
        if (!StringUtils.isEmpty(fieldsDto.getParamName()))
        {
            criteria.andParamNameLike("%" + fieldsDto.getParamName() + "%");
        }
        if (!StringUtils.isEmpty(fieldsDto.getIsdiy()))
        {
            criteria.andIsDiyEqualTo(fieldsDto.getIsdiy());
        }
        if (!StringUtils.isEmpty(fieldsDto.getIsencrypt()))
        {
            criteria.andIsEncryptEqualTo(fieldsDto.getIsencrypt());
        }
        if (!StringUtils.isEmpty(fieldsDto.getIsneed()))
        {
            criteria.andIsNeedEqualTo(fieldsDto.getIsneed());
        }
        if (!StringUtils.isEmpty(fieldsDto.getIsuniline()))
        {
            criteria.andIsUnilineEqualTo(fieldsDto.getIsuniline());
        }
        if (!StringUtils.isEmpty(fieldsDto.getType()))
        {
            criteria.andTypeEqualTo(fieldsDto.getType());
        }
        if (!StringUtils.isEmpty(fieldsDto.getDataType()))
        {
            criteria.andDataTypeEqualTo(fieldsDto.getDataType());
        }
        List<FieldsDto> list = new ArrayList<FieldsDto>();

        List<Fields> fields = mapper.selectByExample(example);
        for (Fields field : fields)
        {
            FieldsDto dto = new FieldsDto();
            BeanUtils.copyProperties(field, dto);
            //根据cloneId查找对应的引用表单id和引用字段id;
            if (!StringUtils.isEmpty(field.getFieldsCloneId()))
            {
                FiledsClone fieldsClone = fieldsCloneDao.selectByPrimaryKey(field.getFieldsCloneId());
                if (fieldsClone.getStat() == 1)
                {
                    Forms form = formDao.selectByPrimaryKey(fieldsClone.getFormId());
                    Fields fieldss = mapper.selectByPrimaryKey(fieldsClone.getFieldsId());
                    dto.setRefrenceFormName(form.getName());
                    dto.setRefrenceParamName(fieldss.getParamName());
                    dto.setRefrenceParamDesc(fieldss.getParamDesc());
                }
            }
            else
            {
                dto.setRefrenceFormName("");
                dto.setRefrenceParamName("");
                dto.setRefrenceParamDesc("");
            }
            dto.setIsdiy(field.getIsDiy());
            dto.setIsencrypt(field.getIsEncrypt());
            dto.setIsneed(field.getIsNeed());
            dto.setIsuniline(field.getIsUniline());

            list.add(dto);
        }

        return list;
    }

    /**     
     * findCountBy：一句话描述方法功能
     * @param fieldsDto
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年6月24日 上午11:36:31	 
     */
    public int findCountBy(FieldsDto fieldsDto)
    {
        FieldsExample example = new FieldsExample();
        Criteria criteria = example.createCriteria();
        example.setDistinct(true);
        criteria.andStatEqualTo(1);
        if (!StringUtils.isEmpty(fieldsDto.getFormId()))
        {
            criteria.andFormIdEqualTo(fieldsDto.getFormId());
        }
        if (!StringUtils.isEmpty(fieldsDto.getParamDesc()))
        {
            criteria.andParamDescLike("%" + fieldsDto.getParamDesc() + "%");
        }
        if (!StringUtils.isEmpty(fieldsDto.getParamName()))
        {
            criteria.andParamNameLike("%" + fieldsDto.getParamName() + "%");
        }
        if (!StringUtils.isEmpty(fieldsDto.getIsdiy()))
        {
            criteria.andIsDiyEqualTo(fieldsDto.getIsdiy());
        }
        if (!StringUtils.isEmpty(fieldsDto.getIsencrypt()))
        {
            criteria.andIsEncryptEqualTo(fieldsDto.getIsencrypt());
        }
        if (!StringUtils.isEmpty(fieldsDto.getIsneed()))
        {
            criteria.andIsNeedEqualTo(fieldsDto.getIsneed());
        }
        if (!StringUtils.isEmpty(fieldsDto.getIsuniline()))
        {
            criteria.andIsUnilineEqualTo(fieldsDto.getIsuniline());
        }
        if (!StringUtils.isEmpty(fieldsDto.getType()))
        {
            criteria.andTypeEqualTo(fieldsDto.getType());
        }
        if (!StringUtils.isEmpty(fieldsDto.getDataType()))
        {
            criteria.andDataTypeEqualTo(fieldsDto.getDataType());
        }
        int count = mapper.countByExample(example);
        return count;

    }

    /**     
     * vailField：一句话描述方法功能
     * @param fieldsDto
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年6月24日 下午2:00:45	 
     */
    public int vailField(FieldsDto fieldsDto)
    {
        FieldsExample example = new FieldsExample();
        Criteria criteria = example.createCriteria();
        example.setDistinct(true);
        criteria.andStatEqualTo(1);
        criteria.andFormIdEqualTo(fieldsDto.getFormId());
        criteria.andParamNameEqualTo(fieldsDto.getParamName());
        int count = mapper.countByExample(example);
        return count;
    }

    /**     
     * insert：一句话描述方法功能
     * @param fieldsDto
     * @exception	
     * @author 刘博
     * @date 2015年6月24日 下午2:03:43	 
     */
    public void insert(FieldsDto fieldsDto)
    {
        Fields field = new Fields();
        BeanUtils.copyProperties(fieldsDto, field);
        field.setStat(1);
        field.setCreateTime(new Date());
        field.setIsDiy(fieldsDto.getIsdiy());
        field.setIsEncrypt(fieldsDto.getIsencrypt());
        field.setIsNeed(fieldsDto.getIsneed());
        field.setIsUniline(fieldsDto.getIsuniline());
        mapper.insert(field);

    }

    /**     
     * updateField：一句话描述方法功能
     * @param fieldDto
     * @exception	
     * @author 刘博
     * @date 2015年6月24日 下午2:42:51	 
     */
    public void updateField(FieldsDto fieldDto)
    {
        if (fieldDto.getId() != null)
        {
            Fields field = new Fields();
            BeanUtils.copyProperties(fieldDto, field);
            field.setLastUpdateTime(new Date());
            field.setIsDiy(fieldDto.getIsdiy());
            field.setIsEncrypt(fieldDto.getIsencrypt());
            field.setIsNeed(fieldDto.getIsneed());
            field.setIsUniline(fieldDto.getIsuniline());
            mapper.updateByPrimaryKeySelective(field);
        }
    }

    /**     
     * updateStat：一句话描述方法功能
     * @param tempField.getId()
     * @exception	
     * @author A刘博
     * @date 2015年6月24日 下午4:31:19	 
     */
    public void updateStat(FieldsDto tempField)
    {

        Fields field = mapper.selectByPrimaryKey(tempField.getId());
        field.setStat(0);
        mapper.updateByPrimaryKeySelective(field);
    }

    /**     
     * deleteByFormId：一句话描述方法功能
     * @param formId
     * @exception	
     * @author Administrator
     * @date 2015年6月27日 下午3:41:29	 
     */
    public void deleteByFormId(String formId)
    {
        FieldsExample example = new FieldsExample();
        Criteria criteria = example.createCriteria();
        example.setDistinct(true);
        criteria.andStatEqualTo(1);
        if (formId != null)
        {
            criteria.andFormIdEqualTo(formId);
        }

        List<Fields> fields = mapper.selectByExample(example);
        for (Fields field : fields)
        {
            field.setStat(0);
            mapper.updateByPrimaryKeySelective(field);
        }

    }

    /**     
     * findExistFields：一句话描述方法功能
     * @param fieldsDto
     * @param phDto 
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年6月28日 上午9:00:11	 
     */
    public List<FieldsDto> findExistFields(FieldsDto fieldsDto, PageHelperDto ph)
    {
        FieldsExample example = new FieldsExample();
        Criteria criteria = example.createCriteria();

        example.setDistinct(true);
        criteria.andStatEqualTo(1);
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
        //不是当前表单
        if (!StringUtils.isEmpty(fieldsDto.getFormId()))
        {
            criteria.andFormIdNotEqualTo(fieldsDto.getFormId());
        }
        //同一流程
        if (!StringUtils.isEmpty(fieldsDto.getProcId()))
        {
            criteria.andProcIdEqualTo(fieldsDto.getProcId());
        }
        if (!StringUtils.isEmpty(fieldsDto.getOtherFormId()) && (!fieldsDto.getOtherFormId().equals("请选择")))
        {
            criteria.andFormIdEqualTo(fieldsDto.getOtherFormId());
        }
        if (!StringUtils.isEmpty(fieldsDto.getParamDesc()))
        {
            criteria.andParamDescLike("%" + fieldsDto.getParamDesc() + "%");
        }
        List<FieldsDto> list = new ArrayList<FieldsDto>();

        List<Fields> fields = mapper.selectByExample(example);
        for (Fields field : fields)
        {
            FieldsDto dto = new FieldsDto();
            if (!StringUtils.isEmpty(field.getFieldsCloneId()))
            {
                //字段的根据cloneId,获取克隆字段对象;
                FiledsClone clone = fieldsCloneDao.selectByPrimaryKey(field.getFieldsCloneId());
                //list集合中需要排除掉已经引用过字段的对象;
                if (!field.getId().equals(clone.getFieldsId()))
                {
                    //组装fieldDto对象;
                    getFieldDto(dto, field);
                    //讲该对象放入list集合中;
                    list.add(dto);
                }
            }
            else
            {

                //组装fieldDto对象;
                getFieldDto(dto, field);
                //讲该对象放入list集合中;
                list.add(dto);
            }
        }

        return list;
    }

    public FieldsDto getFieldDto(FieldsDto dto, Fields field)
    {
        BeanUtils.copyProperties(field, dto);
        Forms form = formDao.selectByPrimaryKey(field.getFormId());
        dto.setFormName(form.getName());
        dto.setIsdiy(field.getIsDiy());
        dto.setIsencrypt(field.getIsEncrypt());
        dto.setIsneed(field.getIsNeed());
        dto.setIsuniline(field.getIsUniline());
        return dto;
    }

    /**     
     * findFieldsCountBy：一句话描述方法功能
     * @param formId
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年7月7日 上午11:00:04	 
     */
    public int findFieldsCountByFormId(String formId)
    {
        FieldsExample example = new FieldsExample();
        Criteria criteria = example.createCriteria();
        example.setDistinct(true);
        criteria.andStatEqualTo(1);
        criteria.andFormIdEqualTo(formId);
        int count = mapper.countByExample(example);
        return count;

    }
}
