package itstep.learning.filter;

import com.google.inject.Singleton;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
public class CharsetFilter implements Filter {
    private FilterConfig filterConfig ;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig ;
    }

    @Override
    public void doFilter(
            ServletRequest  servletRequest,   // !! обобщенный тип ServletRequest
            ServletResponse servletResponse,  // реально это HttpServletRequest
            FilterChain filterChain           // цепочка фильтров
            ) throws IOException, ServletException
    {
        HttpServletRequest req = (HttpServletRequest) servletRequest ;
        HttpServletResponse resp = (HttpServletResponse) servletResponse ;
        req.setCharacterEncoding( "UTF-8" ) ;  // установка кодировки чтения из запроса. ДО ПЕРВОГО ЧТЕНИЯ
        resp.setCharacterEncoding( "UTF-8" ) ;
        // System.out.println("Forward");
        // цепочку фильтров необходимо продолжить. Иначе она будет прервана и запрос прекратит обработку
        filterChain.doFilter( servletRequest, servletResponse ) ;
        // после вызова цепочки - обратный ход (обработка ответа)
        // System.out.println("Backward");
    }

    @Override
    public void destroy() {
        filterConfig = null ;
    }
}
/*
Фильтры (сервлетные фильтры) - сетевые классы для концепции Middleware -
создания "цепочки" задач с возможностью внедрения кода как на этапе
обработки запроса (прямой ход), так и на этапе обработки ответа (обратный ход)

Задание: Создать фильтр formsFilter, встроить его после CharsetFilter
В фильре реализовать проверку: если в запросе есть данные формы (текст), то перенаправить запрос на
/formsprocessor, иначе не принимать действий
Перенести код обработки формы в formsprocessorServlet и подключить сервлет к пути /formsprocessor
          formsServlet->JSP
/forms <
         /formsprocessor -> formsprocessorServlet -> JSP (без формы, только результат)
 */
/*
Инверсия управления в веб-проектах (Guice)
Добавляем две зависимости
<!-- https://mvnrepository.com/artifact/com.google.inject/guice -->
<!-- https://mvnrepository.com/artifact/com.google.inject.extensions/guice-servlet -->

- заменяем файл web.xml
- создаем пакет ioc
  = класс ConfigListener extends GuiceServletContextListener
  = класс RouterModule extends ServletModule
  = класс ServiceModule extends AbstractModule
- переносим коды управления фильтрами/сервлетами в RouterModule
- убираем аннотации @WebServlet, @WebFilter и добавляем для
   всех фильтров и сервлетов аннотацию @Singleton  (из com.google.inject)
- Enjoy
 */