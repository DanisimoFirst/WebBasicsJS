package itstep.learning.servlet;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;

@Singleton
public class DownloadServlet extends HttpServlet {
    @Inject @Named( "AvatarFolder" )
    private String avatarFolder ;

    @Override
    protected void doGet( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {
        String path = req.getServletContext().getRealPath( "/" ) + avatarFolder ;   // из Upload (RegUser)
        String requestedFile = req.getPathInfo() ;
        File file = new File( path, requestedFile ) ;
        if( file.isFile() && file.canRead() ) {
            String mimeType = Files.probeContentType( file.toPath() ) ;
            // проверка типа файла на изображение
            if( ! mimeType.startsWith( "image" ) ) {
                resp.setStatus( 415 ) ;
                resp.getWriter().print( "Unsupported Media Type: " + mimeType ) ;
                return ;
            }

            resp.setContentType( mimeType ) ;

            // resp.setContentType( "application/octet-stream" ) ;
            // resp.setHeader( "Content-Disposition", "attachment; filename=\"download.jpg\"" ) ;

            // piping - передача данных из одного потока в другой
            byte[] buf = new byte[1024] ;
            try( InputStream  reader = Files.newInputStream( file.toPath() ) ;
                 OutputStream writer = resp.getOutputStream() ) {
                int len ;
                while( ( len = reader.read( buf ) ) != -1 ) {
                    writer.write( buf, 0, len ) ;
                }
            }
            catch( IOException ex ) {
                resp.setStatus( 500 ) ;
                System.err.println( "DownloadServlet: " + ex.getMessage() ) ;
            }
        }
    }
}
/*
Модифицировать алгоритмы проверки допустимых для загрузки типов файлов
- исключить image/gif, image/avif : AV1 Image File Format (AVIF)
* если обнаруживаются активные запросы (на .class, .php, .exe), то выводить
  предупреждение в консоли сервера
 */
/*
    Для задач загрузки файлов используем шаблонный сервлет "/image/*"
    В таком случае части запроса определяются методами:
    http://localhost:8080/WebBasics/image/123.jpg   http://localhost:8080/WebBasics/image/
    req.getContextPath()   /WebBasics
    req.getServletPath()   /image
    req.getPathInfo()      /123.jpg                                    /

    http://localhost:8080/WebBasics/image -- 404


    Передача файлов должна сопровождаться заголовком Content-Type
    иначе по умолчанию он считается text/plain и может некорректно отображаться
    тип можно получить методом Files.probeContentType( file.toPath() ) ;
    но также желательно ограничить загрузку файлов не-изображений
    Если стоит задача простой загрузки (не как картинок, а именно как скачивание)
    то
    а) указать Content-Type: application/octet-stream  (бинарный поток)
    б) предложить имя файла для сохранения в заголовке
       Content-Disposition: attachment; filename="filename.jpg"
 */