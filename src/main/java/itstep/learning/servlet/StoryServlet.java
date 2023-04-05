package itstep.learning.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import itstep.learning.data.DataContext;
import itstep.learning.data.entity.Story;
import itstep.learning.data.entity.User;
import itstep.learning.model.StoryViewModel;
import itstep.learning.service.auth.AuthService;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Singleton
public class StoryServlet extends HttpServlet {
    private final AuthService authService ;
    private final DataContext dataContext ;

    @Inject
    public StoryServlet( AuthService authService, DataContext dataContext ) {
        this.authService = authService ;
        this.dataContext = dataContext ;
    }

    @Override
    protected void doGet( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {
        resp.setHeader( "Content-Type", "application/json" ) ;

        String taskId = req.getParameter( "task-id" ) ;
        try {
            UUID.fromString( taskId ) ;
        }
        catch( IllegalArgumentException ignored ) {
            resp.setStatus( 400 ) ;
            resp.getWriter().print( "\"Invalid parameter: task-id (UUID required)\"" ) ;
            return ;
        }
        List<Story> stories = dataContext.getStoryDao().getListByTask( taskId ) ;
        List<StoryViewModel> storyViewModels = new ArrayList<>() ;
        for( Story story : stories ) {
            StoryViewModel model = new StoryViewModel(
                story,
                dataContext.getUserDao().getById( story.getIdUser() ),
                null
            ) ;
            if( story.getIdReply() != null ) {
                Story reply = dataContext.getStoryDao().getById( story.getIdReply() ) ;
                model.setReplyStory( new StoryViewModel(
                    reply,
                    dataContext.getUserDao().getById( reply.getIdUser() ),
                    null
                ) ) ;
            }
            storyViewModels.add( model ) ;
        }
        resp.getWriter().print(
            // new Gson().toJson( stories )
            new GsonBuilder().serializeNulls().create().toJson( storyViewModels )
        ) ;
    }

    @Override
    protected void doPost( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {
        String message = this.addStory( req ) ;
        JSONObject result = new JSONObject() ;
        result.put( "status", "OK".equals( message ) ? 200 : 400 ) ;
        result.put( "message", message ) ;
        resp.getWriter().print( result ) ;
    }

    private String addStory( HttpServletRequest req ) {
        Story story = new Story() ;
        User user = authService.getAuthUser() ;
        if( user == null ) {
            return  "Unauthorized" ;
        }
        story.setIdUser( user.getId() ) ;
        String param = req.getParameter( "story-text" ) ;
        if( param == null || param.equals( "" ) ) {
            return "Missing required parameter: story-text" ;
        }
        story.setContent( param ) ;

        param = req.getParameter( "story-id-task" ) ;
        if( param == null || param.equals( "" ) ) {
            return "Missing required parameter: story-id-task" ;
        }
        try {
            story.setIdTask( UUID.fromString( param ) ) ;
        }
        catch( IllegalArgumentException ignored ) {
            return "Invalid value: story-id-task" ;
        }
        return dataContext.getStoryDao().add( story )
            ? "OK"
            : "Inner error" ;
    }
}