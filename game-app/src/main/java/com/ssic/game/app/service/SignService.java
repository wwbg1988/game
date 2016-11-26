/**
 * 
 */
package com.ssic.game.app.service;

import java.text.ParseException;
import java.util.List;

import com.ssic.catering.base.dto.SignDeptUserDto;
import com.ssic.game.common.dto.SignDto;
import com.ssic.game.common.pojo.Sign;
import com.ssic.util.model.Response;

/**		
 * <p>Title: SignService </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年8月10日 下午1:18:01	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年8月10日 下午1:18:01</p>
 * <p>修改备注：</p>
 */
public interface SignService
{
    public Response<SignDto> insert(SignDto signDto);
    
    public Response<SignDeptUserDto> find(SignDto signDto,int beginRows,int endRows) throws ParseException;
    
    public Response<List<SignDto>> findAllSign(SignDto signDto,int beginRows,int endRows) throws ParseException;
    
}

