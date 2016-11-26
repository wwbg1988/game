/**
 * 
 */
package com.ssic.game.common.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.Getter;

import com.ssic.util.BeanUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ssic.game.common.dto.DictDto;
import com.ssic.game.common.dto.FieldDictDto;
import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.game.common.mapper.FieldDictMapper;
import com.ssic.game.common.mapper.ImsDictMapper;
import com.ssic.game.common.mapper.ProjectMapper;
import com.ssic.game.common.pojo.FieldDict;
import com.ssic.game.common.pojo.FieldDictExample;
import com.ssic.game.common.pojo.FieldDictExample.Criteria;
import com.ssic.game.common.pojo.ImsDict;
import com.ssic.game.common.pojo.Project;
import com.ssic.util.base.MyBatisBaseDao;

/**		
 * <p>Title: FieldDictDao </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年6月24日 上午9:23:55	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年6月24日 上午9:23:55</p>
 * <p>修改备注：</p>
 */
@Repository
public class FieldDictDao extends MyBatisBaseDao<FieldDict>
{

    @Getter
    @Autowired
    private FieldDictMapper mapper;

    @Getter
    @Autowired
    private ImsDictMapper imsDictmapper;

    @Getter
    @Autowired
    private ProjectMapper projectMapper;

    /**     
     * deleteByDictId：一句话描述方法功能
     * @param id
     * @exception	
     * @author 刘博
     * @date 2015年6月25日 下午5:26:39	 
     */
    public void deleteByDictId(String id)
    {
        FieldDictExample example = new FieldDictExample();
        Criteria criteria = example.createCriteria();
        example.setDistinct(true);
        criteria.andDictIdEqualTo(id);
        criteria.andStatEqualTo(1);
        List<FieldDict> fieldDicts = mapper.selectByExample(example);
        for (FieldDict fieldDict : fieldDicts)
        {
            fieldDict.setStat(0);
            mapper.updateByPrimaryKeySelective(fieldDict);
            //mapper.deleteByPrimaryKey(fieldDict.getId());
        }

    }

    /**     
     * deleteByFormId：一句话描述方法功能
     * @param id
     * @exception   
     * @author 刘博
     * @date 2015年6月25日 下午4:17:23    
     */
    public void deleteByFieldId(String id)
    {
        FieldDictExample example = new FieldDictExample();
        Criteria criteria = example.createCriteria();
        example.setDistinct(true);
        criteria.andFieldIdEqualTo(id);
        criteria.andStatEqualTo(1);
        List<FieldDict> fieldDicts = mapper.selectByExample(example);
        for (FieldDict fieldDict : fieldDicts)
        {
            fieldDict.setStat(0);
            mapper.updateByPrimaryKeySelective(fieldDict);
            //  mapper.deleteByPrimaryKey(fieldDict.getId());
        }

    }

    /**     
     * findFieldsByFieldId：一句话描述方法功能
     * @param fieldId
     * @param phDto 
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年6月25日 下午7:57:28	 
     */
    public List<DictDto> findDictsByFieldId(String fieldId, PageHelperDto ph)
    {
        List<DictDto> list = new ArrayList<DictDto>();

        FieldDictExample example = new FieldDictExample();
        Criteria criteria = example.createCriteria();

        example.setDistinct(true);
        if (ph != null && !StringUtils.isEmpty(ph.getBeginRow()) && !StringUtils.isEmpty(ph.getRows()))
        {
            int beginRow = ph.getBeginRow();
            int rows = ph.getRows();
            example.setOrderByClause("create_time desc limit " + beginRow + "," + rows);
        }
        else
        {
            example.setOrderByClause("create_time desc");
        }
        criteria.andFieldIdEqualTo(fieldId);
        criteria.andStatEqualTo(1);

        List<FieldDict> fieldDicts = mapper.selectByExample(example);
        for (FieldDict fieldDict : fieldDicts)
        {
            //根据fomField关系表中的formId查找对应的fieldId,然后根据fieldId查找对应的Fields对象;
            ImsDict dict = imsDictmapper.selectByPrimaryKey(fieldDict.getDictId());
            Project project = projectMapper.selectByPrimaryKey(dict.getProjId());
            DictDto dto = new DictDto();
            BeanUtils.copyProperties(dict, dto);
            if (project != null)
            {
                dto.setProjName(project.getProjName());
            }
            else
            {
                dto.setProjName("暂无项目");
            }
            list.add(dto);
        }
        return list;
    }

