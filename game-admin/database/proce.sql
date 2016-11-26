#t_ims_project  项目表
DROP TABLE IF EXISTS  `t_ims_project`;
CREATE TABLE `t_ims_project` (
`id`  varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`proj_name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目名称',
`describe`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目描述',
`stat` int(11)  NOT NULL COMMENT '0:无效 1：有效',
`last_time`  datetime NULL DEFAULT NULL COMMENT '最后更新日期',
`create_time`  datetime NULL DEFAULT NULL,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=COMPACT
;

#流程表t_ims_process
DROP TABLE IF EXISTS  `t_ims_process`;
CREATE TABLE `t_ims_process` (
`id`  varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`proj_id` int(11)  NOT NULL COMMENT  '所属项目编号',
`proc_name`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程名称',
`describe`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程描述',
`isDefine` int(11)  NOT NULL COMMENT '流程是否定义完整 0否 1是 完整的流程可使用 ',
`start_task` int(11)  NOT NULL COMMENT '流程开始节点',
`stat` int(11)  NOT NULL COMMENT '0:无效 1：有效',
`start_date`  datetime NULL DEFAULT NULL COMMENT '流程开始日期',
`end_date`  datetime NULL DEFAULT NULL COMMENT '流程结束日期',
`state` int(11)  NOT NULL COMMENT '流程状态 1未开始 2赛中进行 3 已结束 ',
`last_time`  datetime NULL DEFAULT NULL COMMENT '最后更新日期',
`create_time`  datetime NULL DEFAULT NULL,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=COMPACT
;


# 流程实例表t_ims_proc_instance
DROP TABLE IF EXISTS  `t_ims_proc_instance`;
CREATE TABLE `t_ims_proc_instance` (
`id`  varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`proj_id`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属项目编号',
`proc_id`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属流程编号',
`now_task_id` int(11)  NOT NULL COMMENT '流程当前节点',
`stat` int(11)  NOT NULL COMMENT '0:无效 1：有效',
`retire_date`  datetime NULL DEFAULT NULL COMMENT '办理截至日期',
`last_time`  datetime NULL DEFAULT NULL COMMENT '最后更新日期',
`create_time`  datetime NULL DEFAULT NULL,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=COMPACT
;

#任务流程节点t_ims_tasks
DROP TABLE IF EXISTS  `t_ims_tasks`;
CREATE TABLE `t_ims_tasks` (
`id`  varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`name`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务节点名称',
`proj_id`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属项目编号',
`form_id`  int(11)  NOT NULL COMMENT '任务节点对应表单',
`action_id`  int(11)  NOT NULL COMMENT '任务节点对应动作',
`type` int(11)  NOT NULL COMMENT '1开始节点 2任务节点 3结束',
`stat` int(11)  NOT NULL COMMENT '0:无效 1：有效',
`state`  int(11)  NOT NULL COMMENT '节点状态 0—未激活  1—办理中(激活) 2—已办 3-否决 4-退回 前驱节点已办后当前节点才进入激活状态用户可见',
`pre_task_id`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '前驱流程节点编号',
`countersign` int(11)  NOT NULL COMMENT '是否会签，0否、1是 如果是会签节点 需要所有拥有办理权限全部办理 该节点状态才能变为已办',
`last_time`  datetime NULL DEFAULT NULL COMMENT '最后更新日期',
`create_time`  datetime NULL DEFAULT NULL COMMENT '操作日期',
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=COMPACT
;

#t_ims_forms 表单表
DROP TABLE IF EXISTS  `t_ims_forms`;
CREATE TABLE `t_ims_forms` (
`id`  varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`name`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表单名称',
`proj_id` int(11)  NOT NULL COMMENT  '所属项目编号',
`task_id`  int(11)  NOT NULL COMMENT '表单对应任务节点',
`field_id` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '表单对应字段 多个|隔开',
`type` int(11)  NOT NULL COMMENT '表单类型 1单人表单、2多人表单、3重复提交表单',
`stat` int(11)  NOT NULL COMMENT '0:无效 1：有效',
`last_time`  datetime NULL DEFAULT NULL COMMENT '最后更新日期',
`create_time`  datetime NULL DEFAULT NULL COMMENT '添加日期',
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=COMPACT
;


#表单字段表t_ims_fields
DROP TABLE IF EXISTS  `t_ims_fields`;
CREATE TABLE `t_ims_fields` (
`id`  varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`form_id` int(11)  NOT NULL COMMENT '所属表单编号',
`proj_id` int(11)  NOT NULL COMMENT  '所属项目编号',
`param_desc`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数描述',
`param_name`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字段参数名称 与ID同名 唯一',
`field_value`  varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字段值',
`field_desc`  varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字段显示描述',
`pattern`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '校验格式 正则校验',
`isEncrypt`  int(11)  NOT NULL  COMMENT '字段值是否加密 0否 1是',
`isUniline`  int(11)  NOT NULL  COMMENT '是否独占一行 0是 1否',
`isDIY`  int(11)  NOT NULL  COMMENT '是否为自定义字段 0是 1否 自定义字段可重复添加 规则为字段名+自增编号 1开始 主要针对text、checkbox、radio',
`length`  int(11)  NOT NULL  COMMENT '长度',
`height`  int(11)  NOT NULL  COMMENT '高度',
`type` int(11)  NOT NULL COMMENT '字段类型 1:text 2:checkbox  3:hidden 4:img 5:password 6:radio 7:reset 8:file 9:label 10:select 11:textarea',
`data_type` int(11)  NOT NULL COMMENT '字段数据类型 1:int 2:string 3:float 4:date',
`isNeed` int(11)  NOT NULL COMMENT '是否必填 0否、1是 ',
`stat` int(11)  NOT NULL COMMENT '0:无效 1：有效',
`last_time`  datetime NULL DEFAULT NULL COMMENT '修改日期',
`create_time`  datetime NULL DEFAULT NULL COMMENT '添加日期',
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=COMPACT
;

#表单字段字典t_ims_fields_dict 主要针对select、checkbox、radio的预设值
DROP TABLE IF EXISTS  `t_ims_fields_dict`;
CREATE TABLE `t_ims_fields_dict` (
`id`  varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`form_id` int(11)  NOT NULL COMMENT '所属表单编号',
`field_id` int(11)  NOT NULL COMMENT '所属字段编号',
`proj_id` int(11)  NOT NULL COMMENT  '所属项目编号',
`param_desc`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数描述',
`param_name`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字段参数名称 与ID同名 唯一',
`field_value`  varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字段值',
`field_desc`  varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字段显示描述',
`stat` int(11)  NOT NULL COMMENT '0:无效 1：有效',
`last_time`  datetime NULL DEFAULT NULL COMMENT '修改日期',
`create_time`  datetime NULL DEFAULT NULL COMMENT '添加日期',
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=COMPACT
;

#表单字段权限表t_ims_field_rights
DROP TABLE IF EXISTS  `t_ims_field_rights`;
CREATE TABLE `t_ims_field_rights` (
`id`  varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`form_id` int(11)  NOT NULL COMMENT '所属表单编号',
`proj_id` int(11)  NOT NULL COMMENT  '所属项目编号',
`task_id`  int(11)  NOT NULL COMMENT '字段权限对应任务节点',
`field_no` int(11)  NOT NULL COMMENT '字段编号',
`field_user_no`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字段对应权限人员的帐号 |隔开',
`type` int(11)  NOT NULL COMMENT '权限类型 0只读 1可写',
`stat` int(11)  NOT NULL COMMENT '0:无效 1：有效',
`last_time`  datetime NULL DEFAULT NULL COMMENT '修改日期',
`create_time`  datetime NULL DEFAULT NULL COMMENT '添加日期',
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=COMPACT
;

#表单动作权限表t_ims_actions
DROP TABLE IF EXISTS  `t_ims_actions`;
CREATE TABLE `t_ims_actions` (
`id`  varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`task_id`  int(11)  NOT NULL COMMENT '字段权限对应任务节点',
`proj_id` int(11)  NOT NULL COMMENT  '所属项目编号',
`type`  int(11)  NOT NULL COMMENT '动作类型 1:发起/启动 2:办理 3:退回 4:办结 5：自定义动作',
`action_url`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '自定义动作url',
`stat` int(11)  NOT NULL COMMENT '0:无效 1：有效',
`last_time`  datetime NULL DEFAULT NULL COMMENT '修改日期',
`create_time`  datetime NULL DEFAULT NULL COMMENT '添加日期',
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=COMPACT
;


#表单字段权限用户表t_ims_field_users
DROP TABLE IF EXISTS  `t_ims_field_users`;
CREATE TABLE `t_ims_field_users` (
`id`  varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`proj_id` int(11)  NOT NULL COMMENT  '所属项目编号',
`field_rights_id` int(11)  NOT NULL COMMENT '对应字段权限编号',
`form_id` int(11)  NOT NULL COMMENT '所属表单编号',
`role_id`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字段权限关联用户帐号',
`stat` int(11)  NOT NULL COMMENT '0:无效 1：有效',
`last_time`  datetime NULL DEFAULT NULL COMMENT '修改日期',
`create_time`  datetime NULL DEFAULT NULL COMMENT '添加日期',
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=COMPACT
;


#表单动作权限用户表t_ims_action_users
DROP TABLE IF EXISTS  `t_ims_action_users`;
CREATE TABLE `t_ims_action_users` (
`id`  varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`proj_id` int(11)  NOT NULL COMMENT  '所属项目编号',
`action_id` int(11)  NOT NULL COMMENT '对应表单动作权限编号',
`role_id`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '动作权限关联用户角色',
`stat` int(11)  NOT NULL COMMENT '0:无效 1：有效',
`last_time`  datetime NULL DEFAULT NULL COMMENT '修改日期',
`create_time`  datetime NULL DEFAULT NULL COMMENT '添加日期',
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=COMPACT
;

#项目权限用户表t_ims_project_users
DROP TABLE IF EXISTS  `t_ims_project_users`;
CREATE TABLE `t_ims_project_users` (
`id`  varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`role_id`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目权限关联用户角色',
`proj_id` int(11)  NOT NULL COMMENT  '所属项目编号',
`last_time`  datetime NULL DEFAULT NULL COMMENT '修改日期',
`create_time`  datetime NULL DEFAULT NULL COMMENT '添加日期',
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=COMPACT
;