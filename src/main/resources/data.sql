insert into ERROR_MASTER
(
   errorId,
   errorCode,
   errorMessage,
   entityId,
   createdBy,
   updatedBy,
   createdAt,
   updatedAt,
   active
)
values (1,'EE0001','Account has product with START_DAT is after END_DAT',1,'Admin','Admin',10000000,100000,1);

insert into ERROR_MASTER
(
   errorId,
   errorCode,
   errorMessage,
   entityId,
   createdBy,
   updatedBy,
   createdAt,
   updatedAt,
   active
)
values (2,'EE0002','Account has one of the fields as NULL',1,'Admin','Admin',10000000,100000,1);

insert into ERROR_MASTER
(
   errorId,
   errorCode,
   errorMessage,
   entityId,
   createdBy,
   updatedBy,
   createdAt,
   updatedAt,
   active
)
values (3,'EE0003','Account has product with START_DAT set to NULL',1,'Admin','Admin',10000000,100000,1);


insert into ERROR_MASTER
(
   errorId,
   errorCode,
   errorMessage,
   entityId,
   createdBy,
   updatedBy,
   createdAt,
   updatedAt,
   active
)
values (4,'EE0004','Customer with product is present in OMNIA_CUSTOMERPRODUCTATTR_EXT but not in OMNIA_CUSTOMERPRODUCT_EXT',2,'Admin','Admin',10000000,100000,1);


insert into ERROR_MASTER
(
   errorId,
   errorCode,
   errorMessage,
   entityId,
   createdBy,
   updatedBy,
   createdAt,
   updatedAt,
   active
)
values (5,'EE0005','Account has product with BILLED_TO_DATE before product START_DATE',1,'Admin','Admin',10000000,100000,1);

insert into ERROR_MASTER
(
   errorId,
   errorCode,
   errorMessage,
   entityId,
   createdBy,
   updatedBy,
   createdAt,
   updatedAt,
   active
)
values (6,'EE0006','Customer record in OMNIA_CUSTOMERATTRIBUTES_EXT but not in OMNIA_CUSTOMER_EXT',2,'Admin','Admin',10000000,100000,1);

insert into ERROR_MASTER
(
   errorId,
   errorCode,
   errorMessage,
   entityId,
   createdBy,
   updatedBy,
   createdAt,
   updatedAt,
   active
)
values (7,'EE0007','Customer record in OMNIA_CUSTOMERPRODUCTATTR_EXT but no ACCOUNT_NUMBER in OMNIA_CUSTOMERPRODUCT_EXT',2,'Admin','Admin',10000000,100000,1);


insert into ENTITY_MASTER
(
   entityId,
   entityType,
   entityDescription,
   createdBy,
   updatedBy,
   createdAt,
   updatedAt,
   active,entityCode
)
values (1,'Accounts','Accounts desc','Admin','Admin',10000000,100000,1,'Accounts');

insert into ENTITY_MASTER
(
   entityId,
   entityType,
   entityDescription,
   createdBy,
   updatedBy,
   createdAt,
   updatedAt,
   active,entityCode
)
values (2,'Customer','Customer desc','Admin','Admin',10000000,100000,1,'Customer');


insert into ENVIRONMENT_DETAILS_MASTER
(
   environmentId,
   environmentType,
   createdBy,
   updatedBy,
   createdAt,
   updatedAt,
   active
)
values (1,'csr','Admin','Admin',10000000,100000,1);
insert into ENVIRONMENT_DETAILS_MASTER
(
   environmentId,
   environmentType,
   createdBy,
   updatedBy,
   createdAt,
   updatedAt,
   active
)
values (2,'rbm','Admin','Admin',10000000,100000,1);

insert into ENVIRONMENT_DETAILS_MASTER
(
   environmentId,
   environmentType,
   createdBy,
   updatedBy,
   createdAt,
   updatedAt,
   active
)
values (3,'omnia','Admin','Admin',10000000,100000,1);

insert into MigrationHistory
(
   migrationId, 
   migrationDescription,clientCode,
   createdBy,
   updatedBy,
   createdAt,
   updatedAt,
   active
)
values (1,'Migration Done at 12-12-2018','Telecom','Admin','Admin',1544595947000,1544595947000,1);

