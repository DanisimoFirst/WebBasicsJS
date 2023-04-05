package itstep.learning.servlet;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import itstep.learning.model.FormsModel;
import itstep.learning.service.HashService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

@Singleton
public class FormsServlet extends HttpServlet {
    @Inject
    private HashService hashService ;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession() ;
        FormsModel model = (FormsModel) session.getAttribute( "model" ) ;
        if( model == null ) {
            model = parseModel( req ) ;
        }
        else {
            session.removeAttribute( "model" ) ;
        }

        req.setAttribute( "formsModel", model ) ;
        req.getRequestDispatcher( "WEB-INF/forms.jsp" )
                .forward( req, resp ) ;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        FormsModel model = parseModel( req ) ;

        HttpSession session = req.getSession() ;
        session.setAttribute( "model", model ) ;
        resp.sendRedirect( req.getRequestURI() ) ;
    }

    private FormsModel parseModel(HttpServletRequest req ) {
        FormsModel model = new FormsModel() ;
        model.setMethod( req.getMethod() ) ;
        // region "text"
        String text = req.getParameter( "text" ) ;   // "text" - name of <input name="text" .../>
        if( text == null ) {   // нет параметра (скорее всего нет передачи формы)
            model.setText( "NULL" ) ;
        }
        else if( text.isEmpty() ) {   // параметр передан, но значение отсутствует
            model.setText( "empty" ) ;
        }
        else {
            model.setText( text + " " + hashService.getHexHash( text ) ) ;
        }
        // endregion
        // region "number"
        String numberParam = req.getParameter( "number" ) ;
        if( numberParam == null ) {
            model.setNumber( -1 ) ;
        }
        else if( numberParam.isEmpty() ) {
            model.setNumber( 0 ) ;
        }
        else {
            try { model.setNumber( Double.parseDouble( numberParam ) ) ; }
            catch( NumberFormatException ignored ) { model.setNumber( -0.1 ) ; }
        }
        // endregion
        // region "date"
        String dateParam = req.getParameter( "date" ) ;
        if( dateParam == null ) {
            model.setDate( null ) ;
        }
        else if( dateParam.isEmpty() ) {
            model.setDate( new Date() ) ;
        }
        else {
            String dateFormat = "yyyy-MM-dd" ;
            SimpleDateFormat format = new SimpleDateFormat( dateFormat ) ;
            try { model.setDate( format.parse( dateParam ) ) ; }
            catch( ParseException ignored ) { model.setDate( Date.from( Instant.EPOCH ) ) ; }
        }
        // endregion
        return model ;
    }
}
/*
Д.З. Сверстать две формы на странице /forms
Одна методом GET, другая POST, обе на action /forms
Заложить передачу нескольких полей
- строка
- число
- дата
- цвет
 */