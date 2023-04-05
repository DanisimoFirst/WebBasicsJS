package itstep.learning.data.entity;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class User extends Entity {  // ORM for DB table Users
    // region fields
    private UUID id ;
    private String login ;
    private String name ;
    private String salt ;
    private String pass ;
    private String email ;
    private String confirm ;
    private Date regDt ;
    private String avatar ;
    private Date deleteDt ;
    private String roleId ;
    // endregion

    // region constructors
    public User() {
    }
    private static final SimpleDateFormat sqlDatetime =
            new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" ) ;
    public User( ResultSet res ) throws RuntimeException {
        try {
            setId( UUID.fromString( res.getString( "id" ) ) ) ;
            setLogin(   res.getString( "login" ) ) ;
            setName(    res.getString( "name" ) ) ;
            setSalt(    res.getString( "salt" ) ) ;
            setPass(    res.getString( "pass" ) ) ;
            setEmail(   res.getString( "email" ) ) ;
            setConfirm( res.getString( "confirm" ) ); ;
            setAvatar(  res.getString( "avatar" ) ); ;
            setRoleId(  res.getString( "role_id" ) ); ;
            setRegDt( sqlDatetime.parse( res.getString( "reg_dt" ) ) ) ;
            String deleteDtString = res.getString( "delete_dt" ) ;
            if( deleteDtString != null )
                setDeleteDt( sqlDatetime.parse( deleteDtString ) ) ;
        }
        catch( Exception ex ) {
            throw new RuntimeException( ex.getMessage() ) ;
        }
    }
    // endregion

    // region accessors

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public Date getRegDt() {
        return regDt;
    }

    public void setRegDt(Date regDt) {
        this.regDt = regDt;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getDeleteDt() {
        return deleteDt;
    }

    public void setDeleteDt(Date deleteDt) {
        this.deleteDt = deleteDt;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    // endregion
}
/*
CREATE TABLE `user_roles` (
      `id` varchar(16) NOT NULL,
      `descr` tinytext NOT NULL,
      PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;

CREATE TABLE `users` (
  `id`      char(36) NOT NULL COMMENT 'UUID',
  `login`   varchar(64) NOT NULL,
  `name`        varchar(64) DEFAULT NULL,
  `salt`    char(32) NOT NULL COMMENT 'random 128 bit hex-string',
  `pass`    char(32) NOT NULL COMMENT 'password hash',
  `email`   varchar(64) NOT NULL,
  `confirm` char(6) DEFAULT NULL COMMENT 'email confirm code',
  `reg_dt`  datetime NOT NULL DEFAULT current_timestamp(),
  `avatar`  varchar(64) DEFAULT NULL COMMENT 'avatar filename',
  `delete_dt` datetime DEFAULT NULL,
  `role_id` varchar(16) NOT NULL DEFAULT 'guest',
  PRIMARY KEY (`id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `users_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `user_roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
 */