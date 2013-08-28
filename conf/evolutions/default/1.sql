# --- Created by Slick DDL
# To stop Slick DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table "ApplicationInstances" ("id" INTEGER NOT NULL PRIMARY KEY,"applicationId" INTEGER NOT NULL,"applicationVersionId" INTEGER NOT NULL,"hostId" INTEGER NOT NULL,"status" INTEGER NOT NULL);
create table "ApplicationVersions" ("id" INTEGER NOT NULL PRIMARY KEY,"applicationId" INTEGER NOT NULL,"name" VARCHAR NOT NULL);
create table "Applications" ("id" INTEGER NOT NULL PRIMARY KEY,"name" VARCHAR NOT NULL);
create table "Hosts" ("id" INTEGER NOT NULL PRIMARY KEY,"hostname" VARCHAR NOT NULL);
alter table "ApplicationInstances" add constraint "FK_ApplicationInstances_Hosts" foreign key("hostId") references "Hosts"("id") on update NO ACTION on delete NO ACTION;
alter table "ApplicationInstances" add constraint "FK_ApplicaitonInstances_ApplicationVersions" foreign key("applicationVersionId") references "ApplicationVersions"("id") on update NO ACTION on delete NO ACTION;
alter table "ApplicationInstances" add constraint "FK_ApplicationInstances_Applications" foreign key("applicationId") references "Applications"("id") on update NO ACTION on delete NO ACTION;
alter table "ApplicationVersions" add constraint "FK_ApplicationVersions_Applications" foreign key("applicationId") references "Applications"("id") on update NO ACTION on delete NO ACTION;

# --- !Downs

alter table "ApplicationInstances" drop constraint "FK_ApplicationInstances_Hosts";
alter table "ApplicationInstances" drop constraint "FK_ApplicaitonInstances_ApplicationVersions";
alter table "ApplicationInstances" drop constraint "FK_ApplicationInstances_Applications";
alter table "ApplicationVersions" drop constraint "FK_ApplicationVersions_Applications";
drop table "ApplicationInstances";
drop table "ApplicationVersions";
drop table "Applications";
drop table "Hosts";

