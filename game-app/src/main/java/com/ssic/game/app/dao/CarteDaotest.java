package com.ssic.game.app.dao;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mysql.fabric.xmlrpc.base.Data;
import com.ssic.catering.base.dto.CarteDto;
import com.ssic.catering.base.mapper.CarteMapper;
import com.ssic.catering.base.pojo.Carte;
import com.ssic.catering.base.pojo.CarteExample;
import com.ssic.util.DateUtils;

/**
 * 菜单数据交互
 * 
 * @author 张亚伟
 *
 */
@Repository
public class CarteDaotest {

	@Autowired
	private CarteMapper mapper;

	/**
	 * 通过当年周数查询菜品列表
	 * 
	 * @param carteDto
	 * @return
	 */
	public List<Carte> getCarteListByCarteWeek(CarteDto carteDto) {
		// 获取今年的第一天和最后一天
		Date startDay = DateUtils.StringToDate(
				DateUtils.format(new Date(), DateUtils.Y) + "-01-01",
				DateUtils.YMD_DASH);
		Date endDay = DateUtils.StringToDate(
				DateUtils.format(new Date(), DateUtils.Y) + "-12-31",
				DateUtils.YMD_DASH);

		CarteExample example = new CarteExample();
		CarteExample.Criteria criteria = example.createCriteria();
		criteria.andCafetoriumIdEqualTo(carteDto.getCafetoriumId());
		criteria.andCreateTimeBetween(startDay, endDay);
		criteria.andCarteWeekEqualTo(carteDto.getCarteWeek());
		criteria.andStatEqualTo(1);

		return mapper.selectByExample(example);
	}

}
