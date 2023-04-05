package itstep.learning.ws;

import com.google.gson.Gson;
import com.google.inject.Inject;
import itstep.learning.data.DataContext;
import itstep.learning.data.entity.Story;
import itstep.learning.data.entity.User;
import itstep.learning.model.StoryViewModel;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@ServerEndpoint(
    value = "/chat",                              // url вебсокет-сервера (ws://.../chat)
    configurator = WebsocketConfigurator.class )  // класс, управляющий работой Endpoint (в т.ч. создание экземпляра)
public class WebsocketServer {
    private final DataContext dataContext ;

    private static final Set<Session> sessions =
        Collections.synchronizedSet(
            new HashSet<>()
        ) ;
    private static final Gson gson = new Gson() ;

    @Inject
    public WebsocketServer( DataContext dataContext ) {
        this.dataContext = dataContext ;
    }

    @OnOpen
    public void onOpen( Session session, EndpointConfig configurator ) {
        User authUser = (User) configurator.getUserProperties().get( "authUser" ) ;
        // if( authUser == null ) { onClose( session ) ; }
        session.getUserProperties()             // на событии onOpen configurator доступен,
               .put( "authUser", authUser ) ;   // для других событий переносим authUser в сессию
        sessions.add( session ) ;
        // sendToAll( String.format( "User '%s' Enter chat", authUser.getName() ) ) ;
    }
    @OnMessage
    public void onMessage( String message, Session session ) {
        StoryViewModel res = this.addStory( message, session ) ;
        if( res == null ) {
            try {
                session.getBasicRemote().sendText( "{ \"status\": 400 }" ) ;
            }
            catch( IOException ex ) {
                ex.printStackTrace() ;
            }
        }
        else {
            sendToAll( gson.toJson( res ) ) ;
        }
    }
    @OnClose
    public void onClose( Session session ) {
        // User authUser = (User) session.getUserProperties().get( "authUser" ) ;
        sessions.remove( session ) ;
        // sendToAll( String.format( "User '%s' Exit chat", authUser.getName() ) ) ;
    }
    @OnError
    public void onError( Throwable ex, Session session ) {
        ex.printStackTrace() ;
    }

    private void sendToAll( String message ) {
        for( Session session : sessions ) {
            try {
                session.getBasicRemote().sendText( message ) ;
            }
            catch( IOException ex ) {
                ex.printStackTrace() ;
            }
        }
    }


    private StoryViewModel addStory( String message, Session session ) {
        ChatMessage chatMessage = gson.fromJson( message, ChatMessage.class ) ;
        if( chatMessage.getContent() == null
            || chatMessage.getContent().length() < 1 ) {
            return null ;
        }
        if( chatMessage.getTaskId() == null ) {
            return null ;
        }
        User authUser = (User) session.getUserProperties().get( "authUser" ) ;
        Story story = new Story() ;
        story.setIdUser( authUser.getId() ) ;
        story.setIdTask( chatMessage.getTaskId() ) ;
        story.setContent( chatMessage.getContent() ) ;
        story = dataContext.getStoryDao().create( story ) ;
        if( story == null ) {
            return null ;
        }
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
        return model ;
    }

    private static class ChatMessage {
        UUID taskId ;
        String content ;

        public UUID getTaskId() {
            return taskId;
        }

        public void setTaskId( UUID taskId ) {
            this.taskId = taskId ;
        }

        public String getContent() {
            return content;
        }

        public void setContent( String content ) {
            this.content = content;
        }
    }
}
/*
Websocket Server
Отдельный сервер, работающий по другому протоколу (ws:// wss://)
1. Зависимость
<!-- https://mvnrepository.com/artifact/javax.websocket/javax.websocket-api -->
2. Класс сервера - как отдельный endpoint
Общая концепция - событийная модель. Описываются методы, которые будут
  вызываться по определенным событиям канала (клиент-сервер)
Сигнатуры методов не строго определены (возможны перегрузки). Одним из
  основных параметров является сессия - объект, ответственный за канал
  коммуникации. Для всех подключений выделяются сессии и основной задачей
  вебсокетов обычно является уведомление всех подключенных участников о
  разных событиях. Поэтому сервер хранит коллекцию сессий, изменяя ее по
  событиям открытия/закрытия соединений.
3. Основных событий у сервера 4:
    open              Обработчики этих событий задаются как на стороне
    message           сервера, так и на стороне клиента.
    close
    error
    Начало коммуникации происходит с клиента. В JS подключение инициируется
    созданием нового объекта new WebSocket( "ws://localhost:8080/WebBasics/chat" )
    !! обратить внимание на другой протокол ws:// ( если настроен SSL, то wss:// )
    После установки подключения со стороны клиента отправка данных выглядит как
    websocket.send( "...." )
    Это вызывает событие @OnMessage на сервере
    В свою очередь сервер пересылает данное сообщение всем сессиям
    На их клиентах срабатывают обработчики onMessage (без необходимости
     перепроверять наличие сообщений)
 */