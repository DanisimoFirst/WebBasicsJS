package itstep.learning.ioc;

import com.google.inject.AbstractModule;
import itstep.learning.ws.WebsocketConfigurator;

public class WebsocketModule extends AbstractModule {
    @Override
    protected void configure() {
        requestStaticInjection( WebsocketConfigurator.class ) ;
    }
}