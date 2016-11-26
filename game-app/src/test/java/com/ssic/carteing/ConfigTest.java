/**
 * 
 */
package com.ssic.carteing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ssic.catering.base.mapper.CommentExMapper;
import com.ssic.catering.base.mapper.CommentSensitiveExMapper;
import com.ssic.catering.base.mapper.ConfigScoreExMapper;
import com.ssic.catering.base.mapper.ConfigScoreMapper;
import com.ssic.catering.base.mapper.SensitiveMapper;
import com.ssic.catering.base.pojo.Comment;
import com.ssic.game.app.test.BaseTestCase;
import com.ssic.util.DateUtils;

/**		
 * <p>Title: NextCarteStatisticsTest </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年8月8日 下午1:36:25	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年8月8日 下午1:36:25</p>
 * <p>修改备注：</p>
 */
public class ConfigTest extends BaseTestCase {
	protected static final Log logger = LogFactory.getLog(ConfigTest.class);
	@Autowired
	private CommentSensitiveExMapper exMapper;

	@Autowired
	@Getter
	private SensitiveMapper mapper;

	@Test
	public void nextCarteStatisticsTest() {

		System.out.println("=========================================="+mapper.selectByPrimaryKey("3").getSensitiveName());
		
	}

	public static void main(String[] args) {
		System.out.println(DateUtils.StringToDate(
				DateUtils.format(new Date(), "yyy-MM-dd") + " 00:00",
				"yyyy-MM-dd HH:mm"));
		System.out.println(DateUtils.StringToDate(
				DateUtils.format(new Date(), "yyy-MM-dd") + " 23:59",
				"yyyy-MM-dd HH:mm"));
	}
}
