/*==============================================================*/
/* Table: train_station_info                                    */
/*==============================================================*/
drop table if exists train_station_info;
create table train_station_info
(
   id                   varchar(32) primary key,
   code                 varchar(20) not null,
   name                 varchar(20) not null,
   is_valid             tinyint default 1,
   create_time          datetime,
   update_time          timestamp default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP
) engine = InnoDB default charset utf8 comment '火车站信息';

/*==============================================================*/
/* Table: train_seat_type                                       */
/*==============================================================*/
drop table if exists train_seat_type;
create table train_seat_type
(
   id                   varchar(32) primary key,
   code                 varchar(20) not null comment '席别代号',
   name                 varchar(20) not null comment '席别名称'
)engine = InnoDB default charset utf8 comment '列车席别';

/*==============================================================*/
/* Table: train_info                                            */
/*==============================================================*/
drop table if exists train_info;
create table train_info
(
   id                   varchar(32) primary key,
   train_no             varchar(20) not null  unique key comment '车次',
   type                 varchar(20) not null comment '车次类型',
   start_station_code   varchar(20) not null comment '始发站代码',
   end_station_code     varchar(20) not null comment '终点站代码',
   start_time           varchar(10) not null comment '发车时间',
   end_time             varchar(10) not null comment '结束时间',
   total_distance       varchar(10) not null comment '总距离',
   total_time           varchar(10) not null comment '总时间',
   summery              varchar(32) comment '摘要',
   create_time          datetime,
   update_time          timestamp default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
   update_timestamp     bigint
)engine = InnoDB default charset utf8 comment '车次信息';

/*==============================================================*/
/* Table: train_price_info                                   */
/*==============================================================*/
drop table if exists train_price_info;
create table train_price_info
(
   id                   varchar(32) primary key,
   train_no             varchar(20) not null,
   seat_type_code       varchar(20) not null,
   price                double default 0,
   create_time          datetime,
   update_time          timestamp default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
   update_timestamp     bigint
)engine = InnoDB default charset utf8 comment '车次票价';

create index tps_train_no on train_price_info(train_no);

/*==============================================================*/
/* Table: train_station_link                                    */
/*==============================================================*/
drop table if exists train_station_link;
create table train_station_link
(
   id                   varchar(32) primary key,
   train_no             varchar(20) not null comment '车次',
   station_order        tinyint not null comment '站次',
   station_code         varchar(20) not null comment '车站代码',
   station_name         varchar(20) not null comment '车站名-冗余',
   arrive_time          varchar(10) comment '到达时间',
   leave_time           varchar(10) comment '离开时间',
   stay_time             double comment '停留时间',
   run_time             varchar(15) comment '运行时间',
   days                 varchar(10) default '1' comment '天数',
   distance             double comment '运行距离',
   create_time          datetime,
   update_time          timestamp default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
   update_timestamp     bigint
)engine = InnoDB default charset utf8 comment '火车与车站的关联';

create index tsl_train_no on train_station_link(train_no);
create index tsl_station_code on train_station_link(station_code);

/*==============================================================*/
/* Table: train_station_price_info                              */
/*==============================================================*/
drop table if exists train_station_price_info;
create table train_station_price_info
(
   id                   varchar(32) primary key,
   train_no             varchar(20) not null comment '车次',
   station_code         varchar(20) not null comment '车站代码',
   seat_type_code       varchar(20) not null comment '席别',
   price                double comment '票价',
   create_time          datetime,
   update_time          timestamp default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
   update_timestamp     bigint
)engine = InnoDB default charset utf8 comment '车站票价';

create index tspi_train_no on train_station_price_info(train_no);
create index tspi_station_code on train_station_price_info(station_code);