insert into MigrationHistory
(
   migrationId, 
   migrationDescription,clientCode,
   createdBy,
   updatedBy,
   createdAt,
   updatedAt,
   active
)
values (2,'Migration Done at 12-12-2018','Telecom','Admin','Admin',1543991147000,1543991147000,1);


insert into MigrationHistory
(
   migrationId, 
   migrationDescription,clientCode,
   createdBy,
   updatedBy,
   createdAt,
   updatedAt,
   active
)
values (3,'Migration Done at 12-12-2018','Telecom','Admin','Admin',1543386347000,1543386347000,1);

insert into MigrationHistory
(
   migrationId, 
   migrationDescription,clientCode,
   createdBy,
   updatedBy,
   createdAt,
   updatedAt,
   active
)
values (4,'Migration Done at 12-12-2018','Telecom','Admin','Admin',1542781547000,1542781547000,1);

insert into EntityMigrationData
(
   entityMigrationId ,
   entityMigrationDescription ,
   entityId ,
   migrationId  ,
   createdBy ,
   updatedBy ,
   createdAt ,
   updatedAt ,
   active
)
values (1,'Migration Done for accounts at 12-12-2018',1,1,'Admin','Admin',1544595947000,1544595947000,1);

insert into EntityMigrationData
(
   entityMigrationId ,
   entityMigrationDescription ,
   entityId ,
   migrationId  ,
   createdBy ,
   updatedBy ,
   createdAt ,
   updatedAt ,
   active
)
values (4,'Migration Done for accounts at 12-12-2018',1,2,'Admin','Admin',1543991147000,1543991147000,1);

insert into EntityMigrationData
(
   entityMigrationId ,
   entityMigrationDescription ,
   entityId ,
   migrationId  ,
   createdBy ,
   updatedBy ,
   createdAt ,
   updatedAt ,
   active
)
values (5,'Migration Done for accounts at 12-12-2018',1,3,'Admin','Admin',1543386347000,1543386347000,1);



insert into EntityMigrationData
(
   entityMigrationId ,
   entityMigrationDescription ,
   entityId ,
   migrationId  ,
   createdBy ,
   updatedBy ,
   createdAt ,
   updatedAt ,
   active
)
values (2,'Migration Done for accounts at 12-12-2018',2,1,'Admin','Admin',1544595947000,1544595947000,1);

insert into EntityMigrationData
(
   entityMigrationId ,
   entityMigrationDescription ,
   entityId ,
   migrationId  ,
   createdBy ,
   updatedBy ,
   createdAt ,
   updatedAt ,
   active
)
values (6,'Migration Done for accounts at 12-12-2018',2,2,'Admin','Admin',1543991147000,1543991147000,1);

insert into EntityMigrationData
(
   entityMigrationId ,
   entityMigrationDescription ,
   entityId ,
   migrationId  ,
   createdBy ,
   updatedBy ,
   createdAt ,
   updatedAt ,
   active
)
values (7,'Migration Done for accounts at 12-12-2018',2,3,'Admin','Admin',1543386347000,1543386347000,1);



insert into EntityMigrationData
(
   entityMigrationId ,
   entityMigrationDescription ,
   entityId ,
   migrationId  ,
   createdBy ,
   updatedBy ,
   createdAt ,
   updatedAt ,
   active
)
values (3,'Migration Done for accounts at 12-12-2018',3,1,'Admin','Admin',1544595947000,1544595947000,1);

insert into EntityMigrationData
(
   entityMigrationId ,
   entityMigrationDescription ,
   entityId ,
   migrationId  ,
   createdBy ,
   updatedBy ,
   createdAt ,
   updatedAt ,
   active
)
values (8,'Migration Done for accounts at 12-12-2018',3,2,'Admin','Admin',1543991147000,1543991147000,1);

insert into EntityMigrationData
(
   entityMigrationId ,
   entityMigrationDescription ,
   entityId ,
   migrationId  ,
   createdBy ,
   updatedBy ,
   createdAt ,
   updatedAt ,
   active
)
values (9,'Migration Done for accounts at 12-12-2018',3,3,'Admin','Admin',1543386347000,1543386347000,1);

