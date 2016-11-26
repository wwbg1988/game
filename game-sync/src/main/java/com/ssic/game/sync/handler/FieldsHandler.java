/**
 * 
 */
package com.ssic.game.sync.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ssic.game.common.dto.FieldsDto;
import com.ssic.game.sync.service.IFormsHandlerService;
import com.ssic.sync.annotation.TableName;
import com.ssic.sync.handler.IDataHandler;
import com.ssic.sync.model.DataInfo;

/**		
 * <p>Title: FieldsHandler </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author rkzhang	
 * @date 2015年6月28日 下午3:40:35	
 * @version 1.0
 * <p>修改人：rkzhang</p>
 * <p>修改时间：2015年6月28日 下午3:40:35</p>
 * <p>修改备注：</p>
 */

@TableName("t_ims_fields")
@Service
public class FieldsHandler implements IDataHandler {

    protected static final Log logger = LogFactory.getLog(FieldsHandler.class);
    
    @Autowired
    private IFormsHandlerService formsHandlerService;
    
     /** 
     * (non-Javadoc)   
     * @see com.ssic.sync.handler.IDataHandler#create(com.ssic.sync.model.DataInfo)   
     */
    @Override
    public void create(DataInfo dataInfo) {
	logger.info("create : " + dataInfo);
	FieldsDto field = populateFields(dataInfo);
	logger.info("field : " + field);
	formsHandlerService.updateById(field.getFormId());
    }


    /** 
     * (non-Javadoc)   
     * @see com.ssic.sync.handler.IDataHandler#update(com.ssic.sync.model.DataInfo)   
     */
    @Override
    public void update(DataInfo dataInfo) {
	logger.info("update : " + dataInfo);
	FieldsDto field = populateFields(dataInfo);
	logger.info("field : " + field);
	formsHandlerService.updateById(field.getFormId());
    }

    
     /** 
     * (non-Javadoc)   
     * @see com.ssic.sync.handler.IDataHandler#delete(com.ssic.sync.model.DataInfo)   
     */
    @Override
    public void delete(DataInfo dataInfo) {
	// TODO 添加方法注释
	
    }

    
    /**     
     * populateFields：组装属性对象
     * @param dataInfo
     * @return
     * @exception	
     * @author rkzhang
     * @date 2015年6月28日 下午5:51:39	 
     */
    private FieldsDto populateFields(DataInfo dataInfo) {
	FieldsDto fields = new FieldsDto();
	fields.setId(dataInfo.getColumnStringValue("id"));
	fields.setProjId(dataInfo.getColumnStringValue("proj_id"));
	fields.setOrderId(dataInfo.getColumnIntegerValue("order_id"));//`order_id` int(11) NOT NULL COMMENT '字段排列顺序',
	fields.setProcId(dataInfo.getColumnStringValue("proc_id"));//`proc_id` varchar(36) DEFAULT NULL,
	fields.setFormId(dataInfo.getColumnStringValue("form_id"));//`form_id` varchar(36) DEFAULT NULL,
	fields.setParamDesc(dataInfo.getColumnStringValue("param_desc"));    //	  `param_desc` varchar(100) DEFAULT NULL COMMENT '参数描述',
	fields.setParamName(dataInfo.getColumnStringValue("param_name"));    //	  `param_name` varchar(100) DEFAULT NULL COMMENT '字段参数名称 与ID同名 唯一',
	fields.setPattern(dataInfo.getColumnStringValue("pattern"));   //	  `pattern` varchar(100) DEFAULT NULL COMMENT '校验格式 正则校验',
	fields.setIsencrypt(dataInfo.getColumnBooleanValue("is_encrypt"));	//	  `is_encrypt` bit(1) NOT NULL COMMENT '字段值是否加密 1是 0否',
	fields.setIsuniline(dataInfo.getColumnBooleanValue("is_uniline"));	//	  `is_uniline` bit(1) NOT NULL COMMENT '是否独占一行 1是 0否',
	fields.setIsdiy(dataInfo.getColumnBooleanValue("is_diy"));//	  `is_diy` bit(1) NOT NULL COMMENT '是否为自定义字段 1是 0否 自定义字段可重复添加 规则为字段名+自增编号 1开始 主要针对text、checkbox、radio',
	fields.setLength(dataInfo.getColumnIntegerValue("length"));//	  `length` int(11) NOT NULL COMMENT '长度',
	fields.setHeight(dataInfo.getColumnIntegerValue("height"));//	  `height` int(11) NOT NULL COMMENT '高度',
	fields.setType(dataInfo.getColumnIntegerValue("type"));    //	  `type` int(11) NOT NULL COMMENT '字段类型 1:text 2:checkbox  3:hidden 4:img 5:password 6:radio 7:reset 8:file 9:label 10:select 11:textarea',
	fields.setFieldsCloneId(dataInfo.getColumnStringValue("fields_clone_id"));//	  `fields_clone_id` varchar(36) DEFAULT NULL COMMENT '是否克隆字段值 1是、0否',
	fields.setDataType(dataInfo.getColumnIntegerValue("data_type"));   //	  `data_type` int(11) NOT NULL COMMENT '字段数据类型 1:int 2:string 3:float 4:date',
	fields.setIsneed(dataInfo.getColumnBooleanValue("is_need"));//	  `is_need` bit(1) NOT NULL COMMENT '是否必填 1是、0否 ',
	fields.setStat(dataInfo.getColumnIntegerValue("stat"));   //	  `stat` int(11) NOT NULL COMMENT '0:无效 1：有效',
	fields.setLastUpdateTime(dataInfo.getColumnDateValue("last_update_time"));   //	  `last_update_time` datetime DEFAULT NULL COMMENT '修改日期',
	fields.setCreateTime(dataInfo.getColumnDateValue("create_time"));    //	  `create_time` datetime DEFAULT NULL COMMENT '添加日期',
	
	return fields;
    }

}

