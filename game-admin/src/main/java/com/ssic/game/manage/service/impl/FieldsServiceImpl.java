/**
 * 
 */
package com.ssic.game.manage.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ssic.game.admin.pageModel.DataGrid;
import com.ssic.game.admin.pageModel.PageHelper;
import com.ssic.game.admin.pageModel.Tree;
import com.ssic.game.common.constant.HttpConstants;
import com.ssic.game.common.dao.FieldDictDao;
import com.ssic.game.common.dao.FieldsCloneDao;
import com.ssic.game.common.dao.FieldsDao;
import com.ssic.game.common.dao.FieldsRoleDao;
import com.ssic.game.common.dao.FieldsUserDao;
import com.ssic.game.common.dao.FormsDao;
import com.ssic.game.common.dao.ImsRolesDao;
import com.ssic.game.common.dto.FieldRoleDto;
import com.ssic.game.common.dto.FieldUserDto;
import com.ssic.game.common.dto.FieldsDto;
import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.game.common.pojo.FieldRole;
import com.ssic.game.common.pojo.FieldUser;
import com.ssic.game.common.pojo.Fields;
import com.ssic.game.common.pojo.FiledsClone;
import com.ssic.game.common.pojo.Forms;
import com.ssic.game.common.pojo.ImsRole;
import com.ssic.game.manage.service.IFieldsCloneService;
import com.ssic.game.manage.service.IFieldsService;
import com.ssic.util.BeanUtils;
import com.ssic.util.UUIDGenerator;
import com.ssic.util.constants.DataStatus;

/**		
 * <p>Title: FieldsServiceImpl </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p> 
 * @author 刘博	
 * @date 2015年6月24日 上午10:23:29	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年6月24日 上午10:23:29</p>
 * <p>修改备注：</p>
 */
@Service
public class FieldsServiceImpl implements IFieldsService
{

    @Autowired
    private FieldsDao fieldsDao;

    @Autowired
    private FieldsCloneDao fieldsCloneDao;

    @Autowired
    private FieldsRoleDao fieldsRoleDao;

    @Autowired
    private FieldsUserDao fieldsUserDao;

    @Autowired
    private ImsRolesDao imsRoleDao;

    @Autowired
    private FieldDictDao fieldDictDao;

    @Autowired
    private FormsDao formDao;

