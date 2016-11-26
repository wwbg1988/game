package com.ssic.game.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssic.game.admin.dto.TImsMenuDto;
import com.ssic.game.admin.pojo.Menu;

public interface TImsMenuExMapper {

	TImsMenuDto findBy(@Param("menuId") String menuId);

	Menu findById(@Param("menuId") String menuId);

	List<Menu> findChildMenuById(@Param("menuId") String id);

	void updateDeleteStat(@Param("menuId") String id);

	void insertBy(@Param("menu") Menu menu);

	void updateMenu(@Param("menu") Menu menu);

    
    /**     
     * findByRoleIdList：一句话描述方法功能
     * @param roles
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年7月31日 下午5:56:59	 
     */
    List<Menu> findByUserId(@Param("userId")String userId);

}
