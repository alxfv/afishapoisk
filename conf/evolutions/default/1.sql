# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table category (
  id                        bigint auto_increment not null,
  title                     varchar(255),
  constraint pk_category primary key (id))
;




# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table category;

SET FOREIGN_KEY_CHECKS=1;

