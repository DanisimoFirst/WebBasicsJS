package itstep.learning.ioc;

import com.google.inject.servlet.ServletModule;
import itstep.learning.filter.AuthFilter;
import itstep.learning.filter.CharsetFilter;
import itstep.learning.filter.DbCheckFilter;
import itstep.learning.servlet.*;

public class RouterModule extends ServletModule {
    @Override
    protected void configureServlets() {

        // задаем фильтры
        filter( "/*" ).through( CharsetFilter.class ) ;
        // filter( "/*" ).through( DbCheckFilter.class ) ;
        // используя Regex отключаем фильтры для загрузки файлов (для ускорения работы)
        filterRegex( "^/(?!image/.*).*$" ).through( DbCheckFilter.class ) ;
        // filter( "/*" ).through( AuthFilter.class    ) ;
        filterRegex( "^/(?!image/.*).*$" ).through( AuthFilter.class ) ;

        // и сервлеты
        serve( "/"          ).with( HomeServlet.class  ) ;
        serve( "/home"      ).with( HomeServlet.class  ) ;
        serve( "/forms"     ).with( FormsServlet.class ) ;
        serve( "/about"     ).with( AboutServlet.class ) ;
        serve( "/auth"      ).with( UserAuthServlet.class ) ;
        serve( "/register"  ).with( UserRegisterServlet.class ) ;
        serve( "/image/*"   ).with( DownloadServlet.class ) ;
        serve( "/profile/*" ).with( UserProfileServlet.class ) ;
        serve( "/story"     ).with( StoryServlet.class ) ;
    }
}