insert into EnvironmentValueDetails
(
   historyId ,
   environmentId ,
   entityMigrationId ,
   environmentCount ,
   createdBy ,
   updatedBy ,
   createdAt ,
   updatedAt ,
   active
)
values (1,1,1,2000,'Admin','Admin',1544595947000,1544595947000,1);

insert into EnvironmentValueDetails
(
   historyId ,
   environmentId ,
   entityMigrationId ,
   environmentCount ,
   createdBy ,
   updatedBy ,
   createdAt ,
   updatedAt ,
   active
)
values (2,2,1,3000,'Admin','Admin',1544595947000,1544595947000,1);

insert into EnvironmentValueDetails
(
   historyId ,
   environmentId ,
   entityMigrationId ,
   environmentCount ,
   createdBy ,
   updatedBy ,
   createdAt ,
   updatedAt ,
   active
)
values (3,3,1,2908,'Admin','Admin',1544595947000,1544595947000,1);


insert into EnvironmentValueDetails
(
   historyId ,
   environmentId ,
   entityMigrationId ,
   environmentCount ,
   createdBy ,
   updatedBy ,
   createdAt ,
   updatedAt ,
   active
)
values (4,1,4,2908,'Admin','Admin',1543991147000,1543991147000,1);

insert into EnvironmentValueDetails
(
   historyId ,
   environmentId ,
   entityMigrationId ,
   environmentCount ,
   createdBy ,
   updatedBy ,
   createdAt ,
   updatedAt ,
   active
)
values (5,2,4,3000,'Admin','Admin',1543991147000,1543991147000,1);

insert into EnvironmentValueDetails
(
   historyId ,
   environmentId ,
   entityMigrationId ,
   environmentCount ,
   createdBy ,
   updatedBy ,
   createdAt ,
   updatedAt ,
   active
)
values (6,3,4,2800,'Admin','Admin',1543991147000,1543991147000,1);


insert into EnvironmentValueDetails
(
   historyId ,
   environmentId ,
   entityMigrationId ,
   environmentCount ,
   createdBy ,
   updatedBy ,
   createdAt ,
   updatedAt ,
   active
)
values (7,1,5,2800,'Admin','Admin',1543386347000,1543386347000,1);

insert into EnvironmentValueDetails
(
   historyId ,
   environmentId ,
   entityMigrationId ,
   environmentCount ,
   createdBy ,
   updatedBy ,
   createdAt ,
   updatedAt ,
   active
)
values (8,2,5,2800,'Admin','Admin',1543386347000,1543386347000,1);

insert into EnvironmentValueDetails
(
   historyId ,
   environmentId ,
   entityMigrationId ,
   environmentCount ,
   createdBy ,
   updatedBy ,
   createdAt ,
   updatedAt ,
   active
)
values (9,3,5,2801,'Admin','Admin',1543386347000,1543386347000,1);

insert into EnvironmentValueDetails
(
   historyId ,
   environmentId ,
   entityMigrationId ,
   environmentCount ,
   createdBy ,
   updatedBy ,
   createdAt ,
   updatedAt ,
   active
)
values (10,1,2,2801,'Admin','Admin',1544595947000,1544595947000,1);

insert into EnvironmentValueDetails
(
   historyId ,
   environmentId ,
   entityMigrationId ,
   environmentCount ,
   createdBy ,
   updatedBy ,
   createdAt ,
   updatedAt ,
   active
)
values (11,2,2,2801,'Admin','Admin',1544595947000,1544595947000,1);

insert into EnvironmentValueDetails
(
   historyId ,
   environmentId ,
   entityMigrationId ,
   environmentCount ,
   createdBy ,
   updatedBy ,
   createdAt ,
   updatedAt ,
   active
)
values (12,3,2,2801,'Admin','Admin',1544595947000,1544595947000,1);

insert into EnvironmentValueDetails
(
   historyId ,
   environmentId ,
   entityMigrationId ,
   environmentCount ,
   createdBy ,
   updatedBy ,
   createdAt ,
   updatedAt ,
   active
)
values (13,1,6,2801,'Admin','Admin',1543991147000,1543991147000,1);

insert into EnvironmentValueDetails
(
   historyId ,
   environmentId ,
   entityMigrationId ,
   environmentCount ,
   createdBy ,
   updatedBy ,
   createdAt ,
   updatedAt ,
   active
)
values (14,2,6,2801,'Admin','Admin',1543991147000,1543991147000,1);

