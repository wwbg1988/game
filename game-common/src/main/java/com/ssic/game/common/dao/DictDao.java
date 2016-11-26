/**
 * 
 */
package com.ssic.game.common.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ssic.game.common.dto.DictDto;
import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.game.common.mapper.ImsDictMapper;
import com.ssic.game.common.pojo.ImsDict;
import com.ssic.game.common.pojo.ImsDictExample;
import com.ssic.game.common.pojo.ImsDictExample.Criteria;
import com.ssic.util.base.MyBatisBaseDao;

/**		
 * <p>Title: DictDao </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年6月25日 下午2:38:32	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年6月25日 下午2:38:32</p>
 * <p>修改备注：</p>
 */
@Repository
public class DictDao extends MyBatisBaseDao<ImsDict>
{
    @Getter
    @Autowired
    private ImsDictMapper mapper;

    @Autowired
    private FieldDictDao fieldDictDao;

    /**     
     * findAll：一句话描述方法功能
     * @param dictDto
     * @param ph 
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年6月25日 下午5:19:24	 
     */
    public List<DictDto> findAll(DictDto dictDto, PageHelperDto ph)
    {
        ImsDictExample example = new ImsDictExample();
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
        criteria.andStatEqualTo(1);
        if (!StringUtils.isEmpty(dictDto.getFieldDesc()))
        {
            criteria.andFieldDescLike("%" + dictDto.getFieldDesc() + "%");
        }
        if (!StringUtils.isEmpty(dictDto.getFieldValue()))
        {
            criteria.andFieldValueLike("%" + dictDto.getFieldValue() + "%");
        }

        List<DictDto> list = new ArrayList<DictDto>();

        List<ImsDict> dicts = mapper.selectByExample(example);
        for (ImsDict dict : dicts)
        {
            DictDto dto = new DictDto();
            BeanUtils.copyProperties(dict, dto);
            list.add(dto);
        }

        return list;
    }

    /**     
     * findCountBy：一句话描述方法功能
     * @param dictDto
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年6月25日 下午5:19:31	 
     */
    public int findCountBy(DictDto dictDto)
    {
        ImsDictExample example = new ImsDictExample();
        Criteria criteria = example.createCriteria();
        example.setDistinct(true);
        criteria.andStatEqualTo(1);
        if (!StringUtils.isEmpty(dictDto.getFieldDesc()))
        {
            criteria.andFieldDescLike("%" + dictDto.getFieldDesc() + "%");
        }
        if (!StringUtils.isEmpty(dictDto.getFieldValue()))
        {
            criteria.andFieldValueLike("%" + dictDto.getFieldValue() + "%");
        }
        int count = mapper.countByExample(example);
        return count;
    }

    /**     
       * insert：一句话描述方法功能
       * @param dictDto
       * @exception	
       * @author 刘博
       * @date 2015年6月25日 下午5:20:43	 
       */
    public void insert(DictDto dictDto)
    {
        ImsDict dict = new ImsDict();
        BeanUtils.copyProperties(dictDto, dict);
        dict.setStat(1);
        dict.setLastUpdateTime(new Date());
        dict.setCreateTime(new Date());
        // dict.setId(UUID.randomUUID().toString());
        mapper.insert(dict);

    }

    /**     
        * updateStat：一句话描述方法功能
        * @param id
        * @exception	
        * @author 刘博
        * @date 2015年6月25日 下午5:21:00	 
        */
    public void updateStat(String id)
    {
        if (id != null)
        {
            ImsDict dict = mapper.selectByPrimaryKey(id);
            dict.setStat(0);
            mapper.updateByPrimaryKeySelective(dict);
        }

    }

    /**     
     * updateField：一句话描述方法功能
     * @param dictDto
     * @exception	
     * @author 刘博
     * @date 2015年6月25日 下午5:21:08	 
     */
    public void updateDict(DictDto dictDto)
    {
        if (dictDto.getId() != null)
        {
            ImsDict dict = new ImsDict();
            BeanUtils.copyProperties(dictDto, dict);
            dict.setLastUpdateTime(new Date());
            mapper.updateByPrimaryKeySelective(dict);
        }

    }
}
