package com.ssic.catering.base.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class NotifyDto implements Serializable{
        @Getter
        @Setter
	    private String id;
        @Getter
        @Setter
	    private String name;
        @Getter
        @Setter
	    private String listTitle;
        @Getter
        @Setter
	    private String listText;
        @Getter
        @Setter
	    private String title;
        @Getter
        @Setter
	    private String text;
        @Getter
        @Setter
	    private String textNotifyImageId;
        @Getter
        @Setter
	    private String textHtml;
        @Getter
        @Setter
	    private Date notifyTime;
        @Getter
        @Setter
        private String sNotifyTime;
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
	    private Integer state;
        @Getter
        @Setter
        private Integer countNotify;//公告的总数量
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
