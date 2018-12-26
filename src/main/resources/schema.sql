
create table ENTITY_MASTER
(
   entityId bigint AUTO_INCREMENT not null,
   entityType varchar(255) not null,
   entityCode varchar(255) not null,
   entityDescription varchar(255),
   createdBy varchar(255),
   updatedBy varchar(255),
   createdAt bigint ,
   updatedAt bigint ,
   active boolean not null,
   primary key(entityId)
);

create table DATA_VALIDATION_DETAILS
(
   validationId bigint AUTO_INCREMENT not null,
   entityId bigint not null,
   migrationId bigint not null,
   collectedCount bigint ,
   validatedCount bigint ,
   transformedCount bigint ,
   loadedCount bigint ,
   startedAt bigint ,
   endedAt bigint ,
   createdBy varchar(255),
   updatedBy varchar(255),
   createdAt bigint ,
   updatedAt bigint ,
   active boolean not null,
   source varchar(255),
   primary key(validationId)
);

create table DATA_REJECTION_DETAILS
(
   rejectionId bigint AUTO_INCREMENT not null,
   entityId bigint not null,
   migrationId bigint not null,
   errorId bigint not null,
   rejectionAccountId bigint,
   createdBy varchar(255),
   updatedBy varchar(255),
   createdAt bigint ,
   updatedAt bigint ,
   active boolean not null,
   source varchar(255),
   primary key(rejectionId)
);

create table ERROR_MASTER
(
   errorId bigint AUTO_INCREMENT not null,
   errorCode varchar(255) not null,
   errorMessage varchar(255) not null,
   entityId bigint not null,
   createdBy varchar(255),
   updatedBy varchar(255),
   createdAt bigint ,
   updatedAt bigint ,
   active boolean not null,
   primary key(errorId)
);

create table ENVIRONMENT_DETAILS_MASTER
(
   environmentId bigint AUTO_INCREMENT not null,
   environmentType varchar(255) not null,
   createdBy varchar(255),
   updatedBy varchar(255),
   createdAt bigint ,
   updatedAt bigint ,
   active boolean not null,
   environmentDisplayOrder bigint not null,
   environmentDetails varchar(255),
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