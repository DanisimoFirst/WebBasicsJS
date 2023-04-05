package itstep.learning.ioc;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class ConfigListener extends GuiceServletContextListener {
    @Override                              // Listener (ServletContextListener) - обработчик
    protected Injector getInjector() {     // события сервера ServletContext Create - первого
        return Guice.createInjector(       // события обработки сайта (до фильтров, до сервлетов).
            new RouterModule(),        // Внедряется через web.xml и создает Injector -
            new ServiceModule(),       // точку управления внедрением зависимостей.
            new StringModule(),        // Традиционно модули разделяют на два (и более) -
            new LoggerModule(),        // отдельно конфигурация фильтров/сервлетов (RouterModule),
            new WebsocketModule()      // отдельно служб (реализаций интерфейсов) (ServiceModule)
        ) ;                                // + тематические модули, например, строки
    }
}