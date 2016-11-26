package com.ssic.game.app.controller.catering;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.catering.base.dto.VersionDto;
import com.ssic.catering.base.service.IVersionService;
import com.ssic.catering.common.util.Json;

@Controller
@RequestMapping("/appVersionController")
public class AppVersionController {

	@Autowired
	private IVersionService versionService;
	
	@RequestMapping("/findBy")
	@ResponseBody
	public Json findBy (VersionDto versionDto){
		Json json = new Json();
		if(versionDto.getVersiontype()==null){
		    	json.setMsg("版本类型不能为空");
		    	json.setSuccess(false);
		    	return json;
		}
		List<VersionDto> list = versionService.findBy(versionDto);
		
		if(CollectionUtils.isEmpty(list)){
			json.setMsg("版本信息为空");
			json.setSuccess(false);
		}else{
			json.setMsg("查询版本信息成功");
			json.setObj(list.get(0));
			json.setSuccess(true);
		}

		return json;
		
	}
	
	
	
}
