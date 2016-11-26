package com.ssic.catering.base.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssic.catering.base.dto.ConfigScoreValveConfDto;
import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.pojo.ConfigScoreValveConf;

/**
 * 		
 * <p>Title: ConfigScoreValveConfService </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年8月10日 上午10:54:44	
 * @version 1.0
 * <p>修改人：pengcheng.yang</p>
 * <p>修改时间：2015年8月10日 上午10:54:44</p>
 * <p>修改备注：</p>
 */
public interface IConfigScoreValveConfService
{
    List<ConfigScoreValveConfDto> queryConfigId(String cafetoriumId);
    int insertConfScoreValue(ConfigScoreValveConfDto Dto);
    int updateConfScoreValue(ConfigScoreValveConfDto Dto);
    int deleteConfScoreValue(ConfigScoreValveConfDto Dto);
    List<ConfigScoreValveConfDto> findBy(ConfigScoreValveConfDto param,List<String> listStr,PageHelperDto ph);
    int findCountBy(List<String> list);
    ConfigScoreValveConf getConfigConfById(String id);
    int deleteConfigConfById(ConfigScoreValveConfDto conf);
    int updateConfigConfById(ConfigScoreValveConfDto conf);
}

