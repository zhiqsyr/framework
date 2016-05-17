-- Excel 报告整体配置
CREATE TABLE excel_report_config
	(
		id               VARCHAR (36) NOT NULL ,
		report_name      NVARCHAR (100) NOT NULL,	-- 报告名称，随便填写，不用于解析
		table_name       VARCHAR (50) NOT NULL,		-- 导入到哪张表
		
		sheet_name       NVARCHAR (50),	-- 需要解析的sheet页名称，不填解析第一个
		start_row        INT,			-- 开始解析行号，取Excel行号
		end_row          INT,			-- 截止解析行号，<1表示解析sheet所有行，建议设值，提高性能
		start_col        INT,			-- 开始解析列号，取Excel列号
		end_col          INT,			-- 截止解析列号，<1表示解析sheet所有列，建议设值，提高性能	
		serial_num_col   VARCHAR (10),	-- 字面意思，序号所在列号，引申：假如该列某行值空白时，不再向下解析
		
		report_type		 VARCHAR (50) UNIQUE NOT NULL,	-- 报告类型，唯一标识，可用于查询excel_column_config配置
		template_version VARCHAR (50),
		template_path    VARCHAR(200),
		
		PRIMARY KEY (id)
	)
GO

-- Excel 各个字段配置
CREATE TABLE excel_column_config
	(
		id                      VARCHAR (50) NOT NULL,	-- 有些自定义的不规则guid
		report_id               VARCHAR (36) NOT NULL,
		alias_name              NVARCHAR (100)       ,	-- Excel 列名，辅助显示，不用于解析，空置时取column_name
		column_name             VARCHAR (50)		 ,	-- 对应表字段名称，可以为null
		data_position           VARCHAR (10)         ,	-- 字段值所处位置，逐行解析=列序，逐列解析=行序
		data_type               VARCHAR (30)         ,	-- 字段值的类型，小写
		
		processor_and_validator VARCHAR (500)        ,	-- 处理器与验证器，需要满足约定正则格式
		script_validator		VARCHAR (50)		 ,
		column_converter        VARCHAR (100)        ,	-- 字段类型转换器
		need_save               CHAR (1)             ,	-- 是否需要保存
		need_show				CHAR (1)             ,
		
		value_mapper            VARCHAR (255)        ,
		serial					VARCHAR (10)		 ,
		ordinal                 INT                  ,	-- 排序序号
		PRIMARY KEY (id)
	)
GO

-- 导入文件保存记录
CREATE TABLE excel_file_save
	(
		id             VARCHAR (36) NOT NULL  ,
		file_name      NVARCHAR (200) NOT NULL,
		file_save_path NVARCHAR (200)         ,
		report_type    VARCHAR (20)           ,
		is_success     CHAR (1) DEFAULT ('0') ,
		user_id        VARCHAR (36) NOT NULL  ,
		upload_time    DATETIME               ,
		PRIMARY KEY (id)
	)
GO