    @Autowired
    private IFieldsCloneService fieldsCloneService;

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.manage.service.IFieldsService#dataGrid(com.ssic.game.common.dto.FieldsDto, com.ssic.game.admin.pageModel.PageHelper)   
    */
    public DataGrid dataGrid(FieldsDto fieldsDto, PageHelper ph)
    {
        int beginRow = (ph.getPage() - 1) * ph.getRows();
        ph.setBeginRow(beginRow);
        PageHelperDto phDto = new PageHelperDto();
        BeanUtils.copyProperties(ph, phDto);

        DataGrid dg = new DataGrid();
        List<FieldsDto> listDto = fieldsDao.findAll(fieldsDto, phDto);
        for (FieldsDto fieldDto : listDto)
        {
            if (!StringUtils.isEmpty(fieldDto.getFormId()))
            {
                Forms form = formDao.selectByPrimaryKey(fieldDto.getFormId());
                fieldDto.setFormName(form.getName());
            }
            else
            {
                fieldDto.setFormName("数据异常");
            }
        }
        int counts = fieldsDao.findCountBy(fieldsDto);
        dg.setTotal(Long.valueOf(counts));
        dg.setRows(listDto);
        return dg;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.manage.service.IFieldsService#vailFieldValue(com.ssic.game.common.dto.FieldsDto)   
    */
    public int vaildField(FieldsDto fieldsDto)
    {
        int count = fieldsDao.vailField(fieldsDto);
        return count;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.manage.service.IFieldsService#add(com.ssic.game.common.dto.FieldsDto)   
    */
    @CacheEvict(value="default", key = "'game.common.dto.FieldsDto.formId:' + #fieldsDto.getFormId()", beforeInvocation=true)
    public void add(FieldsDto fieldsDto)
    {
        fieldsDao.insert(fieldsDto);

    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.manage.service.IFieldsService#delete(com.ssic.game.common.dto.FieldsDto)   
    */
  
    @CacheEvict(value="default", key = "'game.common.dto.FieldsDto.formId:' + #tempField.getFormId()", beforeInvocation=true)
    
    public void delete(FieldsDto tempField)
    { //删除字段;
        fieldsDao.updateStat(tempField);
        //删除字段字典
        updateFieldDictByFieldId(tempField.getId());
        //删除字段角色;
        updateFieldRoleByFieldId(tempField.getId());

    }

    public void updateFieldDictByFieldId(String id)
    {
        //删除字段的同时，要同时删除fieldDict表的字段记录;
        fieldDictDao.deleteByFieldId(id);

    };

    public void updateFieldRoleByFieldId(String id)
    {
        //字段删除时，要同时删除字段权限表(t_ims_field_role)的字段记录;
        fieldsRoleDao.deleteByFieldId(id);

    };

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.manage.service.IFieldsService#findById(java.lang.String)   
    */
    public FieldsDto findById(String id)
    {
        Fields field = fieldsDao.selectByPrimaryKey(id);
        FieldsDto dto = new FieldsDto();
        BeanUtils.copyProperties(field, dto);
        dto.setIsencrypt(field.getIsEncrypt());
        dto.setIsdiy(field.getIsDiy());
        dto.setIsneed(field.getIsNeed());
        dto.setIsuniline(field.getIsUniline());
        return dto;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.manage.service.IFieldsService#update(com.ssic.game.common.dto.FieldsDto)   
    */
  
    @CacheEvict(value="default", key = "'game.common.dto.FieldsDto.formId:' + #fieldDto.getId()", beforeInvocation=true)
    public void update(FieldsDto fieldDto)
    {
        //更新字段;
        fieldsDao.updateField(fieldDto);
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.manage.service.IFieldsService#findAllBy(com.ssic.game.common.dto.FieldsDto)   
    */
    public List<FieldsDto> findAllBy(FieldsDto fieldDto)
    {
        Fields fields = new Fields();
        BeanUtils.copyProperties(fieldDto, fields);
        List<Fields> list = fieldsDao.findAllBy(fields);
        if (list != null && list.size() > 0)
        {
            List<FieldsDto> result = BeanUtils.createBeanListByTarget(list, FieldsDto.class);
            return result;
        }
        return null;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.manage.service.IFieldsService#findFieldRole(com.ssic.game.common.dto.FieldRoleDto)   
    */
    public List<FieldRole> findFieldRole(FieldRoleDto fieldRoleDto)
    {
        FieldRole fieldRole = new FieldRole();
        BeanUtils.copyProperties(fieldRoleDto, fieldRole);
        return fieldsRoleDao.findAllBy(fieldRole);
    }

    /** 
     * (non-Javadoc)   
     * @see com.ssic.game.manage.service.IFieldsService#findFieldUser(com.ssic.game.common.dto.FieldUserDto)   
     */
    @Override
    public List<FieldUser> findFieldUser(FieldUserDto FieldUserDto)
    {
        FieldUser fieldUser = new FieldUser();
        BeanUtils.copyProperties(FieldUserDto, fieldUser);

        return fieldsUserDao.findUserAllBy(fieldUser);
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.manage.service.IFieldsService#grantUser(java.lang.String, java.lang.String, java.lang.String, java.lang.String)   
    */
    @Override
    public void grantUser(String fieldId, String usersIds, String projId, String procId,String userOwn)
    {

       if(!StringUtils.isEmpty(usersIds)){
           for (String userId : usersIds.split(","))
           {
               FieldUser searchRole = new FieldUser();
               searchRole.setFieldId(fieldId);
               searchRole.setUserId(userId);
               searchRole.setProjId(projId);
               searchRole.setProcId(procId);
               searchRole.setReaderWrite(HttpConstants.FIELD_READ_PER);
               searchRole.setStat(1);
               List<FieldUser> list = fieldsUserDao.findUserAllBy(searchRole);
               if (list != null && list.size() > 0)
               {
                   FieldUser f = new FieldUser();
                   f.setId(list.get(0).getId());
                   f.setReaderWrite(HttpConstants.FIELD_WRITE_PER);
                   fieldsUserDao.updateByPrimaryKeySelective(f);
               }
               else
               {
                   FieldUser searchRoles = new FieldUser();
                   searchRoles.setFieldId(fieldId);
                   searchRoles.setUserId(userId);
                   searchRoles.setProjId(projId);
                   searchRoles.setProcId(procId);
                   searchRoles.setReaderWrite(HttpConstants.FIELD_WRITE_PER);
                   searchRoles.setStat(1);
                   List<FieldUser> delUser = fieldsUserDao.findUserAllBy(searchRoles);
                   if (delUser != null && delUser.size() > 0)
                   {
                       FieldUser fs = new FieldUser();
                       fs.setId(delUser.get(0).getId());
                       fs.setStat(0);
                       fieldsUserDao.updateByPrimaryKeySelective(fs);

                   }
                   FieldUser insertUser = new FieldUser();
                   insertUser.setFieldId(fieldId);
                   insertUser.setUserId(userId);
                   insertUser.setProjId(projId);
                   insertUser.setProcId(procId);
                   insertUser.setReaderWrite(HttpConstants.FIELD_WRITE_PER);
                   insertUser.setStat(1);
                   insertUser.setId(UUIDGenerator.getUUID());
                   insertUser.setCreateTime(new Date());
                   fieldsUserDao.insertSelective(insertUser);

               }
           }
       }
        
        
        FieldUser searchRoles = new FieldUser();
        searchRoles.setFieldId(fieldId);
        searchRoles.setUserId("owner");
        searchRoles.setProjId(projId);
        searchRoles.setProcId(procId);
        searchRoles.setReaderWrite(HttpConstants.FIELD_WRITE_PER);
        searchRoles.setStat(1);
        List<FieldUser> delUser = fieldsUserDao.findUserAllBy(searchRoles);
        if(delUser!=null&&delUser.size()>0){
            FieldUser fs = new FieldUser();
            fs.setId(delUser.get(0).getId());
            fs.setStat(0);
            fieldsUserDao.updateByPrimaryKeySelective(fs);
        }
        
        if(!StringUtils.isEmpty(userOwn)){
            FieldUser insertUser = new FieldUser();
            insertUser.setFieldId(fieldId);
            insertUser.setUserId("owner");
            insertUser.setProjId(projId);
            insertUser.setProcId(procId);
            insertUser.setReaderWrite(HttpConstants.FIELD_WRITE_PER);
            insertUser.setStat(1);
            insertUser.setId(UUIDGenerator.getUUID());
            insertUser.setCreateTime(new Date());
            fieldsUserDao.insertSelective(insertUser);
        }

    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.manage.service.IFieldsService#grantUserRead(java.lang.String, java.lang.String, java.lang.String, java.lang.String)   
    */
    @Override
    public void grantUserRead(String fieldId, String usersIds, String projId, String procId)
    {
        FieldUser searchRole = new FieldUser();
        searchRole.setFieldId(fieldId);
        searchRole.setProjId(projId);
        searchRole.setProcId(procId);
        searchRole.setReaderWrite(HttpConstants.FIELD_READ_PER);
        searchRole.setStat(1);
        List<FieldUser> list = fieldsUserDao.findUserAllBy(searchRole);
        if (list != null && list.size() > 0)
        {
            for (FieldUser fs : list)
            {
                FieldUser delUser = new FieldUser();
                delUser.setId(fs.getId());
                delUser.setStat(0);
                delUser.setLastUpdateTime(new Date());
                fieldsUserDao.updateByPrimaryKeySelective(delUser);
            }
        }
        for (String userId : usersIds.split(","))
        {
            FieldUser insertUser = new FieldUser();
            insertUser.setFieldId(fieldId);
            insertUser.setUserId(userId);
            insertUser.setProjId(projId);
            insertUser.setProcId(procId);
            insertUser.setReaderWrite(HttpConstants.FIELD_READ_PER);
            insertUser.setStat(1);
            insertUser.setId(UUIDGenerator.getUUID());
            insertUser.setCreateTime(new Date());
            fieldsUserDao.insertSelective(insertUser);
        }
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.manage.service.IFieldsService#grantWriter(java.lang.String, java.lang.String)   
    */
    public void grantWriter(String formId, String fieldId, String roleId, String projId, String procId,String ownRole)
    {
        

      if(StringUtils.isEmpty(ownRole)&&!StringUtils.isEmpty(roleId)){
          FieldRole searchRole = new FieldRole();
          searchRole.setFormId(formId);
          searchRole.setFieldId(fieldId);
          searchRole.setRoleId(roleId);
          searchRole.setProjId(projId);
          searchRole.setProcId(procId);
          searchRole.setReaderWrite(HttpConstants.FIELD_READ_PER);
          searchRole.setStat(1);
          List<FieldRole> list = fieldsRoleDao.findAllBy(searchRole);
          if (list != null && list.size() > 0)
          {
              FieldRole f = new FieldRole();
              f.setId(list.get(0).getId());
              //            f.setLastTime(new Date());
              f.setReaderWrite(HttpConstants.FIELD_WRITE_PER);
              fieldsRoleDao.updateByPrimaryKeySelective(f);
          }
          else
          {
              FieldRole fieldRole = new FieldRole();
              fieldRole.setFormId(formId);
              fieldRole.setFieldId(fieldId);
              fieldRole.setRoleId(roleId);
              fieldRole.setProjId(projId);
              fieldRole.setProcId(procId);
              fieldRole.setStat(DataStatus.ENABLED);
              fieldRole.setCreateTime(new Date());
              fieldRole.setReaderWrite(HttpConstants.FIELD_WRITE_PER);
              fieldRole.setId(UUIDGenerator.getUUID());
              fieldsRoleDao.insertFun(fieldRole);
          }
      }else{
          FieldRole ownFieldRole = new FieldRole();
          ownFieldRole.setFormId(formId);
          ownFieldRole.setFieldId(fieldId);
          ownFieldRole.setRoleId("owner");
          ownFieldRole.setProjId(projId);
          ownFieldRole.setProcId(procId);
          ownFieldRole.setStat(DataStatus.ENABLED);
          ownFieldRole.setCreateTime(new Date());
          ownFieldRole.setReaderWrite(HttpConstants.FIELD_WRITE_PER);
          ownFieldRole.setId(UUIDGenerator.getUUID());
          fieldsRoleDao.insertFun(ownFieldRole);
      }
      
      
      
      
      
      

    }

    /** 
     * (non-Javadoc)   
     * @see com.ssic.game.manage.service.IFieldsService#grantRead(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)   
     */
    public void grantRead(String formId, String fieldId, String roleId, String projId, String procId)
    {
        FieldRole fieldRole = new FieldRole();
        fieldRole.setFormId(formId);
        fieldRole.setFieldId(fieldId);
        fieldRole.setRoleId(roleId);
        fieldRole.setProjId(projId);
        fieldRole.setProcId(procId);
        fieldRole.setStat(1);
        fieldRole.setCreateTime(new Date());
        fieldRole.setReaderWrite(HttpConstants.FIELD_READ_PER);
        fieldRole.setId(UUIDGenerator.getUUID());
        fieldsRoleDao.insertFun(fieldRole);

    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.manage.service.IFieldsService#delGrant(java.lang.String, java.lang.String, java.lang.String)   
    */
    public void delGrant(String formId, String fieldId, String projId, int readWriter)
    {
        FieldRole fieldRole = new FieldRole();
        fieldRole.setFormId(formId);
        fieldRole.setFieldId(fieldId);
        fieldRole.setProjId(projId);
        fieldRole.setLastUpdateTime(new Date());
        //        fieldRole.setReaderWrite(1);
        fieldRole.setReaderWrite(readWriter);
        fieldsRoleDao.updateFun(fieldRole);
    }
    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.manage.service.IFieldsService#delGrantByUser(java.lang.String, java.lang.String, java.lang.String, int)   
    */
   @Override
   public void delGrantByUser( String fieldId, String projId, int readWriter)
   {
       FieldUser  fieldUser =new FieldUser();
       fieldUser.setFieldId(fieldId);
       fieldUser.setProjId(projId);
       fieldUser.setReaderWrite(readWriter);
       fieldUser.setStat(1);
       List<FieldUser> list = fieldsUserDao.findUserAllBy(fieldUser);
       if(list!=null&&list.size()>0){
          for(FieldUser temp : list){
              FieldUser delUser = new FieldUser();
              delUser.setId(temp.getId());
              delUser.setStat(0);
              delUser.setLastUpdateTime(new Date());
              fieldsUserDao.updateByPrimaryKeySelective(delUser);
          }
       }

       
   }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.manage.service.IFieldsService#findReadTree(java.lang.String)   
    */
    public List<Tree> findReadTree(List<String> param)
    {
        List<Tree> result = new ArrayList<Tree>();
        ImsRole params = new ImsRole();
        List<ImsRole> list = imsRoleDao.findFieldReadTree(params, param);
        if (list != null && list.size() > 0)
        {
            for (ImsRole ir : list)
            {
                Tree tree = new Tree();
                tree.setId(ir.getId());
                tree.setText(ir.getName());
                result.add(tree);
            }

        }
        return result;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.manage.service.IFieldsService#dataGrid(java.lang.String, com.ssic.game.admin.pageModel.PageHelper)   
    */
    public DataGrid dataGrid(String formId, PageHelper ph)
    {
        int beginRow = (ph.getPage() - 1) * ph.getRows();
        ph.setBeginRow(beginRow);
        PageHelperDto phDto = new PageHelperDto();
        BeanUtils.copyProperties(ph, phDto);
        FieldsDto fieldsDto = new FieldsDto();
        fieldsDto.setFormId(formId);
        List<FieldsDto> fieldDtoList = fieldsDao.findAll(fieldsDto, phDto);
        DataGrid dg = new DataGrid();
        int counts = fieldsDao.findFieldsCountByFormId(formId);

        dg.setTotal(Long.valueOf(counts));
        dg.setRows(fieldDtoList);
        return dg;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.manage.service.IFieldsService#addExistFieldDataGrid(java.lang.String, com.ssic.game.admin.pageModel.PageHelper)   
    */
    @Override
    public DataGrid addExistFieldDataGrid(FieldsDto fieldsDto, PageHelper ph)
    {

        int beginRow = (ph.getPage() - 1) * ph.getRows();
        ph.setBeginRow(beginRow);
        PageHelperDto phDto = new PageHelperDto();
        BeanUtils.copyProperties(ph, phDto);
        Forms form = formDao.selectByPrimaryKey(fieldsDto.getFormId());
        fieldsDto.setProcId(form.getProcId());
        //查找除当前表单字段外的所有字段信息;
        List<FieldsDto> fieldDtoList = fieldsDao.findExistFields(fieldsDto, phDto);
        DataGrid dg = new DataGrid();
        dg.setTotal(Long.valueOf(fieldDtoList.size()));
        dg.setRows(fieldDtoList);
        return dg;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.manage.service.IFieldsService#insert(java.lang.String, java.lang.String)   
    */
    @Override
    public void insertExistFields(String fieldId, String formId, String formFieldId)
    {

        //字段克隆表添加或者更新一条新的引用记录,返回克隆字段表的id;
        String cloneId = fieldsCloneService.insertExistFields(fieldId, formFieldId);
        //根据表单下的字段id查找字段对象，然后放入cloneId;
        Fields field = fieldsDao.selectByPrimaryKey(formFieldId);
        field.setFieldsCloneId(cloneId);
        //更新这条字段数据;
        fieldsDao.updateByPrimaryKeySelective(field);
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.manage.service.IFieldsService#clearRefrenceField(java.lang.String)   
    */
    @Override
    public void clearRefrenceField(String fieldId)
    {
        Fields field = fieldsDao.selectByPrimaryKey(fieldId);

        //字段克隆表删除当前这条字段的引用记录;
        fieldsCloneService.deleteById(field.getFieldsCloneId());
        //更新这条字段数据;
        field.setFieldsCloneId(null);
        fieldsDao.updateByPrimaryKey(field);

    }

    


}