insert into EnvironmentValueDetails
(
   historyId ,
   environmentId ,
   entityMigrationId ,
   environmentCount ,
   createdBy ,
   updatedBy ,
   createdAt ,
   updatedAt ,
   active
)
values (15,3,6,2801,'Admin','Admin',1543991147000,1543991147000,1);

insert into EnvironmentValueDetails
(
   historyId ,
   environmentId ,
   entityMigrationId ,
   environmentCount ,
   createdBy ,
   updatedBy ,
   createdAt ,
   updatedAt ,
   active
)
values (16,1,7,2801,'Admin','Admin',1543386347000,1543386347000,1);

insert into EnvironmentValueDetails
(
   historyId ,
   environmentId ,
   entityMigrationId ,
   environmentCount ,
   createdBy ,
   updatedBy ,
   createdAt ,
   updatedAt ,
   active
)
values (17,2,7,2801,'Admin','Admin',1543386347000,1543386347000,1);

insert into EnvironmentValueDetails
(
   historyId ,
   environmentId ,
   entityMigrationId ,
   environmentCount ,
   createdBy ,
   updatedBy ,
   createdAt ,
   updatedAt ,
   active
)
values (18,3,7,2801,'Admin','Admin',1543386347000,1543386347000,1);

insert into EnvironmentValueDetails
(
   historyId ,
   environmentId ,
   entityMigrationId ,
   environmentCount ,
   createdBy ,
   updatedBy ,
   createdAt ,
   updatedAt ,
   active
)
values (19,1,3,2801,'Admin','Admin',1544595947000,1544595947000,1);

insert into EnvironmentValueDetails
(
   historyId ,
   environmentId ,
   entityMigrationId ,
   environmentCount ,
   createdBy ,
   updatedBy ,
   createdAt ,
   updatedAt ,
   active
)
values (20,2,3,2801,'Admin','Admin',1544595947000,1544595947000,1);

insert into EnvironmentValueDetails
(
   historyId ,
   environmentId ,
   entityMigrationId ,
   environmentCount ,
   createdBy ,
   updatedBy ,
   createdAt ,
   updatedAt ,
   active
)
values (21,3,3,2801,'Admin','Admin',1544595947000,1544595947000,1);

insert into EnvironmentValueDetails
(
   historyId ,
   environmentId ,
   entityMigrationId ,
   environmentCount ,
   createdBy ,
   updatedBy ,
   createdAt ,
   updatedAt ,
   active
)
values (22,1,8,2801,'Admin','Admin',1543991147000,1543991147000,1);

insert into EnvironmentValueDetails
(
   historyId ,
   environmentId ,
   entityMigrationId ,
   environmentCount ,
   createdBy ,
   updatedBy ,
   createdAt ,
   updatedAt ,
   active
)
values (23,2,8,2801,'Admin','Admin',1543991147000,1543991147000,1);


insert into EnvironmentValueDetails
(
   historyId ,
   environmentId ,
   entityMigrationId ,
   environmentCount ,
   createdBy ,
   updatedBy ,
   createdAt ,
   updatedAt ,
   active
)
values (24,3,8,2801,'Admin','Admin',1543991147000,1543991147000,1);

insert into EnvironmentValueDetails
(
   historyId ,
   environmentId ,
   entityMigrationId ,
   environmentCount ,
   createdBy ,
   updatedBy ,
   createdAt ,
   updatedAt ,
   active
)
values (25,1,8,2801,'Admin','Admin',1543386347000,1543386347000,1);

insert into EnvironmentValueDetails
(
   historyId ,
   environmentId ,
   entityMigrationId ,
   environmentCount ,
   createdBy ,
   updatedBy ,
   createdAt ,
   updatedAt ,
   active
)
values (26,2,8,2801,'Admin','Admin',1543386347000,1543386347000,1);


insert into EnvironmentValueDetails
(
   historyId ,
   environmentId ,
   entityMigrationId ,
   environmentCount ,
   createdBy ,
   updatedBy ,
   createdAt ,
   updatedAt ,
   active
)
values (27,3,8,2801,'Admin','Admin',1543386347000,1543386347000,1);