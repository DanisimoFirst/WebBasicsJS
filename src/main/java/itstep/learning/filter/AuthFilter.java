package itstep.learning.filter;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import itstep.learning.service.auth.AuthService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
public class AuthFilter implements Filter {   // Auth Middleware
    @Inject
    private AuthService authService ;
    @Inject
    private Logger logger ;   // Guice auto inject JUL

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // logger.log( Level.WARNING, "Warning from AuthFilter" ) ;

        HttpServletRequest request = (HttpServletRequest) servletRequest ;
        // System.out.println("Auth filter");
        if( request.getParameter( "logout" ) != null ) {
            authService.logout( request ) ;
            // изменение статуса авторизации должно перегружать страницу
            HttpServletResponse response = (HttpServletResponse) servletResponse ;
            response.sendRedirect( request.getContextPath() + "/home" ) ;
            return ;  // sendRedirect - не прекращает обработку
        }
        authService.authorize( request ) ;
        servletRequest.setAttribute( "authUser", authService.getAuthUser() ) ;
        filterChain.doFilter( servletRequest, servletResponse ) ;
    }

    @Override
    public void destroy() {

    }
}
