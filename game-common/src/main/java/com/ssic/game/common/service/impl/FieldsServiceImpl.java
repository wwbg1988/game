/**
 * 
 */
package com.ssic.game.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.ssic.game.common.dao.FieldsDao;
import com.ssic.game.common.dto.FieldsDto;
import com.ssic.game.common.pojo.Fields;
import com.ssic.game.common.service.IFieldsService;
import com.ssic.util.BeanUtils;

/**		
 * <p>Title: FieldsServiceImpl </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author rkzhang	
 * @date 2015年6月30日 下午4:53:04	
 * @version 1.0
 * <p>修改人：rkzhang</p>
 * <p>修改时间：2015年6月30日 下午4:53:04</p>
 * <p>修改备注：</p>
 */
@Service("FieldsServiceImpl")
public class FieldsServiceImpl implements IFieldsService
{

    @Autowired
    private FieldsDao fieldsDao;

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.common.service.IFieldsService#findByFormId()   
    */
    @Override
    @Cacheable(value = "default", key = "'game.common.dto.FieldsDto.formId:' + #formId")
    public List<FieldsDto> findByFormId(String formId)
    {
        List<FieldsDto> dtoList = new ArrayList<FieldsDto>();
        List<Fields> results = fieldsDao.findByFormId(formId);
        for (Fields field : results)
        {
            FieldsDto dto = new FieldsDto();
            BeanUtils.copyProperties(field, dto);
            dto.setIsdiy(field.getIsDiy());
            dto.setIsencrypt(field.getIsEncrypt());
            dto.setIsneed(field.getIsNeed());
            dto.setIsuniline(field.getIsUniline());
            dtoList.add(dto);
        }
        return dtoList;
    }

}
