package itstep.learning.ws;

import com.google.inject.Inject;
import com.google.inject.Injector;
import itstep.learning.data.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import java.lang.reflect.Field;

public class WebsocketConfigurator extends ServerEndpointConfig.Configurator {
    @Inject
    private static Injector injector ;  // ! статическая инъекция

    /**
     * Управление созданием экземпляра Вебсокет-сервера
     */
    @Override
    public <T> T getEndpointInstance( Class<T> endpointClass ) throws InstantiationException {
        return injector.getInstance( endpointClass ) ;
    }

    /**
     * Управление соединением с клиентом вебсокета
     */
    @Override
    public void modifyHandshake(
        ServerEndpointConfig sec,
        HandshakeRequest request,
        HandshakeResponse response ) {
        super.modifyHandshake( sec, request, response ) ;
        // request - не НТТР, а HTTP-request, передавший код JS, который открывает соединение
        // находится внутри него, но в приватном поле. Извлекаем его методами рефлексии
        HttpServletRequest httpServletRequest = null ;
        try {
            for( Field field :                                   // Цикл по всем полям (рефлексии),
                request.getClass().getDeclaredFields() ) {   // которые объявлены в классе объекта request
                if( HttpServletRequest.class                     // Проверяем совместимость типов: объекту типа HttpServletRequest
                                                                 .isAssignableFrom( field.getType() ) ) {   // может быть присвоено значение типа данного поля
                    field.setAccessible( true ) ;
                    httpServletRequest = (HttpServletRequest) field.get( request ) ;
                }
            }
            // Field requestField = request.getClass().getDeclaredField( "request" ) ;
        }
        catch( IllegalAccessException ex ) {
            System.err.println( "modifyHandshake " + ex.getMessage() ) ;
        }
        User authUser = null ;
        if( httpServletRequest != null ) {
            authUser = (User) httpServletRequest.getAttribute( "authUser" ) ;
        }
        if( authUser != null ) {   // переносим данные об авторизованном пользователе в ServerEndpointConfig
            sec.getUserProperties().put( "authUser", authUser ) ;
        }
        else {
            throw new RuntimeException( "Unauthorized user in websocket" ) ;
        }
    }
}