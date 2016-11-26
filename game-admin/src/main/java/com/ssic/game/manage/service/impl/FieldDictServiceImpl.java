/**
 * 
 */
package com.ssic.game.manage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssic.game.admin.pageModel.DataGrid;
import com.ssic.game.admin.pageModel.PageHelper;
import com.ssic.game.common.dao.DictDao;
import com.ssic.game.common.dao.FieldDictDao;
import com.ssic.game.common.dto.DictDto;
import com.ssic.game.common.dto.FieldDictDto;
import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.game.manage.service.IFieldDictService;
import com.ssic.util.BeanUtils;

/**		
 * <p>Title: FieldDictServiceImpl </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年6月24日 上午10:24:51	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年6月24日 上午10:24:51</p>
 * <p>修改备注：</p>
 */
@Service
public class FieldDictServiceImpl implements IFieldDictService
{
    @Autowired
    private FieldDictDao fieldDictDao;
    
    @Autowired
    private DictDao DictDao;

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.manage.service.IFieldDictService#dataGrid(java.lang.String, com.ssic.game.admin.pageModel.PageHelper)   
    */
    public DataGrid dataGrid(String fieldId, PageHelper ph)
    {
        int beginRow = (ph.getPage() - 1) * ph.getRows();
        ph.setBeginRow(beginRow);
        PageHelperDto phDto = new PageHelperDto();
        BeanUtils.copyProperties(ph, phDto);
        
        List<DictDto> fieldDtoList = fieldDictDao.findDictsByFieldId(fieldId,phDto);
        DataGrid dg = new DataGrid();
        int counts = fieldDictDao.findDictsCountByFieldId(fieldId);
        dg.setTotal(Long.valueOf(counts));
        dg.setRows(fieldDtoList);
        return dg;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.manage.service.IFieldDictService#vaildIsExists(java.lang.String, java.lang.String)   
    */
    public int vaildIsExists(String fieldId, String dictId)
    {
        return fieldDictDao.vaildIsExists(fieldId, dictId);
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.manage.service.IFieldDictService#insert(java.lang.String, java.lang.String)   
    */
    public void insert(String fieldId, String dictId)
    {
        fieldDictDao.insert(fieldId, dictId);

    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.manage.service.IFieldDictService#delete(java.lang.String, java.lang.String)   
    */
    public void delete(String fieldId, String dictId)
    {
        fieldDictDao.delete(fieldId, dictId);

    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.manage.service.IFieldDictService#findByDictId(java.lang.String)   
    */
    public List<FieldDictDto> findByDictId(String id)
    {
        List<FieldDictDto> list = fieldDictDao.findByDictId(id);
        return list;
    }

}
