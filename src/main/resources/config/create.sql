
##Delete all table
#DROP TABLE T_USER;
/*DELETE FROM T_SHPEE_CONFIG;
DELETE FROM T_SHPEE_USER;
DELETE FROM T_USER_GROUP;
DELETE FROM T_GROUP_ROLE;
DELETE FROM T_PRODUCT_GROUP;
DELETE FROM T_PRODUCT;*/

#system
CREATE TABLE IF NOT EXISTS T_SHPEE_CONFIG(
    SHPEE_KEY   char(32)    PRIMARY KEY NOT NULL,
    SHPEE_VALUE    char(32)    NOT NULL
);

#user
CREATE TABLE IF NOT EXISTS T_SHPEE_USER(
    USER_ID     char(64)    PRIMARY KEY NOT NULL,
    USERNAME    char(64) UNIQUE NOT NULL,
    PASSWORD    char(64)    NOT NULL ,
    USER_GROUP  char(64)    NOT NULL,
    EMAIL       char(64)    UNIQUE,
    ENABLED     boolean DEFAULT TRUE,
    ACCOUNT_NON_EXPIRED boolean DEFAULT TRUE,
    CREDENTIALS_NON_EXPIRED boolean DEFAULT TRUE,
    ACCOUNT_NON_LOCKED  boolean DEFAULT TRUE
    );

CREATE TABLE IF NOT EXISTS T_USER_GROUP(
    GROUP_ID            char(64)    PRIMARY KEY NOT NULL,
    GROUP_ADMIN_USER_ID char(64)    NOT NULL,
    GROUP_NAME          char(64)    UNIQUE NOT NULL,
    ENABLED             boolean     DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS T_GROUP_ROLE(
    GROUP_ID  char(64) NOT NULL,
    ROLE_NAME   char(64) NOT NULL
);

#product
CREATE TABLE IF NOT EXISTS T_PRODUCT_GROUP(
    PRODUCT_GROUP_ID    char(64)    NOT NULL ,
    PRODUCT_GROUP_NAME        char(64)    NOT NULL
);

CREATE TABLE IF NOT EXISTS T_PRODUCT(
    PRODUCT_ID      char(64)    NOT NULL ,
    PRODUCT_NAME    char(64)    NOT NULL ,
    PRODUCT_GROUP_ID    char(64)    NOT NULL ,
    PRODUCT_LABEL       char(64),
    PRODUCT_PARAMETER_ID    char(64),
    ENABLED boolean default TRUE
);

CREATE TABLE IF NOT EXISTS T_PRODUCT_PARAMETER(
    PRODUCT_PARAMETER_ID        char(64),
    PRODUCT_PARAMETER_NEXT_ID   char(64),
    KEY_ITEM                    boolean default FALSE ,
    PRODUCT_PARAMETER_TEXT      nvarchar(256)
);