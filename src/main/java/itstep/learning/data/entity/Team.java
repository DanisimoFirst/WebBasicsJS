package itstep.learning.data.entity;

import java.sql.ResultSet;
import java.util.UUID;

public class Team extends Entity {
    private UUID id ;
    private String name ;

    public Team() {
    }
    public Team( ResultSet res ) {
        try {
            setId( UUID.fromString( res.getString( "id" ) ) ) ;
            setName( res.getString( "name" ) ) ;
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

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }
}
