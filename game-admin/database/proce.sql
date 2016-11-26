#t_ims_project  ��Ŀ��
DROP TABLE IF EXISTS  `t_ims_project`;
CREATE TABLE `t_ims_project` (
`id`  varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`proj_name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '��Ŀ����',
`describe`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '��Ŀ����',
`stat` int(11)  NOT NULL COMMENT '0:��Ч 1����Ч',
`last_time`  datetime NULL DEFAULT NULL COMMENT '����������',
`create_time`  datetime NULL DEFAULT NULL,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=COMPACT
;

#���̱�t_ims_process
DROP TABLE IF EXISTS  `t_ims_process`;
CREATE TABLE `t_ims_process` (
`id`  varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`proj_id` int(11)  NOT NULL COMMENT  '������Ŀ���',
`proc_name`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '��������',
`describe`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '��������',
`isDefine` int(11)  NOT NULL COMMENT '�����Ƿ������� 0�� 1�� ���������̿�ʹ�� ',
`start_task` int(11)  NOT NULL COMMENT '���̿�ʼ�ڵ�',
`stat` int(11)  NOT NULL COMMENT '0:��Ч 1����Ч',
`start_date`  datetime NULL DEFAULT NULL COMMENT '���̿�ʼ����',
`end_date`  datetime NULL DEFAULT NULL COMMENT '���̽�������',
`state` int(11)  NOT NULL COMMENT '����״̬ 1δ��ʼ 2���н��� 3 �ѽ��� ',
`last_time`  datetime NULL DEFAULT NULL COMMENT '����������',
`create_time`  datetime NULL DEFAULT NULL,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=COMPACT
;


# ����ʵ����t_ims_proc_instance
DROP TABLE IF EXISTS  `t_ims_proc_instance`;
CREATE TABLE `t_ims_proc_instance` (
`id`  varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`proj_id`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '������Ŀ���',
`proc_id`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '�������̱��',
`now_task_id` int(11)  NOT NULL COMMENT '���̵�ǰ�ڵ�',
`stat` int(11)  NOT NULL COMMENT '0:��Ч 1����Ч',
`retire_date`  datetime NULL DEFAULT NULL COMMENT '�����������',
`last_time`  datetime NULL DEFAULT NULL COMMENT '����������',
`create_time`  datetime NULL DEFAULT NULL,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=COMPACT
;

#�������̽ڵ�t_ims_tasks
DROP TABLE IF EXISTS  `t_ims_tasks`;
CREATE TABLE `t_ims_tasks` (
`id`  varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`name`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '����ڵ�����',
`proj_id`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '������Ŀ���',
`form_id`  int(11)  NOT NULL COMMENT '����ڵ��Ӧ��',
`action_id`  int(11)  NOT NULL COMMENT '����ڵ��Ӧ����',
`type` int(11)  NOT NULL COMMENT '1��ʼ�ڵ� 2����ڵ� 3����',
`stat` int(11)  NOT NULL COMMENT '0:��Ч 1����Ч',
`state`  int(11)  NOT NULL COMMENT '�ڵ�״̬ 0��δ����  1��������(����) 2���Ѱ� 3-��� 4-�˻� ǰ���ڵ��Ѱ��ǰ�ڵ�Ž��뼤��״̬�û��ɼ�',
`pre_task_id`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ǰ�����̽ڵ���',
`countersign` int(11)  NOT NULL COMMENT '�Ƿ��ǩ��0��1�� ����ǻ�ǩ�ڵ� ��Ҫ����ӵ�а���Ȩ��ȫ������ �ýڵ�״̬���ܱ�Ϊ�Ѱ�',
`last_time`  datetime NULL DEFAULT NULL COMMENT '����������',
`create_time`  datetime NULL DEFAULT NULL COMMENT '��������',
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=COMPACT
;

#t_ims_forms ����
DROP TABLE IF EXISTS  `t_ims_forms`;
CREATE TABLE `t_ims_forms` (
`id`  varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`name`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '������',
`proj_id` int(11)  NOT NULL COMMENT  '������Ŀ���',
`task_id`  int(11)  NOT NULL COMMENT '����Ӧ����ڵ�',
`field_id` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '����Ӧ�ֶ� ���|����',
`type` int(11)  NOT NULL COMMENT '������ 1���˱���2���˱���3�ظ��ύ��',
`stat` int(11)  NOT NULL COMMENT '0:��Ч 1����Ч',
`last_time`  datetime NULL DEFAULT NULL COMMENT '����������',
`create_time`  datetime NULL DEFAULT NULL COMMENT '�������',
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=COMPACT
;


#���ֶα�t_ims_fields
DROP TABLE IF EXISTS  `t_ims_fields`;
CREATE TABLE `t_ims_fields` (
`id`  varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`form_id` int(11)  NOT NULL COMMENT '���������',
`proj_id` int(11)  NOT NULL COMMENT  '������Ŀ���',
`param_desc`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '��������',
`param_name`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '�ֶβ������� ��IDͬ�� Ψһ',
`field_value`  varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '�ֶ�ֵ',
`field_desc`  varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '�ֶ���ʾ����',
`pattern`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'У���ʽ ����У��',
`isEncrypt`  int(11)  NOT NULL  COMMENT '�ֶ�ֵ�Ƿ���� 0�� 1��',
`isUniline`  int(11)  NOT NULL  COMMENT '�Ƿ��ռһ�� 0�� 1��',
`isDIY`  int(11)  NOT NULL  COMMENT '�Ƿ�Ϊ�Զ����ֶ� 0�� 1�� �Զ����ֶο��ظ���� ����Ϊ�ֶ���+������� 1��ʼ ��Ҫ���text��checkbox��radio',
`length`  int(11)  NOT NULL  COMMENT '����',
`height`  int(11)  NOT NULL  COMMENT '�߶�',
`type` int(11)  NOT NULL COMMENT '�ֶ����� 1:text 2:checkbox  3:hidden 4:img 5:password 6:radio 7:reset 8:file 9:label 10:select 11:textarea',
`data_type` int(11)  NOT NULL COMMENT '�ֶ��������� 1:int 2:string 3:float 4:date',
`isNeed` int(11)  NOT NULL COMMENT '�Ƿ���� 0��1�� ',
`stat` int(11)  NOT NULL COMMENT '0:��Ч 1����Ч',
`last_time`  datetime NULL DEFAULT NULL COMMENT '�޸�����',
`create_time`  datetime NULL DEFAULT NULL COMMENT '�������',
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=COMPACT
;

#���ֶ��ֵ�t_ims_fields_dict ��Ҫ���select��checkbox��radio��Ԥ��ֵ
DROP TABLE IF EXISTS  `t_ims_fields_dict`;
CREATE TABLE `t_ims_fields_dict` (
`id`  varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`form_id` int(11)  NOT NULL COMMENT '���������',
`field_id` int(11)  NOT NULL COMMENT '�����ֶα��',
`proj_id` int(11)  NOT NULL COMMENT  '������Ŀ���',
`param_desc`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '��������',
`param_name`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '�ֶβ������� ��IDͬ�� Ψһ',
`field_value`  varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '�ֶ�ֵ',
`field_desc`  varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '�ֶ���ʾ����',
`stat` int(11)  NOT NULL COMMENT '0:��Ч 1����Ч',
`last_time`  datetime NULL DEFAULT NULL COMMENT '�޸�����',
`create_time`  datetime NULL DEFAULT NULL COMMENT '�������',
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=COMPACT
;

#���ֶ�Ȩ�ޱ�t_ims_field_rights
DROP TABLE IF EXISTS  `t_ims_field_rights`;
CREATE TABLE `t_ims_field_rights` (
`id`  varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`form_id` int(11)  NOT NULL COMMENT '���������',
`proj_id` int(11)  NOT NULL COMMENT  '������Ŀ���',
`task_id`  int(11)  NOT NULL COMMENT '�ֶ�Ȩ�޶�Ӧ����ڵ�',
`field_no` int(11)  NOT NULL COMMENT '�ֶα��',
`field_user_no`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '�ֶζ�ӦȨ����Ա���ʺ� |����',
`type` int(11)  NOT NULL COMMENT 'Ȩ������ 0ֻ�� 1��д',
`stat` int(11)  NOT NULL COMMENT '0:��Ч 1����Ч',
`last_time`  datetime NULL DEFAULT NULL COMMENT '�޸�����',
`create_time`  datetime NULL DEFAULT NULL COMMENT '�������',
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=COMPACT
;

#������Ȩ�ޱ�t_ims_actions
DROP TABLE IF EXISTS  `t_ims_actions`;
CREATE TABLE `t_ims_actions` (
`id`  varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`task_id`  int(11)  NOT NULL COMMENT '�ֶ�Ȩ�޶�Ӧ����ڵ�',
`proj_id` int(11)  NOT NULL COMMENT  '������Ŀ���',
`type`  int(11)  NOT NULL COMMENT '�������� 1:����/���� 2:���� 3:�˻� 4:��� 5���Զ��嶯��',
`action_url`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '�Զ��嶯��url',
`stat` int(11)  NOT NULL COMMENT '0:��Ч 1����Ч',
`last_time`  datetime NULL DEFAULT NULL COMMENT '�޸�����',
`create_time`  datetime NULL DEFAULT NULL COMMENT '�������',
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=COMPACT
;


#���ֶ�Ȩ���û���t_ims_field_users
DROP TABLE IF EXISTS  `t_ims_field_users`;
CREATE TABLE `t_ims_field_users` (
`id`  varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`proj_id` int(11)  NOT NULL COMMENT  '������Ŀ���',
`field_rights_id` int(11)  NOT NULL COMMENT '��Ӧ�ֶ�Ȩ�ޱ��',
`form_id` int(11)  NOT NULL COMMENT '���������',
`role_id`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '�ֶ�Ȩ�޹����û��ʺ�',
`stat` int(11)  NOT NULL COMMENT '0:��Ч 1����Ч',
`last_time`  datetime NULL DEFAULT NULL COMMENT '�޸�����',
`create_time`  datetime NULL DEFAULT NULL COMMENT '�������',
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=COMPACT
;


#������Ȩ���û���t_ims_action_users
DROP TABLE IF EXISTS  `t_ims_action_users`;
CREATE TABLE `t_ims_action_users` (
`id`  varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`proj_id` int(11)  NOT NULL COMMENT  '������Ŀ���',
`action_id` int(11)  NOT NULL COMMENT '��Ӧ������Ȩ�ޱ��',
`role_id`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '����Ȩ�޹����û���ɫ',
`stat` int(11)  NOT NULL COMMENT '0:��Ч 1����Ч',
`last_time`  datetime NULL DEFAULT NULL COMMENT '�޸�����',
`create_time`  datetime NULL DEFAULT NULL COMMENT '�������',
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=COMPACT
;

#��ĿȨ���û���t_ims_project_users
DROP TABLE IF EXISTS  `t_ims_project_users`;
CREATE TABLE `t_ims_project_users` (
`id`  varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`role_id`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '��ĿȨ�޹����û���ɫ',
`proj_id` int(11)  NOT NULL COMMENT  '������Ŀ���',
`last_time`  datetime NULL DEFAULT NULL COMMENT '�޸�����',
`create_time`  datetime NULL DEFAULT NULL COMMENT '�������',
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=COMPACT
;