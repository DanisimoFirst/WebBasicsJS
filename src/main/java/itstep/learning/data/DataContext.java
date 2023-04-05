package itstep.learning.data;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import itstep.learning.data.dao.StoryDao;
import itstep.learning.data.dao.TaskDao;
import itstep.learning.data.dao.TeamDao;
import itstep.learning.data.dao.UserDao;

@Singleton
public class DataContext {
    private final UserDao  userDao  ;
    private final TeamDao  teamDao  ;
    private final TaskDao  taskDao  ;
    private final StoryDao storyDao ;

    @Inject
    public DataContext( UserDao userDao, TeamDao teamDao, TaskDao taskDao, StoryDao storyDao ) {
        this.userDao  = userDao  ;
        this.teamDao  = teamDao  ;
        this.taskDao  = taskDao  ;
        this.storyDao = storyDao ;
    }

    public StoryDao getStoryDao() {
        return storyDao ;
    }

    public TaskDao getTaskDao() {
        return taskDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public TeamDao getTeamDao() {
        return teamDao;
    }
}
