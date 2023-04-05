package itstep.learning.servlet;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import itstep.learning.data.DataContext;
import itstep.learning.data.entity.Task;
import itstep.learning.data.entity.Team;
import itstep.learning.data.entity.User;
import itstep.learning.model.TaskModel;
import itstep.learning.service.auth.AuthService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@Singleton
public class HomeServlet extends HttpServlet {
    @Inject
    private DataContext dataContext ;
    @Inject
    private AuthService authService ;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User authUser = authService.getAuthUser() ;

        List<Team> teams = authUser == null ? null
            : dataContext.getTeamDao().getUserTeams( authUser ) ;
        req.setAttribute( "teams", teams ) ;

        List<Task> tasks = authUser == null ? null
            : dataContext.getTaskDao().getUserTask( authUser ) ;
        req.setAttribute( "tasks", tasks ) ;

        req.setAttribute( "viewName", "index" ) ;
        req.getRequestDispatcher( "WEB-INF/_layout.jsp" ).forward( req, resp ) ;
    }

    @Override
    protected void doPost( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {
        TaskModel taskModel = new TaskModel() ;
        try {
            taskModel.setAuthor( authService.getAuthUser() ) ;
            if( taskModel.getAuthor() == null ) {
                throw new RuntimeException( "Unauthorized" ) ;
            }
            taskModel.setName( req.getParameter( "task-name" ) ) ;
            if( taskModel.getName() == null || taskModel.getName().equals( "" ) ) {
                throw new RuntimeException( "Missing required parameter: task-name" ) ;
            }
            String param = req.getParameter( "task-team" ) ;
            if( param == null || param.equals( "" ) ) {
                throw new RuntimeException( "Missing required parameter: task-team" ) ;
            }
            try { taskModel.setIdTeam( param ) ; }
            catch( IllegalArgumentException ignored ) {
                throw new RuntimeException( "Invalid value: task-team" ) ;
            }
            param = req.getParameter( "task-deadline" ) ;
            if( param == null || param.equals( "" ) ) {
                throw new RuntimeException( "Missing required parameter: task-deadline" ) ;
            }
            try { taskModel.setDeadline( param ); ; }
            catch( ParseException ignored ) {
                throw new RuntimeException( "Invalid value: task-deadline" ) ;
            }
            param = req.getParameter( "task-priority" ) ;
            if( param == null || param.equals( "" ) ) {
                throw new RuntimeException( "Missing required parameter: task-priority" ) ;
            }
            try { taskModel.setPriority( Byte.parseByte( param ) ) ; }
            catch( NumberFormatException ignored ) {
                throw new RuntimeException( "Invalid value: task-priority" ) ;
            }
            if( dataContext.getTaskDao().add( taskModel ) ) {
                resp.getWriter().print( "OK" ) ;
            }
            else throw new RuntimeException( "Inner error" ) ;
        }
        catch( RuntimeException ex ) {
            System.err.println( ex.getMessage() ) ;
            resp.getWriter().print( ex.getMessage() ) ;
        }
    }
}
