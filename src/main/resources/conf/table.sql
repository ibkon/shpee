CREATE TABLE IF NOT EXISTS T_UPLOAD(
    uid     char(32)    NOT NULL
    ,name   varchar(64) NOT NULL
    ,hash   char(64)    NOT NULL
    ,path   char(32)    NOT NULL
    ,type   char(32)
    ,size   BIGINT
    ,uptime TIMESTAMP
    ,isdelete   int    
    );

CREATE TABLE IF NOT EXISTS T_USER(
  NAME   char(32)    not null,
  PASSWORD  char(64)    not null
    );

CREATE TABLE IF NOT EXISTS T_ROLE(
    RID char(32) not null,
    ROLE    char(32) not null
    );

CREATE TABLE IF NOT EXISTS T_USER_ROLE(
    NAME char(32) not null,
    RID char(32) not null
    );