/**
 * 
 */
package com.ssic.game.manage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssic.game.admin.pageModel.DataGrid;
import com.ssic.game.admin.pageModel.PageHelper;
import com.ssic.game.common.dao.FieldDictDao;
import com.ssic.game.common.dao.FieldsDao;
import com.ssic.game.common.dao.FieldsRoleDao;
import com.ssic.game.common.dao.FormsDao;
import com.ssic.game.common.dao.ProjectDao;
import com.ssic.game.common.dao.TasksDao;
import com.ssic.game.common.dto.FieldsDto;
import com.ssic.game.common.dto.FormsDto;
import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.game.common.dto.ProcessDto;
import com.ssic.game.common.dto.ProjectDto;
import com.ssic.game.common.pojo.Forms;
import com.ssic.game.common.pojo.Tasks;
import com.ssic.game.manage.service.IFormsService;
import com.ssic.game.manage.service.ITImsProcessService;
import com.ssic.util.BeanUtils;
import com.ssic.util.StringUtils;

/**		
 * <p>Title: FormsServiceImpl </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年6月24日 上午10:20:01	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年6月24日 上午10:20:01</p>
 * <p>修改备注：</p>
 */
@Service
public class FormsServiceImpl implements IFormsService
{

    @Autowired
    private FormsDao formsDao;

    @Autowired
    private ProjectDao projectDao;
    
    @Autowired
    private ITImsProcessService processService;

    @Autowired
    private TasksDao taskDao;

    @Autowired
    private FieldsRoleDao fieldsRoleDao;
    
    @Autowired
    private FieldsDao fieldsDao;
    
