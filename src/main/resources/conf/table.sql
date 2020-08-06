CREATE TABLE IF NOT EXISTS T_UPLOAD(
    UID     char(32)    NOT NULL
    ,FILE_NAME   varchar(128) NOT NULL
    ,HASH   char(64)
    ,PATH   varchar(128)    NOT NULL
    ,TYPE   char(16)
    ,SIZE   BIGINT
    ,UPTIME TIMESTAMP
    ,ISDELETE   int
    );

CREATE TABLE IF NOT EXISTS T_USER(
  NAME   varchar(128)    not null,
  PASSWORD  char(64)    not null,
  UPTIME   timestamp
    );

CREATE TABLE IF NOT EXISTS T_ROLE(
    RID    char(32) not null,
    ROLE   char(32) not  null
    );

CREATE TABLE IF NOT EXISTS T_USER_ROLE(
    NAME char(32) not null,
    RID char(32) not null
    );

CREATE TABLE IF NOT EXISTS T_CONFIGURE(
  SKEY   char(64)    not null,
  SVAL  char(64)    not null
    );