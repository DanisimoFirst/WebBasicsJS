package itstep.learning.data.dao;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import itstep.learning.data.entity.Team;
import itstep.learning.data.entity.User;
import itstep.learning.service.DbService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
public class TeamDao {
    private final DbService dbService ;
    private final Logger logger ;

    @Inject
    public TeamDao( DbService dbService, Logger logger ) {
        this.dbService = dbService;
        this.logger = logger;
    }

    public List<Team> getAll() {
        try( Statement statement = dbService.getConnection().createStatement() ) {
            ResultSet res = statement.executeQuery( "SELECT t.* FROM `teams` t" ) ;
            List<Team> teams = new ArrayList<>() ;
            while( res.next() )
                teams.add( new Team( res ) ) ;
            return teams ;
        }
        catch( SQLException ex ) {
            logger.log( Level.WARNING, ex.getMessage() ) ;
            return null ;
        }
    }

    public List<Team> getUserTeams( User user ) {
        String sql = "SELECT t.* FROM `teams` t " +
            "JOIN `teams_users` tu ON t.`id` = tu.`id_team` " +
            "WHERE tu.`id_user` = ?" ;
        try( PreparedStatement prep = dbService.getConnection().prepareStatement( sql ) ) {
            prep.setString( 1, user.getId().toString() ) ;
            ResultSet res = prep.executeQuery() ;
            List<Team> teams = new ArrayList<>() ;
            while( res.next() )
                teams.add( new Team( res ) ) ;
            return teams ;
        }
        catch( SQLException ex ) {
            logger.log( Level.WARNING, ex.getMessage() ) ;
            return null ;
        }
    }
}
