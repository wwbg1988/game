/**
 * 
 */
package com.ssic.game.sync.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssic.base.redis.WdRedisDao;
import com.ssic.game.common.dao.FieldsDao;
import com.ssic.game.common.dao.FormsDao;
import com.ssic.game.common.dto.FieldsDto;
import com.ssic.game.common.dto.FormsDto;
import com.ssic.game.common.pojo.Fields;
import com.ssic.game.common.pojo.Forms;
import com.ssic.game.sync.service.IFormsHandlerService;
import com.ssic.util.BeanUtils;
import com.ssic.util.constants.DataStatus;

/**		
 * <p>Title: FormsHandlerServiceImpl </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author rkzhang	
 * @date 2015年6月28日 上午9:44:54	
 * @version 1.0
 * <p>修改人：rkzhang</p>
 * <p>修改时间：2015年6月28日 上午9:44:54</p>
 * <p>修改备注：</p>
 */
@Service
public class FormsHandlerServiceImpl implements IFormsHandlerService {

    @Autowired
    private WdRedisDao<FormsDto> redisDao;
    
    @Autowired
    private FieldsDao fieldsDao;
    
    @Autowired
    private FormsDao formsDao;
    
    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.sync.service.IFormsHandlerService#processCreate(com.ssic.game.common.dto.FormsDto)   
    */
    @Override
    public void processCreate(FormsDto form) {
	if(isEnabled(form)) {
	    return;
	}
	setFieldsAndPutToRedis(form);
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.sync.service.IFormsHandlerService#processUpdate(com.ssic.game.common.dto.FormsDto)   
    */
    @Override
    public void processUpdate(FormsDto form) {
	if(isEnabled(form)) {
	    redisDao.delete(form, FormsDto.class);
	    return;
	}
	setFieldsAndPutToRedis(form);
    }
    
    /** 
     * (non-Javadoc)   
     * @see com.ssic.game.sync.service.IFormsHandlerService#updateById(java.lang.String)   
     */
    @Override
    public void updateById(String formId) {
	Forms form = formsDao.selectByPrimaryKey(formId);
	FormsDto formDto = BeanUtils.createBeanByTarget(form, FormsDto.class);
	processUpdate(formDto);
    }
    

    private boolean isEnabled(FormsDto form) {
	return form.getStat() == null || form.getStat().intValue() != DataStatus.ENABLED;
    }
    
    
    private void setFieldsAndPutToRedis(FormsDto form) {
   	List<Fields> results = fieldsDao.findByFormId(form.getId());
   	List<FieldsDto> fields = BeanUtils.createBeanListByTarget(results, FieldsDto.class);
   	form.setFields(fields);
   	redisDao.set(form);
    }


}

