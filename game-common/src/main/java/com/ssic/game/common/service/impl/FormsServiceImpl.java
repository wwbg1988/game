/**
 * 
 */
package com.ssic.game.common.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.ssic.game.common.dao.FormsDao;
import com.ssic.game.common.dto.FormsDto;
import com.ssic.game.common.pojo.Forms;
import com.ssic.game.common.service.IFormsService;
import com.ssic.game.common.util.DataStatusUtils;
import com.ssic.util.BeanUtils;

/**		
 * <p>Title: FormsServiceImpl </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author rkzhang	
 * @date 2015年6月30日 下午4:08:19	
 * @version 1.0
 * <p>修改人：rkzhang</p>
 * <p>修改时间：2015年6月30日 下午4:08:19</p>
 * <p>修改备注：</p>
 */
@Service("FormsServiceImpl")
public class FormsServiceImpl implements IFormsService
{

    @Autowired
    private FormsDao formsDao;

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.common.service.IFormsService#findByFormId(java.lang.String)   
    */
    @Override
    @Cacheable(value = "default", key = "'game.common.dto.FormsDto:' + #formId")
    public FormsDto findByFormId(String formId)
    {
        Forms form = formsDao.selectByPrimaryKey(formId);

        if (isNotExist(form))
        {
            return null;
        }
        return BeanUtils.createBeanByTarget(form, FormsDto.class);
    }

    private boolean isNotExist(Forms form)
    {

        return form == null || DataStatusUtils.isNotEnabled(form.getStat());
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.common.service.IFormsService#findByTaskId(java.lang.String)   
    */
    @Override
    public FormsDto findByTaskId(String nowTaskId)
    {
        FormsDto formsDto = new FormsDto();
        formsDto.setTaskId(nowTaskId);
        List<FormsDto> formList = formsDao.findAll(formsDto, null);
        if (CollectionUtils.isEmpty(formList))
        {
            return null;
        }
        return BeanUtils.createBeanByTarget(formList.get(0), FormsDto.class);
    }

}
