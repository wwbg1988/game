package com.ssic.catering.common.util;

public interface AppResponse
{

    //返回成功
    static final int RETURN_SUCCESS = 200;
    //获取新闻列表失败
    static final int SEND_NEWS_FAILE = 300;
    //上传成功
    static final int UPLOAD_SUCCESS = 400;
    //上传失败
    static final int UPLOAD_FAILE = 550;
    //返回失败
    static final int RETURN_FAILE = 500;
    //会议室没人预约
    static final int MEETING_NO_ONE = 600;
    //会议室有人预约但是时间不交叉
    static final int MEETING_TIME_NOT_CROSSING = 610;
    //会议室有人预约时间交叉
    static final int MEETING_TIME_CROSSING = 620;
    //"员工菜单建议 清单
    static final String CARTE_ADVICE = "供餐点";
    //供餐点
    static final String FOR_MEALS = "员工菜单建议 清单";
    //返回菜品百分比成功
    static final String RETURN_CARTE_PERCENT_SUCCESS = "返回菜品百分比成功";
    //敏感词预警url
    static final String SENSITIVE_WARNING_URL = "App/pie.html";
    //该用户不能看到会议记录的部门列表
    static final int M_IS_NOT_DEPT = 310;
    //该用户能看到会议记录的部门列表
    static final int M_IS_DEPT = 320;
    //该用户不能看到会议记录的全部列表
    static final int M_IS_NOT_ALL = 330;
    //该用户能看到会议记录的全部列表
    static final int M_IS_ALL = 340;
}
