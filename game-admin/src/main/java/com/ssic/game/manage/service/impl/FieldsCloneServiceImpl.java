/**
 * 
 */
package com.ssic.game.manage.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssic.game.common.dao.FieldsCloneDao;
import com.ssic.game.common.dao.FieldsDao;
import com.ssic.game.common.pojo.Fields;
import com.ssic.game.common.pojo.FiledsClone;
import com.ssic.game.manage.service.IFieldsCloneService;

/**		
 * <p>Title: FiledsCloneServiceImpl </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年6月28日 上午9:38:05	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年6月28日 上午9:38:05</p>
 * <p>修改备注：</p>
 */

@Service
public class FieldsCloneServiceImpl implements IFieldsCloneService
{

    @Autowired
    private FieldsCloneDao fieldsCloneDao;

    @Autowired
    private FieldsDao fieldsDao;

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.manage.service.IFieldsCloneService#insertExistFields(java.lang.String, java.lang.String)   
    */
    public String insertExistFields(String fieldId, String formFieldId)
    {
        String fieldCloneId = null;
        //表单的字段对象;
        Fields formField = fieldsDao.selectByPrimaryKey(formFieldId);
        //要引用的字段对象;
        Fields field = fieldsDao.selectByPrimaryKey(fieldId);
        //根据表单的字段对象查找对应的克隆字段表的id;
        if (!StringUtils.isEmpty(formField.getFieldsCloneId()))
        {
            FiledsClone clone = fieldsCloneDao.selectByPrimaryKey(formField.getFieldsCloneId());
            //存在引用的字段对象，则更新该克隆对象;
            clone.setFieldsId(field.getId());
            clone.setFormId(field.getFormId());
            clone.setParamName(field.getParamName());
            clone.setLastUpdateTime(new Date());
            fieldsCloneDao.updateByPrimaryKeySelective(clone);
            fieldCloneId = clone.getId();
            return fieldCloneId;
        }
        else
        {
            //不存在引用的字段对象，则新增一条克隆对象;
            FiledsClone newClone = new FiledsClone();
            fieldCloneId = UUID.randomUUID().toString();
            newClone.setId(fieldCloneId);
            newClone.setFieldsId(fieldId);
            newClone.setFormId(field.getFormId());
            newClone.setStat(1);
            newClone.setCreateTime(new Date());
            newClone.setParamName(field.getParamName());
            fieldsCloneDao.insert(newClone);
            return fieldCloneId;
        }
    }

    
     /** 
     * (non-Javadoc)   
     * @see com.ssic.game.manage.service.IFieldsCloneService#deleteById(java.lang.String)   
     */
    @Override
    public void deleteById(String fieldsCloneId)
    {
        FiledsClone clone= fieldsCloneDao.selectByPrimaryKey(fieldsCloneId);
        clone.setStat(0);
        fieldsCloneDao.updateByPrimaryKeySelective(clone);
    }

}
