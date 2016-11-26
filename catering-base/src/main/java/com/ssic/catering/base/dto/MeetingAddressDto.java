package com.ssic.catering.base.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class MeetingAddressDto implements Serializable
{

    /**
     * serialVersionUID: （一句话描述这个变量表示什么）
     */

    private static final long serialVersionUID = 2976950022922191056L;
    @Getter
    @Setter
    private String id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private Integer stat;
    @Getter
    @Setter
    private Date lastUpdateTime;
    @Getter      
    @Setter
    private Date createTime;
    @Getter
    @Setter
    private String province;
    @Getter
    @Setter
    private String city;
    @Getter
    @Setter
    private String address;
    @Getter
    @Setter
    private String largeArea;
    @Getter
    @Setter
    private String largeAreaName;
    @Getter
    @Setter
    private String provinceName;
    @Getter
    @Setter
    private String cityName;
    @Getter
    @Setter
    private List<MeetingAddressDto> list;
    @Getter
    @Setter
    private String projId;
    
    /**
     * 项目名称
     */
    @Getter
    @Setter
    private String projectName;
}
