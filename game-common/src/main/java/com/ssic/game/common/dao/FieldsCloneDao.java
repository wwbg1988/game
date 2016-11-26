/**
 * 
 */
package com.ssic.game.common.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssic.game.common.mapper.FieldsMapper;
import com.ssic.game.common.mapper.FiledsCloneMapper;
import com.ssic.game.common.pojo.FiledsCloneExample.Criteria;
import com.ssic.game.common.pojo.FiledsClone;
import com.ssic.game.common.pojo.FiledsCloneExample;
import com.ssic.util.base.MyBatisBaseDao;

/**		
 * <p>Title: FiledsCloneDao </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年6月28日 上午9:35:23	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年6月28日 上午9:35:23</p>
 * <p>修改备注：</p>
 */

@Repository
public class FieldsCloneDao extends MyBatisBaseDao<FiledsClone>
{

    @Getter
    @Autowired
    private FiledsCloneMapper mapper;
    
    
    @Autowired
    private FieldsMapper fieldMapper;

    
    public  List<FiledsClone> findByFieldId(String fieldId)
    {
        FiledsCloneExample example = new FiledsCloneExample();
        Criteria criteria = example.createCriteria();
        example.setDistinct(true);
        criteria.andStatEqualTo(1);
        criteria.andFieldsIdEqualTo(fieldId);
        List<FiledsClone> list = mapper.selectByExample(example);
        if (list != null && list.size() > 0)
        {
            return list;
        }
        return null;
    }
    
}