    /**     
     * vaildIsExists：一句话描述方法功能
     * @param fieldId
     * @param dictId
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年6月25日 下午7:57:56	 
     */
    public int vaildIsExists(String fieldId, String dictId)
    {
        FieldDictExample example = new FieldDictExample();
        Criteria criteria = example.createCriteria();
        example.setDistinct(true);
        criteria.andDictIdEqualTo(dictId);
        criteria.andFieldIdEqualTo(fieldId);
        criteria.andStatEqualTo(1);
        List<FieldDict> fieldDicts = mapper.selectByExample(example);
        if (fieldDicts.size() > 0)
        {
            return 1;
        }
        return 0;
    }

    /**     
     * insert：一句话描述方法功能
     * @param fieldId
     * @param dictId
     * @exception	
     * @author 刘博
     * @date 2015年6月25日 下午7:58:21	 
     */
    public void insert(String fieldId, String dictId)
    {
        FieldDict fieldDict = new FieldDict();
        fieldDict.setId(UUID.randomUUID().toString());
        fieldDict.setCreateTime(new Date());
        fieldDict.setLastUpdateTime(new Date());
        fieldDict.setFieldId(fieldId);
        fieldDict.setDictId(dictId);
        fieldDict.setStat(1);
        mapper.insert(fieldDict);

    }

    /**     
     * delete：一句话描述方法功能
     * @param fieldId
     * @param dictId
     * @exception	
     * @author 刘博
     * @date 2015年6月25日 下午7:58:42	 
     */
    public void delete(String fieldId, String dictId)
    {
        FieldDictExample example = new FieldDictExample();
        Criteria criteria = example.createCriteria();
        example.setDistinct(true);
        criteria.andDictIdEqualTo(dictId);
        criteria.andFieldIdEqualTo(fieldId);
        criteria.andStatEqualTo(1);
        List<FieldDict> fieldDicts = mapper.selectByExample(example);
        for (FieldDict fieldDict : fieldDicts)
        {
            fieldDict.setStat(0);
            //非空字段更新操作;
            mapper.updateByPrimaryKeySelective(fieldDict);

        }

    }

    /**     
     * findByDictId：一句话描述方法功能
     * @param id
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年6月26日 下午8:56:34	 
     */
    public List<FieldDictDto> findByDictId(String id)
    {
        List<FieldDictDto> dtoList = new ArrayList<FieldDictDto>();
        FieldDictExample example = new FieldDictExample();
        Criteria criteria = example.createCriteria();
        example.setDistinct(true);
        criteria.andDictIdEqualTo(id);
        criteria.andStatEqualTo(1);
        List<FieldDict> fieldDicts = mapper.selectByExample(example);
        dtoList = BeanUtils.createBeanListByTarget(fieldDicts, FieldDictDto.class);
        return dtoList;
    }

    /**     
     * findDictsCountByFieldId：一句话描述方法功能
     * @param fieldId
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年7月7日 上午11:31:19	 
     */
    public int findDictsCountByFieldId(String fieldId)
    {
        FieldDictExample example = new FieldDictExample();
        Criteria criteria = example.createCriteria();
        example.setDistinct(true);
        criteria.andFieldIdEqualTo(fieldId);
        criteria.andStatEqualTo(1);
        int count = mapper.countByExample(example);
        return count;
    }
}
