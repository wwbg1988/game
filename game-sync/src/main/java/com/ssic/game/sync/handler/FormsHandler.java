/**
 * 
 */
package com.ssic.game.sync.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ssic.game.common.dto.FormsDto;
import com.ssic.game.sync.service.IFormsHandlerService;
import com.ssic.sync.annotation.TableName;
import com.ssic.sync.handler.IDataHandler;
import com.ssic.sync.model.DataInfo;

/**		
 * <p>Title: FormsHandler </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author rkzhang	
 * @date 2015年6月27日 下午7:02:04	
 * @version 1.0
 * <p>修改人：rkzhang</p>
 * <p>修改时间：2015年6月27日 下午7:02:04</p>
 * <p>修改备注：</p>
 */

@TableName("t_ims_forms")
@Service
public class FormsHandler implements IDataHandler {
    
    protected static final Log logger = LogFactory.getLog(FormsHandler.class);
    
    @Autowired
    private IFormsHandlerService formService;

    /** 
    * (non-Javadoc)   
    * @see com.ssic.sync.handler.IDataHandler#create(com.ssic.sync.model.DataInfo)   
    */
    @Override
    public void create(DataInfo dataInfo) {
	logger.info("create : " + dataInfo);
	FormsDto form = populateForm(dataInfo);
	formService.processCreate(form);
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.sync.handler.IDataHandler#update(com.ssic.sync.model.DataInfo)   
    */
    @Override
    public void update(DataInfo dataInfo) {
	logger.info("update : " + dataInfo);
	FormsDto form = populateForm(dataInfo);
	formService.processUpdate(form);
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.sync.handler.IDataHandler#delete(com.ssic.sync.model.DataInfo)   
    */
    @Override
    public void delete(DataInfo dataInfo) {
	logger.info("delete : " + dataInfo);

    }

    /**     
     * populateForm：组装FormData信息
     * @param dataInfo
     * @return
     * @exception	
     * @author rkzhang
     * @date 2015年6月28日 上午9:17:56	 
     */
    private FormsDto populateForm(DataInfo dataInfo) {	  
	  FormsDto form = new FormsDto();
	  form.setId(dataInfo.getColumnStringValue("id"));
	  form.setName(dataInfo.getColumnStringValue("name"));
	  form.setProjId(dataInfo.getColumnStringValue("proj_id"));
	  form.setProcId(dataInfo.getColumnStringValue("proc_id"));
	  form.setTaskId(dataInfo.getColumnStringValue("task_id"));
	  form.setStat(dataInfo.getColumnIntegerValue("stat"));
	  form.setLastUpdateTime(dataInfo.getColumnDateValue("last_update_time"));
	  form.setCreateTime(dataInfo.getColumnDateValue("create_time"));
	return form;
    }
}