    @Autowired
    private FieldDictDao fieldDictDao;
    
    
    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.manage.service.IFormsService#dataGrid(com.ssic.game.common.dto.FieldsDto, com.ssic.game.admin.pageModel.PageHelper)   
    */
    public DataGrid dataGrid(FormsDto formsDto, PageHelper ph)
    {
        int beginRow = (ph.getPage() - 1) * ph.getRows();
        ph.setBeginRow(beginRow);
        PageHelperDto phDto = new PageHelperDto();
        BeanUtils.copyProperties(ph, phDto);
        
        DataGrid dg = new DataGrid();
        List<FormsDto> listDto = formsDao.findAll(formsDto,phDto);
        int counts = formsDao.findCountBy(formsDto);
        dg.setTotal(Long.valueOf(counts));
        if (listDto.size() > 0)
        {
            for (FormsDto dto : listDto)
            {
                if (!StringUtils.isEmpty(dto.getProjId()))
                {

                    ProjectDto projectDto = projectDao.findById(dto.getProjId());
                    if (projectDto != null)
                    {
                        dto.setProjName(projectDto.getProjName());
                    }
                    else
                    {
                        dto.setProjName("无所属项目");
                    }
                }
                else
                {
                    dto.setProjName("无所属项目");
                }

                if (!StringUtils.isEmpty(dto.getTaskId()))
                {
                    Tasks tasks = taskDao.selectByPrimaryKey(dto.getTaskId());
                    if (tasks != null)
                    {
                        dto.setTaskName(tasks.getName());
                    }
                    else
                    {
                        dto.setTaskName("无任务节点");
                    }
                }
                else
                {
                    dto.setTaskName("无任务节点");
                }
                if (!StringUtils.isEmpty(dto.getProcId()))
                {
                    ProcessDto procDto=new ProcessDto();
                    procDto.setId(dto.getProcId());
                    List<ProcessDto> listProcDto= processService.findProcess(procDto);
                    if (listProcDto != null&&listProcDto.size()>0)
                    {
                        dto.setProcName(listProcDto.get(0).getProcName());
                    }
                    else
                    {
                        dto.setProcName("无任流程节点");
                    }
                }
                else
                {
                    dto.setProcName("无任流程节点");
                }
            }
        }
        dg.setRows(listDto);
        return dg;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.manage.service.IFormsService#vaildForms(com.ssic.game.common.dto.FormsDto)   
    */
    public int vaildForms(FormsDto formsDto)
    {
        int count = formsDao.vailField(formsDto);
        return count;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.manage.service.IFormsService#add(com.ssic.game.common.dto.FormsDto)   
    */
    @CacheEvict(value="default", key = "'game.common.dto.FormsDto:' + #formDto.getId()", beforeInvocation=true)
    public void add(FormsDto formDto)
    {
        formsDao.insert(formDto);

    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.manage.service.IFormsService#delete(com.ssic.game.common.dto.FormsDto)   
    */
    @Transactional
    @CacheEvict(value="default", key = "'game.common.dto.FormsDto:' + #forms.getId()", beforeInvocation=true)
    public void delete(FormsDto forms)
    { 
        //表单删除
        deleteForm(forms.getId());
     
        //删除字段字典关系表的所有数据;
        updateFieldDictByFormId(forms.getId());
        //删除表单同时，要删除表单下的字段角色关系;
        updateFieldRoleByFormId(forms.getId());
        //删除表单同时，要删除字段表里有该fomrId的数据;
        updateFieldByFormId(forms.getId());

    }
    
    private void deleteForm(String formId){
        formsDao.updateStat(formId);
    }
    
    private void updateFieldByFormId(String formId){
      //删除表单同时，要删除字段表的所有form_id等于该formId的数据;
        fieldsDao.deleteByFormId(formId);
    }    
  
    private void updateFieldDictByFormId(String formId){
        PageHelperDto phDto = new PageHelperDto();
        FieldsDto fieldsDto=new FieldsDto();
        fieldsDto.setFormId(formId);
         List<FieldsDto> fieldDtos=  fieldsDao.findAll(fieldsDto,phDto);
        for( FieldsDto fieldDto: fieldDtos){
            //删除表单的同时，要同时删除fieldDict表的字段记录;
            fieldDictDao.deleteByFieldId(fieldDto.getId());
        }
     
    };
    
    private void updateFieldRoleByFormId(String formId){
        //删除表单的同时，要同时删除eFieldRole表的stat=1的字段记录;
        fieldsRoleDao.deleteByFormId(formId);
        
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.manage.service.IFormsService#findById(java.lang.String)   
    */
    public FormsDto findById(String id)
    {
        Forms forms = formsDao.selectByPrimaryKey(id);
        FormsDto dto = new FormsDto();
        BeanUtils.copyProperties(forms, dto);
        return dto;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.manage.service.IFormsService#update(com.ssic.game.common.dto.FormsDto)   
    */
    @CacheEvict(value="default", key = "'game.common.dto.FormsDto:' + #formsDto.getId()", beforeInvocation=true)
    public void update(FormsDto formsDto)
    {
        formsDao.updateForm(formsDto);

    }

    public List<FormsDto> findAll(FormsDto formsDto)
    {      PageHelperDto phDto = new PageHelperDto();

        return formsDao.findAll(formsDto,phDto);
    }

    
     /** 
     * (non-Javadoc)   
     * @see com.ssic.game.manage.service.IFormsService#findFormsByFieldId(java.lang.String)   
     */
    public FormsDto findFormsByFieldId(String id)
    {
        FormsDto dto= formsDao.findFormsByFieldId(id);
        return dto;
       
    }

    
     /** 
     * (non-Javadoc)   
     * @see com.ssic.game.manage.service.IFormsService#findAllByNotInclude(java.lang.String)   
     */
    @Override
    public List<FormsDto> findAllByNotInclude(String formId)
    {
        List<FormsDto> list=  formsDao.findAllByNotInclude(formId);
        return list;
    }

    
     /** 
     * (non-Javadoc)   
     * @see com.ssic.game.manage.service.IFormsService#findSameProcForm(com.ssic.game.common.dto.FormsDto)   
     */
    @Override
    public List<FormsDto> findSameProcForms(String procId,String formId)
    {
       
        List<FormsDto> dtoList = formsDao.findSameProcForms(procId,formId);
        return dtoList;
    }

}
