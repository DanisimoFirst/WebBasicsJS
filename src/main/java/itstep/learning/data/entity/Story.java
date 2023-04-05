package itstep.learning.data.entity;

import java.sql.ResultSet;
import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

public class Story extends Entity {
    private UUID id ;
    private UUID idUser ;
    private UUID idTask ;
    private UUID idReply ;
    private String content ;
    private Date createdDt ;

    public Story() {
    }

    public Story( ResultSet res ) throws RuntimeException {
        try {
            this.setId( UUID.fromString( res.getString( "id" ) ) ) ;
            this.setIdUser( UUID.fromString( res.getString( "id_user" ) ) ) ;
            this.setIdTask( UUID.fromString( res.getString( "id_task" ) ) ) ;
            String idReply = res.getString( "id_reply" ) ;
            if( idReply != null ) {
                this.setIdReply( UUID.fromString( idReply ) ) ;
            }
            this.setContent( res.getString( "content" ) ) ;
            this.setCreatedDt( Entity.sqlDatetimeFormat.parse(
                res.getString( "created_dt" ) ) ) ;
        }
        catch( Exception ex ) {
            throw new RuntimeException( ex.getMessage() ) ;
        }
    }

    public UUID getId() {
        return id;
    }

    public void setId( UUID id ) {
        this.id = id;
    }

    public UUID getIdUser() {
        return idUser;
    }

    public void setIdUser( UUID idUser ) {
        this.idUser = idUser;
    }

    public UUID getIdTask() {
        return idTask;
    }

    public void setIdTask( UUID idTask ) {
        this.idTask = idTask;
    }

    public UUID getIdReply() {
        return idReply;
    }

    public void setIdReply( UUID idReply ) {
        this.idReply = idReply;
    }

    public String getContent() {
        return content;
    }

    public void setContent( String content ) {
        this.content = content;
    }

    public Date getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt( Date createdDt ) {
        this.createdDt = createdDt;
    }
    public void setCreatedDt( String createdDt ) throws ParseException {
        this.createdDt = Entity.sqlDatetimeFormat.parse( createdDt ) ;
    }
}
/*
`id`
`id_user`
`id_task`
`id_reply`
`content`
`created_dt`
 */