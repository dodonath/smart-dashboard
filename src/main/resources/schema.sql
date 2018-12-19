
create table ENTITYMASTER
(
   entityId bigint AUTO_INCREMENT not null,
   entityType varchar(255) not null,
   entityDescription varchar(255),
   createdBy varchar(255),
   updatedBy varchar(255),
   createdAt bigint ,
   updatedAt bigint ,
   active boolean not null,
   primary key(entityId)
);

create table ENVIRONMENTDETAILS
(
   environmentId bigint AUTO_INCREMENT not null,
   environmentType varchar(255) not null,
   createdBy varchar(255),
   updatedBy varchar(255),
   createdAt bigint ,
   updatedAt bigint ,
   active boolean not null,
   primary key(environmentId)
);


create table EnvironmentValueDetails
(
   historyId bigint AUTO_INCREMENT not null,
   environmentId bigint not null,
   entityMigrationId bigint not null,
   environmentCount bigint ,
   createdBy varchar(255),
   updatedBy varchar(255),
   createdAt bigint ,
   updatedAt bigint ,
   active boolean not null,
   primary key(historyId)
);


create table EntityMigrationData
(
   entityMigrationId bigint AUTO_INCREMENT not null,
   entityMigrationDescription varchar(255),
   entityId bigint not null,
   migrationId bigint ,
   createdBy varchar(255),
   updatedBy varchar(255),
   createdAt bigint ,
   updatedAt bigint ,
   active boolean not null,
   primary key(entityMigrationId)
);

create table MigrationHistory
(
   migrationId bigint AUTO_INCREMENT not null,
   migrationDescription varchar(255),
   clientCode varchar(255),
   createdBy varchar(255),
   updatedBy varchar(255),
   createdAt bigint ,
   updatedAt bigint ,
   active boolean not null,
   primary key(migrationId)
);