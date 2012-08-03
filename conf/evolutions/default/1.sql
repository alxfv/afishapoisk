# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table category (
  id                        bigint auto_increment not null,
  title                     varchar(255),
  front                     tinyint(1) default 0,
  constraint pk_category primary key (id))
;

create table city (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  constraint pk_city primary key (id))
;

create table event (
  id                        bigint auto_increment not null,
  title                     varchar(64),
  teaser                    varchar(128),
  info                      longtext,
  front                     tinyint(1) default 0,
  publish                   tinyint(1) default 0,
  start                     datetime,
  created                   datetime,
  updated                   datetime,
  category_id               bigint,
  place_id                  bigint,
  image                     varchar(255),
  constraint pk_event primary key (id))
;

create table place (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  city_id                   bigint,
  info                      longtext,
  phone                     varchar(255),
  address                   varchar(255),
  url                       varchar(255),
  lat                       double,
  lng                       double,
  created                   datetime,
  updated                   datetime,
  constraint pk_place primary key (id))
;

create table account (
  id                        bigint auto_increment not null,
  name                      varchar(255) not null,
  email                     varchar(255) not null,
  password                  varchar(255) not null,
  constraint uq_account_name unique (name),
  constraint uq_account_email unique (email),
  constraint pk_account primary key (id))
;

alter table event add constraint fk_event_category_1 foreign key (category_id) references category (id) on delete restrict on update restrict;
create index ix_event_category_1 on event (category_id);
alter table event add constraint fk_event_place_2 foreign key (place_id) references place (id) on delete restrict on update restrict;
create index ix_event_place_2 on event (place_id);
alter table place add constraint fk_place_city_3 foreign key (city_id) references city (id) on delete restrict on update restrict;
create index ix_place_city_3 on place (city_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table category;

drop table city;

drop table event;

drop table place;

drop table account;

SET FOREIGN_KEY_CHECKS=1;

