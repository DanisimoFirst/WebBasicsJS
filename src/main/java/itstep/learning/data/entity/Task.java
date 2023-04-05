package itstep.learning.data.entity;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Task extends Entity {
    private UUID id ;
    private String name ;
    private int status ;
    private UUID idUser ;
    private UUID idTeam ;
    private Date createdDt ;
    private Date deadline ;
    private byte priority ;

    public static final SimpleDateFormat sqlDatetime =
        new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" ) ;

    public Task() {
    }
    public Task( ResultSet res ) {
        try {
            setId( UUID.fromString( res.getString( "id" ) ) ) ;
            setName( res.getString( "name" ) ) ;
            setStatus( res.getInt( "status" ) ) ;
            setIdUser( UUID.fromString( res.getString( "id_user" ) ) ) ;
            setIdTeam( UUID.fromString( res.getString( "id_team" ) ) ) ;
            setCreatedDt( sqlDatetime.parse( res.getString( "created_dt" ) ) ) ;
            setDeadline(  sqlDatetime.parse( res.getString( "deadline" ) ) ); ;
            setPriority( res.getByte( "priority" ) ) ;
        }
        catch( Exception ex ) {
            throw new RuntimeException( ex.getMessage() ) ;
        }
    }
    // region Get|Set
    public UUID getId() {
        return id;
    }

    public void setId( UUID id ) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus( int status ) {
        this.status = status;
    }

    public UUID getIdUser() {
        return idUser;
    }

    public void setIdUser( UUID idUser ) {
        this.idUser = idUser;
    }

    public UUID getIdTeam() {
        return idTeam;
    }

    public void setIdTeam( UUID idTeam ) {
        this.idTeam = idTeam;
    }

    public Date getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt( Date createdDt ) {
        this.createdDt = createdDt;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline( Date deadline ) {
        this.deadline = deadline;
    }

    public byte getPriority() {
        return priority;
    }

    public void setPriority( byte priority ) {
        this.priority = priority;
    }
    // endregion
}
/*
`id`         char(36) NOT NULL COMMENT 'UUID',
`name`       varchar(64) NOT NULL,
`status`     int DEFAULT 0,
`id_user`    char(36) NOT NULL COMMENT 'Author',
`id_team`    char(36) NOT NULL,
`created_dt` datetime DEFAULT CURRENT_TIMESTAMP,
`deadline`   datetime,
`priority`   TINYINT default 0,
 */