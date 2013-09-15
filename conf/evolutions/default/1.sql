# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table credentials (
  id                        bigint not null,
  email                     varchar(255),
  password                  varchar(255),
  constraint pk_credentials primary key (id))
;

create table user (
  id                        bigint not null,
  first_name                varchar(255),
  last_name                 varchar(255),
  credentials_id            bigint,
  role                      integer,
  constraint ck_user_role check (role in (0,1)),
  constraint pk_user primary key (id))
;

create sequence credentials_seq;

create sequence user_seq;

alter table user add constraint fk_user_credentials_1 foreign key (credentials_id) references credentials (id) on delete restrict on update restrict;
create index ix_user_credentials_1 on user (credentials_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists credentials;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists credentials_seq;

drop sequence if exists user_seq;

