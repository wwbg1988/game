/**
 * 
 */
package com.ssic.game.manage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssic.game.admin.pageModel.DataGrid;
import com.ssic.game.admin.pageModel.PageHelper;
import com.ssic.game.common.dao.DictDao;
import com.ssic.game.common.dao.FieldDictDao;
import com.ssic.game.common.dao.ProjectDao;
import com.ssic.game.common.dto.DictDto;
import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.game.common.pojo.ImsDict;
import com.ssic.game.manage.service.IDictService;
import com.ssic.util.BeanUtils;

/**		
 * <p>Title: DictServiceImpl </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年6月25日 下午2:40:53	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年6月25日 下午2:40:53</p>
 * <p>修改备注：</p>
 */
@Service
public class DictServiceImpl implements IDictService
{
    @Autowired
    private DictDao dictDao;

    @Autowired
    private ProjectDao projectDao;
    
    @Autowired
    private FieldDictDao fieldDictDao;
     /** 
     * (non-Javadoc)   
     * @see com.ssic.game.manage.service.IDictService#dataGrid(com.ssic.game.common.dto.DictDto, com.ssic.game.admin.pageModel.PageHelper)   
     */

    public DataGrid dataGrid(DictDto dictDto, PageHelper ph)
    {
        int beginRow = (ph.getPage() - 1) * ph.getRows();
        ph.setBeginRow(beginRow);
        PageHelperDto phDto=new PageHelperDto();
        BeanUtils.copyProperties(ph, phDto);
   
        DataGrid dg = new DataGrid();
        List<DictDto> listDto = dictDao.findAll(dictDto,phDto);
        int counts = dictDao.findCountBy(dictDto);
        dg.setTotal(Long.valueOf(counts));
        dg.setRows(listDto);
        return dg;
    }


    
     /** 
     * (non-Javadoc)   
     * @see com.ssic.game.manage.service.IDictService#add(com.ssic.game.common.dto.DictDto)   
     */

    public void add(DictDto dictDto)
    {
        dictDao.insert(dictDto);
        
    }
    
    
    /** 
     * (non-Javadoc)   
     * @see com.ssic.game.manage.service.IDictService#delete(com.ssic.game.common.dto.DictDto)   
     */
   @Transactional
    public void delete(DictDto tempDict)
    {
        dictDao.updateStat(tempDict.getId());
        //删除字典同时，要删除字段所引用的字段字典关系记录;
        updateFieldDictByDictId(tempDict.getId());
        
    }
    
  public void updateFieldDictByDictId(String dictId){
      //根据字典id,删除对应的字段字典对应记录;
      fieldDictDao.deleteByDictId(dictId);
    }




    
     /** 
     * (non-Javadoc)   
     * @see com.ssic.game.manage.service.IDictService#findById(java.lang.String)   
     */

    public DictDto findById(String id)
    {
        ImsDict dict = dictDao.selectByPrimaryKey(id);
        DictDto dto = new DictDto();
        BeanUtils.copyProperties(dict, dto);
        return dto;
    }



    
     /** 
     * (non-Javadoc)   
     * @see com.ssic.game.manage.service.IDictService#update(com.ssic.game.common.dto.DictDto)   
     */

    public void update(DictDto dictDto)
    {
        dictDao.updateDict(dictDto);
        
    }
}

