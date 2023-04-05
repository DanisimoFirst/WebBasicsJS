package itstep.learning.data.dao;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import itstep.learning.data.entity.Task;
import itstep.learning.data.entity.Team;
import itstep.learning.data.entity.User;
import itstep.learning.model.TaskModel;
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
public class TaskDao {
    private final DbService dbService ;
    private final Logger logger ;

    @Inject
    public TaskDao( DbService dbService, Logger logger ) {
        this.dbService = dbService;
        this.logger = logger;
    }
    public boolean add( TaskModel model ) {
        String sql = "INSERT INTO `tasks` (`id`,`name`,`status`,`id_user`,`id_team`,`created_dt`,`deadline`,`priority`) " +
            " VALUES( UUID(), ?, 0, ?, ?, CURRENT_TIMESTAMP, ?, ? ) " ;
        try( PreparedStatement prep = dbService.getConnection().prepareStatement( sql ) ) {
            prep.setString( 1, model.getName() ) ;
            prep.setString( 2, model.getAuthor().getId().toString() ) ;
            prep.setString( 3, model.getIdTeam().toString() ) ;
            prep.setString( 4, Task.sqlDatetime.format( model.getDeadline() ) ) ;
            prep.setByte(   5, model.getPriority() ) ;
            prep.executeUpdate() ;
            return true ;
        }
        catch( SQLException ex ) {
            logger.log( Level.WARNING, ex.getMessage() ) ;
            return false ;
        }
    }
    public List<Task> getAll() {
        try( Statement statement = dbService.getConnection().createStatement() ) {
            ResultSet res = statement.executeQuery( "SELECT t.* FROM `tasks` t" ) ;
            List<Task> tasks = new ArrayList<>() ;
            while( res.next() )
                tasks.add( new Task( res ) ) ;
            return tasks ;
        }
        catch( SQLException ex ) {
            logger.log( Level.WARNING, ex.getMessage() ) ;
            return null ;
        }
    }

    public List<Task> getUserTask( User user ) {
        String sql = "SELECT t.* FROM `tasks` t JOIN `teams_users` tu on t.`id_team` = tu.`id_team`  WHERE tu.`id_user` = ?" ;
        try( PreparedStatement prep = dbService.getConnection().prepareStatement( sql ) ) {
            prep.setString( 1, user.getId().toString() ) ;
            List<Task> task = new ArrayList<>() ;
            ResultSet res = prep.executeQuery() ;
            while( res.next() ) task.add( new Task( res ) ) ;
            return task ;
        }
        catch( SQLException ex ) {
            logger.log( Level.WARNING, ex.getMessage() ) ;
            return null ;
        }
    }
}
/*
07094a33-c1c3-11ed-b458-14857fd97497
16e22c6e-c1c3-11ed-b458-14857fd97497
d5a875eb-c1c2-11ed-b458-14857fd97497

527398a5-b6c0-11ed-b910-14857fd97497
5d399bea-bc3c-11ed-8ee5-14857fd97497

`id`,`name`,`status`,`id_user`,`id_team`,`created_dt`,`deadline`,`priority`
 */