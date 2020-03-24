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
