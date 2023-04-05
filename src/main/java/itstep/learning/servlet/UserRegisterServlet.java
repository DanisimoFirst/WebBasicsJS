package itstep.learning.servlet;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import itstep.learning.data.dao.UserDao;
import itstep.learning.model.UserModel;
import itstep.learning.service.HashService;
import itstep.learning.service.UploadService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Map;

@Singleton
public class UserRegisterServlet extends HttpServlet {

    @Inject
    private UploadService uploadService ;
    @Inject
    private HashService hashService ;
    @Inject
    private UserDao userDao ;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute( "viewName", "reg-user" ) ;
        HttpSession session = req.getSession() ;
        String regMessage = (String) session.getAttribute( "reg-message" ) ;
        if( regMessage != null ) {
            req.setAttribute( "reg-message", regMessage ) ;
            session.removeAttribute( "reg-message" ) ;
        }
        req.getRequestDispatcher( "WEB-INF/_layout.jsp" ).forward( req, resp ) ;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String regMessage ;
        try {
            UserModel model = parseModel( req ) ;
            validateModel( model ) ;
            if( userDao.add( model ) ) {
                regMessage = "OK" ;
            }
            else {
                regMessage = "Add fail" ;
            }
        }
        catch( IllegalArgumentException ex ) {
            System.err.println( ex.getMessage() ) ;
            regMessage = ex.getMessage() ;
        }
        HttpSession session = req.getSession() ;
        session.setAttribute( "reg-message", regMessage ) ;
        resp.sendRedirect( req.getRequestURI() ) ;
    }
    private UserModel parseModel( HttpServletRequest req ) throws IllegalArgumentException {
        try {
            Map<String, FileItem> parameters = uploadService.parse( req ) ;
            UserModel model = new UserModel() ;
            model.setLogin( parameters.get( "user-login" ).getString() ) ;
            model.setPass1( parameters.get( "user-pass1" ).getString() ) ;
            model.setPass2( parameters.get( "user-pass2" ).getString() ) ;
            model.setName(  parameters.get( "user-name"  ).getString() ) ;
            model.setEmail( parameters.get( "user-email" ).getString() ) ;
            // проверка и сохранение файла
            FileItem avatar = parameters.get( "user-avatar" ) ;
            if( ! avatar.isFormField() ) {    // это файловое поле
                if( avatar.getSize() > 0 ) {  // есть данные
                    String filename = avatar.getName() ;
                    int dotPosition = filename.lastIndexOf( '.' ) ;
                    String extension = filename.substring( dotPosition ) ;
                    // TODO: проверить на допустимое расширение файла
                    // Имя файла сохранять опасно - генерируем случайное имя и проверяем на существование файла
                    String savedName = "" ;
                    String path = req.getServletContext().getRealPath( "/" ) + "../avatars/" ;
                    File file ;
                    do {
                        savedName = hashService.getHexHash( savedName + System.nanoTime() ) + extension ;
                        file = new File( path, savedName ) ;
                    } while( file.exists() ) ;
                    avatar.write( file ) ;  // сохраняем файл в постоянном хранилище
                    model.setAvatar( savedName ) ;
                }
            }
            return model ;
        }
        catch( Exception ex ) {
            if( ex instanceof IllegalArgumentException ) throw (IllegalArgumentException) ex ;
            System.err.println( ex.getMessage() ) ;
            return null ;
        }
    }
    private UserModel parseModelOld( HttpServletRequest req ) {
        UserModel model = new UserModel() ;
        model.setLogin( req.getParameter( "user-login" ) ) ;
        model.setPass1( req.getParameter( "user-pass1" ) ) ;
        model.setPass2( req.getParameter( "user-pass2" ) ) ;
        model.setName(  req.getParameter( "user-name"  ) ) ;
        model.setEmail( req.getParameter( "user-email" ) ) ;
        return model ;
    }
    private void validateModel( UserModel model ) throws IllegalArgumentException {
        String message = null ;
        if( model == null ) message = "Missing all parameters" ;
        else if( model.getLogin() == null ) message = "Missing parameter: user-login" ;
        else {
            String login = model.getLogin() ;
            if( "".equals( login ) ) message = "user-login should not be empty" ;
            else {
                // TODO: проверить логин на занятость
                /*
                Д.З. В UserDao реализовать метод проверки логина на свободность/занятость
                использовать его в валидаторе модели пользователя
                * реализовать проверку загруженных файлов-аватарок на допустимые расширения
                 */
            }
        }
        if( message == null ) {
            String pass1 = model.getPass1() ;
            String pass2 = model.getPass2() ;
            if( pass1 == null || pass2 == null )  {
                message = "Missing parameter: user-passwords" ;
            }
            else {
                if( pass1.length() < 3 ) message = "Password too short" ;
                if( ! pass1.equals( pass2 ) ) message = "Passwords mismatch" ;
                // TODO: проверить пароль на сложность
            }
        }
        if( message != null ) {
            throw new IllegalArgumentException( message ) ;
        }
    }
}
/*
Uploading - загрузка файлов на сервер
Особенность: файлы передаются запросами multipart/form-data

POST ...                      POST
Headers                       GeneralHeaders
Body                          Separator-----
                              Headers-1 (form-url-encoded)
                              Body-1 (name=User&pass=123)
                              Separator------
                              Headers-1 (file attachment)
                              Body-2  (13n1t349-vqn30)

Сервлеты могут работать с одной из форм
req.getParameter()            req.getPart()
Конфигурация сервлета производится либо аннотацией @MultipartConfig
либо в web.xml инструкцией <multipart-config /> в определении сервлета

В ситуации с инъекциями зависимостей, особенно через конструкторы, стандартные
аннотации могут не сработать. Рекомендуется использовать сторонние сканнеры
multipart-запросов, например, https://mvnrepository.com/artifact/commons-fileupload/commons-fileupload
 */