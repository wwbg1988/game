package com.ssic.game.common.constant;

public interface QueryConditionType {
	//自定义查询条件
	
	//等于=
    static final int EQ =1;
    //小于
    static final int LT = 2;
    //小于等于
    static final int LTE = 3;
    //大于
    static final int GT = 4;
    //大于等于
    static final int GTE = 5;
    //like
    static final int LIKE =6;
    //排序倒序
    static final int ORDERBY_DESC =7;
    //排序正序
    static final int ORDERBY_ASC =8;
    
    
    
}
