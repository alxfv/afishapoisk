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
  info                      longtext,
  front                     tinyint(1) default 0,
  category_id               bigint,
  constraint pk_event primary key (id))
;

create table account (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  email                     varchar(255),
  password                  varchar(255),
  constraint pk_account primary key (id))
;

alter table event add constraint fk_event_category_1 foreign key (category_id) references category (id) on delete restrict on update restrict;
create index ix_event_category_1 on event (category_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table category;

drop table city;

drop table event;

drop table account;

SET FOREIGN_KEY_CHECKS=1;

